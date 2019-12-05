package com.hanyun.scm.api.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.IdGenerateType;
import com.hanyun.scm.api.consts.MapConsts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.*;
import com.hanyun.scm.api.domain.*;
import com.hanyun.scm.api.domain.request.InspectionPickingIn.InspectionPickingInDetailRequest;
import com.hanyun.scm.api.domain.request.InspectionPickingIn.InspectionPickingInRequest;
import com.hanyun.scm.api.domain.request.Replenishment.ReplenishmentApplyRequest;
import com.hanyun.scm.api.domain.request.distribution.DistributionOrderModifyRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceCreateRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceModifyRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.exception.InspectionpickinginException;
import com.hanyun.scm.api.exception.ProcessInstanceException;
import com.hanyun.scm.api.service.*;
import com.hanyun.scm.api.utils.ActivitiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class InspectionPickingInServiceImpl implements InspectionPickingInService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectionPickingInServiceImpl.class);
    @Resource
    private IdGenerateSeqService idGenerateSeqService;

    @Resource
    private InspectionPickingInDetailDao inspectionPickingInDetailDao;

    @Resource
    private InspectionPickingInDao inspectionPickingInDao;

    @Resource
    private StockQuantService stockQuantService;

    @Resource
    private DistributionOrderDao distributionOrderDao;

    @Resource
    private ReplenishmentApplyDao replenishmentApplyDao;

    @Resource
    private ReplenishmentApplyDetailDao replenishmentApplyDetailDao;

    @Resource
    private DistributionOrderDetailDao distributionOrderDetailDao;

    @Resource
    private ProcessInstanceService processInstanceService;

    @Resource
    private ProcessDefinitionService processDefinitionService;

    @Resource
    private GoodsOdooService goodsOdooService;

    @Override
    public HttpResponse select(InspectionPickingInRequest inspectionPickingInRequest) {

        try {
            int count = inspectionPickingInDao.countAllOrders(inspectionPickingInRequest);
            inspectionPickingInRequest.setCount(count);
            List<InspectionPickingIn> list = inspectionPickingInDao.selectOrders(inspectionPickingInRequest);
            if (!StringUtils.isEmpty(inspectionPickingInRequest.getUserId())) {
                for (InspectionPickingIn inspectionPickingIn : list) {
                    inspectionPickingIn.setAuditStatus(processInstanceService.queryAuditor(inspectionPickingIn.getInspectionId(), inspectionPickingInRequest.getUserId()));
                    List<String> auditors = processInstanceService.queryAuditors(inspectionPickingIn.getInspectionId());
                    auditors.add(inspectionPickingIn.getOperatorId());
                    inspectionPickingIn.setHistoryStatus(auditors.size() > 1 && auditors.contains(inspectionPickingInRequest.getUserId()));
                }
            }
            BaseResponse response = new BaseResponse(count, list);
            return HttpResponse.success(response);
        } catch (Exception e) {
            LOGGER.error("验货入库单查询失败", e);
            return HttpResponse.failure(ResultCode.INSPECTION_QUERY_ERROR);
        }
    }

    @Override
    public HttpResponse deleteOrder(String inspectionId) {
        try {
            InspectionPickingIn inspectionPickingIn = inspectionPickingInDao.selectByPrimaryKey(inspectionId);
            if (inspectionPickingIn == null) {
                throw new InspectionpickinginException("未查到单号:[" + inspectionId + "]的数据。");
            }

            List<InspectionPickingInDetail> goodsList = inspectionPickingInDetailDao.selectByInspectionId(inspectionId);
            //配送单map
            Map<String, DistributionOrderDetail> map = getMap(inspectionPickingIn);
            String distributionOrderId = inspectionPickingIn.getDistributionOrderId();
            //删除更新配送单入库数量和收货状态
            int updateDistriRow = updateDistriForInNumAndStatus(goodsList, map, distributionOrderId, inspectionPickingIn);
            if (updateDistriRow <= 0) {
                throw new InspectionpickinginException("更新配送单详情的入库数量或单据收货状态失败。");
            }

            //删除单据
            int deleteOrderRow = inspectionPickingInDao.deleteByInspectionId(inspectionId);
            if (deleteOrderRow <= 0) {
                throw new InspectionpickinginException("删除单据失败。");
            }

            //删除详情
            int deleteDetailRow = inspectionPickingInDetailDao.deleteByInspectionId(inspectionId);
            if (deleteDetailRow <= 0) {
                LOGGER.error("收货入库单详情删除失败");
                return HttpResponse.failure(ResultCode.INSPECTION_DELETE_ERROR);
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("收货入库单删除失败", e);
            return HttpResponse.failure(ResultCode.INSPECTION_DELETE_ERROR);
        }
    }

    @Override
    public HttpResponse create(InspectionPickingIn inspectionPickingIn) {
        try {
            //验证
            List<InspectionPickingInDetail> goodsList = inspectionPickingIn.getDetailList();
            if(goodsList == null || goodsList.size() == 0){
                return HttpResponse.failure(ResultCode.INSPECTION_DETAIL_INSERT_GOODS_ERROR);
            }
            for(InspectionPickingInDetail detail : goodsList){
                if(StringUtils.isEmpty(detail.getGoodsId())){
                    return HttpResponse.failure(ResultCode.INSPECTION_DETAIL_INSERT_GOODS_ID_ERROR);
                }
                if(detail.getReceiptNum() == null || detail.getReceiptNum()==0){
                    return HttpResponse.failure(ResultCode.INSPECTION_DETAIL_INSERT_GOODS_ERROR);
                }
            }
            String inspectionPickingInId = inspectionPickingIn.getInspectionId();
            String inspectionPickingDocumentId = inspectionPickingIn.getInspectionDocumentId();
            //配送单详情Map
            Map<String, DistributionOrderDetail> map = getMap(inspectionPickingIn);
            //配送单Id
            String distributionOrderId = inspectionPickingIn.getDistributionOrderId();

            if (StringUtils.isEmpty(inspectionPickingInId)) {//新增
                inspectionPickingInId = IdUtil.uuid();
                inspectionPickingDocumentId = idGenerateSeqService.generateId(IdGenerateType.YSRK);
                inspectionPickingIn.setInspectionDocumentId(inspectionPickingDocumentId);
                inspectionPickingIn.setInspectionId(inspectionPickingInId);

                int rows = inspectionPickingInDao.insertSelective(inspectionPickingIn);
                if (rows == Consts.DB_WITHOUT_HANDLE) {
                    LOGGER.error("收货入库单插入失败");
                    return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_INSERT_ERROR);
                }
            } else {
                //如果有单号
                int updateRow = inspectionPickingInDao.updateByPrimaryKeySelective(inspectionPickingIn);
                if (updateRow == Consts.DB_WITHOUT_HANDLE) {
                    return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_UPDATE_ERROR);
                }
                //删除更新配送单入库数量和收货状态
                List<InspectionPickingInDetail> list = inspectionPickingInDetailDao.selectByInspectionId(inspectionPickingInId);
                int updateDistriRow = updateDistriForInNumAndStatus(list, map, distributionOrderId, inspectionPickingIn);
                if (updateDistriRow <= 0) {
                    throw new InspectionpickinginException("更新配送单详情的入库数量或单据收货状态失败。");
                }
                int deleteRow = inspectionPickingInDetailDao.deleteByInspectionId(inspectionPickingInId);
                if (deleteRow <= 0) {
                    throw new InspectionpickinginException("删除入库单详情失败。");
                }
                inspectionPickingIn = inspectionPickingInDao.selectByPrimaryKey(inspectionPickingInId);
            }
            //更新数量
            for (InspectionPickingInDetail obj : goodsList) {  //插入明细信息
                String goodsIdForInspection = obj.getGoodsId();
                int rows = insertInspctionGoods(inspectionPickingIn, obj);
                if (rows <= 0) {
                    LOGGER.error("创建商品详单失败!");
                    return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_INSERT_ERROR);
                }
                DistributionOrderDetail object = map.get(distributionOrderId + "," + goodsIdForInspection);
                if (object == null) {
                    continue;
                }
                Long receiptNum = obj.getReceiptNum();            //收货数量
                Long inNum = object.getStockInNum();              //入库数
                object.setStockInNum(receiptNum + inNum);
                int updateDistriRow = distributionOrderDetailDao.updateByDistributionOrderDetailId(object);
                if (updateDistriRow <= 0) {
                    throw new InspectionpickinginException("更新配送单的入库数量失败");
                }
            }
            //更新状态
            boolean updateFlag = false;
            for (Map.Entry<String, DistributionOrderDetail> entry : map.entrySet()) {
                if (entry.getValue().getStockInNum() < entry.getValue().getDistributionQuantity()) {
                    updateFlag = true;
                    break;
                }
            }
            //修改状态的对象
            DistributionOrderModifyRequest distributionOrder = new DistributionOrderModifyRequest();
            distributionOrder.setBrandId(inspectionPickingIn.getBrandId());
            if (!StringUtils.isEmpty(inspectionPickingIn.getStoreId())) {
                distributionOrder.setStoreId(inspectionPickingIn.getStoreId());
            }
            distributionOrder.setDistributionOrderId(distributionOrderId);


            distributionOrder.setReceiptStatus(updateFlag ? Consts.REPLENISHMENT_APPLY_RECEIPT_PORTION : Consts.REPLENISHMENT_APPLY_RECEIPT_ALL);
            int updateDisStatusRow = distributionOrderDao.updateByDistributionOrderId(distributionOrder);
            if (updateDisStatusRow <= 0) {
                throw new InspectionpickinginException("更新配送单的收货状态失败");
            }
            boolean exitStatus = processDefinitionService.exist(inspectionPickingIn.getBrandId(), Consts.PROCESS_TYPE_INSPECTION_PICKING_IN);
            if (exitStatus) {
                return HttpResponse.success(new String[]{inspectionPickingInId, inspectionPickingDocumentId, "exist"});
            }
            return HttpResponse.success(new String[]{inspectionPickingInId, inspectionPickingDocumentId});
        } catch (Exception e) {
            LOGGER.error("创建库存管理商品详单失败", e);
            return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_INSERT_ERROR);
        }
    }

    /**
     * 删除更新配送单入库数量和收货状态
     *
     * @param goodsList           详单list
     * @param map                 配送单map
     * @param distributionOrderId 配送单id
     * @param inspectionPickingIn 入库对象
     * @return update No
     */
    private int updateDistriForInNumAndStatus(List<InspectionPickingInDetail> goodsList, Map<String, DistributionOrderDetail> map, String distributionOrderId,
                                              InspectionPickingIn inspectionPickingIn) {
        for (InspectionPickingInDetail detail : goodsList) {
            String goodsIdForIns = detail.getGoodsId();
            DistributionOrderDetail object = map.get(distributionOrderId + "," + goodsIdForIns);
            if(object == null){
                continue;
            }
            object.setStockInNum(object.getStockInNum() - detail.getReceiptNum());
            int updateDistriRow = distributionOrderDetailDao.updateByDistributionOrderDetailId(object);
            if (updateDistriRow <= 0) {
                return updateDistriRow;
            }
        }
        //修改状态的对象
        DistributionOrderModifyRequest distributionOrder = new DistributionOrderModifyRequest();
        distributionOrder.setBrandId(inspectionPickingIn.getBrandId());
        if (!StringUtils.isEmpty(inspectionPickingIn.getStoreId())) {
            distributionOrder.setStoreId(inspectionPickingIn.getStoreId());
        }
        distributionOrder.setDistributionOrderId(distributionOrderId);
        distributionOrder.setReceiptStatus(Consts.REPLENISHMENT_APPLY_RECEIPT_PORTION);
        int updateDisStatusRow = distributionOrderDao.updateByDistributionOrderId(distributionOrder);
        if (updateDisStatusRow <= 0) {
            return updateDisStatusRow;
        }
        return 1;
    }

    /**
     * GET配送单详情Map
     *
     * @param inspectionPickingIn 入库对象
     * @return map
     */
    private Map<String, DistributionOrderDetail> getMap(InspectionPickingIn inspectionPickingIn) {
        String distributionOrderId = inspectionPickingIn.getDistributionOrderId();
        Map<String, DistributionOrderDetail> map = new HashMap<>();
        //查询详单对象
        DistributionOrderDetail detail = new DistributionOrderDetail();
        detail.setBrandId(inspectionPickingIn.getBrandId());
        if (!StringUtils.isEmpty(inspectionPickingIn.getStoreId())) {
            detail.setStoreId(inspectionPickingIn.getStoreId());
        }
        detail.setDistributionOrderId(distributionOrderId);
        List<DistributionOrderDetail> distributionorderList = distributionOrderDetailDao.select(detail);

        for (DistributionOrderDetail orderDetail : distributionorderList) {
            String goodsId = orderDetail.getGoodsId();
            map.put(distributionOrderId + "," + goodsId, orderDetail);
        }
        return map;
    }

    @Override
    public HttpResponse selectDetail(InspectionPickingInDetailRequest record) {
        try {
            String inspectionId = record.getInspectionId();
            InspectionPickingIn old = inspectionPickingInDao.selectByPrimaryKey(inspectionId);
            if(old == null){
                LOGGER.error("未查询到数据");
               return HttpResponse.failure(ResultCode.DATA_NOT_FOUND);
            }
            List<InspectionPickingInDetail> detailList = inspectionPickingInDetailDao.selectByInspectionId(inspectionId);
            if(!detailList.isEmpty() && detailList.size() > 0){
                Map<String, GoodsOdoo> map = goodsOdooService.getInspectionGoodsMap(old.getBrandId(), detailList);
                detailList.forEach(i -> {
                    String goodsId = i.getGoodsId();
                    boolean bl = !map.isEmpty() && map.get(goodsId) != null && !StringUtils.isEmpty(map.get(goodsId).getGoodsPic());
                    i.setGoodsPic(bl ? map.get(goodsId).getGoodsPic() : "");
                });
                old.setDetailList(detailList);
            }
            return HttpResponse.success(old);
        } catch (Exception e) {
            LOGGER.error("验货入库单详单查询失败", e);
            return HttpResponse.failure(ResultCode.INSPECTION_DETAIL_QUERY_ERROR, e);
        }
    }

    @Override
    public HttpResponse auditOrder(InspectionPickingIn inspectionPickingIn) {
        try {
            String inspectionId = inspectionPickingIn.getInspectionId();
            String auditorId = inspectionPickingIn.getAuditorId();
            String auditorName = inspectionPickingIn.getAuditorName();
            /**提审------------start*/
            //查询单据信息
            InspectionPickingIn old = inspectionPickingInDao.selectByPrimaryKey(inspectionPickingIn.getInspectionId());
            if (old == null) {
                throw new InspectionpickinginException("未查询到该单据。");
            }
            if (inspectionPickingIn.getInspectionStatus() != null && inspectionPickingIn.getInspectionStatus() == Consts.INSPECTIONPICKING_ORDER_STATUS_CONFIRMED) {
                inspectionPickingIn.setInspectionStatus(Consts.INSPECTIONPICKING_ORDER_STATUS_CONFIRMED);
            } else {
                if (!inspectionPickingIn.getAuditStatus()) {
                    InspectionPickingIn modify = new InspectionPickingIn();
                    modify.setInspectionId(inspectionId);
                    modify.setAuditorId(auditorId);
                    modify.setAuditorName(auditorName);
                    modify.setInspectionStatus(Consts.INSPECTIONPICKING_ORDER_STATUS_SAVE);
                    int row = inspectionPickingInDao.updateByPrimaryKeySelective(modify);
                    if (row <= 0) {
                        return HttpResponse.failure(ResultCode.LOGISTICSDELIVERY_MODIFY_ERROR);
                    }
                    processInstanceService.delete(inspectionId);
                    return HttpResponse.success();
                }
                ProcessInstanceModifyRequest processInstanceModifyRequest = new ProcessInstanceModifyRequest();
                processInstanceModifyRequest.setBusinessId(inspectionId);
                processInstanceModifyRequest.setUserId(inspectionPickingIn.getUserId());
                processInstanceModifyRequest.setRemark("通过, 执行");
                HttpResponse response = processInstanceService.audit(processInstanceModifyRequest);
                if (!StringUtils.equals(response.getCode(), "0") || response.getData() == null) {
                    return HttpResponse.failure(ResultCode.LOGISTICSDELIVERY_MODIFY_ERROR);
                }

                Integer instanceStatus = Integer.parseInt(response.getData().toString());
                if (instanceStatus != Consts.PROCESS_INSTANCE_STATUS_CONFIRMED) {
                    inspectionPickingIn.setInspectionStatus(Consts.INSPECTIONPICKING_ORDER_STATUS_CONIFRMING);
                } else {
                    inspectionPickingIn.setInspectionStatus(Consts.INSPECTIONPICKING_ORDER_STATUS_CONFIRMED);
                }
            }

            InspectionPickingIn modifyRequest = new InspectionPickingIn();
            modifyRequest.setInspectionStatus(inspectionPickingIn.getInspectionStatus());
            modifyRequest.setInspectionId(inspectionId);
            modifyRequest.setAuditorId(auditorId);
            modifyRequest.setAuditorName(auditorName);
            modifyRequest.setAuditTime(new Date());

            int updateRow = inspectionPickingInDao.updateByPrimaryKeySelective(modifyRequest);

            if (updateRow <= 0) {
                return HttpResponse.failure(ResultCode.INSPECTIONPICKING_ORDER_CONFIRM_ERROR);
            }

            /**提审------------end*/

            if (inspectionPickingIn.getInspectionStatus() != Consts.ORDER_STATUS_CONFIRMED) {
                return HttpResponse.success();
            }

            //获取配送商品总数
            List<InspectionPickingInDetail> detailList = inspectionPickingInDetailDao.queryByDistributionId(old.getDistributionOrderId());

            DistributionOrder distributionOrder = distributionOrderDao.selectByDistributionOrderId(old.getDistributionOrderId());

            Map<String, InspectionPickingInDetail> inspectionPickingInDetailMap = new HashMap<>();

            for (InspectionPickingInDetail inspectionPickingInDetail1 : detailList) {
                inspectionPickingInDetailMap.put(inspectionPickingInDetail1.getGoodsId(), inspectionPickingInDetail1);
            }

            //更新配送单的收货状态start
            Map<String, InspectionPickingInDetail> goodsMap = new HashMap<>();
            List<InspectionPickingInDetail> inspectionPickingInDetailList = inspectionPickingInDetailDao.selectByInspectionId(old.getInspectionId());
            inspectionPickingInDetailList.forEach(i -> goodsMap.put(i.getGoodsId(), i));
            DistributionOrderDetail record = new DistributionOrderDetail();
            record.setBrandId(distributionOrder.getBrandId());
            record.setDistributionOrderId(distributionOrder.getDistributionOrderId());
            List<DistributionOrderDetail> distributionOrderDetailList= distributionOrderDetailDao.select(record);
            boolean deliveryStatus = true;
            Long allStockInNum = distributionOrder.getReceiptedNum()!=null ? distributionOrder.getReceiptedNum():0L;    //配送单入库数
            for(DistributionOrderDetail detail : distributionOrderDetailList){
                String goodsId = detail.getGoodsId();
                if(StringUtils.isEmpty(goodsId)){
                    continue;
                }
                InspectionPickingInDetail inDetail = goodsMap.get(goodsId);
                if(inDetail == null || inDetail.getReceiptNum() == null){
                    continue;
                }

                Long receiptNum = inDetail.getReceiptNum();                     //收货数
                detail.setReceiptedNum((detail.getReceiptedNum()!=null?detail.getReceiptedNum():0L) + receiptNum);
                distributionOrderDetailDao.updateByDistributionOrderDetailId(detail);       //配送单商品入库数量
                Long distributionNum = detail.getDistributionQuantity();        //配送数量
                allStockInNum += receiptNum;
                if(detail.getReceiptedNum() < distributionNum){
                    deliveryStatus = false;
                }
            }
            DistributionOrderModifyRequest distributionModify = new DistributionOrderModifyRequest();
            distributionModify.setDistributionOrderId(distributionOrder.getDistributionOrderId());
            if(deliveryStatus){
                distributionModify.setReceiptedStatus(Consts.RECEIPTED_ALL_STATUS);
                distributionModify.setDistributionOrderStatus(Consts.DISTRIBUTION_ORDER_STATUS_DONE);
            } else {
                distributionModify.setReceiptedStatus(Consts.RECEIPTED_SECTION_STATUS);
            }
            distributionModify.setReceiptedNum(allStockInNum);
            int modifyRow = distributionOrderDao.updateByDistributionOrderId(distributionModify);
            if(modifyRow <= 0){
                throw new InspectionpickinginException("入库更新配送单状态失败");
            }
            //end

            String sourceApplyId = distributionOrder.getSourceReplenishmentId();

            String[] replenishmentApplyIds = sourceApplyId.split(",");
            ReplenishmentApplyRequest replenishmentApplyRequest = new ReplenishmentApplyRequest();
            replenishmentApplyRequest.setReplenishmentApplyIds(replenishmentApplyIds);
            replenishmentApplyRequest.setSort(1);
            List<ReplenishmentApply> replenishmentApplyList = replenishmentApplyDao.selectSelective(replenishmentApplyRequest);
            boolean allReceivedFlag = true;
            for (ReplenishmentApply replenishmentApply : replenishmentApplyList) {
                Long totalReceivedNum = 0L;
                List<ReplenishmentApplyDetail> replenishmentApplyDetailList = replenishmentApplyDetailDao.selectByReplenishmentId(replenishmentApply.getReplenishmentId());
                for (ReplenishmentApplyDetail replenishmentApplyDetail : replenishmentApplyDetailList) {
                    InspectionPickingInDetail inspectionPickingInDetail = inspectionPickingInDetailMap.get(replenishmentApplyDetail.getGoodsId());
                    Long remainingNum = replenishmentApplyDetail.getApplyNum() - replenishmentApplyDetail.getStockInNum();
                    Long distributeRemainingNum = replenishmentApplyDetail.getDistributedNum() - replenishmentApplyDetail.getStockInNum();
                    if (inspectionPickingInDetail == null) {
                        if (remainingNum > 0) {
                            totalReceivedNum += replenishmentApplyDetail.getStockInNum();
                            allReceivedFlag = false;
                        }
                        continue;
                    }
                    if (distributeRemainingNum <= inspectionPickingInDetail.getReceiptNum()) {
                        inspectionPickingInDetail.setReceiptNum(inspectionPickingInDetail.getReceiptNum() - distributeRemainingNum);
                        replenishmentApplyDetail.setStockInNum(replenishmentApplyDetail.getDistributedNum());
                        if (remainingNum > distributeRemainingNum) {
                            allReceivedFlag = false;
                        }
                    } else {
                        replenishmentApplyDetail.setStockInNum(replenishmentApplyDetail.getStockInNum() + inspectionPickingInDetail.getReceiptNum());
                        allReceivedFlag = false;
                    }
                    totalReceivedNum += replenishmentApplyDetail.getStockInNum();
                    int row = replenishmentApplyDetailDao.updateByReplenishmentId(replenishmentApplyDetail);
                    if (row == 0) {
                        return HttpResponse.failure(ResultCode.INSPECTION_UPDATE_ERROR);
                    }
                }
                ReplenishmentApply newApply = new ReplenishmentApply();
                if (allReceivedFlag) {
                    newApply.setReceiptStatus(Consts.REPLENISHMENT_APPLY_RECEIPT_ALL);
                    newApply.setReplenishmentStatus(Consts.REPLENISHEMTN_APPLY_STATUS_DONE);
                } else if (totalReceivedNum > 0) {
                    newApply.setReceiptStatus(Consts.REPLENISHMENT_APPLY_RECEIPT_PORTION);
                } else {
                    newApply.setReceiptStatus(Consts.REPLENISHMENT_APPLY_RECEIPT_NONE);
                }
                newApply.setReplenishmentId(replenishmentApply.getReplenishmentId());
                int row = replenishmentApplyDao.updateByPrimaryKeySelective(newApply);
                if (row == 0) {
                    return HttpResponse.failure(ResultCode.INSPECTION_UPDATE_ERROR);
                }
            }
            //详单修改状态和审核时间
            int orderDetailRow = inspectionPickingInDetailDao.auditOrder(inspectionPickingIn.getInspectionId());
            if (orderDetailRow == 0) {
                return HttpResponse.failure(ResultCode.INSPECTION_UPDATE_ERROR);
            }
            //更改库存
            stockQuantService.updateQuantByInspectionPickingIn(old);
        } catch (Exception e) {
            LOGGER.error("审核入库单失败!", e);
            return HttpResponse.failure(ResultCode.INSPECTION_DETAIL_UPDATE_ERROR, e);
        }
        return HttpResponse.success();
    }

    @Override
    public HttpResponse commit(String inspectionId) {
        try {
            InspectionPickingIn old = inspectionPickingInDao.selectByPrimaryKey(inspectionId);
            if (old == null) {
                throw new InspectionpickinginException("未查询到该订单。");
            }
            InspectionPickingIn inspection = new InspectionPickingIn();
            inspection.setInspectionId(inspectionId);
            inspection.setInspectionStatus(Consts.INSPECTIONPICKING_ORDER_STATUS_COMMITED);
            int modifyRow = inspectionPickingInDao.updateByPrimaryKeySelective(inspection);
            if (modifyRow <= 0) {
                return HttpResponse.failure(ResultCode.INSPECTIONPICKING_ORDER_COMMIT_ERROR);
            }
            ProcessInstanceCreateRequest processInstanceCreateRequest = new ProcessInstanceCreateRequest();
            processInstanceCreateRequest.setProcessType(Consts.PROCESS_TYPE_INSPECTION_PICKING_IN);
            processInstanceCreateRequest.setBrandId(old.getBrandId());
            processInstanceCreateRequest.setBusinessId(inspectionId);
            ActivitiUtil.getInstance();
            HttpResponse response = processInstanceService.create(processInstanceCreateRequest, old.getOperatorId());
            if (!StringUtils.equals(response.getCode(), "0")) {
                throw new ProcessInstanceException("提审失败!");
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("提审失败", e);
            return HttpResponse.failure(ResultCode.INSPECTIONPICKING_ORDER_COMMIT_ERROR);
        }
    }

    @Override
    public HttpResponse queryByDistributionId(String distributionOrderId) {
        List<InspectionPickingInDetail> detailList;
        try {
            detailList = inspectionPickingInDetailDao.queryByDistributionId(distributionOrderId);
            BaseResponse response = new BaseResponse(0, detailList);
            return HttpResponse.success(response);
        } catch (Exception e) {
            LOGGER.error("查询配货单已占用商品数失败", e);
            return HttpResponse.failure(ResultCode.INSPECTION_DETAIL_QUERY_ERROR);
        }
    }

    private int insertInspctionGoods(InspectionPickingIn inspectionPickingIn, InspectionPickingInDetail replenishmentApplyDetail) {
        String replenishmentApplyDetailId = IdUtil.uuid();
        replenishmentApplyDetail.setInspectionDetailId(replenishmentApplyDetailId);
        replenishmentApplyDetail.setInspectionId(inspectionPickingIn.getInspectionId());
        replenishmentApplyDetail.setInspectionStatus(inspectionPickingIn.getInspectionStatus());
        replenishmentApplyDetail.setBrandId(inspectionPickingIn.getBrandId());
        replenishmentApplyDetail.setStoreId(inspectionPickingIn.getStoreId());

        return inspectionPickingInDetailDao.insertSelective(replenishmentApplyDetail);
    }

    @Override
    public List<List<Object>> exportInspection(InspectionPickingInRequest inspection) {
        List<List<Object>> resultList = new ArrayList<>();
        List<InspectionPickingIn> list = inspectionPickingInDao.selectOrders(inspection);
        Integer step = 0;
        for(InspectionPickingIn inspec : list){
            step++;
            List<Object> beanList = new ArrayList<>();
            beanList.add(step);
            beanList.add(inspec.getInspectionDocumentId());
            beanList.add(inspec.getDistributionOrderDocumentId());
            beanList.add(inspec.getToStoreName());
            beanList.add(inspec.getWarehouseName());
            beanList.add(inspec.getOperatorName());
            beanList.add(inspec.getCreateTime());
            beanList.add(MapConsts.getOrderStatus().get(inspec.getInspectionStatus()));
            resultList.add(beanList);
        }
        return resultList;
    }

    @Override
    public List<List<Object>> exportInspectionId(String inspectionId) {
        List<List<Object>> resultList = new ArrayList<>();
        List<InspectionPickingInDetail> list = inspectionPickingInDetailDao.selectByInspectionId(inspectionId);
        Integer step = 0;
        for(InspectionPickingInDetail inspecDetail : list){
            step++;
            List<Object> beanList = new ArrayList<>();
            beanList.add(step);
            beanList.add(inspecDetail.getGoodsCode());
            beanList.add(inspecDetail.getGoodsBarCode());
            beanList.add(inspecDetail.getGoodsName());
            beanList.add(inspecDetail.getFeatures());
            beanList.add(inspecDetail.getUnitName());
            beanList.add(inspecDetail.getReceiptNum());
            beanList.add(inspecDetail.getRemark());
            resultList.add(beanList);
        }
        return resultList;
    }
}

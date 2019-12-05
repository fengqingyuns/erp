package com.hanyun.scm.api.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.IdGenerateType;
import com.hanyun.scm.api.consts.MapConsts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.*;
import com.hanyun.scm.api.domain.*;
import com.hanyun.scm.api.domain.request.InspectionPickingIn.InspectionPickingInRequest;
import com.hanyun.scm.api.domain.request.distribution.DistributionOrderCreateRequest;
import com.hanyun.scm.api.domain.request.distribution.DistributionOrderModifyRequest;
import com.hanyun.scm.api.domain.request.distribution.DistributionOrderQueryRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceCreateRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceModifyRequest;
import com.hanyun.scm.api.domain.request.stock.StockQuantQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.domain.result.StockQuantResult;
import com.hanyun.scm.api.exception.DistributionOrderException;
import com.hanyun.scm.api.exception.ProcessInstanceException;
import com.hanyun.scm.api.exception.StockQuantException;
import com.hanyun.scm.api.service.*;
import com.hanyun.scm.api.utils.ActivitiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <pre>
 *                             _ooOoo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            O\  =  /O
 *                         ____/`---'\____
 *                       .'  \\|     |//  `.
 *                      /  \\|||  :  |||//  \
 *                     /  _||||| -:- |||||-  \
 *                     |   | \\\  -  /// |   |
 *                     | \_|  ''\---/''  |   |
 *                     \  .-\__  `-`  ___/-. /
 *                   ___`. .'  /--.--\  `. . __
 *                ."" '<  `.___\_<|>_/___.'  >'"".
 *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *          ======`-.____`-.___\_____/___.-`____.-'======
 *                             `=---='
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *
 *                     佛祖保佑        永无BUG
 *
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * </pre>
 * 配送单serviceImpl
 * Date: 17/3/7
 * Time: 下午15:17
 *
 * @author 1007661792@qq.com
 */
@Service
public class DistributionOrderServiceImpl implements DistributionOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributionOrderServiceImpl.class);

    @Resource
    private StockQuantDao stockQuantDao;

    @Resource
    private DistributionOrderDao distributionOrderDao;

    @Resource
    private DistributionOrderDetailDao distributionOrderDetailDao;

    @Resource
    private IdGenerateSeqService idGenerateSeqService;

    @Resource
    private ReplenishmentApplyDao replenishmentApplyDao;

    @Resource
    private ReplenishmentApplyDetailDao replenishmentApplyDetailDao;

    @Resource
    private ProcessInstanceService processInstanceService;

    @Resource
    private ProcessDefinitionService processDefinitionService;

    @Resource
    private StockQuantChangeHistoryDao stockQuantChangeHistoryDao;

    @Resource
    private StockQuantService stockQuantService;

    @Resource
    private GoodsOdooService goodsOdooService;

    @Resource
    private InspectionPickingInDao inspectionPickingInDao;

    @Resource
    private InspectionPickingInDetailDao inspectionPickingInDetailDao;

    @Override
    public HttpResponse create(DistributionOrderCreateRequest record) {
        try {
            List<DistributionOrderDetail> detailList = romveEmpty(record.getDistributionOrderDetailList());
            record.setDistributionQuantity(record.getTotalCenterNum());
            record.setDistributionAmount(record.getTotalAmount());
            String distributionOrderId = record.getDistributionOrderId();
            String distributionOrderDocumentId = record.getDistributionOrderDocumentId();
            /** 源单数组 */
            String[] replenishmentApplyIds = record.getSourceReplenishmentId().split(",");
            if (detailList == null || detailList.isEmpty()) {
                throw new DistributionOrderException("添加配送单--配送单list为空");
            }
            if (StringUtils.isEmpty(distributionOrderId)) {
                //新增配送单
                distributionOrderId = IdUtil.uuid();
                distributionOrderDocumentId = idGenerateSeqService.generateId(IdGenerateType.PSD);
                record.setDistributionOrderId(distributionOrderId);
                record.setDistributionOrderDocumentId(distributionOrderDocumentId);
                int insRow = distributionOrderDao.insert(record);
                if (insRow <= 0) {
                    LOGGER.error("创建配送单据失败");
                    return HttpResponse.failure(ResultCode.DISTRIBUTIONORDER_CREATE_ERROR);
                }
            } else {
                DistributionOrder old = distributionOrderDao.selectByDistributionOrderId(distributionOrderId);
                if (old == null) {
                    LOGGER.error("记录不存在!");
                    return HttpResponse.failure(ResultCode.DISTRIBUTIONORDER_DATA_NOT_FOUND);
                }
                if (old.getDistributionOrderStatus() != null && old.getDistributionOrderStatus() > Consts.ORDER_STATUS_ZERO) {
                    LOGGER.error("配送单--已提审,不允许修改!");
                    return HttpResponse.failure(ResultCode.DISTRIBUTION_ORDER_COMMITED);
                }
                //更新配送单据信息
                int updRow = updateDistributionOrder(record, distributionOrderId);
                if (updRow <= 0) {
                    LOGGER.error("更新配送单单据失败");
                    return HttpResponse.failure(ResultCode.DISTRIBUTIONORDER_MODIFY_ERROR);
                }
                DistributionOrderDetail query = new DistributionOrderDetail();
                query.setDistributionOrderId(distributionOrderId);
                List<DistributionOrderDetail> oldDistributionOrderDetailList = distributionOrderDetailDao.select(query);
                /** 更新补货申请单的状态 */
                int updateApplyRow = updateReplenishmentStatus(replenishmentApplyIds, oldDistributionOrderDetailList);
                if (updateApplyRow <= 0) {
                    throw new DistributionOrderException("更新补货申请单状态或更新详情数据失败。");
                }
                //删除配送单所有详情
                int row = distributionOrderDetailDao.deleteByDistributionOrderId(distributionOrderId);
                if (row == 0) {
                    LOGGER.error("更新配送单单据失败");
                    return HttpResponse.failure(ResultCode.DISTRIBUTIONORDER_MODIFY_ERROR);
                }
            }

            //map 查找在申请单上是否有商品
            Map<String, ReplenishmentApplyDetail> map = getMap(replenishmentApplyIds);
            //新增配送单详情
            for (DistributionOrderDetail detail : detailList) {
                String goodsIdForDistri = detail.getGoodsId();          //商品id
                Long distributionNum = detail.getDistributionQuantity();//配送单--配送数量
                int insdetalROw = insertDistributionOrderDetail(detail, record);
                if (insdetalROw <= 0) {
                    LOGGER.error("创建配送单详情失败");
                    return HttpResponse.failure(ResultCode.DISTRIBUTIONORDER_CREATE_ERROR);
                }

                /** 保存申请单操作 start */

                for (int i = replenishmentApplyIds.length - 1; i >= 0; i--) {
                    /**  对象得在循环申请单id 里面 */
                    String appyId = replenishmentApplyIds[i];
                    ReplenishmentApplyDetail object = map.get(appyId + "," + goodsIdForDistri);
                    if (object == null) {
                        continue;
                    }
                    //申请单--未配送数量
                    Long appyNum = object.getApplyNum() != null ? object.getApplyNum() : 0L;
                    Long distributeNum = object.getDistributedNum() != null ? object.getDistributeNum() : 0L;
                    long waitingDistributionNum = appyNum - distributeNum;

                    if (distributionNum >= waitingDistributionNum) {            //配送数量大于等于申请单的未配送数量(申请数-已配送数)
                        object.setDistributeNum(appyNum);
                        distributionNum = distributionNum - object.getApplyNum();
                    } else if (distributionNum < waitingDistributionNum) {      //小于
                        object.setDistributeNum(distributionNum + object.getDistributeNum());
                        distributionNum = 0L;
                    }
                    //更新详情数据
                    int updateRow = replenishmentApplyDetailDao.updateByReplenishmentId(object);
                    if (updateRow <= 0) {
                        LOGGER.error("更新申请单详情失败。");
                        return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_DETAIL_UPDATE_ERROR);
                    }
                }
                /** 保存申请单操作 end */
            }

            //更新申请单数据
            for (int i = replenishmentApplyIds.length - 1; i >= 0; i--) {
                String applyId = replenishmentApplyIds[i];
                List<ReplenishmentApplyDetail> applyDetailList = replenishmentApplyDetailDao.selectByReplenishmentId(replenishmentApplyIds[i]);
                boolean updateFlag = false;
                for (ReplenishmentApplyDetail replDetail : applyDetailList) {
                    String goodsIdForApply = replDetail.getGoodsId();
                    ReplenishmentApplyDetail object = map.get(applyId + "," + goodsIdForApply);
                    if (object == null) {
                        continue;
                    }
                    if (object.getDistributeNum() >= object.getApplyNum()) {
                        updateFlag = true;
                        break;
                    }
                }
                ReplenishmentApply apply = replenishmentApplyDao.selectById(applyId);
                if(apply == null || apply.getDistributionType() == null){
                    LOGGER.error("查询申请单失败");
                    return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_QUERY_ERROR);
                }
                //获取系统配置是一次配送还是多次配送(1,单次 2,多次)
                int distributionType = apply.getDistributionType();
                //更新申请单单据的配送状态
                if(updateFlag){
                    apply.setDistributeStatus(Consts.REPLENISHMENT_APPLY_DISTRIBUTE_STATUS_IMPORTED);
                } else {
                    apply.setDistributeStatus(distributionType == Consts.SINGLE_DISTRIBUTION_TYPE ? Consts.REPLENISHMENT_APPLY_DISTRIBUTE_STATUS_IMPORTED :
                            Consts.REPLENISHMENT_APPLY_DISTRIBUTE_STATUS_NOT_IMPORTED);
                }
                int updaOrderRow = replenishmentApplyDao.updateByPrimaryKeySelective(apply);
                if (updaOrderRow <= 0) {
                    LOGGER.error("更新申请单配送状态失败-->未/已配送。");
                    return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_UPDATE_ERROR);
                }
            }
            boolean existStatus = processDefinitionService.exist(record.getBrandId(), Consts.PROCESS_TYPE_DISTRIBUTION_ORDER);
            if (existStatus) {
                return HttpResponse.success(new String[]{distributionOrderId, distributionOrderDocumentId, "exist"});
            }
            return HttpResponse.success(new String[]{distributionOrderId, distributionOrderDocumentId});
        } catch (Exception e) {
            LOGGER.error("创建配送单信息失败", e);
            return HttpResponse.failure(ResultCode.DISTRIBUTIONORDER_CREATE_ERROR);
        }
    }

    /**
     * GET申请单详情Map
     * 更新配送单单据信息
     *
     * @param replenishmentApplyIds 源单数组
     * @return map
     */
    @Override
    public Map<String, ReplenishmentApplyDetail> getMap(String[] replenishmentApplyIds) {
        Map<String, ReplenishmentApplyDetail> map = new HashMap<>();
        for (String applyId : replenishmentApplyIds) {
            List<ReplenishmentApplyDetail> applyDetailsList = replenishmentApplyDetailDao.selectByReplenishmentId(applyId);
            for (ReplenishmentApplyDetail applyDetail : applyDetailsList) {
                String goodsIdForApply = applyDetail.getGoodsId();
                map.put(applyId + "," + goodsIdForApply, applyDetail);
            }
        }
        return map;
    }

    /**
     * 更新补货申请单配送状态和配送数量
     *
     * @param replenishmentApplyIds 源单数组
     * @return no
     */
    private Integer updateReplenishmentStatus(String[] replenishmentApplyIds, List<DistributionOrderDetail> detailList) {
        Map<String, ReplenishmentApplyDetail> map = getMap(replenishmentApplyIds);

        //更新数据
        for (DistributionOrderDetail detail : detailList) {
            String goodsId = detail.getGoodsId();
            long quanty = detail.getDistributionQuantity();         //配送单--->配送数
            for (int i = replenishmentApplyIds.length - 1; i >= 0; i--) {
                String applyId = replenishmentApplyIds[i];
                ReplenishmentApplyDetail object = map.get(applyId + "," + goodsId);
                if (object == null) {
                    continue;
                }
                long distributeNum = object.getDistributeNum();     //申请单--->配送数
                if (quanty >= distributeNum) {
                    object.setDistributeNum(0L);
                    quanty = 0;
                } else {
                    object.setDistributeNum(distributeNum - quanty);
                    quanty = distributeNum - quanty;
                }
                int updatedetailRow = replenishmentApplyDetailDao.updateByReplenishmentId(object);
                if (updatedetailRow <= 0) {
                    return updatedetailRow;
                }
            }
        }


        //更新状态
        for (int i = 0; i < replenishmentApplyIds.length; i++) {
            boolean flag = false;
            String applyId = replenishmentApplyIds[i];

            ReplenishmentApply apply = new ReplenishmentApply();
            apply.setReplenishmentId(replenishmentApplyIds[i]);
            apply.setDistributeStatus(Consts.REPLENISHMENT_APPLY_DISTRIBUTE_STATUS_NOT_IMPORTED);

            for (DistributionOrderDetail detail : detailList) {
                String goodsId = detail.getGoodsId();
                ReplenishmentApplyDetail object = map.get(applyId + "," + goodsId);
                if (object == null) {
                    continue;
                }
                flag = true;
                break;
            }
            if (flag) {
                int updateRow = replenishmentApplyDao.updateByPrimaryKeySelective(apply);
                if (updateRow <= 0) {
                    return updateRow;
                }
            }
        }
        return 1;
    }


    /**
     * 更新配送单单据信息
     *
     * @param record              传入的新增对象
     * @param distributionOrderId 配送单业务主键Id
     * @return update No
     */
    private int updateDistributionOrder(DistributionOrderCreateRequest record, String distributionOrderId) {
        DistributionOrderModifyRequest orderModify = new DistributionOrderModifyRequest();
        orderModify.setBrandId(record.getBrandId());
        if (!StringUtils.isEmpty(record.getStoreId())) {
            orderModify.setStoreId(record.getStoreId());
        }
        orderModify.setDistributionOrderId(distributionOrderId);
        orderModify.setDistributionQuantity(record.getTotalCenterNum());
        orderModify.setDistributionAmount(record.getTotalAmount());
        orderModify.setRemark(record.getRemark());
        orderModify.setSourceReplenishmentId(record.getSourceReplenishmentId());
        orderModify.setSourceReplenishmentDocumentId(record.getSourceReplenishmentDocumentId());
        orderModify.setToStoreId(record.getToStoreId());
        orderModify.setToStoreName(record.getToStoreName());
        orderModify.setWarehouseId(record.getWarehouseId());
        orderModify.setWarehouseName(record.getWarehouseName());
        return distributionOrderDao.updateByDistributionOrderId(orderModify);
    }

    /**
     * 插入配送单据详细
     *
     * @param orderDetail 页面传过来的单个对象
     * @param record      单据对象
     * @return insert No
     */
    private int insertDistributionOrderDetail(DistributionOrderDetail orderDetail, DistributionOrderCreateRequest record) {
        String distributionOrderDetailId = IdUtil.uuid();
        orderDetail.setDistributionOrderDetailId(distributionOrderDetailId);
        orderDetail.setDistributionOrderId(record.getDistributionOrderId());
        orderDetail.setBrandId(record.getBrandId());
        orderDetail.setStoreId(record.getStoreId());
        return distributionOrderDetailDao.insert(orderDetail);
    }

    @Override
    public HttpResponse select(DistributionOrderQueryRequest record) {
        try {
            if (!StringUtils.isEmpty(record.getStoreIds())) {
                List<String> storeIdList = JsonUtil.fromJson(record.getStoreIds(), new TypeReference<List<String>>() {
                });
                if (storeIdList != null && storeIdList.size() > Consts.LIST_IS_EMPTY) {
                    record.setStoreIdList(storeIdList);
                }
            }
            if (!StringUtils.isEmpty(record.getDistributionIds())) {
                List<String> distributionIdList = JsonUtil.fromJson(record.getDistributionIds(), new TypeReference<List<String>>() {
                });
                if (distributionIdList != null && distributionIdList.size() > Consts.LIST_IS_EMPTY) {
                    record.setDistributionIdList(distributionIdList);
                }
            }
            String queryBeginTime = record.getQueryBeginTime();
            String queryEndTime = record.getQueryEndTime();
            if (!StringUtils.isEmpty(queryBeginTime)) {
                record.setQueryBeginTime(queryBeginTime.replace('T', ' '));
            }
            if (!StringUtils.isEmpty(queryEndTime)) {
                record.setQueryEndTime(queryEndTime.replace('T', ' '));
            }
            int count = distributionOrderDao.countAll(record);
            record.setCount(count);
            List<DistributionOrder> list = distributionOrderDao.select(record);
            if (!StringUtils.isEmpty(record.getUserId())) {
                for (DistributionOrder distribution : list) {
                    distribution.setAuditStatus(processInstanceService.queryAuditor(distribution.getDistributionOrderId(), record.getUserId()));
                    List<String> auditors = processInstanceService.queryAuditors(distribution.getDistributionOrderId());
                    auditors.add(distribution.getMakeorderManId());
                    distribution.setHistoryStatus(auditors.size() > 1 && auditors.contains(record.getUserId()));
                }
            }
            BaseResponse response = new BaseResponse(count, list);
            return HttpResponse.success(response);
        } catch (Exception e) {
            LOGGER.error("查询配送单信息失败", e);
            return HttpResponse.failure(ResultCode.DISTRIBUTIONORDER_QUERY_ERROR);
        }
    }

    @Override
    public HttpResponse modify(DistributionOrderModifyRequest record) {
        try {
            //提审
            String orderId = record.getDistributionOrderId();
            DistributionOrder distributionOrder = distributionOrderDao.selectByDistributionOrderId(orderId);
            if (distributionOrder == null) {
                LOGGER.error("配送单号:[" + orderId + "]不存在。");
                return HttpResponse.failure(ResultCode.DISTRIBUTIONORDER_DATA_NOT_FOUND);
            }

            String auditorId = record.getAuditorId();
            String auditorName = record.getAuditorName();
            if (record.getDistributionOrderStatus() != null && record.getDistributionOrderStatus() == Consts.DISTRIBUTION_ORDER_STATUS_CONFIRMED) {
                distributionOrder.setDistributionOrderStatus(Consts.DISTRIBUTION_ORDER_STATUS_CONFIRMED);
            } else {
                if (!record.getAuditStatus()) {
                    DistributionOrderModifyRequest modify = new DistributionOrderModifyRequest();
                    modify.setDistributionOrderId(orderId);
                    modify.setAuditorId(auditorId);
                    modify.setAuditorName(auditorName);
                    modify.setDistributionOrderStatus(Consts.DISTRIBUTION_ORDER_STATUS_SAVE);
                    int row = distributionOrderDao.updateByDistributionOrderId(modify);
                    if (row == 0) {
                        HttpResponse.failure(ResultCode.DISTRIBUTION_ORDER_CONFIRM_ERROR);
                    }
                    processInstanceService.delete(orderId);
                    return HttpResponse.success();
                }
                ProcessInstanceModifyRequest processInstanceModifyRequest = new ProcessInstanceModifyRequest();
                processInstanceModifyRequest.setBusinessId(orderId);
                processInstanceModifyRequest.setUserId(record.getUserId());
                processInstanceModifyRequest.setRemark("通过, 执行");
                HttpResponse response = processInstanceService.audit(processInstanceModifyRequest);
                if (!StringUtils.equals(response.getCode(), "0") || response.getData() == null) {
                    return HttpResponse.failure(ResultCode.DISTRIBUTION_ORDER_CONFIRM_ERROR);
                }
                Integer instanceStatus = Integer.parseInt(response.getData().toString());
                if (instanceStatus != Consts.PROCESS_INSTANCE_STATUS_CONFIRMED) {
                    distributionOrder.setDistributionOrderStatus(Consts.DISTRIBUTION_ORDER_STATUS_CONIFRMING);
                } else {
                    distributionOrder.setDistributionOrderStatus(Consts.DISTRIBUTION_ORDER_STATUS_CONFIRMED);
                }
            }
            //更新对象
            DistributionOrderModifyRequest modifyRequest = new DistributionOrderModifyRequest();
            modifyRequest.setDistributionOrderId(orderId);
            modifyRequest.setAuditorId(auditorId);
            modifyRequest.setAuditorName(auditorName);
            modifyRequest.setAuditTime(new Date());
            modifyRequest.setDistributionOrderStatus(distributionOrder.getDistributionOrderStatus());

            int updateRow = distributionOrderDao.updateByDistributionOrderId(modifyRequest);
            if (updateRow <= 0) {
                LOGGER.error("更新配单据信息失败!");
                return HttpResponse.failure(ResultCode.DISTRIBUTION_ORDER_CONFIRM_ERROR);
            }

            if (modifyRequest.getDistributionOrderStatus() != Consts.ORDER_STATUS_CONFIRMED) {
                return HttpResponse.success();
            }

            /** 更新申请单的已配送数量(更新Map) */
            String[] replenishmentApplyIds = distributionOrder.getSourceReplenishmentId().split(",");
            Map<String, ReplenishmentApplyDetail> map = getMap(replenishmentApplyIds);
            List<DistributionOrderDetail> orderDetailList = getDetailList(distributionOrder, orderId);
            for (DistributionOrderDetail detail : orderDetailList) {
                String goodsIdForDetail = detail.getGoodsId();          //商品id
                Long distributionNum = detail.getDistributionQuantity();//配送数量
                ReplenishmentApplyDetail object;

                for (int i = replenishmentApplyIds.length - 1; i >= 0; i--) {
                    String applyId = replenishmentApplyIds[i];
                    object = map.get(applyId + "," + goodsIdForDetail);
                    if (object == null) {
                        continue;
                    }
                    if (distributionNum < object.getApplyNum()) {
                        object.setDistributedNum(object.getDistributedNum() + distributionNum);
                    } else if (distributionNum >= object.getApplyNum()) {
                        object.setDistributedNum(object.getApplyNum());
                        distributionNum = distributionNum - object.getApplyNum();
                    }
                }
            }
            for (int i = replenishmentApplyIds.length - 1; i >= 0; i--) {
                String applyId = replenishmentApplyIds[i];
                for (DistributionOrderDetail detail : orderDetailList) {
                    String goodsIdForDis = detail.getGoodsId();
                    ReplenishmentApplyDetail object = map.get(applyId + "," + goodsIdForDis);
                    if (object == null) {
                        continue;
                    }
                    int updateApplyDetailRow = replenishmentApplyDetailDao.updateByReplenishmentId(object);
                    if (updateApplyDetailRow <= 0) {
                        LOGGER.error("更新申请单详情失败。");
                        return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_DETAIL_UPDATE_ERROR);
                    }
                }
            }
            /** 更新库存 */
            int updateStockRow = updateStockNum(orderDetailList, distributionOrder);
            if (updateStockRow <= 0) {
                throw new StockQuantException("更新库存信息失败");
            }
            return HttpResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("更新配送单信息失败", e);
            return HttpResponse.failure(ResultCode.DISTRIBUTIONORDER_MODIFY_ERROR);
        }
    }

    @Override
    public HttpResponse commit(String distributionOrderId) {
        try {
            DistributionOrder old = distributionOrderDao.selectByDistributionOrderId(distributionOrderId);
            if (old == null) {
                throw new DistributionOrderException("未查询到该订单");
            }
            DistributionOrderModifyRequest modifyRequest = new DistributionOrderModifyRequest();
            modifyRequest.setDistributionOrderId(distributionOrderId);
            modifyRequest.setDistributionOrderStatus(Consts.DISTRIBUTION_ORDER_STATUS_COMMITED);
            int modifyRow = distributionOrderDao.updateByDistributionOrderId(modifyRequest);
            if (modifyRow <= 0) {
                LOGGER.error("更新配送单状态提审失败。");
                return HttpResponse.failure(ResultCode.DISTRIBUTIONORDER_MODIFY_ERROR);
            }
            ProcessInstanceCreateRequest processInstanceCreateRequest = new ProcessInstanceCreateRequest();
            processInstanceCreateRequest.setProcessType(Consts.PROCESS_TYPE_DISTRIBUTION_ORDER);
            processInstanceCreateRequest.setBrandId(old.getBrandId());
            processInstanceCreateRequest.setBusinessId(distributionOrderId);
            processInstanceCreateRequest.setPrice(old.getDistributionAmount());
            ActivitiUtil.getInstance();
            HttpResponse response = processInstanceService.create(processInstanceCreateRequest, old.getMakeorderManId());
            if (!StringUtils.equals(response.getCode(), "0")) {
                throw new ProcessInstanceException("提审失败!");
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("更新配送单提审状态失败", e);
            return HttpResponse.failure(ResultCode.DISTRIBUTION_ORDER_COMMIT_ERROR);
        }
    }

    /**
     * 更新配送单的详情更新库存
     *
     * @param orderDetailList   配送详单list
     * @param distributionOrder 查询的对象
     * @return udpate No
     */
    private int updateStockNum(List<DistributionOrderDetail> orderDetailList, DistributionOrder distributionOrder) {
        String centerId = distributionOrder.getWarehouseId();
        // 更新对象
        StockQuant stockQuant = new StockQuant();
        stockQuant.setBrandId(distributionOrder.getBrandId());
        stockQuant.setWarehouseId(centerId);
        // 查询对象
        StockQuantQueryRequest queryStockQuant = new StockQuantQueryRequest();
        queryStockQuant.setBrandId(distributionOrder.getBrandId());
        queryStockQuant.setWarehouseId(centerId);

        for (DistributionOrderDetail distriDetail : orderDetailList) {
            if (StringUtils.isEmpty(distriDetail.getGoodsId())) {
                continue;
            }
            queryStockQuant.setGoodsId(distriDetail.getGoodsId());
            stockQuant.setGoodsId(distriDetail.getGoodsId());
            List<StockQuant> stockQuantList = stockQuantDao.selectWarehouseGoods(queryStockQuant);
            stockQuant.setStockNum(stockQuantList.get(0).getStockNum() - distriDetail.getDistributionQuantity());
            StockQuantQueryRequest query = new StockQuantQueryRequest();
            query.setBrandId(distributionOrder.getBrandId());
            query.setWarehouseId(centerId);
            query.setGoodsId(distriDetail.getGoodsId());
            try {
                StockQuant old = (StockQuant) stockQuantService.selectWithParam(query).getList().get(0);
                stockQuant.setStockQuantId(old.getStockQuantId());
                int updateStockRow = stockQuantService.updateEsByStockQuantId(stockQuant);
                try {
                    StockQuantChangeHistory stockQuantChangeHistory = new StockQuantChangeHistory(stockQuantList.get(0));
                    stockQuantChangeHistory.setStockChangeNum(distriDetail.getDistributionQuantity());
                    stockQuantChangeHistory.setStockChangeType(Consts.STOCK_IN_OUT_DISTRIBUTION);
                    stockQuantChangeHistoryDao.insertSelective(stockQuantChangeHistory);
                } catch (Exception e) {
                    LOGGER.error("更新出入库记录失败!", e);
                }
                if (updateStockRow <= 0) {
                    return updateStockRow;
                }
            } catch (Exception e) {
                LOGGER.error("更新配送单的详情更新库存失败！", e);
            }
        }
        return 1;
    }

    /**
     * 查询配送单据的详单并返回
     *
     * @param distributionOrder   查询的对象
     * @param distributionOrderId 配送单id
     * @return List
     */
    private List<DistributionOrderDetail> getDetailList(DistributionOrder distributionOrder, String distributionOrderId) {
        //查询详单对象
        DistributionOrderDetail detail = new DistributionOrderDetail();
        detail.setBrandId(distributionOrder.getBrandId());
        if (!StringUtils.isEmpty(distributionOrder.getStoreId())) {
            detail.setStoreId(distributionOrder.getStoreId());
        }
        detail.setDistributionOrderId(distributionOrderId);
        return distributionOrderDetailDao.select(detail);
    }

    @Override
    public HttpResponse detail(String distributionOrderId) {
        try {
            DistributionOrder distributionOrder = distributionOrderDao.selectByDistributionOrderId(distributionOrderId);
            if (distributionOrder == null) {
                LOGGER.error("配送单号:[" + distributionOrderId + "]不存在。");
                return HttpResponse.failure(ResultCode.DISTRIBUTIONORDER_DATA_NOT_FOUND);
            }
            List<DistributionOrderDetail> detailList = getDetailList(distributionOrder, distributionOrderId);
            Map<String, GoodsOdoo> map = goodsOdooService.getDistributionGoodsMap(distributionOrder.getBrandId(), detailList);
            detailList.forEach(d -> {
                String goodsId = d.getGoodsId();
                boolean bl = !map.isEmpty() && map.get(goodsId) != null && !StringUtils.isEmpty(map.get(goodsId).getGoodsPic());
                d.setGoodsPic(bl ? map.get(goodsId).getGoodsPic() : "");
                boolean brandBL = !StringUtils.isEmpty(d.getGoodsBrandName());
                d.setGoodsBrandName(brandBL ? d.getGoodsBrandName() : "");
            });
            distributionOrder.setDistributionOrderDetailList(detailList);
            return HttpResponse.success(distributionOrder);
        } catch (Exception e) {
            LOGGER.error("查询配送单信息失败", e);
            return HttpResponse.failure(ResultCode.DISTRIBUTIONORDER_QUERY_ERROR);
        }
    }

    @Override
    public HttpResponse delete(String distributionOrderId) {
        try {
            DistributionOrder old = distributionOrderDao.selectByDistributionOrderId(distributionOrderId);
            if (old == null) {
                LOGGER.error("配送单号:[" + distributionOrderId + "]不存在");
                return HttpResponse.failure(ResultCode.DISTRIBUTIONORDER_DATA_NOT_FOUND);
            }
            int deleteRow = distributionOrderDao.deleteByDistributionOrderId(distributionOrderId);
            if (deleteRow <= 0) {
                LOGGER.error("删除配送单失败。");
                return HttpResponse.failure(ResultCode.DISTRIBUTIONORDER_DELETE_ERROR);
            }
            /** 更新补货申请单的状态 */
            String[] replenishmentApplyIds = old.getSourceReplenishmentId().split(",");
            List<DistributionOrderDetail> detailList = getDetailList(old, distributionOrderId);
            int updateApplyRow = updateReplenishmentStatus(replenishmentApplyIds, detailList);
            if (updateApplyRow <= 0) {
                throw new DistributionOrderException("更新补货申请单状态或更新详情数据失败。");
            }
            /** 删除配送单的详情 */
            int deleteDetailRow = distributionOrderDetailDao.deleteByDistributionOrderId(distributionOrderId);
            if (deleteDetailRow <= 0) {
                throw new DistributionOrderException("删除详情失败。");
            }
            return HttpResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("删除配送单信息失败", e);
            return HttpResponse.failure(ResultCode.DISTRIBUTIONORDER_DELETE_ERROR);
        }
    }

    @Override
    public List<List<Object>> exportByDistribution(DistributionOrderQueryRequest record) throws DistributionOrderException {
        List<List<Object>> resultList = new ArrayList<List<Object>>();//返回的数据List
        List<DistributionOrder> list = distributionOrderDao.select(record);
        Integer step = 0;
        for (DistributionOrder distributionOrder : list) {
            List<Object> beanList = new ArrayList<Object>();

            ++step;
            beanList.add(step);
            beanList.add(distributionOrder.getDistributionOrderDocumentId());
            beanList.add(distributionOrder.getSourceReplenishmentDocumentId());
            beanList.add(distributionOrder.getToStoreName());
            beanList.add(distributionOrder.getWarehouseName());
            beanList.add(distributionOrder.getDistributionQuantity());
            if (distributionOrder.getDistributionAmount() == null || distributionOrder.getDistributionAmount() == 0) {
                beanList.add("0.00");
            } else {
                beanList.add(Double.parseDouble(distributionOrder.getDistributionAmount() + "") / 100);
            }
            beanList.add(distributionOrder.getMakeorderManName());
            beanList.add(distributionOrder.getCreateTime());
            beanList.add(MapConsts.getDistributionOrderStatus().get(distributionOrder.getDistributionOrderStatus()));
            beanList.add(MapConsts.getReceiptStatus().get(distributionOrder.getReceiptStatus()));
            resultList.add(beanList);
        }
        return resultList;
    }

    @Override
    public List<List<Object>> exportByDistributionById(String distributionOrderId) throws DistributionOrderException {
        List<List<Object>> resultList = new ArrayList<List<Object>>();//返回的数据List
        DistributionOrder distributionOrder = distributionOrderDao.selectByDistributionOrderId(distributionOrderId);
        if (distributionOrder == null) {
            throw new DistributionOrderException("配送单号:[" + distributionOrderId + "]不存在。");
        }
        //按门店查询库存对象
        StockQuantQueryRequest stockQuantStore = new StockQuantQueryRequest();
        stockQuantStore.setBrandId(distributionOrder.getBrandId());
        stockQuantStore.setStoreId(distributionOrder.getToStoreId());
        //按配货中心查询库存对象
        StockQuantQueryRequest stockQuantCenter = new StockQuantQueryRequest();
        stockQuantCenter.setBrandId(distributionOrder.getBrandId());
        if (!StringUtils.isEmpty(stockQuantCenter.getStoreId())) {
            stockQuantCenter.setStoreId(distributionOrder.getStoreId());
        }
        stockQuantCenter.setWarehouseId(distributionOrder.getWarehouseId());
        //门店库存list
        List<StockQuantResult> stockQuantStoreList = stockQuantDao.queryDetailGroupList(stockQuantStore);
        //配货中心库存list
        List<StockQuantResult> stockQuantCenterList = stockQuantDao.queryDetail(stockQuantCenter);

        Map<String, Long> storeMap = new HashMap<String, Long>();
        Map<String, Long> centerMap = new HashMap<String, Long>();
        for (StockQuantResult result : stockQuantStoreList) {
            storeMap.put(result.getGoodsId(), result.getTotalNum());
        }
        for (StockQuantResult result : stockQuantCenterList) {
            centerMap.put(result.getGoodsId(), result.getStockNum());
        }
        //查询详情对象
        DistributionOrderDetail detail = new DistributionOrderDetail();
        detail.setBrandId(distributionOrder.getBrandId());
        if (!StringUtils.isEmpty(distributionOrder.getStoreId())) {
            detail.setStoreId(distributionOrder.getStoreId());
        }
        detail.setDistributionOrderId(distributionOrderId);
        //详情list
        List<DistributionOrderDetail> list = distributionOrderDetailDao.select(detail);
        Integer step = 0;
        for (DistributionOrderDetail distributionOrderDetail : list) {
            List<Object> beanList = new ArrayList<Object>();
            ++step;
            beanList.add(step);
            beanList.add(distributionOrderDetail.getDistributionQuantity());
            beanList.add(distributionOrderDetail.getGoodsCode());
            beanList.add(distributionOrderDetail.getGoodsBarCode());
            beanList.add(distributionOrderDetail.getGoodsName());
            beanList.add(distributionOrderDetail.getFeatures());
            beanList.add(distributionOrderDetail.getUnitName());
            Double price = Double.parseDouble(distributionOrderDetail.getUnitPrice() + "") / 100;
            beanList.add(price);
            beanList.add(distributionOrderDetail.getDistributionQuantity() * price);
            beanList.add(storeMap.get(distributionOrderDetail.getGoodsId()) != null ? storeMap.get(distributionOrderDetail.getGoodsId()) : 0);
            beanList.add(centerMap.get(distributionOrderDetail.getGoodsId()) != null ? centerMap.get(distributionOrderDetail.getGoodsId()) : 0);
            resultList.add(beanList);
        }
        return resultList;
    }

    /**
     * 去除goodsId 为空的数据和计算
     *
     * @param orderDetailList
     * @return
     */
    private List<DistributionOrderDetail> romveEmpty(List<DistributionOrderDetail> orderDetailList) {
        for (int i = orderDetailList.size() - 1; i >= 0; i--) {
            if (StringUtils.isEmpty(orderDetailList.get(i).getGoodsId())) {
                orderDetailList.remove(i);
            }
        }
        return orderDetailList;
    }


    @Override
    public HttpResponse initStatusAndNum() {
        try {
            List<String> inspectionIds = new ArrayList<>();
            Map<String, List<String>> map = new HashMap<>();
            InspectionPickingInRequest in = new InspectionPickingInRequest();
            in.setInspectionStatus(Consts.INSPECTIONPICKING_ORDER_STATUS_CONFIRMED);
            List<InspectionPickingIn> inspectionPickingInList = inspectionPickingInDao.selectOrders(in);
            inspectionPickingInList.forEach(i -> {
                String inspectionId = i.getInspectionId();
                String distributionId = i.getDistributionOrderId();
                inspectionIds.add(inspectionId);
                List<String> ids = map.get(distributionId);
                if(ids == null){
                    ids = new ArrayList<>();
                }
                ids.add(inspectionId);
                map.put(distributionId, ids);
            });
            //查询所有已审核的入库商品
            InspectionPickingInDetail record = new InspectionPickingInDetail();
            record.setInspectionStockInIds(inspectionIds);
            List<InspectionPickingInDetail> inspectionGoods = inspectionPickingInDetailDao.selectSelective(record);

            //组装两个map 根据配送单和商品的数量/总数量
            Map<String, Long> distributionMap = new HashMap<>();        //单据对应商品总数量
            Map<String, Long> distributionGoodsMap = new HashMap<>();   //单据商品对应商品数量
            inspectionGoods.forEach(i -> {
                String goodsId = i.getGoodsId();
                String distributionId = i.getDistributionOrderId();
                String inspectionId = i.getInspectionId();
                List<String> ids = map.get(distributionId);
                if(ids == null){
                    return;
                }
                    Long totalNum = distributionMap.get(distributionId);
                    if(ids.contains(inspectionId)){
                        Long goodsNum = distributionGoodsMap.get(distributionId+","+goodsId);
                        Long receiptNum = i.getReceiptNum() != null ? i.getReceiptNum() : 0;
                        if(goodsNum == null){
                            goodsNum = 0L;
                        }
                        if(totalNum == null){
                            totalNum = 0L;
                        }
                        goodsNum += receiptNum;
                        totalNum += receiptNum;
                        distributionGoodsMap.put(distributionId+","+goodsId, goodsNum);
                    }
                    distributionMap.put(distributionId, totalNum);
                });
            //更新配送单
            distributionMap.forEach((k,v) -> {
                DistributionOrder distributionOrder = distributionOrderDao.selectByDistributionOrderId(k);
                if(distributionOrder == null){
                    return;
                }
                DistributionOrderModifyRequest request = new DistributionOrderModifyRequest();
                request.setDistributionOrderId(k);
                request.setReceiptedNum(v);
                Long distributionNum = distributionOrder.getDistributionQuantity();        //配送数量
                if(v < distributionNum){
                    request.setReceiptedStatus(Consts.RECEIPTED_SECTION_STATUS);
                } else {
                    request.setReceiptedStatus(Consts.RECEIPTED_ALL_STATUS);
                    request.setDistributionOrderStatus(Consts.DISTRIBUTION_ORDER_STATUS_DONE);
                }
                distributionOrderDao.updateByDistributionOrderId(request);
            });
            //更新配送单商品
            distributionGoodsMap.forEach((k,v) -> {
                //String key = "a50d434d4b0a4bdfaa44c1e21ae539ce,fcde4da7-d810-4b28-9995-7c6a643d814d";
                DistributionOrderDetail detail = new DistributionOrderDetail();
                String[] key = key = k.split(",");
                detail.setDistributionOrderId(key[0]);
                detail.setGoodsId(key[1]);
                List<DistributionOrderDetail> list = distributionOrderDetailDao.select(detail);
                if(list.get(0) == null){
                    return;
                }
                DistributionOrderDetail update = list.get(0);
                update.setReceiptedNum(v);
                distributionOrderDetailDao.updateByDistributionOrderDetailId(update);
            });
            return HttpResponse.success();
        } catch (Exception e){
            LOGGER.error("初始化配送数据失败", e);
            return HttpResponse.failure(ResultCode.INIT_DISTIRBUTION_HISTORY_ERROR);
        }
    }
}

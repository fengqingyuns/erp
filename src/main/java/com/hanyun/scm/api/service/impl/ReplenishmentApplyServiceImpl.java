package com.hanyun.scm.api.service.impl;


import com.alibaba.druid.util.StringUtils;
import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.scm.api.common.UtilCommon;
import com.hanyun.ground.util.date.DateCalcUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.IdGenerateType;
import com.hanyun.scm.api.consts.MapConsts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.*;
import com.hanyun.scm.api.domain.*;
import com.hanyun.scm.api.domain.dto.ReplenishmentDTO;
import com.hanyun.scm.api.domain.request.InspectionPickingIn.InspectionPickingInRequest;
import com.hanyun.scm.api.domain.request.Replenishment.ReplenishmentApplyConfirmRequest;
import com.hanyun.scm.api.domain.request.Replenishment.ReplenishmentApplyRequest;
import com.hanyun.scm.api.domain.request.aggregate.AggregateDistributionRequest;
import com.hanyun.scm.api.domain.request.distribution.DistributionOrderModifyRequest;
import com.hanyun.scm.api.domain.request.distribution.DistributionOrderQueryRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceCreateRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceModifyRequest;
import com.hanyun.scm.api.domain.request.stock.StockQuantQueryRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.domain.response.aggregate.AggregateDistributionResponse;
import com.hanyun.scm.api.domain.result.StockQuantResult;
import com.hanyun.scm.api.exception.ProcessInstanceException;
import com.hanyun.scm.api.exception.ReplenishmentApplyException;
import com.hanyun.scm.api.service.*;
import com.hanyun.scm.api.utils.ActivitiUtil;
import com.hanyun.scm.api.utils.SystemConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


@Service
public class ReplenishmentApplyServiceImpl implements ReplenishmentApplyService {

    @Resource
    private ReplenishmentApplyDao replenishmentApplyDao;
    @Resource
    private ReplenishmentApplyDetailDao replenishmentApplyDetailDao;
    @Resource
    private IdGenerateSeqService idGenerateSeqService;
    @Resource
    private StockQuantService stockQuantService;

    @Resource
    private DistributionOrderDao distributionOrderDao;

    @Resource
    private ProcessInstanceService processInstanceService;

    @Resource
    private ProcessDefinitionService processDefinitionService;

    @Resource
    private AggregateDistributionService aggregateDistributionService;

    @Resource
    private GoodsOdooService goodsOdooService;

    @Resource
    private DistributionOrderDetailDao distributionOrderDetailDao;

    @Resource
    private StockQuantDao stockQuantDao;

    @Resource
    private WarehouseDao warehouseDao;

    @Resource
    private InspectionPickingInDao inspectionPickingInDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReplenishmentApplyServiceImpl.class);

    @Override
    public HttpResponse select(ReplenishmentApplyRequest replenishmentApplyRequest) {
        int count = replenishmentApplyDao.countAllSelect(replenishmentApplyRequest);
        replenishmentApplyRequest.setCount(count);
        List<ReplenishmentApply> list;
        try {
            list = replenishmentApplyDao.selectSelective(replenishmentApplyRequest);
            if (!StringUtils.isEmpty(replenishmentApplyRequest.getUserId())) {
                for (ReplenishmentApply apply : list) {
                    apply.setAuditStatus(processInstanceService.queryAuditor(apply.getReplenishmentId(), replenishmentApplyRequest.getUserId()));
                    List<String> auditors = processInstanceService.queryAuditors(apply.getReplenishmentId());
                    auditors.add(apply.getOperatorId());
                    boolean bl = auditors.size() > 1 && auditors.contains(replenishmentApplyRequest.getUserId());
                    apply.setHistoryStatus(bl);
                }
            }
            BaseResponse response = new BaseResponse(count, list);
            return HttpResponse.success(response);
        } catch (Exception e) {
            LOGGER.error("查询补货申请单失败!", e);
            return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_QUERY_ERROR, e);
        }
    }

    @Override
    public HttpResponse modifyOrder(ReplenishmentApply replenishmentApply) {
        int row;
        try {
            row = replenishmentApplyDao.updateByPrimaryKeySelective(replenishmentApply);
            if (row == 0) {
                return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_UPDATE_ERROR);
            }
            ReplenishmentApply newReplenishmentApply = replenishmentApplyDao.selectById(replenishmentApply.getReplenishmentId());
            ReplenishmentApplyDetail replenishmentApplyDetail = new ReplenishmentApplyDetail();
            updateReplenishmentApplyGoods(replenishmentApplyDetail, newReplenishmentApply);
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("修改补货申请单失败", e);
            return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_QUERY_ERROR, e);
        }
    }

    @Override
    public HttpResponse queryById(ReplenishmentApplyRequest replenishmentApplyRequest) {
        try {
            String applyId = replenishmentApplyRequest.getReplenishmentId();
            ReplenishmentApply old = replenishmentApplyDao.selectById(applyId);
            if (old == null) {
                LOGGER.error("未查询到记录!");
                return HttpResponse.failure(ResultCode.DATA_NOT_FOUND);
            }
            List<ReplenishmentApplyDetail> detailList = replenishmentApplyDetailDao.selectByReplenishmentId(applyId);
            Map<String, GoodsOdoo> map = goodsOdooService.getReplenishmentApplyGoodsMap(old.getBrandId(), detailList);
            detailList.forEach(r -> {
                String goodsId = r.getGoodsId();
                boolean bl = !map.isEmpty() && map.get(goodsId) != null && !StringUtils.isEmpty(map.get(goodsId).getGoodsPic());
                r.setGoodsPic(bl ? map.get(goodsId).getGoodsPic() : "");
            });
            old.setDetailList(detailList);
            //查询申请单对应的配送单
            DistributionOrderQueryRequest record = new DistributionOrderQueryRequest();
            record.setBrandId(old.getBrandId());
            record.setSourceReplenishmentId(applyId);
            List<DistributionOrder> sourceList = distributionOrderDao.selectSourceApplyId(record);
            old.setSourceIds(sourceList);
            return HttpResponse.success(old);
        } catch (Exception e) {
            LOGGER.error("查询补货申请单详单失败", e);
            return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_QUERY_ERROR, e);
        }

    }

    @Override
    public HttpResponse create(ReplenishmentApply replenishmentApply) {
        List<ReplenishmentApplyDetail> goodsList = replenishmentApply.getDetailList();
        for (int i = goodsList.size() - 1; i >= 0; i--) {
            if (goodsList.get(i) == null || StringUtils.isEmpty(goodsList.get(i).getGoodsId())) {
                goodsList.remove(i);
            }
        }
        String replenishmentApplyId = replenishmentApply.getReplenishmentId();
        String replenishmentDocumentId = replenishmentApply.getReplenishmentDocumentId();
        if (StringUtils.isEmpty(replenishmentApplyId)) {//新增
            replenishmentApplyId = IdUtil.uuid();
            try {
                replenishmentDocumentId = idGenerateSeqService.generateId(IdGenerateType.BHSQ);
                replenishmentApply.setReplenishmentDocumentId(replenishmentDocumentId);
            } catch (Exception e) {
                LOGGER.error("创建商品详单失败!", e);
                return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_UPDATE_ERROR);
            }

            replenishmentApply.setReplenishmentId(replenishmentApplyId);
            replenishmentApply.setDistributionType(SystemConfigUtil.getDistributionType(replenishmentApply.getBrandId(),replenishmentApply.getStoreId()));
            try {
                int rows = replenishmentApplyDao.insertSelective(replenishmentApply);
                if (rows == Consts.DB_WITHOUT_HANDLE) {
                    return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_INSERT_ERROR);
                }

                for (ReplenishmentApplyDetail obj : goodsList) {  //插入明细信息
                    obj.setToStoreId(replenishmentApply.getToStoreId());
                    obj.setToStoreName(replenishmentApply.getToStoreName());
                    rows = insertReplenishmentApplyGoods(replenishmentApply, obj);
                    if (rows == Consts.DB_WITHOUT_HANDLE) {
                        LOGGER.error("创建商品详单失败!");
                        return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_INSERT_ERROR);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("创建库存管理商品详单失败", e);
                return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_INSERT_ERROR);
            }
        } else {
            //如果有单号
            ReplenishmentApplyDetail replenishmentApplyDetail = new ReplenishmentApplyDetail();
            replenishmentApplyDao.updateByPrimaryKeySelective(replenishmentApply);
            replenishmentApplyDetail.setReplenishmentId(replenishmentApplyId);
            List<ReplenishmentApplyDetail> dbList;
            try {
                dbList = replenishmentApplyDetailDao.selectByReplenishmentId(replenishmentApplyId);
            } catch (Exception e) {
                LOGGER.error("查询商品详单失败!", e);
                return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_DETAIL_QUERY_ERROR);
            }
            for (ReplenishmentApplyDetail obj : goodsList) {  //插入明细信息
                obj.setToStoreId(replenishmentApply.getToStoreId());
                obj.setToStoreName(replenishmentApply.getToStoreName());
                boolean insertFlag = true;
                String goodsId = obj.getGoodsId();
                for (ReplenishmentApplyDetail Goods : dbList) {
                    String goodsIdDB = Goods.getGoodsId();
                    if (goodsIdDB.equals(goodsId)) {//相同则更新
                        int rows = updateReplenishmentApplyGoods(obj, replenishmentApply);
                        if (rows == Consts.DB_WITHOUT_HANDLE) {
                            return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_DETAIL_UPDATE_ERROR);
                        }
                        insertFlag = false;
                        break;
                    }
                }
                if (insertFlag) {
                    int rows = insertReplenishmentApplyGoods(replenishmentApply, obj);
                    if (rows == Consts.DB_WITHOUT_HANDLE) {
                        return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_DETAIL_INSERT_ERROR);
                    }
                }
            }
            for (ReplenishmentApplyDetail Goods : dbList) {//判断是否需要删除
                String goodsIdDB = Goods.getGoodsId();
                boolean deleteFlag = true;
                for (ReplenishmentApplyDetail obj : goodsList) {  //插入明细信息
                    String goodsId = obj.getGoodsId();
                    if (goodsIdDB.equals(goodsId)) {
                        deleteFlag = false;
                    }
                }
                if (deleteFlag) {
                    try {
                        int rows = replenishmentApplyDetailDao.deleteByPrimaryKey(goodsIdDB, replenishmentApplyId);
                        if (rows == Consts.DB_WITHOUT_HANDLE) {
                            return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_DETAIL_DELETE_ERROR);
                        }
                    } catch (Exception e) {
                        LOGGER.error("删除商品失败!", e);
                        return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_DETAIL_DELETE_ERROR);
                    }
                }
            }
        }
        boolean exitStatus = processDefinitionService.exist(replenishmentApply.getBrandId(), Consts.PROCESS_TYPE_REPLENISHMENT_APPLY);
        if (exitStatus) {
            return HttpResponse.success(new String[]{replenishmentApplyId, replenishmentDocumentId, "exist"});
        }
        return HttpResponse.success(new String[]{replenishmentApplyId, replenishmentDocumentId});
    }

    @Override
    public List<ReplenishmentApply> exportReplenishmentApply(ReplenishmentApplyRequest replenishmentApplyRequest) {
        List<ReplenishmentApply> list = new ArrayList<>();
        list = replenishmentApplyDao.selectSelective(replenishmentApplyRequest);
        for (ReplenishmentApply apply : list) {
            apply.setExportOrderStatus(MapConsts.getReplenishmentApplyStatus().get(apply.getReplenishmentStatus()));
            apply.setExportGetStatus(MapConsts.getReceiptStatus().get(apply.getReceiptStatus()));
        }
        return list;
    }

    @Override
    public List<List<Object>> exportReplenishmentApplyDetail(ReplenishmentApplyRequest record) throws ReplenishmentApplyException {
        try {
            List<List<Object>> resultList = new ArrayList<List<Object>>();//返回的数据List
            String applyId = record.getReplenishmentId();
            ReplenishmentApply apply = replenishmentApplyDao.selectById(applyId);
            if (apply == null) {
                throw new ReplenishmentApplyException("未查询到数据");
            }

            String brandId = apply.getBrandId();
            String storeId = apply.getToStoreId();
            //查询门店仓库
            WarehouseQueryRequest warehouse = new WarehouseQueryRequest();
            warehouse.setBrandId(brandId);
            warehouse.setStoreId(storeId);
            warehouse.setWarehouseStatus(Consts.WAREHOUSE_STATUS_ENABLE);
            List<Warehouse> warehouseList = warehouseDao.select(warehouse);

            //按门店查询库存对象
            StockQuantQueryRequest stockQuantStore = new StockQuantQueryRequest();
            stockQuantStore.setBrandId(brandId);
            stockQuantStore.setWarehouseId(warehouseList.get(0).getWarehouseId());

            //按配货中心查询库存对象
            StockQuantQueryRequest stockQuantCenter = new StockQuantQueryRequest();
            stockQuantCenter.setBrandId(apply.getBrandId());
            if (!StringUtils.isEmpty(apply.getStoreId())) {
                stockQuantCenter.setStoreId(apply.getStoreId());
            }
            stockQuantCenter.setWarehouseId(record.getToStoreWarehouse());
            //门店库存list
            List<StockQuantResult> stockQuantStoreList = stockQuantService.selectWithParam(stockQuantStore).getList();
            //配货中心库存list
            List<StockQuantResult> stockQuantCenterList = stockQuantDao.queryDetail(stockQuantCenter);

            Map<String, StockQuant> storeMap = new HashMap<String, StockQuant>();
            Map<String, StockQuantResult> centerMap = new HashMap<String, StockQuantResult>();
            stockQuantStoreList.forEach(s -> storeMap.put(s.getGoodsId(), s));
            stockQuantCenterList.forEach(s -> centerMap.put(s.getGoodsId(), s));

            List<ReplenishmentApplyDetail> detailList = replenishmentApplyDetailDao.selectByReplenishmentId(applyId);
            Integer step = 0;
            for (ReplenishmentApplyDetail r : detailList) {
                ++step;
                List<Object> beanList = new ArrayList<>();
                String goodsId = r.getGoodsId();
                Long applyNum = r.getApplyNum();
                beanList.add(step);
                beanList.add(r.getGoodsName());
                beanList.add(r.getFeatures());
                beanList.add(r.getGoodsCode());
                beanList.add(r.getGoodsBarCode());
                beanList.add(r.getUnitName());
                beanList.add(applyNum);
                beanList.add(r.getStockInNum());
                beanList.add(applyNum - r.getStockInNum());
                beanList.add(UtilCommon.getMultiplyValue(r.getUnitPrice() / 100, 1));
                beanList.add(UtilCommon.getMultiplyValue(r.getUnitPrice() / 100, applyNum));
                beanList.add(storeMap.get(goodsId).getStockNum());
                beanList.add(storeMap.get(goodsId).getStockLower());
                beanList.add(r.getClassifyName());
                resultList.add(beanList);
            }
            return resultList;
        } catch (Exception e) {
            LOGGER.error("查询导出数据失败!", e);
            throw new ReplenishmentApplyException("查询导出数据失败!", e);
        }
    }

    @Override
    public HttpResponse detailForDistribution(String[] applyIds, Integer editStatus) {
        try {
            List<String> applyIdList = Arrays.asList(applyIds);
            AggregateDistributionRequest aggregateDistributionRequest = new AggregateDistributionRequest();
            if (editStatus != null && editStatus == 1) {
                aggregateDistributionRequest.setEditStatus(editStatus);
            }
            aggregateDistributionRequest.setApplyIds(applyIdList);
            List<String> goodsIds = replenishmentApplyDetailDao.selectGoodsIdsForAggregate(aggregateDistributionRequest);
            if (goodsIds == null || goodsIds.isEmpty()) {
                return HttpResponse.failure(ResultCode.REPLENISHMENT_APPLY_DETAIL_NO_GOODS);
            }
            aggregateDistributionRequest.setGoodsIds(goodsIds);
            List<ReplenishmentApplyDetail> replenishmentApplyDetailList = replenishmentApplyDetailDao.selectListForAggregate(aggregateDistributionRequest);
            Map<String, List<ReplenishmentApplyDetail>> replenishmentApplyDetailMap = new HashMap<>();
            for (ReplenishmentApplyDetail replenishmentApplyDetail : replenishmentApplyDetailList) {
                List<ReplenishmentApplyDetail> children;
                if (replenishmentApplyDetailMap.get(replenishmentApplyDetail.getGoodsId()) == null) {
                    children = new ArrayList<>();
                } else {
                    children = replenishmentApplyDetailMap.get(replenishmentApplyDetail.getGoodsId());
                }
                children.add(replenishmentApplyDetail);
                replenishmentApplyDetailMap.put(replenishmentApplyDetail.getGoodsId(), children);
            }
            AggregateDistributionResponse aggregateDistributionResponse = new AggregateDistributionResponse(goodsIds, 0, replenishmentApplyDetailMap);
            return HttpResponse.success(aggregateDistributionResponse);
        } catch (Exception e) {
            LOGGER.error("汇总补货申请商品信息失败!", e);
            return HttpResponse.failure(ResultCode.REPLENISHMENT_APPLY_DETAIL_FOR_DISTRIBUTION_ERROR);
        }
    }

    @Override
    public HttpResponse queryStore(ReplenishmentApplyRequest replenishmentApplyRequest) {
        try {
            return null;
        } catch (Exception e) {
            LOGGER.error("查询补货门店失败!", e);
            return HttpResponse.failure(ResultCode.REPLENISHMENT_APPLY_QUERY_STORE_ERROR);
        }
    }

    @Override
    public void invalidateApply() {
        try {
            Date now = new Date();
            Date morning = DateCalcUtil.addSeconds(DateCalcUtil.getDayBegin(new Date()), -1);
            int row = replenishmentApplyDao.invalidateApply(morning);
            LOGGER.debug("执行时间为：" + (new Date().getTime() - now.getTime()));
            if (row == 0) {
                LOGGER.error("无可更改数据!");
            }
        } catch (Exception e) {
            LOGGER.error("失效申请单状态修改失败!", e);
        }
    }

    private int insertReplenishmentApplyGoods(ReplenishmentApply replenishmentApply, ReplenishmentApplyDetail replenishmentApplyDetail) {
        String replenishmentApplyDetailId = IdUtil.uuid();
        replenishmentApplyDetail.setReplenishmentDetailId(replenishmentApplyDetailId);
        replenishmentApplyDetail.setReplenishmentId(replenishmentApply.getReplenishmentId());
        replenishmentApplyDetail.setReplenishmentStatus(replenishmentApply.getReplenishmentStatus());
        replenishmentApplyDetail.setBrandId(replenishmentApply.getBrandId());
        replenishmentApplyDetail.setStoreId(replenishmentApply.getStoreId());
        return replenishmentApplyDetailDao.insertSelective(replenishmentApplyDetail);
    }

    private int updateReplenishmentApplyGoods(ReplenishmentApplyDetail replenishmentApplyDetail, ReplenishmentApply replenishmentApply) {

        replenishmentApplyDetail.setReplenishmentId(replenishmentApply.getReplenishmentId());
        replenishmentApplyDetail.setAuditTime(replenishmentApply.getAuditTime());
        replenishmentApplyDetail.setReplenishmentStatus(replenishmentApply.getReplenishmentStatus());
        return replenishmentApplyDetailDao.updateByReplenishmentId(replenishmentApplyDetail);
    }

    @Override
    public HttpResponse confirm(ReplenishmentApplyConfirmRequest applyConfirmRequest) {
        try {
            String replenishmentId = applyConfirmRequest.getReplenishmentId();
            String auditorId = applyConfirmRequest.getAuditorId();
            String auditorName = applyConfirmRequest.getAuditorName();

            ReplenishmentApply apply = replenishmentApplyDao.selectById(replenishmentId);
            if (apply == null) {
                throw new ReplenishmentApplyException("单据为空");
            }
            if (applyConfirmRequest.getReplenishmentStatus() != null && applyConfirmRequest.getReplenishmentStatus() == Consts.REPLENISHEMTN_APPLY_STATUS_CONFIRMED) {
                apply.setReplenishmentStatus(Consts.REPLENISHEMTN_APPLY_STATUS_CONFIRMED);
            } else {
                if (!applyConfirmRequest.getAuditStatus()) {
                    ReplenishmentApply modifyApply = new ReplenishmentApply();
                    modifyApply.setReplenishmentId(replenishmentId);
                    modifyApply.setAuditorId(auditorId);
                    modifyApply.setAuditorName(auditorName);
                    modifyApply.setReplenishmentStatus(Consts.REPLENISHEMTN_APPLY_STATUS_SAVE);
                    int row = replenishmentApplyDao.updateByPrimaryKeySelective(modifyApply);
                    if (row == 0) {
                        HttpResponse.failure(ResultCode.REPLENISHMENT_ORDER_CONFIRM_ERROR);
                    }
                    processInstanceService.delete(replenishmentId);
                    return HttpResponse.success();
                }
                ProcessInstanceModifyRequest processInstanceModifyRequest = new ProcessInstanceModifyRequest();
                processInstanceModifyRequest.setBusinessId(replenishmentId);
                processInstanceModifyRequest.setUserId(applyConfirmRequest.getUserId());
                processInstanceModifyRequest.setRemark("通过, 执行");
                HttpResponse response = processInstanceService.audit(processInstanceModifyRequest);
                if (!StringUtils.equals(response.getCode(), "0") || response.getData() == null) {
                    return HttpResponse.failure(ResultCode.REPLENISHMENT_ORDER_CONFIRM_ERROR);
                }
                Integer instanceStatus = Integer.parseInt(response.getData().toString());
                if (instanceStatus != Consts.PROCESS_INSTANCE_STATUS_CONFIRMED) {
                    apply.setReplenishmentStatus(Consts.REPLENISHEMTN_APPLY_STATUS_CONIFRMING);
                } else {
                    apply.setReplenishmentStatus(Consts.REPLENISHEMTN_APPLY_STATUS_CONFIRMED);
                }
            }
            ReplenishmentApply modifyApply = new ReplenishmentApply();
            modifyApply.setReplenishmentId(replenishmentId);
            modifyApply.setAuditorId(auditorId);
            modifyApply.setAuditorName(auditorName);
            modifyApply.setAuditTime(new Date());
            modifyApply.setReplenishmentStatus(apply.getReplenishmentStatus());
            int modifyRow = replenishmentApplyDao.updateByPrimaryKeySelective(modifyApply);
            if (modifyRow <= 0) {
                return HttpResponse.failure(ResultCode.REPLENISHMENT_ORDER_CONFIRM_ERROR);
            }
            int detailRow = replenishmentApplyDetailDao.aduitDetail(replenishmentId);
            if (detailRow <= 0) {
                throw new ReplenishmentApplyException("详情审核失败");
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("更新状态失败!", e);
            return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_DETAIL_UPDATE_ERROR);
        }
    }

    @Override
    public HttpResponse commit(String replenishmentId) {
        try {
            ReplenishmentApply old = replenishmentApplyDao.selectById(replenishmentId);
            if (old == null) {
                throw new ReplenishmentApplyException("单据为空");
            }
            old.setReplenishmentStatus(Consts.REPLENISHEMTN_APPLY_STATUS_COMMITED);
            int modifyRow = replenishmentApplyDao.updateByPrimaryKeySelective(old);
            if (modifyRow <= 0) {
                return HttpResponse.failure(ResultCode.REPLENISHMENT_ORDER_COMMIT_ERROR);
            }
            ProcessInstanceCreateRequest processInstanceCreateRequest = new ProcessInstanceCreateRequest();
            processInstanceCreateRequest.setProcessType(Consts.PROCESS_TYPE_REPLENISHMENT_APPLY);
            processInstanceCreateRequest.setBrandId(old.getBrandId());
            processInstanceCreateRequest.setBusinessId(replenishmentId);
            processInstanceCreateRequest.setPrice(old.getTotalPrice());
            ActivitiUtil.getInstance();
            HttpResponse response = processInstanceService.create(processInstanceCreateRequest, old.getOperatorId());
            if (!StringUtils.equals(response.getCode(), "0")) {
                throw new ProcessInstanceException("提审失败!");
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("更新状态失败!", e);
            return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_DETAIL_UPDATE_ERROR);
        }
    }

    @Override
    public HttpResponse queryReplenishmentGoods(ReplenishmentDTO dto) {
        try {
            String applyId = dto.getReplenishmentApplyId();
            ReplenishmentApply old = replenishmentApplyDao.selectById(applyId);
            if (old == null) {
                LOGGER.error("申请单记录为空");
                return HttpResponse.failure(ResultCode.DATA_NOT_FOUND);
            }
            ReplenishmentApplyDetail record = new ReplenishmentApplyDetail();
            record.setReplenishmentId(applyId);
            record.setGoodsName(dto.getGoodsName());
            record.setGoodsBarCode(dto.getGoodsBarCode());
            List<ReplenishmentApplyDetail> list = replenishmentApplyDetailDao.selectSelective(record);

            //封装商品map对象
            Map<String, GoodsOdoo> map = goodsOdooService.getReplenishmentApplyGoodsMap(old.getBrandId(), list);
            if (!list.isEmpty() && list.size() > 0) {
                list.forEach(r -> {
                    String goodsId = r.getGoodsId();
                    boolean bl = !map.isEmpty() && map.get(goodsId) != null && !StringUtils.isEmpty(map.get(goodsId).getGoodsPic());
                    r.setGoodsPic(bl ? map.get(goodsId).getGoodsPic() : "");
                });
            }
            return HttpResponse.success(list);
        } catch (Exception e) {
            LOGGER.error("查询申请单详情失败！", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse queryApplyForDistributionRecord(String applyId) {
        try {
            ReplenishmentApply old = replenishmentApplyDao.selectById(applyId);
            if (old == null) {
                LOGGER.error("申请单记录为空");
                return HttpResponse.failure(ResultCode.DATA_NOT_FOUND);
            }
            List<Object> result = new ArrayList<>();
            DistributionOrderQueryRequest record = new DistributionOrderQueryRequest();
            record.setBrandId(old.getBrandId());
            record.setSourceReplenishmentId(applyId);
            List<DistributionOrder> list = distributionOrderDao.select(record);
            if (!list.isEmpty() && list.size() > 0) {
                list.forEach(d -> {
                    String distributionId = d.getDistributionOrderId();
                    DistributionOrderDetail distributionOrderDetail = new DistributionOrderDetail();
                    distributionOrderDetail.setDistributionOrderId(distributionId);
                    List<DistributionOrderDetail> detailList = distributionOrderDetailDao.select(distributionOrderDetail);
                    if (!detailList.isEmpty() && detailList.size() > 0) {
                        Map<String, GoodsOdoo> map = goodsOdooService.getDistributionGoodsMap(d.getBrandId(), detailList);
                        detailList.forEach(dd -> {
                            String goodsId = dd.getGoodsId();
                            boolean bl = !map.isEmpty() && map.get(goodsId) != null && !StringUtils.isEmpty(map.get(goodsId).getGoodsPic());
                            dd.setGoodsPic(bl ? map.get(goodsId).getGoodsPic() : "");
                        });
                    }
                    d.setDistributionOrderDetailList(detailList);
                    result.add(d);
                });
            }
            return HttpResponse.success(result);
        } catch (Exception e) {
            LOGGER.error("查询申请单收发货记录失败", e);
            return HttpResponse.failure(ResultCode.APPLY_RECEIVING_AND_DELIVERY_RECORD_ERROR);
        }
    }

    /**
     * 驳回配送单
     * @param brandId 品牌id
     * @param ids     配送单ids
     * @throws ReplenishmentApplyException 异常
     */
    private void reject(String brandId, Set<String> ids) throws ReplenishmentApplyException{
//        boolean exitStatus = processDefinitionService.exist(brandId, Consts.PROCESS_TYPE_DISTRIBUTION_ORDER);
        for(String id : ids){
            DistributionOrder old = distributionOrderDao.selectByDistributionOrderId(id);
            if(old == null){
                throw new ReplenishmentApplyException("未查询配送单到订单");
            }
            DistributionOrderModifyRequest record = new DistributionOrderModifyRequest();
            record.setBrandId(brandId);
            record.setDistributionOrderId(id);
            record.setRemark((StringUtils.isEmpty(old.getRemark())?"":old.getRemark()) + Consts.CLOSE_REMARK);
            record.setDistributionOrderStatus(Consts.DISTRIBUTION_ORDER_STATUS_OVER);
            int row = distributionOrderDao.updateByDistributionOrderId(record);
            if(row <= 0){
                throw new ReplenishmentApplyException("驳回配送单失败");
            }
            try {
                ActivitiUtil.deleteProcessInstance(old.getDistributionOrderId(), processInstanceService);
            }catch (Exception e){
                throw new ReplenishmentApplyException("删除实例失败", e);
            }
//            if(exitStatus){
//                processInstanceService.delete(id);
//            }
        }
    }

    @Override
    public HttpResponse close(String replenishmentId) {
        try {
            ReplenishmentApply apply = replenishmentApplyDao.selectById(replenishmentId);
            if (apply == null) {
                LOGGER.error("记录不存在");
                return HttpResponse.failure(ResultCode.REPLENISHMENT_APPLY_RECORD_NOT_FOUND);
            }
            if(apply.getReplenishmentStatus() == Consts.REPLENISHEMTN_APPLY_STATUS_DONE){
                LOGGER.error("该单据已完成不需要关闭");
                return HttpResponse.failure(ResultCode.ORDER_IS_DONE_NOT_CLOSE_ERROR);
            }
            //处理还有待处理的入库单和配送单
            //配送单
            DistributionOrderQueryRequest record = new DistributionOrderQueryRequest();
            record.setBrandId(apply.getBrandId());
            record.setSourceReplenishmentId(apply.getReplenishmentId());
            List<DistributionOrder> distributionOrderList = distributionOrderDao.select(record);

            Set<String> pedingDistributionIds = new HashSet<>();            //待处理的配送单ids
            if (!distributionOrderList.isEmpty()) {
                boolean pedinginspectionStatus = false;                     //是否有待入库 状态
                Set<String> pedingInspectionIds = new HashSet<>();

                boolean distributionNotInStockStatus = false;               //配送已出库未入库 状态
                Set<String> notInIds = new HashSet<>();                     //已出入未入库的配送单

                //配送单对应的入库单列表
                Map<String, Set<String>> map = new HashMap<>();

                for (DistributionOrder distributionOrder : distributionOrderList) {
                    String brandId = distributionOrder.getBrandId();
                    String distributionId = distributionOrder.getDistributionOrderId();
                    String distributionDocumentId = distributionOrder.getDistributionOrderDocumentId();
                    Integer distributionStatus = distributionOrder.getDistributionOrderStatus();
                    //处理未审核的配送单
                    if (distributionStatus < Consts.REPLENISHEMTN_APPLY_STATUS_CONFIRMED) { //待审核
                        pedingDistributionIds.add(distributionId);
                    } else {
                        pedingDistributionIds.add(distributionId);
                    }
                    //处理已审核的配送单的下的验收入库单
                    if (distributionStatus == Consts.REPLENISHEMTN_APPLY_STATUS_CONFIRMED) {
                        InspectionPickingInRequest request = new InspectionPickingInRequest();
                        request.setBrandId(brandId);
                        request.setDistributionOrderId(distributionId);
                        List<InspectionPickingIn> inspectionPickingInList = inspectionPickingInDao.selectOrders(request);
                        if (inspectionPickingInList.isEmpty()) {
                            distributionNotInStockStatus = true;
                            notInIds.add(distributionDocumentId);
                            continue;
                        }
                        for (InspectionPickingIn in : inspectionPickingInList) {
                            Integer inspecitonStatus = in.getInspectionStatus();
                            if (inspecitonStatus < Consts.INSPECTIONPICKING_ORDER_STATUS_CONFIRMED) {
                                pedinginspectionStatus = true;
                                pedingInspectionIds.add(in.getInspectionDocumentId());
                            }
                        }
                        map.put(distributionDocumentId, pedingInspectionIds);
                    }
                }
                if (distributionNotInStockStatus) {
                    LOGGER.info("还有已审核却未入库的配送单:" + notInIds);
                    return HttpResponse.failure(ResultCode.NOT_IN_DISTRIBUTION_ORDER);
                }
                if (pedinginspectionStatus) {
                    LOGGER.info("还有待处理的入库单:" + pedingInspectionIds);
                    return HttpResponse.failure(ResultCode.INSPECTION_PEDING_ORDER);
                }
            }
            apply.setReplenishmentStatus(Consts.REPLENISHEMTN_APPLY_STATUS_OVER);
            apply.setRemark((StringUtils.isEmpty(apply.getRemark())?"":apply.getRemark())+Consts.CLOSE_REMARK);
            int row = replenishmentApplyDao.updateByPrimaryKeySelective(apply);
            if (row <= 0) {
                return HttpResponse.failure(ResultCode.REPLENISHMENT_APPLY_CLOSE_STATUS_ERROR);
            }
            ActivitiUtil.deleteProcessInstance(apply.getReplenishmentId(), processInstanceService);
            //对待处理的配送单驳回
            reject(apply.getBrandId(), pedingDistributionIds);
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("关闭申请单状态失败", e);
            return HttpResponse.failure(ResultCode.REPLENISHMENT_APPLY_CLOSE_STATUS_ERROR);
        }
    }

}

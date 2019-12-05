package com.hanyun.scm.api.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.IdGenerateType;
import com.hanyun.scm.api.consts.MapConsts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.PurchaseOrderDetailDao;
import com.hanyun.scm.api.dao.StockPickingDao;
import com.hanyun.scm.api.dao.StockPickingGoodsDao;
import com.hanyun.scm.api.dao.StockQuantDao;
import com.hanyun.scm.api.domain.PurchaseOrderDetail;
import com.hanyun.scm.api.domain.StockPicking;
import com.hanyun.scm.api.domain.StockPickingGoods;
import com.hanyun.scm.api.domain.StockQuant;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceCreateRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceModifyRequest;
import com.hanyun.scm.api.domain.request.stock.PurchasePickingRequest;
import com.hanyun.scm.api.domain.request.stock.StockPickingGoodsRequest;
import com.hanyun.scm.api.domain.request.stock.StockPickingRequest;
import com.hanyun.scm.api.domain.request.stock.StockQuantQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.domain.response.PurchasePickingAmountResponse;
import com.hanyun.scm.api.domain.result.StockQuantResult;
import com.hanyun.scm.api.exception.ProcessInstanceException;
import com.hanyun.scm.api.exception.StockPickingException;
import com.hanyun.scm.api.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StockPickingServiceImpl implements StockPickingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockPickingServiceImpl.class);
    @Resource
    private StockPickingDao stockPickingDao;

    @Resource
    private StockPickingGoodsDao stockPickingGoodsDao;

    @Resource
    private StockQuantService stockQuantService;

    @Resource
    private IdGenerateSeqService idGenerateSeqService;

    @Resource
    private StockQuantDao stockQuantDao;

    @Resource
    private PurchaseOrderDetailDao purchaseOrderDetailDao;

    @Resource
    private ProcessInstanceService processInstanceService;

    @Resource
    private ProcessDefinitionService processDefinitionService;

    //审核
    @Override
    public HttpResponse modifyStockPicking(StockPicking stockPicking) {
        try {
            StockPicking old = stockPickingDao.selectByStockPickingId(stockPicking.getStockPickingId());
            if (old == null) {
                return HttpResponse.failure(ResultCode.DATA_NOT_FOUND);
            }
            StockPicking modify = new StockPicking();
            modify.setStockPickingId(stockPicking.getStockPickingId());
            if (stockPicking.getStockPickingStatus() != null && stockPicking.getStockPickingStatus() == Consts.ORDER_STATUS_CONFIRMED) {
                modify.setStockPickingStatus(Consts.ORDER_STATUS_CONFIRMED);
            } else {
                if (!stockPicking.getAuditStatus()) {
                    int row = cancelStockPicking(stockPicking.getStockPickingId());
                    if (row == 0) {
                        return HttpResponse.failure(ResultCode.STOCK_PICKING_CONFIRM_ERROR);
                    }
                    processInstanceService.delete(stockPicking.getStockPickingId());
                    return HttpResponse.success();
                }
                ProcessInstanceModifyRequest processInstanceModifyRequest = new ProcessInstanceModifyRequest();
                processInstanceModifyRequest.setBusinessId(stockPicking.getStockPickingId());
                processInstanceModifyRequest.setUserId(stockPicking.getUserId());
                processInstanceModifyRequest.setRemark("通过, 执行");
                HttpResponse response = processInstanceService.audit(processInstanceModifyRequest);
                if (!StringUtils.equals(response.getCode(), "0") || response.getData() == null) {
                    return HttpResponse.failure(ResultCode.STOCK_PICKING_CONFIRM_ERROR);
                }
                Integer instanceStatus = Integer.parseInt(response.getData().toString());
                if (instanceStatus != Consts.PROCESS_INSTANCE_STATUS_CONFIRMED) {
                    modify.setStockPickingStatus(Consts.ORDER_STATUS_CONFIRMING);
                } else {
                    modify.setStockPickingStatus(Consts.ORDER_STATUS_CONFIRMED);
                }
            }
            if (modify.getStockPickingStatus() == Consts.ORDER_STATUS_CONFIRMED) {
                stockQuantService.updateQuantByStockPicking(old, Consts.STOCK_QUANT_CHANGE_TYPE_STOCK_PICKING);
            }
            int rows = stockPickingDao.updateByStockPickingId(modify);
            if (rows == 0) {
                return HttpResponse.failure(ResultCode.STOCK_PICKING_CONFIRM_ERROR);
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("审核失败!", e);
            return HttpResponse.failure(ResultCode.STOCK_PICKING_CONFIRM_ERROR);
        }
    }

    @Override
    public HttpResponse selectByType(StockPickingRequest stockPickingRequest) {
        try {
            int count = stockPickingDao.countAll(stockPickingRequest);
            //判断分页，页码     begin
            stockPickingRequest.setCount(count);
            //判断分页，页码    end
            List<StockPicking> stockPickingList = stockPickingDao.select(stockPickingRequest);
            if (!StringUtils.isEmpty(stockPickingRequest.getUserId())) {
                for (StockPicking stockPicking : stockPickingList) {
                    stockPicking.setAuditStatus(processInstanceService.queryAuditor(stockPicking.getStockPickingId(), stockPickingRequest.getUserId()));
                    List<String> auditors = processInstanceService.queryAuditors(stockPicking.getStockPickingId());
                    auditors.add(stockPicking.getOperatorId());
                    stockPicking.setHistoryStatus(auditors.size() > 1 && auditors.contains(stockPickingRequest.getUserId()));
                }
            }
            BaseResponse baseRespose = new BaseResponse(count, stockPickingList);
            return HttpResponse.success(baseRespose);
        } catch (Exception e) {
            LOGGER.error("查询商品列表失败!", e);
            if (stockPickingRequest.getStockPickingType() == Consts.STOCK_PICKING_TYPE_STOCK_IN) {
                return HttpResponse.failure(ResultCode.STOCKPICKING_IN_QUERY_ERROR);
            } else if (stockPickingRequest.getStockPickingType() == Consts.STOCK_PICKING_TYPE_STOCK_OUT) {
                return HttpResponse.failure(ResultCode.STOCKPICKING_OUT_QUERY_ERROR);
            } else if (stockPickingRequest.getStockPickingType() == Consts.STOCK_PICKING_TYPE_STOCK_TRANSFER) {
                return HttpResponse.failure(ResultCode.STOCKPICKING_CHANGE_QUERY_ERROR);
            } else {
                return HttpResponse.failure(ResultCode.STOCKPICKING_QUERY_ERROR);
            }

        }
    }

    /**
     * 查询收货单详情
     */
    @Override
    public HttpResponse queryBystockPickingId(StockPickingRequest stockPickingRequest) {
        try {
            StockPicking select = stockPickingDao.queryBystockPickingId(stockPickingRequest);
            return HttpResponse.success(select);
        } catch (Exception e) {
            LOGGER.error("查询列表失败!", e);
            return HttpResponse.failure(ResultCode.STOCKPICKINGGOODS_QUERY_ERROR);
        }
    }

    @Override
    public HttpResponse create(StockPicking stockPicking) {
        List<StockPickingGoods> goodsList = stockPicking.getStockPickingGoodsList();
        for (int i = goodsList.size() - 1; i >= 0; i--) {
            if (goodsList.get(i) == null || StringUtils.isEmpty(goodsList.get(i).getGoodsId())) {
                goodsList.remove(i);
            }
        }
        String brandId;
        String stockPickingId = stockPicking.getStockPickingId();
        String stockPickingDocumentId = stockPicking.getStockPickingDocumentId();
        if (StringUtils.isEmpty(stockPickingId)) {//新增
            brandId = stockPicking.getBrandId();
            stockPickingId = IdUtil.uuid();
            try {
                if (stockPicking.getStockPickingType() == Consts.STOCK_PICKING_TYPE_STOCK_IN) {
                    stockPickingDocumentId = idGenerateSeqService.generateId(IdGenerateType.RK);
                    stockPicking.setStockPickingDocumentId(stockPickingDocumentId);
                }
                if (stockPicking.getStockPickingType() == Consts.STOCK_PICKING_TYPE_STOCK_OUT) {
                    stockPickingDocumentId = idGenerateSeqService.generateId(IdGenerateType.CK);
                    stockPicking.setStockPickingDocumentId(stockPickingDocumentId);
                }
                if (stockPicking.getStockPickingType() == Consts.STOCK_PICKING_TYPE_STOCK_INTERVAL) {
                    stockPickingDocumentId = idGenerateSeqService.generateId(IdGenerateType.YK);
                    stockPicking.setStockPickingDocumentId(stockPickingDocumentId); //移库单
                }
                if (stockPicking.getStockPickingType() == Consts.STOCK_PICKING_TYPE_STOCK_TRANSFER) {
                    stockPickingDocumentId = idGenerateSeqService.generateId(IdGenerateType.DB);
                    stockPicking.setStockPickingDocumentId(stockPickingDocumentId);
                }
                if (stockPicking.getStockPickingType() == Consts.STOCK_PICKING_TYPE_STOCK_RECEIVING) {
                    stockPickingDocumentId = idGenerateSeqService.generateId(IdGenerateType.SH);
                    stockPicking.setStockPickingDocumentId(stockPickingDocumentId);
                }
            } catch (Exception e) {
                LOGGER.error("创建商品详单失败!", e);
                return HttpResponse.failure(ResultCode.STOCKPICKING_MAKE_ORDERID_ERROR);
            }

            stockPicking.setStockPickingId(stockPickingId);

            try {
                int rows = stockPickingDao.insertSelective(stockPicking);
                if (rows == Consts.DB_WITHOUT_HANDLE) {
                    return HttpResponse.failure(ResultCode.STOCKPICKING_INSERT_ERROR);
                }

                for (StockPickingGoods obj : goodsList) {  //插入明细信息
                    rows = insertStockpickingGoods(stockPicking, obj);
                    if (rows == Consts.DB_WITHOUT_HANDLE) {
                        LOGGER.error("创建商品详单失败!");
                        return HttpResponse.failure(ResultCode.STOCKPICKINGGOODS_INSERT_ERROR);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("创建库存管理商品详单失败", e);
                return HttpResponse.failure(ResultCode.STOCKPICKING_CREAT_ERROR);
            }
        } else {
            StockPicking old = stockPickingDao.selectByStockPickingId(stockPickingId);
            if (old == null) {
                return HttpResponse.failure(ResultCode.STOCK_PICKING_CONFIRM_ERROR);
            }
            brandId = old.getBrandId();
            //如果有单号
            StockPickingGoods stockPickingGoods = new StockPickingGoods();
            stockPickingDao.updateByStockPickingId(stockPicking);
            stockPickingGoods.setStockPickingId(stockPickingId);
            List<StockPickingGoods> dbList;
            try {
                dbList = stockPickingGoodsDao.selectStockPickingGoods(stockPickingGoods);
            } catch (Exception e) {
                LOGGER.error("查询商品详单失败!", e);
                return HttpResponse.failure(ResultCode.STOCKPICKINGGOODS_QUERY_ERROR);
            }
            for (StockPickingGoods obj : goodsList) {  //插入明细信息
                boolean insertFlag = true;
                String goodsId = obj.getGoodsId();
                for (StockPickingGoods Goods : dbList) {
                    String goodsIdDB = Goods.getGoodsId();
                    if (goodsIdDB.equals(goodsId)) {//相同则更新
                        int rows = updateStockpickingGoods(obj, stockPicking);
                        if (rows == Consts.DB_WITHOUT_HANDLE) {
                            return HttpResponse.failure(ResultCode.STOCKPICKINGGOODS_UPDATE_ERROR);
                        }
                        insertFlag = false;
                        break;
                    }
                }
                if (insertFlag) {
                    int rows = insertStockpickingGoods(stockPicking, obj);
                    if (rows == Consts.DB_WITHOUT_HANDLE) {
                        return HttpResponse.failure(ResultCode.STOCKPICKING_INSERT_ERROR);
                    }
                }
            }
            for (StockPickingGoods Goods : dbList) {//判断是否需要删除
                String goodsIdDB = Goods.getGoodsId();
                boolean deleteFlag = true;
                for (StockPickingGoods obj : goodsList) {  //插入明细信息
                    String goodsId = obj.getGoodsId();
                    if (goodsIdDB.equals(goodsId)) {
                        deleteFlag = false;
                    }
                }
                if (deleteFlag) {
                    try {
                        int rows = stockPickingGoodsDao.deleteByPrimaryKey(goodsIdDB, stockPickingId);
                        if (rows == Consts.DB_WITHOUT_HANDLE) {
                            return HttpResponse.failure(ResultCode.STOCKPICKINGGOODS_DELETE_FOUND);
                        }
                    } catch (Exception e) {
                        LOGGER.error("删除商品失败!", e);
                        return HttpResponse.failure(ResultCode.STOCKPICKINGGOODS_DELETE_ERROR);
                    }
                }
            }
        }
        int processType = 0;
        if (stockPicking.getStockPickingType() == Consts.STOCK_PICKING_TYPE_STOCK_IN || stockPicking.getStockPickingType() == Consts.STOCK_PICKING_TYPE_STOCK_OUT) {
            processType = Consts.PROCESS_TYPE_STOCK_IN_AND_OUT;
        } else if (stockPicking.getStockPickingType() == Consts.STOCK_PICKING_TYPE_STOCK_INTERVAL) {
            processType = Consts.PROCESS_TYPE_STOCK_REQUISITION;
        } else {
            return HttpResponse.failure(ResultCode.STOCK_PICKING_MODIFY_ERROR);
        }
        boolean existStatus = processDefinitionService.exist(brandId, processType);
        if (existStatus) {
            return HttpResponse.success(new String[]{stockPickingId, stockPickingDocumentId, "exist"});
        }
        return HttpResponse.success(new String[]{stockPickingId, stockPickingDocumentId});
    }

    public int insertStockpickingGoods(StockPicking stockPicking, StockPickingGoods stockPickingGoods) {
        String pickingGoodsId = IdUtil.uuid();
        int rows = Consts.PARAMETER_INITIALIZE_INT;
        stockPickingGoods.setPickingGoodsId(pickingGoodsId);
        stockPickingGoods.setStockPickingId(stockPicking.getStockPickingId());
        stockPickingGoods.setBrandId(stockPicking.getBrandId());
        stockPickingGoods.setStoreId(stockPicking.getStoreId());
        try {
            rows = stockPickingGoodsDao.insertSelective(stockPickingGoods);
            return rows;
        } catch (Exception e) {
            LOGGER.error("插入库存管理 商品详单失败!", e);
            return rows;
        }

    }

    /**
     * 更新
     *
     * @param stockPickingGoods 移库商品
     * @param stockPicking      移库单
     * @return
     */
    public int updateStockpickingGoods(StockPickingGoods stockPickingGoods, StockPicking stockPicking) {

        stockPickingGoods.setStockPickingId(stockPicking.getStockPickingId());
        int rows = Consts.PARAMETER_INITIALIZE_INT;
        try {
            rows = stockPickingGoodsDao.updateByPrimaryKey(stockPickingGoods);
            return rows;
        } catch (Exception e) {
            return rows;
        }
    }

    @Override
    public HttpResponse selectStockInAndOut(StockPickingRequest stockPickingRequest) {
        try {
            int count = stockPickingDao.countAllStockInAndOut(stockPickingRequest);
            //判断分页，页码     begin
            stockPickingRequest.setCount(count);
            //判断分页，页码    end
            List<StockPicking> stockPickingList = stockPickingDao.selectStockInAndOut(stockPickingRequest);
            if (!StringUtils.isEmpty(stockPickingRequest.getUserId())) {
                for (StockPicking stockPicking : stockPickingList) {
                    stockPicking.setAuditStatus(processInstanceService.queryAuditor(stockPicking.getStockPickingId(), stockPickingRequest.getUserId()));
                    List<String> auditors = processInstanceService.queryAuditors(stockPicking.getStockPickingId());
                    auditors.add(stockPicking.getOperatorId());
                    stockPicking.setHistoryStatus(auditors.size() > 1 && auditors.contains(stockPickingRequest.getUserId()));
                }
            }
            BaseResponse baseRespose = new BaseResponse(count, stockPickingList);
            return HttpResponse.success(baseRespose);
        } catch (Exception e) {
            LOGGER.error("查询商品列表失败!", e);
            return HttpResponse.failure(ResultCode.STOCKPICKING_QUERY_ERROR);
        }
    }

    @Override
    public HttpResponse stockPickingForPurchase(StockPickingRequest stockPickingRequest) {
        try {
            stockPickingRequest.setWithPage(Consts.QUERY_ALL);
            List<StockPicking> stockPickingList = stockPickingDao.select(stockPickingRequest);
            Set<String> stockPickingIds = new HashSet<>();
            for (int i = 0; i < stockPickingList.size(); i++) {
                stockPickingIds.add(stockPickingList.get(i).getStockPickingId());
            }
            if (stockPickingIds.size() > 0) {
                PurchasePickingRequest purchasePickingRequest = new PurchasePickingRequest();
                purchasePickingRequest.setIds(stockPickingIds);
                purchasePickingRequest.setSrcOrderId(stockPickingRequest.getSrcOrderId());
                List<PurchasePickingAmountResponse> purchasePickingAmountResponseList = stockPickingGoodsDao.countPurchasePickingAmount(purchasePickingRequest);
                if (purchasePickingAmountResponseList == null) {
                    return HttpResponse.success(new ArrayList<>());
                }
                return HttpResponse.success(purchasePickingAmountResponseList);
            } else {
                return HttpResponse.success(new ArrayList<>());
            }
        } catch (Exception e) {
            LOGGER.error("获取采购移库信息失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_SYSTEM_ERROR);
        }
    }

    @Override
    public StockPicking selectByStockPickingId(String stockPickingId) throws StockPickingException {
        StockPicking stockPicking = stockPickingDao.selectByStockPickingId(stockPickingId);
        Map<String, PurchaseOrderDetail> detailMap = new HashMap<>();
        if (stockPicking == null) {
            LOGGER.error("导出入库/退货单--查询入库/退货单失败!");
            throw new StockPickingException("导出入库/退货单--查询入库/退货单失败!");
        }
        List<StockPickingGoods> stockPickingGoodsList = stockPickingGoodsDao.selectByStockPickingId(stockPickingId);
        if (stockPickingGoodsList == null) {
            LOGGER.error("导出入库/退货单--查询入库/退货单失败!");
            throw new StockPickingException("导出入库/退货单--查询入库/退货单失败!");
        }
        List<PurchaseOrderDetail> purchaseOrderDetailsList = purchaseOrderDetailDao.selectDetailForStockIn(stockPicking.getSrcOrderId());
        for (PurchaseOrderDetail purchaseDetail : purchaseOrderDetailsList) {
            detailMap.put(purchaseDetail.getGoodsId(), purchaseDetail);
        }

        for (StockPickingGoods stockPickingGoods : stockPickingGoodsList) {
            stockPickingGoods.setTotalPrice(stockPickingGoods.getPickingAmount() * stockPickingGoods.getPickingPrice());
            if (stockPickingGoods.getPresentAmount() != null) {
                stockPickingGoods.setTotalPresentPrice(stockPickingGoods.getPresentAmount() * stockPickingGoods.getPickingPrice());
            }
            if (detailMap.get(stockPickingGoods.getGoodsId()) != null) {
                stockPickingGoods.setStockInAmount(detailMap.get(stockPickingGoods.getGoodsId()).getCompleteStockInAmount());
            }
        }
        stockPicking.setStockPickingGoodsList(stockPickingGoodsList);
        return stockPicking;
    }

    @Override
    public boolean insertStockPickingGoodsList(String stockPickingId, List<StockPickingGoods> stockPickingGoodsList) throws StockPickingException {
        int pickingGoodsRow;
        stockPickingGoodsList = removeEmpty(stockPickingGoodsList);
        for (StockPickingGoods stockPickingGoods : stockPickingGoodsList) {
            String pickingGoodsId = IdUtil.uuid();
            stockPickingGoods.setStockPickingId(stockPickingId);
            stockPickingGoods.setPickingGoodsId(pickingGoodsId);
            pickingGoodsRow = stockPickingGoodsDao.insertSelective(stockPickingGoods);
            if (pickingGoodsRow <= 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<StockPicking> exportStockInAndOut(StockPickingRequest stockPickingRequest) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<StockPicking> stockPickingList = new ArrayList<StockPicking>();
        try {
            stockPickingList = stockPickingDao.selectStockInAndOut(stockPickingRequest);
            for (StockPicking stockPicking : stockPickingList) {
                //非空验证
                if (!(stockPicking.getPickingAmount() > Consts.PARAMETER_INITIALIZE_LONG)) {
                    stockPicking.setPickingAmount(0L);
                }
                if (StringUtils.isEmpty(stockPicking.getExportWarehouse())) {
                    stockPicking.setExportWarehouse("");
                }
                if (StringUtils.isEmpty(stockPicking.getOperatorName())) {
                    stockPicking.setOperatorName("");
                }
                if (StringUtils.isEmpty(stockPicking.getAuditorName())) {
                    stockPicking.setAuditorName("");
                }
                if (StringUtils.isEmpty(stockPicking.getExportOrderStatus())) {
                    stockPicking.setExportOrderStatus("");
                }
                //非空验证
                if (stockPicking.getStockPickingType() == Consts.STOCK_PICKING_TYPE_STOCK_IN) {
                    stockPicking.setExportReason(MapConsts.getStockIncomeReason().get(stockPicking.getIncomeReason()));
                    stockPicking.setExportWarehouse(stockPicking.getToWarehouseName());
                } else if (stockPicking.getStockPickingType() == Consts.STOCK_PICKING_TYPE_STOCK_OUT) {
                    stockPicking.setExportReason(MapConsts.getStockOutReason().get(stockPicking.getOutReason()));
                    stockPicking.setExportWarehouse(stockPicking.getSrcWarehouseName());
                }
                if (stockPicking.getAuditorTime() != null) {
                    stockPicking.setExportconformTime(sdf.format(stockPicking.getAuditorTime()).toString());//审核时间
                } else {
                    stockPicking.setExportconformTime("");
                }
                if (stockPicking.getCreateTime() != null) {
                    stockPicking.setExportOrderCreateTime(sdf.format(stockPicking.getCreateTime()).toString());//制单时间
                } else {
                    stockPicking.setExportOrderCreateTime("");
                }
                if (stockPicking.getUpdateTime() != null) {
                    stockPicking.setExportOrderTime(sdf.format(stockPicking.getUpdateTime()).toString());//单据日期
                } else {
                    stockPicking.setExportOrderTime("");
                }
                stockPicking.setOrderType(MapConsts.getOrderStatus().get(stockPicking.getStockPickingType()));
                stockPicking.setExportOrderStatus(MapConsts.getStockOrderStatus().get(stockPicking.getStockPickingStatus()));
            }
            return stockPickingList;
        } catch (Exception e) {
            LOGGER.error("查询商品列表失败!", e);
            return stockPickingList;
        }
    }

    @Override
    public List<StockPicking> exportRequision(StockPickingRequest stockPickingRequest) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<StockPicking> stockPickingList = new ArrayList<StockPicking>();
        try {
            stockPickingList = stockPickingDao.selectStockInAndOut(stockPickingRequest);
            for (StockPicking stockPicking : stockPickingList) {
                //非空验证
                if (stockPicking.getPickingAmount() == null) {
                    stockPicking.setPickingAmount(0L);
                }
                if (!(stockPicking.getPickingAmount() > Consts.PARAMETER_INITIALIZE_LONG)) {
                    stockPicking.setPickingAmount(0L);
                }
                if (StringUtils.isEmpty(stockPicking.getExportWarehouse())) {
                    stockPicking.setExportWarehouse("");
                }
                if (StringUtils.isEmpty(stockPicking.getOperatorName())) {
                    stockPicking.setOperatorName("");
                }
                if (StringUtils.isEmpty(stockPicking.getAuditorName())) {
                    stockPicking.setAuditorName("");
                }
                if (StringUtils.isEmpty(stockPicking.getExportOrderStatus())) {
                    stockPicking.setExportOrderStatus("");
                }
                if (StringUtils.isEmpty(stockPicking.getSrcWarehouseName())) {
                    stockPicking.setSrcWarehouseName("");
                }
                if (StringUtils.isEmpty(stockPicking.getToWarehouseName())) {
                    stockPicking.setToWarehouseName("");
                }

                //非空验证
                if (stockPicking.getStockPickingType() == Consts.STOCK_PICKING_TYPE_STOCK_IN) {
                    stockPicking.setExportReason(MapConsts.getStockIncomeReason().get(stockPicking.getIncomeReason()));
                    stockPicking.setExportWarehouse(stockPicking.getToWarehouseName());
                } else if (stockPicking.getStockPickingType() == Consts.STOCK_PICKING_TYPE_STOCK_OUT) {
                    stockPicking.setExportReason(MapConsts.getStockOutReason().get(stockPicking.getOutReason()));
                    stockPicking.setExportWarehouse(stockPicking.getSrcWarehouseName());
                }
                stockPicking.setExportOrderStatus(MapConsts.getOrderStatus().get(stockPicking.getStockPickingStatus()));
            }
            return stockPickingList;
        } catch (Exception e) {
            LOGGER.error("查询商品列表失败!", e);
            return stockPickingList;
        }
    }

    /**
     * 去掉goodsId为空的数据
     *
     * @param stockPickingGoodsList 商品列表
     * @return
     */
    private List<StockPickingGoods> removeEmpty(List<StockPickingGoods> stockPickingGoodsList) {
        for (int i = stockPickingGoodsList.size() - 1; i >= 0; i--) {
            if (StringUtils.isEmpty(stockPickingGoodsList.get(i).getGoodsId())) {
                stockPickingGoodsList.remove(i);
            }
        }
        return stockPickingGoodsList;
    }


    @Override
    public List<StockPickingGoods> exportStockInAndOutGoods(String stockPickingId, Integer type) {
        StockPicking old = stockPickingDao.selectByStockPickingId(stockPickingId);
        if (old == null) {
            LOGGER.error("未查询到其他出入库对象。");
            return null;
        }
        Map<String, Long> map = new HashMap<>();
        //查詢庫存
        StockQuantQueryRequest query = new StockQuantQueryRequest();
        query.setBrandId(old.getBrandId());
        if (!StringUtils.isEmpty(old.getStoreId())) {
            query.setStoreId(old.getStoreId());
        }
        String warehouseId = type == Consts.STOCK_IN_ORDER ? old.getToWarehouseId() : old.getSrcWarehouseId();
        query.setWarehouseId(warehouseId);
        List<StockQuantResult> stockQuantList = stockQuantDao.queryDetail(query);
        for (StockQuantResult result : stockQuantList) {
            map.put(result.getGoodsId(), result.getStockNum());
        }

        List<StockPickingGoods> pickingGoods = stockPickingGoodsDao.selectByStockPickingId(stockPickingId);
        for (StockPickingGoods goods : pickingGoods) {
            goods.setTotalPrice(goods.getPickingAmount() * goods.getPickingPrice());
            goods.setQuantNum(map.get(goods.getGoodsId()));
        }
        return pickingGoods;
    }

    @Override
    public List<StockPickingGoods> exportRequisitionUpdate(StockPickingRequest stockPickingRequest) {
        StockPickingGoodsRequest stockPickingGoodsRequest = new StockPickingGoodsRequest();
        stockPickingGoodsRequest.setStockPickingId(stockPickingRequest.getStockPickingId());
        StockQuantQueryRequest stockQuant = new StockQuantQueryRequest();
        Map<String, Long> stockQuantmap = new HashMap<String, Long>();
        List<StockQuant> quantList = new ArrayList<StockQuant>();


        stockQuant.setBrandId(stockPickingRequest.getBrandId());
        stockQuant.setStoreId(stockPickingRequest.getStoreId());
        stockQuant.setWarehouseId(stockPickingRequest.getSrcWarehouseId());

        List<StockPickingGoods> goodsList = new ArrayList<StockPickingGoods>();
        try {
            goodsList = stockPickingGoodsDao.select(stockPickingGoodsRequest);
            quantList = stockQuantService.selectWithParam(stockQuant).getList();
            for (StockQuant sq : quantList) {
                stockQuantmap.put(sq.getGoodsId(), sq.getStockNum());
            }
            for (StockPickingGoods stockgoods : goodsList) {
                stockgoods.setQuantNum(stockQuantmap.get(stockgoods.getGoodsId()));
                stockgoods.setTotalPrice(stockgoods.getPickingAmount() * stockgoods.getPickingPrice());
            }
            return goodsList;
        } catch (Exception e) {
            LOGGER.error("查询商品列表失败!", e);
            return goodsList;
        }
    }

    @Override
    public int cancelStockPicking(String stockPickingId) {
        StockPicking modifyStockIn = new StockPicking();
        modifyStockIn.setStockPickingId(stockPickingId);
        modifyStockIn.setStockPickingStatus(Consts.ORDER_STATUS_SAVED);
        return stockPickingDao.updateByStockPickingId(modifyStockIn);
    }

    @Override
    public HttpResponse commit(String stockPickingId) {
        try {
            StockPicking old = stockPickingDao.selectByStockPickingId(stockPickingId);
            if (old == null) {
                return HttpResponse.failure(ResultCode.STOCK_PICKING_COMMIT_ERROR);
            }
            StockPicking stockPicking = new StockPicking();
            stockPicking.setStockPickingId(stockPickingId);
            stockPicking.setStockPickingStatus(Consts.ORDER_STATUS_COMMITED);
            int row = stockPickingDao.updateByStockPickingId(stockPicking);
            if (row == 0) {
                return HttpResponse.failure(ResultCode.STOCK_PICKING_COMMIT_ERROR);
            }
            ProcessInstanceCreateRequest create = new ProcessInstanceCreateRequest();
            if (old.getStockPickingType() == Consts.STOCK_PICKING_TYPE_STOCK_INTERVAL) {
                create.setProcessType(Consts.PROCESS_TYPE_STOCK_REQUISITION);
            } else if (old.getStockPickingType() == Consts.STOCK_PICKING_TYPE_STOCK_IN || old.getStockPickingType() == Consts.STOCK_PICKING_TYPE_STOCK_OUT) {
                create.setProcessType(Consts.PROCESS_TYPE_STOCK_IN_AND_OUT);
            }
            create.setBrandId(old.getBrandId());
            create.setBusinessId(stockPickingId);
            create.setPrice(old.getStockPickingPrice());
            HttpResponse response = processInstanceService.create(create, old.getOperatorId());
            if (!StringUtils.equals(response.getCode(), "0")) {
                throw new ProcessInstanceException("提审失败!");
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("提审失败", e);
            return HttpResponse.failure(ResultCode.STOCK_PICKING_COMMIT_ERROR);
        }
    }

    @Override
    public HttpResponse delete(String stockPickingId) {
        try {
            int row = stockPickingDao.deleteByStockPickingId(stockPickingId);
            if (row == 0) {
                return HttpResponse.failure(ResultCode.STOCK_PICKING_DELETE_ERROR);
            }
            stockPickingGoodsDao.deleteByPickingId(stockPickingId);
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("删除单据失败!", e);
            return HttpResponse.failure(ResultCode.STOCK_PICKING_DELETE_ERROR);
        }
    }
}

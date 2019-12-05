package com.hanyun.scm.api.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.IdGenerateType;
import com.hanyun.scm.api.consts.MapConsts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.StockQuantDao;
import com.hanyun.scm.api.dao.StockSpillLossOrderDao;
import com.hanyun.scm.api.dao.StockSpillLossOrderDetailDao;
import com.hanyun.scm.api.domain.StockQuant;
import com.hanyun.scm.api.domain.StockSpillLossOrder;
import com.hanyun.scm.api.domain.StockSpillLossOrderDetail;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceCreateRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceModifyRequest;
import com.hanyun.scm.api.domain.request.stock.StockQuantQueryRequest;
import com.hanyun.scm.api.domain.request.stock.StockSpillLossOrderRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.exception.ProcessInstanceException;
import com.hanyun.scm.api.service.*;
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
 * 盘点差异单service
 * Date: 16/6/22
 * Time: 下午1:51
 *
 * @author tianye@hanyun.com
 */
@Service
public class StockSpillLossOrderServiceImpl implements StockSpillLossOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockSpillLossOrderServiceImpl.class);

    @Resource
    private StockSpillLossOrderDao stockSpillLossOrderDao;

    @Resource
    private StockSpillLossOrderDetailDao stockSpillLossOrderDetailDao;

    @Resource
    private StockQuantDao stockQuantDao;

    @Resource
    private IdGenerateSeqService idGenerateSeqService;

    @Resource
    private StockQuantService stockQuantService;

    @Resource
    private ProcessInstanceService processInstanceService;

    @Resource
    private ProcessDefinitionService processDefinitionService;

    /***
     * 查询差异单浏览界面
     */

    @Override
    public HttpResponse select(StockSpillLossOrderRequest stockSpillLossOrderRequest) {
        try {
            int count = stockSpillLossOrderDao.countAll(stockSpillLossOrderRequest);
            stockSpillLossOrderRequest.setCount(count);
            List<StockSpillLossOrder> orderList = stockSpillLossOrderDao.select(stockSpillLossOrderRequest);
            if (!StringUtils.isEmpty(stockSpillLossOrderRequest.getUserId())) {
                for (StockSpillLossOrder stockSpillLossOrder : orderList) {
                    stockSpillLossOrder.setAuditStatus(processInstanceService.queryAuditor(stockSpillLossOrder.getStockVarianceId(), stockSpillLossOrderRequest.getUserId()));
                    List<String> auditors = processInstanceService.queryAuditors(stockSpillLossOrder.getStockVarianceId());
                    auditors.add(stockSpillLossOrder.getOperatorId());
                    stockSpillLossOrder.setHistoryStatus(auditors.size() > 1 && auditors.contains(stockSpillLossOrderRequest.getUserId()));
                }
            }
            BaseResponse response = new BaseResponse(count, orderList);
            return HttpResponse.success(response);
        } catch (Exception e) {
            LOGGER.error("盘点差异查询失败", e);
            return HttpResponse.failure(ResultCode.SPILL_LOSS_QUERY_ERROR);
        }
    }

    @Override
    public HttpResponse create(StockSpillLossOrder stockSpillLossOrder) {
        List<StockSpillLossOrderDetail> detailList = stockSpillLossOrder.getDetailList();
        for (int i = detailList.size() - 1; i >= 0; i--) {
            if (detailList.get(i) == null || StringUtils.isEmpty(detailList.get(i).getGoodsId())) {
                detailList.remove(i);
            }
        }
        String brandId;
        String stockVarianceId = stockSpillLossOrder.getStockVarianceId();
        String stockVarianceDocumentId = stockSpillLossOrder.getStockVarianceDocumentId();
        if (StringUtils.isEmpty(stockVarianceId)) {//如果没有单号
            brandId = stockSpillLossOrder.getBrandId();
            stockVarianceId = IdUtil.uuid();
            stockSpillLossOrder.setStockVarianceId(stockVarianceId);
            int rows = 0;
            try {
                stockVarianceDocumentId = idGenerateSeqService.generateId(IdGenerateType.BS);
                stockSpillLossOrder.setStockVarianceDocumentId(stockVarianceDocumentId);
                stockSpillLossOrder.setStockVarianceId(stockVarianceId);
                rows = stockSpillLossOrderDao.insertSelective(stockSpillLossOrder);
                if (rows == 0) {
                    return HttpResponse.failure(ResultCode.SPILL_LOSS_INSERT_ERROR);
                }
            } catch (Exception e) {
                return HttpResponse.failure(ResultCode.SPILL_LOSS_INSERT_ERROR);
            }
            for (StockSpillLossOrderDetail detail : detailList) {
                rows = insertGoods(stockSpillLossOrder, detail);
                if (rows == 0) {
                    return HttpResponse.failure(ResultCode.SPILL_LOSS_DETAIL_INSERT_ERROR);
                }
            }
        } else {//如果有单号
            StockSpillLossOrder old = stockSpillLossOrderDao.selectByVarianceId(stockVarianceId);
            if (old == null) {
                return HttpResponse.failure(ResultCode.SPILL_LOSS_UPDATE_ERROR);
            }
            brandId = old.getBrandId();
            StockSpillLossOrderDetail stockSpillLossOrderDetail = new StockSpillLossOrderDetail();
            stockSpillLossOrderDao.modifyOrder(stockSpillLossOrder);                                    //修改单据信息
            stockSpillLossOrderDetail.setStockVarianceId(stockVarianceId);
            List<StockSpillLossOrderDetail> dbList = new ArrayList<StockSpillLossOrderDetail>();
            try {
                dbList = stockSpillLossOrderDetailDao.select(stockSpillLossOrderDetail);//找到指定商品
            } catch (Exception e) {
                LOGGER.error("查询商品详单失败!", e);
                return HttpResponse.failure(ResultCode.SPILL_LOSS_DETAIL_QUERY_ERROR);
            }
            for (StockSpillLossOrderDetail obj : detailList) {  //插入明细信息
                boolean insertFlag = true;
                String goodsId = obj.getGoodsId();
                for (StockSpillLossOrderDetail Goods : dbList) {
                    String goodsIdDB = Goods.getGoodsId();
                    if (goodsIdDB.equals(goodsId)) {//相同则更新
                        int rows = updateGoods(obj, stockSpillLossOrder);
                        if (rows == Consts.DB_WITHOUT_HANDLE) {
                            return HttpResponse.failure(ResultCode.SPILL_LOSS_DETAIL_UPDATE_ERROR);
                        }
                        insertFlag = false;
                        break;
                    }
                }
                if (insertFlag) {
                    int rows = insertGoods(stockSpillLossOrder, obj);
                    if (rows == Consts.DB_WITHOUT_HANDLE) {
                        return HttpResponse.failure(ResultCode.SPILL_LOSS_DETAIL_INSERT_ERROR);
                    }
                }
            }
            for (StockSpillLossOrderDetail Goods : dbList) {//判断是否需要删除
                String goodsIdDB = Goods.getGoodsId();
                boolean deleteFlag = true;
                for (StockSpillLossOrderDetail obj : detailList) {  //插入明细信息
                    String goodsId = obj.getGoodsId();
                    if (goodsIdDB.equals(goodsId)) {
                        deleteFlag = false;
                    }
                }
                if (deleteFlag) {
                    try {
                        int rows = stockSpillLossOrderDetailDao.deleteByPrimaryKey(goodsIdDB, stockVarianceId);
                        if (rows == Consts.DB_WITHOUT_HANDLE) {
                            return HttpResponse.failure(ResultCode.SPILL_LOSS_DETAIL_DELETE_ERROR);
                        }
                    } catch (Exception e) {
                        LOGGER.error("删除商品失败!", e);
                        return HttpResponse.failure(ResultCode.SPILL_LOSS_DETAIL_DELETE_ERROR);
                    }
                }
            }

        }
        boolean existStatus = processDefinitionService.exist(brandId, Consts.PROCESS_TYPE_STOCK_VARIANCE);
        if (existStatus) {
            return HttpResponse.success(new String[]{stockVarianceId, stockVarianceDocumentId, "exist"});
        }
        return HttpResponse.success(new String[]{stockVarianceId, stockVarianceDocumentId});
    }

    public int insertGoods(StockSpillLossOrder stockSpillLossOrder, StockSpillLossOrderDetail detail) {
        String stockVarianceDetailId = IdUtil.uuid();
        detail.setStockVarianceDetailId(stockVarianceDetailId);
        detail.setStockVarianceId(stockSpillLossOrder.getStockVarianceId());
        detail.setBrandId(stockSpillLossOrder.getBrandId());
        detail.setStoreId(stockSpillLossOrder.getStoreId());
        int rows = Consts.PARAMETER_INITIALIZE_INT;
        try {
            rows = stockSpillLossOrderDetailDao.insertSelective(detail);
            return rows;
        } catch (Exception e) {
            LOGGER.error("插入商品详单失败!", e);
            return rows;
        }

    }

    public int updateGoods(StockSpillLossOrderDetail detail, StockSpillLossOrder stockSpillLossOrder) {

        detail.setStockVarianceId(stockSpillLossOrder.getStockVarianceId());
        int rows = Consts.PARAMETER_INITIALIZE_INT;
        try {
            rows = stockSpillLossOrderDetailDao.updateByPrimaryKey(detail);
            return rows;
        } catch (Exception e) {
            return rows;
        }
    }

    @Override
    public HttpResponse modifyOrder(StockSpillLossOrder stockSpillLossOrder) {
        int rows = stockSpillLossOrderDao.modifyOrder(stockSpillLossOrder);

        if (rows == Consts.PARAMETER_INITIALIZE_INT) {
            return HttpResponse.failure(ResultCode.SPILL_LOSS_UPDATE_ERROR);
        }
        return HttpResponse.success();
    }

    @Override
    public HttpResponse auditOrder(StockSpillLossOrder stockSpillLossOrder) {
        try {
            StockSpillLossOrder old = stockSpillLossOrderDao.selectByVarianceId(stockSpillLossOrder.getStockVarianceId());
            if (old == null) {
                return HttpResponse.failure(ResultCode.SPILL_LOSS_CONFIRM_ERROR);
            }
            StockSpillLossOrder modify = new StockSpillLossOrder();
            modify.setStockVarianceId(stockSpillLossOrder.getStockVarianceId());
            if (stockSpillLossOrder.getStockVarianceStatus() != null && stockSpillLossOrder.getStockVarianceStatus() == Consts.ORDER_STATUS_CONFIRMED) {
                modify.setStockVarianceStatus(Consts.ORDER_STATUS_CONFIRMED);
            } else {
                if (!stockSpillLossOrder.getAuditStatus()) {
                    modify.setStockVarianceStatus(Consts.ORDER_STATUS_SAVED);
                    int row = stockSpillLossOrderDao.modifyOrder(modify);
                    if (row == 0) {
                        return HttpResponse.failure(ResultCode.SPILL_LOSS_CONFIRM_ERROR);
                    }
                    processInstanceService.delete(old.getStockVarianceId());
                    return HttpResponse.success();
                }
                ProcessInstanceModifyRequest processInstanceModifyRequest = new ProcessInstanceModifyRequest();
                processInstanceModifyRequest.setBusinessId(stockSpillLossOrder.getStockVarianceId());
                processInstanceModifyRequest.setUserId(stockSpillLossOrder.getUserId());
                processInstanceModifyRequest.setRemark("通过, 执行");
                HttpResponse response = processInstanceService.audit(processInstanceModifyRequest);
                if (!StringUtils.equals(response.getCode(), "0") || response.getData() == null) {
                    return HttpResponse.failure(ResultCode.SPILL_LOSS_CONFIRM_ERROR);
                }
                Integer instanceStatus = Integer.parseInt(response.getData().toString());
                if (instanceStatus != Consts.PROCESS_INSTANCE_STATUS_CONFIRMED) {
                    modify.setStockVarianceStatus(Consts.ORDER_STATUS_CONFIRMING);
                } else {
                    modify.setStockVarianceStatus(Consts.ORDER_STATUS_CONFIRMED);
                }
            }
            if (modify.getStockVarianceStatus() == Consts.ORDER_STATUS_CONFIRMED) {
                int rows = stockSpillLossOrderDao.aduitOrder(stockSpillLossOrder);
                if (rows == Consts.PARAMETER_INITIALIZE_INT) {
                    return HttpResponse.failure(ResultCode.SPILL_LOSS_UPDATE_ERROR);
                }
                stockQuantService.updateQuantBySpillAndLoss(old);
            }
            stockSpillLossOrderDao.modifyOrder(modify);
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("审核报损报溢单失败", e);
            return HttpResponse.failure(ResultCode.SPILL_LOSS_CONFIRM_ERROR);
        }
    }

    @Override
    public List<StockSpillLossOrder> exportStockSpillLossOrder(StockSpillLossOrderRequest stockSpillLossOrderRequest) {
        List<StockSpillLossOrder> orderList = new ArrayList<>();
        try {
            orderList = stockSpillLossOrderDao.select(stockSpillLossOrderRequest);
            for (StockSpillLossOrder stockSpillLossOrder : orderList) {
                stockSpillLossOrder.setExportType(MapConsts.getStockVarianceType().get(stockSpillLossOrder.getStockVarianceType()));
                if (stockSpillLossOrder.getStockVarianceType() == Consts.LOSS_ORDER) {//报损单
                    stockSpillLossOrder.setExportReason(MapConsts.getStockVarianceLossReason().get(stockSpillLossOrder.getLossReason()));
                } else if (stockSpillLossOrder.getStockVarianceType() == Consts.SPILL_ORDER) {//报溢单
                    stockSpillLossOrder.setExportReason(MapConsts.getStockVarianceSpillReason().get(stockSpillLossOrder.getSpillOverReason()));
                }
                stockSpillLossOrder.setExportStatus(MapConsts.getOrderStatus().get(stockSpillLossOrder.getStockVarianceStatus()));
            }
            return orderList;
        } catch (Exception e) {
            LOGGER.error("导出报损报溢单失败", e);
            return orderList;
        }

    }

    @Override
    public List<StockSpillLossOrderDetail> exportSpillAndLossUpdate(StockSpillLossOrderRequest stockSpillLossOrderRequest) {
        List<StockSpillLossOrderDetail> detailList = new ArrayList<>();
        StockSpillLossOrderDetail stockSpillLossOrderDetail = new StockSpillLossOrderDetail();
        Map<String, Long> stockQuantmap = new HashMap<>();
        StockQuantQueryRequest stockQuant = new StockQuantQueryRequest();
        List<StockQuant> quantList;

        stockQuant.setBrandId(stockSpillLossOrderRequest.getBrandId());
        stockQuant.setStoreId(stockSpillLossOrderRequest.getStoreId());
        stockQuant.setWarehouseId(stockSpillLossOrderRequest.getWarehouseId());
        stockSpillLossOrderDetail.setStockVarianceId(stockSpillLossOrderRequest.getStockVarianceId());

        try {
            quantList = stockQuantService.selectWithParam(stockQuant).getList();
            for (StockQuant sq : quantList) {
                stockQuantmap.put(sq.getGoodsId(), sq.getStockNum());
            }
            detailList = stockSpillLossOrderDetailDao.select(stockSpillLossOrderDetail);
            for (StockSpillLossOrderDetail detail : detailList) {
                detail.setSystemStock(stockQuantmap.get(detail.getGoodsId()));//系统库存
                detail.setSystemPrice(detail.getSystemStock() * detail.getUnitPrice());//系统总金额
                detail.setDifferencePrice(detail.getUnitPrice() * detail.getVarianceStock());//差异金额
            }
            return detailList;
        } catch (Exception e) {
            LOGGER.error("导出报损报溢详单失败", e);
            return detailList;
        }

    }

    @Override
    public HttpResponse commit(String spillLossOrderId) {
        try {
            StockSpillLossOrder old = stockSpillLossOrderDao.selectByVarianceId(spillLossOrderId);
            if (old == null) {
                return HttpResponse.failure(ResultCode.SPILL_LOSS_COMMIT_ERROR);
            }
            StockSpillLossOrder modify = new StockSpillLossOrder();
            modify.setStockVarianceId(spillLossOrderId);
            modify.setStockVarianceStatus(Consts.ORDER_STATUS_COMMITED);
            int modifyRow = stockSpillLossOrderDao.modifyOrder(modify);
            if (modifyRow == 0) {
                return HttpResponse.failure(ResultCode.SPILL_LOSS_COMMIT_ERROR);
            }
            ProcessInstanceCreateRequest create = new ProcessInstanceCreateRequest();
            create.setProcessType(Consts.PROCESS_TYPE_STOCK_VARIANCE);
            create.setBrandId(old.getBrandId());
            create.setBusinessId(spillLossOrderId);
            create.setPrice(old.getDifferencePrice());
            HttpResponse response = processInstanceService.create(create, old.getOperatorId());
            if (!StringUtils.equals(response.getCode(), "0")) {
                throw new ProcessInstanceException("提审失败!");
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("提审失败!", e);
            return HttpResponse.failure(ResultCode.SPILL_LOSS_COMMIT_ERROR);
        }
    }
}

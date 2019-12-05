package com.hanyun.scm.api.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.MapConsts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.StockCheckDifferenceDao;
import com.hanyun.scm.api.dao.StockCheckDifferenceDetailDao;
import com.hanyun.scm.api.domain.*;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceCreateRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceModifyRequest;
import com.hanyun.scm.api.domain.request.stock.StockCheckDifferenceRequest;
import com.hanyun.scm.api.domain.request.stock.StockQuantQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.exception.ProcessInstanceException;
import com.hanyun.scm.api.exception.StockCheckDifferenceException;
import com.hanyun.scm.api.service.*;
import com.hanyun.scm.api.utils.ActivitiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
public class StockCheckDifferenceServiceImpl implements StockCheckDifferenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockCheckDifferenceServiceImpl.class);

    @Resource
    private StockCheckDifferenceDao stockCheckDifferenceDao;

    @Resource
    private StockCheckDifferenceDetailDao stockCheckDifferenceDetailDao;

    @Resource
    private StockSpillLossOrderService stockSpillLossOrderService;

    @Resource
    private ProcessInstanceService processInstanceService;

    @Resource
    private ProcessDefinitionService processDefinitionService;

    @Resource
    private StockQuantService stockQuantService;

    /***
     * 查询差异单浏览界面
     */

    @Override
    public HttpResponse select(StockCheckDifferenceRequest stockCheckDifferenceRequest) {
        try {
            int count = stockCheckDifferenceDao.countAll(stockCheckDifferenceRequest);
            stockCheckDifferenceRequest.setCount(count);
            List<StockCheckDifference> differenceList = stockCheckDifferenceDao.select(stockCheckDifferenceRequest);
            if (!StringUtils.isEmpty(stockCheckDifferenceRequest.getUserId())) {
                for (StockCheckDifference stockCheckDifference : differenceList) {
                    stockCheckDifference.setAuditStatus(processInstanceService.queryAuditor(stockCheckDifference.getStockCheckDifferenceId(), stockCheckDifferenceRequest.getUserId()));
                    List<String> auditors = processInstanceService.queryAuditors(stockCheckDifference.getStockCheckDifferenceId());
                    auditors.add(stockCheckDifference.getOperatorId());
                    stockCheckDifference.setHistoryStatus(auditors.size() > 1 && auditors.contains(stockCheckDifferenceRequest.getUserId()));
                }
            }
            BaseResponse response = new BaseResponse(count, differenceList);
            return HttpResponse.success(response);
        } catch (Exception e) {
            LOGGER.error("盘点差异详单查询失败", e);
            return HttpResponse.failure(ResultCode.STOCK_DIFFERENCE_QUERY_ERROR);
        }


    }

    /*****
     * 保存差异单数据
     */
    @Override
    public HttpResponse modifyDifference(StockCheckDifference stockCheckDifference) {
        StockCheckDifference old = stockCheckDifferenceDao.selectByPrimaryKey(stockCheckDifference.getStockCheckDifferenceId());
        if (old == null) {
            return HttpResponse.failure(ResultCode.STOCK_CHECK_DIFFERENCE_MODIFY_ERROR);
        }
        /********更改库存*********/
        StockCheckDifferenceDetail stockCheckDifferenceDetail = new StockCheckDifferenceDetail();
        List<StockCheckDifferenceDetail> list = stockCheckDifference.getDetailList();
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i) == null || StringUtils.isEmpty(list.get(i).getGoodsId())) {
                list.remove(i);
            }
        }
        int diffRows;
        for (StockCheckDifferenceDetail detail : list) {
            stockCheckDifferenceDetail.setCheckDiffStock(detail.getChangeAfterNum());
            stockCheckDifferenceDetail.setStockCheckDifferenceDetailId(detail.getStockCheckDifferenceDetailId());
            stockCheckDifferenceDetail.setCheckDiffStatus(detail.getCheckDiffStatus());
            diffRows = stockCheckDifferenceDetailDao.update(stockCheckDifferenceDetail);//更改差异表显示库存
            if (diffRows == 0) {
                return HttpResponse.failure(ResultCode.STOCK_DIFFERENCE_UPDATE_ERROR);
            }
        }
        /********更改库存*********/
        int rows;
        try {
            rows = stockCheckDifferenceDao.update(stockCheckDifference);

        } catch (Exception e) {
            LOGGER.error("盘点差异修改失败", e);
            return HttpResponse.failure(ResultCode.STOCK_DIFFERENCE_UPDATE_ERROR);
        }
        if (rows == 0) {
            return HttpResponse.failure(ResultCode.STOCK_DIFFERENCE_UPDATE_ERROR);
        }
        boolean existStatus = processDefinitionService.exist(old.getBrandId(), Consts.PROCESS_TYPE_STOCK_CHECK_DIFFERENCE);
        if (existStatus) {
            return HttpResponse.success(new String[]{stockCheckDifference.getStockCheckDifferenceId(), stockCheckDifference.getStockCheckDifferenceDocumentId(), "exist"});
        }
        return HttpResponse.success(new String[]{stockCheckDifference.getStockCheckDifferenceId()});
    }

    @Override
    public HttpResponse commit(String stockCheckDifferenceId) {
        try {
            StockCheckDifference old = stockCheckDifferenceDao.selectByPrimaryKey(stockCheckDifferenceId);
            if (old == null) {
                LOGGER.error("差异单号:[" + stockCheckDifferenceId + "]不存在!");
                return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_DATA_NOT_FOUND);
            }
            StockCheckDifference stock = new StockCheckDifference();
            stock.setStockCheckDifferenceId(stockCheckDifferenceId);
            stock.setStockCheckDifferenceStatus(Consts.ORDER_STATUS_COMMITED);
            int modifyRow = stockCheckDifferenceDao.update(stock);
            if (modifyRow <= 0) {
                throw new StockCheckDifferenceException("更新差异单提审状态失败");
            }
            ProcessInstanceCreateRequest create = new ProcessInstanceCreateRequest();
            create.setProcessType(Consts.PROCESS_TYPE_STOCK_CHECK_DIFFERENCE);
            create.setBrandId(old.getBrandId());
            create.setBusinessId(stockCheckDifferenceId);
            ActivitiUtil.getInstance();
            HttpResponse response = processInstanceService.create(create, old.getOperatorId());
            if (!StringUtils.equals(response.getCode(), "0")) {
                throw new ProcessInstanceException("提审失败!");
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("盘点差异修改失败", e);
            return HttpResponse.failure(ResultCode.STOCK_CHECK_DIFFERENCE_COMMIT_ERROR);
        }
    }

    /****
     * 审核
     */
    @Override
    public HttpResponse auditDifference(StockCheckDifference stockCheckDifference) {
        try {
            StockCheckDifference old = stockCheckDifferenceDao.selectByPrimaryKey(stockCheckDifference.getStockCheckDifferenceId());
            if (old == null) {
                return HttpResponse.failure(ResultCode.STOCK_CHECK_DIFFERENCE_CONFIRM_ERROR);
            }
            if (stockCheckDifference.getStockCheckDifferenceStatus() != null && stockCheckDifference.getStockCheckDifferenceStatus() == Consts.ORDER_STATUS_CONFIRMED) {
                stockCheckDifference.setStockCheckDifferenceStatus(Consts.ORDER_STATUS_CONFIRMED);
            } else {
                if (!stockCheckDifference.getAuditStatus()) {
                    StockCheckDifference modify = new StockCheckDifference();
                    modify.setStockCheckDifferenceId(old.getStockCheckDifferenceId());
                    modify.setStockCheckDifferenceStatus(Consts.ORDER_STATUS_SAVED);
                    int row = stockCheckDifferenceDao.update(modify);
                    if (row == 0) {
                        return HttpResponse.failure(ResultCode.STOCK_CHECK_DIFFERENCE_CONFIRM_ERROR);
                    }
                    processInstanceService.delete(old.getStockCheckDifferenceId());
                    return HttpResponse.success();
                }
                ProcessInstanceModifyRequest processInstanceModifyRequest = new ProcessInstanceModifyRequest();
                processInstanceModifyRequest.setBusinessId(stockCheckDifference.getStockCheckDifferenceId());
                processInstanceModifyRequest.setUserId(stockCheckDifference.getUserId());
                processInstanceModifyRequest.setRemark("通过, 执行");
                HttpResponse response = processInstanceService.audit(processInstanceModifyRequest);
                if (!StringUtils.equals(response.getCode(), "0") || response.getData() == null) {
                    return HttpResponse.failure(ResultCode.STOCK_CHECK_DIFFERENCE_CONFIRM_ERROR);
                }
                Integer instanceStatus = Integer.parseInt(response.getData().toString());
                if (instanceStatus != Consts.PROCESS_INSTANCE_STATUS_CONFIRMED) {
                    stockCheckDifference.setStockCheckDifferenceStatus(Consts.ORDER_STATUS_CONFIRMING);
                } else {
                    stockCheckDifference.setStockCheckDifferenceStatus(Consts.ORDER_STATUS_CONFIRMED);
                }
            }
            if (stockCheckDifference.getStockCheckDifferenceStatus() == Consts.ORDER_STATUS_CONFIRMED) {
                StockCheckDifferenceDetail query = new StockCheckDifferenceDetail();
                query.setStockCheckDifferenceId(stockCheckDifference.getStockCheckDifferenceId());
                StockSpillLossOrder fullOrder = new StockSpillLossOrder();
                StockSpillLossOrder lossOrder = new StockSpillLossOrder();
                List<StockSpillLossOrderDetail> fullOrderDetailList = new ArrayList<>();
                List<StockSpillLossOrderDetail> lossOrderDetailList = new ArrayList<>();
                Long fullDifferentPrice = 0L;
                Long fullDifferentNum = 0L;
                Long lossDifferentPrice = 0L;
                Long lossDifferentNum = 0L;
                StockQuant stockQuant;
                List<StockCheckDifferenceDetail> stockCheckDifferenceDetailList = stockCheckDifferenceDetailDao.selectByTaskId(query);
                for (StockCheckDifferenceDetail stockCheckDifferenceDetail : stockCheckDifferenceDetailList) {
                    if (stockCheckDifferenceDetail.getCheckDiffStock() == null) {
                        stockCheckDifferenceDetail.setCheckDiffStock(stockCheckDifferenceDetail.getCheckStock());
                    }
                    if (stockCheckDifferenceDetail.getStockNum() > stockCheckDifferenceDetail.getCheckDiffStock()) {
                        StockSpillLossOrderDetail lossOrderDetail = JsonUtil.fromJson(JsonUtil.toJson(stockCheckDifferenceDetail), StockSpillLossOrderDetail.class);
                        lossOrderDetail.setVarianceStock(stockCheckDifferenceDetail.getStockNum() - stockCheckDifferenceDetail.getCheckDiffStock());
                        lossOrderDetail.setSystemStock(stockCheckDifferenceDetail.getStockNum());
                        lossOrderDetail.setStockNum(stockCheckDifferenceDetail.getStockNum());
                        lossOrderDetail.setFeature(stockCheckDifferenceDetail.getFeatures());
                        lossOrderDetailList.add(lossOrderDetail);
                        lossDifferentNum += lossOrderDetail.getVarianceStock();
                        lossDifferentPrice += lossOrderDetail.getVarianceStock() * lossOrderDetail.getUnitPrice();
                        stockCheckDifferenceDetail.setStockNum(stockCheckDifferenceDetail.getCheckDiffStock());
                        stockCheckDifferenceDetailDao.update(stockCheckDifferenceDetail);
                        stockQuant = new StockQuant();
                        stockQuant.setGoodsId(stockCheckDifferenceDetail.getGoodsId());
                        stockQuant.setWarehouseId(stockCheckDifferenceDetail.getWarehouseId());
                        stockQuant.setStockNum(stockCheckDifferenceDetail.getCheckDiffStock());
                        StockQuantQueryRequest stockQuantQueryRequest = new StockQuantQueryRequest();
                        stockQuantQueryRequest.setGoodsId(stockCheckDifferenceDetail.getGoodsId());
                        stockQuantQueryRequest.setWarehouseId(stockCheckDifferenceDetail.getWarehouseId());
                        List<StockQuant> oldStockQuant = stockQuantService.selectWithParam(stockQuantQueryRequest).getList();
                        if (oldStockQuant != null && oldStockQuant.size() > 0) {
                            stockQuant.setStockQuantId(oldStockQuant.get(0).getStockQuantId());
                        }
                        stockQuantService.updateEsByStockQuantId(stockQuant);
                    } else if (stockCheckDifferenceDetail.getStockNum() < stockCheckDifferenceDetail.getCheckDiffStock()) {
                        StockSpillLossOrderDetail fullOrderDetail = JsonUtil.fromJson(JsonUtil.toJson(stockCheckDifferenceDetail), StockSpillLossOrderDetail.class);
                        fullOrderDetail.setVarianceStock(stockCheckDifferenceDetail.getCheckDiffStock() - stockCheckDifferenceDetail.getStockNum());
                        fullOrderDetail.setStockNum(stockCheckDifferenceDetail.getStockNum());
                        fullOrderDetail.setSystemStock(stockCheckDifferenceDetail.getStockNum());
                        fullOrderDetail.setFeature(stockCheckDifferenceDetail.getFeatures());
                        fullOrderDetailList.add(fullOrderDetail);
                        fullDifferentNum += fullOrderDetail.getVarianceStock();
                        fullDifferentPrice += fullOrderDetail.getVarianceStock() * fullOrderDetail.getUnitPrice();
                        stockCheckDifferenceDetail.setStockNum(stockCheckDifferenceDetail.getCheckDiffStock());
                        stockCheckDifferenceDetailDao.update(stockCheckDifferenceDetail);
                        stockQuant = new StockQuant();
                        stockQuant.setGoodsId(stockCheckDifferenceDetail.getGoodsId());
                        stockQuant.setWarehouseId(stockCheckDifferenceDetail.getWarehouseId());
                        stockQuant.setStockNum(stockCheckDifferenceDetail.getCheckDiffStock());
                        StockQuantQueryRequest stockQuantQueryRequest = new StockQuantQueryRequest();
                        stockQuantQueryRequest.setGoodsId(stockCheckDifferenceDetail.getGoodsId());
                        stockQuantQueryRequest.setWarehouseId(stockCheckDifferenceDetail.getWarehouseId());
                        List<StockQuant> oldStockQuant = stockQuantService.selectWithParam(stockQuantQueryRequest).getList();
                        if (oldStockQuant != null && oldStockQuant.size() > 0) {
                            stockQuant.setStockQuantId(oldStockQuant.get(0).getStockQuantId());
                        }
                        stockQuantService.updateEsByStockQuantId(stockQuant);
                    }
                }
                if (lossOrderDetailList.size() > 0) {
                    lossOrder.setWarehouseId(old.getWarehouseId());
                    lossOrder.setWarehouseName(old.getWarehouseName());
                    lossOrder.setStockCheckDifferenceId(old.getStockCheckDifferenceId());
                    lossOrder.setStockCheckDifferenceDocumentId(old.getStockCheckDifferenceDocumentId());
                    lossOrder.setBrandId(old.getBrandId());
                    lossOrder.setStoreId(old.getStoreId());
                    lossOrder.setStockVarianceStatus(Consts.ORDER_STATUS_CONFIRMED);
                    lossOrder.setStockVarianceType(Consts.STOCK_VARIANCE_TYPE_LOSS);
                    lossOrder.setLossReason(Consts.SPILL_LOSS_OUT_STOCK);
                    lossOrder.setDifferenceNum(lossDifferentNum);
                    lossOrder.setDifferencePrice(lossDifferentPrice);
                    lossOrder.setOperatorId(old.getOperatorId());
                    lossOrder.setOperatorName(old.getOperatorName());
                    lossOrder.setDetailList(lossOrderDetailList);
                    lossOrder.setDifferenceCreateLoss(Consts.SPILL_DIFFERENCE_CREATE);
                    lossOrder.setValidStatus(Consts.VALID_STATUS_VALID);
                    lossOrder.setLossReason(1);
                    HttpResponse lossOrderResponse = stockSpillLossOrderService.create(lossOrder);
                    if (!StringUtils.equals(lossOrderResponse.getCode(), "0")) {
                        return HttpResponse.failure(ResultCode.SPILL_LOSS_INSERT_ERROR);
                    }
                }
                if (fullOrderDetailList.size() > 0) {
                    fullOrder.setWarehouseId(old.getWarehouseId());
                    fullOrder.setWarehouseName(old.getWarehouseName());
                    fullOrder.setStockCheckDifferenceId(old.getStockCheckDifferenceId());
                    fullOrder.setStockCheckDifferenceDocumentId(old.getStockCheckDifferenceDocumentId());
                    fullOrder.setBrandId(old.getBrandId());
                    fullOrder.setStoreId(old.getStoreId());
                    fullOrder.setStockVarianceStatus(Consts.ORDER_STATUS_CONFIRMED);
                    fullOrder.setStockVarianceType(Consts.STOCK_VARIANCE_TYPE_FULL);
                    fullOrder.setLossReason(Consts.SPILL_LOSS_IN_STOCK);
                    fullOrder.setDifferenceNum(fullDifferentNum);
                    fullOrder.setDifferencePrice(fullDifferentPrice);
                    fullOrder.setOperatorId(old.getOperatorId());
                    fullOrder.setOperatorName(old.getOperatorName());
                    fullOrder.setDetailList(fullOrderDetailList);
                    fullOrder.setDifferenceCreateLoss(Consts.SPILL_DIFFERENCE_CREATE);
                    fullOrder.setValidStatus(Consts.VALID_STATUS_VALID);
                    fullOrder.setSpillOverReason(1);
                    HttpResponse fullOrderResponse = stockSpillLossOrderService.create(fullOrder);
                    if (!StringUtils.equals(fullOrderResponse.getCode(), "0")) {
                        return HttpResponse.failure(ResultCode.SPILL_LOSS_INSERT_ERROR);
                    }
                }
                int auditRows = stockCheckDifferenceDao.audit(stockCheckDifference);
                if (auditRows == 0) {
                    return HttpResponse.failure(ResultCode.STOCK_DIFFERENCE_UPDATE_ERROR);
                }
            } else {
                stockCheckDifferenceDao.update(stockCheckDifference);
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("审核盘点差异单失败!", e);
            return HttpResponse.failure(ResultCode.STOCK_CHECK_DIFFERENCE_CONFIRM_ERROR);
        }
    }

    /***
     * 生成差异单
     * @param stockCheckDifference  参数
     * @return
     */
    @Override
    public HttpResponse createDifference(StockCheckDifference stockCheckDifference) {
        int rows;
        try {
            rows = stockCheckDifferenceDao.insert(stockCheckDifference);
            if (rows == 0) {
                return HttpResponse.failure(ResultCode.STOCK_DIFFERENCE_CREATE_ERROR);
            }
            rows = 0;
            List<StockCheckDifferenceDetail> listdetail = stockCheckDifference.getDetailList();//获取单据下的商品信息
            for (int i = listdetail.size() - 1; i >= 0; i--) {
                if (listdetail.get(i) == null || StringUtils.isEmpty(listdetail.get(i).getGoodsId())) {
                    listdetail.remove(i);
                }
            }
            for (StockCheckDifferenceDetail detail : listdetail) {
                if (detail.getStockNum() > detail.getCheckStock()) {//报损
                    detail.setCheckDiffStatus(1);
                } else if (detail.getStockNum() < detail.getCheckStock()) {
                    detail.setCheckDiffStatus(2);
                } else {
                    detail.setCheckDiffStatus(0);
                }
                rows = stockCheckDifferenceDetailDao.insertDetail(detail);//循环插入商品信息

            }
            if (rows == 0) {
                return HttpResponse.failure(ResultCode.STOCK_DIFFERENCE_CREATE_ERROR);
            }
        } catch (Exception e) {
            LOGGER.error("创建差异单失败", e);
            return HttpResponse.failure(ResultCode.STOCK_DIFFERENCE_DETAIL_CREATE_ERROR);
        }
        return HttpResponse.success();
    }

    @Override
    public List<StockCheckDifference> exportCheckDifference(StockCheckDifferenceRequest stockCheckDifferenceRequest) {
        List<StockCheckDifference> differencelist = new ArrayList<StockCheckDifference>();
        try {
            differencelist = stockCheckDifferenceDao.select(stockCheckDifferenceRequest);
            for (StockCheckDifference stockCheckDifference : differencelist) {
                stockCheckDifference.setExportStatus(MapConsts.getOrderStatus().get(stockCheckDifference.getStockCheckDifferenceStatus()));
                stockCheckDifference.setExporttype(MapConsts.getDifferenceTaskType().get(stockCheckDifference.getStockCheckTaskType()));
            }
            return differencelist;
        } catch (Exception e) {
            LOGGER.error("查询盘点差异单失败", e);
            return differencelist;
        }
    }

    @Override
    public HttpResponse createVariance(StockCheckDifference stockCheckDifference) {
        List<StockSpillLossOrder> list = stockCheckDifference.getVarianceList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) != null) {
                    try {
                        stockSpillLossOrderService.create(list.get(i));
                    } catch (Exception e) {
                        LOGGER.error("生成报损报溢单失败", e);
                        return HttpResponse.failure(ResultCode.SPILL_LOSS_DETAIL_INSERT_ERROR);
                    }
                }
            }
        }
        return HttpResponse.success();
    }

    @Override
    public List<StockCheckDifferenceDetail> exportDifferenceUpdate(StockCheckDifferenceRequest StockCheckDifferenceRequest) {
        StockCheckDifferenceDetail stockCheckDifferenceDetail = new StockCheckDifferenceDetail();
        stockCheckDifferenceDetail.setStockCheckDifferenceId(StockCheckDifferenceRequest.getStockCheckDifferenceId());
        List<StockCheckDifferenceDetail> detailList = new ArrayList<>();
        try {
            detailList = stockCheckDifferenceDetailDao.selectByTaskId(stockCheckDifferenceDetail);
            for (StockCheckDifferenceDetail detail : detailList) {
                detail.setSystemPrice(detail.getStockNum() * detail.getUnitPrice());
                detail.setStockNowPrice(detail.getCheckStock() * detail.getUnitPrice());
                detail.setChangePrice(detail.getCheckDiffStock() * detail.getUnitPrice());
                if (detail.getCheckDiffStatus() == 2) {//盘盈
                    detail.setSpillOrLoss("盘盈");
                } else if (detail.getCheckDiffStatus() == 1) {//盘亏
                    detail.setSpillOrLoss("盘亏");
                }
            }
        } catch (Exception e) {
            LOGGER.error("导出盘点差异单失败", e);
        }
        return detailList;
    }

    @Override
    public HttpResponse detail(String stockCheckDifferenceId) {
        try {
            StockCheckDifferenceDetail stockCheckDifferenceDetailRequest = new StockCheckDifferenceDetail();
            stockCheckDifferenceDetailRequest.setStockCheckDifferenceId(stockCheckDifferenceId);
            List<StockCheckDifferenceDetail> detailList = stockCheckDifferenceDetailDao.selectByTaskId(stockCheckDifferenceDetailRequest);
            int count = 0;
            BaseResponse response = new BaseResponse(count, detailList);
            return HttpResponse.success(response);
        } catch (Exception e) {
            LOGGER.error("查询盘点差异详情失败!", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
}

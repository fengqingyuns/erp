package com.hanyun.scm.api.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.hanyun.ground.util.date.DateFormatUtil;
import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.IdGenerateType;
import com.hanyun.scm.api.consts.MapConsts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.PurchaseOrderDao;
import com.hanyun.scm.api.dao.PurchaseOrderDetailDao;
import com.hanyun.scm.api.dao.StockPickingDao;
import com.hanyun.scm.api.dao.StockPickingGoodsDao;
import com.hanyun.scm.api.domain.PurchaseOrder;
import com.hanyun.scm.api.domain.PurchaseOrderDetail;
import com.hanyun.scm.api.domain.StockPicking;
import com.hanyun.scm.api.domain.StockPickingGoods;
import com.hanyun.scm.api.domain.request.payment.SynchronizedPurchaseOrderRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceCreateRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.PurchaseStockInConfirmRequest;
import com.hanyun.scm.api.domain.request.purchase.PurchaseStockInCreateRequest;
import com.hanyun.scm.api.domain.request.purchase.PurchaseStockInModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.PurchaseStockInQueryRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderQueryRequest;
import com.hanyun.scm.api.domain.request.stock.StockPickingGoodsRequest;
import com.hanyun.scm.api.domain.request.stock.StockPickingRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.exception.ProcessInstanceException;
import com.hanyun.scm.api.exception.PurchaseStockInException;
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
 * 采购入库单
 * Date: 2016/10/27
 * Time: 下午12:08
 *
 * @author tianye@hanyun.com
 */
@Service
public class PurchaseStockInServiceImpl implements PurchaseStockInService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseStockInServiceImpl.class);

    @Resource
    private StockPickingDao stockPickingDao;

    @Resource
    private StockPickingGoodsDao stockPickingGoodsDao;

    @Resource
    private PurchaseOrderDao purchaseOrderDao;

    @Resource
    private PurchaseOrderDetailDao purchaseOrderDetailDao;

    @Resource
    private StockQuantService stockQuantService;

    @Resource
    private IdGenerateSeqService idGenerateSeqService;

    @Resource
    private StockPickingService stockPickingService;

    @Resource
    private PaymentService paymentService;

    @Resource
    private ProcessInstanceService processInstanceService;

    @Resource
    private ProcessDefinitionService processDefinitionService;

    @Override
    public HttpResponse create(PurchaseStockInCreateRequest purchaseStockInCreateRequest) {
        //验证
        List<StockPickingGoods> detailList = purchaseStockInCreateRequest.getStockPickingGoodsList();
        if(detailList == null || detailList.size() == 0){
            return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_CREATE_GOODS_ERROR);
        }
        for(StockPickingGoods goods : detailList){
            if(StringUtils.isEmpty(goods.getGoodsId())){
                return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_CREATE_GOODS_ID_ERROR);
            }
            if(goods.getPickingAmount() == null || goods.getPickingAmount() == 0){
                return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_CREATE_GOODS_ERROR);
            }
        }
        StockPicking stockPicking = purchaseStockInCreateRequest.getStockPicking();
        String stockPickingId = IdUtil.uuid();
        stockPicking.setStockPickingType(Consts.STOCK_PICKING_TYPE_PURCHASE_STOCK_IN);
        stockPicking.setStockPickingId(stockPickingId);
        try {
            stockPicking.setStockPickingDocumentId(idGenerateSeqService.generateId(IdGenerateType.CR));
        } catch (Exception e) {
            LOGGER.error("生成采购入库单编号失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_CREATE_ERROR);
        }
        try {
            int row = stockPickingDao.insertSelective(stockPicking);
            if (row <= 0) {
                LOGGER.error("创建采购入库单失败!");
                return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_CREATE_ERROR);
            }
            Map<String, StockPickingGoods> stockPickingGoodsMap = new HashMap<>();
            boolean insertFlag = stockPickingService.insertStockPickingGoodsList(stockPicking.getStockPickingId(), detailList);
            if (!insertFlag) {
                throw new PurchaseStockInException("添加采购入库单商品失败!");
            }
            for (StockPickingGoods stockPickingGoods : detailList) {
                stockPickingGoods.setBrandId(purchaseStockInCreateRequest.getBrandId());
                stockPickingGoods.setStoreId(purchaseStockInCreateRequest.getStoreId());
                stockPickingGoodsMap.put(stockPickingGoods.getGoodsId(), stockPickingGoods);
            }
            String orderId = purchaseStockInCreateRequest.getSrcOrderId();
            if (!StringUtils.isEmpty(orderId)) {
                StockPickingGoods newStockPickingGoods;
                PurchaseOrderDetail query = new PurchaseOrderDetail();
                query.setOrderId(orderId);
                Long totalStockInAmount = 0L;
                List<PurchaseOrderDetail> purchaseOrderDetailList = purchaseOrderDetailDao.select(query);
                for (PurchaseOrderDetail purchaseOrderDetail : purchaseOrderDetailList) {
                    if (stockPickingGoodsMap.get(purchaseOrderDetail.getGoodsId()) != null) {
                        StockPickingGoods stockPickingGoods = stockPickingGoodsMap.get(purchaseOrderDetail.getGoodsId());
                        Long remainingAmount = purchaseOrderDetail.getPurchaseAmount() - purchaseOrderDetail.getStockInAmount();
                        Long pickingAmount = stockPickingGoods.getPickingAmount() != null ? stockPickingGoods.getPickingAmount() : 0; //采购数量
                        Long stockInAmount = pickingAmount > remainingAmount ?
                                purchaseOrderDetail.getPurchaseAmount() : (pickingAmount + purchaseOrderDetail.getStockInAmount());
                        Long sourcePurchaseStockInNum = stockInAmount - purchaseOrderDetail.getStockInAmount();
                        newStockPickingGoods = new StockPickingGoods();
                        newStockPickingGoods.setPickingGoodsId(stockPickingGoods.getPickingGoodsId());
                        newStockPickingGoods.setSourcePurchaseStockInAmount(sourcePurchaseStockInNum);
                        stockPickingGoodsDao.updateByPickingGoodsId(newStockPickingGoods);
                        PurchaseOrderDetail modifyRequest = new PurchaseOrderDetail();
                        modifyRequest.setOrderDetailId(purchaseOrderDetail.getOrderDetailId());
                        //更新实际入库数量
                        modifyRequest.setStockInAmount(pickingAmount + purchaseOrderDetail.getStockInAmount());
                        int orderDetailRow = purchaseOrderDetailDao.updateByPrimaryKeySelective(modifyRequest);
                        if (orderDetailRow <= 0) {
                            throw new PurchaseStockInException("采购入库-更新采购单失败!");
                        }
                        totalStockInAmount += pickingAmount + purchaseOrderDetail.getStockInAmount();
                    } else {
                        totalStockInAmount += purchaseOrderDetail.getStockInAmount();
                    }
                }
                PurchaseOrderModifyRequest newOrder = new PurchaseOrderModifyRequest();
                newOrder.setOrderId(orderId);
                newOrder.setStockInAmount(totalStockInAmount);
                int orderRow = purchaseOrderDao.updateByPrimaryKeySelective(newOrder);
                if (orderRow <= 0) {
                    throw new PurchaseStockInException("采购入库-更新采购单失败!");
                }
            }
            boolean existStatus = processDefinitionService.exist(stockPicking.getBrandId(), Consts.PROCESS_TYPE_PURCHASE_STOCK_IN);
            if (existStatus) {
                return HttpResponse.success(new String[]{stockPicking.getStockPickingId(), stockPicking.getStockPickingDocumentId(), "exist"});
            }
            return HttpResponse.success(new String[]{stockPicking.getStockPickingId(), stockPicking.getStockPickingDocumentId()});
        } catch (Exception e) {
            LOGGER.error("创建采购入库单失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_CREATE_ERROR);
        }

    }

    @Override
    public HttpResponse detail(String stockPickingId) {
        try {
            StockPicking stockPicking = stockPickingDao.selectByStockPickingId(stockPickingId);
            if (stockPicking == null) {
                LOGGER.error("该采购入库单不存在!");
                return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_DATA_NOT_FOUND);
            }
            StockPickingGoodsRequest stockPickingGoods = new StockPickingGoodsRequest();
            stockPickingGoods.setWithPage(Consts.QUERY_ALL);
            stockPickingGoods.setStockPickingId(stockPickingId);
            List<StockPickingGoods> pickingGoodsList = stockPickingGoodsDao.select(stockPickingGoods);
            if (pickingGoodsList != null) {
                stockPicking.setStockPickingGoodsList(pickingGoodsList);
            }
            return HttpResponse.success(stockPicking);
        } catch (Exception e) {
            LOGGER.error("查询入库单详情失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse select(PurchaseStockInQueryRequest purchaseStockInQueryRequest) {
        try {
            return HttpResponse.success(selectList(purchaseStockInQueryRequest, false));
        } catch (Exception e) {
            LOGGER.error("查询采购入库单列表失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_SYSTEM_ERROR);
        }
    }

    /*****
     * 查询当页入库单对应的订单--》查询各个订单入库数--》set进对应的对象
     *
     * @throws PurchaseStockInException
     */
    @Override
    public BaseResponse selectList(PurchaseStockInQueryRequest purchaseStockInQueryRequest, boolean excel) throws PurchaseStockInException {
        PurchaseOrderQueryRequest purchaseOrderQueryRequest = new PurchaseOrderQueryRequest();
        Map<String, Long> purchaseOrderMap = new HashMap<>();
        StockPickingRequest request = purchaseStockInQueryRequest.getStockPickingRequest();
        List<String> orderIds = new ArrayList<>();//此分页入库单关联的订单
        request.setStockPickingType(Consts.STOCK_PICKING_TYPE_PURCHASE_STOCK_IN);
        request.setPurchaseReturnStatus(purchaseStockInQueryRequest.getPurchaseReturnStatus());
        Integer count = stockPickingDao.countAll(request);
        request.setCount(count);
        List<StockPicking> stockPickingList = stockPickingDao.select(request);
        if (excel) {
            for (StockPicking stockPicking : stockPickingList) {
                orderIds.add(stockPicking.getSrcOrderId());
                stockPicking.setStockPickingStatusName(MapConsts.getOrderStatus().get(stockPicking.getStockPickingStatus()));
                stockPicking.setPaymentStatusName(MapConsts.getPaymentStatus().get(stockPicking.getPaymentStatus()));
            }
            purchaseOrderQueryRequest.setBrandId(purchaseStockInQueryRequest.getBrandId());
            purchaseOrderQueryRequest.setOrderIds(orderIds);
            List<PurchaseOrder> purchaseOrderList = purchaseOrderDao.queryForPurchaseStockIn(purchaseOrderQueryRequest);
            for (PurchaseOrder purchaseOrder : purchaseOrderList) {
                purchaseOrderMap.put(purchaseOrder.getOrderId(), purchaseOrder.getTotalAmount());
            }
            for (StockPicking stockPicking : stockPickingList) {
                if (purchaseOrderMap.get(stockPicking.getSrcOrderId()) != null) {
                    stockPicking.setPurchaseAmount(purchaseOrderMap.get(stockPicking.getSrcOrderId()));
                } else {
                    stockPicking.setPurchaseAmount(0L);
                }
            }
        } else if (!StringUtils.isEmpty(purchaseStockInQueryRequest.getUserId())) {
            for (StockPicking stockPicking : stockPickingList) {
                stockPicking.setAuditStatus(processInstanceService.queryAuditor(stockPicking.getStockPickingId(), purchaseStockInQueryRequest.getUserId()));
                List<String> auditors = processInstanceService.queryAuditors(stockPicking.getStockPickingId());
                auditors.add(stockPicking.getOperatorId());
                stockPicking.setHistoryStatus(auditors.size() > 1 && auditors.contains(purchaseStockInQueryRequest.getUserId()));
            }
        }
        return new BaseResponse(count, stockPickingList);
    }

    /***
     * beforeGoodsMap 记录数据库修改前的商品列表
     * afterGoodsMap  记录页面修改后的商品列表
     * changedAmountMap 记录某件商品修改后的商品数量
     * 某件商品更新入库数  = 更改前采购订单入库数 + 本次入库数 + 商品修改数量
     */
    @Override
    public HttpResponse modify(PurchaseStockInModifyRequest purchaseStockInModifyRequest) {
        StockPicking stockPicking = purchaseStockInModifyRequest.getStockPicking();
        StockPickingGoodsRequest stockPickingGoodsRequest = new StockPickingGoodsRequest();
        Map<String, StockPickingGoods> beforeGoodsMap = new HashMap<>();//数据库原始入库数量
        Map<String, StockPickingGoods> afterGoodsMap = new HashMap<>();//页面更改后入库数量
        try {
            StockPicking old = stockPickingDao.selectByStockPickingId(stockPicking.getStockPickingId());
            if (old == null) {
                LOGGER.error("该采购入库单不存在!");
                return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_DATA_NOT_FOUND);
            }
            stockPickingGoodsRequest.setStockPickingId(stockPicking.getStockPickingId());
            List<StockPickingGoods> goodsList = stockPickingGoodsDao.select(stockPickingGoodsRequest);
            for (StockPickingGoods pickingGoods : goodsList) {
                beforeGoodsMap.put(pickingGoods.getGoodsId(), pickingGoods);
            }
            int updateRow = stockPickingDao.updateByStockPickingId(stockPicking);
            if (updateRow <= 0) {
                LOGGER.error("修改采购入库单失败!");
                return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_DELETE_ERROR);
            }
            //页面传来的商品列表
            List<StockPickingGoods> stockPickingGoodsList = purchaseStockInModifyRequest.getStockPickingGoodsList();
            if (stockPickingGoodsList == null || stockPickingGoodsList.size() == 0) {
                LOGGER.error("采购入库商品列表为空!");
                return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_PARAM_ERROR);
            }
            //修改采购入库商品信息
            stockPickingGoodsList = removeEmpty(stockPickingGoodsList);
            stockPickingGoodsDao.deleteByPickingId(old.getStockPickingId());
            for (StockPickingGoods stockPickingGoods : stockPickingGoodsList) {
                stockPickingGoods.setBrandId(old.getBrandId());
                stockPickingGoods.setStoreId(old.getStoreId());
            }
            boolean insertFlag = stockPickingService.insertStockPickingGoodsList(stockPicking.getStockPickingId(), stockPickingGoodsList);
            if (!insertFlag) {
                throw new PurchaseStockInException("添加采购入库单商品失败!");
            }
            for (StockPickingGoods stockPickingGoods : stockPickingGoodsList) {
                afterGoodsMap.put(stockPickingGoods.getGoodsId(), stockPickingGoods);
            }
            String orderId = purchaseStockInModifyRequest.getSrcOrderId();
            if (!StringUtils.isEmpty(orderId)) {
                PurchaseOrderDetail queryDetail = new PurchaseOrderDetail();
                queryDetail.setOrderId(orderId);
                Long totalStockInAmount = 0L;
                //获得详单-->修改已入库数据
                List<PurchaseOrderDetail> purchaseOrderDetailModifyList = purchaseOrderDetailDao.select(queryDetail);
                StockPickingGoods newStockPickingGoods;
                for (PurchaseOrderDetail detail : purchaseOrderDetailModifyList) {
                    Long afterStockInNum = 0L;
                    StockPickingGoods afterStockPickingGoods = afterGoodsMap.get(detail.getGoodsId());
                    if (afterStockPickingGoods != null && afterStockPickingGoods.getPickingAmount() != null) {
                        afterStockInNum = afterStockPickingGoods.getPickingAmount();
                    }
                    Long beforeStockInNum = 0L;
                    StockPickingGoods beforeStockPickingGoods = beforeGoodsMap.get(detail.getGoodsId());
                    if (beforeStockPickingGoods != null && beforeStockPickingGoods.getPickingAmount() != null) {
                        beforeStockInNum = beforeStockPickingGoods.getPickingAmount();
                    }
                    PurchaseOrderDetail modifyRequest = new PurchaseOrderDetail();
                    modifyRequest.setOrderDetailId(detail.getOrderDetailId());
                    Long moreStockInNum = afterStockInNum - beforeStockInNum;
                    Long remainPurchaseNum = detail.getPurchaseAmount() - detail.getStockInAmount() + beforeStockInNum;
                    Long stockInAmount = afterStockInNum > remainPurchaseNum ? detail.getPurchaseAmount() : (detail.getStockInAmount() + moreStockInNum);
                    if (afterStockPickingGoods != null && afterStockPickingGoods.getSourcePurchaseStockInAmount() == null) {
                        Long sourcePurchaseStockInAmount = stockInAmount - detail.getStockInAmount();
                        if (beforeStockPickingGoods != null && beforeStockPickingGoods.getSourcePurchaseStockInAmount() != null) {
                            sourcePurchaseStockInAmount += beforeStockPickingGoods.getSourcePurchaseStockInAmount();
                        }
                        newStockPickingGoods = new StockPickingGoods();
                        newStockPickingGoods.setPickingGoodsId(afterStockPickingGoods.getPickingGoodsId());
                        newStockPickingGoods.setSourcePurchaseStockInAmount(sourcePurchaseStockInAmount);
                        stockPickingGoodsDao.updateByPickingGoodsId(newStockPickingGoods);
                    }
                    //更新实际入库数量
                    modifyRequest.setStockInAmount(stockInAmount);
                    int orderDetailRow = purchaseOrderDetailDao.updateByPrimaryKeySelective(modifyRequest);
                    if (orderDetailRow <= 0) {
                        throw new PurchaseStockInException("采购入库-更新采购单失败!");
                    }
                    totalStockInAmount += stockInAmount;
                }
                PurchaseOrderModifyRequest newOrder = new PurchaseOrderModifyRequest();
                newOrder.setOrderId(orderId);
                newOrder.setStockInAmount(totalStockInAmount);
                int orderRow = purchaseOrderDao.updateByPrimaryKeySelective(newOrder);
                if (orderRow <= 0) {
                    throw new PurchaseStockInException("采购入库-更新采购单失败!");
                }
            }
            boolean existStatus = processDefinitionService.exist(old.getBrandId(), Consts.PROCESS_TYPE_PURCHASE_STOCK_IN);
            if (existStatus) {
                return HttpResponse.success(new String[]{old.getStockPickingId(), old.getStockPickingDocumentId(), "exist"});
            }
            return HttpResponse.success(new String[]{old.getStockPickingId()});
        } catch (Exception e) {
            LOGGER.error("修改采购入库单失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse delete(String stockPickingId) {
        try {
            StockPicking old = stockPickingDao.selectByStockPickingId(stockPickingId);
            if (old == null) {
                LOGGER.error("该采购入库单不存在!");
                return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_DATA_NOT_FOUND);
            }
            if (old.getStockPickingStatus() != 0) {
                LOGGER.error("该采购入库单不允许删除!");
                return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_CAN_NOT_DELETE);
            }
            int deleteRow = stockPickingDao.deleteByStockPickingId(stockPickingId);
            if (deleteRow <= 0) {
                LOGGER.error("删除采购入库单失败!");
                return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_DELETE_ERROR);
            }
            StockPickingGoodsRequest stockPickingGoodsRequest = new StockPickingGoodsRequest();
            stockPickingGoodsRequest.setStockPickingId(stockPickingId);
            stockPickingGoodsRequest.setWithPage(1);
            //删除采购入库商品
            List<StockPickingGoods> stockPickingGoodsList = stockPickingGoodsDao.select(stockPickingGoodsRequest);
            if (stockPickingGoodsList == null || stockPickingGoodsList.size() == 0) {
                return HttpResponse.success(ResultCode.SUCCESS);
            }
            Map<String, StockPickingGoods> stockPickingGoodsMap = new HashMap<>();
            for (StockPickingGoods stockPickingGoods : stockPickingGoodsList) {
                stockPickingGoodsMap.put(stockPickingGoods.getGoodsId(), stockPickingGoods);
                stockPickingGoodsDao.deleteByPickingGoodsId(stockPickingGoods.getPickingGoodsId());
            }
            //无来源采购订单
            if (StringUtils.isEmpty(old.getSrcOrderId())) {
                return HttpResponse.success(ResultCode.SUCCESS);
            }
            PurchaseOrder purchaseOrder = purchaseOrderDao.selectByOrderId(old.getSrcOrderId());
            if (purchaseOrder.getStockInAmount() == null || purchaseOrder.getStockInAmount() == 0) {
                return HttpResponse.success(ResultCode.SUCCESS);
            }
            PurchaseOrderModifyRequest purchaseOrderModifyRequest = new PurchaseOrderModifyRequest();
            purchaseOrderModifyRequest.setOrderId(old.getSrcOrderId());
            PurchaseOrderDetail query = new PurchaseOrderDetail();
            query.setOrderId(old.getSrcOrderId());
            List<PurchaseOrderDetail> purchaseOrderDetailList = purchaseOrderDetailDao.select(query);
            //无来源采购单商品信息
            if (purchaseOrderDetailList == null || purchaseOrderDetailList.size() == 0) {
                return HttpResponse.success(ResultCode.SUCCESS);
            }
            Long totalStockInAmount = 0L;
            for (PurchaseOrderDetail purchaseOrderDetail : purchaseOrderDetailList) {
                StockPickingGoods stockPickingGoods = stockPickingGoodsMap.get(purchaseOrderDetail.getGoodsId());
                //商品没有入库信息,则跳过
                if (stockPickingGoods == null) {
                    continue;
                }
                purchaseOrderDetail.setStockInAmount(purchaseOrderDetail.getStockInAmount() - stockPickingGoods.getPickingAmount());
                int detailRow = purchaseOrderDetailDao.updateByPrimaryKeySelective(purchaseOrderDetail);
                if (detailRow <= 0) {
                    throw new PurchaseStockInException("更新采购订单失败!");
                }
                totalStockInAmount += purchaseOrderDetail.getStockInAmount();
            }
            purchaseOrderModifyRequest.setStockInAmount(totalStockInAmount);
            int orderRow = purchaseOrderDao.updateByPrimaryKeySelective(purchaseOrderModifyRequest);
            if (orderRow <= 0) {
                throw new PurchaseStockInException("更新采购订单失败!");
            }
            return HttpResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("删除采购入库单失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse detailPurchaseOrder(String purchaseOrderId) {
        try {
            PurchaseOrder purchaseOrder = purchaseOrderDao.selectByOrderId(purchaseOrderId);
            if (purchaseOrder == null) {
                LOGGER.error("该订单不存在!");
                return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_DATA_NOT_FOUND);
            }
            //查询采购商品列表
            PurchaseOrderDetail query = new PurchaseOrderDetail();
            query.setWithPage(Consts.QUERY_ALL);
            query.setOrderId(purchaseOrderId);
            List<PurchaseOrderDetail> purchaseOrderDetailList = purchaseOrderDetailDao.select(query);
            if (purchaseOrderDetailList != null) {
                purchaseOrder.setPurchaseOrderDetailList(purchaseOrderDetailList);
            }
            return HttpResponse.success(purchaseOrder);
        } catch (Exception e) {
            LOGGER.error("查询采购订单详情失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse confirm(PurchaseStockInConfirmRequest purchaseStockInConfirmRequest) {
        Map<String, StockPickingGoods> pickingGoodsMap = new HashMap<>();
        PurchaseOrderModifyRequest modifyRequest = new PurchaseOrderModifyRequest();
        try {
            //采购入库单据
            StockPicking old = stockPickingDao.selectByStockPickingId(purchaseStockInConfirmRequest.getStockPickingId());
            if (old == null) {
                LOGGER.error("确认采购入库单操作失败!");
                return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_DATA_NOT_FOUND);
            }
            StockPicking stockPicking = new StockPicking();
            stockPicking.setStockPickingId(old.getStockPickingId());
            stockPicking.setStockPickingDocumentId(old.getStockPickingDocumentId());
            if (purchaseStockInConfirmRequest.getPurchaseStockInStatus() != null && purchaseStockInConfirmRequest.getPurchaseStockInStatus() == Consts.ORDER_STATUS_CONFIRMED) {
                stockPicking.setStockPickingStatus(Consts.ORDER_STATUS_CONFIRMED);
            } else {
                if (!purchaseStockInConfirmRequest.getAuditStatus()) {
                    int row = stockPickingService.cancelStockPicking(old.getStockPickingId());
                    if (row == 0) {
                        return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_MODIFY_ERROR);
                    }
                    processInstanceService.delete(old.getStockPickingId());
                    return HttpResponse.success();
                }
                ProcessInstanceModifyRequest processInstanceModifyRequest = new ProcessInstanceModifyRequest();
                processInstanceModifyRequest.setBusinessId(old.getStockPickingId());
                processInstanceModifyRequest.setUserId(purchaseStockInConfirmRequest.getUserId());
                processInstanceModifyRequest.setRemark("通过, 执行");
                HttpResponse response = processInstanceService.audit(processInstanceModifyRequest);
                if (!StringUtils.equals(response.getCode(), "0") || response.getData() == null) {
                    return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_MODIFY_ERROR);
                }
                Integer instanceStatus = Integer.parseInt(response.getData().toString());
                if (instanceStatus != Consts.PROCESS_INSTANCE_STATUS_CONFIRMED) {
                    stockPicking.setStockPickingStatus(Consts.ORDER_STATUS_CONFIRMING);
                } else {
                    stockPicking.setStockPickingStatus(Consts.ORDER_STATUS_CONFIRMED);
                }
            }
            stockPicking.setStockPickingId(purchaseStockInConfirmRequest.getStockPickingId());
            if (!StringUtils.isEmpty(purchaseStockInConfirmRequest.getAuditorName())) {
                stockPicking.setAuditorName(purchaseStockInConfirmRequest.getAuditorName());
            }
            if (!StringUtils.isEmpty(purchaseStockInConfirmRequest.getAuditTime())) {
                stockPicking.setAuditorTime(DateFormatUtil.parseDateISO(purchaseStockInConfirmRequest.getAuditTime()));
            } else {
                stockPicking.setAuditorTime(new Date());
            }
            StockPickingGoodsRequest stockPickingGoodsRequest = new StockPickingGoodsRequest();
            stockPickingGoodsRequest.setStockPickingId(purchaseStockInConfirmRequest.getStockPickingId());
            //采购入库单据
            List<StockPickingGoods> stockPickingGoodsList = stockPickingGoodsDao.select(stockPickingGoodsRequest);
            for (StockPickingGoods goods : stockPickingGoodsList) {
                pickingGoodsMap.put(goods.getGoodsId(), goods);
            }
            stockPicking.setStockPickingGoodsList(stockPickingGoodsList);
            stockPicking.setStockPickingType(Consts.STOCK_PICKING_TYPE_PURCHASE_STOCK_IN);
            stockPicking.setToWarehouseId(old.getToWarehouseId());
            if (stockPicking.getStockPickingStatus() == Consts.ORDER_STATUS_CONFIRMED && stockPickingGoodsList.size() > 0) {
                stockQuantService.updateQuantByStockPicking(stockPicking, Consts.STOCK_QUANT_CHANGE_TYPE_STOCK_PICKING);
                StockPicking newStockPicking = stockPickingDao.selectByStockPickingId(purchaseStockInConfirmRequest.getStockPickingId());
                newStockPicking.setAuditorTime(new Date());
                paymentService.synchronizedPurchaseOrder(new SynchronizedPurchaseOrderRequest(newStockPicking));
                //更新采购订单
                String purchaseOrderId = old.getSrcOrderId();
                //采购订单
                PurchaseOrder order = purchaseOrderDao.selectByOrderId(purchaseOrderId);
                //采购订单详单
                List<PurchaseOrderDetail> details = purchaseOrderDetailDao.selectByOrderId(order.getOrderId());
                StockPickingGoods temporaryDetail;

                for (PurchaseOrderDetail orderDetail : details) {
                    temporaryDetail = pickingGoodsMap.get(orderDetail.getGoodsId());
                    if (temporaryDetail != null) {
                        //采购剩余数
                        orderDetail.setCompleteStockInAmount(temporaryDetail.getPickingAmount() + orderDetail.getCompleteStockInAmount());
                    }
                    purchaseOrderDetailDao.updateByPrimaryKeySelective(orderDetail);
                }
                modifyRequest.setBrandId(order.getBrandId());
                modifyRequest.setOrderId(order.getOrderId());
                modifyRequest.setCompleteStockInAmount(old.getPickingAmount() + order.getCompleteStockInAmount());
                if (old.getPickingAmount() + order.getCompleteStockInAmount() < order.getTotalAmount()) {
                    //已入库
                    modifyRequest.setOrderStatus(Consts.PURCHASE_ORDER_RK_STATUS);
                } else {
                    //已完成
                    modifyRequest.setOrderStatus(Consts.PURCHASE_ORDER_STATUS_FINISHED);
                }
                int modifyRow = purchaseOrderDao.updateByPrimaryKeySelective(modifyRequest);
                if (modifyRow == 0) {
                    return HttpResponse.failure(ResultCode.PURCHASE_ORDER_UPD_ERROR);
                }
            }
            stockPickingDao.updateByStockPickingId(stockPicking);
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("确认采购入库单操作失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse commit(String stockPickingId) {
        try {
            StockPicking old = stockPickingDao.selectByStockPickingId(stockPickingId);
            if (old == null) {
                LOGGER.error("采购入库单号:[" + stockPickingId + "]不存在!");
                return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_DATA_NOT_FOUND);
            }
            StockPicking stockPicking = new StockPicking();
            stockPicking.setStockPickingId(stockPickingId);
            stockPicking.setStockPickingStatus(Consts.ORDER_STATUS_COMMITED);          //已提审
            int row = stockPickingDao.updateByStockPickingId(stockPicking);
            if (row <= 0) {
                return HttpResponse.failure(ResultCode.PURCHASE_ORDER_UPD_ERROR);
            }
            ProcessInstanceCreateRequest processInstanceCreateRequest = new ProcessInstanceCreateRequest();
            processInstanceCreateRequest.setProcessType(Consts.PROCESS_TYPE_PURCHASE_STOCK_IN);
            processInstanceCreateRequest.setBrandId(old.getBrandId());
            processInstanceCreateRequest.setBusinessId(stockPickingId);
            processInstanceCreateRequest.setPrice(old.getStockPickingPrice());
            ActivitiUtil.getInstance();
            HttpResponse response = processInstanceService.create(processInstanceCreateRequest, old.getOperatorId());
            if (!StringUtils.equals(response.getCode(), "0")) {
                throw new ProcessInstanceException("提审失败!");
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("确认采购入库单操作失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_SYSTEM_ERROR);
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

}

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
import com.hanyun.scm.api.domain.*;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceCreateRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderConfirmRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderCreateRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderQueryRequest;
import com.hanyun.scm.api.domain.request.stock.StockPickingRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.exception.ProcessInstanceException;
import com.hanyun.scm.api.exception.PurchaseOrderException;
import com.hanyun.scm.api.service.*;
import com.hanyun.scm.api.utils.ActivitiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private static Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderServiceImpl.class);

    @Resource
    private PurchaseOrderDao purchaseOrderDao;

    @Resource
    private PurchaseOrderDetailDao purchaseOrderDetailDao;

    @Resource
    private IdGenerateSeqService idGenerateSeqService;

    @Resource
    private ProcessInstanceService processInstanceService;

    @Resource
    private ProcessDefinitionService processDefinitionService;

    @Resource
    private StockPickingDao stockPickingDao;

    @Resource
    private StockPickingGoodsDao stockPickingGoodsDao;

    @Resource
    private GoodsOdooService goodsOdooService;

    @Override
    public HttpResponse select(PurchaseOrderQueryRequest purchaseOrdersRequest) {
        try {
            int count = purchaseOrderDao.countAll(purchaseOrdersRequest);
            purchaseOrdersRequest.setCount(count);
            List<PurchaseOrder> purchaseOrderList = purchaseOrderDao.select(purchaseOrdersRequest);
            if (purchaseOrdersRequest.getUserId() != null) {
                for (PurchaseOrder purchaseOrder : purchaseOrderList) {
                    purchaseOrder.setAuditStatus(processInstanceService.queryAuditor(purchaseOrder.getOrderId(), purchaseOrdersRequest.getUserId()));
                    List<String> auditors = processInstanceService.queryAuditors(purchaseOrder.getOrderId());
                    auditors.add(purchaseOrder.getOperatorId());
                    purchaseOrder.setHistoryStatus(auditors.size() > 1 && auditors.contains(purchaseOrdersRequest.getUserId()));
                }
            }
            BaseResponse result = new BaseResponse(count, purchaseOrderList);
            return HttpResponse.success(result);
        } catch (Exception e) {
            LOGGER.error("查询商品列表失败!", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @Override
    public BaseResponse selectList(PurchaseOrderQueryRequest purchaseOrderQueryRequest, boolean excel) throws PurchaseOrderException {
        Integer count = purchaseOrderDao.countAll(purchaseOrderQueryRequest);
        purchaseOrderQueryRequest.setCount(count);
        List<PurchaseOrder> orderList = purchaseOrderDao.select(purchaseOrderQueryRequest);
        if (excel) {
            for (PurchaseOrder purchaseOrder : orderList) {
                purchaseOrder.setNotInStockNum(purchaseOrder.getTotalAmount()-purchaseOrder.getCompleteStockInAmount());
                purchaseOrder.setOrderStatusName(MapConsts.getPurchaseOrderStatus().get(purchaseOrder.getOrderStatus()));
            }
        }
        return new BaseResponse(count, orderList);
    }

    @Override
    public HttpResponse delete(String orderId) {
        int code = purchaseOrderDao.deleteOrder(orderId);
        if (code <= 0) {
            LOGGER.error("删除采购订单失败!");
            return HttpResponse.failure(ResultCode.PURCHASE_ORDER_DEL_ERROR);
        }
        int detailRow = purchaseOrderDetailDao.deleteByOrderId(orderId);
        if (detailRow <= 0) {
            LOGGER.error("删除订单详情失败!");
        }
        return HttpResponse.success();
    }

    @Override
    public HttpResponse modify(PurchaseOrderModifyRequest purchaseOrder) {
        try {
            PurchaseOrder purOrder = purchaseOrderDao.selectByOrderId(purchaseOrder.getOrderId());
            if (purOrder == null) {
                LOGGER.error("订单不存在!");
                return HttpResponse.failure(ResultCode.DATA_NOT_FOUND);
            }
            PurchaseOrderDetail query = new PurchaseOrderDetail();
            query.setOrderId(purchaseOrder.getOrderId());
            List<PurchaseOrderDetail> oldDetailList = purchaseOrderDetailDao.select(query);
            if (oldDetailList != null && oldDetailList.size() > 0) {
                for (PurchaseOrderDetail purchaseOrderDetail : oldDetailList) {
                    purchaseOrderDetail.setBrandId(purOrder.getBrandId());
                    purchaseOrderDetail.setStoreId(purOrder.getStoreId());
                    int res = purchaseOrderDetailDao.deleteByPrimaryKey(purchaseOrderDetail.getOrderDetailId());
                    if (res == 0) {
                        LOGGER.error("订单中详情删除失败!");
                        return HttpResponse.failure(ResultCode.ORDER_DETAIL_NOT_DELETE);
                    }
                }

            }
            List<PurchaseOrderDetail> purchaseOrderDetailList = purchaseOrder.getPurchaseOrderDetailList();
            if (purchaseOrderDetailList == null || purchaseOrderDetailList.size() == 0) {
                LOGGER.error("无商品信息!");
                return HttpResponse.failure(ResultCode.PURCHASE_ORDER_GOODS_EMPTY_ERROR);
            }
            purchaseOrderDetailList = removeEmpty(purchaseOrderDetailList);
            for (PurchaseOrderDetail purchaseOrderDetail : purchaseOrderDetailList) {
                String detailId = IdUtil.uuid();
                purchaseOrderDetail.setOrderDetailId(detailId);
                purchaseOrderDetail.setOrderId(purchaseOrder.getOrderId());
                purchaseOrderDetail.setBrandId(purOrder.getBrandId());
                purchaseOrderDetail.setStoreId(purOrder.getStoreId());
                int detailRow = purchaseOrderDetailDao.insertSelective(purchaseOrderDetail);
                if (detailRow <= 0) {
                    LOGGER.error("更新采购订单商品失败!");
                    throw new PurchaseOrderException("更新采购订单商品失败!");
                }
            }
            purchaseOrder.setPlanTime(DateFormatUtil.parseDateISO(purchaseOrder.getPlanTimeString()));
            int res = purchaseOrderDao.updateByPrimaryKeySelective(purchaseOrder);
            if (res == 0) {
                LOGGER.error("订单更新失败!");
                return HttpResponse.failure(ResultCode.PURCHASE_ORDER_UPD_ERROR);
            }
            boolean existStatus = processDefinitionService.exist(purOrder.getBrandId(), Consts.PROCESS_TYPE_PURCHASE_ORDER);
            if (existStatus) {
                return HttpResponse.success(new String[]{purOrder.getOrderId(), purOrder.getOrderDocumentId(), "exist"});
            }
            return HttpResponse.success(new String[]{purOrder.getOrderId(), purOrder.getOrderDocumentId()});
        } catch (Exception e) {
            LOGGER.error("更新订单信息失败!", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse detail(String orderId) {
        try {
            PurchaseOrder purchaseOrder = purchaseOrderDao.selectByOrderId(orderId);
            if (purchaseOrder == null) {
                return HttpResponse.failure(ResultCode.DATA_NOT_FOUND);
            }
            PurchaseOrderDetail query = new PurchaseOrderDetail();
            query.setOrderId(orderId);
            List<PurchaseOrderDetail> purchaseOrderDetailList = purchaseOrderDetailDao.select(query);
            if (purchaseOrderDetailList != null && purchaseOrderDetailList.size() > 0) {
                Map<String, GoodsOdoo> map = goodsOdooService.getPurchaseOrderGoodsMap(purchaseOrder.getBrandId(), purchaseOrderDetailList);
                purchaseOrderDetailList.forEach(p -> {
                    String goodsId = p.getGoodsId();
                    boolean bl = !map.isEmpty() && map.get(goodsId) != null && !StringUtils.isEmpty(map.get(goodsId).getGoodsPic());
                    p.setGoodsPic(bl ? map.get(goodsId).getGoodsPic() : "");
                    boolean brandNamebl = !StringUtils.isEmpty(p.getGoodsBrandName());
                    p.setGoodsBrandName(brandNamebl ? p.getGoodsBrandName() : "");
                });
                purchaseOrder.setPurchaseOrderDetailList(purchaseOrderDetailList);
            }
            return HttpResponse.success(purchaseOrder);
        } catch (Exception e) {
            LOGGER.error("查询供应商详情失败!", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse selectPurchaseOrderForStockInAndReturn(PurchaseOrderQueryRequest purchaseOrderQueryRequest) {
        try {
            if (!StringUtils.isEmpty(purchaseOrderQueryRequest.getPlanBeginTime())) {
                purchaseOrderQueryRequest.setPlanBeginTime(DateFormatUtil.formatDateTimeMills(DateFormatUtil.parseDateISO(purchaseOrderQueryRequest.getPlanBeginTime())));
            } else {
                purchaseOrderQueryRequest.setPlanBeginTime(null);
            }
            if (!StringUtils.isEmpty(purchaseOrderQueryRequest.getPlanEndTime())) {
                purchaseOrderQueryRequest.setPlanEndTime(DateFormatUtil.formatDateTimeMills(DateFormatUtil.parseDateISO(purchaseOrderQueryRequest.getPlanEndTime())));
            } else {
                purchaseOrderQueryRequest.setPlanEndTime(null);
            }
            Integer count = purchaseOrderDao.countAllForPurchaseReturn(purchaseOrderQueryRequest);
            purchaseOrderQueryRequest.setCount(count);
            List<PurchaseOrder> purchaseOrderList = purchaseOrderDao.queryForPurchaseReturn(purchaseOrderQueryRequest);
            BaseResponse baseResponse = new BaseResponse(count, purchaseOrderList);
            return HttpResponse.success(baseResponse);
        } catch (Exception e) {
            LOGGER.error("查询采购订单列表失败", e);
            return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse confirm(PurchaseOrderConfirmRequest purchaseOrderConfirmRequest) {
        try {
            String orderId = purchaseOrderConfirmRequest.getOrderId();
            if (StringUtils.isEmpty(orderId)) {
                return HttpResponse.failure(ResultCode.ORDERid_IS_NULL);
            }
            //查询订单
            PurchaseOrder purOrder = purchaseOrderDao.selectByOrderId(orderId);
            if (purOrder == null) {
                return HttpResponse.failure(ResultCode.PURCHASE_ORDER_CONFIRM_ERROR);
            }
            //无流程审核
            if (purchaseOrderConfirmRequest.getOrderStatus() != null && purchaseOrderConfirmRequest.getOrderStatus() == Consts.PURCHASE_ORDER_STATUS_CONFIRMED) {
                purOrder.setOrderStatus(Consts.PURCHASE_ORDER_STATUS_CONFIRMED);
            } else {
                if (!purchaseOrderConfirmRequest.getAuditStatus()) {
                    PurchaseOrderModifyRequest purchaseOrderModifyRequest = new PurchaseOrderModifyRequest();
                    purchaseOrderModifyRequest.setOrderId(orderId);
                    purchaseOrderModifyRequest.setOrderStatus(Consts.PURCHASE_ORDER_STATUS_SAVED);
                    int row = purchaseOrderDao.updateByPrimaryKeySelective(purchaseOrderModifyRequest);
                    if (row == 0) {
                        return HttpResponse.failure(ResultCode.PURCHASE_ORDER_CONFIRM_ERROR);
                    }
                    processInstanceService.delete(orderId);
                    return HttpResponse.success();
                }
                ProcessInstanceModifyRequest processInstanceModifyRequest = new ProcessInstanceModifyRequest();
                processInstanceModifyRequest.setBusinessId(purchaseOrderConfirmRequest.getOrderId());
                processInstanceModifyRequest.setUserId(purchaseOrderConfirmRequest.getUserId());
                processInstanceModifyRequest.setRemark("通过, 执行");
                HttpResponse response = processInstanceService.audit(processInstanceModifyRequest);
                if (!StringUtils.equals(response.getCode(), "0") || response.getData() == null) {
                    return HttpResponse.failure(ResultCode.PURCHASE_ORDER_CONFIRM_ERROR);
                }
                Integer instanceStatus = Integer.parseInt(response.getData().toString());
                if (instanceStatus != Consts.PROCESS_INSTANCE_STATUS_CONFIRMED) {
                    purOrder.setOrderStatus(Consts.PURCHASE_ORDER_STATUS_CONFIRMING);
                } else {
                    purOrder.setOrderStatus(Consts.PURCHASE_ORDER_STATUS_CONFIRMED);
                }
            }
            PurchaseOrderModifyRequest purchaseOrderModifyRequest = new PurchaseOrderModifyRequest();
            purchaseOrderModifyRequest.setOrderId(orderId);
            purchaseOrderModifyRequest.setAuditTime(new Date());
            purchaseOrderModifyRequest.setOrderStatus(purOrder.getOrderStatus());
            //修改订单状态为已确认
            int i = this.purchaseOrderDao.updateByPrimaryKeySelective(purchaseOrderModifyRequest);
            if (i <= 0) {
                return HttpResponse.failure(ResultCode.PURCHASE_ORDER_CONFIRM_ERROR);
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("查询采购订单列表失败", e);
            return HttpResponse.failure(ResultCode.PURCHASE_ORDER_CONFIRM_ERROR);
        }
    }

    @Override
    public HttpResponse create(PurchaseOrderCreateRequest purchaseOrderCreateRequest) {
        try {
            String purchaseOrderId = IdUtil.uuid();
            String purchaseOrderDocumentId = idGenerateSeqService.generateId(IdGenerateType.CD);
            purchaseOrderCreateRequest.setOrderId(purchaseOrderId);
            purchaseOrderCreateRequest.setOrderDocumentId(purchaseOrderDocumentId);
            if (!StringUtils.isEmpty(purchaseOrderCreateRequest.getPlanTimeString())) {
                purchaseOrderCreateRequest.setPlanTime(DateFormatUtil.parseDateISO(purchaseOrderCreateRequest.getPlanTimeString()));
            }
            int row = purchaseOrderDao.insertSelective(purchaseOrderCreateRequest);
            if (row <= 0) {
                LOGGER.error("添加采购订单失败!");
                return HttpResponse.failure(ResultCode.PURCHASE_ORDER_ADD_ERROR);
            }
            List<PurchaseOrderDetail> purchaseOrderDetailList = purchaseOrderCreateRequest.getPurchaseOrderDetailList();
            if (purchaseOrderDetailList == null || purchaseOrderDetailList.size() == 0) {
                LOGGER.error("无商品信息!");
                return HttpResponse.failure(ResultCode.PURCHASE_ORDER_GOODS_EMPTY_ERROR);
            }
            purchaseOrderDetailList = removeEmpty(purchaseOrderDetailList);
            for (PurchaseOrderDetail purchaseOrderDetail : purchaseOrderDetailList) {
                String detailId = IdUtil.uuid();
                purchaseOrderDetail.setOrderDetailId(detailId);
                purchaseOrderDetail.setOrderId(purchaseOrderId);
                purchaseOrderDetail.setBrandId(purchaseOrderCreateRequest.getBrandId());
                purchaseOrderDetail.setStoreId(purchaseOrderCreateRequest.getStoreId());
                int detailRow = purchaseOrderDetailDao.insertSelective(purchaseOrderDetail);
                if (detailRow <= 0) {
                    LOGGER.error("添加采购订单商品失败!");
                    throw new PurchaseOrderException("添加采购订单商品失败!");
                }
            }
            boolean existStatus = processDefinitionService.exist(purchaseOrderCreateRequest.getBrandId(), Consts.PROCESS_TYPE_PURCHASE_ORDER);
            if (existStatus) {
                return HttpResponse.success(new String[]{purchaseOrderId, purchaseOrderDocumentId, "exist"});
            }
            return HttpResponse.success(new String[]{purchaseOrderId, purchaseOrderDocumentId});
        } catch (Exception e) {
            LOGGER.error("添加采购订单失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_ORDER_ADD_ERROR);
        }
    }

    @Override
    public PurchaseOrder getOrder(String orderId) throws PurchaseOrderException {
        PurchaseOrder purchaseOrder = purchaseOrderDao.selectByOrderId(orderId);
        if (purchaseOrder == null) {
            LOGGER.error("该采购订单不存在!");
            throw new PurchaseOrderException("该采购订单不存在!");
        }
        List<PurchaseOrderDetail> purchaseOrderDetailList = purchaseOrderDetailDao.selectByOrderId(orderId);
        if (purchaseOrderDetailList == null) {
            LOGGER.error("采购订单商品信息不存在!");
            throw new PurchaseOrderException("采购订单商品信息不存在!");
        }
        for (PurchaseOrderDetail purchaseOrderDetail : purchaseOrderDetailList) {
            purchaseOrderDetail.setTotalPrice(purchaseOrderDetail.getUnitPrice() * purchaseOrderDetail.getPurchaseAmount());
            purchaseOrderDetail.setUnStockInAmount(purchaseOrderDetail.getPurchaseAmount() - purchaseOrderDetail.getCompleteStockInAmount());
        }
        purchaseOrder.setPurchaseOrderDetailList(purchaseOrderDetailList);
        return purchaseOrder;
    }

    /**
     * 去掉goodsId为空的数据
     *
     * @param purchaseOrderDetailList 商品列表
     * @return
     */
    private List<PurchaseOrderDetail> removeEmpty(List<PurchaseOrderDetail> purchaseOrderDetailList) {
        for (int i = purchaseOrderDetailList.size() - 1; i >= 0; i--) {
            if (StringUtils.isEmpty(purchaseOrderDetailList.get(i).getGoodsId())) {
                purchaseOrderDetailList.remove(i);
            }
        }
        return purchaseOrderDetailList;
    }

    @Override
    public HttpResponse queryForStockIn(PurchaseOrderQueryRequest purchaseOrderQueryRequest) {

        try {
            List<PurchaseOrder> purchaseOrderList = purchaseOrderDao.queryForPurchaseStockIn(purchaseOrderQueryRequest);
            BaseResponse baseResponse = new BaseResponse(0, purchaseOrderList);
            return HttpResponse.success(baseResponse);
        } catch (Exception e) {
            LOGGER.error("查询入库单关联订单失败", e);
            return HttpResponse.failure(ResultCode.PURCHASE_ORDER_QUERY_ERROR);
        }
    }

    @Override
    public HttpResponse selectDetailForStockIn(String orderId) {
        try {
            List<PurchaseOrderDetail> detailList = purchaseOrderDetailDao.selectDetailForStockIn(orderId);
            BaseResponse response = new BaseResponse(0, detailList);
            return HttpResponse.success(response);
        } catch (Exception e) {
            LOGGER.error("采购订单详单查询失败", e);
            return HttpResponse.failure(ResultCode.PURCHASE_ORDER_QUERY_ERROR);
        }
    }

    @Override
    public HttpResponse commit(String orderId) {
        try {
            PurchaseOrder old = purchaseOrderDao.selectByOrderId(orderId);
            if (old == null) {
                return HttpResponse.failure(ResultCode.PURCHASE_ORDER_COMMIT_ERROR);
            }
            PurchaseOrderModifyRequest purchaseOrderModifyRequest = new PurchaseOrderModifyRequest();
            purchaseOrderModifyRequest.setOrderId(orderId);
            purchaseOrderModifyRequest.setOrderStatus(Consts.PURCHASE_ORDER_STATUS_COMMITED);
            int row = purchaseOrderDao.updateByPrimaryKeySelective(purchaseOrderModifyRequest);
            if (row == 0) {
                return HttpResponse.failure(ResultCode.PURCHASE_ORDER_COMMIT_ERROR);
            }
            ProcessInstanceCreateRequest processInstanceCreateRequest = new ProcessInstanceCreateRequest();
            processInstanceCreateRequest.setProcessType(Consts.PROCESS_TYPE_PURCHASE_ORDER);
            processInstanceCreateRequest.setBrandId(old.getBrandId());
            processInstanceCreateRequest.setBusinessId(orderId);
            processInstanceCreateRequest.setPrice(old.getTotalPrice());
            ActivitiUtil.getInstance();
            HttpResponse response = processInstanceService.create(processInstanceCreateRequest, old.getOperatorId());
            if (!StringUtils.equals(response.getCode(), "0")) {
                throw new ProcessInstanceException("提审失败!");
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("采购订单提交审核失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_ORDER_COMMIT_ERROR);
        }
    }

    @Override
    public HttpResponse close(String orderId) {
        try {
            PurchaseOrder old = purchaseOrderDao.selectByOrderId(orderId);
            if (old == null) {
                LOGGER.error("记录不存在");
                return HttpResponse.failure(ResultCode.PURCHASE_ORDER_CANCEL_ERROR);
            }
            if(old.getOrderStatus() == Consts.PURCHASE_ORDER_STATUS_FINISHED){
                LOGGER.error("该单据已完成不需要关闭");
                return HttpResponse.failure(ResultCode.ORDER_IS_DONE_NOT_CLOSE_ERROR);
            }
            //处理采购订单下待处理的采购入库单
            StockPickingRequest record = new StockPickingRequest();
            record.setStockPickingType(Consts.STOCK_PICKING_TYPE_PURCHASE_STOCK_IN);
            record.setBrandId(old.getBrandId());
            record.setSrcOrderId(old.getOrderId());
            List<StockPicking> pickingList = stockPickingDao.select(record);
            if(!pickingList.isEmpty() && pickingList.size() > 0){
                for (StockPicking stockPicking : pickingList){
                    int pickingStatus = stockPicking.getStockPickingStatus();
                    if(pickingStatus < Consts.ORDER_STATUS_CONFIRMED){
                        LOGGER.info("还有待处理的采购入库单:" + stockPicking.getStockPickingDocumentId());
                        return HttpResponse.failure(ResultCode.PURCHASE_PEDING_ORDER);
                    }
                }
            }
            PurchaseOrderModifyRequest modify = new PurchaseOrderModifyRequest();
            modify.setOrderStatus(Consts.PURCHASE_ORDER_STATUS_CANCEL);
            modify.setOrderId(orderId);
            modify.setRemark((StringUtils.isEmpty(old.getRemark())?"":old.getRemark()) + Consts.CLOSE_REMARK);
            int row = purchaseOrderDao.updateByPrimaryKeySelective(modify);
            if (row == 0) {
                return HttpResponse.failure(ResultCode.PURCHASE_ORDER_CANCEL_ERROR);
            }
            ActivitiUtil.deleteProcessInstance(orderId, processInstanceService);
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("关闭采购订单失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_ORDER_CANCEL_ERROR);
        }
    }

    @Override
    public HttpResponse queryStockInList(String orderId) {
        try {
            StockPickingRequest stockPickingRequest = new StockPickingRequest();
            stockPickingRequest.setSrcOrderId(orderId);
            stockPickingRequest.setStockPickingStatus(Consts.ORDER_STATUS_CONFIRMED);
            List<StockPicking> stockPickingList = stockPickingDao.select(stockPickingRequest);
            for (StockPicking stockPicking : stockPickingList) {
                List<StockPickingGoods> stockPickingGoodsList = stockPickingGoodsDao.selectByStockPickingId(stockPicking.getStockPickingId());
                stockPicking.setStockPickingGoodsList(stockPickingGoodsList);
            }/**以上代码没有用**/
            return HttpResponse.success(stockPickingList);
        } catch (Exception e) {
            return HttpResponse.failure(ResultCode.PURCHASE_ORDER_QUERY_ERROR);
        }
    }
}

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
import com.hanyun.scm.api.domain.request.purchase.PurchaseReturnConfirmRequest;
import com.hanyun.scm.api.domain.request.purchase.PurchaseReturnCreateRequest;
import com.hanyun.scm.api.domain.request.purchase.PurchaseReturnModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.PurchaseReturnQueryRequest;
import com.hanyun.scm.api.domain.request.stock.StockPickingGoodsRequest;
import com.hanyun.scm.api.domain.request.stock.StockPickingRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.exception.ProcessInstanceException;
import com.hanyun.scm.api.exception.PurchaseReturnException;
import com.hanyun.scm.api.service.*;
import com.hanyun.scm.api.utils.ActivitiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * 采购退货单
 * Date: 2016/10/27
 * Time: 下午12:08
 *
 * @author tianye@hanyun.com
 */
@Service
public class PurchaseReturnServiceImpl implements PurchaseReturnService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseReturnServiceImpl.class);

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
    public HttpResponse create(PurchaseReturnCreateRequest purchaseReturnCreateRequest) {
        StockPicking stockPicking = purchaseReturnCreateRequest.getStockPicking();
        String stockPickingId = IdUtil.uuid();
        stockPicking.setStockPickingType(Consts.STOCK_PICKING_TYPE_PURCHASE_RETURN);
        stockPicking.setStockPickingId(stockPickingId);
        if (stockPicking.getPurchaseReturnTime() == null) {
            stockPicking.setPurchaseReturnTime(new Date());
        }
        try {
            stockPicking.setStockPickingDocumentId(idGenerateSeqService.generateId(IdGenerateType.CT));
        } catch (Exception e) {
            LOGGER.error("生成采购退货单编号失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_CREATE_ERROR);
        }
        try {
            int row = stockPickingDao.insertSelective(stockPicking);
            if (row <= 0) {
                LOGGER.error("创建采购退货单失败!");
                return HttpResponse.failure(ResultCode.PURCHASE_RETURN_CREATE_ERROR);
            }
            List<StockPickingGoods> stockPickingGoodsList = removeEmpty(purchaseReturnCreateRequest.getStockPickingGoodsList());
            if (stockPickingGoodsList != null && stockPickingGoodsList.size() > 0) {
                for (StockPickingGoods stockPickingGoods : stockPickingGoodsList) {
                    stockPickingGoods.setBrandId(purchaseReturnCreateRequest.getBrandId());
                    stockPickingGoods.setStoreId(purchaseReturnCreateRequest.getStoreId());
                }
                boolean insertFlag = stockPickingService.insertStockPickingGoodsList(stockPicking.getStockPickingId(), stockPickingGoodsList);
                if (!insertFlag) {
                    throw new PurchaseReturnException("添加采购退货单商品失败!");
                }
            }
            //更新采购入库已退数量
            if (purchaseReturnCreateRequest.getTabType() == Consts.QUOTE_PUCHASEIN) {
                int updateRow = updatePurchaseInReturnedNumAndStatus(stockPickingGoodsList, purchaseReturnCreateRequest.getSrcOrderId(), purchaseReturnCreateRequest.getTypeStatus(), null);
                if (updateRow <= 0) {
                    throw new PurchaseReturnException("更新采购入库已退数量或状态失败。");
                }
            }
            boolean existStatus = processDefinitionService.exist(stockPicking.getBrandId(), Consts.PROCESS_TYPE_PURCHASE_RETURN);
            if (existStatus) {
                return HttpResponse.success(new String[]{stockPicking.getStockPickingId(), stockPicking.getStockPickingDocumentId(), "exist"});
            }
            return HttpResponse.success(new String[]{stockPicking.getStockPickingId(), stockPicking.getStockPickingDocumentId()});
        } catch (Exception e) {
            LOGGER.error("创建采购退货单失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_CREATE_ERROR);
        }
    }

    /**
     * 将入库单的商品封装到Map中
     *
     * @param srcOrderId 入库单id
     * @return map
     */
    private Map<String, StockPickingGoods> getMap(String srcOrderId) {
        List<StockPickingGoods> stockInGoodsList = stockPickingGoodsDao.selectByStockPickingId(srcOrderId);
        Map<String, StockPickingGoods> compareMap = new HashMap<>();
        for (StockPickingGoods goods : stockInGoodsList) {
            if (StringUtils.isEmpty(goods.getGoodsId())) {
                continue;
            }
            compareMap.put(goods.getGoodsId(), goods);
        }
        return compareMap;
    }

    /**
     * 更新入库单的商品退送数量和入库单的退送状态
     *
     * @param stockPickingGoodsList 页面传过来的业务商品List
     * @param srcOrderId            入库单的Id
     * @return updateRow
     */
    private int updatePurchaseInReturnedNumAndStatus(List<StockPickingGoods> stockPickingGoodsList, String srcOrderId, boolean typeStatus, List<StockPickingGoods> returnGoods) {
        Map<String, StockPickingGoods> compareMap = getMap(srcOrderId);
        Map<String, StockPickingGoods> returnGoodsMap = new HashMap<>();
        if (returnGoods != null && returnGoods.size() > 0) {
            for (StockPickingGoods goods : returnGoods) {
                returnGoodsMap.put(goods.getGoodsId(), goods);
            }
        }
        for (StockPickingGoods newGoods : stockPickingGoodsList) {
            String goodsId = newGoods.getGoodsId();
            Long pickingAmount = newGoods.getPickingAmount();                                               //页面传过来的退货数量
            StockPickingGoods obj = compareMap.get(goodsId);
            if (obj == null) {
                continue;
            }
            Long purchaseReturnNum = obj.getPurchaseReturnAmount() != null ? obj.getPurchaseReturnAmount() : 0L;  //入库单商品的退送数量

            Long updateNum = purchaseReturnNum + pickingAmount;
            if (typeStatus) { //新增
                obj.setPurchaseReturnAmount(updateNum);
            } else {        //修改
                StockPickingGoods returnObject = returnGoodsMap.get(goodsId);
                Long returnSaveAmount = returnObject.getPickingAmount() != null ? returnObject.getPickingAmount() : 0L;
                Long deleteNum = updateNum - returnSaveAmount;
                obj.setPurchaseReturnAmount(deleteNum < 0 ? 0L : deleteNum);
            }

            //更新入库单详情
            int updateNumRow = stockPickingGoodsDao.updateByPickingGoodsId(obj);
            if (updateNumRow <= 0) {
                return updateNumRow;
            }
        }
        boolean udpateFlag = true;
        for (Map.Entry<String, StockPickingGoods> entry : compareMap.entrySet()) {
            if (entry.getValue().getPurchaseReturnAmount() < entry.getValue().getPickingAmount()) {
                udpateFlag = false;
                break;
            }
        }
        StockPicking stockPicking = new StockPicking();
        stockPicking.setStockPickingId(srcOrderId);
        if (udpateFlag) {
            stockPicking.setPurchaseReturnStatus(Consts.PURCHASE_IN_STATUS_ALL);
        } else {
            stockPicking.setPurchaseReturnStatus(Consts.PURCHASE_IN_STATUS_PART);
        }
        int updateStatusRow = stockPickingDao.updateByStockPickingId(stockPicking);
        if (updateStatusRow <= 0) {
            return updateStatusRow;
        }
        return 1;
    }

    /***
     * 删除退货单时更新入库单的退货状态和商品的退货数量
     * @param stockPickingGoodsList     退货单的商品List
     * @param srcOrderId                采购入库的Id
     * @return
     */
    private int deleteReturnByUpdateStockInStatusAndGoodsNum(List<StockPickingGoods> stockPickingGoodsList, String srcOrderId) {
        Map<String, StockPickingGoods> compareMap = getMap(srcOrderId);
        for (StockPickingGoods goods : stockPickingGoodsList) {
            StockPickingGoods obj = compareMap.get(goods.getGoodsId());
            if (obj == null) {
                continue;
            }
            Long pickingAmount = goods.getPickingAmount() != null ? goods.getPickingAmount() : 0;                 //退货单商品的退送数量
            Long purchaseReturnNum = obj.getPurchaseReturnAmount() != null ? obj.getPurchaseReturnAmount() : 0;   //入库单商品的已退送数量
            Long deleteNum = purchaseReturnNum - pickingAmount;
            obj.setPurchaseReturnAmount(deleteNum < 0 ? 0L : deleteNum);
            //更新入库单商品详情
            int updateNumRow = stockPickingGoodsDao.updateByPickingGoodsId(obj);
            if (updateNumRow <= 0) {
                return updateNumRow;
            }
        }
        StockPicking stockPicking = new StockPicking();
        stockPicking.setStockPickingId(srcOrderId);
        stockPicking.setPurchaseReturnStatus(Consts.PURCHASE_IN_STATUS_PART);
        int updateStatusRow = stockPickingDao.updateByStockPickingId(stockPicking);
        if (updateStatusRow <= 0) {
            return updateStatusRow;
        }
        return 1;
    }

    @Override
    public HttpResponse detail(String stockPickingId) {
        try {
            StockPicking stockPicking = stockPickingDao.selectByStockPickingId(stockPickingId);
            if (stockPicking == null) {
                LOGGER.error("该采购退货单不存在!");
                return HttpResponse.failure(ResultCode.PURCHASE_RETURN_DATA_NOT_FOUND);
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
            LOGGER.error("查询退货单详情失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse select(PurchaseReturnQueryRequest purchaseReturnQueryRequest) {
        try {
            return HttpResponse.success(selectList(purchaseReturnQueryRequest, false));
        } catch (Exception e) {
            LOGGER.error("查询采购退货单列表失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_SYSTEM_ERROR);
        }
    }

    @Override
    public BaseResponse selectList(PurchaseReturnQueryRequest purchaseReturnQueryRequest, boolean excel) throws PurchaseReturnException {
        StockPickingRequest request = purchaseReturnQueryRequest.getStockPickingRequest();
        request.setStockPickingType(Consts.STOCK_PICKING_TYPE_PURCHASE_RETURN);
        Integer count = stockPickingDao.countAll(request);
        request.setCount(count);
        List<StockPicking> stockPickingList = stockPickingDao.select(request);
        if (excel) {
            for (StockPicking stockPicking : stockPickingList) {
                stockPicking.setStockPickingStatusName(MapConsts.getOrderStatus().get(stockPicking.getStockPickingStatus()));
                stockPicking.setPaymentStatusName(MapConsts.getReturnPaymentStatus().get(stockPicking.getPaymentStatus()));
            }
        } else {

        }
        if (!StringUtils.isEmpty(purchaseReturnQueryRequest.getUserId())) {
            for (StockPicking stockPicking : stockPickingList) {
                stockPicking.setAuditStatus(processInstanceService.queryAuditor(stockPicking.getStockPickingId(), purchaseReturnQueryRequest.getUserId()));
                List<String> auditors = processInstanceService.queryAuditors(stockPicking.getStockPickingId());
                auditors.add(stockPicking.getOperatorId());
                stockPicking.setHistoryStatus(auditors.size() > 1 && auditors.contains(purchaseReturnQueryRequest.getUserId()));
            }
        }
        return new BaseResponse(count, stockPickingList);
    }


    @Override
    public HttpResponse modify(PurchaseReturnModifyRequest purchaseReturnModifyRequest) {
//        Map<String, Long> resultMap = new HashMap<>();
        StockPicking stockPicking = purchaseReturnModifyRequest.getStockPicking();
        try {
            StockPicking old = stockPickingDao.selectByStockPickingId(stockPicking.getStockPickingId());
            String stockPickingId = old.getStockPickingId();
            List<StockPickingGoods> returnGoods = stockPickingGoodsDao.selectByStockPickingId(stockPickingId);
            if (old == null) {
                LOGGER.error("该采购退货单不存在!");
                return HttpResponse.failure(ResultCode.PURCHASE_RETURN_DATA_NOT_FOUND);
            }
            int updateRow = stockPickingDao.updateByStockPickingId(stockPicking);
            if (updateRow <= 0) {
                LOGGER.error("修改采购退货单失败!");
                return HttpResponse.failure(ResultCode.PURCHASE_RETURN_DELETE_ERROR);
            }
            List<StockPickingGoods> stockPickingGoodsList = removeEmpty(purchaseReturnModifyRequest.getStockPickingGoodsList());
            //修改采购退货商品信息
            if (stockPickingGoodsList != null && stockPickingGoodsList.size() > 0) {
                for (StockPickingGoods stockPickingGoods : stockPickingGoodsList) {
                    stockPickingGoods.setBrandId(old.getBrandId());
                    stockPickingGoods.setStoreId(old.getStoreId());
                }
                stockPickingGoodsDao.deleteByPickingId(stockPickingId);
                boolean insertFlag = stockPickingService.insertStockPickingGoodsList(stockPicking.getStockPickingId(), stockPickingGoodsList);
                if (!insertFlag) {
                    throw new PurchaseReturnException("添加采购退货单商品失败!");
                }
            }
//            for(StockPickingGoods resultGoods : returnGoods){
//                resultMap.put(resultGoods.getGoodsId(),resultGoods.getPickingAmount());
//            }
            //更新采购入库已退数量
            if (purchaseReturnModifyRequest.getTabType() == Consts.QUOTE_PUCHASEIN) {
                int updateInNumRow = updatePurchaseInReturnedNumAndStatus(stockPickingGoodsList, old.getSrcOrderId(), purchaseReturnModifyRequest.getTypeStatus(), returnGoods);
                if (updateInNumRow <= 0) {
                    throw new PurchaseReturnException("更新采购入库已退数量或状态失败。");
                }
            }
            boolean existStatus = processDefinitionService.exist(stockPicking.getBrandId(), Consts.PROCESS_TYPE_PURCHASE_RETURN);
            if (existStatus) {
                return HttpResponse.success(new String[]{stockPicking.getStockPickingId(), stockPicking.getStockPickingDocumentId(), "exist"});
            }
            return HttpResponse.success(new String[]{stockPicking.getStockPickingId(),old.getSrcOrderId()});
        } catch (Exception e) {
            LOGGER.error("修改采购退货单失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse delete(String stockPickingId) {
        try {
            StockPicking old = stockPickingDao.selectByStockPickingId(stockPickingId);
            if (old == null) {
                LOGGER.error("该采购退货单不存在!");
                return HttpResponse.failure(ResultCode.PURCHASE_RETURN_DATA_NOT_FOUND);
            }
            if (old.getStockPickingStatus() != 0) {
                LOGGER.error("该采购退货单不允许删除!");
                return HttpResponse.failure(ResultCode.PURCHASE_RETURN_CAN_NOT_DELETE);
            }
            int deleteRow = stockPickingDao.deleteByStockPickingId(stockPickingId);
            if (deleteRow <= 0) {
                LOGGER.error("删除采购退货单失败!");
                return HttpResponse.failure(ResultCode.PURCHASE_RETURN_DELETE_ERROR);
            }
            StockPickingGoodsRequest stockPickingGoodsRequest = new StockPickingGoodsRequest();
            stockPickingGoodsRequest.setStockPickingId(stockPickingId);
            stockPickingGoodsRequest.setWithPage(1);
            //删除采购退货商品
            List<StockPickingGoods> stockPickingGoodsList = stockPickingGoodsDao.select(stockPickingGoodsRequest);
            if (stockPickingGoodsList != null && stockPickingGoodsList.size() > 0) {
                int deleteInGoodsRow = stockPickingGoodsDao.deleteByPickingId(stockPickingId);
                if (deleteInGoodsRow <= 0) {
                    throw new PurchaseReturnException("删除采购退货商品失败。");
                }
            }

            //引用采购入库单时才更新入库
            if (!StringUtils.isEmpty(old.getSrcOrderId())) {
                //更新入库单商品数量和状态
                int deleteStatusRow = deleteReturnByUpdateStockInStatusAndGoodsNum(stockPickingGoodsList, old.getSrcOrderId());
                if (deleteStatusRow <= 0) {
                    throw new PurchaseReturnException("删除更新入库单商品数量和状态失败。");
                }
            }

            return HttpResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("删除采购退货单失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse detailPurchaseOrder(String purchaseOrderId) {
        try {
            PurchaseOrder purchaseOrder = purchaseOrderDao.selectByOrderId(purchaseOrderId);
            if (purchaseOrder == null) {
                LOGGER.error("该订单不存在!");
                return HttpResponse.failure(ResultCode.PURCHASE_RETURN_DATA_NOT_FOUND);
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
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse confirm(PurchaseReturnConfirmRequest purchaseReturnConfirmRequest) {
        try {
            StockPicking old = stockPickingDao.selectByStockPickingId(purchaseReturnConfirmRequest.getStockPickingId());
            if (old == null) {
                LOGGER.error("确认采购退货单操作失败!");
                return HttpResponse.failure(ResultCode.PURCHASE_RETURN_DATA_NOT_FOUND);
            }
            StockPicking stockPicking = new StockPicking();
            stockPicking.setStockPickingId(old.getStockPickingId());
            stockPicking.setStockPickingDocumentId(old.getStockPickingDocumentId());
            if (purchaseReturnConfirmRequest.getPurchaseReturnStatus() != null && purchaseReturnConfirmRequest.getPurchaseReturnStatus() == Consts.ORDER_STATUS_CONFIRMED) {
                stockPicking.setStockPickingStatus(Consts.ORDER_STATUS_CONFIRMED);
            } else {
                if (!purchaseReturnConfirmRequest.getAuditStatus()) {
                    int row = stockPickingService.cancelStockPicking(old.getStockPickingId());
                    if (row == 0) {
                        return HttpResponse.failure(ResultCode.PURCHASE_RETURN_CONFIRM_ERROR);
                    }
                    processInstanceService.delete(old.getStockPickingId());
                    return HttpResponse.success();
                }
                ProcessInstanceModifyRequest processInstanceModifyRequest = new ProcessInstanceModifyRequest();
                processInstanceModifyRequest.setBusinessId(old.getStockPickingId());
                processInstanceModifyRequest.setUserId(purchaseReturnConfirmRequest.getUserId());
                processInstanceModifyRequest.setRemark("通过, 执行");
                HttpResponse response = processInstanceService.audit(processInstanceModifyRequest);
                if (!StringUtils.equals(response.getCode(), "0") || response.getData() == null) {
                    return HttpResponse.failure(ResultCode.PURCHASE_RETURN_MODIFY_ERROR);
                }
                Integer instanceStatus = Integer.parseInt(response.getData().toString());
                if (instanceStatus != Consts.PROCESS_INSTANCE_STATUS_CONFIRMED) {
                    stockPicking.setStockPickingStatus(Consts.ORDER_STATUS_CONFIRMING);
                } else {
                    stockPicking.setStockPickingStatus(Consts.ORDER_STATUS_CONFIRMED);
                }
            }
            stockPicking.setStockPickingId(purchaseReturnConfirmRequest.getStockPickingId());
            if (!StringUtils.isEmpty(purchaseReturnConfirmRequest.getAuditorName())) {
                stockPicking.setAuditorName(purchaseReturnConfirmRequest.getAuditorName());
            }
            if (!StringUtils.isEmpty(purchaseReturnConfirmRequest.getAuditTime())) {
                stockPicking.setAuditorTime(DateFormatUtil.parseDateISO(purchaseReturnConfirmRequest.getAuditTime()));
            } else {
                stockPicking.setAuditorTime(new Date());
            }
            StockPickingGoodsRequest stockPickingGoodsRequest = new StockPickingGoodsRequest();
            stockPickingGoodsRequest.setStockPickingId(purchaseReturnConfirmRequest.getStockPickingId());
            List<StockPickingGoods> stockPickingGoodsList = stockPickingGoodsDao.select(stockPickingGoodsRequest);
            stockPicking.setStockPickingGoodsList(stockPickingGoodsList);
            stockPicking.setStockPickingType(Consts.STOCK_PICKING_TYPE_PURCHASE_RETURN);
            stockPicking.setSrcWarehouseId(old.getSrcWarehouseId());
            if (stockPicking.getStockPickingStatus() == Consts.ORDER_STATUS_CONFIRMED && stockPickingGoodsList != null && stockPickingGoodsList.size() > 0) {
                stockQuantService.updateQuantByStockPicking(stockPicking, Consts.STOCK_QUANT_CHANGE_TYPE_STOCK_PICKING);
                StockPicking newStockPicking = stockPickingDao.selectByStockPickingId(purchaseReturnConfirmRequest.getStockPickingId());
                newStockPicking.setAuditorTime(new Date());
                paymentService.synchronizedPurchaseOrder(new SynchronizedPurchaseOrderRequest(newStockPicking));
            }
            /**
             * 审核更新采购入库 商品的完成退货数
             */
            if(!StringUtils.isEmpty(old.getSrcOrderId())){
                Map<String, StockPickingGoods> map = getMap(old.getSrcOrderId());
                Long totalFinalNum = 0L;
                for(StockPickingGoods confirmGoods : stockPickingGoodsList){
                    String goodsId = confirmGoods.getGoodsId();
                    StockPickingGoods obj = map.get(goodsId);
                    if(obj == null){
                        continue;
                    }
                    Long returnNum = confirmGoods.getPickingAmount()!=null?confirmGoods.getPickingAmount():0;
                    Long finishNum = obj.getFinishPurchaseReturnAmount()!=null?obj.getFinishPurchaseReturnAmount():0;
                    obj.setFinishPurchaseReturnAmount(returnNum + finishNum);
                    totalFinalNum += finishNum;
                    int updateRow = stockPickingGoodsDao.updateByPickingGoodsId(obj);
                    if(updateRow <= 0){
                        throw new PurchaseReturnException("审核更新商品的完成退货数失败。");
                    }
                }
                stockPicking.setFinishPurchaseReturnAmount(totalFinalNum);
            }

            stockPickingDao.updateByStockPickingId(stockPicking);
            return HttpResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("确认采购退货单操作失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse commit(String stockPickingId) {
        try {
            StockPicking old = stockPickingDao.selectByStockPickingId(stockPickingId);
            if (old == null) {
                LOGGER.error("采购退货单号:[" + stockPickingId + "]不存在!");
                return HttpResponse.failure(ResultCode.PURCHASE_RETURN_DATA_NOT_FOUND);
            }
            StockPicking stockPicking = new StockPicking();
            stockPicking.setStockPickingId(stockPickingId);
            stockPicking.setStockPickingStatus(Consts.ORDER_STATUS_COMMITED);          //已提审
            int row = stockPickingDao.updateByStockPickingId(stockPicking);
            if (row <= 0) {
                throw new PurchaseReturnException("更新采购退货单失败");
            }
            ProcessInstanceCreateRequest processInstanceCreateRequest = new ProcessInstanceCreateRequest();
            processInstanceCreateRequest.setProcessType(Consts.PROCESS_TYPE_PURCHASE_RETURN);
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
            LOGGER.error("提审失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_SYSTEM_ERROR);
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

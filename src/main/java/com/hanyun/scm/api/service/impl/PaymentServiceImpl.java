package com.hanyun.scm.api.service.impl;

import com.hanyun.ground.util.http.HttpClient;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.StockPickingDao;
import com.hanyun.scm.api.domain.StockPicking;
import com.hanyun.scm.api.domain.dto.PurchasePaymentDTO;
import com.hanyun.scm.api.domain.request.payment.PurchasePaymentRequest;
import com.hanyun.scm.api.domain.request.payment.PurchaseQueryRequest;
import com.hanyun.scm.api.domain.request.payment.SynchronizedPurchaseOrderRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.service.PaymentService;
import com.hanyun.scm.api.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
 * PaymentServiceImpl
 * Date: 2017/3/10
 * Time: 下午3:39
 *
 * @author tianye@hanyun.com
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Resource
    private StockPickingDao stockPickingDao;

    @Override
    public HttpResponse queryOrder(PurchaseQueryRequest purchaseQueryRequest) {
        try {
            int count = stockPickingDao.countPurchaseOrder(purchaseQueryRequest);
            purchaseQueryRequest.setCount(count);
            List<PurchasePaymentDTO> purchasePaymentDTOList = stockPickingDao.queryPurchaseOrder(purchaseQueryRequest);
            BaseResponse response = new BaseResponse(count, purchasePaymentDTOList);
            return HttpResponse.success(response);
        } catch (Exception e) {
            LOGGER.error("查询采购入库和退货单失败!", e);
            return HttpResponse.failure(ResultCode.PAYMENT_QUERY_ORDER_ERROR);
        }
    }

    @Override
    public HttpResponse payment(List<PurchasePaymentRequest> purchasePaymentRequestList) {
        try {
            Map<String, Boolean> resultMap = new HashMap<>();
            for (PurchasePaymentRequest purchasePaymentRequest : purchasePaymentRequestList) {
                Long paymentPrice = purchasePaymentRequest.getPaymentPrice();
                StockPicking old = stockPickingDao.selectByStockPickingId(purchasePaymentRequest.getOrderId());
                if (old == null) {
                    continue;
                }
                Long oldPaymentPrice = old.getPaymentPrice();
                Long distinctPrice = old.getDistinctPrice();
                if (oldPaymentPrice == null) {
                    old.setPaymentPrice(0L);
                }
                if (distinctPrice == null) {
                    old.setDistinctPrice(0L);
                }
                Long totalPaymentPrice = old.getPaymentPrice() + paymentPrice;
                Long realPrice = old.getStockPickingPrice() - old.getDistinctPrice();
                if (totalPaymentPrice >= realPrice) {
                    purchasePaymentRequest.setPaymentStatus(2);
                } else {
                    purchasePaymentRequest.setPaymentStatus(1);
                }
                int updateRow = stockPickingDao.payment(purchasePaymentRequest);
                resultMap.put(purchasePaymentRequest.getOrderId(), updateRow > 0);
            }
            return HttpResponse.success(resultMap);
        } catch (Exception e) {
            LOGGER.error("更新付款金额失败!", e);
            return HttpResponse.failure(ResultCode.PAYMENT_PAY_ERROR);
        }
    }

    @Override
    public HttpResponse statistics(PurchaseQueryRequest purchaseQueryRequest) {
        try {
            PurchaseQueryRequest purchaseStockInRequest = JsonUtil.fromJson(JsonUtil.toJson(purchaseQueryRequest), PurchaseQueryRequest.class);
            purchaseStockInRequest.setPaymentType(Consts.PAYMENT_TYPE_PURCHASE_STOCK_IN);
            PurchasePaymentDTO purchasePaymentDTO = stockPickingDao.sumPurchaseOrder(purchaseStockInRequest);
            if (purchasePaymentDTO == null) {
                return HttpResponse.success(new PurchasePaymentDTO());
            }
            PurchaseQueryRequest purchaseReturnRequest = JsonUtil.fromJson(JsonUtil.toJson(purchaseQueryRequest), PurchaseQueryRequest.class);
            purchaseReturnRequest.setPaymentType(Consts.PAYMENT_TYPE_PURCHASE_RETURN);
            PurchasePaymentDTO purchaseReturnPaymentDTO = stockPickingDao.sumPurchaseOrder(purchaseReturnRequest);
            if (purchaseReturnPaymentDTO == null) {
                return HttpResponse.success(purchasePaymentDTO);
            }
            purchasePaymentDTO.setOrderPrice(purchasePaymentDTO.getOrderPrice() - purchaseReturnPaymentDTO.getOrderPrice());
            purchasePaymentDTO.setPaymentPrice(purchasePaymentDTO.getPaymentPrice() - purchaseReturnPaymentDTO.getPaymentPrice());
            purchasePaymentDTO.setDistinctPrice(purchasePaymentDTO.getDistinctPrice() - purchaseReturnPaymentDTO.getDistinctPrice());
            return HttpResponse.success(purchasePaymentDTO);
        } catch (Exception e) {
            LOGGER.error("采购付款统计失败!", e);
            return HttpResponse.failure(ResultCode.PAYMENT_STATICTICS_ERROR);
        }
    }

    @Override
    public void synchronizedPurchaseOrder(SynchronizedPurchaseOrderRequest synchronizedPurchaseOrderRequest) {
        try {
            Properties properties = PropertiesUtil.getProperties("erp-api.properties");
            String finPayBillSrcUrl = properties.getProperty("finPayBillSrcUrl");
            String result = HttpClient.post(finPayBillSrcUrl).json(synchronizedPurchaseOrderRequest).action().result();
            HttpResponse response = JsonUtil.fromJson(result, HttpResponse.class);
            if (!"0".equals(response.getCode())) {
                LOGGER.error("与结算系统同步采购单失败!");
            }
        } catch (Exception e) {
            LOGGER.error("与结算系统同步采购单失败!", e);
        }
    }
}

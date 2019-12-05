package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.request.payment.PurchasePaymentRequest;
import com.hanyun.scm.api.domain.request.payment.PurchaseQueryRequest;
import com.hanyun.scm.api.domain.request.payment.SynchronizedPurchaseOrderRequest;

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
 * PaymentService
 * Date: 2017/3/10
 * Time: 下午3:32
 *
 * @author tianye@hanyun.com
 */
public interface PaymentService {

    /**
     * 查询采购入库单和退货单
     *
     * @param purchaseQueryRequest 参数
     * @return 返回值
     */
    HttpResponse queryOrder(PurchaseQueryRequest purchaseQueryRequest);

    /**
     * 采购收付款
     *
     * @param purchasePaymentRequestList 参数
     * @return 返回值
     */
    HttpResponse payment(List<PurchasePaymentRequest> purchasePaymentRequestList);

    /**
     * 统计
     * @param purchaseQueryRequest  参数
     * @return  返回值
     */
    HttpResponse statistics(PurchaseQueryRequest purchaseQueryRequest);

    /**
     * 同步采购单
     * @param synchronizedPurchaseOrderRequest  请求参数
     */
    void synchronizedPurchaseOrder(SynchronizedPurchaseOrderRequest synchronizedPurchaseOrderRequest);
}

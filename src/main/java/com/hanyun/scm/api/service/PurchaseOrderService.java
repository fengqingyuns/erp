package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.PurchaseOrder;
import com.hanyun.scm.api.domain.PurchaseOrderDetail;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderConfirmRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderCreateRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.exception.PurchaseOrderException;

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
 * <p>
 * GoodsOdooService Date: 2016/10/23 Time: 上午9:56
 *
 * @author tianye@hanyun.com
 */
public interface PurchaseOrderService {

    /**
     * 查询采购订单
     *
     * @param purchaseOrderQueryRequest 查询参数
     * @return 返回值
     */
    HttpResponse select(PurchaseOrderQueryRequest purchaseOrderQueryRequest);

    /**
     * 修改采购订单
     *
     * @param purchaseOrderModifyRequest 修改参数
     * @return 返回值
     */
    HttpResponse modify(PurchaseOrderModifyRequest purchaseOrderModifyRequest);

    /**
     * 采购订单详情
     *
     * @param orderId 订单id
     * @return 返回值
     */
    HttpResponse detail(String orderId);

    /**
     * 删除采购订单
     *
     * @param orderId 订单id
     * @return 返回值
     */
    HttpResponse delete(String orderId);

    /**
     * 查询采购订单(for 采购入库和采购退货)
     *
     * @param purchaseOrderQueryRequest 查询采购订单参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse selectPurchaseOrderForStockInAndReturn(PurchaseOrderQueryRequest purchaseOrderQueryRequest);

    /**
     * 审核采购订单
     *
     * @param purchaseOrderConfirmRequest 审核参数
     * @return 返回值
     * @author tianye@hanyun.com
     */
    HttpResponse confirm(PurchaseOrderConfirmRequest purchaseOrderConfirmRequest);

    /**
     * 创建采购订单
     *
     * @param purchaseOrderCreateRequest 创建采购订单参数
     * @return 返回值
     */
    HttpResponse create(PurchaseOrderCreateRequest purchaseOrderCreateRequest);

    /**
     * 查询采购订单列表
     *
     * @param purchaseOrderQueryRequest 请求参数
     * @param excel                     是否导出excel
     * @return HttpResponse
     * @throws PurchaseOrderException 异常
     * @author tianye@hanyun.com
     */
    BaseResponse selectList(PurchaseOrderQueryRequest purchaseOrderQueryRequest, boolean excel) throws PurchaseOrderException;

    /**
     * 查询采购订单(for export)
     *
     * @param orderId 采购订单id
     * @return 返回信息
     * @throws PurchaseOrderException 异常信息
     */
    PurchaseOrder getOrder(String orderId) throws PurchaseOrderException;


    HttpResponse queryForStockIn(PurchaseOrderQueryRequest purchaseOrderQueryRequest);

    HttpResponse selectDetailForStockIn(String orderId);

    /**
     * 提交审核
     * @param orderId   订单id
     * @return  返回值
     */
    HttpResponse commit(String orderId);

    /**
     * 关闭采购订单状态
     * @param orderId   订单id
     * @return  返回值
     */
    HttpResponse close(String orderId);

    HttpResponse queryStockInList(String orderId);
}

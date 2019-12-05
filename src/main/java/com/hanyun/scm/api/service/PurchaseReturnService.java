package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.request.purchase.PurchaseReturnConfirmRequest;
import com.hanyun.scm.api.domain.request.purchase.PurchaseReturnCreateRequest;
import com.hanyun.scm.api.domain.request.purchase.PurchaseReturnModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.PurchaseReturnQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.exception.PurchaseReturnException;

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
 * PurchaseReturnService
 * Date: 2016/10/27
 * Time: 下午12:08
 *
 * @author tianye@hanyun.com
 */
public interface PurchaseReturnService {
    /**
     * 创建采购退货单
     *
     * @param purchaseReturnCreateRequest 创建采购退货单参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse create(PurchaseReturnCreateRequest purchaseReturnCreateRequest);

    /**
     * 查询退货单详情
     *
     * @param stockPickingId 退货单id
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse detail(String stockPickingId);

    /**
     * 查询采购退货单列表
     *
     * @param purchaseReturnQueryRequest 查询采购退货单参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse select(PurchaseReturnQueryRequest purchaseReturnQueryRequest);

    /**
     * 修改采购退货单
     *
     * @param purchaseReturnModifyRequest 修改采购退货单参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse modify(PurchaseReturnModifyRequest purchaseReturnModifyRequest);

    /**
     * 删除采购退货单
     *
     * @param stockPickingId 退货单id
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse delete(String stockPickingId);

    /**
     * 查询采购订单详情
     *
     * @param purchaseOrderId 采购订单id
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse detailPurchaseOrder(String purchaseOrderId);

    /**
     * 确认采购退货单
     *
     * @param purchaseReturnConfirmRequest 确认退货单参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse confirm(PurchaseReturnConfirmRequest purchaseReturnConfirmRequest);

    /**
     * 查询采购退货单列表
     *
     * @param purchaseReturnQueryRequest 请求参数
     * @param excel                      是否导出excel
     * @return HttpResponse
     * @throws PurchaseReturnException 异常
     * @author tianye@hanyun.com
     */
    BaseResponse selectList(PurchaseReturnQueryRequest purchaseReturnQueryRequest, boolean excel) throws PurchaseReturnException;

    /**
     * 提审采购退货单
     *
     * @param stockPickingId 退货单id
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse commit(String stockPickingId);
}

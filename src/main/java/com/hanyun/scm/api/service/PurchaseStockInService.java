package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.request.purchase.PurchaseStockInConfirmRequest;
import com.hanyun.scm.api.domain.request.purchase.PurchaseStockInCreateRequest;
import com.hanyun.scm.api.domain.request.purchase.PurchaseStockInModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.PurchaseStockInQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.exception.PurchaseStockInException;

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
 * PurchaseStockInService
 * Date: 2016/10/27
 * Time: 下午12:08
 *
 * @author tianye@hanyun.com
 */
public interface PurchaseStockInService {
    /**
     * 创建采购入库单
     *
     * @param purchaseStockInCreateRequest 创建采购入库单参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse create(PurchaseStockInCreateRequest purchaseStockInCreateRequest);

    /**
     * 查询入库单详情
     *
     * @param stockPickingId 入库单id
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse detail(String stockPickingId);

    /**
     * 查询采购入库单列表
     *
     * @param purchaseStockInQueryRequest 查询采购入库单参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse select(PurchaseStockInQueryRequest purchaseStockInQueryRequest);

    /**
     * 修改采购入库单
     *
     * @param purchaseStockInModifyRequest 修改采购入库单参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse modify(PurchaseStockInModifyRequest purchaseStockInModifyRequest);

    /**
     * 删除采购入库单
     *
     * @param stockPickingId 入库单id
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
     * 确认采购入库单
     *
     * @param purchaseStockInConfirmRequest 确认采购入库单参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse confirm(PurchaseStockInConfirmRequest purchaseStockInConfirmRequest);
    /**
     * 提审采购入库单
     *
     * @param stockPickingId 入库单id
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse commit(String stockPickingId);

    BaseResponse selectList(PurchaseStockInQueryRequest purchaseStockInQueryRequest, boolean excel) throws PurchaseStockInException;
}

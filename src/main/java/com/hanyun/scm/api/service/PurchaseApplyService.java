package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.PurchaseApply;
import com.hanyun.scm.api.domain.request.purchase.apply.PurchaseApplyConfirmRequest;
import com.hanyun.scm.api.domain.request.purchase.apply.PurchaseApplyCreateRequest;
import com.hanyun.scm.api.domain.request.purchase.apply.PurchaseApplyModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.apply.PurchaseApplyQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.exception.PurchaseApplyException;

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
 * PurchaseApplyService
 * Date: 2016/10/27
 * Time: 下午12:08
 *
 * @author tianye@hanyun.com
 */
public interface PurchaseApplyService {
    /**
     * 创建采购退货单
     *
     * @param purchaseApplyCreateRequest 创建采购退货单参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse create(PurchaseApplyCreateRequest purchaseApplyCreateRequest);

    /**
     * 查询申请单详情
     *
     * @param stockPickingId 申请单id
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse detail(String stockPickingId);

    /**
     * 查询采购申请单列表
     *
     * @param purchaseApplyQueryRequest 查询采购申请单参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse select(PurchaseApplyQueryRequest purchaseApplyQueryRequest);

    /**
     * 修改采购申请单
     *
     * @param purchaseApplyModifyRequest 修改采购申请单参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse modify(PurchaseApplyModifyRequest purchaseApplyModifyRequest);

    /**
     * 删除采购申请单
     *
     * @param stockPickingId 申请单id
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse delete(String stockPickingId);

    /**
     * 确认采购申请单
     *
     * @param purchaseApplyConfirmRequest 确认申请单参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse confirm(PurchaseApplyConfirmRequest purchaseApplyConfirmRequest);

    /**
     * 查询采购申请单列表
     *
     * @param purchaseApplyQueryRequest 请求参数
     * @param excel                     是否导出excel
     * @return HttpResponse
     * @throws PurchaseApplyException 异常
     * @author tianye@hanyun.com
     */
    BaseResponse selectList(PurchaseApplyQueryRequest purchaseApplyQueryRequest, boolean excel) throws PurchaseApplyException;

    /**
     * 查询申请单(for export)
     *
     * @param applyId 申请单id
     * @return 返回信息
     * @throws PurchaseApplyException 异常信息
     */
    PurchaseApply getApply(String applyId) throws PurchaseApplyException;

    /**
     * 申请单提审
     * @param applyId   申请单id
     * @return  返回值
     */
    HttpResponse commit(String applyId);
}

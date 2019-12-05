package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.PurchasePlan;
import com.hanyun.scm.api.domain.request.purchase.plan.PurchasePlanConfirmRequest;
import com.hanyun.scm.api.domain.request.purchase.plan.PurchasePlanCreateRequest;
import com.hanyun.scm.api.domain.request.purchase.plan.PurchasePlanModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.plan.PurchasePlanQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.exception.PurchasePlanException;

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
 * PurchasePlanService
 * Date: 2016/10/27
 * Time: 下午12:08
 *
 * @author tianye@hanyun.com
 */
public interface PurchasePlanService {
    /**
     * 创建采购计划单
     *
     * @param purchasePlanCreateRequest 创建采购计划单参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse create(PurchasePlanCreateRequest purchasePlanCreateRequest);

    /**
     * 查询计划单详情
     *
     * @param stockPickingId 计划单id
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse detail(String stockPickingId);

    /**
     * 查询采购计划单列表
     *
     * @param purchasePlanQueryRequest 查询采购计划单参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse select(PurchasePlanQueryRequest purchasePlanQueryRequest);

    /**
     * 修改采购计划单
     *
     * @param purchasePlanModifyRequest 修改采购计划单参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse modify(PurchasePlanModifyRequest purchasePlanModifyRequest);

    /**
     * 删除采购计划单
     *
     * @param stockPickingId 计划单id
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse delete(String stockPickingId);

    /**
     * 确认采购计划单
     *
     * @param purchasePlanConfirmRequest 确认计划单参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse confirm(PurchasePlanConfirmRequest purchasePlanConfirmRequest);

    /**
     * 查询采购计划单列表
     *
     * @param purchasePlanQueryRequest 请求参数
     * @param excel                    是否导出excel
     * @return HttpResponse
     * @throws PurchasePlanException 异常
     * @author tianye@hanyun.com
     */
    BaseResponse selectList(PurchasePlanQueryRequest purchasePlanQueryRequest, boolean excel) throws PurchasePlanException;

    /**
     * 查询计划单(for export)
     *
     * @param planId 计划单id
     * @return 返回信息
     * @throws PurchasePlanException 异常信息
     */
    PurchasePlan getPlan(String planId) throws PurchasePlanException;

    /**
     * 采购计划提审
     * @param planId    计划单号
     * @return  返回值
     */
    HttpResponse commit(String planId);
}

package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.request.BrandStoreRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderStatisticsRequest;

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
 * StatisticsService
 * Date: 2017/2/21
 * Time: 下午6:12
 *
 * @author tianye@hanyun.com
 */
public interface StatisticsService {

    /**
     * 统计信息
     * @param brandStoreRequest 请求参数
     * @return  返回信息
     */
    HttpResponse statistics(BrandStoreRequest brandStoreRequest);

    /**
     *
     * @param purchaseOrderStatisticsRequest    请求参数
     * @return  返回信息
     */
    HttpResponse purchaseStatistics(PurchaseOrderStatisticsRequest purchaseOrderStatisticsRequest);
}

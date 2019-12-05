package com.hanyun.scm.api.domain.request.purchase.order;

import com.hanyun.scm.api.domain.request.BrandStoreRequest;

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
 * PurchaseOrderStatisticsRequest
 * Date: 2017/5/5
 * Time: 下午5:23
 *
 * @author tianye@hanyun.com
 */
public class PurchaseOrderStatisticsRequest extends BrandStoreRequest{

    private String queryBeginTime;

    private String queryEndTime;

    public String getQueryBeginTime() {
        return queryBeginTime;
    }

    public void setQueryBeginTime(String queryBeginTime) {
        this.queryBeginTime = queryBeginTime;
    }

    public String getQueryEndTime() {
        return queryEndTime;
    }

    public void setQueryEndTime(String queryEndTime) {
        this.queryEndTime = queryEndTime;
    }
}

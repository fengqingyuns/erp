package com.hanyun.scm.api.domain.response;

import com.hanyun.scm.api.domain.response.stock.TotalStockResponse;

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
 * StatisticsResponse
 * Date: 2017/2/21
 * Time: 下午6:06
 *
 * @author tianye@hanyun.com
 */
public class StatisticsResponse {

    private Long totalAmount;

    private Long totalCash;

    private Long waitForCheckCount;

    public StatisticsResponse() {}

    public StatisticsResponse(TotalStockResponse totalStockResponse, Long waitForCheckCount) {
        if (totalStockResponse != null) {
            this.totalAmount = totalStockResponse.getTotalAmount();
            this.totalCash = totalStockResponse.getTotalCash();
        }
        this.waitForCheckCount = waitForCheckCount;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(Long totalCash) {
        this.totalCash = totalCash;
    }

    public Long getWaitForCheckCount() {
        return waitForCheckCount;
    }

    public void setWaitForCheckCount(Long waitForCheckCount) {
        this.waitForCheckCount = waitForCheckCount;
    }
}

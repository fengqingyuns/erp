package com.hanyun.scm.api.domain.request.purchase.order;

import com.hanyun.scm.api.domain.PurchaseOrder;

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
 * PurchaseOrderBaseRequest
 * Date: 2017/3/1
 * Time: 上午9:57
 *
 * @author tianye@hanyun.com
 */
class PurchaseOrderBaseRequest extends PurchaseOrder {

    private String stockInStatus;

    public String getStockInStatus() {
        return stockInStatus;
    }

    public void setStockInStatus(String stockInStatus) {
        this.stockInStatus = stockInStatus;
    }
}

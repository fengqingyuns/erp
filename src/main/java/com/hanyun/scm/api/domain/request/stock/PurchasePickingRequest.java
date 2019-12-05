package com.hanyun.scm.api.domain.request.stock;

import java.util.Set;

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
 * PurchasePickingRequest
 * Date: 2016/10/31
 * Time: 下午9:56
 *
 * @author tianye@hanyun.com
 */
public class PurchasePickingRequest {

    private String srcOrderId;

    private Set<String> ids;

    public String getSrcOrderId() {
        return srcOrderId;
    }

    public void setSrcOrderId(String srcOrderId) {
        this.srcOrderId = srcOrderId;
    }

    public Set<String> getIds() {
        return ids;
    }

    public void setIds(Set<String> ids) {
        this.ids = ids;
    }
}

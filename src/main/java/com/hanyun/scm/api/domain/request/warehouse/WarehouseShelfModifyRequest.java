package com.hanyun.scm.api.domain.request.warehouse;

import com.hanyun.scm.api.domain.WarehouseShelf;
import org.hibernate.validator.constraints.NotEmpty;

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
 * WarehouseShelfModifyRequest
 * Date: 2017/7/13
 * Time: 下午4:17
 *
 * @author tianye@hanyun.com
 */
public class WarehouseShelfModifyRequest extends WarehouseShelf {

    @NotEmpty
    private String shelfId;

    @Override
    public String getShelfId() {
        return shelfId;
    }

    @Override
    public void setShelfId(String shelfId) {
        this.shelfId = shelfId;
    }
}

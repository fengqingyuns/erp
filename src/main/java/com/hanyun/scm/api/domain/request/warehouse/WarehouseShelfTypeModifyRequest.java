package com.hanyun.scm.api.domain.request.warehouse;

import com.hanyun.scm.api.domain.WarehouseShelfType;
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
 * WarehouseShelfTypeModifyRequest
 * Date: 2017/7/13
 * Time: 下午12:06
 *
 * @author tianye@hanyun.com
 */
public class WarehouseShelfTypeModifyRequest extends WarehouseShelfType{

    @NotEmpty
    private String shelfTypeId;

    @Override
    public String getShelfTypeId() {
        return shelfTypeId;
    }

    @Override
    public void setShelfTypeId(String shelfTypeId) {
        this.shelfTypeId = shelfTypeId;
    }
}

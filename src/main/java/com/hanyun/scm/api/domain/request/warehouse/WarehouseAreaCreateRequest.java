package com.hanyun.scm.api.domain.request.warehouse;

import com.hanyun.scm.api.domain.WarehouseArea;
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
 * WarehouseAreaCreateRequest
 * Date: 2017/7/12
 * Time: 下午6:48
 *
 * @author tianye@hanyun.com
 */
public class WarehouseAreaCreateRequest extends WarehouseArea {

    @NotEmpty
    private String brandId;

    @NotEmpty
    private String warehouseId;

    @Override
    public String getBrandId() {
        return brandId;
    }

    @Override
    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    @Override
    public String getWarehouseId() {
        return warehouseId;
    }

    @Override
    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }
}

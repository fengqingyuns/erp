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
 * WarehouseShelfCreateRequest
 * Date: 2017/7/13
 * Time: 下午4:05
 *
 * @author tianye@hanyun.com
 */
public class WarehouseShelfCreateRequest extends WarehouseShelf {

    @NotEmpty
    private String brandId;

    @NotEmpty
    private String warehouseId;

    @NotEmpty
    private String areaId;

    @NotEmpty
    private String shelfTypeId;

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

    @Override
    public String getAreaId() {
        return areaId;
    }

    @Override
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    @Override
    public String getShelfTypeId() {
        return shelfTypeId;
    }

    @Override
    public void setShelfTypeId(String shelfTypeId) {
        this.shelfTypeId = shelfTypeId;
    }
}

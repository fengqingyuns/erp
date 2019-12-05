package com.hanyun.scm.api.domain.request.warehouse;

import com.hanyun.scm.api.domain.Warehouse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * WarehouseCreateRequest
 * Date: 2016/12/15
 * Time: 上午11:56
 *
 * @author tianye@hanyun.com
 */
@ApiModel(value = "创建仓库参数")
public class WarehouseCreateRequest extends Warehouse {

    @NotEmpty
    @ApiModelProperty(value = "品牌id")
    private String brandId;

    @NotEmpty
    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

    @Override
    public String getBrandId() {
        return brandId;
    }

    @Override
    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    @Override
    public String getWarehouseName() {
        return warehouseName;
    }

    @Override
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
}

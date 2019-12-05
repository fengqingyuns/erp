package com.hanyun.scm.api.domain.request.warehouse;

import com.hanyun.scm.api.domain.WarehouseShelfType;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
 * WarehouseShelfTypeCreateRequest
 * Date: 2017/7/13
 * Time: 下午12:00
 *
 * @author tianye@hanyun.com
 */
public class WarehouseShelfTypeCreateRequest extends WarehouseShelfType {

    @NotEmpty
    private String brandId;

    @NotEmpty
    private String warehouseId;

    @Min(1)
    private Integer shelfStorageNum;

    @Min(1)
    private Integer shelfLength;

    @Min(1)
    private Integer shelfWidth;

    @Min(1)
    private Integer shelfHeight;

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
    public Integer getShelfStorageNum() {
        return shelfStorageNum;
    }

    @Override
    public void setShelfStorageNum(Integer shelfStorageNum) {
        this.shelfStorageNum = shelfStorageNum;
    }

    @Override
    public Integer getShelfLength() {
        return shelfLength;
    }

    @Override
    public void setShelfLength(Integer shelfLength) {
        this.shelfLength = shelfLength;
    }

    @Override
    public Integer getShelfWidth() {
        return shelfWidth;
    }

    @Override
    public void setShelfWidth(Integer shelfWidth) {
        this.shelfWidth = shelfWidth;
    }

    @Override
    public Integer getShelfHeight() {
        return shelfHeight;
    }

    @Override
    public void setShelfHeight(Integer shelfHeight) {
        this.shelfHeight = shelfHeight;
    }
}

package com.hanyun.scm.api.domain.request.stock;

import com.hanyun.scm.api.domain.StockPickingGoods;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

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
 * SellGoodsRequest
 * Date: 2017/1/10
 * Time: 下午6:04
 *
 * @author tianye@hanyun.com
 */
public class SellGoodsRequest {

    @NotEmpty(message = "品牌id不能为空")
    private String brandId; // 品牌id

    @NotEmpty(message = "门店id不能为空")
    private String storeId; // 门店id

    private String toWarehouseId; // 目标仓库id

    private String toWarehouseName; // 目标仓库名称

    @NotNull(message = "售卖商品不能为空")
    private List<StockPickingGoods> stockPickingGoodsList; // 商品列表

    private String stockPickingId;

    private String stockPickingDocumentId;

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getToWarehouseId() {
        return toWarehouseId;
    }

    public void setToWarehouseId(String toWarehouseId) {
        this.toWarehouseId = toWarehouseId;
    }

    public String getToWarehouseName() {
        return toWarehouseName;
    }

    public void setToWarehouseName(String toWarehouseName) {
        this.toWarehouseName = toWarehouseName;
    }

    public List<StockPickingGoods> getStockPickingGoodsList() {
        return stockPickingGoodsList;
    }

    public void setStockPickingGoodsList(List<StockPickingGoods> stockPickingGoodsList) {
        this.stockPickingGoodsList = stockPickingGoodsList;
    }

    public String getStockPickingDocumentId() {
        return stockPickingDocumentId;
    }

    public void setStockPickingDocumentId(String stockPickingDocumentId) {
        this.stockPickingDocumentId = stockPickingDocumentId;
    }

    public String getStockPickingId() {
        return stockPickingId;
    }

    public void setStockPickingId(String stockPickingId) {
        this.stockPickingId = stockPickingId;
    }
}

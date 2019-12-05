package com.hanyun.scm.api.domain.request.purchase;

import com.hanyun.scm.api.domain.StockPicking;
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
 * PurchaseReturnCreateRequest
 * Date: 2016/12/9
 * Time: 上午10:31
 *
 * @author tianye@hanyun.com
 */
public class PurchaseReturnCreateRequest extends PurchaseReturnBaseRequest {

    @NotEmpty
    private String brandId;             //品牌id

    @NotEmpty
    private String supplierId;          //供应商id(采购出入库使用)

    @NotEmpty
    private String srcWarehouseId;      //来源仓库id

    @NotNull
    private List<StockPickingGoods> stockPickingGoodsList;

    private Integer validStatus;

    public StockPicking getStockPicking() {
        return new StockPicking(null, null, brandId, storeId, srcOrderId,
                srcOrderDocumentId, supplierId, supplierName, srcWarehouseId, srcWarehouseName, toWarehouseId,
                toWarehouseName, purchasePrice, purchaseAmount, stockPickingPrice, pickingAmount, stockPickingStatus,
                operatorId, operatorName, stockPickingType, stock, incomeReason, outReason, remark, createTime, updateTime,
                validStatus, null, businessManId, businessManName, distinctPrice, purchaseReturnTime, purchaseReturnReason);
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSrcWarehouseId() {
        return srcWarehouseId;
    }

    public void setSrcWarehouseId(String srcWarehouseId) {
        this.srcWarehouseId = srcWarehouseId;
    }

    public List<StockPickingGoods> getStockPickingGoodsList() {
        return stockPickingGoodsList;
    }

    public void setStockPickingGoodsList(List<StockPickingGoods> stockPickingGoodsList) {
        this.stockPickingGoodsList = stockPickingGoodsList;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }
}

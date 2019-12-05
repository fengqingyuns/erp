package com.hanyun.scm.api.domain.request.purchase;

import com.hanyun.scm.api.domain.StockPicking;
import com.hanyun.scm.api.domain.StockPickingGoods;
import com.hanyun.scm.api.domain.dto.CreatePurchaseStockInDTO;
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
public class PurchaseStockInCreateRequest extends PurchaseStockInBaseRequest {

    @NotEmpty
    private String brandId;             //品牌id

    @NotEmpty
    private String supplierId;          //供应商id(采购出入库使用)

    @NotEmpty
    private String toWarehouseId;       //目标仓库id

    @NotNull
    private List<StockPickingGoods> stockPickingGoodsList;

    public PurchaseStockInCreateRequest() {}

    public PurchaseStockInCreateRequest(CreatePurchaseStockInDTO createPurchaseStockInDTO) {
        this.setBrandId(createPurchaseStockInDTO.getBrandId());
        this.setStoreId(createPurchaseStockInDTO.getStoreId());
        this.setStoreName(createPurchaseStockInDTO.getStoreName());
        this.setSupplierId(createPurchaseStockInDTO.getSupplierId());
        this.setSupplierName(createPurchaseStockInDTO.getSupplierName());
        this.setToWarehouseId(createPurchaseStockInDTO.getToWarehouseId());
        this.setToWarehouseName(createPurchaseStockInDTO.getToWarehouseName());
        this.setOperatorId(createPurchaseStockInDTO.getOperatorId());
        this.setOperatorName(createPurchaseStockInDTO.getOperatorName());
        this.setStockPickingType(createPurchaseStockInDTO.getStockPickingType());
        this.setStockPickingPrice(createPurchaseStockInDTO.getStockPickingPrice());
        this.setPickingAmount(createPurchaseStockInDTO.getPickingAmount());
        this.setPurchaseType(createPurchaseStockInDTO.getPurchaseType());
        this.setBusinessManId(createPurchaseStockInDTO.getBusinessManId());
        this.setBusinessManName(createPurchaseStockInDTO.getBusinessManName());
        this.setRemark(createPurchaseStockInDTO.getRemark());
        this.setDistinctPrice(createPurchaseStockInDTO.getDistinctPrice());
        this.setSrcOrderId(createPurchaseStockInDTO.getSrcOrderId());
        this.setSrcOrderDocumentId(createPurchaseStockInDTO.getSrcOrderDocumentId());
        this.setStockPickingGoodsList(createPurchaseStockInDTO.getStockPickingGoodsList());
    }

    public StockPicking getStockPicking() {
        return new StockPicking(null, null, brandId, storeId, srcOrderId,
                srcOrderDocumentId, supplierId, supplierName, srcWarehouseId, srcWarehouseName, toWarehouseId,
                toWarehouseName, purchasePrice, purchaseAmount, stockPickingPrice, pickingAmount, stockPickingStatus,
                operatorId, operatorName, stockPickingType, stock, incomeReason, outReason, remark, createTime, updateTime,
                validStatus, purchaseType, businessManId, businessManName, distinctPrice, purchaseReturnTime, purchaseReturnReason);
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

    public String getToWarehouseId() {
        return toWarehouseId;
    }

    public void setToWarehouseId(String toWarehouseId) {
        this.toWarehouseId = toWarehouseId;
    }

    public List<StockPickingGoods> getStockPickingGoodsList() {
        return stockPickingGoodsList;
    }

    public void setStockPickingGoodsList(List<StockPickingGoods> stockPickingGoodsList) {
        this.stockPickingGoodsList = stockPickingGoodsList;
    }
}

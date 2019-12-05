package com.hanyun.scm.api.domain.dto;

import com.hanyun.scm.api.domain.StockPickingGoods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
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
 * CreatePurchaseStockInDTO
 * Date: 2017/6/5
 * Time: 下午3:04
 *
 * @author tianye@hanyun.com
 */
@ApiModel(value = "编辑采购入库单参数实体")
public class ModifyPurchaseStockInDTO {

    @ApiModelProperty(value = "品牌id", required = true)
    @NotEmpty
    private String brandId;

    @ApiModelProperty(value = "采购入库单id", required = true)
    @NotEmpty
    private String stockPickingId;

    @ApiModelProperty(value = "门店id")
    private String storeId;

    @ApiModelProperty(value = "门店名称")
    private String storeName;

    @ApiModelProperty(value = "供应商id", required = true)
    @NotEmpty
    private String supplierId;

    @ApiModelProperty(value = "供应商名称", required = true)
    @NotEmpty
    private String supplierName;

    @ApiModelProperty(value = "入库仓id", required = true)
    @NotEmpty
    private String toWarehouseId;

    @ApiModelProperty(value = "入库仓名称", required = true)
    @NotEmpty
    private String toWarehouseName;

    @ApiModelProperty(value = "操作人id")
    private String operatorId;

    @ApiModelProperty(value = "操作人名称")
    private String operatorName;

    @ApiModelProperty(value = "库存移动类型,采购入库传0,可不传")
    private Integer stockPickingType;

    @ApiModelProperty(value = "入库金额")
    @NotNull
    @Min(1)
    private Long stockPickingPrice;

    @ApiModelProperty(value = "采购入库数量")
    @NotNull
    @Min(1)
    private Long pickingAmount;

    @ApiModelProperty(value = "采购类型  0、统采  1、自采")
    private Integer purchaseType;

    @ApiModelProperty(value = "收货员id")
    @NotEmpty
    private String businessManId;

    @ApiModelProperty(value = "收货员名称")
    @NotEmpty
    private String businessManName;

    @ApiModelProperty(value = "摘要")
    private String remark;

    @ApiModelProperty(value = "优惠金额")
    private Long distinctPrice;

    @ApiModelProperty(value = "源采购订单id")
    @NotEmpty
    private String srcOrderId;

    @ApiModelProperty(value = "原采购订单编号(展示编号)")
    @NotEmpty
    private String srcOrderDocumentId;

    @ApiModelProperty(value = "采购入库商品列表")
    @NotNull
    private List<StockPickingGoods> stockPickingGoodsList;

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getStockPickingId() {
        return stockPickingId;
    }

    public void setStockPickingId(String stockPickingId) {
        this.stockPickingId = stockPickingId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Integer getStockPickingType() {
        return stockPickingType;
    }

    public void setStockPickingType(Integer stockPickingType) {
        this.stockPickingType = stockPickingType;
    }

    public Long getStockPickingPrice() {
        return stockPickingPrice;
    }

    public void setStockPickingPrice(Long stockPickingPrice) {
        this.stockPickingPrice = stockPickingPrice;
    }

    public Long getPickingAmount() {
        return pickingAmount;
    }

    public void setPickingAmount(Long pickingAmount) {
        this.pickingAmount = pickingAmount;
    }

    public Integer getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(Integer purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getBusinessManId() {
        return businessManId;
    }

    public void setBusinessManId(String businessManId) {
        this.businessManId = businessManId;
    }

    public String getBusinessManName() {
        return businessManName;
    }

    public void setBusinessManName(String businessManName) {
        this.businessManName = businessManName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getDistinctPrice() {
        return distinctPrice;
    }

    public void setDistinctPrice(Long distinctPrice) {
        this.distinctPrice = distinctPrice;
    }

    public String getSrcOrderId() {
        return srcOrderId;
    }

    public void setSrcOrderId(String srcOrderId) {
        this.srcOrderId = srcOrderId;
    }

    public String getSrcOrderDocumentId() {
        return srcOrderDocumentId;
    }

    public void setSrcOrderDocumentId(String srcOrderDocumentId) {
        this.srcOrderDocumentId = srcOrderDocumentId;
    }

    public List<StockPickingGoods> getStockPickingGoodsList() {
        return stockPickingGoodsList;
    }

    public void setStockPickingGoodsList(List<StockPickingGoods> stockPickingGoodsList) {
        this.stockPickingGoodsList = stockPickingGoodsList;
    }
}

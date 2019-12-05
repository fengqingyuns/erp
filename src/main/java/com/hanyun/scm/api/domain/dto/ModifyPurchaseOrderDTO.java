package com.hanyun.scm.api.domain.dto;

import com.hanyun.scm.api.domain.PurchaseOrderDetail;
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
 * CreatePurchaseOrderDTO
 * Date: 2017/6/2
 * Time: 下午4:34
 *
 * @author tianye@hanyun.com
 */
@ApiModel(value = "创建采购订单参数实体")
public class ModifyPurchaseOrderDTO {

    @ApiModelProperty(value = "采购订单id", required = true)
    @NotEmpty
    private String orderId;

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

    @ApiModelProperty(value = "采购员id", required = true)
    @NotEmpty
    private String purchaseUserId;

    @ApiModelProperty(value = "采购员名称", required = true)
    @NotEmpty
    private String purchaseUserName;

    @ApiModelProperty(value = "采购类型", required = true)
    @NotNull
    private Integer purchaseType;

    @ApiModelProperty(value = "操作员id(当前登录人)", required = true)
    @NotEmpty
    private String operatorId;

    @ApiModelProperty(value = "操作员名称")
    private String operatorName;

    @ApiModelProperty(value = "总数量", required = true)
    @NotNull
    @Min(1)
    private Long totalAmount;

    @ApiModelProperty(value = "总金额")
    @NotNull
    @Min(1)
    private Long totalPrice;

    @ApiModelProperty(value = "优惠金额")
    private Long distinctPrice;

    @ApiModelProperty(value = "计划时间")
    private String planTimeString;

    private String expectedArrivalDate;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "采购商品列表", required = true)
    @NotNull
    private List<PurchaseOrderDetail> purchaseOrderDetailList;

    public String getExpectedArrivalDate() {
        return expectedArrivalDate;
    }

    public void setExpectedArrivalDate(String expectedArrivalDate) {
        this.expectedArrivalDate = expectedArrivalDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getPurchaseUserId() {
        return purchaseUserId;
    }

    public void setPurchaseUserId(String purchaseUserId) {
        this.purchaseUserId = purchaseUserId;
    }

    public String getPurchaseUserName() {
        return purchaseUserName;
    }

    public void setPurchaseUserName(String purchaseUserName) {
        this.purchaseUserName = purchaseUserName;
    }

    public Integer getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(Integer purchaseType) {
        this.purchaseType = purchaseType;
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

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPlanTimeString() {
        return planTimeString;
    }

    public void setPlanTimeString(String planTimeString) {
        this.planTimeString = planTimeString;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<PurchaseOrderDetail> getPurchaseOrderDetailList() {
        return purchaseOrderDetailList;
    }

    public void setPurchaseOrderDetailList(List<PurchaseOrderDetail> purchaseOrderDetailList) {
        this.purchaseOrderDetailList = purchaseOrderDetailList;
    }

    public Long getDistinctPrice() {
        return distinctPrice;
    }

    public void setDistinctPrice(Long distinctPrice) {
        this.distinctPrice = distinctPrice;
    }
}

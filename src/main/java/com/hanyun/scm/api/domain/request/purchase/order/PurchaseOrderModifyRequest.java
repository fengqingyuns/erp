package com.hanyun.scm.api.domain.request.purchase.order;

import com.hanyun.scm.api.domain.dto.ModifyPurchaseOrderDTO;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <pre>
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑, 永无BUG!
 * 　　　　┃　　　┃Code is far away from bug with the animal protecting
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * </pre>
 */
public class PurchaseOrderModifyRequest extends PurchaseOrderBaseRequest {

    @NotEmpty
    private String orderId;

    private String brandId;

    private Long stockInAmount;

    private String planTimeString;

    public PurchaseOrderModifyRequest() {}

    public PurchaseOrderModifyRequest(ModifyPurchaseOrderDTO modifyPurchaseOrderDTO) {
        this.setOrderId(modifyPurchaseOrderDTO.getOrderId());
        this.setSupplierId(modifyPurchaseOrderDTO.getSupplierId());
        this.setSupplierName(modifyPurchaseOrderDTO.getSupplierName());
        this.setToWarehouseId(modifyPurchaseOrderDTO.getToWarehouseId());
        this.setToWarehouseName(modifyPurchaseOrderDTO.getToWarehouseName());
        this.setPurchaseUserId(modifyPurchaseOrderDTO.getPurchaseUserId());
        this.setPurchaseUserName(modifyPurchaseOrderDTO.getPurchaseUserName());
        this.setPurchaseType(modifyPurchaseOrderDTO.getPurchaseType());
        this.setOperatorId(modifyPurchaseOrderDTO.getOperatorId());
        this.setOperatorName(modifyPurchaseOrderDTO.getOperatorName());
        this.setTotalAmount(modifyPurchaseOrderDTO.getTotalAmount());
        this.setTotalPrice(modifyPurchaseOrderDTO.getTotalPrice());
        this.setDistinctPrice(modifyPurchaseOrderDTO.getDistinctPrice());
        this.setPlanTimeString(modifyPurchaseOrderDTO.getPlanTimeString());
        this.setRemark(modifyPurchaseOrderDTO.getRemark());
        this.setPurchaseOrderDetailList(modifyPurchaseOrderDTO.getPurchaseOrderDetailList());
        this.setExpectedArrivalDate(modifyPurchaseOrderDTO.getExpectedArrivalDate());
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getPlanTimeString() {
        return planTimeString;
    }

    public void setPlanTimeString(String planTimeString) {
        this.planTimeString = planTimeString;
    }

    public Long getStockInAmount() {
        return stockInAmount;
    }

    public void setStockInAmount(Long stockInAmount) {
        this.stockInAmount = stockInAmount;
    }
}
package com.hanyun.scm.api.domain.request.purchase.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanyun.scm.api.domain.PurchaseOrderDetail;
import com.hanyun.scm.api.domain.dto.CreatePurchaseOrderDTO;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;
import java.util.List;

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
public class PurchaseOrderCreateRequest extends PurchaseOrderBaseRequest{

    @JsonIgnore
    private Integer id;

    private String orderId;

    @NotEmpty
    private String brandId;

    private String planTimeString;

    public PurchaseOrderCreateRequest() {}

    public PurchaseOrderCreateRequest(CreatePurchaseOrderDTO createPurchaseOrderDTO) {
        this.setBrandId(createPurchaseOrderDTO.getBrandId());
        this.setStoreId(createPurchaseOrderDTO.getStoreId());
        this.setStoreName(createPurchaseOrderDTO.getStoreName());
        this.setSupplierId(createPurchaseOrderDTO.getSupplierId());
        this.setSupplierName(createPurchaseOrderDTO.getSupplierName());
        this.setToWarehouseId(createPurchaseOrderDTO.getToWarehouseId());
        this.setToWarehouseName(createPurchaseOrderDTO.getToWarehouseName());
        this.setPurchaseUserId(createPurchaseOrderDTO.getPurchaseUserId());
        this.setPurchaseUserName(createPurchaseOrderDTO.getPurchaseUserName());
        this.setPurchaseType(createPurchaseOrderDTO.getPurchaseType());
        this.setOperatorId(createPurchaseOrderDTO.getOperatorId());
        this.setOperatorName(createPurchaseOrderDTO.getOperatorName());
        this.setTotalAmount(createPurchaseOrderDTO.getTotalAmount());
        this.setTotalPrice(createPurchaseOrderDTO.getTotalPrice());
        this.setDistinctPrice(createPurchaseOrderDTO.getDistinctPrice());
        this.setPlanTimeString(createPurchaseOrderDTO.getPlanTimeString());
        this.setRemark(createPurchaseOrderDTO.getRemark());
        this.setPurchaseOrderDetailList(createPurchaseOrderDTO.getPurchaseOrderDetailList());
        this.setExpectedArrivalDate(createPurchaseOrderDTO.getExpectedArrivalDate());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
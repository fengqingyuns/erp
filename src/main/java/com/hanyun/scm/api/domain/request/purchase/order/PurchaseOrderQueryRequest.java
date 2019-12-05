package com.hanyun.scm.api.domain.request.purchase.order;

import com.hanyun.scm.api.domain.dto.QueryPurchaseOrderDTO;
import org.hibernate.validator.constraints.NotEmpty;

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
public class PurchaseOrderQueryRequest extends PurchaseOrderBaseRequest {

    private String orderId;

    @NotEmpty
    private String brandId;

    private List<String> orderIds;

    private String userId;

    public PurchaseOrderQueryRequest() {}

    public PurchaseOrderQueryRequest(QueryPurchaseOrderDTO queryPurchaseOrderDTO) {
        this.setBrandId(queryPurchaseOrderDTO.getBrandId());
        this.setStoreId(queryPurchaseOrderDTO.getStoreId());
        this.setOrderDocumentId(queryPurchaseOrderDTO.getOrderDocumentId());
        this.setUserId(queryPurchaseOrderDTO.getUserId());
        this.setPurchaseUserId(queryPurchaseOrderDTO.getPurchaseUserId());
        this.setOrderStatus(queryPurchaseOrderDTO.getOrderStatus());
        this.setSupplierId(queryPurchaseOrderDTO.getSupplierId());
        this.setToWarehouseId(queryPurchaseOrderDTO.getToWarehouseId());
        this.setStartTime(queryPurchaseOrderDTO.getStartTime());
        this.setEndTime(queryPurchaseOrderDTO.getEndTime());
        this.setPageSize(queryPurchaseOrderDTO.getPageSize());
        this.setPageNum(queryPurchaseOrderDTO.getPageNum());
        this.setStockInStatus(queryPurchaseOrderDTO.getStockInStatus());
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public List<String> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<String> orderIds) {
        this.orderIds = orderIds;
    }
}
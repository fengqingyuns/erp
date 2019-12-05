package com.hanyun.scm.api.domain.result;

import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.domain.PurchaseOrder;
import com.hanyun.scm.api.domain.ReplenishmentApply;

import java.util.Date;

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
 * StockInResult
 * Date: 2017/8/13 0013
 * Time: 15:19
 *
 * @author tangqiming@hanyun.com
 */
public class StockInResult {

    private String orderId;

    private String orderDocumentId;

    private String storeId;

    private String storeName;

    private Integer type;

    private String typeName;

    private String statusName;

    private Long orderAmount;

    private Long orderPrice;

    private Date updateTime;

    private String deliveryOrgId;

    private String deliveryOrgName;

    public StockInResult() {
    }

    public StockInResult(PurchaseOrder order) {
        this.orderId = order.getOrderId();
        this.orderDocumentId = order.getOrderDocumentId();
        this.storeId = order.getToWarehouseId();
        this.storeName = order.getToWarehouseName();
        this.type = Consts.APP_PURCHASE_ORDER_TYPE;
        this.typeName = Consts.APP_PURCHASE_ORDER_TYPE_NAME;
        this.statusName = Consts.APP_ORDER_STATS_NAME;
        this.orderAmount = order.getTotalAmount();
        this.orderPrice = order.getTotalPrice();
        this.updateTime = order.getUpdateTime();
        this.deliveryOrgId = order.getSupplierId();
        this.deliveryOrgName = order.getSupplierName();
    }

    public StockInResult(ReplenishmentApply apply) {
        this.orderId = apply.getReplenishmentId();
        this.orderDocumentId = apply.getReplenishmentDocumentId();
        this.storeId = apply.getToStoreId();
        this.storeName = apply.getToStoreName();
        this.type = Consts.APP_REPLENISHMENT_ORDER_TYPE;
        this.typeName = Consts.APP_REPLENISHMENT_ORDER_TYPE_NAME;
        this.statusName = Consts.APP_ORDER_STATS_NAME;
        this.orderAmount = apply.getApplyNum();
        this.orderPrice = apply.getTotalPrice();
        this.updateTime = apply.getUpdateTime();
        this.deliveryOrgId = apply.getDistributionId();
        this.deliveryOrgName = apply.getDistributionName();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDocumentId() {
        return orderDocumentId;
    }

    public void setOrderDocumentId(String orderDocumentId) {
        this.orderDocumentId = orderDocumentId;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Long getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Long orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDeliveryOrgId() {
        return deliveryOrgId;
    }

    public void setDeliveryOrgId(String deliveryOrgId) {
        this.deliveryOrgId = deliveryOrgId;
    }

    public String getDeliveryOrgName() {
        return deliveryOrgName;
    }

    public void setDeliveryOrgName(String deliveryOrgName) {
        this.deliveryOrgName = deliveryOrgName;
    }
}

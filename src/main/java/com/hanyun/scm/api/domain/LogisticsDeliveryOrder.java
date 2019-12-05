package com.hanyun.scm.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanyun.scm.api.domain.request.BaseRequest;
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
public class LogisticsDeliveryOrder extends BaseRequest{
    @JsonIgnore
    private Long id;
    @NotEmpty
    private String brandId;

    private String storeId;

    private String logisticsDeliveryOrderId;

    private String logisticsDeliveryOrderDocumentId;

    private String logisticsOrderId;

    private String logisticsOrderDocumentId;

    private String warehouseId;

    private String warehouseName;

    private Integer logisticsDeliveryOrderStatus;

    private Date deliveryTime;

    private String makeorderManId;

    private String makeorderManName;

    private String auditorId;

    private String auditorName;

    private Date auditTime;

    private String remark;

    private Integer validStatus;

    private Date createTime;

    private Date updateTime;

    private List<LogisticsDeliveryOrderDetail> deliveryDetailList;

    private List<DistributionOrder> distributionOrderList;

    private List<DeliveryStore> deliveryStoreList;

    private boolean auditStatus;

    private Boolean historyStatus;

    private String userId;

    public Boolean getHistoryStatus() {
        return historyStatus;
    }

    public void setHistoryStatus(Boolean historyStatus) {
        this.historyStatus = historyStatus;
    }

    public boolean getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(boolean auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<DeliveryStore> getDeliveryStoreList() {
        return deliveryStoreList;
    }

    public void setDeliveryStoreList(List<DeliveryStore> deliveryStoreList) {
        this.deliveryStoreList = deliveryStoreList;
    }

    public List<DistributionOrder> getDistributionOrderList() {
        return distributionOrderList;
    }

    public void setDistributionOrderList(List<DistributionOrder> distributionOrderList) {
        this.distributionOrderList = distributionOrderList;
    }

    public List<LogisticsDeliveryOrderDetail> getDeliveryDetailList() {
        return deliveryDetailList;
    }

    public void setDeliveryDetailList(List<LogisticsDeliveryOrderDetail> deliveryDetailList) {
        this.deliveryDetailList = deliveryDetailList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getLogisticsDeliveryOrderId() {
        return logisticsDeliveryOrderId;
    }

    public void setLogisticsDeliveryOrderId(String logisticsDeliveryOrderId) {
        this.logisticsDeliveryOrderId = logisticsDeliveryOrderId;
    }

    public String getLogisticsDeliveryOrderDocumentId() {
        return logisticsDeliveryOrderDocumentId;
    }

    public void setLogisticsDeliveryOrderDocumentId(String logisticsDeliveryOrderDocumentId) {
        this.logisticsDeliveryOrderDocumentId = logisticsDeliveryOrderDocumentId;
    }

    public String getLogisticsOrderId() {
        return logisticsOrderId;
    }

    public void setLogisticsOrderId(String logisticsOrderId) {
        this.logisticsOrderId = logisticsOrderId;
    }

    public String getLogisticsOrderDocumentId() {
        return logisticsOrderDocumentId;
    }

    public void setLogisticsOrderDocumentId(String logisticsOrderDocumentId) {
        this.logisticsOrderDocumentId = logisticsOrderDocumentId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Integer getLogisticsDeliveryOrderStatus() {
        return logisticsDeliveryOrderStatus;
    }

    public void setLogisticsDeliveryOrderStatus(Integer logisticsDeliveryOrderStatus) {
        this.logisticsDeliveryOrderStatus = logisticsDeliveryOrderStatus;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getMakeorderManId() {
        return makeorderManId;
    }

    public void setMakeorderManId(String makeorderManId) {
        this.makeorderManId = makeorderManId;
    }

    public String getMakeorderManName() {
        return makeorderManName;
    }

    public void setMakeorderManName(String makeorderManName) {
        this.makeorderManName = makeorderManName;
    }

    public String getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(String auditorId) {
        this.auditorId = auditorId;
    }

    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
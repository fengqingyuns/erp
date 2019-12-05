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
public class DistributionOrder extends BaseRequest {
    @JsonIgnore
    private Long id;
    @NotEmpty
    private String brandId;

    private String storeId;

    private String distributionOrderId;

    private String distributionOrderDocumentId;

    private String sourceReplenishmentId;

    private String sourceReplenishmentDocumentId;

    private String toStoreId;

    private String toStoreName;

    private String warehouseId;

    private String warehouseName;

    private Long distributionAmount;

    private Long distributionQuantity;

    private Integer distributionOrderStatus;

    private Integer receiptStatus;          //入库引用状态

    private Integer deliveryStatus;         //发货状态 (0:未发货) (1:被引用) (2:已发货) (3:部分收货)(4:全部收货)

    private Integer receiptedStatus;        //已收货状态 1、未收货  2、部分收货  3、全部收货

    private Long receiptedNum;              //已收货数量

    private String makeorderManId;

    private String makeorderManName;

    private String auditorId;

    private String auditorName;

    private Date auditTime;

    private String remark;

    private Integer validStatus;

    private Date createTime;

    private Date updateTime;

    private Integer sourceType;         //1.汇总 2.直接引单

    private boolean auditStatus;

    private Boolean historyStatus;

    private String userId;

    public Boolean getHistoryStatus() {
        return historyStatus;
    }

    public void setHistoryStatus(Boolean historyStatus) {
        this.historyStatus = historyStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(boolean auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    private List<DistributionOrderDetail> distributionOrderDetailList;

    public Integer getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public List<DistributionOrderDetail> getDistributionOrderDetailList() {
        return distributionOrderDetailList;
    }

    public void setDistributionOrderDetailList(List<DistributionOrderDetail> distributionOrderDetailList) {
        this.distributionOrderDetailList = distributionOrderDetailList;
    }

    public Integer getDistributionOrderStatus() {
        return distributionOrderStatus;
    }

    public void setDistributionOrderStatus(Integer distributionOrderStatus) {
        this.distributionOrderStatus = distributionOrderStatus;
    }

    public Integer getReceiptStatus() {
        return receiptStatus;
    }

    public void setReceiptStatus(Integer receiptStatus) {
        this.receiptStatus = receiptStatus;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
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

    public String getDistributionOrderId() {
        return distributionOrderId;
    }

    public void setDistributionOrderId(String distributionOrderId) {
        this.distributionOrderId = distributionOrderId;
    }

    public String getDistributionOrderDocumentId() {
        return distributionOrderDocumentId;
    }

    public void setDistributionOrderDocumentId(String distributionOrderDocumentId) {
        this.distributionOrderDocumentId = distributionOrderDocumentId;
    }

    public String getSourceReplenishmentId() {
        return sourceReplenishmentId;
    }

    public void setSourceReplenishmentId(String sourceReplenishmentId) {
        this.sourceReplenishmentId = sourceReplenishmentId;
    }

    public String getSourceReplenishmentDocumentId() {
        return sourceReplenishmentDocumentId;
    }

    public void setSourceReplenishmentDocumentId(String sourceReplenishmentDocumentId) {
        this.sourceReplenishmentDocumentId = sourceReplenishmentDocumentId;
    }

    public String getToStoreId() {
        return toStoreId;
    }

    public void setToStoreId(String toStoreId) {
        this.toStoreId = toStoreId;
    }

    public String getToStoreName() {
        return toStoreName;
    }

    public void setToStoreName(String toStoreName) {
        this.toStoreName = toStoreName;
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

    public Long getDistributionAmount() {
        return distributionAmount;
    }

    public void setDistributionAmount(Long distributionAmount) {
        this.distributionAmount = distributionAmount;
    }

    public Long getDistributionQuantity() {
        return distributionQuantity;
    }

    public void setDistributionQuantity(Long distributionQuantity) {
        this.distributionQuantity = distributionQuantity;
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

    public Integer getReceiptedStatus() {
        return receiptedStatus;
    }

    public void setReceiptedStatus(Integer receiptedStatus) {
        this.receiptedStatus = receiptedStatus;
    }

    public Long getReceiptedNum() {
        return receiptedNum;
    }

    public void setReceiptedNum(Long receiptedNum) {
        this.receiptedNum = receiptedNum;
    }
}
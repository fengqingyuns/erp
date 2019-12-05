package com.hanyun.scm.api.domain;

import com.hanyun.scm.api.domain.request.BaseRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;

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
public class InspectionPickingIn extends BaseRequest{

    private Long id;

    private String brandId;

    private String storeId;

    private String inspectionId;

    private String inspectionDocumentId;

    private String distributionOrderId;

    private String distributionOrderDocumentId;

    private String toStoreId;

    private String toStoreName;

    private String warehouseId;

    private String warehouseName;

    private String operatorId;

    private String operatorName;

    private String auditorId;

    private String auditorName;

    private String businessManId;

    private String businessManName;

    private Integer inspectionStatus;

    private Long receiptNum;

    private Long receiptPrice;

    private String remark;

    private Date auditTime;

    private Date createTime;

    private Date updateTime;

    private Integer validStatus;

    private List<InspectionPickingInDetail> detailList;

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

    public String getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getInspectionDocumentId() {
        return inspectionDocumentId;
    }

    public void setInspectionDocumentId(String inspectionDocumentId) {
        this.inspectionDocumentId = inspectionDocumentId;
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

    public Integer getInspectionStatus() {
        return inspectionStatus;
    }

    public void setInspectionStatus(Integer inspectionStatus) {
        this.inspectionStatus = inspectionStatus;
    }

    public Long getReceiptNum() {
        return receiptNum;
    }

    public void setReceiptNum(Long receiptNum) {
        this.receiptNum = receiptNum;
    }

    public Long getReceiptPrice() {
        return receiptPrice;
    }

    public void setReceiptPrice(Long receiptPrice) {
        this.receiptPrice = receiptPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
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

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public List<InspectionPickingInDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<InspectionPickingInDetail> detailList) {
        this.detailList = detailList;
    }
}
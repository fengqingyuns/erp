package com.hanyun.scm.api.domain;

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
public class PurchasePlan {

    private Long id;

    private String planId;

    private String planDocumentId;

    private String brandId;

    private String storeId;

    private Integer planStatus;

    private String planStatusName;

    private Integer purchaseType;

    private String operatorId;

    private String operatorName;

    private Integer validStatus;

    private String remark;

    private String businessManId;

    private String businessManName;

    private String auditorId;

    private String auditorName;

    private Date auditTime;

    private Date createTime;

    private Date updateTime;

    private List<PurchasePlanApply> purchasePlanApplyList;

    private List<PurchasePlanDetail> purchasePlanDetailList;

    private Boolean auditStatus;

    private Boolean historyStatus;

    public Boolean getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Boolean auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Boolean getHistoryStatus() {
        return historyStatus;
    }

    public void setHistoryStatus(Boolean historyStatus) {
        this.historyStatus = historyStatus;
    }

    public Integer getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(Integer purchaseType) {
        this.purchaseType = purchaseType;
    }

    public Integer getPlanStatus() {
        return planStatus;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public String getPlanStatusName() {
        return planStatusName;
    }

    public void setPlanStatusName(String planStatusName) {
        this.planStatusName = planStatusName;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public void setPlanStatus(Integer planStatus) {
        this.planStatus = planStatus;
    }

    public List<PurchasePlanApply> getPurchasePlanApplyList() {
        return purchasePlanApplyList;
    }

    public void setPurchasePlanApplyList(List<PurchasePlanApply> purchasePlanApplyList) {
        this.purchasePlanApplyList = purchasePlanApplyList;
    }

    public List<PurchasePlanDetail> getPurchasePlanDetailList() {
        return purchasePlanDetailList;
    }

    public void setPurchasePlanDetailList(List<PurchasePlanDetail> purchasePlanDetailList) {
        this.purchasePlanDetailList = purchasePlanDetailList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanDocumentId() {
        return planDocumentId;
    }

    public void setPlanDocumentId(String planDocumentId) {
        this.planDocumentId = planDocumentId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
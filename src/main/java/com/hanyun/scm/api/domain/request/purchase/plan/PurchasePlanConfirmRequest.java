package com.hanyun.scm.api.domain.request.purchase.plan;

import org.hibernate.validator.constraints.NotEmpty;

public class PurchasePlanConfirmRequest extends PurchasePlanBaseRequest{

    @NotEmpty
    private String planId;

    private String brandId;

    private String userId;

    private Boolean auditStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Boolean auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }
}

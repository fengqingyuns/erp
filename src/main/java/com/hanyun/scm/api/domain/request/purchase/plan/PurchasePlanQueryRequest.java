package com.hanyun.scm.api.domain.request.purchase.plan;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class PurchasePlanQueryRequest extends PurchasePlanBaseRequest {

    private String planId;

    @NotEmpty
    private String brandId;

    private String queryBeginTime;

    private String queryEndTime;

    private String planStatusName;

    private String userId;

    private List<String> ids;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getQueryBeginTime() {
        return queryBeginTime;
    }

    public void setQueryBeginTime(String queryBeginTime) {
        this.queryBeginTime = queryBeginTime;
    }

    public String getQueryEndTime() {
        return queryEndTime;
    }

    public void setQueryEndTime(String queryEndTime) {
        this.queryEndTime = queryEndTime;
    }

    public String getPlanStatusName() {
        return planStatusName;
    }

    public void setPlanStatusName(String planStatusName) {
        this.planStatusName = planStatusName;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}

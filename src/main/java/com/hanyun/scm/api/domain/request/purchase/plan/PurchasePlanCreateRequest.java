package com.hanyun.scm.api.domain.request.purchase.plan;

import org.hibernate.validator.constraints.NotEmpty;

public class PurchasePlanCreateRequest extends PurchasePlanBaseRequest {


    private String planId;

    @NotEmpty
    private String brandId;

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

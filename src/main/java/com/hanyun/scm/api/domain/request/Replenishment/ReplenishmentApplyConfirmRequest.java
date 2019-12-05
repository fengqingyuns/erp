package com.hanyun.scm.api.domain.request.Replenishment;

import org.hibernate.validator.constraints.NotEmpty;

public class ReplenishmentApplyConfirmRequest extends ReplenishmentApplyRequest {

    @NotEmpty
    private String replenishmentId;

    private String brandId;

    private String userId;

    @Override
    public String getReplenishmentId() {
        return replenishmentId;
    }

    @Override
    public void setReplenishmentId(String replenishmentId) {
        this.replenishmentId = replenishmentId;
    }

    @Override
    public String getBrandId() {
        return brandId;
    }

    @Override
    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }
}

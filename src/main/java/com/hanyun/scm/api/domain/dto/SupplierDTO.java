package com.hanyun.scm.api.domain.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class SupplierDTO {
    @NotEmpty
    private String brandId;

    private String storeId;
    @NotEmpty
    private String goodsId;

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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}

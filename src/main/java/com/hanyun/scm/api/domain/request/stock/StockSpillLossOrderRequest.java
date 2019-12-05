package com.hanyun.scm.api.domain.request.stock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanyun.scm.api.domain.StockSpillLossOrder;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class StockSpillLossOrderRequest extends StockSpillLossOrder {
    @JsonIgnore
    private Long id;
    @NotEmpty
    private String brandId;

    private List<String> ids;

    @Override
    public String getBrandId() {
        return brandId;
    }

    @Override
    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
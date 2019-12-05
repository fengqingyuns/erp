package com.hanyun.scm.api.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * Created by zhaojiefeng on 2017/1/4.
 */
@ApiModel(value = "供应链搜索条件")
public class ProductSupplierFormDTO {
    @ApiModelProperty(value = "分类id")
    private String categoryId;
    @ApiModelProperty(value = "搜索条件:商品名称和商品编码和条码 模糊匹配")
    private String condition;
    @ApiModelProperty(value = "条码")
    private String barcode;
    @ApiModelProperty(value = "供应商id")
    private String supplierId;
    @ApiModelProperty(value = "商家品牌id")
    @NotBlank(message = "商家品牌id不能为空")
    private String brandId;
    private List<String> goodsIds;
    @ApiModelProperty(value = "skipIds,过滤商品条件")
    private List<String> skipIds;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public List<String> getSkipIds() {
        return skipIds;
    }

    public void setSkipIds(List<String> skipIds) {
        this.skipIds = skipIds;
    }

    public List<String> getGoodsIds() {
        return goodsIds;
    }

    public void setGoodsIds(List<String> goodsIds) {
        this.goodsIds = goodsIds;
    }
}

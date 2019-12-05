package com.hanyun.scm.api.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by zhaojiefeng on 2017/1/4.
 */
@ApiModel(value = "供应链列表")
public class ProductSupplierDTO {
    @ApiModelProperty(value = "商品id")
    private String productId;
    @ApiModelProperty(value = "商品图片")
    private String pic;
    @ApiModelProperty(value = "单位")
    private String quantityUomId;
    @ApiModelProperty(value = "进价")
    private Long costPrice;
    @ApiModelProperty(value = "集团价")
    private Long groupPrice;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "商品规格")
    private String features;
    @ApiModelProperty(value = "sku编码")
    private String skuCode;
    @ApiModelProperty(value = "条码")
    private String barcode;
    @ApiModelProperty(value = "分类名称")
    private String categoryName;
    @ApiModelProperty(value = "分类id")
    private String categoryId;
    @ApiModelProperty(value = "品牌名称")
    private String productBrandName;
    @ApiModelProperty(value = "产品类型")
    private String productTypeName;
    @ApiModelProperty(value = "供应商编码")
    private String supplierId;
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    public Long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Long costPrice) {
        this.costPrice = costPrice;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getQuantityUomId() {
        return quantityUomId;
    }

    public void setQuantityUomId(String quantityUomId) {
        this.quantityUomId = quantityUomId;
    }

    public Long getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(Long groupPrice) {
        this.groupPrice = groupPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProductBrandName() {
        return productBrandName;
    }

    public void setProductBrandName(String productBrandName) {
        this.productBrandName = productBrandName;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}

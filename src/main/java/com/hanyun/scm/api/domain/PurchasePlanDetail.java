package com.hanyun.scm.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

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
public class PurchasePlanDetail {

    private Long id;

    private String brandId;

    private String storeId;

    private String planId;

    private String planDetailId;

    private String goodsId;

    private String goodsName;

    private String goodsPic;

    private String classifyId;

    private String classifyName;

    private Byte goodsType;

    private Long goodsPrice;

    private Long purchaseAmount;

    private Long purchasePrice;

    private String purchasePriceShow;

    @JsonIgnore
    private Long totalPurchasePrice;

    private String unitId;

    private String unitName;

    private String supplierId;

    private String supplierName;

    private Integer odooSupplierId;

    private Integer odooPurchaseId;

    private Byte validStatus;

    private String goodsBarCode;

    private String goodsTypeName;

    private String goodsCode;

    private String features;

    private String goodsBrandName;

    private String remark;

    private Date createTime;

    private Date updateTime;

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public Long getTotalPurchasePrice() {
        return totalPurchasePrice;
    }

    public void setTotalPurchasePrice(Long totalPurchasePrice) {
        this.totalPurchasePrice = totalPurchasePrice;
    }

    public String getPurchasePriceShow() {
        return purchasePriceShow;
    }

    public void setPurchasePriceShow(String purchasePriceShow) {
        this.purchasePriceShow = purchasePriceShow;
    }

    public Long getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Long purchasePrice) {
        this.purchasePrice = purchasePrice;
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

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanDetailId() {
        return planDetailId;
    }

    public void setPlanDetailId(String planDetailId) {
        this.planDetailId = planDetailId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public Byte getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Byte goodsType) {
        this.goodsType = goodsType;
    }

    public Long getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Long goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Long getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(Long purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
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

    public Integer getOdooSupplierId() {
        return odooSupplierId;
    }

    public void setOdooSupplierId(Integer odooSupplierId) {
        this.odooSupplierId = odooSupplierId;
    }

    public Integer getOdooPurchaseId() {
        return odooPurchaseId;
    }

    public void setOdooPurchaseId(Integer odooPurchaseId) {
        this.odooPurchaseId = odooPurchaseId;
    }

    public Byte getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Byte validStatus) {
        this.validStatus = validStatus;
    }

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public void setGoodsBarCode(String goodsBarCode) {
        this.goodsBarCode = goodsBarCode;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getGoodsBrandName() {
        return goodsBrandName;
    }

    public void setGoodsBrandName(String goodsBrandName) {
        this.goodsBrandName = goodsBrandName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
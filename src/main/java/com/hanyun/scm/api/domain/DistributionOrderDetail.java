package com.hanyun.scm.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanyun.scm.api.domain.dto.QueryBillGoodsDTO;
import com.hanyun.scm.api.domain.request.BaseRequest;
import org.hibernate.validator.constraints.NotEmpty;

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
public class DistributionOrderDetail extends BaseRequest {
    @JsonIgnore
    private Long id;
    @NotEmpty
    private String brandId;

    private String storeId;

    private String distributionOrderDetailId;

    private String distributionOrderId;

    private String goodsId;

    private String goodsName;

    private String goodsCode;

    private String goodsPic;

    private String goodsBarCode;

    private String features;

    private String unitName;

    private Long distributionQuantity;

    private String goodsTypeName;

    private String goodsBrandName;

    private String classifyId;

    private String classifyName;

    private Integer goodsStatus;

    private Long unitPrice;

    private String remark;

    private Integer validStatus;

    private Date createTime;

    private Date updateTime;

    private Long stockInNum;         //入库数

    private Long receiptedNum;      //已收货数量

    private String startTime;

    private String endTime;

    private String condition;

    public DistributionOrderDetail(){}

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public Long getStockInNum() {
        return stockInNum;
    }

    public void setStockInNum(Long stockInNum) {
        this.stockInNum = stockInNum;
    }

    public Long getReceiptedNum() {
        return receiptedNum;
    }

    public void setReceiptedNum(Long receiptedNum) {
        this.receiptedNum = receiptedNum;
    }

    public Long getDistributionQuantity() {
        return distributionQuantity;
    }

    public void setDistributionQuantity(Long distributionQuantity) {
        this.distributionQuantity = distributionQuantity;
    }

    public Integer getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(Integer goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
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

    public String getDistributionOrderDetailId() {
        return distributionOrderDetailId;
    }

    public void setDistributionOrderDetailId(String distributionOrderDetailId) {
        this.distributionOrderDetailId = distributionOrderDetailId;
    }

    public String getDistributionOrderId() {
        return distributionOrderId;
    }

    public void setDistributionOrderId(String distributionOrderId) {
        this.distributionOrderId = distributionOrderId;
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

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public void setGoodsBarCode(String goodsBarCode) {
        this.goodsBarCode = goodsBarCode;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public String getGoodsBrandName() {
        return goodsBrandName;
    }

    public void setGoodsBrandName(String goodsBrandName) {
        this.goodsBrandName = goodsBrandName;
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

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
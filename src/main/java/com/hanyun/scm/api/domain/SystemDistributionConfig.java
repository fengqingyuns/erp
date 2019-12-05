package com.hanyun.scm.api.domain;

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
public class SystemDistributionConfig {

    private String configId;
    @NotEmpty
    private String brandId;

    private String storeId;

    private Integer applyCenterStockShow;

    private Integer receiptCanGreaterDistrbution;

    private Integer distributionNeedSourceOrder;

    private Integer receiptNumCertainlyEqualsDistributionNum;

    private Integer receiptNumNotGreaterDistributionNum;

    private Integer distributionCanGreaterApply;

    private String applyAddStartTime;

    private String applyAddEndTime;

    private String summaryTime;

    private Integer distributionType;//配送方式：1、一次配送 2、多次配送

    private String remark;

    private Integer validStatus;

    private Date createTime;

    private Date updateTime;

    private String createUserId;

    private String updateUserId;

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
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

    public Integer getApplyCenterStockShow() {
        return applyCenterStockShow;
    }

    public void setApplyCenterStockShow(Integer applyCenterStockShow) {
        this.applyCenterStockShow = applyCenterStockShow;
    }

    public Integer getReceiptCanGreaterDistrbution() {
        return receiptCanGreaterDistrbution;
    }

    public void setReceiptCanGreaterDistrbution(Integer receiptCanGreaterDistrbution) {
        this.receiptCanGreaterDistrbution = receiptCanGreaterDistrbution;
    }

    public Integer getDistributionNeedSourceOrder() {
        return distributionNeedSourceOrder;
    }

    public void setDistributionNeedSourceOrder(Integer distributionNeedSourceOrder) {
        this.distributionNeedSourceOrder = distributionNeedSourceOrder;
    }

    public Integer getReceiptNumCertainlyEqualsDistributionNum() {
        return receiptNumCertainlyEqualsDistributionNum;
    }

    public void setReceiptNumCertainlyEqualsDistributionNum(Integer receiptNumCertainlyEqualsDistributionNum) {
        this.receiptNumCertainlyEqualsDistributionNum = receiptNumCertainlyEqualsDistributionNum;
    }

    public Integer getReceiptNumNotGreaterDistributionNum() {
        return receiptNumNotGreaterDistributionNum;
    }

    public void setReceiptNumNotGreaterDistributionNum(Integer receiptNumNotGreaterDistributionNum) {
        this.receiptNumNotGreaterDistributionNum = receiptNumNotGreaterDistributionNum;
    }

    public Integer getDistributionCanGreaterApply() {
        return distributionCanGreaterApply;
    }

    public void setDistributionCanGreaterApply(Integer distributionCanGreaterApply) {
        this.distributionCanGreaterApply = distributionCanGreaterApply;
    }

    public String getApplyAddStartTime() {
        return applyAddStartTime;
    }

    public void setApplyAddStartTime(String applyAddStartTime) {
        this.applyAddStartTime = applyAddStartTime;
    }

    public String getApplyAddEndTime() {
        return applyAddEndTime;
    }

    public void setApplyAddEndTime(String applyAddEndTime) {
        this.applyAddEndTime = applyAddEndTime;
    }

    public String getSummaryTime() {
        return summaryTime;
    }

    public void setSummaryTime(String summaryTime) {
        this.summaryTime = summaryTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
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

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getDistributionType() {
        return distributionType;
    }

    public void setDistributionType(Integer distributionType) {
        this.distributionType = distributionType;
    }
}
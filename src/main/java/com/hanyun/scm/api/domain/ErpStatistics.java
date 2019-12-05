package com.hanyun.scm.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanyun.scm.api.domain.request.BaseRequest;

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
public class ErpStatistics extends BaseRequest {
    @JsonIgnore
    private Long id;

    private String statisticsId;

    private String brandId;

    private String storeId;

    private String goodsId;

    private String goodsName;

    private String goodsSkuCode;

    private String goodsBarCode;

    private Long purchaseApplyNum;

    private Long purchaseOrderNum;

    private Long purchaseStockInNum;

    private Long purchaseReturnNum;

    private Long stockCheckOrderNum;

    private Long replenishmentApplyNum;

    private Long distributionOrderNum;

    private Long otherInStockNum;

    private Long otherOutStockNum;

    private Long inspectedStockInNum;

    private Long abandonedNum;

    private Long spilledNum;

    private String dateString;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatisticsId() {
        return statisticsId;
    }

    public void setStatisticsId(String statisticsId) {
        this.statisticsId = statisticsId;
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

    public String getGoodsSkuCode() {
        return goodsSkuCode;
    }

    public void setGoodsSkuCode(String goodsSkuCode) {
        this.goodsSkuCode = goodsSkuCode;
    }

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public void setGoodsBarCode(String goodsBarCode) {
        this.goodsBarCode = goodsBarCode;
    }

    public Long getPurchaseApplyNum() {
        return purchaseApplyNum;
    }

    public void setPurchaseApplyNum(Long purchaseApplyNum) {
        this.purchaseApplyNum = purchaseApplyNum;
    }

    public Long getPurchaseOrderNum() {
        return purchaseOrderNum;
    }

    public void setPurchaseOrderNum(Long purchaseOrderNum) {
        this.purchaseOrderNum = purchaseOrderNum;
    }

    public Long getPurchaseStockInNum() {
        return purchaseStockInNum;
    }

    public void setPurchaseStockInNum(Long purchaseStockInNum) {
        this.purchaseStockInNum = purchaseStockInNum;
    }

    public Long getPurchaseReturnNum() {
        return purchaseReturnNum;
    }

    public void setPurchaseReturnNum(Long purchaseReturnNum) {
        this.purchaseReturnNum = purchaseReturnNum;
    }

    public Long getStockCheckOrderNum() {
        return stockCheckOrderNum;
    }

    public void setStockCheckOrderNum(Long stockCheckOrderNum) {
        this.stockCheckOrderNum = stockCheckOrderNum;
    }

    public Long getReplenishmentApplyNum() {
        return replenishmentApplyNum;
    }

    public void setReplenishmentApplyNum(Long replenishmentApplyNum) {
        this.replenishmentApplyNum = replenishmentApplyNum;
    }

    public Long getDistributionOrderNum() {
        return distributionOrderNum;
    }

    public void setDistributionOrderNum(Long distributionOrderNum) {
        this.distributionOrderNum = distributionOrderNum;
    }

    public Long getOtherInStockNum() {
        return otherInStockNum;
    }

    public void setOtherInStockNum(Long otherInStockNum) {
        this.otherInStockNum = otherInStockNum;
    }

    public Long getOtherOutStockNum() {
        return otherOutStockNum;
    }

    public void setOtherOutStockNum(Long otherOutStockNum) {
        this.otherOutStockNum = otherOutStockNum;
    }

    public Long getInspectedStockInNum() {
        return inspectedStockInNum;
    }

    public void setInspectedStockInNum(Long inspectedStockInNum) {
        this.inspectedStockInNum = inspectedStockInNum;
    }

    public Long getAbandonedNum() {
        return abandonedNum;
    }

    public void setAbandonedNum(Long abandonedNum) {
        this.abandonedNum = abandonedNum;
    }

    public Long getSpilledNum() {
        return spilledNum;
    }

    public void setSpilledNum(Long spilledNum) {
        this.spilledNum = spilledNum;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
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
package com.hanyun.scm.api.domain;

import com.hanyun.scm.api.domain.dto.QueryBillGoodsDTO;
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
public class ReplenishmentApplyDetail extends BaseRequest {

    private Long id;

    private String brandId;

    private String storeId;

    private String replenishmentDetailId;

    private String replenishmentId;

    private String goodsId;

    private String goodsCode;

    private String goodsBarCode;

    private String goodsName;

    private String goodsPic;

    private String unitName;

    private String unitId;

    private Long  unitPrice;

    private String goodsBrandName;

    private String goodsTypeName;

    private String classifyId;

    private String classifyName;

    private String features;

    private Long applyNum;

    private Long yesterdayApplyNum;

    private Long yesterdayGetNum;

    private Long yesterdaySalesNum;

    private Long yesterdayWasteNum;

    private String toStoreId;

    private String toStoreName;

    private Integer replenishmentStatus;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Date auditTime;

    private Long toStoreStock;

    private Long distributionStock;

    private Long stockInNum;

    private Long distributeNum;

    private Long distributedNum;

    private Long deliveryNum;

    private Integer auditStatus;

    private String startTime;

    private String endTime;

    private String condition;

    public ReplenishmentApplyDetail(){}

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

    public Long getDeliveryNum() {
        return deliveryNum;
    }

    public void setDeliveryNum(Long deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getReplenishmentStatus() {
        return replenishmentStatus;
    }

    public void setReplenishmentStatus(Integer replenishmentStatus) {
        this.replenishmentStatus = replenishmentStatus;
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

    public String getReplenishmentDetailId() {
        return replenishmentDetailId;
    }

    public void setReplenishmentDetailId(String replenishmentDetailId) {
        this.replenishmentDetailId = replenishmentDetailId;
    }

    public String getReplenishmentId() {
        return replenishmentId;
    }

    public void setReplenishmentId(String replenishmentId) {
        this.replenishmentId = replenishmentId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getGoodsBrandName() {
        return goodsBrandName;
    }

    public void setGoodsBrandName(String goodsBrandName) {
        this.goodsBrandName = goodsBrandName;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
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

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public Long getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Long applyNum) {
        this.applyNum = applyNum;
    }

    public Long getYesterdayApplyNum() {
        return yesterdayApplyNum;
    }

    public void setYesterdayApplyNum(Long yesterdayApplyNum) {
        this.yesterdayApplyNum = yesterdayApplyNum;
    }

    public Long getYesterdayGetNum() {
        return yesterdayGetNum;
    }

    public void setYesterdayGetNum(Long yesterdayGetNum) {
        this.yesterdayGetNum = yesterdayGetNum;
    }

    public Long getYesterdaySalesNum() {
        return yesterdaySalesNum;
    }

    public void setYesterdaySalesNum(Long yesterdaySalesNum) {
        this.yesterdaySalesNum = yesterdaySalesNum;
    }

    public Long getYesterdayWasteNum() {
        return yesterdayWasteNum;
    }

    public void setYesterdayWasteNum(Long yesterdayWasteNum) {
        this.yesterdayWasteNum = yesterdayWasteNum;
    }

    public String getToStoreId() {
        return toStoreId;
    }

    public void setToStoreId(String toStoreId) {
        this.toStoreId = toStoreId;
    }

    public String getToStoreName() {
        return toStoreName;
    }

    public void setToStoreName(String toStoreName) {
        this.toStoreName = toStoreName;
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

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getToStoreStock() {
        return toStoreStock;
    }

    public void setToStoreStock(Long toStoreStock) {
        this.toStoreStock = toStoreStock;
    }

    public Long getDistributionStock() {
        return distributionStock;
    }

    public void setDistributionStock(Long distributionStock) {
        this.distributionStock = distributionStock;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Long getStockInNum() {
        return stockInNum;
    }

    public void setStockInNum(Long stockInNum) {
        this.stockInNum = stockInNum;
    }

    public Long getDistributeNum() {
        return distributeNum;
    }

    public void setDistributeNum(Long distributeNum) {
        this.distributeNum = distributeNum;
    }

    public Long getDistributedNum() {
        return distributedNum;
    }

    public void setDistributedNum(Long distributedNum) {
        this.distributedNum = distributedNum;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
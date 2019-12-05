package com.hanyun.scm.api.domain;

import java.util.Date;
import java.util.List;

public class StockSpillLossOrderDetail {

    private Long id;

    private String brandId;

    private String storeId;

    private String stockVarianceId;

    private String stockVarianceDetailId;

    private String goodsCode;

    private String goodsBarCode;

    private String goodsId;

    private String goodsName;

    private String classifyId;

    private String classifyName;

    private String goodsBrandName;

    private String warehouseId;

    private String warehouseName;

    private String unitId;

    private String unitName;

    private String goodsIntroduce;

    private Long unitPrice;

    private String goodsTypeName;

    private String feature;

    private String remark;

    private Long systemStock;

    private Long varianceStock;

    private Long stockNum;

    private Date createTime;

    private Date updateTime;
    
    private Long total;
    
    private Long systemPrice;
    
    private Long differencePrice;

    private Long scrapNum;

    private String beginTime;

    private String endTime;

    private String goodsPic;

    private List<String> orderIds;

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
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

    public String getStockVarianceId() {
        return stockVarianceId;
    }

    public void setStockVarianceId(String stockVarianceId) {
        this.stockVarianceId = stockVarianceId;
    }

    public String getStockVarianceDetailId() {
        return stockVarianceDetailId;
    }

    public void setStockVarianceDetailId(String stockVarianceDetailId) {
        this.stockVarianceDetailId = stockVarianceDetailId;
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

    public String getGoodsBrandName() {
        return goodsBrandName;
    }

    public void setGoodsBrandName(String goodsBrandName) {
        this.goodsBrandName = goodsBrandName;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
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

    public String getGoodsIntroduce() {
        return goodsIntroduce;
    }

    public void setGoodsIntroduce(String goodsIntroduce) {
        this.goodsIntroduce = goodsIntroduce;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getSystemStock() {
        return systemStock;
    }

    public void setSystemStock(Long systemStock) {
        this.systemStock = systemStock;
    }

    public Long getVarianceStock() {
        return varianceStock;
    }

    public void setVarianceStock(Long varianceStock) {
        this.varianceStock = varianceStock;
    }

    public Long getStockNum() {
        return stockNum;
    }

    public void setStockNum(Long stockNum) {
        this.stockNum = stockNum;
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

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getSystemPrice() {
		return systemPrice;
	}

	public void setSystemPrice(Long systemPrice) {
		this.systemPrice = systemPrice;
	}

	public Long getDifferencePrice() {
		return differencePrice;
	}

	public void setDifferencePrice(Long differencePrice) {
		this.differencePrice = differencePrice;
	}

    public Long getScrapNum() {
        return scrapNum;
    }

    public void setScrapNum(Long scrapNum) {
        this.scrapNum = scrapNum;
    }

    public List<String> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<String> orderIds) {
        this.orderIds = orderIds;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
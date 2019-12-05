package com.hanyun.scm.api.domain;

import org.hibernate.validator.constraints.NotEmpty;

public class StockFlow {

	private String classifyName;
	
	private String goodsId;
	
	private String goodsName;
	
	private String unitName;
	
	private Long purchasingIn;
	
	private Long purchasingOut;
	
	private Long saleOut;
	
	private Long saleIn;
	
	private Long profit;
	
	private Long loss;
	
	private Long allocation;
	
	private Long collect;
	@NotEmpty
	private String brandId;
	
	private String storeId;
	
	private String warehouseId;
	
	private String planBeginTime;
	
	private String planEndTime;
	
	private String classifyId;

	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Long getPurchasingIn() {
		return purchasingIn;
	}

	public void setPurchasingIn(Long purchasingIn) {
		this.purchasingIn = purchasingIn;
	}

	public Long getPurchasingOut() {
		return purchasingOut;
	}

	public void setPurchasingOut(Long purchasingOut) {
		this.purchasingOut = purchasingOut;
	}

	public Long getSaleOut() {
		return saleOut;
	}

	public void setSaleOut(Long saleOut) {
		this.saleOut = saleOut;
	}

	public Long getSaleIn() {
		return saleIn;
	}

	public void setSaleIn(Long saleIn) {
		this.saleIn = saleIn;
	}

	public Long getProfit() {
		return profit;
	}

	public void setProfit(Long profit) {
		this.profit = profit;
	}

	public Long getLoss() {
		return loss;
	}

	public void setLoss(Long loss) {
		this.loss = loss;
	}

	public Long getAllocation() {
		return allocation;
	}

	public void setAllocation(Long allocation) {
		this.allocation = allocation;
	}

	public Long getCollect() {
		return collect;
	}

	public void setCollect(Long collect) {
		this.collect = collect;
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

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getPlanBeginTime() {
		return planBeginTime;
	}

	public void setPlanBeginTime(String planBeginTime) {
		this.planBeginTime = planBeginTime;
	}

	public String getPlanEndTime() {
		return planEndTime;
	}

	public void setPlanEndTime(String planEndTime) {
		this.planEndTime = planEndTime;
	}

	public String getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
	}
	
}

package com.hanyun.scm.api.domain.response;

import java.util.List;

import com.hanyun.scm.api.domain.StockPickingGoods;

public class StockPickingGoodsResponse{
	
	private Double pickingTotalPrice;
	
	private Double receiveTotalAmount;
	
	private Integer count;
	
	private List<StockPickingGoods> list;
	
	public StockPickingGoodsResponse(){
		
	}
	public StockPickingGoodsResponse(int count, List<StockPickingGoods> list,Double pickingTotalPrice,Double receiveTotalAmount){
		this.count = count;
		this.list = list;
		this.pickingTotalPrice = pickingTotalPrice;
		this.receiveTotalAmount = receiveTotalAmount;
	}
	public Double getPickingTotalPrice() {
		return pickingTotalPrice;
	}
	public void setPickingTotalPrice(Double pickingTotalPrice) {
		this.pickingTotalPrice = pickingTotalPrice;
	}
	public Double getReceiveTotalAmount() {
		return receiveTotalAmount;
	}
	public void setReceiveTotalAmount(Double receiveTotalAmount) {
		this.receiveTotalAmount = receiveTotalAmount;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public List<StockPickingGoods> getList() {
		return list;
	}
	public void setList(List<StockPickingGoods> list) {
		this.list = list;
	}
}

package com.hanyun.scm.api.domain.response;

import java.util.List;
import java.util.Map;

import com.hanyun.scm.api.domain.StockQuant;
import com.hanyun.scm.api.domain.Warehouse;

public class StockQuantResponse extends BaseResponse {
	

	private List<Warehouse> warhouserNameList;
	
	private List<StockQuant> stockClassfilyList;
	
	private Integer max;
	
	private String maxGoodsId;
	
	private String maxwarhouserId;
	
	private Map<String,Long> mapList;
	
	private Map<String,Object> mapList1;
	
	private Map<String,Object> doubleMap;
	
	private Integer countOne1;
	
	private List<StockQuant> stockAllList;
	
	private Map<String, String> nameMap;
	
	
	public StockQuantResponse(){
		
	}


	public List<Warehouse> getWarhouserNameList() {
		return warhouserNameList;
	}


	public void setWarhouserNameList(List<Warehouse> warhouserNameList) {
		this.warhouserNameList = warhouserNameList;
	}

	public List<StockQuant> getStockClassfilyList() {
		return stockClassfilyList;
	}


	public void setStockClassfilyList(List<StockQuant> stockClassfilyList) {
		this.stockClassfilyList = stockClassfilyList;
	}


	public Integer getMax() {
		return max;
	}


	public void setMax(Integer max) {
		this.max = max;
	}


	public String getMaxGoodsId() {
		return maxGoodsId;
	}


	public void setMaxGoodsId(String maxGoodsId) {
		this.maxGoodsId = maxGoodsId;
	}


	public String getMaxwarhouserId() {
		return maxwarhouserId;
	}


	public void setMaxwarhouserId(String maxwarhouserId) {
		this.maxwarhouserId = maxwarhouserId;
	}


	public Map<String, Long> getMapList() {
		return mapList;
	}


	public void setMapList(Map<String, Long> mapList) {
		this.mapList = mapList;
	}


	public Map<String, Object> getMapList1() {
		return mapList1;
	}


	public void setMapList1(Map<String, Object> mapList1) {
		this.mapList1 = mapList1;
	}


	public Map<String, Object> getDoubleMap() {
		return doubleMap;
	}


	public void setDoubleMap(Map<String, Object> doubleMap) {
		this.doubleMap = doubleMap;
	}


	public Integer getCountOne1() {
		return countOne1;
	}


	public void setCountOne1(Integer countOne1) {
		this.countOne1 = countOne1;
	}


	public List<StockQuant> getStockAllList() {
		return stockAllList;
	}


	public void setStockAllList(List<StockQuant> stockAllList) {
		this.stockAllList = stockAllList;
	}


	public Map<String, String> getNameMap() {
		return nameMap;
	}


	public void setNameMap(Map<String, String> nameMap) {
		this.nameMap = nameMap;
	}

}

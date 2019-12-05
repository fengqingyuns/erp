package com.hanyun.scm.api.domain.response;

import java.util.List;

public class ExportResponse {
	//库存分布明细返回的list
	private List<List<Object>> resultList;
	//库存分布明细返回的仓库数组
	private String[] resultWarehouse;
	
	public ExportResponse(){
		
	}
	public ExportResponse(List<List<Object>> resultList,String[] resultWarehouse){
		this.resultList = resultList;
		this.resultWarehouse = resultWarehouse;
	}
	
	public List<List<Object>> getResultList() {
		return resultList;
	}
	public void setResultList(List<List<Object>> resultList) {
		this.resultList = resultList;
	}
	public String[] getResultWarehouse() {
		return resultWarehouse;
	}
	public void setResultWarehouse(String[] resultWarehouse) {
		this.resultWarehouse = resultWarehouse;
	}
	
	
}

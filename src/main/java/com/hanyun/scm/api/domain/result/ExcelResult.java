package com.hanyun.scm.api.domain.result;

/**
 * 
 * @ClassName: ExcelResult
 * @Description: 导出excel封装类
 * @author 王超群
 * @date 2016年12月9日 下午3:52:50
 *
 */
public class ExcelResult {
	
	private String supplierName;//供应商名字
	
	private Long total_price;//采购总价
	
	private Long total_number;//采购数量
	
	private Double avg_price;//采购平均价
	
	private String orderRatio;//采购占比
	
	private Long total_stock_price;//退货总价
	
	private Long total_stock_number;//退货数
	
	private String stockRatio;//退货占比
	
	private String returnRate;//退货率

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Long getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Long total_price) {
		this.total_price = total_price;
	}

	public Long getTotal_number() {
		return total_number;
	}

	public void setTotal_number(Long total_number) {
		this.total_number = total_number;
	}

	public Double getAvg_price() {
		return avg_price;
	}

	public void setAvg_price(Double avg_price) {
		this.avg_price = avg_price;
	}

	public Long getTotal_stock_price() {
		return total_stock_price;
	}

	public void setTotal_stock_price(Long total_stock_price) {
		this.total_stock_price = total_stock_price;
	}

	public Long getTotal_stock_number() {
		return total_stock_number;
	}

	public void setTotal_stock_number(Long total_stock_number) {
		this.total_stock_number = total_stock_number;
	}

	public String getOrderRatio() {
		return orderRatio;
	}

	public void setOrderRatio(String orderRatio) {
		this.orderRatio = orderRatio;
	}

	public String getStockRatio() {
		return stockRatio;
	}

	public void setStockRatio(String stockRatio) {
		this.stockRatio = stockRatio;
	}

	public String getReturnRate() {
		return returnRate;
	}

	public void setReturnRate(String returnRate) {
		this.returnRate = returnRate;
	}
	
}

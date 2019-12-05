package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.StockPickingGoods;
import com.hanyun.scm.api.domain.request.stock.StockPickingGoodsRequest;

public interface StockPickingGoodsService {

	/**
	 * 
	 * @Title: select 
	 * @Description: 采购入库商品查询
	 * @author: 高杨
	 * @param stockPickingGoodsRequest
	 * @return
	 * @return: HttpResponse
	 * @date: 2016年12月13日 下午7:25:53
	 */
	HttpResponse select(StockPickingGoodsRequest stockPickingGoodsRequest);
	
	/**
	 * 
	 * @Title: queryGoods 
	 * @Description: 采购入库商品查询
	 * @author: 高杨
	 * @param record
	 * @return
	 * @return: HttpResponse
	 * @date: 2016年12月13日 下午7:26:27
	 */
	HttpResponse queryGoods(StockPickingGoods record);
}

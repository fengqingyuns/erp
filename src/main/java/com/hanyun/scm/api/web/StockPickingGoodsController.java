package com.hanyun.scm.api.web;

import com.hanyun.scm.api.domain.StockPickingGoods;
import com.hanyun.scm.api.domain.request.stock.StockPickingGoodsRequest;
import com.hanyun.scm.api.service.StockPickingGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value="/stockPickingGoods")
public class StockPickingGoodsController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StockPickingGoodsController.class);

    @Resource
    private StockPickingGoodsService stockPickingGoods;
	
    @GetMapping(value="/query", produces = "application/json")
    @ResponseBody
    public Object select(StockPickingGoodsRequest stockPickingGoodsRequest) {
        return stockPickingGoods.select(stockPickingGoodsRequest);
    }
    /**
     * 
     * @Title: 查询收货单商品
     * @Description: TODO
     * @author: 唐启明
     * @param stockPickingGoods
     * @return
     * @return: Object
     * @date: 2016年11月25日 下午7:01:46
     */
    @GetMapping(value="/queryGoods", produces = "application/json")
    @ResponseBody
    public Object queryGoods(StockPickingGoods record) {
        return stockPickingGoods.queryGoods(record);
    }
}

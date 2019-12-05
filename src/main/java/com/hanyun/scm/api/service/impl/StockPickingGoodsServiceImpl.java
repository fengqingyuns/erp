package com.hanyun.scm.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hanyun.ground.util.http.HttpClient;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.common.UtilCommon;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.StockPickingGoodsDao;
import com.hanyun.scm.api.domain.StockPickingGoods;
import com.hanyun.scm.api.domain.request.stock.StockPickingGoodsRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.domain.response.StockPickingGoodsResponse;
import com.hanyun.scm.api.service.StockPickingGoodsService;

@Service
public class StockPickingGoodsServiceImpl implements StockPickingGoodsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockPickingGoodsServiceImpl.class);
    @Resource
    private StockPickingGoodsDao stockPickingGoodsDao;

    @Override
    public HttpResponse select(StockPickingGoodsRequest stockPickingGoodsRequest) {

        try {
//			stockPickingGoodsRequest.dealWithPage(0);
            List<StockPickingGoods> stockPickingGoodsList = stockPickingGoodsDao.select(stockPickingGoodsRequest);
            int count = stockPickingGoodsDao.countAll(stockPickingGoodsRequest);
            BaseResponse response = new BaseResponse(count, stockPickingGoodsList);
            return HttpResponse.success(response);
        } catch (Exception e) {
            LOGGER.error("查询出入库详单失败!", e);
            return HttpResponse.failure(ResultCode.STOCKPICKINGGOODS_DATA_NOT_FOUND);
        }

    }

    /**
     * @param record
     * @return
     * @Title: 查询收货单商品
     * @Description: TODO
     * @author: 唐启明
     * @date: 2016年11月25日 下午7:04:06
     */
    @Override
    public HttpResponse queryGoods(StockPickingGoods record) {
        try {
            List<StockPickingGoods> stockPickingGoodsList = stockPickingGoodsDao.selectStockPickingGoods(record);
            int count = stockPickingGoodsDao.countGoodsAll(record);
            double pickingTotalPrice = 0;
            double receiveTotalAmount = 0;
            for (StockPickingGoods stockPickingGoods : stockPickingGoodsList) {
                long receiveAmount = stockPickingGoods.getPickingAmount();
                double pickingPrice = (stockPickingGoods.getPickingPrice().doubleValue()) / 100;
                receiveTotalAmount = receiveTotalAmount + receiveAmount;
                pickingTotalPrice = pickingTotalPrice + UtilCommon.getMultiplyValue(pickingPrice, receiveAmount);
            }
            StockPickingGoodsResponse response = new StockPickingGoodsResponse(count, stockPickingGoodsList, pickingTotalPrice, receiveTotalAmount);
            return HttpResponse.success(response);
        } catch (Exception e) {
            LOGGER.error("收货单商品--查询收货单商品列表失败!", e);
            return HttpResponse.failure(ResultCode.RECEIPTGOODS_QUERY_ERROR);
        }
    }


}

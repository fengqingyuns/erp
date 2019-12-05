package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.BaseParams;
import com.hanyun.scm.api.domain.StockSpillLossOrder;
import com.hanyun.scm.api.domain.request.stock.StockSpillLossOrderRequest;

import java.util.List;

import org.springframework.stereotype.Repository;


@SuppressWarnings("UnnecessaryInterfaceModifier")
@Repository
public interface StockSpillLossOrderDao {

    public int deleteByPrimaryKey(Long id);

    public int insertSelective(StockSpillLossOrder record);

    public List<StockSpillLossOrder> select(StockSpillLossOrderRequest stockSpillLossOrderRequest);

    public int modifyOrder(StockSpillLossOrder record);
    
    public int countAll(StockSpillLossOrderRequest stockSpillLossOrderRequest);
    
    public int aduitOrder(StockSpillLossOrder record);

    /**
     * 查询详情
     * @param id    单据id
     * @return  返回值
     */
    public StockSpillLossOrder selectByVarianceId(String id);

    public List<String> queryIds(BaseParams params);
}
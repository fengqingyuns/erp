package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.StockSpillLossOrderDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("UnnecessaryInterfaceModifier")
@Repository
public interface StockSpillLossOrderDetailDao {

    public int deleteByPrimaryKey(@Param("goodsId")String goodsId,@Param("stockVarianceId")String stockVarianceId);

    public int insertSelective(StockSpillLossOrderDetail record);

    public List<StockSpillLossOrderDetail> select(StockSpillLossOrderDetail record);

    public int updateByPrimaryKey(StockSpillLossOrderDetail record);

    List<StockSpillLossOrderDetail> selectScrapNum(StockSpillLossOrderDetail stockSpillLossOrderDetail);

    public List<StockSpillLossOrderDetail> queryStoreScrapNum(@Param("scrapIds") List<String> scrapIds);

    public List<StockSpillLossOrderDetail> queryBrandScrapNum(@Param("scrapIds") List<String> scrapIds);
}
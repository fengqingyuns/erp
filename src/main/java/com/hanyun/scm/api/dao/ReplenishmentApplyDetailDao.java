package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.ReplenishmentApplyDetail;
import java.util.List;

import com.hanyun.scm.api.domain.request.aggregate.AggregateDistributionRequest;
import com.hanyun.scm.api.domain.request.aggregate.AggregateStatisticsRequest;
import com.hanyun.scm.api.domain.response.aggregate.AggregateStatisticsResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑, 永无BUG!
 * 　　　　┃　　　┃Code is far away from bug with the animal protecting
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * </pre>
 */
@SuppressWarnings("UnnecessaryInterfaceModifier")
@Repository
public interface ReplenishmentApplyDetailDao {

    public int insertSelective(ReplenishmentApplyDetail record);

    public ReplenishmentApplyDetail selectByPrimaryKey(String replenishmentDetailId);

    public List<ReplenishmentApplyDetail> selectByReplenishmentId(@Param("replenishmentId") String replenishmentId);

    public List<ReplenishmentApplyDetail> selectSelective(ReplenishmentApplyDetail record);

//    public int updateByPrimaryKeySelective(ReplenishmentApplyDetail record);

    public int deleteByPrimaryKey(@Param("goodsId") String goodsId, @Param("replenishmentId") String replenishmentId);

    public int updateByReplenishmentId(ReplenishmentApplyDetail replenishmentApplyDetail);

    public List<String> selectGoodsIdsForAggregate(AggregateDistributionRequest aggregateDistributionRequest);

    public List<ReplenishmentApplyDetail> selectListForAggregate(AggregateDistributionRequest aggregateDistributionRequest);

    public List<AggregateStatisticsResponse> statisticsApplyNum(AggregateStatisticsRequest aggregateStatisticsRequest);

    public int countGoods(@Param("replenishmentId")String replenishmentId);

    public int aduitDetail(String replenishmentId);

    public List<ReplenishmentApplyDetail> queryBrandApplyNum(@Param("ids") List<String> ids);

    public List<ReplenishmentApplyDetail> queryStoreApplyNum(@Param("ids") List<String> ids);

    public int countAll(ReplenishmentApplyDetail record);
}
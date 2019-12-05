package com.hanyun.scm.api.dao;

import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.scm.api.domain.ReplenishmentApplyDetail;
import com.hanyun.scm.api.domain.request.aggregate.AggregateDistributionRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class ReplenishmentApplyDetailDaoTest {

    @Resource
    private ReplenishmentApplyDetailDao replenishmentApplyDetailDao;

    @Test
    public void testSelectGoodsIdsForAggregate() throws Exception {
        AggregateDistributionRequest aggregateDistributionRequest = new AggregateDistributionRequest();
        String[] ids = new String[] {"dcc4ff58-b2c8-4525-bd02-fe300e805997"};
        List<String> applyIds = Arrays.asList(ids);
        aggregateDistributionRequest.setApplyIds(applyIds);
        List<String> goodsIds = replenishmentApplyDetailDao.selectGoodsIdsForAggregate(aggregateDistributionRequest);
        System.err.println(goodsIds);
    }

    @Test
    public void testSelectListForAggregate() throws Exception {
        AggregateDistributionRequest aggregateDistributionRequest = new AggregateDistributionRequest();
        String[] ids = new String[] {"dcc4ff58-b2c8-4525-bd02-fe300e805997"};
        List<String> applyIds = Arrays.asList(ids);
        aggregateDistributionRequest.setApplyIds(applyIds);
        List<String> goodsIds = new ArrayList<>();
        goodsIds.add("4c4ff225-8730-44c4-9a0c-0aafc49ec52b");
        aggregateDistributionRequest.setGoodsIds(goodsIds);
        List<ReplenishmentApplyDetail> replenishmentApplyDetailList = replenishmentApplyDetailDao.selectListForAggregate(aggregateDistributionRequest);
        System.err.println(JsonUtil.toJson(replenishmentApplyDetailList));
    }


}
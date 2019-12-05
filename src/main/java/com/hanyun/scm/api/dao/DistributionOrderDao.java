package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.DistributionOrder;
import com.hanyun.scm.api.domain.request.distribution.DistributionOrderCreateRequest;
import com.hanyun.scm.api.domain.request.distribution.DistributionOrderModifyRequest;
import com.hanyun.scm.api.domain.request.distribution.DistributionOrderQueryRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

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
public interface DistributionOrderDao {

    public int deleteByDistributionOrderId(String distributionOrderId);

    public int insert(DistributionOrderCreateRequest record);

    public DistributionOrder selectByDistributionOrderId(String distributionOrderId);

    public int countAll(DistributionOrderQueryRequest record);

    public List<DistributionOrder> select(DistributionOrderQueryRequest record);

    public int updateByDistributionOrderId(DistributionOrderModifyRequest record);

    public List<DistributionOrder> selectSourceApplyId(DistributionOrderQueryRequest record);
}
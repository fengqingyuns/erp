package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.PurchasePlan;
import java.util.List;

import com.hanyun.scm.api.domain.request.purchase.plan.PurchasePlanCreateRequest;
import com.hanyun.scm.api.domain.request.purchase.plan.PurchasePlanModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.plan.PurchasePlanQueryRequest;
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
public interface PurchasePlanDao {

    public int delete(String planId);

    public int insertSelective(PurchasePlanCreateRequest purchasePlanCreateRequest);

    public PurchasePlan selectByPlanId(String planId);

    public List<PurchasePlan> select(PurchasePlanQueryRequest purchasePlanQueryRequest);

    public int countAll(PurchasePlanQueryRequest purchasePlanQueryRequest);

    public int updateByPurchasePlan(PurchasePlanModifyRequest purchasePlanModifyRequest);
}
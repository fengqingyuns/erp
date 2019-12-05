package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.PurchaseApply;
import com.hanyun.scm.api.domain.PurchasePlan;
import com.hanyun.scm.api.domain.PurchasePlanApply;
import java.util.List;

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
public interface PurchasePlanApplyDao {

    public List<PurchasePlanApply> selectByPlanId(String planId);

    public PurchasePlanApply selectByPlanApplyId(String planApplyId);

    public List<PurchasePlanApply> select(PurchasePlanApply purchasePlanApply);

    public int delete(String planApplyId);

    public int deleteByPlanId(String planId);

    public int insertSelective(PurchasePlanApply purchasePlanApply);
}
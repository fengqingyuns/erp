package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.PurchaseApplyGoods;
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
public interface PurchaseApplyGoodsDao {

    public List<PurchaseApplyGoods> select(PurchaseApplyGoods purchaseApplyGoods);

    public int countAll(PurchaseApplyGoods purchaseApplyGoods);

    public List<PurchaseApplyGoods> selectByApplyId(String applyId);

    public PurchaseApplyGoods selectByApplyGoodsId(String applyGoodsId);

    public int deleteByApplyGoodsId(String applyGoodsId);

    public int deleteByApplyId(String applyId);

    public int insertSelective(PurchaseApplyGoods purchaseApplyGoods);

    public int updateByPurchaseApply(PurchaseApplyGoods purchaseApplyGoods);
}
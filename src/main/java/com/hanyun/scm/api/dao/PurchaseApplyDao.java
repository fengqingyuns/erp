package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.PurchaseApply;
import com.hanyun.scm.api.domain.request.purchase.apply.PurchaseApplyCreateRequest;
import com.hanyun.scm.api.domain.request.purchase.apply.PurchaseApplyModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.apply.PurchaseApplyQueryRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("UnnecessaryInterfaceModifier")
@Repository
public interface PurchaseApplyDao {

    /**
     * 查询采购申请单
     *
     * @param purchaseApplyQueryRequest 查询采购申请单参数
     * @return 列表
     * @author tianye@hanyun.com
     */
    public List<PurchaseApply> select(PurchaseApplyQueryRequest purchaseApplyQueryRequest);

    /**
     * 查询采购申请单条数
     *
     * @param purchaseApplyQueryRequest 查询采购申请单参数
     * @return 条数
     * @author tianye@hanyun.com
     */
    public int countAll(PurchaseApplyQueryRequest purchaseApplyQueryRequest);

    /**
     * 添加采购申请单
     *
     * @param purchaseApplyCreateRequest 天价采购申请单参数
     * @return 影响行数
     * @author tianye@hanyun.com
     */
    public int insertPurchaseApply(PurchaseApplyCreateRequest purchaseApplyCreateRequest);

    /**
     * 更新采购申请单
     *
     * @param purchaseApplyModifyRequest 更新采购申请单参数
     * @return 影响行数
     * @author tianye@hanyun.com
     */
    public int updatePurchaseApply(PurchaseApplyModifyRequest purchaseApplyModifyRequest);

    /**
     * 查询采购申请单详情
     *
     * @param applyId 申请单id
     * @return 申请单
     * @author tianye@hanyun.com
     */
    public PurchaseApply selectByApplyId(String applyId);

    /**
     * 删除采购申请单
     *
     * @param purchaseApplyId 申请单id
     * @return 影响行数
     * @author tianye@hanyun.com
     */
    public int delete(String purchaseApplyId);
}
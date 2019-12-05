package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.PurchaseOrder;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderCreateRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderQueryRequest;
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
public interface PurchaseOrderDao {

    /**
     * 创建采购订单
     * @param purchaseOrderCreateRequest    创建参数
     * @return  返回值
     */
    public int insertSelective(PurchaseOrderCreateRequest purchaseOrderCreateRequest);

    /**
     * 更新参数
     * @param purchaseOrderModifyRequest    更新参数
     * @return  返回值
     */
    public int updateByPrimaryKeySelective(PurchaseOrderModifyRequest purchaseOrderModifyRequest);

    /**
     * 查询采购订单详情
     * @param orderId   订单id
     * @return  返回值
     */
    public PurchaseOrder selectByOrderId(String orderId);

    /**
     * 删除采购订单
     * @param orderId   订单id
     * @return  返回值
     */
    public int deleteOrder(String orderId);

    /**
     * 查询采购订单(采购分析使用)
     * @param purchaseOrder 参数
     * @return  返回值
     */
    public List<PurchaseOrder> selectOrderId(PurchaseOrder purchaseOrder);

    /**
     * 查询采购订单列表(采购入库和退货单使用)
     *
     * @param purchaseOrderQueryRequest 查询参数
     * @return  返回值
     */
    public List<PurchaseOrder> queryForPurchaseReturn(PurchaseOrderQueryRequest purchaseOrderQueryRequest);

    /**
     * 查询采购订单总数(采购入库和退货单使用)
     *
     * @param purchaseOrderQueryRequest 查询参数
     * @return  返回值
     */
    public Integer countAllForPurchaseReturn(PurchaseOrderQueryRequest purchaseOrderQueryRequest);

    /**
     * 查询条数
     * @param purchaseOrderQueryRequest 查询条件
     * @return  返回值
     */
    public Integer countAll(PurchaseOrderQueryRequest purchaseOrderQueryRequest);

    /**
     * 查询列表
     * @param purchaseOrderQueryRequest 查询参数
     * @return  返回值
     */
    public List<PurchaseOrder> select(PurchaseOrderQueryRequest purchaseOrderQueryRequest);

    /**
     *
     * @param purchaseOrderQueryRequest 查詢入库单关联订单
     * @return 返回值
     */
    public List<PurchaseOrder> queryForPurchaseStockIn(PurchaseOrderQueryRequest purchaseOrderQueryRequest);

    /**
     * 查询订单id集合
     * @param purchaseOrderQueryRequest 查询条件
     * @return
     */
    public List<String> selectOrderIds(PurchaseOrderQueryRequest purchaseOrderQueryRequest);

    /**
     * 查询订单id集合 for 入库
     * @param purchaseOrderQueryRequest 查询条件
     * @return
     */
    public List<String> selectOrderIdsForStockIn(PurchaseOrderQueryRequest purchaseOrderQueryRequest);


}
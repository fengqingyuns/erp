package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.PurchaseOrderDetail;
import com.hanyun.scm.api.domain.response.purchase.PurchaseOrderStatisticsResponse;
import org.apache.ibatis.annotations.Param;
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
public interface PurchaseOrderDetailDao {

    public int insertSelective(PurchaseOrderDetail record);

    public int updateByPrimaryKeySelective(PurchaseOrderDetail record);

    public int countAll(PurchaseOrderDetail purchaseOrderDetail);

    public List<PurchaseOrderDetail> select(PurchaseOrderDetail purchaseOrderDetail);

    public int deleteByPrimaryKey(String orderDetailId);

    public int deleteByOrderId(String orderId);

    public List<PurchaseOrderDetail> selectByOrderId(String orderId);

    public List<PurchaseOrderDetail> selectDetailForStockIn(String orderId);

    public List<PurchaseOrderStatisticsResponse> statistics(@Param("orderIds") List<String> orderIds);
}
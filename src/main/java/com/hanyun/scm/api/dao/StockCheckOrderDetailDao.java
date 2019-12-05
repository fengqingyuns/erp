package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.StockCheckOrderDetail;
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
public interface StockCheckOrderDetailDao {

    public int countAll(StockCheckOrderDetail record);

    public int deleteByStockCheckOrderDetailId(String stockCheckOrderDetailId);

    public int insert(StockCheckOrderDetail record);

    public StockCheckOrderDetail selectByStockCheckOrderDetailId(String stockCheckOrderDetailId);

    public List<StockCheckOrderDetail> select(StockCheckOrderDetail record);

    public int updateByStockCheckOrderDetailId(StockCheckOrderDetail record);

    public List<StockCheckOrderDetail> queryDetailByStockTaskID(@Param("stockCheckTaskId") String stockCheckTaskId);

    public int audit(@Param("id") String stockCheckOrderId);
}
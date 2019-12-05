package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.StockCheckOrder;
import com.hanyun.scm.api.domain.request.stock.StockCheckOrderCreateRequest;
import com.hanyun.scm.api.domain.request.stock.StockCheckOrderModifyRequest;
import com.hanyun.scm.api.domain.request.stock.StockCheckOrderQueryRequest;
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
public interface StockCheckOrderDao {

    public int countAll(StockCheckOrderQueryRequest record);

    public int deleteByStockCheckOrderId(String stockCheckOrderId);

    public int insert(StockCheckOrderCreateRequest record);

    public StockCheckOrder selectByStockCheckOrderId(String stockCheckOrderId);

    public List<StockCheckOrder> select(StockCheckOrderQueryRequest record);

    public int updateByStockCheckOrderId(StockCheckOrderModifyRequest record);
}
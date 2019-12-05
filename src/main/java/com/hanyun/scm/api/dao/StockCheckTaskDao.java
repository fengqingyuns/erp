package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.StockCheckTask;
import com.hanyun.scm.api.domain.request.BrandStoreRequest;
import com.hanyun.scm.api.domain.request.stock.StockCheckTaskCreateRequest;
import com.hanyun.scm.api.domain.request.stock.StockCheckTaskModifyRequest;
import com.hanyun.scm.api.domain.request.stock.StockCheckTaskQueryRequest;
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
public interface StockCheckTaskDao {

    public int deleteByStockCheckTaskId(String id);

    public int insertSelective(StockCheckTaskCreateRequest stockCheckTaskCreateRequest);

    public StockCheckTask selectByStockCheckTaskId(String id);

    public int countAll(StockCheckTaskQueryRequest stockCheckTaskQueryRequest);

    public List<StockCheckTask> select(StockCheckTaskQueryRequest stockCheckTaskQueryRequest);

    public int updateByStockCheckTaskId(StockCheckTaskModifyRequest stockCheckTaskModifyRequest);
    
    public int updateByStockCheckTask(StockCheckTask stockCheckTask);

    /**
     * 查询待盘点订单
     * @param brandStoreRequest 待盘点订单参数
     * @return
     */
    public Long waitForStockCheckCount(BrandStoreRequest brandStoreRequest);
}
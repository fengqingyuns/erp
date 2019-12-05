package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.StockCheckDifference;
import com.hanyun.scm.api.domain.request.stock.StockCheckDifferenceRequest;

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
public interface StockCheckDifferenceDao {

    public int deleteByPrimaryKey(Long id);

    public int insert(StockCheckDifference record);

    public StockCheckDifference selectByPrimaryKey(String stockCheckDifferenceId);

    public List<StockCheckDifference> selectSelective(StockCheckDifference record);

    public int update(StockCheckDifference record);
    
    public List<StockCheckDifference> select(StockCheckDifferenceRequest stockCheckDifferenceRequest);
    
    public int countAll(StockCheckDifferenceRequest stockCheckDifferenceRequest);
    
    public int audit(StockCheckDifference record);
}
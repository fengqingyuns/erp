package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.StockQuant;
import com.hanyun.scm.api.domain.request.BrandStoreRequest;
import com.hanyun.scm.api.domain.request.stock.StockQuantQueryRequest;
import com.hanyun.scm.api.domain.request.stock.StoreGoodsStock;
import com.hanyun.scm.api.domain.response.stock.TotalStockResponse;
import com.hanyun.scm.api.domain.result.StockQuantResult;
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
public interface StockQuantDao {

    public int deleteByStockQuantId(String id);

    public int insertSelective(StockQuant record);

    public StockQuant selectByStockQuantId(String id);

    public int countAll(StockQuantQueryRequest stockQuant);

    public List<StockQuantResult> queryDetailGroupCount(StockQuantQueryRequest stockQuant);

    //重构代码查询库存分布明细信息List
    public List<StockQuantResult> queryDetail(StockQuantQueryRequest stockQuant);

    public List<StockQuantResult> queryDetailGroupList(StockQuantQueryRequest stockQuant);


    public List<StockQuant> select(StockQuantQueryRequest record);

    //查询明细数量
    public List<StockQuant> countAllDetial(StockQuantQueryRequest stockQuant);

    //查询明细集合
    public List<StockQuant> selectDetail(StockQuantQueryRequest stockQuant);

    public List<StockQuant> selectALL(StockQuant stockQuant);

    public int updateByStockQuantId(StockQuant record);

    //查询商品分类
    public List<StockQuant> selectProductClassfily(StockQuantQueryRequest stockQuant);

    public List<StockQuant> selectALLGoodsBrand(StockQuant stockQuant);

    public List<StockQuant> queryWarehouseId(StockQuantQueryRequest record);

    /**
     * 查询总库存
     *
     * @param brandStoreRequest 查询总库存参数
     * @return  查询总库存返回信息
     */
    public TotalStockResponse totalStock(BrandStoreRequest brandStoreRequest);

    /**
     * 删除库存
     * @param stockQuant 删除库存参数
     * @return  返回值
     * @author tianye@hanyun.com
     */
    int deleteStockQuant(StockQuant stockQuant);

    public List<StockQuant> selectWarehouseGoods(StockQuant query);

    /**
     * 查询门店商品库存
     * @param storeGoodsStock   参数
     * @return  返回值
     */
    List<StockQuant> selectStoreGoods(StoreGoodsStock storeGoodsStock);

    /**
     * 库存置为无效
     * @param stockQuantId  库存id
     * @return  返回值
     */
    public int invalidStock(@Param("stockQuantId") String stockQuantId);
}
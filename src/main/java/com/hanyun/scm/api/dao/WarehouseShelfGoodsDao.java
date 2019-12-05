package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.WarehouseShelfGoods;
import com.hanyun.scm.api.domain.request.warehouse.GoodsShelfQueryRequest;
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
public interface WarehouseShelfGoodsDao {

    /**
     * 删除货架商品(必须包含货架id、所在层数和商品id)
     *
     * @param warehouseShelfGoods 删除条件
     * @return 返回值
     */
    public int deleteShelfGoods(WarehouseShelfGoods warehouseShelfGoods);

    /**
     * 添加货架商品
     *
     * @param warehouseShelfGoods 商品信息
     * @return 返回值
     */
    public int insertSelective(WarehouseShelfGoods warehouseShelfGoods);

    /**
     * 批量添加货架商品
     *
     * @param warehouseShelfGoodsList 货架商品
     * @return 返回值
     */
    public int batchInserts(@Param("goodsList") List<WarehouseShelfGoods> warehouseShelfGoodsList);

    /**
     * 查询货架商品列表
     *
     * @param query 查询条件
     * @return 返回值
     */
    public List<WarehouseShelfGoods> select(WarehouseShelfGoods query);

    /**
     * 查询数据条数
     *
     * @param query 查询条件
     * @return 返回值
     */
    public int countAll(WarehouseShelfGoods query);

    /**
     * 更新货架商品
     *
     * @param warehouseShelfGoods 更新条件
     * @return 返回值
     */
    public int updateShelfGoods(WarehouseShelfGoods warehouseShelfGoods);

    /**
     * 删除货架所有商品
     *
     * @param shelfId 货架id
     * @return 返回值
     */
    public int deleteByShelfId(String shelfId);

    /**
     * 查询仓库商品位置
     * @param goodsShelfQueryRequest    参数
     * @return  返回值
     */
    public List<WarehouseShelfGoods> selectGoodsShelf(GoodsShelfQueryRequest goodsShelfQueryRequest);
}
package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.WarehouseShelfType;
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
public interface WarehouseShelfTypeDao {

    /**
     * 删除货架分类
     *
     * @param shelfTypeId 货架分类id
     * @return 影响行数
     */
    public int deleteById(String shelfTypeId);

    /**
     * 添加货架分类
     *
     * @param warehouseShelfType 货架分类信息
     * @return 影响行数
     */
    public int insertSelective(WarehouseShelfType warehouseShelfType);

    /**
     * 查询货架分类
     *
     * @param shelfTypeId 货架分类id
     * @return 货架分类信息
     */
    public WarehouseShelfType selectById(String shelfTypeId);

    /**
     * 查询货架分类列表
     *
     * @param query 查询条件
     * @return 返回值
     */
    public List<WarehouseShelfType> select(WarehouseShelfType query);

    /**
     * 查询数据条数
     *
     * @param query 查询条件
     * @return 返回值
     */
    public int countAll(WarehouseShelfType query);

    /**
     * 更新货架分类信息
     *
     * @param warehouseShelfType 货架分类信息
     * @return 影响行数
     */
    public int updateByPrimaryKeySelective(WarehouseShelfType warehouseShelfType);
}
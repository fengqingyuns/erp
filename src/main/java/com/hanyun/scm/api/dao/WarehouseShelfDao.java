package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.WarehouseShelf;
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
public interface WarehouseShelfDao {

    /**
     * 删除货架
     *
     * @param shelfId 货架id
     * @return 影响行数
     */
    public int deleteById(String shelfId);

    /**
     * 添加货架
     *
     * @param warehouseShelf 货架信息
     * @return 影响行数
     */
    public int insertSelective(WarehouseShelf warehouseShelf);

    /**
     * 查询货架
     *
     * @param shelfId 货架id
     * @return 货架信息
     */
    public WarehouseShelf selectById(String shelfId);

    /**
     * 查询货架列表
     *
     * @param query 查询条件
     * @return 返回值
     */
    public List<WarehouseShelf> select(WarehouseShelf query);

    /**
     * 查询数据条数
     *
     * @param query 查询条件
     * @return 返回值
     */
    public int countAll(WarehouseShelf query);

    /**
     * 更新货架信息
     *
     * @param warehouseShelf 货架信息
     * @return 影响行数
     */
    public int updateById(WarehouseShelf warehouseShelf);
}
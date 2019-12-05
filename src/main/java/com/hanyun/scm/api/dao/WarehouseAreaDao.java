package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.WarehouseArea;
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
public interface WarehouseAreaDao {

    /**
     * 删除区域
     *
     * @param areaId 区域id
     * @return 影响行数
     */
    public int deleteById(String areaId);

    /**
     * 添加区域
     *
     * @param warehouseArea 区域信息
     * @return 影响行数
     */
    public int insertSelective(WarehouseArea warehouseArea);

    /**
     * 查询区域
     *
     * @param areaId 区域id
     * @return 区域信息
     */
    public WarehouseArea selectById(String areaId);

    /**
     * 查询区域列表
     *
     * @param query 查询条件
     * @return 返回值
     */
    public List<WarehouseArea> select(WarehouseArea query);

    /**
     * 查询数据条数
     *
     * @param query 查询条件
     * @return 返回值
     */
    public int countAll(WarehouseArea query);

    /**
     * 更新区域信息
     *
     * @param warehouseArea 区域信息
     * @return 影响行数
     */
    public int updateById(WarehouseArea warehouseArea);
}
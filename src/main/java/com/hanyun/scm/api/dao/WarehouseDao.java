package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.Warehouse;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseCreateRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseModifyRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseQueryRequest;
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
public interface WarehouseDao {

	public Warehouse queryWarehouseName(Warehouse warehouse);
	
    public int deleteByWarehouseId(String warehouseId);

    public int insertSelective(WarehouseCreateRequest warehouseCreateRequest);

    public Warehouse selectBrandWarehouse(String brandId);

    public Warehouse selectByWarehouseId(String warehouseId);

    public List<Warehouse> select(WarehouseQueryRequest warehouseQueryRequest);

    public int updateByWarehouseId(WarehouseModifyRequest warehouseModifyRequest);

    int countAll(WarehouseQueryRequest warehouseQueryRequest);
    
    public List<Warehouse> selectNoPage(WarehouseQueryRequest warehouseQueryRequest);
}
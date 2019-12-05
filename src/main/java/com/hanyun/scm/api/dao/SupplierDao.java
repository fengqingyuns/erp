package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.Supplier;
import com.hanyun.scm.api.domain.request.supplier.SupplierCreateRequest;
import com.hanyun.scm.api.domain.request.supplier.SupplierModifyRequest;
import com.hanyun.scm.api.domain.request.supplier.SupplierQueryRequest;
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
public interface SupplierDao {

    public Supplier querySupplierName(Supplier supplier);

    public int countAll(SupplierQueryRequest supplier);

    public int insert(SupplierCreateRequest supplier);

    public Supplier selectBySupplierId(String supplierId);

    public List<Supplier> select(SupplierQueryRequest supplier);

    public int updateBySupplierId(SupplierModifyRequest supplier);

    public int updateDeleteStatusBySupplierId(@Param("supplierId") String supplierId, @Param("userId") String userId);

    public int deleteBySupplierId(String supplierId);

    public List<Supplier> querySupplier(@Param("supplierIds") List<String> supplierIds);
}
package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.Supplier;
import com.hanyun.scm.api.domain.SupplierGoods;
import java.util.List;

import com.hanyun.scm.api.domain.dto.SupplierDTO;
import org.apache.ibatis.annotations.Param;
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
public interface SupplierGoodsDao {

    public int deleteBySupplierGoodsId(String supplierGoodsId);

    public int deleteBySupplierIdAndGoodsId(@Param("supplierId") String supplierId, @Param("goodsId") String goodsId);

    public int deleteBySupplierId(String supplierId);

    public int insert(SupplierGoods record);

    public SupplierGoods selectBySupplierGoodsId(String supplierGoodsId);

    public List<SupplierGoods> select(SupplierGoods record);

    public int updateBySupplierGoodsId(SupplierGoods record);

    public int deleteSupplierGoodsBySupplierIdForStatus(String supplierId);

    public List<String> selectSupplierIds(SupplierDTO dto);
}
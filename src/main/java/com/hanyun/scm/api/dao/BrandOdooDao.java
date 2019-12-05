package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.BrandOdoo;
import com.hanyun.scm.api.domain.request.brand.BrandCreateRequest;
import com.hanyun.scm.api.domain.request.brand.BrandModifyRequest;
import com.hanyun.scm.api.domain.request.brand.BrandQueryRequest;
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
public interface BrandOdooDao {

    public int deleteByBrandId(String brandId);

    public int insertSelective(BrandCreateRequest brandCreateRequest);

    public BrandOdoo selectByBrandId(String brandId);

    public List<BrandOdoo> select(BrandQueryRequest brandQueryRequest);

    public int countAll(BrandQueryRequest brandQueryRequest);

    public int updateByBrandId(BrandModifyRequest brandModifyRequest);
}
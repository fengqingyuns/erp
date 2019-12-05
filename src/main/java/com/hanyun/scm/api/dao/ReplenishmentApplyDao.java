package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.BaseParams;
import com.hanyun.scm.api.domain.ReplenishmentApply;

import java.util.Date;
import java.util.List;

import com.hanyun.scm.api.domain.request.Replenishment.ReplenishmentApplyRequest;
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
public interface ReplenishmentApplyDao {

    public int insertSelective(ReplenishmentApply record);

    public ReplenishmentApply selectById(String id);

    public List<ReplenishmentApply> selectSelective(ReplenishmentApplyRequest replenishmentApplyRequest);

    public int updateByPrimaryKeySelective(ReplenishmentApply record);

    public int countAllSelect(ReplenishmentApplyRequest replenishmentApplyRequest);

    public List<ReplenishmentApply> selectIds(ReplenishmentApplyRequest replenishmentApplyRequest);

    public int invalidateApply(@Param("validTime") Date validTime);

    public List<String> queryIds(BaseParams params);
}
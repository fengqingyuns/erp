package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.BaseParams;
import com.hanyun.scm.api.domain.InspectionPickingIn;
import com.hanyun.scm.api.domain.request.InspectionPickingIn.InspectionPickingInRequest;
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
public interface InspectionPickingInDao {

    public List<InspectionPickingIn> selectOrders(InspectionPickingInRequest record);

    public int countAllOrders(InspectionPickingInRequest record);

    public int deleteByInspectionId(String inspectionId);

    public int insertSelective(InspectionPickingIn record);

    public InspectionPickingIn selectByPrimaryKey(@Param("inspectionId") String  inspectionId);

    public int updateByPrimaryKeySelective(InspectionPickingIn record);

    public int auditOrder(InspectionPickingIn inspectionPickingIn);

    public List<String> queryIds(BaseParams params);
}
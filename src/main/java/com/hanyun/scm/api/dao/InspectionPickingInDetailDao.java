package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.InspectionPickingInDetail;
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
public interface InspectionPickingInDetailDao {

    public int insertSelective(InspectionPickingInDetail record);

    public int updateDetail(InspectionPickingInDetail inspectionPickingInDetail);

    public List<InspectionPickingInDetail> selectByInspectionId(String inspectionPickingInId);

    public int deleteByIds(@Param("goodsId")String goodsId, @Param("inspectionPickingInId") String inspectionPickingInId);

    public int deleteByInspectionId(String inspectionId);

    public int auditOrder(String inspectionId);

    public List<InspectionPickingInDetail> queryByDistributionId(@Param("distributionOrderId") String distributionOrderId);

    public List<InspectionPickingInDetail> queryBrandReceiptNum(@Param("ids") List<String> ids);

    public List<InspectionPickingInDetail> queryStoreReceiptNum(@Param("ids") List<String> ids);

    public List<InspectionPickingInDetail> selectSelective(InspectionPickingInDetail record);

}
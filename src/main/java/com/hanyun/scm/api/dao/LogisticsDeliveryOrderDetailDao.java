package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.LogisticsDeliveryOrderDetail;
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
public interface LogisticsDeliveryOrderDetailDao {

    public int deleteByLogisticsDeliveryOrderDetailId(String logisticsDeliveryOrderDetailId);

    public int insert(LogisticsDeliveryOrderDetail record);

    public LogisticsDeliveryOrderDetail selectByLogisticsDeliveryOrderDetailId(String logisticsDeliveryOrderDetailId);

    public int countAll(LogisticsDeliveryOrderDetail record);

    public List<LogisticsDeliveryOrderDetail> select(LogisticsDeliveryOrderDetail record);

    public int updateByLogisticsDeliveryOrderDetailId(LogisticsDeliveryOrderDetail record);
}
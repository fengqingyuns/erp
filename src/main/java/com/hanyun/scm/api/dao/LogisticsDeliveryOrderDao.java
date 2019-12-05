package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.LogisticsDeliveryOrder;
import com.hanyun.scm.api.domain.request.LogisticsDelivery.LogisticsDeliveryCreateRequest;
import com.hanyun.scm.api.domain.request.LogisticsDelivery.LogisticsDeliveryModifyRequest;
import com.hanyun.scm.api.domain.request.LogisticsDelivery.LogisticsDeliveryQueryRequest;
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
public interface LogisticsDeliveryOrderDao {

    public int deleteByLogisticsDeliveryOrderId(String logisticsDeliveryOrderId);

    public int insert(LogisticsDeliveryCreateRequest record);

    public LogisticsDeliveryOrder selectByLogisticsDeliveryOrderId(String logisticsDeliveryOrderId);

    public int countAll(LogisticsDeliveryQueryRequest record);

    public List<LogisticsDeliveryOrder> select(LogisticsDeliveryQueryRequest record);

    public int updateByLogisticsDeliveryOrderId(LogisticsDeliveryModifyRequest record);
}
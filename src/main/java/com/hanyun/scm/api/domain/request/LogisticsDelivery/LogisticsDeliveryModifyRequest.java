package com.hanyun.scm.api.domain.request.LogisticsDelivery;

import com.hanyun.scm.api.domain.LogisticsDeliveryOrder;
import org.hibernate.validator.constraints.NotEmpty;

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
public class LogisticsDeliveryModifyRequest extends LogisticsDeliveryOrder {

    @NotEmpty
    private String logisticsDeliveryOrderId;

    @Override
    public String getLogisticsDeliveryOrderId() {
        return logisticsDeliveryOrderId;
    }

    @Override
    public void setLogisticsDeliveryOrderId(String logisticsDeliveryOrderId) {
        this.logisticsDeliveryOrderId = logisticsDeliveryOrderId;
    }
}
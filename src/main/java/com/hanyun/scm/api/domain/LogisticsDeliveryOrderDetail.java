package com.hanyun.scm.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanyun.scm.api.domain.request.BaseRequest;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

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
public class LogisticsDeliveryOrderDetail extends BaseRequest{
    @JsonIgnore
    private Long id;
    @NotEmpty
    private String brandId;

    private String storeId;

    private String logisticsDeliveryOrderDetailId;

    private String logisticsDeliveryOrderId;

    private String distributionOrderId;

    private String distributionOrderDocumentId;

    private Integer validStatus;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getLogisticsDeliveryOrderDetailId() {
        return logisticsDeliveryOrderDetailId;
    }

    public void setLogisticsDeliveryOrderDetailId(String logisticsDeliveryOrderDetailId) {
        this.logisticsDeliveryOrderDetailId = logisticsDeliveryOrderDetailId;
    }

    public String getLogisticsDeliveryOrderId() {
        return logisticsDeliveryOrderId;
    }

    public void setLogisticsDeliveryOrderId(String logisticsDeliveryOrderId) {
        this.logisticsDeliveryOrderId = logisticsDeliveryOrderId;
    }

    public String getDistributionOrderId() {
        return distributionOrderId;
    }

    public void setDistributionOrderId(String distributionOrderId) {
        this.distributionOrderId = distributionOrderId;
    }

    public String getDistributionOrderDocumentId() {
        return distributionOrderDocumentId;
    }

    public void setDistributionOrderDocumentId(String distributionOrderDocumentId) {
        this.distributionOrderDocumentId = distributionOrderDocumentId;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
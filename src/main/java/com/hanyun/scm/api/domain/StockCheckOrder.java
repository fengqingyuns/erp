package com.hanyun.scm.api.domain;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.hanyun.scm.api.domain.request.BaseRequest;

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
public class StockCheckOrder extends BaseRequest{

    private Long id;

    private String brandId;

    private String storeId;

    private String stockCheckOrderId;

    private String stockCheckOrderDocumentId;

    private String stockCheckTaskId;

    private String stockCheckTaskDocumentId;

    private String warehouseId;

    private String warehouseName;

    private String operatorId;

    private String operatorName;

    private String remark;

    private Integer stockCheckTaskType;

    private Integer stockCheckOrderType;

    private Integer stockCheckOrderStatus;

    private Integer validStatus;

    private String businessManId;

    private String businessManName;

    private String auditorId;

    private String auditorName;

    private Date auditTime;

    private Date createTime;

    private Date updateTime;

    private List<StockCheckOrderDetail> stockCheckOrderDetailList;

    private String stockCheckOrderStatusName;

    private String stockCheckTaskTypeName;

    private String stockCheckOrderTypeName;

    private Boolean auditStatus;

    private Boolean historyStatus;

    public Boolean getHistoryStatus() {
        return historyStatus;
    }

    public void setHistoryStatus(Boolean historyStatus) {
        this.historyStatus = historyStatus;
    }

    public Boolean getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Boolean auditStatus) {
        this.auditStatus = auditStatus;
    }

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

    public String getStockCheckOrderId() {
        return stockCheckOrderId;
    }

    public void setStockCheckOrderId(String stockCheckOrderId) {
        this.stockCheckOrderId = stockCheckOrderId;
    }

    public String getStockCheckOrderDocumentId() {
        return stockCheckOrderDocumentId;
    }

    public void setStockCheckOrderDocumentId(String stockCheckOrderDocumentId) {
        this.stockCheckOrderDocumentId = stockCheckOrderDocumentId;
    }

    public String getStockCheckTaskId() {
        return stockCheckTaskId;
    }

    public void setStockCheckTaskId(String stockCheckTaskId) {
        this.stockCheckTaskId = stockCheckTaskId;
    }

    public String getStockCheckTaskDocumentId() {
        return stockCheckTaskDocumentId;
    }

    public void setStockCheckTaskDocumentId(String stockCheckTaskDocumentId) {
        this.stockCheckTaskDocumentId = stockCheckTaskDocumentId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStockCheckTaskType() {
        return stockCheckTaskType;
    }

    public void setStockCheckTaskType(Integer stockCheckTaskType) {
        this.stockCheckTaskType = stockCheckTaskType;
    }

    public Integer getStockCheckOrderType() {
        return stockCheckOrderType;
    }

    public void setStockCheckOrderType(Integer stockCheckOrderType) {
        this.stockCheckOrderType = stockCheckOrderType;
    }

    public Integer getStockCheckOrderStatus() {
        return stockCheckOrderStatus;
    }

    public void setStockCheckOrderStatus(Integer stockCheckOrderStatus) {
        this.stockCheckOrderStatus = stockCheckOrderStatus;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public String getBusinessManId() {
        return businessManId;
    }

    public void setBusinessManId(String businessManId) {
        this.businessManId = businessManId;
    }

    public String getBusinessManName() {
        return businessManName;
    }

    public void setBusinessManName(String businessManName) {
        this.businessManName = businessManName;
    }

    public String getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(String auditorId) {
        this.auditorId = auditorId;
    }

    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
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

	public List<StockCheckOrderDetail> getStockCheckOrderDetailList() {
		return stockCheckOrderDetailList;
	}

	public void setStockCheckOrderDetailList(List<StockCheckOrderDetail> stockCheckOrderDetailList) {
		this.stockCheckOrderDetailList = stockCheckOrderDetailList;
	}

    public String getStockCheckOrderStatusName() {
        return stockCheckOrderStatusName;
    }

    public void setStockCheckOrderStatusName(String stockCheckOrderStatusName) {
        this.stockCheckOrderStatusName = stockCheckOrderStatusName;
    }

    public String getStockCheckTaskTypeName() {
        return stockCheckTaskTypeName;
    }

    public void setStockCheckTaskTypeName(String stockCheckTaskTypeName) {
        this.stockCheckTaskTypeName = stockCheckTaskTypeName;
    }

    public String getStockCheckOrderTypeName() {
        return stockCheckOrderTypeName;
    }

    public void setStockCheckOrderTypeName(String stockCheckOrderTypeName) {
        this.stockCheckOrderTypeName = stockCheckOrderTypeName;
    }
}
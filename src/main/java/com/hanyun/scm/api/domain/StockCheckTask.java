package com.hanyun.scm.api.domain;

import java.util.Date;
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
public class StockCheckTask {

    private Long id;

    private String brandId;

    private String storeId;

    private String stockCheckTaskId;

    private String stockCheckTaskDocumentId;

    private String warehouseId;

    private String warehouseName;

    private String operatorId;

    private String operatorName;

    private Integer stockCheckTaskStatus;

    private Integer stockCheckTaskType;

    private String remark;

    private Date taskStartTime;

    private Date taskEndTime;

    private String auditorId;

    private String auditorName;

    private Date auditTime;

    private Integer validStatus;

    private Date createTime;

    private Date updateTime;
    
    private String stockCheckTaskStatusName;
    
    private String stockCheckTaskTypeName;

    private List<StockCheckTaskDetail> stockCheckTaskDetailList;

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

    public List<StockCheckTaskDetail> getStockCheckTaskDetailList() {
        return stockCheckTaskDetailList;
    }

    public void setStockCheckTaskDetailList(List<StockCheckTaskDetail> stockCheckTaskDetailList) {
        this.stockCheckTaskDetailList = stockCheckTaskDetailList;
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

    public Date getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(Date taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public Date getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(Date taskEndTime) {
        this.taskEndTime = taskEndTime;
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

    public Integer getStockCheckTaskStatus() {
        return stockCheckTaskStatus;
    }

    public void setStockCheckTaskStatus(Integer stockCheckTaskStatus) {
        this.stockCheckTaskStatus = stockCheckTaskStatus;
    }

    public Integer getStockCheckTaskType() {
        return stockCheckTaskType;
    }

    public void setStockCheckTaskType(Integer stockCheckTaskType) {
        this.stockCheckTaskType = stockCheckTaskType;
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

	public String getStockCheckTaskStatusName() {
		return stockCheckTaskStatusName;
	}

	public void setStockCheckTaskStatusName(String stockCheckTaskStatusName) {
		this.stockCheckTaskStatusName = stockCheckTaskStatusName;
	}

	public String getStockCheckTaskTypeName() {
		return stockCheckTaskTypeName;
	}

	public void setStockCheckTaskTypeName(String stockCheckTaskTypeName) {
		this.stockCheckTaskTypeName = stockCheckTaskTypeName;
	}
    
}
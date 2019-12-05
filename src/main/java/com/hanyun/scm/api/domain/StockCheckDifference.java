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
public class StockCheckDifference {

    private Long id;

    private String brandId;

    private String storeId;

    private String stockCheckDifferenceId;

    private String stockCheckDifferenceDocumentId;

    private String stockCheckTaskId;

    private String stockCheckTaskDocumentId;

    private String warehouseId;

    private String warehouseName;

    private String operatorId;

    private String operatorName;

    private String remark;

    private Integer stockCheckDifferenceStatus;

    private Integer validStatus;

    private String businessManId;

    private String businessManName;

    private Integer stockCheckTaskType;

    private String auditorId;

    private String auditorName;

    private Date auditTime;

    private Date createTime;

    private Date updateTime;
    
    private String endTime;
    
    private String beginTime;
    
    private List<StockCheckDifferenceDetail> detailList;
    
    private String exportStatus;//导出用：单据状态
    
    private String exporttype;//导出用：盘点类型
    
    private List<StockSpillLossOrder> varianceList;

    private String userId;

    private Boolean auditStatus;

    private Boolean historyStatus;

    public Boolean getHistoryStatus() {
        return historyStatus;
    }

    public void setHistoryStatus(Boolean historyStatus) {
        this.historyStatus = historyStatus;
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

    public String getStockCheckDifferenceId() {
		return stockCheckDifferenceId;
	}

	public void setStockCheckDifferenceId(String stockCheckDifferenceId) {
		this.stockCheckDifferenceId = stockCheckDifferenceId;
	}

    public String getStockCheckDifferenceDocumentId() {
		return stockCheckDifferenceDocumentId;
	}

	public void setStockCheckDifferenceDocumentId(String stockCheckDifferenceDocumentId) {
		this.stockCheckDifferenceDocumentId = stockCheckDifferenceDocumentId;
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

    public Integer getStockCheckDifferenceStatus() {
        return stockCheckDifferenceStatus;
    }

    public void setStockCheckDifferenceStatus(Integer stockCheckDifferenceStatus) {
        this.stockCheckDifferenceStatus = stockCheckDifferenceStatus;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public Integer getStockCheckTaskType() {
        return stockCheckTaskType;
    }

    public void setStockCheckTaskType(Integer stockCheckTaskType) {
        this.stockCheckTaskType = stockCheckTaskType;
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

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public List<StockCheckDifferenceDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<StockCheckDifferenceDetail> detailList) {
		this.detailList = detailList;
	}

	public String getExportStatus() {
		return exportStatus;
	}

	public void setExportStatus(String exportStatus) {
		this.exportStatus = exportStatus;
	}

	public String getExporttype() {
		return exporttype;
	}

	public void setExporttype(String exporttype) {
		this.exporttype = exporttype;
	}

	public List<StockSpillLossOrder> getVarianceList() {
		return varianceList;
	}

	public void setVarianceList(List<StockSpillLossOrder> varianceList) {
		this.varianceList = varianceList;
	}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Boolean auditStatus) {
        this.auditStatus = auditStatus;
    }
}
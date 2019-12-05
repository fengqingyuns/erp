package com.hanyun.scm.api.domain;

import com.hanyun.scm.api.domain.request.BaseRequest;

import java.util.Date;
import java.util.List;

public class StockSpillLossOrder extends BaseRequest{

    private Long id;

    private String stockVarianceId;

    private String stockVarianceDocumentId;

    private String stockCheckDifferenceId;

    private String stockCheckDifferenceDocumentId;

    private String brandId;

    private String storeId;

    private Integer stockVarianceType;

    private Integer stockVarianceStatus;

    private Integer spillOverReason;

    private Integer lossReason;

    private Long differenceNum;

    private Long differencePrice;

    private String warehouseId;

    private String warehouseName;

    private String operatorId;

    private String operatorName;

    private String auditorId;

    private String auditorName;

    private Date auditorTime;

    private String remark;

    private String businessManId;

    private String businessManName;

    private Integer validStatus;

    private Date yeoperationTime;

    private Date createTime;

    private Date updateTime;
    
    private String beginTime;
    
    private String endTime;
    
    private String workTime;

    private String queryUpdateTime;

    private Integer delectAction;

    private List<StockSpillLossOrderDetail> detailList;

    private Integer differenceCreateLoss;

    private String exportType;

    private String exportStatus;

    private String exportReason;

    private String userId;

    private Boolean auditStatus;

    private Boolean historyStatus;

    public Boolean getHistoryStatus() {
        return historyStatus;
    }

    public void setHistoryStatus(Boolean historyStatus) {
        this.historyStatus = historyStatus;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockVarianceId() {
        return stockVarianceId;
    }

    public void setStockVarianceId(String stockVarianceId) {
        this.stockVarianceId = stockVarianceId;
    }

    public String getStockVarianceDocumentId() {
        return stockVarianceDocumentId;
    }

    public void setStockVarianceDocumentId(String stockVarianceDocumentId) {
        this.stockVarianceDocumentId = stockVarianceDocumentId;
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

    public Integer getStockVarianceType() {
		return stockVarianceType;
	}

	public void setStockVarianceType(Integer stockVarianceType) {
		this.stockVarianceType = stockVarianceType;
	}

	public Integer getStockVarianceStatus() {
		return stockVarianceStatus;
	}

	public void setStockVarianceStatus(Integer stockVarianceStatus) {
		this.stockVarianceStatus = stockVarianceStatus;
	}

	public Integer getSpillOverReason() {
		return spillOverReason;
	}

	public void setSpillOverReason(Integer spillOverReason) {
		this.spillOverReason = spillOverReason;
	}

	public Integer getLossReason() {
		return lossReason;
	}

	public void setLossReason(Integer lossReason) {
		this.lossReason = lossReason;
	}

	public Long getDifferenceNum() {
        return differenceNum;
    }

    public void setDifferenceNum(Long differenceNum) {
        this.differenceNum = differenceNum;
    }

	public Long getDifferencePrice() {
		return differencePrice;
	}

	public void setDifferencePrice(Long differencePrice) {
		this.differencePrice = differencePrice;
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

    public Date getAuditorTime() {
        return auditorTime;
    }

    public void setAuditorTime(Date auditorTime) {
        this.auditorTime = auditorTime;
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

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public Date getYeoperationTime() {
        return yeoperationTime;
    }

    public void setYeoperationTime(Date yeoperationTime) {
        this.yeoperationTime = yeoperationTime;
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

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getQueryUpdateTime() {
		return queryUpdateTime;
	}

	public void setQueryUpdateTime(String queryUpdateTime) {
		this.queryUpdateTime = queryUpdateTime;
	}

	public Integer getDelectAction() {
		return delectAction;
	}

	public void setDelectAction(Integer delectAction) {
		this.delectAction = delectAction;
	}

	public List<StockSpillLossOrderDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<StockSpillLossOrderDetail> detailList) {
		this.detailList = detailList;
	}

	public Integer getDifferenceCreateLoss() {
		return differenceCreateLoss;
	}

	public void setDifferenceCreateLoss(Integer differenceCreateLoss) {
		this.differenceCreateLoss = differenceCreateLoss;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public String getExportStatus() {
		return exportStatus;
	}

	public void setExportStatus(String exportStatus) {
		this.exportStatus = exportStatus;
	}

	public String getExportReason() {
		return exportReason;
	}

	public void setExportReason(String exportReason) {
		this.exportReason = exportReason;
	}
    
}
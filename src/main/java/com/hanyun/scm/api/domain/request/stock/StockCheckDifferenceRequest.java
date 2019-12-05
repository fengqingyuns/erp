package com.hanyun.scm.api.domain.request.stock;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanyun.scm.api.domain.StockCheckDifferenceDetail;
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
public class StockCheckDifferenceRequest extends BaseRequest{
	@JsonIgnore
    private Long id;
    @NotEmpty
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
    
    private String auditBeginTime;
    
    private String auditEndTime;
    
    private List<StockCheckDifferenceDetail> listDetail;

    private String userId;

    private List<String> ids;

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

	public String getAuditBeginTime() {
		return auditBeginTime;
	}

	public void setAuditBeginTime(String auditBeginTime) {
		this.auditBeginTime = auditBeginTime;
	}

	public String getAuditEndTime() {
		return auditEndTime;
	}

	public void setAuditEndTime(String auditEndTime) {
		this.auditEndTime = auditEndTime;
	}

	public List<StockCheckDifferenceDetail> getListDetail() {
		return listDetail;
	}

	public void setListDetail(List<StockCheckDifferenceDetail> listDetail) {
		this.listDetail = listDetail;
	}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
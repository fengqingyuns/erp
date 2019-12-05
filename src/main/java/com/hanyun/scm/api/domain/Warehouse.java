package com.hanyun.scm.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanyun.scm.api.domain.request.BaseRequest;

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
public class Warehouse extends BaseRequest {

    @JsonIgnore
    private Long id;

    private String brandId;

    private Integer odooCompanyId;

    private String userName;

    private String storeId;

    private String warehouseId;

    private String warehouseName;

    private Integer odooWarehouseId;

    private Integer odooLocationId;

    private Integer warehouseType;

    private Integer warehouseStatus;

    private String orgId;

    private String remark;

    private Integer resupply;

    private Date createTime;

    private Date updateTime;
    
    private Integer validStatus;

    private String extId;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getOdooCompanyId() {
        return odooCompanyId;
    }

    public void setOdooCompanyId(Integer odooCompanyId) {
        this.odooCompanyId = odooCompanyId;
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

    public Integer getOdooWarehouseId() {
        return odooWarehouseId;
    }

    public void setOdooWarehouseId(Integer odooWarehouseId) {
        this.odooWarehouseId = odooWarehouseId;
    }

    public Integer getOdooLocationId() {
        return odooLocationId;
    }

    public void setOdooLocationId(Integer odooLocationId) {
        this.odooLocationId = odooLocationId;
    }

    public Integer getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(Integer warehouseType) {
        this.warehouseType = warehouseType;
    }

    public Integer getWarehouseStatus() {
        return warehouseStatus;
    }

    public void setWarehouseStatus(Integer warehouseStatus) {
        this.warehouseStatus = warehouseStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getResupply() {
        return resupply;
    }

    public void setResupply(Integer resupply) {
        this.resupply = resupply;
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

	public Integer getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(Integer validStatus) {
		this.validStatus = validStatus;
	}

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }
}
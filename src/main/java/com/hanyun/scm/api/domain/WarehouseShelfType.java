package com.hanyun.scm.api.domain;

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
public class WarehouseShelfType extends BaseRequest {

    private Long id;

    private String brandId;

    private String storeId;

    private String warehouseId;

    private String warehouseName;

    private String shelfTypeId;

    private String shelfTypeName;

    private Integer shelfStorageNum;

    private Integer shelfLength;

    private Integer shelfWidth;

    private Integer shelfHeight;

    private String operatorId;

    private String operatorName;

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

    public String getShelfTypeId() {
        return shelfTypeId;
    }

    public void setShelfTypeId(String shelfTypeId) {
        this.shelfTypeId = shelfTypeId;
    }

    public String getShelfTypeName() {
        return shelfTypeName;
    }

    public void setShelfTypeName(String shelfTypeName) {
        this.shelfTypeName = shelfTypeName;
    }

    public Integer getShelfStorageNum() {
        return shelfStorageNum;
    }

    public void setShelfStorageNum(Integer shelfStorageNum) {
        this.shelfStorageNum = shelfStorageNum;
    }

    public Integer getShelfLength() {
        return shelfLength;
    }

    public void setShelfLength(Integer shelfLength) {
        this.shelfLength = shelfLength;
    }

    public Integer getShelfWidth() {
        return shelfWidth;
    }

    public void setShelfWidth(Integer shelfWidth) {
        this.shelfWidth = shelfWidth;
    }

    public Integer getShelfHeight() {
        return shelfHeight;
    }

    public void setShelfHeight(Integer shelfHeight) {
        this.shelfHeight = shelfHeight;
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
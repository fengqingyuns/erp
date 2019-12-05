package com.hanyun.scm.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanyun.scm.api.domain.request.BaseRequest;
import org.hibernate.validator.constraints.NotEmpty;

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
public class DisassemblyOrder extends BaseRequest {
    @JsonIgnore
    private Long id;
    @NotEmpty
    private String brandId;

    private String storeId;

    private String disassemblyOrderId;

    private String disassemblyOrderDocumentId;

    private Integer disassemblyOrderType;

    private String srcWarehouseId;

    private String srcWarehouseName;

    private String toWarehouseId;

    private String toWarehouseName;

    private Integer disassemblyOrderStatus;

    private String makeorderManId;

    private String makeorderManName;

    private String examineId;

    private String examineName;

    private Date examineTime;

    private String remark;

    private Integer validStatus;

    private Date createTime;

    private Date updateTime;

    private boolean auditStatus;

    private Boolean historyStatus;

    private String userId;

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

    public boolean getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(boolean auditStatus) {
        this.auditStatus = auditStatus;
    }

    private List<DisassemblyOrderDetail> motherList;

    private List<DisassemblyOrderDetail> sonList;

    private DisassemblyOrderDetail disassemblyOrderDetail;

    private List<DisassemblyOrderDetail> disassemblyOrderDetailList;

    public List<DisassemblyOrderDetail> getDisassemblyOrderDetailList() {
        return disassemblyOrderDetailList;
    }

    public void setDisassemblyOrderDetailList(List<DisassemblyOrderDetail> disassemblyOrderDetailList) {
        this.disassemblyOrderDetailList = disassemblyOrderDetailList;
    }

    public DisassemblyOrderDetail getDisassemblyOrderDetail() {
        return disassemblyOrderDetail;
    }

    public void setDisassemblyOrderDetail(DisassemblyOrderDetail disassemblyOrderDetail) {
        this.disassemblyOrderDetail = disassemblyOrderDetail;
    }

    public Integer getDisassemblyOrderType() {
        return disassemblyOrderType;
    }

    public void setDisassemblyOrderType(Integer disassemblyOrderType) {
        this.disassemblyOrderType = disassemblyOrderType;
    }

    public Integer getDisassemblyOrderStatus() {
        return disassemblyOrderStatus;
    }

    public void setDisassemblyOrderStatus(Integer disassemblyOrderStatus) {
        this.disassemblyOrderStatus = disassemblyOrderStatus;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public List<DisassemblyOrderDetail> getMotherList() {
        return motherList;
    }

    public void setMotherList(List<DisassemblyOrderDetail> motherList) {
        this.motherList = motherList;
    }

    public List<DisassemblyOrderDetail> getSonList() {
        return sonList;
    }

    public void setSonList(List<DisassemblyOrderDetail> sonList) {
        this.sonList = sonList;
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

    public String getDisassemblyOrderId() {
        return disassemblyOrderId;
    }

    public void setDisassemblyOrderId(String disassemblyOrderId) {
        this.disassemblyOrderId = disassemblyOrderId;
    }

    public String getDisassemblyOrderDocumentId() {
        return disassemblyOrderDocumentId;
    }

    public void setDisassemblyOrderDocumentId(String disassemblyOrderDocumentId) {
        this.disassemblyOrderDocumentId = disassemblyOrderDocumentId;
    }

    public String getSrcWarehouseId() {
        return srcWarehouseId;
    }

    public void setSrcWarehouseId(String srcWarehouseId) {
        this.srcWarehouseId = srcWarehouseId;
    }

    public String getSrcWarehouseName() {
        return srcWarehouseName;
    }

    public void setSrcWarehouseName(String srcWarehouseName) {
        this.srcWarehouseName = srcWarehouseName;
    }

    public String getToWarehouseId() {
        return toWarehouseId;
    }

    public void setToWarehouseId(String toWarehouseId) {
        this.toWarehouseId = toWarehouseId;
    }

    public String getToWarehouseName() {
        return toWarehouseName;
    }

    public void setToWarehouseName(String toWarehouseName) {
        this.toWarehouseName = toWarehouseName;
    }

    public String getMakeorderManId() {
        return makeorderManId;
    }

    public void setMakeorderManId(String makeorderManId) {
        this.makeorderManId = makeorderManId;
    }

    public String getMakeorderManName() {
        return makeorderManName;
    }

    public void setMakeorderManName(String makeorderManName) {
        this.makeorderManName = makeorderManName;
    }

    public String getExamineId() {
        return examineId;
    }

    public void setExamineId(String examineId) {
        this.examineId = examineId;
    }

    public String getExamineName() {
        return examineName;
    }

    public void setExamineName(String examineName) {
        this.examineName = examineName;
    }

    public Date getExamineTime() {
        return examineTime;
    }

    public void setExamineTime(Date examineTime) {
        this.examineTime = examineTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
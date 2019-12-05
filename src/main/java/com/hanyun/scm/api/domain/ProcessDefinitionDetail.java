package com.hanyun.scm.api.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
public class ProcessDefinitionDetail {

    private Long id;

    private String brandId;     //品牌id

    private String processDetailId; //流程节点id

    private String processId;   //流程id

    private String auditorId;   //审批人id(弃用)

    private String auditorName; //审批人名称(弃用)

    private Integer auditorType;    //审批类型(0、审批人  1、审批角色  2、审批机构)

    private Integer sortNo;     //节点位置

    private Integer editStatus; //是否可编辑单据

    private Date createTime;    //创建时间

    private Date updateTime;    //更新时间

    private List<ProcessDefinitionDetailAuditors> auditors;

    public List<ProcessDefinitionDetailAuditors> getAuditors() {
        return auditors;
    }

    public void setAuditors(List<ProcessDefinitionDetailAuditors> auditors) {
        this.auditors = auditors;
    }

    public Integer getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(Integer editStatus) {
        this.editStatus = editStatus;
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

    public String getProcessDetailId() {
        return processDetailId;
    }

    public void setProcessDetailId(String processDetailId) {
        this.processDetailId = processDetailId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
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

    public Integer getAuditorType() {
        return auditorType;
    }

    public void setAuditorType(Integer auditorType) {
        this.auditorType = auditorType;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
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
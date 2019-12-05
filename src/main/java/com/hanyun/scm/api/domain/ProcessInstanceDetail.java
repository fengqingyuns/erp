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
public class ProcessInstanceDetail {

    private Long id;

    private String brandId;     //品牌id

    private String processInstanceDetailId; //流程实例节点id

    private String processInstanceId;   //流程id

    private String activitiProcessInstanceId;   //activiti流程实例id

    private String processDetailId; //流程节点id

    private String auditorId;   //审批人id

    private String auditorName; //审批人姓名

    private Integer auditorType;    //审批人类型

    private Integer sortNo;     //节点位置

    private Date auditTime;     //审批时间

    private String remark;      //备注

    private Integer auditStatus;    //审批状态

    private Date createTime;        //创建时间

    private Date updateTime;        //更新时间

    public Integer getAuditorType() {
        return auditorType;
    }

    public void setAuditorType(Integer auditorType) {
        this.auditorType = auditorType;
    }

    private List<ProcessInstanceDetailAuditors> auditors;

    public List<ProcessInstanceDetailAuditors> getAuditors() {
        return auditors;
    }

    public void setAuditors(List<ProcessInstanceDetailAuditors> auditors) {
        this.auditors = auditors;
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

    public String getProcessInstanceDetailId() {
        return processInstanceDetailId;
    }

    public void setProcessInstanceDetailId(String processInstanceDetailId) {
        this.processInstanceDetailId = processInstanceDetailId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getActivitiProcessInstanceId() {
        return activitiProcessInstanceId;
    }

    public void setActivitiProcessInstanceId(String activitiProcessInstanceId) {
        this.activitiProcessInstanceId = activitiProcessInstanceId;
    }

    public String getProcessDetailId() {
        return processDetailId;
    }

    public void setProcessDetailId(String processDetailId) {
        this.processDetailId = processDetailId;
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

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
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
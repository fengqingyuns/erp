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
public class ProcessDefinition {

    private Long id;

    private String brandId;

    private String processId;   //流程id

    private String processName; //流程名

    private String activitiProcessId;   //activiti流程id

    private String activitiSuperProcessId;  //activiti超级审批人流程id

    private Integer processType;    //流程类型

    private Integer processStatus;  //流程状态

    private Long cashTopLimit;  //触发超级审批金额(大于)

    private String superAuditorId;  //超级审批人id

    private String superAuditorName;    //超级审批人名称

    private Integer superStatus;    //超级审批状态(0、停用  1、启用)

    private Date createTime;

    private Date updateTime;

    public Integer getSuperStatus() {
        return superStatus;
    }

    public void setSuperStatus(Integer superStatus) {
        this.superStatus = superStatus;
    }

    public String getActivitiSuperProcessId() {
        return activitiSuperProcessId;
    }

    public void setActivitiSuperProcessId(String activitiSuperProcessId) {
        this.activitiSuperProcessId = activitiSuperProcessId;
    }

    private List<ProcessDefinitionDetail> processDefinitionDetailList;

    public String getSuperAuditorId() {
        return superAuditorId;
    }

    public void setSuperAuditorId(String superAuditorId) {
        this.superAuditorId = superAuditorId;
    }

    public String getSuperAuditorName() {
        return superAuditorName;
    }

    public void setSuperAuditorName(String superAuditorName) {
        this.superAuditorName = superAuditorName;
    }

    public Long getCashTopLimit() {
        return cashTopLimit;
    }

    public void setCashTopLimit(Long cashTopLimit) {
        this.cashTopLimit = cashTopLimit;
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

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getActivitiProcessId() {
        return activitiProcessId;
    }

    public void setActivitiProcessId(String activitiProcessId) {
        this.activitiProcessId = activitiProcessId;
    }

    public Integer getProcessType() {
        return processType;
    }

    public void setProcessType(Integer processType) {
        this.processType = processType;
    }

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
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

    public List<ProcessDefinitionDetail> getProcessDefinitionDetailList() {
        return processDefinitionDetailList;
    }

    public void setProcessDefinitionDetailList(List<ProcessDefinitionDetail> processDefinitionDetailList) {
        this.processDefinitionDetailList = processDefinitionDetailList;
    }
}
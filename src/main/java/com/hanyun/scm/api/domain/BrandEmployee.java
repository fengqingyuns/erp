package com.hanyun.scm.api.domain;

import java.util.Date;

public class BrandEmployee {
    private Long id;
    private String brandId;
    private String storeId;
    private String employeeId;
    private String name;
    private String phone;
    private String passWord;
    private Integer employeeStatus;
    private String roleName;
    private String roleId;
    private String remark;
    private Date createTime;
    private Date updateTime;
    private String employeeEmail;
    private String orgPartyId;
    private String orgPartyName;

    private Integer firstChangePassword;//0 自己修改 1 别人修改

    public Integer getFirstChangePassword() {
        return firstChangePassword;
    }

    public void setFirstChangePassword(Integer firstChangePassword) {
        this.firstChangePassword = firstChangePassword;
    }

    public String getOrgPartyId() {
        return orgPartyId;
    }

    public void setOrgPartyId(String orgPartyId) {
        this.orgPartyId = orgPartyId;
    }

    public String getOrgPartyName() {
        return orgPartyName;
    }

    public void setOrgPartyName(String orgPartyName) {
        this.orgPartyName = orgPartyName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Integer getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(Integer employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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

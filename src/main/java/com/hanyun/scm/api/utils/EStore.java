package com.hanyun.scm.api.utils;

import javax.validation.constraints.Pattern;
import java.util.Date;
public class EStore {
    /*
        * E店宝ID
        */
    private Long estoreid;

    /*
     *  服务器
     */
    //@NotNull
    @Pattern(regexp = "[^''\\[\\]\\<\\>?\\\\!]+")
    private String server;

    /*
     *  主账号
     */
    //@NotNull
    @Pattern(regexp = "[^''\\[\\]\\<\\>?\\\\!]+")
    private String dbhost;

    /*
     *  AppKey
     */
    //@NotNull
    @Pattern(regexp = "[^''\\[\\]\\<\\>?\\\\!]+")
    private String appkey;

    /*
     * AppScret
     */
    //@NotNull
    @Pattern(regexp = "[^''\\[\\]\\<\\>?\\\\!]+")
    private String appscret;

    /*
     *  token
     */
    //@NotNull
    @Pattern(regexp = "[^''\\[\\]\\<\\>?\\\\!]+")
    private String token;

    /*
    *  申请ERP地址
    */
    @Pattern(regexp = "[^''\\[\\]\\<\\>?\\\\!]+")
    private String address;

    /*
    * ERP提供商
    */
    @Pattern(regexp = "[^''\\[\\]\\<\\>?\\\\!]+")
    private String provider;

    /*
     * ERP图片
     */
    @Pattern(regexp = "[^''\\[\\]\\<\\>?\\\\!]+")
    private String image;

    /*
     * 是否开启
     */
    private String isopen;

    /*
     * 创建时间
     */
    private Date createtime;

    /*
     *  修改时间
     */
    private Date updatetime;

    /*
     *  删除标记
     */
    private String delflag;

    private  String  shopId;

    private String storageId;


    public Long getEstoreid() {
        return estoreid;
    }

    public void setEstoreid(Long estoreid) {
        this.estoreid = estoreid;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getDbhost() {
        return dbhost;
    }

    public void setDbhost(String dbhost) {
        this.dbhost = dbhost;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getAppscret() {
        return appscret;
    }

    public void setAppscret(String appscret) {
        this.appscret = appscret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsopen() {
        return isopen;
    }

    public void setIsopen(String isopen) {
        this.isopen = isopen;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }
}

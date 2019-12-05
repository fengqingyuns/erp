package com.hanyun.scm.api.domain.request.Replenishment;

import com.hanyun.scm.api.domain.ReplenishmentApply;

import java.util.List;

public class ReplenishmentApplyRequest extends ReplenishmentApply {
    private String queryBeginTime;
    private String queryEndTime;
    private String toStoreWarehouse;
    private String[] storeIds;          //补货门店ids集合
    private String[] replenishmentApplyIds; //过滤申请单id集合
    private Integer sort;    //排序规则 0、由大到小 1、有小到大
    private String userId;      //用户id
    private List<String> ids;
    private Integer appQueryApply;      //app端查询的状态需要
    private String receiptQueryByApp;   //已发货且没全部收货
    public Integer getAppQueryApply() {
        return appQueryApply;
    }

    public void setAppQueryApply(Integer appQueryApply) {
        this.appQueryApply = appQueryApply;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQueryBeginTime() {
        return queryBeginTime;
    }

    public void setQueryBeginTime(String queryBeginTime) {
        this.queryBeginTime = queryBeginTime;
    }

    public String getQueryEndTime() {
        return queryEndTime;
    }

    public void setQueryEndTime(String queryEndTime) {
        this.queryEndTime = queryEndTime;
    }

    public String getToStoreWarehouse() {
        return toStoreWarehouse;
    }

    public void setToStoreWarehouse(String toStoreWarehouse) {
        this.toStoreWarehouse = toStoreWarehouse;
    }

    public String[] getReplenishmentApplyIds() {
        return replenishmentApplyIds;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public void setReplenishmentApplyIds(String[] replenishmentApplyIds) {
        this.replenishmentApplyIds = replenishmentApplyIds;
    }

    public String[] getStoreIds() {
        return storeIds;
    }

    public void setStoreIds(String[] storeIds) {
        this.storeIds = storeIds;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getReceiptQueryByApp() {
        return receiptQueryByApp;
    }

    public void setReceiptQueryByApp(String receiptQueryByApp) {
        this.receiptQueryByApp = receiptQueryByApp;
    }
}

package com.hanyun.scm.api.domain.request.distribution;

import com.hanyun.scm.api.domain.DistributionOrder;

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
public class DistributionOrderQueryRequest extends DistributionOrder {

    private String queryBeginTime;

    private String queryEndTime;

    private String storeIds;

    private List<String> storeIdList;           //匹配配送单的门店id集合

    private String distributionIds;

    private List<String> distributionIdList;    //过滤配送单的id集合

    private List<String> ids;


    public String getStoreIds() {
        return storeIds;
    }

    public void setStoreIds(String storeIds) {
        this.storeIds = storeIds;
    }

    public String getDistributionIds() {
        return distributionIds;
    }

    public void setDistributionIds(String distributionIds) {
        this.distributionIds = distributionIds;
    }

    public List<String> getDistributionIdList() {
        return distributionIdList;
    }

    public void setDistributionIdList(List<String> distributionIdList) {
        this.distributionIdList = distributionIdList;
    }

    public List<String> getStoreIdList() {
        return storeIdList;
    }

    public void setStoreIdList(List<String> storeIdList) {
        this.storeIdList = storeIdList;
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

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
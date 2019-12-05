package com.hanyun.scm.api.domain.request.stock;

import com.hanyun.scm.api.domain.StockCheckOrder;

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
public class StockCheckOrderQueryRequest extends StockCheckOrder {

    private String inventoryNumberTaskPersonl;            //任务单号 盘点员 盘点单号

    private String singleBeginTime;                        //单据开始时间

    private String singleEndTime;                        //单据结束时间

    private String auditBeginTime;                        //审核开始时间

    private String auditEndTime;                        //审核结束时间

    private String inventoryMechanism;                    //盘点机构

    private String userId;

    private List<String> ids;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInventoryNumberTaskPersonl() {
        return inventoryNumberTaskPersonl;
    }

    public void setInventoryNumberTaskPersonl(String inventoryNumberTaskPersonl) {
        this.inventoryNumberTaskPersonl = inventoryNumberTaskPersonl;
    }

    public String getSingleBeginTime() {
        return singleBeginTime;
    }

    public void setSingleBeginTime(String singleBeginTime) {
        this.singleBeginTime = singleBeginTime;
    }

    public String getSingleEndTime() {
        return singleEndTime;
    }

    public void setSingleEndTime(String singleEndTime) {
        this.singleEndTime = singleEndTime;
    }

    public String getAuditBeginTime() {
        return auditBeginTime;
    }

    public void setAuditBeginTime(String auditBeginTime) {
        this.auditBeginTime = auditBeginTime;
    }

    public String getAuditEndTime() {
        return auditEndTime;
    }

    public void setAuditEndTime(String auditEndTime) {
        this.auditEndTime = auditEndTime;
    }

    public String getInventoryMechanism() {
        return inventoryMechanism;
    }

    public void setInventoryMechanism(String inventoryMechanism) {
        this.inventoryMechanism = inventoryMechanism;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
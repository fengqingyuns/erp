package com.hanyun.scm.api.domain.request.stock;

import com.hanyun.ground.util.date.DateFormatUtil;

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
public class StockCheckTaskCreateRequest extends StockCheckTaskBaseRequest {
    private String taskStartTime;

    private String taskEndTime;


    public String getTaskStartTime() {
        if (taskStartTime != null) {
            return DateFormatUtil.formatDateTime(DateFormatUtil.parseDateISO(taskStartTime));
        } else {
            return null;
        }
    }

    public void setTaskStartTime(String taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public String getTaskEndTime() {
        if (taskEndTime != null) {
            return DateFormatUtil.formatDateTime(DateFormatUtil.parseDateISO(taskEndTime));
        } else {
            return null;
        }

    }

    public void setTaskEndTime(String taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

}
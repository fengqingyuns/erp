package com.hanyun.scm.api.domain.dto;

import com.hanyun.scm.api.domain.response.instance.ExamineStatus;

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
 * PrecessStatusDTO
 * Date: 2017/10/26 0026
 * Time: 21:28
 *
 * @author tangqiming@hanyun.com
 */
public class ProcessStatusDTO {

    private Map<String, ExamineStatus> statusMap;

    public ProcessStatusDTO(){}

    public ProcessStatusDTO(Map<String, ExamineStatus> statusMap){
        this.statusMap = statusMap;
    }

    public Map<String, ExamineStatus> getStatusMap() {
        return statusMap;
    }

    public void setStatusMap(Map<String, ExamineStatus> statusMap) {
        this.statusMap = statusMap;
    }
}

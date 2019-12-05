package com.hanyun.scm.api.domain.request.InspectionPickingIn;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanyun.scm.api.domain.InspectionPickingIn;

import javax.validation.constraints.NotNull;

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
public class InspectionPickingInDetailRequest extends InspectionPickingIn {

    @NotNull
    private String brandId;
    @NotNull
    private String inspectionId;

    @Override
    public String getInspectionId() {
        return inspectionId;
    }

    @Override
    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }

    @Override
    public String getBrandId() {
        return brandId;
    }

    @Override
    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

}
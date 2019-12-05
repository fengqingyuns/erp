package com.hanyun.scm.api.domain.request.supplier;

import com.hanyun.scm.api.domain.Supplier;
import org.hibernate.validator.constraints.NotEmpty;

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
public class SupplierCreateRequest extends Supplier {

    @NotEmpty
    private String supplierName;
    @NotEmpty
    private String brandId;             //品牌id
    @NotNull
    private Integer supplierStatus;     //供应商状态(0:正常  1:禁用)
    @NotNull
    private Integer supplierCategory;   //供应商类别(1:厂家  2:总代理 3: 二代理  4:三代理  5:批发商  6:其他 )
    @NotNull
    private Integer typeId;             //合作类型(1:购销  2:联营  3:代销)
    @NotNull
    private Integer settleType;         //结算方式(1:临时指定  2:指定账期(按自然月结算,指定N天为账期)  3:N天一结(每N天结算一次))


    @Override
    public String getSupplierName() {
        return supplierName;
    }

    @Override
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Override
    public String getBrandId() {
        return brandId;
    }

    @Override
    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    @Override
    public Integer getSupplierStatus() {
        return supplierStatus;
    }

    @Override
    public void setSupplierStatus(Integer supplierStatus) {
        this.supplierStatus = supplierStatus;
    }

    @Override
    public Integer getSupplierCategory() {
        return supplierCategory;
    }

    @Override
    public void setSupplierCategory(Integer supplierCategory) {
        this.supplierCategory = supplierCategory;
    }

    @Override
    public Integer getTypeId() {
        return typeId;
    }

    @Override
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public Integer getSettleType() {
        return settleType;
    }

    @Override
    public void setSettleType(Integer settleType) {
        this.settleType = settleType;
    }
}
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
public class SupplierModifyRequest extends Supplier {
    @NotEmpty
    private String supplierId;          //供应商id
    @NotEmpty
    private String brandId;             //品牌id

    @NotNull
    private Integer typeId;              //分类id

    @NotEmpty
    private String supplierName;        //供应商名称

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
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
    public String getSupplierName() {
        return supplierName;
    }

    @Override
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
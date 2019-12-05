package com.hanyun.scm.api.domain.request.supplier;

import com.hanyun.scm.api.domain.Supplier;
import org.hibernate.validator.constraints.NotEmpty;

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
public class SupplierModifyStatusRequest extends Supplier {
    
    @NotEmpty
    private String supplierId;          //供应商id


    private Integer supplierStatus;

    @Override
    public String getSupplierId() {
        return supplierId;
    }

    @Override
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public Integer getSupplierStatus() {
        return supplierStatus;
    }

    @Override
    public void setSupplierStatus(Integer supplierStatus) {
        this.supplierStatus = supplierStatus;
    }
}
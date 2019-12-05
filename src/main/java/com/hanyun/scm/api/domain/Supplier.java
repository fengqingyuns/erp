package com.hanyun.scm.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanyun.scm.api.domain.request.BaseRequest;
import com.hanyun.scm.api.domain.response.SupplierGoodsResposne;

import java.util.Date;
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
public class Supplier extends BaseRequest {

    @JsonIgnore
    private Long id;

    private String supplierId;          //供应商id

    private String supplierDocumentId; //显示用

    private String brandId;             //品牌id

    private String storeId;             //门店id

    private Integer typeId;              //分类id

    private Integer categoryId;         //odoo标签id

    private String typeName;            //分类名称

    private String supplierName;        //供应商名称

    private Integer texRate;            //税率

    private String supplierAllName;     //供应商全称

    private String contacts;            //联系人

    private String url;                 //网址

    private String phone;               //电话

    private String zipCode;             //邮编

    private String email;               //邮箱

    private String contractNo;          //合同编号

    private String qq;                  //qq号或微信号

    private String address;             //地址

    private String companyName;         //公司全称(发票抬头)

    private String bankId;              //银行编码

    private String bank;                //开户行

    private String bankName;            //开户名称

    private String invoiceTitle;        //发票抬头

    private String bankAccount;         //银行账号

    private String taxNo;               //税号

    private String registryPhone;       //注册手机

    private String registryAddress;     //注册地址

    private String remark;              //备注

    private Integer odooSupplierId;     //odoo供应商编号

    private Integer supplierStatus;     //供应商状态

    private String createUserId;        //操作人id

    private String updateUserId;        //更新人id

    private Date createTime;            //创建时间

    private Date updateTime;            //更新时间

    private Integer validStatus;        //删除状态

    private String abbreviationName;    //简称

    private String tel;                    //固定电话

    private Integer settleType;            //结算方式

    private Integer supplierCategory;   //供应商类别

    private Date scheduledDate;            //指定日

    private Integer settleInterval;        //结算间隔天数

    private String fax;                    //传真

    private String businessLicense;        //营业执照

    private Date licenseExpireDate;        //营业执照到期日

    private String provinceId;            //省

    private String provinceName;        //省名

    private String cityId;                //市

    private String cityName;            //市名

    private String districtId;            //区、县

    private String districtName;        //区名

    private Long initialTotalPrice;     //期初应付款

    private Long initialPaymentPrice;   //期初预付款

    private List<SupplierGoodsResposne> resultSupplierGoodsList;    //查询返回的goods_list

    private List<SupplierGoods> supplierGoodsList;                  //新增或修改传入的goods_list    不能为空

    private List<String> deleteGoodsIds;                          //修改时传入的删除商品list

    public List<String> getDeleteGoodsIds() {
        return deleteGoodsIds;
    }

    public void setDeleteGoodsIds(List<String> deleteGoodsIds) {
        this.deleteGoodsIds = deleteGoodsIds;
    }

    public List<SupplierGoodsResposne> getResultSupplierGoodsList() {
        return resultSupplierGoodsList;
    }

    public void setResultSupplierGoodsList(List<SupplierGoodsResposne> resultSupplierGoodsList) {
        this.resultSupplierGoodsList = resultSupplierGoodsList;
    }

    public List<SupplierGoods> getSupplierGoodsList() {
        return supplierGoodsList;
    }

    public void setSupplierGoodsList(List<SupplierGoods> supplierGoodsList) {
        this.supplierGoodsList = supplierGoodsList;
    }

    public String getSupplierDocumentId() {
        return supplierDocumentId;
    }

    public void setSupplierDocumentId(String supplierDocumentId) {
        this.supplierDocumentId = supplierDocumentId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getSupplierCategory() {
        return supplierCategory;
    }

    public void setSupplierCategory(Integer supplierCategory) {
        this.supplierCategory = supplierCategory;
    }

    public Integer getSupplierStatus() {
        return supplierStatus;
    }

    public void setSupplierStatus(Integer supplierStatus) {
        this.supplierStatus = supplierStatus;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getTexRate() {
        return texRate;
    }

    public void setTexRate(Integer texRate) {
        this.texRate = texRate;
    }

    public String getSupplierAllName() {
        return supplierAllName;
    }

    public void setSupplierAllName(String supplierAllName) {
        this.supplierAllName = supplierAllName;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public String getRegistryPhone() {
        return registryPhone;
    }

    public void setRegistryPhone(String registryPhone) {
        this.registryPhone = registryPhone;
    }

    public String getRegistryAddress() {
        return registryAddress;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOdooSupplierId() {
        return odooSupplierId;
    }

    public void setOdooSupplierId(Integer odooSupplierId) {
        this.odooSupplierId = odooSupplierId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public String getAbbreviationName() {
        return abbreviationName;
    }

    public void setAbbreviationName(String abbreviationName) {
        this.abbreviationName = abbreviationName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getSettleType() {
        return settleType;
    }

    public void setSettleType(Integer settleType) {
        this.settleType = settleType;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Integer getSettleInterval() {
        return settleInterval;
    }

    public void setSettleInterval(Integer settleInterval) {
        this.settleInterval = settleInterval;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public Date getLicenseExpireDate() {
        return licenseExpireDate;
    }

    public void setLicenseExpireDate(Date licenseExpireDate) {
        this.licenseExpireDate = licenseExpireDate;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Long getInitialTotalPrice() {
        return initialTotalPrice;
    }

    public void setInitialTotalPrice(Long initialTotalPrice) {
        this.initialTotalPrice = initialTotalPrice;
    }

    public Long getInitialPaymentPrice() {
        return initialPaymentPrice;
    }

    public void setInitialPaymentPrice(Long initialPaymentPrice) {
        this.initialPaymentPrice = initialPaymentPrice;
    }
}
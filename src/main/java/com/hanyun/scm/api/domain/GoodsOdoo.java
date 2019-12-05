package com.hanyun.scm.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.domain.dto.ProductSupplierDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
 */
public class GoodsOdoo {

    @JsonIgnore
    private Long id;

    private String goodsId;         //商品id(对应B端品牌商品id)

    private String goodsPic;        //商品图片

    private String brandId;         //品牌id

    private String classifyId;      //分类id
    
    private String classifyName;      //分类名称

    private Integer odooGoodsId;     //odoo商品id

    private String goodsName;       //商品名称

    private String goodsCode;      //商品编号,即SKU

    private String goodsBarCode;    //商品条码

    private String unitId;          //单位id

    private String unitName;        //单位名称

    private Integer goodsStatus;    //商品状态

    private String goodsIntroduce;  //商品介绍

    private Long goodsPrice;        //商品价格

    private Long purchasePrice;     //进价

    private Integer useType;        //使用类型(0、采购&销售  1、销售  2、采购)

    private Integer goodsType;      //商品类型(0、可消耗  1、服务  2、可库存)

    private String goodsTypeName;   //类型名称

    private String skipIds;             //忽略的商品id集合json

    private List<String> skipIdList;    //忽略的商品id集合

    private Date createTime;

    private Date updateTime;
    
    private Integer validStatus;

    private String features;    //规格

    private String goodsBrandName;  //商品品牌名

    private String supplierId; //供应商id

    private String supplierName; //供应商名称

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public Long getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Long purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getGoodsBrandName() {
        return goodsBrandName;
    }

    public void setGoodsBrandName(String goodsBrandName) {
        this.goodsBrandName = goodsBrandName;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getSkipIds() {
        return skipIds;
    }

    public void setSkipIds(String skipIds) {
        this.skipIds = skipIds;
    }

    public List<String> getSkipIdList() {
        return skipIdList;
    }

    public void setSkipIdList(List<String> skipIdList) {
        this.skipIdList = skipIdList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }
    
    public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

    public Integer getOdooGoodsId() {
        return odooGoodsId;
    }

    public void setOdooGoodsId(Integer odooGoodsId) {
        this.odooGoodsId = odooGoodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public void setGoodsBarCode(String goodsBarCode) {
        this.goodsBarCode = goodsBarCode;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(Integer goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public String getGoodsIntroduce() {
        return goodsIntroduce;
    }

    public void setGoodsIntroduce(String goodsIntroduce) {
        this.goodsIntroduce = goodsIntroduce;
    }

    public Long getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Long goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getUseType() {
        return useType;
    }

    public void setUseType(Integer useType) {
        this.useType = useType;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
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

    public GoodsOdoo() {
        super();
    }

    public GoodsOdoo(ProductSupplierDTO productSupplierDTO) {
        this.goodsId = productSupplierDTO.getProductId();
        this.goodsPic = productSupplierDTO.getPic();
        this.goodsName = productSupplierDTO.getProductName();
        this.goodsBarCode = productSupplierDTO.getBarcode();
        this.goodsCode = productSupplierDTO.getSkuCode();
        this.classifyId = productSupplierDTO.getCategoryId();
        this.classifyName = productSupplierDTO.getCategoryName();
        this.features = productSupplierDTO.getFeatures();
        this.unitName = productSupplierDTO.getQuantityUomId();
        this.goodsPrice = productSupplierDTO.getGroupPrice();
        this.purchasePrice = productSupplierDTO.getCostPrice();
        this.goodsTypeName = productSupplierDTO.getProductTypeName();
        this.goodsBrandName = productSupplierDTO.getProductBrandName();
        this.useType = Consts.GOODS_USE_TYPE_PURCHASE_SELL;
        this.supplierId = productSupplierDTO.getSupplierId();
        this.supplierName = productSupplierDTO.getSupplierName();
    }

    public GoodsOdoo(Map<String, Object> goods) {
        if (goods == null) {
            return;
        }
        this.goodsId = goods.get("productId")==null?"":goods.get("productId").toString();
        this.goodsName = goods.get("productName")==null?"":goods.get("productName").toString();
        this.goodsBarCode = goods.get("barcode")==null?"":goods.get("barcode").toString();
        this.goodsCode = goods.get("skuCode")==null?"":goods.get("skuCode").toString();
        this.classifyId = goods.get("categoryId")==null?"":goods.get("categoryId").toString();
        this.classifyName = goods.get("categoryName")==null?"":goods.get("categoryName").toString();
        Double costPrice = goods.get("costPrice")==null?0L:Double.parseDouble(goods.get("costPrice").toString());
        this.goodsPrice = new BigDecimal(costPrice.toString()).multiply(new BigDecimal("100")).longValue();
        this.supplierId = goods.get("supplierId")==null?"":goods.get("supplierId").toString();
        this.supplierName = goods.get("supplierName")==null?"":goods.get("supplierName").toString();

        this.unitName = goods.get("quantityUomId")==null?"":goods.get("quantityUomId").toString();
        this.features = goods.get("features")==null?"":goods.get("features").toString();
        this.goodsBrandName = goods.get("productBrandName")==null?"":goods.get("productBrandName").toString();
        this.goodsTypeName = goods.get("productTypeName")==null?"":goods.get("productTypeName").toString();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }
}
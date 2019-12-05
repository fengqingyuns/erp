package com.hanyun.scm.api.domain;

import com.hanyun.scm.api.domain.dto.QueryBillGoodsDTO;
import com.hanyun.scm.api.domain.request.BaseRequest;

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
public class StockCheckTaskDetail extends BaseRequest {

    private Long id;

    private String brandId;

    private String storeId;

    private String warehouseId;

    private String stockCheckId;

    private String stockCheckDetailId;

    private String goodsId;

    private String goodsName;

    private String goodsCode;

    private String classifyId;

    private String classifyName;

    private String goodsBarCode;

    private String unitId;

    private String unitName;

    private Integer goodsStatus;

    private Integer odooGoodsId;

    private String goodsIntroduce;

    private Long unitPrice;

    private Long goodsPrice;

    private Integer useType;

    private Integer goodsType;

    private Integer validStatus;

    private Long stockNum;

    private Long curStock;

    private Long checkStock;

    private Long checkDiffStock;

    private Long checkDiffPrice;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private String goodsTypeName;

    private String features;
    
    private String goodsBrandName;

    private String goodsPic;

    private String endTime;

    private String startTime;

    private List<String> goodsIds;

    private List<String> skipIds;

    private String condition;

    public StockCheckTaskDetail(){}

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getStockCheckId() {
        return stockCheckId;
    }

    public void setStockCheckId(String stockCheckId) {
        this.stockCheckId = stockCheckId;
    }

    public String getStockCheckDetailId() {
        return stockCheckDetailId;
    }

    public void setStockCheckDetailId(String stockCheckDetailId) {
        this.stockCheckDetailId = stockCheckDetailId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
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

    public Integer getOdooGoodsId() {
        return odooGoodsId;
    }

    public void setOdooGoodsId(Integer odooGoodsId) {
        this.odooGoodsId = odooGoodsId;
    }

    public String getGoodsIntroduce() {
        return goodsIntroduce;
    }

    public void setGoodsIntroduce(String goodsIntroduce) {
        this.goodsIntroduce = goodsIntroduce;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(Integer goodsStatus) {
        this.goodsStatus = goodsStatus;
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

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public Long getStockNum() {
        return stockNum;
    }

    public void setStockNum(Long stockNum) {
        this.stockNum = stockNum;
    }

    public Long getCurStock() {
        return curStock;
    }

    public void setCurStock(Long curStock) {
        this.curStock = curStock;
    }

    public Long getCheckStock() {
        return checkStock;
    }

    public void setCheckStock(Long checkStock) {
        this.checkStock = checkStock;
    }

    public Long getCheckDiffStock() {
        return checkDiffStock;
    }

    public void setCheckDiffStock(Long checkDiffStock) {
        this.checkDiffStock = checkDiffStock;
    }

    public Long getCheckDiffPrice() {
        return checkDiffPrice;
    }

    public void setCheckDiffPrice(Long checkDiffPrice) {
        this.checkDiffPrice = checkDiffPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public List<String> getGoodsIds() {
        return goodsIds;
    }

    public void setGoodsIds(List<String> goodsIds) {
        this.goodsIds = goodsIds;
    }

    public List<String> getSkipIds() {
        return skipIds;
    }

    public void setSkipIds(List<String> skipIds) {
        this.skipIds = skipIds;
    }

    public Long getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Long goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
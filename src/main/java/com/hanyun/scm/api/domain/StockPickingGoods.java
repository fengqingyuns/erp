package com.hanyun.scm.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanyun.scm.api.domain.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

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
@ApiModel(value = "库存移动单(包括采购入库、采购退货、其他出入库、转仓单)商品信息")
public class StockPickingGoods extends BaseRequest {

    @JsonIgnore
    private Long id;

    @ApiModelProperty("库存移动详单id")
    private String pickingGoodsId;      //库存移动商品id

    @ApiModelProperty(value = "库存移动单id")
    private String stockPickingId;      //库存移动单id

    @ApiModelProperty(value = "商品id")
    @NotEmpty
    private String goodsId;             //商品id

    @ApiModelProperty(value = "商品名称")
    private String goodsName;           //商品名称

    private String goodsPic;

    @ApiModelProperty(value = "分类id")
    private String classifyId;          //商品分类id

    @ApiModelProperty(value = "分类名称")
    private String classifyName;        //商品分类名称

    private Integer goodsType;             //商品类型 0、可消耗  1、服务  2、可库存

    private String goodsTypeName;

    @ApiModelProperty(value = "单位id")
    private String unitId;              //单位id

    @ApiModelProperty(value = "单位名称")
    private String unitName;            //单位名称

    @ApiModelProperty(value = "采购数量")
    private Long purchaseAmount;        //采购数量

    @ApiModelProperty(value = "库存移动数量")
    private Long pickingAmount;         //库存移动移动数量

    @ApiModelProperty(value = "采购价格")
    private Long purchasePrice;         //采购价格

    @ApiModelProperty(value = "移库价格")
    private Long pickingPrice;          //移库价格

    private String pickingPriceShow;

    @ApiModelProperty(value = "零售价格")
    private Long unitPrice;          //零售价格

    @ApiModelProperty(value = "总价")
    private Long totalPrice;            //总价

    private Long stock;                 //当前库存

    private Date createTime;

    private Date updateTime;

    private Integer validStatus;            //冻结   0可用 1不可用

    @ApiModelProperty(value = "条码")
    private String goodsBarCode;

    private String inStorage = "0";

    private String purchaseTotalPrice = "0";

    private Long totalNum;

    private Long totalMoney;

    private Long countMoney;

    private Long avgMoney;

    private List goodsIdList;

    private String beginTime;

    private String endTime;

    private Integer stockPickingType;

    @ApiModelProperty(value = "入库原因")
    private Integer incomeReason;           //入库原因

    @ApiModelProperty(value = "出库原因")
    private Integer outReason;              //出库原因

    @ApiModelProperty(value = "品牌id")
    private String brandId;

    @ApiModelProperty(value = "门店id")
    private String storeId;

    private String total;

    private Integer stockNum;//库存量

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "赠送数量")
    private Long presentAmount;  //赠送数量

    @JsonIgnore
    private Long totalPresentPrice;

    @ApiModelProperty(value = "sku码")
    private String goodsCode;

    @ApiModelProperty(value = "规格")
    private String features;

    @ApiModelProperty(value = "商品品牌名")
    private String goodsBrandName;  //商品品牌名

    private Long quantNum;

    private Long stockInAmount;

    private Long sourcePurchaseStockInAmount;   //来源采购的入库数量

    private Long purchaseReturnAmount;          //退送数量

    private Long finishPurchaseReturnAmount;    //审核完成的退送数量

    private String warehouseId;

    private String warehouseName;

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public Long getFinishPurchaseReturnAmount() {
        return finishPurchaseReturnAmount;
    }

    public void setFinishPurchaseReturnAmount(Long finishPurchaseReturnAmount) {
        this.finishPurchaseReturnAmount = finishPurchaseReturnAmount;
    }

    public Long getPurchaseReturnAmount() {
        return purchaseReturnAmount;
    }

    public void setPurchaseReturnAmount(Long purchaseReturnAmount) {
        this.purchaseReturnAmount = purchaseReturnAmount;
    }

    public Long getTotalPresentPrice() {
        return totalPresentPrice;
    }

    public void setTotalPresentPrice(Long totalPresentPrice) {
        this.totalPresentPrice = totalPresentPrice;
    }

    public String getPickingPriceShow() {
        return pickingPriceShow;
    }

    public void setPickingPriceShow(String pickingPriceShow) {
        this.pickingPriceShow = pickingPriceShow;
    }

    public String getGoodsBrandName() {
        return goodsBrandName;
    }

    public void setGoodsBrandName(String goodsBrandName) {
        this.goodsBrandName = goodsBrandName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getPresentAmount() {
        return presentAmount;
    }

    public void setPresentAmount(Long presentAmount) {
        this.presentAmount = presentAmount;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPickingGoodsId() {
        return pickingGoodsId;
    }

    public void setPickingGoodsId(String pickingGoodsId) {
        this.pickingGoodsId = pickingGoodsId;
    }

    public String getStockPickingId() {
        return stockPickingId;
    }

    public void setStockPickingId(String stockPickingId) {
        this.stockPickingId = stockPickingId;
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

    public Long getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(Long purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public Long getPickingAmount() {
        return pickingAmount;
    }

    public void setPickingAmount(Long pickingAmount) {
        this.pickingAmount = pickingAmount;
    }

    public Long getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Long purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Long getPickingPrice() {
        return pickingPrice;
    }

    public void setPickingPrice(Long pickingPrice) {
        this.pickingPrice = pickingPrice;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
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

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public void setGoodsBarCode(String goodsBarCode) {
        this.goodsBarCode = goodsBarCode;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public String getInStorage() {
        return inStorage;
    }

    public void setInStorage(String inStorage) {
        this.inStorage = inStorage;
    }

    public String getPurchaseTotalPrice() {
        return purchaseTotalPrice;
    }

    public void setPurchaseTotalPrice(String purchaseTotalPrice) {
        this.purchaseTotalPrice = purchaseTotalPrice;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public Long getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Long totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Long getCountMoney() {
        return countMoney;
    }

    public void setCountMoney(Long countMoney) {
        this.countMoney = countMoney;
    }

    public Long getAvgMoney() {
        return avgMoney;
    }

    public void setAvgMoney(Long avgMoney) {
        this.avgMoney = avgMoney;
    }

    public List getGoodsIdList() {
        return goodsIdList;
    }

    public void setGoodsIdList(List goodsIdList) {
        this.goodsIdList = goodsIdList;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getStockPickingType() {
        return stockPickingType;
    }

    public void setStockPickingType(Integer stockPickingType) {
        this.stockPickingType = stockPickingType;
    }

    public Integer getIncomeReason() {
        return incomeReason;
    }

    public void setIncomeReason(Integer incomeReason) {
        this.incomeReason = incomeReason;
    }

    public Integer getOutReason() {
        return outReason;
    }

    public void setOutReason(Integer outReason) {
        this.outReason = outReason;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getQuantNum() {
        return quantNum;
    }

    public void setQuantNum(Long quantNum) {
        this.quantNum = quantNum;
    }

    public Long getStockInAmount() {
        return stockInAmount;
    }

    public void setStockInAmount(Long stockInAmount) {
        this.stockInAmount = stockInAmount;
    }

    public Long getSourcePurchaseStockInAmount() {
        return sourcePurchaseStockInAmount;
    }

    public void setSourcePurchaseStockInAmount(Long sourcePurchaseStockInAmount) {
        this.sourcePurchaseStockInAmount = sourcePurchaseStockInAmount;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
}
package com.hanyun.scm.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanyun.scm.api.domain.dto.QueryBillGoodsDTO;
import com.hanyun.scm.api.domain.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
@ApiModel(value = "采购订单商品")
public class PurchaseOrderDetail extends BaseRequest {

    @JsonIgnore
    private Long id;

    @ApiModelProperty(value = "订单id", required = true)
    private String orderId;

    @ApiModelProperty(value = "订单详情id")
    private String orderDetailId;

    @ApiModelProperty(value = "品牌id", required = true)
    private String brandId;

    @ApiModelProperty(value = "门店id")
    private String storeId;

    @ApiModelProperty(value = "商品id", required = true)
    private String goodsId;

    @ApiModelProperty(value = "商品名称", required = true)
    private String goodsName;

    private String goodsPic;

    @ApiModelProperty(value = "分类id", required = true)
    private String classifyId;

    @ApiModelProperty(value = "分类名称", required = true)
    private String classifyName;

    @ApiModelProperty(value = "商品类型")
    private Integer goodsType;

    @ApiModelProperty(value = "单位id")
    private String unitId;

    @ApiModelProperty(value = "单位名称")
    private String unitName;

    @ApiModelProperty(value = "单价", required = true)
    private Long unitPrice;

    private String unitPriceShow;

    private Double unitPriceDouble;

    @ApiModelProperty(value = "采购数量", required = true)
    private Long purchaseAmount;

    private Long stockInAmount;

    private Long totalPrice;

    private Long orderPrice;

    private Long stock;

    @ApiModelProperty(value = "赠送数量")
    private Long presentAmount;

    private String inStorage = "0";

    private Date createTime;

    private Date create_time1;

    private Date updateTime;

    private Integer validStatus;

    private Long purchasePrice;

    private Long pickingAmount;

    private String purchaseTotalPrice = "0";

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "规格")
    private String features;

    @ApiModelProperty(value = "条码")
    private String goodsBarCode;

    private List<String> idList;        //过滤的订单id集合

    private Long avgTotalPrice;

    private Integer withPage;

    private String ids;

    private Long resutNum;                //非持久化數據,用來存放總金额

    private Long resultTotal;                //非持久化數據,用來存放總數量

    private String oneTime;

    private String twoTime;

    private Integer stockNum;//库存量

    private String purchaseScale;//采购金额占比

    private String returnScale;    //退款金额占比

    private Long returnPrice;//退货总价

    private Long returnNum;//退货数

    private String numScale;//退货数量占比

    private String avgMoney;

    private Long resultPrice;

    @ApiModelProperty(value = "sku编码", required = true)
    private String goodsCode;

    @ApiModelProperty(value = "商品品牌")
    private String goodsBrandName;

    private String goodsTypeName;

    private Long unStockInAmount;

    private Long completeStockInAmount;

    private String condition;

    public PurchaseOrderDetail(){}

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public String getUnitPriceShow() {
        return unitPriceShow;
    }

    public void setUnitPriceShow(String unitPriceShow) {
        this.unitPriceShow = unitPriceShow;
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

    public Long getStockInAmount() {
        return stockInAmount;
    }

    public void setStockInAmount(Long stockInAmount) {
        this.stockInAmount = stockInAmount;
    }

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public void setGoodsBarCode(String goodsBarCode) {
        this.goodsBarCode = goodsBarCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
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

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
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

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(Long purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
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

    public String getInStorage() {
        return inStorage;
    }

    public void setInStorage(String inStorage) {
        this.inStorage = inStorage;
    }

    public Long getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Long purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Long getPickingAmount() {
        return pickingAmount;
    }

    public void setPickingAmount(Long pickingAmount) {
        this.pickingAmount = pickingAmount;
    }

    public String getPurchaseTotalPrice() {
        return purchaseTotalPrice;
    }

    public void setPurchaseTotalPrice(String purchaseTotalPrice) {
        this.purchaseTotalPrice = purchaseTotalPrice;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public Double getUnitPriceDouble() {
        return unitPriceDouble;
    }

    public void setUnitPriceDouble(Double unitPriceDouble) {
        this.unitPriceDouble = unitPriceDouble;
    }

    public Integer getWithPage() {
        return withPage;
    }

    public void setWithPage(Integer withPage) {
        this.withPage = withPage;
    }


    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Long getResutNum() {
        return resutNum;
    }

    public void setResutNum(Long resutNum) {
        this.resutNum = resutNum;
    }

    public Long getResultTotal() {
        return resultTotal;
    }

    public void setResultTotal(Long resultTotal) {
        this.resultTotal = resultTotal;
    }


    public String getOneTime() {
        return oneTime;
    }

    public void setOneTime(String oneTime) {
        this.oneTime = oneTime;
    }

    public String getTwoTime() {
        return twoTime;
    }

    public void setTwoTime(String twoTime) {
        this.twoTime = twoTime;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public Long getAvgTotalPrice() {
        return avgTotalPrice;
    }

    public void setAvgTotalPrice(Long avgTotalPrice) {
        this.avgTotalPrice = avgTotalPrice;
    }

    public Date getCreate_time1() {
        return create_time1;
    }

    public void setCreate_time1(Date create_time1) {
        this.create_time1 = create_time1;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public String getPurchaseScale() {
        return purchaseScale;
    }

    public void setPurchaseScale(String purchaseScale) {
        this.purchaseScale = purchaseScale;
    }

    public Long getReturnPrice() {
        return returnPrice;
    }

    public void setReturnPrice(Long returnPrice) {
        this.returnPrice = returnPrice;
    }

    public Long getReturnNum() {
        return returnNum;
    }

    public void setReturnNum(Long returnNum) {
        this.returnNum = returnNum;
    }

    public String getReturnScale() {
        return returnScale;
    }

    public void setReturnScale(String returnScale) {
        this.returnScale = returnScale;
    }

    public String getNumScale() {
        return numScale;
    }

    public void setNumScale(String numScale) {
        this.numScale = numScale;
    }

    public String getAvgMoney() {
        return avgMoney;
    }

    public void setAvgMoney(String avgMoney) {
        this.avgMoney = avgMoney;
    }

    public Long getResultPrice() {
        return resultPrice;
    }

    public void setResultPrice(Long resultPrice) {
        this.resultPrice = resultPrice;
    }

    public Long getPresentAmount() {
        return presentAmount;
    }

    public void setPresentAmount(Long presentAmount) {
        this.presentAmount = presentAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Long orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
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

    public Long getUnStockInAmount() {
        return unStockInAmount;
    }

    public void setUnStockInAmount(Long unStockInAmount) {
        this.unStockInAmount = unStockInAmount;
    }

    public Long getCompleteStockInAmount() {
        return completeStockInAmount;
    }

    public void setCompleteStockInAmount(Long completeStockInAmount) {
        this.completeStockInAmount = completeStockInAmount;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
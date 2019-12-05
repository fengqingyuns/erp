package com.hanyun.scm.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.scm.api.domain.request.BaseRequest;

import java.util.Date;

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
public class StockQuantChangeHistory extends BaseRequest {

    @JsonIgnore
    private Long id;

    private String stockQuantChangeId;

    private String stockQuantId;

    private String brandId;

    private String storeId;

    private String orderId;

    private String orderDocumentId;

    private String warehouseId;

    private String warehouseName;

    private String goodsId;

    private String goodsName;

    private String goodsBarCode;

    private String goodsCode;

    private Long unitPrice;

    private Long initialUnitPrice;

    private Long stockNum;

    private Long stockNumUsing;

    private Long stockNumOnOrder;

    private Long stockChangeNum;

    private Integer stockChangeType;

    private Date createTime;

    private Date updateTime;

    private String queryBeginTime;

    private String queryEndTime;

    private Integer outOrInType;

    private String condition;

    public StockQuantChangeHistory(){}

    public StockQuantChangeHistory(StockQuant stockQuant) {
        this.stockQuantChangeId = IdUtil.uuid();
        this.stockQuantId = stockQuant.getStockQuantId();
        this.brandId = stockQuant.getBrandId();
        this.storeId = stockQuant.getStoreId();
        this.warehouseId = stockQuant.getWarehouseId();
        this.warehouseName = stockQuant.getWarehouseName();
        this.goodsId = stockQuant.getGoodsId();
        this.goodsName = stockQuant.getGoodsName();
        this.goodsBarCode = stockQuant.getGoodsBarCode();
        this.unitPrice = stockQuant.getUnitPrice();
        this.initialUnitPrice = stockQuant.getInitialUnitPrice();
        this.stockNum = stockQuant.getStockNum();
        this.stockNumOnOrder = stockQuant.getStockNumOnOrder();
        this.stockNumUsing = stockQuant.getStockNumUsing();
        this.goodsCode = stockQuant.getGoodsCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockQuantChangeId() {
        return stockQuantChangeId;
    }

    public void setStockQuantChangeId(String stockQuantChangeId) {
        this.stockQuantChangeId = stockQuantChangeId;
    }

    public String getStockQuantId() {
        return stockQuantId;
    }

    public void setStockQuantId(String stockQuantId) {
        this.stockQuantId = stockQuantId;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDocumentId() {
        return orderDocumentId;
    }

    public void setOrderDocumentId(String orderDocumentId) {
        this.orderDocumentId = orderDocumentId;
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

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public void setGoodsBarCode(String goodsBarCode) {
        this.goodsBarCode = goodsBarCode;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getInitialUnitPrice() {
        return initialUnitPrice;
    }

    public void setInitialUnitPrice(Long initialUnitPrice) {
        this.initialUnitPrice = initialUnitPrice;
    }

    public Long getStockNum() {
        return stockNum;
    }

    public void setStockNum(Long stockNum) {
        this.stockNum = stockNum;
    }

    public Long getStockNumUsing() {
        return stockNumUsing;
    }

    public void setStockNumUsing(Long stockNumUsing) {
        this.stockNumUsing = stockNumUsing;
    }

    public Long getStockNumOnOrder() {
        return stockNumOnOrder;
    }

    public void setStockNumOnOrder(Long stockNumOnOrder) {
        this.stockNumOnOrder = stockNumOnOrder;
    }

    public Long getStockChangeNum() {
        return stockChangeNum;
    }

    public void setStockChangeNum(Long stockChangeNum) {
        this.stockChangeNum = stockChangeNum;
    }

    public Integer getStockChangeType() {
        return stockChangeType;
    }

    public void setStockChangeType(Integer stockChangeType) {
        this.stockChangeType = stockChangeType;
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

    public String getQueryBeginTime() {
        return queryBeginTime;
    }

    public void setQueryBeginTime(String queryBeginTime) {
        this.queryBeginTime = queryBeginTime;
    }

    public String getQueryEndTime() {
        return queryEndTime;
    }

    public void setQueryEndTime(String queryEndTime) {
        this.queryEndTime = queryEndTime;
    }

    public Integer getOutOrInType() {
        return outOrInType;
    }

    public void setOutOrInType(Integer outOrInType) {
        this.outOrInType = outOrInType;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
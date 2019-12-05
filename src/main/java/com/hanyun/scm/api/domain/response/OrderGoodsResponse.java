package com.hanyun.scm.api.domain.response;

import java.util.Date;
import java.util.List;

/**
 * Created by hanyun on 2017/2/21.
 */
public class OrderGoodsResponse {

    private String unitName;

    private String goodsName;

    private Integer goodsCount;

    private Integer count;

    private Integer amountCount;

    private String unitPrice;

    private String storeName;

    private Date createTime;

    private String goodsId;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private List<OrderGoodsResponse> orderGoodsList;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getAmountCount() {
        return amountCount;
    }

    public void setAmountCount(Integer amountCount) {
        this.amountCount = amountCount;
    }

    public List<OrderGoodsResponse> getOrderGoodsList() {
        return orderGoodsList;
    }

    public void setOrderGoodsList(List<OrderGoodsResponse> orderGoodsList) {
        this.orderGoodsList = orderGoodsList;
    }
}

package com.hanyun.scm.api.domain;

public class BaseParams {

    private String brandId;

    private String storeId;

    private String beginTime;

    private String endTime;

    private Integer stockVarianceType; //   损/溢单类型：1报损单 2报溢单

    private Integer stockPickingType;  //   库存移动类型0、采购入库单  1、采购退货单  2、普通入库单  3、普通出库单  4、移库单  5、调拨单  6、收货单

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

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public Integer getStockVarianceType() {
        return stockVarianceType;
    }

    public void setStockVarianceType(Integer stockVarianceType) {
        this.stockVarianceType = stockVarianceType;
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
}

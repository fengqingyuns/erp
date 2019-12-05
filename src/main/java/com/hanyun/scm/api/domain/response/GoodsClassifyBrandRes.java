package com.hanyun.scm.api.domain.response;


import com.hanyun.scm.api.domain.GoodsClassifyBrand;

import java.util.List;

public class GoodsClassifyBrandRes {

    private List<GoodsClassifyBrand> goodsClassifyBrandList;

    private Integer count;

    public List<GoodsClassifyBrand> getGoodsClassifyBrandList() {
        return goodsClassifyBrandList;
    }

    public void setGoodsClassifyBrandList(List<GoodsClassifyBrand> goodsClassifyBrandList) {
        this.goodsClassifyBrandList = goodsClassifyBrandList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}

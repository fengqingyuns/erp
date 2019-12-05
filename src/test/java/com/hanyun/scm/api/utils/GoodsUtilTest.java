package com.hanyun.scm.api.utils;

import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.scm.api.domain.GoodsClassifyBrand;
import org.junit.Test;

import java.util.List;


public class GoodsUtilTest {
    @Test
    public void getClassify() throws Exception {
        GoodsClassifyBrand goodsClassifyBrand = new GoodsClassifyBrand();
        goodsClassifyBrand.setBrandId("ADE664709EED294662ABB9425403BEE026");
        List<GoodsClassifyBrand> goodsClassifyBrandList = GoodsUtil.getClassify(goodsClassifyBrand);
        System.err.println(JsonUtil.toJson(goodsClassifyBrandList));
    }

}
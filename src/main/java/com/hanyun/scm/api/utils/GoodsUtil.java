package com.hanyun.scm.api.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hanyun.ground.util.http.HttpClient;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.GoodsClassifyBrand;
import com.hanyun.scm.api.domain.response.GoodsClassifyBrandRes;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.texen.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * <pre>
 *                             _ooOoo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            O\  =  /O
 *                         ____/`---'\____
 *                       .'  \\|     |//  `.
 *                      /  \\|||  :  |||//  \
 *                     /  _||||| -:- |||||-  \
 *                     |   | \\\  -  /// |   |
 *                     | \_|  ''\---/''  |   |
 *                     \  .-\__  `-`  ___/-. /
 *                   ___`. .'  /--.--\  `. . __
 *                ."" '<  `.___\_<|>_/___.'  >'"".
 *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *          ======`-.____`-.___\_____/___.-`____.-'======
 *                             `=---='
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *
 *                     佛祖保佑        永无BUG
 *
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * </pre>
 * GoodsUtil
 * Date: 2016/12/18
 * Time: 下午3:12
 *
 * @author tianye@hanyun.com
 */
public class GoodsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsUtil.class);

    /**
     * 查询商品分类
     *
     * @param goodsClassifyBrand 查询商品分类参数
     * @return 商品分类列表
     * @throws Exception 查询异常
     */
    public static List<GoodsClassifyBrand> getClassify(GoodsClassifyBrand goodsClassifyBrand) throws Exception {
        PropertiesUtil propertiesUtil = new PropertiesUtil();
        Properties properties = propertiesUtil.load("erp-api.properties");
        String queryClassifyUrl = properties.getProperty("queryClassify");
        HttpClient httpClient = HttpClient.get(queryClassifyUrl);
        if (!StringUtils.isEmpty(goodsClassifyBrand.getBrandId())) {
            httpClient.addParameter("brandId", goodsClassifyBrand.getBrandId());
        }
        if (!StringUtils.isEmpty(goodsClassifyBrand.getClassifyId())) {
            httpClient.addParameter("classifyId", goodsClassifyBrand.getClassifyId());
        }
        if (!StringUtils.isEmpty(goodsClassifyBrand.getClassifyName())) {
            httpClient.addParameter("classifyName", goodsClassifyBrand.getClassifyName());
        }
        if (goodsClassifyBrand.getClassifyStatus() != null) {
            httpClient.addParameter("classifyStatus", String.valueOf(goodsClassifyBrand.getClassifyStatus()));
        }
        String responseBody = httpClient.action().getBody();
        HttpResponse httpResponse = JsonUtil.fromJson(responseBody, HttpResponse.class);
        if (StringUtils.isEmpty(httpResponse.getCode()) || !"0".equals(httpResponse.getCode())) {
            LOGGER.error("获取商品分类失败!");
            throw new RuntimeException("获取商品分类失败!");
        }
        if (httpResponse.getData() == null) {
            return new ArrayList<>();
        }
        GoodsClassifyBrandRes goodsClassifyBrandRes =  JsonUtil.fromJson(JsonUtil.toJson(httpResponse.getData()), GoodsClassifyBrandRes.class);
        return goodsClassifyBrandRes.getGoodsClassifyBrandList();
    }

}

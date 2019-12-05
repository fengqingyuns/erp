package com.hanyun.scm.api.utils;


import com.alibaba.druid.util.StringUtils;
import com.hanyun.ground.util.http.HttpClient;
import com.hanyun.ground.util.http.HttpClientPost;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.SystemDistributionConfig;
import com.hanyun.scm.api.exception.DistributionConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * 查询系统设置
 * Date: 2017/06/15
 * Time: 14:04
 *
 * @author tangqiming_v@hanyun.com
 */
public class SystemConfigUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemConfigUtil.class);

    /**
     * 查询配送设置
     *
     * @param brandId 品牌Id
     * @param storeId 门店Id
     * @return HttpResponse
     */
    public static SystemDistributionConfig queryDistributionConfig(String brandId, String storeId) throws DistributionConfigException {
        try {
            SystemDistributionConfig record = new SystemDistributionConfig();
            record.setBrandId(brandId);
            record.setStoreId(storeId);
            PropertiesUtil propertiesUtil = new PropertiesUtil();
            Properties properties = propertiesUtil.getProperties("erp-api.properties");
            StringBuffer url = new StringBuffer(properties.getProperty("queryDistributionConfigUrl"));
            HttpClientPost httpClient = HttpClient.post(url.toString());
            String result = httpClient.json(record).action().result();
            if(StringUtils.isEmpty(result)){
                throw new DistributionConfigException(ResultCode.DISTRIBUTION_CONFIG_QUERY_ERROR.getMessage());
            }
            HttpResponse httpResponse = JsonUtil.fromJson(result,HttpResponse.class);
            if(StringUtils.isEmpty(httpResponse.getCode()) || !StringUtils.equals(httpResponse.getCode(), "0") || httpResponse.getData() == null){
                return null;
            }
            return JsonUtil.fromJson(JsonUtil.toJson(httpResponse.getData()),SystemDistributionConfig.class);
        }catch (Exception e){
            LOGGER.error("查询配送系统设置失败", e);
            throw new DistributionConfigException("查询配送系统设置失败",e);
        }
    }

    /**
     * 获取系统配送设置的配送类型
     * @param brandId 品牌id
     * @param storeId 门店id
     * @return Integer(配送方式 1:一次配送 2:多次配送)
     */
    public static Integer getDistributionType(String brandId, String storeId) {
        try {
            SystemDistributionConfig config = queryDistributionConfig(brandId, storeId);
            if (config == null || config.getDistributionType() == null) {
                return Consts.SINGLE_DISTRIBUTION_TYPE;
            }
            return config.getDistributionType();
        } catch (DistributionConfigException e) {
            LOGGER.error("获取配置信息失败", e);
            return Consts.SINGLE_DISTRIBUTION_TYPE;
        }
    }

}

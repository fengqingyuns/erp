package com.hanyun.scm.api.utils;

import com.alibaba.druid.util.StringUtils;
import org.apache.velocity.texen.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
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
 * FileUtil
 * Date: 2016/11/10
 * Time: 下午3:12
 *
 * @author tianye@hanyun.com
 */
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    private static final String TEMPLATE_NAME = "base.xls";

    /**
     * 读取xls模板
     * @param templateName
     * @return
     */
    public static File getXlsFile(String templateName) {
        File file;
        try {
            PropertiesUtil propertiesUtil = new PropertiesUtil();
            Properties properties = propertiesUtil.load("erp-api.properties");
            String templateUrl = properties.getProperty("templateUrl");
            if (StringUtils.isEmpty(templateName)) {
                templateName = TEMPLATE_NAME;
            }
            file = new File(templateUrl + templateName);
            if (!file.exists()) {
                URL fileUrl = FileUtil.class.getClassLoader().getResource("/../../template/" + templateName);
                if (fileUrl == null) {
                    throw new RuntimeException("模板不存在!");
                }
                file = new File(fileUrl.getPath());
                if (!file.exists()) {
                    throw new RuntimeException("模板不存在!");
                }
            }
        } catch (Exception e) {
            LOGGER.error("获取模板失败!", e);
            throw new RuntimeException("获取模板失败!");
        }
        return file;
    }
}

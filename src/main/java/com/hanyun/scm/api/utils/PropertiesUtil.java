package com.hanyun.scm.api.utils;

import java.io.IOException;
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
 * PropertiesUtil
 * Date: 2016/12/29
 * Time: 下午3:22
 *
 * @author tianye@hanyun.com
 */
public class PropertiesUtil {

    public static Properties getProperties(String propertiesName) throws Exception{
        Properties properties;
        try {
            properties = new Properties();
            properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesName));
        } catch (IOException e) {
            throw new RuntimeException("读取properties文件失败!", e);
        }
        return properties;
    }
}

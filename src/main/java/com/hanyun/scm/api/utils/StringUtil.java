package com.hanyun.scm.api.utils;

import com.hanyun.ground.util.date.DateFormatUtil;

import java.util.Date;

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
 * StringUtil
 * Date: 2016/11/17
 * Time: 下午2:25
 *
 * @author tianye@hanyun.com
 */
public class StringUtil {

    /**
     * 生成编号
     * @param preStr
     * @param id
     * @return
     */
    public static String generateDocumentId(String preStr, Long id) {
        String idStr = id + "";
        String documentId = id + "";
        String dateYmd = DateFormatUtil.formatDateNoSep(new Date()).substring(2);
        int len = 6;
        if (idStr.length()>6) {
            len = idStr.length();
        }
        for (int i=0;i<(len-idStr.length());i++) {
            documentId = "0" + documentId;
        }
        return preStr + dateYmd + documentId;
    }
}

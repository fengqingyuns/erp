package com.hanyun.scm.api.web.util;

import com.hanyun.scm.api.domain.ExcelBaseBean;
import com.hanyun.scm.api.utils.ExcelStreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

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
 * ExcelUtil
 * Date: 2016/12/15
 * Time: 上午11:42
 *
 * @author tianye@hanyun.com
 */
public class ExcelUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 不固定标题excel导出
     * @param excelBaseBean 构造实体
     * @param response      response
     * @throws Exception
     */
    public static void dynamicExport(ExcelBaseBean excelBaseBean, HttpServletResponse response) throws Exception {
        //设置文件信息
        response.setContentType("text/plain;charset=utf-8");
        response.setHeader("Content-Type","application/force-download");
        response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(excelBaseBean.getXlsName(), "utf-8") + ".xls");
        ServletOutputStream out = response.getOutputStream();
        try {
           ExcelStreamUtil.dynamicDeaWithExcelStream(excelBaseBean, out);
        } catch (Exception e) {
            if (out != null) {
                out.close();
            }
            LOGGER.error("写入excel流失败", e);
            throw new Exception("写入excel流失败!", e);
        }
    }

    /**
     * 固定标题excel导出
     * @param xlsName       导出文件名
     * @param templateName  模板名
     * @param values        数据集
     * @param attributes    数据属性集合(即数据集中元素的属性名)
     * @param response      response
     * @throws Exception
     */
    public static void export(String xlsName, String templateName, List values, String[] attributes, HttpServletResponse response) throws Exception{
        //设置文件信息
        response.setContentType("text/plain;charset=utf-8");
        response.setHeader("Content-Type","application/force-download");
        response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(xlsName, "utf-8") + ".xls");
        ServletOutputStream out = response.getOutputStream();
        try {
            ExcelStreamUtil.dealWithExcelStream(templateName, values, attributes, out);
        } catch (Exception e) {
            if (out != null) {
                out.close();
            }
            LOGGER.error("写入excel流失败", e);
            throw new Exception("写入excel流失败!", e);
        }

    }
}

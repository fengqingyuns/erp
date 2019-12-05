package com.hanyun.scm.api.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hanyun.ground.util.date.DateFormatUtil;
import com.hanyun.scm.api.domain.ExcelBaseBean;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.RichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
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
 * Date: 2016/12/8
 * Time: 下午4:53
 *
 * @author tianye@hanyun.com
 */
public class ExcelStreamUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelStreamUtil.class);

    /**
     * 不固定标题excel流处理
     * @param excelBaseBean 构造实体
     * @param out           out
     * @throws Exception
     */
    public static void dynamicDeaWithExcelStream(ExcelBaseBean excelBaseBean, OutputStream out) throws Exception{
        FileInputStream ff = null;
        try {
            //读取模板
            File file = FileUtil.getXlsFile(excelBaseBean.getTemplateName());
            ff = FileUtils.openInputStream(file);
            POIFSFileSystem fs = new POIFSFileSystem(ff);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            int rows = excelBaseBean.getData().size();
            for (int i=0;i<excelBaseBean.getTitles().size();i++) {
                setCellValue(sheet, 1, i+1, excelBaseBean.getTitles().get(i));
            }
            if (rows > 0) {
                sheet.shiftRows(1, sheet.getLastRowNum(), rows, true,false);
                for (int i=0;i<excelBaseBean.getData().size();i++) {
                    int startRow = i + 2;
                    for (int j=0;j<excelBaseBean.getData().get(i).size();j++) {
                        setCellValue(sheet, startRow, j+1, excelBaseBean.getData().get(i).get(j));
                    }
                }
            }
            wb.write(out);
        } catch (Exception e) {
            if (ff != null) {
                ff.close();
            }
            LOGGER.error("导出失败!", e);
            throw new Exception("导出失败!", e);
        }
    }

    /**
     * 固定标题excel流处理
     * @param templateName  模板名
     * @param values        数据集
     * @param attributes    数据属性集合(即数据集中元素的属性名)
     * @param out           out
     * @return
     * @throws Exception
     */
    public static void dealWithExcelStream(String templateName, List values, String[] attributes, OutputStream out) throws Exception{
        FileInputStream ff = null;
        try {
            //读取模板
            File file = FileUtil.getXlsFile(templateName);
            ff = FileUtils.openInputStream(file);
            POIFSFileSystem fs = new POIFSFileSystem(ff);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            int rows = values.size();
            if (rows > 0) {
                sheet.shiftRows(1, sheet.getLastRowNum(), rows, true,false);
                for (int i=0;i<values.size();i++) {
                    int startRow = i + 2;
                    int seq = i + 1;
                    Object value = values.get(i);
                    JSONObject object = JSON.parseObject(JSON.toJSONString(value));
                    setCellValue(sheet, startRow, 1, seq);
                    for (int j=0;j<attributes.length;j++) {
                        if (StringUtils.upperCase(attributes[j]).contains("PRICE") && object.get(attributes[j]) != null) {
                            setCellValue(sheet, startRow, j+2, Double.parseDouble(object.get(attributes[j]) + "")/100);
                        } else if (StringUtils.upperCase(attributes[j]).contains("TIME") && object.get(attributes[j]) != null) {
                            setCellValue(sheet, startRow, j+2, new Date(Long.parseLong(object.get(attributes[j])+"")));
                        } else {
                            setCellValue(sheet, startRow, j+2, object.get(attributes[j]));
                        }
                    }
                }
            }
            wb.write(out);

        } catch (Exception e) {
            if (ff != null) {
                ff.close();
            }
            LOGGER.error("导出失败!", e);
            throw new Exception("导出失败!", e);
        }
    }

    /**
     * 设置xls单元格值
     * @param sheet     表单
     * @param rowNum    行号
     * @param cellNum   列
     * @param cellValue 值
     * @return
     */
    public static HSSFCell setCellValue(HSSFSheet sheet, int rowNum, int cellNum, Object cellValue){
        rowNum = rowNum -1;
        cellNum = cellNum -1;
        HSSFRow row = sheet.getRow(rowNum);
        if(row == null) {
            row = sheet.createRow(rowNum);
        }
        HSSFCell cell = row.getCell(cellNum);
        if(cell == null) {
            cell = row.createCell(cellNum);
        }
        if (cellValue == null) {
            return cell;
        }
        if (cellValue instanceof Double) {
            cell.setCellValue((Double) cellValue);
        } else if (cellValue instanceof Float){
            cell.setCellValue((Float) cellValue);
        } else if (cellValue instanceof Integer) {
            cell.setCellValue((Integer) cellValue);
        } else if (cellValue instanceof Long) {
            cell.setCellValue((Long) cellValue);
        } else if (cellValue instanceof String) {
            cell.setCellValue(cellValue.toString());
        } else if (cellValue instanceof Date) {
            cell.setCellValue(DateFormatUtil.formatDateTime((Date)cellValue));
        } else if (cellValue instanceof Calendar) {
            cell.setCellValue((Calendar) cellValue);
        } else if (cellValue instanceof RichTextString) {
            cell.setCellValue((RichTextString) cellValue);
        }
        return cell;
    }
}

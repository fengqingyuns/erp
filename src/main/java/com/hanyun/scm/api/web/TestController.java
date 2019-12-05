package com.hanyun.scm.api.web;

import com.hanyun.scm.api.consts.ExcelTitle;
import com.hanyun.scm.api.domain.ExcelBaseBean;
import com.hanyun.scm.api.exception.ScmException;
import com.hanyun.scm.api.service.TestService;
import com.hanyun.scm.api.web.util.ExcelUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
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
 * TestController
 * Date: 16/6/22
 * Time: 下午1:51
 *
 * @author tianye@hanyun.com
 */
@Controller
@RequestMapping(value = "/")
public class TestController {

    @Resource
    TestService testService;

    @GetMapping("/")
    public void testController(HttpServletResponse response) throws ScmException {
        String responseStr = testService.testService();
        response.setCharacterEncoding("gb2312");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out;
        try {
            out = response.getWriter();
        } catch (Exception e) {
            throw new ScmException("testController-系统异常!", e);
        }
        out.print(responseStr);
    }

    @GetMapping("/testExport")
    public void testExport(HttpServletResponse response) throws ScmException {
        try {
            List<String> titles = new ArrayList<>();
            titles.add(ExcelTitle.INDEX.getName());
            titles.add(ExcelTitle.GOODS_CODE.getName());
            titles.add(ExcelTitle.GOODS_NAME.getName());
            titles.add(ExcelTitle.UNIT_NAME.getName());
            List<List<Object>> dataList = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                List<Object> data = new ArrayList<>();
                data.add(1);
                data.add("一");
                data.add(1.11);
                data.add(1L);
                dataList.add(data);
            }
            ExcelBaseBean excelBaseBean = new ExcelBaseBean();
            excelBaseBean.setTitles(titles);
            excelBaseBean.setData(dataList);
            excelBaseBean.setXlsName("测试导出");
            ExcelUtil.dynamicExport(excelBaseBean, response);
        } catch (Exception e) {
            throw new ScmException("testExport-导出异常!", e);
        }
    }

}

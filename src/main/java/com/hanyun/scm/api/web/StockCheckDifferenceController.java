package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.StockCheckDifference;
import com.hanyun.scm.api.domain.StockCheckDifferenceDetail;
import com.hanyun.scm.api.domain.request.stock.StockCheckDifferenceRequest;
import com.hanyun.scm.api.service.StockCheckDifferenceService;
import com.hanyun.scm.api.web.util.ExcelUtil;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
 * StockCheckTaskController
 * Date: 2016/12/25
 * Time: 下午7:37
 *
 * @author tianye@hanyun.com
 */
@Controller
@RequestMapping(value = "/stock-check-difference")
public class StockCheckDifferenceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockCheckDifferenceController.class);
    @Resource
    private StockCheckDifferenceService stockCheckDifferenceService;

    /**
     * 查询盘点差异列表
     *
     * @param stockCheckDifferenceRequest   参数
     * @return HttpResponse
     */
    @GetMapping(value = "/query")
    @ResponseBody
    public HttpResponse select(@Valid StockCheckDifferenceRequest stockCheckDifferenceRequest) {
        return stockCheckDifferenceService.select(stockCheckDifferenceRequest);
    }

    /**
     * 查询盘点差异详情
     *
     * @param stockCheckDifferenceId    id
     * @return HttpResponse
     */
    @GetMapping(value = "/detail/{stockCheckDifferenceId}")
    @ResponseBody
    public HttpResponse detail(@PathVariable String stockCheckDifferenceId) {
        return stockCheckDifferenceService.detail(stockCheckDifferenceId);
    }

    /***
     * 修改差异表基础数据
     * @param stockCheckDifference  参数
     * @return
     */
    @PostMapping(value = "/modify")
    @ResponseBody
    public HttpResponse modifyDifference(@RequestBody StockCheckDifference stockCheckDifference) {
        return stockCheckDifferenceService.modifyDifference(stockCheckDifference);
    }

    /****
     * 审核差异表
     */
    @PostMapping(value = "/audit")
    @ResponseBody
    public HttpResponse auditDifference(@RequestBody StockCheckDifference stockCheckDifference) {
        return stockCheckDifferenceService.auditDifference(stockCheckDifference);
    }

    /**
     * 提审盘点差异单
     *
     * @param stockCheckDifferenceId 差异单参数id
     * @return HttpResponse
     */
    @GetMapping(value = "/commit/{stockCheckDifferenceId}")
    @ResponseBody
    public HttpResponse commit(@NotEmpty @PathVariable String stockCheckDifferenceId) {
        return stockCheckDifferenceService.commit(stockCheckDifferenceId);
    }

    /**
     * 盘点差异单导出
     *
     * @param stockCheckDifferenceRequest   差异单参数
     * @param request   request
     * @param response  response
     */
    @GetMapping(value = "/exportCheckDifference")
    @ResponseBody
    public void exportCheckDifference(StockCheckDifferenceRequest stockCheckDifferenceRequest, HttpServletRequest request,
                                      HttpServletResponse response) {

        List<StockCheckDifference> list = stockCheckDifferenceService.exportCheckDifference(stockCheckDifferenceRequest);
        String xlsName = "盘点差异单";
        String templateName = "exportDifference.xls";
        String[] attributes = new String[]{"stockCheckDifferenceDocumentId", "stockCheckTaskDocumentId", "exporttype", "warehouseName", "exportStatus"};
        try {
            ExcelUtil.export(xlsName, templateName, list, attributes, response);
        } catch (Exception e) {
            LOGGER.error("导出盘点差异单失败",e);
        }
    }

    /**
     * @param StockCheckDifference
     * @return
     * @Description:
     */
    /**
     * 生成报损报溢单
     *
     * @param stockCheckDifference  差异单参数
     * @return  HttpResponse
     */
    @PostMapping(value = "/createVariance")
    @ResponseBody
    public HttpResponse createVariance(@RequestBody StockCheckDifference stockCheckDifference) {
        return stockCheckDifferenceService.createVariance(stockCheckDifference);
    }

    /**
     *盘点差异单导出
     *
     * @param stockCheckDifferenceRequest   参数
     * @param request   request
     * @param response  response
     */
    @GetMapping(value = "/exportDifferenceUpdate")
    @ResponseBody
    public void exportDifferenceUpdate(StockCheckDifferenceRequest stockCheckDifferenceRequest, HttpServletRequest request,
                                       HttpServletResponse response) {

        List<StockCheckDifferenceDetail> list = stockCheckDifferenceService.exportDifferenceUpdate(stockCheckDifferenceRequest);
        String xlsName = "盘点差异单：" + stockCheckDifferenceRequest.getStockCheckDifferenceDocumentId();
        String templateName = "exportCheckDiffUpdate.xls";
        String[] attributes = new String[]{"goodsCode", "goodsBarCode", "goodsName", "features",
                "unitName", "stockNum", "unitPrice", "systemPrice", "checkStock", "stockNowPrice", "checkDiffStock", "changePrice", "spillOrLoss"};
        try {
            ExcelUtil.export(xlsName, templateName, list, attributes, response);
        } catch (Exception e) {
            LOGGER.error("导出盘点差异单失败",e);
        }
    }
}

package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.StockSpillLossOrder;
import com.hanyun.scm.api.domain.StockSpillLossOrderDetail;
import com.hanyun.scm.api.domain.request.stock.StockSpillLossOrderRequest;
import com.hanyun.scm.api.exception.StockSpillLossException;
import com.hanyun.scm.api.service.StockSpillLossOrderService;
import com.hanyun.scm.api.web.util.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
 * @author qiaoyu_v@hanyun.com
 */
@Controller
@RequestMapping(value = "/stock-spill-Loss-order")
public class StockSpillLossOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockSpillLossOrderController.class);
    @Resource
    private StockSpillLossOrderService stockSpillLossOrderService;

    /**
     * 查询报损报溢列表
     *
     * @param stockSpillLossOrderRequest 参数
     * @return HttpResponse
     */
    @GetMapping(value = "/query")
    @ResponseBody
    public HttpResponse select(StockSpillLossOrderRequest stockSpillLossOrderRequest) {
        return stockSpillLossOrderService.select(stockSpillLossOrderRequest);
    }

    /***
     * 修改报损报溢数据
     * @param stockSpillLossOrder    参数
     * @return HttpResponse
     */
    @PostMapping(value = "/modify")
    @ResponseBody
    public HttpResponse modify(@RequestBody StockSpillLossOrder stockSpillLossOrder) {
        return stockSpillLossOrderService.modifyOrder(stockSpillLossOrder);
    }

    /*****
     * 创建报损报溢单
     * @param stockSpillLossOrder    参数
     *
     */
    @PostMapping(value = "/create")
    @ResponseBody
    public HttpResponse create(@RequestBody StockSpillLossOrder stockSpillLossOrder) {
        return stockSpillLossOrderService.create(stockSpillLossOrder);
    }

    /*********
     * 审核单据
     */
    @PostMapping(value = "/conform")
    @ResponseBody
    public HttpResponse conformOrder(@RequestBody StockSpillLossOrder stockSpillLossOrder) {
        return stockSpillLossOrderService.auditOrder(stockSpillLossOrder);
    }

    /**
     * @param stockSpillLossOrderRequest 参数
     *                                   报损报溢单-导出
     */
    @GetMapping(value = "/exportvariance")
    @ResponseBody
    public void exportVariance(StockSpillLossOrderRequest stockSpillLossOrderRequest, HttpServletRequest request,
                               HttpServletResponse response) throws StockSpillLossException {

        List<StockSpillLossOrder> list = stockSpillLossOrderService.exportStockSpillLossOrder(stockSpillLossOrderRequest);
        String xlsName = "报损报溢单";
        String templateName = "exportStockSpillLossOrder.xls";
        String[] attributes = new String[]{"stockVarianceDocumentId", "exportType", "stockCheckDifferenceDocumentId", "differenceNum", "differencePrice",
                "warehouseName", "operatorName", "createTime", "exportReason", "exportStatus"};
        try {
            ExcelUtil.export(xlsName, templateName, list, attributes, response);
        } catch (Exception e) {
            LOGGER.error("导出报损报溢单失败");
            throw new StockSpillLossException("导出报损报溢单失败", e);
        }
    }

    /**
     * 报损报溢单详单--导出
     */
    @GetMapping(value = "/exportSpillAndLossUpdate")
    @ResponseBody
    public void exportSpillAndLossUpdate(StockSpillLossOrderRequest stockSpillLossOrderRequest, HttpServletRequest request,
                                         HttpServletResponse response) throws StockSpillLossException {
        List<StockSpillLossOrderDetail> goodsList = stockSpillLossOrderService.exportSpillAndLossUpdate(stockSpillLossOrderRequest);
        String xlsName = null;
        String templateName = null;
        if (stockSpillLossOrderRequest.getStockVarianceType() == 1) {
            xlsName = "报损单" + stockSpillLossOrderRequest.getStockVarianceDocumentId();
            templateName = "exportLoss.xls";
        } else if (stockSpillLossOrderRequest.getStockVarianceType() == 2) {
            xlsName = "报溢单" + stockSpillLossOrderRequest.getStockVarianceDocumentId();
            templateName = "exportSpill.xls";
        }
        String[] attributes = new String[]{"goodsCode", "goodsBarCode", "goodsName", "feature", "unitName",
                "systemStock", "unitPrice", "systemPrice", "varianceStock", "differencePrice", "remark"};
        try {
            ExcelUtil.export(xlsName, templateName, goodsList, attributes, response);
        } catch (Exception e) {
            LOGGER.error("导出报损报溢单详单失败");
            throw new StockSpillLossException("导出报损报溢单详单失败", e);
        }
    }

    /**
     * 提审
     * @param spillLossOrderId  报损报溢单id
     * @return  返回值
     */
    @GetMapping(value = "/commit/{spillLossOrderId}")
    @ResponseBody
    public HttpResponse commit(@PathVariable String spillLossOrderId) {
        return stockSpillLossOrderService.commit(spillLossOrderId);
    }
}

package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.StockCheckOrderDetail;
import com.hanyun.scm.api.domain.request.stock.StockCheckOrderCreateRequest;
import com.hanyun.scm.api.domain.request.stock.StockCheckOrderModifyRequest;
import com.hanyun.scm.api.domain.request.stock.StockCheckOrderQueryRequest;
import com.hanyun.scm.api.exception.StockCheckOrderException;
import com.hanyun.scm.api.service.StockCheckOrderService;
import com.hanyun.scm.api.web.util.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
 * StockCheckOrderController
 * Date: 2016/12/25
 * Time: 下午7:37
 *
 * @author tianye@hanyun.com
 */
@Controller
@RequestMapping(value = "/stock-check-order")
public class StockCheckOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockCheckOrderController.class);

    @Resource
    private StockCheckOrderService stockCheckOrderService;

    /**
     * 创建库存盘点信息
     *
     * @param stockCheckOrderCreateRequest 创建盘点单参数
     * @return HttpResponse
     */
    @PostMapping(value = "/create")
    @ResponseBody
    public HttpResponse create(@RequestBody @Valid StockCheckOrderCreateRequest stockCheckOrderCreateRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("创建库存盘点单参数错误!");
            return HttpResponse.failure(ResultCode.STOCK_CHECK_CREATE_PARAM_ERROR);
        }
        return stockCheckOrderService.create(stockCheckOrderCreateRequest);
    }

    /**
     * 查询库存盘点信息列表
     *
     * @param stockCheckOrderQueryRequest 查询盘点单参数
     * @return HttpResponse
     */
    @GetMapping(value = "/query")
    @ResponseBody
    public HttpResponse select(@Valid StockCheckOrderQueryRequest stockCheckOrderQueryRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("查询盘点参数错误!");
            return HttpResponse.failure(ResultCode.STOCK_CHECK_QUERY_PARAM_ERROR);
        }
        return stockCheckOrderService.select(stockCheckOrderQueryRequest);
    }

    /**
     * 修改库存盘点信息
     *
     * @param stockCheckModifyModifyRequest 修改盘点单参数
     * @return HttpResponse
     */
    @PutMapping(value = "/modify")
    @ResponseBody
    public HttpResponse modify(@RequestBody @Valid StockCheckOrderModifyRequest stockCheckModifyModifyRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("编辑盘点参数错误!");
            return HttpResponse.failure(ResultCode.STOCK_CHECK_MODIFY_PARAM_ERROR);
        }
        return stockCheckOrderService.modify(stockCheckModifyModifyRequest);
    }

    /**
     * 删除库存盘点信息
     *
     * @param stockCheckOrderId 盘点单id
     * @return HttpResponse
     */
    @DeleteMapping(value = "/delete/{stockCheckOrderId}")
    @ResponseBody
    public HttpResponse delete(@PathVariable String stockCheckOrderId) {
        return stockCheckOrderService.delete(stockCheckOrderId);
    }

    /**
     * 查询库存盘点详情
     *
     * @param stockCheckOrderkId 盘点单id
     * @return HttpResponse
     */
    @GetMapping(value = "/detail/{stockCheckOrderkId}")
    @ResponseBody
    public HttpResponse detail(@PathVariable String stockCheckOrderkId, StockCheckOrderDetail record) {
        return stockCheckOrderService.detail(stockCheckOrderkId, record);
    }

    /**
     * 导出库存盘点单列表
     *
     * @param stockCheckOrderQueryRequest 查询盘点单参数
     * @throws StockCheckOrderException 异常
     */
    @GetMapping(value = "/export")
    public void exportStockCheckOrderList(@Valid StockCheckOrderQueryRequest stockCheckOrderQueryRequest, BindingResult result, HttpServletResponse response) throws StockCheckOrderException {
        if (result.hasErrors()) {
            LOGGER.error("导出库存盘点单列表参数错误!");
            throw new StockCheckOrderException("导出库存盘点单列表参数错误!");
        }
        try {
            List stockCheckOrderList = stockCheckOrderService.selectList(stockCheckOrderQueryRequest, true).getList();
            String xlsName = "库存盘点单列表";
            String templateName = "stock_check_order_list.xls";
            String[] attributes = new String[]{"stockCheckOrderDocumentId", "businessManName", "stockCheckTaskDocumentId",
                    "stockCheckTaskTypeName", "warehouseName", "operatorName", "createTime", "stockCheckOrderStatusName"};
            ExcelUtil.export(xlsName, templateName, stockCheckOrderList, attributes, response);
        } catch (Exception e) {
            throw new StockCheckOrderException("导出库存盘点单列表参数错误");
        }
    }

    /**
     * 导出库存盘点单详单
     *
     * @param stockCheckOrderQueryRequest 查询盘点单参数
     * @throws StockCheckOrderException 异常
     */
    @GetMapping(value = "/exportCheckOrderUpdate")
    public void exportCheckOrderUpdate(@Valid StockCheckOrderQueryRequest stockCheckOrderQueryRequest, HttpServletResponse response) throws StockCheckOrderException {
        List<StockCheckOrderDetail> stockCheckOrderDetailList = new ArrayList<StockCheckOrderDetail>();
        try {
            stockCheckOrderDetailList = stockCheckOrderService.exportCheckOrderUpdate(stockCheckOrderQueryRequest);
            String xlsName = "库存盘点单详情：" + stockCheckOrderQueryRequest.getStockCheckOrderDocumentId();
            String templateName = "exportCheckOrderUpdate.xls";
            String[] attributes = new String[]{"goodsCode", "goodsBarCode", "goodsName", "features",
                    "unitName", "stockNum", "unitPrice", "totalPrice", "remark"};
            ExcelUtil.export(xlsName, templateName, stockCheckOrderDetailList, attributes, response);
        } catch (Exception e) {
            throw new StockCheckOrderException("导出库存盘点单列表参数错误");
        }
    }

    /**
     * 提审盘点单
     * @param stockCheckOrderId 盘点单id
     * @return  返回值
     */
    @GetMapping(value = "/commit/{stockCheckOrderId}")
    @ResponseBody
    public HttpResponse commit(@PathVariable String stockCheckOrderId) {
        return stockCheckOrderService.commit(stockCheckOrderId);
    }
}

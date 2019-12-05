package com.hanyun.scm.api.web;


import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.StockPicking;
import com.hanyun.scm.api.domain.dto.CreatePurchaseStockInDTO;
import com.hanyun.scm.api.domain.dto.ModifyPurchaseStockInDTO;
import com.hanyun.scm.api.domain.dto.QueryPurchaseOrderDTO;
import com.hanyun.scm.api.domain.dto.QueryPurchaseStockInDTO;
import com.hanyun.scm.api.domain.request.purchase.PurchaseStockInConfirmRequest;
import com.hanyun.scm.api.domain.request.purchase.PurchaseStockInCreateRequest;
import com.hanyun.scm.api.domain.request.purchase.PurchaseStockInModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.PurchaseStockInQueryRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderQueryRequest;
import com.hanyun.scm.api.domain.request.stock.StockPickingRequest;
import com.hanyun.scm.api.exception.PurchaseStockInException;
import com.hanyun.scm.api.service.PurchaseOrderService;
import com.hanyun.scm.api.service.PurchaseStockInService;
import com.hanyun.scm.api.service.StockPickingService;
import com.hanyun.scm.api.web.util.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
 * PurchaseStockInController
 * Date: 2016/10/27
 * Time: 下午12:07
 *
 * @author tianye@hanyun.com
 */
@RestController
@RequestMapping(value = "/purchase-stock-in")
@Api(value = "/purchase-stock-in", description = "采购入库单相关api")
public class PurchaseStockInController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseStockInController.class);

    @Resource
    private PurchaseStockInService purchaseStockInService;

    @Resource
    private StockPickingService stockPickingService;

    @Resource
    private PurchaseOrderService purchaseOrderService;

    /**
     * 创建采购入库单
     *
     * @param createPurchaseStockInDTO 创建入库单参数
     * @return HttpResponse
     */
    @ApiOperation(value = "创建采购入库单")
    @PostMapping(value = "/create")
    @ResponseBody
    public HttpResponse create(@RequestBody @Valid @ApiParam(value = "创建采购入库单参数实体") CreatePurchaseStockInDTO createPurchaseStockInDTO, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("创建采购入库单参数错误!");
            return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_PARAM_ERROR);
        }
        return purchaseStockInService.create(new PurchaseStockInCreateRequest(createPurchaseStockInDTO));
    }

    /**
     * 查询采购入库单列表
     *
     * @param queryPurchaseStockInDTO 查询入库单参数
     * @return HttpResponse
     */
    @ApiOperation(value = "查询采购入库单列表")
    @GetMapping(value = "/query")
    @ResponseBody
    public HttpResponse select(@Valid @ModelAttribute QueryPurchaseStockInDTO queryPurchaseStockInDTO, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("查询采购入库单列表参数错误!");
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_PARAM_ERROR);
        }
        return purchaseStockInService.select(new PurchaseStockInQueryRequest(queryPurchaseStockInDTO));
    }

    /**
     * 修改采购入库单
     *
     * @param modifyPurchaseStockInDTO 修改入库单参数
     * @return HttpResponse
     */
    @ApiOperation(value = "修改采购入库单")
    @PutMapping(value = "/modify")
    @ResponseBody
    public HttpResponse modify(@RequestBody @Valid @ApiParam(value = "修改采购入库单参数实体") ModifyPurchaseStockInDTO modifyPurchaseStockInDTO, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("修改采购入库单参数错误!");
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_MODIFY_ERROR);
        }
        return purchaseStockInService.modify(new PurchaseStockInModifyRequest(modifyPurchaseStockInDTO));
    }

    /**
     * 删除采购入库单
     *
     * @param stockPickingId 入库单id
     * @return HttpResponse
     */
    @ApiOperation(value = "删除采购入库单")
    @DeleteMapping(value = "/delete/{stockPickingId}")
    @ResponseBody
    public HttpResponse delete(@PathVariable @ApiParam(value = "采购入库单id", required = true) String stockPickingId) {
        return purchaseStockInService.delete(stockPickingId);
    }

    /**
     * 查询采购入库单详情
     *
     * @param stockPickingId 入库单id
     * @return HttpResponse
     */
    @ApiOperation(value = "查看采购入库单详情")
    @GetMapping(value = "/detail/{stockPickingId}")
    @ResponseBody
    public HttpResponse detail(@PathVariable @ApiParam(value = "采购入库单id", required = true) String stockPickingId) {
        return purchaseStockInService.detail(stockPickingId);
    }

    /**
     * 查询采购订单列表
     *
     * @param queryPurchaseOrderDTO 查询采购订单参数
     * @return HttpResponse
     */
    @ApiOperation(value = "查询采购订单列表")
    @GetMapping(value = "/order/query")
    @ResponseBody
    public HttpResponse selectPurchaseOrder(@Valid @ModelAttribute QueryPurchaseOrderDTO queryPurchaseOrderDTO, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("查询采购订单(for 采购入库)参数错误!");
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_PARAM_ERROR);
        }
        return purchaseOrderService.selectPurchaseOrderForStockInAndReturn(new PurchaseOrderQueryRequest(queryPurchaseOrderDTO));
    }

    /**
     * 查询采购订单详情
     *
     * @param purchaseOrderId 采购订单id
     * @return HttpResponse
     */
    @ApiOperation(value = "查询采购订单详情")
    @GetMapping(value = "/order/detail/{purchaseOrderId}")
    @ResponseBody
    public HttpResponse detailPurchaseOrder(@PathVariable @ApiParam(value = "查询采购订单详情", required = true) String purchaseOrderId) {
        return purchaseStockInService.detailPurchaseOrder(purchaseOrderId);
    }

    /**
     * 审核采购入库单
     *
     * @param purchaseStockInConfirmRequest 审核采购入库单参数
     * @return HttpResponse
     */
    @ApiOperation(value = "审核采购入库单")
    @PutMapping(value = "/confirm")
    @ResponseBody
    public HttpResponse confirm(@Valid @RequestBody @ApiParam(value = "审核采购入库单参数实体") PurchaseStockInConfirmRequest purchaseStockInConfirmRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.PURCHASE_STOCK_IN_PARAM_ERROR);
        }
        return purchaseStockInService.confirm(purchaseStockInConfirmRequest);
    }

    /**
     * 查询采购移库信息
     *
     * @param stockPickingRequest 查询移库(出库和入库)信息参数
     * @return HttpResponse
     */
    @ApiOperation(value = "查询采购出入库信息")
    @GetMapping(value = "/picking-num")
    @ResponseBody
    public HttpResponse stockPickingForReturn(@ModelAttribute StockPickingRequest stockPickingRequest) {
        return stockPickingService.stockPickingForPurchase(stockPickingRequest);
    }

    /**
     * 导出采购入库单详情
     *
     * @param stockPickingId 入库单id
     * @param response       HttpServletResponse
     */
    @ApiOperation(value = "导出采购入库单详情")
    @GetMapping(value = "/export/{stockPickingId}")
    public void purchaseStockInExport(@PathVariable @ApiParam(value = "采购入库单id", required = true) String stockPickingId, HttpServletResponse response) throws PurchaseStockInException {
        try {
            StockPicking stockPicking = stockPickingService.selectByStockPickingId(stockPickingId);
            String xlsName = stockPicking.getStockPickingDocumentId();
            String templateName = "purchase_stock_in_detail.xls";
            String[] attributes = new String[]{"goodsCode", "goodsBarCode", "goodsName", "features", "unitName", "purchaseAmount", "pickingPrice",
                    "pickingAmount", "totalPrice", "stockInAmount", "presentAmount", "totalPresentPrice", "remark"};
            ExcelUtil.export(xlsName, templateName, stockPicking.getStockPickingGoodsList(), attributes, response);
        } catch (Exception e) {
            LOGGER.error("导出采购入库单--查询采购入库单失败!");
            throw new PurchaseStockInException("导出采购入库单--查询采购入库单失败!", e);
        }

    }

    /**
     * 提审采购入库单
     *
     * @param stockPickingId 提审参数id
     * @return HttpResponse
     */
    @ApiOperation(value = "提审采购入库单")
    @GetMapping(value = "/commit/{stockPickingId}")
    @ResponseBody
    public HttpResponse commit(@NotEmpty @PathVariable @ApiParam(value = "采购入库单号", required = true) String stockPickingId) {
        return purchaseStockInService.commit(stockPickingId);
    }

    /**
     * 导出采购入库单列表
     *
     * @param queryPurchaseStockInDTO 采购入库单查询参数
     * @throws Exception 异常
     */
    @ApiOperation(value = "导出采购入库单列表")
    @GetMapping(value = "/export")
    public void exportPurchaseStockInList(@Valid @ModelAttribute QueryPurchaseStockInDTO queryPurchaseStockInDTO, BindingResult result, HttpServletResponse response) throws PurchaseStockInException {
        if (result.hasErrors()) {
            LOGGER.error("导出采购入库单列表参数错误!");
            throw new PurchaseStockInException("导出采购入库单列表参数错误!");
        }
        try {
            List purchaseStockInList = purchaseStockInService.selectList(new PurchaseStockInQueryRequest(queryPurchaseStockInDTO), true).getList();
            String xlsName = "采购入库单列表";
            String templateName = "purchase_stock_in_list.xls";
            String[] attributes = new String[]{"stockPickingDocumentId", "srcOrderDocumentId",
                    "purchaseAmount", "updateTime", "supplierName", "pickingAmount", "stockPickingPrice", "businessManName", "operatorName", "createTime",
                    "toWarehouseName", "stockPickingStatusName", "paymentStatusName"};
            ExcelUtil.export(xlsName, templateName, purchaseStockInList, attributes, response);
        } catch (Exception e) {
            LOGGER.error("导出采购入库单列表失败");
            throw new PurchaseStockInException("导出采购入库单列表参数错误");
        }
    }

    /**
     * 查询采购订单列表
     *
     * @param purchaseOrderQueryRequest 查询采购订单参数
     * @return HttpResponse
     */
    @ApiOperation(value = "查询采购订单列表")
    @PostMapping(value = "/order/queryForStockIn")
    @ResponseBody
    public HttpResponse queryForStockIn(@RequestBody @Valid @ApiParam(value = "查询采购订单列表") PurchaseOrderQueryRequest purchaseOrderQueryRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("查询采购订单(for 采购入库)参数错误!");
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_PARAM_ERROR);
        }
        return purchaseOrderService.queryForStockIn(purchaseOrderQueryRequest);
    }

}

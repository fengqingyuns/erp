package com.hanyun.scm.api.web;


import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.StockPicking;
import com.hanyun.scm.api.domain.request.purchase.PurchaseReturnConfirmRequest;
import com.hanyun.scm.api.domain.request.purchase.PurchaseReturnCreateRequest;
import com.hanyun.scm.api.domain.request.purchase.PurchaseReturnModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.PurchaseReturnQueryRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderQueryRequest;
import com.hanyun.scm.api.domain.request.stock.StockPickingRequest;
import com.hanyun.scm.api.exception.PurchaseReturnException;
import com.hanyun.scm.api.service.PurchaseOrderService;
import com.hanyun.scm.api.service.PurchaseReturnService;
import com.hanyun.scm.api.service.StockPickingService;
import com.hanyun.scm.api.web.util.ExcelUtil;
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
 * PurchaseReturnController
 * Date: 2016/10/27
 * Time: 下午12:07
 *
 * @author tianye@hanyun.com
 */
@Controller
@RequestMapping(value = "/purchase-return")
public class PurchaseReturnController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseReturnController.class);

    @Resource
    private PurchaseReturnService purchaseReturnService;

    @Resource
    private StockPickingService stockPickingService;

    @Resource
    private PurchaseOrderService purchaseOrderService;

    /**
     * 创建采购退货单
     *
     * @param purchaseReturnCreateRequest 创建退货单参数
     * @return HttpResponse
     */
    @PostMapping(value = "/create")
    @ResponseBody
    public HttpResponse create(@RequestBody @Valid PurchaseReturnCreateRequest purchaseReturnCreateRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("创建采购退货单参数错误!");
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_CREATE_PARAM_ERROR);
        }
        return purchaseReturnService.create(purchaseReturnCreateRequest);
    }

    /**
     * 查询采购退货单列表
     *
     * @param purchaseReturnQueryRequest 查询退货单参数
     * @return HttpResponse
     */
    @GetMapping(value = "/query")
    @ResponseBody
    public HttpResponse select(@Valid PurchaseReturnQueryRequest purchaseReturnQueryRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("查询采购退货单列表参数错误!");
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_PARAM_ERROR);
        }
        return purchaseReturnService.select(purchaseReturnQueryRequest);
    }

    /**
     * 修改采购退货单
     *
     * @param purchaseReturnModifyRequest 修改退货单参数
     * @return HttpResponse
     */
    @PutMapping(value = "/modify")
    @ResponseBody
    public HttpResponse modify(@RequestBody @Valid PurchaseReturnModifyRequest purchaseReturnModifyRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("修改采购退货单参数错误!");
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_MODIFY_ERROR);
        }
        return purchaseReturnService.modify(purchaseReturnModifyRequest);
    }

    /**
     * 删除采购退货单
     *
     * @param stockPickingId 退货单id
     * @return HttpResponse
     */
    @DeleteMapping(value = "/delete/{stockPickingId}")
    @ResponseBody
    public HttpResponse delete(@PathVariable String stockPickingId) {
        return purchaseReturnService.delete(stockPickingId);
    }

    /**
     * 查询采购退货单详情
     *
     * @param stockPickingId 退货单id
     * @return HttpResponse
     */
    @GetMapping(value = "/detail/{stockPickingId}")
    @ResponseBody
    public HttpResponse detail(@PathVariable String stockPickingId) {
        return purchaseReturnService.detail(stockPickingId);
    }

    /**
     * 查询采购订单列表
     *
     * @param purchaseOrderQueryRequest 查询采购订单参数
     * @return HttpResponse
     */
    @GetMapping(value = "/order/query")
    @ResponseBody
    public HttpResponse selectPurchaseOrder(@Valid PurchaseOrderQueryRequest purchaseOrderQueryRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("查询采购订单(for 采购退货)参数错误!");
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_PARAM_ERROR);
        }
        return purchaseOrderService.selectPurchaseOrderForStockInAndReturn(purchaseOrderQueryRequest);
    }

    /**
     * 查询采购订单详情
     *
     * @param purchaseOrderId 采购订单id
     * @return HttpResponse
     */
    @GetMapping(value = "/order/detail/{purchaseOrderId}")
    @ResponseBody
    public HttpResponse detailPurchaseOrder(@PathVariable String purchaseOrderId) {
        return purchaseReturnService.detailPurchaseOrder(purchaseOrderId);
    }

    /**
     * 确认采购退货单
     *
     * @param purchaseReturnConfirmRequest 确认退货单参数
     * @return HttpResponse
     */
    @PutMapping(value = "/confirm")
    @ResponseBody
    public HttpResponse confirm(@Valid @RequestBody PurchaseReturnConfirmRequest purchaseReturnConfirmRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_PARAM_ERROR);
        }
        return purchaseReturnService.confirm(purchaseReturnConfirmRequest);
    }

    /**
     * 查询采购移库信息
     *
     * @param stockPickingRequest 查询移库(出库和入库)信息参数
     * @return HttpResponse
     */
    @GetMapping(value = "/picking-num")
    @ResponseBody
    public HttpResponse stockPickingForReturn(StockPickingRequest stockPickingRequest) {
        return stockPickingService.stockPickingForPurchase(stockPickingRequest);
    }

    /**
     * 提审采购退货单
     *
     * @param stockPickingId 提审参数id
     * @return HttpResponse
     */
    @GetMapping(value = "/commit/{stockPickingId}")
    @ResponseBody
    public HttpResponse commit(@NotEmpty @PathVariable String stockPickingId) {
        return purchaseReturnService.commit(stockPickingId);
    }

    /**
     * 导出采购退货单
     *
     * @param stockPickingId 退货单id
     * @param response       HttpServletResponse
     */
    @GetMapping(value = "/export/{stockPickingId}")
    public void purchaseReturnExport(@PathVariable String stockPickingId, HttpServletResponse response) throws PurchaseReturnException {
        try {
            StockPicking stockPicking = stockPickingService.selectByStockPickingId(stockPickingId);
            String xlsName = stockPicking.getStockPickingDocumentId();
            String templateName = "purchase_return_detail.xls";
            String[] attributes = new String[]{"goodsCode", "goodsBarCode", "goodsName", "features", "unitName", "pickingPrice",
                    "pickingAmount", "totalPrice", "remark"};
            ExcelUtil.export(xlsName, templateName, stockPicking.getStockPickingGoodsList(), attributes, response);
        } catch (Exception e) {
            LOGGER.error("导出采购退货单失败!");
            throw new PurchaseReturnException("导出采购退货单失败!", e);
        }
    }

    /**
     * 导出采购退货单列表
     *
     * @param purchaseReturnQueryRequest 采购退货单查询参数
     * @throws Exception 异常
     */
    @GetMapping(value = "/export")
    public void exportPurchaseReturnList(@Valid PurchaseReturnQueryRequest purchaseReturnQueryRequest, BindingResult result, HttpServletResponse response) throws PurchaseReturnException {
        if (result.hasErrors()) {
            LOGGER.error("导出采购退货单列表参数错误!");
            throw new PurchaseReturnException("导出采购退货单列表参数错误!");
        }
        try {
            List purchaseReturnList = purchaseReturnService.selectList(purchaseReturnQueryRequest, true).getList();
            String xlsName = "采购退货单列表";
            String templateName = "purchase_return_list.xls";
            String[] attributes = new String[]{"stockPickingDocumentId", "updateTime", "supplierName",
                    "pickingAmount", "stockPickingPrice", "businessManName", "operatorName", "createTime",
                    "srcWarehouseName", "stockPickingStatusName", "paymentStatusName"};
            ExcelUtil.export(xlsName, templateName, purchaseReturnList, attributes, response);
        } catch (Exception e) {
            throw new PurchaseReturnException("导出采购退货单列表参数错误");
        }
    }
}

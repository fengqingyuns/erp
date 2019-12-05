package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.PurchaseOrder;
import com.hanyun.scm.api.domain.PurchaseOrderDetail;
import com.hanyun.scm.api.domain.dto.CreatePurchaseOrderDTO;
import com.hanyun.scm.api.domain.dto.ModifyPurchaseOrderDTO;
import com.hanyun.scm.api.domain.dto.QueryPurchaseOrderDTO;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderConfirmRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderCreateRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderQueryRequest;
import com.hanyun.scm.api.exception.PurchaseOrderException;
import com.hanyun.scm.api.service.PurchaseOrderService;
import com.hanyun.scm.api.web.util.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RestController
@RequestMapping(value = "/purchase-order")
@Api(value = "/purchase-order", description = "采购订单相关api")
public class PurchaseOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderController.class);

    @Resource
    private PurchaseOrderService purchaseOrderService;

    /**
     * 查询采购订单
     *
     * @param queryPurchaseOrderDTO 查询参数
     * @return 返回信息
     */
    @ApiOperation(value = "查询采购订单列表")
    @GetMapping(value = "/query")
    @ResponseBody
    public HttpResponse select(@Valid @ModelAttribute QueryPurchaseOrderDTO queryPurchaseOrderDTO) {
        PurchaseOrderQueryRequest purchaseOrderQueryRequest = new PurchaseOrderQueryRequest(queryPurchaseOrderDTO);
        return purchaseOrderService.select(purchaseOrderQueryRequest);
    }

    /**
     * 查询采购对应入库单列表
     *
     * @param orderId 查询参数
     * @return 返回信息
     */
    @ApiOperation(value = "查询采购关联入库单列表")
    @GetMapping(value = "/purchase-stock-in/{orderId}")
    @ResponseBody
    public HttpResponse select(@PathVariable @ApiParam(value = "采购订单id", required = true) String orderId) {

        return purchaseOrderService.queryStockInList(orderId);
    }

    /**
     * 查询采购单详情
     *
     * @param orderId 订单号
     * @return 返回值
     */
    @ApiOperation(value = "查询采购订单详情")
    @GetMapping(value = "/detail/{orderId}")
    @ResponseBody
    public HttpResponse detail(@PathVariable @ApiParam(value = "订单id", required = true) String orderId) {
        return purchaseOrderService.detail(orderId);
    }

    /**
     * 创建采购订单
     *
     * @param createPurchaseOrderDTO 创建参数
     * @return 返回值
     */
    @ApiOperation(value = "创建采购订单")
    @PostMapping(value = "/create")
    @ResponseBody
    public HttpResponse create(@RequestBody @Valid @ApiParam(value = "创建采购订单参数实体", required = true) CreatePurchaseOrderDTO createPurchaseOrderDTO) {
        PurchaseOrderCreateRequest purchaseOrderCreateRequest = new PurchaseOrderCreateRequest(createPurchaseOrderDTO);
        return purchaseOrderService.create(purchaseOrderCreateRequest);
    }

    /**
     * 修改采购订单
     *
     * @param modifyPurchaseOrderDTO 修改参数
     * @return 返回信息
     */
    @ApiOperation(value = "更新采购订单")
    @PutMapping(value = "/modify")
    @ResponseBody
    public HttpResponse modify(@RequestBody @Valid @ApiParam(value = "更新采购订单参数实体", required = true) ModifyPurchaseOrderDTO modifyPurchaseOrderDTO) {
        PurchaseOrderModifyRequest purchaseOrderModifyRequest = new PurchaseOrderModifyRequest(modifyPurchaseOrderDTO);
        return purchaseOrderService.modify(purchaseOrderModifyRequest);
    }

    /**
     * 删除采购订单
     *
     * @param orderId 订单id
     * @return 返回值
     */
    @ApiOperation(value = "删除采购订单")
    @DeleteMapping(value = "/delete/{orderId}")
    @ResponseBody
    public HttpResponse delete(@PathVariable @ApiParam(value = "订单id", required = true) String orderId) {
        return purchaseOrderService.delete(orderId);
    }

    /**
     * 审核采购订单
     *
     * @param purchaseOrderConfirmRequest 审核参数
     * @return 返回信息
     */
    @ApiOperation(value = "审核采购订单")
    @PutMapping(value = "/confirm")
    @ResponseBody
    public HttpResponse confirm(@RequestBody @Valid @ApiParam(value = "审核采购订单参数实体", required = true) PurchaseOrderConfirmRequest purchaseOrderConfirmRequest) {
        return purchaseOrderService.confirm(purchaseOrderConfirmRequest);
    }

    /**
     * 导出采购订单列表
     *
     * @param queryPurchaseOrderDTO 采购订单查询参数
     * @throws PurchaseOrderException 异常
     */
    @ApiOperation(value = "导出采购订单列表(需在浏览器地址栏访问)")
    @GetMapping(value = "/export")
    public void exportPurchaseOrderList(@Valid @ModelAttribute QueryPurchaseOrderDTO queryPurchaseOrderDTO, BindingResult result, HttpServletResponse response) throws PurchaseOrderException {
        if (result.hasErrors()) {
            LOGGER.error("导出采购订单列表参数错误!");
            throw new PurchaseOrderException("导出采购入库单列表参数错误!");
        }
        PurchaseOrderQueryRequest purchaseOrderQueryRequest = new PurchaseOrderQueryRequest(queryPurchaseOrderDTO);
        try {
            List purchaseStockInList = purchaseOrderService.selectList(purchaseOrderQueryRequest, true).getList();
            String xlsName = "采购订单列表";
            String templateName = "purchase_order_list.xls";
            String[] attributes = new String[]{"orderDocumentId", "planTime", "supplierName",
                    "toWarehouseName", "totalAmount", "totalPrice","completeStockInAmount" ,"notInStockNum" ,"purchaseUserName", "operatorName",
                    "createTime", "orderStatusName"};
            ExcelUtil.export(xlsName, templateName, purchaseStockInList, attributes, response);
        } catch (Exception e) {
            LOGGER.error("导出采购入库单列表失败",e);
            throw new PurchaseOrderException("导出采购入库单列表参数错误");
        }
    }

    /**
     * 导出采购订单明细列表
     *
     * @param orderId  采购订单id
     * @param response 响应
     * @throws PurchaseOrderException 异常信息
     */
    @ApiOperation(value = "导出采购订单商品列表(需在浏览器地址栏访问)")
    @GetMapping(value = "export/{orderId}")
    public void exportPurchaseOrder(@PathVariable @ApiParam(value = "订单id", required = true) String orderId, HttpServletResponse response) throws PurchaseOrderException {
        try {
            PurchaseOrder purchaseOrder = purchaseOrderService.getOrder(orderId);
            List<PurchaseOrderDetail> purchaseOrderDetailList = purchaseOrder.getPurchaseOrderDetailList();
            String xlsName = purchaseOrder.getOrderDocumentId();
            String templateName = "purchase_order_detail.xls";
            String[] attributes = new String[]{"goodsCode", "goodsBarCode", "goodsName", "classifyName", "features",
                    "unitName", "unitPrice", "purchaseAmount", "totalPrice","completeStockInAmount","unStockInAmount" ,"remark"};
            ExcelUtil.export(xlsName, templateName, purchaseOrderDetailList, attributes, response);
        } catch (Exception e) {
            LOGGER.error("导出采购订单明细失败",e);
            throw new PurchaseOrderException("导出采购订单明细失败!", e);
        }
    }
    /**
     * 查询采购订单
     *
     * @param orderId 查询参数
     * @return 返回信息
     */
    @ApiOperation("查询采购订单for采购入库")
    @GetMapping(value = "/queryDetailForStockIn/{orderId}")
    @ResponseBody
    public HttpResponse selectDetailForStockIn(@PathVariable @ApiParam(value = "orderId", required = true) String orderId) {
        return purchaseOrderService.selectDetailForStockIn(orderId);
    }

    /**
     * 提交审核
     * @param orderId   订单id
     * @return  返回值
     */
    @ApiOperation(value = "提交审核")
    @GetMapping(value = "/commit/{orderId}")
    @ResponseBody
    public HttpResponse commit(@PathVariable @ApiParam(value = "订单id", required = true) String orderId) {
        return purchaseOrderService.commit(orderId);
    }

    /**
     * 关闭采购订单状态
     * @param orderId   订单id
     * @return  返回值
     */
    @ApiOperation(value = "终止采购订单")
    @GetMapping(value = "/close/{orderId}")
    @ResponseBody
    public HttpResponse close(@PathVariable @ApiParam(value = "订单id", required = true) String orderId) {
        return purchaseOrderService.close(orderId);
    }
}

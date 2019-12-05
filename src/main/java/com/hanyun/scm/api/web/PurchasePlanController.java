package com.hanyun.scm.api.web;


import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.PurchasePlan;
import com.hanyun.scm.api.domain.PurchasePlanDetail;
import com.hanyun.scm.api.domain.request.purchase.plan.PurchasePlanConfirmRequest;
import com.hanyun.scm.api.domain.request.purchase.plan.PurchasePlanCreateRequest;
import com.hanyun.scm.api.domain.request.purchase.plan.PurchasePlanModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.plan.PurchasePlanQueryRequest;
import com.hanyun.scm.api.exception.PurchasePlanException;
import com.hanyun.scm.api.service.PurchasePlanService;
import com.hanyun.scm.api.web.util.ExcelUtil;
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
 * PurchasePlanController
 * Date: 2016/10/27
 * Time: 下午12:07
 *
 * @author tianye@hanyun.com
 */
@Controller
@RequestMapping(value = "/purchase-plan")
public class PurchasePlanController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchasePlanController.class);

    @Resource
    private PurchasePlanService purchasePlanService;

    /**
     * 创建采购计划单
     *
     * @param purchasePlanCreateRequest 创建计划单参数
     * @return HttpResponse
     */
    @PostMapping(value = "/create")
    @ResponseBody
    public HttpResponse create(@RequestBody @Valid PurchasePlanCreateRequest purchasePlanCreateRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("创建采购计划单参数错误!");
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_CREATE_PARAM_ERROR);
        }
        return purchasePlanService.create(purchasePlanCreateRequest);
    }

    /**
     * 查询采购计划单列表
     *
     * @param purchasePlanQueryRequest 查询计划单参数
     * @return HttpResponse
     */
    @GetMapping(value = "/query")
    @ResponseBody
    public HttpResponse select(@Valid PurchasePlanQueryRequest purchasePlanQueryRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("查询采购计划单列表参数错误!");
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_PARAM_ERROR);
        }
        return purchasePlanService.select(purchasePlanQueryRequest);
    }

    /**
     * 修改采购计划单
     *
     * @param purchasePlanModifyRequest 修改计划单参数
     * @return HttpResponse
     */
    @PutMapping(value = "/modify")
    @ResponseBody
    public HttpResponse modify(@RequestBody @Valid PurchasePlanModifyRequest purchasePlanModifyRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("修改采购计划单参数错误!");
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_MODIFY_ERROR);
        }
        return purchasePlanService.modify(purchasePlanModifyRequest);
    }

    /**
     * 删除采购计划单
     *
     * @param stockPickingId 计划单id
     * @return HttpResponse
     */
    @DeleteMapping(value = "/delete/{stockPickingId}")
    @ResponseBody
    public HttpResponse delete(@PathVariable String stockPickingId) {
        return purchasePlanService.delete(stockPickingId);
    }

    /**
     * 查询采购计划单详情
     *
     * @param stockPickingId 计划单id
     * @return HttpResponse
     */
    @GetMapping(value = "/detail/{stockPickingId}")
    @ResponseBody
    public HttpResponse detail(@PathVariable String stockPickingId) {
        return purchasePlanService.detail(stockPickingId);
    }

    /**
     * 确认采购计划单
     *
     * @param purchasePlanConfirmRequest 确认计划单参数
     * @return HttpResponse
     */
    @PutMapping(value = "/confirm")
    @ResponseBody
    public HttpResponse confirm(@Valid @RequestBody PurchasePlanConfirmRequest purchasePlanConfirmRequest) {
        return purchasePlanService.confirm(purchasePlanConfirmRequest);
    }

    /**
     * 导出采购计划单列表
     *
     * @param purchasePlanQueryRequest 采购计划单查询参数
     * @throws PurchasePlanException 异常
     */
    @GetMapping(value = "/export")
    public void exportPurchasePlanList(@Valid PurchasePlanQueryRequest purchasePlanQueryRequest, BindingResult result, HttpServletResponse response) throws PurchasePlanException {
        if (result.hasErrors()) {
            LOGGER.error("导出采购计划单列表参数错误!");
            throw new RuntimeException("导出采购计划单列表参数错误!");
        }
        try {
            List purchasePlanList = purchasePlanService.selectList(purchasePlanQueryRequest, true).getList();
            String xlsName = "采购计划单列表";
            String templateName = "purchase_plan_list.xls";
            String[] attributes = new String[]{"planDocumentId", "updateTime", "operatorName",
                    "createTime", "planStatusName"};
            ExcelUtil.export(xlsName, templateName, purchasePlanList, attributes, response);
        } catch (Exception e) {
            throw new PurchasePlanException("导出采购计划单列表参数错误");
        }
    }

    /**
     * 导出计划单明细列表
     *
     * @param planId   计划单id
     * @param response 响应
     * @throws PurchasePlanException 异常信息
     */
    @GetMapping(value = "export/{planId}")
    public void exportPurchasePlan(@PathVariable String planId, HttpServletResponse response) throws PurchasePlanException {
        try {
            PurchasePlan purchasePlan = purchasePlanService.getPlan(planId);
            List<PurchasePlanDetail> purchasePlanDetailList = purchasePlan.getPurchasePlanDetailList();
            String xlsName = purchasePlan.getPlanDocumentId();
            String templateName = "purchase_plan_detail.xls";
            String[] attributes = new String[]{"goodsCode", "goodsBarCode", "goodsName", "features", "unitName",
                    "supplierName", "purchasePrice", "purchaseAmount", "totalPurchasePrice", "remark"};
            ExcelUtil.export(xlsName, templateName, purchasePlanDetailList, attributes, response);
        } catch (Exception e) {
            throw new PurchasePlanException("导出计划单明细失败!", e);
        }
    }

    /**
     * 采购计划提审
     * @param planId    计划单号
     * @return  返回值
     */
    @GetMapping(value = "/commit/{planId}")
    @ResponseBody
    public HttpResponse commit(@PathVariable String planId) {
        return purchasePlanService.commit(planId);
    }
}

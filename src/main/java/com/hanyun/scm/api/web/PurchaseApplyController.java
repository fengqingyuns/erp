package com.hanyun.scm.api.web;


import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.PurchaseApply;
import com.hanyun.scm.api.domain.PurchaseApplyGoods;
import com.hanyun.scm.api.domain.request.purchase.apply.PurchaseApplyConfirmRequest;
import com.hanyun.scm.api.domain.request.purchase.apply.PurchaseApplyCreateRequest;
import com.hanyun.scm.api.domain.request.purchase.apply.PurchaseApplyModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.apply.PurchaseApplyQueryRequest;
import com.hanyun.scm.api.exception.PurchaseApplyException;
import com.hanyun.scm.api.service.PurchaseApplyService;
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
 * PurchaseReturnController
 * Date: 2016/10/27
 * Time: 下午12:07
 *
 * @author tianye@hanyun.com
 */
@Controller
@RequestMapping(value = "/purchase-apply")
public class PurchaseApplyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseApplyController.class);

    @Resource
    private PurchaseApplyService purchaseApplyService;

    /**
     * 创建采购退货单
     *
     * @param purchaseApplyCreateRequest 创建退货单参数
     * @return HttpResponse
     */
    @PostMapping(value = "/create")
    @ResponseBody
    public HttpResponse create(@RequestBody @Valid PurchaseApplyCreateRequest purchaseApplyCreateRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("创建采购退货单参数错误!");
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_CREATE_PARAM_ERROR);
        }
        return purchaseApplyService.create(purchaseApplyCreateRequest);
    }

    /**
     * 查询采购退货单列表
     *
     * @param purchaseApplyQueryRequest 查询退货单参数
     * @return HttpResponse
     */
    @GetMapping(value = "/query")
    @ResponseBody
    public HttpResponse select(@Valid PurchaseApplyQueryRequest purchaseApplyQueryRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("查询采购退货单列表参数错误!");
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_PARAM_ERROR);
        }
        return purchaseApplyService.select(purchaseApplyQueryRequest);
    }

    /**
     * 修改采购退货单
     *
     * @param purchaseApplyModifyRequest 修改退货单参数
     * @return HttpResponse
     */
    @PutMapping(value = "/modify")
    @ResponseBody
    public HttpResponse modify(@RequestBody @Valid PurchaseApplyModifyRequest purchaseApplyModifyRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("修改采购退货单参数错误!");
            return HttpResponse.failure(ResultCode.PURCHASE_RETURN_MODIFY_ERROR);
        }
        return purchaseApplyService.modify(purchaseApplyModifyRequest);
    }

    /**
     * 删除采购退货单
     *
     * @param applyId 退货单id
     * @return HttpResponse
     */
    @DeleteMapping(value = "/delete/{applyId}")
    @ResponseBody
    public HttpResponse delete(@PathVariable String applyId) {
        return purchaseApplyService.delete(applyId);
    }

    /**
     * 查询采购退货单详情
     *
     * @param applyId 退货单id
     * @return HttpResponse
     */
    @GetMapping(value = "/detail/{applyId}")
    @ResponseBody
    public HttpResponse detail(@PathVariable String applyId) {
        return purchaseApplyService.detail(applyId);
    }

    /**
     * 确认采购退货单
     *
     * @param purchaseApplyConfirmRequest 确认退货单参数
     * @return HttpResponse
     */
    @PutMapping(value = "/confirm")
    @ResponseBody
    public HttpResponse confirm(@Valid @RequestBody PurchaseApplyConfirmRequest purchaseApplyConfirmRequest) {
        return purchaseApplyService.confirm(purchaseApplyConfirmRequest);
    }

    /**
     * 导出采购退货单列表
     *
     * @param purchaseApplyQueryRequest 采购退货单查询参数
     * @throws PurchaseApplyException 异常
     */
    @GetMapping(value = "/export")
    public void exportPurchaseApplyList(@Valid PurchaseApplyQueryRequest purchaseApplyQueryRequest, BindingResult result, HttpServletResponse response) throws PurchaseApplyException {
        if (result.hasErrors()) {
            LOGGER.error("导出采购退货单列表参数错误!");
            throw new PurchaseApplyException("导出采购退货单列表参数错误!");
        }
        try {
            List purchaseApplyList = purchaseApplyService.selectList(purchaseApplyQueryRequest, true).getList();
            String xlsName = "采购申请单列表";
            String templateName = "purchase_apply_list.xls";
            String[] attributes = new String[]{"applyDocumentId", "updateTime", "exportpurchaseType", "storeName", "operatorName", "createTime", "applyStatusName"};
            ExcelUtil.export(xlsName, templateName, purchaseApplyList, attributes, response);
        } catch (Exception e) {
            throw new PurchaseApplyException("导出采购退货单列表参数错误");
        }
    }

    /**
     * 导出申请单明细列表
     * @param applyId   申请单id
     * @param response  响应
     * @throws PurchaseApplyException    异常信息
     */
    @GetMapping(value = "export/{applyId}")
    public void exportPurchaseApply(@PathVariable String applyId, HttpServletResponse response) throws PurchaseApplyException{
        try {
            PurchaseApply purchaseApply = purchaseApplyService.getApply(applyId);
            List<PurchaseApplyGoods> purchaseApplyGoodsList = purchaseApply.getPurchaseApplyGoodsList();
            String xlsName = purchaseApply.getApplyDocumentId();
            String templateName = "purchase_apply_detail.xls";
            String[] attributes = new String[] {"goodsCode", "goodsBarCode", "goodsName", "classifyName",
                    "features", "unitName", "applyAmount", "remark"};
                    ExcelUtil.export(xlsName, templateName, purchaseApplyGoodsList, attributes, response);
        } catch (Exception e) {
            throw new PurchaseApplyException("导出申请单明细失败!", e);
        }
    }

    /**
     * 采购申请单提审
     * @param applyId   申请单id
     */
    @GetMapping(value = "/commit/{applyId}")
    @ResponseBody
    public HttpResponse commit(@PathVariable String applyId) {
        return purchaseApplyService.commit(applyId);
    }
}

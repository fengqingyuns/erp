package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.request.payment.PurchasePaymentRequest;
import com.hanyun.scm.api.domain.request.payment.PurchaseQueryRequest;
import com.hanyun.scm.api.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
 * PaymentController
 * Date: 2017/3/10
 * Time: 下午3:22
 *
 * @author tianye@hanyun.com
 */
@RequestMapping("/payment")
@Controller
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    /**
     * 查询采购入库单和退货单
     * @param purchaseQueryRequest  参数
     * @param bindingResult valid信息
     * @return  httpResponse
     */
    @GetMapping("/order")
    @ResponseBody
    public HttpResponse queryOrder(@Valid PurchaseQueryRequest purchaseQueryRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.PAYMENT_PARAM_ERROR);
        }
        return paymentService.queryOrder(purchaseQueryRequest);
    }

    /**
     * 采购收付款
     * @param purchasePaymentRequestList    参数
     * @param bindingResult valid信息
     * @return  httpResponse
     */
    @PutMapping("")
    @ResponseBody
    public HttpResponse payment(@Valid @RequestBody List<PurchasePaymentRequest> purchasePaymentRequestList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.PAYMENT_PARAM_ERROR);
        }
        return paymentService.payment(purchasePaymentRequestList);
    }

    /**
     * 统计采购信息
     * @param purchaseQueryRequest 参数
     * @param bindingResult 验证信息
     * @return  返回值
     */
    @GetMapping(value = "/statistics")
    @ResponseBody
    public HttpResponse statictics(PurchaseQueryRequest purchaseQueryRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.PAYMENT_PARAM_ERROR);
        }
        return paymentService.statistics(purchaseQueryRequest);
    }
}

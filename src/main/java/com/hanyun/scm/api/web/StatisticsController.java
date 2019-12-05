package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.request.BrandStoreRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderStatisticsRequest;
import com.hanyun.scm.api.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;

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
 * StatisticsController
 * Date: 2017/2/21
 * Time: 下午5:24
 *
 * @author tianye@hanyun.com
 */
@Controller
@RequestMapping(value = "/statistics")
public class StatisticsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsController.class);

    @Resource
    private StatisticsService statisticsService;

    /**
     * 获取统计信息
     * @param brandStoreRequest 请求参数
     * @param bindingResult 参数验证
     * @return  返回信息
     */
    @GetMapping(value = "")
    @ResponseBody
    public HttpResponse statistics(@Valid BrandStoreRequest brandStoreRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOGGER.error("获取统计信息参数错误!");
            return HttpResponse.failure(ResultCode.STATISTICS_PARAM_ERROR);
        }
        return statisticsService.statistics(brandStoreRequest);
    }

    @GetMapping(value = "/purchase-order")
    @ResponseBody
    public HttpResponse purchaseStatistics(@Valid PurchaseOrderStatisticsRequest purchaseOrderStatisticsRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOGGER.error("获取采购统计信息参数错误");
            return HttpResponse.failure(ResultCode.STATISTICS_PARAM_ERROR);
        }
        return statisticsService.purchaseStatistics(purchaseOrderStatisticsRequest);
    }
}

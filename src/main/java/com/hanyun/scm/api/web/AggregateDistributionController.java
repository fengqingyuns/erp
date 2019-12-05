package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.request.Replenishment.ReplenishmentApplyRequest;
import com.hanyun.scm.api.domain.request.aggregate.AggregateDistributionRequest;
import com.hanyun.scm.api.domain.request.aggregate.AggregateResultRequest;
import com.hanyun.scm.api.domain.request.aggregate.AggregateStatisticsRequest;
import com.hanyun.scm.api.service.AggregateDistributionService;
import com.hanyun.scm.api.service.ReplenishmentApplyService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
 * AggregateDistributionController
 * Date: 2017/3/14
 * Time: 下午2:10
 *
 * @author tianye@hanyun.com
 */
@Controller
@RequestMapping(value = "/aggregate-distribution")
public class AggregateDistributionController {

    @Resource
    private ReplenishmentApplyService replenishmentApplyService;

    @Resource
    private AggregateDistributionService aggregateDistributionService;


    /**
     * 查询补货申请单for配送
     *
     * @param storeIds  补货门店id集合
     * @param replenishmentApplyRequest 请求参数
     */
    @PostMapping(value = "/query-apply")
    @ResponseBody
    public HttpResponse selectForDistribution(@NotNull @RequestBody String[] storeIds, @Valid ReplenishmentApplyRequest replenishmentApplyRequest, BindingResult result) {
        if(result.hasErrors()){
            return HttpResponse.failure(ResultCode.AGGREGATE_DISTRIBUTION_PARAM_ERROR);
        }
        replenishmentApplyRequest.setStoreIds(storeIds);
        replenishmentApplyRequest.setDistributeStatus(0);
        return replenishmentApplyService.select(replenishmentApplyRequest);
    }

    /**
     * 查询补货申请单详情for配送
     * @param applyIds  申请单id
     * @param bindingResult 验证信息
     * @return  返回值
     */
    @PostMapping(value = "/detail-apply")
    @ResponseBody
    public HttpResponse detailForDistribution(@NotNull @RequestBody String[] applyIds,@RequestParam(name = "edit_status", required = false) Integer editStatus, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.AGGREGATE_DISTRIBUTION_PARAM_ERROR);
        }
        return replenishmentApplyService.detailForDistribution(applyIds, editStatus);
    }

    /**
     * 汇总生成配送单
     * @param aggregateResultRequest    请求参数
     * @param bindingResult 验证信息
     * @return  返回值
     */
    @PostMapping(value = "/aggregate")
    @ResponseBody
    public HttpResponse aggregateDistribution(@Valid @RequestBody AggregateResultRequest aggregateResultRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.AGGREGATE_DISTRIBUTION_PARAM_ERROR);
        }
        return aggregateDistributionService.aggregationDistribution(aggregateResultRequest);
    }

    /**
     * 统计补货配送信息
     * @param aggregateStatisticsRequest    请求参数
     * @param bindingResult 验证信息
     * @return  返回值
     */
    @GetMapping(value = "/statistics")
    @ResponseBody
    public HttpResponse statistics(@Valid AggregateStatisticsRequest aggregateStatisticsRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.AGGREGATE_DISTRIBUTION_PARAM_ERROR);
        }
        return aggregateDistributionService.statistics(aggregateStatisticsRequest);
    }

}

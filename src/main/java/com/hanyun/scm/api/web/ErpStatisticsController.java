package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.request.erpStatistics.ErpStatisticsQueryRequest;
import com.hanyun.scm.api.service.ErpStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
 * Date: 2017/6/13
 * Time: 下午18:24
 *
 * @author tangqiming_v@hanyun.com
 */
@Controller
@RequestMapping(value = "/erp-statistics")
public class ErpStatisticsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErpStatisticsController.class);

    @Resource
    private ErpStatisticsService erpStatisticsService;


    /**
     * 查询进销存统计信息
     *
     * @param query 查询统计对象
     * @return HttpResponse
     */
    @PostMapping(value = "/query")
    @ResponseBody
    public HttpResponse select(@Valid @RequestBody ErpStatisticsQueryRequest query, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("查询进销存统计参数错误");
            return HttpResponse.failure(ResultCode.ERP_STATISTICS_QUERY_PARAMS_ERROR);
        }
        return erpStatisticsService.queryErpStatistics(query);
    }
}

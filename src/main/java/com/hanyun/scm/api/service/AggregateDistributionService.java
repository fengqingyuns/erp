package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.request.aggregate.AggregateResultRequest;
import com.hanyun.scm.api.domain.request.aggregate.AggregateStatisticsRequest;

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
 * AggregateDistributionService
 * Date: 2017/3/14
 * Time: 下午4:21
 *
 * @author tianye@hanyun.com
 */
public interface AggregateDistributionService {

    /**
     * 汇总生成配送单
     * @param aggregateResultRequest    参数
     * @return  返回值
     * @author tianye@hanyun.com
     */
    HttpResponse aggregationDistribution(AggregateResultRequest aggregateResultRequest);

    /**
     * 统计补货配送
     * @param aggregateStatisticsRequest    参数
     * @return  返回值
     * @author tianye@hanyun.com
     */
    HttpResponse statistics(AggregateStatisticsRequest aggregateStatisticsRequest);
}

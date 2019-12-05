package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.dao.ReplenishmentApplyDao;
import com.hanyun.scm.api.domain.ReplenishmentApplyDetail;
import com.hanyun.scm.api.domain.request.distribution.DistributionOrderCreateRequest;
import com.hanyun.scm.api.domain.request.distribution.DistributionOrderModifyRequest;
import com.hanyun.scm.api.domain.request.distribution.DistributionOrderQueryRequest;
import com.hanyun.scm.api.exception.DistributionOrderException;

import java.util.List;
import java.util.Map;

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
 * DistributionOrderService
 * Date: 17/3/7
 * Time: 下午14:45
 *
 * @author 1007661792@qq.com
 */
public interface DistributionOrderService {
    /**
     * 创建配送单
     *
     * @param record 创建配送单参数
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse create(DistributionOrderCreateRequest record);

    /**
     * 查询配送单
     *
     * @param record 查询配送单参数
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse select(DistributionOrderQueryRequest record);

    /**
     * 修改配送单
     *
     * @param record 修改配送单参数
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse modify(DistributionOrderModifyRequest record);

    /**
     * 查询配送单详情
     *
     * @param distributionOrderId 查询配送单Id
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse detail(String distributionOrderId);

    /**
     * 删除配送单详情
     *
     * @param distributionOrderId 删除配送单ID
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse delete(String distributionOrderId);

    /**
     * GET申请单详情Map
     * 更新配送单单据信息
     *
     * @param replenishmentApplyIds 源单数组
     * @return map
     */
    Map<String, ReplenishmentApplyDetail> getMap(String[] replenishmentApplyIds);

    /**
     * 导出配送单
     *
     * @param record 导出配送单参数
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    List<List<Object>> exportByDistribution(DistributionOrderQueryRequest record)throws DistributionOrderException;

    /**
     * 导出配送单详情
     *
     * @param distributionOrderId 导出配送单ID
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    List<List<Object>> exportByDistributionById(String distributionOrderId) throws DistributionOrderException;


    /**
     * 提审
     * @return HttpResponse
     */
    HttpResponse commit(String distributionOrderId);

    /**
     * 初始化配送单历史数据(收货状态/收货数量)
     * @return HttpResponse
     */
    HttpResponse initStatusAndNum();
}

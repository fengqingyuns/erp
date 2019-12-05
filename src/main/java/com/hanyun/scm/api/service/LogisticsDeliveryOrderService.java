package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.request.LogisticsDelivery.LogisticsDeliveryCreateRequest;
import com.hanyun.scm.api.domain.request.LogisticsDelivery.LogisticsDeliveryModifyRequest;
import com.hanyun.scm.api.domain.request.LogisticsDelivery.LogisticsDeliveryQueryRequest;
import com.hanyun.scm.api.exception.LogisticsDeliveryException;
import io.swagger.models.auth.In;

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
 * LogisticsDeliveryOrderService
 * Date: 17/3/13
 * Time: 下午18:25
 *
 * @author 1007661792@qq.com
 */
public interface LogisticsDeliveryOrderService {
    /**
     * 创建物流发货单
     *
     * @param record 创建物流发货单参数
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse create(LogisticsDeliveryCreateRequest record);

    /**
     * 更改配送单状态
     *
     * @param distributionOrderId 配送单id
     * @param brandId 品牌id
     * @param storeId 门店id
     * @return Integer 条目
     * @author tangqiming_v@hanyun.com
     */
    Integer updateDeliveryOrderStatus(String distributionOrderId, String brandId, String storeId, Integer status);

    /**
     * 查询物流发货单
     *
     * @param record 查询物流发货单参数
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse select(LogisticsDeliveryQueryRequest record);

    /**
     * 修改物流发货单
     *
     * @param record 修改物流发货单参数
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse modify(LogisticsDeliveryModifyRequest record);

    /**
     * 查询物流发货单详情
     *
     * @param logisticsDeliveryOrderId 查询物流发货单Id
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse detail(String logisticsDeliveryOrderId);

    /**
     * 删除物流发货单详情
     *
     * @param logisticsDeliveryOrderId 删除物流发货单ID
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse delete(String logisticsDeliveryOrderId);

    /**
     * 导出物流发货单
     *
     * @param record 导出物流发货单参数
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    List<List<Object>> exportByLogisticsDelivery(LogisticsDeliveryQueryRequest record) throws LogisticsDeliveryException;

    /**
     * 导出物流发货单详情
     *
     * @param logisticsDeliveryOrderId 导出物流发货单ID
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    List<List<Object>> exportBylogisticsDeliveryById(String logisticsDeliveryOrderId) throws LogisticsDeliveryException;

    /**
     * 提审
     * @param logisticsDeliveryOrderId 发货id
     * @return HttpResponse
     */
    HttpResponse commit(String logisticsDeliveryOrderId);
}

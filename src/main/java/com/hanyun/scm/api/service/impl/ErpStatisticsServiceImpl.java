package com.hanyun.scm.api.service.impl;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.ErpStatisticsDao;
import com.hanyun.scm.api.domain.ErpStatistics;
import com.hanyun.scm.api.domain.request.erpStatistics.ErpStatisticsQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.service.ErpStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
 * ErpStatisticsServiceImpl
 * Date: 2017/06/13
 * Time: 下午18:30
 *
 * @author tagnqiming_v@hanyun.com
 */
@Service
public class ErpStatisticsServiceImpl implements ErpStatisticsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErpStatisticsServiceImpl.class);
    @Resource
    private ErpStatisticsDao erpStatisticsDao;

    @Override
    public HttpResponse queryErpStatistics(ErpStatisticsQueryRequest record) {
        try {
            int count = erpStatisticsDao.countAll(record);
            record.setCount(count);
            List<ErpStatistics> list = erpStatisticsDao.select(record);
            BaseResponse response = new BaseResponse(count,list);
            return HttpResponse.success(response);
        } catch (Exception e){
            LOGGER.error("查询进销存统计数据失败", e);
            return HttpResponse.failure(ResultCode.ERP_STATISTICS_QUERY_ERROR);
        }
    }
}

package com.hanyun.scm.api.service.impl;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.StockSpillLossOrderDetailDao;
import com.hanyun.scm.api.domain.StockSpillLossOrderDetail;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.service.StockSpillLossDetailService;
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
 * 盘点差异单service
 * Date: 16/6/22
 * Time: 下午1:51
 *
 * @author tianye@hanyun.com
 */
@Service
public class StockSpillLossOrderDetailServiceImpl implements StockSpillLossDetailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockSpillLossOrderDetailServiceImpl.class);
    @Resource
    private StockSpillLossOrderDetailDao stockSpillLossOrderDetailDao;

    /***
     * 查询报损报溢单详单
     */

    @Override
    public HttpResponse select(StockSpillLossOrderDetail stockSpillLossOrderDetail) {
        try {
            List<StockSpillLossOrderDetail> detailList = stockSpillLossOrderDetailDao.select(stockSpillLossOrderDetail);
            BaseResponse response = new BaseResponse(0, detailList);
            return HttpResponse.success(response);
        } catch (Exception e) {
            LOGGER.error("报损报溢单查询失败", e);
            return HttpResponse.failure(ResultCode.SPILL_LOSS_DETAIL_QUERY_ERROR);
        }
    }

}

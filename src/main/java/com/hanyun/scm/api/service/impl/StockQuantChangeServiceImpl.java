package com.hanyun.scm.api.service.impl;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.StockQuantChangeHistoryDao;
import com.hanyun.scm.api.domain.StockQuantChangeHistory;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.service.StockQuantChangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
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
 * StockQuantChangeServiceImpl
 * Date: 2017/6/23
 * Time: 上午10:13
 *
 * @author tianye@hanyun.com
 */
@Service
public class StockQuantChangeServiceImpl implements StockQuantChangeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockQuantChangeServiceImpl.class);

    @Resource
    private StockQuantChangeHistoryDao stockQuantChangeHistoryDao;

    @Override
    public HttpResponse query(StockQuantChangeHistory stockQuantChangeHistory) {
        try {
            if (stockQuantChangeHistory.getStockChangeType() != null && stockQuantChangeHistory.getStockChangeType() == Consts.STOCK_IN_OUT_ALL_IN) {
                stockQuantChangeHistory.setOutOrInType(Consts.STOCK_IN_OUT_ALL_IN);
                stockQuantChangeHistory.setStockChangeType(null);
            }
            if (stockQuantChangeHistory.getStockChangeType() != null && stockQuantChangeHistory.getStockChangeType() == Consts.STOCK_IN_OUT_ALL_OUT) {
                stockQuantChangeHistory.setOutOrInType(Consts.STOCK_IN_OUT_ALL_OUT);
                stockQuantChangeHistory.setStockChangeType(null);
            }
            int count = stockQuantChangeHistoryDao.countAll(stockQuantChangeHistory);
            stockQuantChangeHistory.setCount(count);
            List<StockQuantChangeHistory> stockQuantChangeHistoryList = stockQuantChangeHistoryDao.selectSelective(stockQuantChangeHistory);
            Integer totalInNum = 0;
            Integer totalOutNum = 0;
            Integer outInType = stockQuantChangeHistory.getOutOrInType();
            if (outInType == null || outInType == Consts.STOCK_IN_OUT_ALL_IN) {
                stockQuantChangeHistory.setOutOrInType(Consts.STOCK_IN_OUT_ALL_IN);
                totalInNum = stockQuantChangeHistoryDao.totalNum(stockQuantChangeHistory);
            }
            if (outInType == null || outInType == Consts.STOCK_IN_OUT_ALL_OUT) {
                stockQuantChangeHistory.setOutOrInType(Consts.STOCK_IN_OUT_ALL_OUT);
                totalOutNum = stockQuantChangeHistoryDao.totalNum(stockQuantChangeHistory);
            }
            Map<String, Integer> extData = new HashMap<>();
            extData.put("totalInNum", totalInNum==null?0:totalInNum);
            extData.put("totalOutNum", totalOutNum==null?0:totalOutNum);
            BaseResponse baseResponse = new BaseResponse(count, stockQuantChangeHistoryList, extData);
            return HttpResponse.success(baseResponse);
        } catch (Exception e) {
            LOGGER.error("查询库存变更记录失败!", e);
            return HttpResponse.failure(ResultCode.STOCK_QUANT_CHANGE_QUERY_ERROR);
        }
    }
}

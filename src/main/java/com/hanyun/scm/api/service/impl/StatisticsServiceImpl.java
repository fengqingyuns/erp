package com.hanyun.scm.api.service.impl;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.PurchaseOrderDao;
import com.hanyun.scm.api.dao.PurchaseOrderDetailDao;
import com.hanyun.scm.api.domain.GoodsOdoo;
import com.hanyun.scm.api.domain.request.BrandStoreRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderQueryRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderStatisticsRequest;
import com.hanyun.scm.api.domain.response.StatisticsResponse;
import com.hanyun.scm.api.domain.response.purchase.PurchaseOrderStatisticsResponse;
import com.hanyun.scm.api.domain.response.stock.TotalStockResponse;
import com.hanyun.scm.api.service.GoodsOdooService;
import com.hanyun.scm.api.service.StatisticsService;
import com.hanyun.scm.api.service.StockCheckTaskService;
import com.hanyun.scm.api.service.StockQuantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
 * StatisticsServiceImpl
 * Date: 2017/2/21
 * Time: 下午6:15
 *
 * @author tianye@hanyun.com
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    @Resource
    private StockCheckTaskService stockCheckTaskService;

    @Resource
    private StockQuantService stockQuantService;

    @Resource
    private PurchaseOrderDao purchaseOrderDao;

    @Resource
    private PurchaseOrderDetailDao purchaseOrderDetailDao;

    @Resource
    private GoodsOdooService goodsOdooService;

    @Override
    public HttpResponse statistics(BrandStoreRequest brandStoreRequest) {
        try {
            Long waitForCheckCount = stockCheckTaskService.waitForStockCheckCount(brandStoreRequest);
            TotalStockResponse totalStockResponse = stockQuantService.totalStock(brandStoreRequest);
            StatisticsResponse statisticsResponse = new StatisticsResponse(totalStockResponse, waitForCheckCount);
            return HttpResponse.success(statisticsResponse);
        } catch (Exception e) {
            LOGGER.error("统计失败!", e);
            return HttpResponse.failure(ResultCode.STATISTICS_SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse purchaseStatistics(PurchaseOrderStatisticsRequest purchaseOrderStatisticsRequest) {
        try {
            PurchaseOrderQueryRequest query = new PurchaseOrderQueryRequest();
            query.setBrandId(purchaseOrderStatisticsRequest.getBrandId());
            query.setStoreId(purchaseOrderStatisticsRequest.getStoreId());
            query.setStartTime(purchaseOrderStatisticsRequest.getQueryBeginTime());
            query.setEndTime(purchaseOrderStatisticsRequest.getQueryEndTime());
            List<String> purchaseOrderIdList = purchaseOrderDao.selectOrderIds(query);
            if (purchaseOrderIdList == null || purchaseOrderIdList.size() == 0) {
                return HttpResponse.success(new ArrayList<>());
            }
            List<PurchaseOrderStatisticsResponse> purchaseOrderStatisticsResponseList = purchaseOrderDetailDao.statistics(purchaseOrderIdList);
            for (PurchaseOrderStatisticsResponse response : purchaseOrderStatisticsResponseList) {
                GoodsOdoo goodsOdoo = goodsOdooService.selectById(response.getGoodsId());
                if (goodsOdoo == null) {
                    continue;
                }
                response.setGoodsName(goodsOdoo.getGoodsName());
                response.setUnitName(goodsOdoo.getUnitName());
            }
            return HttpResponse.success(purchaseOrderStatisticsResponseList);
        } catch (Exception e) {
            LOGGER.error("采购订单统计失败!", e);
            return HttpResponse.failure(ResultCode.STATISTICS_SYSTEM_ERROR);
        }
    }
}

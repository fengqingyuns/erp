package com.hanyun.scm.api.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hanyun.ground.util.http.HttpClient;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.ReplenishmentApplyDetailDao;
import com.hanyun.scm.api.dao.StockSpillLossOrderDao;
import com.hanyun.scm.api.dao.StockSpillLossOrderDetailDao;
import com.hanyun.scm.api.domain.GoodsOdoo;
import com.hanyun.scm.api.domain.PurchaseOrderDetail;
import com.hanyun.scm.api.domain.StockSpillLossOrder;
import com.hanyun.scm.api.domain.StockSpillLossOrderDetail;
import com.hanyun.scm.api.domain.request.aggregate.AggregateResultRequest;
import com.hanyun.scm.api.domain.request.aggregate.AggregateStatisticsRequest;
import com.hanyun.scm.api.domain.request.distribution.DistributionOrderCreateRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderCreateRequest;
import com.hanyun.scm.api.domain.request.stock.StockSpillLossOrderRequest;
import com.hanyun.scm.api.domain.response.OrderGoodsResponse;
import com.hanyun.scm.api.domain.response.aggregate.AggregateStatisticsResponse;
import com.hanyun.scm.api.exception.AggregateDistributionException;
import com.hanyun.scm.api.exception.GoodsException;
import com.hanyun.scm.api.service.AggregateDistributionService;
import com.hanyun.scm.api.service.DistributionOrderService;
import com.hanyun.scm.api.service.GoodsOdooService;
import com.hanyun.scm.api.service.PurchaseOrderService;
import com.hanyun.scm.api.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
 * AggregateDistributionServiceImpl
 * Date: 2017/3/14
 * Time: 下午4:24
 *
 * @author tianye@hanyun.com
 */
@Service
public class AggregateDistributionServiceImpl implements AggregateDistributionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AggregateDistributionServiceImpl.class);

    @Resource
    private GoodsOdooService goodsOdooService;

    @Resource
    private PurchaseOrderService purchaseOrderService;

    @Resource
    private DistributionOrderService distributionOrderService;

    @Resource
    private ReplenishmentApplyDetailDao replenishmentApplyDetailDao;

    @Resource
    private StockSpillLossOrderDetailDao stockSpillLossOrderDetailDao;

    @Resource
    private StockSpillLossOrderDao stockSpillLossOrderDao;

    @Override
    public HttpResponse aggregationDistribution(AggregateResultRequest aggregateResultRequest) {
        try {
            List<String[]> distributionOrderIds = new ArrayList<>();
            List<DistributionOrderCreateRequest> distributionOrderCreateRequestList = aggregateResultRequest.getDistributionOrderCreateRequestList();
            if (distributionOrderCreateRequestList != null && !distributionOrderCreateRequestList.isEmpty()) {
                //生成配送单
                for (DistributionOrderCreateRequest distributionOrderCreateRequest : distributionOrderCreateRequestList) {
                    if (distributionOrderCreateRequest.getTotalCenterNum() > 0) {
                        HttpResponse response = distributionOrderService.create(distributionOrderCreateRequest);
                        if (!"0".equals(response.getCode())) {
                            throw new AggregateDistributionException("添加配送单失败!");
                        }
                        String[] ids = JsonUtil.fromJson(JsonUtil.toJson(response.getData()), new TypeReference<String[]>() {
                        });
                        distributionOrderIds.add(ids);
                    }
                }
            }
            List<String[]> purchaseOrderIds = new ArrayList<>();
            PurchaseOrderCreateRequest purchaseOrderCreateRequest = aggregateResultRequest.getPurchaseOrderCreateRequest();
            //生成采购订单
            if (purchaseOrderCreateRequest != null && purchaseOrderCreateRequest.getPurchaseOrderDetailList() != null
                    && purchaseOrderCreateRequest.getPurchaseOrderDetailList().size() > 0) {
                Map<String, List<PurchaseOrderDetail>> detailMap = new HashMap<>();
                Map<String, String> supplierMap = new HashMap<>();
                for (PurchaseOrderDetail purchaseOrderDetail : purchaseOrderCreateRequest.getPurchaseOrderDetailList()) {
                    GoodsOdoo goodsOdoo = goodsOdooService.selectById(purchaseOrderDetail.getGoodsId());
                    supplierMap.put(goodsOdoo.getSupplierId(), goodsOdoo.getSupplierName());
                    List<PurchaseOrderDetail> detail = detailMap.get(goodsOdoo.getSupplierId());
                    if (detail == null) {
                        detail = new ArrayList<>();
                    }
                    detail.add(purchaseOrderDetail);
                    detailMap.put(goodsOdoo.getSupplierId(), detail);
                }
                //获取供应商信息, 按供应商生成采购订单
                Set<String> supplierIds = detailMap.keySet();
                for (String supplierId : supplierIds) {
                    if (!StringUtils.isEmpty(supplierId)) {
                        purchaseOrderCreateRequest.setSupplierId(supplierId);
                        purchaseOrderCreateRequest.setSupplierName(supplierMap.get(supplierId));
                    }
                    purchaseOrderCreateRequest.setPurchaseOrderDetailList(detailMap.get(supplierId));
                    purchaseOrderCreateRequest.setPurchaseType(Consts.PURCHASE_TYPE_UNIFIED);
                    HttpResponse response = purchaseOrderService.create(purchaseOrderCreateRequest);
                    if (!"0".equals(response.getCode())) {
                        throw new AggregateDistributionException("添加采购单失败!");
                    }
                    String[] ids = JsonUtil.fromJson(JsonUtil.toJson(response.getData()), new TypeReference<String[]>() {
                    });
                    purchaseOrderIds.add(ids);
                }
            }
            Map<String, Object> idsMap = new HashMap<>();
            idsMap.put("distributionOrderIds", distributionOrderIds);
            idsMap.put("purchaseOrderIds", purchaseOrderIds);
            return HttpResponse.success(idsMap);
        } catch (Exception e) {
            LOGGER.error("汇总配送单失败!", e);
            return HttpResponse.failure(ResultCode.AGGREGATE_DISTRIBUTION_AGGREGATE_ERROR);
        }
    }

    @Override
    public HttpResponse statistics(AggregateStatisticsRequest aggregateStatisticsRequest) {
        try {
            Set<String> goodsIdSet = new HashSet<>();
            Map<String, Long> scrapMap = new HashMap<>();
            Map<String, Integer> saleMap = new HashMap<>();
            List<OrderGoodsResponse> orderGoodsList;
            Long scrapNum;
            Integer saleNum;
            Long applyNum;
            Long stockInNum;
            //废弃数
            StockSpillLossOrderRequest orderRequest = new StockSpillLossOrderRequest();
            orderRequest.setBrandId(aggregateStatisticsRequest.getBrandId());
            orderRequest.setStoreId(aggregateStatisticsRequest.getStoreId());
            orderRequest.setBeginTime(aggregateStatisticsRequest.getBeginTime());
            orderRequest.setEndTime(aggregateStatisticsRequest.getEndTime());
            List<StockSpillLossOrder> orderList = stockSpillLossOrderDao.select(orderRequest);

            List<String> ids = new ArrayList<>();
            for (StockSpillLossOrder order : orderList) {
                ids.add(order.getStockVarianceId());
            }
            StockSpillLossOrderDetail stockSpillLossOrderDetail = new StockSpillLossOrderDetail();
            stockSpillLossOrderDetail.setBrandId(aggregateStatisticsRequest.getBrandId());
            stockSpillLossOrderDetail.setStoreId(aggregateStatisticsRequest.getStoreId());
            stockSpillLossOrderDetail.setBeginTime(aggregateStatisticsRequest.getBeginTime());
            stockSpillLossOrderDetail.setEndTime(aggregateStatisticsRequest.getEndTime());
            stockSpillLossOrderDetail.setOrderIds(ids);
            List<StockSpillLossOrderDetail> details = stockSpillLossOrderDetailDao.selectScrapNum(stockSpillLossOrderDetail);
            for (StockSpillLossOrderDetail detail : details) {
                scrapMap.put(detail.getGoodsId(), detail.getScrapNum());
                goodsIdSet.add(detail.getGoodsId());
            }
            //销售数
            Properties properties = PropertiesUtil.getProperties("erp-api.properties");
            StringBuilder productQueryUrl = new StringBuilder(properties.getProperty("orderSaleUrl"));
            productQueryUrl.append("?brandId=").append(aggregateStatisticsRequest.getBrandId());
            if (!StringUtils.isEmpty(aggregateStatisticsRequest.getStoreId())) {
                productQueryUrl.append("&storeId=").append(aggregateStatisticsRequest.getStoreId());
            }
            if (!StringUtils.isEmpty(aggregateStatisticsRequest.getBeginTime())) {
                productQueryUrl.append("&beginTime=").append(aggregateStatisticsRequest.getBeginTime().replace("T", "%20"));
            }
            if (!StringUtils.isEmpty(aggregateStatisticsRequest.getEndTime())) {
                productQueryUrl.append("&endTime=").append(aggregateStatisticsRequest.getEndTime().replace("T", "%20"));
            }
            productQueryUrl.append("&pageSize=").append(1000000);
            productQueryUrl.append("&pageNum=").append(1);
            HttpClient httpClient = HttpClient.get(productQueryUrl.toString());
            try {
                String result = httpClient.action().result();
                HttpResponse httpResponse = JsonUtil.fromJson(result, HttpResponse.class);
                if (StringUtils.isEmpty(httpResponse.getCode()) || !StringUtils.equals(httpResponse.getCode(), "0") || httpResponse.getData() == null) {
                    throw new GoodsException(ResultCode.GOODS_ODOO_QUERY_ERROR.getMessage());
                }

                if (!"0".equals(httpResponse.getData().toString())) {
                    OrderGoodsResponse orderGoods = JsonUtil.fromJson(JsonUtil.toJson(httpResponse.getData()), OrderGoodsResponse.class);
                    if (orderGoods != null) {
                        orderGoodsList = orderGoods.getOrderGoodsList();
                        for (OrderGoodsResponse response : orderGoodsList) {
                            saleMap.put(response.getGoodsId(), response.getAmountCount());
                            goodsIdSet.add(response.getGoodsId());
                        }

                    }
                }
            } catch (Exception e) {
                LOGGER.error("获取商品销售数失败!", e);
            }
            Map<String, AggregateStatisticsResponse> map = new HashMap<>();
            //统计补货申请数量
            List<AggregateStatisticsResponse> replenishmentApplyStatistics = replenishmentApplyDetailDao.statisticsApplyNum(aggregateStatisticsRequest);//到货数，申请数
            for (AggregateStatisticsResponse aggregateStatisticsResponse : replenishmentApplyStatistics) {
                map.put(aggregateStatisticsResponse.getGoodsId(), aggregateStatisticsResponse);
                goodsIdSet.add(aggregateStatisticsResponse.getGoodsId());
            }
            List<AggregateStatisticsResponse> aggregateStatisticsResponseList = new ArrayList<>();
            for (String goodsId : goodsIdSet) {
                AggregateStatisticsResponse aggregateStatisticsResponse = new AggregateStatisticsResponse();
                aggregateStatisticsResponse.setGoodsId(goodsId);
                if (scrapMap.get(goodsId) == null) {
                    scrapNum = 0L;
                } else {
                    scrapNum = scrapMap.get(goodsId);
                }
                aggregateStatisticsResponse.setScrapNum(scrapNum);
                if (saleMap.get(goodsId) == null) {
                    saleNum = 0;
                } else {
                    saleNum = saleMap.get(goodsId);
                }
                aggregateStatisticsResponse.setSaleNum(saleNum);
                if (map.get(goodsId) != null && map.get(goodsId).getApplyNum() != null) {
                    applyNum = map.get(goodsId).getApplyNum();
                } else {
                    applyNum = 0L;
                }
                if (map.get(goodsId) != null && map.get(goodsId).getStockInNum() != null) {
                    stockInNum = map.get(goodsId).getStockInNum();
                } else {
                    stockInNum = 0L;
                }
                aggregateStatisticsResponse.setApplyNum(applyNum);
                aggregateStatisticsResponse.setStockInNum(stockInNum);
                aggregateStatisticsResponseList.add(aggregateStatisticsResponse);
            }

            return HttpResponse.success(aggregateStatisticsResponseList);

        } catch (Exception e) {
            LOGGER.error("补货配送统计失败!", e);
            return HttpResponse.failure(ResultCode.AGGREGATE_DISTRIBUTION_STATISTICS_ERROR);
        }
    }
}

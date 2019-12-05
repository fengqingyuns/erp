package com.hanyun.scm.api.task;

import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.dao.*;
import com.hanyun.scm.api.domain.*;
import com.hanyun.scm.api.service.ReplenishmentApplyService;
import com.hanyun.scm.api.utils.DateUtilGet;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
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
 * ReplenshmentApplyTask
 * Date: 2017/3/23
 * Time: 下午3:36
 *
 * @author tianye@hanyun.com
 */
@Component
public class ReplenshmentApplyTask {


    private static final String START_TIME = DateUtilGet.getTime(-1) + " 00:00:00";

    private static final String END_TIME = DateUtilGet.getTime(-1) + " 23:59:59";

    @Resource
    private ReplenishmentApplyService replenishmentApplyService;

    @Resource
    private StockSpillLossOrderDao stockSpillLossOrderDao;

    @Resource
    private StockSpillLossOrderDetailDao stockSpillLossOrderDetailDao;

    @Resource
    private StockPickingDao stockPickingDao;

    @Resource
    private StockPickingGoodsDao stockPickingGoodsDao;

    @Resource
    private InspectionPickingInDao inspectionPickingInDao;

    @Resource
    private InspectionPickingInDetailDao inspectionPickingInDetailDao;

    @Resource
    private ReplenishmentApplyDao replenishmentApplyDao;

    @Resource
    private ReplenishmentApplyDetailDao replenishmentApplyDetailDao;

    @Resource
    private ErpStatisticsDao erpStatisticsDao;


//    @Scheduled(cron = "0 10,40 0 * * ?")
    public void invalidateApply() {
        replenishmentApplyService.invalidateApply();
    }

    /**
     * 公用方法set统计bean的值
     *
     * @param brandId   品牌id
     * @param storeId   门店id
     * @param goodsId   商品id
     * @param goodsName 商品名称
     * @param skuCode   商品sku
     * @param barCode   商品国条
     * @return bean对象
     */
    private ErpStatistics applicationFunciton(String brandId, String storeId, String goodsId, String goodsName, String skuCode, String barCode) {
        ErpStatistics obj = new ErpStatistics();
        obj.setStatisticsId(IdUtil.uuid());
        obj.setBrandId(brandId);
        obj.setStoreId(storeId);
        obj.setGoodsId(goodsId);
        obj.setGoodsName(goodsName);
        obj.setGoodsSkuCode(skuCode);
        obj.setGoodsBarCode(barCode);
        obj.setDateString(DateUtilGet.getYear(-1));
        return obj;
    }

    /**
     * 废弃数循环处理
     *
     * @param map    application Map
     * @param object 对象
     */
    private void scrapLoopHandle(Map<String, ErpStatistics> map, StockSpillLossOrderDetail object) {
        String key = object.getBrandId() + object.getStoreId() + object.getGoodsId();
        ErpStatistics obj = map.get(key);
        if (obj != null) {
            obj.setAbandonedNum(object.getScrapNum());
        } else {
            obj = applicationFunciton(object.getBrandId(), object.getStoreId(),
                    object.getGoodsId(), object.getGoodsName(), object.getGoodsCode(), object.getGoodsBarCode());
            obj.setAbandonedNum(object.getScrapNum());
        }
        map.put(key, obj);
    }

    /**
     * 采购入库数循环处理
     *
     * @param map    application Map
     * @param object 对象
     */
    private void purchaseStockInLoopHandle(Map<String, ErpStatistics> map, StockPickingGoods object) {
        String key = object.getBrandId() + object.getStoreId() + object.getGoodsId();
        ErpStatistics obj = map.get(key);
        if (obj != null) {
            obj.setPurchaseStockInNum(object.getPickingAmount());
        } else {
            obj = applicationFunciton(object.getBrandId(), object.getStoreId(), object.getGoodsId(),
                    object.getGoodsName(), object.getGoodsCode(), object.getGoodsBarCode());
            obj.setPurchaseStockInNum(object.getPickingAmount());
        }
        map.put(key, obj);
    }

    /**
     * 其他出入库数循环处理
     *
     * @param map    application Map
     * @param object 对象
     */
    private void otherStockInLoopHandle(Map<String, ErpStatistics> map, StockPickingGoods object) {
        String key = object.getBrandId() + object.getStoreId() + object.getGoodsId();
        ErpStatistics obj = map.get(key);
        if (obj != null) {
            obj.setOtherInStockNum(object.getPickingAmount());
        } else {
            obj = applicationFunciton(object.getBrandId(), object.getStoreId(), object.getGoodsId(),
                    object.getGoodsName(), object.getGoodsCode(), object.getGoodsBarCode());
            obj.setOtherInStockNum(object.getPickingAmount());
        }
        map.put(key, obj);
    }

    /**
     * 验收入库数循环处理
     *
     * @param map    application Map
     * @param object 对象
     */
    private void inspectionStockInLoopHandle(Map<String, ErpStatistics> map, InspectionPickingInDetail object) {
        String key = object.getBrandId() + object.getStoreId() + object.getGoodsId();
        ErpStatistics obj = map.get(key);
        if (obj != null) {
            obj.setInspectedStockInNum(object.getReceiptNum());
        } else {
            obj = applicationFunciton(object.getBrandId(), object.getStoreId(), object.getGoodsId(),
                    object.getGoodsName(), object.getGoodsCode(), object.getGoodsBarCode());
            obj.setInspectedStockInNum(object.getReceiptNum());
        }
        map.put(key, obj);
    }

    /**
     * 补货申请数处理
     * @param map application Map
     * @param object 申请单详情对象
     */
    private void replenishmentAppyNumLoopHandle(Map<String, ErpStatistics> map, ReplenishmentApplyDetail object){
        String key = object.getBrandId() + object.getStoreId() + object.getGoodsId();
        ErpStatistics obj = map.get(key);
        if(obj != null){
            obj.setReplenishmentApplyNum(object.getApplyNum());
        }else{
            obj = applicationFunciton(object.getBrandId(), object.getStoreId(), object.getGoodsId(),
                    object.getGoodsName(), object.getGoodsCode(), object.getGoodsBarCode());
            obj.setReplenishmentApplyNum(object.getApplyNum());
        }
        map.put(key, obj);
    }


    @Scheduled(cron = "0 30 2 * * ?")//每天两点半执行   0 2 0 * * ?(2分钟执行一次) 0 30 2 * * ?(2点半执行) 0/5 * * * * ?(5秒执行一次)
    public void generateErpStatisticsData() {
        Map<String, ErpStatistics> erpStatisticsMap = new HashMap<>();
        BaseParams baseParams = new BaseParams();
        baseParams.setBeginTime(START_TIME);
        baseParams.setEndTime(END_TIME);
        //废弃数
        baseParams.setStockVarianceType(Consts.SCRAP_STATS);
        List<String> scrapIds = stockSpillLossOrderDao.queryIds(baseParams);
        if (scrapIds != null && scrapIds.size() > 0) {
            List<StockSpillLossOrderDetail> brandList = stockSpillLossOrderDetailDao.queryBrandScrapNum(scrapIds);
            List<StockSpillLossOrderDetail> storeList = stockSpillLossOrderDetailDao.queryStoreScrapNum(scrapIds);

            brandList.forEach(brandDetail -> scrapLoopHandle(erpStatisticsMap, brandDetail));
            storeList.forEach(storeDetail -> scrapLoopHandle(erpStatisticsMap, storeDetail));
        }
        //采购入库数
        baseParams.setStockPickingType(Consts.STOCK_PICKING_TYPE_PURCHASE_STOCK_IN);
        List<String> purchaseStockInIds = stockPickingDao.queryIds(baseParams);
        if (purchaseStockInIds != null && purchaseStockInIds.size() > 0) {
            List<StockPickingGoods> brandList = stockPickingGoodsDao.queryBrandStockIn(purchaseStockInIds);
            List<StockPickingGoods> storeList = stockPickingGoodsDao.queryStoreStockIn(purchaseStockInIds);

            brandList.forEach(brandPickingGoods -> purchaseStockInLoopHandle(erpStatisticsMap, brandPickingGoods));
            storeList.forEach(storePickingGoods -> purchaseStockInLoopHandle(erpStatisticsMap, storePickingGoods));
        }
        //其他入库数
        baseParams.setStockPickingType(Consts.STOCK_PICKING_TYPE_STOCK_IN);
        List<String> otherStockInIds = stockPickingDao.queryIds(baseParams);
        if (otherStockInIds != null && otherStockInIds.size() > 0) {
            List<StockPickingGoods> brandList = stockPickingGoodsDao.queryBrandStockIn(otherStockInIds);
            List<StockPickingGoods> storeList = stockPickingGoodsDao.queryStoreStockIn(otherStockInIds);

            brandList.forEach(otherBrandPickingGoods -> otherStockInLoopHandle(erpStatisticsMap, otherBrandPickingGoods));
            storeList.forEach(otherStorePickingGoods -> otherStockInLoopHandle(erpStatisticsMap, otherStorePickingGoods));
        }
        //验收入库数
        List<String> inspectionStockInIds = inspectionPickingInDao.queryIds(baseParams);
        if (inspectionStockInIds != null && inspectionStockInIds.size() > 0) {
            List<InspectionPickingInDetail> brandList = inspectionPickingInDetailDao.queryBrandReceiptNum(inspectionStockInIds);
            List<InspectionPickingInDetail> storeList = inspectionPickingInDetailDao.queryStoreReceiptNum(inspectionStockInIds);

            brandList.forEach(inspection -> inspectionStockInLoopHandle(erpStatisticsMap, inspection));
            storeList.forEach(inspection -> inspectionStockInLoopHandle(erpStatisticsMap, inspection));
        }
        //补货申请数
        List<String> applyIds = replenishmentApplyDao.queryIds(baseParams);
        if (applyIds != null && applyIds.size() > 0){
            List<ReplenishmentApplyDetail> brandList = replenishmentApplyDetailDao.queryBrandApplyNum(applyIds);
            List<ReplenishmentApplyDetail> storeList = replenishmentApplyDetailDao.queryStoreApplyNum(applyIds);

            brandList.forEach(applyDetail -> replenishmentAppyNumLoopHandle(erpStatisticsMap, applyDetail));
            storeList.forEach(applyDetail -> replenishmentAppyNumLoopHandle(erpStatisticsMap, applyDetail));
        }

        //构造bean值
        List<ErpStatistics> list = new ArrayList<>();
        if(!erpStatisticsMap.isEmpty() && erpStatisticsMap.size() > 0){
            erpStatisticsMap.forEach((k,v)-> list.add(v));
            erpStatisticsDao.insert(list);
        }
    }
}

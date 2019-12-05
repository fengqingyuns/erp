package com.hanyun.scm.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.StockPickingDao;
import com.hanyun.scm.api.dao.StockPickingGoodsDao;
import com.hanyun.scm.api.dao.StockQuantDao;
import com.hanyun.scm.api.dao.WarehouseDao;
import com.hanyun.scm.api.domain.*;
import com.hanyun.scm.api.domain.request.goods.GoodsQueryRequest;
import com.hanyun.scm.api.domain.request.stock.StockQuantQueryRequest;
import com.hanyun.scm.api.domain.request.stock.SynchronizedStockPickingGoodsRequest;
import com.hanyun.scm.api.domain.request.stock.SynchronizedStockPickingRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.service.DataTransferService;
import com.hanyun.scm.api.service.GoodsOdooService;
import com.hanyun.scm.api.service.StockPickingService;
import com.hanyun.scm.api.service.StockQuantService;
import com.hanyun.scm.api.utils.HttpMethodUtil;
import com.hanyun.scm.api.utils.PropertiesUtil;
import com.hanyun.scm.api.utils.StockGoodsResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DataTransferServiceImpl implements DataTransferService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataTransferServiceImpl.class);

    @Resource
    private GoodsOdooService goodsOdooService;

    @Resource
    private StockQuantDao stockQuantDao;

    @Resource
    private StockQuantService stockQuantService;

    @Resource
    private WarehouseDao warehouseDao;

    @Resource
    private StockPickingService stockPickingService;

    @Resource
    private StockPickingDao stockPickingDao;

    @Resource
    private StockPickingGoodsDao stockPickingGoodsDao;

    // 返回格式
    private static final String FORMAT = "JSON";

    private TreeMap<String, String> eStoreConfig() {
        // 定义一个TreeMap
        TreeMap<String, String> apiparamsMap = new TreeMap<>();
        // 查询E店宝信息(数据库)
        // 添加请求参数——主帐号
        apiparamsMap.put("dbhost", "edb_a86164");
        // 添加请求参数——appkey
        apiparamsMap.put("appkey", "19287f3b");
        // 添加请求参数——appscret
        apiparamsMap.put("appscret", "116baaea72184f3c9dbe1c56f90fbab9");
        // 添加请求参数——token
        apiparamsMap.put("token", "b7cd3d782fde4cf59e852846397dda92");
        // 返回
        return apiparamsMap;
    }

    @Override
    public HttpResponse deleteStock(String brandId, String warehouseId) {
        try {
            StockQuant stockQuant = new StockQuant();
            stockQuant.setBrandId(brandId);
            stockQuant.setWarehouseId(warehouseId);
            int row = stockQuantDao.deleteStockQuant(stockQuant);
            return HttpResponse.success(row);
        } catch (Exception e) {
            LOGGER.error("删除库存失败!", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    @Override
    @Transactional
    public int importGoodsByPage(String pageNum, String brandId, String warehouseId, String edbStoreId) {

        PropertiesUtil propertiesUtil = new PropertiesUtil();


        String url = "http://vip599.edb07.com.cn/rest/index.aspx";
        TreeMap<String, String> apiparamsMap = eStoreConfig();

        // 添加请求参数——返回格式
        apiparamsMap.put("format", FORMAT);

        // 添加请求参数——接口名称
        apiparamsMap.put("method", "edbProductGet");

        // 添加请求参数——返回结果是否加密（0，为不加密 ，1.加密）
        apiparamsMap.put("slencry", "0");

        try {
            Properties ip = propertiesUtil.getProperties("erp-api.properties");
            String ipString = ip.getProperty("dataTransferIp");
            // 添加请求参数——IP地址
            apiparamsMap.put("ip", ipString);
        } catch (Exception e) {
            LOGGER.error("获取ip失败!", e);
        }

        // 添加请求参数——版本号（目前只提供2.0版本）
        apiparamsMap.put("v", "2.0");

        // 添加请求参数-fields

        // 日期格式化
        String timestamp = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());

        // 添加请求参数——时间戳
        apiparamsMap.put("timestamp", timestamp);
        apiparamsMap.put("store_id", edbStoreId);
        apiparamsMap.put("page_no", pageNum);//分页
        apiparamsMap.put("page_size", "200");//页数量
        // 获取数字签名
        String sign = HttpMethodUtil.md5Signature(apiparamsMap, apiparamsMap.get("appkey"));

        // 添加请求参数-数字签名
        apiparamsMap.put("sign", sign);

        // 定义StringBuilder类型的param
        StringBuilder param = new StringBuilder();

        // 遍历参数
        for (Iterator<Map.Entry<String, String>> it = apiparamsMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, String> e = it.next();
            // 判断key值和token
            if (!"appscret".equals(e.getKey()) && !"token".equals(e.getKey())) {
                if (!"shop_id".equals(e.getKey()) && !"wangwang_id".equals(e.getKey()) && !"date_type".equals(e.getKey())) {
                    // 拼接参数传给param
                    param.append("&").append(e.getKey()).append("=").append(e.getValue());
                    continue;
                }
                try {
                    // 拼接参数传给param
                    param.append("&").append(e.getKey()).append("=").append(HttpMethodUtil.encodeUri(e.getValue()));
                } catch (Exception e1) {
                    // 日志记录异常信息
                    LOGGER.error("同步e店宝分类异常", e1);
                }
            }
        }
        // 定义String类型的PostData
        String postData;
        // 将param转换成字符串类型并且截取再赋值给PostData
        postData = param.toString().substring(1);
        // 定义String类型的result
        String result;

        // 将连接到TOP服务器并获取数据赋值给result
        result = HttpMethodUtil.getResult(url, postData);
        // 不捕获异常
        try {
            // 用\"item\":分隔result并赋值给s
            String s = result.split("\"item\":")[1];
            // 截取s并赋值给result
            result = s.substring(0, s.length() - 3);
        } catch (Exception e) {
            LOGGER.error("导入商品失败!", e);
        }
        // 解析result 插入数据库
        List<StockGoodsResult> goods = JSON.parseArray(result, StockGoodsResult.class);
        Warehouse warehouse = warehouseDao.selectByWarehouseId(warehouseId);
        if (goods == null || goods.isEmpty()) {
            return 0;
        }
        try {
            for (StockGoodsResult stockGoodsResult : goods) {
                String barCode = stockGoodsResult.getBar_code();
                Long stockNum = stockGoodsResult.getEntity_stock();
                String cost = stockGoodsResult.getCost();
                Double costPrice = Double.parseDouble(cost) * 100;
                Long price = Math.round(costPrice);
                GoodsQueryRequest goodsQueryRequest = new GoodsQueryRequest();
                goodsQueryRequest.setBrandId(brandId);
                goodsQueryRequest.setBarcode(barCode);
                BaseResponse baseResponse = goodsOdooService.selectGoods(goodsQueryRequest);
                List<GoodsOdoo> goodsOdooList = baseResponse.getList();
                if (goodsOdooList.isEmpty()) {
                    continue;
                }
                GoodsOdoo goodsOdoo = goodsOdooList.get(0);
                StockQuantQueryRequest stockQuantQueryRequest = new StockQuantQueryRequest();
                stockQuantQueryRequest.setBrandId(brandId);
                stockQuantQueryRequest.setGoodsId(goodsOdoo.getGoodsId());
                stockQuantQueryRequest.setWarehouseId(warehouseId);
                int count = stockQuantDao.countAll(stockQuantQueryRequest);
                if (count == 0) {
                    StockQuant stockQuantCreateRequest = new StockQuant();
                    String stockQuantId = IdUtil.uuid();
                    stockQuantCreateRequest.setStockQuantId(stockQuantId);
                    stockQuantCreateRequest.setGoodsId(goodsOdoo.getGoodsId());
                    stockQuantCreateRequest.setBrandId(brandId);
                    stockQuantCreateRequest.setStoreId(warehouse.getStoreId());
                    stockQuantCreateRequest.setUnitPrice(price);
                    stockQuantCreateRequest.setStockNum(stockNum);
                    stockQuantCreateRequest.setInitialUnitPrice(price);
                    stockQuantCreateRequest.setInitialStockNum(stockNum);
                    stockQuantCreateRequest.setWarehouseId(warehouseId);
                    stockQuantCreateRequest.setWarehouseName(warehouse.getWarehouseName());
                    stockQuantCreateRequest.setClassifyId(goodsOdoo.getClassifyId());
                    stockQuantCreateRequest.setClassifyName(goodsOdoo.getClassifyName());
                    stockQuantCreateRequest.setGoodsName(goodsOdoo.getGoodsName());
                    stockQuantCreateRequest.setUnitName(goodsOdoo.getUnitName());
                    stockQuantCreateRequest.setClassifyId(goodsOdoo.getClassifyId());
                    stockQuantCreateRequest.setClassifyName(goodsOdoo.getClassifyName());
                    stockQuantCreateRequest.setGoodsTypeName(goodsOdoo.getGoodsTypeName());
                    stockQuantCreateRequest.setGoodsBarCode(goodsOdoo.getGoodsBarCode());
                    stockQuantCreateRequest.setGoodsCode(goodsOdoo.getGoodsCode());
                    stockQuantCreateRequest.setFeatures(goodsOdoo.getFeatures());
                    stockQuantCreateRequest.setGoodsBrandName(goodsOdoo.getGoodsBrandName());
                    stockQuantService.create(stockQuantCreateRequest);
                }
            }
        } catch (Exception e) {
            LOGGER.error("同步库存失败!", e);
        }
        return goods.size();
    }

    @Override
    public HttpResponse synchronizedStockPicking(SynchronizedStockPickingRequest synchronizedStockPickingRequest) {
        try {
            StockPicking stockPicking = new StockPicking(synchronizedStockPickingRequest);
            HttpResponse httpResponse = stockPickingService.create(stockPicking);
            if (StringUtils.isEmpty(httpResponse.getCode()) || !"0".equals(httpResponse.getCode())) {
                return httpResponse;
            }
            List<String> ids = JsonUtil.fromJson(JsonUtil.toJson(httpResponse.getData()), new TypeReference<List<String>>() {
            });
            if (ids == null || ids.size() == 0 || StringUtils.isEmpty(ids.get(0))) {
                return HttpResponse.failure(ResultCode.STOCKPICKING_CREAT_ERROR);
            }
            String pickingId = ids.get(0);
            StockPicking old = stockPickingDao.selectByStockPickingId(pickingId);
            List<SynchronizedStockPickingGoodsRequest> goodsList = synchronizedStockPickingRequest.getGoodsList();
            for (SynchronizedStockPickingGoodsRequest synchronizedStockPickingGoodsRequest : goodsList) {
                GoodsOdoo goodsOdoo = goodsOdooService.selectById(synchronizedStockPickingGoodsRequest.getGoodsId());
                StockPickingGoods stockPickingGoods = new StockPickingGoods();
                stockPickingGoods.setGoodsId(synchronizedStockPickingGoodsRequest.getGoodsId());
                stockPickingGoods.setPickingAmount(synchronizedStockPickingGoodsRequest.getAmount());
                stockPickingGoods.setPickingPrice(synchronizedStockPickingGoodsRequest.getPrice());
                stockPickingGoods.setBrandId(synchronizedStockPickingRequest.getBrandId());
                stockPickingGoods.setStoreId(synchronizedStockPickingRequest.getStoreId());
                stockPickingGoods.setStockPickingId(pickingId);
                stockPickingGoods.setGoodsBarCode(goodsOdoo.getGoodsBarCode());
                stockPickingGoods.setGoodsCode(goodsOdoo.getGoodsCode());
                stockPickingGoods.setClassifyId(goodsOdoo.getClassifyId());
                stockPickingGoods.setClassifyName(goodsOdoo.getClassifyName());
                stockPickingGoods.setFeatures(goodsOdoo.getFeatures());
                stockPickingGoods.setGoodsBrandName(goodsOdoo.getGoodsBrandName());
                stockPickingGoods.setGoodsName(goodsOdoo.getGoodsName());
                stockPickingGoods.setGoodsPic(goodsOdoo.getGoodsPic());
                stockPickingGoods.setUnitName(goodsOdoo.getUnitName());
                stockPickingGoods.setRemark("来跑吧一点宝数据");
                stockPickingGoodsDao.insertSelective(stockPickingGoods);
            }
            List<StockPickingGoods> stockPickingGoodsList = stockPickingGoodsDao.selectByStockPickingId(pickingId);
            old.setStockPickingGoodsList(stockPickingGoodsList);
            old.setStockPickingId(null);
            stockQuantService.updateQuantByStockPicking(old, Consts.STOCK_QUANT_CHANGE_TYPE_TRANSFER);
            StockPicking modify = new StockPicking();
            modify.setStockPickingId(pickingId);
            modify.setStockPickingStatus(Consts.ORDER_STATUS_COMMITED);
            stockPickingDao.updateByStockPickingId(modify);
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("同步来跑吧库存单据失败!", e);
            return HttpResponse.failure(ResultCode.STOCKPICKING_CREAT_ERROR);
        }
    }
}

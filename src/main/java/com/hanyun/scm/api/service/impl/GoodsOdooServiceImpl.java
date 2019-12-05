package com.hanyun.scm.api.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.PageInfo;
import com.hanyun.ground.util.http.HttpClient;
import com.hanyun.ground.util.http.HttpClientPost;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.DistributionOrderDetailDao;
import com.hanyun.scm.api.dao.PurchaseOrderDetailDao;
import com.hanyun.scm.api.dao.ReplenishmentApplyDetailDao;
import com.hanyun.scm.api.dao.StockCheckTaskDetailDao;
import com.hanyun.scm.api.domain.*;
import com.hanyun.scm.api.domain.dto.ProductSupplierDTO;
import com.hanyun.scm.api.domain.dto.ProductSupplierFormDTO;
import com.hanyun.scm.api.domain.dto.QueryBillGoodsDTO;
import com.hanyun.scm.api.domain.request.goods.GoodsQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.domain.result.StockQuantResult;
import com.hanyun.scm.api.exception.GoodsException;
import com.hanyun.scm.api.service.GoodsOdooService;
import com.hanyun.scm.api.service.SupplierService;
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
 * 基础商品
 * Date: 2016/10/23
 * Time: 上午9:58
 *
 * @author tianye@hanyun.com
 */
@Service
public class GoodsOdooServiceImpl implements GoodsOdooService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsOdooServiceImpl.class);

    @Resource
    private SupplierService supplierService;

    @Resource
    private PurchaseOrderDetailDao purchaseOrderDetailDao;

    @Resource
    private DistributionOrderDetailDao distributionOrderDetailDao;

    @Resource
    private ReplenishmentApplyDetailDao replenishmentApplyDetailDao;

    @Resource
    private StockCheckTaskDetailDao stockCheckTaskDetailDao;

    @Override
    public HttpResponse select(GoodsQueryRequest goodsRequest) {
        try {
            return HttpResponse.success(selectGoods(goodsRequest));
        } catch (Exception e) {
            LOGGER.error("商品查询失败!", e);
            return HttpResponse.failure(ResultCode.GOODS_ODOO_QUERY_ERROR);
        }
    }

    @Override
    public BaseResponse selectGoods(GoodsQueryRequest goodsRequest) throws GoodsException {
        try {
            PropertiesUtil propertiesUtil = new PropertiesUtil();
            Properties properties = propertiesUtil.getProperties("erp-api.properties");
            ProductSupplierFormDTO productSupplierFormDTO = new ProductSupplierFormDTO();
            productSupplierFormDTO.setBrandId(goodsRequest.getBrandId());
            //查询供应商商品
            List<String> goodsIds = supplierService.getSupplierGoodsIds(goodsRequest.getSupplierId());
            if (goodsIds != null && goodsIds.size() > 0) {
                goodsRequest.setGoodsIds(goodsIds);
            } else if (!StringUtils.isEmpty(goodsRequest.getSupplierId())) {
                return new BaseResponse(0, new ArrayList());
            }
            Map<String, SupplierGoods> supplierGoodsMap = null;
            if (!StringUtils.isEmpty(goodsRequest.getSupplierId())) {
                supplierGoodsMap = supplierService.getSupplierGoodsMap(goodsRequest.getSupplierId());
            }
            //查询商品信息
            StringBuilder productQueryUrl = new StringBuilder(properties.getProperty("productQueryUrl"));
            productQueryUrl.append("?1=1");
            if (!StringUtils.isEmpty(goodsRequest.getCondition())) {
                productSupplierFormDTO.setCondition(goodsRequest.getCondition());
            }
            if (!StringUtils.isEmpty(goodsRequest.getBarcode())) {
                productSupplierFormDTO.setBarcode(goodsRequest.getBarcode());
            }
            if (!StringUtils.isEmpty(goodsRequest.getCategoryId())) {
                productSupplierFormDTO.setCategoryId(goodsRequest.getCategoryId());
            }
            if (goodsRequest.getGoodsIds() != null && goodsRequest.getGoodsIds().size() > 0) {
                productSupplierFormDTO.setGoodsIds(goodsRequest.getGoodsIds());
            }
            if (goodsRequest.getSkipIds() != null && goodsRequest.getSkipIds().size() > 0) {
                productSupplierFormDTO.setSkipIds(goodsRequest.getSkipIds());
            }
            if (goodsRequest.getPageNum() != null) {
                productQueryUrl.append("&pageNum=").append(goodsRequest.getPageNum());
            }
            if (goodsRequest.getPageSize() != null) {
                productQueryUrl.append("&pageSize=").append(goodsRequest.getPageSize());
            } else {
                productQueryUrl.append("&pageSize=100000000");
                productQueryUrl.append("&pageNum=1");
            }
            HttpClientPost httpClient = HttpClient.post(productQueryUrl.toString());
            String result = httpClient.json(productSupplierFormDTO).action().result();
            if (StringUtils.isEmpty(result)) {
                throw new GoodsException(ResultCode.GOODS_ODOO_QUERY_ERROR.getMessage());
            }
            HttpResponse httpResponse = JsonUtil.fromJson(result, HttpResponse.class);
            if (StringUtils.isEmpty(httpResponse.getCode()) || !StringUtils.equals(httpResponse.getCode(), "0") || httpResponse.getData() == null) {
                throw new GoodsException(ResultCode.GOODS_ODOO_QUERY_ERROR.getMessage());
            }
            PageInfo<ProductSupplierDTO> pageInfo = JsonUtil.fromJson(JsonUtil.toJson(httpResponse.getData()), new TypeReference<PageInfo<ProductSupplierDTO>>() {
            });
            if (pageInfo == null) {
                throw new GoodsException(ResultCode.GOODS_ODOO_QUERY_ERROR.getMessage());
            }
            List<ProductSupplierDTO> dtos = pageInfo.getList();
            List<GoodsOdoo> goodsOdooList = new ArrayList<>();
            for (ProductSupplierDTO productSupplierDTO : dtos) {
                GoodsOdoo goodsOdoo = new GoodsOdoo(productSupplierDTO);
                goodsOdoo.setBrandId(goodsRequest.getBrandId());
                if (supplierGoodsMap != null) {
                    SupplierGoods goods = supplierGoodsMap.get(goodsRequest.getSupplierId() + "," + goodsOdoo.getGoodsId());
                    if (goods != null) {
                        goodsOdoo.setPurchasePrice(goods.getPurchasePrice());
                    }
                }
                goodsOdooList.add(goodsOdoo);
            }
            return new BaseResponse(Integer.parseInt(pageInfo.getTotal() + ""), goodsOdooList);
        } catch (Exception e) {
            LOGGER.error("查询商品失败!", e);
            throw new GoodsException("查询商品失败!", e);
        }
    }

    @Override
    public GoodsOdoo selectById(String goodsId) {
        try {
            PropertiesUtil propertiesUtil = new PropertiesUtil();
            Properties properties = propertiesUtil.getProperties("erp-api.properties");
            String detailGoodsUrl = properties.getProperty("productQueryUrl") + "/" + goodsId;
            String result = HttpClient.get(detailGoodsUrl).action().result();
            if (StringUtils.isEmpty(result)) {
                throw new GoodsException(ResultCode.GOODS_ODOO_QUERY_ERROR.getMessage());
            }
            HttpResponse httpResponse = JsonUtil.fromJson(result, HttpResponse.class);
            if (StringUtils.isEmpty(httpResponse.getCode()) || !StringUtils.equals(httpResponse.getCode(), "0") || httpResponse.getData() == null) {
                throw new GoodsException(ResultCode.GOODS_ODOO_QUERY_ERROR.getMessage());
            }
            ProductSupplierDTO productSupplierDTO = JsonUtil.fromJson(JsonUtil.toJson(httpResponse.getData()), ProductSupplierDTO.class);
            return new GoodsOdoo(productSupplierDTO);
        } catch (Exception e) {
            LOGGER.error("查询商品详情失败!", e);
            return null;
        }
    }

    /**
     * 通过分类id去基础商品查找商品id
     * @param brandId 品牌id
     * @param classifyId 分类id
     * @return goodsIds
     */
    private List<String> getGoodsIdsByClassifyId(String brandId, String classifyId){
        List<String> goodsIds = new ArrayList<>();
        try {
            Properties properties = PropertiesUtil.getProperties("erp-api.properties");
            ProductSupplierFormDTO productSupplierFormDTO = new ProductSupplierFormDTO();
            productSupplierFormDTO.setBrandId(brandId);
            productSupplierFormDTO.setCategoryId(classifyId);
            StringBuilder url = new StringBuilder(properties.getProperty("productQueryUrl"));
            url.append("?1=1");
            url.append("&pageSize=100000000");
            url.append("&pageNum=1");
            HttpClientPost httpClent = HttpClient.post(url.toString());
            String result = httpClent.json(productSupplierFormDTO).action().result();
            if (StringUtils.isEmpty(result)) {
                throw new GoodsException(ResultCode.GOODS_ODOO_QUERY_ERROR.getMessage());
            }
            HttpResponse httpResponse = JsonUtil.fromJson(result, HttpResponse.class);
            if (StringUtils.isEmpty(httpResponse.getCode()) || !StringUtils.equals(httpResponse.getCode(), "0") || httpResponse.getData() == null) {
                throw new GoodsException(ResultCode.GOODS_ODOO_QUERY_ERROR.getMessage());
            }
            PageInfo<ProductSupplierDTO> pageInfo = JsonUtil.fromJson(JsonUtil.toJson(httpResponse.getData()), new TypeReference<PageInfo<ProductSupplierDTO>>() {
            });
            pageInfo.getList().forEach(p -> goodsIds.add(p.getProductId()));
            return goodsIds;
        } catch (Exception e){
            LOGGER.error("查询商品详情失败!", e);
            return goodsIds;
        }
    }

    @Override
    public HttpResponse selectBillGoods(QueryBillGoodsDTO dto) {
        try {
            Integer orderType = dto.getOrderType();
            String brandId = dto.getBrandId();
            if (orderType == null) {
                return HttpResponse.failure(ResultCode.BILL_TYPE_IS_NULL);
            }
            if(!StringUtils.isEmpty(dto.getClassifyId())){
                dto.setGoodsIds(getGoodsIdsByClassifyId(dto.getBrandId(),dto.getClassifyId()));
                dto.setClassifyId(null);
            }
            int count;
            Map<String, GoodsOdoo> map;
            switch (orderType) {
                case Consts.PURCHASE_ORDER_TYPE:            //扫码采购订单商品
                    PurchaseOrderDetail purchaseOrderDetail = dto.setPurchaseOrderDetail(dto);
                    count = purchaseOrderDetailDao.countAll(purchaseOrderDetail);
                    purchaseOrderDetail.setCount(count);
                    List<PurchaseOrderDetail> purchaseOrderDetailList = purchaseOrderDetailDao.select(purchaseOrderDetail);
                    if(purchaseOrderDetailList != null && purchaseOrderDetailList.size() > 0){
                        map = getPurchaseOrderGoodsMap(brandId, purchaseOrderDetailList);
                        purchaseOrderDetailList.forEach(p -> {
                            String goodsId = p.getGoodsId();
                            boolean bl = map != null && map.get(goodsId) != null && !StringUtils.isEmpty(map.get(goodsId).getGoodsPic());
                            p.setGoodsPic(bl ? map.get(goodsId).getGoodsPic() : "");
                        });
                    }
                    BaseResponse purchaseResponse = new BaseResponse(count, purchaseOrderDetailList);
                    return HttpResponse.success(purchaseResponse);
                case Consts.DISTRIBUTION_ORDER_TYPE:        //扫码配送单商品
                    DistributionOrderDetail distributionOrderDetail = dto.setDistributionOrderDetail(dto);
                    count = distributionOrderDetailDao.countAll(distributionOrderDetail);
                    distributionOrderDetail.setCount(count);
                    List<DistributionOrderDetail> distributionOrderDetailList = distributionOrderDetailDao.select(distributionOrderDetail);
                    if(distributionOrderDetailList != null && distributionOrderDetailList.size() > 0){
                        map = getDistributionGoodsMap(brandId, distributionOrderDetailList);
                        distributionOrderDetailList.forEach(d -> {
                            String goodsId = d.getGoodsId();
                            boolean bl = map != null && map.get(goodsId) != null && !StringUtils.isEmpty(map.get(goodsId).getGoodsPic());
                            d.setGoodsPic(bl ? map.get(goodsId).getGoodsPic() : "");
                        });
                    }
                    BaseResponse distributionResponse = new BaseResponse(count, distributionOrderDetailList);
                    return HttpResponse.success(distributionResponse);
                case Consts.REPLENISHMENT_APPLY_ORDER_TYPE: //扫码补货申请单商品
                    ReplenishmentApplyDetail replenishmentApplyDetail = dto.setReplenishmentApplyDetail(dto);
                    count = replenishmentApplyDetailDao.countAll(replenishmentApplyDetail);
                    replenishmentApplyDetail.setCount(count);
                    List<ReplenishmentApplyDetail> replenishmentApplyDetailList = replenishmentApplyDetailDao.selectSelective(replenishmentApplyDetail);
                    if(replenishmentApplyDetailList !=null && replenishmentApplyDetailList.size() > 0){
                        map = getReplenishmentApplyGoodsMap(brandId, replenishmentApplyDetailList);
                        replenishmentApplyDetailList.forEach(r -> {
                            String goodsId = r.getGoodsId();
                            boolean bl = map != null && map.get(goodsId) != null && !StringUtils.isEmpty(map.get(goodsId).getGoodsPic());
                            r.setGoodsPic(bl ? map.get(goodsId).getGoodsPic() : "");
                        });
                    }
                    BaseResponse applyResponse = new BaseResponse(count, replenishmentApplyDetailList);
                    return HttpResponse.success(applyResponse);
                case Consts.STOCK_CHECK_TASK_TYPE :         //盘点任务单商品--->任务快照记录
                    StockCheckTaskDetail stockCheckTaskDetail = dto.setStockCheckTaskDetail(dto);
                    count = stockCheckTaskDetailDao.countAll(stockCheckTaskDetail);
                    stockCheckTaskDetail.setCount(count);
                    List<StockCheckTaskDetail> result = stockCheckTaskDetailDao.select(stockCheckTaskDetail);
                    if(result != null && result.size() > 0){
                        map = getStockcheckTaskGoodsMap(brandId, result);
                        result.forEach(s -> {
                            String goodsId = s.getGoodsId();
                            s.setGoodsPrice(s.getUnitPrice());
                            boolean bl = map != null && map.get(goodsId) != null && !StringUtils.isEmpty(map.get(goodsId).getGoodsPic());
                            s.setGoodsPic(bl ? map.get(goodsId).getGoodsPic() : "");
                        });
                    }
                    BaseResponse taskResponse = new BaseResponse(count, result);
                    return HttpResponse.success(taskResponse);
                default:
                    LOGGER.error("类型为空");
                    break;
            }
            return HttpResponse.failure(ResultCode.BILL_GOODS_QUERY_ERROR);
        } catch (Exception e) {
            LOGGER.error("查询单据商品失败", e);
            return HttpResponse.failure(ResultCode.BILL_GOODS_QUERY_ERROR);
        }
    }

    /**
     * 公用方法通过brandId和商品ids查询商品对象
     *
     * @param brandId 品牌id
     * @param ids     商品id列表
     * @return map
     */
    private Map<String, GoodsOdoo> commonMap(String brandId, List<String> ids) {
        Map<String, GoodsOdoo> map = new HashMap<>();
        try {
            GoodsQueryRequest query = new GoodsQueryRequest();
            query.setBrandId(brandId);
            query.setGoodsIds(ids);
            BaseResponse response = selectGoods(query);
            List<GoodsOdoo> goodsList = response.getList();
            if (!goodsList.isEmpty() && goodsList.size() > 0) {
                goodsList.forEach(go -> map.put(go.getGoodsId(),go));
            }
            return map;
        } catch (Exception e) {
            LOGGER.error("查询商品信息失败", e);
            return map;
        }
    }

    /**
     * 通用获取goodsId列表
     * @param json 详情列表字符串Json
     * @return ids
     */
    private List<String> getIds(String json){
        List<String> ids = new ArrayList<>();
        List<GoodsOdoo> list = JsonUtil.fromJson(json, new TypeReference<List<GoodsOdoo>>() {
        });
        if(!list.isEmpty() && list.size() > 0){
            list.forEach(p -> {
                String goodsId = p.getGoodsId();
                if (!StringUtils.isEmpty(goodsId)) {
                    ids.add(goodsId);
                }
            });
        }
        return ids;
    }

    /**
     * 通过采购订单商品id去获取基础商品信息
     *
     * @param brandId 品牌id
     * @param list    采购订单列表
     * @return map
     */
    @Override
    public Map<String, GoodsOdoo> getPurchaseOrderGoodsMap(String brandId, List<PurchaseOrderDetail> list) {
        return commonMap(brandId, getIds(JsonUtil.toJson(list)));
    }

    /**
     * 通过配送单商品id去获取基础商品信息
     *
     * @param brandId 品牌id
     * @param list    配送单列表
     * @return map
     */
    @Override
    public Map<String, GoodsOdoo> getDistributionGoodsMap(String brandId, List<DistributionOrderDetail> list) {
        return commonMap(brandId, getIds(JsonUtil.toJson(list)));
    }

    /**
     * 通过申请单商品id去获取基础商品信息
     *
     * @param brandId 品牌id
     * @param list    申请单详情列表
     * @return map
     */
    @Override
    public Map<String, GoodsOdoo> getReplenishmentApplyGoodsMap(String brandId, List<ReplenishmentApplyDetail> list) {
        return commonMap(brandId, getIds(JsonUtil.toJson(list)));
    }

    /**
     * 通过库存商品id去获取基础商品信息(库存查询)
     *
     * @param brandId 品牌id
     * @param list    库存列表
     * @return map
     */
    @Override
    public Map<String, GoodsOdoo> getStockQuantResultGoodsMap(String brandId, List<StockQuantResult> list) {
        return commonMap(brandId, getIds(JsonUtil.toJson(list)));
    }

    /**
     * 通过库存商品id去获取基础商品信息(单独查库存)
     *
     * @param brandId 品牌id
     * @param list    库存列表
     * @return map
     */
    @Override
    public Map<String, GoodsOdoo> getStockQuantGoodsMap(String brandId, List<StockQuant> list) {
        return commonMap(brandId, getIds(JsonUtil.toJson(list)));
    }

    /**
     * 通过盘点单商品id去获取基础商品信息
     *
     * @param brandId 品牌id
     * @param list    盘点单详细列表
     * @return map
     */
    @Override
    public Map<String, GoodsOdoo> getStockCheckOrderGoodsMap(String brandId, List<StockCheckOrderDetail> list) {
        return commonMap(brandId, getIds(JsonUtil.toJson(list)));
    }

    /**
     * 通过验收入库单商品id去获取基础商品信息
     * @param brandId 品牌id
     * @param list    验收入库单详细列表
     * @return Map
     */
    @Override
    public Map<String, GoodsOdoo> getInspectionGoodsMap(String brandId, List<InspectionPickingInDetail> list) {
        return commonMap(brandId, getIds(JsonUtil.toJson(list)));
    }

    /**
     * 通过盘点任务单商品id去获取基础商品信息
     * @param brandId 品牌id
     * @param list    验收入库单详细列表
     * @return Map
     */
    @Override
    public Map<String, GoodsOdoo> getStockcheckTaskGoodsMap(String brandId, List<StockCheckTaskDetail> list) {
        return commonMap(brandId, getIds(JsonUtil.toJson(list)));
    }
}

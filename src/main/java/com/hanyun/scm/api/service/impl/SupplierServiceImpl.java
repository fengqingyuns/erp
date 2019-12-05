package com.hanyun.scm.api.service.impl;


import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.IdGenerateType;
import com.hanyun.scm.api.consts.MapConsts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.SupplierDao;
import com.hanyun.scm.api.dao.SupplierGoodsDao;
import com.hanyun.scm.api.domain.GoodsOdoo;
import com.hanyun.scm.api.domain.Supplier;
import com.hanyun.scm.api.domain.SupplierGoods;
import com.hanyun.scm.api.domain.dto.SupplierDTO;
import com.hanyun.scm.api.domain.request.goods.GoodsQueryRequest;
import com.hanyun.scm.api.domain.request.purchase.PurchaseReturnQueryRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderQueryRequest;
import com.hanyun.scm.api.domain.request.supplier.*;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.domain.response.SupplierGoodsResposne;
import com.hanyun.scm.api.domain.result.SupplierResult;
import com.hanyun.scm.api.exception.SupplierException;
import com.hanyun.scm.api.service.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
 * <p>
 * SupplierServiceImpl
 * Date: 2016/10/23
 * Time: 下午10:23
 *
 * @author tangqiming_v@hanyun.com
 */
@Service
public class SupplierServiceImpl implements SupplierService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierServiceImpl.class);

    @Resource
    private SupplierDao supplierDao;

    @Resource
    private SupplierGoodsDao supplierGoodsDao;

    @Resource
    private IdGenerateSeqService idGenerateSeqService;

    @Resource
    private GoodsOdooService goodsOdooService;

    @Resource
    private PurchaseOrderService purchaseOrderService;

    @Resource
    private PurchaseReturnService purchaseReturnService;

    @Override
    public HttpResponse create(SupplierCreateRequest supplier) {
        try {
            List<SupplierGoods> goodsList = removeGoods(supplier.getSupplierGoodsList());
            //查询供应商名字是否有相同
            String suppleirName = supplier.getSupplierName();
            if (!StringUtils.isEmpty(suppleirName)) {
                Supplier supplierIn = new Supplier();
                supplierIn.setBrandId(supplier.getBrandId());
                if (!StringUtils.isEmpty(supplier.getStoreId())) {
                    supplierIn.setStoreId(supplier.getStoreId());
                }
                supplierIn.setValidStatus(Consts.VALID_STATUS_VALID);
                supplierIn.setSupplierName(suppleirName);
                Supplier resultSupplier = supplierDao.querySupplierName(supplierIn);
                if (resultSupplier != null) {
                    return HttpResponse.failure(ResultCode.SUPPLIERNAME_QUERY_ERROR);
                }
            }
            String supplierId = IdUtil.uuid();
            String supplierDocumentId = idGenerateSeqService.generateId(IdGenerateType.GY);
            supplier.setSupplierId(supplierId);
            supplier.setSupplierDocumentId(supplierDocumentId);
            int row = supplierDao.insert(supplier);
            if (row <= 0) {
                return HttpResponse.failure(ResultCode.SUPPLIER_CREATE_ERROR);
            }
            //插入供应商商品
            for (SupplierGoods supplierGoods : goodsList) {
                if (!StringUtils.isNotBlank(supplierGoods.getGoodsId())) {
                    continue;
                }
                insertSupplierGoods(supplier, supplierGoods);
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("创建供应商失败!", e);
            return HttpResponse.failure(ResultCode.SUPPLIER_CREATE_ERROR);
        }
    }

    /**
     * 封装供应商商品到map中
     *
     * @param supplierId 供应商id
     * @return map
     */
    @Override
    public Map<String, SupplierGoods> getSupplierGoodsMap(String supplierId) {
        Map<String, SupplierGoods> goodsMap = new HashMap<>();
        SupplierGoods querySuppleirGoods = new SupplierGoods();
        querySuppleirGoods.setSupplierId(supplierId);
        List<SupplierGoods> supplierGoodsList = supplierGoodsDao.select(querySuppleirGoods);
        if (supplierGoodsList != null && supplierGoodsList.size() != 0) {
            for (SupplierGoods supplierGoods : supplierGoodsList) {
                goodsMap.put(supplierId + "," + supplierGoods.getGoodsId(), supplierGoods);
            }
        }
        return goodsMap;
    }

    @Override
    public HttpResponse detail(String supplierId) {
        try {
            List<SupplierGoodsResposne> resultGoodsList = new ArrayList<>();
            Supplier supplier = supplierDao.selectBySupplierId(supplierId);
            if (supplier == null) {
                return HttpResponse.failure(ResultCode.SUPPLIER_QUERYID_ERROR);
            }
            //封装供应商商品Map
            Map<String, SupplierGoods> goodsMap = getSupplierGoodsMap(supplierId);
            //查询基础商品list
            GoodsQueryRequest queryGoods = new GoodsQueryRequest();
            queryGoods.setBrandId(supplier.getBrandId());
            queryGoods.setSupplierId(supplierId);
            queryGoods.setPageSize(999999);
            BaseResponse baseResponse = goodsOdooService.selectGoods(queryGoods);
            List<GoodsOdoo> goodsOdooList = baseResponse.getList();
            if (goodsOdooList != null && goodsOdooList.size() != 0) {
                for (GoodsOdoo goods : goodsOdooList) {
                    String goodsId = goods.getGoodsId();
                    String key = supplierId + "," + goodsId;
                    if (goodsMap.get(key) == null) {
                        continue;
                    }
                    SupplierGoodsResposne response = new SupplierGoodsResposne();
                    response.setGoodsId(goodsMap.get(key).getGoodsId());
                    response.setGoodsPic(goods.getGoodsPic());
                    response.setGoodsName(goods.getGoodsName());
                    response.setGoodsSkuCode(goods.getGoodsCode());
                    response.setGoodsBarCode(goods.getGoodsBarCode());
                    response.setGoodsFeatures(goods.getFeatures());
                    response.setPurchasePrice(goodsMap.get(key).getPurchasePrice());
                    response.setCategoryId(goods.getClassifyId());
                    response.setClassifyName(goods.getClassifyName());
                    resultGoodsList.add(response);
                }
                supplier.setResultSupplierGoodsList(resultGoodsList);
            }
            return HttpResponse.success(supplier);
        } catch (Exception e) {
            LOGGER.error("查询供应商详情失败!", e);
            return HttpResponse.failure(ResultCode.SUPPLIER_QUERYID_ERROR);
        }
    }


    /**
     * 查询供应商
     */
    @Override
    public HttpResponse select(SupplierQueryRequest supplierRequest) {
        try {
            int count = supplierDao.countAll(supplierRequest);
            supplierRequest.setCount(count);
            List<Supplier> supplierList = supplierDao.select(supplierRequest);
            BaseResponse reponse = new BaseResponse(count, supplierList);
            return HttpResponse.success(reponse);
        } catch (Exception e) {
            LOGGER.error("查询供应商列表失败!", e);
            return HttpResponse.failure(ResultCode.SUPPLIER_QUERY_ERROR);
        }
    }

    @Override
    public List<String> getSupplierGoodsIds(String supplierId) {
        List<String> goodsIds = new ArrayList<>();
        if(!StringUtils.isNotBlank(supplierId)){
            return goodsIds;
        }
        SupplierGoods queryGoods = new SupplierGoods();
        queryGoods.setSupplierId(supplierId);
        List<SupplierGoods> supplierGoodsList = supplierGoodsDao.select(queryGoods);
        for (SupplierGoods goods : supplierGoodsList) {
            String goodsId = goods.getGoodsId();
            if (!StringUtils.isNotBlank(goodsId)) {
                continue;
            }
            goodsIds.add(goodsId);
        }
        return goodsIds;
    }

    /**
     * 修改供应商详情
     */
    @Override
    public HttpResponse modifySupplier(SupplierModifyRequest supplier) {
        try {
            List<SupplierGoods> goodsList = removeGoods(supplier.getSupplierGoodsList());
            List<String> deleteGoodsIds = supplier.getDeleteGoodsIds();

            Supplier supplierOld = supplierDao.selectBySupplierId(supplier.getSupplierId());
            if (supplierOld == null) {
                return HttpResponse.failure(ResultCode.SUPPLIER_DATA_NOT_FOUND);
            }
            //查询供应商名字是否有相同
            String supplierId = supplier.getSupplierId();
            String supplierName = supplier.getSupplierName();
            if (!StringUtils.isEmpty(supplierName)) {
                Supplier supplierIn = new Supplier();
                supplierIn.setBrandId(supplier.getBrandId());
                if (!StringUtils.isEmpty(supplier.getStoreId())) {
                    supplierIn.setStoreId(supplier.getStoreId());
                }
                supplierIn.setSupplierName(supplierName);
                supplierIn.setValidStatus(Consts.VALID_STATUS_VALID);
                Supplier resultSupplier = supplierDao.querySupplierName(supplierIn);
                if (resultSupplier != null) {
                    String resultSupplierId = resultSupplier.getSupplierId();
                    if (!supplierId.equals(resultSupplierId)) {
                        return HttpResponse.failure(ResultCode.SUPPLIERNAME_QUERY_ERROR);
                    }
                }
            }
            int row = supplierDao.updateBySupplierId(supplier);
            if (row <= 0) {
                return HttpResponse.failure(ResultCode.SUPPLIER_UPDATE_ERROR);
            }

            if (deleteGoodsIds != null && deleteGoodsIds.size() > 0) {
                for (String goodsId : deleteGoodsIds) {
                    if (!StringUtils.isNotBlank(goodsId) && getSupplierGoodsIds(supplierId).indexOf(goodsId) < 0) {
                        continue;
                    }
                    supplierGoodsDao.deleteBySupplierIdAndGoodsId(supplierId, goodsId);
                }
            }

            //封装供应商商品Map
            Map<String, SupplierGoods> goodsMap = getSupplierGoodsMap(supplierId);

            //封装插入时供应商对象
            SupplierCreateRequest insSupplier = new SupplierCreateRequest();
            insSupplier.setSupplierId(supplierId);
            insSupplier.setBrandId(supplier.getBrandId());
            insSupplier.setStoreId(supplier.getStoreId());
            //处理供应商商品
            for (SupplierGoods goods : goodsList) {
                String goodsId = goods.getGoodsId();
                SupplierGoods obj = goodsMap.get(supplierId + "," + goodsId);
                if (obj == null) { //插入供应商商品
                    insertSupplierGoods(insSupplier, goods);
                    continue;
                }
                obj.setPurchasePrice(goods.getPurchasePrice());
                int upRow = supplierGoodsDao.updateBySupplierGoodsId(obj);
                if (upRow <= 0) {
                    return HttpResponse.failure(ResultCode.SUPPLIER_GOODS_UPDATE_ERROR);
                }
            }
            return HttpResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("更新供应商信息失败!", e);
            return HttpResponse.failure(ResultCode.SUPPLIER_UPDATE_ERROR);
        }
    }

    private Map<String, Supplier> getSupplierMap(List<String> supplierIds){
        Map<String, Supplier> map = new HashMap<>();
        List<Supplier> list = supplierDao.querySupplier(supplierIds);
        if(list != null || list.size() > 0){
            for(Supplier supplier : list){
                map.put(supplier.getSupplierId(),supplier);
            }
        }
        return map;
    }

    @Override
    public HttpResponse querySupplierGoodsList(SupplierDTO supplierDTO) {
        List<SupplierResult> result = new ArrayList<>();
        List<String> supplierIds = supplierGoodsDao.selectSupplierIds(supplierDTO);
        Map<String, Supplier> map = getSupplierMap(supplierIds);
        SupplierGoods goods = new SupplierGoods();
        goods.setBrandId(supplierDTO.getBrandId());
        if(!StringUtils.isNotBlank(supplierDTO.getStoreId())){
            goods.setStoreId(supplierDTO.getStoreId());
        }
        goods.setGoodsId(supplierDTO.getGoodsId());
        List<SupplierGoods> goodsList = supplierGoodsDao.select(goods);
        for(SupplierGoods supplierGoods : goodsList){
            SupplierResult bean = new SupplierResult();
            bean.setSupplierId(supplierGoods.getSupplierId());
            bean.setSupplierName(map.get(supplierGoods.getSupplierId()).getSupplierName());
            bean.setPurchasePrice(supplierGoods.getPurchasePrice());
            result.add(bean);
        }
        return HttpResponse.success(result);
    }

    /**
     * 删除供应商->改状态
     *
     * @param supplier 供应商对象
     * @return HttpResponse
     */
    @Override
    public HttpResponse deleteSupplierByStatus(SupplierDeleteStatusRequest supplier) {
        try {
            String supplierId = supplier.getSupplierId();
            String brandId = supplier.getBrandId();
            String storeId = supplier.getStoreId();
            Supplier suppleirOld = supplierDao.selectBySupplierId(supplierId);
            if (suppleirOld == null) {
                return HttpResponse.failure(ResultCode.SUPPLIER_DATA_NOT_FOUND);
            }
            //查询供应商商品接口
            SupplierGoods goodsQuery = new SupplierGoods();
            goodsQuery.setBrandId(brandId);
            goodsQuery.setSupplierId(supplierId);
            List<SupplierGoods> goodsList = supplierGoodsDao.select(goodsQuery);
            if (goodsList != null && goodsList.size()>0) {
                return HttpResponse.failure(ResultCode.SUPPLIER_PRODUCT_RELATION_ERROR);
            }
            //查询退货接口
            PurchaseReturnQueryRequest returnQuery = new PurchaseReturnQueryRequest();
            returnQuery.setSupplierId(supplierId);
            returnQuery.setBrandId(brandId);
            if (!StringUtils.isEmpty(storeId)) {
                returnQuery.setStoreId(storeId);
            }
            BaseResponse returnBaseResponse = purchaseReturnService.selectList(returnQuery, false);
            if (returnBaseResponse.getCount() > 0) {
                return HttpResponse.failure(ResultCode.SUPPLIER_PURCHASERETURN_RELATION_ERROR);
            }
            //查询订单接口
            PurchaseOrderQueryRequest orderQuery = new PurchaseOrderQueryRequest();
            orderQuery.setSupplierId(supplierId);
            orderQuery.setBrandId(brandId);
            if (!StringUtils.isEmpty(storeId)) {
                orderQuery.setStoreId(storeId);
            }
            orderQuery.setSupplierName(suppleirOld.getSupplierName());
            BaseResponse orderRBaseResponse = purchaseOrderService.selectList(orderQuery, false);
            if (orderRBaseResponse.getCount() > 0) {
                return HttpResponse.failure(ResultCode.SUPPLIER_PURCHASEORDER_RELATION_ERROR);
            }
            //更新供应商状态
            int row = supplierDao.updateDeleteStatusBySupplierId(supplierId, supplier.getUpdateUserId());
            if (row <= 0) {
                return HttpResponse.failure(ResultCode.SUPPLIER_UPDATE_ERROR);
            }
            //更新商品状态
//            supplierGoodsDao.deleteSupplierGoodsBySupplierIdForStatus(supplierId);
            return HttpResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("更新供应商信息失败!", e);
            return HttpResponse.failure(ResultCode.SUPPLIER_UPDATE_ERROR);
        }
    }
    /**以下代码没用到**/
    @Override
    public HttpResponse modifyAvailableStatus(SupplierModifyStatusRequest supplier) {
        try {
            String supplierId = supplier.getSupplierId();
            String brandId = supplier.getBrandId();
            String storeId = supplier.getStoreId();
            Supplier old = supplierDao.selectBySupplierId(supplierId);
            if (old == null) {
                throw new SupplierException("未查询到该数据");
            }
            //禁用状态时做相应操作
            if (supplier.getSupplierStatus() == Consts.SUPPLIER_DISABLE_STATUS) {
                //查询商品调接口
                GoodsQueryRequest goodsQuery = new GoodsQueryRequest();
                goodsQuery.setBrandId(brandId);
                goodsQuery.setSupplierId(supplierId);
                BaseResponse goodsBaseResponse = goodsOdooService.selectGoods(goodsQuery);
                if (goodsBaseResponse.getCount() > 0) {
                    return HttpResponse.failure(ResultCode.SUPPLIER_SUPPLIER_RELATION_STATUS_ERROR);
                }
                //查询退货接口
                PurchaseReturnQueryRequest returnQuery = new PurchaseReturnQueryRequest();
                returnQuery.setSupplierId(supplierId);
                returnQuery.setBrandId(brandId);
                if (!StringUtils.isEmpty(storeId)) {
                    returnQuery.setStoreId(storeId);
                }
                BaseResponse returnBaseResponse = purchaseReturnService.selectList(returnQuery, false);
                if (returnBaseResponse.getCount() > 0) {
                    return HttpResponse.failure(ResultCode.SUPPLIER_PURCHASERETURN_RELATION_STATUS_ERROR);
                }
                //查询订单接口
                PurchaseOrderQueryRequest orderQuery = new PurchaseOrderQueryRequest();
                orderQuery.setSupplierId(supplierId);
                if (!StringUtils.isEmpty(supplier.getSupplierName())) {
                    orderQuery.setSupplierName(supplier.getSupplierName());
                }
                orderQuery.setBrandId(brandId);
                if (!StringUtils.isEmpty(storeId)) {
                    orderQuery.setStoreId(storeId);
                }
                BaseResponse orderRBaseResponse = purchaseOrderService.selectList(orderQuery, false);
                if (orderRBaseResponse.getCount() > 0) {
                    return HttpResponse.failure(ResultCode.SUPPLIER_PURCHASEORDER_RELATION_STATUS_ERROR);
                }
            }
            SupplierModifyRequest modify = new SupplierModifyRequest();
            modify.setSupplierId(supplierId);
            modify.setSupplierStatus(supplier.getSupplierStatus());
            int row = supplierDao.updateBySupplierId(modify);
            if (row <= 0) {
                HttpResponse.failure(ResultCode.SUPPLIER_MODIFY_AVAILABLE_STATUS_ERROR);
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("修改供应商状态失败", e);
            return HttpResponse.failure(ResultCode.SUPPLIER_MODIFY_AVAILABLE_STATUS_ERROR);
        }
    }
    /**以上代码没用到**/
   
    /**
     * 删除供应商
     */
    @Override
    public HttpResponse deleteSupplier(String supplierId) {
        try {
            Supplier supplier = supplierDao.selectBySupplierId(supplierId);
            if (supplier == null) {
                return HttpResponse.failure(ResultCode.SUPPLIER_DATA_NOT_FOUND);
            }
            int row = supplierDao.deleteBySupplierId(supplierId);
            if (row <= 0) {
                return HttpResponse.failure(ResultCode.SUPPLIER_DELETE_ERROR);
            }
            int goodsRow = supplierGoodsDao.deleteBySupplierId(supplierId);
            if (goodsRow <= 0) {
                return HttpResponse.failure(ResultCode.SUPPLIER_GOODS_DELETE_ERROR);
            }
            return HttpResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("删除供应商失败!", e);
            return HttpResponse.failure(ResultCode.SUPPLIER_DELETE_ERROR);
        }
    }
  
    /**
     * 导出供应商
     */
    @Override
    public List<List<Object>> exportExcelBySuppleir(SupplierQueryRequest supplierQuery) throws SupplierException {
        List<List<Object>> resultList = new ArrayList<List<Object>>();//返回的数据List
        List<Supplier> list = supplierDao.select(supplierQuery);//查询的List
        Integer step = 0;
        for (Supplier supplier : list) {
            List<Object> beanList = new ArrayList<Object>();
            ++step;
            String provinceName = !StringUtils.isEmpty(supplier.getProvinceName()) ? supplier.getProvinceName() : "";
            String cityName = !StringUtils.isEmpty(supplier.getCityName()) ? supplier.getCityName() : "";
            String districeName = !StringUtils.isEmpty(supplier.getDistrictName()) ? supplier.getDistrictName() : "";
            String address = !StringUtils.isEmpty(supplier.getAddress()) ? supplier.getAddress() : "";
            String allAddress = provinceName + " " + cityName + " " + districeName + " " + address;
            beanList.add(step);
            beanList.add(supplier.getSupplierDocumentId());
            beanList.add(supplier.getSupplierName());
            beanList.add(supplier.getAbbreviationName());
            beanList.add(supplier.getContacts());
            beanList.add(supplier.getPhone());
            beanList.add(supplier.getTel());
            beanList.add(MapConsts.getSupplierType().get(supplier.getTypeId()));
            beanList.add(allAddress);
            beanList.add(MapConsts.getSupplierStatus().get(supplier.getSupplierStatus()));
            resultList.add(beanList);
        }
        return resultList;
    }

    /**
     * 去除goodsId为空的数据
     *
     * @param supplierGoodsList 要去空的list
     * @return supplierGoodsList
     */
    private List<SupplierGoods> removeGoods(List<SupplierGoods> supplierGoodsList) {
        for (int i = supplierGoodsList.size() - 1; i >= 0; i--) {
            if (!StringUtils.isNotBlank(supplierGoodsList.get(i).getGoodsId())) {
                supplierGoodsList.remove(i);
            }
        }
        return supplierGoodsList;
    }

    /**
     * 插入供应商商品(单条插入)
     *
     * @param supplier      供应商对象
     * @param supplierGoods 商品对象
     * @throws SupplierException
     */
    private void insertSupplierGoods(SupplierCreateRequest supplier, SupplierGoods supplierGoods) throws SupplierException {
        supplierGoods.setSupplierId(supplier.getSupplierId());
        supplierGoods.setSupplierGoodsId(IdUtil.uuid());
        supplierGoods.setBrandId(supplier.getBrandId());
        if (StringUtils.isNotBlank(supplier.getStoreId())) {
            supplierGoods.setStoreId(supplier.getStoreId());
        }
        int insertGoodsRow = supplierGoodsDao.insert(supplierGoods);
        if (insertGoodsRow <= 0) {
            LOGGER.info("插入失败商品{}" + supplierGoods.getGoodsId());
            throw new SupplierException("插入商品失败。");
        }
    }
}

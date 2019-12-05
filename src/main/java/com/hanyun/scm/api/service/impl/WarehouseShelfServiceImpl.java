package com.hanyun.scm.api.service.impl;

import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.IdGenerateType;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.WarehouseAreaDao;
import com.hanyun.scm.api.dao.WarehouseShelfDao;
import com.hanyun.scm.api.dao.WarehouseShelfGoodsDao;
import com.hanyun.scm.api.dao.WarehouseShelfTypeDao;
import com.hanyun.scm.api.domain.WarehouseArea;
import com.hanyun.scm.api.domain.WarehouseShelf;
import com.hanyun.scm.api.domain.WarehouseShelfGoods;
import com.hanyun.scm.api.domain.WarehouseShelfType;
import com.hanyun.scm.api.domain.request.warehouse.GoodsShelfQueryRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseShelfCreateRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseShelfModifyRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseShelfQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.domain.result.ShelfGoodsResult;
import com.hanyun.scm.api.service.IdGenerateSeqService;
import com.hanyun.scm.api.service.WarehouseShelfService;
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
 * WarehouseShelfServiceImpl
 * Date: 2017/7/13
 * Time: 上午11:56
 *
 * @author tianye@hanyun.com
 */
@Service
public class WarehouseShelfServiceImpl implements WarehouseShelfService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseShelfServiceImpl.class);

    @Resource
    private WarehouseShelfDao warehouseShelfDao;

    @Resource
    private WarehouseShelfGoodsDao warehouseShelfGoodsDao;

    @Resource
    private IdGenerateSeqService idGenerateSeqService;

    @Resource
    private WarehouseShelfTypeDao warehouseShelfTypeDao;

    @Resource
    private WarehouseAreaDao warehouseAreaDao;

    @Override
    public HttpResponse select(WarehouseShelfQueryRequest warehouseShelfQueryRequest) {
        try {
            int count = warehouseShelfDao.countAll(warehouseShelfQueryRequest);
            warehouseShelfQueryRequest.setCount(count);
            List<WarehouseShelf> warehouseShelfList = warehouseShelfDao.select(warehouseShelfQueryRequest);
            for (WarehouseShelf warehouseShelf : warehouseShelfList) {
                WarehouseShelfType warehouseShelfType = warehouseShelfTypeDao.selectById(warehouseShelf.getShelfTypeId());
                if (warehouseShelfType != null) {
                    warehouseShelf.setShelfTypeName(warehouseShelfType.getShelfTypeName());
                    warehouseShelf.setShelfStorageNum(warehouseShelfType.getShelfStorageNum());
                    warehouseShelf.setShelfHeight(warehouseShelfType.getShelfHeight());
                    warehouseShelf.setShelfLength(warehouseShelfType.getShelfLength());
                    warehouseShelf.setShelfWidth(warehouseShelfType.getShelfWidth());
                }
                WarehouseArea warehouseArea = warehouseAreaDao.selectById(warehouseShelf.getAreaId());
                if (warehouseArea != null) {
                    warehouseShelf.setAreaName(warehouseArea.getAreaName());
                    warehouseShelf.setAreaType(warehouseArea.getAreaType());
                }
            }
            return HttpResponse.success(new BaseResponse(count, warehouseShelfList));
        } catch (Exception e) {
            LOGGER.error("查询货架列表失败!", e);
            return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_QUERY_ERROR);
        }
    }

    @Override
    public HttpResponse insert(WarehouseShelfCreateRequest warehouseShelfCreateRequest) {
        String shelfId = "";
        int count = 0;
        try {
            shelfId = IdUtil.uuid();
            String shelfDocumentId = idGenerateSeqService.generateId(IdGenerateType.HJ);
            warehouseShelfCreateRequest.setShelfId(shelfId);
            warehouseShelfCreateRequest.setShelfDocumentId(shelfDocumentId);
            count = warehouseShelfDao.insertSelective(warehouseShelfCreateRequest);
            if (count == 0) {
                return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_INSERT_ERROR);
            }
            List<WarehouseShelfGoods> goodsList = warehouseShelfCreateRequest.getGoodsList();
            Set<String> goodsSet = new HashSet<>();
            if (goodsList != null && goodsList.size() > 0) {
                for (WarehouseShelfGoods warehouseShelfGoods : goodsList) {
                    warehouseShelfGoods.setShelfId(shelfId);
                    warehouseShelfGoods.setBrandId(warehouseShelfCreateRequest.getBrandId());
                    warehouseShelfGoods.setStoreId(warehouseShelfCreateRequest.getStoreId());
                    warehouseShelfGoods.setWarehouseId(warehouseShelfCreateRequest.getWarehouseId());
                    goodsSet.add(warehouseShelfGoods.getGoodsId());
                }
                int goodsCount = warehouseShelfGoodsDao.batchInserts(goodsList);
                if (goodsCount == 0) {
                    warehouseShelfDao.deleteById(shelfId);
                    return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_INSERT_ERROR);
                }
            }
            WarehouseShelf updateParam = new WarehouseShelf();
            updateParam.setShelfId(shelfId);
            updateParam.setGoodsNum((long)goodsSet.size());
            warehouseShelfDao.updateById(updateParam);
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("添加货架失败!", e);
            if (count > 0) {
                try {
                    warehouseShelfDao.deleteById(shelfId);
                } catch (Exception e1) {
                    LOGGER.error("删除货架失败!", e1);
                }
            }
            return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_INSERT_ERROR);
        }
    }

    @Override
    public HttpResponse update(WarehouseShelfModifyRequest warehouseShelfModifyRequest) {
        try {
            WarehouseShelf old = warehouseShelfDao.selectById(warehouseShelfModifyRequest.getShelfId());
            if (old == null) {
                return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_DATA_NOT_FOUND);
            }
            warehouseShelfDao.updateById(warehouseShelfModifyRequest);
            List<WarehouseShelfGoods> goodsList = warehouseShelfModifyRequest.getGoodsList();
            Set<String> goodsSet = new HashSet<>();
            if (goodsList != null && goodsList.size() > 0) {
                warehouseShelfGoodsDao.deleteByShelfId(old.getShelfId());
                for (WarehouseShelfGoods warehouseShelfGoods : goodsList) {
                    warehouseShelfGoods.setShelfId(old.getShelfId());
                    warehouseShelfGoods.setBrandId(old.getBrandId());
                    warehouseShelfGoods.setStoreId(old.getStoreId());
                    warehouseShelfGoods.setWarehouseId(warehouseShelfModifyRequest.getWarehouseId()==null?old.getWarehouseId():warehouseShelfModifyRequest.getWarehouseId());
                    goodsSet.add(warehouseShelfGoods.getGoodsId());
                }
                warehouseShelfGoodsDao.batchInserts(goodsList);
            } else {
                warehouseShelfGoodsDao.deleteByShelfId(old.getShelfId());
            }
            WarehouseShelf updateParam = new WarehouseShelf();
            updateParam.setShelfId(old.getShelfId());
            updateParam.setGoodsNum((long)goodsSet.size());
            warehouseShelfDao.updateById(updateParam);
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("更新货架信息失败!", e);
            return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_MODIFY_ERROR);
        }
    }

    @Override
    public HttpResponse detail(String shelfId) {
        try {
            WarehouseShelf warehouseShelf = warehouseShelfDao.selectById(shelfId);
            if (warehouseShelf == null) {
                return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_DATA_NOT_FOUND);
            }
            WarehouseShelfGoods query = new WarehouseShelfGoods();
            query.setShelfId(shelfId);
            List<WarehouseShelfGoods> goodsList = warehouseShelfGoodsDao.select(query);
            if (goodsList != null) {
                warehouseShelf.setGoodsList(goodsList);
            }
            return HttpResponse.success(warehouseShelf);
        } catch (Exception e) {
            LOGGER.error("查询货架详情失败!", e);
            return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_DETAIL_ERROR);
        }
    }

    @Override
    public HttpResponse delete(String shelfId) {
        try {
            WarehouseShelf warehouseShelf = warehouseShelfDao.selectById(shelfId);
            if (warehouseShelf == null) {
                return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_DATA_NOT_FOUND);
            }
            int count = warehouseShelfDao.deleteById(shelfId);
            if (count == 0) {
                return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_DELETE_ERROR);
            }
            warehouseShelfGoodsDao.deleteByShelfId(shelfId);
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("删除货架失败!", e);
            return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_DELETE_ERROR);
        }
    }

    @Override
    public HttpResponse shelfGoods(GoodsShelfQueryRequest goodsShelfQueryRequest) {
        try {
            List<WarehouseShelfGoods> goodsList = warehouseShelfGoodsDao.selectGoodsShelf(goodsShelfQueryRequest);
            WarehouseShelfQueryRequest query = new WarehouseShelfQueryRequest();
            query.setBrandId(goodsShelfQueryRequest.getBrandId());
            query.setWarehouseId(goodsShelfQueryRequest.getWarehouseId());
            List<WarehouseShelf> shelfList = warehouseShelfDao.select(query);
            Map<String, WarehouseShelf> shelfMap = new HashMap<>();
            for (WarehouseShelf shelf : shelfList) {
                shelfMap.put(shelf.getShelfId(), shelf);
            }
            Map<String, List<ShelfGoodsResult>> resultMap = new HashMap<>();
            for (WarehouseShelfGoods goods : goodsList) {
                WarehouseShelf shelf = shelfMap.get(goods.getShelfId());
                ShelfGoodsResult shelfGoodsResult = JsonUtil.fromJson(JsonUtil.toJson(shelf), ShelfGoodsResult.class);
                shelfGoodsResult.setGoodsId(goods.getGoodsId());
                shelfGoodsResult.setStorageNo(goods.getShelfStorageNo());
                List<ShelfGoodsResult> shelfGoodsResultList = resultMap.get(goods.getGoodsId());
                if (shelfGoodsResultList == null) {
                    shelfGoodsResultList = new ArrayList<>();
                }
                shelfGoodsResultList.add(shelfGoodsResult);
            }
            return HttpResponse.success(resultMap);
        } catch (Exception e) {
            LOGGER.error("查询商品货架位置失败!", e);
            return HttpResponse.failure(ResultCode.GOODS_SHELF_QUERY_ERROR);
        }
    }
}

package com.hanyun.scm.api.service.impl;

import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.StockPickingDao;
import com.hanyun.scm.api.dao.WarehouseDao;
import com.hanyun.scm.api.domain.StockQuant;
import com.hanyun.scm.api.domain.Warehouse;
import com.hanyun.scm.api.domain.request.stock.StockPickingRequest;
import com.hanyun.scm.api.domain.request.stock.StockQuantQueryRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseCreateRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseModifyRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.exception.WarehouseException;
import com.hanyun.scm.api.service.StockQuantService;
import com.hanyun.scm.api.service.WarehouseService;
import org.apache.commons.lang.StringUtils;
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
 * WarehouseServiceImpl
 * Date: 2016/10/21
 * Time: 上午11:16
 *
 * @author tianye@hanyun.com
 */
@Service
public class WarehouseServiceImpl implements WarehouseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseServiceImpl.class);

    @Resource
    private WarehouseDao warehouseDao;

    @Resource
    private StockQuantService stockQuantService;

    @Resource
    private StockPickingDao stockPickingDao;

    @Override
    public HttpResponse create(WarehouseCreateRequest warehouseCreateRequest) {
        try {
            //查询仓库名称是否有相同
            if (!StringUtils.isEmpty(warehouseCreateRequest.getWarehouseName())) {
                Warehouse warehouse = new Warehouse();
                warehouse.setBrandId(warehouseCreateRequest.getBrandId());
                warehouse.setStoreId(warehouseCreateRequest.getStoreId());
                warehouse.setWarehouseName(warehouseCreateRequest.getWarehouseName());
                Warehouse resultWarehouse = warehouseDao.queryWarehouseName(warehouse);
                if (resultWarehouse != null) {
                    return HttpResponse.failure(ResultCode.WAREHOUSENAME_QUERY_ERROR);
                }
            }
            String warehouseId = IdUtil.uuid();
            warehouseCreateRequest.setWarehouseId(warehouseId);
            warehouseCreateRequest.setWarehouseStatus(Consts.WAREHOUSE_STATUS_ENABLE);
            warehouseCreateRequest.setValidStatus(Consts.VALID_STATUS_VALID);
            int insertRow = warehouseDao.insertSelective(warehouseCreateRequest);
            if (insertRow <= 0) {
                LOGGER.error("创建仓库失败!");
                return HttpResponse.failure(ResultCode.WAREHOUSE_CREATE_ERROR);
            }
            return HttpResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("创建仓库失败!", e);
            return HttpResponse.failure(ResultCode.WAREHOUSE_SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse select(WarehouseQueryRequest warehouseQueryRequest) {
        try {
            BaseResponse baseResponse = selectList(warehouseQueryRequest, false);
            return HttpResponse.success(baseResponse);
        } catch (Exception e) {
            LOGGER.error("查询仓库失败!", e);
            return HttpResponse.failure(ResultCode.WAREHOUSE_SYSTEM_ERROR);
        }
    }
 
    @Override
    public HttpResponse selectDistributionCenter(WarehouseQueryRequest warehouseQueryRequest) {
        try {
            BaseResponse baseResponse = selectList(warehouseQueryRequest, true);
            return HttpResponse.success(baseResponse);
        } catch (Exception e) {
            LOGGER.error("查询仓库失败!", e);
            return HttpResponse.failure(ResultCode.WAREHOUSE_SYSTEM_ERROR);
        }
    }
  
    @Override
    public HttpResponse modify(WarehouseModifyRequest warehouseModifyRequest) {
        try {
            Warehouse old;
            if (StringUtils.isEmpty(warehouseModifyRequest.getWarehouseId())) {
                WarehouseQueryRequest warehouseQueryRequest = new WarehouseQueryRequest();
                warehouseQueryRequest.setBrandId(warehouseModifyRequest.getBrandId());
                warehouseQueryRequest.setStoreId(warehouseModifyRequest.getStoreId());
                List<Warehouse> warehouseList = warehouseDao.select(warehouseQueryRequest);
                if (warehouseList == null || warehouseList.size() != 1) {
                    return HttpResponse.failure(ResultCode.WAREHOUSE_MODIFY_ERROR);
                }
                old = warehouseList.get(0);
                warehouseModifyRequest.setWarehouseId(old.getWarehouseId());
            } else {
                old = warehouseDao.selectByWarehouseId(warehouseModifyRequest.getWarehouseId());
            }
            if (old == null) {
                return HttpResponse.failure(ResultCode.WAREHOUSE_DATA_NOT_FOUND);
            }
            String warehouseId = warehouseModifyRequest.getWarehouseId();
            String warehouseName = warehouseModifyRequest.getWarehouseName();
            if (!StringUtils.isEmpty(warehouseName)) {
                WarehouseQueryRequest query = new WarehouseQueryRequest();
                query.setBrandId(old.getBrandId());
                query.setWarehouseName(warehouseName);
                List<Warehouse> warehouseList = warehouseDao.select(query);
                if (warehouseList.size() > 1) {
                    return HttpResponse.failure(ResultCode.WAREHOUSENAME_QUERY_ERROR);
                }
                if (warehouseList.size() == 1 && !StringUtils.equals(warehouseList.get(0).getWarehouseId(), warehouseId)) {
                    return HttpResponse.failure(ResultCode.WAREHOUSENAME_QUERY_ERROR);
                }
            }
            if (warehouseModifyRequest.getWarehouseStatus() != null && warehouseModifyRequest.getWarehouseStatus() == Consts.WAREHOUSE_STATUS_DISABLE) {
                StockQuantQueryRequest query = new StockQuantQueryRequest();
                query.setWarehouseId(warehouseModifyRequest.getWarehouseId());
                query.setWithPage(Consts.QUERY_ALL);
                //查询本仓库库存记录
                List<StockQuant> stockQuantList = stockQuantService.selectWithParam(query).getList();
                if (stockQuantList != null) {
                    for (StockQuant stockQuant : stockQuantList) {
                        if (stockQuant.getStockNum() > 0) {
                            return HttpResponse.failure(ResultCode.WAREHOUSE_CAN_NOT_DELETE);
                        }
                    }
                }
                //查询是否有待入库单据
                StockPickingRequest stockPickingRequest = new StockPickingRequest();
                stockPickingRequest.setBrandId(old.getBrandId());
                stockPickingRequest.setToWarehouseId(warehouseModifyRequest.getWarehouseId());
                stockPickingRequest.setStockPickingStatus(Consts.PURCHASE_RETURN_STATUS_SAVED);
                int count = stockPickingDao.countAll(stockPickingRequest);
                if (count > 0) {
                    return HttpResponse.failure(ResultCode.WAREHOUSE_CAN_NOT_DELETE);
                }
            }
            int updateRow = warehouseDao.updateByWarehouseId(warehouseModifyRequest);
            if (updateRow <= 0) {
                LOGGER.error("修改仓库信息失败!");
                return HttpResponse.failure(ResultCode.WAREHOUSE_MODIFY_ERROR);
            }
            return HttpResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("修改仓库信息失败!", e);
            return HttpResponse.failure(ResultCode.WAREHOUSE_SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse delete(String warehouseId) {
        try {
            int deleteRow = warehouseDao.deleteByWarehouseId(warehouseId);
            if (deleteRow <= 0) {
                LOGGER.error("删除仓库失败!");
                return HttpResponse.failure(ResultCode.WAREHOUSE_DELETE_ERROR);
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("删除仓库信息失败!", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
    /**以下代码没用到**/
    @Override
    public HttpResponse detail(String warehouseId) {
        try {
            Warehouse warehouse = warehouseDao.selectByWarehouseId(warehouseId);
            if (warehouse == null) {
                return HttpResponse.failure(ResultCode.WAREHOUSE_DATA_NOT_FOUND);
            }
            return HttpResponse.success(warehouse);
        } catch (Exception e) {
            LOGGER.error("查看仓库详情失败!", e);
            return HttpResponse.failure(ResultCode.WAREHOUSE_SYSTEM_ERROR);
        }
    }
    /**以上代码没用到**/
    /**
     * 查询仓库
     *
     * @param warehouseQueryRequest 参数
     * @param isDistribution        是否为配送中心(true表示是配送中心,false不是)
     * @return 返回值
     * @throws WarehouseException 异常
     */
    private BaseResponse selectList(WarehouseQueryRequest warehouseQueryRequest, boolean isDistribution) throws WarehouseException {
        if (isDistribution) {
            warehouseQueryRequest.setWarehouseType(Consts.WAREHOUSE_TYPE_DISTRIBUTION_CENTER);
        }
        int count = warehouseDao.countAll(warehouseQueryRequest);
        warehouseQueryRequest.setCount(count);
        List<Warehouse> warehouseList = warehouseDao.select(warehouseQueryRequest);
        return new BaseResponse(count, warehouseList);
    }

    @Override
    public Map<String, Warehouse> getWarehouseMap(String brandId, String storeId, Integer warehouseStatus) {
        Map<String, Warehouse> map = new HashMap<>();
        WarehouseQueryRequest warehouse = new WarehouseQueryRequest();
        warehouse.setBrandId(brandId);
        if(!StringUtils.isEmpty(storeId)){
            warehouse.setStoreId(storeId);
        }
        warehouse.setWarehouseStatus(warehouseStatus);
        List<Warehouse> warehouseList = warehouseDao.select(warehouse);
        for(Warehouse house : warehouseList){
            map.put(house.getWarehouseId(),house);
        }
        return map;
    }
}

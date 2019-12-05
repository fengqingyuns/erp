package com.hanyun.scm.api.service.impl;

import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.WarehouseShelfDao;
import com.hanyun.scm.api.dao.WarehouseShelfTypeDao;
import com.hanyun.scm.api.domain.WarehouseShelf;
import com.hanyun.scm.api.domain.WarehouseShelfType;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseShelfTypeCreateRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseShelfTypeModifyRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseShelfTypeQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.service.WarehouseShelfTypeService;
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
 * WarehouseShelfTypeServiceImpl
 * Date: 2017/7/13
 * Time: 上午11:55
 *
 * @author tianye@hanyun.com
 */
@Service
public class WarehouseShelfTypeServiceImpl implements WarehouseShelfTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseShelfTypeServiceImpl.class);

    @Resource
    private WarehouseShelfTypeDao warehouseShelfTypeDao;

    @Resource
    private WarehouseShelfDao warehouseShelfDao;

    @Override
    public HttpResponse select(WarehouseShelfTypeQueryRequest warehouseShelfTypeQueryRequest) {
        try {
            int count = warehouseShelfTypeDao.countAll(warehouseShelfTypeQueryRequest);
            warehouseShelfTypeQueryRequest.setCount(count);
            List<WarehouseShelfType> warehouseShelfTypeList = warehouseShelfTypeDao.select(warehouseShelfTypeQueryRequest);
            return HttpResponse.success(new BaseResponse(count, warehouseShelfTypeList));
        } catch (Exception e) {
            LOGGER.error("查询货架分类列表失败!", e);
            return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_TYPE_QUERY_ERROR);
        }
    }

    @Override
    public HttpResponse insert(WarehouseShelfTypeCreateRequest warehouseShelfTypeCreateRequest) {
        try {
            String shelfTypeId = IdUtil.uuid();
            warehouseShelfTypeCreateRequest.setShelfTypeId(shelfTypeId);
            int row = warehouseShelfTypeDao.insertSelective(warehouseShelfTypeCreateRequest);
            if (row == 0) {
                return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_TYPE_INSERT_ERROR);
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("添加货架分类失败!", e);
            return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_TYPE_INSERT_ERROR);
        }
    }

    @Override
    public HttpResponse update(WarehouseShelfTypeModifyRequest warehouseShelfTypeModifyRequest) {
        try {
            WarehouseShelfType old = warehouseShelfTypeDao.selectById(warehouseShelfTypeModifyRequest.getShelfTypeId());
            if (old == null) {
                return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_TYPE_INSERT_ERROR);
            }
            int row = warehouseShelfTypeDao.updateByPrimaryKeySelective(warehouseShelfTypeModifyRequest);
            if (row == 0) {
                return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_TYPE_MODIFY_ERROR);
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("更新货架分类失败!", e);
            return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_TYPE_MODIFY_ERROR);
        }
    }

    @Override
    public HttpResponse detail(String shelfTypeId) {
        try {
            WarehouseShelfType warehouseShelfType = warehouseShelfTypeDao.selectById(shelfTypeId);
            if (warehouseShelfType == null) {
                return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_TYPE_DATA_NOT_FOUND);
            }
            return HttpResponse.success(warehouseShelfType);
        } catch (Exception e) {
            LOGGER.error("查询货架分类详情失败!", e);
            return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_TYPE_DETAIL_ERROR);
        }
    }

    @Override
    public HttpResponse delete(String shelfTypeId) {
        try {
            WarehouseShelfType warehouseShelfType = warehouseShelfTypeDao.selectById(shelfTypeId);
            if (warehouseShelfType == null) {
                return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_TYPE_DATA_NOT_FOUND);
            }
            WarehouseShelf query = new WarehouseShelf();
            query.setBrandId(warehouseShelfType.getBrandId());
            query.setShelfTypeId(warehouseShelfType.getShelfTypeId());
            List<WarehouseShelf> warehouseShelfList = warehouseShelfDao.select(query);
            if (warehouseShelfList != null && warehouseShelfList.size() > 0) {
                return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_TYPE_WAREHOUSE_SHELF_EXIST);
            }
            int row = warehouseShelfTypeDao.deleteById(shelfTypeId);
            if (row == 0) {
                return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_TYPE_DELETE_ERROR);
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("删除货架分类失败!", e);
            return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_TYPE_DELETE_ERROR);
        }
    }
}

package com.hanyun.scm.api.service.impl;

import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.WarehouseAreaDao;
import com.hanyun.scm.api.dao.WarehouseShelfDao;
import com.hanyun.scm.api.domain.WarehouseArea;
import com.hanyun.scm.api.domain.WarehouseShelf;
import com.hanyun.scm.api.domain.WarehouseShelfType;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseAreaCreateRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseAreaModifyRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseAreaQueryRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseShelfQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.service.WarehouseAreaService;
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
 * WarehouseAreaServiceImpl
 * Date: 2017/7/12
 * Time: 下午6:22
 *
 * @author tianye@hanyun.com
 */
@Service
public class WarehouseAreaServiceImpl implements WarehouseAreaService {

    private Logger LOGGER = LoggerFactory.getLogger(WarehouseAreaServiceImpl.class);

    @Resource
    private WarehouseAreaDao warehouseAreaDao;

    @Resource
    private WarehouseShelfDao warehouseShelfDao;

    @Override
    public HttpResponse select(WarehouseAreaQueryRequest warehouseAreaQueryRequest) {
        try {
            int count = warehouseAreaDao.countAll(warehouseAreaQueryRequest);
            warehouseAreaQueryRequest.setCount(count);
            List<WarehouseArea> warehouseAreaList = warehouseAreaDao.select(warehouseAreaQueryRequest);
            for (WarehouseArea warehouseArea : warehouseAreaList) {
                WarehouseShelfQueryRequest warehouseShelfQueryRequest = new WarehouseShelfQueryRequest();
                warehouseShelfQueryRequest.setBrandId(warehouseArea.getBrandId());
                warehouseShelfQueryRequest.setAreaId(warehouseArea.getAreaId());
                long shelfNum = warehouseShelfDao.countAll(warehouseShelfQueryRequest);
                warehouseArea.setShelfNum(shelfNum);
            }
            return HttpResponse.success(new BaseResponse(count,warehouseAreaList));
        } catch (Exception e) {
            LOGGER.error("仓库区域查询失败!", e);
            return HttpResponse.failure(ResultCode.WAREHOUSE_AREA_QUERY_PARAM_ERROR);
        }
    }

    @Override
    public HttpResponse insert(WarehouseAreaCreateRequest warehouseAreaCreateRequest) {
        try {
            String areaId = IdUtil.uuid();
            warehouseAreaCreateRequest.setAreaId(areaId);
            int row = warehouseAreaDao.insertSelective(warehouseAreaCreateRequest);
            if (row == 0) {
                return HttpResponse.failure(ResultCode.WAREHOUSE_AREA_INSERT_ERROR);
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("仓库区域添加失败!", e);
            return HttpResponse.failure(ResultCode.WAREHOUSE_AREA_INSERT_ERROR);
        }
    }

    @Override
    public HttpResponse update(WarehouseAreaModifyRequest warehouseAreaModifyRequest) {
        try {
            WarehouseArea old = warehouseAreaDao.selectById(warehouseAreaModifyRequest.getAreaId());
            if (old == null) {
                return HttpResponse.failure(ResultCode.WAREHOUSE_AREA_DATA_NOT_FOUND);
            }
            int row = warehouseAreaDao.updateById(warehouseAreaModifyRequest);
            if (row == 0) {
                return HttpResponse.failure(ResultCode.WAREHOUSE_AREA_MODIFY_ERROR);
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("更新仓库区域信息失败", e);
            return HttpResponse.failure(ResultCode.WAREHOUSE_AREA_MODIFY_ERROR);
        }
    }

    @Override
    public HttpResponse detail(String areaId) {
        try {
            WarehouseArea warehouseArea = warehouseAreaDao.selectById(areaId);
            if (warehouseArea == null) {
                return HttpResponse.failure(ResultCode.WAREHOUSE_AREA_DATA_NOT_FOUND);
            }
            return HttpResponse.success(warehouseArea);
        } catch (Exception e) {
            LOGGER.error("查询仓库区域详情失败!", e);
            return HttpResponse.failure(ResultCode.WAREHOUSE_AREA_DETAIL_ERROR);
        }
    }

    @Override
    public HttpResponse delete(String areaId) {
        try {
            WarehouseArea warehouseArea = warehouseAreaDao.selectById(areaId);
            if (warehouseArea == null) {
                return HttpResponse.failure(ResultCode.WAREHOUSE_AREA_DATA_NOT_FOUND);
            }
            WarehouseShelf query = new WarehouseShelf();
            query.setBrandId(warehouseArea.getBrandId());
            query.setAreaId(warehouseArea.getAreaId());
            List<WarehouseShelf> warehouseShelfList = warehouseShelfDao.select(query);
            if (warehouseShelfList != null && warehouseShelfList.size() > 0) {
                return HttpResponse.failure(ResultCode.WAREHOUSE_AREA_WAREHOUSE_SHELF_EXIST);
            }
            int row = warehouseAreaDao.deleteById(areaId);
            if (row == 0) {
                return HttpResponse.failure(ResultCode.WAREHOUSE_AREA_DELETE_ERROR);
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("删除仓库区域信息失败!", e);
            return HttpResponse.failure(ResultCode.WAREHOUSE_AREA_DELETE_ERROR);
        }
    }
}

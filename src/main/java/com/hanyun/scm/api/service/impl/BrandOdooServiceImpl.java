package com.hanyun.scm.api.service.impl;

import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.BrandOdooDao;
import com.hanyun.scm.api.dao.WarehouseDao;
import com.hanyun.scm.api.domain.BrandOdoo;
import com.hanyun.scm.api.domain.request.brand.BrandCreateRequest;
import com.hanyun.scm.api.domain.request.brand.BrandModifyRequest;
import com.hanyun.scm.api.domain.request.brand.BrandQueryRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseCreateRequest;
import com.hanyun.scm.api.service.BrandOdooService;
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
 * BrandOdooServiceImpl
 * Date: 2016/10/21
 * Time: 下午6:48
 *
 * @author tianye@hanyun.com
 */
@Service
public class BrandOdooServiceImpl implements BrandOdooService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrandOdooServiceImpl.class);

    @Resource
    private BrandOdooDao brandOdooDao;

    @Resource
    private WarehouseDao warehouseDao;

    @Override
    public HttpResponse create(BrandCreateRequest brandCreateRequest) {
        try {
            BrandOdoo old = brandOdooDao.selectByBrandId(brandCreateRequest.getBrandId());
            if (old != null) {
                LOGGER.error("创建品牌失败!");
                return HttpResponse.failure(ResultCode.BRAND_ODOO_ID_EXIST);
            }
            brandOdooDao.insertSelective(brandCreateRequest);
            WarehouseCreateRequest warehouse = new WarehouseCreateRequest();
            warehouse.setWarehouseId(IdUtil.uuid());
            warehouse.setBrandId(brandCreateRequest.getBrandId());
            warehouse.setWarehouseName(brandCreateRequest.getBrandName());
            warehouse.setResupply(Consts.WAREHOUSE_RESUPPLY_TRUE);
            warehouse.setWarehouseType(Consts.WAREHOUSE_TYPE_BRAND);
            int row = warehouseDao.insertSelective(warehouse);
            if (row > 0) {
                return HttpResponse.success(ResultCode.SUCCESS);
            }
            return HttpResponse.failure(ResultCode.BRAND_ODOO_CREATE_ERROR);
        } catch (Exception e) {
            LOGGER.error("创建品牌失败!", e);
            return HttpResponse.failure(ResultCode.BRAND_ODOO_CREATE_ERROR);
        }
    }

    @Override
    public HttpResponse select(BrandQueryRequest brandQueryRequest) {
        try {
            int count = brandOdooDao.countAll(brandQueryRequest);
            brandQueryRequest.setCount(count);
            List<BrandOdoo> brandOdooList = brandOdooDao.select(brandQueryRequest);
            return HttpResponse.success(brandOdooList);
        } catch (Exception e) {
            LOGGER.error("查询品牌列表失败!", e);
            return HttpResponse.failure(ResultCode.BRAND_ODOO_QUERY_ERROR);
        }
    }

    @Override
    public HttpResponse detail(String brandId) {
        try {
            BrandOdoo brandOdoo = brandOdooDao.selectByBrandId(brandId);
            if (brandOdoo == null) {
                return HttpResponse.failure(ResultCode.BRAND_ODOO_DATA_NOT_FOUND);
            }
            return HttpResponse.success(brandOdoo);
        } catch (Exception e) {
            LOGGER.error("查询品牌详情失败!", e);
            return HttpResponse.failure(ResultCode.BRAND_ODOO_QUERY_ERROR);
        }
    }

    @Override
    public HttpResponse modify(BrandModifyRequest brandModifyRequest) {
        try {
            BrandOdoo old = brandOdooDao.selectByBrandId(brandModifyRequest.getBrandId());
            if (old == null) {
                return HttpResponse.failure(ResultCode.BRAND_ODOO_DATA_NOT_FOUND);
            }
            int row = brandOdooDao.updateByBrandId(brandModifyRequest);
            if (row > 0) {
                return HttpResponse.success(ResultCode.SUCCESS);
            }
            return HttpResponse.failure(ResultCode.BRAND_ODOO_MODIFY_ERROR);
        } catch (Exception e) {
            LOGGER.error("修改品牌信息失败!", e);
            return HttpResponse.failure(ResultCode.BRAND_ODOO_MODIFY_ERROR);
        }

    }

    @Override
    public HttpResponse delete(String brandId) {
        try {
            BrandOdoo brandOdoo = brandOdooDao.selectByBrandId(brandId);
            if (brandOdoo == null) {
                return HttpResponse.failure(ResultCode.BRAND_ODOO_DATA_NOT_FOUND);
            }
            int row = brandOdooDao.deleteByBrandId(brandId);
            if (row > 0) {
                return HttpResponse.success(ResultCode.SUCCESS);
            }
            return HttpResponse.failure(ResultCode.BRAND_ODOO_DELETE_ERROR);
        } catch (Exception e) {
            LOGGER.error("删除品牌信息失败!", e);
            return HttpResponse.failure(ResultCode.BRAND_ODOO_DELETE_ERROR);
        }
    }
}

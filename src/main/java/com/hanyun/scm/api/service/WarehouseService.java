package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.Warehouse;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseCreateRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseModifyRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseQueryRequest;

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
 * WarehouseService
 * Date: 2016/10/21
 * Time: 上午11:16
 *
 * @author tianye@hanyun.com
 */
public interface WarehouseService {

    /**
     * 创建仓库
     *
     * @param warehouseCreateRequest 创建仓库请求参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse create(WarehouseCreateRequest warehouseCreateRequest);

    /**
     * 查询仓库
     *
     * @param warehouseQueryRequest 查询仓库请求参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse select(WarehouseQueryRequest warehouseQueryRequest);

    /**
     * 查询配送中心
     *
     * @param warehouseQueryRequest 查询配送中心请求参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse selectDistributionCenter(WarehouseQueryRequest warehouseQueryRequest);

    /**
     * 修改仓库信息
     *
     * @param warehouseModifyRequest 修改仓库请求参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse modify(WarehouseModifyRequest warehouseModifyRequest);

    /**
     * 删除仓库
     *
     * @param warehouseId 仓库id
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse delete(String warehouseId);

    /**
     * 查询仓库详情
     *
     * @param warehouseId 仓库id
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse detail(String warehouseId);

    /**
     * 获取通过仓库ID仓库对象map
     * @param brandId 品牌id
     * @param storeId 门店Id
     * @param warehouseStatus 仓库状态
     * @return worehouseMap
     */
    Map<String, Warehouse> getWarehouseMap(String brandId, String storeId, Integer warehouseStatus);
}

package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseAreaCreateRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseAreaModifyRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseAreaQueryRequest;

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
 * WarehouseAreaService
 * Date: 2017/7/12
 * Time: 下午6:17
 *
 * @author tianye@hanyun.com
 */
public interface WarehouseAreaService {

    /**
     * 查询仓库区域列表
     *
     * @param warehouseAreaQueryRequest 参数
     * @return 返回值
     */
    HttpResponse select(WarehouseAreaQueryRequest warehouseAreaQueryRequest);

    /**
     * 添加仓库区域
     *
     * @param warehouseAreaCreateRequest 参数
     * @return 返回值
     */
    HttpResponse insert(WarehouseAreaCreateRequest warehouseAreaCreateRequest);

    /**
     * 更新仓库区域
     *
     * @param warehouseAreaModifyRequest 参数
     * @return 返回值
     */
    HttpResponse update(WarehouseAreaModifyRequest warehouseAreaModifyRequest);

    /**
     * 查询仓库区域详情
     *
     * @param areaId 区域id
     * @return 返回值
     */
    HttpResponse detail(String areaId);

    /**
     * 删除仓库区域
     *
     * @param areaId 区域id
     * @return 返回值
     */
    HttpResponse delete(String areaId);
}

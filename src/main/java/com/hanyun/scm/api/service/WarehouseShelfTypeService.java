package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseShelfTypeCreateRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseShelfTypeModifyRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseShelfTypeQueryRequest;

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
 * WarehouseShelfTypeService
 * Date: 2017/7/12
 * Time: 下午6:18
 *
 * @author tianye@hanyun.com
 */
public interface WarehouseShelfTypeService {

    /**
     * 查询仓库货架类型列表
     *
     * @param warehouseShelfTypeQueryRequest 参数
     * @return 返回值
     */
    HttpResponse select(WarehouseShelfTypeQueryRequest warehouseShelfTypeQueryRequest);

    /**
     * 添加仓库货架类型
     *
     * @param warehouseShelfTypeCreateRequest 参数
     * @return 返回值
     */
    HttpResponse insert(WarehouseShelfTypeCreateRequest warehouseShelfTypeCreateRequest);

    /**
     * 更新残酷货架类型
     *
     * @param warehouseShelfTypeModifyRequest 参数
     * @return 返回值
     */
    HttpResponse update(WarehouseShelfTypeModifyRequest warehouseShelfTypeModifyRequest);

    /**
     * 查询货架分类详情
     *
     * @param shelfTypeId 货架分类id
     * @return 返回值
     */
    HttpResponse detail(String shelfTypeId);

    /**
     * 删除货架分类
     *
     * @param shelfTypeId 货架分类id
     * @return 返回值
     */
    HttpResponse delete(String shelfTypeId);
}

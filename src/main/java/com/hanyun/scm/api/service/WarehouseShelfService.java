package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.request.warehouse.GoodsShelfQueryRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseShelfCreateRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseShelfModifyRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseShelfQueryRequest;

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
 * WarehouseShelfService
 * Date: 2017/7/12
 * Time: 下午6:18
 *
 * @author tianye@hanyun.com
 */
public interface WarehouseShelfService {

    /**
     * 查询货架列表
     *
     * @param warehouseShelfQueryRequest 参数
     * @return 返回值
     */
    HttpResponse select(WarehouseShelfQueryRequest warehouseShelfQueryRequest);

    /**
     * 添加货架
     *
     * @param warehouseShelfCreateRequest 参数
     * @return 返回值
     */
    HttpResponse insert(WarehouseShelfCreateRequest warehouseShelfCreateRequest);

    /**
     * 更新货架信息
     *
     * @param warehouseShelfModifyRequest 参数
     * @return 返回值
     */
    HttpResponse update(WarehouseShelfModifyRequest warehouseShelfModifyRequest);

    /**
     * 查询货架详情
     *
     * @param shelfId 货架id
     * @return 返回值
     */
    HttpResponse detail(String shelfId);

    /**
     * 删除货架
     *
     * @param shelfId 货架id
     * @return 返回值
     */
    HttpResponse delete(String shelfId);

    /**
     * 查询商品货架位置
     * @param goodsShelfQueryRequest    参数
     * @return  返回值
     */
    HttpResponse shelfGoods(GoodsShelfQueryRequest goodsShelfQueryRequest);
}

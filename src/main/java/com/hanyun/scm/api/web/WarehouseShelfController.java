package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.request.warehouse.GoodsShelfQueryRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseShelfCreateRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseShelfModifyRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseShelfQueryRequest;
import com.hanyun.scm.api.service.WarehouseShelfService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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
 * WarehouseShelfController
 * Date: 2017/7/12
 * Time: 下午6:10
 *
 * @author tianye@hanyun.com
 */
@RestController
@RequestMapping("/warehouse-shelf")
public class WarehouseShelfController {

    @Resource
    private WarehouseShelfService warehouseShelfService;

    /**
     * 查询仓库货架列表
     *
     * @param warehouseShelfQueryRequest 参数
     * @param bindingResult              参数验证信息
     * @return 返回值
     */
    @GetMapping
    public HttpResponse query(@Valid  WarehouseShelfQueryRequest warehouseShelfQueryRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_QUERY_PARAM_ERROR);
        }
        return warehouseShelfService.select(warehouseShelfQueryRequest);
    }

    /**
     * 创建货架
     *
     * @param warehouseShelfCreateRequest 参数
     * @param bindingResult               参数验证信息
     * @return
     */
    @PostMapping
    public HttpResponse create(@Valid @RequestBody WarehouseShelfCreateRequest warehouseShelfCreateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_INSERT_PARAM_ERROR);
        }
        return warehouseShelfService.insert(warehouseShelfCreateRequest);
    }

    /**
     * 更新货架信息
     *
     * @param warehouseShelfModifyRequest 参数
     * @param bindingResult               参数验证信息
     * @return 返回值
     */
    @PutMapping
    public HttpResponse modify(@Valid @RequestBody WarehouseShelfModifyRequest warehouseShelfModifyRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_MODIFY_PARAM_ERROR);
        }
        return warehouseShelfService.update(warehouseShelfModifyRequest);
    }

    /**
     * 查询货架详情
     *
     * @param shelfId 货架id
     * @return 返回值
     */
    @GetMapping("/{shelfId}")
    public HttpResponse detail(@PathVariable String shelfId) {
        return warehouseShelfService.detail(shelfId);
    }

    /**
     * 删除货架
     *
     * @param shelfId 货架id
     * @return 返回值
     */
    @DeleteMapping("/{shelfId}")
    public HttpResponse delete(@PathVariable String shelfId) {
        return warehouseShelfService.delete(shelfId);
    }

    /**
     * 查询商品位置
     * @param goodsShelfQueryRequest    参数
     * @return  返回值
     */
    @PostMapping("/goods-shelf")
    public HttpResponse shelfGoods(@Valid @RequestBody GoodsShelfQueryRequest goodsShelfQueryRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.GOODS_SHELF_QUERY_PARAM_ERROR);
        }
        return warehouseShelfService.shelfGoods(goodsShelfQueryRequest);
    }
}

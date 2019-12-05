package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseShelfTypeCreateRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseShelfTypeModifyRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseShelfTypeQueryRequest;
import com.hanyun.scm.api.service.WarehouseShelfTypeService;
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
 * WarehouseShelfTypeController
 * Date: 2017/7/12
 * Time: 下午6:11
 *
 * @author tianye@hanyun.com
 */
@RestController
@RequestMapping("/warehouse-shelf-type")
public class WarehouseShelfTypeController {

    @Resource
    private WarehouseShelfTypeService warehouseShelfTypeService;

    /**
     * 查询仓库货架分类列表
     *
     * @param warehouseShelfTypeQueryRequest 参数
     * @param bindingResult                  参数验证信息
     * @return 返回值
     */
    @GetMapping
    public HttpResponse query(@Valid WarehouseShelfTypeQueryRequest warehouseShelfTypeQueryRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_TYPE_QUERY_PARAM_ERROR);
        }
        return warehouseShelfTypeService.select(warehouseShelfTypeQueryRequest);
    }

    /**
     * 创建仓库货架分类
     *
     * @param warehouseShelfTypeCreateRequest 参数
     * @param bindingResult                   参数验证信息
     * @return 返回值
     */
    @PostMapping
    public HttpResponse create(@Valid @RequestBody WarehouseShelfTypeCreateRequest warehouseShelfTypeCreateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_TYPE_INSERT_PARAM_ERROR);
        }
        return warehouseShelfTypeService.insert(warehouseShelfTypeCreateRequest);
    }

    /**
     * 更新仓库货架分类
     *
     * @param warehouseShelfTypeModifyRequest 参数
     * @param bindingResult                   参数验证信息
     * @return 返回值
     */
    @PutMapping
    public HttpResponse modify(@Valid @RequestBody WarehouseShelfTypeModifyRequest warehouseShelfTypeModifyRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.WAREHOUSE_SHELF_TYPE_MODIFY_PARAM_ERROR);
        }
        return warehouseShelfTypeService.update(warehouseShelfTypeModifyRequest);
    }

    /**
     * 查询仓库货架分类详情
     *
     * @param shelfTypeId 货架分类id
     * @return 返回值
     */
    @GetMapping("/{shelfTypeId}")
    public HttpResponse detail(@PathVariable String shelfTypeId) {
        return warehouseShelfTypeService.detail(shelfTypeId);
    }

    /**
     * 删除仓库货架分类
     *
     * @param shelfTypeId 分类id
     * @return 返回值
     */
    @DeleteMapping("/{shelfTypeId}")
    public HttpResponse delete(@PathVariable String shelfTypeId) {
        return warehouseShelfTypeService.delete(shelfTypeId);
    }
}

package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseAreaCreateRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseAreaModifyRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseAreaQueryRequest;
import com.hanyun.scm.api.service.WarehouseAreaService;
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
 * WarehouseAreaController
 * Date: 2017/7/12
 * Time: 下午6:11
 *
 * @author tianye@hanyun.com
 */
@RestController
@RequestMapping("/warehouse-area")
public class WarehouseAreaController {

    @Resource
    private WarehouseAreaService warehouseAreaService;

    /**
     * 查询仓库区域列表
     *
     * @param warehouseAreaQueryRequest 参数
     * @param bindingResult             参数验证信息
     * @return 返回值
     */
    @GetMapping
    public HttpResponse query(@Valid WarehouseAreaQueryRequest warehouseAreaQueryRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.WAREHOUSE_AREA_QUERY_PARAM_ERROR);
        }
        return warehouseAreaService.select(warehouseAreaQueryRequest);
    }

    /**
     * 添加仓库区域
     *
     * @param warehouseAreaCreateRequest 参数
     * @param bindingResult              参数验证信息
     * @return 返回值
     */
    @PostMapping
    public HttpResponse add(@Valid @RequestBody WarehouseAreaCreateRequest warehouseAreaCreateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.WAREHOUSE_AREA_INSERT_PARAM_ERROR);
        }
        return warehouseAreaService.insert(warehouseAreaCreateRequest);
    }

    /**
     * 更新仓库区域
     *
     * @param warehouseAreaModifyRequest 参数
     * @param bindingResult              参数验证信息
     * @return 返回值
     */
    @PutMapping
    public HttpResponse modify(@Valid @RequestBody WarehouseAreaModifyRequest warehouseAreaModifyRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.WAREHOUSE_AREA_MODIFY_PARAM_ERROR);
        }
        return warehouseAreaService.update(warehouseAreaModifyRequest);
    }

    /**
     * 查询仓库区域详情
     *
     * @param areaId 区域id
     * @return 返回值
     */
    @GetMapping("/{areaId}")
    public HttpResponse detail(@PathVariable String areaId) {
        return warehouseAreaService.detail(areaId);
    }

    /**
     * 删除仓库区域
     *
     * @param areaId 区域id
     * @return 返回值
     */
    @DeleteMapping("/{areaId}")
    public HttpResponse delete(@PathVariable String areaId) {
        return warehouseAreaService.delete(areaId);
    }

}

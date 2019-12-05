package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseCreateRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseModifyRequest;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseQueryRequest;
import com.hanyun.scm.api.service.WarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
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
 * WarehouseController
 * Date: 2016/10/21
 * Time: 上午11:14
 *
 * @author tianye@hanyun.com
 */
@Controller
@RequestMapping(value = "/warehouse")
@Api(value = "warehouse", description = "仓库相关接口")
public class WarehouseController {

    @Resource
    private WarehouseService warehouseService;

    /**
     * 创建仓库
     *
     * @param warehouseCreateRequest 创建仓库请求参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    @PostMapping(value = "/create")
    @ResponseBody
    @ApiOperation(value = "创建仓库", notes = "传入参数创建仓库")
    public HttpResponse create(@Valid @RequestBody WarehouseCreateRequest warehouseCreateRequest) {
        return warehouseService.create(warehouseCreateRequest);
    }

    /**
     * 查询仓库列表
     *
     * @param warehouseQueryRequest 查询仓库请求参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    @GetMapping(value = "/query")
    @ResponseBody
    public HttpResponse select(@Valid WarehouseQueryRequest warehouseQueryRequest) {
        return warehouseService.select(warehouseQueryRequest);
    }
  
    /**
     * 查询配送中心列表
     *
     * @param warehouseQueryRequest 查询配送中心请求参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    @GetMapping(value = "/distribution-center/query")
    @ResponseBody
    public HttpResponse selectDistributionCenter(@Valid WarehouseQueryRequest warehouseQueryRequest) {
        return warehouseService.selectDistributionCenter(warehouseQueryRequest);
    }
   
    /**
     * 修改仓库
     *
     * @param warehouseModifyRequest 修改仓库请求参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    @PostMapping(value = "/modify")
    @ResponseBody
    public HttpResponse modify(@Valid @RequestBody WarehouseModifyRequest warehouseModifyRequest) {
        return warehouseService.modify(warehouseModifyRequest);
    }
    /**以下代码没用到**/
    /**
     * 修改门店仓库
     *
     * @param warehouseModifyRequest 修改仓库请求参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    @PostMapping(value = "/modify-by-store")
    @ResponseBody
    public HttpResponse modifyByStoreId(@RequestBody WarehouseModifyRequest warehouseModifyRequest) {
        return warehouseService.modify(warehouseModifyRequest);
    }
    /**以上代码没用到**/
    /**
     * 删除仓库
     *
     * @param warehouseId 仓库id
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    @GetMapping(value = "/delete/{warehouseId}")
    @ResponseBody
    public HttpResponse delete(@PathVariable String warehouseId) {
        return warehouseService.delete(warehouseId);
    }
    /**以下代码没用到**/
    /**
     * 查询仓库详情
     *
     * @param warehouseId 仓库id
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    @GetMapping(value = "/detail/{warehouseId}")
    @ResponseBody
    public HttpResponse detail(@PathVariable String warehouseId) {
        return warehouseService.detail(warehouseId);
    }
    /**以上代码没用到**/
}

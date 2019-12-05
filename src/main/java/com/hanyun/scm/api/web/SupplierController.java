package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ExcelTitle;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.ExcelBaseBean;
import com.hanyun.scm.api.domain.dto.SupplierDTO;
import com.hanyun.scm.api.domain.request.supplier.*;
import com.hanyun.scm.api.exception.SupplierException;
import com.hanyun.scm.api.service.SupplierService;
import com.hanyun.scm.api.web.util.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
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
 * <p>
 * SupplierController
 * Date: 2016/10/18
 * Time: 下午9:15
 *
 * @author tianye@hanyun.com
 */
@Controller
@RequestMapping(value = "/supplier")
public class SupplierController {


    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierController.class);

    @Resource
    private SupplierService supplierService;

    /**
     * 创建供应商
     *
     * @param supplierCreate
     * @return
     */
    @PostMapping(value = "/create")
    @ResponseBody
    public HttpResponse createSupplier(@RequestBody @Valid SupplierCreateRequest supplierCreate) {
        return supplierService.create(supplierCreate);
    }

    /**
     * 查询供应商列表
     *
     * @param supplierQuery
     * @return
     */
    @GetMapping(value = "/query")
    @ResponseBody
    public HttpResponse select(@Valid SupplierQueryRequest supplierQuery) {
        return supplierService.select(supplierQuery);
    }

    /**
     * 修改供应商
     *
     * @param supplierModify
     * @return
     */
    @PostMapping(value = "/modify")
    @ResponseBody
    public HttpResponse modifySupplier(@RequestBody @Valid SupplierModifyRequest supplierModify) {
        return supplierService.modifySupplier(supplierModify);
    }
   
    /**
     * 供应商停用、启用
     *
     * @param supplier 供应商修改对象
     * @return HttpResponse
     */
    @PutMapping(value = "/modifyAvailableStatus")
    @ResponseBody
    public HttpResponse modifyAvailableStatus(@RequestBody @Valid SupplierModifyStatusRequest supplier, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("修改供应商参数错误");
            return HttpResponse.failure(ResultCode.SUPPLIER_MODIFY_PARAM_ERROR);
        }
        return supplierService.modifyAvailableStatus(supplier);
    }
   
    /**
     * 删除供应商->改状态
     *
     * @param supplierModify
     * @return
     */
    @PostMapping(value = "/modify/delete")
    @ResponseBody
    public HttpResponse deleteSupplierByStatus(@RequestBody @Valid SupplierDeleteStatusRequest supplierModify) {
        return supplierService.deleteSupplierByStatus(supplierModify);
    }
   
    /**
     * 删除供应商
     *
     * @param supplierId
     * @return
     */
    @GetMapping(value = "/delete/{supplierId}")
    @ResponseBody
    public HttpResponse deleteSupplier(@PathVariable String supplierId) {
        return supplierService.deleteSupplier(supplierId);
    }
   
    /**以下代码没用到**/
    /**
     * 获取供应商列表
     * @param dto 接口对象
     * @return httpResponse
     */
    @PostMapping(value = "query/supplier-detail")
    @ResponseBody
    public HttpResponse querySupplierGoodsList(@Valid @RequestBody SupplierDTO dto){
        return supplierService.querySupplierGoodsList(dto);
    }
    /**以上代码没用到**/
    /**
     * 查询供应商详情
     *
     * @param supplierId
     * @return
     */
    @GetMapping(value = "/detail/{supplierId}")
    @ResponseBody
    public HttpResponse detail(@PathVariable String supplierId) {
        return supplierService.detail(supplierId);
    }

    /**
     * 导出供应商
     *
     * @param supplierQuery
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/exportBySupplier")
    public void exportBySupplier(@Valid SupplierQueryRequest supplierQuery, HttpServletResponse response) throws SupplierException {
        // 导出数据data
        List<List<Object>> dataList;
        dataList = supplierService.exportExcelBySuppleir(supplierQuery);
        //标题
        List<String> titles = new ArrayList<>();
        titles.add(ExcelTitle.INDEX.getName());
        titles.add(ExcelTitle.SUPPLIER_CODE.getName());
        titles.add(ExcelTitle.SUPPLIER_NAME.getName());
        titles.add(ExcelTitle.ABBREVIATIONNAME.getName());
        titles.add(ExcelTitle.CONTACTS.getName());
        titles.add(ExcelTitle.PHONE.getName());
        titles.add(ExcelTitle.TEL.getName());
        titles.add(ExcelTitle.TYPE.getName());
        titles.add(ExcelTitle.ADDRESS.getName());
        titles.add(ExcelTitle.AVAILABLE_STATUS.getName());

        ExcelBaseBean excelBaseBean = new ExcelBaseBean();
        excelBaseBean.setData(dataList);
        excelBaseBean.setTitles(titles);
        excelBaseBean.setXlsName("供应商导出");
        try {
            ExcelUtil.dynamicExport(excelBaseBean, response);
        } catch (Exception e) {
            LOGGER.error("供应商--导出失败");
            throw new SupplierException("供应商--导出失败");
        }
    }

}
 
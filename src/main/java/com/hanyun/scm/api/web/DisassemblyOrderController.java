package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ExcelTitle;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.DisassemblyOrder;
import com.hanyun.scm.api.domain.DisassemblyOrderDetail;
import com.hanyun.scm.api.domain.ExcelBaseBean;
import com.hanyun.scm.api.domain.request.disassembly.DisassemblyOrderCreateRequest;
import com.hanyun.scm.api.domain.request.disassembly.DisassemblyOrderModifyRequest;
import com.hanyun.scm.api.domain.request.disassembly.DisassemblyOrderQueryRequest;
import com.hanyun.scm.api.exception.DisassemblyException;
import com.hanyun.scm.api.service.DisassemblyOrderService;
import com.hanyun.scm.api.web.util.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
 * StockCheckOrderController
 * Date: 2017/1/13
 * Time: 下午16:03
 *
 * @author 1007661792@qq.com
 */
@Controller
@RequestMapping(value = "/disassembly-order")
public class DisassemblyOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisassemblyOrderController.class);

    @Resource
    private DisassemblyOrderService disassemblyOrderService;

    /**
     * 创建拆装单信息
     *
     * @param disassemblyOrderCreateRequest 创建拆装单参数
     * @return HttpResponse
     */
    @PostMapping(value = "/create")
    @ResponseBody
    public HttpResponse create(@RequestBody @Valid DisassemblyOrderCreateRequest disassemblyOrderCreateRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("创建拆装单参数错误！");
            return HttpResponse.failure(ResultCode.DISASSEMBLYORDER_CREATE_PARAM_ERROR);
        }
        return disassemblyOrderService.create(disassemblyOrderCreateRequest);
    }

    /**
     * 查询拆装单信息
     *
     * @param disassemblyOrderQueryRequest 查询拆装单参数
     * @return HttpResponse
     */
    @GetMapping(value = "/query")
    @ResponseBody
    public HttpResponse select(@Valid DisassemblyOrderQueryRequest disassemblyOrderQueryRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("查询拆装单参数错误");
            return HttpResponse.failure(ResultCode.DISASSEMBLYORDER_QUERY_PARAM_ERROR);
        }
        return disassemblyOrderService.select(disassemblyOrderQueryRequest);
    }

    /**
     * 修改拆装单信息
     *
     * @param disassemblyOrderModifyRequest 修改拆装单参数
     * @return HttpResponse
     */
    @PutMapping(value = "/modify")
    @ResponseBody
    public HttpResponse modify(@RequestBody @Valid DisassemblyOrderModifyRequest disassemblyOrderModifyRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("修改拆装单参数错误");
            return HttpResponse.failure(ResultCode.DISASSEMBLYORDER_MODIFY_PARAM_ERROR);
        }
        return disassemblyOrderService.modify(disassemblyOrderModifyRequest);
    }

    /**
     * 提交审核
     * @param disassemblyOrderId   订单id
     * @return
     */
    @GetMapping(value = "/commit/{disassemblyOrderId}")
    @ResponseBody
    public HttpResponse commit(@PathVariable String disassemblyOrderId) {
        return disassemblyOrderService.commit(disassemblyOrderId);
    }

    /**
     * 查询拆装单详情信息
     *
     * @param disassemblyOrderId 拆装单id
     * @return HttpResponse
     */
    @GetMapping(value = "/detail/{disassemblyOrderId}")
    @ResponseBody
    public HttpResponse detail(@PathVariable String disassemblyOrderId) {
        return disassemblyOrderService.detail(disassemblyOrderId);
    }

    /**
     * 删除拆装单信息
     *
     * @param disassemblyOrderId 拆装单id
     * @return HttpResponse
     */
    @DeleteMapping(value = "/delete/{disassemblyOrderId}")
    @ResponseBody
    public HttpResponse delete(@PathVariable String disassemblyOrderId) {
        return disassemblyOrderService.delete(disassemblyOrderId);
    }

    /**
     * 导出拆装单信息
     *
     * @param DisassemblyOrder
     * @param response
     * @throws DisassemblyException
     */
    @GetMapping(value = "/exportDisassemblyOrder")
    public void exportDisassemblyOrder(@Valid DisassemblyOrderQueryRequest DisassemblyOrder, HttpServletResponse response) throws DisassemblyException {
        //导出数据
        List<List<Object>> dataList = new ArrayList<>();
        dataList = disassemblyOrderService.exportExcelBySuppleir(DisassemblyOrder);
        //标题
        List<String> titles = new ArrayList<String>();
        titles.add(ExcelTitle.INDEX.getName());
        titles.add(ExcelTitle.DISASSEMBLY_ID.getName());
        titles.add(ExcelTitle.ORDER_TIME.getName());
        titles.add(ExcelTitle.ORDER_TYPE.getName());
        titles.add(ExcelTitle.MOTHER_GOODSNAME.getName());
        titles.add(ExcelTitle.MOTHER_GOODSNUMBER.getName());
        titles.add(ExcelTitle.SON_GOODSNAME.getName());
        titles.add(ExcelTitle.SON_GOODSNUMBER.getName());
        titles.add(ExcelTitle.MAKE_ORDER_NAME.getName());
        titles.add(ExcelTitle.MAKE_ORDER_TIME.getName());
        titles.add(ExcelTitle.EXAMINE.getName());
        titles.add(ExcelTitle.EXAMINT_TIME.getName());
        titles.add(ExcelTitle.ORDER_STATUS.getName());

        ExcelBaseBean excelBaseBean = new ExcelBaseBean();
        excelBaseBean.setTitles(titles);
        excelBaseBean.setData(dataList);
        excelBaseBean.setXlsName("拆装单导出");
        try {
            ExcelUtil.dynamicExport(excelBaseBean, response);
        } catch (Exception e) {
            LOGGER.error("供应商--导出失败",e);
            throw new DisassemblyException("供应商--导出失败");
        }
    }

    /**
     * 导出拆装单详情
     *
     * @param disassemblyOrderId
     * @param response
     * @throws DisassemblyException
     */
    @GetMapping(value = "/exportDisassemblyOrder/{disassemblyOrderId}")
    public void exportDisassemblyOrder(@PathVariable String disassemblyOrderId, HttpServletResponse response) throws DisassemblyException {
        //导出数据
        try {
            DisassemblyOrder disassemblyOrder = disassemblyOrderService.getDisassemblyOrder(disassemblyOrderId);
            List<DisassemblyOrderDetail> disassemblyOrderDetailList = disassemblyOrder.getDisassemblyOrderDetailList();
            String xlsName = disassemblyOrder.getDisassemblyOrderDocumentId();
            String templateName = "disassembly_order_detail.xls";
            String[] attributes = new String[]{"goodsCode", "goodsBarCode", "goodsName", "features", "goodsBrandName",
                    "unitName", "stockNum", "unitPrice", "totalPrice", "disassemblyGoodsTypeName"};
            ExcelUtil.export(xlsName, templateName, disassemblyOrderDetailList, attributes, response);
        } catch (Exception e) {
            throw new DisassemblyException("导出申请单明细失败!", e);
        }
    }
}

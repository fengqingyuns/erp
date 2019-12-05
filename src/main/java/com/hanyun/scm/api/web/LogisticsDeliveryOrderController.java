package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ExcelTitle;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.ExcelBaseBean;
import com.hanyun.scm.api.domain.request.LogisticsDelivery.LogisticsDeliveryCreateRequest;
import com.hanyun.scm.api.domain.request.LogisticsDelivery.LogisticsDeliveryModifyRequest;
import com.hanyun.scm.api.domain.request.LogisticsDelivery.LogisticsDeliveryQueryRequest;
import com.hanyun.scm.api.exception.LogisticsDeliveryException;
import com.hanyun.scm.api.service.LogisticsDeliveryOrderService;
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
 * LogisticsDeliveryOrderController
 * Date: 17/3/13
 * Time: 下午17:01
 *
 * @author 1007661792@qq.com
 */
@Controller
@RequestMapping(value = "/delivery-order")
public class LogisticsDeliveryOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogisticsDeliveryOrderController.class);

    @Resource
    private LogisticsDeliveryOrderService logisticsDeliveryOrderService;

    /**
     * 创建物流送货单信息
     *
     * @param logisticsDeliveryCreateRequest 创建物流送货单信息参数
     * @return HttpResponse
     */
    @PostMapping(value = "/create")
    @ResponseBody
    public HttpResponse create(@RequestBody @Valid LogisticsDeliveryCreateRequest logisticsDeliveryCreateRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("创建物流送货单参数错误！");
            return HttpResponse.failure(ResultCode.LOGISTICSDELIVERY_CREATE_PARAM_ERROR);
        }
        return logisticsDeliveryOrderService.create(logisticsDeliveryCreateRequest);
    }

    /**
     * 查询物流送货单信息
     *
     * @param logisticsDeliveryQueryRequest 查询物流送货单参数
     * @return HttpResponse
     */
    @GetMapping(value = "/query")
    @ResponseBody
    public HttpResponse select(@Valid LogisticsDeliveryQueryRequest logisticsDeliveryQueryRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("查询物流送货单参数错误");
            return HttpResponse.failure(ResultCode.LOGISTICSDELIVERY_QUERY_PARAM_ERROR);
        }
        return logisticsDeliveryOrderService.select(logisticsDeliveryQueryRequest);
    }

    /**
     * 修改物流送货单信息
     *
     * @param logisticsDeliveryModifyRequest 修改物流送货单信息参数
     * @return HttpResponse
     */
    @PutMapping(value = "/modify")
    @ResponseBody
    public HttpResponse modify(@RequestBody @Valid LogisticsDeliveryModifyRequest logisticsDeliveryModifyRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("修改物流送货单参数错误");
            return HttpResponse.failure(ResultCode.LOGISTICSDELIVERY_MODIFY_PARAM_ERROR);
        }
        return logisticsDeliveryOrderService.modify(logisticsDeliveryModifyRequest);
    }

    /**
     * 提审
     *
     * @param logisticsDeliveryOrderId 物流送货单id
     * @return HttpResponse
     */
    @GetMapping(value = "/commit/{logisticsDeliveryOrderId}")
    @ResponseBody
    public HttpResponse commit(@PathVariable String logisticsDeliveryOrderId) {
        return logisticsDeliveryOrderService.commit(logisticsDeliveryOrderId);
    }

    /**
     * 查询物流送货单详情信息
     *
     * @param logisticsDeliveryOrderId 物流送货单id
     * @return HttpResponse
     */
    @GetMapping(value = "/detail/{logisticsDeliveryOrderId}")
    @ResponseBody
    public HttpResponse detail(@PathVariable String logisticsDeliveryOrderId) {
        return logisticsDeliveryOrderService.detail(logisticsDeliveryOrderId);
    }

    /**
     * 删除物流送货单信息
     *
     * @param logisticsDeliveryOrderId 物流送货单id
     * @return HttpResponse
     */
    @DeleteMapping(value = "/delete/{logisticsDeliveryOrderId}")
    @ResponseBody
    public HttpResponse delete(@PathVariable String logisticsDeliveryOrderId) {
        return logisticsDeliveryOrderService.delete(logisticsDeliveryOrderId);
    }

    /**
     * 导出物流送货单信息
     *
     * @param logisticsDeliveryQueryRequest 导出物流送货单信息参数
     * @param response                      返回
     * @throws LogisticsDeliveryException
     */
    @GetMapping(value = "/exportByLogisticsDelivery")
    public void exportDistributionOrder(@Valid LogisticsDeliveryQueryRequest logisticsDeliveryQueryRequest, HttpServletResponse response) throws LogisticsDeliveryException {
        //导出数据
        List<List<Object>> dataList = logisticsDeliveryOrderService.exportByLogisticsDelivery(logisticsDeliveryQueryRequest);
        //标题
        List<String> titles = new ArrayList<String>();
        titles.add(ExcelTitle.INDEX.getName());
        titles.add(ExcelTitle.LOGISTICSDELIVERY_ID.getName());
        titles.add(ExcelTitle.DELIVERY_ORDER_ID.getName());
        titles.add(ExcelTitle.DISTRIBUTION_CENTER.getName());
        titles.add(ExcelTitle.DELIVERGOODS_DATE.getName());
        titles.add(ExcelTitle.MAKE_ORDER_NAME.getName());
        titles.add(ExcelTitle.MAKE_ORDER_TIME.getName());
        titles.add(ExcelTitle.ORDER_STATUS.getName());

        ExcelBaseBean excelBaseBean = new ExcelBaseBean();
        excelBaseBean.setData(dataList);
        excelBaseBean.setTitles(titles);
        excelBaseBean.setXlsName("物流送货单");
        try {
            ExcelUtil.dynamicExport(excelBaseBean, response);
        } catch (Exception e) {
            LOGGER.error("物流送货单--导出失败", e);
            throw new LogisticsDeliveryException("物流送货单--导出失败");
        }
    }

    /**
     * 导出物流送货单详情
     *
     * @param logisticsDeliveryOrderId 导出物流送货单id
     * @param response                 返回
     * @throws LogisticsDeliveryException
     */
    @GetMapping(value = "/exportBylogisticsDeliveryById/{logisticsDeliveryOrderId}")
    public void exportDistributionOrderById(@PathVariable String logisticsDeliveryOrderId, HttpServletResponse response) throws LogisticsDeliveryException {
        //导出数据
        List<List<Object>> dataList = logisticsDeliveryOrderService.exportBylogisticsDeliveryById(logisticsDeliveryOrderId);
        //标题
        List<String> titles = new ArrayList<String>();
        titles.add(ExcelTitle.INDEX.getName());
        titles.add(ExcelTitle.DISTRIBUTION_ID.getName());
        titles.add(ExcelTitle.GOODSRECEIPT.getName());
        titles.add(ExcelTitle.DISTRIBUTION_CENTER.getName());
        titles.add(ExcelTitle.MAKE_ORDER_NAME.getName());
        titles.add(ExcelTitle.MAKE_ORDER_TIME.getName());
        titles.add(ExcelTitle.DISTRIBUTIONORDER_FOR_REMARK.getName());

        ExcelBaseBean excelBaseBean = new ExcelBaseBean();
        excelBaseBean.setData(dataList);
        excelBaseBean.setTitles(titles);
        excelBaseBean.setXlsName("物流送货单详情导出");
        try {
            ExcelUtil.dynamicExport(excelBaseBean, response);
        } catch (Exception e) {
            LOGGER.error("物流送货单--导出失败", e);
            throw new LogisticsDeliveryException("物流送货单详情--导出失败");
        }
    }
}

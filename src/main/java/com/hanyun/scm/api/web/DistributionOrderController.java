package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ExcelTitle;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.ExcelBaseBean;
import com.hanyun.scm.api.domain.request.distribution.DistributionOrderCreateRequest;
import com.hanyun.scm.api.domain.request.distribution.DistributionOrderModifyRequest;
import com.hanyun.scm.api.domain.request.distribution.DistributionOrderQueryRequest;
import com.hanyun.scm.api.exception.DistributionOrderException;
import com.hanyun.scm.api.service.DistributionOrderService;
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
 * DistributionOrderController
 * Date: 17/3/7
 * Time: 下午14:45
 *
 * @author 1007661792@qq.com
 */
@Controller
@RequestMapping(value = "/distribution-order")
public class DistributionOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributionOrderController.class);

    @Resource
    private DistributionOrderService distributionOrderService;

    /**
     * 创建配送单信息
     *
     * @param distributionOrderCreateRequest 创建配送单信息参数
     * @return HttpResponse
     */
    @PostMapping(value = "/create")
    @ResponseBody
    public HttpResponse create(@RequestBody @Valid DistributionOrderCreateRequest distributionOrderCreateRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("创建配送单参数错误！");
            return HttpResponse.failure(ResultCode.DISTRIBUTIONORDER_CREATE_PARAM_ERROR);
        }
        return distributionOrderService.create(distributionOrderCreateRequest);
    }

    /**
     * 查询配送单信息
     *
     * @param distributionOrderQueryRequest 查询配送单参数
     * @return HttpResponse
     */
    @GetMapping(value = "/query")
    @ResponseBody
    public HttpResponse select(@Valid DistributionOrderQueryRequest distributionOrderQueryRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("查询配送单参数错误");
            return HttpResponse.failure(ResultCode.DISTRIBUTIONORDER_QUERY_PARAM_ERROR);
        }
        return distributionOrderService.select(distributionOrderQueryRequest);
    }

    /**
     * 修改配送单信息
     *
     * @param distributionOrderModifyRequest 修改配送单信息参数
     * @return HttpResponse
     */
    @PutMapping(value = "/modify")
    @ResponseBody
    public HttpResponse modify(@RequestBody @Valid DistributionOrderModifyRequest distributionOrderModifyRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("修改配送单参数错误");
            return HttpResponse.failure(ResultCode.DISTRIBUTIONORDER_MODIFY_PARAM_ERROR);
        }
        return distributionOrderService.modify(distributionOrderModifyRequest);
    }

    /**
     * 提交审核
     *
     * @param distributionOrderId 配送单id
     * @return
     */
    @GetMapping(value = "/commit/{distributionOrderId}")
    @ResponseBody
    public HttpResponse commit(@PathVariable String distributionOrderId) {
        return distributionOrderService.commit(distributionOrderId);
    }

    /**
     * 查询配送单详情信息
     *
     * @param distributionOrderId 配送单id
     * @return HttpResponse
     */
    @GetMapping(value = "/detail/{distributionOrderId}")
    @ResponseBody
    public HttpResponse detail(@PathVariable String distributionOrderId) {
        return distributionOrderService.detail(distributionOrderId);
    }

    /**
     * 删除配送单信息
     *
     * @param distributionOrderId 配送单id
     * @return HttpResponse
     */
    @DeleteMapping(value = "/delete/{distributionOrderId}")
    @ResponseBody
    public HttpResponse delete(@PathVariable String distributionOrderId) {
        return distributionOrderService.delete(distributionOrderId);
    }

    /**
     * 导出配送单信息
     *
     * @param DistributionOrder
     * @param response
     * @throws DistributionOrderException
     */
    @GetMapping(value = "/exportDistributionOrder")
    public void exportDistributionOrder(@Valid DistributionOrderQueryRequest DistributionOrder, HttpServletResponse response) throws DistributionOrderException {
        //导出数据
        List<List<Object>> dataList = distributionOrderService.exportByDistribution(DistributionOrder);
        //标题
        List<String> titles = new ArrayList<String>();
        titles.add(ExcelTitle.INDEX.getName());
        titles.add(ExcelTitle.DISTRIBUTION_ID.getName());
        titles.add(ExcelTitle.SOURCEAPPLY_ID.getName());
        titles.add(ExcelTitle.RECEIPT_NAME.getName());
        titles.add(ExcelTitle.DISTRIBUTION_CENTER.getName());
        titles.add(ExcelTitle.DISTRIBUTION_NUM.getName());
        titles.add(ExcelTitle.ORDER_AMOUT.getName());
        titles.add(ExcelTitle.MAKE_ORDER_NAME.getName());
        titles.add(ExcelTitle.MAKE_ORDER_TIME.getName());
        titles.add(ExcelTitle.ORDER_STATUS.getName());
        titles.add(ExcelTitle.RECEIPT_STATUS.getName());

        ExcelBaseBean excelBaseBean = new ExcelBaseBean();
        excelBaseBean.setData(dataList);
        excelBaseBean.setTitles(titles);
        excelBaseBean.setXlsName("配送单");
        try {
            ExcelUtil.dynamicExport(excelBaseBean, response);
        } catch (Exception e) {
            LOGGER.error("配送单--导出失败", e);
            throw new DistributionOrderException("配送单--导出失败");
        }
    }

    /**
     * 导出配送单详情
     *
     * @param distributionOrderId
     * @param response
     * @throws DistributionOrderException
     */
    @GetMapping(value = "/exportDistributionOrderById/{distributionOrderId}")
    public void exportDistributionOrderById(@PathVariable String distributionOrderId, HttpServletResponse response) throws DistributionOrderException {
        //导出数据
        List<List<Object>> dataList = distributionOrderService.exportByDistributionById(distributionOrderId);
        //标题
        List<String> titles = new ArrayList<String>();
        titles.add(ExcelTitle.INDEX.getName());
        titles.add(ExcelTitle.DISTRIBUTION_NUM.getName());
        titles.add(ExcelTitle.GOODS_SKUCODE.getName());
        titles.add(ExcelTitle.INTERNATIONAL_CODE.getName());
        titles.add(ExcelTitle.GOODS_NAME.getName());
        titles.add(ExcelTitle.SPECIFICATIONS.getName());
        titles.add(ExcelTitle.UNIT_NAME.getName());
        titles.add(ExcelTitle.GOODS_UNITPRICE.getName());
        titles.add(ExcelTitle.TOTAL_UNITPRICE.getName());
        titles.add(ExcelTitle.STORE_QUANT.getName());
        titles.add(ExcelTitle.CENTER_QUANT.getName());

        ExcelBaseBean excelBaseBean = new ExcelBaseBean();
        excelBaseBean.setData(dataList);
        excelBaseBean.setTitles(titles);
        excelBaseBean.setXlsName("配送单详情导出");
        try {
            ExcelUtil.dynamicExport(excelBaseBean, response);
        } catch (Exception e) {
            LOGGER.error("配送单--导出失败", e);
            throw new DistributionOrderException("配送单详情--导出失败");
        }
    }

    /**
     * 初始化配送单历史数据(收货状态/收货数量)
     * @return HttpResponse
     */
    @GetMapping(value = "/init-history")
    @ResponseBody
    public HttpResponse init(){
        return distributionOrderService.initStatusAndNum();
    }
}

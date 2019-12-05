package com.hanyun.scm.api.web;

import com.hanyun.scm.api.consts.ExcelTitle;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.ExcelBaseBean;
import com.hanyun.scm.api.domain.ReplenishmentApply;
import com.hanyun.scm.api.domain.dto.ReplenishmentDTO;
import com.hanyun.scm.api.domain.request.Replenishment.ReplenishmentApplyConfirmRequest;
import com.hanyun.scm.api.domain.request.Replenishment.ReplenishmentApplyRequest;
import com.hanyun.scm.api.exception.ReplenishmentApplyException;
import com.hanyun.scm.api.service.ReplenishmentApplyService;
import com.hanyun.scm.api.web.util.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: qiaoyu_v@hanhun.com
 * @date: 2017年3月7日 下午3:05:23
 */
@Controller
@RequestMapping(value = "/replenishmentApply")
public class ReplenishmentApplyController {
    @Resource
    private ReplenishmentApplyService replenishmentApplyService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReplenishmentApplyController.class);

    /**
     * 修改单据
     *
     * @param replenishmentApply;
     */
    @PutMapping(value = "/modify", produces = "application/json")
    @ResponseBody
    public Object modifyReplenishmentApply(@RequestBody ReplenishmentApply replenishmentApply) {
        return replenishmentApplyService.modifyOrder(replenishmentApply);
    }


    /**
     * 查询入库单
     *
     * @param replenishmentApplyRequest;
     */
    @GetMapping(value = "/query", produces = "application/json")
    @ResponseBody
    public Object select(ReplenishmentApplyRequest replenishmentApplyRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("参数错误");
        }
        return replenishmentApplyService.select(replenishmentApplyRequest);
    }


    /**
     * 查找订单信息
     */
    @GetMapping(value = "/queryById", produces = "application/json")
    @ResponseBody
    public Object queryById(ReplenishmentApplyRequest replenishmentApplyRequest) {
        return replenishmentApplyService.queryById(replenishmentApplyRequest);
    }

    /**
     * 添加补货申请单
     */
    @PostMapping(value = "/create", produces = "application/json")
    @ResponseBody
    public Object create(@RequestBody ReplenishmentApply replenishmentApply) {
        return replenishmentApplyService.create(replenishmentApply);
    }


    /**
     * @return
     * @Description: 补货申请单--导出
     */
    @GetMapping(value = "/exportReplenishmentApply")
    @ResponseBody
    public void exportReplenishmentApply(ReplenishmentApplyRequest replenishmentApplyRequest, HttpServletRequest request,
                                         HttpServletResponse response) throws ReplenishmentApplyException {
        List<ReplenishmentApply> list = replenishmentApplyService.exportReplenishmentApply(replenishmentApplyRequest);

        String xlsName = "补货申请单";
        String templateName = "exportReplenishmentApply.xls";
        String[] attributes = new String[]{"replenishmentDocumentId", "updateTime", "toStoreName", "distributionName", "applyNum",
                "totalPrice", "operatorName", "createTime", "exportOrderStatus", "exportGetStatus"};
        try {
            ExcelUtil.export(xlsName, templateName, list, attributes, response);
        } catch (Exception e) {
            LOGGER.error("导出补货申请单失败");
            throw new ReplenishmentApplyException("导出补货申请单失败", e);
        }
    }

    /**
     * @return
     * @Description: 补货申请单--导出
     */
    @GetMapping(value = "/exportReplenishmentApplyDetail")
    @ResponseBody
    public void exportReplenishmentApplyDetail(ReplenishmentApplyRequest replenishmentApplyRequest, HttpServletResponse response) throws ReplenishmentApplyException {
        //导出数据
        List<List<Object>> dataList = replenishmentApplyService.exportReplenishmentApplyDetail(replenishmentApplyRequest);
        //标题
        List<String> titles = new ArrayList<String>();
        titles.add(ExcelTitle.INDEX.getName());
        titles.add(ExcelTitle.GOODS_NAME.getName());
        titles.add(ExcelTitle.SPECIFICATIONS.getName());
        titles.add(ExcelTitle.GOODS_SKUCODE.getName());
        titles.add(ExcelTitle.GOODS_BAR_CODE.getName());
        titles.add(ExcelTitle.UNIT_NAME.getName());
        titles.add(ExcelTitle.RECEIPT_NUM.getName());
        titles.add(ExcelTitle.RECEIPTED_NUM.getName());
        titles.add(ExcelTitle.NOT_RECEIPT_NUM.getName());
        titles.add(ExcelTitle.GOODS_UNITPRICE.getName());
        titles.add(ExcelTitle.TOTAL_UNITPRICE.getName());
        titles.add(ExcelTitle.NOW_STOCKNUM.getName());
        titles.add(ExcelTitle.WARNING_NUMBER.getName());
        titles.add(ExcelTitle.GOODS_CLASSFIYNAME.getName());

        ExcelBaseBean excelBaseBean = new ExcelBaseBean();
        excelBaseBean.setData(dataList);
        excelBaseBean.setTitles(titles);
        excelBaseBean.setXlsName("补货申请详情导出");
        try {
            ExcelUtil.dynamicExport(excelBaseBean, response);
        } catch (Exception e) {
            LOGGER.error("导出补货申请单失败");
            throw new ReplenishmentApplyException("导出补货申请单失败", e);
        }
    }

    public HttpResponse queryStore(ReplenishmentApplyRequest replenishmentApplyRequest) {
        return replenishmentApplyService.queryStore(replenishmentApplyRequest);
    }

    /**
     * 审核
     *
     * @param ReplenishmentApplyConfirmRequest 审核参数
     * @return 返回信息
     */
    @PutMapping(value = "/confirm")
    @ResponseBody
    public HttpResponse confirm(@RequestBody @Valid ReplenishmentApplyConfirmRequest ReplenishmentApplyConfirmRequest) {
        return replenishmentApplyService.confirm(ReplenishmentApplyConfirmRequest);
    }

    /**
     * 提审
     *
     * @param replenishmentId 订单id
     * @return
     */
    @GetMapping(value = "/commit/{replenishmentId}")
    @ResponseBody
    public HttpResponse commit(@PathVariable String replenishmentId) {
        return replenishmentApplyService.commit(replenishmentId);
    }

    /**
     * 查询申请单商品详情
     * @param dto 参数对象
     * @return HttpResponse
     */
    @GetMapping(value = "/queryApplyDetail", produces = "application/json")
    @ResponseBody
    public HttpResponse queryReplenishmentGoods(@Valid ReplenishmentDTO dto, BindingResult result){
        if(result.hasErrors()){
            LOGGER.error("参数错误");
            return HttpResponse.failure(ResultCode.PARAM_ERROR);
        }
        return replenishmentApplyService.queryReplenishmentGoods(dto);
    }

    /**
     *  查询申请单下的配送单(收发货记录)
     * @param applyId 申请单id
     * @return HttpResponse
     */
    @GetMapping(value = "/queryApplyRecord/{applyId}")
    @ResponseBody
    public HttpResponse queryApplyForDistributionRecord(@PathVariable String applyId){
        return replenishmentApplyService.queryApplyForDistributionRecord(applyId);
    }

    /**
     * 关闭申请单接口(状态)
     * @param replenishmentId id
     * @return HttpResponse
     */
    @GetMapping(value = "/close/{replenishmentId}")
    @ResponseBody
    public HttpResponse close(@PathVariable String replenishmentId){
        return replenishmentApplyService.close(replenishmentId);
    }

}

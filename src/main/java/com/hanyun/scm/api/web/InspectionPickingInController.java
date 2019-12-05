package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ExcelTitle;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.ExcelBaseBean;
import com.hanyun.scm.api.domain.InspectionPickingIn;
import com.hanyun.scm.api.domain.request.InspectionPickingIn.InspectionPickingInDetailRequest;
import com.hanyun.scm.api.domain.request.InspectionPickingIn.InspectionPickingInRequest;
import com.hanyun.scm.api.exception.InspectionpickinginException;
import com.hanyun.scm.api.service.InspectionPickingInService;
import com.hanyun.scm.api.web.util.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "/inspection")
public class InspectionPickingInController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectionPickingInController.class);

    @Resource
    InspectionPickingInService inspectionPickingInService;

    /**
     * 查询验货入库单信息
     *
     * @param inspectionPickingInRequest 查询验货入库单信息；
     * @return HttpResponse
     */
    @GetMapping(value = "/query")
    @ResponseBody
    public HttpResponse select(@Valid InspectionPickingInRequest inspectionPickingInRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("查询验货入库单参数错误!");
            return HttpResponse.failure(ResultCode.INSPECTION_QUERY_ERROR);
        }
        return inspectionPickingInService.select(inspectionPickingInRequest);
    }

    /**
     * 删除单据
     * @param inspectionId;
     * @return HttpResponse;
     */
    @DeleteMapping(value = "/delete/{inspectionId}")
    @ResponseBody
    public HttpResponse deleteOrder(@PathVariable String inspectionId) {
        return inspectionPickingInService.deleteOrder(inspectionId);
    }

    /**
     * 创建或修改单据
     * @param inspectionPickingIn;
     * @return HttpResponse;
     */
    @PostMapping(value = "/create", produces = "application/json")
    @ResponseBody
    public HttpResponse create(@RequestBody InspectionPickingIn inspectionPickingIn) {
        return inspectionPickingInService.create(inspectionPickingIn);
    }

    /**
     * 查询商品信息
     * @param inspectionPickingInDetailRequest;
     * @param result;
     * @return HttpResponse;
     */
    @GetMapping(value = "/detail/query")
    @ResponseBody
    public HttpResponse selectDetail(@Valid InspectionPickingInDetailRequest inspectionPickingInDetailRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("查询验货入库单参数错误!");
            return HttpResponse.failure(ResultCode.INSPECTION_QUERY_ERROR);
        }
        return inspectionPickingInService.selectDetail(inspectionPickingInDetailRequest);
    }

    /**
     * 审核单据
     * @param inspectionPickingIn;
     * @param result;
     * @return HttpResponse;
     */
    @PutMapping(value = "/audit")
    @ResponseBody
    public HttpResponse auditOrder(@RequestBody InspectionPickingIn inspectionPickingIn, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("查询验货入库单参数错误!");
            return HttpResponse.failure(ResultCode.INSPECTION_QUERY_ERROR);
        }
        return inspectionPickingInService.auditOrder(inspectionPickingIn);
    }
    /**
     * 提审
     *
     * @param inspectionId 物流送货单id
     * @return HttpResponse
     */
    @GetMapping(value = "/commit/{inspectionId}")
    @ResponseBody
    public HttpResponse commit(@PathVariable String inspectionId) {
        return inspectionPickingInService.commit(inspectionId);
    }

    /**
     * 已占用配送数查询
     */
    @GetMapping(value = "/detail/queryByDistributionId")
    @ResponseBody
    public HttpResponse queryByDistributionId(@Valid InspectionPickingInDetailRequest inspectionPickingInDetailRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("查询验货入库单参数错误!");
            return HttpResponse.failure(ResultCode.INSPECTION_QUERY_ERROR);
        }
        return inspectionPickingInService.queryByDistributionId(inspectionPickingInDetailRequest.getDistributionOrderId());
    }

    /**
     * 导出入库单list
     * @param inspection 传参对象
     * @param response 返回
     * @throws InspectionpickinginException 异常
     */
    @GetMapping(value = "/exportInspection")
    public void exportInspection(@Valid InspectionPickingInRequest inspection, HttpServletResponse response) throws InspectionpickinginException{
        List<List<Object>> dataList = inspectionPickingInService.exportInspection(inspection);
        List<String> titles = new ArrayList<>();
        titles.add(ExcelTitle.INDEX.getName());
        titles.add(ExcelTitle.INSPECTIONINORDER.getName());
        titles.add(ExcelTitle.SOURCE_DISTRIBUTION.getName());
        titles.add(ExcelTitle.RECEIPT_STORENAME.getName());
        titles.add(ExcelTitle.DISTRIBUTION_CENTER.getName());
        titles.add(ExcelTitle.MAKE_ORDER_NAME.getName());
        titles.add(ExcelTitle.MAKE_ORDER_TIME.getName());
        titles.add(ExcelTitle.ORDER_STATUS.getName());

        ExcelBaseBean baseBean = new ExcelBaseBean();
        baseBean.setData(dataList);
        baseBean.setTitles(titles);
        baseBean.setXlsName("入库单");
        try {
            ExcelUtil.dynamicExport(baseBean, response);
        } catch (Exception e){
            LOGGER.error("导出入库单list失败", e);
            throw new InspectionpickinginException("配送单-导出失败");
        }
    }

    /**
     * 导出入库单详情
     * @param inspectionId 入库单详情Id
     * @param response 返回
     * @throws InspectionpickinginException 异常
     */
    @GetMapping(value = "/exportByInspectionId/{inspectionId}")
    public void exportByInspectionId(@PathVariable String inspectionId, HttpServletResponse response) throws InspectionpickinginException{
        List<List<Object>> dataList = inspectionPickingInService.exportInspectionId(inspectionId);
        List<String> titles = new ArrayList<>();
        titles.add(ExcelTitle.INDEX.getName());
        titles.add(ExcelTitle.GOODS_SKUCODE.getName());
        titles.add(ExcelTitle.INTERNATIONAL_CODE.getName());
        titles.add(ExcelTitle.GOODS_NAME.getName());
        titles.add(ExcelTitle.SPECIFICATIONS.getName());
        titles.add(ExcelTitle.UNIT_NAME.getName());
        titles.add(ExcelTitle.RECEIPT_NUM.getName());
        titles.add(ExcelTitle.REMARK.getName());
        ExcelBaseBean baseBean = new ExcelBaseBean();
        baseBean.setData(dataList);
        baseBean.setTitles(titles);
        baseBean.setXlsName("入库单详情");

        try {
            ExcelUtil.dynamicExport(baseBean, response);
        } catch (Exception e){
            LOGGER.error("导出入库单详情失败。", e);
            throw new InspectionpickinginException("导出详情失败。");
        }

    }

}


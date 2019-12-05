package com.hanyun.scm.api.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.StockPickingDao;
import com.hanyun.scm.api.dao.SupplierDao;
import com.hanyun.scm.api.domain.StockPicking;
import com.hanyun.scm.api.domain.Supplier;
import com.hanyun.scm.api.domain.request.stock.SynchronizedStockPickingRequest;
import com.hanyun.scm.api.domain.request.supplier.SupplierCreateRequest;
import com.hanyun.scm.api.service.DataTransferService;
import com.hanyun.scm.api.service.StockPickingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "transfer")
@Api(value = "transfer",description = "来跑吧数据传输相关api")
public class DataTransferController {

    @Resource
    private DataTransferService dataTransferService;

    @Resource
    private SupplierDao supplierDao;

    @ApiOperation(value = "导入e店宝库存")
    @GetMapping(value = "/stock")
    public HttpResponse importGoods(@ApiParam(value = "导入到那个商业品牌",required = true)
                                        @RequestParam(value = "brandId") String brandId,
                                    @ApiParam(value = "导入到哪个仓库",required = true)
                                        @RequestParam(value = "warehouseId") String warehouseId,
                                    @ApiParam(value = "从哪个仓导入",required = true)
                                        @RequestParam(value = "edbStoreId") String edbStoreId){
        int i = 0;
        int count;
        StringBuilder sb = new StringBuilder();
        do {
            count = dataTransferService.importGoodsByPage(String.valueOf(++i), brandId, warehouseId, edbStoreId);
            sb.append("count ").append(count).append("*").append(i).append(";");
        } while (count > 0);

        return HttpResponse.success(sb.toString());
    }

    public HttpResponse deleteStock(@ApiParam(value = "删除哪个商业品牌库存",required = true)
                                    @RequestParam(value = "brandId") String brandId,
                                    @ApiParam(value = "删除哪个仓库库存")
                                    @RequestParam(value = "warehouseId") String warehouseId) {
        return dataTransferService.deleteStock(brandId, warehouseId);
    }

    @ApiOperation(value = "导入e店宝供应商")
    @GetMapping(value = "/supplier")
    public HttpResponse initSupplier(@Valid SupplierCreateRequest supplierCreateRequest) {
        if (StringUtils.isEmpty(supplierCreateRequest.getSupplierId())) {
            List<String[]> supplierList = new ArrayList<>();
            supplierList.add(new String[]{"1", "亚瑟士(中国)商贸有限公司"});
            supplierList.add(new String[]{"2", "北京奥易绿野车用装备有限公司"});
            supplierList.add(new String[]{"3", "北京和时本物商贸有限公司"});
            supplierList.add(new String[]{"4", "永三商贸(上海)有限公司"});
            supplierList.add(new String[]{"5", "沈阳窗外体育文化传播有限公司"});
            supplierList.add(new String[]{"6", "厦门辰炀科技商贸有限公司"});
            supplierList.add(new String[]{"7", "上海美津浓有限公司"});
            supplierList.add(new String[]{"8", "深圳市喜马拉雅贸易有限公司"});
            supplierList.add(new String[]{"9", "北京京广盈通贸易有限公司"});
            supplierList.add(new String[]{"10", "北京悦欧新体育发展有限公司"});
            supplierList.add(new String[]{"11", "北京七索时代科技有限公司"});
            supplierList.add(new String[]{"12", "北京奥冠英有限公司"});
            supplierList.add(new String[]{"13", "北京青年绘文化传播有限公司"});
            supplierList.add(new String[]{"14", "安踏体育用品集团有限公司"});
            supplierList.add(new String[]{"15", "深圳市伟新工艺品有限公司"});
            supplierList.add(new String[]{"16", "温州浩腾包装有限公司"});
            supplierList.add(new String[]{"17", "义乌市丽虹织带有限公司"});
            supplierList.add(new String[]{"18", "跑步之家"});
            supplierList.add(new String[]{"19", "李进一"});
            supplierList.add(new String[]{"20", "龙带国际有限公司"});
            for (int i=0;i<supplierList.size();i++) {
                SupplierCreateRequest defaultRequest = new SupplierCreateRequest();
                defaultRequest.setBrandId(supplierCreateRequest.getBrandId());
                defaultRequest.setSupplierId(supplierList.get(i)[0]);
                defaultRequest.setSupplierName(supplierList.get(i)[1]);
                defaultRequest.setSupplierStatus(Consts.SUPPLIER_STATUS_USABLE);
                supplierDao.insert(defaultRequest);
            }
            return HttpResponse.success();
        }
        Supplier supplier = supplierDao.selectBySupplierId(supplierCreateRequest.getSupplierId());
        if (supplier != null) {
            return HttpResponse.failure(ResultCode.SUPPLIER_CREATE_ERROR);
        }
        int row = supplierDao.insert(supplierCreateRequest);
        if (row == 0) {
            return HttpResponse.failure(ResultCode.SUPPLIER_CREATE_ERROR);
        }
        return HttpResponse.success();
    }

    @PostMapping("/synchronized-stock")
    @ResponseBody
    public HttpResponse synchronizedStockPicking(@Valid @RequestBody SynchronizedStockPickingRequest synchronizedStockPickingRequest) {
        return dataTransferService.synchronizedStockPicking(synchronizedStockPickingRequest);

    }
}

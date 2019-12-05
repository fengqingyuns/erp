package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.StockPicking;
import com.hanyun.scm.api.domain.StockPickingGoods;
import com.hanyun.scm.api.domain.request.stock.StockPickingRequest;
import com.hanyun.scm.api.service.StockPickingService;
import com.hanyun.scm.api.web.util.ExcelUtil;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName: DepositController
 * @Description: 出入库
 * @author: 高杨
 * @date: 2016年10月27日 下午5:27:23
 */
@Controller
@RequestMapping(value = "/stock-picking")
public class StockPickingController {
    @Resource
    private StockPickingService stockPickingService;

    private static final Logger LOGGER = LoggerFactory.getLogger(StockPickingController.class);

    /**
     * 更新出入库状态
     *
     * @param stockPicking
     * @return
     */
    @PostMapping(value = "/updateStockpinkingStatus/modify", produces = "application/json")
    @ResponseBody
    public Object modifyStockPicking(@RequestBody StockPicking stockPicking) {
        return stockPickingService.modifyStockPicking(stockPicking);
    }

    /**
     * @param stockPickingRequest
     * @return
     * @Description: 入库单浏览
     */

    @GetMapping(value = "/queryByType", produces = "application/json")
    @ResponseBody
    public Object selectByType(StockPickingRequest stockPickingRequest) {
        return stockPickingService.selectByType(stockPickingRequest);
    }

    /**
     * 查找订单信息
     */
    @GetMapping(value = "/queryBystockPickingId", produces = "application/json")
    @ResponseBody
    public Object queryBystockPickingId(StockPickingRequest stockPickingRequest) {
        return stockPickingService.queryBystockPickingId(stockPickingRequest);
    }

    /**
     * 添加库存管理数据
     */
    @PostMapping(value = "/create", produces = "application/json")
    @ResponseBody
    public Object create(@RequestBody StockPicking stockPicking) {
        return stockPickingService.create(stockPicking);
    }

    /**
     * @param stockPickingRequest
     * @return
     * @Description: 入库单浏览
     */

    @GetMapping(value = "/selectStockInAndOut", produces = "application/json")
    @ResponseBody
    public Object selectStockInAndOut(StockPickingRequest stockPickingRequest) {
        return stockPickingService.selectStockInAndOut(stockPickingRequest);
    }

    /**
     * @param stockPickingRequest
     * @return
     * @Description: 其他出入库-导出
     */
    @GetMapping(value = "/exportStockInAndOut", produces = "application/json")
    @ResponseBody
    public void exportStockInAndOut(StockPickingRequest stockPickingRequest, HttpServletRequest request,
                                    HttpServletResponse response) {
    	
        List<StockPicking> list = stockPickingService.exportStockInAndOut(stockPickingRequest);
        String xlsName = "其他出入库";
        String templateName = "exportStockInandOut.xls";//createTime,updateTime,auditorTime,exportOrderTime,exportOrderCreateTime,exportconformTime
        String[] attributes = new String[]{"stockPickingDocumentId", "updateTime", "pickingAmount", "orderType", "exportWarehouse",
                "operatorName", "createTime", "exportOrderStatus"};
        try {
            ExcelUtil.export(xlsName, templateName, list, attributes, response);
        } catch (Exception e) {
            LOGGER.error("导出其他出入库失败");
            throw new RuntimeException("导出其他出入库失败");
        }
    }
    /**
     * @param stockPickingRequest
     * @return
     * @Description: 转仓单-导出
     */
    @GetMapping(value = "/exportRequision")
    @ResponseBody
    public void exportRequision(StockPickingRequest stockPickingRequest, HttpServletRequest request,
                                    HttpServletResponse response) {
        List<StockPicking> list = stockPickingService.exportRequision(stockPickingRequest);
        String xlsName = "转仓单";
        String templateName = "exportStockRequisition.xls";
        String[] attributes = new String[]{"stockPickingDocumentId", "updateTime", "pickingAmount", "srcWarehouseName", "toWarehouseName",
                "operatorName", "createTime", "exportOrderStatus"};
        try {
            ExcelUtil.export(xlsName, templateName, list, attributes, response);
        } catch (Exception e) {
            LOGGER.error("导出转仓单失败");
            throw new RuntimeException("导出转仓单失败");
        }
    }
    /**
     * 
     * @return
     * @Description: 其他出入库详单--导出
     */
    @GetMapping(value = "/exportStockInAndOutGoods")
    @ResponseBody
    public void exportStockInAndOut(@NotEmpty @RequestParam(value = "stockPickingId", required = false) String stockPickingId,
                                    @NotNull @RequestParam(value = "type", required = false) Integer type,
                                    HttpServletResponse response) {
    	List<StockPickingGoods> goodsList=stockPickingService.exportStockInAndOutGoods(stockPickingId,type);
        
        String xlsName = "其他出入库";
        String templateName = "exportStockInAndOutUpdate.xls";
        String[] attributes = new String[]{"goodsCode", "goodsBarCode", "goodsName", "features", "unitName",
                 "pickingAmount", "pickingPrice", "totalPrice", "quantNum","remark"};
        try {
            ExcelUtil.export(xlsName, templateName, goodsList, attributes, response);
        } catch (Exception e) {
            LOGGER.error("导出其他出入库详单失败");
            throw new RuntimeException("导出其他出入库详单失败");
        }
    }
    
    /**
     * 
     * @return
     * @Description: 转仓单详单--导出
     */
    @GetMapping(value = "/exportRequisitionUpdate")
    @ResponseBody
    public void exportRequisitionUpdate(StockPickingRequest stockPickingRequest, HttpServletRequest request,
                                    HttpServletResponse response) {
    	List<StockPickingGoods> goodsList=stockPickingService.exportRequisitionUpdate(stockPickingRequest);
        
        String xlsName = "转仓单："+stockPickingRequest.getStockPickingDocumentId();
        String templateName = "exportRequisionUpdate.xls";
        String[] attributes = new String[]{"goodsCode", "goodsBarCode", "goodsName", "features", "unitName",
                 "pickingAmount", "pickingPrice", "totalPrice", "quantNum","remark"};
        try {
            ExcelUtil.export(xlsName, templateName, goodsList, attributes, response);
        } catch (Exception e) {
            LOGGER.error("导出其他出入库详单失败");
            throw new RuntimeException("导出其他出入库详单失败");
        }
    }

    /**
     * 提审
     * @param stockPickingId 单号
     * @return  返回值
     */
    @GetMapping(value = "/commit/{stockPickingId}")
    @ResponseBody
    public HttpResponse commit(@PathVariable String stockPickingId) {
        return stockPickingService.commit(stockPickingId);
    }

    /**
     * 删除
     * @param stockPickingId    单号
     * @return  返回值
     */
    @DeleteMapping(value = "/delete/{stockPickingId}")
    @ResponseBody
    public HttpResponse delete(@PathVariable String stockPickingId) {
        return stockPickingService.delete(stockPickingId);
    }
}

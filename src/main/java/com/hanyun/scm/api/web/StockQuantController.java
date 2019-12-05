package com.hanyun.scm.api.web;

import com.alibaba.druid.util.StringUtils;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ExcelTitle;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.ExcelBaseBean;
import com.hanyun.scm.api.domain.StockQuant;
import com.hanyun.scm.api.domain.Warehouse;
import com.hanyun.scm.api.domain.request.stock.SellGoodsRequest;
import com.hanyun.scm.api.domain.request.stock.StockQuantQueryRequest;
import com.hanyun.scm.api.domain.request.stock.StoreGoodsStock;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseQueryRequest;
import com.hanyun.scm.api.domain.response.ExportResponse;
import com.hanyun.scm.api.exception.StockQuantException;
import com.hanyun.scm.api.service.StockQuantService;
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
 * StockQuant Date: 2016/10/25 Time: 下午8:38
 *
 * @author tianye@hanyun.com
 */
@Controller
@RequestMapping(value = "/stock-quant")
public class StockQuantController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseReturnController.class);

    @Resource
    private StockQuantService stockQuantService;

    /**
     * 创建库存信息
     *
     * @param stockQuant
     * @return
     */
    @PostMapping(value = "/create")
    @ResponseBody
    public HttpResponse create(@RequestBody StockQuant stockQuant) {
        return stockQuantService.create(stockQuant);
    }

    /**
     * 查询库存信息列表
     *
     * @param stockQuant
     * @return
     */
    @GetMapping(value = "/query")
    @ResponseBody
    public HttpResponse select(StockQuantQueryRequest stockQuant) {
        return stockQuantService.select(stockQuant);
    }

    /**
     * 重构代码查询库存分布明细信息  GET
     *
     * @param stockQuant
     * @return
     */
    @GetMapping(value = "/queryByDetail")
    @ResponseBody
    public HttpResponse queryByDetail(@Valid StockQuantQueryRequest stockQuant) {
        return stockQuantService.queryByDetail(stockQuant);
    }
    /**
     * 重构代码查询库存分布明细信息  POST
     *
     * @param stockQuant 查询对象
     * @return HttpResponse
     */
    @PostMapping(value = "/query")
    @ResponseBody
    public HttpResponse selectForPost(@RequestBody @Valid StockQuantQueryRequest stockQuant){
        return stockQuantService.select(stockQuant);
    }

    /**
     * 查询库存商品信息
     *
     * @param stockQuant
     * @return
     */
    @GetMapping(value = "/queryGoods")
    @ResponseBody
    public HttpResponse selectGoods(StockQuantQueryRequest stockQuant) {
        return stockQuantService.select(stockQuant);
    }

    /**
     * 查询库存明细
     *
     * @param stockQuant
     * @return
     */
    @GetMapping(value = "/queryDetail")
    @ResponseBody
    public HttpResponse queryDetail(StockQuantQueryRequest stockQuant) {
        return stockQuantService.selectStockDetail(stockQuant);
    }

    /**
     * @throws StockQuantException
     * @Title: exportStockDistributionDetail
     * @Description: 库存分布明细excel导出
     * @author: 唐启明
     * @return: void
     * @date: 2016年11月9日 下午9:18:02
     */
    @GetMapping(value = "/exportStockDistributionDetail")
    public void exportStockDistributionDetail(StockQuantQueryRequest stockQuant, HttpServletResponse response) throws StockQuantException {
        // 导出数据data
        List<List<Object>> dataList;
        String[] warseHouse;
        try {
            ExportResponse receiveResponse = stockQuantService.selectGoodsWarehouse(stockQuant);
            dataList = receiveResponse.getResultList();
            warseHouse = receiveResponse.getResultWarehouse();
        } catch (Exception e) {
            LOGGER.error("库存分布明细--库存分布明细数据list查询失败", e);
            throw new StockQuantException("库存分布明细--库存分布明细数据list查询失败");
        }
        // 标题
        List<String> titles = new ArrayList<>();
        titles.add(ExcelTitle.INDEX.getName());
        titles.add(ExcelTitle.GOODS_CLASSFIYNAME.getName());
        titles.add(ExcelTitle.STOCK_TYPE.getName());
        titles.add(ExcelTitle.GOODS_CODE.getName());
        titles.add(ExcelTitle.GOODS_NAME.getName());
        titles.add(ExcelTitle.UNIT_NAME.getName());
        titles.add(ExcelTitle.STOCK_TOTAL.getName());
        for (int i = 0; i < warseHouse.length; i++) {
            titles.add(warseHouse[i]);
        }
        ExcelBaseBean excelBaseBean = new ExcelBaseBean();
        excelBaseBean.setData(dataList);
        excelBaseBean.setTitles(titles);
        excelBaseBean.setXlsName("库存分布明细导出");
        try {
            ExcelUtil.dynamicExport(excelBaseBean, response);
        } catch (Exception e) {
            LOGGER.error("库存分布明细--库存分布明细导出失败!", e);
            throw new StockQuantException("库存分布明细--库存分布明细导出失败!");
        }
    }

    /**
     * 单仓库导出
     *
     * @param stockQuant
     * @param response
     * @throws StockQuantException
     */
    @GetMapping(value = "/exportExcelByProfitAndLossSingle")
    public void exportExcelByProfitAndLossSingle(StockQuantQueryRequest stockQuant, HttpServletResponse response) throws StockQuantException {

        List<List<Object>> dataList;
        try {
            dataList = stockQuantService.exportExcelByProfitAndLossSingle(stockQuant);
        } catch (Exception e) {
            LOGGER.error("库存预警设置--查询单库存预警设置list失败", e);
            throw new StockQuantException("库存预警设置--查询单库存预警设置list失败");
        }
        // 标题
        List<String> titles = new ArrayList<>();
        titles.add(ExcelTitle.INDEX.getName());
        titles.add(ExcelTitle.GOODS_CLASSFIYNAME.getName());
        titles.add(ExcelTitle.STOCK_TYPE.getName());
        titles.add(ExcelTitle.GOODS_CODE.getName());
        titles.add(ExcelTitle.GOODS_NAME.getName());
        titles.add(ExcelTitle.UNIT_NAME.getName());
        titles.add(ExcelTitle.STOCK_LOWER.getName());
        titles.add(ExcelTitle.SAFE_STOCK.getName());
        titles.add(ExcelTitle.STOCK_UPPER.getName());

        ExcelBaseBean excelBaseBean = new ExcelBaseBean();
        excelBaseBean.setData(dataList);
        excelBaseBean.setTitles(titles);
        excelBaseBean.setXlsName("单仓库预警设置导出");
        try {
            ExcelUtil.dynamicExport(excelBaseBean, response);
        } catch (Exception e) {
            LOGGER.error("库存预警设置--单仓库库存预警设置导出失败", e);
            throw new StockQuantException("库存预警设置--单仓库库存预警设置导出失败");
        }
    }

    /**
     * 多仓库导出
     *
     * @param stockQuant
     * @param response
     * @throws StockQuantException
     */
    @GetMapping(value = "/exportExcelByProfitAndLossDouble")
    public void exportExcelByProfitAndLossDouble(StockQuantQueryRequest stockQuant, HttpServletResponse response) throws StockQuantException {
        String[] warseHouse;
        List<List<Object>> dataList;
        try {
            ExportResponse receiveResponse = stockQuantService.exportExcelByProfitAndLossDouble(stockQuant);
            dataList = receiveResponse.getResultList();
            warseHouse = receiveResponse.getResultWarehouse();
        } catch (Exception e) {
            LOGGER.error("库存预警设置--查询多库存预警设置list失败", e);
            throw new StockQuantException("库存预警设置--查询多库存预警设置list失败");
        }

        // 标题
        List<String> titles = new ArrayList<>();
        titles.add(ExcelTitle.INDEX.getName());
        titles.add(ExcelTitle.GOODS_CLASSFIYNAME.getName());
        titles.add(ExcelTitle.STOCK_TYPE.getName());
        titles.add(ExcelTitle.GOODS_CODE.getName());
        titles.add(ExcelTitle.GOODS_NAME.getName());
        titles.add(ExcelTitle.UNIT_NAME.getName());
        for (int i = 0; i < warseHouse.length; i++) {
            titles.add(warseHouse[i]);
        }
        ExcelBaseBean excelBaseBean = new ExcelBaseBean();
        excelBaseBean.setData(dataList);
        excelBaseBean.setTitles(titles);
        excelBaseBean.setXlsName("多仓库预警设置导出");
        try {
            ExcelUtil.dynamicExport(excelBaseBean, response);
        } catch (Exception e) {
            LOGGER.error("库存预警设置--多仓库库存预警设置导出失败", e);
            throw new StockQuantException("库存预警设置--多仓库库存预警设置导出失败");
        }

    }

    /**
     * 修改库存信息
     *
     * @param stockQuant
     * @return
     */
    @PostMapping(value = "/modify")
    @ResponseBody
    public HttpResponse modify(@RequestBody StockQuant stockQuant) {
        return stockQuantService.modify(stockQuant);
    }

    /**
     * 修改多库存信息 --stock_upper
     *
     * @param stockQuant
     * @return
     */
    @PostMapping(value = "/updateDoubleStockNum")
    @ResponseBody
    public HttpResponse updateDoubleStockNum(@RequestBody StockQuant stockQuant) {
        return stockQuantService.updateDoubleStockNum(stockQuant);
    }

    /**
     * 修改单库存信息 --stock_upper
     *
     * @param stockQuant
     * @return
     */
    @PostMapping(value = "/updateStockQuant")
    @ResponseBody
    public HttpResponse updateStockQuant(@RequestBody StockQuant stockQuant) {
        return stockQuantService.updateStockQuant(stockQuant);
    }

    /**
     * 删除库存信息
     *
     * @param brandId   品牌id
     * @param goodsId   商品id
     * @return
     */
    @DeleteMapping(value = "/{brandId}/{goodsId}")
    @ResponseBody
    public HttpResponse delete(@PathVariable String brandId, @PathVariable String goodsId) {
        return stockQuantService.delete(brandId, goodsId);
    }

    /**
     * 查询库存详情
     *
     * @param stockQuantId
     * @return
     */
    @GetMapping(value = "/detail/{stockQuantId}")
    @ResponseBody
    public HttpResponse detail(@PathVariable String stockQuantId) {
        return stockQuantService.detail(stockQuantId);
    }

    @GetMapping(value = "/exportExcelByStockQuantList")
    public void exportExcelByStockQuantList(StockQuantQueryRequest stockQuant, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
        WarehouseQueryRequest warehouseRequest = new WarehouseQueryRequest();
        // 查询
        List<Warehouse> warehouselist = stockQuantService.selectWarhouseName(warehouseRequest);//查询仓库名
        List<StockQuant> list = stockQuantService.selectExexportStockQuantList(stockQuant, warehouselist);//查询详单
        // 查询结束
        String xlsName = "库存查看";
        String templateName = "kucunchakan.xls";

        String[] attributes = new String[]{"warehouseName", "goodsBarCode", "goodsName", "unitName", "unitPrice",
                "stockNum", "totalPrice"};
        try {
            ExcelUtil.export(xlsName, templateName, list, attributes, response);
        } catch (Exception e) {
            LOGGER.error("库存查看导出失败", e);
            throw new StockQuantException("报表-库存查看导出失败");
        }

    }
    /**一下代码没有用到**/
    @GetMapping(value = "/selectAllocationGoods")
    @ResponseBody
    public HttpResponse selectAllocationGoods(StockQuantQueryRequest stockQuant) {
        return stockQuantService.selectAllocationGoods(stockQuant);
    }
    /**以上代码没有用到**/
    @GetMapping(value = "/selectAllBrandName")
    @ResponseBody
    public HttpResponse selectAllBrandName(StockQuant stockQuant) {
        return stockQuantService.selectALLGoodsBrand(stockQuant);
    }

    /**
     * 售卖商品更新库存
     *
     * @param sellGoodsRequest 售卖商品请求参数
     * @return
     */
    @PostMapping(value = "/sell-goods")
    @ResponseBody
    public HttpResponse sellGoods(@RequestBody @Valid SellGoodsRequest sellGoodsRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.STOCK_QUANT_SELL_ERROR);
        }
        return stockQuantService.sellGoods(sellGoodsRequest);
    }

    /**
     * 库存查询导出
     *
     * @param stockQuant
     * @param response
     * @throws StockQuantException
     */
    @GetMapping(value = "/exportByStockQuantDetail")
    public void exportByStockQuantDetail(@Valid StockQuantQueryRequest stockQuant, HttpServletResponse response) throws StockQuantException {
        //导出数据
        List<List<Object>> dataList;
        dataList = stockQuantService.exportQueryDetailed(stockQuant);
        //标题
        List<String> titleList = new ArrayList<>();
        titleList.add(ExcelTitle.INDEX.getName());
        if (!StringUtils.isEmpty(stockQuant.getQueryGroup())) {
            titleList.add(ExcelTitle.GOODS_NAME.getName());
            titleList.add(ExcelTitle.GOODS_CLASSFIYNAME.getName());
            titleList.add(ExcelTitle.GOODS_SKUCODE.getName());
            titleList.add(ExcelTitle.GOODS_BAR_CODE.getName());
            titleList.add(ExcelTitle.UNIT_NAME.getName());
            titleList.add(ExcelTitle.SPECIFICATIONS.getName());
            titleList.add(ExcelTitle.ALL_STOCKNUM.getName());
        } else {
            titleList.add(ExcelTitle.WAREHOUSE_NAME.getName());
            titleList.add(ExcelTitle.GOODS_NAME.getName());
            titleList.add(ExcelTitle.GOODS_CLASSFIYNAME.getName());
            titleList.add(ExcelTitle.GOODS_SKUCODE.getName());
            titleList.add(ExcelTitle.GOODS_BAR_CODE.getName());
            titleList.add(ExcelTitle.UNIT_NAME.getName());
            titleList.add(ExcelTitle.SPECIFICATIONS.getName());
            titleList.add(ExcelTitle.NOW_STOCKNUM.getName());
        }
        titleList.add(ExcelTitle.COST_UNIT_PRICE.getName());
        titleList.add(ExcelTitle.STOCK_BALANCE.getName());
        titleList.add(ExcelTitle.INITIAL_STOCK_QUANT.getName());
        titleList.add(ExcelTitle.INITIAL_UNIT_PRICE.getName());
        titleList.add(ExcelTitle.INITIAL_TOTAL_PRICE.getName());

        ExcelBaseBean excelBaseBean = new ExcelBaseBean();
        excelBaseBean.setData(dataList);
        excelBaseBean.setTitles(titleList);
        excelBaseBean.setXlsName("库存明细导出");
        try {
            ExcelUtil.dynamicExport(excelBaseBean, response);
        } catch (Exception e) {
            LOGGER.error("库存--导出失败", e);
            throw new StockQuantException("库存--导出失败");
        }
    }

    /**
     * 查询门店商品库存
     *
     * @param storeGoodsStock 参数
     * @return 返回值
     */
    @PostMapping("/store-goods")
    @ResponseBody
    public HttpResponse queryStoreGoods(@RequestBody @Valid StoreGoodsStock storeGoodsStock, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || storeGoodsStock.getGoodsIds().size() == 0) {
            return HttpResponse.failure(ResultCode.STOCK_QUANT_PARAM_ERROR);
        }
        return stockQuantService.queryStoreGoods(storeGoodsStock);
    }

    /**
     * 初始化es库存数据
     *
     * @return 返回值
     */
    @GetMapping("/init-es-stock")
    @ResponseBody
    public HttpResponse initEsStock() {
        return stockQuantService.initEsStock();
    }

    /**
     * 初始化删除无用库存信息
     * @return  返回值
     */
    @GetMapping("/init-delete-stock")
    @ResponseBody
    public HttpResponse initDeleteStock() {
        return HttpResponse.success(stockQuantService.initDeleteStock());
    }
}

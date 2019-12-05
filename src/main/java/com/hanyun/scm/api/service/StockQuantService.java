package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.*;
import com.hanyun.scm.api.domain.request.BrandStoreRequest;
import com.hanyun.scm.api.domain.request.stock.SellGoodsRequest;
import com.hanyun.scm.api.domain.request.stock.StockQuantQueryRequest;
import com.hanyun.scm.api.domain.request.stock.StoreGoodsStock;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.domain.response.ExportResponse;
import com.hanyun.scm.api.domain.response.stock.TotalStockResponse;
import com.hanyun.scm.api.exception.StockQuantException;

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
 * StockQuantService Date: 2016/10/25 Time: 下午8:40
 *
 * @author tianye@hanyun.com
 */
public interface StockQuantService {

    HttpResponse create(StockQuant stockQuant);

    HttpResponse detail(String id);

    HttpResponse select(StockQuantQueryRequest stockQuant);

    HttpResponse selectStockDetail(StockQuantQueryRequest stockQuant);

    HttpResponse queryByDetail(StockQuantQueryRequest stockQuant);

    ExportResponse selectGoodsWarehouse(StockQuantQueryRequest stockQuant) throws Exception;

    List<List<Object>> exportExcelByProfitAndLossSingle(StockQuantQueryRequest stockQuant) throws StockQuantException;

    ExportResponse exportExcelByProfitAndLossDouble(StockQuantQueryRequest stockQuant) throws StockQuantException;

    HttpResponse modify(StockQuant stockQuant);

    HttpResponse updateStockQuant(StockQuant stockQuant);

    HttpResponse updateDoubleStockNum(StockQuant stockQuant);

    HttpResponse delete(String brandId, String goodsId);

    void updateQuantByStockPicking(StockPicking stockPicking, Integer type);

    List<StockQuant> selectExexportStockQuantList(StockQuantQueryRequest stockQuant, List<Warehouse> warhouseList) throws Exception;

    List<Warehouse> selectWarhouseName(WarehouseQueryRequest warehouseQueryRequest);

    HttpResponse selectAllocationGoods(StockQuantQueryRequest stockQuant);

    HttpResponse selectALLGoodsBrand(StockQuant stockQuant);

    HttpResponse sellGoods(SellGoodsRequest sellGoodsRequest);

    void updateQuantBySpillAndLoss(StockSpillLossOrder stockSpillLossOrder);

    void disassemblyModifyStockQuant(DisassemblyOrderDetail disassemblyOrderDetail, List<DisassemblyOrderDetail> sonList, DisassemblyOrder disassemblyOrder) throws Exception;

    List<List<Object>> exportQueryDetailed(StockQuantQueryRequest stockQuant) throws StockQuantException;

    /**
     * 查询总库存
     *
     * @param brandStoreRequest 总库存查询参数
     * @return 总库存信息
     */
    TotalStockResponse totalStock(BrandStoreRequest brandStoreRequest);

    void updateQuantByInspectionPickingIn(InspectionPickingIn inspectionPickingIn);

    /**
     * 查询门店商品库存
     * @param storeGoodsStock  参数
     * @return 返回值
     */
    HttpResponse queryStoreGoods(StoreGoodsStock storeGoodsStock);

    StockQuant selectById(String id);

    BaseResponse selectWithParam(StockQuantQueryRequest query) throws Exception;

    /**
     * 初始化es库存
     * @return  返回值
     */
    HttpResponse initEsStock();

    /**
     * 更新库存
     * @param stockQuant    参数
     * @return  返回值
     */
    int updateEsByStockQuantId(StockQuant stockQuant);

    /**
     * 分组查询
     * @param query 参数
     * @return  返回值
     * @throws Exception    异常
     */
    BaseResponse selectGroupWithParam(StockQuantQueryRequest query) throws Exception;

    /**
     * 初始化删除垃圾库存信息
     * @return
     */
    HttpResponse initDeleteStock();
}

package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.StockPicking;
import com.hanyun.scm.api.domain.request.stock.SynchronizedStockPickingRequest;

public interface DataTransferService {

    /**
     * 删除库存
     * @param brandId   品牌id
     * @param warehouseId   仓库id
     * @return  返回值
     */
    HttpResponse deleteStock(String brandId, String warehouseId);

    /**
     * 导入商品
     * @param pageNum   页数
     * @param brandId   品牌id
     * @param warehouseId   仓库id
     * @param edbStoreId    一点宝仓库id
     * @return  返回值
     */
    int importGoodsByPage(String pageNum, String brandId, String warehouseId, String edbStoreId);

    /**
     * 同步来跑吧数据
     * @param synchronizedStockPickingRequest  库存移动数据
     * @return  返回值
     */
    HttpResponse synchronizedStockPicking(SynchronizedStockPickingRequest synchronizedStockPickingRequest);
}

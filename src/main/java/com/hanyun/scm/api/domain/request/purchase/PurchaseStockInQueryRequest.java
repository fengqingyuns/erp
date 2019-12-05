package com.hanyun.scm.api.domain.request.purchase;

import com.hanyun.scm.api.domain.StockPickingGoods;
import com.hanyun.scm.api.domain.dto.QueryPurchaseStockInDTO;
import com.hanyun.scm.api.domain.request.stock.StockPickingRequest;
import org.hibernate.validator.constraints.NotEmpty;

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
 * PurchaseReturnQueryRequest
 * Date: 2016/12/9
 * Time: 上午10:07
 *
 * @author tianye@hanyun.com
 */
public class PurchaseStockInQueryRequest extends PurchaseStockInBaseRequest {

    private String stockPickingId;      //库存移动单id

    private String stockPickingDocumentId;      //库存移动单id

    @NotEmpty
    private String brandId;             //品牌id

    private String supplierId;          //供应商id(采购出入库使用)

    private String toWarehouseId;       //目标仓库id

    private List<StockPickingGoods> stockPickingGoodsList;

    private String queryBeginTime;

    private String queryEndTime;

    private String userId;

    public PurchaseStockInQueryRequest() {}

    public PurchaseStockInQueryRequest(QueryPurchaseStockInDTO queryPurchaseStockInDTO) {
        this.setBrandId(queryPurchaseStockInDTO.getBrandId());
        this.setStoreId(queryPurchaseStockInDTO.getStoreId());
        this.setQueryBeginTime(queryPurchaseStockInDTO.getQueryBeginTime());
        this.setQueryEndTime(queryPurchaseStockInDTO.getQueryEndTime());
        this.setStockPickingDocumentId(queryPurchaseStockInDTO.getStockPickingDocumentId());
        this.setBusinessManId(queryPurchaseStockInDTO.getBusinessManId());
        this.setSupplierId(queryPurchaseStockInDTO.getSupplierId());
        this.setStockPickingStatus(queryPurchaseStockInDTO.getStockPickingStatus());
        this.setToWarehouseId(queryPurchaseStockInDTO.getToWarehouseId());
        this.setUserId(queryPurchaseStockInDTO.getUserId());
        this.setPageSize(queryPurchaseStockInDTO.getPageSize());
        this.setPageNum(queryPurchaseStockInDTO.getPageNum());
    }

    public StockPickingRequest getStockPickingRequest() {
        return new StockPickingRequest(stockPickingId, stockPickingDocumentId, brandId, storeId, srcOrderId,
                srcOrderDocumentId, supplierId, supplierName, srcWarehouseId, srcWarehouseName, toWarehouseId,
                toWarehouseName, purchasePrice, purchaseAmount, stockPickingPrice, pickingAmount, stockPickingStatus,
                operatorId, operatorName, stockPickingType, stock, incomeReason, outReason, remark, createTime, updateTime, validStatus,
                getPageNum(), getPageSize(), queryBeginTime, queryEndTime, purchaseType, businessManId, businessManName);
    }

    public String getStockPickingId() {
        return stockPickingId;
    }

    public void setStockPickingId(String stockPickingId) {
        this.stockPickingId = stockPickingId;
    }

    public String getStockPickingDocumentId() {
        return stockPickingDocumentId;
    }

    public void setStockPickingDocumentId(String stockPickingDocumentId) {
        this.stockPickingDocumentId = stockPickingDocumentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getToWarehouseId() {
        return toWarehouseId;
    }

    public void setToWarehouseId(String toWarehouseId) {
        this.toWarehouseId = toWarehouseId;
    }

    public List<StockPickingGoods> getStockPickingGoodsList() {
        return stockPickingGoodsList;
    }

    public void setStockPickingGoodsList(List<StockPickingGoods> stockPickingGoodsList) {
        this.stockPickingGoodsList = stockPickingGoodsList;
    }

    public String getQueryBeginTime() {
        return queryBeginTime;
    }

    public void setQueryBeginTime(String queryBeginTime) {
        this.queryBeginTime = queryBeginTime;
    }

    public String getQueryEndTime() {
        return queryEndTime;
    }

    public void setQueryEndTime(String queryEndTime) {
        this.queryEndTime = queryEndTime;
    }
}

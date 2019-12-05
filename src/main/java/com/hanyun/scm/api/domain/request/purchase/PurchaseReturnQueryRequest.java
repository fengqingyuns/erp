package com.hanyun.scm.api.domain.request.purchase;

import com.hanyun.scm.api.domain.request.stock.StockPickingRequest;
import org.hibernate.validator.constraints.NotEmpty;

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
public class PurchaseReturnQueryRequest extends PurchaseReturnBaseRequest {

    private String stockPickingId;      //库存移动单id

    private String stockPickingDocumentId;      //库存移动单id

    @NotEmpty
    private String brandId;             //品牌id

    private String supplierId;          //供应商id(采购出入库使用)

    private String srcWarehouseId;      //来源仓库id

    private Integer validStatus;

    private String queryBeginTime;

    private String queryEndTime;

    private String userId;

    public StockPickingRequest getStockPickingRequest() {
        return new StockPickingRequest(stockPickingId, stockPickingDocumentId, brandId, storeId, srcOrderId,
                srcOrderDocumentId, supplierId, supplierName, srcWarehouseId, srcWarehouseName, toWarehouseId,
                toWarehouseName, purchasePrice, purchaseAmount, stockPickingPrice, pickingAmount, stockPickingStatus,
                operatorId, operatorName, stockPickingType, stock, incomeReason, outReason, remark, createTime, updateTime, validStatus,
                getPageNum(), getPageSize(), queryBeginTime, queryEndTime, null, businessManId, businessManName);
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

    public String getSrcWarehouseId() {
        return srcWarehouseId;
    }

    public void setSrcWarehouseId(String srcWarehouseId) {
        this.srcWarehouseId = srcWarehouseId;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
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

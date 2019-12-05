package com.hanyun.scm.api.domain.request.purchase;

import com.hanyun.scm.api.domain.request.BaseRequest;

import java.util.Date;

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
 * PurchaseStockInBaseRequest
 * Date: 2017/3/1
 * Time: 上午10:45
 *
 * @author tianye@hanyun.com
 */
class PurchaseStockInBaseRequest extends BaseRequest {

    String storeId;             //门店id

    private String storeName;            //门店名称

    String srcOrderId;          //来源订单id

    String srcOrderDocumentId;          //来源订单id

    String supplierName;        //供应商名称

    String toWarehouseName;     //目标仓库名称

    String srcWarehouseId;      //来源仓库id

    String srcWarehouseName;    //来源仓库名称

    Long purchasePrice;         //供应商价格

    Long stockPickingPrice;     //库存移动价格

    Integer stockPickingStatus; //库存移动状态 0、以保存  1、已完成

    Long purchaseAmount;        //采购数量

    Long pickingAmount;       //库存移动数量

    String operatorId;              //操作人id

    String operatorName;            //操作人名称

    private Integer odooPickingIncomeId;    //odoo入库单id

    private Integer odooPickingOutId;       //odoo出库单id

    Integer stockPickingType;       //库存移动类型 0、采购入库单  1、采购退货单  2、普通入库单  3、普通出库单  4、内部调拨单

    Long stock;                     //当前库存

    Integer incomeReason;           //入库原因

    Integer outReason;              //出库原因

    String remark;                  //备注

    Date createTime;

    Date updateTime;

    private String goodsBarCode;

    private String auditorId;

    private String auditorName;

    private String auditorTime;

    String businessManId;

    String businessManName;

    Long distinctPrice;  //优惠金额

    Date purchaseReturnTime;  //退货时间

    Integer purchaseReturnReason;  //退货原因

    Integer purchaseType;  //采购类型

    Integer validStatus;

    private Long completeStockInAmount;

    private Integer purchaseReturnStatus;       //退送状态

    public Integer getPurchaseReturnStatus() {
        return purchaseReturnStatus;
    }

    public void setPurchaseReturnStatus(Integer purchaseReturnStatus) {
        this.purchaseReturnStatus = purchaseReturnStatus;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSrcOrderId() {
        return srcOrderId;
    }

    public void setSrcOrderId(String srcOrderId) {
        this.srcOrderId = srcOrderId;
    }

    public String getSrcOrderDocumentId() {
        return srcOrderDocumentId;
    }

    public void setSrcOrderDocumentId(String srcOrderDocumentId) {
        this.srcOrderDocumentId = srcOrderDocumentId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getToWarehouseName() {
        return toWarehouseName;
    }

    public void setToWarehouseName(String toWarehouseName) {
        this.toWarehouseName = toWarehouseName;
    }

    public String getSrcWarehouseId() {
        return srcWarehouseId;
    }

    public void setSrcWarehouseId(String srcWarehouseId) {
        this.srcWarehouseId = srcWarehouseId;
    }

    public String getSrcWarehouseName() {
        return srcWarehouseName;
    }

    public void setSrcWarehouseName(String srcWarehouseName) {
        this.srcWarehouseName = srcWarehouseName;
    }

    public Long getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Long purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Long getStockPickingPrice() {
        return stockPickingPrice;
    }

    public void setStockPickingPrice(Long stockPickingPrice) {
        this.stockPickingPrice = stockPickingPrice;
    }

    public Integer getStockPickingStatus() {
        return stockPickingStatus;
    }

    public void setStockPickingStatus(Integer stockPickingStatus) {
        this.stockPickingStatus = stockPickingStatus;
    }

    public Long getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(Long purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public Long getPickingAmount() {
        return pickingAmount;
    }

    public void setPickingAmount(Long pickingAmount) {
        this.pickingAmount = pickingAmount;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Integer getOdooPickingIncomeId() {
        return odooPickingIncomeId;
    }

    public void setOdooPickingIncomeId(Integer odooPickingIncomeId) {
        this.odooPickingIncomeId = odooPickingIncomeId;
    }

    public Integer getOdooPickingOutId() {
        return odooPickingOutId;
    }

    public void setOdooPickingOutId(Integer odooPickingOutId) {
        this.odooPickingOutId = odooPickingOutId;
    }

    public Integer getStockPickingType() {
        return stockPickingType;
    }

    public void setStockPickingType(Integer stockPickingType) {
        this.stockPickingType = stockPickingType;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Integer getIncomeReason() {
        return incomeReason;
    }

    public void setIncomeReason(Integer incomeReason) {
        this.incomeReason = incomeReason;
    }

    public Integer getOutReason() {
        return outReason;
    }

    public void setOutReason(Integer outReason) {
        this.outReason = outReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public void setGoodsBarCode(String goodsBarCode) {
        this.goodsBarCode = goodsBarCode;
    }

    public String getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(String auditorId) {
        this.auditorId = auditorId;
    }

    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public String getAuditorTime() {
        return auditorTime;
    }

    public void setAuditorTime(String auditorTime) {
        this.auditorTime = auditorTime;
    }

    public String getBusinessManId() {
        return businessManId;
    }

    public void setBusinessManId(String businessManId) {
        this.businessManId = businessManId;
    }

    public String getBusinessManName() {
        return businessManName;
    }

    public void setBusinessManName(String businessManName) {
        this.businessManName = businessManName;
    }

    public Long getDistinctPrice() {
        return distinctPrice;
    }

    public void setDistinctPrice(Long distinctPrice) {
        this.distinctPrice = distinctPrice;
    }

    public Date getPurchaseReturnTime() {
        return purchaseReturnTime;
    }

    public void setPurchaseReturnTime(Date purchaseReturnTime) {
        this.purchaseReturnTime = purchaseReturnTime;
    }

    public Integer getPurchaseReturnReason() {
        return purchaseReturnReason;
    }

    public void setPurchaseReturnReason(Integer purchaseReturnReason) {
        this.purchaseReturnReason = purchaseReturnReason;
    }

    public Integer getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(Integer purchaseType) {
        this.purchaseType = purchaseType;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public Long getCompleteStockInAmount() {
        return completeStockInAmount;
    }

    public void setCompleteStockInAmount(Long completeStockInAmount) {
        this.completeStockInAmount = completeStockInAmount;
    }
}

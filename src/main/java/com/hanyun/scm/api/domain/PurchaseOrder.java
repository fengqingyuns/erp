package com.hanyun.scm.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanyun.scm.api.domain.request.BaseRequest;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑, 永无BUG!
 * 　　　　┃　　　┃Code is far away from bug with the animal protecting
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * </pre>
 */
public class PurchaseOrder extends BaseRequest {

    @JsonIgnore
    private Integer id;

    private String orderId;

    private String orderDocumentId;

    private String planId;

    private String planDocumentId;

    private String brandId;

    private String storeId;

    private String supplierId;

    private String supplierName;

    private Integer odooSupplierId;

    private Long orderPrice;

    private Long advance;

    private Integer orderStatus;

    private String orderStatusName;

    private Date planTime;

    private String operatorId;

    private String operatorName;

    private Integer payType;

    private Integer purchaseType;

    private Long totalAmount;

    private Long stockInAmount;

    private Long totalPrice;

    private Long distinctPrice;

    private Integer odooPurchaseId;

    private Date createTime;

    private Date updateTime;

    private int validStatus;

    private String purchaseUserId;

    private String purchaseUserName;

    private String toWarehouseId;

    private String toWarehouseName;

    private String auditorId;

    private String auditorName;

    private Date auditTime;

    private Integer purchaseStatus;

    private String remark;

    private List<PurchaseOrderDetail> purchaseOrderDetailList;

    private String planBeginTime;

    private String planEndTime;

    private String storeName;

    private String ids;                 //过滤的商品id集合json	仅用于传递数据

    private String goodsId;

    private String oneTime;

    private List<String> idList;        //过滤的商品id集合    仅用于传递数据

    private String classifyId;          //商品分类id   仅用于传递数据

    private String goodsBarCode;        //商品编码    仅用于传递数据

    private String startTime;            //开始时间   仅用于传递数据

    private String endTime;                //结束时间    仅用于传递数据

    private String twoTime;

    private Long stockInNum;

    private String queryStatus = "0";

    private Long completeStockInAmount;

    private Boolean auditStatus;

    private Boolean historyStatus;

    private Integer purchaseReturnStatus;       //退送状态

    private Long notInStockNum;                 //未入库数量---->导出用

    private String expectedArrivalDate;

    public String getExpectedArrivalDate() {
        return expectedArrivalDate;
    }

    public void setExpectedArrivalDate(String expectedArrivalDate) {
        this.expectedArrivalDate = expectedArrivalDate;
    }

    public Long getNotInStockNum() {
        return notInStockNum;
    }

    public void setNotInStockNum(Long notInStockNum) {
        this.notInStockNum = notInStockNum;
    }

    public Integer getPurchaseReturnStatus() {
        return purchaseReturnStatus;
    }

    public void setPurchaseReturnStatus(Integer purchaseReturnStatus) {
        this.purchaseReturnStatus = purchaseReturnStatus;
    }

    public Boolean getHistoryStatus() {
        return historyStatus;
    }

    public void setHistoryStatus(Boolean historyStatus) {
        this.historyStatus = historyStatus;
    }

    public Boolean getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Boolean auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public Long getStockInAmount() {
        return stockInAmount;
    }

    public void setStockInAmount(Long stockInAmount) {
        this.stockInAmount = stockInAmount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getOrderDocumentId() {
        return orderDocumentId;
    }

    public void setOrderDocumentId(String orderDocumentId) {
        this.orderDocumentId = orderDocumentId;
    }

    public String getPlanDocumentId() {
        return planDocumentId;
    }

    public void setPlanDocumentId(String planDocumentId) {
        this.planDocumentId = planDocumentId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getOdooSupplierId() {
        return odooSupplierId;
    }

    public void setOdooSupplierId(Integer odooSupplierId) {
        this.odooSupplierId = odooSupplierId;
    }

    public Long getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Long orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Long getAdvance() {
        return advance;
    }

    public void setAdvance(Long advance) {
        this.advance = advance;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }


    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
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

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getOdooPurchaseId() {
        return odooPurchaseId;
    }

    public void setOdooPurchaseId(Integer odooPurchaseId) {
        this.odooPurchaseId = odooPurchaseId;
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

    public int getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(int validStatus) {
        this.validStatus = validStatus;
    }

    public List<PurchaseOrderDetail> getPurchaseOrderDetailList() {
        return purchaseOrderDetailList;
    }

    public void setPurchaseOrderDetailList(List<PurchaseOrderDetail> purchaseOrderDetailList) {
        this.purchaseOrderDetailList = purchaseOrderDetailList;
    }

    public String getPlanBeginTime() {
        return planBeginTime;
    }

    public void setPlanBeginTime(String planBeginTime) {
        this.planBeginTime = planBeginTime;
    }

    public String getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public void setGoodsBarCode(String goodsBarCode) {
        this.goodsBarCode = goodsBarCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getOneTime() {
        return oneTime;
    }

    public void setOneTime(String oneTime) {
        this.oneTime = oneTime;
    }

    public String getTwoTime() {
        return twoTime;
    }

    public void setTwoTime(String twoTime) {
        this.twoTime = twoTime;
    }

    public void setQueryStatus(String queryStatus) {
        this.queryStatus = queryStatus;
    }
//	public String getJudge() {
//		return judge;
//	}
//
//	public void setJudge(String judge) {
//		this.judge = judge;
//	}

    public String getQueryStatus() {
        return queryStatus;
    }

    public String getPurchaseUserId() {
        return purchaseUserId;
    }

    public void setPurchaseUserId(String purchaseUserId) {
        this.purchaseUserId = purchaseUserId;
    }

    public String getPurchaseUserName() {
        return purchaseUserName;
    }

    public void setPurchaseUserName(String purchaseUserName) {
        this.purchaseUserName = purchaseUserName;
    }

    public String getToWarehouseId() {
        return toWarehouseId;
    }

    public void setToWarehouseId(String toWarehouseId) {
        this.toWarehouseId = toWarehouseId;
    }

    public String getToWarehouseName() {
        return toWarehouseName;
    }

    public void setToWarehouseName(String toWarehouseName) {
        this.toWarehouseName = toWarehouseName;
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

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getPurchaseStatus() {
        return purchaseStatus;
    }

    public void setPurchaseStatus(Integer purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public Integer getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(Integer purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getStockInNum() {
        return stockInNum;
    }

    public void setStockInNum(Long stockInNum) {
        this.stockInNum = stockInNum;
    }

    public Long getCompleteStockInAmount() {
        return completeStockInAmount;
    }

    public void setCompleteStockInAmount(Long completeStockInAmount) {
        this.completeStockInAmount = completeStockInAmount;
    }

    public Long getDistinctPrice() {
        return distinctPrice;
    }

    public void setDistinctPrice(Long distinctPrice) {
        this.distinctPrice = distinctPrice;
    }
}
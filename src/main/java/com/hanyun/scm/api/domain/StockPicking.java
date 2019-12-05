package com.hanyun.scm.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.domain.request.BaseRequest;
import com.hanyun.scm.api.domain.request.stock.SynchronizedStockPickingRequest;

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
public class StockPicking extends BaseRequest {

	@JsonIgnore
	private Long id;

	private String stockPickingId; // 库存移动单id

	private String stockPickingDocumentId; // 库存移动单id

	private String brandId; // 品牌id

	private String storeId; // 门店id

	private String storeName; // 门店名称

	private String srcOrderId; // 来源订单id

	private String srcOrderDocumentId; // 来源订单id

	private String supplierId; // 供应商id(采购出入库使用)

	private String supplierName; // 供应商名称

	private String toWarehouseId; // 目标仓库id

	private String toWarehouseName; // 目标仓库名称

	private String srcWarehouseId; // 来源仓库id

	private String srcWarehouseName; // 来源仓库名称

	private Long purchasePrice; // 供应商价格

	private Long stockPickingPrice; // 库存移动价格

	private Integer stockPickingStatus; // 库存移动状态 0、以保存 1、已完成

	private String stockPickingStatusName;

	private Long purchaseAmount; // 采购数量

	private Long pickingAmount; // 库存移动数量

	private String operatorId; // 操作人id

	private String operatorName; // 操作人名称

	private Integer odooPickingIncomeId; // odoo入库单id

	private Integer odooPickingOutId; // odoo出库单id

	private Integer stockPickingType; // 库存移动类型 0、采购入库单 1、采购退货单 2、普通入库单 3、普通出库单
										// 4、内部调拨单

	private Long stock; // 当前库存

	private Integer incomeReason; // 入库原因

	private Integer outReason; // 出库原因

	private String remark; // 备注

	private Date createTime;

	private Date updateTime;

	private String goodsBarCode;

	private String beginTime;

	private String endTime;

	private String stockPickingGoodsJson; // 商品列表json

	private String toStoreName;

	private String toStoreId;

	private Integer validStatus;

	private Integer choose;

	private Integer allocationType; // 1.调拨出库 其他~~

	private Integer delectAction; // 判断是否为删除操作,1为删除 ,2为审核

	private String auditorId;

	private String auditorName;

	private Date auditorTime;

	private String businessManId;

	private String businessManName;

	private List<StockPickingGoods> stockPickingGoodsList; // 商品列表

	private Integer purchaseReturnReason;  //退货原因

	private Long distinctPrice;  //优惠金额

	private Date purchaseReturnTime;  //退货时间

	private Integer purchaseType;  //采购类型
	
    private String exportReason;	//导出原因： 入库原因，出库原因
    
    private String orderType;		//导出的单据类型： 入库单，出库单
    
    private String exportWarehouse;
    
    private String exportOrderStatus;
    
    private String exportOrderTime;  //导出用 ：单据日期
    
    private String exportOrderCreateTime;//导出用： 制单日期
    
    private String exportconformTime;	//导出用：审核日期
    
    private String goodsTypeName;
    
    private String goodsBrandName;

	private Long paymentPrice;	//付款金额

	private Integer paymentStatus;	//付款状态

	private String paymentStatusName;	//付款状态名

	private Boolean auditStatus;

	private String userId;

	private Boolean historyStatus;

	private Integer purchaseReturnStatus;       //退送状态

	private Long finishPurchaseReturnAmount;    //审核完成的退送数量

    public StockPicking(SynchronizedStockPickingRequest synchronizedStockPickingRequest) {
		this.brandId = synchronizedStockPickingRequest.getBrandId();
		this.storeId = synchronizedStockPickingRequest.getStoreId();
		if (synchronizedStockPickingRequest.getType()== Consts.SYNCHRONIZED_STOCK_PICKING_TYPE_IN) {
			this.stockPickingType = Consts.STOCK_PICKING_TYPE_STOCK_IN;
			this.toWarehouseId = synchronizedStockPickingRequest.getWarehouseId();
			this.toWarehouseName = synchronizedStockPickingRequest.getWarehouseName();
		} else {
			this.stockPickingType = Consts.STOCK_PICKING_TYPE_STOCK_OUT;
			this.srcWarehouseId = synchronizedStockPickingRequest.getWarehouseId();
			this.srcWarehouseName = synchronizedStockPickingRequest.getWarehouseName();
		}
		this.stockPickingPrice = synchronizedStockPickingRequest.getTotalPrice();
		this.pickingAmount = synchronizedStockPickingRequest.getTotalAmount();
    }

    public String getPaymentStatusName() {
		return paymentStatusName;
	}

	public void setPaymentStatusName(String paymentStatusName) {
		this.paymentStatusName = paymentStatusName;
	}

	public Long getFinishPurchaseReturnAmount() {
		return finishPurchaseReturnAmount;
	}

	public void setFinishPurchaseReturnAmount(Long finishPurchaseReturnAmount) {
		this.finishPurchaseReturnAmount = finishPurchaseReturnAmount;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Boolean getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Boolean auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getStockPickingStatusName() {
		return stockPickingStatusName;
	}

	public void setStockPickingStatusName(String stockPickingStatusName) {
		this.stockPickingStatusName = stockPickingStatusName;
	}

	public Integer getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(Integer purchaseType) {
		this.purchaseType = purchaseType;
	}

	public Integer getPurchaseReturnReason() {
		return purchaseReturnReason;
	}

	public void setPurchaseReturnReason(Integer purchaseReturnReason) {
		this.purchaseReturnReason = purchaseReturnReason;
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

	public String getStockPickingGoodsJson() {
		return stockPickingGoodsJson;
	}

	public void setStockPickingGoodsJson(String stockPickingGoodsJson) {
		this.stockPickingGoodsJson = stockPickingGoodsJson;
	}

	public List<StockPickingGoods> getStockPickingGoodsList() {
		return stockPickingGoodsList;
	}

	public void setStockPickingGoodsList(List<StockPickingGoods> stockPickingGoodsList) {
		this.stockPickingGoodsList = stockPickingGoodsList;
	}

	public Integer getStockPickingType() {
		return stockPickingType;
	}

	public void setStockPickingType(Integer stockPickingType) {
		this.stockPickingType = stockPickingType;
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

	public Integer getStockPickingStatus() {
		return stockPickingStatus;
	}

	public void setStockPickingStatus(Integer stockPickingStatus) {
		this.stockPickingStatus = stockPickingStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStockPickingId() {
		return stockPickingId;
	}

	public void setStockPickingId(String stockPickingId) {
		this.stockPickingId = stockPickingId;
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

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
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

	public Integer getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(Integer validStatus) {
		this.validStatus = validStatus;
	}

	public String getGoodsBarCode() {
		return goodsBarCode;
	}

	public void setGoodsBarCode(String goodsBarCode) {
		this.goodsBarCode = goodsBarCode;
	}

	public String getStockPickingDocumentId() {
		return stockPickingDocumentId;
	}

	public void setStockPickingDocumentId(String stockPickingDocumentId) {
		this.stockPickingDocumentId = stockPickingDocumentId;
	}

	public String getSrcOrderDocumentId() {
		return srcOrderDocumentId;
	}

	public void setSrcOrderDocumentId(String srcOrderDocumentId) {
		this.srcOrderDocumentId = srcOrderDocumentId;
	}

	public void setToStoreName(String toStoreName) {
		this.toStoreName = toStoreName;
	}

	public String getToStoreId() {
		return toStoreId;
	}

	public void setToStoreId(String toStoreId) {
		this.toStoreId = toStoreId;
	}

	public Integer getAllocationType() {
		return allocationType;
	}

	public void setAllocationType(Integer allocationType) {
		this.allocationType = allocationType;
	}

	public Integer getChoose() {
		return choose;
	}

	public Integer getDelectAction() {
		return delectAction;
	}

	public void setDelectAction(Integer delectAction) {
		this.delectAction = delectAction;
	}

	public void setChoose(Integer choose) {
		this.choose = choose;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getToStoreName() {
		return toStoreName;
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

	public Date getAuditorTime() {
		return auditorTime;
	}

	public void setAuditorTime(Date auditorTime) {
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
	

	public String getExportReason() {
		return exportReason;
	}

	public void setExportReason(String exportReason) {
		this.exportReason = exportReason;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getExportWarehouse() {
		return exportWarehouse;
	}

	public void setExportWarehouse(String exportWarehouse) {
		this.exportWarehouse = exportWarehouse;
	}

	public String getExportOrderStatus() {
		return exportOrderStatus;
	}

	public void setExportOrderStatus(String exportOrderStatus) {
		this.exportOrderStatus = exportOrderStatus;
	}

	public String getExportOrderTime() {
		return exportOrderTime;
	}

	public void setExportOrderTime(String exportOrderTime) {
		this.exportOrderTime = exportOrderTime;
	}

	public String getExportOrderCreateTime() {
		return exportOrderCreateTime;
	}

	public void setExportOrderCreateTime(String exportOrderCreateTime) {
		this.exportOrderCreateTime = exportOrderCreateTime;
	}

	public String getExportconformTime() {
		return exportconformTime;
	}

	public void setExportconformTime(String exportconformTime) {
		this.exportconformTime = exportconformTime;
	}
	

	public StockPicking() {
		super();
	}
	

	public String getGoodsTypeName() {
		return goodsTypeName;
	}

	public void setGoodsTypeName(String goodsTypeName) {
		this.goodsTypeName = goodsTypeName;
	}
	
	public String getGoodsBrandName() {
		return goodsBrandName;
	}

	public Long getPaymentPrice() {
		return paymentPrice;
	}

	public void setPaymentPrice(Long paymentPrice) {
		this.paymentPrice = paymentPrice;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public void setGoodsBrandName(String goodsBrandName) {
		this.goodsBrandName = goodsBrandName;
	}


	public StockPicking(String stockPickingId, String stockPickingDocumentId, String brandId,
						String storeId, String srcOrderId, String srcOrderDocumentId, String supplierId, String supplierName,
						String srcWarehouseId, String srcWarehouseName, String toWarehouseId, String toWarehouseName,
						Long purchasePrice, Long purchaseAmount, Long stockPickingPrice, Long pickingAmount,
						Integer stockPickingStatus, String operatorId, String operatorName, Integer stockPickingType,
						Long stock, Integer incomeReason, Integer outReason, String remark, Date createTime,
						Date updateTime, Integer validStatus, Integer purchaseType, String businessManId, String businessManName,
						Long distinctPrice, Date purchaseReturnTime, Integer purchaseReturnReason) {
		this.stockPickingId = stockPickingId;
		this.stockPickingDocumentId = stockPickingDocumentId;
		this.brandId = brandId;
		this.storeId = storeId;
		this.srcOrderId = srcOrderId;
		this.srcOrderDocumentId = srcOrderDocumentId;
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.srcWarehouseId = srcWarehouseId;
		this.srcWarehouseName = srcWarehouseName;
		this.toWarehouseId = toWarehouseId;
		this.toWarehouseName = toWarehouseName;
		this.purchasePrice = purchasePrice;
		this.purchaseAmount = purchaseAmount;
		this.stockPickingPrice = stockPickingPrice;
		this.pickingAmount = pickingAmount;
		this.stockPickingStatus = stockPickingStatus;
		this.operatorId = operatorId;
		this.operatorName = operatorName;
		this.stockPickingType = stockPickingType;
		this.stock = stock;
		this.incomeReason = incomeReason;
		this.outReason = outReason;
		this.remark = remark;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.validStatus = validStatus;
		this.purchaseType = purchaseType;
		this.businessManId = businessManId;
		this.businessManName = businessManName;
		this.distinctPrice = distinctPrice;
		this.purchaseReturnTime = purchaseReturnTime;
		this.purchaseReturnReason = purchaseReturnReason;
	}
}

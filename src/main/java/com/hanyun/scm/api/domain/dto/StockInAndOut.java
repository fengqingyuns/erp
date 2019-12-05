package com.hanyun.scm.api.domain.dto;

import com.alibaba.druid.util.StringUtils;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.dao.WarehouseDao;
import com.hanyun.scm.api.domain.*;

import java.util.ArrayList;
import java.util.Date;
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
 * StockInAndOut
 * Date: 2017/6/15
 * Time: 下午3:08
 *
 * @author tianye@hanyun.com
 */
public class StockInAndOut {

    private String messageType; //订单类型(值为"STOCK_ORDER")

    private String brandId;

    private String storeId;

    private String orderId;

    private String orderShowId;

    private String srcOrderId;

    private String srcOrderShowId;

    private String toWarehouseId;

    private String toWarehouseName;

    private String toWarehouseExtId;

    private String srcWarehouseId;

    private String srcWarehouseName;

    private String srcWarehouseExtId;

    private String supplierId;

    private String supplierName;

    private Integer orderType;  //0、采购入库 1、采购退货 2、其它入库 3、其它出库 4、转仓单 5、配送单 6、配送收货单 7、报损单 8、报溢单 9、组装单 10、拆分单

    private Integer testFlag = 0;

    private List<StockInAndOutDetail> stockInDetailList;

    private List<StockInAndOutDetail> stockOutDetailList;

    public StockInAndOut(){}

    public StockInAndOut(StockPicking stockPicking, WarehouseDao warehouseDao) {
        this.brandId = stockPicking.getBrandId();
        this.storeId = stockPicking.getStoreId();
        this.orderId = stockPicking.getStockPickingId();
        this.orderShowId = stockPicking.getStockPickingDocumentId();
        this.srcOrderId = stockPicking.getSrcOrderId();
        this.srcOrderShowId = stockPicking.getSrcOrderDocumentId();
        this.toWarehouseId = stockPicking.getToWarehouseId();
        this.toWarehouseName = stockPicking.getToWarehouseName();
        this.toWarehouseExtId = getExtId(stockPicking.getToWarehouseId(), warehouseDao);
        this.srcWarehouseId = stockPicking.getSrcWarehouseId();
        this.srcWarehouseName = stockPicking.getSrcWarehouseName();
        this.srcWarehouseExtId = getExtId(stockPicking.getSrcWarehouseId(), warehouseDao);
        this.supplierId = stockPicking.getSupplierId();
        this.supplierName = stockPicking.getSupplierName();
        this.orderType = stockPicking.getStockPickingType();
        //出库入库标识
        int inOutStatus;
        if (stockPicking.getStockPickingType() == Consts.STOCK_PICKING_TYPE_PURCHASE_STOCK_IN || stockPicking.getStockPickingType() == Consts.STOCK_PICKING_TYPE_STOCK_IN) {
            inOutStatus = Consts.STOCK_PICKING_TYPE_STOCK_IN;
        } else if (stockPicking.getStockPickingType() == Consts.STOCK_PICKING_TYPE_PURCHASE_RETURN || stockPicking.getStockPickingType() == Consts.STOCK_PICKING_TYPE_STOCK_OUT) {
            inOutStatus = Consts.STOCK_PICKING_TYPE_STOCK_OUT;
        } else {
            inOutStatus = Consts.STOCK_PICKING_TYPE_STOCK_INTERVAL;
        }
        List<StockInAndOutDetail> stockInAndOutDetailList = new ArrayList<>();
        for (StockPickingGoods stockPickingGoods : stockPicking.getStockPickingGoodsList()) {
            StockInAndOutDetail stockInAndOutDetail = new StockInAndOutDetail();
            stockInAndOutDetail.setGoodsId(stockPickingGoods.getGoodsId());
            stockInAndOutDetail.setGoodsName(stockPickingGoods.getGoodsName());
            stockInAndOutDetail.setBarCode(stockPickingGoods.getGoodsBarCode());
            stockInAndOutDetail.setSkuCode(stockPickingGoods.getGoodsCode());
            stockInAndOutDetail.setClassifyId(stockPickingGoods.getClassifyId());
            stockInAndOutDetail.setClassifyName(stockPickingGoods.getClassifyName());
            stockInAndOutDetail.setUnit(stockPickingGoods.getUnitName());
            stockInAndOutDetail.setFeatures(stockPickingGoods.getFeatures());
            stockInAndOutDetail.setPrice(stockPickingGoods.getPickingPrice());
            stockInAndOutDetail.setAmount(stockPickingGoods.getPickingAmount());
            stockInAndOutDetail.setCreateTime(stockPickingGoods.getCreateTime());
            stockInAndOutDetail.setUpdateTime(new Date());
            stockInAndOutDetailList.add(stockInAndOutDetail);
        }
        if (inOutStatus == Consts.STOCK_PICKING_TYPE_STOCK_IN || inOutStatus == Consts.STOCK_PICKING_TYPE_STOCK_INTERVAL) {
            this.stockInDetailList = stockInAndOutDetailList;
        }
        if (inOutStatus == Consts.STOCK_PICKING_TYPE_STOCK_OUT || inOutStatus == Consts.STOCK_PICKING_TYPE_STOCK_INTERVAL) {
            this.stockOutDetailList = stockInAndOutDetailList;
        }
    }

    public StockInAndOut(StockSpillLossOrder stockSpillLossOrder, WarehouseDao warehouseDao) {
        this.brandId = stockSpillLossOrder.getBrandId();
        this.storeId = stockSpillLossOrder.getStoreId();
        this.orderId = stockSpillLossOrder.getStockVarianceId();
        this.orderShowId = stockSpillLossOrder.getStockVarianceDocumentId();
        this.srcOrderId = stockSpillLossOrder.getStockCheckDifferenceId();
        this.srcOrderShowId = stockSpillLossOrder.getStockCheckDifferenceDocumentId();
        this.toWarehouseId = stockSpillLossOrder.getWarehouseId();
        this.toWarehouseName = stockSpillLossOrder.getWarehouseName();
        this.toWarehouseExtId = getExtId(stockSpillLossOrder.getWarehouseId(), warehouseDao);
        this.srcWarehouseId = stockSpillLossOrder.getWarehouseId();
        this.srcWarehouseName = stockSpillLossOrder.getWarehouseName();
        this.srcWarehouseExtId = getExtId(stockSpillLossOrder.getWarehouseId(), warehouseDao);
        this.orderType = stockSpillLossOrder.getStockVarianceType()==Consts.STOCK_VARIANCE_TYPE_LOSS?
                Consts.STOCK_IN_OUT_STOCK_LOSS:Consts.STOCK_IN_OUT_STOCK_OVERFLOW;
        List<StockInAndOutDetail> stockInAndOutDetailList = new ArrayList<>();
        for (StockSpillLossOrderDetail stockSpillLossOrderDetail : stockSpillLossOrder.getDetailList()) {
            StockInAndOutDetail stockInAndOutDetail = new StockInAndOutDetail();
            stockInAndOutDetail.setGoodsId(stockSpillLossOrderDetail.getGoodsId());
            stockInAndOutDetail.setGoodsName(stockSpillLossOrderDetail.getGoodsName());
            stockInAndOutDetail.setBarCode(stockSpillLossOrderDetail.getGoodsBarCode());
            stockInAndOutDetail.setSkuCode(stockSpillLossOrderDetail.getGoodsCode());
            stockInAndOutDetail.setClassifyId(stockSpillLossOrderDetail.getClassifyId());
            stockInAndOutDetail.setClassifyName(stockSpillLossOrderDetail.getClassifyName());
            stockInAndOutDetail.setUnit(stockSpillLossOrderDetail.getUnitName());
            stockInAndOutDetail.setAmount(stockSpillLossOrderDetail.getDifferencePrice());
            stockInAndOutDetail.setCreateTime(stockSpillLossOrderDetail.getCreateTime());
            stockInAndOutDetail.setUpdateTime(new Date());
            stockInAndOutDetailList.add(stockInAndOutDetail);
        }
        if (stockSpillLossOrder.getStockVarianceType() == Consts.SPILL_LOSS_IN_STOCK) {
            this.stockInDetailList = stockInAndOutDetailList;
        }
        if (stockSpillLossOrder.getStockVarianceType() == Consts.SPILL_LOSS_OUT_STOCK) {
            this.stockOutDetailList = stockInAndOutDetailList;
        }
    }

    public StockInAndOut(DisassemblyOrderDetail newDetail, List<DisassemblyOrderDetail> newSonList, DisassemblyOrder disassemblyOrder, WarehouseDao warehouseDao) {
        this.brandId = disassemblyOrder.getBrandId();
        this.storeId = disassemblyOrder.getStoreId();
        this.orderId = disassemblyOrder.getDisassemblyOrderId();
        this.orderShowId = disassemblyOrder.getDisassemblyOrderDocumentId();
        this.toWarehouseId = disassemblyOrder.getToWarehouseId();
        this.toWarehouseName = disassemblyOrder.getToWarehouseName();
        this.toWarehouseExtId = getExtId(disassemblyOrder.getToWarehouseId(), warehouseDao);
        this.srcWarehouseId = disassemblyOrder.getSrcWarehouseId();
        this.srcWarehouseName = disassemblyOrder.getSrcWarehouseName();
        this.srcWarehouseExtId = getExtId(disassemblyOrder.getSrcWarehouseId(), warehouseDao);
        this.orderType = disassemblyOrder.getDisassemblyOrderType()==Consts.DISASSEMBLY_ASSEMBLE_TYPE?
                Consts.STOCK_IN_OUT_ASSEMBLY_ORDER:Consts.STOCK_IN_OUT_DISASSEMBLY_ORDER;
        List<StockInAndOutDetail> motherOrderDetailList = new ArrayList<>();
        StockInAndOutDetail motherGoods = new StockInAndOutDetail();
        motherGoods.setGoodsId(newDetail.getGoodsId());
        motherGoods.setGoodsName(newDetail.getGoodsName());
        motherGoods.setBarCode(newDetail.getGoodsBarCode());
        motherGoods.setSkuCode(newDetail.getGoodsCode());
        motherGoods.setClassifyId(newDetail.getClassifyId());
        motherGoods.setClassifyName(newDetail.getClassifyName());
        motherGoods.setUnit(newDetail.getUnitName());
        motherGoods.setFeatures(newDetail.getFeatures());
        motherGoods.setAmount(newDetail.getStockNum());
        motherGoods.setCreateTime(newDetail.getCreateTime());
        motherGoods.setUpdateTime(new Date());
        motherOrderDetailList.add(motherGoods);
        List<StockInAndOutDetail> childrenOrderDetailList = new ArrayList<>();
        for (DisassemblyOrderDetail disassemblyOrderDetail : newSonList) {
            StockInAndOutDetail stockInAndOutDetail = new StockInAndOutDetail();
            stockInAndOutDetail.setGoodsId(disassemblyOrderDetail.getGoodsId());
            stockInAndOutDetail.setGoodsName(disassemblyOrderDetail.getGoodsName());
            stockInAndOutDetail.setBarCode(disassemblyOrderDetail.getGoodsBarCode());
            stockInAndOutDetail.setSkuCode(disassemblyOrderDetail.getGoodsCode());
            stockInAndOutDetail.setClassifyId(disassemblyOrderDetail.getClassifyId());
            stockInAndOutDetail.setClassifyName(disassemblyOrderDetail.getClassifyName());
            stockInAndOutDetail.setUnit(disassemblyOrderDetail.getUnitName());
            stockInAndOutDetail.setFeatures(disassemblyOrderDetail.getFeatures());
            stockInAndOutDetail.setAmount(disassemblyOrderDetail.getStockNum());
            stockInAndOutDetail.setCreateTime(disassemblyOrderDetail.getCreateTime());
            stockInAndOutDetail.setUpdateTime(new Date());
            childrenOrderDetailList.add(stockInAndOutDetail);
        }
        if (disassemblyOrder.getDisassemblyOrderType() == Consts.DISASSEMBLY_ASSEMBLE_TYPE) {
            this.stockInDetailList = motherOrderDetailList;
            this.stockOutDetailList = childrenOrderDetailList;
        }
        if (disassemblyOrder.getDisassemblyOrderType() == Consts.DISASSEMBLY_SPLIT_TYPE){
            this.stockInDetailList = childrenOrderDetailList;
            this.stockOutDetailList = motherOrderDetailList;
        }
    }

    private String getExtId(String warehouseId, WarehouseDao warehouseDao) {
        if (StringUtils.isEmpty(warehouseId) || warehouseDao == null) {
            return "";
        }
        Warehouse warehouse = warehouseDao.selectByWarehouseId(warehouseId);
        if (warehouse == null) {
            return "";
        }
        return warehouse.getExtId();
    }


    public String getToWarehouseExtId() {
        return toWarehouseExtId;
    }

    public void setToWarehouseExtId(String toWarehouseExtId) {
        this.toWarehouseExtId = toWarehouseExtId;
    }

    public String getSrcWarehouseExtId() {
        return srcWarehouseExtId;
    }

    public void setSrcWarehouseExtId(String srcWarehouseExtId) {
        this.srcWarehouseExtId = srcWarehouseExtId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderShowId() {
        return orderShowId;
    }

    public void setOrderShowId(String orderShowId) {
        this.orderShowId = orderShowId;
    }

    public String getSrcOrderId() {
        return srcOrderId;
    }

    public void setSrcOrderId(String srcOrderId) {
        this.srcOrderId = srcOrderId;
    }

    public String getSrcOrderShowId() {
        return srcOrderShowId;
    }

    public void setSrcOrderShowId(String srcOrderShowId) {
        this.srcOrderShowId = srcOrderShowId;
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

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public List<StockInAndOutDetail> getStockInDetailList() {
        return stockInDetailList;
    }

    public void setStockInDetailList(List<StockInAndOutDetail> stockInDetailList) {
        this.stockInDetailList = stockInDetailList;
    }

    public List<StockInAndOutDetail> getStockOutDetailList() {
        return stockOutDetailList;
    }

    public void setStockOutDetailList(List<StockInAndOutDetail> stockOutDetailList) {
        this.stockOutDetailList = stockOutDetailList;
    }

    public Integer getTestFlag() {
        return testFlag;
    }

    public void setTestFlag(Integer testFlag) {
        this.testFlag = testFlag;
    }
}

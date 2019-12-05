package com.hanyun.scm.api.domain.result;

import com.hanyun.ground.util.date.DateCalcUtil;
import com.hanyun.ground.util.date.DateFormatUtil;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.domain.*;
import org.joda.time.DateTimeUtils;

import java.util.Comparator;
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
 * AuditOrderResult
 * Date: 2017/7/14
 * Time: 下午4:16
 *
 * @author tianye@hanyun.com
 */
public class AuditOrderResult {

    private String orderId;

    private String orderDocumentId;

    private Integer orderType;

    private String operatorId;

    private String operatorName;

    private Date createTime;

    private Date updateTime;

    public AuditOrderResult(){}

    public AuditOrderResult(PurchaseApply purchaseApply) {
        this.orderId = purchaseApply.getApplyId();
        this.orderDocumentId = purchaseApply.getApplyDocumentId();
        this.orderType = Consts.PROCESS_TYPE_PURCHASE_APPLY;
        this.operatorId = purchaseApply.getOperatorId();
        this.operatorName = purchaseApply.getOperatorName();
        this.createTime = purchaseApply.getCreateTime();
        this.updateTime = purchaseApply.getUpdateTime();
    }

    public AuditOrderResult(PurchasePlan purchasePlan) {
        this.orderId = purchasePlan.getPlanId();
        this.orderDocumentId = purchasePlan.getPlanDocumentId();
        this.orderType = Consts.PROCESS_TYPE_PURCHASE_PLAN;
        this.operatorId = purchasePlan.getOperatorId();
        this.operatorName = purchasePlan.getOperatorName();
        this.createTime = purchasePlan.getCreateTime();
        this.updateTime = purchasePlan.getUpdateTime();
    }

    public AuditOrderResult(PurchaseOrder purchaseOrder) {
        this.orderId = purchaseOrder.getOrderId();
        this.orderDocumentId = purchaseOrder.getOrderDocumentId();
        this.orderType = Consts.PROCESS_TYPE_PURCHASE_ORDER;
        this.operatorId = purchaseOrder.getOperatorId();
        this.operatorName = purchaseOrder.getOperatorName();
        this.createTime = purchaseOrder.getCreateTime();
        this.updateTime = purchaseOrder.getUpdateTime();
    }

    public AuditOrderResult(StockPicking stockPicking) {
        this.orderId = stockPicking.getStockPickingId();
        this.orderDocumentId = stockPicking.getStockPickingDocumentId();
        switch (stockPicking.getStockPickingType()) {
            case Consts.STOCK_PICKING_TYPE_PURCHASE_STOCK_IN:
                this.orderType = Consts.PROCESS_TYPE_PURCHASE_STOCK_IN;
                break;
            case Consts.STOCK_PICKING_TYPE_PURCHASE_RETURN:
                this.orderType = Consts.PROCESS_TYPE_PURCHASE_RETURN;
                break;
            case Consts.STOCK_PICKING_TYPE_STOCK_IN:
                this.orderType = Consts.PROCESS_TYPE_STOCK_IN_AND_OUT;
                break;
            case Consts.STOCK_PICKING_TYPE_STOCK_OUT:
                this.orderType = Consts.PROCESS_TYPE_STOCK_IN_AND_OUT;
                break;
            case Consts.STOCK_PICKING_TYPE_STOCK_INTERVAL:
                this.orderType = Consts.PROCESS_TYPE_STOCK_REQUISITION;
                break;
        }
        this.operatorId = stockPicking.getOperatorId();
        this.operatorName = stockPicking.getOperatorName();
        this.createTime = stockPicking.getCreateTime();
        this.updateTime = stockPicking.getUpdateTime();
    }

    public AuditOrderResult(DisassemblyOrder disassemblyOrder) {
        this.orderId = disassemblyOrder.getDisassemblyOrderId();
        this.orderDocumentId = disassemblyOrder.getDisassemblyOrderDocumentId();
        this.orderType = Consts.PROCESS_TYPE_DISASSEMBLY_ORDER;
        this.operatorId = disassemblyOrder.getMakeorderManId();
        this.operatorName = disassemblyOrder.getMakeorderManName();
        this.createTime = disassemblyOrder.getCreateTime();
        this.updateTime = disassemblyOrder.getUpdateTime();
    }

    public AuditOrderResult(StockCheckTask stockCheckTask) {
        this.orderId = stockCheckTask.getStockCheckTaskId();
        this.orderDocumentId = stockCheckTask.getStockCheckTaskDocumentId();
        this.orderType = Consts.PROCESS_TYPE_STOCK_CHECK_TASK;
        this.operatorId = stockCheckTask.getOperatorId();
        this.operatorName = stockCheckTask.getOperatorName();
        this.createTime = stockCheckTask.getCreateTime();
        this.updateTime = stockCheckTask.getUpdateTime();
    }

    public AuditOrderResult(StockCheckOrder stockCheckOrder) {
        this.orderId = stockCheckOrder.getStockCheckOrderId();
        this.orderDocumentId = stockCheckOrder.getStockCheckOrderDocumentId();
        this.orderType = Consts.PROCESS_TYPE_STOCK_CHECK_ORDER;
        this.operatorId = stockCheckOrder.getOperatorId();
        this.operatorName = stockCheckOrder.getOperatorName();
        this.createTime = stockCheckOrder.getCreateTime();
        this.updateTime = stockCheckOrder.getUpdateTime();
    }

    public AuditOrderResult(StockCheckDifference stockCheckDifference) {
        this.orderId = stockCheckDifference.getStockCheckDifferenceId();
        this.orderDocumentId = stockCheckDifference.getStockCheckDifferenceDocumentId();
        this.orderType = Consts.PROCESS_TYPE_STOCK_CHECK_DIFFERENCE;
        this.operatorId = stockCheckDifference.getOperatorId();
        this.operatorName = stockCheckDifference.getOperatorName();
        this.createTime = stockCheckDifference.getCreateTime();
        this.updateTime = stockCheckDifference.getUpdateTime();
    }

    public AuditOrderResult(StockSpillLossOrder stockSpillLossOrder) {
        this.orderId = stockSpillLossOrder.getStockVarianceId();
        this.orderDocumentId = stockSpillLossOrder.getStockVarianceDocumentId();
        this.orderType = Consts.PROCESS_TYPE_STOCK_VARIANCE;
        this.operatorId = stockSpillLossOrder.getOperatorId();
        this.operatorName = stockSpillLossOrder.getOperatorName();
        this.createTime = stockSpillLossOrder.getCreateTime();
        this.updateTime = stockSpillLossOrder.getUpdateTime();
    }

    public AuditOrderResult(ReplenishmentApply replenishmentApply) {
        this.orderId = replenishmentApply.getReplenishmentId();
        this.orderDocumentId = replenishmentApply.getReplenishmentDocumentId();
        this.orderType = Consts.PROCESS_TYPE_REPLENISHMENT_APPLY;
        this.operatorId = replenishmentApply.getOperatorId();
        this.operatorName = replenishmentApply.getOperatorName();
        this.createTime = replenishmentApply.getCreateTime();
        this.updateTime = replenishmentApply.getUpdateTime();
    }

    public AuditOrderResult(DistributionOrder distributionOrder) {
        this.orderId = distributionOrder.getDistributionOrderId();
        this.orderDocumentId = distributionOrder.getDistributionOrderDocumentId();
        this.orderType = Consts.PROCESS_TYPE_DISTRIBUTION_ORDER;
        this.operatorId = distributionOrder.getMakeorderManId();
        this.operatorName = distributionOrder.getMakeorderManName();
        this.createTime = distributionOrder.getCreateTime();
        this.updateTime = distributionOrder.getUpdateTime();
    }

    public AuditOrderResult(LogisticsDeliveryOrder logisticsDeliveryOrder) {
        this.orderId = logisticsDeliveryOrder.getLogisticsOrderId();
        this.orderDocumentId = logisticsDeliveryOrder.getLogisticsOrderDocumentId();
        this.orderType = Consts.PROCESS_TYPE_LOGISTICS_DELIVERY;
        this.operatorId = logisticsDeliveryOrder.getMakeorderManId();
        this.operatorName = logisticsDeliveryOrder.getMakeorderManName();
        this.createTime = logisticsDeliveryOrder.getCreateTime();
        this.updateTime = logisticsDeliveryOrder.getUpdateTime();
    }

    public AuditOrderResult(InspectionPickingIn inspectionPickingIn) {
        this.orderId = inspectionPickingIn.getInspectionId();
        this.orderDocumentId = inspectionPickingIn.getInspectionDocumentId();
        this.orderType = Consts.PROCESS_TYPE_INSPECTION_PICKING_IN;
        this.operatorId = inspectionPickingIn.getOperatorId();
        this.operatorName = inspectionPickingIn.getOperatorName();
        this.createTime = inspectionPickingIn.getCreateTime();
        this.updateTime = inspectionPickingIn.getUpdateTime();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDocumentId() {
        return orderDocumentId;
    }

    public void setOrderDocumentId(String orderDocumentId) {
        this.orderDocumentId = orderDocumentId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
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
}

class ComparatorOrder implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        AuditOrderResult order1 = (AuditOrderResult) o1;
        AuditOrderResult order2 = (AuditOrderResult) o2;
        if (order1 == null || order1.getCreateTime() == null) {
            return 1;
        }
        if (order2 == null || order2.getCreateTime() == null) {
            return 0;
        }
        if (order2.getCreateTime().before(order1.getCreateTime())) {
            return 1;
        }
        return 0;
    }
}

package com.hanyun.scm.api.consts;

import java.util.HashMap;
import java.util.Map;

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
 * MapConsts
 * Date: 2017/3/2
 * Time: 下午6:38
 *
 * @author tianye@hanyun.com
 */
public abstract class MapConsts {

    //库存类型
    private static final Map<Integer, String> goodsType = new HashMap<>();
    //供应商类型
    private static final Map<Integer, String> supplierType = new HashMap<>();
    //供应商状态
    private static final Map<Integer, String> supplierStatus = new HashMap<>();
    //其他出入库
    private static final Map<Integer, String> stockIncomeReason= new HashMap<>();

    private static final Map<Integer, String> stockOutReason = new HashMap<>();

    private static final Map<Integer, String> stockOrderType = new HashMap<>();

    private static final Map<Integer, String> stockOrderStatus = new HashMap<>();

    private static final Map<Integer, String> stockCheckTaskType = new HashMap<>();

    private static final Map<Integer, String> stockCheckOrderStatus = new HashMap<>();

    private static final Map<Integer, String> stockCheckPurchaseType = new HashMap<>();

    private static final Map<Integer, String> purchaseApplyStatus = new HashMap<>();

    private static final Map<Integer, String> purchasePlanStatus = new HashMap<>();

    private static final Map<Integer, String> purchaseOrderStatus = new HashMap<>();

    private static final Map<Integer, String> purchaseReturnStatus = new HashMap<>();

    private static final Map<Integer, String> purchaseStockInStatus = new HashMap<>();

    private static final Map<Integer, String> stockVarianceType = new HashMap<>();

    private static final Map<Integer, String> stockVarianceSpillReason = new HashMap<>();

    private static final Map<Integer, String> stockVarianceLossReason = new HashMap<>();

    private static final Map<Integer, String> differenceStatus = new HashMap<>();

    private static final Map<Integer, String> differenceTaskType = new HashMap<>();

    //拆装单类型
    private static final Map<Integer, String> disassemblyType = new HashMap<>();

    //拆装单状态
    private static final Map<Integer, String> disassemblyStatus = new HashMap<>();

    //库存盘点
    private static final Map<Integer, String> stockCheckTaskStatus = new HashMap<>();

    private static final Map<Integer, String> disassemblyOrderGoodsType = new HashMap<>();
    //配送单单据状态
    private static final Map<Integer, String> distributionOrderStatus = new HashMap<>();
    //配送单收货状态
    private static final Map<Integer, String> receiptStatus = new HashMap<>();
    //补货申请单据状态
    private static final Map<Integer, String> replenishmentApplyStatus = new HashMap<>();
    //补货申请单收货状态
    private static final Map<Integer, String> replenishmentGetStatus = new HashMap<>();
    //物流发货单单据状态
    private static final Map<Integer, String> logisticsDeliveryStatus = new HashMap<>();

    //配送单-->收货状态
    private static final Map<Integer, String> deliveryStatus = new HashMap<>();

    //单据状态
    private static final Map<Integer, String> orderStatus = new HashMap<>();

    private static final Map<Integer, String> paymentStatus = new HashMap<>();

    private static final Map<Integer, String> returnPaymentStatus = new HashMap<>();

    private MapConsts(){}

    static {
        //库存类型
        goodsType.put(1, "商品");
        goodsType.put(2, "原材料");
        goodsType.put(3, "半成品");
        goodsType.put(4, "低值易耗品");
        goodsType.put(5, "固定资产");
        //供应商类型
        supplierType.put(1, "购销");
        supplierType.put(2, "联营");
        supplierType.put(3, "代销");
        //供应商状态
        supplierStatus.put(0, "正常");
        supplierStatus.put(1, "禁用");

        //单据状态
        orderStatus.put(0, "已保存");
        orderStatus.put(1, "已提审");
        orderStatus.put(2, "审核中");
        orderStatus.put(3, "已审核");
        orderStatus.put(4, "已终止");

        //其他出入库
        stockIncomeReason.put(0, "退还入库");
        stockIncomeReason.put(1, "赠送入库");
        stockIncomeReason.put(2, "调拨入库");
        stockIncomeReason.put(3, "其他入库");

        stockOutReason.put(0, "退还入库");
        stockOutReason.put(1, "赠送入库");
        stockOutReason.put(2, "调拨入库");
        stockOutReason.put(3, "其他入库");

        stockOrderType.put(2, "入库单");
        stockOrderType.put(3, "出库单");

        stockOrderStatus.put(0, "已保存");
        stockOrderStatus.put(1, "已提审");
        stockOrderStatus.put(2, "审核中");
        stockOrderStatus.put(3, "已审核");
        stockOrderStatus.put(4, "已终止");

        stockCheckTaskType.put(1, "全场盘点");
        stockCheckTaskType.put(2, "单品盘点");

        stockCheckOrderStatus.put(0, "已保存");
        stockCheckOrderStatus.put(3, "已审核");
        stockCheckOrderStatus.put(4, "已完成");
        stockCheckOrderStatus.put(5, "已终止");

        stockCheckPurchaseType.put(1, "统采");
        stockCheckPurchaseType.put(2, "自采");

        purchaseApplyStatus.put(0, "已保存");
        purchaseApplyStatus.put(1, "已审核");
        purchaseApplyStatus.put(2, "已计划");
        purchaseApplyStatus.put(3, "已提审");
        purchaseApplyStatus.put(4, "审核中");

        purchasePlanStatus.put(0, "已保存");
        purchasePlanStatus.put(1, "已审核");
        purchasePlanStatus.put(2, "已采购");

        purchaseOrderStatus.put(0, "已保存");
        purchaseOrderStatus.put(1, "已审核");
        purchaseOrderStatus.put(2, "部分入库");
        purchaseOrderStatus.put(3, "全部入库");
        purchaseOrderStatus.put(4, "已提审");
        purchaseOrderStatus.put(5, "审核中");

        purchaseReturnStatus.put(0, "已保存");
        purchaseReturnStatus.put(1, "已审核");

        purchaseStockInStatus.put(0, "已保存");
        purchaseStockInStatus.put(1, "已审核");

        stockVarianceType.put(1, "报损单");
        stockVarianceType.put(2, "报溢单");

        stockVarianceSpillReason.put(1, "盘点-盘盈");
        stockVarianceSpillReason.put(2, "其他");

        stockVarianceLossReason.put(1, "盘点-盘亏");
        stockVarianceLossReason.put(2, "质量问题");
        stockVarianceLossReason.put(3, "损坏");

        differenceStatus.put(0, "已保存");
        differenceStatus.put(1, "已审核");

        differenceTaskType.put(1, "全场盘点");
        differenceTaskType.put(2, "单品盘点");

        disassemblyType.put(1, "组装");
        disassemblyType.put(2, "拆分");

        disassemblyStatus.put(0, "已保存");
        disassemblyStatus.put(1, "已提审");
        disassemblyStatus.put(2, "审核中");
        disassemblyStatus.put(3, "已审核");
        disassemblyStatus.put(4, "已完成");
        disassemblyStatus.put(5, "已终止");

        stockCheckTaskStatus.put(0, "已保存");
        stockCheckTaskStatus.put(1, "已提审");
        stockCheckTaskStatus.put(2, "审核中");
        stockCheckTaskStatus.put(3, "已审核");
        stockCheckTaskStatus.put(4, "已完成");

        disassemblyOrderGoodsType.put(1, "母件");
        disassemblyOrderGoodsType.put(2, "子件");

        distributionOrderStatus.put(0, "已保存");
        distributionOrderStatus.put(1, "已提审");
        distributionOrderStatus.put(2, "审核中");
        distributionOrderStatus.put(3, "已审核");
        distributionOrderStatus.put(4, "已完成");
        distributionOrderStatus.put(5, "已终止");

        receiptStatus.put(0, "未发货");
        receiptStatus.put(1, "部分发货");
        receiptStatus.put(2, "部分收货");
        receiptStatus.put(3, "全部收货");
        receiptStatus.put(4, "全部发货");

        replenishmentApplyStatus.put(0, "已保存");
        replenishmentApplyStatus.put(1, "已提审");
        replenishmentApplyStatus.put(2, "审核中");
        replenishmentApplyStatus.put(3, "已审核");
        replenishmentApplyStatus.put(4, "已完成");
        replenishmentApplyStatus.put(5, "已终止");

        replenishmentGetStatus.put(0, "未完成");
        replenishmentGetStatus.put(1, "已完成");
        replenishmentGetStatus.put(2, "已作费");

        logisticsDeliveryStatus.put(0, "已保存");
        logisticsDeliveryStatus.put(1, "已提审");
        logisticsDeliveryStatus.put(2, "审核中");
        logisticsDeliveryStatus.put(3, "已审核");
        logisticsDeliveryStatus.put(4, "已完成");
        logisticsDeliveryStatus.put(5, "已终止");

        deliveryStatus.put(0, "未发货");
        deliveryStatus.put(1, "被占用");
        deliveryStatus.put(2, "已发货");
        deliveryStatus.put(3, "部分收货");
        deliveryStatus.put(4, "全部收货");

        paymentStatus.put(0, "未付款");
        paymentStatus.put(1, "部分付款");
        paymentStatus.put(2, "全部付款");

        returnPaymentStatus.put(0, "未退款");
        returnPaymentStatus.put(1, "部分退款");
        returnPaymentStatus.put(2, "全部退款");

    }

    public static Map<Integer, String> getOrderStatus() {
        return orderStatus;
    }

    public static Map<Integer, String> getGoodsType() {
        return goodsType;
    }

    public static Map<Integer, String> getSupplierType() {
        return supplierType;
    }

    public static Map<Integer, String> getSupplierStatus() {
        return supplierStatus;
    }

    public static Map<Integer, String> getStockIncomeReason() {
        return stockIncomeReason;
    }

    public static Map<Integer, String> getStockOutReason() {
        return stockOutReason;
    }

    public static Map<Integer, String> getStockOrderType() {
        return stockOrderType;
    }

    public static Map<Integer, String> getStockOrderStatus() {
        return stockOrderStatus;
    }

    public static Map<Integer, String> getStockCheckTaskType() {
        return stockCheckTaskType;
    }

    public static Map<Integer, String> getStockCheckOrderStatus() {
        return stockCheckOrderStatus;
    }

    public static Map<Integer, String> getStockCheckPurchaseType() {
        return stockCheckPurchaseType;
    }

    public static Map<Integer, String> getPurchaseApplyStatus() {
        return purchaseApplyStatus;
    }

    public static Map<Integer, String> getPurchasePlanStatus() {
        return purchasePlanStatus;
    }

    public static Map<Integer, String> getPurchaseOrderStatus() {
        return purchaseOrderStatus;
    }

    public static Map<Integer, String> getPurchaseReturnStatus() {
        return purchaseReturnStatus;
    }

    public static Map<Integer, String> getPurchaseStockInStatus() {
        return purchaseStockInStatus;
    }

    public static Map<Integer, String> getStockVarianceType() {
        return stockVarianceType;
    }

    public static Map<Integer, String> getStockVarianceSpillReason() {
        return stockVarianceSpillReason;
    }

    public static Map<Integer, String> getStockVarianceLossReason() {
        return stockVarianceLossReason;
    }

    public static Map<Integer, String> getDifferenceStatus() {
        return differenceStatus;
    }

    public static Map<Integer, String> getDifferenceTaskType() {
        return differenceTaskType;
    }

    public static Map<Integer, String> getDisassemblyType() {
        return disassemblyType;
    }

    public static Map<Integer, String> getDisassemblyStatus() {
        return disassemblyStatus;
    }

    public static Map<Integer, String> getStockCheckTaskStatus() {
        return stockCheckTaskStatus;
    }

    public static Map<Integer, String> getDisassemblyOrderGoodsType() {
        return disassemblyOrderGoodsType;
    }

    public static Map<Integer, String> getDistributionOrderStatus() {
        return distributionOrderStatus;
    }

    public static Map<Integer, String> getReceiptStatus() {
        return receiptStatus;
    }
    public static Map<Integer, String> getReplenishmentApplyStatus() {
        return replenishmentApplyStatus;
    }

    public static Map<Integer, String> getReplenishmentGetStatus() {
        return replenishmentGetStatus;
    }

    public static Map<Integer, String> getLogisticsDeliveryStatus() {
        return logisticsDeliveryStatus;
    }

    public static Map<Integer, String> getDeliveryStatus() {
        return deliveryStatus;
    }

    public static Map<Integer, String> getPaymentStatus() {
        return paymentStatus;
    }

    public static Map<Integer, String> getReturnPaymentStatus() {
        return returnPaymentStatus;
    }
}

package com.hanyun.scm.api.consts;

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
 * ExcelTitle   在此定义excel导出标题
 * Date: 2016/12/16
 * Time: 下午4:29
 *
 * @author tianye@hanyun.com
 */
public enum ExcelTitle {
    INDEX("序号"),
    WAREHOUSE_NAME("仓库名称"),
    GOODS_CLASSFIYNAME("商品分类"),
    STOCK_TYPE("库存类型"),
    GOODS_CODE("商品编码"),
    GOODS_NAME("商品名称"),
    UNIT_NAME("单位"),
    PURCHASE_RETURN_COUNT("采购退货数"),
    PURCHASE_RETURN_MONEY("退货金额(含税)"),
    STOCK_LOWER("库存下限"),
    SAFE_STOCK("安全库存"),
    STOCK_UPPER("库存上限"),
    STOCK_NUMBER("当前库存"),
    STOCK_TOTAL("库存合计"),
    SHORTAGE_NUMBER("短缺数"),
    SHORTAGE_PROPORTION("短缺比例"),
    WARNING_NUMBER("预警数"),
    WARNING_PROPORTION("预警比例"),
    BACKLOG_NUMBER("积压数"),
    BACKLOG_PROPORTION("积压比例"),
	SUPPLIER_CODE("供应商编码"),
	SUPPLIER_NAME("供应商名称"),
	ABBREVIATIONNAME("简称"),
	CONTACTS("联系人"),
	PHONE("手机号"),
	TEL("电话"),
	TYPE("类型"),
	ADDRESS("地址"),
    DISASSEMBLY_ID("拆装单号"),
    ORDER_TIME("单据日期"),
    ORDER_TYPE("类型"),
    MOTHER_GOODSNAME("母件商品"),
    MOTHER_GOODSNUMBER("母件数量"),
    SON_GOODSNAME("子件商品"),
    SON_GOODSNUMBER("子件数量"),
    MAKE_ORDER_NAME("制单人"),
    MAKE_ORDER_TIME("制单日期"),
    EXAMINE("审核人"),
    EXAMINT_TIME("审核日期"),
    ORDER_STATUS("单据状态"),
    GOODS_SKUCODE("SKU商品编码"),
    GOODS_BAR_CODE("商品条码"),
    SPECIFICATIONS("规格"),
    ALL_STOCKNUM("总库存"),
    NOW_STOCKNUM("当前库存"),
    COST_UNIT_PRICE("成本单价"),
    STOCK_BALANCE("库存余额"),
    INITIAL_STOCK_QUANT("期初库存"),
    INITIAL_UNIT_PRICE("期初成本"),
    INITIAL_TOTAL_PRICE("期初余额"),
	AVAILABLE_STATUS("可用状态"),
    DISTRIBUTION_ID("配送单号"),
    SOURCEAPPLY_ID("源补货申请单"),
    RECEIPT_NAME("补货门店"),
    DISTRIBUTION_CENTER("配送中心"),
    DISTRIBUTION_NUM("配送数"),
    ORDER_AMOUT("单据金额"),
    RECEIPT_STATUS("发货状态"),
    INTERNATIONAL_CODE("国际条码"),
    STORE_QUANT("补货门店库存"),
    CENTER_QUANT("配送中心库存"),
    LOGISTICSDELIVERY_ID("物流发货单号"),
    DELIVERY_ORDER_ID("物流单号"),
    DELIVERGOODS_DATE("发货日期"),
    GOODSRECEIPT("收货门店"),
    DISTRIBUTIONORDER_FOR_REMARK("配送单备注"),
    TOTAL_UNITPRICE("金额"),
    INSPECTIONINORDER("验收入库单"),
    SOURCE_DISTRIBUTION("源配送单"),
    RECEIPT_STORENAME("收货门店"),
    RECEIPT_NUM("收货数量"),
    REMARK("备注"),
    GOODS_UNITPRICE("单价"),
    GOODS_PIC("商品图片"),
    RECEIPTED_NUM("已收数"),
    NOT_RECEIPT_NUM("未收数");
	
    private String name;

    ExcelTitle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

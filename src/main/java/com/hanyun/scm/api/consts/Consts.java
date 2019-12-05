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
 * Consts
 * Date: 2016/12/8
 * Time: 下午6:23
 *
 * @author tianye@hanyun.com
 */
public interface Consts {

    int SUPPLIER_STATUS_USABLE = 0;
    int SUPPLIER_STATUS_UNUSED = 1;

    //其他出入库     结束-----------------------------------------------------
    int GOODS_TYPE_SELLING = 1;//商品
    int GOODS_TYPE_RAW_MATERIALS = 2;//原材料
    //商品用途
    int GOODS_TYPE_SEMI_FINISHED = 3;//半成品
    int GOODS_TYPE_LOW_VALUE_CONSUMABLE = 4;//低值易耗品
    int GOODS_TYPE_FIXED_ASSETS = 5;//固定资产

    int GOODS_USE_TYPE_PURCHASE = 1;//采购
    int GOODS_USE_TYPE_SELL = 2;//销售
    int GOODS_USE_TYPE_PURCHASE_SELL = 3;//销售&采购

    //有效标识
    int VALID_STATUS_VALID = 0;//有效
    int VALID_STATUS_INVALID = 1;//无效

    //仓库状态
    int WAREHOUSE_STATUS_ENABLE = 0;//启用
    int WAREHOUSE_STATUS_DISABLE = 1;//停用

    //采购申请单状态
    int PURCHASE_APPLY_STATUS_SAVED = 0;//已保存
    int PURCHASE_APPLY_STATUS_CONFIRMED = 1;//已审核
    int PURCHASE_APPLY_STATUS_PLAN = 2;//已计划
    int PURCHASE_APPLY_STATUS_COMMITED = 3; //已提审
    int PURCHASE_APPLY_STATUS_CONFIRMING = 4; //审核中

    //采购计划单状态
    int PURCHASE_PLAN_STATUS_SAVED = 0;//已保存
    int PURCHASE_PLAN_STATUS_COMMITED = 1;//已提审
    int PURCHASE_PLAN_STATUS_COFIRMING = 2;//审核中
    int PURCHASE_PLAN_STATUS_CONFIRMED = 3;//已审核

    //采购单状态
    int PURCHASE_ORDER_STATUS_SAVED = 0;//已保存
    int PURCHASE_ORDER_STATUS_CONFIRMED = 1;//已审核
    int PURCHASE_ORDER_STATUS_STOCK_IN = 2;//部分入库
    int PURCHASE_ORDER_STATUS_FINISHED = 3;//全部入库
    int PURCHASE_ORDER_STATUS_COMMITED = 4;//已提审
    int PURCHASE_ORDER_STATUS_CONFIRMING = 5;//审核中
    int PURCHASE_ORDER_STATUS_CANCEL = 6;//已终止

    //退货单状态
    int PURCHASE_RETURN_STATUS_SAVED = 0;//已保存
    int PURCHASE_RETURN_STATUS_CONFIRMED = 1;//已确认

    //采购入库单状态
    int PURCHASE_STOCK_IN_STATUS_SAVED = 0;//已保存
    int PURCHASE_STOCK_IN_STATUS_CONFIRMED = 1;//已确认

    //入库单状态
    int STOCK_IN_STATUS_SAVED = 0;//已保存
    int STOCK_IN_STATUS_CONFIRMED = 1;//已确认

    //出库单状态
    int STOCK_OUT_STATUS_SAVED = 0;//已保存
    int STOCK_OUT_STATUS_CONFIRMED = 1;//已确认

    //调拨单状态
    int STOCK_TRANSFER_STATUS_SAVED = 0;//已保存
    int STOCK_TRANSFER_STATUS_CONFIRMED = 1;//已确认
    int STOCK_TRANSFER_STATUS_RECEIVED = 2;//已收货

    //收货单状态
    int STOCK_RECEIVE_STATUS_SAVED = 0;//已保存
    int STOCK_RECEIVE_STATUS_CONFIRMED = 1;//已确认

    //盘点单状态
    int STOCK_CHECK_STATUS_SAVED = 0;//已保存
    int STOCK_CHECK_STATUS_CONFIRMED = 1;//已确认

    //盘点任务状态
    int STOCK_CHECK_TASK_STATUS_SAVED = 0;//已保存
    int STOCK_CHECK_TASK_STATUS_AUDITED = 1;//已审核
    int STOCK_CHECK_TASK_STATUS_COMPLETED = 2;//已结束
    int STOCK_CHECK_TASK_STATUS_DISCARDED = 3;//已废弃

    //移库类型
    int STOCK_PICKING_TYPE_PURCHASE_STOCK_IN = 0;//采购入库单
    int STOCK_PICKING_TYPE_PURCHASE_RETURN = 1;//采购退货单
    int STOCK_PICKING_TYPE_STOCK_IN = 2;//普通入库单
    int STOCK_PICKING_TYPE_STOCK_OUT = 3;//普通出库单
    int STOCK_PICKING_TYPE_STOCK_INTERVAL = 4;//移库单
    int STOCK_PICKING_TYPE_STOCK_TRANSFER = 5;//调拨单
    int STOCK_PICKING_TYPE_STOCK_RECEIVING = 6;//收货单

    int STOCK_QUANT_CHANGE_TYPE_SELL = 1;
    int STOCK_QUANT_CHANGE_TYPE_TRANSFER = 2;
    int STOCK_QUANT_CHANGE_TYPE_STOCK_PICKING = 3;
    int STOCK_QUANT_CHANGE_TYPE_REFUND = 4;
    int STOCK_QUANT_CHANGE_TYPE_STOCK_INTERVAL_OUT = 5;
    int STOCK_QUANT_CHANGE_TYPE_ALL_IN = 6;
    int STOCK_QUANT_CHANGE_TYPE_ALL_OUT = 7;

    int SYNCHRONIZED_STOCK_PICKING_TYPE_IN = 1;
    int SYNCHRONIZED_STOCK_PICKING_TYPE_OUT = 2;

    //是否带分页查询
    int QUERY_WITH_PAGE = 0;//带分页查询
    int QUERY_ALL = 1;//不带分页查询

    //是否默认补给此仓库
    int WAREHOUSE_RESUPPLY_TRUE = 0;//默认补给此仓库
    int WAREHOUSE_RESUPPLY_FALSE = 1;//不默认补给此仓库

    //仓库类型
    int WAREHOUSE_TYPE_BRAND = 0;//品牌仓库
    int WAREHOUSE_TYPE_STORE = 1;//门店仓库
    int WAREHOUSE_TYPE_SUPPLIER = 2;//供应商仓库(虚拟仓)
    int WAREHOUSE_TYPE_CUSTOMER = 3;//客户仓库(虚拟仓)
    int WAREHOUSE_TYPE_DISTRIBUTION_CENTER = 4;//配送中心

    //采购单订单
    int PURCHASE_ORDER_SAVE_STATUS = 0;//0、已保存 1、已确认 2、已入库 3、完成入库
    int PURCHASE_ORDER_COMFIRM_STATUS = 1;
    int PURCHASE_ORDER_RK_STATUS = 2;
    int PURCHASE_ORDER_WRK_STATUS = 3;

    //基础参数
    int PARAMETER_INITIALIZE_INT = 0;//参数初始化 int型
    Long PARAMETER_INITIALIZE_LONG = 0L;////参数初始化 Long型
    Double PARAMETER_INITIALIZE_DOUBLE = 0d;//参数初始化 Double型
    Double PARAMETER_PRICE_LONGTODOUBLE = 100.00;//价格long转double
    int LIST_IS_EMPTY = 0;//判断集合是否为空
    int DB_WITHOUT_HANDLE = 0;//判断数据删除，修改影响行数
    int NUMBER_LIMIT = 2;//小数点后保留两位
    int ACTION_DELETE = 1; //判断是否为删除操作
    int ACTION_AUDIT = 2;    //判断是否为审核操作
    //
    int STOCK_TYPE_LOWER = 1;    //库存短缺
    int STOCK_TYPE_SAFE = 2;    //安全库存
    int STOCK_TYPE_UPPER = 3;    //库存积压

    //盘点详细商品状态
    Integer GOODS_ENABLE_STATUS = 0;    //启用
    Integer GOODS_DISABLE_STATUS = 1;    //禁用

    //盘点差异
    Integer STOCK_CHECK_DIFFERENCE_STATUS = 0;//保存

    //盘点任务保存状态
    Integer STOCK_TASK_SAVE_STATUS = 0;

    //报损报溢单类型
    int SPILL_LOSS_IN_STOCK = 2;   //入库
    int SPILL_LOSS_OUT_STOCK = 1;     //出库

    //List大小
    int LIST_SIZE = 0;
    int LIST_ONE_SIZE = 1;

    //拆装单详情类型
    int MOTHER_LIST_TYPE = 1;       //母商品
    int SON_LIST_TYPE = 2;          //子商品

    //拆装单单据类型
    int DISASSEMBLY_ASSEMBLE_TYPE = 1;  //组装
    int DISASSEMBLY_SPLIT_TYPE = 2;     //拆分

    //list下标
    int LIST_ZERO_SIZE = 0;             //下标0

    //仓库状态
    int WAREHOUSE_ENABLE = 0;   //启用
    int WAREHOUSE_DISABLE = 1;  //停用

    int LOSS_ORDER = 1;
    int SPILL_ORDER = 2;

    int PAYMENT_TYPE_PURCHASE_STOCK_IN = 1;
    int PAYMENT_TYPE_PURCHASE_RETURN = 2;

    int PURCHASE_TYPE_UNIFIED = 1;
    int PURCHASE_TYPE_ALONE = 2;

    //配送单 ---> 保存时-->申请单的配送状态
    int REPLENISHMENT_APPLY_DISTRIBUTE_STATUS_NOT_IMPORTED = 0; //未配送
    int REPLENISHMENT_APPLY_DISTRIBUTE_STATUS_IMPORTED = 1;     //已配送

    //配送单-->收货状态
    int DELIVERYSTATUS_NOT = 0;     //未发货
    int DELIVERYSTATUS_OCCUPY = 1;  //被占用
    int DELIVERYSTATUS_DONE = 2;    //已发货
    int RECEIPT_PART = 3;           //部分收货
    int RECEIPT_ALL = 4;            //全部收货
    //申请单-收发状态
    int REPLENISHMENT_APPLY_RECEIPT_NONE = 0;       //未发货
    int REPLENISHMENT_APPLY_DELIVER_PART = 1;       //部分发货
    int REPLENISHMENT_APPLY_RECEIPT_PORTION = 2;    //部分收货
    int REPLENISHMENT_APPLY_RECEIPT_ALL = 3;        //全部收货
    int REPLENISHMENT_APPLY_DELIVER_OVER = 4;       //全部发货

    int GOODS_COUNT_IS_NONE = 0;//商品数为零

    int PROCESS_INSTANCE_STATUS_UNCONFIRMED = 0;
    int PROCESS_INSTANCE_STATUS_COMMITED = 1;
    int PROCESS_INSTANCE_STATUS_CONFIRMING = 2;
    int PROCESS_INSTANCE_STATUS_CONFIRMED = 3;
    int PROCESS_INSTANCE_STATUS_REJECT = 4;         //驳回

    //单据状态
    int ORDER_STATUS_ZERO = 0;          //保存
    int ORDER_STATUS_ONE = 1;           //提审
    int ORDER_STATUS_TWO = 2;           //审核中
    int ORDER_STATUS_THREE = 3;         //已审核
    int ORDER_STATUS_FOUR = 4;          //已完成
    int ORDER_STATUS_FIVE = 5;          //已终止

    //补货申请单
    int REPLENISHEMTN_APPLY_STATUS_SAVE = 0;        //已保存
    int REPLENISHEMTN_APPLY_STATUS_COMMITED = 1;    //已提审
    int REPLENISHEMTN_APPLY_STATUS_CONIFRMING = 2;  //审核中
    int REPLENISHEMTN_APPLY_STATUS_CONFIRMED = 3;   //已审核
    int REPLENISHEMTN_APPLY_STATUS_overdued = 4;    //已过期
    int REPLENISHEMTN_APPLY_STATUS_OVER = 5;        //已终止
    int REPLENISHEMTN_APPLY_STATUS_DONE = 6;        //已完成

    //配送单
    int DISTRIBUTION_ORDER_STATUS_SAVE = 0;        //已保存
    int DISTRIBUTION_ORDER_STATUS_COMMITED = 1;    //已提审
    int DISTRIBUTION_ORDER_STATUS_CONIFRMING = 2;  //审核中
    int DISTRIBUTION_ORDER_STATUS_CONFIRMED = 3;   //已审核
    int DISTRIBUTION_ORDER_STATUS_DONE = 4;        //已完成
    int DISTRIBUTION_ORDER_STATUS_OVER = 5;        //已终止

    //物流发货单
    int DELIVERY_ORDER_STATUS_SAVE = 0;        //已保存
    int DELIVERY_ORDER_STATUS_COMMITED = 1;    //已提审
    int DELIVERY_ORDER_STATUS_CONIFRMING = 2;  //审核中
    int DELIVERY_ORDER_STATUS_CONFIRMED = 3;   //已审核
    int DELIVERY_ORDER_STATUS_DONE = 4;        //已完成
    int DELIVERY_ORDER_STATUS_OVER = 5;        //已终止

    //入库单
    int INSPECTIONPICKING_ORDER_STATUS_SAVE = 0;        //已保存
    int INSPECTIONPICKING_ORDER_STATUS_COMMITED = 1;    //已提审
    int INSPECTIONPICKING_ORDER_STATUS_CONIFRMING = 2;  //审核中
    int INSPECTIONPICKING_ORDER_STATUS_CONFIRMED = 3;   //已审核
    int INSPECTIONPICKING_ORDER_STATUS_DONE = 4;        //已完成
    int INSPECTIONPICKING_ORDER_STATUS_OVER = 5;        //已终止

    //拆装单
    int DISASSEMBLY_ORDER_STATUS_SAVE = 0;        //已保存
    int DISASSEMBLY_ORDER_STATUS_COMMITED = 1;    //已提审
    int DISASSEMBLY_ORDER_STATUS_CONIFRMING = 2;  //审核中
    int DISASSEMBLY_ORDER_STATUS_CONFIRMED = 3;   //已审核
    int DISASSEMBLY_ORDER_STATUS_DONE = 4;        //已完成
    int DISASSEMBLY_ORDER_STATUS_OVER = 5;        //已终止


    //真·单据状态
    int ORDER_STATUS_SAVED = 0;
    int ORDER_STATUS_COMMITED = 1;
    int ORDER_STATUS_CONFIRMING = 2;
    int ORDER_STATUS_CONFIRMED = 3;
    int ORDER_STATUS_FINISHED = 4;
    int ORDER_STATUS_CANCEL = 5;

    int PROCESS_INSTANCE_DETAIL_STATUS_UNCONFIRMED = 0;
    int PROCESS_INSTANCE_DETAIL_STATUS_CONFIRMED = 1;

    int PROCESS_TYPE_PURCHASE_APPLY = 1;
    int PROCESS_TYPE_PURCHASE_PLAN = 2;
    int PROCESS_TYPE_PURCHASE_ORDER = 3;
    int PROCESS_TYPE_PURCHASE_STOCK_IN = 4;
    int PROCESS_TYPE_PURCHASE_RETURN = 5;
    int PROCESS_TYPE_STOCK_IN_AND_OUT = 6;
    int PROCESS_TYPE_STOCK_REQUISITION = 7;
    int PROCESS_TYPE_DISASSEMBLY_ORDER = 8;
    int PROCESS_TYPE_STOCK_CHECK_TASK = 9;
    int PROCESS_TYPE_STOCK_CHECK_ORDER = 10;
    int PROCESS_TYPE_STOCK_CHECK_DIFFERENCE = 11;
    int PROCESS_TYPE_STOCK_VARIANCE = 12;
    int PROCESS_TYPE_REPLENISHMENT_APPLY = 13;
    int PROCESS_TYPE_DISTRIBUTION_ORDER = 14;
    int PROCESS_TYPE_LOGISTICS_DELIVERY = 15;
    int PROCESS_TYPE_INSPECTION_PICKING_IN = 16;
    int PROCESS_TYPE_BACE_CARD = 17;            //退卡单

    int STOCK_VARIANCE_TYPE_LOSS = 1;
    int STOCK_VARIANCE_TYPE_FULL = 2;

    int SPILL_DIFFERENCE_CREATE = 1;

    int ORDER_VALID_STATUS_INVALID = 0;
    int ORDER_VALID_STATUS_VALID = 1;

    //流程状态
    int PROCESS_STATUS_UNDEPLOYED = 0;  //未部署
    int PROCESS_STATUS_DEPLOYED = 1;    //已部署

    //供应商状态
    int SUPPLIER_ENABLE_STATUS = 0;     //可用
    int SUPPLIER_DISABLE_STATUS = 1;    //禁用

    //删除 更新状态
    int SUPPLIER_STATUS_DELETE = 1;     //删除
    int SUPPLIER_STATUS_MODIFY = 2;     //更新

    int AUDIT_TYPE_USERS = 0;   //审批人
    int AUDIT_TYPE_ROLES = 1;   //审批角色
    int AUDIT_TYPE_ORGANIZATION = 2;    //审批机构

    //采购入库退送状态
    int PURCHASE_IN_STATUS_ALL = 0;     //全部配送
    int PURCHASE_IN_STATUS_PART = 1;    //部分配送

    int QUOTER_ORDER = 1;               //直接引订单
    int QUOTE_PUCHASEIN = 2;            //直接引用入库单
    int DIRECTTRTRUN_COMMODITY = 3;     //直接退货

    //其他出入库导出用
    int STOCK_IN_ORDER = 2;             //入库单
    int STOCK_OUT_ORDER = 3;            //出库单

    //盘点类型
    int FULL_COURT_INVENTORY = 1;        //全场盘点
    int SINGLE_GOODS_INVENTORY = 2;      //单品盘点

    //报溢报损的类型
    int SCRAP_STATS = 1;                //报损单
    int OVERFLOW_STATUS = 2;            //报溢单

    int SINGLE_DISTRIBUTION_TYPE = 1;   //单次配送
    int DOUBLE_DISTRIBUTION_TYPE = 2;   //多次配送

    int STOCK_IN_OUT_PURCHASE_STOCK_IN = 0; //采购入库
    int STOCK_IN_OUT_PURCHASE_RETURN = 1;   //采购退货
    int STOCK_IN_OUT_OTHER_STOCK_IN = 2;    //其它入库
    int STOCK_IN_OUT_OTHER_STOCK_OUT = 3;   //其它出库
    int STOCK_IN_OUT_INTERVAL = 4;          //转仓单
    int STOCK_IN_OUT_DISTRIBUTION = 5;      //配送单
    int STOCK_IN_OUT_DISTRIBUTION_STOCK_IN = 6; //配送收货单
    int STOCK_IN_OUT_STOCK_LOSS = 7;        //报损单
    int STOCK_IN_OUT_STOCK_OVERFLOW = 8;    //报溢单
    int STOCK_IN_OUT_ASSEMBLY_ORDER = 9;    //组装单
    int STOCK_IN_OUT_DISASSEMBLY_ORDER = 10;//拆分单
    int STOCK_IN_OUT_SELL = 11;//销售单
    int STOCK_IN_OUT_TRANSFER = 12;//库存同步
    int STOCK_IN_OUT_ALL_OUT = 13;//全部出库
    int STOCK_IN_OUT_ALL_IN = 14;//全部入库
    int STOCK_IN_OUT_INTERVAL_OUT = 15;//转仓出库
    int STOCK_IN_OUT_REFUND = 16;//退货入库

    String LAIPAOBA_BRAND_ID = "AD7F2B3EE5339D4A65986EF2D15C2CE2F0";

    String REMOTE_USER = "REMOTE_USER";
    //查询erp单据详情的类型
    int PURCHASE_ORDER_TYPE = 1;            //采购订单
    int DISTRIBUTION_ORDER_TYPE = 2;        //配送单
    int REPLENISHMENT_APPLY_ORDER_TYPE = 3; //申请单
    int PURCHASE_STOCK_IN_TYPE = 4;         //采购入库单
    int PURCHASE_RETURN_TYPE = 5;           //采购退货单
    int PURCHASE_APPLY_TYPE = 6;            //采购申请单
    int PURCHASE_PLAN_TYPE = 7;             //采购计划单
    int STOCK_CHECK_TASK_TYPE = 8;          //盘点任务单
    int STOCK_CHECK_ORDER_TYPE = 9;         //盘点订单
    int STOCK_CHECK_DIFFERENCE = 10;        //盘点差异单
    int OTHER_STOCK_IN_OUT_TYPE = 11;       //其他出入库
    int DISASSEMBLY_TYPE = 12;              //拆装单
    int STOCK_VARIANCE_TYPE = 13;           //报溢/损单
    int REQUISITION_TYPE = 14;              //转仓单
    int LOGISTICS_DELIVERY_ORDER_TYPE = 15; //物流发货单
    int INSPECTION_PICKING_IN_TYPE = 16;    //验收入库单

    //app返回单据类型
    int APP_PURCHASE_ORDER_TYPE = 1;                    //采购订单
    int APP_REPLENISHMENT_ORDER_TYPE = 2;               //补货申请单
    String APP_PURCHASE_ORDER_TYPE_NAME = "门店自采";         //采购订单名字
    String APP_REPLENISHMENT_ORDER_TYPE_NAME = "仓库订货";    //补货申请单名字
    String APP_ORDER_STATS_NAME = "已审核";

    int SIX_MONTH = 6;          //6个月之内数据
    int TEN_SIZE = 10;          //10条数据

    int RECEIPTED_NOT_STATUS = 1;           //未收货
    int RECEIPTED_SECTION_STATUS = 2;       //部分收货
    int RECEIPTED_ALL_STATUS = 3;           //全部收货

    String CLOSE_REMARK = "(系统自动终止该订单)";//终止订单备注

    //activiti
    String USER_NAME = "kermit";
    String PASS_WORD = "kermit";
}

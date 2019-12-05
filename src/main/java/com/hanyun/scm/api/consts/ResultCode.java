package com.hanyun.scm.api.consts;


import com.hanyun.ground.util.protocol.MessageId;
import com.hanyun.ground.util.protocol.Project;

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
 * ResultCode
 * Date: 2016/10/18
 * Time: 下午2:15
 *
 * @author tianye@hanyun.com
 */
public interface ResultCode {

    MessageId SUCCESS = MessageId.create(Project.SCM_API, 0, "成功!");

    MessageId SYSTEM_ERROR = MessageId.create(Project.SCM_API, 5000, "系统异常!");

    MessageId PARAM_ERROR = MessageId.create(Project.SCM_API, 4000, "参数错误!");

    MessageId DATA_NOT_FOUND = MessageId.create(Project.SCM_API, 4040, "该数据不存在!");

    MessageId ID_EXIST = MessageId.create(Project.SCM_API, 6000, "编号已存在!");

    MessageId CAN_NOT_DELETE = MessageId.create(Project.SCM_API, 6010, "该数据无法删除!");

    //基础商品
    MessageId GOODS_ODOO_SYSTEM_ERROR = MessageId.create(Project.SCM_API, 100, "基础商品--系统异常!");

    MessageId GOODS_ODOO_PARAM_ERROR = MessageId.create(Project.SCM_API, 101, "基础商品--参数错误!");

    MessageId GOODS_ODOO_DATA_NOT_FOUND = MessageId.create(Project.SCM_API, 102, "基础商品--数据不存在!");

    MessageId GOODS_ODOO_ID_EXIST = MessageId.create(Project.SCM_API, 103, "基础商品--ID已存在!");

    MessageId GOODS_ODOO_CAN_NOT_DELETE = MessageId.create(Project.SCM_API, 104, "基础商品--数据无法删除!");

    MessageId GOODS_ODOO_CREATE_ERROR = MessageId.create(Project.SCM_API, 105, "基础商品--创建失败!");

    MessageId GOODS_ODOO_QUERY_ERROR = MessageId.create(Project.SCM_API, 106, "基础商品--查询异常!");

    MessageId GOODS_ODOO_CREATE_ODOO_ERROR = MessageId.create(Project.SCM_API, 107, "基础商品--创建odoo商品失败!");

    MessageId GOODS_ODOO_COUNTALL_ERROR = MessageId.create(Project.SCM_API, 108, "基础商品--countAll失败!");

    MessageId GOODS_ODOO_MODIFY_ERROR = MessageId.create(Project.SCM_API, 109, "基础商品--修改失败!");

    MessageId GOODS_ODOO_DELETE_ERROR = MessageId.create(Project.SCM_API, 110, "基础商品--删除失败!");

    MessageId GOODS_ODOO_CREATE_PARAM_ERROR = MessageId.create(Project.SCM_API, 111, "添加基础商品--参数错误!");

    MessageId GOODS_ODOO_QUERY_PARAM_ERROR = MessageId.create(Project.SCM_API, 112, "查询基础商品--参数错误!");

    MessageId GOODS_ODOO_MODIFY_PARAM_ERROR = MessageId.create(Project.SCM_API, 113, "更新基础商品--参数错误!");

    //品牌管理
    MessageId BRAND_ODOO_SYSTEM_ERROR = MessageId.create(Project.SCM_API, 200, "品牌管理--系统异常!");

    MessageId BRAND_ODOO_PARAM_ERROR = MessageId.create(Project.SCM_API, 201, "品牌管理--参数错误!");

    MessageId BRAND_ODOO_DATA_NOT_FOUND = MessageId.create(Project.SCM_API, 202, "品牌管理--该数据不存在!");

    MessageId BRAND_ODOO_ID_EXIST = MessageId.create(Project.SCM_API, 203, "品牌管理--编号已存在!");

    MessageId BRAND_ODOO_CAN_NOT_DELETE = MessageId.create(Project.SCM_API, 204, "品牌管理--该数据无法删除!");

    MessageId BRAND_ODOO_CREATE_ERROR = MessageId.create(Project.SCM_API, 105, "品牌管理--创建失败!");

    MessageId BRAND_ODOO_QUERY_ERROR = MessageId.create(Project.SCM_API, 106, "品牌管理--查询异常!");

    MessageId BRAND_ODOO_CREATE_ODOO_ERROR = MessageId.create(Project.SCM_API, 107, "品牌管理--创建odoo公司失败!");

    MessageId BRAND_ODOO_COUNTALL_ERROR = MessageId.create(Project.SCM_API, 108, "品牌管理--countAll失败!");

    MessageId BRAND_ODOO_MODIFY_ERROR = MessageId.create(Project.SCM_API, 109, "品牌管理--修改失败!");

    MessageId BRAND_ODOO_DELETE_ERROR = MessageId.create(Project.SCM_API, 110, "品牌管理--删除失败!");

    MessageId BRAND_ODOO_CREATE_PARAM_ERROR = MessageId.create(Project.SCM_API, 111, "创建品牌供应链信息--参数错误!");

    MessageId BRAND_ODOO_QUERY_PARAM_ERROR = MessageId.create(Project.SCM_API, 112, "查询品牌供应链信息--参数错误!");

    MessageId BRAND_ODOO_MODIFY_PARAM_ERROR = MessageId.create(Project.SCM_API, 113, "更新品牌供应链信息--参数错误!");

    //采购退货单
    MessageId PURCHASE_RETURN_SYSTEM_ERROR = MessageId.create(Project.SCM_API, 300, "采购退货单--系统异常!");

    MessageId PURCHASE_RETURN_PARAM_ERROR = MessageId.create(Project.SCM_API, 301, "采购退货单--参数错误!");

    MessageId PURCHASE_RETURN_DATA_NOT_FOUND = MessageId.create(Project.SCM_API, 302, "采购退货单--该数据不存在!");

    MessageId PURCHASE_RETURN_ID_EXIST = MessageId.create(Project.SCM_API, 303, "采购退货单--编号已存在!");

    MessageId PURCHASE_RETURN_CAN_NOT_DELETE = MessageId.create(Project.SCM_API, 304, "采购退货单--该数据无法删除!");

    MessageId PURCHASE_RETURN_CREATE_ERROR = MessageId.create(Project.SCM_API, 305, "采购退货单--创建失败!");

    MessageId PURCHASE_RETURN_MODIFY_ERROR = MessageId.create(Project.SCM_API, 306, "采购退货单--修改失败!");

    MessageId PURCHASE_RETURN_DELETE_ERROR = MessageId.create(Project.SCM_API, 307, "采购退货单--删除失败!");

    MessageId PURCHASE_RETURN_EXPORT_ERROR = MessageId.create(Project.SCM_API, 308, "采购退货单--导出失败!");

    MessageId PURCHASE_RETURN_CREATE_PARAM_ERROR = MessageId.create(Project.SCM_API, 309, "创建采购退货单--参数错误!");

    MessageId PURCHASE_RETURN_MODIFY_PARAM_ERROR = MessageId.create(Project.SCM_API, 310, "修改采购退货单--参数错误!");

    MessageId PURCHASE_RETURN_QUERY_PARAM_ERROR = MessageId.create(Project.SCM_API, 311, "查询采购退货单--参数错误!");

    MessageId PURCHASE_RETURN_CONFIRM_ERROR = MessageId.create(Project.SCM_API, 312, "采购退货单--审核失败!");

    //采购入库(1300~1399)
    MessageId PURCHASE_STOCK_IN_SYSTEM_ERROR = MessageId.create(Project.SCM_API, 1300, "采购入库单--系统异常!");

    MessageId PURCHASE_STOCK_IN_PARAM_ERROR = MessageId.create(Project.SCM_API, 1301, "采购入库单--参数错误!");

    MessageId PURCHASE_STOCK_IN_DATA_NOT_FOUND = MessageId.create(Project.SCM_API, 1302, "采购入库单--该数据不存在!");

    MessageId PURCHASE_STOCK_IN_ID_EXIST = MessageId.create(Project.SCM_API, 1303, "采购入库单--编号已存在!");

    MessageId PURCHASE_STOCK_IN_CAN_NOT_DELETE = MessageId.create(Project.SCM_API, 1304, "采购入库单--该数据无法删除!");

    MessageId PURCHASE_STOCK_IN_CREATE_ERROR = MessageId.create(Project.SCM_API, 1305, "采购入库单--创建失败!");

    MessageId PURCHASE_STOCK_IN_MODIFY_ERROR = MessageId.create(Project.SCM_API, 1306, "采购入库单--修改失败!");

    MessageId PURCHASE_STOCK_IN_DELETE_ERROR = MessageId.create(Project.SCM_API, 1307, "采购入库单--删除失败!");

    MessageId PURCHASE_STOCK_IN_EXPORT_ERROR = MessageId.create(Project.SCM_API, 1308, "采购入库单--导出失败!");

    MessageId PURCHASE_STOCK_IN_CREATE_PARAM_ERROR = MessageId.create(Project.SCM_API, 1309, "创建采购入库单--参数错误!");

    MessageId PURCHASE_STOCK_IN_MODIFY_PARAM_ERROR = MessageId.create(Project.SCM_API, 1310, "修改采购入库单--参数错误!");

    MessageId PURCHASE_STOCK_IN_QUERY_PARAM_ERROR = MessageId.create(Project.SCM_API, 1311, "查询采购入库单--参数错误!");

    MessageId PURCHASE_STOCK_IN_CONFIRM_ERROR = MessageId.create(Project.SCM_API, 1312, "采购入库单--审核失败!");

    MessageId PURCHASE_STOCK_IN_CREATE_GOODS_ERROR = MessageId.create(Project.SCM_API, 1313, "采购入库单--商品详情不能为空且商品入库数量不能为空或为0!");

    MessageId PURCHASE_STOCK_IN_CREATE_GOODS_ID_ERROR = MessageId.create(Project.SCM_API, 1314, "采购入库单--商品详情ID不能为空");

    //库存盘点任务1400~1499
    MessageId STOCK_CHECK_TASK_SYSTEM_ERROR = MessageId.create(Project.SCM_API, 1400, "库存盘点任务--系统异常!");

    MessageId STOCK_CHECK_TASK_PARAM_ERROR = MessageId.create(Project.SCM_API, 1401, "库存盘点任务--参数错误!");

    MessageId STOCK_CHECK_TASK_DATA_NOT_FOUND = MessageId.create(Project.SCM_API, 1402, "库存盘点任务--该数据不存在!");

    MessageId STOCK_CHECK_TASK_ID_EXIST = MessageId.create(Project.SCM_API, 1403, "库存盘点任务--编号已存在!");

    MessageId STOCK_CHECK_TASK_CAN_NOT_DELETE = MessageId.create(Project.SCM_API, 1404, "库存盘点任务--该数据无法删除!");

    MessageId STOCK_CHECK_TASK_CREATE_ERROR = MessageId.create(Project.SCM_API, 1405, "库存盘点任务--创建盘点任务单失败!");

    MessageId STOCK_CHECK_TASK_MODIFY_ERROR = MessageId.create(Project.SCM_API, 1406, "库存盘点任务--跟新盘点任务单失败!");

    MessageId STOCK_CHECK_TASK_DELETE_ERROR = MessageId.create(Project.SCM_API, 1407, "库存盘点任务--删除失败!");

    MessageId STOCK_CHECK_TASK_CREATE_PARAM_ERROR = MessageId.create(Project.SCM_API, 1408, "创建库存盘点任务单--参数错误!");

    MessageId STOCK_CHECK_TASK_MODIFY_PARAM_ERROR = MessageId.create(Project.SCM_API, 1409, "修改库存盘点任务单--参数错误!");

    MessageId STOCK_CHECK_TASK_QUERY_PARAM_ERROR = MessageId.create(Project.SCM_API, 1410, "查询库存盘点任务单--参数错误!");

    MessageId STOCK_CHECK_TASK_END_PARAM_ERROR = MessageId.create(Project.SCM_API, 1411, "库存盘点任务单审核结束--参数错误!");

    MessageId STOCK_CHECK_TASK_END_ERROR = MessageId.create(Project.SCM_API, 1412, "盘点任务审核失败!");

    MessageId STOCK_NO_CHECK_TASK_END_ERROR = MessageId.create(Project.SCM_API, 1413, "没有盘点数据!");

    MessageId STOCK_CHECK_TASK_CONFIRM_ERROR = MessageId.create(Project.SCM_API, 1414, "盘点任务--审核失败!");

    MessageId STOCK_CHECK_TASK_NO_GOODS_ERROR = MessageId.create(Project.SCM_API, 1415, "仓库中暂无商品, 无法盘点!");

    MessageId STOCK_CHECK_ORDER_COMMIT_ERROR = MessageId.create(Project.SCM_API, 1500, "盘点单--提审失败!");

    MessageId STOCK_CHECK_ORDER_CONFIRM_ERROR = MessageId.create(Project.SCM_API, 1501, "盘点单--审核失败!");

    MessageId STOCK_CHECK_DIFFERENCE_COMMIT_ERROR = MessageId.create(Project.SCM_API, 1550, "盘点差异单--提审失败!");

    MessageId STOCK_CHECK_DIFFERENCE_CONFIRM_ERROR = MessageId.create(Project.SCM_API, 1551, "盘点差异单--审核失败!");

    MessageId STOCK_CHECK_DIFFERENCE_MODIFY_ERROR = MessageId.create(Project.SCM_API, 1552, "盘点差异单--修改失败!");

    //库存盘点
    MessageId STOCK_CHECK_SYSTEM_ERROR = MessageId.create(Project.SCM_API, 400, "库存盘点--系统异常!");

    MessageId STOCK_CHECK_PARAM_ERROR = MessageId.create(Project.SCM_API, 401, "库存盘点--参数错误!");

    MessageId STOCK_CHECK_DATA_NOT_FOUND = MessageId.create(Project.SCM_API, 402, "库存盘点--该数据不存在!");

    MessageId STOCK_CHECK_ID_EXIST = MessageId.create(Project.SCM_API, 403, "库存盘点--编号已存在!");

    MessageId STOCK_CHECK_CAN_NOT_DELETE = MessageId.create(Project.SCM_API, 404, "库存盘点--该数据无法删除!");

    MessageId STOCK_CHECK_CREATE_ERROR = MessageId.create(Project.SCM_API, 405, "库存盘点--创建盘点单失败!");

    MessageId STOCK_CHECK_MODIFY_ERROR = MessageId.create(Project.SCM_API, 406, "库存盘点--更新盘点单失败!");

    MessageId STOCK_CHECK_DELETE_ERROR = MessageId.create(Project.SCM_API, 407, "库存盘点--删除失败!");

    MessageId STOCK_CHECK_CREATE_PARAM_ERROR = MessageId.create(Project.SCM_API, 408, "创建库存盘点单--参数错误!");

    MessageId STOCK_CHECK_MODIFY_PARAM_ERROR = MessageId.create(Project.SCM_API, 409, "修改库存盘点单--参数错误!");

    MessageId STOCK_CHECK_QUERY_PARAM_ERROR = MessageId.create(Project.SCM_API, 410, "查询库存盘点单--参数错误!");

    MessageId STOCK_CHECK_ORDER_COMMITED = MessageId.create(Project.SCM_API, 411, "库存盘点单--单据已提审,不允许修改");

    //库存操作
    MessageId STOCK_QUANT_SYSTEM_ERROR = MessageId.create(Project.SCM_API, 500, "库存操作--系统异常!");

    MessageId STOCK_QUANT_PARAM_ERROR = MessageId.create(Project.SCM_API, 501, "库存操作--参数错误!");

    MessageId STOCK_QUANT_DATA_NOT_FOUND = MessageId.create(Project.SCM_API, 502, "库存操作--该数据不存在!");

    MessageId STOCK_QUANT_ID_EXIST = MessageId.create(Project.SCM_API, 503, "库存操作--编号已存在!");

    MessageId STOCK_QUANT_CAN_NOT_DELETE = MessageId.create(Project.SCM_API, 504, "库存操作--该数据无法删除!");

    MessageId STOCK_QUANT_SELL_ERROR = MessageId.create(Project.SCM_API, 505, "销售出库失败!");

    MessageId STOCK_QUANT_TOTAL_ERROR = MessageId.create(Project.SCM_API, 506, "查询总库存失败!");

    MessageId STOCK_QUANT_QUERY_STORE_GOODS_ERROR = MessageId.create(Project.SCM_API, 507, "查询门店商品库存失败!");

    MessageId STOCK_QUANT_CHANGE_QUERY_ERROR = MessageId.create(Project.SCM_API, 508, "查询库存变更记录失败!");

    //仓库操作
    MessageId WAREHOUSE_SYSTEM_ERROR = MessageId.create(Project.SCM_API, 600, "仓库操作--系统异常!");

    MessageId WAREHOUSE_PARAM_ERROR = MessageId.create(Project.SCM_API, 601, "仓库操作--参数错误!");

    MessageId WAREHOUSE_DATA_NOT_FOUND = MessageId.create(Project.SCM_API, 602, "仓库操作--该数据不存在!");

    MessageId WAREHOUSE_ID_EXIST = MessageId.create(Project.SCM_API, 603, "仓库操作--编号已存在!");

    MessageId WAREHOUSE_CAN_NOT_DELETE = MessageId.create(Project.SCM_API, 604, "仓库操作--该数据无法删除!");

    MessageId WAREHOUSE_CREATE_ERROR = MessageId.create(Project.SCM_API, 605, "仓库操作--创建失败!");

    MessageId WAREHOUSE_MODIFY_ERROR = MessageId.create(Project.SCM_API, 606, "仓库操作--修改失败!");

    MessageId WAREHOUSE_DELETE_ERROR = MessageId.create(Project.SCM_API, 607, "仓库操作--删除失败!");

    //库存管理 （入库单，出库单，调拨单）800~830
    MessageId STOCKPICKING_IN_UPDATE_ERROR = MessageId.create(Project.SCM_API, 800, "入库操作--修改状态失败");

    MessageId STOCKPICKING_OUT_UPDATE_ERROR = MessageId.create(Project.SCM_API, 801, "出库操作--修改状态失败");

    MessageId STOCKPICKING_CHANGE_UPDATE_ERROR = MessageId.create(Project.SCM_API, 802, "调拨操作--修改状态失败");

    MessageId STOCKPICKING_UPDATE_ERROR = MessageId.create(Project.SCM_API, 802, "库存管理--修改状态失败");

    MessageId STOCKPICKING_IN_QUERY_ERROR = MessageId.create(Project.SCM_API, 803, "入库操作--查询入库失败");

    MessageId STOCKPICKING_OUT_QUERY_ERROR = MessageId.create(Project.SCM_API, 804, "出库操作--查询出库失败");

    MessageId STOCKPICKING_CHANGE_QUERY_ERROR = MessageId.create(Project.SCM_API, 805, "调拨操作--查询调拨失败");

    MessageId STOCKPICKING_QUERY_ERROR = MessageId.create(Project.SCM_API, 806, "库存管理--查询数据失败");

    MessageId STOCKPICKINGGOODS_QUERY_ERROR = MessageId.create(Project.SCM_API, 807, "库存管理--查询详单失败");

    MessageId STOCKPICKINGGOODS_INSERT_ERROR = MessageId.create(Project.SCM_API, 808, "库存管理--插入商品详单失败");

    MessageId STOCKPICKINGGOODS_UPDATE_ERROR = MessageId.create(Project.SCM_API, 809, "库存管理--更新商品详单失败");

    MessageId STOCKPICKINGGOODS_DELETE_ERROR = MessageId.create(Project.SCM_API, 810, "库存管理--删除商品详单失败");

    MessageId STOCKPICKINGGOODS_CREAT_ERROR = MessageId.create(Project.SCM_API, 811, "库存管理--创建商品详单失败");

    MessageId STOCKPICKINGGOODS_DATA_NOT_FOUND = MessageId.create(Project.SCM_API, 812, "库存管理--查询商品详单失败");

    MessageId STOCKPICKING_INSERT_ERROR = MessageId.create(Project.SCM_API, 813, "库存管理--插入商品单据失败");

    MessageId STOCKPICKINGGOODS_DELETE_FOUND = MessageId.create(Project.SCM_API, 814, "库存管理--删除商品详单失败");

    MessageId STOCKPICKING_CREAT_ERROR = MessageId.create(Project.SCM_API, 815, "库存管理--创建单据失败");

    MessageId STOCKPICKING_MAKE_ORDERID_ERROR = MessageId.create(Project.SCM_API, 816, "库存管理--创建单据号失败");

    MessageId STOCKQUANT_QUERY_BRANDNAME_ERROR = MessageId.create(Project.SCM_API, 817, "库存管理--查询商品品牌失败");
    /**
     * WANG 680-690
     */
    MessageId SAVE_OPERATION_FAILED = MessageId.create(Project.SCM_API, 680, "添加操作失败!");

    MessageId UPDATE_OPERATION_FAILED = MessageId.create(Project.SCM_API, 681, "更新操作失败!");

    MessageId DELETE_OPERATION_FAILED = MessageId.create(Project.SCM_API, 682, "删除操作失败!");

    MessageId PLAN_NOT_SUPPLIER = MessageId.create(Project.SCM_API, 683, "有未选择供应商的商品!");

    MessageId ORDER_DETAIL_NOT_DELETE = MessageId.create(Project.SCM_API, 684, "删除订单详情失败!");

    MessageId ORDER_NOT_DELETE = MessageId.create(Project.SCM_API, 685, "删除订单失败!");

    MessageId ORDER_AUDITOR_FAILED = MessageId.create(Project.SCM_API, 686, "审核失败!");


    //供应商
    MessageId SUPPLIER_CREATE_ERROR = MessageId.create(Project.SCM_API, 1008, "供应商--创建供应商失败!");

    MessageId SUPPLIER_QUERY_ERROR = MessageId.create(Project.SCM_API, 1009, "供应商--查询供应商列表失败!");

    MessageId SUPPLIER_UPDATE_ERROR = MessageId.create(Project.SCM_API, 1010, "供应商--更新该供应商失败!");

    MessageId SUPPLIER_DELETE_ERROR = MessageId.create(Project.SCM_API, 1011, "供应商--删除供应商失败!");

    MessageId SUPPLIER_QUERYID_ERROR = MessageId.create(Project.SCM_API, 1012, "供应商--查询该供应商详情失败!");

    MessageId SUPPLIER_QUERYODOOID_ERROR = MessageId.create(Project.SCM_API, 1013, "供应商--查询ODOO供应商ID失败!");

    MessageId SUPPLIER_ID_CREATE = MessageId.create(Project.SCM_API, 1014, "供应商--创建供应商编号ID失败");

    MessageId SUPPLIER_NUMBER_QUERY = MessageId.create(Project.SCM_API, 1015, "供应商--查询供应商数量失败");

    MessageId SUPPLIER_MODIFY_PARAM_ERROR = MessageId.create(Project.SCM_API, 1016, "修改供应商状态--参数错误");

    MessageId SUPPLIER_MODIFY_AVAILABLE_STATUS_ERROR = MessageId.create(Project.SCM_API, 1017, "修改供应商状态--更新失败");

    MessageId SUPPLIER_GOODS_DELETE_ERROR = MessageId.create(Project.SCM_API, 1018, "供应商商品--删除失败");

    MessageId SUPPLIER_GOODS_MOIDFY_STATUS_ERROR = MessageId.create(Project.SCM_API, 1019, "供应商商品--修改状态失败");

    MessageId SUPPLIER_GOODS_UPDATE_ERROR = MessageId.create(Project.SCM_API, 1020, "供应商商品--修改数据失败");


    MessageId SUPPLIER_QUERYODOOBRAND_ERROR = MessageId.create(Project.SCM_API, 1022, "供应商--查询ODOO品牌失败!");

    //收货单
    MessageId RECEIPT_QUERY_ERROR = MessageId.create(Project.SCM_API, 1023, "收货单--查询收货单列表失败!");

    MessageId RECEIPT_UPDATE_ERROR = MessageId.create(Project.SCM_API, 1024, "收货单--更新收货单状态失败!");

    //收货单商品
    MessageId RECEIPTGOODS_QUERY_ERROR = MessageId.create(Project.SCM_API, 1025, "收货单商品--查询收货单商品列表失败!");

    //库存信息
    MessageId STOCKQUNAT_QUERY_ERROR = MessageId.create(Project.SCM_API, 1026, "库存信息--查询库存信息列表失败!");

    //采购入库
    MessageId STORAGE_QUERY_ERROR = MessageId.create(Project.SCM_API, 1027, "采购入库--查询采购入库失败!");

    MessageId STORAGE_UPD_ERROR = MessageId.create(Project.SCM_API, 1028, "采购入库--更新采购入库失败!");

    MessageId STORAGE_ADD_ERROR = MessageId.create(Project.SCM_API, 1029, "采购入库--插入采购入库失败!");

    MessageId STORAGE_DEL_ERROR = MessageId.create(Project.SCM_API, 1030, "采购入库--删除采购入库失败!");

    //收货单确认
    MessageId RECEIPTGOODS_QUERYWAREHOUSEID_ERROR = MessageId.create(Project.SCM_API, 1031, "收货单确认--未选择入库仓库!");

    //供应商名字查询
    MessageId SUPPLIERNAME_QUERY_ERROR = MessageId.create(Project.SCM_API, 1032, "供应商--已有该供应商名,请重新输入!");

    //仓库名字查询
    MessageId WAREHOUSENAME_QUERY_ERROR = MessageId.create(Project.SCM_API, 1033, "仓库管理--已有该仓库名,请重新输入!");

    //库存预警设置
    MessageId STOCKQUANTSETUP_DOUBLEUPD_ERROR = MessageId.create(Project.SCM_API, 1034, "库存预警设置--多库存修改失败");

    MessageId STOCKQUANTSETUP_SINGLEUPD_ERROR = MessageId.create(Project.SCM_API, 1035, "库存预警设置--单库存修改失败!");

    //供应商类型名称
    MessageId SUPPLIERTYPENAME_QUERY_ERROR = MessageId.create(Project.SCM_API, 1036, "供应商类型--已有该供应商类型名,请重新输入!");


    //库存盘点详情
    MessageId STOCK_CHECK_DETAIL_SYSTEM_ERROR = MessageId.create(Project.SCM_API, 1037, "库存盘点详情--系统异常!");

    MessageId STOCK_CHECK_DETAIL_PARAM_ERROR = MessageId.create(Project.SCM_API, 1038, "库存盘点详情--参数错误!");

    MessageId STOCK_CHECK_DETAIL_DATA_NOT_FOUND = MessageId.create(Project.SCM_API, 1039, "库存盘点详情--该数据不存在!");

    MessageId STOCK_CHECK_DETAIL_ID_EXIST = MessageId.create(Project.SCM_API, 1040, "库存盘点详情--编号已存在!");

    MessageId STOCK_CHECK_DETAIL_CAN_NOT_DELETE = MessageId.create(Project.SCM_API, 1041, "库存盘点详情--该数据无法删除!");

    MessageId STOCK_CHECK_DETAIL_CREATE_ERROR = MessageId.create(Project.SCM_API, 1042, "库存盘点详情--创建盘点详情失败!");

    MessageId STOCK_CHECK_DETAIL_MODIFY_ERROR = MessageId.create(Project.SCM_API, 1043, "库存盘点详情--更新盘点详情失败!");

    MessageId STOCK_CHECK_DETAIL_DELETE_ERROR = MessageId.create(Project.SCM_API, 1044, "库存盘点详情--删除失败!");

    //供应商有关联商品
    MessageId SUPPLIER_PRODUCT_RELATION_ERROR = MessageId.create(Project.SCM_API, 1045, "供应商--有关联商品,将关联商品删除后才能删除供应商!");

    MessageId SUPPLIER_DATA_NOT_FOUND = MessageId.create(Project.SCM_API, 1046, "供应商--该数据不存在!");

    //拆装单
    MessageId DISASSEMBLYORDER_SYSTEM_ERROR = MessageId.create(Project.SCM_API, 1047, "拆装单--系统异常!");

    MessageId DISASSEMBLYORDER_PARAM_ERROR = MessageId.create(Project.SCM_API, 1048, "拆装单--参数错误!");

    MessageId DISASSEMBLYORDER_DATA_NOT_FOUND = MessageId.create(Project.SCM_API, 1049, "拆装单--该数据不存在!");

    MessageId DISASSEMBLYORDER_ID_EXIST = MessageId.create(Project.SCM_API, 1050, "拆装单--编号已存在!");

    MessageId DISASSEMBLYORDER_CAN_NOT_DELETE = MessageId.create(Project.SCM_API, 1051, "拆装单--该数据无法删除!");

    MessageId DISASSEMBLYORDER_CREATE_ERROR = MessageId.create(Project.SCM_API, 1052, "拆装单--创建拆装单失败!");

    MessageId DISASSEMBLYORDER_QUERY_ERROR = MessageId.create(Project.SCM_API, 1053, "拆装单--查询拆装单失败!");

    MessageId DISASSEMBLYORDER_MODIFY_ERROR = MessageId.create(Project.SCM_API, 1054, "拆装单--更新拆装单失败!");

    MessageId DISASSEMBLYORDER_DELETE_ERROR = MessageId.create(Project.SCM_API, 1055, "拆装单--删除失败!");

    MessageId DISASSEMBLYORDER_CREATE_PARAM_ERROR = MessageId.create(Project.SCM_API, 1056, "创建拆装单--参数错误!");

    MessageId DISASSEMBLYORDER_MODIFY_PARAM_ERROR = MessageId.create(Project.SCM_API, 1057, "修改拆装单--参数错误!");

    MessageId DISASSEMBLYORDER_QUERY_PARAM_ERROR = MessageId.create(Project.SCM_API, 1058, "查询拆装单--参数错误!");

    MessageId DISASSEMBLYORDER_QUERY_DETAIL_ERROR = MessageId.create(Project.SCM_API, 1059, "查询拆装单--未查询到详单对象!");

    MessageId DISASSEMBLYORDER_CREATE_DETAIL_ERROR = MessageId.create(Project.SCM_API, 1060, "拆装详单--插入失败");

    MessageId DISASSEMBLYORDER_UPDATE_DETAIL_ERROR = MessageId.create(Project.SCM_API, 1061, "拆装详单--修改失败");

    MessageId DISASSEMBLYORDER_DELETE_DETAIL_ERROR = MessageId.create(Project.SCM_API, 1062, "拆装详单--删除失败");

    //配送单
    MessageId DISTRIBUTIONORDER_SYSTEM_ERROR = MessageId.create(Project.SCM_API, 1063, "配送单--系统异常!");

    MessageId DISTRIBUTIONORDER_PARAM_ERROR = MessageId.create(Project.SCM_API, 1064, "配送单--参数错误!");

    MessageId DISTRIBUTIONORDER_DATA_NOT_FOUND = MessageId.create(Project.SCM_API, 1065, "配送单--该数据不存在!");

    MessageId DISTRIBUTIONORDER_ID_EXIST = MessageId.create(Project.SCM_API, 1066, "配送单--编号已存在!");

    MessageId DISTRIBUTIONORDER_CAN_NOT_DELETE = MessageId.create(Project.SCM_API, 1067, "配送单--该数据无法删除!");

    MessageId DISTRIBUTIONORDER_CREATE_ERROR = MessageId.create(Project.SCM_API, 1068, "配送单--创建配送单失败!");

    MessageId DISTRIBUTIONORDER_QUERY_ERROR = MessageId.create(Project.SCM_API, 1069, "配送单--查询配送单失败!");

    MessageId DISTRIBUTIONORDER_MODIFY_ERROR = MessageId.create(Project.SCM_API, 1070, "配送单--更新配送单失败!");

    MessageId DISTRIBUTIONORDER_DELETE_ERROR = MessageId.create(Project.SCM_API, 1071, "配送单--删除失败!");

    MessageId DISTRIBUTIONORDER_CREATE_PARAM_ERROR = MessageId.create(Project.SCM_API, 1072, "创建配送单--参数错误!");

    MessageId DISTRIBUTIONORDER_MODIFY_PARAM_ERROR = MessageId.create(Project.SCM_API, 1073, "修改配送单--参数错误!");

    MessageId DISTRIBUTIONORDER_QUERY_PARAM_ERROR = MessageId.create(Project.SCM_API, 1074, "查询配送单--参数错误!");

    //物流送货单
    MessageId LOGISTICSDELIVERY_SYSTEM_ERROR = MessageId.create(Project.SCM_API, 1075, "物流送货单--系统异常!");

    MessageId LOGISTICSDELIVERY_PARAM_ERROR = MessageId.create(Project.SCM_API, 1076, "物流送货单--参数错误!");

    MessageId LOGISTICSDELIVERY_DATA_NOT_FOUND = MessageId.create(Project.SCM_API, 1077, "物流送货单--该数据不存在!");

    MessageId LOGISTICSDELIVERY_ID_EXIST = MessageId.create(Project.SCM_API, 1078, "物流送货单--编号已存在!");

    MessageId LOGISTICSDELIVERY_CAN_NOT_DELETE = MessageId.create(Project.SCM_API, 1079, "物流送货单--该数据无法删除!");

    MessageId LOGISTICSDELIVERY_CREATE_ERROR = MessageId.create(Project.SCM_API, 1080, "物流送货单--创建物流送货单失败!");

    MessageId LOGISTICSDELIVERY_QUERY_ERROR = MessageId.create(Project.SCM_API, 1081, "物流送货单--查询物流送货单失败!");

    MessageId LOGISTICSDELIVERY_MODIFY_ERROR = MessageId.create(Project.SCM_API, 1082, "物流送货单--更新物流送货单失败!");

    MessageId LOGISTICSDELIVERY_DELETE_ERROR = MessageId.create(Project.SCM_API, 1083, "物流送货单--删除单据和详情失败!");

    MessageId LOGISTICSDELIVERY_CREATE_PARAM_ERROR = MessageId.create(Project.SCM_API, 1084, "创建物流送货单--参数错误!");

    MessageId LOGISTICSDELIVERY_MODIFY_PARAM_ERROR = MessageId.create(Project.SCM_API, 1085, "修改物流送货单--参数错误!");

    MessageId LOGISTICSDELIVERY_QUERY_PARAM_ERROR = MessageId.create(Project.SCM_API, 1086, "查询物流送货单--参数错误!");

    MessageId LOGISTICSDELIVERY_QUERY_DETAIL_ERROR = MessageId.create(Project.SCM_API, 1087, "物流送货单--查询物流送货单单据信息和详情失败!");

    MessageId LOGISTICSDELIVERY_ORDER_DELETE_ERROR = MessageId.create(Project.SCM_API, 1088, "物流送货单--删除单据失败!");

    MessageId LOGISTICSDELIVERY_DETAIL_DELETE_ERROR = MessageId.create(Project.SCM_API, 1089, "物流送货单--删除详情失败!");

    MessageId LOGISTICSDELIVERY_DETAIL_CREATE_ERROR = MessageId.create(Project.SCM_API, 1090, "物流送货单--插入详情失败!");

    MessageId DELIVERYANDDISTRIBUTION_CENTER_CREATE_ERROR = MessageId.create(Project.SCM_API, 1091, "物流送货单--插入中间表失败!");

    MessageId DELIVERYANDDISTRIBUTION_CENTER_DELETE_ERROR = MessageId.create(Project.SCM_API, 1091, "物流送货单--删除中间表失败!");

    MessageId DISTRIBUTION_UPDATE_STATUS_ERROR = MessageId.create(Project.SCM_API, 1092, "配送单--更新收货状态失败!");

    MessageId DISTRIBUTIONSTORE_DELETE_STATUS_ERROR = MessageId.create(Project.SCM_API, 1092, "配送单--删除中间表失败!");

    MessageId STOCKQUANT_MODIFY_ERROR = MessageId.create(Project.SCM_API, 1092, "库存--更新失败!");

    MessageId SUPPLIER_PURCHASEORDER_RELATION_ERROR = MessageId.create(Project.SCM_API, 1093, "供应商--采购订单关联中,无法删除");

    MessageId SUPPLIER_PURCHASERETURN_RELATION_ERROR = MessageId.create(Project.SCM_API, 1094, "供应商--采购退货关联中,无法删除");

    MessageId SUPPLIER_SUPPLIER_RELATION_STATUS_ERROR = MessageId.create(Project.SCM_API, 1095, "供应商状态--商品关联中,无法禁用");

    MessageId SUPPLIER_PURCHASEORDER_RELATION_STATUS_ERROR = MessageId.create(Project.SCM_API, 1096, "供应商状态--采购订单关联中,无法禁用");

    MessageId SUPPLIER_PURCHASERETURN_RELATION_STATUS_ERROR = MessageId.create(Project.SCM_API, 1097, "供应商状态--采购退货关联中,无法禁用");

    MessageId DISTRIBUTION_ORDER_COMMITED = MessageId.create(Project.SCM_API, 1098, "配送单--单据已提审,不允许修改");
    /* end */

    MessageId PURCHASE_APPLY_QUERY_ERROR = MessageId.create(Project.SCM_API, 3000, "采购申请单--查询采购申请单失败!");

    MessageId PURCHASE_APPLY_UPD_ERROR = MessageId.create(Project.SCM_API, 3001, "采购申请单--更新采购申请单失败!");

    MessageId PURCHASE_APPLY_ADD_ERROR = MessageId.create(Project.SCM_API, 3002, "采购申请单--插入采购申请单失败!");

    MessageId PURCHASE_APPLY_CONFIRM_ERROR = MessageId.create(Project.SCM_API, 3100, "采购申请单--确认采购申请单失败!");

    MessageId PURCHASE_APPLY_GOODS_EMPTY_ERROR = MessageId.create(Project.SCM_API, 3101, "采购申请单--无商品信息!");

    MessageId PURCHASE_APPLY_COMMIT_ERROR = MessageId.create(Project.SCM_API, 3102, "采购申请单--提审失败!");

    MessageId PURCHASE_ORDER_QUERY_ERROR = MessageId.create(Project.SCM_API, 3003, "采购订单--查询采购订单失败!");

    MessageId PURCHASE_ORDER_UPD_ERROR = MessageId.create(Project.SCM_API, 3004, "采购订单--更新采购订单失败!");

    MessageId PURCHASE_ORDER_ADD_ERROR = MessageId.create(Project.SCM_API, 3005, "采购订单--插入采购订单失败!");

    MessageId PURCHASE_ORDER_DEL_ERROR = MessageId.create(Project.SCM_API, 3006, "采购订单--删除采购订单失败!");

    MessageId PURCHASE_ORDER_CONFIRM_ERROR = MessageId.create(Project.SCM_API, 3007, "采购订单--审核失败!");

    MessageId ORDERid_IS_NULL = MessageId.create(Project.SCM_API, 3008, "订单id为空!");

    MessageId PURCHASE_ORDER_GOODS_EMPTY_ERROR = MessageId.create(Project.SCM_API, 3009, "采购订单--无商品信息!");

    MessageId PURCHASE_ORDER_COMMIT_ERROR = MessageId.create(Project.SCM_API, 3010, "采购订单--提交审核失败!");

    MessageId PURCHASE_ORDER_CANCEL_ERROR = MessageId.create(Project.SCM_API, 3011, "采购订单--终止订单失败!");


    /**
     * 采购申请单5000~5099
     */
    MessageId PURCHASE_APPLY_DATA_NOT_FOUND = MessageId.create(Project.SCM_API, 5000, "删除采购申请单--数据不存在!");

    MessageId PURCHASE_APPLY_DELETE_ERROR = MessageId.create(Project.SCM_API, 5001, "删除采购申请单--删除失败!");

    MessageId PURCHASE_APPLY_DETAIL_ERROR = MessageId.create(Project.SCM_API, 5002, "查询采购申请单详情失败!");

    /**
     * 采购计划单5100~5199
     */
    MessageId PURCHASE_PLAN_QUERY_ERROR = MessageId.create(Project.SCM_API, 3000, "采购假话单--查询采购计划单失败!");

    MessageId PURCHASE_PLAN_UPD_ERROR = MessageId.create(Project.SCM_API, 3001, "采购计划单--更新采购计划单失败!");

    MessageId PURCHASE_PLAN_ADD_ERROR = MessageId.create(Project.SCM_API, 3002, "采购计划单--插入采购计划单失败!");

    MessageId PURCHASE_PLAN_CONFIRM_ERROR = MessageId.create(Project.SCM_API, 3100, "采购计划单--确认采购计划单失败!");

    MessageId PURCHASE_PLAN_DATA_NOT_FOUND = MessageId.create(Project.SCM_API, 5000, "删除采购计划单--数据不存在!");

    MessageId PURCHASE_PLAN_DELETE_ERROR = MessageId.create(Project.SCM_API, 5001, "删除采购计划单--删除失败!");

    MessageId PURCHASE_PLAN_DETAIL_ERROR = MessageId.create(Project.SCM_API, 5002, "查询采购计划单详情失败!");

    MessageId PURCHASE_PLAN_COMMIT_ERROR = MessageId.create(Project.SCM_API, 5003, "采购计划单--审核失败!");

    //盘点差异单 871~880
    MessageId STOCK_DIFFERENCE_UPDATE_ERROR = MessageId.create(Project.SCM_API, 871, "盘点差异单--修改失败!");

    MessageId STOCK_DIFFERENCE_QUERY_ERROR = MessageId.create(Project.SCM_API, 872, "盘点差异单--查询失败!");

    MessageId STOCK_DIFFERENCE_CREATE_ERROR = MessageId.create(Project.SCM_API, 873, "盘点差异单--创建失败!");

    MessageId STOCK_DIFFERENCE_DETAIL_CREATE_ERROR = MessageId.create(Project.SCM_API, 874, "盘点差异单--创建商品详单失败!");
    //报损报溢单 881~890
    MessageId SPILL_LOSS_UPDATE_ERROR = MessageId.create(Project.SCM_API, 881, "报损报溢单--修改失败!");

    MessageId SPILL_LOSS_QUERY_ERROR = MessageId.create(Project.SCM_API, 882, "报损报溢单--查询失败!");

    MessageId SPILL_LOSS_NO_DATA = MessageId.create(Project.SCM_API, 883, "报损报溢单--无数据!");

    MessageId SPILL_LOSS_DELETE_ERROR = MessageId.create(Project.SCM_API, 884, "报损报溢单--删除失败!");

    MessageId SPILL_LOSS_INSERT_ERROR = MessageId.create(Project.SCM_API, 885, "报损报溢单--添加失败!");

    MessageId SPILL_LOSS_DETAIL_UPDATE_ERROR = MessageId.create(Project.SCM_API, 886, "报损报溢单--详单修改失败!");

    MessageId SPILL_LOSS_DETAIL_QUERY_ERROR = MessageId.create(Project.SCM_API, 887, "报损报溢单--详单查询失败!");

    MessageId SPILL_LOSS_DETAIL_DELETE_ERROR = MessageId.create(Project.SCM_API, 888, "报损报溢单--详单删除失败!");

    MessageId SPILL_LOSS_DETAIL_INSERT_ERROR = MessageId.create(Project.SCM_API, 889, "报损报溢单--详单添加失败!");

    MessageId SPILL_LOSS_COMMIT_ERROR = MessageId.create(Project.SCM_API, 890, "报损报溢单--提审失败!");

    MessageId SPILL_LOSS_CONFIRM_ERROR = MessageId.create(Project.SCM_API, 891, "报损报溢单--审核失败!");

    MessageId STATISTICS_SYSTEM_ERROR = MessageId.create(Project.SCM_API, 901, "供应链统计--获取统计信息失败!");

    MessageId STATISTICS_PARAM_ERROR = MessageId.create(Project.SCM_API, 902, "供应链统计--参数错误!");
    //补货申请单 690-700
    MessageId REPLENISHMENTAPPLY_QUERY_ERROR = MessageId.create(Project.SCM_API, 690, "补货申请单--查询失败!");

    MessageId REPLENISHMENTAPPLY_UPDATE_ERROR = MessageId.create(Project.SCM_API, 691, "补货申请单--修改失败!");

    MessageId REPLENISHMENTAPPLY_INSERT_ERROR = MessageId.create(Project.SCM_API, 692, "补货申请单--新增失败!");

    MessageId REPLENISHMENTAPPLY_DELETE_ERROR = MessageId.create(Project.SCM_API, 693, "补货申请单--删除失败!");

    MessageId REPLENISHMENTAPPLY_DETAIL_QUERY_ERROR = MessageId.create(Project.SCM_API, 694, "补货申请单详单--查询失败!");

    MessageId REPLENISHMENTAPPLY_DETAIL_UPDATE_ERROR = MessageId.create(Project.SCM_API, 695, "补货申请单详单--修改失败!");

    MessageId REPLENISHMENTAPPLY_DETAIL_INSERT_ERROR = MessageId.create(Project.SCM_API, 696, "补货申请单详单--新增失败!");

    MessageId REPLENISHMENTAPPLY_DETAIL_DELETE_ERROR = MessageId.create(Project.SCM_API, 697, "补货申请单详单--删除失败!");



    MessageId REPLENISHMENT_APPLY_DETAIL_FOR_DISTRIBUTION_ERROR = MessageId.create(Project.SCM_API, 698, "配送汇总失败!");

    MessageId REPLENISHMENT_APPLY_QUERY_STORE_ERROR = MessageId.create(Project.SCM_API, 699, "查询补货门店失败!");

    MessageId REPLENISHMENT_APPLY_DETAIL_NO_GOODS = MessageId.create(Project.SCM_API, 700, "无商品信息!");

    MessageId REPLENISHMENT_APPLY_CLOSE_STATUS_ERROR = MessageId.create(Project.SCM_API, 701, "关闭申请单状态失败!");

    MessageId REPLENISHMENT_APPLY_RECORD_NOT_FOUND = MessageId.create(Project.SCM_API, 702, "申请单记录不存在");

    MessageId PAYMENT_PARAM_ERROR = MessageId.create(Project.SCM_API, 900, "采购收付款--参数错误!");

    MessageId PAYMENT_PARAM_SYSTEM_ERROR = MessageId.create(Project.SCM_API, 901, "采购收付款--系统异常!");

    MessageId PAYMENT_QUERY_ORDER_ERROR = MessageId.create(Project.SCM_API, 902, "采购收付款--查询订单失败!");

    MessageId PAYMENT_PAY_ERROR = MessageId.create(Project.SCM_API, 903, "采购收付款--更新付款金额失败!");

    MessageId PAYMENT_STATICTICS_ERROR = MessageId.create(Project.SCM_API, 904, "采购收付款--统计失败!");

    MessageId AGGREGATE_DISTRIBUTION_PARAM_ERROR = MessageId.create(Project.SCM_API, 1100, "配送汇总--参数错误!");

    MessageId AGGREGATE_DISTRIBUTION_AGGREGATE_ERROR = MessageId.create(Project.SCM_API, 1101, "您所选的源单商品配送数均为0,未生成配送单!");

    MessageId AGGREGATE_DISTRIBUTION_STATISTICS_ERROR = MessageId.create(Project.SCM_API, 1102, "配送汇总--汇总配送统计失败!");

    MessageId AGGREGATE_TOTALNUM_ERROR = MessageId.create(Project.SCM_API, 1103, "您所选的源单商品配送总数为0,未生成配送单!");

    MessageId REPLENISHMENT_ORDER_CONFIRM_ERROR = MessageId.create(Project.SCM_API, 1104, "补货申请单--审核失败!");

    MessageId REPLENISHMENT_ORDER_COMMIT_ERROR = MessageId.create(Project.SCM_API, 1105, "补货申请单--提交审核失败!");

    MessageId DISTRIBUTION_ORDER_CONFIRM_ERROR = MessageId.create(Project.SCM_API, 1106, "配送单--审核失败!");

    MessageId DISTRIBUTION_ORDER_COMMIT_ERROR = MessageId.create(Project.SCM_API, 1107, "配送单--提交审核失败!");

    MessageId DELIVERY_ORDER_CONFIRM_ERROR = MessageId.create(Project.SCM_API, 1108, "发货单--审核失败!");

    MessageId DELIVERY_ORDER_COMMIT_ERROR = MessageId.create(Project.SCM_API, 1109, "发货单--提交审核失败!");

    MessageId INSPECTIONPICKING_ORDER_CONFIRM_ERROR = MessageId.create(Project.SCM_API, 1110, "入库单--审核失败!");

    MessageId INSPECTIONPICKING_ORDER_COMMIT_ERROR = MessageId.create(Project.SCM_API, 1111, "入库单--提交审核失败!");

    MessageId DISASSEMBLY_ORDER_CONFIRM_ERROR = MessageId.create(Project.SCM_API, 1112, "拆装单--审核失败!");

    MessageId DISASSEMBLY_ORDER_COMMIT_ERROR = MessageId.create(Project.SCM_API, 1113, "拆装单--提交审核失败!");

    //验货入库单 701~710
    MessageId INSPECTION_QUERY_ERROR = MessageId.create(Project.SCM_API, 701, "验货入库单--查询失败!");

    MessageId INSPECTION_UPDATE_ERROR = MessageId.create(Project.SCM_API, 702, "验货入库单--修改失败!");

    MessageId INSPECTION_DELETE_ERROR = MessageId.create(Project.SCM_API, 703, "验货入库单--删除失败!");

    MessageId INSPECTION_INSERT_ERROR = MessageId.create(Project.SCM_API, 704, "验货入库单--添加失败!");

    MessageId INSPECTION_DETAIL_QUERY_ERROR = MessageId.create(Project.SCM_API, 705, "验货入库单详单--查询失败!");

    MessageId INSPECTION_DETAIL_UPDATE_ERROR = MessageId.create(Project.SCM_API, 706, "验货入库单详单--修改失败!");

    MessageId INSPECTION_DETAIL_DELETE_ERROR = MessageId.create(Project.SCM_API, 707, "验货入库单详单--删除失败!");

    MessageId INSPECTION_DETAIL_INSERT_ERROR = MessageId.create(Project.SCM_API, 708, "验货入库单详单--添加失败!");

    MessageId INSPECTION_DETAIL_INSERT_GOODS_ERROR = MessageId.create(Project.SCM_API, 709, "验货入库单详单--商品详情不能为空且商品入库数量不能为空或为0!!");

    MessageId INSPECTION_DETAIL_INSERT_GOODS_ID_ERROR = MessageId.create(Project.SCM_API, 710, "验货入库单详单--商品详情ID不能为空");

    MessageId PROCESS_DEFINITION_PARAM_ERROR = MessageId.create(Project.SCM_API, 1200, "审批流程定义--参数错误!");

    MessageId PROCESS_DEFINITION_CREATE_ERROR = MessageId.create(Project.SCM_API, 1201, "审批流程定义--创建流程失败!");

    MessageId PROCESS_DEFINITION_UPDATE_ERROR = MessageId.create(Project.SCM_API, 1202, "审批流程定义--修改失败!");

    MessageId PROCESS_DEFINITION_DELETE_ERROR = MessageId.create(Project.SCM_API, 1203, "审批流程定义--删除失败!");

    MessageId PROCESS_DEFINITION_DETAIL_ERROR = MessageId.create(Project.SCM_API, 1204, "审批流程定义--查询详情失败!");

    MessageId PROCESS_DEFINITION_QUERY_ERROR = MessageId.create(Project.SCM_API, 1205, "审批流程定义--条件查询失败");

    MessageId PROCESS_DEFINITION_DEPLOY_ERROR = MessageId.create(Project.SCM_API, 1206, "审批流程定义--部署失败!");

    MessageId PROCESS_INSTANCE_PARAM_ERROR = MessageId.create(Project.SCM_API, 1210, "审批流程实例--参数错误!");

    MessageId PROCESS_INSTANCE_CREATE_ERROR = MessageId.create(Project.SCM_API, 1211, "审批流程实例--创建流程失败!");

    MessageId PROCESS_INSTANCE_UPDATE_ERROR = MessageId.create(Project.SCM_API, 1212, "审批流程实例--修改失败!");

    MessageId PROCESS_INSTANCE_DELETE_ERROR = MessageId.create(Project.SCM_API, 1213, "审批流程实例--删除失败!");

    MessageId PROCESS_INSTANCE_DETAIL_ERROR = MessageId.create(Project.SCM_API, 1214, "审批流程实例--查询详情失败!");

    MessageId PROCESS_INSTANCE_QUERY_ERROR = MessageId.create(Project.SCM_API, 1215, "审批流程实例--条件查询失败");

    MessageId PROCESS_INSTANCE_AUDIT_ERROR = MessageId.create(Project.SCM_API, 1216, "审批流程实例--审核失败");

    MessageId PROCESS_INSTANCE_STATUS_EXAMINE_QUERY_ERROR = MessageId.create(Project.SCM_API, 1217, "审批流程实例--查询审核状态失败");

    MessageId PROCESS_STATUS_EXAMINE_IDS_IS_NOT_NULL_ERROR = MessageId.create(Project.SCM_API, 1218, "审批流程实例--查询审核状态订单列表不能为空");
    MessageId PROCESS_DEPLOYED_EXISTS_ERROR = MessageId.create(Project.SCM_API, 1219, "查询流程部署状态失败");
    MessageId PROCESS_INSTANCE_REJECT_PARAM_ERROR = MessageId.create(Project.SCM_API, 1220, "审批实例-驳回单据参数错误");
    MessageId PROCESS_INSTANCE_REJECT_ERROR = MessageId.create(Project.SCM_API, 1221, "审批实例-驳回单据失败");
    MessageId PROCESS_INSTANCE_IS_NOT_ERROR = MessageId.create(Project.SCM_API, 1222, "无审批记录");
    MessageId PROCESS_INSTANCE_NOT_FOUND_PERSON_ERROR = MessageId.create(Project.SCM_API, 1223, "审批实例-未找到当前审批人的数据,无法驳回");

    MessageId STOCK_PICKING_COMMIT_ERROR = MessageId.create(Project.SCM_API, 2000, "库存移动订单--提审失败!");

    MessageId STOCK_PICKING_DELETE_ERROR = MessageId.create(Project.SCM_API, 2001, "库存移动订单--删除失败!");

    MessageId STOCK_PICKING_CONFIRM_ERROR = MessageId.create(Project.SCM_API, 2002, "库存移动订单--审核失败!");

    MessageId STOCK_PICKING_MODIFY_ERROR = MessageId.create(Project.SCM_API, 2003, "库存移动订单--编辑失败!");

    MessageId ERP_STATISTICS_QUERY_ERROR = MessageId.create(Project.SCM_API, 2004, "进销存统计--查询失败！");

    MessageId ERP_STATISTICS_QUERY_PARAMS_ERROR = MessageId.create(Project.SCM_API, 2005, "进销存统计--查询参数错误！");

    MessageId WAREHOUSE_AREA_QUERY_PARAM_ERROR = MessageId.create(Project.SCM_API, 2100, "仓库区域管理-查询参数错误!");

    MessageId WAREHOUSE_AREA_QUERY_ERROR = MessageId.create(Project.SCM_API, 2101, "仓库区域管理--查询列表失败!");

    MessageId WAREHOUSE_AREA_INSERT_PARAM_ERROR = MessageId.create(Project.SCM_API, 2102, "仓库区域管理--添加区域参数错误!");

    MessageId WAREHOUSE_AREA_INSERT_ERROR = MessageId.create(Project.SCM_API, 2103, "仓库区域管理--添加区域失败!");

    MessageId WAREHOUSE_AREA_MODIFY_PARAM_ERROR = MessageId.create(Project.SCM_API, 2104, "仓库区域管理--更新区域参数错误!");

    MessageId WAREHOUSE_AREA_MODIFY_ERROR = MessageId.create(Project.SCM_API, 2105, "仓库区域管理--更新区域失败!");

    MessageId WAREHOUSE_AREA_DATA_NOT_FOUND = MessageId.create(Project.SCM_API, 2106, "仓库区域管理--该区域不存在!");

    MessageId WAREHOUSE_AREA_DETAIL_ERROR = MessageId.create(Project.SCM_API, 2107, "仓库区域管理--查询区域详情失败!");

    MessageId WAREHOUSE_AREA_DELETE_ERROR = MessageId.create(Project.SCM_API, 2108, "仓库区域管理--删除区域失败!");

    MessageId WAREHOUSE_SHELF_TYPE_QUERY_PARAM_ERROR = MessageId.create(Project.SCM_API, 2109, "仓库货架类型管理--查询参数错误!");

    MessageId WAREHOUSE_SHELF_TYPE_QUERY_ERROR = MessageId.create(Project.SCM_API, 2110, "仓库货架类型管理--查询失败!");

    MessageId WAREHOUSE_SHELF_TYPE_INSERT_PARAM_ERROR = MessageId.create(Project.SCM_API, 2111, "仓库货架类型管理--添加类型参数错误!");

    MessageId WAREHOUSE_SHELF_TYPE_INSERT_ERROR = MessageId.create(Project.SCM_API, 2112, "仓库货架类型管理--添加类型失败!");

    MessageId WAREHOUSE_SHELF_TYPE_MODIFY_PARAM_ERROR = MessageId.create(Project.SCM_API, 2113, "仓库货架类型管理--更新参数错误!");

    MessageId WAREHOUSE_SHELF_TYPE_MODIFY_ERROR = MessageId.create(Project.SCM_API, 2114, "仓库货架类型管理--更新失败!");

    MessageId WAREHOUSE_SHELF_TYPE_DATA_NOT_FOUND = MessageId.create(Project.SCM_API, 2115, "仓库货架类型管理--该货架类型不存在!");

    MessageId WAREHOUSE_SHELF_TYPE_DETAIL_ERROR = MessageId.create(Project.SCM_API, 2116, "仓库货架类型管理--查询详情失败!");

    MessageId WAREHOUSE_SHELF_TYPE_DELETE_ERROR = MessageId.create(Project.SCM_API, 2117, "仓库货架类型管理--删除失败!");

    MessageId WAREHOUSE_SHELF_QUERY_PARAM_ERROR = MessageId.create(Project.SCM_API, 2118, "仓库货架管理--查询参数错误!");

    MessageId WAREHOUSE_SHELF_QUERY_ERROR = MessageId.create(Project.SCM_API, 2119, "仓库货架管理--查询失败!");

    MessageId WAREHOUSE_SHELF_INSERT_PARAM_ERROR = MessageId.create(Project.SCM_API, 2120, "仓库货架管理--添加货架参数错误!");

    MessageId WAREHOUSE_SHELF_INSERT_ERROR = MessageId.create(Project.SCM_API, 2121, "仓库货架管理--添加货架失败!");

    MessageId WAREHOUSE_SHELF_DATA_NOT_FOUND = MessageId.create(Project.SCM_API, 2122, "仓库货架管理--该货架不存在!");

    MessageId WAREHOUSE_SHELF_MODIFY_PARAM_ERROR = MessageId.create(Project.SCM_API, 2123, "仓库货架管理--更新货架参数错误!");

    MessageId WAREHOUSE_SHELF_MODIFY_ERROR = MessageId.create(Project.SCM_API, 2124, "仓库货架管理--更新货架失败!");

    MessageId WAREHOUSE_SHELF_DETAIL_ERROR = MessageId.create(Project.SCM_API, 2125, "仓库货架管理--查询详情失败!");

    MessageId WAREHOUSE_SHELF_DELETE_ERROR = MessageId.create(Project.SCM_API, 2126, "仓库货架管理--删除货架失败!");

    MessageId GOODS_SHELF_QUERY_PARAM_ERROR = MessageId.create(Project.SCM_API, 2127, "查询商品货架位置参数错误!");

    MessageId GOODS_SHELF_QUERY_ERROR = MessageId.create(Project.SCM_API, 2128, "查询商品货架位置错误!");

    MessageId WAREHOUSE_AREA_WAREHOUSE_SHELF_EXIST = MessageId.create(Project.SCM_API, 2129, "删除仓库区域失败--该区域有货架不允许删除!");

    MessageId WAREHOUSE_SHELF_TYPE_WAREHOUSE_SHELF_EXIST = MessageId.create(Project.SCM_API, 2130, "删除货架类型失败--存在该类型货架不允许删除!");

    MessageId AUDIT_ORDER_QUERY_PARAM_ERROR = MessageId.create(Project.SCM_API, 2200, "查询待审核订单列表参数错误!");

    MessageId AUDIT_ORDER_QUERY_ERROR = MessageId.create(Project.SCM_API, 2201, "查询待审核订单列表失败!");

    MessageId GET_USER_INFO_ERROR = MessageId.create(Project.SCM_API, 2202, "获取用户信息失败!");

    MessageId ES_INIT_STOCK_ERROR = MessageId.create(Project.SCM_API, 2006, "初始化es库存失败!");

    MessageId DISTRIBUTION_CONFIG_QUERY_ERROR = MessageId.create(Project.SCM_API, 2007, "系统设置--配送设置查询失败");

    //单据商品(7000 - 7100)
    MessageId BILL_GOODS_PARAMS_ERROR = MessageId.create(Project.SCM_API, 7000, "查询单据商品信息参数错误");

    MessageId BILL_TYPE_IS_NULL = MessageId.create(Project.SCM_API, 7001, "单据类型为空");

    MessageId BILL_GOODS_QUERY_ERROR = MessageId.create(Project.SCM_API, 7002, "单据商品查询失败！");

    MessageId APPLY_RECEIVING_AND_DELIVERY_RECORD_ERROR = MessageId.create(Project.SCM_API, 7003, "查询申请单收发货记录失败");

    MessageId APPLY_PEDING_ORDER = MessageId.create(Project.SCM_API, 7004, "不能关闭,还有待处理的配送单!");

    MessageId INSPECTION_PEDING_ORDER = MessageId.create(Project.SCM_API, 7005, "不能关闭,还有待处理的验收入库单!");

    MessageId PURCHASE_PEDING_ORDER = MessageId.create(Project.SCM_API, 7006, "不能关闭,还有待处理的采购入库单!");

    MessageId NOT_IN_DISTRIBUTION_ORDER = MessageId.create(Project.SCM_API, 7007, "不能关闭,还有已审核却未入库的配送单,请完成入库!");

    MessageId ORDER_IS_DONE_NOT_CLOSE_ERROR = MessageId.create(Project.SCM_API, 7008, "该单据已完成,无需关闭该订单");

    MessageId STOCK_IN_LIST_PARAMS_ERROR = MessageId.create(Project.SCM_API, 7009, "查询app入库列表参数错误");

    MessageId STOCK_IN_LIST_QUERY_ERROR = MessageId.create(Project.SCM_API, 7010, "查询app入库列表失败");

    MessageId INIT_DISTIRBUTION_HISTORY_ERROR = MessageId.create(Project.SCM_API, 7011, "初始化配送单历史数据失败");
}

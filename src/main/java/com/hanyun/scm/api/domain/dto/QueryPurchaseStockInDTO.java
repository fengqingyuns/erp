package com.hanyun.scm.api.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * QueryPurchaseStockInDTO
 * Date: 2017/6/5
 * Time: 下午4:13
 *
 * @author tianye@hanyun.com
 */
@ApiModel(value = "查询采购入库单参数实体")
public class QueryPurchaseStockInDTO {

    @ApiModelProperty(value = "品牌id", required = true)
    @NotEmpty
    private String brandId;

    @ApiModelProperty(value = "门店id")
    private String storeId;

    @ApiModelProperty(value = "查询采购入库单开始时间")
    private String queryBeginTime;

    @ApiModelProperty(value = "查询采购入库单结束时间")
    private String queryEndTime;

    @ApiModelProperty(value = "采购入库单编号(展示编号)")
    private String stockPickingDocumentId;

    @ApiModelProperty(value = "收货员id")
    private String businessManId;

    @ApiModelProperty(value = "供应商id")
    private String supplierId;

    @ApiModelProperty(value = "入库单状态(0、已保存  1、已提审  2、已收货/审核中  3、已审核  4、已完成  5、已终止)")
    private Integer stockPickingStatus;

    @ApiModelProperty(value = "入库仓id")
    private String toWarehouseId;

    @ApiModelProperty(value = "当前登录人id")
    private String userId;

    @ApiModelProperty(value = "每页条数")
    private Integer pageSize;

    @ApiModelProperty(value = "页码")
    private Integer pageNum;

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

    public String getStockPickingDocumentId() {
        return stockPickingDocumentId;
    }

    public void setStockPickingDocumentId(String stockPickingDocumentId) {
        this.stockPickingDocumentId = stockPickingDocumentId;
    }

    public String getBusinessManId() {
        return businessManId;
    }

    public void setBusinessManId(String businessManId) {
        this.businessManId = businessManId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getStockPickingStatus() {
        return stockPickingStatus;
    }

    public void setStockPickingStatus(Integer stockPickingStatus) {
        this.stockPickingStatus = stockPickingStatus;
    }

    public String getToWarehouseId() {
        return toWarehouseId;
    }

    public void setToWarehouseId(String toWarehouseId) {
        this.toWarehouseId = toWarehouseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}

package com.hanyun.scm.api.domain.dto;

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
 * QueryPurchaseOrderDTO
 * Date: 2017/6/2
 * Time: 下午3:27
 *
 * @author tianye@hanyun.com
 */
public class QueryPurchaseOrderDTO {

    @ApiModelProperty(value = "品牌id", required = true)
    @NotEmpty
    private String brandId;

    @ApiModelProperty(value = "门店id")
    private String storeId;

    @ApiModelProperty(value = "用户id(当前登录用户id)")
    private String userId;

    @ApiModelProperty(value = "查询开始时间")
    private String startTime;

    @ApiModelProperty(value = "查询结束时间")
    private String endTime;

    @ApiModelProperty(value = "订单id(订单编号)")
    private String orderDocumentId;

    @ApiModelProperty(value = "采购员id")
    private String purchaseUserId;

    @ApiModelProperty(value = "供应商id")
    private String supplierId;

    @ApiModelProperty(value = "入库仓id")
    private String toWarehouseId;

    @ApiModelProperty(value = "订单状态(0、已保存  1、已审核  2、部分入库  3、已完成  4、已提审  5、审核中  6、已终止)")
    private Integer orderStatus;

    private String stockInStatus;

    @ApiModelProperty(value = "分页每页条数")
    private Integer pageSize;

    @ApiModelProperty(value = "分页页码")
    private Integer pageNum;

    public String getStockInStatus() {
        return stockInStatus;
    }

    public void setStockInStatus(String stockInStatus) {
        this.stockInStatus = stockInStatus;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getOrderDocumentId() {
        return orderDocumentId;
    }

    public void setOrderDocumentId(String orderDocumentId) {
        this.orderDocumentId = orderDocumentId;
    }

    public String getPurchaseUserId() {
        return purchaseUserId;
    }

    public void setPurchaseUserId(String purchaseUserId) {
        this.purchaseUserId = purchaseUserId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getToWarehouseId() {
        return toWarehouseId;
    }

    public void setToWarehouseId(String toWarehouseId) {
        this.toWarehouseId = toWarehouseId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
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

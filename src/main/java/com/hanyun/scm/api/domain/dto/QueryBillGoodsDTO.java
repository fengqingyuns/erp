package com.hanyun.scm.api.domain.dto;

import com.hanyun.scm.api.domain.DistributionOrderDetail;
import com.hanyun.scm.api.domain.PurchaseOrderDetail;
import com.hanyun.scm.api.domain.ReplenishmentApplyDetail;
import com.hanyun.scm.api.domain.StockCheckTaskDetail;
import com.hanyun.scm.api.domain.request.BaseRequest;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
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
 * QueryBillGoodsDTO
 * Date: 2017/7/10 0010
 * Time: 11:50
 *
 * @author tangqiming@hanyun.com
 */
public class QueryBillGoodsDTO extends BaseRequest {

    @NotEmpty
    private String brandId;

    private String storeId;

    private String goodsId;             //商品id

    private String goodsName;           //商品名称

    private String goodsCode;           //商品SKU

    private String goodsBarCode;        //商品国条

    private String classifyId;          //商品分类id

    @NotEmpty
    private String orderId;             //单据id
    @NotNull
    private Integer orderType;          //单据类型          1,采购订单  2,配送单   3,补货申请单  8,盘点任务单快照

    private String startTime;           //开始时间

    private String endTime;             //结束时间

    private List<String> goodsIds;      //商品id

    private List<String> skipIds;         //过滤商品ids

    private String condition;               //模糊查询

    //采购订单商品
    public PurchaseOrderDetail setPurchaseOrderDetail(QueryBillGoodsDTO dto){
        PurchaseOrderDetail detail = new PurchaseOrderDetail();
        detail.setBrandId(dto.getBrandId());
        detail.setStoreId(dto.getStoreId());
        detail.setGoodsId(dto.getGoodsId());
//        detail.setGoodsName(dto.getGoodsName());
//        detail.setGoodsCode(dto.getGoodsCode());
//        detail.setGoodsBarCode(dto.getGoodsBarCode());
        detail.setOrderId(dto.getOrderId());
        detail.setOneTime(dto.getStartTime());
        detail.setTwoTime(dto.getEndTime());
        detail.setClassifyId(dto.getClassifyId());
        detail.setPageSize(dto.getPageSize());
        detail.setPageNum(dto.getPageNum());
        detail.setCondition(dto.getCondition());
        return detail;
    }
    //配送单商品
    public DistributionOrderDetail setDistributionOrderDetail(QueryBillGoodsDTO dto){
        DistributionOrderDetail detail = new DistributionOrderDetail();
        detail.setBrandId(dto.getBrandId());
        detail.setStoreId(dto.getStoreId());
        detail.setGoodsId(dto.getGoodsId());
//        detail.setGoodsName(dto.getGoodsName());
//        detail.setGoodsCode(dto.getGoodsCode());
//        detail.setGoodsBarCode(dto.getGoodsBarCode());
        detail.setDistributionOrderId(dto.getOrderId());
        detail.setStartTime(dto.getStartTime());
        detail.setEndTime(dto.getEndTime());
        detail.setClassifyId(dto.getClassifyId());
        detail.setPageSize(dto.getPageSize());
        detail.setPageNum(dto.getPageNum());
        detail.setCondition(dto.getCondition());
        return detail;
    }
    //补货申请单商品
    public ReplenishmentApplyDetail setReplenishmentApplyDetail(QueryBillGoodsDTO dto){
        ReplenishmentApplyDetail detail = new ReplenishmentApplyDetail();
        detail.setBrandId(dto.getBrandId());
        detail.setStoreId(dto.getStoreId());
        detail.setGoodsId(dto.getGoodsId());
//        detail.setGoodsName(dto.getGoodsName());
//        detail.setGoodsCode(dto.getGoodsCode());
//        detail.setGoodsBarCode(dto.getGoodsBarCode());
        detail.setReplenishmentId(dto.getOrderId());
        detail.setStartTime(dto.getStartTime());
        detail.setEndTime(dto.getEndTime());
        detail.setClassifyId(dto.getClassifyId());
        detail.setPageSize(dto.getPageSize());
        detail.setPageNum(dto.getPageNum());
        detail.setCondition(dto.getCondition());
        return detail;
    }
    //任务单商品
    public StockCheckTaskDetail setStockCheckTaskDetail(QueryBillGoodsDTO dto){
        StockCheckTaskDetail detail = new StockCheckTaskDetail();
        detail.setBrandId(dto.getBrandId());
        detail.setStoreId(dto.getStoreId());
        detail.setGoodsId(dto.getGoodsId());
//        detail.setGoodsName(dto.getGoodsName());
//        detail.setGoodsCode(dto.getGoodsCode());
//        detail.setGoodsBarCode(dto.getGoodsBarCode());
        detail.setStockCheckId(dto.getOrderId());
        detail.setStartTime(dto.getStartTime());
        detail.setEndTime(dto.getEndTime());
        detail.setClassifyId(dto.getClassifyId());
        detail.setPageSize(dto.getPageSize());
        detail.setPageNum(dto.getPageNum());
        detail.setGoodsIds(dto.getGoodsIds());
        detail.setSkipIds(dto.getSkipIds());
        detail.setCondition(dto.getCondition());
        return detail;
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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public void setGoodsBarCode(String goodsBarCode) {
        this.goodsBarCode = goodsBarCode;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
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

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public List<String> getGoodsIds() {
        return goodsIds;
    }

    public void setGoodsIds(List<String> goodsIds) {
        this.goodsIds = goodsIds;
    }

    public List<String> getSkipIds() {
        return skipIds;
    }

    public void setSkipIds(List<String> skipIds) {
        this.skipIds = skipIds;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}

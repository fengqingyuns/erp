package com.hanyun.scm.api.domain.request.stock;

import com.alibaba.druid.util.StringUtils;
import com.hanyun.ground.util.date.DateFormatUtil;
import com.hanyun.scm.api.domain.StockPicking;
import com.hanyun.scm.api.domain.StockPickingGoods;

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
 * WarehouseRequest
 * Date: 2016/10/21
 * Time: 上午11:11
 *
 * @author tianye@hanyun.com
 */
public class StockPickingRequest extends StockPicking {

    private String jsonList;

    private List supplierIdlist;

    private String oneTime;

    private String twoTime;

    private Date queryBeginTime;

    private Date queryEndTime;

    private String goodsCode;   //商品编码

    private String features;    //规格

    private String userId;

    private List<StockPickingGoods>  StockPickingGoodsList;

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJsonList() {
        return jsonList;
    }

    public void setJsonList(String jsonList) {
        this.jsonList = jsonList;
    }

    public List getSupplierIdlist() {
        return supplierIdlist;
    }

    public void setSupplierIdlist(List supplierIdlist) {
        this.supplierIdlist = supplierIdlist;
    }

    public String getOneTime() {
        return oneTime;
    }

    public void setOneTime(String oneTime) {
        this.oneTime = oneTime;
    }

    public String getTwoTime() {
        return twoTime;
    }

    public void setTwoTime(String twoTime) {
        this.twoTime = twoTime;
    }

    public Date getQueryBeginTime() {
        return queryBeginTime;
    }

    public void setQueryBeginTime(Date queryBeginTime) {
        this.queryBeginTime = queryBeginTime;
    }

    public Date getQueryEndTime() {
        return queryEndTime;
    }

    public void setQueryEndTime(Date queryEndTime) {
        this.queryEndTime = queryEndTime;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    @Override
    public List<StockPickingGoods> getStockPickingGoodsList() {
        return StockPickingGoodsList;
    }

    @Override
    public void setStockPickingGoodsList(List<StockPickingGoods> stockPickingGoodsList) {
        StockPickingGoodsList = stockPickingGoodsList;
    }

    public StockPickingRequest(String stockPickingId, String stockPickingDocumentId, String brandId,
                               String storeId, String srcOrderId, String srcOrderDocumentId, String supplierId, String supplierName,
                               String srcWarehouseId, String srcWarehouseName, String toWarehouseId, String toWarehouseName,
                               Long purchasePrice, Long purchaseAmount, Long stockPickingPrice, Long pickingAmount,
                               Integer stockPickingStatus, String operatorId, String operatorName, Integer stockPickingType,
                               Long stock, Integer incomeReason, Integer outReason, String remark, Date createTime,
                               Date updateTime, Integer validStatus, Integer pageNum, Integer pageSize, String queryBeginTime,
                               String queryEndTime, Integer purchaseType, String businessManId, String businessManName) {
        this.setStockPickingId(stockPickingId);
        this.setStockPickingDocumentId(stockPickingDocumentId);
        this.setBrandId(brandId);
        this.setStoreId(storeId);
        this.setSrcOrderId(srcOrderId);
        this.setSrcOrderDocumentId(srcOrderDocumentId);
        this.setSupplierId(supplierId);
        this.setSupplierName(supplierName);
        this.setSrcWarehouseId(srcWarehouseId);
        this.setSrcWarehouseName(srcWarehouseName);
        this.setToWarehouseId(toWarehouseId);
        this.setToWarehouseName(toWarehouseName);
        this.setPurchasePrice(purchasePrice);
        this.setPurchaseAmount(purchaseAmount);
        this.setStockPickingPrice(stockPickingPrice);
        this.setPickingAmount(pickingAmount);
        this.setStockPickingStatus(stockPickingStatus);
        this.setOperatorId(operatorId);
        this.setOperatorName(operatorName);
        this.setStockPickingType(stockPickingType);
        this.setStock(stock);
        this.setIncomeReason(incomeReason);
        this.setOutReason(outReason);
        this.setRemark(remark);
        this.setCreateTime(createTime);
        this.setUpdateTime(updateTime);
        this.setValidStatus(validStatus);
        this.setPageNum(pageNum);
        this.setPageSize(pageSize);
        if (!StringUtils.isEmpty(queryBeginTime)) {
            this.queryBeginTime = DateFormatUtil.parseDateISO(queryBeginTime);
        }
        if (!StringUtils.isEmpty(queryEndTime)) {
            this.queryEndTime = DateFormatUtil.parseDateISO(queryEndTime);
        }
        this.setPurchaseType(purchaseType);
        this.setBusinessManId(businessManId);
        this.setBusinessManName(businessManName);
    }

    public StockPickingRequest(){
        super();
    }
}

package com.hanyun.scm.api.domain.result;

import com.hanyun.scm.api.domain.StockQuant;

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
 * DateResult
 * Date: 2017/02/13
 * Time: 下午14:06
 *
 * @author tianye@hanyun.com
 */
public class StockQuantResult extends StockQuant {

    private String stockQuantId;

    private Long totalNum;                //非持久化數據,用來存放總數量

    private Long totalUnitPrice;          //非持久化數據,用來存放總价格

    private Long totalCash;               //非持久化數據,用來存放總金额

    private String goodsId;

    private String goodsName;

    private String goodsPic;                //商品图片

    private String goodsCode;

    private String goodsBarCode;

    private String unitName;

    private String features;

    private String  classifyId;

    private String classifyName;

    private String warehouseId;

    private String warehouseName;

    private Long stockNum;

    private Long unitPrice;

    private Long initialUnitPrice;

    private Long initialStockNum;

    private Long totalPrice;

    private Long totalInitialPrice;

    private Long initinalTotalNum;              //期初总库存

    private Long initinalTotalPrice;            //期初单价

    private Long initialTotalCash;              //期初总价

    private String goodsBrandName;

    @Override
    public Long getTotalPrice() {
        return totalPrice;
    }

    @Override
    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getTotalInitialPrice() {
        return totalInitialPrice;
    }

    public void setTotalInitialPrice(Long totalInitialPrice) {
        this.totalInitialPrice = totalInitialPrice;
    }

    public String getGoodsBrandName() {
        return goodsBrandName;
    }

    public void setGoodsBrandName(String goodsBrandName) {
        this.goodsBrandName = goodsBrandName;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public String getStockQuantId() {
        return stockQuantId;
    }

    public void setStockQuantId(String stockQuantId) {
        this.stockQuantId = stockQuantId;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public void setGoodsBarCode(String goodsBarCode) {
        this.goodsBarCode = goodsBarCode;
    }

    public Long getTotalUnitPrice() {
        return totalUnitPrice;
    }

    public void setTotalUnitPrice(Long totalUnitPrice) {
        this.totalUnitPrice = totalUnitPrice;
    }

    public Long getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(Long totalCash) {
        this.totalCash = totalCash;
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

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Long getStockNum() {
        return stockNum;
    }

    public void setStockNum(Long stockNum) {
        this.stockNum = stockNum;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getInitialUnitPrice() {
        return initialUnitPrice;
    }

    public void setInitialUnitPrice(Long initialUnitPrice) {
        this.initialUnitPrice = initialUnitPrice;
    }

    public Long getInitialStockNum() {
        return initialStockNum;
    }

    public void setInitialStockNum(Long initialStockNum) {
        this.initialStockNum = initialStockNum;
    }

    public Long getInitinalTotalNum() {
        return initinalTotalNum;
    }

    public void setInitinalTotalNum(Long initinalTotalNum) {
        this.initinalTotalNum = initinalTotalNum;
    }

    public Long getInitinalTotalPrice() {
        return initinalTotalPrice;
    }

    public void setInitinalTotalPrice(Long initinalTotalPrice) {
        this.initinalTotalPrice = initinalTotalPrice;
    }

    public Long getInitialTotalCash() {
        return initialTotalCash;
    }

    public void setInitialTotalCash(Long initialTotalCash) {
        this.initialTotalCash = initialTotalCash;
    }
}

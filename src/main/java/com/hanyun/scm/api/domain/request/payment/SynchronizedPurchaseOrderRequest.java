package com.hanyun.scm.api.domain.request.payment;

import com.hanyun.ground.util.date.DateFormatUtil;
import com.hanyun.scm.api.domain.StockPicking;

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
 * SynchronizedPurchaseOrderRequest
 * Date: 2017/3/17
 * Time: 下午6:20
 *
 * @author tianye@hanyun.com
 */
public class SynchronizedPurchaseOrderRequest {

    private String brandId; //品牌id

    private String billId;  //单据编号

    private String documentId;  //显示编号

    private String relateUnitId;  //往来单位编号

    private Integer busiType;   //原单据业务类型

    private Integer billStatus; //原单据状态

    private Long totalAmt;  //总金额

    private Long discountAmt;   //优惠金额

    private Long actualAmt; //单据应付金额

    private Long hadVerifyAmt;  //已核销金额

    private String billDate;    //单据日期

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getRelateUnitId() {
        return relateUnitId;
    }

    public void setRelateUnitId(String relateUnitId) {
        this.relateUnitId = relateUnitId;
    }

    public Integer getBusiType() {
        return busiType;
    }

    public void setBusiType(Integer busiType) {
        this.busiType = busiType;
    }

    public Integer getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(Integer billStatus) {
        this.billStatus = billStatus;
    }

    public Long getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Long totalAmt) {
        this.totalAmt = totalAmt;
    }

    public Long getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(Long discountAmt) {
        this.discountAmt = discountAmt;
    }

    public Long getActualAmt() {
        return actualAmt;
    }

    public void setActualAmt(Long actualAmt) {
        this.actualAmt = actualAmt;
    }

    public Long getHadVerifyAmt() {
        return hadVerifyAmt;
    }

    public void setHadVerifyAmt(Long hadVerifyAmt) {
        this.hadVerifyAmt = hadVerifyAmt;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public SynchronizedPurchaseOrderRequest(StockPicking stockPicking) {
        this.brandId = stockPicking.getBrandId();
        this.billId = stockPicking.getStockPickingId();
        this.documentId = stockPicking.getStockPickingDocumentId();
        this.relateUnitId = stockPicking.getSupplierId();
        this.billStatus = stockPicking.getStockPickingStatus();
        this.busiType = stockPicking.getStockPickingType();
        this.discountAmt = stockPicking.getDistinctPrice();
        this.totalAmt = stockPicking.getStockPickingPrice();
        this.actualAmt = totalAmt - discountAmt;
        this.hadVerifyAmt = stockPicking.getPaymentPrice();
        this.billDate = DateFormatUtil.formatDateTime(stockPicking.getAuditorTime());
    }
}

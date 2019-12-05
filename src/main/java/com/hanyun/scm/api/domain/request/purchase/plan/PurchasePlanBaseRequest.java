package com.hanyun.scm.api.domain.request.purchase.plan;

import com.hanyun.scm.api.domain.PurchasePlanApply;
import com.hanyun.scm.api.domain.PurchasePlanDetail;
import com.hanyun.scm.api.domain.request.BaseRequest;

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
 * PurchasePlanBaseRequest
 * Date: 2017/3/1
 * Time: 上午10:13
 *
 * @author tianye@hanyun.com
 */
class PurchasePlanBaseRequest extends BaseRequest {

    private String planDocumentId;

    private String storeId;

    private Integer planStatus;

    private Integer purchaseType;

    private String operatorId;

    private String operatorName;

    private Date createTime;

    private Date updateTime;

    private Integer validStatus;

    private String detailId;//订单详情主键

    private String supplierId;//供应商id

    private String remark;

    private String businessManId;

    private String businessManName;

    private String auditorId;

    private String auditorName;

    private Date auditTime;

    private List<PurchasePlanApply> purchasePlanApplyList;

    private List<PurchasePlanDetail> purchasePlanDetailList;

    public String getPlanDocumentId() {
        return planDocumentId;
    }

    public void setPlanDocumentId(String planDocumentId) {
        this.planDocumentId = planDocumentId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(Integer planStatus) {
        this.planStatus = planStatus;
    }

    public Integer getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(Integer purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBusinessManId() {
        return businessManId;
    }

    public void setBusinessManId(String businessManId) {
        this.businessManId = businessManId;
    }

    public String getBusinessManName() {
        return businessManName;
    }

    public void setBusinessManName(String businessManName) {
        this.businessManName = businessManName;
    }

    public String getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(String auditorId) {
        this.auditorId = auditorId;
    }

    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public List<PurchasePlanApply> getPurchasePlanApplyList() {
        return purchasePlanApplyList;
    }

    public void setPurchasePlanApplyList(List<PurchasePlanApply> purchasePlanApplyList) {
        this.purchasePlanApplyList = purchasePlanApplyList;
    }

    public List<PurchasePlanDetail> getPurchasePlanDetailList() {
        return purchasePlanDetailList;
    }

    public void setPurchasePlanDetailList(List<PurchasePlanDetail> purchasePlanDetailList) {
        this.purchasePlanDetailList = purchasePlanDetailList;
    }
}

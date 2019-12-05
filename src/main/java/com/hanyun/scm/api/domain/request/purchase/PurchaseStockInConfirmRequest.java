package com.hanyun.scm.api.domain.request.purchase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

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
 * PurchaseReturnCreateRequest
 * Date: 2016/12/9
 * Time: 上午10:31
 *
 * @author tianye@hanyun.com
 */
@ApiModel(value = "审核采购入库单参数实体")
public class PurchaseStockInConfirmRequest {

    @ApiModelProperty(value = "采购入库单id", required = true)
    @NotEmpty
    private String stockPickingId;

    @ApiModelProperty(value = "审核时间(格式yyyy-MM-ddTHH:mm:ss)")
    private String auditTime;

    @ApiModelProperty(value = "审核人姓名")
    private String auditorName;

    @ApiModelProperty(value = "当前登录人id")
    private String userId;

    @ApiModelProperty(value = "审核状态 0、通过  1、不通过")
    private Boolean auditStatus;

    @ApiModelProperty(value = "采购入库单状态")
    private Integer purchaseStockInStatus;

    public Integer getPurchaseStockInStatus() {
        return purchaseStockInStatus;
    }

    public void setPurchaseStockInStatus(Integer purchaseStockInStatus) {
        this.purchaseStockInStatus = purchaseStockInStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Boolean auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getStockPickingId() {
        return stockPickingId;
    }

    public void setStockPickingId(String stockPickingId) {
        this.stockPickingId = stockPickingId;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }
}

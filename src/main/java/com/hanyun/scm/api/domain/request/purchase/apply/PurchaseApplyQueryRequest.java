package com.hanyun.scm.api.domain.request.purchase.apply;

import org.hibernate.validator.constraints.NotEmpty;

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
 * PurchaseApplyQueryRequest
 * Date: 2016/12/9
 * Time: 下午2:35
 *
 * @author tianye@hanyun.com
 */
public class PurchaseApplyQueryRequest extends PurchaseApplyBaseRequest {
    @NotEmpty
    private String brandId;     //品牌id

    private String applyId; //采购计划单id

    private String skipIds;

    private List<String> skipIdList;

    private String queryBeginTime;//查询计划时间区间传入值

    private String queryEndTime;//查询计划时间区间传入值

    private String userId;

    private List<String> ids;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getSkipIds() {
        return skipIds;
    }

    public void setSkipIds(String skipIds) {
        this.skipIds = skipIds;
    }

    public List<String> getSkipIdList() {
        return skipIdList;
    }

    public void setSkipIdList(List<String> skipIdList) {
        this.skipIdList = skipIdList;
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

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}

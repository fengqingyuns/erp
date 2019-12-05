package com.hanyun.scm.api.domain.request.aggregate;

import javax.validation.constraints.NotNull;
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
 * AggregateDistributionRequest
 * Date: 2017/3/12
 * Time: 下午1:59
 *
 * @author tianye@hanyun.com
 */
public class AggregateDistributionRequest {

    private List<String> applyIds;

    private List<String> goodsIds;

    private Integer editStatus;

    public Integer getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(Integer editStatus) {
        this.editStatus = editStatus;
    }

    public List<String> getApplyIds() {
        return applyIds;
    }

    public void setApplyIds(List<String> applyIds) {
        this.applyIds = applyIds;
    }

    public List<String> getGoodsIds() {
        return goodsIds;
    }

    public void setGoodsIds(List<String> goodsIds) {
        this.goodsIds = goodsIds;
    }
}

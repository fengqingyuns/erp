package com.hanyun.scm.api.domain.request.purchase.apply;

import com.hanyun.scm.api.domain.PurchaseApplyGoods;
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
 * PurchaseApplyModifyRequest
 * Date: 2016/12/9
 * Time: 下午2:35
 *
 * @author tianye@hanyun.com
 */
public class PurchaseApplyModifyRequest extends PurchaseApplyBaseRequest {

    private String brandId;     //品牌id

    @NotEmpty
    private String applyId; //申请表id
    private List<PurchaseApplyGoods> purchaseApplyGoodsList;

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

    public List<PurchaseApplyGoods> getPurchaseApplyGoodsList() {
        return purchaseApplyGoodsList;
    }

    public void setPurchaseApplyGoodsList(List<PurchaseApplyGoods> purchaseApplyGoodsList) {
        this.purchaseApplyGoodsList = purchaseApplyGoodsList;
    }
}

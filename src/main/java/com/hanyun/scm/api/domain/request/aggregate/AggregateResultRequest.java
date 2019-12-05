package com.hanyun.scm.api.domain.request.aggregate;

import com.hanyun.scm.api.domain.request.distribution.DistributionOrderCreateRequest;
import com.hanyun.scm.api.domain.request.purchase.PurchaseStockInCreateRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderCreateRequest;

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
 * AggregateResultRequest
 * Date: 2017/3/14
 * Time: 下午4:03
 *
 * @author tianye@hanyun.com
 */
public class AggregateResultRequest {

    private PurchaseOrderCreateRequest purchaseOrderCreateRequest;

    private List<DistributionOrderCreateRequest> distributionOrderCreateRequestList;

    public PurchaseOrderCreateRequest getPurchaseOrderCreateRequest() {
        return purchaseOrderCreateRequest;
    }

    public void setPurchaseOrderCreateRequest(PurchaseOrderCreateRequest purchaseOrderCreateRequest) {
        this.purchaseOrderCreateRequest = purchaseOrderCreateRequest;
    }

    public List<DistributionOrderCreateRequest> getDistributionOrderCreateRequestList() {
        return distributionOrderCreateRequestList;
    }

    public void setDistributionOrderCreateRequestList(List<DistributionOrderCreateRequest> distributionOrderCreateRequestList) {
        this.distributionOrderCreateRequestList = distributionOrderCreateRequestList;
    }
}

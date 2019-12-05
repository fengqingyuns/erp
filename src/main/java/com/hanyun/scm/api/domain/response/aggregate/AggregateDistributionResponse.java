package com.hanyun.scm.api.domain.response.aggregate;

import com.hanyun.scm.api.domain.response.BaseResponse;

import java.util.List;
import java.util.Map;

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
 * AggregateDistributionResponse
 * Date: 2017/3/13
 * Time: 上午10:04
 *
 * @author tianye@hanyun.com
 */
public class AggregateDistributionResponse extends BaseResponse{

    private Map goodsMap;

    public AggregateDistributionResponse(List<String> goodsIds, int count, Map goodsMap) {
        super(count, goodsIds);
        this.goodsMap = goodsMap;
    }

    public Map getGoodsMap() {
        return goodsMap;
    }

    public void setGoodsMap(Map goodsMap) {
        this.goodsMap = goodsMap;
    }
}

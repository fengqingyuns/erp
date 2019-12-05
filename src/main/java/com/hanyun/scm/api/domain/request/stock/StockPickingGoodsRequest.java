package com.hanyun.scm.api.domain.request.stock;

import com.hanyun.scm.api.domain.StockPickingGoods;

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
public class StockPickingGoodsRequest extends StockPickingGoods {

    private String skipIds;             //忽略的商品id集合json

    private List<String> skipIdList;    //忽略的商品id集合
    
    private String ids;             //忽略的商品id集合json

    private List<String> idList;    //忽略的商品id集合

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

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }
}

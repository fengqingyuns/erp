package com.hanyun.scm.api.domain.response.aggregate;

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
 * AggregateStatisticsResponse
 * Date: 2017/3/16
 * Time: 上午11:12
 *
 * @author tianye@hanyun.com
 */
public class AggregateStatisticsResponse {

    private String goodsId;

    private String storeId;

    private Long applyNum;

    private Long stockInNum;

    private Long scrapNum;

    private Integer saleNum;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Long getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Long applyNum) {
        this.applyNum = applyNum;
    }

    public Long getStockInNum() {
        return stockInNum;
    }

    public void setStockInNum(Long stockInNum) {
        this.stockInNum = stockInNum;
    }

    public Long getScrapNum() {
        return scrapNum;
    }

    public void setScrapNum(Long scrapNum) {
        this.scrapNum = scrapNum;
    }

    public Integer getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }
}

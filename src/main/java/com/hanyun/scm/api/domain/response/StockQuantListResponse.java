package com.hanyun.scm.api.domain.response;

import com.hanyun.scm.api.domain.StockQuant;

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
 * BaseResponse
 * Date: 2017/1/17
 * Time: 下午1:58
 *
 * @author 1007661792@qq.com
 */
public class StockQuantListResponse {

    private List<StockQuant> srcList;

    private List<StockQuant> toList;

    public StockQuantListResponse(List<StockQuant> srcList, List<StockQuant> toList){
        this.srcList = srcList;
        this.toList = toList;
    }

    public List<StockQuant> getSrcList() {
        return srcList;
    }

    public void setSrcList(List<StockQuant> srcList) {
        this.srcList = srcList;
    }

    public List<StockQuant> getToList() {
        return toList;
    }

    public void setToList(List<StockQuant> toList) {
        this.toList = toList;
    }
}

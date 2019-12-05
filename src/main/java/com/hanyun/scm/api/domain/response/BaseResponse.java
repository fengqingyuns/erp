package com.hanyun.scm.api.domain.response;

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
 * BaseResponse
 * Date: 2016/12/7
 * Time: 下午1:58
 *
 * @author tianye@hanyun.com
 */
public class BaseResponse {

    public BaseResponse(){
    }

    public BaseResponse(int count, List list) {
        this.count = count;
        this.list = list;
    }

    public BaseResponse(int count, List list, Map extData) {
        this.count = count;
        this.list = list;
        this.extData = extData;
    }

    private Integer count;

    private List list;

    private Map extData;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Map getExtData() {
        return extData;
    }

    public void setExtData(Map extData) {
        this.extData = extData;
    }
}

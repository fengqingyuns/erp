package com.hanyun.scm.api.domain.response;

import com.hanyun.scm.api.domain.DisassemblyOrderDetail;

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
public class ListResponse {

    public ListResponse(){
    }

    private List<DisassemblyOrderDetail> motherList;

    private List<DisassemblyOrderDetail> sonList;

    private DisassemblyOrderDetail detail;

    public ListResponse(List<DisassemblyOrderDetail> motherList, List<DisassemblyOrderDetail> sonList, DisassemblyOrderDetail detail){
        this.motherList = motherList;
        this.sonList = sonList;
        this.detail = detail;
    }

    public DisassemblyOrderDetail getDetail() {
        return detail;
    }

    public void setDetail(DisassemblyOrderDetail detail) {
        this.detail = detail;
    }

    public List<DisassemblyOrderDetail> getMotherList() {
        return motherList;
    }

    public void setMotherList(List<DisassemblyOrderDetail> motherList) {
        this.motherList = motherList;
    }

    public List<DisassemblyOrderDetail> getSonList() {
        return sonList;
    }

    public void setSonList(List<DisassemblyOrderDetail> sonList) {
        this.sonList = sonList;
    }
}

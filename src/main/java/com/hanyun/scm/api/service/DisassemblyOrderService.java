package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.DisassemblyOrder;
import com.hanyun.scm.api.domain.request.disassembly.DisassemblyOrderCreateRequest;
import com.hanyun.scm.api.domain.request.disassembly.DisassemblyOrderModifyRequest;
import com.hanyun.scm.api.domain.request.disassembly.DisassemblyOrderQueryRequest;
import com.hanyun.scm.api.exception.DisassemblyException;

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
 * StockCheckOrderService
 * Date: 17/1/13
 * Time: 下午16:05
 *
 * @author 1007661792@qq.com
 */
public interface DisassemblyOrderService {
    /**
     * 添加拆装单
     *
     * @param disassemblyOrderCreateRequest 添加拆装单参数
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse create(DisassemblyOrderCreateRequest disassemblyOrderCreateRequest);

    /**
     * 查询拆装单
     *
     * @param disassemblyOrderQueryRequest 查询拆装单参数
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse select(DisassemblyOrderQueryRequest disassemblyOrderQueryRequest);

    /**
     * 修改拆装单
     *
     * @param disassemblyOrderModifyRequest 修改拆装单参数
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse modify(DisassemblyOrderModifyRequest disassemblyOrderModifyRequest);

    /**
     * 查询拆装单详情
     *
     * @param disassemblyOrderId 修改拆装单参数
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse detail(String disassemblyOrderId);

    /**
     * 删除拆装单
     *
     * @param disassemblyOrderId 删除拆装单
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse delete(String disassemblyOrderId);

    /**
     * 导出拆装单列表
     *
     * @param disassemblyOrder
     * @return
     * @throws DisassemblyException
     */
    List<List<Object>> exportExcelBySuppleir(DisassemblyOrderQueryRequest disassemblyOrder) throws DisassemblyException;

    /**
     * 查询拆装单(for export)
     *
     * @param disassemblyOrderId 拆装单id
     * @return 返回信息
     * @throws DisassemblyException 异常信息
     */
    DisassemblyOrder getDisassemblyOrder(String disassemblyOrderId) throws DisassemblyException;

    /**
     * 提审
     * @param disassemblyOrderId 拆装单id
     * @return httpResponse
     */
    HttpResponse commit(String disassemblyOrderId);
}

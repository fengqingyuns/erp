package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.StockCheckOrderDetail;
import com.hanyun.scm.api.domain.request.stock.StockCheckOrderCreateRequest;
import com.hanyun.scm.api.domain.request.stock.StockCheckOrderModifyRequest;
import com.hanyun.scm.api.domain.request.stock.StockCheckOrderQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.exception.StockCheckOrderException;

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
 * Date: 16/6/22
 * Time: 下午1:51
 *
 * @author tianye@hanyun.com
 */
public interface StockCheckOrderService {

    /**
     * 添加库存盘点
     *
     * @param stockCheckOrderCreateRequest 添加盘点单参数
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse create(StockCheckOrderCreateRequest stockCheckOrderCreateRequest);

    /**
     * 查询盘点信息
     *
     * @param stockCheckOrderId 盘点单id
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse detail(String stockCheckOrderId, StockCheckOrderDetail record);

    /**
     * 查询库存盘点列表
     *
     * @param stockCheckOrderQueryRequest 查询盘点单参数
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse select(StockCheckOrderQueryRequest stockCheckOrderQueryRequest);

    /**
     * 修改库存盘点信息
     *
     * @param stockCheckOrderModifyRequest 修改盘点单参数
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse modify(StockCheckOrderModifyRequest stockCheckOrderModifyRequest);

    /**
     * 删除盘点单
     *
     * @param stockCheckOrderId 盘点单id
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse delete(String stockCheckOrderId);

    /**
     * 查询库存盘点单列表
     *
     * @param stockCheckOrderQueryRequest 请求参数
     * @param excel                       是否导出excel
     * @return HttpResponse
     * @throws StockCheckOrderException 异常
     * @author liuliang@hanyun.com
     */
    BaseResponse selectList(StockCheckOrderQueryRequest stockCheckOrderQueryRequest, boolean excel) throws StockCheckOrderException;

    List<StockCheckOrderDetail> exportCheckOrderUpdate(StockCheckOrderQueryRequest stockCheckOrderQueryRequest) throws StockCheckOrderException;

    /**
     * 提审盘点单
     * @param stockCheckOrderId 盘点单id
     * @return  返回值
     */
    HttpResponse commit(String stockCheckOrderId);
}

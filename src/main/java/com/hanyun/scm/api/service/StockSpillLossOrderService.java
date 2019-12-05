package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.StockSpillLossOrder;
import com.hanyun.scm.api.domain.StockSpillLossOrderDetail;
import com.hanyun.scm.api.domain.request.stock.StockSpillLossOrderRequest;

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
 * TestService
 * Date: 16/6/22
 * Time: 下午1:51
 *
 * @author qiaoyu_v@hanyun.com
 */
public interface StockSpillLossOrderService {
	HttpResponse select(StockSpillLossOrderRequest stockSpillLossOrderRequest);
	
	HttpResponse modifyOrder(StockSpillLossOrder stockSpillLossOrder);
	
	HttpResponse create(StockSpillLossOrder stockSpillLossOrder);

	HttpResponse auditOrder(StockSpillLossOrder stockSpillLossOrder);

	List<StockSpillLossOrder> exportStockSpillLossOrder(StockSpillLossOrderRequest stockSpillLossOrderRequest);
	
	List<StockSpillLossOrderDetail> exportSpillAndLossUpdate(StockSpillLossOrderRequest stockSpillLossOrderRequest);

	/**
	 * 提审
	 * @param spillLossOrderId	参数
	 * @return	返回值
     */
    HttpResponse commit(String spillLossOrderId);
}

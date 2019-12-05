package com.hanyun.scm.api.service;

import java.util.List;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.StockCheckDifference;
import com.hanyun.scm.api.domain.StockCheckDifferenceDetail;
import com.hanyun.scm.api.domain.request.stock.StockCheckDifferenceRequest;

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
 * @author tianye@hanyun.com
 */
public interface StockCheckDifferenceService {
	HttpResponse select(StockCheckDifferenceRequest stockCheckDifferenceRequest);
	
	HttpResponse modifyDifference(StockCheckDifference stockCheckDifference);
	
	HttpResponse auditDifference(StockCheckDifference stockCheckDifference);
	
	HttpResponse createDifference(StockCheckDifference stockCheckDifference);
	
	List<StockCheckDifference> exportCheckDifference(StockCheckDifferenceRequest StockCheckDifferenceRequest);
	
	HttpResponse createVariance(StockCheckDifference stockCheckDifference);
	
	List<StockCheckDifferenceDetail> exportDifferenceUpdate(StockCheckDifferenceRequest StockCheckDifferenceRequest);

	HttpResponse detail(String stockCheckDifferenceId);

	/**
	 * 提审盘点差异单
	 *
	 * @param stockCheckDifferenceId 差异单id
	 * @return HttpResponse
	 * @author tianye@hanyun.com
	 */
	HttpResponse commit(String stockCheckDifferenceId);
}

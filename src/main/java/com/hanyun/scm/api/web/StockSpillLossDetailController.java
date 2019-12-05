package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.StockSpillLossOrderDetail;
import com.hanyun.scm.api.service.StockSpillLossDetailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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
 * StockCheckTaskController
 * Date: 2016/12/25
 * Time: 下午7:37
 *
 * @author qiaoyu_v@hanyun.com
 */
@Controller
@RequestMapping(value = "/stock-spill-Loss-detail")
public class StockSpillLossDetailController {
	
	 	@Resource
	    private StockSpillLossDetailService stockSpillLossDetailService;
	 
	 	/**
	     * 查询盘点差异列表
	     *
	     * @param stockSpillLossOrderDetail
	     * @return HttpResponse
	     */
		@GetMapping(value = "/query")
	    @ResponseBody
	    public HttpResponse select(StockSpillLossOrderDetail stockSpillLossOrderDetail) {
	        return stockSpillLossDetailService.select(stockSpillLossOrderDetail);
	    }
		
}

package com.hanyun.scm.api.service;

import java.util.List;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.StockCheckTaskDetail;
import com.hanyun.scm.api.domain.dto.StockCheckTaskDTO;
import com.hanyun.scm.api.domain.request.BrandStoreRequest;
import com.hanyun.scm.api.domain.request.stock.StockCheckTaskCreateRequest;
import com.hanyun.scm.api.domain.request.stock.StockCheckTaskModifyRequest;
import com.hanyun.scm.api.domain.request.stock.StockCheckTaskQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.exception.StockCheckTaskException;

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
public interface StockCheckTaskService {

    /**
     * 添加库存盘点任务信息
     *
     * @param stockCheckTaskCreateRequest   添加盘点任务单参数
     * @return  HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse create(StockCheckTaskCreateRequest stockCheckTaskCreateRequest);

    /**
     * 查询库存盘点任务信息
     *
     * @param id    盘点任务单id
     * @return  HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse detail(String id);

    /**
     * 查询库存盘点任务列表
     *
     * @param stockCheckTaskQueryRequest    查询盘点任务单参数
     * @return  HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse select(StockCheckTaskQueryRequest stockCheckTaskQueryRequest);

    /**
     * 修改库存盘点任务信息
     *
     * @param stockCheckTaskModifyRequest   修改盘点任务单参数
     * @return  HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse modify(StockCheckTaskModifyRequest stockCheckTaskModifyRequest);
    
    /**
     * 审核-盘点结束
     *
     * @return HttpResponse
     * @author 王超群 
     */
    HttpResponse modifyStockCheckTaskEnd(StockCheckTaskModifyRequest stockCheckTaskModifyRequest);

    /**
     * 删除盘点任务单
     * @param id    盘点任务单id
     * @return  HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse delete(String id);
    
    /**
     * 查询盘点任务单列表
     *
     * @param stockCheckTaskQueryRequest 请求参数
     * @param excel 是否导出excel
     * @return HttpResponse
     * @throws StockCheckTaskException 异常
     * @author 自己的名字@hanyun.com
     */
    BaseResponse selectList(StockCheckTaskQueryRequest stockCheckTaskQueryRequest, boolean excel) throws StockCheckTaskException;

    /**
     * 查询带
     * @param brandStoreRequest 参数
     */
    Long waitForStockCheckCount(BrandStoreRequest brandStoreRequest) throws StockCheckTaskException;
    /**
     * 导出盘点任务单详单
     * @param stockCheckTaskQueryRequest    参数
     */
    
    List<StockCheckTaskDetail> exportDetailList(StockCheckTaskQueryRequest stockCheckTaskQueryRequest) throws StockCheckTaskException;

    /**
     * 提审盘点任务单
     *
     * @param stockCheckTaskId 任务单id
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse commit(String stockCheckTaskId);

    /**
     * 查询任务单详情
     * @param dto 参数
     * @return HttpResponse
     */
    HttpResponse queryTaskGoods(StockCheckTaskDTO dto);
}

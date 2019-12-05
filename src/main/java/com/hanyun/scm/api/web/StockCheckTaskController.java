package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.StockCheckTaskDetail;
import com.hanyun.scm.api.domain.dto.StockCheckTaskDTO;
import com.hanyun.scm.api.domain.request.stock.StockCheckTaskCreateRequest;
import com.hanyun.scm.api.domain.request.stock.StockCheckTaskModifyRequest;
import com.hanyun.scm.api.domain.request.stock.StockCheckTaskQueryRequest;
import com.hanyun.scm.api.exception.StockCheckTaskException;
import com.hanyun.scm.api.service.StockCheckTaskService;
import com.hanyun.scm.api.web.util.ExcelUtil;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
 * StockCheckTaskTaskController
 * Date: 2016/12/25
 * Time: 下午7:37
 *
 * @author tianye@hanyun.com
 */
@Controller
@RequestMapping(value = "/stock-check-task")
public class StockCheckTaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockCheckTaskController.class);

    @Resource
    private StockCheckTaskService stockCheckTaskService;

    /**
     * 创建库存盘点任务信息
     *
     * @param stockCheckTaskCreateRequest 创建盘点任务单参数
     * @return HttpResponse
     */
    @PostMapping(value = "/create")
    @ResponseBody
    public HttpResponse create(@RequestBody @Valid StockCheckTaskCreateRequest stockCheckTaskCreateRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("创建库存盘点任务单参数错误!");
            return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_PARAM_ERROR);
        }
        return stockCheckTaskService.create(stockCheckTaskCreateRequest);
    }

    /**
     * 查询库存盘点任务信息列表
     *
     * @param stockCheckTaskQueryRequest 查询盘点任务单参数
     * @return HttpResponse
     */
    @GetMapping(value = "/query")
    @ResponseBody
    public HttpResponse select(@Valid StockCheckTaskQueryRequest stockCheckTaskQueryRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("查询盘点任务单参数错误!");
            return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_QUERY_PARAM_ERROR);
        }
        return stockCheckTaskService.select(stockCheckTaskQueryRequest);
    }

    /**
     * 修改库存盘点任务信息
     *
     * @param stockCheckTaskModifyRequest 修改盘点任务单参数
     * @return HttpResponse
     */
    @PutMapping(value = "/modify")
    @ResponseBody
    public HttpResponse modify(@RequestBody @Valid StockCheckTaskModifyRequest stockCheckTaskModifyRequest, BindingResult result) {
        if (result.hasErrors()) {
            LOGGER.error("编辑盘点任务单参数错误!");
            return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_MODIFY_PARAM_ERROR);
        }
        return stockCheckTaskService.modify(stockCheckTaskModifyRequest);
    }

    /**
     * 审核-盘点结束
     *
     * @author 王超群
     */
    @PostMapping(value = "/modifyStockCheckTaskEnd", produces = "application/json")
    @ResponseBody
    public HttpResponse modifyStockCheckTaskEnd(@RequestBody StockCheckTaskModifyRequest stockCheckTaskModifyRequest) {
        return stockCheckTaskService.modifyStockCheckTaskEnd(stockCheckTaskModifyRequest);
    }

    /**
     * 删除库存盘点任务信息
     *
     * @param stockCheckTaskId 盘点任务单id
     * @return HttpResponse
     */
    @DeleteMapping(value = "/delete/{stockCheckTaskId}")
    @ResponseBody
    public HttpResponse delete(@PathVariable String stockCheckTaskId) {
        return stockCheckTaskService.delete(stockCheckTaskId);
    }

    /**
     * 查询库存盘点任务详情
     *
     * @param stockCheckTaskId 盘点任务单id
     * @return HttpResponse
     */
    @GetMapping(value = "/detail/{stockCheckTaskId}")
    @ResponseBody
    public HttpResponse detail(@PathVariable String stockCheckTaskId) {
        return stockCheckTaskService.detail(stockCheckTaskId);
    }

    /**
     * 提审盘点任务单
     *
     * @param stockCheckTaskId 提审参数id
     * @return HttpResponse
     */
    @GetMapping(value = "/commit/{stockCheckTaskId}")
    @ResponseBody
    public HttpResponse commit(@NotEmpty @PathVariable String stockCheckTaskId) {
        return stockCheckTaskService.commit(stockCheckTaskId);
    }

    /**
     * 导出盘点任务单列表
     *
     * @param stockCheckTaskQueryRequest 查询盘点任务单参数
     * @throws Exception 异常
     */
    @GetMapping(value = "/export")
    public void exportStockCheckTaskList(@Valid StockCheckTaskQueryRequest stockCheckTaskQueryRequest, BindingResult result, HttpServletResponse

            response) throws StockCheckTaskException {
        if (result.hasErrors()) {
            LOGGER.error("导出盘点任务单列表参数错误!");
            throw new StockCheckTaskException("导出盘点任务单列表参数错误!");
        }
        try {
            List stockCheckTaskList = stockCheckTaskService.selectList(stockCheckTaskQueryRequest, true).getList();
            String xlsName = "盘点任务单列表";
            String templateName = "stock_check_task_list.xls";
            String[] attributes = new String[]{"stockCheckTaskDocumentId", "stockCheckTaskTypeName", "operatorName", "warehouseName", "taskStartTime",

                    "taskEndTime", "stockCheckTaskStatusName"};
            ExcelUtil.export(xlsName, templateName, stockCheckTaskList, attributes, response);
        } catch (Exception e) {
            throw new StockCheckTaskException("导出盘点任务单列表参数错误");
        }
    }

    /**
     * 导出盘点任务单详单
     *
     * @param stockCheckTaskQueryRequest 查询盘点任务现详单参数
     * @throws StockCheckTaskException 异常
     */
    @GetMapping(value = "/exportTaskAdd")
    public void exportStockCheckTaskUpdate(@Valid StockCheckTaskQueryRequest stockCheckTaskQueryRequest, HttpServletResponse
            response) throws StockCheckTaskException {

        try {
            List<StockCheckTaskDetail> stockCheckTaskList = stockCheckTaskService.exportDetailList(stockCheckTaskQueryRequest);
            String xlsName = "盘点任务单详单：" + stockCheckTaskQueryRequest.getStockCheckTaskDocumentId();
            String templateName = "exportCheckTaskUpdate.xls";
            String[] attributes = new String[]{"goodsCode", "goodsBarCode", "goodsName", "features", "unitName",
                    "stockNum", "unitPrice", "checkDiffPrice", "goodsBrandName", "classifyName"};
            ExcelUtil.export(xlsName, templateName, stockCheckTaskList, attributes, response);
        } catch (Exception e) {
            throw new StockCheckTaskException("导出盘点任务单列表参数错误");
        }
    }

    /**
     * 查询任务单商品详情
     * @param dto 参数对象
     * @return HttpResponse
     */
    @PostMapping(value = "/queryTaskDetail")
    @ResponseBody
    public HttpResponse queryTaskDetail(@Valid @RequestBody StockCheckTaskDTO dto, BindingResult result){
        if (result.hasErrors()) {
            LOGGER.error("查询任务单商品详情参数错误!");
            return HttpResponse.failure(ResultCode.PARAM_ERROR);
        }
        return stockCheckTaskService.queryTaskGoods(dto);
    }
}

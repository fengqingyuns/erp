package com.hanyun.scm.api.web;


import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.request.process.instance.*;
import com.hanyun.scm.api.service.ProcessInstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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
 * ProcessInstanceController
 * Date: 2017/4/5
 * Time: 下午2:27
 *
 * @author tianye@hanyun.com
 */
@Controller
@RequestMapping("/process-instance")
public class ProcessInstanceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessInstanceController.class);

    @Resource
    private ProcessInstanceService processInstanceService;

    /**
     * 创建流程实例
     *
     * @param processInstanceCreateRequest 创建流程参数
     * @param bindingResult                验证信息
     * @return 返回值
     */
    @PostMapping("/create")
    @ResponseBody
    public HttpResponse create(@RequestBody @Valid ProcessInstanceCreateRequest processInstanceCreateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_PARAM_ERROR);
        }
        return processInstanceService.create(processInstanceCreateRequest, null);
    }

    /**
     * 查询流程实例
     *
     * @param processInstanceQueryRequest 查询流程实例参数
     * @param bindingResult               验证信息
     * @return 返回值
     */
    @GetMapping("/query")
    @ResponseBody
    public HttpResponse query(@Valid ProcessInstanceQueryRequest processInstanceQueryRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_PARAM_ERROR);
        }
        return processInstanceService.query(processInstanceQueryRequest);
    }

    /**
     * 修改流程实例
     *
     * @param processInstanceModifyRequest 修改流程实例参数
     * @param bindingResult                验证信息
     * @return 返回值
     */
    @PutMapping("/modify")
    @ResponseBody
    public HttpResponse modify(@RequestBody @Valid ProcessInstanceModifyRequest processInstanceModifyRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_UPDATE_ERROR);
        }
        return processInstanceService.modify(processInstanceModifyRequest);
    }

    /**
     * 删除流程实例
     *
     * @param businessId 单据id
     * @return 返回值
     */
    @DeleteMapping("/delete/{businessId}")
    @ResponseBody
    public HttpResponse delete(@PathVariable String businessId) {
        return processInstanceService.delete(businessId);
    }

    /**
     * 查询流程实例详情
     *
     * @param businessId 流程id
     * @return 返回值
     */
    @GetMapping("/detail/{businessId}")
    @ResponseBody
    public HttpResponse detail(@PathVariable String businessId) {
        return processInstanceService.detail(businessId);
    }

    /**
     * 审核
     *
     * @param processInstanceModifyRequest 审核参数
     * @return 返回值
     */
    @PutMapping("/audit")
    @ResponseBody
    public HttpResponse audit(@RequestBody @Valid ProcessInstanceModifyRequest processInstanceModifyRequest) {
        return processInstanceService.audit(processInstanceModifyRequest);
    }

    /**
     * 通过订单id查询是否当前登陆人审核和查看历史状态
     * @param request 参数
     * @return HttpResponse
     */
    @PostMapping(value = "/examine-status")
    @ResponseBody
    public HttpResponse queryExamineStatus(@RequestBody @Valid ExamineStatusQueryRequest request){
        return processInstanceService.queryExamineStatus(request);
    }

    /**
     * 驳回单据
     * @param request 对象
     * @param result 验证
     * @return HttpResponse
     */
    @PostMapping(value = "/reject")
    @ResponseBody
    public HttpResponse reject(@RequestBody @Valid ProcessInstanceRejectRequest request, BindingResult result){
        if(result.hasErrors()){
            LOGGER.error("驳回单据参数错误");
            return HttpResponse.failure(ResultCode.PROCESS_INSTANCE_REJECT_PARAM_ERROR);
        }
        return processInstanceService.reject(request);
    }

}

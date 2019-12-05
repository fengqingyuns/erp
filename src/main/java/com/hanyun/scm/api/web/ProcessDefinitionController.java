package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.request.process.definition.ProcessDefinitionCreateRequest;
import com.hanyun.scm.api.domain.request.process.definition.ProcessDefinitionModifyRequest;
import com.hanyun.scm.api.domain.request.process.definition.ProcessDefinitionQueryRequest;
import com.hanyun.scm.api.service.ProcessDefinitionService;
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
 * ProcessDefinitionController
 * Date: 2017/4/5
 * Time: 下午2:27
 *
 * @author tianye@hanyun.com
 */
@Controller
@RequestMapping("/process-definition")
public class ProcessDefinitionController {

    @Resource
    private ProcessDefinitionService processDefinitionService;

    /**
     * 创建流程
     * @param processDefinitionCreateRequest    创建流程参数
     * @param bindingResult 验证信息
     * @return  返回值
     */
    @PostMapping("/create")
    @ResponseBody
    public HttpResponse create(@RequestBody @Valid ProcessDefinitionCreateRequest processDefinitionCreateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_PARAM_ERROR);
        }
        return processDefinitionService.create(processDefinitionCreateRequest, Consts.PROCESS_STATUS_DEPLOYED);
    }

    /**
     * 部署流程
     * @param brandId   品牌id
     * @param processType 流程id
     * @return  返回值
     */
    @GetMapping("/deploy/{brandId}/{processType}")
    @ResponseBody
    public HttpResponse deploy(@PathVariable String brandId, @PathVariable Integer processType) {
        return processDefinitionService.deploy(brandId, processType);
    }

    /**
     * 取消部署
     * @param brandId   品牌id
     * @param processType 流程类型
     * @return
     */
    @GetMapping("/cancel/{brandId}/{processType}")
    @ResponseBody
    public HttpResponse cancel(@PathVariable String brandId, @PathVariable Integer processType) {
        return processDefinitionService.cancel(brandId, processType);
    }

    /**
     * 查询流程
     * @param processDefinitionQueryRequest 查询流程参数
     * @param bindingResult 验证信息
     * @return  返回值
     */
    @GetMapping("/query")
    @ResponseBody
    public HttpResponse query(@Valid ProcessDefinitionQueryRequest processDefinitionQueryRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_PARAM_ERROR);
        }
        return processDefinitionService.query(processDefinitionQueryRequest);
    }

    /**
     * 修改流程
     * @param processDefinitionModifyRequest    修改流程参数
     * @param bindingResult 验证信息
     * @return  返回值
     */
    @PutMapping("/modify")
    @ResponseBody
    public HttpResponse modify(@RequestBody @Valid ProcessDefinitionModifyRequest processDefinitionModifyRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_UPDATE_ERROR);
        }
        return processDefinitionService.modify(processDefinitionModifyRequest);
    }

    /**
     * 删除流程
     * @param processId 流程id
     * @return  返回值
     */
    @DeleteMapping("/delete/{processId}")
    @ResponseBody
    public HttpResponse delete(@PathVariable String processId) {
        return processDefinitionService.delete(processId);
    }

    /**
     * 查询流程详情
     * @param processId 流程id
     * @return  返回值
     */
    @GetMapping("/detail/{processId}")
    @ResponseBody
    public HttpResponse detail(@PathVariable String processId) {
        return processDefinitionService.detail(processId);
    }

    /**
     * 根据类型查询流程详情
     * @param processType    类型
     * @return  返回值
     */
    @GetMapping("/detail-by-type/{brandId}/{processType}")
    @ResponseBody
    public HttpResponse detailByType(@PathVariable String brandId, @PathVariable Integer processType) {
        return processDefinitionService.detailByType(brandId, processType);
    }

    /**
     * 查询类型单据是否存在流程部署
     * @param brandId 品牌id
     * @param processType 单据类型
     * @return HttpResponse
     */
    @GetMapping(value = "/exist-deployed/{brandId}/{processType}")
    @ResponseBody
    public HttpResponse existProcessesDeployed(@PathVariable String brandId, @PathVariable Integer processType){
        return processDefinitionService.existProcessesDeployed(brandId, processType);
    }
}

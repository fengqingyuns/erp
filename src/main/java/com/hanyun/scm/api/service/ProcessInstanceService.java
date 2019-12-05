package com.hanyun.scm.api.service;



import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.request.process.instance.*;

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
 * ProcessInstanceService
 * Date: 2017/4/5
 * Time: 下午2:29
 *
 * @author tianye@hanyun.com
 */
public interface ProcessInstanceService {

    /**
     * 创建流程实例
     * @param processInstanceCreateRequest  创建流程实例参数
     * @param userId  登录人id
     * @return  返回值
     */
    HttpResponse create(ProcessInstanceCreateRequest processInstanceCreateRequest, String userId);

    /**
     * 修改流程实例
     * @param processInstanceModifyRequest  修改流程实例参数
     * @return  返回值
     */
    HttpResponse modify(ProcessInstanceModifyRequest processInstanceModifyRequest);

    /**
     * 删除流程实例
     * @param businessId 单据id
     * @return  返回值
     */
    HttpResponse delete(String businessId);

    /**
     * 查询流程实例详情
     * @param businessId 流程实例id
     * @return  返回值
     */
    HttpResponse detail(String businessId);

    /**
     * 查询流程实例id
     * @param businessId 单据id
     * @return String
     */
    String queryProcessInstanceId(String businessId);

    /**
     * 查询流程实例列表
     * @param processInstanceQueryRequest   查询流程实例列表参数
     * @return  返回值
     */
    HttpResponse query(ProcessInstanceQueryRequest processInstanceQueryRequest);

    /**
     * 审核
     * @param processInstanceModifyRequest  审核参数
     * @return  返回值
     */
    HttpResponse audit(ProcessInstanceModifyRequest processInstanceModifyRequest);

    /**
     * 查询审核人
     * @param businessId   参数
     * @return  返回值
     */
    Boolean queryAuditor(String businessId, String userId);

    /**
     * 查询所有审核人
     * @param businessId   参数
     * @return  返回值
     */
    List<String> queryAuditors(String businessId);

    /**
     * 通过订单id查询是否当前登陆人审核和查看历史状态
     * @param request 参数
     * @return HttpResponse
     */
    HttpResponse queryExamineStatus(ExamineStatusQueryRequest request);

    /**
     * 驳回单据
     * @param request 对象
     * @return HttpResponse
     */
    HttpResponse reject(ProcessInstanceRejectRequest request);
}

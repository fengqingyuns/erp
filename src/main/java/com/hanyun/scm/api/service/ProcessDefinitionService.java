package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.request.process.definition.ProcessDefinitionCreateRequest;
import com.hanyun.scm.api.domain.request.process.definition.ProcessDefinitionModifyRequest;
import com.hanyun.scm.api.domain.request.process.definition.ProcessDefinitionQueryRequest;

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
 * ProcessDefinitionService
 * Date: 2017/4/5
 * Time: 下午2:29
 *
 * @author tianye@hanyun.com
 */
public interface ProcessDefinitionService {

    /**
     * 创建流程
     * @param processDefinitionCreateRequest    创建流程参数
     * @param processStatus    流程状态
     * @return  返回值
     * @author tianye@hanyun.com
     */
    HttpResponse create(ProcessDefinitionCreateRequest processDefinitionCreateRequest, Integer processStatus);

    /**
     * 修改流程
     * @param processDefinitionModifyRequest    修改流程参数
     * @return  返回值
     */
    HttpResponse modify(ProcessDefinitionModifyRequest processDefinitionModifyRequest);

    /**
     * 删除流程
     * @param processId 流程id
     * @return  返回值
     */
    HttpResponse delete(String processId);

    /**
     * 查询流程详情
     * @param processId 流程id
     * @return  返回值
     */
    HttpResponse detail(String processId);

    /**
     * 查询流程列表
     * @param processDefinitionQueryRequest 查询流程列表参数
     * @return  返回值
     */
    HttpResponse query(ProcessDefinitionQueryRequest processDefinitionQueryRequest);

    /**
     * 部署流程
     * @param brandId   品牌id
     * @param processType 流程类型
     * @return  返回值
     */
    HttpResponse deploy(String brandId, Integer processType);

    /**
     * 取消部署
     * @param brandId   品牌id
     * @param processType   流程类型
     * @return  返回值
     */
    HttpResponse cancel(String brandId, Integer processType);

    /**
     * 根据类型查询流程详情
     * @param processType   类型
     * @return  返回值
     */
    HttpResponse detailByType(String brandId, Integer processType);

    /**
     * 是否存在已部署流程
     * @param brandId   品牌id
     * @param processType   流程类型
     * @return
     */
    Boolean exist(String brandId, Integer processType);

    /**
     * 查询类型单据是否存在流程部署
     * @param brandId 品牌id
     * @param processType 单据类型
     * @return HttpResponse
     */
    HttpResponse existProcessesDeployed(String brandId, Integer processType);
}

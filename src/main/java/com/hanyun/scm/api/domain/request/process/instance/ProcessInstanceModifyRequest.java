package com.hanyun.scm.api.domain.request.process.instance;

import com.hanyun.scm.api.domain.ProcessInstance;
import org.hibernate.validator.constraints.NotEmpty;

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
 * ProcessInstanceModifyRequest
 * Date: 2017/4/5
 * Time: 下午2:40
 *
 * @author tianye@hanyun.com
 */
public class ProcessInstanceModifyRequest extends ProcessInstance {

    private String processInstanceId;

    @NotEmpty
    private String userId;  //操作人id

    private String remark;  //备注信息

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    @Override
    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ProcessInstanceModifyRequest(){}

    public ProcessInstanceModifyRequest(ProcessInstance processInstance) {
        this.setBrandId(processInstance.getBrandId());
        this.setProcessInstanceId(processInstance.getProcessInstanceId());
        this.setActivitiProcessInstanceId(processInstance.getActivitiProcessInstanceId());
        this.setProcessId(processInstance.getProcessId());
        this.setActivitiProcessId(processInstance.getActivitiProcessId());
        this.setInstanceStatus(processInstance.getInstanceStatus());
        this.setProcessType(processInstance.getProcessType());
        this.setCurrentStep(processInstance.getCurrentStep());
    }
}

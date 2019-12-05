package com.hanyun.scm.api.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hanyun.ground.util.http.HttpClient;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.service.ProcessInstanceService;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.UserTask;
import org.activiti.rest.common.api.DataResponse;
import org.activiti.rest.service.api.repository.ProcessDefinitionRequest;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
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
 * ActivitiUtil
 * Date: 2017/4/6
 * Time: 上午11:03
 *
 * @author tianye@hanyun.com
 */
public class ActivitiUtil {
    private volatile static ActivitiUtil instance = null;
    private static final String URL = "http://activiti.api.hanyun.com/service";
    private String userName;
    private String password;
    private static final String PROCESS_DEFINITION_URL = URL + "/repository/process-definitions";
    private static final String PROCESS_INSTANCE_URL = URL + "/runtime/process-instances";
    private static final String TASK_URL = URL + "/runtime/tasks";

    private ActivitiUtil() {
    }

    public static ActivitiUtil getInstance() {
        if (instance == null) {
            synchronized (ActivitiUtil.class) {
                if (instance == null) {
                    instance = new ActivitiUtil();
                    instance.userName = Consts.USER_NAME;
                    instance.password = Consts.PASS_WORD;
                }
            }
        }
        return instance;
    }

    public static ActivitiUtil config(String username, String password) {
        ActivitiUtil activitiUtil = ActivitiUtil.getInstance();
        if (StringUtils.isNotBlank(username)) {
            activitiUtil.userName = username;
        }
        if (StringUtils.isNotBlank(password)) {
            activitiUtil.password = password;
        }
        return activitiUtil;
    }




    public static void createProcess(ProcessDefinitionRequest processDefinitionRequest) {
        String result = HttpClient.post(PROCESS_DEFINITION_URL).json(JsonUtil.toJson(processDefinitionRequest)).authentication(instance.userName, instance.password).action().result();
        System.err.println("创建流程返回数据为:");
        System.err.println(result);
    }

    public static ProcessInstanceResponse createProcessInstance(String processDefinitionKey, String businessKey) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("processDefinitionKey", processDefinitionKey);
        jsonObject.put("businessKey", businessKey);
        String result = HttpClient.post(PROCESS_INSTANCE_URL).json(jsonObject).authentication(getInstance().userName, getInstance().password).action().result();
        return JsonUtil.fromJson(result, ProcessInstanceResponse.class);

    }

    public static List<Object> queryTasks(String processInstanceId, String assignee, String candidateUser, String candidateGroup) {
        HttpClient httpClient = HttpClient.get(TASK_URL);
        if (!StringUtils.isEmpty(assignee)) {
            httpClient = httpClient.addParameter("assignee", assignee);
        }
        if (!StringUtils.isEmpty(candidateUser)) {
            httpClient = httpClient.addParameter("candidateUser", candidateUser);
        }
        if (!StringUtils.isEmpty(candidateGroup)) {
            httpClient = httpClient.addParameter("candidateGroup", candidateGroup);
        }
        if (!StringUtils.isEmpty(processInstanceId)) {
            httpClient = httpClient.addParameter("processInstanceId", processInstanceId);
        }
        httpClient = httpClient.addParameter("size", 999999 + "");
        String result = httpClient.authentication(instance.userName, instance.password).action().result();
        DataResponse dataResponse = JsonUtil.fromJson(result, DataResponse.class);
        if (dataResponse == null || dataResponse.getSize() == 0) {
            return null;
        }
        System.err.println(result);
        return JsonUtil.fromJson(JsonUtil.toJson(dataResponse.getData()), new TypeReference<List<Object>>() {
        });
    }

    /**
     * 完成任务
     */
    public static void complete(String taskId) throws Exception {
        JSONObject params = new JSONObject();
        params.put("action", "complete");
        String result = HttpClient.post(TASK_URL + "/" + taskId).json(params).authentication(instance.userName, instance.password).action().result();
        System.out.println("完成任务返回信息为:");
        System.err.println(result);
    }


    public static UserTask createUserTask(String id, String name, String assignee, List<String> users, List<String> groups) {
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
        if (assignee != null) {
            userTask.setAssignee(assignee);
        }
        if (users != null && users.size() > 0) {
            userTask.setCandidateUsers(users);
        }
        if (groups != null && groups.size() > 0) {
            userTask.setCandidateGroups(groups);
        }
        MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = new MultiInstanceLoopCharacteristics();
        multiInstanceLoopCharacteristics.setSequential(false);
        multiInstanceLoopCharacteristics.setLoopCardinality("assigneeList");
        multiInstanceLoopCharacteristics.setAttributes(new HashMap<String, List<ExtensionAttribute>>() {{

        }});
        return userTask;
    }

    public static SequenceFlow createSequenceFlow(String from, String to) {
        SequenceFlow flow = new SequenceFlow();
        flow.setSourceRef(from);
        flow.setTargetRef(to);
        return flow;
    }

    /**
     * 删除流程实例部署
     *
     * @param businessId             单据id
     * @param processInstanceService service
     * @throws Exception 异常
     */
    public static void deleteProcessInstance(String businessId, ProcessInstanceService processInstanceService) throws Exception {
        try {
            String processInstanceId = processInstanceService.queryProcessInstanceId(businessId);
            if (!StringUtils.isEmpty(processInstanceId)) {
                HttpClient httpClient = HttpClient.delete(PROCESS_INSTANCE_URL + "/" + processInstanceId);
                httpClient.authentication(getInstance().userName, getInstance().password).action();
            }
        } catch (Exception e) {
            throw new Exception("调用activity删除流程实例接口失败", e);
        }
    }
}

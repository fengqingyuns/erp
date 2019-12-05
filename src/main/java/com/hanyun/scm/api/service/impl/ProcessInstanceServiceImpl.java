package com.hanyun.scm.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hanyun.ground.util.http.HttpClient;
import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.*;
import com.hanyun.scm.api.domain.*;
import com.hanyun.scm.api.domain.dto.OrderDataDTO;
import com.hanyun.scm.api.domain.dto.ProcessStatusDTO;
import com.hanyun.scm.api.domain.request.process.instance.*;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.domain.response.instance.ExamineStatus;
import com.hanyun.scm.api.exception.ProcessInstanceException;
import com.hanyun.scm.api.service.ProcessInstanceService;
import com.hanyun.scm.api.utils.ActivitiUtil;
import com.hanyun.scm.api.utils.PropertiesUtil;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

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
 * ProcessInstanceServiceImpl
 * Date: 2017/4/5
 * Time: 下午2:36
 *
 * @author tianye@hanyun.com
 */
@Service
public class ProcessInstanceServiceImpl implements ProcessInstanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessInstanceServiceImpl.class);

    @Resource
    private ProcessInstanceDao processInstanceDao;

    @Resource
    private ProcessInstanceDetailDao processInstanceDetailDao;

    @Resource
    private ProcessDefinitionDao processDefinitionDao;

    @Resource
    private ProcessDefinitionDetailDao processDefinitionDetailDao;

    @Resource
    private ProcessInstanceDetailAuditorsDao processInstanceDetailAuditorsDao;

    @Resource
    private ProcessDefinitionDetailAuditorsDao processDefinitionDetailAuditorsDao;

    @Resource
    private ProcessInstanceService processInstanceService;

    @Override
    public HttpResponse create(ProcessInstanceCreateRequest processInstanceCreateRequest, String userId) {
        try {
            ProcessDefinition processDefinition = processDefinitionDao.selectByProcessType(processInstanceCreateRequest.getBrandId(), processInstanceCreateRequest.getProcessType());
            ProcessDefinitionDetail query = new ProcessDefinitionDetail();
            query.setProcessId(processDefinition.getProcessId());
            String processInstanceId = IdUtil.uuid();
            boolean superStatus = false;
            ProcessInstanceResponse processInstanceResponse;
            ActivitiUtil.config(userId, userId);
            String activitiProcessId;
            if (processDefinition.getSuperStatus() != null && processDefinition.getSuperStatus() == 1 && processInstanceCreateRequest.getPrice() != null
                    && processDefinition.getCashTopLimit() != null && processInstanceCreateRequest.getPrice() > processDefinition.getCashTopLimit()) {
                processInstanceResponse = ActivitiUtil.createProcessInstance(processDefinition.getActivitiSuperProcessId(), "_" + processInstanceId);
                activitiProcessId = processDefinition.getActivitiSuperProcessId();
                superStatus = true;
            } else {
                processInstanceResponse = ActivitiUtil.createProcessInstance(processDefinition.getActivitiProcessId(), "_" + processInstanceId);
                activitiProcessId = processDefinition.getActivitiProcessId();

            }
            LOGGER.warn("创建流程的数据  processDefinitionKey:{}  processInstanceId:{} ActivitiProcessInstanceId:{}",processDefinition.getActivitiSuperProcessId(),
                    processInstanceId, processInstanceResponse.getId());
            processInstanceCreateRequest.setProcessInstanceId(processInstanceId);
            processInstanceCreateRequest.setActivitiProcessInstanceId(processInstanceResponse.getId());
            processInstanceCreateRequest.setCurrentStep(0);
            processInstanceCreateRequest.setInstanceStatus(Consts.PROCESS_INSTANCE_STATUS_COMMITED);
            processInstanceCreateRequest.setBrandId(processDefinition.getBrandId());
            processInstanceCreateRequest.setProcessType(processDefinition.getProcessType());
            processInstanceCreateRequest.setProcessId(processDefinition.getProcessId());
            processInstanceCreateRequest.setActivitiProcessId(activitiProcessId);
            int insertRow = processInstanceDao.insertSelective(processInstanceCreateRequest);
            if (insertRow == 0) {
                return HttpResponse.failure(ResultCode.PROCESS_INSTANCE_CREATE_ERROR);
            }
            int detailRow;
            String processInstanceDetailId;
            ProcessInstanceDetail processInstanceDetail;
            List<ProcessDefinitionDetail> processDefinitionDetailList = processDefinitionDetailDao.selectSelective(query);
            int i = 0;
            ProcessDefinitionDetailAuditors auditorQuery;
            for (ProcessDefinitionDetail processDefinitionDetail : processDefinitionDetailList) {
                if (i == 0) {
                    i++;
                    continue;
                }
                i++;
                processInstanceDetail = new ProcessInstanceDetail();
                processInstanceDetailId = IdUtil.uuid();
                processInstanceDetail.setProcessInstanceDetailId(processInstanceDetailId);
                processInstanceDetail.setProcessInstanceId(processInstanceId);
                processInstanceDetail.setBrandId(processDefinition.getBrandId());
                processInstanceDetail.setAuditorType(processDefinitionDetail.getAuditorType());
                processInstanceDetail.setSortNo(processDefinitionDetail.getSortNo());
                processInstanceDetail.setActivitiProcessInstanceId(processInstanceCreateRequest.getActivitiProcessInstanceId());
                processInstanceDetail.setAuditStatus(Consts.PROCESS_INSTANCE_DETAIL_STATUS_UNCONFIRMED);
                String auditorIds = "";
                String auditorNames = "";
                if (superStatus) {
                    processInstanceDetail.setAuditorId(processDefinition.getSuperAuditorId());
                    processInstanceDetail.setAuditorName(processDefinition.getSuperAuditorName());
                    detailRow = processInstanceDetailDao.insertSelective(processInstanceDetail);
                    if (detailRow == 0) {
                        return HttpResponse.failure(ResultCode.PROCESS_INSTANCE_CREATE_ERROR);
                    }
                    ProcessInstanceDetailAuditors processInstanceDetailAuditors = new ProcessInstanceDetailAuditors();
                    processInstanceDetailAuditors.setActivitiProcessInstanceId(processInstanceDetail.getActivitiProcessInstanceId());
                    processInstanceDetailAuditors.setProcessInstanceDetailId(processInstanceDetail.getProcessInstanceDetailId());
                    processInstanceDetailAuditors.setProcessInstanceId(processInstanceDetail.getProcessInstanceId());
                    processInstanceDetailAuditors.setAuditorType(processInstanceDetail.getAuditorType());
                    processInstanceDetailAuditors.setAuditorId(processDefinition.getSuperAuditorId());
                    processInstanceDetailAuditors.setAuditorName(processDefinition.getSuperAuditorName());
                    processInstanceDetailAuditors.setBrandId(processInstanceDetail.getBrandId());
                    processInstanceDetailAuditors.setSortNo(processInstanceDetail.getSortNo());
                    processInstanceDetailAuditorsDao.insertSelective(processInstanceDetailAuditors);
                    break;
                } else {
                    int seq = 0;
                    auditorQuery = new ProcessDefinitionDetailAuditors();
                    auditorQuery.setProcessDetailId(processDefinitionDetail.getProcessDetailId());
                    for (ProcessDefinitionDetailAuditors processDefinitionDetailAuditors : processDefinitionDetailAuditorsDao.selectSelective(auditorQuery)) {
                        ProcessInstanceDetailAuditors processInstanceDetailAuditors = JsonUtil.fromJson(JsonUtil.toJson(processDefinitionDetailAuditors), ProcessInstanceDetailAuditors.class);
                        processInstanceDetailAuditors.setActivitiProcessInstanceId(processInstanceDetail.getActivitiProcessInstanceId());
                        processInstanceDetailAuditors.setProcessInstanceDetailId(processInstanceDetail.getProcessInstanceDetailId());
                        processInstanceDetailAuditors.setProcessInstanceId(processInstanceDetail.getProcessInstanceId());
                        processInstanceDetailAuditors.setBrandId(processInstanceDetail.getBrandId());
                        processInstanceDetailAuditors.setSortNo(processInstanceDetail.getSortNo());
                        processInstanceDetailAuditorsDao.insertSelective(processInstanceDetailAuditors);
                        if (seq == 0) {
                            auditorIds += processDefinitionDetailAuditors.getAuditorId();
                            auditorNames += processDefinitionDetailAuditors.getAuditorName();
                        } else {
                            auditorIds += "," + processDefinitionDetailAuditors.getAuditorId();
                            auditorNames += "," + processDefinitionDetailAuditors.getAuditorName();
                        }
                        seq++;
                    }
                    processInstanceDetail.setAuditorId(auditorIds);
                    processInstanceDetail.setAuditorName(auditorNames);
                    detailRow = processInstanceDetailDao.insertSelective(processInstanceDetail);
                    if (detailRow == 0) {
                        return HttpResponse.failure(ResultCode.PROCESS_INSTANCE_CREATE_ERROR);
                    }
                }
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("创建流程失败!", e);
            return HttpResponse.failure(ResultCode.PROCESS_INSTANCE_CREATE_ERROR);
        }
    }

    @Override
    public HttpResponse modify(ProcessInstanceModifyRequest processInstanceModifyRequest) {
        try {
            ProcessInstance old = processInstanceDao.selectByProcessInstanceId(processInstanceModifyRequest.getProcessInstanceId());
            if (old == null) {
                return HttpResponse.failure(ResultCode.PROCESS_INSTANCE_UPDATE_ERROR);
            }
            int updateRow = processInstanceDao.updateByProcessInstanceIdSelective(processInstanceModifyRequest);
            if (updateRow == 0) {
                return HttpResponse.failure(ResultCode.PROCESS_INSTANCE_UPDATE_ERROR);
            }
            processInstanceDetailDao.deleteByProcessInstanceId(processInstanceModifyRequest.getProcessInstanceId());
            List<ProcessInstanceDetail> processInstanceDetailList = processInstanceModifyRequest.getProcessInstanceDetailList();
            for (ProcessInstanceDetail modify : processInstanceDetailList) {
                modify.setProcessInstanceId(processInstanceModifyRequest.getProcessInstanceId());
                modify.setProcessDetailId(IdUtil.uuid());
                int row = processInstanceDetailDao.insertSelective(modify);
                if (row == 0) {
                    return HttpResponse.failure(ResultCode.PROCESS_INSTANCE_UPDATE_ERROR);
                }
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("修改流程失败!", e);
            return HttpResponse.failure(ResultCode.PROCESS_INSTANCE_UPDATE_ERROR);
        }
    }

    @Override
    public HttpResponse delete(String businessId) {
        try {
            ProcessInstance old = processInstanceDao.selectByBusinessId(businessId);
            if (old == null) {
                return HttpResponse.failure(ResultCode.PROCESS_INSTANCE_DELETE_ERROR);
            }
            int row = processInstanceDao.deleteByProcessInstanceId(old.getProcessInstanceId());
            if (row == 0) {
                return HttpResponse.failure(ResultCode.PROCESS_INSTANCE_DELETE_ERROR);
            }
            processInstanceDetailDao.deleteByProcessInstanceId(old.getProcessInstanceId());
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("删除流程失败!", e);
            return HttpResponse.failure(ResultCode.PROCESS_INSTANCE_DELETE_ERROR);
        }
    }

    @Override
    public HttpResponse detail(String businessId) {
        try {
            ProcessInstance processInstance = processInstanceDao.selectByBusinessId(businessId);
            if (processInstance == null) {
                return HttpResponse.failure(ResultCode.PROCESS_INSTANCE_IS_NOT_ERROR);
            }
            ProcessInstanceDetail query = new ProcessInstanceDetail();
            query.setProcessInstanceId(processInstance.getProcessInstanceId());
            List<ProcessInstanceDetail> processInstanceDetailList = processInstanceDetailDao.selectSelective(query);
            processInstance.setProcessInstanceDetailList(processInstanceDetailList);
            return HttpResponse.success(processInstance);
        } catch (Exception e) {
            LOGGER.error("查询流程详情失败!", e);
            return HttpResponse.failure(ResultCode.PROCESS_INSTANCE_DETAIL_ERROR);
        }
    }

    @Override
    public String queryProcessInstanceId(String businessId) {
        ProcessInstance processInstance = processInstanceDao.selectByBusinessId(businessId);
        if (processInstance == null || StringUtils.isEmpty(processInstance.getActivitiProcessInstanceId())) {
            return null;
        }
        return processInstance.getActivitiProcessInstanceId();
    }

    @Override
    public HttpResponse query(ProcessInstanceQueryRequest processInstanceQueryRequest) {
        try {
            List<ProcessInstance> processInstanceList = processInstanceDao.selectSelective(processInstanceQueryRequest);
            BaseResponse baseResponse = new BaseResponse(processInstanceList.size(), processInstanceList);
            return HttpResponse.success(baseResponse);
        } catch (Exception e) {
            LOGGER.error("查询流程列表失败!", e);
            return HttpResponse.failure(ResultCode.PROCESS_INSTANCE_QUERY_ERROR);
        }
    }

    @Override
    public HttpResponse audit(ProcessInstanceModifyRequest processInstanceModifyRequest) {
        try {
            String userId = processInstanceModifyRequest.getUserId();
            String remark = processInstanceModifyRequest.getRemark();
            ProcessInstance processInstance = processInstanceDao.selectByBusinessId(processInstanceModifyRequest.getBusinessId());
            if (processInstance == null || processInstance.getInstanceStatus() == Consts.PROCESS_INSTANCE_STATUS_CONFIRMED) {
                return HttpResponse.failure(ResultCode.PROCESS_INSTANCE_AUDIT_ERROR);
            }
            processInstanceModifyRequest = new ProcessInstanceModifyRequest(processInstance);
            processInstanceModifyRequest.setUserId(userId);
            processInstanceModifyRequest.setRemark(remark);
            int currentStep = processInstance.getCurrentStep();
            currentStep++;
            processInstanceModifyRequest.setCurrentStep(currentStep);
            ProcessInstanceDetail query = new ProcessInstanceDetail();
            query.setProcessInstanceId(processInstanceModifyRequest.getProcessInstanceId());
            List<ProcessInstanceDetail> processInstanceDetailList = processInstanceDetailDao.selectSelective(query);
            ProcessInstanceDetail currentNode = processInstanceDetailList.get(currentStep - 1);
            if (currentStep == processInstanceDetailList.size()) {
                processInstanceModifyRequest.setInstanceStatus(Consts.PROCESS_INSTANCE_STATUS_CONFIRMED);
            } else {
                processInstanceModifyRequest.setInstanceStatus(Consts.PROCESS_INSTANCE_STATUS_CONFIRMING);
            }

            Map<String, String> userMap = new HashMap<>();
            ProcessInstanceDetailAuditors auditorQuery = new ProcessInstanceDetailAuditors();
            auditorQuery.setProcessInstanceDetailId(currentNode.getProcessInstanceDetailId());
            Map<String, String> auditorNames = new HashMap<>();
            for (ProcessInstanceDetailAuditors processInstanceDetailAuditors : processInstanceDetailAuditorsDao.selectSelective(auditorQuery)) {
                userMap.put(processInstanceDetailAuditors.getAuditorId(), processInstanceDetailAuditors.getAuditorName());
                if(processInstanceDetailAuditors.getAuditorType() == Consts.AUDIT_TYPE_ROLES || processInstanceDetailAuditors.getAuditorType() == Consts.AUDIT_TYPE_ORGANIZATION) {
                    String auditorId = processInstanceDetailAuditors.getAuditorId();
                    Properties properties = PropertiesUtil.getProperties("erp-api.properties");
                    String queryRoleEmployeeUrl = properties.getProperty("queryRoleEmployeeUrl") + "/" + auditorId;
                    String result = HttpClient.get(queryRoleEmployeeUrl).action().result();
                    HttpResponse response = JsonUtil.fromJson(result, HttpResponse.class);
                    if (!StringUtils.equals(response.getCode(), "0") || response.getData() == null) {
                        continue;
                    }
                    try {
                        List<BrandEmployee> brandEmployeeList = JsonUtil.fromJson(JsonUtil.toJson(response.getData()), new TypeReference<List<BrandEmployee>>() {
                        });
                        for (BrandEmployee brandEmployee : brandEmployeeList) {
                            auditorNames.put(brandEmployee.getEmployeeId(), brandEmployee.getName());
                        }
                    } catch (Exception e) {
                        LOGGER.error("查询角色用户失败!", e);
                    }
                }
            }
            currentNode.setAuditTime(new Date());
            currentNode.setAuditStatus(Consts.PROCESS_INSTANCE_DETAIL_STATUS_CONFIRMED);
            currentNode.setAuditorId(userId);
            currentNode.setAuditorName(userMap.get(userId)==null?auditorNames.get(userId):userMap.get(userId));
            currentNode.setRemark(processInstanceModifyRequest.getRemark());
            processInstanceDetailDao.updateByDetailIdSelective(currentNode);
            ActivitiUtil.getInstance();
            List<Object> taskResponseList;
            taskResponseList = ActivitiUtil.queryTasks(processInstance.getActivitiProcessInstanceId(), null, userId, null);
            if (taskResponseList == null || taskResponseList.size() == 0) {
                throw new ProcessInstanceException("查询审批任务失败!");
            }
            JSONObject jsonObject = JsonUtil.fromJson(JsonUtil.toJson(taskResponseList.get(0)), JSONObject.class);
            String taskId = jsonObject.getString("id");
            ActivitiUtil.complete(taskId);
            processInstanceDao.updateByProcessInstanceIdSelective(processInstanceModifyRequest);
            return HttpResponse.success(processInstanceModifyRequest.getInstanceStatus());
        } catch (Exception e) {
            LOGGER.error("审核失败!", e);
            return HttpResponse.failure(ResultCode.PROCESS_INSTANCE_UPDATE_ERROR);
        }
    }

    @Override
    public Boolean queryAuditor(String businessId, String userId) {
        try {
            ProcessInstance processInstance = processInstanceDao.selectByBusinessId(businessId);
            if (processInstance == null) {
                return false;
            }
            if (processInstance.getInstanceStatus() == Consts.PROCESS_INSTANCE_STATUS_CONFIRMED) {
                return false;
            }
            List<Object> taskResponseList = ActivitiUtil.queryTasks(processInstance.getActivitiProcessInstanceId(), null, userId, null);
            if (taskResponseList == null || taskResponseList.size() == 0) {
                throw new ProcessInstanceException("查询审批任务失败!");
            }
            JSONObject jsonObject = JsonUtil.fromJson(JsonUtil.toJson(taskResponseList.get(0)), JSONObject.class);
            String taskId = jsonObject.getString("id");
            return !StringUtils.isEmpty(taskId);
        } catch (Exception e) {
            LOGGER.warn("这个人不是审批人!", e);
            return false;
        }
    }

    @Override
    public List<String> queryAuditors(String businessId) {
        try {
            ProcessInstance processInstance = processInstanceDao.selectByBusinessId(businessId);
            if (processInstance == null) {
                return new ArrayList<>();
            }
            List<String> auditorList = new ArrayList<>();
            List<String> roleList = new ArrayList<>();
            List<String> organizationList = new ArrayList<>();
            List<ProcessInstanceDetailAuditors> auditors = processInstanceDetailAuditorsDao.selectByProcessInstanceId(processInstance.getProcessInstanceId());
            for (ProcessInstanceDetailAuditors processInstanceDetailAuditor: auditors) {
                String auditorId = processInstanceDetailAuditor.getAuditorId();
                if (processInstanceDetailAuditor.getAuditorType() == Consts.AUDIT_TYPE_USERS && !auditorList.contains(auditorId)) {
                    auditorList.add(processInstanceDetailAuditor.getAuditorId());
                } else if (processInstanceDetailAuditor.getAuditorType() == Consts.AUDIT_TYPE_ROLES && !roleList.contains(auditorId)) {
                    Properties properties = PropertiesUtil.getProperties("erp-api.properties");
                    String queryRoleEmployeeUrl = properties.getProperty("queryRoleEmployeeUrl") + "/" + auditorId;
                    String result = HttpClient.get(queryRoleEmployeeUrl).action().result();
                    HttpResponse response = JsonUtil.fromJson(result, HttpResponse.class);
                    if (!StringUtils.equals(response.getCode(), "0") || response.getData() == null) {
                        continue;
                    }
                    List<BrandEmployee> brandEmployeeList;
                    try {
                        brandEmployeeList = JsonUtil.fromJson(JsonUtil.toJson(response.getData()), new TypeReference<List<BrandEmployee>>() {
                        });
                    } catch (Exception e) {
                        LOGGER.error("查询角色用户失败!", e);
                        continue;
                    }
                    for (BrandEmployee employee: brandEmployeeList) {
                        auditorList.add(employee.getEmployeeId());
                    }
                    roleList.add(auditorId);
                } else if (processInstanceDetailAuditor.getAuditorType() == Consts.AUDIT_TYPE_ORGANIZATION && !organizationList.contains(auditorId)) {
                    organizationList.add(auditorId);
                }
            }
            return auditorList;
        } catch (Exception e) {
            LOGGER.error("查询所有审批人失败!", e);
            return new ArrayList<>();
        }
    }

    @Override
    public HttpResponse queryExamineStatus(ExamineStatusQueryRequest request) {
        try {
            Map<String, ExamineStatus> statusMap = new HashMap<>();
            String userId = request.getUserId();
            List<OrderDataDTO> orderData = request.getOrderData();
            if(orderData == null || orderData.size() == 0){
                LOGGER.error("查询状态订单id不能为空");
                return HttpResponse.failure(ResultCode.PROCESS_STATUS_EXAMINE_IDS_IS_NOT_NULL_ERROR);
            }
            orderData.forEach(order -> {
                String orderId = order.getOrderId();
                //审核状态
                Boolean auditStatus = queryAuditor(orderId, userId);

                List<String> auditors = queryAuditors(orderId);
                auditors.add(order.getCreateUserId());
                //历史状态
                Boolean historyStatus = auditors.size() > 1 && auditors.contains(userId);

                statusMap.put(orderId, new ExamineStatus(auditStatus,historyStatus));
            });

            return HttpResponse.success(new ProcessStatusDTO(statusMap));
        } catch (Exception e){
            LOGGER.error("查询审批状态失败", e);
            return HttpResponse.failure(ResultCode.PROCESS_INSTANCE_STATUS_EXAMINE_QUERY_ERROR);
        }
    }

    @Override
    public HttpResponse reject(ProcessInstanceRejectRequest request) {
        try {
            String businessId = request.getBusinessId();
            ProcessInstance processInstance = processInstanceDao.selectByBusinessId(businessId);
            if(processInstance == null || processInstance.getInstanceStatus() == Consts.PROCESS_INSTANCE_STATUS_CONFIRMED){
                LOGGER.error("未查到实例数据或当前状态实例的已经审核结束");
                return HttpResponse.failure(ResultCode.PROCESS_INSTANCE_IS_NOT_ERROR);
            }
            int currentStep = processInstance.getCurrentStep();
            currentStep++;

            //查询详情处理当前审批人
            ProcessInstanceDetail query = new ProcessInstanceDetail();
            query.setProcessInstanceId(processInstance.getProcessInstanceId());
            List<ProcessInstanceDetail> processInstanceDetailList = processInstanceDetailDao.selectSelective(query);

            ProcessInstanceDetail currentNode = processInstanceDetailList.get(currentStep-1);

            currentNode.setProcessInstanceDetailId(currentNode.getProcessInstanceDetailId());
            currentNode.setRemark(request.getRemark());
            currentNode.setAuditStatus(Consts.PROCESS_INSTANCE_STATUS_REJECT);
            currentNode.setAuditTime(new Date());
            currentNode.setAuditorId(request.getAuditId());
            currentNode.setAuditorName(request.getAuditName());
            //更新审批状态 和当前审批人的状态
            ProcessInstanceModifyRequest modifyRequest = new ProcessInstanceModifyRequest();
            modifyRequest.setProcessInstanceId(processInstance.getProcessInstanceId());
            modifyRequest.setInstanceStatus(Consts.PROCESS_INSTANCE_STATUS_REJECT);
            modifyRequest.setCurrentStep(currentStep);
            int row = processInstanceDao.updateByProcessInstanceIdSelective(modifyRequest);
            int detailRow = processInstanceDetailDao.updateByDetailIdSelective(currentNode);

            if(row <= 0 || detailRow <= 0){
                LOGGER.error("更新审批实例和审批详情的当前人节点状态和意见失败");
                return HttpResponse.failure(ResultCode.PROCESS_INSTANCE_REJECT_ERROR);
            }

            //删除activiti的流程部署
            ActivitiUtil.deleteProcessInstance(businessId, processInstanceService);

            return HttpResponse.success();
        } catch (Exception e){
            LOGGER.error("审批实例-驳回单据失败");
            return HttpResponse.failure(ResultCode.PROCESS_INSTANCE_REJECT_ERROR);
        }
    }
}

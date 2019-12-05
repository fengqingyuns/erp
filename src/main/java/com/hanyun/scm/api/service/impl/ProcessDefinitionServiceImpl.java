package com.hanyun.scm.api.service.impl;

import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.ProcessDefinitionDao;
import com.hanyun.scm.api.dao.ProcessDefinitionDetailAuditorsDao;
import com.hanyun.scm.api.dao.ProcessDefinitionDetailDao;
import com.hanyun.scm.api.domain.ProcessDefinition;
import com.hanyun.scm.api.domain.ProcessDefinitionDetail;
import com.hanyun.scm.api.domain.ProcessDefinitionDetailAuditors;
import com.hanyun.scm.api.domain.request.process.definition.ProcessDefinitionCreateRequest;
import com.hanyun.scm.api.domain.request.process.definition.ProcessDefinitionModifyRequest;
import com.hanyun.scm.api.domain.request.process.definition.ProcessDefinitionQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.service.ProcessDefinitionService;
import com.hanyun.scm.api.utils.ActivitiUtil;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.UserTask;
import org.activiti.rest.service.api.repository.ProcessDefinitionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
 * ProcessDefinitionServiceImpl
 * Date: 2017/4/5
 * Time: 下午2:33
 *
 * @author tianye@hanyun.com
 */
@Service
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDefinitionServiceImpl.class);

    @Resource
    private ProcessDefinitionDao processDefinitionDao;

    @Resource
    private ProcessDefinitionDetailDao processDefinitionDetailDao;

    @Resource
    private ProcessDefinitionDetailAuditorsDao processDefinitionDetailAuditorsDao;

    @Override
    public HttpResponse create(ProcessDefinitionCreateRequest processDefinitionCreateRequest, Integer processStatus) {
        String processId = IdUtil.uuid();
        try {
            String activitiSuperProcessId = "_" + IdUtil.uuid();
            processDefinitionCreateRequest.setProcessId(processId);
            processDefinitionCreateRequest.setActivitiProcessId("_" + processId);
            processDefinitionCreateRequest.setActivitiSuperProcessId(activitiSuperProcessId);
            if (processStatus != null) {
                processDefinitionCreateRequest.setProcessStatus(processStatus);
            }
            int insertRow = processDefinitionDao.insertSelective(processDefinitionCreateRequest);
            if (insertRow == 0) {
                return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_CREATE_ERROR);
            }
            ActivitiUtil.config(processDefinitionCreateRequest.getUserId(), processDefinitionCreateRequest.getUserId());
            ProcessDefinitionRequest processDefinitionRequest = new ProcessDefinitionRequest();
            String processDetailId;
            int detailRow;
            List<ProcessDefinitionDetail> processDefinitionDetailList = processDefinitionCreateRequest.getProcessDefinitionDetailList();
            if (processDefinitionDetailList == null) {
                processDefinitionDao.deleteByProcessId(processId);
                return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_CREATE_ERROR);
            }
            int size = processDefinitionDetailList.size();
            //只有一个节点,则只是初始节点,没有审批节点
            if (size < 1) {
                processDefinitionDao.deleteByProcessId(processId);
                return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_CREATE_ERROR);
            }
            if (processDefinitionCreateRequest.getSuperStatus() != null && processDefinitionCreateRequest.getSuperStatus() == 1) {
                List<UserTask> nodes = new ArrayList<>();
                List<SequenceFlow> flows = new ArrayList<>();
                nodes.add(ActivitiUtil.createUserTask("_1", processDefinitionCreateRequest.getSuperAuditorName(), null, new ArrayList<String>(){{add(processDefinitionCreateRequest.getSuperAuditorId());}}, null));
                processDefinitionRequest.setNodes(nodes);
                processDefinitionRequest.setFlows(flows);
                processDefinitionRequest.setBeginId("_1");
                processDefinitionRequest.setEndId("_1");
                processDefinitionRequest.setProcessId(processDefinitionCreateRequest.getActivitiSuperProcessId());
                ActivitiUtil.createProcess(processDefinitionRequest);
            }
            List<UserTask> nodes = new ArrayList<>();
            List<SequenceFlow> flows = new ArrayList<>();
            for (int i = 0; i < processDefinitionDetailList.size(); i++) {
                ProcessDefinitionDetail detail = processDefinitionDetailList.get(i);
                processDetailId = IdUtil.uuid();
                detail.setProcessDetailId(processDetailId);
                detail.setProcessId(processId);
                detail.setSortNo(i);
                //第一个节点为初始节点,不处理
                if (i == 0) {
                    detail.setSortNo(-1);
                    detailRow = processDefinitionDetailDao.insertSelective(detail);
                    if (detailRow == 0) {
                        processDefinitionDao.deleteByProcessId(processId);
                        return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_CREATE_ERROR);
                    }
                    continue;
                }
                List<ProcessDefinitionDetailAuditors> auditorList = detail.getAuditors();
                List<String> users = new ArrayList<>();
                List<String> groups = new ArrayList<>();
                for (ProcessDefinitionDetailAuditors auditor : auditorList) {
                    auditor.setSortNo(detail.getSortNo());
                    auditor.setBrandId(detail.getBrandId());
                    auditor.setProcessId(detail.getProcessId());
                    auditor.setEditStatus(detail.getEditStatus());
                    auditor.setProcessDetailId(detail.getProcessDetailId());
                    processDefinitionDetailAuditorsDao.insertSelective(auditor);
                    if (auditor.getAuditorType() == Consts.AUDIT_TYPE_USERS && !users.contains(auditor.getAuditorId())) {
                        users.add(auditor.getAuditorId());
                    } else if ((auditor.getAuditorType() == Consts.AUDIT_TYPE_ROLES || detail.getAuditorType() == Consts.AUDIT_TYPE_ORGANIZATION) && !groups.contains(auditor.getAuditorId())) {
                        groups.add(auditor.getAuditorId());
                    }
                    if (users.size() == 0 && groups.size() == 0) {
                        return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_CREATE_ERROR);
                    }
                }
                nodes.add(ActivitiUtil.createUserTask("_" + String.valueOf(i), detail.getAuditorName(), null, users.size()>0?users:null, groups.size()>0?groups:null));
                if (i > 1) {
                    flows.add(ActivitiUtil.createSequenceFlow("_" + String.valueOf(i - 1), "_" + String.valueOf(i)));
                }
                detailRow = processDefinitionDetailDao.insertSelective(detail);
                if (detailRow == 0) {
                    processDefinitionDao.deleteByProcessId(processId);
                    return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_CREATE_ERROR);
                }
            }
            processDefinitionRequest.setNodes(nodes);
            processDefinitionRequest.setFlows(flows);
            processDefinitionRequest.setBeginId("_1");
            processDefinitionRequest.setEndId("_" + (size - 1));
            processDefinitionRequest.setProcessId(processDefinitionCreateRequest.getActivitiProcessId());
            ActivitiUtil.createProcess(processDefinitionRequest);
            return HttpResponse.success(processId);
        } catch (Exception e) {
            processDefinitionDao.deleteByProcessId(processId);
            processDefinitionDetailDao.deleteByProcessId(processId);
            LOGGER.error("创建流程失败!", e);
            return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_CREATE_ERROR);
        }
    }

    @Override
    public HttpResponse modify(ProcessDefinitionModifyRequest processDefinitionModifyRequest) {
        try {
            ProcessDefinition old = processDefinitionDao.selectByProcessType(processDefinitionModifyRequest.getBrandId(), processDefinitionModifyRequest.getProcessType());
            if (old == null) {
                return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_UPDATE_ERROR);
            }
            Integer processStatus = old.getProcessStatus();
            int deleteRow = processDefinitionDao.deleteByProcessId(old.getProcessId());
            if (deleteRow == 0) {
                return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_UPDATE_ERROR);
            }
            processDefinitionDetailDao.deleteByProcessId(processDefinitionModifyRequest.getProcessId());
            return create(JsonUtil.fromJson(JsonUtil.toJson(processDefinitionModifyRequest), ProcessDefinitionCreateRequest.class), processStatus);
        } catch (Exception e) {
            LOGGER.error("修改流程失败!", e);
            return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_UPDATE_ERROR);
        }
    }


    @Override
    public HttpResponse delete(String processId) {
        try {
            ProcessDefinition old = processDefinitionDao.selectByProcessId(processId);
            if (old == null) {
                return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_DELETE_ERROR);
            }
            int row = processDefinitionDao.deleteByProcessId(processId);
            if (row == 0) {
                return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_DELETE_ERROR);
            }
            processDefinitionDetailDao.deleteByProcessId(processId);
            processDefinitionDetailAuditorsDao.deleteByProcessId(processId);
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("删除流程失败!", e);
            return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_DELETE_ERROR);
        }
    }

    @Override
    public HttpResponse detail(String processId) {
        try {
            ProcessDefinition processDefinition = processDefinitionDao.selectByProcessId(processId);
            if (processDefinition == null) {
                return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_DETAIL_ERROR);
            }
            ProcessDefinitionDetail query = new ProcessDefinitionDetail();
            query.setProcessId(processId);
            List<ProcessDefinitionDetail> processDefinitionDetailList = processDefinitionDetailDao.selectSelective(query);
            for (ProcessDefinitionDetail processDefinitionDetail : processDefinitionDetailList) {
                ProcessDefinitionDetailAuditors auditorQuery = new ProcessDefinitionDetailAuditors();
                auditorQuery.setProcessDetailId(processDefinitionDetail.getProcessDetailId());
                List<ProcessDefinitionDetailAuditors> processDefinitionDetailAuditorsList = processDefinitionDetailAuditorsDao.selectSelective(auditorQuery);
                processDefinitionDetail.setAuditors(processDefinitionDetailAuditorsList);
            }
            processDefinition.setProcessDefinitionDetailList(processDefinitionDetailList);
            return HttpResponse.success(processDefinition);
        } catch (Exception e) {
            LOGGER.error("查询流程详情失败!", e);
            return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_DETAIL_ERROR);
        }
    }

    @Override
    public HttpResponse query(ProcessDefinitionQueryRequest processDefinitionQueryRequest) {
        try {
            List<ProcessDefinition> processDefinitionList = processDefinitionDao.selectSelective(processDefinitionQueryRequest);
            BaseResponse baseResponse = new BaseResponse(processDefinitionList.size(), processDefinitionList);
            return HttpResponse.success(baseResponse);
        } catch (Exception e) {
            LOGGER.error("查询流程列表失败!", e);
            return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_QUERY_ERROR);
        }
    }

    @Override
    public HttpResponse deploy(String brandId, Integer processType) {
        try {
            ProcessDefinition processDefinition = processDefinitionDao.selectByProcessType(brandId, processType);
            ProcessDefinitionModifyRequest deployParameter = new ProcessDefinitionModifyRequest();
            deployParameter.setProcessId(processDefinition.getProcessId());
            deployParameter.setProcessStatus(1);
            int deployRow = processDefinitionDao.updateByProcessIdSelective(deployParameter);
            if (deployRow == 0) {
                return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_DEPLOY_ERROR);
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("部署流程失败!", e);
            return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_DEPLOY_ERROR);
        }
    }

    @Override
    public HttpResponse cancel(String brandId, Integer processType) {
        try {
            ProcessDefinition processDefinition = processDefinitionDao.selectByProcessType(brandId, processType);
            ProcessDefinitionModifyRequest cancelParameter = new ProcessDefinitionModifyRequest();
            cancelParameter.setProcessId(processDefinition.getProcessId());
            cancelParameter.setProcessStatus(0);
            int cancelRow = processDefinitionDao.updateByProcessIdSelective(cancelParameter);
            if (cancelRow == 0) {
                return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_DEPLOY_ERROR);
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("部署流程失败!", e);
            return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_DEPLOY_ERROR);
        }
    }

    @Override
    public HttpResponse detailByType(String brandId, Integer processType) {
        try {
            ProcessDefinition processDefinition = processDefinitionDao.selectByProcessType(brandId, processType);
            if (processDefinition == null) {
                return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_DETAIL_ERROR);
            }
            ProcessDefinitionDetail query = new ProcessDefinitionDetail();
            query.setProcessId(processDefinition.getProcessId());
            List<ProcessDefinitionDetail> processDefinitionDetailList = processDefinitionDetailDao.selectSelective(query);
            for (ProcessDefinitionDetail processDefinitionDetail : processDefinitionDetailList) {
                ProcessDefinitionDetailAuditors auditorQuery = new ProcessDefinitionDetailAuditors();
                auditorQuery.setProcessDetailId(processDefinitionDetail.getProcessDetailId());
                List<ProcessDefinitionDetailAuditors> processDefinitionDetailAuditorsList = processDefinitionDetailAuditorsDao.selectSelective(auditorQuery);
                processDefinitionDetail.setAuditors(processDefinitionDetailAuditorsList);
            }
            processDefinition.setProcessDefinitionDetailList(processDefinitionDetailList);
            return HttpResponse.success(processDefinition);
        } catch (Exception e) {
            LOGGER.error("查询流程详情失败!", e);
            return HttpResponse.failure(ResultCode.PROCESS_DEFINITION_DETAIL_ERROR);
        }
    }

    @Override
    public Boolean exist(String brandId, Integer processType) {
        try {
            ProcessDefinition processDefinition = processDefinitionDao.selectByProcessType(brandId, processType);
            return processDefinition != null && processDefinition.getProcessStatus() != null && processDefinition.getProcessStatus() == Consts.PROCESS_STATUS_DEPLOYED;
        } catch (Exception e) {
            LOGGER.error("查询流程部署状态失败!", e);
            return false;
        }
    }

    @Override
    public HttpResponse existProcessesDeployed(String brandId, Integer processType) {
        try {
            Boolean existsStatus = exist(brandId, processType);
            return HttpResponse.success(existsStatus);
        } catch (Exception e) {
            LOGGER.error("查询流程部署状态失败!", e);
            return HttpResponse.failure(ResultCode.PROCESS_DEPLOYED_EXISTS_ERROR);
        }
    }
}

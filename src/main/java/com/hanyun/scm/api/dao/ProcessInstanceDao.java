package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.ProcessInstance;
import java.util.List;

import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceCreateRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceModifyRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceQueryRequest;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑, 永无BUG!
 * 　　　　┃　　　┃Code is far away from bug with the animal protecting
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * </pre>
 */
@SuppressWarnings("UnnecessaryInterfaceModifier")
@Repository
public interface ProcessInstanceDao {

    /**
     * 删除流程实例
     * @param id    流程实例id
     * @return  返回值
     */
    public int deleteByProcessInstanceId(String id);

    /**
     * 新增流程实例
     * @param processInstanceCreateRequest  参数
     * @return  返回值
     */
    public int insertSelective(ProcessInstanceCreateRequest processInstanceCreateRequest);

    /**
     * 查询流程实例详情
     * @param id    流程实例id
     * @return  返回值
     */
    public ProcessInstance selectByProcessInstanceId(String id);

    /**
     * 查询流程实例列表
     * @param processInstanceQueryRequest   查询条件
     * @return  返回值
     */
    public List<ProcessInstance> selectSelective(ProcessInstanceQueryRequest processInstanceQueryRequest);

    /**
     * 更新流程实例
     * @param processInstanceModifyRequest  参数
     * @return  返回值
     */
    public int updateByProcessInstanceIdSelective(ProcessInstanceModifyRequest processInstanceModifyRequest);

    /**
     * 查询流程实例by单据id
     * @param id    单据id
     * @return  返回值
     */
    public ProcessInstance selectByBusinessId(String id);
}
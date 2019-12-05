package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.ProcessDefinition;
import java.util.List;

import com.hanyun.scm.api.domain.request.process.definition.ProcessDefinitionCreateRequest;
import com.hanyun.scm.api.domain.request.process.definition.ProcessDefinitionModifyRequest;
import com.hanyun.scm.api.domain.request.process.definition.ProcessDefinitionQueryRequest;
import org.apache.ibatis.annotations.Param;
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
public interface ProcessDefinitionDao {

    /**
     * 删除流程
     * @param id    流程id
     * @return  返回值
     */
    public int deleteByProcessId(String id);

    /**
     * 新增流程
     * @param processDefinitionCreateRequest    参数
     * @return 返回值
     */
    public int insertSelective(ProcessDefinitionCreateRequest processDefinitionCreateRequest);

    /**
     * 查询流程详情
     * @param id    流程id
     * @return  返回值
     */
    public ProcessDefinition selectByProcessId(String id);

    /**
     * 查询流程列表
     * @param processDefinitionQueryRequest 查询参数
     * @return  返回值
     */
    public List<ProcessDefinition> selectSelective(ProcessDefinitionQueryRequest processDefinitionQueryRequest);

    /**
     * 更新流程
     * @param processDefinitionModifyRequest    参数
     * @return  返回值
     */
    public int updateByProcessIdSelective(ProcessDefinitionModifyRequest processDefinitionModifyRequest);

    /**
     * 根据类型查询流程详情
     * @param processType   类型
     * @return  返回值
     */
    ProcessDefinition selectByProcessType(@Param("brandId") String brandId, @Param("processType") Integer processType);
}
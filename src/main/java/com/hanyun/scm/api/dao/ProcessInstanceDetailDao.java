package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.ProcessInstanceDetail;
import java.util.List;

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
public interface ProcessInstanceDetailDao {

    /**
     * 新增流程实例详情
     * @param record    流程实例详情
     * @return  返回值
     */
    public int insertSelective(ProcessInstanceDetail record);

    /**
     * 查询流程实例详情列表
     * @param record    参数
     * @return  返回值
     */
    public List<ProcessInstanceDetail> selectSelective(ProcessInstanceDetail record);

    /**
     * 删除流程实例详情by实例id
     * @param processInstanceId 流程实例id
     * @return  返回值
     */
    public int deleteByProcessInstanceId(String processInstanceId);

    /**
     * 更新审批信息
     * @param processInstanceDetail 更新参数
     * @return 返回值
     */
    public int updateByDetailIdSelective(ProcessInstanceDetail processInstanceDetail);

    /**
     * 查询单据所有审批人
     * @param processId    单据id
     * @return  返回值
     */
    List<String> queryAuditors(String processId);
}
package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.ProcessDefinitionDetail;
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
public interface ProcessDefinitionDetailDao {

    /**
     * 新增流程详情
     * @param record    流程详情
     * @return  返回值
     */
    public int insertSelective(ProcessDefinitionDetail record);

    /**
     * 查询流程详情
     * @param record    参数
     * @return  返回值
     */
    public List<ProcessDefinitionDetail> selectSelective(ProcessDefinitionDetail record);

    /**
     * 删除流程详情by流程id
     * @param processId 流程id
     * @return  返回值
     */
    public int deleteByProcessId(String processId);
}
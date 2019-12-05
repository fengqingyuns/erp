package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.DisassemblyOrder;
import com.hanyun.scm.api.domain.request.disassembly.DisassemblyOrderCreateRequest;
import com.hanyun.scm.api.domain.request.disassembly.DisassemblyOrderModifyRequest;
import com.hanyun.scm.api.domain.request.disassembly.DisassemblyOrderQueryRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

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
public interface DisassemblyOrderDao {

    public int insert(DisassemblyOrderCreateRequest record);

    public DisassemblyOrder selectByDisassemblyOrderId(String disassemblyOrderId);

    public int countAll(DisassemblyOrderQueryRequest record);

    public List<DisassemblyOrder> select(DisassemblyOrderQueryRequest record);

    public int updateByDisassemblyOrderId(DisassemblyOrderModifyRequest record);

    public int deleteByDisassemblyOrderId(String disassemblyOrderId);
}
package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.DisassemblyOrderDetail;
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
public interface DisassemblyOrderDetailDao {

    public int countAll(DisassemblyOrderDetail record);

    public int deleteByDisassemblyOrderDetailId(String disassemblyOrderDetailId);

    public int deleteByDisassemblyOrderId(String disassemblyOrderId);

    public int insert(DisassemblyOrderDetail record);

    //获取母商品对象
    public DisassemblyOrderDetail queryMotherDetailObject(DisassemblyOrderDetail record);

    public DisassemblyOrderDetail selectByDisassemblyOrderDetailId(String disassemblyOrderDetailId);

    public List<DisassemblyOrderDetail> select(DisassemblyOrderDetail record);

    public int updateByDisassemblyOrderDetailId(DisassemblyOrderDetail record);

    public List<DisassemblyOrderDetail> selectByDisassemblyOrderId(String disassemblyOrderId);
}
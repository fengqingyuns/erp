package com.hanyun.scm.api.domain.request.disassembly;

import com.hanyun.scm.api.domain.DisassemblyOrder;
import com.hanyun.scm.api.domain.DisassemblyOrderDetail;

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
public class DisassemblyOrderCreateRequest extends DisassemblyOrder {

    private List<DisassemblyOrderDetail> srcList;

    private List<DisassemblyOrderDetail> toList;

    public List<DisassemblyOrderDetail> getSrcList() {
        return srcList;
    }

    public void setSrcList(List<DisassemblyOrderDetail> srcList) {
        this.srcList = srcList;
    }

    public List<DisassemblyOrderDetail> getToList() {
        return toList;
    }

    public void setToList(List<DisassemblyOrderDetail> toList) {
        this.toList = toList;
    }
}
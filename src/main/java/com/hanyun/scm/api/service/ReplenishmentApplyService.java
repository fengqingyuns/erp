package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.ReplenishmentApply;
import com.hanyun.scm.api.domain.dto.ReplenishmentDTO;
import com.hanyun.scm.api.domain.request.Replenishment.ReplenishmentApplyConfirmRequest;
import com.hanyun.scm.api.domain.request.Replenishment.ReplenishmentApplyRequest;
import com.hanyun.scm.api.exception.ReplenishmentApplyException;

import java.util.List;

public interface ReplenishmentApplyService {

    HttpResponse select(ReplenishmentApplyRequest replenishmentApplyRequest);

    HttpResponse modifyOrder(ReplenishmentApply replenishmentApply);

    HttpResponse queryById(ReplenishmentApplyRequest replenishmentApplyRequest);

    HttpResponse create(ReplenishmentApply replenishmentApply);

    List<ReplenishmentApply> exportReplenishmentApply(ReplenishmentApplyRequest replenishmentApplyRequest) throws ReplenishmentApplyException;

    List<List<Object>> exportReplenishmentApplyDetail(ReplenishmentApplyRequest replenishmentApplyRequest) throws ReplenishmentApplyException;

    /**
     * 查询补货申请单详情for配送汇总
     *
     * @param applyIds 申请单id
     * @return 返回值
     * @author tianye@hanyun.com
     */
    HttpResponse detailForDistribution(String[] applyIds, Integer editStatus);

    /**
     * 查询补货申请门店
     *
     * @param replenishmentApplyRequest 请求参数
     * @return 返回值
     * @author tianye@hanyun.com
     */
    HttpResponse queryStore(ReplenishmentApplyRequest replenishmentApplyRequest);

    /**
     * 处理失效订单
     *
     * @author tianye@hanyun.com
     */
    void invalidateApply();

    /**
     * 审核
     */
    HttpResponse confirm(ReplenishmentApplyConfirmRequest applyConfirmRequest);

    /**
     * 提审
     */
    HttpResponse commit(String replenishmentId);

    /**
     * 查询补货申请单商品详情
     *
     * @param dto 查询对象
     * @return HttpResponse
     */
    HttpResponse queryReplenishmentGoods(ReplenishmentDTO dto);

    HttpResponse queryApplyForDistributionRecord(String applyId);

    /**
     * 关闭申请单接口(状态)
     * @param replenishmentId 申请单id
     * @return HttpResponse
     */
    HttpResponse close(String replenishmentId);
}

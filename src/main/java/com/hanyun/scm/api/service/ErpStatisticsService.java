package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.request.erpStatistics.ErpStatisticsQueryRequest;

public interface ErpStatisticsService {

    /**
     * 查询进销存统计
     *
     * @param record 查询对象
     * @return HttpResponse
     */
    HttpResponse queryErpStatistics(ErpStatisticsQueryRequest record);
}

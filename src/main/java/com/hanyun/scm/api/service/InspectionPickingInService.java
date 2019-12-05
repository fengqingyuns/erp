package com.hanyun.scm.api.service;


import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.InspectionPickingIn;
import com.hanyun.scm.api.domain.request.InspectionPickingIn.InspectionPickingInDetailRequest;
import com.hanyun.scm.api.domain.request.InspectionPickingIn.InspectionPickingInRequest;

import java.util.List;

public interface InspectionPickingInService {

    HttpResponse select(InspectionPickingInRequest inspectionPickingInRequest);

    HttpResponse deleteOrder(String inspectionId);

    HttpResponse create(InspectionPickingIn inspectionPickingIn);

    HttpResponse selectDetail(InspectionPickingInDetailRequest inspectionPickingInDetailRequest);

    HttpResponse auditOrder(InspectionPickingIn inspectionPickingIn);

    HttpResponse queryByDistributionId(String distributionOrderId);

    /**
     * 提审
     * @param inspectionId 入库单id
     * @return HttpResponse
     */
    HttpResponse commit(String inspectionId);

    List<List<Object>> exportInspection(InspectionPickingInRequest inspection);

    List<List<Object>> exportInspectionId(String inspectionId);
}

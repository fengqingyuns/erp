package com.hanyun.scm.api.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.IdGenerateType;
import com.hanyun.scm.api.consts.MapConsts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.*;
import com.hanyun.scm.api.domain.PurchaseOrderDetail;
import com.hanyun.scm.api.domain.PurchasePlan;
import com.hanyun.scm.api.domain.PurchasePlanApply;
import com.hanyun.scm.api.domain.PurchasePlanDetail;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceCreateRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.apply.PurchaseApplyModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderCreateRequest;
import com.hanyun.scm.api.domain.request.purchase.plan.PurchasePlanConfirmRequest;
import com.hanyun.scm.api.domain.request.purchase.plan.PurchasePlanCreateRequest;
import com.hanyun.scm.api.domain.request.purchase.plan.PurchasePlanModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.plan.PurchasePlanQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.exception.ProcessInstanceException;
import com.hanyun.scm.api.exception.PurchasePlanException;
import com.hanyun.scm.api.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <pre>
 *                             _ooOoo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            O\  =  /O
 *                         ____/`---'\____
 *                       .'  \\|     |//  `.
 *                      /  \\|||  :  |||//  \
 *                     /  _||||| -:- |||||-  \
 *                     |   | \\\  -  /// |   |
 *                     | \_|  ''\---/''  |   |
 *                     \  .-\__  `-`  ___/-. /
 *                   ___`. .'  /--.--\  `. . __
 *                ."" '<  `.___\_<|>_/___.'  >'"".
 *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *          ======`-.____`-.___\_____/___.-`____.-'======
 *                             `=---='
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *
 *                     佛祖保佑        永无BUG
 *
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * </pre>
 * 采购计划单
 * Date: 2016/10/27
 * Time: 下午12:08
 *
 * @author tianye@hanyun.com
 */
@Service
public class PurchasePlanServiceImpl implements PurchasePlanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchasePlanServiceImpl.class);

    @Resource
    private PurchasePlanDao purchasePlanDao;

    @Resource
    private IdGenerateSeqService idGenerateSeqService;

    @Resource
    private PurchasePlanDetailDao purchasePlanDetailDao;

    @Resource
    private PurchasePlanApplyDao purchasePlanApplyDao;

    @Resource
    private PurchaseApplyDao purchaseApplyDao;

    @Resource
    private PurchaseOrderDao purchaseOrderDao;

    @Resource
    private PurchaseOrderDetailDao purchaseOrderDetailDao;

    @Resource
    private ProcessInstanceService processInstanceService;

    @Resource
    private ProcessDefinitionService processDefinitionService;

    @Resource
    private GoodsOdooService goodsOdooService;

    @Override
    public HttpResponse create(PurchasePlanCreateRequest purchasePlanCreateRequest) {
        try {
            String purchasePlanId = IdUtil.uuid();
            String purchasePlanDocumentId = idGenerateSeqService.generateId(IdGenerateType.CJ);
            purchasePlanCreateRequest.setPlanId(purchasePlanId);
            purchasePlanCreateRequest.setPlanDocumentId(purchasePlanDocumentId);
            int row = purchasePlanDao.insertSelective(purchasePlanCreateRequest);
            if (row <= 0) {
                LOGGER.error("添加采购计划单失败!");
                return HttpResponse.failure(ResultCode.PURCHASE_PLAN_ADD_ERROR);
            }
            if (purchasePlanCreateRequest.getPurchasePlanApplyList() != null && purchasePlanCreateRequest.getPurchasePlanApplyList().size() > 0) {
                purchasePlanCreateRequest.setPurchasePlanApplyList(removeEmpty(purchasePlanCreateRequest.getPurchasePlanApplyList()));
                for (PurchasePlanApply purchasePlanApply : purchasePlanCreateRequest.getPurchasePlanApplyList()) {
                    String planApplyId = IdUtil.uuid();
                    purchasePlanApply.setPlanApplyId(planApplyId);
                    purchasePlanApply.setPlanId(purchasePlanId);
                    int applyRow = purchasePlanApplyDao.insertSelective(purchasePlanApply);
                    if (applyRow <= 0) {
                        throw new PurchasePlanException("计划单添加申请单失败!");
                    }
                    planPurchaseApply(purchasePlanApply.getApplyId());
                }
            }
            if (purchasePlanCreateRequest.getPurchasePlanDetailList() != null && purchasePlanCreateRequest.getPurchasePlanDetailList().size() > 0) {
                for (PurchasePlanDetail purchasePlanDetail : purchasePlanCreateRequest.getPurchasePlanDetailList()) {
                    String planGoodsId = IdUtil.uuid();
                    purchasePlanDetail.setPlanId(purchasePlanId);
                    purchasePlanDetail.setPlanDetailId(planGoodsId);
                    int goodsRow = purchasePlanDetailDao.insertSelective(purchasePlanDetail);
                    if (goodsRow <= 0) {
                        throw new PurchasePlanException("添加商品失败!");
                    }
                }
            }
            boolean existStatus = processDefinitionService.exist(purchasePlanCreateRequest.getBrandId(), Consts.PROCESS_TYPE_PURCHASE_PLAN);
            if (existStatus) {
                return HttpResponse.success(new String[]{purchasePlanId, purchasePlanDocumentId, "exist"});
            }
            return HttpResponse.success(new String[]{purchasePlanId, purchasePlanDocumentId});
        } catch (Exception e) {
            LOGGER.error("添加采购计划单失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_PLAN_ADD_ERROR);
        }
    }

    @Override
    public HttpResponse detail(String planId) {
        try {
            PurchasePlan purchasePlan = purchasePlanDao.selectByPlanId(planId);
            if (purchasePlan == null) {
                LOGGER.error("查询采购计划单详情失败!");
                return HttpResponse.failure(ResultCode.PURCHASE_PLAN_DATA_NOT_FOUND);
            }
            List<PurchasePlanDetail> purchasePlanDetailList = purchasePlanDetailDao.selectByPlanId(planId);
            List<PurchasePlanApply> purchasePlanApplyList = purchasePlanApplyDao.selectByPlanId(planId);
            purchasePlan.setPurchasePlanDetailList(purchasePlanDetailList);
            purchasePlan.setPurchasePlanApplyList(purchasePlanApplyList);
            return HttpResponse.success(purchasePlan);
        } catch (Exception e) {
            LOGGER.error("查询采购计划单详情失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_PLAN_DETAIL_ERROR);
        }
    }

    @Override
    public HttpResponse select(PurchasePlanQueryRequest purchasePlanQueryRequest) {
        try {
            return HttpResponse.success(selectList(purchasePlanQueryRequest, false));
        } catch (Exception e) {
            LOGGER.error("查询采购计划单列表失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_PLAN_QUERY_ERROR);
        }
    }

    @Override
    public BaseResponse selectList(PurchasePlanQueryRequest purchasePlanQueryRequest, boolean excel) throws PurchasePlanException {
        try {
            int count = purchasePlanDao.countAll(purchasePlanQueryRequest);
            purchasePlanQueryRequest.setCount(count);
            List<PurchasePlan> purchasePlanList = purchasePlanDao.select(purchasePlanQueryRequest);
            if (excel) {
                for (PurchasePlan purchasePlan : purchasePlanList) {
                    purchasePlan.setPlanStatusName(MapConsts.getOrderStatus().get(purchasePlan.getPlanStatus()));
                }
            } else if (!StringUtils.isEmpty(purchasePlanQueryRequest.getUserId())) {
                for (PurchasePlan purchasePlan : purchasePlanList) {
                    purchasePlan.setAuditStatus(processInstanceService.queryAuditor(purchasePlan.getPlanId(), purchasePlanQueryRequest.getUserId()));
                    List<String> auditors = processInstanceService.queryAuditors(purchasePlan.getPlanId());
                    auditors.add(purchasePlan.getOperatorId());
                    purchasePlan.setHistoryStatus(auditors.size() > 1 && auditors.contains(purchasePlanQueryRequest.getUserId()));
                }
            }
            return new BaseResponse(count, purchasePlanList);
        } catch (Exception e) {
            LOGGER.error("查询采购计划单列表失败!", e);
            throw new PurchasePlanException("查询采购计划单列表失败!", e);
        }
    }

    @Override
    public HttpResponse modify(PurchasePlanModifyRequest purchasePlanModifyRequest) {
        try {
            PurchasePlan old = purchasePlanDao.selectByPlanId(purchasePlanModifyRequest.getPlanId());
            if (old == null) {
                LOGGER.error("采购计划单不存在!");
                return HttpResponse.failure(ResultCode.PURCHASE_PLAN_DATA_NOT_FOUND);
            }
            int row = purchasePlanDao.updateByPurchasePlan(purchasePlanModifyRequest);
            if (row <= 0) {
                LOGGER.error("更新采购计划单失败!");
                return HttpResponse.failure(ResultCode.PURCHASE_PLAN_UPD_ERROR);
            }
            modifyPurchaseApply(purchasePlanModifyRequest.getPlanId());
            int deleteApplyRow = purchasePlanApplyDao.deleteByPlanId(purchasePlanModifyRequest.getPlanId());
            if (deleteApplyRow <= 0) {
                throw new PurchasePlanException("更新计划单失败!");
            }
            if (purchasePlanModifyRequest.getPurchasePlanApplyList() != null && purchasePlanModifyRequest.getPurchasePlanApplyList().size() > 0) {
                purchasePlanModifyRequest.setPurchasePlanApplyList(removeEmpty(purchasePlanModifyRequest.getPurchasePlanApplyList()));
                for (PurchasePlanApply purchasePlanApply : purchasePlanModifyRequest.getPurchasePlanApplyList()) {
                    String planApplyId = IdUtil.uuid();
                    purchasePlanApply.setPlanApplyId(planApplyId);
                    purchasePlanApply.setPlanId(purchasePlanModifyRequest.getPlanId());
                    int applyRow = purchasePlanApplyDao.insertSelective(purchasePlanApply);
                    if (applyRow <= 0) {
                        throw new PurchasePlanException("计划单添加申请单失败!");
                    }
                    planPurchaseApply(purchasePlanApply.getApplyId());
                }
            }
            int deleteDetailRow = purchasePlanDetailDao.deleteByPlanId(purchasePlanModifyRequest.getPlanId());
            if (deleteDetailRow <= 0) {
                throw new PurchasePlanException("更新计划单失败!");
            }
            if (purchasePlanModifyRequest.getPurchasePlanDetailList() != null && purchasePlanModifyRequest.getPurchasePlanDetailList().size() > 0) {
                for (PurchasePlanDetail purchasePlanDetail : purchasePlanModifyRequest.getPurchasePlanDetailList()) {
                    String planGoodsId = IdUtil.uuid();
                    purchasePlanDetail.setPlanId(purchasePlanModifyRequest.getPlanId());
                    purchasePlanDetail.setPlanDetailId(planGoodsId);
                    int goodsRow = purchasePlanDetailDao.insertSelective(purchasePlanDetail);
                    if (goodsRow <= 0) {
                        throw new PurchasePlanException("添加商品失败!");
                    }
                }
            }
            boolean existStatus = processDefinitionService.exist(old.getBrandId(), Consts.PROCESS_TYPE_PURCHASE_PLAN);
            if (existStatus) {
                return HttpResponse.success(new String[]{old.getPlanId(), old.getPlanDocumentId(), "exists"});
            }
            return HttpResponse.success(new String[]{old.getPlanId(), old.getPlanDocumentId()});
        } catch (Exception e) {
            LOGGER.error("更新采购计划单失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_PLAN_UPD_ERROR);
        }
    }

    @Override
    public HttpResponse delete(String purchasePlanId) {
        try {
            PurchasePlan old = purchasePlanDao.selectByPlanId(purchasePlanId);
            if (old == null) {
                LOGGER.error("删除采购计划单失败-该采购计划单不存在!");
                return HttpResponse.failure(ResultCode.PURCHASE_PLAN_DATA_NOT_FOUND);
            }
            int row = purchasePlanDao.delete(purchasePlanId);
            if (row <= 0) {
                LOGGER.error("删除采购计划单失败!");
                return HttpResponse.failure(ResultCode.PURCHASE_PLAN_DELETE_ERROR);
            }
            modifyPurchaseApply(purchasePlanId);
            purchasePlanApplyDao.deleteByPlanId(purchasePlanId);
            purchasePlanDetailDao.deleteByPlanId(purchasePlanId);
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("删除采购计划单失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_PLAN_DELETE_ERROR);
        }
    }

    @Override
    public HttpResponse confirm(PurchasePlanConfirmRequest purchasePlanConfirmRequest) {
        try {
            PurchasePlan old = purchasePlanDao.selectByPlanId(purchasePlanConfirmRequest.getPlanId());
            if (old == null) {
                LOGGER.error("确认采购计划单失败-该采购计划单不存在!");
                return HttpResponse.failure(ResultCode.PURCHASE_PLAN_DATA_NOT_FOUND);
            }
            PurchasePlanModifyRequest purchasePlanModifyRequest = new PurchasePlanModifyRequest();
            purchasePlanModifyRequest.setPlanId(purchasePlanConfirmRequest.getPlanId());
            if (purchasePlanConfirmRequest.getPlanStatus() != null && purchasePlanConfirmRequest.getPlanStatus() == Consts.ORDER_STATUS_CONFIRMED) {
                purchasePlanModifyRequest.setPlanStatus(Consts.ORDER_STATUS_CONFIRMED);
            } else {
                if (!purchasePlanConfirmRequest.getAuditStatus()) {
                    purchasePlanModifyRequest.setPlanId(old.getPlanId());
                    purchasePlanModifyRequest.setPlanStatus(Consts.ORDER_STATUS_SAVED);
                    int row = purchasePlanDao.updateByPurchasePlan(purchasePlanModifyRequest);
                    if (row == 0) {
                        return HttpResponse.failure(ResultCode.PURCHASE_PLAN_CONFIRM_ERROR);
                    }
                    processInstanceService.delete(old.getPlanId());
                    return HttpResponse.success();
                }
                ProcessInstanceModifyRequest instanceModifyRequest = new ProcessInstanceModifyRequest();
                instanceModifyRequest.setBusinessId(old.getPlanId());
                instanceModifyRequest.setUserId(purchasePlanConfirmRequest.getUserId());
                instanceModifyRequest.setRemark("通过, 执行");
                HttpResponse response = processInstanceService.audit(instanceModifyRequest);
                if (!StringUtils.equals(response.getCode(), "0") || response.getData() == null) {
                    return HttpResponse.failure(ResultCode.PURCHASE_PLAN_CONFIRM_ERROR);
                }
                Integer instanceStatus = Integer.parseInt(response.getData().toString());
                if (instanceStatus != Consts.PROCESS_INSTANCE_STATUS_CONFIRMED) {
                    purchasePlanModifyRequest.setPlanStatus(Consts.ORDER_STATUS_CONFIRMING);
                } else {
                    purchasePlanModifyRequest.setPlanStatus(Consts.ORDER_STATUS_CONFIRMED);
                }
            }
            int row = purchasePlanDao.updateByPurchasePlan(purchasePlanModifyRequest);
            if (row == 0) {
                LOGGER.error("确认采购计划单失败!");
                return HttpResponse.failure(ResultCode.PURCHASE_PLAN_CONFIRM_ERROR);
            }
            //生成采购订单
            createPurchaseOrder(old);
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("确认采购计划单失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_PLAN_CONFIRM_ERROR);
        }
    }

    @Override
    public PurchasePlan getPlan(String planId) throws PurchasePlanException {
        PurchasePlan purchasePlan = purchasePlanDao.selectByPlanId(planId);
        if (purchasePlan == null) {
            LOGGER.error("该计划单不存在!");
            throw new PurchasePlanException("该计划单不存在!");
        }
        List<PurchasePlanDetail> purchasePlanDetailList = purchasePlanDetailDao.selectByPlanId(planId);
        if (purchasePlanDetailList == null) {
            LOGGER.error("计划单商品信息不存在!");
            throw new PurchasePlanException("计划单商品信息不存在!");
        }
        for (PurchasePlanDetail purchasePlanDetail : purchasePlanDetailList) {
            purchasePlanDetail.setTotalPurchasePrice(purchasePlanDetail.getPurchasePrice() * purchasePlanDetail.getPurchaseAmount());
        }
        purchasePlan.setPurchasePlanDetailList(purchasePlanDetailList);
        return purchasePlan;
    }

    @Override
    public HttpResponse commit(String planId) {
        try {
            PurchasePlan old = purchasePlanDao.selectByPlanId(planId);
            if (old == null) {
                return HttpResponse.failure(ResultCode.PURCHASE_PLAN_COMMIT_ERROR);
            }
            PurchasePlanModifyRequest purchasePlanModifyRequest = new PurchasePlanModifyRequest();
            purchasePlanModifyRequest.setPlanId(planId);
            purchasePlanModifyRequest.setPlanStatus(Consts.ORDER_STATUS_COMMITED);
            int row = purchasePlanDao.updateByPurchasePlan(purchasePlanModifyRequest);
            if (row == 0) {
                return HttpResponse.failure(ResultCode.PURCHASE_PLAN_COMMIT_ERROR);
            }
            ProcessInstanceCreateRequest createRequest = new ProcessInstanceCreateRequest();
            createRequest.setProcessType(Consts.PROCESS_TYPE_PURCHASE_PLAN);
            createRequest.setBrandId(old.getBrandId());
            createRequest.setBusinessId(planId);
            List<PurchasePlanDetail> purchasePlanDetailList = purchasePlanDetailDao.selectByPlanId(planId);
            Long totalPrice = 0L;
            for (PurchasePlanDetail purchasePlanDetail : purchasePlanDetailList) {
                totalPrice += purchasePlanDetail.getPurchasePrice() * purchasePlanDetail.getPurchaseAmount();
            }
            createRequest.setPrice(totalPrice);
            HttpResponse response = processInstanceService.create(createRequest, old.getOperatorId());
            if (!StringUtils.equals(response.getCode(), "0")) {
                throw new ProcessInstanceException("提审失败!");
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("采购计划单提审失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_APPLY_COMMIT_ERROR);
        }
    }

    /**
     * 生成采购订单
     *
     * @param old 采购申请单
     * @throws Exception 异常
     */
    private void createPurchaseOrder(PurchasePlan old) throws Exception {
        List<PurchasePlanDetail> purchasePlanDetailList = purchasePlanDetailDao.selectByPlanId(old.getPlanId());
        Map<String, List<PurchaseOrderDetail>> purchaseOrderMap = new HashMap<>();
        Map<String, String> supplierMap = new HashMap<>();
        for (PurchasePlanDetail purchasePlanDetail : purchasePlanDetailList) {
            PurchaseOrderDetail purchaseOrderDetail = JsonUtil.fromJson(JsonUtil.toJson(purchasePlanDetail), PurchaseOrderDetail.class);
            purchaseOrderDetail.setBrandId(old.getBrandId());
            purchaseOrderDetail.setStoreId(old.getStoreId());
            purchaseOrderDetail.setUnitPrice(purchasePlanDetail.getPurchasePrice());
            List<PurchaseOrderDetail> purchaseOrderDetailList;
            if (purchaseOrderMap.get(purchasePlanDetail.getSupplierName()) == null) {
                purchaseOrderDetailList = new ArrayList<>();
            } else {
                purchaseOrderDetailList = purchaseOrderMap.get(purchasePlanDetail.getSupplierName());
            }
            purchaseOrderDetailList.add(purchaseOrderDetail);
            purchaseOrderMap.put(purchasePlanDetail.getSupplierName(), purchaseOrderDetailList);
            supplierMap.put(purchasePlanDetail.getSupplierName(), purchasePlanDetail.getSupplierId());
        }
        Set<String> supplierSet = purchaseOrderMap.keySet();
        for (String supplierName : supplierSet) {
            List<PurchaseOrderDetail> purchaseOrderDetailList = purchaseOrderMap.get(supplierName);
            PurchaseOrderCreateRequest purchaseOrderCreateRequest = new PurchaseOrderCreateRequest();
            purchaseOrderCreateRequest.setBrandId(old.getBrandId());
            purchaseOrderCreateRequest.setStoreId(old.getStoreId());
            String orderId = IdUtil.uuid();
            purchaseOrderCreateRequest.setOrderId(orderId);
            String documentId = idGenerateSeqService.generateId(IdGenerateType.CD);
            purchaseOrderCreateRequest.setOrderDocumentId(documentId);
            purchaseOrderCreateRequest.setSupplierName(supplierName);
            purchaseOrderCreateRequest.setSupplierId(supplierMap.get(supplierName));
            purchaseOrderCreateRequest.setAuditorId(old.getAuditorId());
            purchaseOrderCreateRequest.setAuditorName(old.getAuditorName());
            purchaseOrderCreateRequest.setAuditTime(old.getAuditTime());
            purchaseOrderCreateRequest.setOperatorId(old.getAuditorId());
            purchaseOrderCreateRequest.setOperatorName(old.getAuditorName());
            purchaseOrderCreateRequest.setOrderStatus(Consts.PURCHASE_ORDER_SAVE_STATUS);
            purchaseOrderCreateRequest.setPlanDocumentId(old.getPlanDocumentId());
            purchaseOrderCreateRequest.setPlanId(old.getPlanId());
            purchaseOrderCreateRequest.setPurchaseType(old.getPurchaseType());
            Long totalAmount = 0L;
            Long totalPrice = 0L;
            for (PurchaseOrderDetail purchaseOrderDetail : purchaseOrderDetailList) {
                purchaseOrderDetail.setOrderId(orderId);
                String orderDetailId = IdUtil.uuid();
                purchaseOrderDetail.setOrderDetailId(orderDetailId);
                totalAmount += purchaseOrderDetail.getPurchaseAmount();
                totalPrice += purchaseOrderDetail.getPurchasePrice() * purchaseOrderDetail.getPurchaseAmount();
                int detailRow = purchaseOrderDetailDao.insertSelective(purchaseOrderDetail);
                if (detailRow <= 0) {
                    throw new PurchasePlanException("添加采购订单失败!");
                }
            }
            purchaseOrderCreateRequest.setTotalAmount(totalAmount);
            purchaseOrderCreateRequest.setTotalPrice(totalPrice);
            purchaseOrderCreateRequest.setPurchaseUserId(old.getAuditorId());
            purchaseOrderCreateRequest.setPurchaseUserName(old.getAuditorName());
            int orderRow = purchaseOrderDao.insertSelective(purchaseOrderCreateRequest);
            if (orderRow <= 0) {
                throw new PurchasePlanException("添加采购订单失败");
            }
        }
    }

    /**
     * 修改采购申请单状态为已计划
     *
     * @param applyId
     * @throws PurchasePlanException
     */
    private void planPurchaseApply(String applyId) throws PurchasePlanException {
        PurchaseApplyModifyRequest purchaseApplyModifyRequest = new PurchaseApplyModifyRequest();
        purchaseApplyModifyRequest.setApplyId(applyId);
        purchaseApplyModifyRequest.setApplyStatus(Consts.PURCHASE_APPLY_STATUS_PLAN);
        int updateApplyRow = purchaseApplyDao.updatePurchaseApply(purchaseApplyModifyRequest);
        if (updateApplyRow <= 0) {
            throw new PurchasePlanException("计划单添加申请单失败!");
        }
    }

    /**
     * 还原采购申请单状态为已审核
     *
     * @param planId 计划单id
     * @throws Exception 异常
     */
    private void modifyPurchaseApply(String planId) throws PurchasePlanException {
        List<PurchasePlanApply> purchasePlanApplyList = purchasePlanApplyDao.selectByPlanId(planId);
        for (PurchasePlanApply purchasePlanApply : purchasePlanApplyList) {
            PurchaseApplyModifyRequest purchaseApplyModifyRequest = new PurchaseApplyModifyRequest();
            purchaseApplyModifyRequest.setApplyId(purchasePlanApply.getApplyId());
            purchaseApplyModifyRequest.setApplyStatus(Consts.PURCHASE_APPLY_STATUS_CONFIRMED);
            int updateApplyRow = purchaseApplyDao.updatePurchaseApply(purchaseApplyModifyRequest);
            if (updateApplyRow <= 0) {
                throw new PurchasePlanException("删除计划单失败!");
            }
        }
    }

    /**
     * 去掉applyId为空的数据
     *
     * @param purchasePlanApplyList 商品列表
     * @return
     */
    private List<PurchasePlanApply> removeEmpty(List<PurchasePlanApply> purchasePlanApplyList) {
        for (int i = purchasePlanApplyList.size() - 1; i >= 0; i--) {
            if (StringUtils.isEmpty(purchasePlanApplyList.get(i).getApplyId())) {
                purchasePlanApplyList.remove(i);
            }
        }
        return purchasePlanApplyList;
    }
}

package com.hanyun.scm.api.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.IdGenerateType;
import com.hanyun.scm.api.consts.MapConsts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.*;
import com.hanyun.scm.api.domain.*;
import com.hanyun.scm.api.domain.request.LogisticsDelivery.LogisticsDeliveryCreateRequest;
import com.hanyun.scm.api.domain.request.LogisticsDelivery.LogisticsDeliveryModifyRequest;
import com.hanyun.scm.api.domain.request.LogisticsDelivery.LogisticsDeliveryQueryRequest;
import com.hanyun.scm.api.domain.request.distribution.DistributionOrderModifyRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceCreateRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceModifyRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.exception.LogisticsDeliveryException;
import com.hanyun.scm.api.exception.ProcessInstanceException;
import com.hanyun.scm.api.service.*;
import com.hanyun.scm.api.utils.ActivitiUtil;
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
 * 配送单serviceImpl
 * Date: 17/3/7
 * Time: 下午15:17
 *
 * @author 1007661792@qq.com
 */
@Service
public class LogisticsDeliveryOrderServiceImpl implements LogisticsDeliveryOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogisticsDeliveryOrderServiceImpl.class);

    @Resource
    private LogisticsDeliveryOrderDao logisticsDeliveryOrderDao;

    @Resource
    private LogisticsDeliveryOrderDetailDao logisticsDeliveryOrderDetailDao;

    @Resource
    private DistributionOrderDao distributionOrderDao;

    @Resource
    private DistributionOrderDetailDao distributionOrderDetailDao;

    @Resource
    private DistributionOrderService distributionOrderService;

    @Resource
    private ReplenishmentApplyDao replenishmentApplyDao;

    @Resource
    private ReplenishmentApplyDetailDao replenishmentApplyDetailDao;

    @Resource
    private DeliveryStoreDao deliveryStoreDao;

    @Resource
    private IdGenerateSeqService idGenerateSeqService;

    @Resource
    private ProcessInstanceService processInstanceService;

    @Resource
    private ProcessDefinitionService processDefinitionService;


    @Override
    public HttpResponse create(LogisticsDeliveryCreateRequest record) {
        try {
            List<LogisticsDeliveryOrderDetail> deliveryDetailList = removeDeliveryIdEmpty(record.getDeliveryDetailList());
            String logisticsDeliveryOrderId = record.getLogisticsDeliveryOrderId();
            String logisticsDeliveryDocumentId = record.getLogisticsDeliveryOrderDocumentId();
            if (deliveryDetailList == null || deliveryDetailList.isEmpty()) {
                throw new LogisticsDeliveryException("发货单详情列表为空。");
            }
            //获取页面上过来的详情的的门店list
            Map<String, String> storeMap = new HashMap<>();
            for (LogisticsDeliveryOrderDetail detail : deliveryDetailList) {
                DistributionOrder distributionOrder = distributionOrderDao.selectByDistributionOrderId(detail.getDistributionOrderId());
                storeMap.put(distributionOrder.getToStoreId(), distributionOrder.getToStoreName());
            }
            //获取门店id结束end

            //根据发货单id 新增(id不存在)或编辑(id存在)
            if (StringUtils.isEmpty(logisticsDeliveryOrderId)) {
                //发货单新增
                logisticsDeliveryOrderId = IdUtil.uuid();
                logisticsDeliveryDocumentId = idGenerateSeqService.generateId(IdGenerateType.WLFH);
                record.setLogisticsDeliveryOrderId(logisticsDeliveryOrderId);
                record.setLogisticsDeliveryOrderDocumentId(logisticsDeliveryDocumentId);
                int insertRow = logisticsDeliveryOrderDao.insert(record);
                if (insertRow <= 0) {
                    LOGGER.error("发货单单据插入失败。");
                    return HttpResponse.failure(ResultCode.LOGISTICSDELIVERY_CREATE_ERROR);
                }
                //发货单详情新增
                for (LogisticsDeliveryOrderDetail detail : deliveryDetailList) {
                    int insertDetailRow = insertDeliveryDetail(detail, record);
                    if (insertDetailRow <= 0) {
                        LOGGER.error("发货单详情单据插入失败。");
                        return HttpResponse.failure(ResultCode.LOGISTICSDELIVERY_DETAIL_CREATE_ERROR);
                    }
                    //更新配送单状态
                    int updateDistributionRow = updateDeliveryOrderStatus(detail.getDistributionOrderId(), record.getBrandId(), record.getStoreId(), Consts.DELIVERYSTATUS_OCCUPY);
                    if (updateDistributionRow <= 0) {
                        LOGGER.error("更改配送单单据状态失败。");
                        return HttpResponse.failure(ResultCode.DISTRIBUTION_UPDATE_STATUS_ERROR);
                    }
                }
                //中间表storeName新增
                for (Map.Entry<String, String> entry : storeMap.entrySet()) {
                    int insertStoreRow = insertDeliveryAndStore(entry, record.getLogisticsDeliveryOrderId());
                    if (insertStoreRow <= 0) {
                        LOGGER.error("插入中间表数据失败");
                        return HttpResponse.failure(ResultCode.DELIVERYANDDISTRIBUTION_CENTER_CREATE_ERROR);
                    }
                }
            } else {
                //发货单修改
                int updateRow = updateDelivery(record);
                if (updateRow <= 0) {
                    LOGGER.error("更新发货单单据信息失败。");
                    HttpResponse.failure(ResultCode.LOGISTICSDELIVERY_MODIFY_ERROR);
                }
                //发货单详情-->删除或新增
                LogisticsDeliveryOrderDetail oldQueryDetail = new LogisticsDeliveryOrderDetail();
                oldQueryDetail.setBrandId(record.getBrandId());
                if (!StringUtils.isEmpty(record.getStoreId())) {
                    oldQueryDetail.setStoreId(record.getStoreId());
                }
                oldQueryDetail.setLogisticsDeliveryOrderId(logisticsDeliveryOrderId);
                List<LogisticsDeliveryOrderDetail> oldDetailList = logisticsDeliveryOrderDetailDao.select(oldQueryDetail);
                //详情新增
                for (LogisticsDeliveryOrderDetail newDetail : deliveryDetailList) {//页面list
                    boolean insertFlag = true;
                    String newDistributionId = newDetail.getDistributionOrderId();
                    for (LogisticsDeliveryOrderDetail oldDetail : oldDetailList) {//数据库里的详情list
                        String oldDistributionId = oldDetail.getDistributionOrderId();
                        if (!newDistributionId.equalsIgnoreCase(oldDistributionId)) {
                            continue;
                        }
                        insertFlag = false;
                        break;
                    }
                    if (insertFlag) {
                        int insertDetailRow = insertDeliveryDetail(newDetail, record);
                        if (insertDetailRow <= 0) {
                            LOGGER.error("发货单详情单据插入失败。");
                            return HttpResponse.failure(ResultCode.LOGISTICSDELIVERY_DETAIL_CREATE_ERROR);
                        }
                        //更新配送单状态
                        int updateDistributionRow = updateDeliveryOrderStatus(newDistributionId, record.getBrandId(), record.getStoreId(), Consts.DELIVERYSTATUS_OCCUPY);
                        if (updateDistributionRow <= 0) {
                            LOGGER.error("更改配送单单据状态失败。");
                            return HttpResponse.failure(ResultCode.DISTRIBUTION_UPDATE_STATUS_ERROR);
                        }
                    }

                }
                //详情删除
                for (LogisticsDeliveryOrderDetail oldDetail : oldDetailList) {
                    String oldDistributionId = oldDetail.getDistributionOrderId();
                    String deliveryOrderdetailId = oldDetail.getLogisticsDeliveryOrderDetailId();
                    boolean deleteFlag = true;
                    for (LogisticsDeliveryOrderDetail newDetail : deliveryDetailList) {
                        String newDistributionId = newDetail.getDistributionOrderId();
                        if (oldDistributionId.equalsIgnoreCase(newDistributionId)) {
                            deleteFlag = false;
                            break;
                        }
                    }
                    if (deleteFlag) {
                        LogisticsDeliveryOrderDetail detailDB = logisticsDeliveryOrderDetailDao.selectByLogisticsDeliveryOrderDetailId(deliveryOrderdetailId);
                        if (detailDB == null) {
                            throw new LogisticsDeliveryException("单号:[" + deliveryOrderdetailId + "]不存在。");
                        }
                        int detailDeleteRow = logisticsDeliveryOrderDetailDao.deleteByLogisticsDeliveryOrderDetailId(deliveryOrderdetailId);
                        if (detailDeleteRow <= 0) {
                            LOGGER.error("删除物流送货单详情失败");
                            HttpResponse.failure(ResultCode.LOGISTICSDELIVERY_DETAIL_DELETE_ERROR);
                        }
                        //更新配送单状态
                        int updateDistributionRow = updateDeliveryOrderStatus(oldDistributionId, record.getBrandId(), record.getStoreId(), Consts.DELIVERYSTATUS_NOT);
                        if (updateDistributionRow <= 0) {
                            LOGGER.error("更改配送单单据状态失败。");
                            return HttpResponse.failure(ResultCode.DISTRIBUTION_UPDATE_STATUS_ERROR);
                        }
                    }
                }
                //中间表storeName新增 -删除
                DeliveryStore deliveryStore = new DeliveryStore();
                deliveryStore.setLogisticsDeliveryOrderId(logisticsDeliveryOrderId);
                List<DeliveryStore> deliveryStoreList = deliveryStoreDao.select(deliveryStore);//数据库查询的已有数据(该发货单下的门店ID)
                //新增
                for (Map.Entry<String, String> entry : storeMap.entrySet()) {
                    String newStoreId = entry.getKey();
                    boolean insertFlag = true;
                    for (DeliveryStore oldDeliveryStore : deliveryStoreList) {
                        String oldStoreId = oldDeliveryStore.getStoreId();
                        if (!newStoreId.equalsIgnoreCase(oldStoreId)) {
                            continue;
                        }
                        insertFlag = false;
                        break;
                    }
                    if (insertFlag) {
                        int insertStoreRow = insertDeliveryAndStore(entry, logisticsDeliveryOrderId);
                        if (insertStoreRow <= 0) {
                            LOGGER.error("插入中间表数据失败");
                            return HttpResponse.failure(ResultCode.DELIVERYANDDISTRIBUTION_CENTER_CREATE_ERROR);
                        }
                    }
                }
                //删除
                for (DeliveryStore oldDeliveryStore : deliveryStoreList) {
                    boolean deleteStoreFlag = true;
                    String oldStoreId = oldDeliveryStore.getStoreId();
                    for (Map.Entry<String, String> entry : storeMap.entrySet()) {
                        String newStoreId = entry.getKey();
                        if (oldStoreId.equalsIgnoreCase(newStoreId)) {
                            deleteStoreFlag = false;
                            break;
                        }
                    }
                    if (deleteStoreFlag) {
                        List<DeliveryStore> listDB = deliveryStoreDao.select(oldDeliveryStore);
                        if (listDB != null && listDB.size() != Consts.LIST_ONE_SIZE) {
                            throw new LogisticsDeliveryException("中间表数据未空、或数据不等于1");
                        }
                        int deleteStoreRw = deliveryStoreDao.deleteByOrderIdAndStoreId(oldDeliveryStore);
                        if (deleteStoreRw <= 0) {
                            LOGGER.error("删除中间表数据失败");
                            return HttpResponse.failure(ResultCode.DELIVERYANDDISTRIBUTION_CENTER_DELETE_ERROR);
                        }
                    }
                }
            }
            boolean existStatus = processDefinitionService.exist(record.getBrandId(), Consts.PROCESS_TYPE_LOGISTICS_DELIVERY);
            if (existStatus) {
                return HttpResponse.success(new String[]{logisticsDeliveryOrderId, logisticsDeliveryDocumentId, "exist"});
            }
            return HttpResponse.success(new String[]{logisticsDeliveryOrderId, logisticsDeliveryDocumentId});
        } catch (Exception e) {
            LOGGER.error("创建物流送货单失败.", e);
            return HttpResponse.failure(ResultCode.LOGISTICSDELIVERY_CREATE_ERROR);
        }
    }

    /**
     * 在新增和删除时更新配送单发货状态(用于查询时过滤被占用的配送单)
     *
     * @param distributionOrderId 配送单id
     * @param brandId             平牌id
     * @param storeId             门店id
     * @param status              要更改的配送单状态
     * @return update No
     */
    public Integer updateDeliveryOrderStatus(String distributionOrderId, String brandId, String storeId, Integer status) {
        DistributionOrderModifyRequest modify = new DistributionOrderModifyRequest();
        modify.setBrandId(brandId);
        if (!StringUtils.isEmpty(storeId)) {
            modify.setStoreId(storeId);
        }
        modify.setDistributionOrderId(distributionOrderId);
        modify.setDeliveryStatus(status);
        return distributionOrderDao.updateByDistributionOrderId(modify);
    }

    /**
     * 插入物流发货单详细信息
     *
     * @param detail 详情对象
     * @param record 发货单据对象
     * @return insert No
     */
    private int insertDeliveryDetail(LogisticsDeliveryOrderDetail detail, LogisticsDeliveryCreateRequest record) {
        String brandId = record.getBrandId();
        String storeId = record.getStoreId();
        detail.setBrandId(brandId);
        if (!StringUtils.isEmpty(storeId)) {
            detail.setStoreId(storeId);
        }
        detail.setLogisticsDeliveryOrderDetailId(IdUtil.uuid());
        detail.setLogisticsDeliveryOrderId(record.getLogisticsDeliveryOrderId());
        return logisticsDeliveryOrderDetailDao.insert(detail);
    }

    /**
     * 插入中间表数据
     *
     * @param entry                    map对象
     * @param logisticsDeliveryOrderId 发货单业务ID
     * @return inset No
     */
    private int insertDeliveryAndStore(Map.Entry<String, String> entry, String logisticsDeliveryOrderId) {
        DeliveryStore deliveryStore = new DeliveryStore();
        deliveryStore.setLogisticsDeliveryOrderId(logisticsDeliveryOrderId);
        deliveryStore.setStoreId(entry.getKey());
        deliveryStore.setStoreName(entry.getValue());
        deliveryStore.setLogisticsDeliveryOrderId(logisticsDeliveryOrderId);
        return deliveryStoreDao.insert(deliveryStore);
    }

    /**
     * 修改发货单单据信息
     *
     * @param record 修改对象
     * @return update No
     */
    private int updateDelivery(LogisticsDeliveryCreateRequest record) {
        LogisticsDeliveryModifyRequest modifyRequest = new LogisticsDeliveryModifyRequest();
        modifyRequest.setBrandId(record.getBrandId());
        if (!StringUtils.isEmpty(record.getStoreId())) {
            modifyRequest.setStoreId(record.getStoreId());
        }
        modifyRequest.setLogisticsDeliveryOrderId(record.getLogisticsDeliveryOrderId());
        modifyRequest.setWarehouseId(record.getWarehouseId());
        modifyRequest.setWarehouseName(record.getWarehouseName());
        modifyRequest.setLogisticsOrderDocumentId(record.getLogisticsOrderDocumentId());
        modifyRequest.setDeliveryTime(record.getDeliveryTime());
        modifyRequest.setRemark(record.getRemark());
        return logisticsDeliveryOrderDao.updateByLogisticsDeliveryOrderId(modifyRequest);
    }

    @Override
    public HttpResponse select(LogisticsDeliveryQueryRequest record) {
        try {
            String beginTime = record.getQueryBeginTime();
            String endTime = record.getQueryEndTime();
            if (!StringUtils.isEmpty(beginTime)) {
                record.setQueryBeginTime(beginTime.replace('T', ' '));
            }
            if (!StringUtils.isEmpty(endTime)) {
                record.setQueryEndTime(endTime.replace('T', ' '));
            }
            int count = logisticsDeliveryOrderDao.countAll(record);
            record.setCount(count);
            List<LogisticsDeliveryOrder> list = logisticsDeliveryOrderDao.select(record);
            if (!StringUtils.isEmpty(record.getUserId())) {
                for (LogisticsDeliveryOrder delivery : list) {
                    delivery.setAuditStatus(processInstanceService.queryAuditor(delivery.getLogisticsDeliveryOrderId(), record.getUserId()));
                    List<String> auditors = processInstanceService.queryAuditors(delivery.getLogisticsDeliveryOrderId());
                    auditors.add(delivery.getMakeorderManId());
                    delivery.setHistoryStatus(auditors.size() > 1 && auditors.contains(record.getUserId()));
                }
            }
            BaseResponse resposne = new BaseResponse(count, list);
            return HttpResponse.success(resposne);
        } catch (Exception e) {
            LOGGER.error("查询物流送货单失败.", e);
            return HttpResponse.failure(ResultCode.LOGISTICSDELIVERY_QUERY_ERROR);
        }
    }

    @Override
    public HttpResponse modify(LogisticsDeliveryModifyRequest record) {
        try {
            //审核
            String logisticsDeliveryOrderId = record.getLogisticsDeliveryOrderId();
            String auditorId = record.getAuditorId();
            String auditorName = record.getAuditorName();

            LogisticsDeliveryOrder logisticsDeliveryOrder = logisticsDeliveryOrderDao.selectByLogisticsDeliveryOrderId(logisticsDeliveryOrderId);
            if (logisticsDeliveryOrder == null) {
                throw new LogisticsDeliveryException("单号:[" + logisticsDeliveryOrderId + "]不存在。");
            }
            if (record.getLogisticsDeliveryOrderStatus() != null && record.getLogisticsDeliveryOrderStatus() == Consts.DELIVERY_ORDER_STATUS_CONFIRMED) {
                logisticsDeliveryOrder.setLogisticsDeliveryOrderStatus(Consts.DELIVERY_ORDER_STATUS_CONFIRMED);
            } else {
                if (!record.getAuditStatus()) {
                    LogisticsDeliveryModifyRequest modifyRequest = new LogisticsDeliveryModifyRequest();
                    modifyRequest.setLogisticsDeliveryOrderId(logisticsDeliveryOrderId);
                    modifyRequest.setAuditorId(auditorId);
                    modifyRequest.setAuditorName(auditorName);
                    modifyRequest.setLogisticsDeliveryOrderStatus(Consts.DELIVERY_ORDER_STATUS_SAVE);
                    int row = logisticsDeliveryOrderDao.updateByLogisticsDeliveryOrderId(modifyRequest);
                    if (row <= 0) {
                        return HttpResponse.failure(ResultCode.LOGISTICSDELIVERY_MODIFY_ERROR);
                    }
                    processInstanceService.delete(logisticsDeliveryOrderId);
                    return HttpResponse.success();
                }
                ProcessInstanceModifyRequest processInstanceModifyRequest = new ProcessInstanceModifyRequest();
                processInstanceModifyRequest.setBusinessId(logisticsDeliveryOrderId);
                processInstanceModifyRequest.setUserId(record.getUserId());
                processInstanceModifyRequest.setRemark("通过, 执行");
                HttpResponse response = processInstanceService.audit(processInstanceModifyRequest);
                if (!StringUtils.equals(response.getCode(), "0") || response.getData() == null) {
                    return HttpResponse.failure(ResultCode.LOGISTICSDELIVERY_MODIFY_ERROR);
                }

                Integer instanceStatus = Integer.parseInt(response.getData().toString());
                if (instanceStatus != Consts.PROCESS_INSTANCE_STATUS_CONFIRMED) {
                    logisticsDeliveryOrder.setLogisticsDeliveryOrderStatus(Consts.DELIVERY_ORDER_STATUS_CONIFRMING);
                } else {
                    logisticsDeliveryOrder.setLogisticsDeliveryOrderStatus(Consts.DELIVERY_ORDER_STATUS_CONFIRMED);
                }
            }

            LogisticsDeliveryModifyRequest modify = new LogisticsDeliveryModifyRequest();
            modify.setLogisticsDeliveryOrderId(logisticsDeliveryOrderId);
            modify.setAuditorId(auditorId);
            modify.setAuditorName(auditorName);
            modify.setAuditTime(new Date());
            modify.setLogisticsDeliveryOrderStatus(logisticsDeliveryOrder.getLogisticsDeliveryOrderStatus());

            int updateRow = logisticsDeliveryOrderDao.updateByLogisticsDeliveryOrderId(modify);
            if (updateRow <= 0) {
                LOGGER.error("更新物流送货单失败");
                return HttpResponse.failure(ResultCode.DELIVERY_ORDER_CONFIRM_ERROR);
            }
            //--------------end

            if (modify.getLogisticsDeliveryOrderStatus() != Consts.ORDER_STATUS_CONFIRMED) {
                return HttpResponse.success();
            }

            //查询发货单详情对象
            LogisticsDeliveryOrderDetail orderdetail = new LogisticsDeliveryOrderDetail();
            orderdetail.setBrandId(record.getBrandId());
            if (!StringUtils.isEmpty(record.getStoreId())) {
                orderdetail.setStoreId(record.getStoreId());
            }
            orderdetail.setLogisticsDeliveryOrderId(logisticsDeliveryOrderId);
            //查询的详情list
            List<LogisticsDeliveryOrderDetail> detailList = logisticsDeliveryOrderDetailDao.select(orderdetail);
            for (LogisticsDeliveryOrderDetail detail : detailList) {
                String distributionId = detail.getDistributionOrderId();
                if (StringUtils.isEmpty(distributionId)) {
                    continue;
                }
                //更新配送单状态
                int udpateRow = updateDeliveryOrderStatus(distributionId, record.getBrandId(), record.getStoreId(), Consts.DELIVERYSTATUS_DONE);
                if (udpateRow <= 0) {
                    LOGGER.error("更改配送单单据状态失败。");
                    return HttpResponse.failure(ResultCode.DISTRIBUTION_UPDATE_STATUS_ERROR);
                }

                /**更新申请单的发货状态 和发货数量*/
                //配送单单据对象
                DistributionOrder distributionOrder = distributionOrderDao.selectByDistributionOrderId(distributionId);
                if (distributionOrder == null) {
                    throw new LogisticsDeliveryException("该配送单不存在。");
                }
                //查询配送单详情对象
                DistributionOrderDetail disorderdetail = new DistributionOrderDetail();
                disorderdetail.setBrandId(distributionOrder.getBrandId());
                if (!StringUtils.isEmpty(distributionOrder.getStoreId())) {
                    disorderdetail.setStoreId(distributionOrder.getStoreId());
                }
                disorderdetail.setDistributionOrderId(distributionOrder.getDistributionOrderId());
                List<DistributionOrderDetail> distributionDetailList = distributionOrderDetailDao.select(disorderdetail);
                //调用update申请单发货状态和发货数量方法
                int updateRenishmentStatusRow = updateReplenishmentStatus(distributionOrder, distributionDetailList);
                if (updateRenishmentStatusRow <= 0) {
                    LOGGER.error("更新发货状态和发货数量失败。");
                    return HttpResponse.failure(ResultCode.REPLENISHMENTAPPLY_DETAIL_UPDATE_ERROR);
                }
            }
            return HttpResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("更新物流送货单失败.", e);
            return HttpResponse.failure(ResultCode.LOGISTICSDELIVERY_MODIFY_ERROR);
        }
    }

    @Override
    public HttpResponse commit(String logisticsDeliveryOrderId) {
        try {
            LogisticsDeliveryOrder old = logisticsDeliveryOrderDao.selectByLogisticsDeliveryOrderId(logisticsDeliveryOrderId);
            if (old == null) {
                throw new LogisticsDeliveryException("为查询到该单据");
            }
            LogisticsDeliveryModifyRequest modifyRequest = new LogisticsDeliveryModifyRequest();
            modifyRequest.setLogisticsDeliveryOrderId(logisticsDeliveryOrderId);
            modifyRequest.setLogisticsDeliveryOrderStatus(Consts.DELIVERY_ORDER_STATUS_COMMITED);
            int modifyRow = logisticsDeliveryOrderDao.updateByLogisticsDeliveryOrderId(modifyRequest);
            if (modifyRow <= 0) {
                return HttpResponse.failure(ResultCode.DELIVERY_ORDER_COMMIT_ERROR);
            }
            ProcessInstanceCreateRequest processInstanceCreateRequest = new ProcessInstanceCreateRequest();
            processInstanceCreateRequest.setProcessType(Consts.PROCESS_TYPE_LOGISTICS_DELIVERY);
            processInstanceCreateRequest.setBrandId(old.getBrandId());
            processInstanceCreateRequest.setBusinessId(logisticsDeliveryOrderId);
            ActivitiUtil.getInstance();
            HttpResponse response = processInstanceService.create(processInstanceCreateRequest, old.getMakeorderManId());
            if (!StringUtils.equals(response.getCode(), "0")) {
                throw new ProcessInstanceException("提审失败!");
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("提审失败", e);
            return HttpResponse.failure(ResultCode.DELIVERY_ORDER_COMMIT_ERROR);
        }
    }

    /**
     * 更新补货申请单发货状态和发货数量
     *
     * @param distributionOrder 配送单对象
     * @return update no
     */
    private Integer updateReplenishmentStatus(DistributionOrder distributionOrder, List<DistributionOrderDetail> distributionDetailList) {
        //源单数组
        String[] replenishmentApplyIds = distributionOrder.getSourceReplenishmentId().split(",");
        //当前配送单关联的所有源申请单的详情map
        Map<String, ReplenishmentApplyDetail> map = distributionOrderService.getMap(replenishmentApplyIds);
        /**更新发货数量*/
        for (DistributionOrderDetail distributionDetail : distributionDetailList) {
            Long distributionNum = distributionDetail.getDistributionQuantity();//配送单商品的配送数量(即是发货数量)
            String goodsId = distributionDetail.getGoodsId();                   //配送单商品Id
            for (int i = replenishmentApplyIds.length - 1; i >= 0; i--) {
                String replenApplyId = replenishmentApplyIds[i];

                //通过goodsId和applyId 获取到map中的这个详情对象 进行对比赋值
                ReplenishmentApplyDetail object = map.get(replenApplyId + "," + goodsId);
                if (object == null) {
                    continue;
                }
                Long deliveryNum = object.getDeliveryNum() != null ? object.getDeliveryNum() : 0L;                     //申请单-->发货数量
                Long applyNum = object.getApplyNum() != null ? object.getApplyNum() : 0L;                              //申请单-->计划数
                Long importedNum = applyNum - deliveryNum;                                                             //申请单-->未发货数
                if (distributionNum >= importedNum) {
                    object.setDeliveryNum(applyNum);
                    distributionNum = distributionNum - applyNum;
                } else {
                    object.setDeliveryNum(deliveryNum + distributionNum);
                    distributionNum = 0L;
                }
                int updateNumRow = replenishmentApplyDetailDao.updateByReplenishmentId(object);
                if (updateNumRow <= 0) {
                    return updateNumRow;
                }
            }
        }
        /**更新发货状态*/
        for (int i = replenishmentApplyIds.length - 1; i >= 0; i--) {
            String replenApplyId = replenishmentApplyIds[i];
            boolean udpateDeliveryStatusFlag = true;
            List<ReplenishmentApplyDetail> applyDetailList = replenishmentApplyDetailDao.selectByReplenishmentId(replenApplyId);
            for (ReplenishmentApplyDetail applyDetail : applyDetailList) {
                String goodsId = applyDetail.getGoodsId();
                ReplenishmentApplyDetail object;
                //通过goodsId和applyId 获取到map中的这个详情对象 进行对比赋值
                object = map.get(replenApplyId + "," + goodsId);
                if (object == null) {
                    continue;
                }
                if (object.getDeliveryNum() < object.getApplyNum()) {
                    udpateDeliveryStatusFlag = false;
                    break;
                }
            }
            //更新单据的发货状态
            ReplenishmentApply replenishmentApply = new ReplenishmentApply();
            replenishmentApply.setReplenishmentId(replenApplyId);
            if (udpateDeliveryStatusFlag) {
                replenishmentApply.setReceiptStatus(Consts.REPLENISHMENT_APPLY_DELIVER_OVER);
                int updaOrderRow = replenishmentApplyDao.updateByPrimaryKeySelective(replenishmentApply);
                if (updaOrderRow <= 0) {
                    return updaOrderRow;
                }
            } else {
                replenishmentApply.setReceiptStatus(Consts.REPLENISHMENT_APPLY_DELIVER_PART);
                int updaOrderRow = replenishmentApplyDao.updateByPrimaryKeySelective(replenishmentApply);
                if (updaOrderRow <= 0) {
                    return updaOrderRow;
                }
            }
        }
        return 1;
    }

    @Override
    public HttpResponse detail(String logisticsDeliveryOrderId) {
        try {
            LogisticsDeliveryOrder logisticsDeliveryOrder = logisticsDeliveryOrderDao.selectByLogisticsDeliveryOrderId(logisticsDeliveryOrderId);
            if (logisticsDeliveryOrder == null) {
                throw new LogisticsDeliveryException("单号:[" + logisticsDeliveryOrderId + "]不存在。");
            }
            //查询配送点详情对象
            LogisticsDeliveryOrderDetail detail = new LogisticsDeliveryOrderDetail();
            detail.setBrandId(logisticsDeliveryOrder.getBrandId());
            if (!StringUtils.isEmpty(logisticsDeliveryOrder.getStoreId())) {
                detail.setStoreId(logisticsDeliveryOrder.getStoreId());
            }
            detail.setLogisticsDeliveryOrderId(logisticsDeliveryOrderId);
            List<LogisticsDeliveryOrderDetail> detailList = logisticsDeliveryOrderDetailDao.select(detail);
            //通过详情列表获取  配送点id
            List<String> idList = new ArrayList<>();//存放配送单id
            for (LogisticsDeliveryOrderDetail delivDetailList : detailList) {
                String distirbutionId = delivDetailList.getDistributionOrderId();
                idList.add(distirbutionId);
            }
            //通过配送单id查找配送单详情
            List<DistributionOrder> distributionOrderList = new ArrayList<DistributionOrder>();
            for (int i = 0; i < idList.size(); i++) {
                String distirbutionId = idList.get(i);
                DistributionOrder distributionOrder = distributionOrderDao.selectByDistributionOrderId(distirbutionId);
                //将查询的配送单对象信息封装到list并返回
                if (distributionOrder != null) {
                    distributionOrderList.add(distributionOrder);
                }
            }
            logisticsDeliveryOrder.setDistributionOrderList(distributionOrderList);
            //通过送货单id获取中间表的store列表
            DeliveryStore deliveryStore = new DeliveryStore();
            deliveryStore.setLogisticsDeliveryOrderId(logisticsDeliveryOrderId);
            List<DeliveryStore> deliveryStoreList = deliveryStoreDao.select(deliveryStore);
            logisticsDeliveryOrder.setDeliveryStoreList(deliveryStoreList);
            return HttpResponse.success(logisticsDeliveryOrder);
        } catch (Exception e) {
            LOGGER.error("查询物流送货单单据信息和详情失败.", e);
            return HttpResponse.failure(ResultCode.LOGISTICSDELIVERY_QUERY_DETAIL_ERROR);
        }
    }

    @Override
    public HttpResponse delete(String logisticsDeliveryOrderId) {
        try {
            LogisticsDeliveryOrder orderDB = logisticsDeliveryOrderDao.selectByLogisticsDeliveryOrderId(logisticsDeliveryOrderId);
            if (orderDB == null) {
                throw new LogisticsDeliveryException("单号:[" + logisticsDeliveryOrderId + "]不存在。");
            }
            int deleteRow = logisticsDeliveryOrderDao.deleteByLogisticsDeliveryOrderId(logisticsDeliveryOrderId);
            if (deleteRow <= 0) {
                LOGGER.error("删除物流送货单单据信息失败");
                HttpResponse.failure(ResultCode.LOGISTICSDELIVERY_ORDER_DELETE_ERROR);
            }
            //发货单查询详情对象
            LogisticsDeliveryOrderDetail detail = new LogisticsDeliveryOrderDetail();
            detail.setBrandId(orderDB.getBrandId());
            if (!StringUtils.isEmpty(orderDB.getStoreId())) {
                detail.setStoreId(orderDB.getStoreId());
            }
            detail.setLogisticsDeliveryOrderId(logisticsDeliveryOrderId);
            //通过发货单Id查询详情的list
            List<LogisticsDeliveryOrderDetail> detailList = logisticsDeliveryOrderDetailDao.select(detail);
            for (LogisticsDeliveryOrderDetail detailDB : detailList) {
                String detailId = detailDB.getLogisticsDeliveryOrderDetailId();
                LogisticsDeliveryOrderDetail oldDetial = logisticsDeliveryOrderDetailDao.selectByLogisticsDeliveryOrderDetailId(detailId);
                if (oldDetial != null) {
                    int detailDeleteRow = logisticsDeliveryOrderDetailDao.deleteByLogisticsDeliveryOrderDetailId(detailId);
                    if (detailDeleteRow <= 0) {
                        LOGGER.error("删除物流送货单详情失败");
                        HttpResponse.failure(ResultCode.LOGISTICSDELIVERY_DETAIL_DELETE_ERROR);
                    }
                    //更新配送单状态
                    int updateDistributionRow = updateDeliveryOrderStatus(oldDetial.getDistributionOrderId(), orderDB.getBrandId(), orderDB.getStoreId(), Consts.DELIVERYSTATUS_NOT);
                    if (updateDistributionRow <= 0) {
                        LOGGER.error("更改配送单单据状态失败。");
                        return HttpResponse.failure(ResultCode.DISTRIBUTION_UPDATE_STATUS_ERROR);
                    }
                }
            }
            //删除中间表数据
            DeliveryStore deliveryStore = new DeliveryStore();
            deliveryStore.setLogisticsDeliveryOrderId(logisticsDeliveryOrderId);
            List<DeliveryStore> deliveryStoreList = deliveryStoreDao.select(deliveryStore);
            if (deliveryStoreList != null && deliveryStoreList.size() > 0) {
                for (DeliveryStore oldDeliveryStore : deliveryStoreList) {
                    DeliveryStore deleteDeliveryStore = new DeliveryStore();
                    deleteDeliveryStore.setLogisticsDeliveryOrderId(oldDeliveryStore.getLogisticsDeliveryOrderId());
                    deleteDeliveryStore.setStoreId(oldDeliveryStore.getStoreId());
                    int deleteStoreRow = deliveryStoreDao.deleteByOrderIdAndStoreId(deleteDeliveryStore);
                    if (deleteStoreRow <= 0) {
                        LOGGER.error("删除中间表数据失败。");
                        return HttpResponse.failure(ResultCode.DISTRIBUTIONSTORE_DELETE_STATUS_ERROR);
                    }
                }
            }
            return HttpResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("删除物流送货单单据信息和详情失败.", e);
            return HttpResponse.failure(ResultCode.LOGISTICSDELIVERY_DELETE_ERROR);
        }
    }

    @Override
    public List<List<Object>> exportByLogisticsDelivery(LogisticsDeliveryQueryRequest record) throws LogisticsDeliveryException {
        List<List<Object>> resultList = new ArrayList<List<Object>>();//返回的list
        List<LogisticsDeliveryOrder> list = logisticsDeliveryOrderDao.select(record);//查询的list
        Integer step = 0;
        for (LogisticsDeliveryOrder logisticsDeliveryOrder : list) {
            step++;
            List<Object> beanList = new ArrayList<>();
            beanList.add(step);
            beanList.add(logisticsDeliveryOrder.getLogisticsDeliveryOrderDocumentId());
            beanList.add(logisticsDeliveryOrder.getLogisticsOrderDocumentId());
            beanList.add(logisticsDeliveryOrder.getWarehouseName());
            beanList.add(logisticsDeliveryOrder.getDeliveryTime());
            beanList.add(logisticsDeliveryOrder.getMakeorderManName());
            beanList.add(logisticsDeliveryOrder.getCreateTime());
            beanList.add(MapConsts.getLogisticsDeliveryStatus().get(logisticsDeliveryOrder.getLogisticsDeliveryOrderStatus()));
            resultList.add(beanList);
        }

        return resultList;
    }

    @Override
    public List<List<Object>> exportBylogisticsDeliveryById(String logisticsDeliveryOrderId) throws LogisticsDeliveryException {
        List<List<Object>> resultList = new ArrayList<List<Object>>();//返回的list
        LogisticsDeliveryOrder logisticsDeliveryOrder = logisticsDeliveryOrderDao.selectByLogisticsDeliveryOrderId(logisticsDeliveryOrderId);
        if (logisticsDeliveryOrder == null) {
            throw new LogisticsDeliveryException("发货单Id:[" + logisticsDeliveryOrderId + "]不存在。");
        }
        //查询发货单详单对象
        LogisticsDeliveryOrderDetail detail = new LogisticsDeliveryOrderDetail();
        detail.setBrandId(logisticsDeliveryOrder.getBrandId());
        if (!StringUtils.isEmpty(logisticsDeliveryOrder.getStoreId())) {
            detail.setStoreId(logisticsDeliveryOrder.getStoreId());
        }
        detail.setLogisticsDeliveryOrderId(logisticsDeliveryOrderId);
        List<LogisticsDeliveryOrderDetail> detailList = logisticsDeliveryOrderDetailDao.select(detail);
        Integer step = 0;
        for (LogisticsDeliveryOrderDetail orderDetail : detailList) {
            step++;
            String distributionId = orderDetail.getDistributionOrderId();
            if (distributionId == null) {
                continue;
            }
            DistributionOrder distributionOrder = distributionOrderDao.selectByDistributionOrderId(distributionId);
            List<Object> beanList = new ArrayList<>();
            beanList.add(step);
            beanList.add(distributionOrder.getDistributionOrderDocumentId());
            beanList.add(distributionOrder.getToStoreName());
            beanList.add(distributionOrder.getWarehouseName());
            beanList.add(distributionOrder.getMakeorderManName());
            beanList.add(distributionOrder.getCreateTime());
            beanList.add(distributionOrder.getRemark());
            resultList.add(beanList);
        }
        return resultList;
    }

    /**
     * 去除发货单ID 为空的数据
     *
     * @param deliveryDetailList 页面获取的详细列表
     * @return deliveryDetailList
     */
    private List<LogisticsDeliveryOrderDetail> removeDeliveryIdEmpty(List<LogisticsDeliveryOrderDetail> deliveryDetailList) {
        for (int i = deliveryDetailList.size() - 1; i >= 0; i--) {
            if (StringUtils.isEmpty(deliveryDetailList.get(i).getDistributionOrderId())) {
                deliveryDetailList.remove(i);
            }
        }
        return deliveryDetailList;
    }
}

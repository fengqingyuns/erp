package com.hanyun.scm.api.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.IdGenerateType;
import com.hanyun.scm.api.consts.MapConsts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.StockCheckOrderDao;
import com.hanyun.scm.api.dao.StockCheckOrderDetailDao;
import com.hanyun.scm.api.dao.StockCheckTaskDetailDao;
import com.hanyun.scm.api.domain.GoodsOdoo;
import com.hanyun.scm.api.domain.StockCheckOrder;
import com.hanyun.scm.api.domain.StockCheckOrderDetail;
import com.hanyun.scm.api.domain.StockCheckTaskDetail;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceCreateRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceModifyRequest;
import com.hanyun.scm.api.domain.request.stock.StockCheckOrderCreateRequest;
import com.hanyun.scm.api.domain.request.stock.StockCheckOrderModifyRequest;
import com.hanyun.scm.api.domain.request.stock.StockCheckOrderQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.exception.ProcessInstanceException;
import com.hanyun.scm.api.exception.StockCheckOrderException;
import com.hanyun.scm.api.service.*;
import com.hanyun.scm.api.utils.ActivitiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * 盘点单service
 * Date: 16/6/22
 * Time: 下午1:51
 *
 * @author tianye@hanyun.com
 */
@Service
public class StockCheckOrderServiceImpl implements StockCheckOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockCheckOrderServiceImpl.class);

    @Resource
    private StockCheckOrderDao stockCheckOrderDao;

    @Resource
    private StockCheckOrderDetailDao stockCheckOrderDetailDao;

    @Resource
    private IdGenerateSeqService idGenerateSeqService;

    @Resource
    private ProcessInstanceService processInstanceService;

    @Resource
    private ProcessDefinitionService processDefinitionService;

    @Resource
    private GoodsOdooService goodsOdooService;

    @Resource
    private StockCheckTaskDetailDao stockCheckTaskDetailDao;

    @Override
    public HttpResponse create(StockCheckOrderCreateRequest stockCheckOrderCreateRequest) {
        try {
            List<StockCheckOrderDetail> orderDetailList = removeEmpty(stockCheckOrderCreateRequest.getStockCheckOrderDetailList());
            String brandId;
            String stockCheckOrderId = stockCheckOrderCreateRequest.getStockCheckOrderId();
            String stockCheckOrderDocumentId = stockCheckOrderCreateRequest.getStockCheckOrderDocumentId();
            if (orderDetailList == null || orderDetailList.isEmpty()) {
                throw new StockCheckOrderException("添加盘点单--盘点list为空");
            }
            if (StringUtils.isEmpty(stockCheckOrderId)) {
                brandId = stockCheckOrderCreateRequest.getBrandId();
                //新增盘点单据信息
                stockCheckOrderId = IdUtil.uuid();
                stockCheckOrderDocumentId = idGenerateSeqService.generateId(IdGenerateType.PD);
                if (StringUtils.isEmpty(stockCheckOrderDocumentId)) {
                    LOGGER.error("盘点单号生成失败！");
                    return HttpResponse.failure(ResultCode.STOCK_CHECK_CREATE_ERROR);
                }
                stockCheckOrderCreateRequest.setStockCheckOrderId(stockCheckOrderId);
                stockCheckOrderCreateRequest.setStockCheckOrderDocumentId(stockCheckOrderDocumentId);
                Integer row = stockCheckOrderDao.insert(stockCheckOrderCreateRequest);
                if (row <= 0) {
                    LOGGER.error("创建盘点单失败！");
                    return HttpResponse.failure(ResultCode.STOCK_CHECK_CREATE_ERROR);
                }
                //新增盘点详细
                for (StockCheckOrderDetail orderDetail : orderDetailList) {
                    int rowDetail = insertStockCheckOrderDetail(orderDetail, stockCheckOrderCreateRequest);
                    if (rowDetail <= 0) {
                        LOGGER.error("创建盘点详情失败！");
                        return HttpResponse.failure(ResultCode.STOCK_CHECK_DETAIL_CREATE_ERROR);
                    }
                }
            } else {
                //修改盘点单据信息
                StockCheckOrderModifyRequest orderModify = new StockCheckOrderModifyRequest();
                orderModify.setStockCheckOrderId(stockCheckOrderId);
                orderModify.setRemark(stockCheckOrderCreateRequest.getRemark());
                StockCheckOrder old = stockCheckOrderDao.selectByStockCheckOrderId(stockCheckOrderId);
                if (old == null) {
                    LOGGER.error("盘点单号:[" + stockCheckOrderId + "]数据不存在。");
                    return HttpResponse.failure(ResultCode.STOCK_CHECK_DATA_NOT_FOUND);
                }
                if (old.getStockCheckOrderStatus() != null && old.getStockCheckOrderStatus() > Consts.ORDER_STATUS_COMMITED) {
                    LOGGER.error("盘点单已提审,不允许编辑");
                    return HttpResponse.failure(ResultCode.STOCK_CHECK_ORDER_COMMITED);
                }
                brandId = old.getBrandId();
                Integer updateRow = stockCheckOrderDao.updateByStockCheckOrderId(orderModify);
                if (updateRow <= 0) {
                    LOGGER.error("更新盘点单据失败！");
                    return HttpResponse.failure(ResultCode.STOCK_CHECK_MODIFY_ERROR);
                }
                //更新或添加详细信息
                StockCheckOrderDetail stockOrderDetail = new StockCheckOrderDetail();
                stockOrderDetail.setStockCheckOrderId(stockCheckOrderId);
                List<StockCheckOrderDetail> oldDetailList = stockCheckOrderDetailDao.select(stockOrderDetail);

                for (StockCheckOrderDetail newDetail : orderDetailList) {
                    boolean insertFlag = true;
                    String newGoodsId = newDetail.getGoodsId();            //页面传来的商品id
                    String remark = newDetail.getRemark();
                    Long stockNum = newDetail.getStockNum();
                    for (StockCheckOrderDetail oldDetail : oldDetailList) {
                        String oldGoodsId = oldDetail.getGoodsId();        //数据库里的商品id
                        String oldStockCheckOrderDetailId = oldDetail.getStockCheckOrderDetailId();
                        StockCheckOrderDetail oldDetailDB = stockCheckOrderDetailDao.selectByStockCheckOrderDetailId(oldStockCheckOrderDetailId);
                        if (oldDetailDB == null) {
                            LOGGER.error("盘点详细单号：[" + oldStockCheckOrderDetailId + "]不存在。");
                            return HttpResponse.failure(ResultCode.STOCK_CHECK_DETAIL_DATA_NOT_FOUND);
                        }
                        if (!newGoodsId.equalsIgnoreCase(oldGoodsId)) {
                            continue;
                        }
                        //相同更新 不相同插入
                        Integer rowDetail = updateStockCheckOrderDetail(oldStockCheckOrderDetailId, stockNum, remark);
                        if (rowDetail <= 0) {
                            LOGGER.error("更新盘点详细失败！");
                            return HttpResponse.failure(ResultCode.STOCK_CHECK_DETAIL_MODIFY_ERROR);
                        }
                        insertFlag = false;
                        break;
                    }
                    if (insertFlag) {
                        int insertRow = insertStockCheckOrderDetail(newDetail, stockCheckOrderCreateRequest);
                        if (insertRow <= 0) {
                            LOGGER.error("创建盘点详情失败！");
                            return HttpResponse.failure(ResultCode.STOCK_CHECK_DETAIL_CREATE_ERROR);
                        }
                    }
                }
                //删除盘点详细
                for (StockCheckOrderDetail oldDetail : oldDetailList) {
                    String stockCheckOrderDetailId = oldDetail.getStockCheckOrderDetailId();
                    String goodsIdDB = oldDetail.getGoodsId();
                    boolean deleteFlag = true;
                    for (StockCheckOrderDetail newDetail : orderDetailList) {
                        String goodsId = newDetail.getGoodsId();
                        if (goodsIdDB.equalsIgnoreCase(goodsId)) {
                            deleteFlag = false;
                        }
                    }
                    if (deleteFlag) {
                        StockCheckOrderDetail oldDB = stockCheckOrderDetailDao.selectByStockCheckOrderDetailId(stockCheckOrderDetailId);
                        if (oldDB == null) {
                            LOGGER.error("盘点详细单号：[" + stockCheckOrderDetailId + "]不存在。");
                            return HttpResponse.failure(ResultCode.STOCK_CHECK_DETAIL_DATA_NOT_FOUND);
                        }
                        int rowDelete = stockCheckOrderDetailDao.deleteByStockCheckOrderDetailId(stockCheckOrderDetailId);
                        if (rowDelete <= 0) {
                            LOGGER.error("删除盘点详细失败！");
                            return HttpResponse.failure(ResultCode.STOCK_CHECK_DETAIL_DELETE_ERROR);
                        }
                    }
                }
            }
            boolean existStatus = processDefinitionService.exist(brandId, Consts.PROCESS_TYPE_STOCK_CHECK_ORDER);
            if (existStatus) {
                return HttpResponse.success(new String[]{stockCheckOrderId, stockCheckOrderDocumentId, "exist"});
            }
            return HttpResponse.success(new String[]{stockCheckOrderId, stockCheckOrderDocumentId});
        } catch (Exception e) {
            LOGGER.error("创建盘点单信息失败", e);
            return HttpResponse.failure(ResultCode.STOCK_CHECK_SYSTEM_ERROR);
        }
    }

    // 更新盘点单据详细
    private int updateStockCheckOrderDetail(String stockCheckOrderDetailId, Long stockNum, String remark) {
        StockCheckOrderDetail stockCheckOrderDetail = new StockCheckOrderDetail();
        stockCheckOrderDetail.setStockCheckOrderDetailId(stockCheckOrderDetailId);
        stockCheckOrderDetail.setStockNum(stockNum);
        stockCheckOrderDetail.setRemark(remark);

        return stockCheckOrderDetailDao.updateByStockCheckOrderDetailId(stockCheckOrderDetail);
    }

    // 插入盘点单据详细
    private int insertStockCheckOrderDetail(StockCheckOrderDetail orderDetail, StockCheckOrderCreateRequest stockCheckOrder) {
        String stockCheckOrderDetailId = IdUtil.uuid();
        orderDetail.setStockCheckOrderId(stockCheckOrder.getStockCheckOrderId());
        orderDetail.setStockCheckOrderDetailId(stockCheckOrderDetailId);
        orderDetail.setBrandId(stockCheckOrder.getBrandId());
        orderDetail.setStoreId(stockCheckOrder.getStoreId());
        orderDetail.setWarehouseId(stockCheckOrder.getWarehouseId());
        orderDetail.setGoodsStatus(Consts.GOODS_ENABLE_STATUS);
        orderDetail.setStockCheckTaskId(stockCheckOrder.getStockCheckTaskId());

        return stockCheckOrderDetailDao.insert(orderDetail);
    }

    @Override
    public HttpResponse detail(String stockCheckOrderId, StockCheckOrderDetail record) {
        try {
            StockCheckOrder stockCheckOrder = stockCheckOrderDao.selectByStockCheckOrderId(stockCheckOrderId);
            if (stockCheckOrder == null) {
                LOGGER.error("盘点单号:[" + stockCheckOrderId + "]不存在。");
                return HttpResponse.failure(ResultCode.STOCK_CHECK_DATA_NOT_FOUND);
            }
            StockCheckOrderDetail stockCheckOrderDetail = new StockCheckOrderDetail();
            stockCheckOrderDetail.setStockCheckOrderId(stockCheckOrder.getStockCheckOrderId());
            stockCheckOrderDetail.setPageNum(record != null && record.getPageNum() != null ? record.getPageNum() : null);
            stockCheckOrderDetail.setPageSize(record != null && record.getPageSize() != null ? record.getPageSize() : null);
            int count = stockCheckOrderDetailDao.countAll(stockCheckOrderDetail);
            stockCheckOrderDetail.setCount(count);
            List<StockCheckOrderDetail> stockCheckOrderDetailList = stockCheckOrderDetailDao.select(stockCheckOrderDetail);
            Map<String, GoodsOdoo> map = goodsOdooService.getStockCheckOrderGoodsMap(stockCheckOrder.getBrandId(), stockCheckOrderDetailList);
            for (StockCheckOrderDetail detail : stockCheckOrderDetailList) {
                String goodsId = detail.getGoodsId();
                if (map != null && map.get(goodsId) != null && !StringUtils.isEmpty(map.get(goodsId).getGoodsPic())) {
                    detail.setGoodsPic(map.get(goodsId).getGoodsPic());
                } else {
                    detail.setGoodsPic("");
                }
            }
            stockCheckOrder.setStockCheckOrderDetailList(stockCheckOrderDetailList);
            return HttpResponse.success(stockCheckOrder);
        } catch (Exception e) {
            LOGGER.error("查询库存盘点详情失败!", e);
            return HttpResponse.failure(ResultCode.STOCK_CHECK_SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse select(StockCheckOrderQueryRequest stockCheckOrderQueryRequest) {
        try {
            String singleBeginTime = stockCheckOrderQueryRequest.getSingleBeginTime();
            String singleEndTime = stockCheckOrderQueryRequest.getSingleEndTime();
            String auditBeginTime = stockCheckOrderQueryRequest.getAuditBeginTime();
            String auditEndTime = stockCheckOrderQueryRequest.getAuditEndTime();
            if (!StringUtils.isEmpty(singleBeginTime)) {
                stockCheckOrderQueryRequest.setSingleBeginTime(singleBeginTime.replace("T", " "));
            }
            if (!StringUtils.isEmpty(singleEndTime)) {
                stockCheckOrderQueryRequest.setSingleEndTime(singleEndTime.replace("T", " "));
            }
            if (!StringUtils.isEmpty(auditBeginTime)) {
                stockCheckOrderQueryRequest.setAuditBeginTime(auditBeginTime.replace("T", " "));
            }
            if (!StringUtils.isEmpty(auditEndTime)) {
                stockCheckOrderQueryRequest.setAuditEndTime(auditEndTime.replace("T", " "));
            }
            Integer count = stockCheckOrderDao.countAll(stockCheckOrderQueryRequest);
            stockCheckOrderQueryRequest.setCount(count);
            List<StockCheckOrder> list = stockCheckOrderDao.select(stockCheckOrderQueryRequest);
            if (!StringUtils.isEmpty(stockCheckOrderQueryRequest.getUserId())) {
                for (StockCheckOrder stockCheckOrder : list) {
                    stockCheckOrder.setAuditStatus(processInstanceService.queryAuditor(stockCheckOrder.getStockCheckOrderId(), stockCheckOrderQueryRequest.getUserId()));
                    List<String> auditors = processInstanceService.queryAuditors(stockCheckOrder.getStockCheckOrderId());
                    auditors.add(stockCheckOrder.getOperatorId());
                    stockCheckOrder.setHistoryStatus(auditors.size() > 1 && auditors.contains(stockCheckOrderQueryRequest.getUserId()));
                }
            }
            BaseResponse response = new BaseResponse(count, list);
            return HttpResponse.success(response);
        } catch (Exception e) {
            LOGGER.error("查询盘点单列表失败。", e);
            return HttpResponse.failure(ResultCode.STOCK_CHECK_SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse modify(StockCheckOrderModifyRequest stockCheckOrderModifyRequest) {
        try {
            StockCheckOrder old = stockCheckOrderDao.selectByStockCheckOrderId(stockCheckOrderModifyRequest.getStockCheckOrderId());
            if (old == null) {
                LOGGER.error("盘点单号:[" + stockCheckOrderModifyRequest.getStockCheckOrderId() + "]不存在。");
                return HttpResponse.failure(ResultCode.STOCK_CHECK_DATA_NOT_FOUND);
            }
            if (stockCheckOrderModifyRequest.getStockCheckOrderStatus() != null && stockCheckOrderModifyRequest.getStockCheckOrderStatus() == Consts.ORDER_STATUS_CONFIRMED) {
                stockCheckOrderModifyRequest.setStockCheckOrderStatus(Consts.ORDER_STATUS_CONFIRMED);
            } else {
                if (!stockCheckOrderModifyRequest.getAuditStatus()) {
                    StockCheckOrderModifyRequest modify = new StockCheckOrderModifyRequest();
                    modify.setStockCheckOrderId(old.getStockCheckOrderId());
                    modify.setStockCheckOrderStatus(Consts.ORDER_STATUS_SAVED);
                    int row = stockCheckOrderDao.updateByStockCheckOrderId(modify);
                    if (row == 0) {
                        return HttpResponse.failure(ResultCode.STOCK_CHECK_ORDER_CONFIRM_ERROR);
                    }
                    processInstanceService.delete(old.getStockCheckOrderId());
                    return HttpResponse.success();
                }
                ProcessInstanceModifyRequest processInstanceModifyRequest = new ProcessInstanceModifyRequest();
                processInstanceModifyRequest.setBusinessId(stockCheckOrderModifyRequest.getStockCheckOrderId());
                processInstanceModifyRequest.setUserId(stockCheckOrderModifyRequest.getUserId());
                processInstanceModifyRequest.setRemark("通过, 执行");
                HttpResponse response = processInstanceService.audit(processInstanceModifyRequest);
                if (!StringUtils.equals(response.getCode(), "0") || response.getData() == null) {
                    return HttpResponse.failure(ResultCode.STOCK_CHECK_ORDER_CONFIRM_ERROR);
                }
                Integer instanceStatus = Integer.parseInt(response.getData().toString());
                if (instanceStatus != Consts.PROCESS_INSTANCE_STATUS_CONFIRMED) {
                    stockCheckOrderModifyRequest.setStockCheckOrderStatus(Consts.ORDER_STATUS_CONFIRMING);
                } else {
                    stockCheckOrderModifyRequest.setStockCheckOrderStatus(Consts.ORDER_STATUS_CONFIRMED);
                }
            }
            int row = stockCheckOrderDao.updateByStockCheckOrderId(stockCheckOrderModifyRequest);
            if (row <= 0) {
                LOGGER.error("更新盘点单据信息失败!");
                return HttpResponse.failure(ResultCode.STOCK_CHECK_MODIFY_ERROR);
            }
            stockCheckOrderDetailDao.audit(stockCheckOrderModifyRequest.getStockCheckOrderId());
            //处理盘点商品更新任务单的商品盘点数
            String brandId = old.getBrandId();
            String taskId = old.getStockCheckTaskId();
            StockCheckOrderDetail record = new StockCheckOrderDetail();
            record.setBrandId(brandId);
            record.setStockCheckOrderId(old.getStockCheckOrderId());
            List<StockCheckOrderDetail> detailList = stockCheckOrderDetailDao.select(record);
            Map<String, StockCheckTaskDetail> map = getTaskDetailMap(brandId,taskId);
            detailList.forEach(sd -> {
                String goodsId = sd.getGoodsId();
                Long stockNum = sd.getStockNum();       //盘点数量
                if(map!=null && map.size() > 0 && map.get(brandId + taskId + goodsId) != null){
                    StockCheckTaskDetail detail = map.get(brandId + taskId + goodsId);
                    detail.setCheckStock(detail.getCheckStock() + stockNum);
                    stockCheckTaskDetailDao.updateByStockCheckTaskDetailId(detail);
                }
            });
            return HttpResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("更新盘点单据信息失败。", e);
            return HttpResponse.failure(ResultCode.STOCK_CHECK_SYSTEM_ERROR);
        }
    }

    /**
     * 获取任务单下的商品并放到map中
     * @param brandId 品牌id
     * @param taskId  任务单id
     * @return map
     */
    private Map<String, StockCheckTaskDetail> getTaskDetailMap(String brandId, String taskId){
        Map<String, StockCheckTaskDetail> map = new HashMap<>();
        StockCheckTaskDetail query = new StockCheckTaskDetail();
        query.setBrandId(brandId);
        query.setStockCheckId(taskId);
        List<StockCheckTaskDetail> list = stockCheckTaskDetailDao.select(query);
        list.forEach(s -> map.put(brandId+taskId+s.getGoodsId(),s));
        return map;
    }

    @Override
    public HttpResponse delete(String stockCheckOrderId) {
        try {
            StockCheckOrder old = stockCheckOrderDao.selectByStockCheckOrderId(stockCheckOrderId);
            if (old == null) {
                LOGGER.error("盘点单号:[" + stockCheckOrderId + "]不存在。");
                return HttpResponse.failure(ResultCode.STOCK_CHECK_DATA_NOT_FOUND);
            }
            Integer row = stockCheckOrderDao.deleteByStockCheckOrderId(stockCheckOrderId);
            if (row <= 0) {
                LOGGER.error("删除盘点单失败。");
                return HttpResponse.failure(ResultCode.STOCK_CHECK_DELETE_ERROR);
            }
            StockCheckOrderDetail detail = new StockCheckOrderDetail();
            detail.setBrandId(old.getBrandId());
            detail.setStoreId(old.getStoreId());
            detail.setStockCheckOrderId(stockCheckOrderId);
            List<StockCheckOrderDetail> detailList = stockCheckOrderDetailDao.select(detail);
            if (detailList == null || detailList.isEmpty()) {
                throw new StockCheckOrderException("删除查询的详单list为空");
            }
            boolean flag = true;
            for (StockCheckOrderDetail stockCheckOrderDetail : detailList) {
                Integer detailRow = stockCheckOrderDetailDao.deleteByStockCheckOrderDetailId(stockCheckOrderDetail.getStockCheckOrderDetailId());
                if (detailRow <= 0) {
                    flag = false;
                }
            }
            if (!flag) {
                throw new StockCheckOrderException("删除盘点详细失败!");
            }
            return HttpResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("删除盘点失败。", e);
            return HttpResponse.failure(ResultCode.STOCK_CHECK_SYSTEM_ERROR);
        }
    }

    @Override
    public BaseResponse selectList(StockCheckOrderQueryRequest stockCheckOrderQueryRequest, boolean excel) throws StockCheckOrderException {
        try {
            int count = stockCheckOrderDao.countAll(stockCheckOrderQueryRequest);
            stockCheckOrderQueryRequest.setCount(count);
            List<StockCheckOrder> stockCheckList = stockCheckOrderDao.select(stockCheckOrderQueryRequest);
            if (excel) {
                for (StockCheckOrder stockCheckOrder : stockCheckList) {
                    stockCheckOrder.setStockCheckOrderStatusName(MapConsts.getOrderStatus().get(stockCheckOrder.getStockCheckOrderStatus()));
                    stockCheckOrder.setStockCheckTaskTypeName(MapConsts.getStockCheckTaskType().get(stockCheckOrder.getStockCheckTaskType()));
                    stockCheckOrder.setStockCheckOrderTypeName(MapConsts.getStockCheckPurchaseType().get(stockCheckOrder.getStockCheckOrderType()));
                }
            }
            return new BaseResponse(count, stockCheckList);
        } catch (Exception e) {
            LOGGER.error("查询库存盘点单列表失败!", e);
            throw new StockCheckOrderException("查询库存盘点单列表失败!", e);
        }
    }

    /**
     * 去除goodsId 为空的数据
     *
     * @param orderDetailList
     * @return
     */
    private List<StockCheckOrderDetail> removeEmpty(List<StockCheckOrderDetail> orderDetailList) {
        for (int i = orderDetailList.size() - 1; i >= 0; i--) {
            if (StringUtils.isEmpty(orderDetailList.get(i).getGoodsId())) {
                orderDetailList.remove(i);
            }
        }
        return orderDetailList;
    }

    @Override
    public List<StockCheckOrderDetail> exportCheckOrderUpdate(StockCheckOrderQueryRequest stockCheckOrderQueryRequest) throws StockCheckOrderException {
        List<StockCheckOrderDetail> stockCheckOrderDetailList;
        StockCheckOrderDetail stockCheckOrderDetail = new StockCheckOrderDetail();
        stockCheckOrderDetail.setStockCheckOrderId(stockCheckOrderQueryRequest.getStockCheckOrderId());

        try {
            stockCheckOrderDetailList = stockCheckOrderDetailDao.select(stockCheckOrderDetail);
            for (StockCheckOrderDetail detail : stockCheckOrderDetailList) {
                detail.setTotalPrice(detail.getUnitPrice() * detail.getStockNum());
            }
        } catch (Exception e) {
            LOGGER.error("导出库存盘点单详单失败!", e);
            throw new StockCheckOrderException("查询库存盘点单列表失败!", e);
        }

        return stockCheckOrderDetailList;
    }

    @Override
    public HttpResponse commit(String stockCheckOrderId) {
        try {
            StockCheckOrder old = stockCheckOrderDao.selectByStockCheckOrderId(stockCheckOrderId);
            if (old == null) {
                return HttpResponse.failure(ResultCode.STOCK_CHECK_ORDER_COMMIT_ERROR);
            }
            StockCheckOrderModifyRequest modify = new StockCheckOrderModifyRequest();
            modify.setStockCheckOrderId(stockCheckOrderId);
            modify.setStockCheckOrderStatus(Consts.ORDER_STATUS_COMMITED);
            int modifyRow = stockCheckOrderDao.updateByStockCheckOrderId(modify);
            if (modifyRow == 0) {
                return HttpResponse.failure(ResultCode.STOCK_CHECK_ORDER_COMMIT_ERROR);
            }
            ProcessInstanceCreateRequest create = new ProcessInstanceCreateRequest();
            create.setProcessType(Consts.PROCESS_TYPE_STOCK_CHECK_ORDER);
            create.setBrandId(old.getBrandId());
            create.setBusinessId(stockCheckOrderId);
            ActivitiUtil.getInstance();
            HttpResponse response = processInstanceService.create(create, old.getOperatorId());
            if (!StringUtils.equals(response.getCode(), "0")) {
                throw new ProcessInstanceException("提审失败!");
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("提审失败!", e);
            return HttpResponse.failure(ResultCode.STOCK_CHECK_ORDER_COMMIT_ERROR);
        }
    }
}

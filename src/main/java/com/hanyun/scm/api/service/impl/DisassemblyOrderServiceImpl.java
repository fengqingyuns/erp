package com.hanyun.scm.api.service.impl;

import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.IdGenerateType;
import com.hanyun.scm.api.consts.MapConsts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.DisassemblyOrderDao;
import com.hanyun.scm.api.dao.DisassemblyOrderDetailDao;
import com.hanyun.scm.api.domain.DisassemblyOrder;
import com.hanyun.scm.api.domain.DisassemblyOrderDetail;
import com.hanyun.scm.api.domain.request.disassembly.DisassemblyOrderCreateRequest;
import com.hanyun.scm.api.domain.request.disassembly.DisassemblyOrderModifyRequest;
import com.hanyun.scm.api.domain.request.disassembly.DisassemblyOrderQueryRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceCreateRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceModifyRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.domain.response.ListResponse;
import com.hanyun.scm.api.exception.DisassemblyException;
import com.hanyun.scm.api.exception.ProcessInstanceException;
import com.hanyun.scm.api.service.*;
import com.hanyun.scm.api.utils.ActivitiUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
 * Date: 17/1/13
 * Time: 下午17:01
 *
 * @author 1007661792@qq.com
 */
@Service
public class DisassemblyOrderServiceImpl implements DisassemblyOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisassemblyOrderServiceImpl.class);

    @Resource
    private DisassemblyOrderDao disassemblyOrderDao;

    @Resource
    private DisassemblyOrderDetailDao disassemblyOrderDetailDao;

    @Resource
    private StockQuantService stockQuantService;

    @Resource
    private IdGenerateSeqService idGenerateSeqService;

    @Resource
    private ProcessInstanceService processInstanceService;

    @Resource
    private ProcessDefinitionService processDefinitionService;

    @Override
    public HttpResponse create(DisassemblyOrderCreateRequest disassembly) {
        try {
            String disassemblyOrderId = disassembly.getDisassemblyOrderId();        //拆装单id
            String disassemblyOrderDocumentId = disassembly.getDisassemblyOrderDocumentId();
            Integer disassemblyOrderType = disassembly.getDisassemblyOrderType();   //类型
            List<DisassemblyOrderDetail> srcList = null;
            List<DisassemblyOrderDetail> toList = null;
            if (disassemblyOrderType == Consts.DISASSEMBLY_ASSEMBLE_TYPE) {
                srcList = removeSrcGoodsId(disassembly.getSrcList());        //出库list
                toList = disassembly.getToList();
            } else if (disassemblyOrderType == Consts.DISASSEMBLY_SPLIT_TYPE) {
                srcList = disassembly.getSrcList();
                toList = removeToGoodsId(disassembly.getToList());
            }
            if (srcList == null || srcList.isEmpty() || toList == null || toList.isEmpty()) {
                throw new DisassemblyException("添加拆装单出/入库list为空");
            }
            //根据传过来的disassemeblyorderId 判断是否是增加还是修改
            if (StringUtils.isEmpty(disassemblyOrderId)) {
                //新增拆装单单据信息
                disassemblyOrderId = IdUtil.uuid();
                disassemblyOrderDocumentId = idGenerateSeqService.generateId(IdGenerateType.CZD);
                disassembly.setDisassemblyOrderId(disassemblyOrderId);
                disassembly.setDisassemblyOrderDocumentId(disassemblyOrderDocumentId);
                Integer insertRow = disassemblyOrderDao.insert(disassembly);
                if (insertRow <= 0) {
                    LOGGER.error("创建拆装单失败！");
                    return HttpResponse.failure(ResultCode.DISASSEMBLYORDER_CREATE_ERROR);
                }
            } else {     //修改
                DisassemblyOrderModifyRequest orderModify = new DisassemblyOrderModifyRequest();
                orderModify.setDisassemblyOrderId(disassemblyOrderId);
                orderModify.setRemark(disassembly.getRemark());
                DisassemblyOrder old = disassemblyOrderDao.selectByDisassemblyOrderId(disassemblyOrderId);
                if (old == null) {
                    LOGGER.error("拆装单号:[" + disassemblyOrderId + "]数据不存在！");
                    return HttpResponse.failure(ResultCode.DISASSEMBLYORDER_DATA_NOT_FOUND);
                }
                Integer updateOrderRow = disassemblyOrderDao.updateByDisassemblyOrderId(orderModify);
                if (updateOrderRow <= 0) {
                    LOGGER.error("更新拆装单据失败!");
                    return HttpResponse.failure(ResultCode.DISASSEMBLYORDER_MODIFY_ERROR);
                }
                int deleteDetailRow = disassemblyOrderDetailDao.deleteByDisassemblyOrderId(old.getDisassemblyOrderId());
                if(deleteDetailRow <= 0){
                    LOGGER.error("删除拆装单详情失败");
                    throw new DisassemblyException("删除拆装单详情失败。");
                }
            }

            //新增拆装单单据详细信息
            //添加出库详细信息
            for (DisassemblyOrderDetail detail : srcList) {
                Integer insertDetailRow = insertOrderSrcDetail(detail, disassembly, disassemblyOrderType);
                if (insertDetailRow <= 0) {
                    LOGGER.error("创建详细单据信息失败");
                    return HttpResponse.failure(ResultCode.DISASSEMBLYORDER_CREATE_DETAIL_ERROR);
                }
            }
            //添加入库详细信息
            for (DisassemblyOrderDetail detail : toList) {
                Integer insertDetailRow = insertOrderToDetail(detail, disassembly, disassemblyOrderType);
                if (insertDetailRow <= 0) {
                    LOGGER.error("创建详细单据信息失败");
                    return HttpResponse.failure(ResultCode.DISASSEMBLYORDER_CREATE_DETAIL_ERROR);
                }
            }


            boolean existStatus = processDefinitionService.exist(disassembly.getBrandId(), Consts.PROCESS_TYPE_DISASSEMBLY_ORDER);
            if (existStatus) {
                return HttpResponse.success(new String[]{disassemblyOrderId, disassemblyOrderDocumentId, "exist"});
            }
            return HttpResponse.success(new String[]{disassemblyOrderId, disassemblyOrderDocumentId});
        } catch (Exception e) {
            LOGGER.error("创建拆装单失败", e);
            return HttpResponse.failure(ResultCode.DISASSEMBLYORDER_SYSTEM_ERROR);
        }
    }

    /**
     * 插入出库单据详细信息
     */
    private Integer insertOrderSrcDetail(DisassemblyOrderDetail detail, DisassemblyOrderCreateRequest disassembly, int orderType) {
        detail.setBrandId(disassembly.getBrandId());
        if (!StringUtils.isEmpty(disassembly.getStoreId())) {
            detail.setStoreId(disassembly.getStoreId());
        }
        detail.setDisassemblyOrderDetailId(IdUtil.uuid());
        detail.setDisassemblyOrderId(disassembly.getDisassemblyOrderId());
        detail.setDisassemblyGoodsType(orderType == Consts.DISASSEMBLY_ASSEMBLE_TYPE ? Consts.SON_LIST_TYPE : Consts.MOTHER_LIST_TYPE);
        return disassemblyOrderDetailDao.insert(detail);
    }

    /**
     * 插入入库单据详细信息
     */
    private Integer insertOrderToDetail(DisassemblyOrderDetail detail, DisassemblyOrderCreateRequest disassembly, int orderType) {
        detail.setBrandId(disassembly.getBrandId());
        if (!StringUtils.isEmpty(disassembly.getStoreId())) {
            detail.setStoreId(disassembly.getStoreId());
        }
        if (detail.getUnitPrice() == null) {
            detail.setUnitPrice(0L);
        }
        detail.setDisassemblyOrderDetailId(IdUtil.uuid());
        detail.setDisassemblyOrderId(disassembly.getDisassemblyOrderId());
        detail.setDisassemblyGoodsType(orderType == Consts.DISASSEMBLY_ASSEMBLE_TYPE ? Consts.MOTHER_LIST_TYPE : Consts.SON_LIST_TYPE);
        return disassemblyOrderDetailDao.insert(detail);
    }


    @Override
    public HttpResponse select(DisassemblyOrderQueryRequest disassemblyOrderQueryRequest) {
        try {
            String queryBeginTime = disassemblyOrderQueryRequest.getQueryBeginTime();
            String queryEndTime = disassemblyOrderQueryRequest.getQueryEndTime();
            if (!StringUtils.isEmpty(queryBeginTime)) {
                disassemblyOrderQueryRequest.setQueryBeginTime(queryBeginTime.replace("T", " "));
            }
            if (!StringUtils.isEmpty(queryEndTime)) {
                disassemblyOrderQueryRequest.setQueryEndTime(queryEndTime.replace("T", " "));
            }
            Integer count = disassemblyOrderDao.countAll(disassemblyOrderQueryRequest);
            disassemblyOrderQueryRequest.setCount(count);
            List<DisassemblyOrder> list = disassemblyOrderDao.select(disassemblyOrderQueryRequest);
            if (!StringUtils.isEmpty(disassemblyOrderQueryRequest.getUserId())) {
                for (DisassemblyOrder order : list) {
                    order.setAuditStatus(processInstanceService.queryAuditor(order.getDisassemblyOrderId(), disassemblyOrderQueryRequest.getUserId()));
                    List<String> auditors = processInstanceService.queryAuditors(order.getDisassemblyOrderId());
                    auditors.add(order.getMakeorderManId());
                    order.setHistoryStatus(auditors.size() > 1 && auditors.contains(disassemblyOrderQueryRequest.getUserId()));
                }
            }
            //查询详单的母件商品对象和子件list
            for (DisassemblyOrder query : list) {
                ListResponse getResponse = getDetailList(query);
                if (getResponse.getDetail() != null) {
                    query.setDisassemblyOrderDetail(getResponse.getDetail());
                }
                if (getResponse.getSonList() != null || !getResponse.getSonList().isEmpty()) {
                    query.setSonList(getResponse.getSonList());
                }
            }
            BaseResponse response = new BaseResponse(count, list);
            return HttpResponse.success(response);
        } catch (Exception e) {
            LOGGER.error("查询拆装单失败", e);
            return HttpResponse.failure(ResultCode.DISASSEMBLYORDER_SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse modify(DisassemblyOrderModifyRequest disassemblyOrderModifyRequest) {
        try {
            //审核----------
            String disassemblyOrderId = disassemblyOrderModifyRequest.getDisassemblyOrderId();
            String examineId = disassemblyOrderModifyRequest.getExamineId();
            String examineName = disassemblyOrderModifyRequest.getExamineName();
            DisassemblyOrder old = disassemblyOrderDao.selectByDisassemblyOrderId(disassemblyOrderId);
            if (old == null) {
                LOGGER.error("拆装单号:[" + disassemblyOrderModifyRequest.getDisassemblyOrderId() + "]不存在!");
                return HttpResponse.failure(ResultCode.DISASSEMBLYORDER_DATA_NOT_FOUND);
            }
            if (disassemblyOrderModifyRequest.getDisassemblyOrderStatus() != null && disassemblyOrderModifyRequest.getDisassemblyOrderStatus() == Consts.DISASSEMBLY_ORDER_STATUS_CONFIRMED) {
                old.setDisassemblyOrderStatus(Consts.DISASSEMBLY_ORDER_STATUS_CONFIRMED);
            } else {
                if (!disassemblyOrderModifyRequest.getAuditStatus()) {
                    DisassemblyOrderModifyRequest modify = new DisassemblyOrderModifyRequest();
                    modify.setDisassemblyOrderId(disassemblyOrderId);
                    modify.setExamineId(examineId);
                    modify.setExamineName(examineName);
                    modify.setExamineTime(new Date());
                    modify.setDisassemblyOrderStatus(Consts.DISASSEMBLY_ORDER_STATUS_SAVE);
                    int row = disassemblyOrderDao.updateByDisassemblyOrderId(modify);
                    if (row == 0) {
                        HttpResponse.failure(ResultCode.PURCHASE_ORDER_CONFIRM_ERROR);
                    }
                    processInstanceService.delete(disassemblyOrderId);
                    return HttpResponse.success();
                }

                ProcessInstanceModifyRequest processInstanceModifyRequest = new ProcessInstanceModifyRequest();
                processInstanceModifyRequest.setBusinessId(disassemblyOrderModifyRequest.getDisassemblyOrderId());
                processInstanceModifyRequest.setUserId(disassemblyOrderModifyRequest.getUserId());
                processInstanceModifyRequest.setRemark("通过, 执行");
                HttpResponse httpResponse = processInstanceService.audit(processInstanceModifyRequest);
                if (!com.alibaba.druid.util.StringUtils.equals(httpResponse.getCode(), "0") || httpResponse.getData() == null) {
                    return HttpResponse.failure(ResultCode.PURCHASE_ORDER_CONFIRM_ERROR);
                }
                Integer instanceStatus = Integer.parseInt(httpResponse.getData().toString());
                if (instanceStatus != Consts.PROCESS_INSTANCE_STATUS_CONFIRMED) {
                    old.setDisassemblyOrderStatus(Consts.DISASSEMBLY_ORDER_STATUS_CONIFRMING);
                } else {
                    old.setDisassemblyOrderStatus(Consts.DISASSEMBLY_ORDER_STATUS_CONFIRMED);
                }
            }
            DisassemblyOrderModifyRequest modifyRequest = new DisassemblyOrderModifyRequest();
            modifyRequest.setDisassemblyOrderId(disassemblyOrderId);
            modifyRequest.setExamineId(examineId);
            modifyRequest.setExamineName(examineName);
            modifyRequest.setExamineTime(new Date());
            modifyRequest.setDisassemblyOrderStatus(old.getDisassemblyOrderStatus());
            //审核---------
            Integer row = disassemblyOrderDao.updateByDisassemblyOrderId(modifyRequest);
            if (row <= 0) {
                LOGGER.error("更新拆装单信息失败！");
                return HttpResponse.failure(ResultCode.DISASSEMBLY_ORDER_CONFIRM_ERROR);
            }

            if (modifyRequest.getDisassemblyOrderStatus() != Consts.ORDER_STATUS_CONFIRMED) {
                return HttpResponse.success();
            }

            //查询字母商品集合
            DisassemblyOrder disassemblyOrder = new DisassemblyOrder();
            disassemblyOrder.setBrandId(disassemblyOrderModifyRequest.getBrandId());
            if (!StringUtils.isEmpty(disassemblyOrderModifyRequest.getStoreId())) {
                disassemblyOrder.setStoreId(disassemblyOrderModifyRequest.getStoreId());
            }
            disassemblyOrder.setDisassemblyOrderId(disassemblyOrderId);
            disassemblyOrder.setSrcWarehouseId(old.getSrcWarehouseId());
            disassemblyOrder.setToWarehouseId(old.getToWarehouseId());
            disassemblyOrder.setDisassemblyOrderType(old.getDisassemblyOrderType());
            ListResponse response = getDetailList(disassemblyOrder);
            //审核更新库存信息
            if (response.getDetail() != null || response.getSonList() != null || !response.getSonList().isEmpty()) {
                stockQuantService.disassemblyModifyStockQuant(response.getDetail(), response.getSonList(), old);
            }
            return HttpResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("更新拆装单失败", e);
            return HttpResponse.failure(ResultCode.DISASSEMBLYORDER_SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse commit(String disassemblyOrderId) {
        try {
            DisassemblyOrder old = disassemblyOrderDao.selectByDisassemblyOrderId(disassemblyOrderId);
            if (old == null) {
                throw new DisassemblyException("未查询到该拆装单。");
            }
            DisassemblyOrderModifyRequest modifyRequest = new DisassemblyOrderModifyRequest();
            modifyRequest.setDisassemblyOrderId(disassemblyOrderId);
            modifyRequest.setDisassemblyOrderStatus(Consts.DISASSEMBLY_ORDER_STATUS_COMMITED);
            int modifyRow = disassemblyOrderDao.updateByDisassemblyOrderId(modifyRequest);
            if (modifyRow <= 0) {
                return HttpResponse.failure(ResultCode.DISASSEMBLY_ORDER_COMMIT_ERROR);
            }
            ProcessInstanceCreateRequest processInstanceCreateRequest = new ProcessInstanceCreateRequest();
            processInstanceCreateRequest.setProcessType(Consts.PROCESS_TYPE_DISASSEMBLY_ORDER);
            processInstanceCreateRequest.setBrandId(old.getBrandId());
            processInstanceCreateRequest.setBusinessId(disassemblyOrderId);
            ActivitiUtil.getInstance();
            HttpResponse response = processInstanceService.create(processInstanceCreateRequest, old.getMakeorderManId());
            if (!com.alibaba.druid.util.StringUtils.equals(response.getCode(), "0")) {
                throw new ProcessInstanceException("提审失败!");
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("提审失败。", e);
            return HttpResponse.failure(ResultCode.DISASSEMBLY_ORDER_COMMIT_ERROR);
        }
    }

    /**
     * 获取详单的子母商品list
     *
     * @return ListResponse
     */
    private ListResponse getDetailList(DisassemblyOrder disassemblyOrder) {
        //母商品list对象
        DisassemblyOrderDetail detailMother = new DisassemblyOrderDetail();
        detailMother.setBrandId(disassemblyOrder.getBrandId());
        if (!StringUtils.isEmpty(disassemblyOrder.getStoreId())) {
            detailMother.setStoreId(disassemblyOrder.getStoreId());
        }
        detailMother.setDisassemblyOrderId(disassemblyOrder.getDisassemblyOrderId());
        detailMother.setDisassemblyGoodsType(Consts.MOTHER_LIST_TYPE);
        //子商品list对象
        DisassemblyOrderDetail detailSon = new DisassemblyOrderDetail();
        detailSon.setBrandId(disassemblyOrder.getBrandId());
        if (!StringUtils.isEmpty(disassemblyOrder.getStoreId())) {
            detailSon.setStoreId(disassemblyOrder.getStoreId());
        }
        detailSon.setDisassemblyOrderId(disassemblyOrder.getDisassemblyOrderId());
        detailSon.setDisassemblyGoodsType(Consts.SON_LIST_TYPE);
        //母商品集合->只有一条数据
        List<DisassemblyOrderDetail> motherList = disassemblyOrderDetailDao.select(detailMother);
        //母商品对象->一个对象
        DisassemblyOrderDetail detail = disassemblyOrderDetailDao.queryMotherDetailObject(detailMother);
        //子商品集合
        List<DisassemblyOrderDetail> sonList = disassemblyOrderDetailDao.select(detailSon);

        ListResponse resposne = new ListResponse(motherList, sonList, detail);
        return resposne;
    }

    @Override
    public HttpResponse detail(String disassemblyOrderId) {
        try {
            DisassemblyOrder disassemblyOrder = disassemblyOrderDao.selectByDisassemblyOrderId(disassemblyOrderId);
            if (disassemblyOrder == null) {
                LOGGER.error("拆装单号:[" + disassemblyOrderId + "]数据不存在");
                return HttpResponse.failure(ResultCode.DISASSEMBLYORDER_DATA_NOT_FOUND);
            }
            ListResponse getResponse = getDetailList(disassemblyOrder);
            if (getResponse.getMotherList() != null || !getResponse.getMotherList().isEmpty()) {
                disassemblyOrder.setMotherList(getResponse.getMotherList());
            }
            if (getResponse.getSonList() != null || !getResponse.getSonList().isEmpty()) {
                disassemblyOrder.setSonList(getResponse.getSonList());
            }
            return HttpResponse.success(disassemblyOrder);
        } catch (Exception e) {
            LOGGER.error("查询拆装单详情失败", e);
            return HttpResponse.failure(ResultCode.DISASSEMBLYORDER_SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse delete(String disassemblyOrderId) {
        try {
            DisassemblyOrder old = disassemblyOrderDao.selectByDisassemblyOrderId(disassemblyOrderId);
            if (old == null) {
                LOGGER.error("拆装单号:[" + disassemblyOrderId + "]数据不存在！");
                return HttpResponse.failure(ResultCode.DISASSEMBLYORDER_DATA_NOT_FOUND);
            }
            Integer row = disassemblyOrderDao.deleteByDisassemblyOrderId(old.getDisassemblyOrderId());
            if (row <= 0) {
                LOGGER.error("拆装单单据删除失败！");
                return HttpResponse.failure(ResultCode.DISASSEMBLYORDER_DELETE_ERROR);
            }
            int deleteDetailRow = disassemblyOrderDetailDao.deleteByDisassemblyOrderId(disassemblyOrderId);
            if(deleteDetailRow <= 0){
                LOGGER.error("删除拆装单详情失败。");
                throw new DisassemblyException("删除拆装单详情失败。");
            }
            return HttpResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("删除拆装单失败", e);
            return HttpResponse.failure(ResultCode.DISASSEMBLYORDER_SYSTEM_ERROR);
        }
    }

    @Override
    public List<List<Object>> exportExcelBySuppleir(DisassemblyOrderQueryRequest disassemblyOrder) throws DisassemblyException {
        List<List<Object>> resultList = new ArrayList<List<Object>>();//返回的数据List
        List<DisassemblyOrder> list = disassemblyOrderDao.select(disassemblyOrder);//查询的list
        Integer step = 0;
        for (DisassemblyOrder disassembly : list) {
            List<Object> beanList = new ArrayList<Object>();
            ++step;
            beanList.add(step);
            beanList.add(disassembly.getDisassemblyOrderDocumentId());
            beanList.add(disassembly.getUpdateTime());
            beanList.add(MapConsts.getDisassemblyType().get(disassembly.getDisassemblyOrderType()));
            //重新定义查询对象 Start
            DisassemblyOrder queryDisassemblyOrder = new DisassemblyOrder();
            queryDisassemblyOrder.setBrandId(disassembly.getBrandId());
            if (!StringUtils.isEmpty(disassembly.getStoreId())) {
                queryDisassemblyOrder.setStoreId(disassembly.getStoreId());
            }
            queryDisassemblyOrder.setDisassemblyOrderId(disassembly.getDisassemblyOrderId());
            // end
            ListResponse getResponse = getDetailList(queryDisassemblyOrder);
            String goodsName = getResponse.getDetail().getGoodsName();
            Long stockNum = getResponse.getDetail().getStockNum();
            beanList.add(!goodsName.equals("") ? goodsName : "未有商品名");
            beanList.add(!stockNum.toString().equals("") ? stockNum : 0);

            List<DisassemblyOrderDetail> sonList = getResponse.getSonList();
            String sonName = "";
            String sonNum = "";
            Integer sonStep = 0;
            for (DisassemblyOrderDetail disassemblyOrderDetail : sonList) {
                ++sonStep;
                if (sonList.size() == 1) {
                    sonName += disassemblyOrderDetail.getGoodsName() + " ";
                    sonNum += disassemblyOrderDetail.getStockNum() + " ";
                } else if (sonList.size() > 1) {
                    sonName += "第" + sonStep + "件子件商品:" + disassemblyOrderDetail.getGoodsName() + "\n";
                    sonNum += "第" + sonStep + "子件商品数量:" + disassemblyOrderDetail.getStockNum() + "\n";
                }
            }
            beanList.add(sonName);
            beanList.add(sonNum);
            beanList.add(disassembly.getMakeorderManName());
            beanList.add(disassembly.getCreateTime());
            beanList.add(disassembly.getExamineName());
            beanList.add(disassembly.getExamineTime());
            beanList.add(MapConsts.getDisassemblyStatus().get(disassembly.getDisassemblyOrderStatus()));
            resultList.add(beanList);
        }
        return resultList;
    }

    @Override
    public DisassemblyOrder getDisassemblyOrder(String disassemblyOrderId) throws DisassemblyException {
        DisassemblyOrder disassemblyOrder = disassemblyOrderDao.selectByDisassemblyOrderId(disassemblyOrderId);
        if (disassemblyOrder == null) {
            LOGGER.error("该拆装单不存在!");
            throw new DisassemblyException("该拆装单不存在!");
        }
        List<DisassemblyOrderDetail> disassemblyOrderDetailList = disassemblyOrderDetailDao.selectByDisassemblyOrderId(disassemblyOrderId);
        if (disassemblyOrderDetailList == null || disassemblyOrderDetailList.isEmpty()) {
            LOGGER.error("拆装单商品信息不存在!");
            throw new DisassemblyException("拆装单商品信息不存在!");
        }
        for (DisassemblyOrderDetail disassemblyOrderDetail : disassemblyOrderDetailList) {
            if (disassemblyOrderDetail.getUnitPrice() == null || disassemblyOrderDetail.getStockNum() == null) {
                throw new DisassemblyException("单价、数量为空。");
            }
            disassemblyOrderDetail.setTotalPrice(disassemblyOrderDetail.getUnitPrice() * disassemblyOrderDetail.getStockNum());
            disassemblyOrderDetail.setDisassemblyGoodsTypeName(MapConsts.getDisassemblyOrderGoodsType().get(disassemblyOrderDetail.getDisassemblyGoodsType()));
        }
        disassemblyOrder.setDisassemblyOrderDetailList(disassemblyOrderDetailList);
        return disassemblyOrder;
    }

    /**
     * 删除出库list GoodsId为空的数据
     *
     * @param srcList
     * @return
     */
    private List<DisassemblyOrderDetail> removeSrcGoodsId(List<DisassemblyOrderDetail> srcList) {
        for (int i = srcList.size() - 1; i >= 0; i--) {
            if (StringUtils.isEmpty(srcList.get(i).getGoodsId())) {
                srcList.remove(i);
            }
        }
        return srcList;
    }

    /**
     * 删除入库商品list GoodsId 为空的数据
     *
     * @param toList
     * @return
     */
    private List<DisassemblyOrderDetail> removeToGoodsId(List<DisassemblyOrderDetail> toList) {
        for (int i = toList.size() - 1; i >= 0; i--) {
            if (StringUtils.isEmpty(toList.get(i).getGoodsId())) {
                toList.remove(i);
            }
        }
        return toList;
    }

}

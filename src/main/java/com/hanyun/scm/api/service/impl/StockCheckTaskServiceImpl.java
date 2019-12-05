package com.hanyun.scm.api.service.impl;

import com.github.pagehelper.StringUtil;
import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.IdGenerateType;
import com.hanyun.scm.api.consts.MapConsts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.StockCheckOrderDetailDao;
import com.hanyun.scm.api.dao.StockCheckTaskDao;
import com.hanyun.scm.api.dao.StockCheckTaskDetailDao;
import com.hanyun.scm.api.dao.StockQuantDao;
import com.hanyun.scm.api.domain.*;
import com.hanyun.scm.api.domain.dto.StockCheckTaskDTO;
import com.hanyun.scm.api.domain.request.BrandStoreRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceCreateRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceModifyRequest;
import com.hanyun.scm.api.domain.request.stock.StockCheckTaskCreateRequest;
import com.hanyun.scm.api.domain.request.stock.StockCheckTaskModifyRequest;
import com.hanyun.scm.api.domain.request.stock.StockCheckTaskQueryRequest;
import com.hanyun.scm.api.domain.request.stock.StockQuantQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.domain.result.StockQuantResult;
import com.hanyun.scm.api.exception.ProcessInstanceException;
import com.hanyun.scm.api.exception.StockCheckTaskException;
import com.hanyun.scm.api.service.*;
import com.hanyun.scm.api.utils.ActivitiUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
 * 盘点任务service
 * Date: 16/6/22
 * Time: 下午1:51
 *
 * @author tianye@hanyun.com
 */
@Service
public class StockCheckTaskServiceImpl implements StockCheckTaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockCheckTaskServiceImpl.class);

    @Resource
    private StockCheckTaskDao stockCheckTaskDao;

    @Resource
    private StockCheckTaskDetailDao stockCheckTaskDetailDao;

    @Resource
    private StockCheckOrderDetailDao stockCheckOrerDetailDao;

    @Resource
    private IdGenerateSeqService idGenerateSeqService;

    @Resource
    private StockCheckDifferenceService stockCheckDifferenceService;

    @Resource
    private StockQuantDao stockQuantDao;

    @Resource
    private ProcessInstanceService processInstanceService;

    @Resource
    private ProcessDefinitionService processDefinitionService;

    @Resource
    private GoodsOdooService goodsOdooService;

    @Resource
    private StockQuantService stockQuantService;

    @Override
    public HttpResponse create(StockCheckTaskCreateRequest stockCheckTaskCreateRequest) {
        String id = IdUtil.uuid();
        String documentId;
        String brandId = stockCheckTaskCreateRequest.getBrandId();
        String storeId = stockCheckTaskCreateRequest.getStoreId();
        String warehouseId = stockCheckTaskCreateRequest.getWarehouseId();
        stockCheckTaskCreateRequest.setStockCheckTaskId(id);
        try {
            documentId = idGenerateSeqService.generateId(IdGenerateType.PR);
            stockCheckTaskCreateRequest.setStockCheckTaskDocumentId(documentId);
        } catch (Exception e) {
            LOGGER.error("生成盘点单编号失败!", e);
            return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_CREATE_ERROR);
        }
        try {
            int row = stockCheckTaskDao.insertSelective(stockCheckTaskCreateRequest);
            if (row <= 0) {
                return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_CREATE_ERROR);
            }
        } catch (Exception e) {
            LOGGER.error("创建盘点单失败!", e);
            return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_CREATE_ERROR);
        }
        try {
            boolean inserGoodsFlag = true;
            int type = stockCheckTaskCreateRequest.getStockCheckTaskType();
            //全场盘点保存记录
            List<StockCheckTaskDetail> stockCheckTaskDetailList = type == Consts.FULL_COURT_INVENTORY ?
                    getStockRecord(brandId, storeId, warehouseId, null) : removeEmpty(stockCheckTaskCreateRequest.getStockCheckTaskDetailList());
            if (stockCheckTaskDetailList != null && stockCheckTaskDetailList.size() > 0) {
                int row;
                for (StockCheckTaskDetail stockCheckTaskDetail : stockCheckTaskDetailList) {
                    String stockCheckTaskDetailId = IdUtil.uuid();
                    stockCheckTaskDetail.setStockCheckDetailId(stockCheckTaskDetailId);
                    stockCheckTaskDetail.setStockCheckId(id);
                    row = stockCheckTaskDetailDao.insertSelective(stockCheckTaskDetail);
                    if (row <= 0) {
                        inserGoodsFlag = false;
                    }
                }
                if (!inserGoodsFlag) {
                    LOGGER.error("添加盘点单商品失败!");
                    return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_CREATE_ERROR);
                }
            }
            boolean existStatus = processDefinitionService.exist(stockCheckTaskCreateRequest.getBrandId(), Consts.PROCESS_TYPE_STOCK_CHECK_TASK);
            if (existStatus) {
                return HttpResponse.success(new String[]{id, documentId, "exist"});
            }
            return HttpResponse.success(new String[]{id, documentId});
        } catch (Exception e) {
            LOGGER.error("添加盘点商品失败!", e);
            return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_CREATE_ERROR);
        }
    }

    /**
     * 全场盘点时获取仓库数据
     * @param brandId 品牌id
     * @param storeId 门店id
     * @param warehouseId 仓库id
     * @param dto 查询对象
     * @return list
     */
    private List<StockCheckTaskDetail> getStockRecord(String brandId, String storeId, String warehouseId, StockCheckTaskDTO dto) throws Exception{
        List<StockCheckTaskDetail> detailList = new ArrayList<>();
        StockQuantQueryRequest query = new StockQuantQueryRequest();
        query.setBrandId(brandId);
        query.setWarehouseId(warehouseId);
        List<StockQuantResult> list = stockQuantService.selectWithParam(query).getList();
        list.forEach(sq -> {
            StockCheckTaskDetail d = new StockCheckTaskDetail();
            d.setBrandId(brandId);
            if(StringUtils.isNotBlank(storeId)){
                d.setStoreId(storeId);
            }
            d.setWarehouseId(warehouseId);
            d.setGoodsId(sq.getGoodsId());
            d.setGoodsName(sq.getGoodsName());
            d.setGoodsCode(sq.getGoodsCode());
            d.setGoodsBarCode(sq.getGoodsBarCode());
            d.setClassifyId(sq.getClassifyId());
            d.setClassifyName(sq.getClassifyName());
            d.setUnitName(sq.getUnitName());
            d.setUnitId(sq.getUnitId());
            d.setUnitPrice(sq.getUnitPrice());
            d.setGoodsType(sq.getGoodsType());
            d.setGoodsTypeName(sq.getGoodsTypeName());
            d.setStockNum(sq.getStockNum());
            d.setRemark("(系统自动保存的全场盘点记录)");
            d.setFeatures(sq.getFeatures());
            d.setGoodsBrandName(sq.getGoodsBrandName());
            detailList.add(d);
        });
        return detailList;
    }

    @Override
    public HttpResponse detail(String id) {
        try {
            StockCheckTask stockCheckTask = stockCheckTaskDao.selectByStockCheckTaskId(id);
            if (stockCheckTask == null) {
                LOGGER.error("查询库存盘点信息失败!");
                return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_DATA_NOT_FOUND);
            }
            int type = stockCheckTask.getStockCheckTaskType();      //盘点类型
            StockCheckTaskDetail stockCheckTaskDetail = new StockCheckTaskDetail();
            stockCheckTaskDetail.setStockCheckId(stockCheckTask.getStockCheckTaskId());
            List<StockCheckTaskDetail> stockCheckTaskDetailList = stockCheckTaskDetailDao.select(stockCheckTaskDetail);
            stockCheckTask.setStockCheckTaskDetailList(type != Consts.FULL_COURT_INVENTORY ? stockCheckTaskDetailList : null);
            return HttpResponse.success(stockCheckTask);
        } catch (Exception e) {
            LOGGER.error("查询库存盘点详情失败!", e);
            return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse select(StockCheckTaskQueryRequest stockCheckTaskQueryRequest) {
        try {
            int count = stockCheckTaskDao.countAll(stockCheckTaskQueryRequest);
            stockCheckTaskQueryRequest.setCount(count);
            List<StockCheckTask> stockCheckTaskList = stockCheckTaskDao.select(stockCheckTaskQueryRequest);
            if (!StringUtils.isEmpty(stockCheckTaskQueryRequest.getUserId())) {
                for (StockCheckTask stockCheckTask : stockCheckTaskList) {
                    stockCheckTask.setAuditStatus(processInstanceService.queryAuditor(stockCheckTask.getStockCheckTaskId(), stockCheckTaskQueryRequest.getUserId()));
                    List<String> auditors = processInstanceService.queryAuditors(stockCheckTask.getStockCheckTaskId());
                    auditors.add(stockCheckTask.getOperatorId());
                    stockCheckTask.setHistoryStatus(auditors.size() > 1 && auditors.contains(stockCheckTaskQueryRequest.getUserId()));
                }
            }
            BaseResponse response = new BaseResponse(count, stockCheckTaskList);
            return HttpResponse.success(response);
        } catch (Exception e) {
            LOGGER.error("查询库存盘点列表失败!", e);
            return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_SYSTEM_ERROR);
        }
    }

    @Override
    public HttpResponse modify(StockCheckTaskModifyRequest stockCheckTaskModifyRequest) {
        try {
            StockCheckTask old = stockCheckTaskDao.selectByStockCheckTaskId(stockCheckTaskModifyRequest.getStockCheckTaskId());
            if (old == null) {
                LOGGER.error("查询库存盘点信息失败!");
                return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_DATA_NOT_FOUND);
            }
            int updateRow = stockCheckTaskDao.updateByStockCheckTaskId(stockCheckTaskModifyRequest);
            if (updateRow <= 0) {
                LOGGER.error("更新盘点单失败!");
                return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_MODIFY_ERROR);
            }
            //保存状态
            if (stockCheckTaskModifyRequest.getStockCheckTaskStatus() == Consts.STOCK_CHECK_TASK_STATUS_SAVED) {
                List<StockCheckTaskDetail> stockCheckTaskDetailList = stockCheckTaskModifyRequest.getStockCheckTaskDetailList();
                if (stockCheckTaskDetailList != null && stockCheckTaskDetailList.size() > 0) {
                    stockCheckTaskDetailList = removeEmpty(stockCheckTaskDetailList);
                    //新盘点商品id集合
                    List<String> newIds = stockCheckTaskDetailList.stream().map(StockCheckTaskDetail::getStockCheckDetailId).collect(Collectors.toList());
                    StockCheckTaskDetail query = new StockCheckTaskDetail();
                    query.setStockCheckId(stockCheckTaskModifyRequest.getStockCheckTaskId());
                    //原来的盘点商品列表
                    List<StockCheckTaskDetail> oldStockCheckTaskDetailList = stockCheckTaskDetailDao.select(query);
                    //原来的盘点商品id集合
                    List<String> oldIds = oldStockCheckTaskDetailList.stream().map(StockCheckTaskDetail::getStockCheckDetailId).collect(Collectors.toList());
                    //原来的盘点商品id集合包含新的盘点商品id,则更新盘点商品;否则新增一条盘点商品信息
                    boolean updateFlag = true;
                    int updateGoodsRow;
                    for (int i = 0; i < stockCheckTaskDetailList.size(); i++) {
                        if (oldIds.contains(newIds.get(i))) {
                            updateGoodsRow = stockCheckTaskDetailDao.updateByStockCheckTaskDetailId(stockCheckTaskDetailList.get(i));
                            if (updateGoodsRow <= 0) {
                                updateFlag = false;
                            }
                        } else {
                            String stockCheckTaskDetailId = IdUtil.uuid();
                            stockCheckTaskDetailList.get(i).setStockCheckDetailId(stockCheckTaskDetailId);
                            stockCheckTaskDetailList.get(i).setStockCheckId(stockCheckTaskModifyRequest.getStockCheckTaskId());
                            updateGoodsRow = stockCheckTaskDetailDao.insertSelective(stockCheckTaskDetailList.get(i));
                            if (updateGoodsRow <= 0) {
                                updateFlag = false;
                            }
                        }
                    }
                    //新增的盘点商品id集合不包含原来的盘点商品id,则删除原来的盘点商品id
                    for (int i = 0; i < oldStockCheckTaskDetailList.size(); i++) {
                        if (!newIds.contains(oldIds.get(i))) {
                            updateGoodsRow = stockCheckTaskDetailDao.deleteByStockCheckTaskDetailId(oldStockCheckTaskDetailList.get(i).getStockCheckDetailId());
                            if (updateGoodsRow <= 0) {
                                updateFlag = false;
                            }
                        }
                    }
                    if (!updateFlag) {
                        throw new StockCheckTaskException("更新盘点商品失败!");
                    }
                }
            }
            boolean existStatus = processDefinitionService.exist(old.getBrandId(), Consts.PROCESS_TYPE_STOCK_CHECK_TASK);
            if (existStatus) {
                return HttpResponse.success(new String[]{old.getStockCheckTaskId(), old.getStockCheckTaskDocumentId(), "exist"});
            }
            return HttpResponse.success(new String[]{old.getStockCheckTaskId()});
        } catch (Exception e) {
            LOGGER.error("修改库存盘点信息失败!", e);
            return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_SYSTEM_ERROR);
        }
    }

    /**
     * @param stockCheckTaskModifyRequest 参数对象
     * @return HttpResponse
     * @Title: modifyStockCheckTaskEnd
     * @Description: 审核-盘点结束
     * @author 王超群
     */
    @Override
    public HttpResponse modifyStockCheckTaskEnd(StockCheckTaskModifyRequest stockCheckTaskModifyRequest) {
        try {
            String stockCheckTaskId = stockCheckTaskModifyRequest.getStockCheckTaskId();
            if (StringUtil.isEmpty(stockCheckTaskId)) {
                LOGGER.error("编辑盘点任务单参数错误!");
                return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_END_PARAM_ERROR);
            }
            //获取盘点任务数据
            StockCheckTask old = stockCheckTaskDao.selectByStockCheckTaskId(stockCheckTaskModifyRequest.getStockCheckTaskId());
            if (old == null) {
                LOGGER.error("查询库存盘点信息失败!");
                return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_DATA_NOT_FOUND);
            }
            /**------------获取任务详情数据 (StockCheckTaskType: 1.全场盘点  2.单品盘点)---------------------*/

            List<StockCheckTaskDetail> tasklist;//任务详情
            Integer stockCheckTaskType = old.getStockCheckTaskType();//任务类型
            if(stockCheckTaskType == Consts.FULL_COURT_INVENTORY || stockCheckTaskType == Consts.SINGLE_GOODS_INVENTORY){
                StockCheckTaskDetail stockCheckTaskDetail = new StockCheckTaskDetail();
                stockCheckTaskDetail.setBrandId(old.getBrandId());
                stockCheckTaskDetail.setStockCheckId(stockCheckTaskId);
                tasklist = this.stockCheckTaskDetailDao.select(stockCheckTaskDetail);
            } else {
                LOGGER.error("盘点任务类型无效!");
                return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_END_ERROR);
            }
            if (tasklist == null || tasklist.size() == 0) {
                LOGGER.error("查询盘点任务详情失败!");
                return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_END_ERROR);
            }
            /**------------获取任务详情数据 end---------------------*/
            //获取盘点数据
            List<StockCheckOrderDetail> queryDetailByStockTaskID = stockCheckOrerDetailDao.queryDetailByStockTaskID(stockCheckTaskId);
            if (queryDetailByStockTaskID == null || queryDetailByStockTaskID.size() == 0) {
                LOGGER.error("查询盘点详情失败!");
                return HttpResponse.failure(ResultCode.STOCK_NO_CHECK_TASK_END_ERROR);
            }
            //整合数据生成差异数据
            StockCheckDifference stockCheckDifference = getStockCheckDifference(stockCheckTaskModifyRequest, old, tasklist, queryDetailByStockTaskID);
            if (stockCheckDifference == null) {
                LOGGER.error("整合数据失败!");
                return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_END_ERROR);
            }
            StockCheckTaskModifyRequest modify = new StockCheckTaskModifyRequest();
            modify.setStockCheckTaskId(stockCheckTaskId);
            if (stockCheckTaskModifyRequest.getStockCheckTaskStatus() != null && stockCheckTaskModifyRequest.getStockCheckTaskStatus() == Consts.ORDER_STATUS_CONFIRMED) {
                modify.setStockCheckTaskStatus(Consts.ORDER_STATUS_CONFIRMED);
                modify.setTaskEndTime(new Date());
            } else {
                if (!stockCheckTaskModifyRequest.getAuditStatus()) {
                    modify.setStockCheckTaskStatus(Consts.ORDER_STATUS_SAVED);
                    int row = stockCheckTaskDao.updateByStockCheckTaskId(modify);
                    if (row == 0) {
                        return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_CONFIRM_ERROR);
                    }
                    processInstanceService.delete(stockCheckTaskId);
                    return HttpResponse.success();
                }
                ProcessInstanceModifyRequest processInstanceModifyRequest = new ProcessInstanceModifyRequest();
                processInstanceModifyRequest.setBusinessId(stockCheckTaskId);
                processInstanceModifyRequest.setUserId(stockCheckTaskModifyRequest.getUserId());
                processInstanceModifyRequest.setRemark("通过, 执行");
                HttpResponse response = processInstanceService.audit(processInstanceModifyRequest);
                if (!StringUtils.equals(response.getCode(), "0") || response.getData() == null) {
                    return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_MODIFY_ERROR);
                }
                Integer instanceStatus = Integer.parseInt(response.getData().toString());
                if (instanceStatus != Consts.PROCESS_INSTANCE_STATUS_CONFIRMED) {
                    modify.setStockCheckTaskStatus(Consts.ORDER_STATUS_CONFIRMING);
                } else {
                    modify.setStockCheckTaskStatus(Consts.ORDER_STATUS_CONFIRMED);
                    modify.setTaskEndTime(new Date());
                }
            }
            //更改盘点任务状态
            int row = stockCheckTaskDao.updateByStockCheckTaskId(modify);
            if (row <= 0) {
                LOGGER.error("审核盘点任务-修改状态失败!");
                return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_END_ERROR);
            }
            if (modify.getStockCheckTaskStatus() == Consts.ORDER_STATUS_CONFIRMED) {
                return stockCheckDifferenceService.createDifference(stockCheckDifference);
            } else {
                return HttpResponse.success();
            }
        } catch (Exception e) {
            LOGGER.error("审核库存盘点信息失败!", e);
            return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_END_ERROR);
        }
    }

    /**
     * @param stockQuantList 库存list
     * @return List<StockCheckTaskDetail>
     * @Title: getTeskList
     * @Description: 全场盘点-整理库存数据
     * @author 王超群
     */
    private List<StockCheckTaskDetail> getTeskList(List<StockQuant> stockQuantList) {
        List<StockCheckTaskDetail> tasklist = new ArrayList<>();
        for (StockQuant quant : stockQuantList) {
            StockCheckTaskDetail task = new StockCheckTaskDetail();
            task.setBrandId(quant.getBrandId());
            task.setStoreId(quant.getStoreId());
            task.setWarehouseId(quant.getWarehouseId());
            task.setGoodsId(quant.getGoodsId());
            task.setGoodsName(quant.getGoodsName());
            task.setGoodsBarCode(quant.getGoodsBarCode());
            task.setGoodsCode(quant.getGoodsCode());
            task.setClassifyId(quant.getClassifyId());
            task.setClassifyName(quant.getClassifyName());
            task.setUnitId(quant.getUnitId());
            task.setUnitName(quant.getUnitName());
            task.setUnitPrice(quant.getUnitPrice());
            task.setGoodsType(quant.getGoodsType());
            task.setStockNum(quant.getStockNum());
            task.setFeatures(quant.getFeatures());
            tasklist.add(task);
        }
        return tasklist;
    }

    /**
     * @param old
     * @param tasklist
     * @param orderlist
     * @return StockCheckDifference
     * @Description: 整合存入差异表的数据
     * @author 王超群
     */
    private StockCheckDifference getStockCheckDifference(StockCheckTaskModifyRequest stockCheckTaskModifyRequest, StockCheckTask old, List<StockCheckTaskDetail> tasklist, List<StockCheckOrderDetail> orderlist) {
        String stockCheckDifferenceId = IdUtil.uuid();//差异单id
        String stockCheckDifferenceDocumentId;
        try {
            stockCheckDifferenceDocumentId = idGenerateSeqService.generateId(IdGenerateType.PC);//差异单业务id
        } catch (Exception e) {
            LOGGER.error("差异单id生成失败失败!", e);
            return null;
        }
        //盘点的商品放入map中
        Map<String, Long> map = new HashMap<>();
        orderlist.forEach(d ->{
            Long totalNum = d.getTotalNum() == null ? 0L : d.getTotalNum();
            map.put(d.getGoodsId(), totalNum);
        });
        //差异单详情数据
        List<StockCheckDifferenceDetail> differenceList = new ArrayList<>();
        for (StockCheckTaskDetail task : tasklist) {
            String goodsId = task.getGoodsId();//盘点任务详情中商品id
            Long totalNum = map.get(goodsId) != null ? map.get(goodsId) : 0L;
            StockCheckDifferenceDetail detail = new StockCheckDifferenceDetail();
            detail.setStockCheckTaskId(stockCheckTaskModifyRequest.getStockCheckTaskId());
            detail.setBrandId(task.getBrandId());
            detail.setStoreId(task.getStoreId());
            detail.setWarehouseId(task.getWarehouseId());
            detail.setStockCheckDifferenceId(stockCheckDifferenceId);
            detail.setStockCheckDifferenceDetailId(IdUtil.uuid());
            detail.setGoodsId(task.getGoodsId());
            detail.setGoodsName(task.getGoodsName());
            detail.setGoodsBarCode(task.getGoodsBarCode());
            detail.setGoodsCode(task.getGoodsCode());
            detail.setClassifyId(task.getClassifyId());
            detail.setClassifyName(task.getClassifyName());
            detail.setUnitId(task.getUnitId());
            detail.setUnitName(task.getUnitName());
            detail.setOdooGoodsId(task.getOdooGoodsId());
            detail.setGoodsIntroduce(task.getGoodsIntroduce());
            detail.setUnitPrice(task.getUnitPrice());
            detail.setUseType(task.getUseType());
            detail.setGoodsType(task.getGoodsType());
            detail.setStockNum(task.getStockNum() == null ? 0 : task.getStockNum());
            detail.setCheckStock(totalNum);
            detail.setCurStock(task.getCurStock());
            detail.setFeatures(task.getFeatures());
            differenceList.add(detail);
        }
        //差异单数据
        StockCheckDifference stockCheckDifference = new StockCheckDifference();
        stockCheckDifference.setBrandId(old.getBrandId());
        stockCheckDifference.setStoreId(old.getStoreId());
        stockCheckDifference.setStockCheckTaskId(old.getStockCheckTaskId());
        stockCheckDifference.setStockCheckTaskDocumentId(old.getStockCheckTaskDocumentId());
        stockCheckDifference.setStockCheckDifferenceId(stockCheckDifferenceId);
        stockCheckDifference.setStockCheckDifferenceDocumentId(stockCheckDifferenceDocumentId);
        stockCheckDifference.setWarehouseId(old.getWarehouseId());
        stockCheckDifference.setWarehouseName(old.getWarehouseName());
        stockCheckDifference.setOperatorId(old.getOperatorId());
        stockCheckDifference.setOperatorName(old.getOperatorName());
        stockCheckDifference.setStockCheckDifferenceStatus(Consts.STOCK_CHECK_DIFFERENCE_STATUS);
        stockCheckDifference.setStockCheckTaskType(old.getStockCheckTaskType());
        stockCheckDifference.setAuditTime(old.getAuditTime());
        stockCheckDifference.setDetailList(differenceList);
        return stockCheckDifference;
    }

    @Override
    public HttpResponse delete(String id) {
        try {
            StockCheckTask stockCheckTask = stockCheckTaskDao.selectByStockCheckTaskId(id);
            if (stockCheckTask == null) {
                LOGGER.error("该库存盘点信息不存在!");
                return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_DATA_NOT_FOUND);
            }
            int row = stockCheckTaskDao.deleteByStockCheckTaskId(id);
            if (row <= 0) {
                LOGGER.error("删除盘点单失败!");
                return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_DELETE_ERROR);
            }
            StockCheckTaskDetail stockCheckTaskDetail = new StockCheckTaskDetail();
            stockCheckTaskDetail.setStockCheckId(stockCheckTask.getStockCheckTaskId());
            List<StockCheckTaskDetail> stockCheckTaskDetailList = stockCheckTaskDetailDao.select(stockCheckTaskDetail);
            if (stockCheckTaskDetailList != null && stockCheckTaskDetailList.size() > 0) {
                boolean deleteFlag = true;
                int deleteRow;
                for (StockCheckTaskDetail goods : stockCheckTaskDetailList) {
                    deleteRow = stockCheckTaskDetailDao.deleteByStockCheckTaskDetailId(goods.getStockCheckDetailId());
                    if (deleteRow <= 0) {
                        deleteFlag = false;
                    }
                }
                if (!deleteFlag) {
                    throw new StockCheckTaskException("删除盘点商品失败!");
                }
            }
            return HttpResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("删除库存盘点信息失败!", e);
            return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_SYSTEM_ERROR);
        }
    }

    @Override
    public BaseResponse selectList(StockCheckTaskQueryRequest stockCheckTaskQueryRequest, boolean excel) throws StockCheckTaskException {
        try {
            int count = stockCheckTaskDao.countAll(stockCheckTaskQueryRequest);
            stockCheckTaskQueryRequest.setCount(count);
            List<StockCheckTask> stockCheckList = stockCheckTaskDao.select(stockCheckTaskQueryRequest);
            if (excel) {
                for (StockCheckTask stockCheckTask : stockCheckList) {
                    stockCheckTask.setStockCheckTaskStatusName(MapConsts.getStockCheckTaskStatus().get(stockCheckTask.getStockCheckTaskStatus()));
                    stockCheckTask.setStockCheckTaskTypeName(MapConsts.getStockCheckTaskType().get(stockCheckTask.getStockCheckTaskType()));

                }
            }
            return new BaseResponse(count, stockCheckList);
        } catch (Exception e) {
            LOGGER.error("查询盘点任务单列表失败!", e);
            throw new StockCheckTaskException("查询盘点任务单单列表失败!", e);
        }
    }

    @Override
    public HttpResponse commit(String stockCheckTaskId) {
        try {
            StockCheckTask old = stockCheckTaskDao.selectByStockCheckTaskId(stockCheckTaskId);
            if (old == null) {
                LOGGER.error("该库存盘点信息不存在!");
                return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_DATA_NOT_FOUND);
            }
            //获取盘点数据
            List<StockCheckOrderDetail> orderList = stockCheckOrerDetailDao.queryDetailByStockTaskID(stockCheckTaskId);
            if (orderList == null || orderList.size() == 0) {
                LOGGER.error("查询盘点详情失败!");
                return HttpResponse.failure(ResultCode.STOCK_NO_CHECK_TASK_END_ERROR);
            }
            StockCheckTaskModifyRequest modify = new StockCheckTaskModifyRequest();
            modify.setStockCheckTaskId(stockCheckTaskId);
            modify.setStockCheckTaskStatus(Consts.ORDER_STATUS_COMMITED);
            int modifyRow = stockCheckTaskDao.updateByStockCheckTaskId(modify);
            if (modifyRow <= 0) {
                throw new StockCheckTaskException("更新提审任务单状态失败");
            }
            ProcessInstanceCreateRequest processInstanceCreateRequest = new ProcessInstanceCreateRequest();
            processInstanceCreateRequest.setProcessType(Consts.PROCESS_TYPE_STOCK_CHECK_TASK);
            processInstanceCreateRequest.setBrandId(old.getBrandId());
            processInstanceCreateRequest.setBusinessId(stockCheckTaskId);
            ActivitiUtil.getInstance();
            HttpResponse response = processInstanceService.create(processInstanceCreateRequest, old.getOperatorId());
            if (!StringUtils.equals(response.getCode(), "0")) {
                throw new ProcessInstanceException("提审失败!");
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("修改库存盘点信息失败!", e);
            return HttpResponse.failure(ResultCode.STOCK_CHECK_TASK_SYSTEM_ERROR);
        }
    }

    @Override
    public Long waitForStockCheckCount(BrandStoreRequest brandStoreRequest) throws StockCheckTaskException {
        try {
            return stockCheckTaskDao.waitForStockCheckCount(brandStoreRequest);
        } catch (Exception e) {
            LOGGER.error("待盘点订单统计失败!", e);
            throw new StockCheckTaskException("待盘点订单统计失败!", e);
        }
    }

    private List<StockCheckTaskDetail> removeEmpty(List<StockCheckTaskDetail> stockCheckTaskDetailList) {
        for (int i = stockCheckTaskDetailList.size() - 1; i >= 0; i--) {
            if (StringUtils.isEmpty(stockCheckTaskDetailList.get(i).getGoodsId())) {
                stockCheckTaskDetailList.remove(i);
            }
        }
        return stockCheckTaskDetailList;
    }

    @Override
    public List<StockCheckTaskDetail> exportDetailList(StockCheckTaskQueryRequest stockCheckTaskQueryRequest) throws StockCheckTaskException {
        StockCheckTaskDetail stockCheckTaskDetail = new StockCheckTaskDetail();
        stockCheckTaskDetail.setStockCheckId(stockCheckTaskQueryRequest.getStockCheckTaskId());
        List<StockCheckTaskDetail> stockCheckTaskDetailList = new ArrayList<StockCheckTaskDetail>();

        try {
            stockCheckTaskDetailList = stockCheckTaskDetailDao.select(stockCheckTaskDetail);
            for (StockCheckTaskDetail TaskDetail : stockCheckTaskDetailList) {
                TaskDetail.setCheckDiffPrice(TaskDetail.getStockNum() * TaskDetail.getUnitPrice());
            }
        } catch (Exception e) {
            LOGGER.error("盘点任务单导出失败!", e);
            throw new StockCheckTaskException("盘点任务单导出失败!", e);
        }


        return stockCheckTaskDetailList;
    }

    @Override
    public HttpResponse queryTaskGoods(StockCheckTaskDTO dto) {
        try {
            String taskId = dto.getStockCheckTaskId();
            StockCheckTask old = stockCheckTaskDao.selectByStockCheckTaskId(taskId);
            if (old == null) {
                return HttpResponse.failure(ResultCode.DATA_NOT_FOUND);
            }
            //盘点类型
            int type = old.getStockCheckTaskType();
            List<StockCheckTaskDetail> result = new ArrayList<>();
            switch (type) {
                case Consts.FULL_COURT_INVENTORY:
                    StockQuantQueryRequest query = new StockQuantQueryRequest();
                    query.setBrandId(old.getBrandId());
                    query.setWarehouseId(old.getWarehouseId());
                    query.setGoodsBarCode(dto.getGoodsBarCode());
                    query.setGoodsName(dto.getGoodsName());
                    query.setPageNum(dto.getPageNum());
                    query.setPageSize(dto.getPageSize());
                    List<StockQuant> stockQuantList = stockQuantService.selectWithParam(query).getList();
                    Map<String, GoodsOdoo> map = goodsOdooService.getStockQuantGoodsMap(old.getBrandId(), stockQuantList);
                    for (StockQuant sq : stockQuantList) {
                        String goodsId = sq.getGoodsId();
                        StockCheckTaskDetail detail = new StockCheckTaskDetail();
                        detail.setGoodsId(goodsId);
                        detail.setGoodsName(sq.getGoodsName());
                        detail.setGoodsCode(sq.getGoodsCode());
                        detail.setGoodsBarCode(sq.getGoodsBarCode());
                        detail.setClassifyId(sq.getClassifyId());
                        detail.setClassifyName(sq.getClassifyName());
                        detail.setUnitId(sq.getUnitId());
                        detail.setUnitName(sq.getUnitName());
                        detail.setStockNum(sq.getStockNum());
                        detail.setUnitPrice(sq.getUnitPrice());
                        boolean bl = map != null && map.get(goodsId) != null && StringUtils.isNotBlank(map.get(goodsId).getGoodsPic());
                        detail.setGoodsPic(bl ? map.get(goodsId).getGoodsPic() : "");
                        detail.setGoodsBrandName(sq.getGoodsBrandName());
                        result.add(detail);
                    }
                    break;
                case Consts.SINGLE_GOODS_INVENTORY:
                    StockCheckTaskDetail record = new StockCheckTaskDetail();
                    record.setStockCheckId(taskId);
                    record.setGoodsBarCode(dto.getGoodsBarCode());
                    record.setGoodsName(dto.getGoodsName());
                    record.setPageNum(dto.getPageNum());
                    record.setPageSize(dto.getPageSize());
                    int countDetail = stockCheckTaskDetailDao.countAll(record);
                    record.setCount(countDetail);
                    result = stockCheckTaskDetailDao.select(record);
                    for (StockCheckTaskDetail detail : result) {
                        detail.setGoodsPic(StringUtils.isNotBlank(detail.getGoodsPic()) ? detail.getGoodsPic() : "");
                    }
                    break;
                default:
                    System.err.println("类型为空");
                    break;
            }
            return HttpResponse.success(result);
        } catch (Exception e) {
            LOGGER.error("查询任务单商品异常", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }
}

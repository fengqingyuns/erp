package com.hanyun.scm.api.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.IdGenerateType;
import com.hanyun.scm.api.consts.MapConsts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.PurchaseApplyDao;
import com.hanyun.scm.api.dao.PurchaseApplyGoodsDao;
import com.hanyun.scm.api.domain.GoodsOdoo;
import com.hanyun.scm.api.domain.PurchaseApply;
import com.hanyun.scm.api.domain.PurchaseApplyGoods;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceCreateRequest;
import com.hanyun.scm.api.domain.request.process.instance.ProcessInstanceModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.apply.PurchaseApplyConfirmRequest;
import com.hanyun.scm.api.domain.request.purchase.apply.PurchaseApplyCreateRequest;
import com.hanyun.scm.api.domain.request.purchase.apply.PurchaseApplyModifyRequest;
import com.hanyun.scm.api.domain.request.purchase.apply.PurchaseApplyQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.exception.ProcessInstanceException;
import com.hanyun.scm.api.exception.PurchaseApplyException;
import com.hanyun.scm.api.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
 * 采购退货单
 * Date: 2016/10/27
 * Time: 下午12:08
 *
 * @author tianye@hanyun.com
 */
@Service
public class PurchaseApplyServiceImpl implements PurchaseApplyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseApplyServiceImpl.class);

    @Resource
    private PurchaseApplyDao purchaseApplyDao;

    @Resource
    private IdGenerateSeqService idGenerateSeqService;

    @Resource
    private PurchaseApplyGoodsDao purchaseApplyGoodsDao;

    @Resource
    private ProcessInstanceService processInstanceService;

    @Resource
    private ProcessDefinitionService processDefinitionService;

    @Resource
    private GoodsOdooService goodsOdooService;

    @Override
    public HttpResponse create(PurchaseApplyCreateRequest purchaseApplyCreateRequest) {
        try {
            String purchaseApplyId = IdUtil.uuid();
            String purchaseApplyDocumentId = idGenerateSeqService.generateId(IdGenerateType.CG);
            purchaseApplyCreateRequest.setApplyId(purchaseApplyId);
            purchaseApplyCreateRequest.setApplyDocumentId(purchaseApplyDocumentId);
            int row = purchaseApplyDao.insertPurchaseApply(purchaseApplyCreateRequest);
            if (row <= 0) {
                LOGGER.error("添加采购申请单失败!");
                return HttpResponse.failure(ResultCode.PURCHASE_APPLY_ADD_ERROR);
            }
            if (purchaseApplyCreateRequest.getPurchaseApplyGoodsList() != null && purchaseApplyCreateRequest.getPurchaseApplyGoodsList().size() > 0) {
                purchaseApplyCreateRequest.setPurchaseApplyGoodsList(removeEmpty(purchaseApplyCreateRequest.getPurchaseApplyGoodsList()));
                for (PurchaseApplyGoods purchaseApplyGoods : purchaseApplyCreateRequest.getPurchaseApplyGoodsList()) {
                    String applyGoodsId = IdUtil.uuid();
                    purchaseApplyGoods.setApplyId(purchaseApplyId);
                    purchaseApplyGoods.setApplyGoodsId(applyGoodsId);
                    int goodsRow = purchaseApplyGoodsDao.insertSelective(purchaseApplyGoods);
                    if (goodsRow <= 0) {
                        throw new RuntimeException("添加商品失败!");
                    }
                }
            }
            boolean existStatus = processDefinitionService.exist(purchaseApplyCreateRequest.getBrandId(), Consts.PROCESS_TYPE_PURCHASE_APPLY);
            if (existStatus) {
                return HttpResponse.success(new String[]{purchaseApplyId, purchaseApplyDocumentId, "exist"});
            }
            return HttpResponse.success(new String[]{purchaseApplyId, purchaseApplyDocumentId});
        } catch (Exception e) {
            LOGGER.error("添加采购申请单失败!",e);
            return HttpResponse.failure(ResultCode.PURCHASE_APPLY_ADD_ERROR);
        }
    }

    @Override
    public HttpResponse detail(String applyId) {
        try {
            PurchaseApply purchaseApply = purchaseApplyDao.selectByApplyId(applyId);
            if (purchaseApply == null) {
                LOGGER.error("查询采购申请单详情失败!");
                return HttpResponse.failure(ResultCode.PURCHASE_APPLY_DATA_NOT_FOUND);
            }
            List<PurchaseApplyGoods> purchaseApplyGoodsList = purchaseApplyGoodsDao.selectByApplyId(applyId);
            purchaseApply.setPurchaseApplyGoodsList(purchaseApplyGoodsList);
            return HttpResponse.success(purchaseApply);
        } catch (Exception e) {
            LOGGER.error("查询采购申请单详情失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_APPLY_DETAIL_ERROR);
        }
    }

    @Override
    public HttpResponse select(PurchaseApplyQueryRequest purchaseApplyQueryRequest) {
        try {
            if (!StringUtils.isEmpty(purchaseApplyQueryRequest.getSkipIds())) {
                try {
                    purchaseApplyQueryRequest.setSkipIdList(JsonUtil.fromJson(purchaseApplyQueryRequest.getSkipIds(), new TypeReference<List<String>>() {
                    }));
                } catch (Exception e) {
                    LOGGER.error("转化skipIds失败!", e);
                }
            }
            return HttpResponse.success(selectList(purchaseApplyQueryRequest, false));
        } catch (Exception e) {
            LOGGER.error("查询采购申请单列表失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_APPLY_QUERY_ERROR);
        }
    }

    @Override
    public BaseResponse selectList(PurchaseApplyQueryRequest purchaseApplyQueryRequest, boolean excel) throws PurchaseApplyException {
        try {
            int count = purchaseApplyDao.countAll(purchaseApplyQueryRequest);
            purchaseApplyQueryRequest.setCount(count);
            List<PurchaseApply> purchaseApplyList = purchaseApplyDao.select(purchaseApplyQueryRequest);
            if (excel) {
                for (PurchaseApply purchaseApply : purchaseApplyList) {
                    purchaseApply.setApplyStatusName(MapConsts.getPurchaseApplyStatus().get(purchaseApply.getApplyStatus()));
                    purchaseApply.setExportpurchaseType(MapConsts.getStockCheckPurchaseType().get(purchaseApply.getPurchaseType()));
                }
            } else if (!StringUtils.isEmpty(purchaseApplyQueryRequest.getUserId())) {
                for (PurchaseApply purchaseApply : purchaseApplyList) {
                    purchaseApply.setAuditStatus(processInstanceService.queryAuditor(purchaseApply.getApplyId(), purchaseApplyQueryRequest.getUserId()));
                    List<String> auditors = processInstanceService.queryAuditors(purchaseApply.getApplyId());
                    auditors.add(purchaseApply.getOperatorId());
                    purchaseApply.setHistoryStatus(auditors.size() > 1 && auditors.contains(purchaseApplyQueryRequest.getUserId()));
                }
            }
            return new BaseResponse(count, purchaseApplyList);
        } catch (Exception e) {
            LOGGER.error("查询采购申请单列表失败!", e);
            throw new PurchaseApplyException("查询采购申请单列表失败!", e);
        }
    }

    @Override
    public HttpResponse modify(PurchaseApplyModifyRequest purchaseApplyModifyRequest) {
        try {
            PurchaseApply old = purchaseApplyDao.selectByApplyId(purchaseApplyModifyRequest.getApplyId());
            if (old == null) {
                LOGGER.error("采购申请单不存在!");
                return HttpResponse.failure(ResultCode.PURCHASE_APPLY_DATA_NOT_FOUND);
            }
            purchaseApplyGoodsDao.deleteByApplyId(old.getApplyId());
            List<PurchaseApplyGoods> purchaseApplyGoodsList = purchaseApplyModifyRequest.getPurchaseApplyGoodsList();
            if (purchaseApplyGoodsList == null || purchaseApplyGoodsList.size() == 0) {
                LOGGER.error("无商品信息!");
                return HttpResponse.failure(ResultCode.PURCHASE_APPLY_GOODS_EMPTY_ERROR);
            }
            purchaseApplyGoodsList = removeEmpty(purchaseApplyGoodsList);
            for (PurchaseApplyGoods purchaseApplyGoods : purchaseApplyGoodsList) {
                String applyGoodsId = IdUtil.uuid();
                purchaseApplyGoods.setApplyGoodsId(applyGoodsId);
                purchaseApplyGoods.setApplyId(old.getApplyId());
                int detailRow = purchaseApplyGoodsDao.insertSelective(purchaseApplyGoods);
                if (detailRow <= 0) {
                    LOGGER.error("更新采购申请单商品失败!");
                    throw new PurchaseApplyException("更新采购申请单商品失败!");
                }
            }
            int row = purchaseApplyDao.updatePurchaseApply(purchaseApplyModifyRequest);
            if (row <= 0) {
                LOGGER.error("更新采购申请单失败!");
                return HttpResponse.failure(ResultCode.PURCHASE_APPLY_UPD_ERROR);
            }
            boolean existStatus = processDefinitionService.exist(old.getBrandId(), Consts.PROCESS_TYPE_PURCHASE_APPLY);
            if (existStatus) {
                return HttpResponse.success(new String[]{old.getApplyId(), old.getApplyDocumentId(), "exist"});
            }
            return HttpResponse.success(new String[]{old.getApplyId(), old.getApplyDocumentId()});
        } catch (Exception e) {
            LOGGER.error("更新采购申请单失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_APPLY_UPD_ERROR);
        }
    }

    @Override
    public HttpResponse delete(String purchaseApplyId) {
        try {
            PurchaseApply old = purchaseApplyDao.selectByApplyId(purchaseApplyId);
            if (old == null) {
                LOGGER.error("删除采购申请单失败-该采购申请单不存在!");
                return HttpResponse.failure(ResultCode.PURCHASE_APPLY_DATA_NOT_FOUND);
            }
            int row = purchaseApplyDao.delete(purchaseApplyId);
            if (row <= 0) {
                LOGGER.error("删除采购申请单失败!");
                return HttpResponse.failure(ResultCode.PURCHASE_APPLY_DELETE_ERROR);
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("删除采购申请单失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_APPLY_DELETE_ERROR);
        }
    }

    @Override
    public HttpResponse confirm(PurchaseApplyConfirmRequest purchaseApplyConfirmRequest) {
        try {
            PurchaseApply old = purchaseApplyDao.selectByApplyId(purchaseApplyConfirmRequest.getApplyId());
            if (old == null) {
                LOGGER.error("确认采购申请单失败-该采购申请单不存在!");
                return HttpResponse.failure(ResultCode.PURCHASE_APPLY_DATA_NOT_FOUND);
            }
            PurchaseApplyModifyRequest purchaseApplyModifyRequest = new PurchaseApplyModifyRequest();
            purchaseApplyModifyRequest.setApplyId(old.getApplyId());
            if (purchaseApplyConfirmRequest.getApplyStatus() != null && purchaseApplyConfirmRequest.getApplyStatus() == Consts.PURCHASE_APPLY_STATUS_CONFIRMED) {
                purchaseApplyModifyRequest.setApplyStatus(Consts.PURCHASE_APPLY_STATUS_CONFIRMED);
            } else {
                if (!purchaseApplyConfirmRequest.getAuditStatus()) {
                    purchaseApplyModifyRequest.setApplyStatus(Consts.PURCHASE_APPLY_STATUS_SAVED);
                    int row = purchaseApplyDao.updatePurchaseApply(purchaseApplyModifyRequest);
                    if (row == 0) {
                        return HttpResponse.failure(ResultCode.PURCHASE_APPLY_CONFIRM_ERROR);
                    }
                    processInstanceService.delete(old.getApplyId());
                    return HttpResponse.success();
                }
                ProcessInstanceModifyRequest modifyRequest = new ProcessInstanceModifyRequest();
                modifyRequest.setBusinessId(old.getApplyId());
                modifyRequest.setUserId(purchaseApplyConfirmRequest.getUserId());
                modifyRequest.setRemark("通过, 执行");
                HttpResponse response = processInstanceService.audit(modifyRequest);
                if (!StringUtils.equals(response.getCode(), "0") || response.getData() == null) {
                    return HttpResponse.failure(ResultCode.PURCHASE_APPLY_CONFIRM_ERROR);
                }
                Integer instanceStatus = Integer.parseInt(response.getData().toString());
                if (instanceStatus != Consts.PROCESS_INSTANCE_STATUS_CONFIRMED) {
                    purchaseApplyModifyRequest.setApplyStatus(Consts.PURCHASE_APPLY_STATUS_CONFIRMING);
                } else {
                    purchaseApplyModifyRequest.setApplyStatus(Consts.PURCHASE_APPLY_STATUS_CONFIRMED);
                }
            }
            int row = purchaseApplyDao.updatePurchaseApply(purchaseApplyModifyRequest);
            if (row == 0) {
                LOGGER.error("确认采购申请单失败!");
                return HttpResponse.failure(ResultCode.PURCHASE_APPLY_CONFIRM_ERROR);
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("确认采购申请单失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_APPLY_CONFIRM_ERROR);
        }
    }

    @Override
    public PurchaseApply getApply(String applyId) throws PurchaseApplyException {
        PurchaseApply purchaseApply = purchaseApplyDao.selectByApplyId(applyId);
        if (purchaseApply == null) {
            LOGGER.error("该申请单不存在!");
            throw new PurchaseApplyException("该申请单不存在!");
        }
        List<PurchaseApplyGoods> purchaseApplyGoodsList = purchaseApplyGoodsDao.selectByApplyId(applyId);
        if (purchaseApplyGoodsList == null) {
            LOGGER.error("申请单商品信息不存在!");
            throw new PurchaseApplyException("申请单商品信息不存在!");
        }
        purchaseApply.setPurchaseApplyGoodsList(purchaseApplyGoodsList);
        return purchaseApply;
    }

    @Override
    public HttpResponse commit(String applyId) {
        try {
            PurchaseApply old = purchaseApplyDao.selectByApplyId(applyId);
            if (old == null) {
                return HttpResponse.failure(ResultCode.PURCHASE_APPLY_COMMIT_ERROR);
            }
            PurchaseApplyModifyRequest modifyRequest = new PurchaseApplyModifyRequest();
            modifyRequest.setApplyId(applyId);
            modifyRequest.setApplyStatus(Consts.PURCHASE_APPLY_STATUS_COMMITED);
            int row = purchaseApplyDao.updatePurchaseApply(modifyRequest);
            if (row == 0) {
                return HttpResponse.failure(ResultCode.PURCHASE_APPLY_COMMIT_ERROR);
            }
            ProcessInstanceCreateRequest create = new ProcessInstanceCreateRequest();
            create.setProcessType(Consts.PROCESS_TYPE_PURCHASE_APPLY);
            create.setBrandId(old.getBrandId());
            create.setBusinessId(applyId);
            List<PurchaseApplyGoods> purchaseApplyGoodsList = purchaseApplyGoodsDao.selectByApplyId(applyId);
            Long totalPrice = 0L;
            for (PurchaseApplyGoods purchaseApplyGoods : purchaseApplyGoodsList) {
                GoodsOdoo goodsOdoo = goodsOdooService.selectById(purchaseApplyGoods.getGoodsId());
                if (goodsOdoo != null && goodsOdoo.getPurchasePrice() != null) {
                    totalPrice += purchaseApplyGoods.getApplyAmount() * goodsOdoo.getPurchasePrice();
                }
            }
            create.setPrice(totalPrice);
            HttpResponse response = processInstanceService.create(create, old.getOperatorId());
            if (!StringUtils.equals(response.getCode(), "0")) {
                throw new ProcessInstanceException("提审失败!");
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("采购申请单提审失败!", e);
            return HttpResponse.failure(ResultCode.PURCHASE_APPLY_COMMIT_ERROR);
        }
    }

    /**
     * 去掉goodsId为空的数据
     *
     * @param purchaseApplyGoodsList 商品列表
     * @return 返回值
     */
    private List<PurchaseApplyGoods> removeEmpty(List<PurchaseApplyGoods> purchaseApplyGoodsList) {
        for (int i = purchaseApplyGoodsList.size() - 1; i >= 0; i--) {
            if (StringUtils.isEmpty(purchaseApplyGoodsList.get(i).getGoodsId())) {
                purchaseApplyGoodsList.remove(i);
            }
        }
        return purchaseApplyGoodsList;
    }
}

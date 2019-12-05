package com.hanyun.scm.api.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.PurchaseOrderDao;
import com.hanyun.scm.api.dao.ReplenishmentApplyDao;
import com.hanyun.scm.api.domain.PurchaseOrder;
import com.hanyun.scm.api.domain.ReplenishmentApply;
import com.hanyun.scm.api.domain.request.Replenishment.ReplenishmentApplyRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderQueryRequest;
import com.hanyun.scm.api.domain.request.stock.StockInListRequestDTO;
import com.hanyun.scm.api.domain.result.StockInListResultByApp;
import com.hanyun.scm.api.domain.result.StockInResult;
import com.hanyun.scm.api.service.StockInDetailByAppService;
import com.hanyun.scm.api.utils.DateUtilGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
 * stockInDetailByAppServiceImpl
 * Date: 2017/8/13 0013
 * Time: 13:33
 *
 * @author tangqiming@hanyun.com
 */
@Service
public class StockInDetailByAppServiceImpl implements StockInDetailByAppService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockInDetailByAppServiceImpl.class);

    @Resource
    private ReplenishmentApplyDao replenishmentApplyDao;

    @Resource
    private PurchaseOrderDao purchaseOrderDao;


    /**
     * 对list按照updateTime进行排序
     * @param list 参数
     * @return List
     */
    private List<StockInResult> compareSort(List<StockInResult> list){
        Collections.sort(list, (o1, o2) -> {
            if (o1.getUpdateTime().before(o2.getUpdateTime())){
                return 1;
            }else {
                return -1;
            }

        });
        return list;
    }

    /**
     * 计算的时间   格式 yyyy/MM  * 2017/09
     * @param date 参数
     * @return String
     */
    private String calculateTime(String date){
        Integer[] time = StockInListRequestDTO.getDate(date);
        if (time[1] <= 1) {
            time[1] = 12;
            time[0] = time[0] - 1;
        } else {
            time[1] = time[1] - 1;
        }
        return time[0] + "/" + (time[1] < 10 ? "0" + time[1] : time[1]);
    }

    @Override
    public HttpResponse select(StockInListRequestDTO dto) {
        //第一次时间给初始化值
        dto.setDate(StringUtils.isEmpty(dto.getDate()) ? DateUtilGet.getYearAndMonth() : calculateTime(dto.getDate()));
        List<StockInResult> groupList = new ArrayList<>();                  //组合list(合并)
        List<StockInListResultByApp> result = new ArrayList<>();
        try {
            ReplenishmentApplyRequest applyRequest = dto.setApplyRequest(dto);
            PurchaseOrderQueryRequest orderRequest = dto.setPurchaseRequest(dto);
            List<ReplenishmentApply> applyList = replenishmentApplyDao.selectSelective(applyRequest);
            List<String> orderIds = purchaseOrderDao.selectOrderIdsForStockIn(orderRequest);
            orderRequest.setOrderIds(orderIds);
            List<PurchaseOrder> orderList = purchaseOrderDao.queryForPurchaseStockIn(orderRequest);
            orderList.forEach(o -> groupList.add(new StockInResult(o)));
            applyList.forEach(a -> groupList.add(new StockInResult(a)));
            if (groupList.size() > 0) {
                result.add(new StockInListResultByApp(dto.getDate(), compareSort(new ArrayList<>(groupList))));
            }
            if (groupList.size() < Consts.TEN_SIZE) {         //查询条目小于10条
                for (int i = 1; i < Consts.SIX_MONTH; i++) {  //6个月之内
                    List<StockInResult> center = new ArrayList<>();                  //组合list(合并)
                    dto.setDate(calculateTime(dto.getDate()));
                    applyRequest = dto.setApplyRequest(dto);
                    orderRequest = dto.setPurchaseRequest(dto);
                    applyList = replenishmentApplyDao.selectSelective(applyRequest);
                    List<String> mOrderIds = purchaseOrderDao.selectOrderIdsForStockIn(orderRequest);
                    orderRequest.setOrderIds(mOrderIds);
                    orderList = purchaseOrderDao.queryForPurchaseStockIn(orderRequest);
                    orderList.forEach(o -> groupList.add(new StockInResult(o)));
                    applyList.forEach(a -> groupList.add(new StockInResult(a)));
                    orderList.forEach(o -> center.add(new StockInResult(o)));
                    applyList.forEach(a -> center.add(new StockInResult(a)));
                    if (center.size() > 0) {
                        result.add(new StockInListResultByApp(dto.getDate(), compareSort(center)));
                    }
                    if (groupList.size() > Consts.TEN_SIZE) {
                        break;
                    }
                }
            }
            return HttpResponse.success(result);
        } catch (Exception e) {
            LOGGER.error("查询入库列表失败", e);
            return HttpResponse.failure(ResultCode.STOCK_IN_LIST_QUERY_ERROR);
        }
    }
}

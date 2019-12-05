package com.hanyun.scm.api.domain.request.stock;

import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.domain.request.Replenishment.ReplenishmentApplyRequest;
import com.hanyun.scm.api.domain.request.purchase.order.PurchaseOrderQueryRequest;
import com.hanyun.scm.api.utils.DateUtilGet;
import org.hibernate.validator.constraints.NotEmpty;

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
 * StockInListRequestDTO
 * Date: 2017/8/13 0013
 * Time: 13:40
 *
 * @author tangqiming@hanyun.com
 */
public class StockInListRequestDTO {
    @NotEmpty
    private String brandId;

    private String storeId;
    @NotEmpty
    private String userId;

    private String date;

    //采购订单
    public PurchaseOrderQueryRequest setPurchaseRequest(StockInListRequestDTO dto){
        Integer[] date = getDate(dto.getDate());
        PurchaseOrderQueryRequest request = new PurchaseOrderQueryRequest();
//        request.setUserId(dto.getUserId());
        request.setBrandId(dto.getBrandId());
        request.setStoreId(dto.getStoreId());
        request.setOrderStatus(Consts.PURCHASE_ORDER_STATUS_CONFIRMED);
        request.setStockInStatus("333");
        request.setStartTime(DateUtilGet.getFirstDayForMonth(date[0], date[1]));
        request.setEndTime(DateUtilGet.getLastDayOfMonth(date[0], date[1]));
        return request;
    }

    //补货申请单
    public ReplenishmentApplyRequest setApplyRequest(StockInListRequestDTO dto){
        Integer[] date = getDate(dto.getDate());
        ReplenishmentApplyRequest request = new ReplenishmentApplyRequest();
//        request.setUserId(dto.getUserId());
        request.setBrandId(dto.getBrandId());
        request.setStoreId(dto.getStoreId());
        request.setQueryBeginTime(DateUtilGet.getFirstDayForMonth(date[0], date[1]));
        request.setQueryEndTime(DateUtilGet.getLastDayOfMonth(date[0], date[1]));
        request.setReplenishmentStatus(Consts.REPLENISHEMTN_APPLY_STATUS_CONFIRMED);
        request.setReceiptQueryByApp("yes");
//        request.setReceiptStatus(Consts.REPLENISHMENT_APPLY_RECEIPT_ALL);
//        request.setDistributeStatus(Consts.REPLENISHMENT_APPLY_DISTRIBUTE_STATUS_IMPORTED);
        return request;
    }

    /**
     * 返回时间
     * @param date 时间字符串
     * @return Integer[]
     */
    public static Integer[] getDate (String date){
        String[] company = date.split("/");
        Integer[] time = new Integer[company.length];
        for(int i = 0; i < company.length; i++){
            time[i] = Integer.parseInt(company[i]);
        }
        return time;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

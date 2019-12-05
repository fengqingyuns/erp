package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.BaseParams;
import com.hanyun.scm.api.domain.StockPicking;
import com.hanyun.scm.api.domain.dto.PurchasePaymentDTO;
import com.hanyun.scm.api.domain.request.payment.PurchasePaymentRequest;
import com.hanyun.scm.api.domain.request.payment.PurchaseQueryRequest;
import com.hanyun.scm.api.domain.request.stock.StockPickingQueryForAuditOrderRequest;
import com.hanyun.scm.api.domain.request.stock.StockPickingRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

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
 */
@SuppressWarnings("UnnecessaryInterfaceModifier")
@Repository
public interface StockPickingDao {

    public int deleteByStockPickingId(String stockPickingId);

    public int updateByStockPickingId(StockPicking record);
    
    public int insertSelective(StockPicking record);

    public StockPicking selectByStockPickingId(String stockPickingId);

    public List<StockPicking> select(StockPickingRequest stockPickingRequest);
    
    public int countAll(StockPickingRequest stockPickingRequest);
    
    public StockPicking queryBystockPickingId(StockPickingRequest stockPickingRequest);

    //查询收货单
    public int countReceiptAll(StockPicking record);

    public List<StockPicking> queryReceipt(StockPicking record);

    
    public int countAllStockInAndOut(StockPickingRequest stockPickingRequest);
    
    public List<StockPicking> selectStockInAndOut(StockPickingRequest stockPickingRequest);

    public List<PurchasePaymentDTO> queryPurchaseOrder(PurchaseQueryRequest purchaseQueryRequest);

    public int countPurchaseOrder(PurchaseQueryRequest purchaseQueryRequest);

    public int payment(PurchasePaymentRequest purchasePaymentRequest);

    public PurchasePaymentDTO sumPurchaseOrder(PurchaseQueryRequest purchaseQueryRequest);

    public List<StockPicking> selectForAuditOrder(StockPickingQueryForAuditOrderRequest query);

    public List<String> queryIds(BaseParams params);

}
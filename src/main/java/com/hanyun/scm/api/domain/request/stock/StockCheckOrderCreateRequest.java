package com.hanyun.scm.api.domain.request.stock;

import com.hanyun.scm.api.domain.StockCheckOrder;
import com.hanyun.scm.api.domain.StockCheckOrderDetail;

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
public class StockCheckOrderCreateRequest extends StockCheckOrder {

    private List<StockCheckOrderDetail> stockCheckOrderDetailList;

    public List<StockCheckOrderDetail> getStockCheckOrderDetailList() {
        return stockCheckOrderDetailList;
    }

    public void setStockCheckOrderDetailList(List<StockCheckOrderDetail> stockCheckOrderDetailList) {
        this.stockCheckOrderDetailList = stockCheckOrderDetailList;
    }

}
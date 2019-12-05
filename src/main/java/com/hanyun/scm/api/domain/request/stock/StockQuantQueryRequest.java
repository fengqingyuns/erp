package com.hanyun.scm.api.domain.request.stock;

import com.hanyun.scm.api.domain.StockQuant;

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
public class StockQuantQueryRequest extends StockQuant {

    private String condition;               //模糊查询  sku码 名称 国条

    private String supplierId;              //供应商id

    private String queryGroup;              //是否分组  有值就分,没值就不分组(有值:商品库存量查询,没值:仓库查询)
    
    private List<String> classfiyIdList;

    private String features;                //规格

    private Long minAmount;                 //最小值

    private Long maxAmount;                 //最大值

    @Override
    public String getFeatures() {
        return features;
    }

    @Override
    public void setFeatures(String features) {
        this.features = features;
    }

    public Long getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Long minAmount) {
        this.minAmount = minAmount;
    }

    public Long getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Long maxAmount) {
        this.maxAmount = maxAmount;
    }

    @Override
    public String getCondition() {
        return condition;
    }

    @Override
    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getQueryGroup() {
        return queryGroup;
    }

    public void setQueryGroup(String queryGroup) {
        this.queryGroup = queryGroup;
    }

    public List<String> getClassfiyIdList() {
        return classfiyIdList;
    }

    public void setClassfiyIdList(List<String> classfiyIdList) {
        this.classfiyIdList = classfiyIdList;
    }
}
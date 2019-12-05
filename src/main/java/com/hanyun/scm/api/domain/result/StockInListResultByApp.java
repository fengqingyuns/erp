package com.hanyun.scm.api.domain.result;

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
 * StockInListResultByApp
 * Date: 2017/8/14 0014
 * Time: 18:30
 *
 * @author tangqiming@hanyun.com
 */
public class StockInListResultByApp {

    private String date;

    private List<StockInResult> results;

    public StockInListResultByApp(){}

    public StockInListResultByApp(String date, List<StockInResult> results){
        this.date = date;
        this.results = results;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StockInResult> getResults() {
        return results;
    }

    public void setResults(List<StockInResult> results) {
        this.results = results;
    }
}

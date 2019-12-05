package com.hanyun.scm.api.thread;

import com.hanyun.ground.util.date.DateFormatUtil;
import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.domain.dto.StockInAndOut;
import com.hanyun.scm.api.domain.dto.StockInAndOutDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
 * ThreadPoolTaskTest
 * Date: 2017/6/18
 * Time: 下午3:36
 *
 * @author tianye@hanyun.com
 */
public class ThreadPoolTaskTest {

    public static void main(String[] args) {
        StockInAndOut stockInAndOut = getPurchaseStockIn();
        String task = "测试";
        ThreadPool.getFixedInstance().execute(new ThreadPoolTask(stockInAndOut, task));
    }


    private static StockInAndOut getPurchaseStockIn() {
        StockInAndOut stockInAndOut = new StockInAndOut();
        stockInAndOut.setMessageType("STOCK_ORDER");
        stockInAndOut.setBrandId("AD7F2B3EE5339D4A65986EF2D15C2CE2F0");
        stockInAndOut.setStoreId("AHD2CC6BF68F1248C4BF7584DA901E8DEB");
        stockInAndOut.setOrderId(IdUtil.uuid());
        stockInAndOut.setOrderShowId("CR" + DateFormatUtil.format(new Date(), "yyyyMMddHHmmss"));
        stockInAndOut.setSrcOrderId(IdUtil.uuid());
        stockInAndOut.setSrcOrderShowId("CD" + DateFormatUtil.format(new Date(), "yyyyMMddHHmmss"));
        stockInAndOut.setToWarehouseId("843ccfe574f44b00aaaa301883ba749c");
        stockInAndOut.setToWarehouseName("来跑吧跑步服务站（长沙贺龙体育场店");
        stockInAndOut.setSupplierId("1");
        stockInAndOut.setSupplierName("亚瑟士(中国)商贸有限公司");
        stockInAndOut.setOrderType(Consts.STOCK_IN_OUT_PURCHASE_STOCK_IN);
        stockInAndOut.setTestFlag(1);
        List<StockInAndOutDetail> stockInAndOutDetailList = new ArrayList<>();
        StockInAndOutDetail stockInAndOutDetail = new StockInAndOutDetail();
        stockInAndOutDetail.setGoodsId("2247");
        stockInAndOutDetail.setGoodsName("SAUCONY-FREEDOM-ISO-17春夏（红940.5）");
        stockInAndOutDetail.setBarCode("11055627");
        stockInAndOutDetail.setSkuCode("11055627");
        stockInAndOutDetail.setClassifyId("3");
        stockInAndOutDetail.setClassifyName("鞋");
        stockInAndOutDetail.setUnit("");
        stockInAndOutDetail.setFeatures("");
        stockInAndOutDetail.setPrice(62954L);
        stockInAndOutDetail.setAmount(1L);
        stockInAndOutDetail.setCreateTime(new Date());
        stockInAndOutDetail.setUpdateTime(new Date());
        stockInAndOutDetailList.add(stockInAndOutDetail);
        stockInAndOut.setStockInDetailList(stockInAndOutDetailList);
        return stockInAndOut;
    }

}
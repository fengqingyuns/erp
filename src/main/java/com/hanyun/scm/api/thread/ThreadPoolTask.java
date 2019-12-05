package com.hanyun.scm.api.thread;

import com.alibaba.druid.util.StringUtils;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.domain.dto.StockInAndOut;
import com.hanyun.scm.api.domain.dto.StockInAndOutDetail;
import com.hanyun.scm.api.utils.AliMessageServiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * ThreadPoolTask
 * Date: 2017/6/18
 * Time: 下午3:36
 *
 * @author tianye@hanyun.com
 */
public class ThreadPoolTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolTask.class);
    // 保存任务所需要的数据
    private StockInAndOut stockInAndOut;
    private String task;

    public ThreadPoolTask(StockInAndOut stockInAndOut, String task) {
        this.stockInAndOut = stockInAndOut;
        this.stockInAndOut.setMessageType("STOCK_ORDER");
        this.task = task;
    }

    @Override
    public void run() {
        LOGGER.info("开始执行任务：" + task);
        try {
            if (!StringUtils.equals(stockInAndOut.getBrandId(), Consts.LAIPAOBA_BRAND_ID)) {
                stockInAndOut.setTestFlag(1);
            }
            AliMessageServiceApi.sendMessage(JsonUtil.toJson(stockInAndOut));
            LOGGER.info("执行成功!");
        } catch (Exception e) {
            LOGGER.error("推送出库入库相关订单失败!");
        }
    }
}
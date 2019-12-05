package com.hanyun.scm.api.utils;

import com.hanyun.ground.util.http.HttpClient;
import com.hanyun.scm.api.consts.AliMessageSendConstant;
import com.hanyun.scm.api.domain.alimessage.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
 * 17/3/10 0010
 * Time: 13:45
 *
 * @author zhangpengcheng@hanyun.com
 */
public class AliMessageServiceApi {
    private static Logger LOGGER = LoggerFactory.getLogger(AliMessageServiceApi.class);

    public static void sendMessage(String jsonData){
        LOGGER.info(" RS PREPARE TO SEND DATA : {}", jsonData);
        /** 获取GMT时间 **/
        String gmtTime = DateUtil.formatRfc822Date(new Date());

        /** 计算签名 **/
        Map<String, String> headers = new HashMap<>();
        headers.put(AliMessageSendConstant.DATE,gmtTime);
        headers.put(AliMessageSendConstant.X_MNS_VERSION, AliMessageSendConstant.X_MNS_VERSION_VALUE);
        headers.put(AliMessageSendConstant.CONTENT_TYPE, AliMessageSendConstant.CONTENT_TYPE_VALUE);
        String signature = AliMessageServiceUtil.getSignature(headers);

        /** 请求地址 **/
        String requestPath = AliMessageSendConstant.HOST+ AliMessageSendConstant.MESSAGE_QUEUE_SEND_PATH;

        /** 发送数据转换为xml格式 **/
        Message message = new Message();
        message.setMessageBody(jsonData); // use your own message body here
        message.setDelaySeconds(0);

        /** 转换数据为XML格式 **/
        String postData = null;
        try {
            postData = new MessageSerializer().serialize(message, AliMessageSendConstant.DEFAULT_ENCODING);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /** 发送请求 **/
        String result = HttpClient.post(requestPath).xml(postData)
                .setHeader(AliMessageSendConstant.DATE,gmtTime)
                .setHeader(AliMessageSendConstant.AUTHORIZATION,"MNS " + AliMessageSendConstant.ACCESS_KEY + ":"+ signature)
                .setHeader(AliMessageSendConstant.X_MNS_VERSION, AliMessageSendConstant.X_MNS_VERSION_VALUE)
                .setHeader(AliMessageSendConstant.CONTENT_TYPE, AliMessageSendConstant.CONTENT_TYPE_VALUE).action().result();
        LOGGER.info(" RS DATA SEND COMPLETE, THE RESPONSE : {}", result);
    }

}

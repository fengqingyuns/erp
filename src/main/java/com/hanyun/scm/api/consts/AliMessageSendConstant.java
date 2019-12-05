package com.hanyun.scm.api.consts;

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
 * Time: 13:42
 *
 * @author zhangpengcheng@hanyun.com
 */
@SuppressWarnings("UnnecessaryInterfaceModifier")
public interface AliMessageSendConstant {
    /** Personal Setting **/
//    public static final String ACCESS_KEY = "LTAI1TyxfW1HzGsa";
//    public static final String ACCESS_SECRET = "VIAnehtJIIKOWL8c9g2VeW2AEPOZhd";
//    public static final String MESSAGE_QUEUE_SEND = "Lily";
//    public static final String HOST = "https://1716488942640866.mns.cn-beijing.aliyuncs.com/";

    /** RS Setting **/
    public static final String ACCESS_KEY = "LTAIUPz1crkTrqh4";
    public static final String ACCESS_SECRET = "1OcBc3o7WHkTFM4ZNqpWP5WO1ttetf";
    public static final String MESSAGE_QUEUE_SEND = "laipao8-hanyun-record";
    public static final String HOST = "https://1373428649740809.mns.cn-beijing.aliyuncs.com/";

    public static final String DEFAULT_ENCODING = "UTF-8"; // Default encoding

    public static final String X_MNS_VERSION = "x-mns-version";
    public static final String X_MNS_VERSION_VALUE = "2015-06-06";
    public static final String DATE = "Date";
    public static final String AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_VALUE = "text/xml;charset=UTF-8";

    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final String CONTENT_MD5 = "Content-MD5";

    public static final String METHOD_POST = "POST";

    public static final String MESSAGE_QUEUE_SEND_PATH = "/queues/" + MESSAGE_QUEUE_SEND + "/messages";

    public static final String DEFAULT_XML_NAMESPACE = "http://mns.aliyuncs.com/doc/v1";
    public static final String MESSAGE_TAG = "Message";
    public static final String MESSAGE_BODY_TAG = "MessageBody";
    public static final String DELAY_SECONDS_TAG = "DelaySeconds";
    public static final String PRIORITY_TAG = "Priority";
    public static final String X_HEADER_MNS_PREFIX = "x-mns-";
    public static final String MESSAGE_TYPE = "messageType";
    /** 来跑吧生产数据品牌ID **/
    public static final String RS_BRAND_ID = "AD7F2B3EE5339D4A65986EF2D15C2CE2F0";
    public static final String RS_SHOP_ID = "shop_id";
    public static final String RS_SHOP_ID_VALUE = "3";
}

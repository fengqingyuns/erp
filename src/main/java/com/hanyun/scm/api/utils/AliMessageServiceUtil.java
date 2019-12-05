package com.hanyun.scm.api.utils;

import com.hanyun.scm.api.consts.AliMessageSendConstant;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
 * Time: 13:53
 *
 * @author zhangpengcheng@hanyun.com
 */
public class AliMessageServiceUtil {
    private static final String ALGORITHM = "HmacSHA1";
    private static final Object LOCK = new Object();
    private static Mac macInstance;

    public static String getSignature(Map<String, String> headers) {
        StringBuilder msnHeaders = new StringBuilder();
        StringBuilder stringToSign = new StringBuilder();
        String contentMd5 = headers.get(AliMessageSendConstant.CONTENT_MD5);
        if(StringUtils.isBlank(contentMd5)){
            contentMd5 = "";
        }
        String date = headers.get(AliMessageSendConstant.DATE);
        TreeMap<String, String> tmpHeaders = sortHeader(headers);
        if (tmpHeaders.size() > 0) {
            tmpHeaders.keySet().stream().filter(key -> key.toLowerCase().startsWith(AliMessageSendConstant.X_HEADER_MNS_PREFIX)).forEach(key ->
                msnHeaders.append(key).append(":").append(tmpHeaders.get(key)).append("\n")
            );
        }
        stringToSign.append(AliMessageSendConstant.METHOD_POST).append("\n").append(contentMd5)
                .append("\n").append(AliMessageSendConstant.CONTENT_TYPE_VALUE).append("\n").append(date)
                .append("\n").append(msnHeaders)
                .append(AliMessageSendConstant.MESSAGE_QUEUE_SEND_PATH);
        return computeSignature(stringToSign.toString());
    }

    private static TreeMap<String, String> sortHeader(Map<String, String> headers) {
        TreeMap<String, String> tmpHeaders = new TreeMap<>();
        Set<String> keySet = headers.keySet();
        for (String key : keySet) {
            if (key.toLowerCase().startsWith(AliMessageSendConstant.X_HEADER_MNS_PREFIX)) {
                tmpHeaders.put(key.toLowerCase(), headers.get(key));
            } else {
                tmpHeaders.put(key, headers.get(key));
            }
        }
        return tmpHeaders;
    }

    private static String computeSignature(String data) {
        try {
            byte[] signData = sign(AliMessageSendConstant.ACCESS_SECRET.getBytes(AliMessageSendConstant.DEFAULT_CHARSET),data.getBytes(AliMessageSendConstant.DEFAULT_ENCODING));
            return BinaryUtil.toBase64String(signData);
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Unsupported algorithm: " + AliMessageSendConstant.DEFAULT_ENCODING);
        }
    }

    private static byte[] sign(byte[] key, byte[] data) {
        try {
            if (macInstance == null) {
                synchronized (LOCK) {
                    if (macInstance == null) {
                        macInstance = Mac.getInstance(ALGORITHM);
                    }
                }
            }
            Mac mac;
            try {
                mac = (Mac) macInstance.clone();
            } catch (CloneNotSupportedException e) {
                mac = Mac.getInstance(ALGORITHM);
            }
            mac.init(new SecretKeySpec(key, ALGORITHM));
            return mac.doFinal(data);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Unsupported algorithm: " + ALGORITHM);
        } catch (InvalidKeyException ex) {
            throw new RuntimeException();
        }
    }
}

package com.hanyun.scm.api.utils;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

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
 * SearchClient
 * Date: 2017/7/15
 * Time: 下午4:49
 *
 * @author tianye@hanyun.com
 */

public class SearchClient {

    private static TransportClient client;

    private static Settings SETTINGS;

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchClient.class);

    public static TransportClient getClient() throws Exception {
        SETTINGS = Settings.builder().put("cluster.name", PropertiesUtil.getProperties("erp-api.properties").getProperty("es.cluster.name", "hy_es")).build();
        if (client == null) {
            synchronized (SearchClient.class) {
                client = new PreBuiltTransportClient(SETTINGS)
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(PropertiesUtil.getProperties("erp-api.properties").getProperty("es.ip")), 9300));
            }
        }
        AdminClient adminClient = null;
        try {
            adminClient = client.admin();
        } catch (Exception e) {
            LOGGER.error("es client 连接失败%>_<%!", e);
        }
        if (adminClient == null) {
            LOGGER.error("es 重连中。。。");
            client = new PreBuiltTransportClient(SETTINGS)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(PropertiesUtil.getProperties("erp-api.properties").getProperty("es.ip")), 9300));
            LOGGER.error("es 重连成功O(∩_∩)O!");
        }
        return client;
    }

    public static boolean createIndex(TransportClient client, String index) throws Exception {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .startObject("index")
                    .field("max_result_window", 100000000)
                    .endObject()
                    .startObject("analysis")
                    .startObject("analyzer")
                    .startObject("ik")
                    .field("tokenizer", "ik_smart")
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject();
            CreateIndexResponse response = client
                    .admin()
                    .indices()
                    .prepareCreate(index)
                    .setSettings(builder)
                    .get();
            return response.isShardsAcked();
        } catch (Exception e) {
            LOGGER.error("创建索引失败!", e);
            return false;
        }
    }

}

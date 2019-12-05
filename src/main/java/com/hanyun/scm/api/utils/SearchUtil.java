package com.hanyun.scm.api.utils;

import com.hanyun.scm.api.domain.StockQuant;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
 * SearchUtil
 * Date: 2017/7/15
 * Time: 下午4:45
 *
 * @author tianye@hanyun.com
 */
public class SearchUtil {

    private static final Settings SETTINGS = Settings.builder().put("cluster.name", "hy_es").build();

    private static TransportClient client;

    public static void init() throws UnknownHostException {
        client = new PreBuiltTransportClient(SETTINGS)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.10.1.43"), 9300));
    }

    public static void close() {
        client.close();
    }

    public static void insertOrUpdateStockQuant(StockQuant stockQuant) throws Exception{
        client.prepareIndex("index", "stock_quant")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("stock_quant_id", stockQuant.getStockQuantId())
                        .field("brand_id", stockQuant.getBrandId())
                        .field("store_id", stockQuant.getStoreId())
                        .field("warehouse_id", stockQuant.getWarehouseId())
                        .field("warehouse_name", stockQuant.getWarehouseName())
                        .field("goods_id", stockQuant.getGoodsId())
                        .field("goods_name", stockQuant.getGoodsName())
                        .field("goods_pic", stockQuant.getGoodsPic())
                        .field("classify_id", stockQuant.getClassifyId())
                        .field("classify_name", stockQuant.getClassifyName())
                        .field("goods_bar_code", stockQuant.getGoodsBarCode())
                        .field("goods_sku_code", stockQuant.getGoodsCode())
                        .field("features", stockQuant.getFeatures())
                        .field("goods_brand_name", stockQuant.getGoodsBrandName())
                        .field("unit_name", stockQuant.getUnitName())
                        .field("unit_price", stockQuant.getUnitPrice())
                        .field("stock_num", stockQuant.getStockNum())
                        .field("initial_unit_price", stockQuant.getInitialUnitPrice())
                        .field("initial_stock_num", stockQuant.getInitialStockNum())
                        .field("stock_upper", stockQuant.getStockUpper())
                        .field("stock_lower", stockQuant.getStockLower())
                        .field("stock_safe", stockQuant.getStockSafe())
                        .field("stock_num_usable", stockQuant.getStockNumUsable())
                        .field("stock_num_using", stockQuant.getStockNumUsing())
                        .field("stock_num_on_order", stockQuant.getStockNumOnOrder())
                        .field("create_time", stockQuant.getCreateTime())
                        .field("update_time", stockQuant.getUpdateTime())
                        .endObject())
                .setId(stockQuant.getStockQuantId()).execute().actionGet();
    }

    public static void testCreateIndex() throws Exception {

        System.err.println(SearchClient.createIndex(client, "index"));
    }

    public void testCreateType() throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("dynamic", true)
                .field("properties")
                .startObject()

                .field("product_name")
                .startObject()
                .field("type", "text")
                .field("analyzer", "ik_smart")
                .endObject()

                .field("brand_id").startObject().field("type", "keyword").endObject()
                .field("product_id").startObject().field("type", "keyword").endObject()

                .endObject()
                .endObject();

        PutMappingRequest request = Requests.putMappingRequest("index").source(builder).type("product");
        PutMappingResponse response = client.admin().indices().putMapping(request).actionGet();

        System.err.println(response.isAcknowledged());
    }

    public void testAddData() throws Exception {
//        Product product = new Product();
//        product.setProductName("33333333333");
//        product.setBrandId("11111111111");
//        product.setProductId("2222222222");
//        client.prepareIndex("index", "product")
//                .setSource(XContentFactory.jsonBuilder()
//                        .startObject()
//                        .field("product_name", product.getProductName())
//                        .field("brand_id", product.getBrandId())
//                        .field("product_id", product.getProductId())
//                        .endObject())
//                .setId(product.getProductId()).execute().actionGet();
    }

    public void testAddStock(StockQuant stockQuant) {

    }

    public void testDeleteIndex() throws Exception {
        client.admin().indices().prepareDelete("index").get();
    }

    public void testDeleteDocById() throws Exception {
        client.prepareDelete("index", "product", "2222222222").execute().actionGet();
    }

    public static void testQueryList() throws Exception {
        init();
        QueryBuilder query = QueryBuilders.matchAllQuery();
        QueryBuilders queryBuilders = null;
        SearchResponse response = client.prepareSearch("index").setTypes("stock_quant").setQuery(query)
                .setFrom(20)
                .setSize(200)
                .execute().actionGet();
        SearchHits hits = response.getHits();
        for (SearchHit o : hits) {
            System.err.println(o.getSourceAsString());
        }
        System.err.println(hits.getTotalHits());
        close();
    }

    public static void testQueryById() throws Exception {
        init();
    }

    public void testX() throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("dynamic", true)
                .field("properties")
                .startObject()

                .field("product_name")
                .startObject()
                .field("type", "text")
                .field("analyzer", "ik_smart")
                .endObject()

                .field("brand_id").startObject().field("type", "keyword").endObject()
                .field("product_id").startObject().field("type", "keyword").endObject()

                .endObject()
                .endObject();

        System.err.println(builder.string());
    }


    // 定义库存索引的映射类型
    public static void defineStockQuantIndexTypeMapping() {
        try {
            init();
            XContentBuilder mapBuilder = XContentFactory.jsonBuilder();
            mapBuilder.startObject()
                    .startObject("stock_quant")
                    .startObject("properties")
                    .startObject("stock_quant_id").field("type", "string").endObject()
                    .startObject("brand_id").field("type", "string").endObject()
                    .startObject("store_id").field("type", "string").endObject()
                    .startObject("warehouse_id").field("type", "string").endObject()
                    .startObject("warehouse_name").field("type", "string").endObject()
                    .startObject("goods_id").field("type", "string").endObject()
                    .startObject("goods_name").field("type", "string").endObject()
                    .startObject("goods_pic").field("type", "string").endObject()
                    .startObject("classify_id").field("type", "string").endObject()
                    .startObject("classify_name").field("type", "string").endObject()
                    .startObject("goods_bar_code").field("type", "string").endObject()
                    .startObject("goods_sku_code").field("type", "string").endObject()
                    .startObject("features").field("type", "string").endObject()
                    .startObject("goods_brand_name").field("type", "string").endObject()
                    .startObject("unit_name").field("type", "string").endObject()
                    .startObject("unit_price").field("type", "long").endObject()
                    .startObject("stock_num").field("type", "long").endObject()
                    .startObject("initial_unit_price").field("type", "long").endObject()
                    .startObject("initial_stock_num").field("type", "long").endObject()
                    .startObject("stock_upper").field("type", "long").endObject()
                    .startObject("stock_lower").field("type", "long").endObject()
                    .startObject("stock_safe").field("type", "long").endObject()
                    .startObject("stock_num_usable").field("type", "long").endObject()
                    .startObject("stock_num_using").field("type", "long").endObject()
                    .startObject("stock_num_on_order").field("type", "long").endObject()
                    .startObject("create_time").field("type", "date").endObject()
                    .startObject("update_time").field("type", "date").endObject()
                    .endObject()
                    .endObject()
                    .endObject();

            PutMappingRequest putMappingRequest = Requests
                    .putMappingRequest("index").type("stock_quant")
                    .source(mapBuilder);
            PutMappingResponse response = client.admin().indices().putMapping(putMappingRequest).actionGet();
            System.err.println(response.isAcknowledged());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
        close();
    }

    public static void main(String[] args) throws Exception {
        testQueryList();
    }
}

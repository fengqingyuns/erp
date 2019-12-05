package com.hanyun.scm.api;

import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.scm.api.dao.StockQuantDao;
import com.hanyun.scm.api.domain.StockQuant;
import com.hanyun.scm.api.domain.request.stock.StockQuantQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.service.StockQuantService;
import com.hanyun.scm.api.utils.PropertiesUtil;
import com.hanyun.scm.api.utils.SearchClient;
import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Collections;
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
 * DataTest
 * Date: 2017/7/17
 * Time: 上午11:28
 *
 * @author tianye@hanyun.com
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class DataTest {

    private static final Settings SETTINGS = Settings.builder().put("cluster.name", "hy_es").build();

    @Resource
    private StockQuantDao stockQuantDao;

    @Resource
    private StockQuantService stockQuantService;

    private TransportClient client;

    private RestClient restClient;

    private String index;

    private String type;

    private Date begin;

    @Before
    public void init() throws Exception {
        begin = new Date();
        index = PropertiesUtil.getProperties("erp-api.properties").getProperty("es.index");
        type = PropertiesUtil.getProperties("erp-api.properties").getProperty("es.type.stock.single");
        client = new PreBuiltTransportClient(SETTINGS)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(PropertiesUtil.getProperties("erp-api.properties").getProperty("es.ip")), 9300));
        restClient = RestClient.builder(
                new HttpHost("10.10.1.43", 9200, "http"),
                new HttpHost("10.10.1.43", 9201, "http")).build();
    }

    @After
    public void close() throws Exception {
        client.close();
        restClient.close();
        System.err.println("执行时间: " + (new Date().getTime() - begin.getTime()));
    }

    @Test
    public void testCreateIndex() throws Exception {
        System.err.println(SearchClient.createIndex(client, index));
    }

    @Test
    public void testUpdateIndex() throws Exception {

    }

    @Test
    public void testCreateType() throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("dynamic", true)
                .field("properties")
                .startObject()

                .field("goods_name")
                .startObject()
                .field("type", "text")
                .field("analyzer", "ik_smart")
                .endObject()

                .field("classify_name")
                .startObject()
                .field("type", "text")
                .field("analyzer", "ik_smart")
                .endObject()

                .field("warehouse_name")
                .startObject()
                .field("type", "text")
                .field("analyzer", "ik_smart")
                .endObject()

                .field("goods_bar_code")
                .startObject()
                .field("type", "text")
                .field("analyzer", "ik_smart")
                .endObject()

                .field("goods_brand_name")
                .startObject()
                .field("type", "text")
                .field("analyzer", "ik_smart")
                .endObject()

                .field("unit_name")
                .startObject()
                .field("type", "text")
                .field("analyzer", "ik_smart")
                .endObject()

                .field("features")
                .startObject()
                .field("type", "text")
                .field("analyzer", "ik_smart")
                .endObject()

                .field("stock_quant_id").startObject().field("type", "keyword").endObject()
                .field("brand_id").startObject().field("type", "keyword").endObject()
                .field("store_id").startObject().field("type", "keyword").endObject()
                .field("warehouse_id").startObject().field("type", "keyword").endObject()
                .field("goods_id").startObject().field("type", "keyword").endObject()
                .field("classify_id").startObject().field("type", "keyword").endObject()
                .field("goods_sku_code").startObject().field("type", "keyword").endObject()
                .field("unit_price").startObject().field("type", "long").endObject()
                .field("stock_num").startObject().field("type", "long").endObject()
                .field("initial_stock_num").startObject().field("type", "long").endObject()
                .field("stock_upper").startObject().field("type", "long").endObject()

                .endObject()
                .endObject();

        PutMappingRequest request = Requests.putMappingRequest(index).source(builder).type(type);
        PutMappingResponse response = client.admin().indices().putMapping(request).actionGet();

        System.err.println(response.isAcknowledged());
    }

    @Test
    public void testSynchronized() {
        StockQuantQueryRequest query = new StockQuantQueryRequest();
        query.setCount(99999999);
        List<StockQuant> list = stockQuantDao.select(query);
        int errorCount = 0;
        for (StockQuant stockQuant : list) {
            try {
                client.prepareIndex(index, type)
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
            } catch (Exception e) {
                errorCount++;
            }
        }
        System.err.println("数据总条数: " + list.size() + "其中插入失败数据条数: " + errorCount);
    }

    @Test
    public void testQuery() throws Exception {
        QueryBuilder query = QueryBuilders.matchAllQuery();
        SearchResponse response = client.prepareSearch(index).setTypes(type).setQuery(query)
                .setFrom(20)
                .setSize(200)
                .execute().actionGet();
        SearchHits hits = response.getHits();
        for (SearchHit o : hits) {
            System.err.println(o.getSourceAsString());
        }
        System.err.println(hits.getTotalHits());
    }

    @Test
    public void testQueryById() throws Exception {
        StockQuant stockQuant = stockQuantService.selectById("253e424a9c5a4c30a83361724ce52acd");
        System.err.println(JsonUtil.toJson(stockQuant));
    }

    @Test
    public void testQueryWithParam() throws Exception {
        Date clientBegin = new Date();
        StockQuantQueryRequest query = new StockQuantQueryRequest();
        query.setCondition("李宁");
//        query.setClassifyId("0dbbab76-a8e5-4e78-b977-41706d1a0de1");
//        query.setBrandId("AD322CD8CF5FDE4AC0BA7FC3A0BCD89F57");
//        query.setCondition("蓝色");
        query.setPageSize(200);
        query.setPageNum(1);
        BaseResponse result = stockQuantService.selectWithParam(query);
        System.err.println(JsonUtil.toJson(result));
    }

    @Test
    public void testDeleteType() throws Exception {
        int pageSize = 10000;
        int pageNum = 1;
        int count = delete(pageSize, pageNum);
        System.err.println("数据总数:" + count);
        while (count > pageSize * pageNum) {
            pageNum++;
            delete(pageSize, pageNum);
        }
    }

    private int delete(int pageSize, int pageNum) throws Exception {
        StockQuantQueryRequest quantQueryRequest = new StockQuantQueryRequest();
        quantQueryRequest.setPageSize(pageSize);
        quantQueryRequest.setPageNum(pageNum);
        BaseResponse result = stockQuantService.selectWithParam(quantQueryRequest);
        List<StockQuant> stockQuantList = result.getList();
        for (StockQuant stockQuant : stockQuantList) {
            client.prepareDelete(index, type, stockQuant.getStockQuantId()).execute().actionGet();
        }
        return result.getCount();
    }

    @Test
    public void testAggregation() throws Exception {
        int pageSize = 100;
        int pageNum = 1;
        int record = pageSize * (pageNum - 1);
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        SearchRequestBuilder searchRequestBuilder = client
                .prepareSearch(index).setTypes(type)
                .setQuery(queryBuilder);
        //创建TermsBuilder对象，使用term查询，设置该分组的名称为aggs-class，并根据aggsField字段进行分组
        TermsAggregationBuilder goodsTermsBuilder = AggregationBuilders.terms("goodsId")
                .field("goods_id").shardSize(99999999).size(99999999);//此处也可继续设置order、size等
        SumAggregationBuilder sumStockNumBuilder = AggregationBuilders.sum("stockNum")
                .field("stock_num");
        SumAggregationBuilder sumTotalPrice = AggregationBuilders.sum("totalPrice")
                .field("total_price");
        SumAggregationBuilder sumTotalInitPrice = AggregationBuilders.sum("totalInitialPrice")
                .field("total_initial_price");
        goodsTermsBuilder.subAggregation(sumStockNumBuilder).subAggregation(sumTotalPrice).subAggregation(sumTotalInitPrice);
        //添加分组信息
        searchRequestBuilder.addAggregation(goodsTermsBuilder);
        //执行搜索
        SearchResponse searchResponse = searchRequestBuilder.setFrom(0).setSize(99999999).execute()
                .actionGet();
        //解析返回数据，获取分组名称为goodsId的数据
        Terms terms = searchResponse.getAggregations().get("goodsId");
        Collection<Terms.Bucket> buckets = terms.getBuckets();
        int index = 0;
        for (Terms.Bucket bucket : buckets) {
            if (index < record) {
                index++;
                continue;
            } else if (index == pageSize * pageNum) {
                break;
            }
            Sum stockNumSum = bucket.getAggregations().get("stockNum");
            Sum totalPriceSum = bucket.getAggregations().get("totalPrice");
            Sum totalInitialPrice = bucket.getAggregations().get("totalInitialPrice");
            System.err.println("goodsId为" + bucket.getKeyAsString() + "的库存为" + stockNumSum.getValue() + ", 库存余额为" + totalPriceSum.getValue() + ", 初始余额为" + totalInitialPrice.getValue());
        }
    }

    @Test
    public void testRestQuery() throws Exception {
        Date begin = new Date();
        Response response = restClient.performRequest("GET", "/" + index + "/" + type + "/_search", Collections.singletonMap("pretty", "true"));
        System.err.println("查询时间: " + (new Date().getTime() - begin.getTime()));
        System.err.println(EntityUtils.toString(response.getEntity()));
    }


}

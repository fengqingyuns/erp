package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.DistributionOrderDetail;
import com.hanyun.scm.api.domain.GoodsOdoo;
import com.hanyun.scm.api.domain.InspectionPickingInDetail;
import com.hanyun.scm.api.domain.PurchaseOrderDetail;
import com.hanyun.scm.api.domain.ReplenishmentApplyDetail;
import com.hanyun.scm.api.domain.StockCheckOrderDetail;
import com.hanyun.scm.api.domain.StockCheckTaskDetail;
import com.hanyun.scm.api.domain.StockQuant;
import com.hanyun.scm.api.domain.dto.QueryBillGoodsDTO;
import com.hanyun.scm.api.domain.request.goods.GoodsQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.domain.result.StockQuantResult;
import com.hanyun.scm.api.exception.GoodsException;

import java.util.List;
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
 * GoodsOdooService
 * Date: 2016/10/23
 * Time: 上午9:56
 *
 * @author tianye@hanyun.com
 */
public interface GoodsOdooService {

    /**
     * 查询商品信息
     *
     * @param goodsQueryRequestRequest 查询商品参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse select(GoodsQueryRequest goodsQueryRequestRequest);

    /**
     * 查询商品信息结果集
     *
     * @param goodsQueryRequestRequest 查询商品参数
     * @return BaseResponse
     * @author tianye@hanyun.com
     */
    BaseResponse selectGoods(GoodsQueryRequest goodsQueryRequestRequest) throws GoodsException;

    /**
     * 查询商品详情
     *
     * @param goodsId 商品id
     * @return
     */
    GoodsOdoo selectById(String goodsId);

    /**
     * 查询单据商品信息
     *
     * @param dto 查询对象
     * @return HttpResponse
     */
    HttpResponse selectBillGoods(QueryBillGoodsDTO dto);

    /**
     * 通过采购订单商品id去获取基础商品信息
     *
     * @param brandId 品牌id
     * @param list    采购订单列表
     * @return map
     */
    Map<String, GoodsOdoo> getPurchaseOrderGoodsMap(String brandId, List<PurchaseOrderDetail> list);

    /**
     * 通过配送单商品id去获取基础商品信息
     *
     * @param brandId 品牌id
     * @param list    配送单列表
     * @return map
     */
    Map<String, GoodsOdoo> getDistributionGoodsMap(String brandId, List<DistributionOrderDetail> list);

    /**
     * 通过申请单商品id去获取基础商品信息
     *
     * @param brandId 品牌id
     * @param list    申请单详情列表
     * @return map
     */
    Map<String, GoodsOdoo> getReplenishmentApplyGoodsMap(String brandId, List<ReplenishmentApplyDetail> list);

    /**
     * 通过库存商品id去获取基础商品信息(库存查询)
     *
     * @param brandId 品牌id
     * @param list    库存列表
     * @return Map
     */
    Map<String, GoodsOdoo> getStockQuantResultGoodsMap(String brandId, List<StockQuantResult> list);

    /**
     * 通过库存商品id去获取基础商品信息(单独查库存)
     *
     * @param brandId 品牌id
     * @param list    库存列表
     * @return Map
     */
    Map<String, GoodsOdoo> getStockQuantGoodsMap(String brandId, List<StockQuant> list);

    /**
     * 通过盘点单商品id去获取基础商品信息
     *
     * @param brandId 品牌id
     * @param list    盘点单详细列表
     * @return Map
     */
    Map<String, GoodsOdoo> getStockCheckOrderGoodsMap(String brandId, List<StockCheckOrderDetail> list);

    /**
     * 通过验收入库单商品id去获取基础商品信息
     *
     * @param brandId 品牌id
     * @param list    验收入库单详细列表
     * @return Map
     */
    Map<String, GoodsOdoo> getInspectionGoodsMap(String brandId, List<InspectionPickingInDetail> list);

    /**
     * 通过盘点商品id去获取基础商品信息
     *
     * @param brandId 品牌id
     * @param list    验收入库单详细列表
     * @return Map
     */
    Map<String, GoodsOdoo> getStockcheckTaskGoodsMap(String brandId, List<StockCheckTaskDetail> list);
}

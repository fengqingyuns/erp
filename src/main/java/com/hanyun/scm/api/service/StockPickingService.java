package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.StockPicking;
import com.hanyun.scm.api.domain.StockPickingGoods;
import com.hanyun.scm.api.domain.request.stock.StockPickingRequest;
import com.hanyun.scm.api.exception.StockPickingException;

import java.util.List;

@SuppressWarnings("rawtypes")
public interface StockPickingService {

    /**
     * @param stockPickingRequest
     * @return
     * @Title: selectByType
     * @Description: 查询类型
     * @author: 乔宇
     * @return: HttpResponse
     * @date: 2016年12月13日 下午7:29:08
     */
    HttpResponse selectByType(StockPickingRequest stockPickingRequest);

    /**
     * @param stockPickingRequest
     * @return
     * @Title: queryBystockPickingId
     * @Description: 查询采购入库单
     * @author: 高杨
     * @return: HttpResponse
     * @date: 2016年12月13日 下午7:28:54
     */
    HttpResponse queryBystockPickingId(StockPickingRequest stockPickingRequest);

    /**
     * @param stockPicking
     * @return
     * @Title: modifyStockPicking
     * @Description: 修改采购入库单
     * @author: 高杨
     * @return: HttpResponse
     * @date: 2016年12月13日 下午7:27:50
     */
    HttpResponse modifyStockPicking(StockPicking stockPicking);

    /**
     * @param stockPicking
     * @return
     * @Title: create
     * @Description: 创建采购入库单
     * @author: 高杨
     * @return: HttpResponse
     * @date: 2016年12月13日 下午7:28:19
     */
    HttpResponse create(StockPicking stockPicking);

    /**
     * @param stockPickingRequest;
     * @return
     * @Title: query
     * @Description: 查询出入库单
     * @author: 乔宇
     * @return: HttpResponse
     * @date: 2016年12月20日
     */
    HttpResponse selectStockInAndOut(StockPickingRequest stockPickingRequest);

    /**
     * 查询采购移库信息
     *
     * @param stockPickingRequest 查询移库(入库和出库)信息参数
     * @return HttpResponse
     * @author tianye@hanyun.com
     */
    HttpResponse stockPickingForPurchase(StockPickingRequest stockPickingRequest);

    /**
     * 根据id查询采购入库/采购退货信息
     *
     * @param stockPickingId 采购入库/退货单id
     * @return HttpResponse
     * @throws Exception 异常
     * @author tianye@hanyun.com
     */
    StockPicking selectByStockPickingId(String stockPickingId) throws StockPickingException;

    /**
     * 批量添加采购入库/退货商品
     *
     * @param stockPickingId        采购入库/退货单id
     * @param stockPickingGoodsList 商品列表
     * @return 是否添加成功
     */
    boolean insertStockPickingGoodsList(String stockPickingId, List<StockPickingGoods> stockPickingGoodsList) throws StockPickingException;

    /**
     * 导出其他出入库
     *
     * @param stockPickingRequest
     * @return
     */
    List<StockPicking> exportStockInAndOut(StockPickingRequest stockPickingRequest);

    /**
     * 转仓单导出
     *
     * @param stockPickingRequest
     * @return
     */
    List<StockPicking> exportRequision(StockPickingRequest stockPickingRequest);

    /**其他出入库编辑导出
     * @param stockPickingId
     * @return
     */
    List<StockPickingGoods> exportStockInAndOutGoods(String stockPickingId, Integer type);

    List<StockPickingGoods> exportRequisitionUpdate(StockPickingRequest stockPickingRequest);

    /**
     * 取消订单
     *
     * @param stockPickingId 订单id
     * @return 返回值
     */
    int cancelStockPicking(String stockPickingId);

    /**
     * 提审
     *
     * @param stockPickingId 单号
     * @return 返回值
     */
    HttpResponse commit(String stockPickingId);

    /**
     * 删除
     *
     * @param stockPickingId 单号
     * @return  返回值
     */
    HttpResponse delete(String stockPickingId);
}

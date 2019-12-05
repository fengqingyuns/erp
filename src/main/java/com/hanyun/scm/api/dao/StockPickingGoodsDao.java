package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.StockPickingGoods;
import com.hanyun.scm.api.domain.request.stock.PurchasePickingRequest;
import com.hanyun.scm.api.domain.request.stock.StockPickingGoodsRequest;
import com.hanyun.scm.api.domain.response.PurchasePickingAmountResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑, 永无BUG!
 * 　　　　┃　　　┃Code is far away from bug with the animal protecting
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * </pre>
 */
@SuppressWarnings("UnnecessaryInterfaceModifier")
@Repository
public interface StockPickingGoodsDao {

	/**
	 * 
	 * @Title: deleteByPickingGoodsId 
	 * @Description: 删除入库单明细
	 * @author: 高杨
	 * @param pickingGoodsId
	 * @return
	 * @return: int
	 * @date: 2016年12月13日 下午6:55:34
	 */
    public int deleteByPickingGoodsId(String pickingGoodsId);
    
    /**
     * 
     * @Title: deleteByPickingId 
     * @Description: 删除入库单
     * @author: 高杨
     * @param stockPickingId
     * @return
     * @return: int
     * @date: 2016年12月13日 下午6:56:05
     */
    public int deleteByPickingId(String stockPickingId);
    
    /**
     * 
     * @Title: insertSelective 
     * @Description: 创建入库单明细
     * @author: 高杨
     * @param record
     * @return
     * @return: int
     * @date: 2016年12月13日 下午6:56:44
     */
    public int insertSelective(StockPickingGoods record);

    /**
     * 
     * @Title: selectByPickingGoodsId 
     * @Description: 查询采购入库单商品
     * @author: 高杨
     * @param pickingGoodsId
     * @return
     * @return: StockPickingGoods
     * @date: 2016年12月13日 下午6:57:01
     */
    public StockPickingGoods selectByPickingGoodsId(String pickingGoodsId);

    public List<StockPickingGoods> select(StockPickingGoodsRequest stockPickingGoodsRequest);

    public int updateByPickingGoodsId(StockPickingGoods record);

    public int countAll(StockPickingGoodsRequest stockPickingGoodsRequest);

    public int deleteByPrimaryKey(@Param("goodsId") String goodsId, @Param("stockPickingId") String stockPickingId);
    
    public int updateByPrimaryKeySelective(StockPickingGoods record);
    
    public int deleteGoodsById(String GoodsId, String pickingGoodsId);

    List<PurchasePickingAmountResponse> countPurchasePickingAmount(PurchasePickingRequest purchasePickingRequest);
    
    public List<StockPickingGoods> selectStockPickingGoods(StockPickingGoods stockPickingGoods);
	
	public int updateByPrimaryKey(StockPickingGoods stockPickingGoods);
	
	public int countGoodsAll(StockPickingGoods record);
	
	/**
	 * 
	 * @Title: querySupplierStockPickingGoods
	 * @Description: TODO
	 * @param @param stockPickingGoodsRequest
	 * @param @return
	 * @return List<StockPickingGoods>
	 * @throws
	 */
	public List<StockPickingGoods> querySupplierStockPickingGoods(StockPickingGoodsRequest stockPickingGoodsRequest);
	
	public List<Integer> countAllGoods(StockPickingGoodsRequest stockPickingGoodsRequest);
	
	public List<StockPickingGoods> selectReturnGoods(StockPickingGoodsRequest stockPickingGoodsRequest);
	
	public List<StockPickingGoods> selectGoodsList(StockPickingGoods stockPickingGoods);

	public List<StockPickingGoods> selectByStockPickingId(String stockPickingId);

	public List<StockPickingGoods> queryBrandStockIn(@Param("ids") List<String> ids);

	public List<StockPickingGoods> queryStoreStockIn(@Param("ids") List<String> ids);

}
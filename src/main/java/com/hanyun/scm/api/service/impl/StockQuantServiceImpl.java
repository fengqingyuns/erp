package com.hanyun.scm.api.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hanyun.ground.util.date.DateFormatUtil;
import com.hanyun.ground.util.http.HttpClient;
import com.hanyun.ground.util.id.IdUtil;
import com.hanyun.ground.util.json.JsonUtil;
import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.Consts;
import com.hanyun.scm.api.consts.MapConsts;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.dao.*;
import com.hanyun.scm.api.domain.*;
import com.hanyun.scm.api.domain.dto.StockInAndOut;
import com.hanyun.scm.api.domain.request.BrandStoreRequest;
import com.hanyun.scm.api.domain.request.goods.GoodsQueryRequest;
import com.hanyun.scm.api.domain.request.stock.SellGoodsRequest;
import com.hanyun.scm.api.domain.request.stock.StockPickingGoodsRequest;
import com.hanyun.scm.api.domain.request.stock.StockQuantQueryRequest;
import com.hanyun.scm.api.domain.request.stock.StoreGoodsStock;
import com.hanyun.scm.api.domain.request.warehouse.WarehouseQueryRequest;
import com.hanyun.scm.api.domain.response.BaseResponse;
import com.hanyun.scm.api.domain.response.ExportResponse;
import com.hanyun.scm.api.domain.response.StockQuantResponse;
import com.hanyun.scm.api.domain.response.stock.TotalStockResponse;
import com.hanyun.scm.api.domain.result.StockQuantResult;
import com.hanyun.scm.api.exception.StockQuantException;
import com.hanyun.scm.api.service.GoodsOdooService;
import com.hanyun.scm.api.service.StockQuantService;
import com.hanyun.scm.api.service.SupplierService;
import com.hanyun.scm.api.thread.ThreadPool;
import com.hanyun.scm.api.thread.ThreadPoolTask;
import com.hanyun.scm.api.utils.GoodsUtil;
import com.hanyun.scm.api.utils.PropertiesUtil;
import com.hanyun.scm.api.utils.SearchClient;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
 * StockQuantServiceImpl
 * Date: 2016/10/25
 * Time: 下午8:43
 *
 * @author tianye@hanyun.com
 */
@Service
public class StockQuantServiceImpl implements StockQuantService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockQuantServiceImpl.class);


    @Resource
    private StockQuantDao stockQuantDao;

    @Resource
    private WarehouseDao warehouseDao;

    @Resource
    private StockPickingGoodsDao stockPickingGoodsDao;

    @Resource
    private StockPickingDao stockPickingDao;

    @Resource
    private DisassemblyOrderDao disassemblyOrderDao;

    @Resource
    private GoodsOdooService goodsOdooService;

    @Resource
    private StockSpillLossOrderDetailDao stockSpillLossOrderDetailDao;

    @Resource
    private StockSpillLossOrderDao stockSpillLossOrderDao;

    @Resource
    private InspectionPickingInDetailDao inspectionPickingInDetailDao;

    @Resource
    private StockQuantChangeHistoryDao stockQuantChangeHistoryDao;

    @Resource
    private SupplierService supplierService;

    /**
     * 添加库存信息
     *
     * @param stockQuant 参数
     * @return
     */
    @Override
    public HttpResponse create(StockQuant stockQuant) {
        try {
            String id = IdUtil.uuid();
            stockQuant.setStockQuantId(id);
            insertEsStockQuant(stockQuant);
            return HttpResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("创建库存信息失败!", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    /**
     * 重构代码查询库存分布明细信息
     *
     * @param stockQuant 参数
     * @return
     */
    @Override
    public HttpResponse queryByDetail(StockQuantQueryRequest stockQuant) {

        try {
            if (StringUtils.isNotEmpty(stockQuant.getQueryGroup())) {
                return HttpResponse.success(selectGroupWithParam(stockQuant));
            } else {
                return HttpResponse.success(selectWithParam(stockQuant));
            }
        } catch (Exception e) {
            LOGGER.error("查询库存信息失败!", e);
            return HttpResponse.failure(ResultCode.STOCKPICKINGGOODS_QUERY_ERROR);
        }
    }


    /**
     * 查询库存信息详情
     *
     * @param id id
     * @return 返回值
     */
    @Override
    public HttpResponse detail(String id) {
        try {
            StockQuant stockQuant;
            stockQuant = selectById(id);
            if (stockQuant == null) {
                stockQuant = selectById(id);
            }/**以上代码没用**/
            if (stockQuant == null) {
                LOGGER.error("该库存信息不存在!");
                return HttpResponse.failure(ResultCode.DATA_NOT_FOUND);
            }
            return HttpResponse.success(stockQuant);
        } catch (Exception e) {
            LOGGER.error("查询仓库信息详情失败!", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    /**
     * 查询库存信息列表
     *
     * @param stockQuant 参数
     * @return 返回值
     */
    @Override
    public HttpResponse select(StockQuantQueryRequest stockQuant) {
        try {
            BaseResponse response = selectWithParam(stockQuant);
            List<StockQuantResult> stockQuantList = response.getList();
            removeInitialValue(stockQuantList);
            return HttpResponse.success(response);
        } catch (Exception e) {
            LOGGER.error("查询库存信息列表失败!", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    /**
     * 查询库存列表
     *
     * @param stockQuant
     * @return
     * @throws StockQuantException
     */
    private BaseResponse selectList(StockQuantQueryRequest stockQuant) throws StockQuantException {
        int count = stockQuantDao.countAll(stockQuant);
        stockQuant.setCount(count);
        List<StockQuant> stockQuantList = stockQuantDao.select(stockQuant);
        return new BaseResponse(count, stockQuantList);
    }

    /**
     * 查詢庫存明細列表
     */
    @Override
    public HttpResponse selectStockDetail(StockQuantQueryRequest stockQuant) {
        try {
            List<StockQuant> list = stockQuantDao.countAllDetial(stockQuant);
            int count = list.size();
            stockQuant.setCount(count);

            Map<String, Long> mapList = new HashMap<String, Long>();//goodsId/warhouserId
            Map<String, Object> mapList1 = new HashMap<String, Object>();//用来输出最多仓库
            Map<String, Object> doubleMap = new HashMap<String, Object>();//用来存放GoodsId、warserId 对应的上限 下限。
            List<StockQuant> stockQuantList = stockQuantDao.selectDetail(stockQuant);


            String interimWarehouseId = stockQuant.getWarehouseId();
            stockQuant.setWarehouseId(null);
            List<StockQuant> AllwarehouseQuantList = stockQuantDao.selectDetail(stockQuant);
            //所有仓库加起来的总数量
            Map<String, Long> TotalNummap = new HashMap<String, Long>();
            for (StockQuant sq : AllwarehouseQuantList) {
                TotalNummap.put(sq.getGoodsId(), sq.getTotalNum());
            }
            for (StockQuant s : stockQuantList) {
                s.setAllTotalNum(TotalNummap.get(s.getGoodsId()));
            }
            stockQuant.setWarehouseId(interimWarehouseId);
            //所有仓库加起来的总数量
            List<StockQuant> warhouserList = new ArrayList<StockQuant>();
            StockQuant stockQuant1 = new StockQuant();
            stockQuant1.setBrandId(stockQuant.getBrandId());
            //stockQuant1.setStoreId(stockQuant.getStoreId());

            Integer max = 0;
            String maxGoodsId = null;
            String maxwarhouserId = null;
            String goodsId = null;
            String warhouserId = null;


            for (int i = 0; i < stockQuantList.size(); i++) {
                goodsId = stockQuantList.get(i).getGoodsId();
                stockQuant1.setGoodsId(goodsId);
                warhouserList = stockQuantDao.selectALL(stockQuant1);

                for (int j = 0; j < warhouserList.size(); j++) {
                    Long[] str = new Long[3];
                    warhouserId = warhouserList.get(j).getWarehouseId();
                    Long resultNum = warhouserList.get(j).getResutNum();
                    Long safe = warhouserList.get(j).getStockSafe();
                    Long Lower = warhouserList.get(j).getStockLower();
                    Long upper = warhouserList.get(j).getStockUpper();
                    //下限 、安全、上限
                    str[0] = Lower;
                    str[1] = safe;
                    str[2] = upper;

                    if (warhouserList.size() > max) {
                        max = warhouserList.size();
                        maxGoodsId = goodsId;
                        maxwarhouserId = warhouserId;
                    }
                    doubleMap.put(goodsId + "," + warhouserId, str);
                    mapList.put(goodsId + "," + warhouserId, resultNum);
                    mapList1.put(goodsId + "," + warhouserId, warhouserList);
                }

                stockQuantList.get(i).setStockNum(stockQuantList.get(i).getTotalNum());
            }
            int countOne1;
            List<StockQuant> stockAllList;
            BaseResponse stockAllListResponse = selectWithParam(stockQuant);
            if (stockAllListResponse == null) {
                countOne1 = 0;
                stockAllList = new ArrayList();
            } else {
                countOne1 = stockAllListResponse.getCount();
                stockAllList = stockAllListResponse.getList();
            }
            Map<String, String> nameMap = new TreeMap<String, String>();

            //将最多的warhouserId存放到list中
            List<StockQuant> warhouserIdList = new ArrayList<StockQuant>();
            List<Warehouse> warhouserNameList = new ArrayList<Warehouse>();
            if (stockQuantList.size() > 0) {
                List obj = (List) mapList1.get(maxGoodsId + "," + maxwarhouserId);
                for (int i = 0; i < obj.size(); i++) {
                    warhouserIdList.add((StockQuant) obj.get(i));
                }
            }
            for (StockQuant stockQuant2 : warhouserIdList) {
                String warehouseId = stockQuant2.getWarehouseId();
                Warehouse warehouse = warehouseDao.selectByWarehouseId(warehouseId);
                if (warehouse != null) {
                    String warehouseName = warehouse.getWarehouseName();
                    warhouserNameList.add(warehouse);
                    nameMap.putIfAbsent(warehouseId, warehouseName);
                }
            }

            List<StockQuant> StockClassfilyList = stockQuantDao.selectProductClassfily(stockQuant);
            StockQuantResponse response = new StockQuantResponse();
            response.setCount(count);
            response.setList(stockQuantList);
            response.setWarhouserNameList(warhouserNameList);
            response.setStockClassfilyList(StockClassfilyList);
            response.setMax(max);
            response.setMaxGoodsId(maxGoodsId);
            response.setMaxwarhouserId(maxwarhouserId);
            response.setMapList(mapList);
            response.setMapList1(mapList1);
            response.setDoubleMap(doubleMap);
            //库存预预警表使用
            response.setCountOne1(countOne1);
            response.setStockAllList(stockAllList);
            response.setNameMap(nameMap);
            return HttpResponse.success(response);
        } catch (Exception e) {
            LOGGER.error("查询库存信息列表失败!", e);
            return HttpResponse.failure(ResultCode.STOCKQUNAT_QUERY_ERROR);
        }

    }

    /**
     * @param stockQuant
     * @throws StockQuantException
     * @Title: exportExcelByProfitAndLoss
     * @Description: 库存分布明细excel导出
     * @author: 唐启明
     * @date: 2016年11月9日 下午9:38:43
     */
    @Override
    public ExportResponse selectGoodsWarehouse(StockQuantQueryRequest stockQuant) throws StockQuantException {
        //库存分布明细返回的list
        List<List<Object>> resultList = new ArrayList<List<Object>>();

        //业务查询处理 ----------------------------------------------------------------begin
        Map<String, Long> mapList = new HashMap<String, Long>();//goodsId/warhouserId
        Map<String, Object> mapList1 = new HashMap<String, Object>();//用来输出最多仓库

        List<StockQuant> warhouserList = new ArrayList<StockQuant>();//仓库List
        StockQuant stockQuant2 = new StockQuant();//查询商品的实体
        stockQuant2.setBrandId(stockQuant.getBrandId());
        //lt
        stockQuant2.setStoreId(stockQuant.getStoreId());

        List<StockQuant> stockQuantList = stockQuantDao.selectDetail(stockQuant);
        Integer max = 0;
        String maxGoodsId = null;
        String maxwarhouserId = null;
        String goodsId = null;
        String warhouserId = null;
        for (StockQuant stockQuant1 : stockQuantList) {
            goodsId = stockQuant1.getGoodsId();
            stockQuant2.setGoodsId(goodsId);
            warhouserList = stockQuantDao.selectALL(stockQuant2);

            for (int j = 0; j < warhouserList.size(); j++) {
                warhouserId = warhouserList.get(j).getWarehouseId();
                Long resultNum = warhouserList.get(j).getResutNum();

                if (warhouserList.size() > max) {
                    max = warhouserList.size();
                    maxGoodsId = goodsId;
                    maxwarhouserId = warhouserId;
                }
                mapList.put(goodsId + "," + warhouserId, resultNum);
                mapList1.put(goodsId + "," + warhouserId, warhouserList);
            }
        }
        //将最多仓库的对象放入一个list中
        List<StockQuant> listOne = new ArrayList<StockQuant>();
        StockQuant stockQuanta = new StockQuant();
        stockQuanta.setStockNum(stockQuant.getResutNum());
        List obj = (List) mapList1.get(maxGoodsId + "," + maxwarhouserId);

        if (obj != null) {
            for (int i = 0; i < obj.size(); i++) {
                listOne.add((StockQuant) obj.get(i));
            }
        }

        //商品分类Map查询
        Map<String, String> classFilyMap = new HashMap<String, String>();
        GoodsClassifyBrand goodsClassifyBrand = new GoodsClassifyBrand();
        goodsClassifyBrand.setBrandId(stockQuant.getBrandId());
        List<GoodsClassifyBrand> classFilyList;
        try {
            classFilyList = GoodsUtil.getClassify(goodsClassifyBrand);
        } catch (Exception e) {
            LOGGER.error("获取商品分类失败!", e);
            throw new RuntimeException("获取商品分类失败!");
        }
        for (GoodsClassifyBrand classband : classFilyList) {
            classFilyMap.put(classband.getClassifyId(), classband.getClassifyName());
        }
        //构造返回的list
        int index = 0;
        for (StockQuant stockResult : stockQuantList) {
            ++index;
            String reslutGoodsId = stockResult.getGoodsId();
            Double stockPrice = new Double(stockResult.getUnitPrice());
            if (stockPrice == null || stockPrice.equals("")) {
                stockPrice = 0.0;
            }
            //返回的list
            List<Object> beanList = new ArrayList<>();
            beanList.add(index);
            beanList.add(classFilyMap.get(stockResult.getClassifyId()));
            beanList.add(MapConsts.getGoodsType().get(stockResult.getGoodsType()));
            beanList.add(stockResult.getGoodsBarCode());
            beanList.add(stockResult.getGoodsName());
            beanList.add(stockResult.getUnitName());
            beanList.add(stockResult.getTotalNum());

            for (int i = 0; i < listOne.size(); i++) {
                String resultWarehouseId = listOne.get(i).getWarehouseId();
                Long stock = mapList.get(reslutGoodsId + "," + resultWarehouseId) != null ? mapList.get(reslutGoodsId + "," + resultWarehouseId) : 0L;
                Double stockNum1 = (stock * stockPrice / 100) > 0 ? stock * stockPrice / 100 : 0.0;
                String str = stock + "/" + stockNum1;
                beanList.add(str);
            }
            resultList.add(beanList);
        }

        String[] resultWarehouse = new String[listOne.size()];//返回的仓库名
        //插入第一行的所有仓库
        for (int i = 0; i < listOne.size(); i++) {
            String warehouseIdTh = listOne.get(i).getWarehouseId();
            Warehouse warehouse = warehouseDao.selectByWarehouseId(warehouseIdTh);//仓库名
            String warehouseName = warehouse.getWarehouseName();
            String str1 = warehouseName + "\n库存/库存金额";
            resultWarehouse[i] = str1;
        }
        //业务查询处理 ----------------------------------------------------------------end
        ExportResponse response = new ExportResponse(resultList, resultWarehouse);
        return response;
    }

    /**
     * 单库存导出
     *
     * @param stockQuant
     * @throws StockQuantException
     */
    @Override
    public List<List<Object>> exportExcelByProfitAndLossSingle(StockQuantQueryRequest stockQuant) throws StockQuantException {

        List<List<Object>> resultList = new ArrayList<List<Object>>();
        //业务查询处理 ----------------------------------------------------------------begin
        Map<String, Long> mapList = new HashMap<String, Long>();//goodsId/warhouserId
        Map<String, Object> mapList1 = new HashMap<String, Object>();//用来输出最多仓库

        List<StockQuant> warhouserList = new ArrayList<StockQuant>();//仓库List
        StockQuant stockQuant2 = new StockQuant();
        stockQuant2.setBrandId(stockQuant.getBrandId());
        //lt
        stockQuant2.setStoreId(stockQuant.getStoreId());

        List<StockQuant> stockQuantList = stockQuantDao.selectDetail(stockQuant);
        Integer max = 0;
        String maxGoodsId = null;
        String maxwarhouserId = null;
        String goodsId = null;
        String warhouserId = null;
        for (StockQuant stockQuant1 : stockQuantList) {
            goodsId = stockQuant1.getGoodsId();
            stockQuant2.setGoodsId(goodsId);
            warhouserList = stockQuantDao.selectALL(stockQuant2);

            for (int j = 0; j < warhouserList.size(); j++) {
                warhouserId = warhouserList.get(j).getWarehouseId();
                Long resultNum = warhouserList.get(j).getResutNum();

                if (warhouserList.size() > max) {
                    max = warhouserList.size();
                    maxGoodsId = goodsId;
                    maxwarhouserId = warhouserId;
                }
                mapList.put(goodsId + "," + warhouserId, resultNum);
                mapList1.put(goodsId + "," + warhouserId, warhouserList);
            }
        }

        //商品分类Map查询
        Map<String, String> classFilyMap = new HashMap<String, String>();
        GoodsClassifyBrand goodsClassifyBrand = new GoodsClassifyBrand();
        goodsClassifyBrand.setBrandId(stockQuant.getBrandId());
        List<GoodsClassifyBrand> classFilyList;
        try {
            classFilyList = GoodsUtil.getClassify(goodsClassifyBrand);
        } catch (Exception e) {
            LOGGER.error("获取商品分类失败!", e);
            throw new RuntimeException("获取商品分类失败!");
        }
        for (GoodsClassifyBrand classband : classFilyList) {
            classFilyMap.put(classband.getClassifyId(), classband.getClassifyName());
        }

        int index = 0;        //下标
        for (StockQuant stockResult : stockQuantList) {
            ++index;
            List<Object> beanList = new ArrayList<>();
            beanList.add(index);
            beanList.add(classFilyMap.get(stockResult.getClassifyId()));
            beanList.add(MapConsts.getGoodsType().get(stockResult.getGoodsType()));
            beanList.add(stockResult.getGoodsBarCode());
            beanList.add(stockResult.getGoodsName());
            beanList.add(stockResult.getUnitName());
            beanList.add(stockResult.getStockLower());
            beanList.add(stockResult.getStockSafe());
            beanList.add(stockResult.getStockUpper());
            resultList.add(beanList);
        }
        //业务查询处理 ----------------------------------------------------------------end
        return resultList;
    }

    /**
     * 多库存导出
     *
     * @param stockQuant
     * @throws StockQuantException
     */
    @Override
    public ExportResponse exportExcelByProfitAndLossDouble(StockQuantQueryRequest stockQuant) throws StockQuantException {
        List<List<Object>> resultList = new ArrayList<List<Object>>();
        //业务查询处理 ----------------------------------------------------------------begin

        Map<String, Long> mapList = new HashMap<String, Long>();//goodsId/warhouserId
        Map<String, Object> mapList1 = new HashMap<String, Object>();//用来输出最多仓库
        Map<String, Object> doubleMap = new HashMap<String, Object>();//用来存放GoodsId、warserId 对应的上限 下限。
        List<StockQuant> warhouserList = new ArrayList<StockQuant>();//仓库List
        StockQuant stockQuant2 = new StockQuant();
        stockQuant2.setBrandId(stockQuant.getBrandId());
        stockQuant2.setStoreId(stockQuant.getStoreId());

        List<StockQuant> stockQuantList = stockQuantDao.selectDetail(stockQuant);
        Integer max = 0;
        String maxGoodsId = null;
        String maxwarhouserId = null;
        String goodsId = null;
        String warhouserId = null;
        for (StockQuant stockQuant1 : stockQuantList) {
            goodsId = stockQuant1.getGoodsId();
            stockQuant2.setGoodsId(goodsId);
            warhouserList = stockQuantDao.selectALL(stockQuant2);

            for (int j = 0; j < warhouserList.size(); j++) {
                Long[] str = new Long[3];
                warhouserId = warhouserList.get(j).getWarehouseId();
                Long resultNum = warhouserList.get(j).getResutNum();
                Long safe = warhouserList.get(j).getStockSafe();
                Long Lower = warhouserList.get(j).getStockLower();
                Long upper = warhouserList.get(j).getStockUpper();
                //下限 、安全、上限
                str[0] = Lower;
                str[1] = safe;
                str[2] = upper;

                if (warhouserList.size() > max) {
                    max = warhouserList.size();
                    maxGoodsId = goodsId;
                    maxwarhouserId = warhouserId;
                }
                doubleMap.put(goodsId + "," + warhouserId, str);
                mapList.put(goodsId + "," + warhouserId, resultNum);
                mapList1.put(goodsId + "," + warhouserId, warhouserList);
            }
        }
        //将最多仓库的对象放入一个list中
        List<StockQuant> listOne = new ArrayList<StockQuant>();
        StockQuant stockQuanta = new StockQuant();
        stockQuanta.setStockNum(stockQuant.getResutNum());
        List obj = (List) mapList1.get(maxGoodsId + "," + maxwarhouserId);
        if (obj != null) {
            for (int i = 0; i < obj.size(); i++) {
                listOne.add((StockQuant) obj.get(i));
            }
        }

        //商品分类Map查询
        Map<String, String> classFilyMap = new HashMap<String, String>();
        GoodsClassifyBrand goodsClassifyBrand = new GoodsClassifyBrand();
        goodsClassifyBrand.setBrandId(stockQuant.getBrandId());
        List<GoodsClassifyBrand> classFilyList;
        try {
            classFilyList = GoodsUtil.getClassify(goodsClassifyBrand);
        } catch (Exception e) {
            LOGGER.error("获取商品分类失败!", e);
            throw new RuntimeException("获取商品分类失败!");
        }
        for (GoodsClassifyBrand classband : classFilyList) {
            classFilyMap.put(classband.getClassifyId(), classband.getClassifyName());
        }
        int index = 0;
        for (StockQuant stockResult : stockQuantList) {
            String resultGoodsId = stockResult.getGoodsId();
            ++index;
            List<Object> beanList = new ArrayList<>();
            beanList.add(index);
            beanList.add(classFilyMap.get(stockResult.getClassifyId()));
            beanList.add(MapConsts.getGoodsType().get(stockResult.getGoodsType()));
            beanList.add(stockResult.getGoodsBarCode());
            beanList.add(stockResult.getGoodsName());
            beanList.add(stockResult.getUnitName());
            for (int i = 0; i < listOne.size(); i++) {
                String str = "";
                String resultWarehouseId = listOne.get(i).getWarehouseId();
                Long[] obj12 = (Long[]) doubleMap.get(resultGoodsId + "," + resultWarehouseId);
                if (obj12 == null) {
                    str = "-----";//无此仓库
                } else {
                    Long safe = obj12[0] == null ? 0 : obj12[0];
                    Long Lower = obj12[1] == null ? 0 : obj12[1];
                    Long upper = obj12[2] == null ? 0 : obj12[2];
                    str = safe + "/" + Lower + "/" + upper;
                }
                beanList.add(str);
            }
            resultList.add(beanList);
        }
        String[] resultWarehouse = new String[listOne.size()];
        //插入第一行的所有仓库
        for (int i = 0; i < listOne.size(); i++) {
            String warehouseIdTh = listOne.get(i).getWarehouseId();
            Warehouse warehouse = warehouseDao.selectByWarehouseId(warehouseIdTh);//仓库名
            String warehouseName = warehouse.getWarehouseName();
            String str1 = warehouseName + "\n库存下线/安全库存/库存上限";
            resultWarehouse[i] = str1;
        }

        //业务查询处理 ----------------------------------------------------------------end
        ExportResponse response = new ExportResponse(resultList, resultWarehouse);
        return response;
    }

    /**
     * 修改库存信息
     *
     * @param stockQuant
     * @return
     */
    @Override
    public HttpResponse modify(StockQuant stockQuant) {
        try {
            StockQuant old = selectById(stockQuant.getStockQuantId());
            if (old == null) {
                LOGGER.error("修改库存信息失败!");
                return HttpResponse.failure(ResultCode.DATA_NOT_FOUND);
            }
            updateEsByStockQuantId(stockQuant);
            return HttpResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("修改库存信息失败!", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    /**
     * 修改多库存信息
     */

    @Override
    public HttpResponse updateDoubleStockNum(StockQuant stockQuant) {
        try {
//            List<StockQuant> stockQuantDoubleList = stockQuant.getStockQuantDoubleList();
//            for (StockQuant stockQuant1 : stockQuantDoubleList) {
//                stockQuant1.setBrandId(stockQuant.getBrandId());
//                stockQuant1.setStoreId(stockQuant.getStoreId());
//                int index = stockQuantDao.countAll(stockQuant1);
//                if (index > 0) {
//                    stockQuantDao.updateByStockQuantGoodsIDorWarehouseId(stockQuant1);
//                }
//            }
            return HttpResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("修改多库存信息失败!", e);
            return HttpResponse.failure(ResultCode.STOCKQUANTSETUP_DOUBLEUPD_ERROR);
        }
    }


    /**
     * 修改单库存库存信息- stock_upper
     */
    @Override
    public HttpResponse updateStockQuant(StockQuant stockQuant) {
        try {
            StockQuant record = selectById(stockQuant.getStockQuantId());
            if (record == null) {
                throw new StockQuantException("库存单号:[" + stockQuant.getStockQuantId() + "]不存在");
            }
            int updateRow = updateEsByStockQuantId(stockQuant);
            if (updateRow <= 0) {
                LOGGER.error("更新库存失败");
                return HttpResponse.failure(ResultCode.STOCKQUANT_MODIFY_ERROR);
            }
            return HttpResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("修改单库存信息失败!", e);
            return HttpResponse.failure(ResultCode.STOCKQUANTSETUP_SINGLEUPD_ERROR);
        }

    }

    /**
     * 删除仓库信息
     *
     * @param goodsId
     * @return
     */
    @Override
    public HttpResponse delete(String brandId, String goodsId) {
        try {
            StockQuantQueryRequest stockQuantQueryRequest = new StockQuantQueryRequest();
            stockQuantQueryRequest.setBrandId(brandId);
            stockQuantQueryRequest.setGoodsId(goodsId);
            List<StockQuant> stockQuantList = stockQuantDao.select(stockQuantQueryRequest);
            if (stockQuantList == null || stockQuantList.size() == 0) {
                LOGGER.error("删除商品库存失败--库存不存在！");
                return HttpResponse.failure(ResultCode.DATA_NOT_FOUND);
            }
            for (StockQuant stockQuant : stockQuantList) {
                if (stockQuant == null) {
                    continue;
                }
                //stockQuantDao.deleteByStockQuantId(stockQuant.getStockQuantId());
                stockQuantDao.deleteByStockQuantId(stockQuantList.get(0).getStockQuantId());
                deleteEsData(null, null, stockQuant.getStockQuantId());
            }
            deleteEsData(brandId, goodsId, null);
            return HttpResponse.success(ResultCode.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("删除仓库信息失败!", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    /**
     * 更新库存by stockPicking
     *
     * @param stockPicking
     * @return
     */
    @Override
    public void updateQuantByStockPicking(StockPicking stockPicking, Integer type) {
        try {
            StockPicking old = null;
            List<StockPickingGoods> stockPickingGoodsList;
            if (StringUtils.isEmpty(stockPicking.getStockPickingId())) {
                stockPickingGoodsList = stockPicking.getStockPickingGoodsList();
            } else {
                old = stockPickingDao.selectByStockPickingId(stockPicking.getStockPickingId());
                StockPickingGoodsRequest stockPickingGoodsRequest = new StockPickingGoodsRequest();
                stockPickingGoodsRequest.setStockPickingId(stockPicking.getStockPickingId());
                stockPickingGoodsList = stockPickingGoodsDao.select(stockPickingGoodsRequest);
                old.setStockPickingGoodsList(stockPickingGoodsList);
            }
            //出库入库标识
            int inOutStatus;
            if (stockPicking.getStockPickingType() == Consts.STOCK_PICKING_TYPE_PURCHASE_STOCK_IN || stockPicking.getStockPickingType() == Consts.STOCK_PICKING_TYPE_STOCK_IN) {
                inOutStatus = Consts.STOCK_PICKING_TYPE_STOCK_IN;
            } else if (stockPicking.getStockPickingType() == Consts.STOCK_PICKING_TYPE_PURCHASE_RETURN || stockPicking.getStockPickingType() == Consts.STOCK_PICKING_TYPE_STOCK_OUT) {
                inOutStatus = Consts.STOCK_PICKING_TYPE_STOCK_OUT;
            } else {
                inOutStatus = Consts.STOCK_PICKING_TYPE_STOCK_INTERVAL;
            }
            StockQuantQueryRequest srcStockQuant = null;
            StockQuantQueryRequest toStockQuant = null;
            switch (inOutStatus) {
                //入库:更新目标仓库库存
                case Consts.STOCK_PICKING_TYPE_STOCK_IN:
                    toStockQuant = new StockQuantQueryRequest();
                    if (StringUtils.isEmpty(stockPicking.getToWarehouseId())) {
                        throw new RuntimeException("仓库不存在!");
                    }
                    toStockQuant.setWarehouseId(stockPicking.getToWarehouseId());
                    break;
                //出库:更新来源仓库库存
                case Consts.STOCK_PICKING_TYPE_STOCK_OUT:
                    srcStockQuant = new StockQuantQueryRequest();
                    if (StringUtils.isEmpty(stockPicking.getSrcWarehouseId())) {
                        throw new RuntimeException("仓库不存在!");
                    }
                    srcStockQuant.setWarehouseId(stockPicking.getSrcWarehouseId());
                    break;
                //移库:更新来源仓库和目标仓库库存
                case Consts.STOCK_PICKING_TYPE_STOCK_INTERVAL:
                    srcStockQuant = new StockQuantQueryRequest();
                    if (StringUtils.isEmpty(stockPicking.getSrcWarehouseId())) {
                        throw new RuntimeException("仓库不存在!");
                    }
                    srcStockQuant.setWarehouseId(stockPicking.getSrcWarehouseId());
                    toStockQuant = new StockQuantQueryRequest();
                    if (StringUtils.isEmpty(stockPicking.getToWarehouseId())) {
                        throw new RuntimeException("仓库不存在!");
                    }
                    toStockQuant.setWarehouseId(stockPicking.getToWarehouseId());
                    break;
            }
            for (StockPickingGoods stockPickingGoods : stockPickingGoodsList) {
                StockQuantChangeHistory toStockQuantChangeHistory = null;
                StockQuantChangeHistory srcStockQuantChangeHistory = null;
                int rows = 0;
                GoodsOdoo goodsOdoo = goodsOdooService.selectById(stockPickingGoods.getGoodsId());
                //更新目标仓库库存
                if (toStockQuant != null) {
                    toStockQuant.setGoodsId(stockPickingGoods.getGoodsId());
                    List<StockQuant> stockQuantList = stockQuantDao.selectWarehouseGoods(toStockQuant);
                    //库存存在则做更新
                    if (stockQuantList != null && stockQuantList.size() > 0) {
                        StockQuant stockQuant = stockQuantList.get(0);

                        //构造库存变更历史
                        toStockQuantChangeHistory = new StockQuantChangeHistory(stockQuant);
                        toStockQuantChangeHistory.setStockChangeNum(stockPickingGoods.getPickingAmount());

                        Long totalAmount = stockPickingGoods.getPickingAmount() + stockQuant.getStockNum();
                        if (stockPickingGoods.getPresentAmount() != null && stockPickingGoods.getPresentAmount() > 0) {
                            totalAmount += stockPickingGoods.getPresentAmount();
                        }
                        if (stockPickingGoods.getPickingPrice() != null && stockPickingGoods.getPickingPrice() > 0) {
                            Long totalPrice = stockPickingGoods.getPickingPrice() * stockPickingGoods.getPickingAmount() + stockQuant.getUnitPrice() * stockQuant.getStockNum();
                            Long newUnitPrice = (totalAmount == 0 ? 0 : (totalPrice / totalAmount));
                            stockQuant.setUnitPrice(newUnitPrice);
                        }
                        stockQuant.setStockNum(totalAmount);
                        if (StringUtils.isNotEmpty(stockPickingGoods.getGoodsBrandName())) {
                            stockQuant.setGoodsBrandName(stockPickingGoods.getGoodsBrandName());
                        }
                        stockQuant.setGoodsName(goodsOdoo.getGoodsName());
                        stockQuant.setUnitName(goodsOdoo.getUnitName());
                        stockQuant.setClassifyId(goodsOdoo.getClassifyId());
                        stockQuant.setClassifyName(goodsOdoo.getClassifyName());
                        stockQuant.setGoodsTypeName(goodsOdoo.getGoodsTypeName());
                        stockQuant.setGoodsBarCode(goodsOdoo.getGoodsBarCode());
                        stockQuant.setGoodsCode(goodsOdoo.getGoodsCode());
                        stockQuant.setFeatures(goodsOdoo.getFeatures());
                        stockQuant.setGoodsBrandName(goodsOdoo.getGoodsBrandName());
                        stockQuant.setGoodsPic(goodsOdoo.getGoodsPic());
                        rows = updateEsByStockQuantId(stockQuant);
                    }
                    if (rows == 0) {
                        Warehouse warehouse = warehouseDao.selectByWarehouseId(toStockQuant.getWarehouseId());
                        //仓库不存在不作处理
                        if (warehouse == null) {
                            continue;
                        }
                        toStockQuant.setBrandId(warehouse.getBrandId());
                        toStockQuant.setStoreId(warehouse.getStoreId());
                        //创建库存
                        String stockQuantId = IdUtil.uuid();
                        toStockQuant.setStockQuantId(stockQuantId);
                        toStockQuant.setGoodsName(goodsOdoo.getGoodsName());
                        toStockQuant.setUnitName(goodsOdoo.getUnitName());
                        toStockQuant.setClassifyId(goodsOdoo.getClassifyId());
                        toStockQuant.setClassifyName(goodsOdoo.getClassifyName());
                        toStockQuant.setGoodsTypeName(goodsOdoo.getGoodsTypeName());
                        toStockQuant.setGoodsBarCode(goodsOdoo.getGoodsBarCode());
                        toStockQuant.setGoodsCode(goodsOdoo.getGoodsCode());
                        toStockQuant.setFeatures(goodsOdoo.getFeatures());
                        toStockQuant.setGoodsBrandName(goodsOdoo.getGoodsBrandName());
                        toStockQuant.setGoodsPic(goodsOdoo.getGoodsPic());
                        //库存价格设置
                        switch (stockPicking.getStockPickingType()) {
                            //采购入库
                            case Consts.STOCK_PICKING_TYPE_PURCHASE_STOCK_IN:
                                if (stockPickingGoods.getPresentAmount() != null) {
                                    Long newTotalPrice = stockPickingGoods.getPickingPrice() * stockPickingGoods.getPickingAmount();
                                    newTotalPrice = newTotalPrice / (stockPickingGoods.getPickingAmount() + stockPickingGoods.getPresentAmount());
                                    toStockQuant.setUnitPrice(newTotalPrice);
                                } else {
                                    toStockQuant.setUnitPrice(stockPickingGoods.getPickingPrice());
                                }
                                break;
                            //普通入库
                            case Consts.STOCK_PICKING_TYPE_STOCK_IN:
                                toStockQuant.setUnitPrice(stockPickingGoods.getPickingPrice());
                                break;
                            //调拨
                            case Consts.STOCK_PICKING_TYPE_STOCK_INTERVAL:
                                toStockQuant.setUnitPrice(stockPickingGoods.getPickingPrice());
                                break;
                            default:
                                toStockQuant.setUnitPrice(0L);
                        }
                        if (stockPickingGoods.getPresentAmount() == null) {
                            toStockQuant.setStockNum(stockPickingGoods.getPickingAmount());
                        } else {
                            toStockQuant.setStockNum(stockPickingGoods.getPickingAmount() + stockPickingGoods.getPresentAmount());
                        }
                        insertEsStockQuant(JsonUtil.fromJson(JsonUtil.toJson(toStockQuant), StockQuant.class));
                        toStockQuantChangeHistory = new StockQuantChangeHistory(toStockQuant);
                        toStockQuantChangeHistory.setStockNum(0L);
                        toStockQuantChangeHistory.setStockChangeNum(stockPickingGoods.getPickingAmount());
                    }


                }
                //来源仓库库存更新
                if (srcStockQuant != null) {
                    srcStockQuant.setGoodsId(stockPickingGoods.getGoodsId());
                    List<StockQuant> stockQuantList = stockQuantDao.selectWarehouseGoods(srcStockQuant);
                    //有库存信息则更新
                    if (stockQuantList != null && stockQuantList.size() > 0) {
                        StockQuant stockQuant = stockQuantList.get(0);

                        //构造库存变更历史
                        srcStockQuantChangeHistory = new StockQuantChangeHistory(stockQuant);
                        srcStockQuantChangeHistory.setStockChangeNum(stockPickingGoods.getPickingAmount());

                        Long totalAmount = stockQuant.getStockNum() - stockPickingGoods.getPickingAmount();
                        stockQuant.setStockNum(totalAmount);
                        if (StringUtils.isNotEmpty(stockPickingGoods.getGoodsBrandName())) {
                            stockQuant.setGoodsBrandName(stockPickingGoods.getGoodsBrandName());
                        }
                        rows = updateEsByStockQuantId(stockQuant);
                    }
                    if (rows == 0) {
                        Warehouse warehouse = warehouseDao.selectByWarehouseId(srcStockQuant.getWarehouseId());
                        if (warehouse == null) {
                            continue;
                        }
                        srcStockQuant.setBrandId(warehouse.getBrandId());
                        srcStockQuant.setStoreId(warehouse.getStoreId());
                        //新增库存信息
                        String stockQuantId = IdUtil.uuid();
                        srcStockQuant.setStockQuantId(stockQuantId);
                        srcStockQuant.setGoodsName(goodsOdoo.getGoodsName());
                        srcStockQuant.setUnitName(goodsOdoo.getUnitName());
                        srcStockQuant.setClassifyId(goodsOdoo.getClassifyId());
                        srcStockQuant.setClassifyName(goodsOdoo.getClassifyName());
                        srcStockQuant.setGoodsTypeName(goodsOdoo.getGoodsTypeName());
                        srcStockQuant.setGoodsBarCode(goodsOdoo.getGoodsBarCode());
                        srcStockQuant.setGoodsCode(goodsOdoo.getGoodsCode());
                        srcStockQuant.setFeatures(goodsOdoo.getFeatures());
                        srcStockQuant.setGoodsBrandName(goodsOdoo.getGoodsBrandName());
                        srcStockQuant.setUnitPrice(goodsOdoo.getGoodsPrice());
                        srcStockQuant.setGoodsPic(goodsOdoo.getGoodsPic());
                        srcStockQuant.setStockNum(0 - stockPickingGoods.getPickingAmount());
                        insertEsStockQuant(JsonUtil.fromJson(JsonUtil.toJson(srcStockQuant), StockQuant.class));
                        toStockQuantChangeHistory = new StockQuantChangeHistory(toStockQuant);
                        toStockQuantChangeHistory.setStockNum(0L);
                        toStockQuantChangeHistory.setStockChangeNum(stockPickingGoods.getPickingAmount());
                    }
                }

                LOGGER.info("出入库类型为:{}, 品牌id为:{}, 出库仓库id为:{}, 入库仓库id为:{}, 商品id为:{}, 数量为:{}", stockPicking.getStockPickingType(), stockPicking.getBrandId(),
                        stockPicking.getSrcWarehouseId(), stockPicking.getToWarehouseId(), stockPickingGoods.getGoodsId(), stockPickingGoods.getPickingAmount());

                updateStockQuantChangeHistory(type, stockPicking, toStockQuantChangeHistory, srcStockQuantChangeHistory);
            }
            try {
                if (old != null && Consts.LAIPAOBA_BRAND_ID.equals(old.getBrandId())) {
                    StockInAndOut stockInAndOut = new StockInAndOut(old, warehouseDao);
                    String task = "{id:" + stockPicking.getStockPickingId() + ", type:" + stockPicking.getStockPickingType();
                    ThreadPool.getFixedInstance().execute(new ThreadPoolTask(stockInAndOut, task));
                }
            } catch (Exception e) {
                LOGGER.error("执行同步来跑吧库存任务失败!");
            }
        } catch (Exception e) {
            LOGGER.error("更新库存失败!", e);
        }
    }

    private void updateStockQuantChangeHistory(int type, StockPicking stockPicking, StockQuantChangeHistory toStockQuantChangeHistory, StockQuantChangeHistory srcStockQuantChangeHistory) {
        int stockChangeType;
        if (type == Consts.STOCK_QUANT_CHANGE_TYPE_TRANSFER) {
            stockChangeType = Consts.STOCK_IN_OUT_TRANSFER;
        } else if (type == Consts.STOCK_QUANT_CHANGE_TYPE_SELL) {
            stockChangeType = Consts.STOCK_IN_OUT_SELL;
        } else if (type == Consts.STOCK_QUANT_CHANGE_TYPE_REFUND) {
            stockChangeType = Consts.STOCK_IN_OUT_REFUND;
        } else {
            stockChangeType = stockPicking.getStockPickingType();
        }
        try {
            if (toStockQuantChangeHistory != null) {
                toStockQuantChangeHistory.setOrderId(stockPicking.getStockPickingId());
                toStockQuantChangeHistory.setOrderDocumentId(stockPicking.getStockPickingDocumentId());
                toStockQuantChangeHistory.setStockChangeType(stockChangeType);
                stockQuantChangeHistoryDao.insertSelective(toStockQuantChangeHistory);
            }
            if (srcStockQuantChangeHistory != null) {
                srcStockQuantChangeHistory.setOrderId(stockPicking.getStockPickingId());
                srcStockQuantChangeHistory.setOrderDocumentId(stockPicking.getStockPickingDocumentId());
                srcStockQuantChangeHistory.setStockChangeType(stockChangeType==Consts.STOCK_IN_OUT_INTERVAL?Consts.STOCK_IN_OUT_INTERVAL_OUT:stockChangeType);
                stockQuantChangeHistoryDao.insertSelective(srcStockQuantChangeHistory);
            }
        } catch (Exception e) {
            LOGGER.error("保存库存变更记录失败!", e);
        }
    }

    //库存查看--查询详单
    @Override
    public List<StockQuant> selectExexportStockQuantList(StockQuantQueryRequest stockQuant, List<Warehouse> warehouselist) throws Exception {
        List<StockQuant> stockQuantList = selectWithParam(stockQuant).getList();
        Map<String, String> warhousemap = new HashMap<>();
        Long Price = 0L;
        Long Num = 0L;
        for (Warehouse warhouse : warehouselist) {
            warhousemap.put(warhouse.getWarehouseId(), warhouse.getWarehouseName());
        }
        for (StockQuant sq : stockQuantList) {
            if (StringUtils.isEmpty(sq.getWarehouseId())) {
                sq.setWarehouseId("");
            }
            if (StringUtils.isEmpty(sq.getGoodsBarCode())) {
                sq.setGoodsBarCode("");
            }
            if (StringUtils.isEmpty(sq.getGoodsName())) {
                sq.setGoodsName("");
            }
            if (StringUtils.isEmpty(sq.getUnitName())) {
                sq.setUnitName("");
            }
            if (sq.getUnitPrice() == null) {
                sq.setUnitPrice(Consts.PARAMETER_INITIALIZE_LONG);
            }
            if (sq.getStockNum() == null) {
                sq.setStockNum(Consts.PARAMETER_INITIALIZE_LONG);
            }
            Price = sq.getUnitPrice();
            Num = sq.getStockNum();
            sq.setTotalPrice(Price * Num);
            sq.setWarehouseName(warhousemap.get(sq.getWarehouseId()));
        }
        return stockQuantList;
    }

    //库存查看--查询仓库
    @Override
    public List<Warehouse> selectWarhouseName(WarehouseQueryRequest warehouseQueryRequest) {
        List<Warehouse> list = warehouseDao.select(warehouseQueryRequest);
        return list;
    }
    /**以下代码没有用到**/
    /**
     * 查询调拨，出库库存信息列表
     *
     * @param stockQuant 参数
     * @return 返回值
     */
    @Override
    public HttpResponse selectAllocationGoods(StockQuantQueryRequest stockQuant) {
        try {
            if (StringUtils.isNotEmpty(stockQuant.getSkipIds())) {
                List<String> skipIdList = JsonUtil.fromJson(stockQuant.getSkipIds(), new TypeReference<List<String>>() {
                });
                if (skipIdList != null && skipIdList.size() > Consts.LIST_IS_EMPTY) {
                    stockQuant.setSkipIdList(skipIdList);
                }
            }
            if (StringUtils.isNotEmpty(stockQuant.getIds())) {
                List<String> idList = JsonUtil.fromJson(stockQuant.getIds(), new TypeReference<List<String>>() {
                });
                if (idList != null && idList.size() > Consts.LIST_IS_EMPTY) {
                    stockQuant.setIdList(idList);
                }
            }

            BaseResponse response = selectWithParam(stockQuant);
            return HttpResponse.success(response);
        } catch (Exception e) {
            LOGGER.error("查询库存信息列表失败!", e);
            return HttpResponse.failure(ResultCode.STOCKPICKING_QUERY_ERROR);
        }
    }
    /**以上代码没有用到**/
    @Override
    public HttpResponse selectALLGoodsBrand(StockQuant stockQuant) {
        List<StockQuant> list;
        try {
            list = stockQuantDao.selectALLGoodsBrand(stockQuant);
        } catch (Exception e) {
            LOGGER.error("查询库存信息列表失败!", e);
            return HttpResponse.failure(ResultCode.STOCKQUANT_QUERY_BRANDNAME_ERROR);
        }
        BaseResponse response = new BaseResponse(0, list);
        return HttpResponse.success(response);
    }

    @Override
    public HttpResponse sellGoods(SellGoodsRequest sellGoodsRequest) {
        try {
            StockPicking stockPicking = JsonUtil.fromJson(JsonUtil.toJson(sellGoodsRequest), StockPicking.class);
            stockPicking.setStockPickingId(null);
            if (StringUtils.isEmpty(stockPicking.getToWarehouseId())) {
                WarehouseQueryRequest warehouseQueryRequest = new WarehouseQueryRequest();
                warehouseQueryRequest.setBrandId(sellGoodsRequest.getBrandId());
                warehouseQueryRequest.setStoreId(sellGoodsRequest.getStoreId());
                List<Warehouse> warehouseList = warehouseDao.select(warehouseQueryRequest);
                if (warehouseList == null || warehouseList.size() == 0) {
                    return HttpResponse.failure(ResultCode.STOCK_QUANT_SELL_ERROR);
                }
                Warehouse warehouse = warehouseList.get(0);
                stockPicking.setSrcWarehouseId(warehouse.getWarehouseId());
                stockPicking.setSrcWarehouseName(warehouse.getWarehouseName());
            }
            List<StockPickingGoods> stockPickingGoodsList = stockPicking.getStockPickingGoodsList();
            int sellType = Consts.STOCK_QUANT_CHANGE_TYPE_SELL;
            for (StockPickingGoods stockPickingGoods : stockPickingGoodsList) {
                if (stockPickingGoods.getPickingAmount() < 0) {
                    sellType = Consts.STOCK_QUANT_CHANGE_TYPE_REFUND;
                    break;
                }
            }
            stockPicking.setStockPickingType(Consts.STOCK_PICKING_TYPE_STOCK_OUT);
            updateQuantByStockPicking(stockPicking, sellType);
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("售卖商品异常", e);
            return HttpResponse.failure(ResultCode.STOCK_QUANT_SYSTEM_ERROR);
        }

    }

    /**
     * 报损报溢单更新库存
     */
    @Override
    public void updateQuantBySpillAndLoss(StockSpillLossOrder stockSpillLossOrder) {
        try {
            StockSpillLossOrder old = null;
            List<StockSpillLossOrderDetail> stockSpillLossOrderDetailList;
            if (StringUtils.isEmpty(stockSpillLossOrder.getStockVarianceId())) {
                stockSpillLossOrderDetailList = stockSpillLossOrder.getDetailList();
            } else {
                old = stockSpillLossOrderDao.selectByVarianceId(stockSpillLossOrder.getStockVarianceId());
                StockSpillLossOrderDetail stockSpillLossOrderDetail = new StockSpillLossOrderDetail();
                stockSpillLossOrderDetail.setStockVarianceId(stockSpillLossOrder.getStockVarianceId());
                stockSpillLossOrderDetailList = stockSpillLossOrderDetailDao.select(stockSpillLossOrderDetail);
                old.setDetailList(stockSpillLossOrderDetailList);
            }
            //出库入库标识
            int inOutStatus;
            if (stockSpillLossOrder.getStockVarianceType() == Consts.SPILL_LOSS_IN_STOCK) {
                inOutStatus = Consts.STOCK_PICKING_TYPE_STOCK_IN;
            } else if (stockSpillLossOrder.getStockVarianceType() == Consts.SPILL_LOSS_OUT_STOCK) {
                inOutStatus = Consts.STOCK_PICKING_TYPE_STOCK_OUT;
            } else {
                inOutStatus = Consts.STOCK_PICKING_TYPE_STOCK_INTERVAL;
            }
            StockQuantQueryRequest srcStockQuant = null;
            StockQuantQueryRequest toStockQuant = null;
            switch (inOutStatus) {
                //入库:更新目标仓库库存
                case Consts.STOCK_PICKING_TYPE_STOCK_IN:
                    toStockQuant = new StockQuantQueryRequest();
                    if (StringUtils.isEmpty(stockSpillLossOrder.getWarehouseId())) {
                        throw new RuntimeException("仓库不存在!");
                    }
                    toStockQuant.setWarehouseId(stockSpillLossOrder.getWarehouseId());
                    break;
                //出库:更新来源仓库库存
                case Consts.STOCK_PICKING_TYPE_STOCK_OUT:
                    srcStockQuant = new StockQuantQueryRequest();
                    if (StringUtils.isEmpty(stockSpillLossOrder.getWarehouseId())) {
                        throw new RuntimeException("仓库不存在!");
                    }
                    srcStockQuant.setWarehouseId(stockSpillLossOrder.getWarehouseId());
                    break;
                //移库:更新来源仓库和目标仓库库存
                case Consts.STOCK_PICKING_TYPE_STOCK_INTERVAL:
//                    if (stockPicking.getAllocationType() != null && stockPicking.getAllocationType() == 1) {
                    srcStockQuant = new StockQuantQueryRequest();
                    if (StringUtils.isEmpty(stockSpillLossOrder.getWarehouseId())) {
                        throw new RuntimeException("仓库不存在!");
                    }
                    srcStockQuant.setWarehouseId(stockSpillLossOrder.getWarehouseId());
//                    } else {
                    toStockQuant = new StockQuantQueryRequest();
                    if (StringUtils.isEmpty(stockSpillLossOrder.getWarehouseId())) {
                        throw new RuntimeException("仓库不存在!");
                    }
                    toStockQuant.setWarehouseId(stockSpillLossOrder.getWarehouseId());
//                    }
                    break;
            }
            for (StockSpillLossOrderDetail stockSpillLossOrderDetail : stockSpillLossOrderDetailList) {
                int rows = 0;
                GoodsOdoo goodsOdoo = goodsOdooService.selectById(stockSpillLossOrderDetail.getGoodsId());
                StockQuantChangeHistory toStockQuantChangeHistory = null;
                StockQuantChangeHistory srcStockQuantChangeHistory = null;
                //更新目标仓库库存
                if (toStockQuant != null) {
                    toStockQuant.setGoodsId(stockSpillLossOrderDetail.getGoodsId());
                    List<StockQuant> stockQuantList = stockQuantDao.selectWarehouseGoods(toStockQuant);
                    //库存存在则做更新
                    if (stockQuantList != null && stockQuantList.size() > 0) {
                        StockQuant stockQuant = stockQuantList.get(0);
                        //构造库存变更历史
                        toStockQuantChangeHistory = new StockQuantChangeHistory(stockQuant);
                        toStockQuantChangeHistory.setStockChangeNum(stockSpillLossOrderDetail.getVarianceStock());

                        Long totalAmount = stockSpillLossOrderDetail.getVarianceStock() + stockQuant.getStockNum();
                        stockQuant.setStockNum(totalAmount);
                        if (StringUtils.isNotEmpty(stockSpillLossOrderDetail.getGoodsBrandName())) {
                            stockQuant.setGoodsBrandName(stockSpillLossOrderDetail.getGoodsBrandName());
                        }
                        rows = updateEsByStockQuantId(stockQuant);
                    }
                    if (rows == 0) {
                        Warehouse warehouse = warehouseDao.selectByWarehouseId(toStockQuant.getWarehouseId());
                        //仓库不存在不作处理
                        if (warehouse == null) {
                            continue;
                        }
                        toStockQuant.setBrandId(warehouse.getBrandId());
                        toStockQuant.setStoreId(warehouse.getStoreId());
                        //创建库存
                        String stockQuantId = IdUtil.uuid();
                        toStockQuant.setStockQuantId(stockQuantId);
                        toStockQuant.setGoodsName(goodsOdoo.getGoodsName());
                        toStockQuant.setUnitName(goodsOdoo.getUnitName());
                        toStockQuant.setClassifyId(goodsOdoo.getClassifyId());
                        toStockQuant.setClassifyName(goodsOdoo.getClassifyName());
                        toStockQuant.setGoodsTypeName(goodsOdoo.getGoodsTypeName());
                        toStockQuant.setGoodsBarCode(goodsOdoo.getGoodsBarCode());
                        toStockQuant.setGoodsCode(goodsOdoo.getGoodsCode());
                        toStockQuant.setFeatures(goodsOdoo.getFeatures());
                        toStockQuant.setGoodsBrandName(goodsOdoo.getGoodsBrandName());
                        //库存价格设置
                        switch (stockSpillLossOrder.getStockVarianceType()) {
                            //入库
                            case Consts.STOCK_PICKING_TYPE_STOCK_IN:
                                toStockQuant.setUnitPrice(stockSpillLossOrderDetail.getUnitPrice());
                                break;
                            default:
                                toStockQuant.setUnitPrice(0L);
                        }
                        stockSpillLossOrderDetail.setVarianceStock(toStockQuant.getStockNum());
                        insertEsStockQuant(JsonUtil.fromJson(JsonUtil.toJson(toStockQuant), StockQuant.class));
                        //构造库存变更历史
                        toStockQuantChangeHistory = new StockQuantChangeHistory(JsonUtil.fromJson(JsonUtil.toJson(toStockQuant), StockQuant.class));
                        toStockQuantChangeHistory.setStockChangeNum(stockSpillLossOrderDetail.getVarianceStock());
                    }
                }
                //来源仓库库存更新
                if (srcStockQuant != null) {
                    srcStockQuant.setGoodsId(stockSpillLossOrderDetail.getGoodsId());
                    List<StockQuant> stockQuantList = stockQuantDao.selectWarehouseGoods(srcStockQuant);
                    //有库存信息则更新
                    if (stockQuantList != null && stockQuantList.size() > 0) {
                        StockQuant stockQuant = stockQuantList.get(0);

                        //构造库存变更历史
                        srcStockQuantChangeHistory = new StockQuantChangeHistory(stockQuant);
                        srcStockQuantChangeHistory.setStockChangeNum(stockSpillLossOrderDetail.getVarianceStock());

                        Long totalAmount = stockQuant.getStockNum() - stockSpillLossOrderDetail.getVarianceStock();
                        stockQuant.setStockNum(totalAmount);
                        if (StringUtils.isNotEmpty(stockSpillLossOrderDetail.getGoodsBrandName())) {
                            stockQuant.setGoodsBrandName(stockSpillLossOrderDetail.getGoodsBrandName());
                        }
                        rows = updateEsByStockQuantId(stockQuant);
                    }
                    if (rows == 0) {
                        Warehouse warehouse = warehouseDao.selectByWarehouseId(srcStockQuant.getWarehouseId());
                        if (warehouse == null) {
                            continue;
                        }
                        if (goodsOdoo != null) {
                            srcStockQuant.setBrandId(warehouse.getBrandId());
                            srcStockQuant.setStoreId(warehouse.getStoreId());
                            //新增库存信息
                            String stockQuantId = IdUtil.uuid();
                            srcStockQuant.setStockQuantId(stockQuantId);
                            srcStockQuant.setGoodsName(goodsOdoo.getGoodsName());
                            srcStockQuant.setUnitName(goodsOdoo.getUnitName());
                            srcStockQuant.setClassifyId(goodsOdoo.getClassifyId());
                            srcStockQuant.setClassifyName(goodsOdoo.getClassifyName());
                            srcStockQuant.setGoodsTypeName(goodsOdoo.getGoodsTypeName());
                            srcStockQuant.setGoodsBarCode(goodsOdoo.getGoodsBarCode());
                            srcStockQuant.setGoodsCode(goodsOdoo.getGoodsCode());
                            srcStockQuant.setFeatures(goodsOdoo.getFeatures());
                            srcStockQuant.setGoodsBrandName(goodsOdoo.getGoodsBrandName());
                            srcStockQuant.setUnitPrice(0L);
                            srcStockQuant.setStockNum(0 - stockSpillLossOrderDetail.getVarianceStock());
                            insertEsStockQuant(JsonUtil.fromJson(JsonUtil.toJson(srcStockQuant), StockQuant.class));
                            //构造库存变更历史
                            srcStockQuantChangeHistory = new StockQuantChangeHistory(JsonUtil.fromJson(JsonUtil.toJson(srcStockQuant), StockQuant.class));
                            srcStockQuantChangeHistory.setStockChangeNum(stockSpillLossOrderDetail.getVarianceStock());
                        } else {
                            LOGGER.error("该商品不存在!");
                        }
                    }
                }

                try {
                    int stockQuantChangeType;
                    if (stockSpillLossOrder.getStockVarianceType() == Consts.STOCK_VARIANCE_TYPE_FULL) {
                        stockQuantChangeType = Consts.STOCK_IN_OUT_STOCK_OVERFLOW;
                    } else {
                        stockQuantChangeType = Consts.STOCK_IN_OUT_STOCK_LOSS;
                    }
                    if (srcStockQuantChangeHistory != null) {
                        srcStockQuantChangeHistory.setStockChangeType(stockQuantChangeType);
                        stockQuantChangeHistoryDao.insertSelective(srcStockQuantChangeHistory);
                    }
                    if (toStockQuantChangeHistory != null) {
                        toStockQuantChangeHistory.setStockChangeType(stockQuantChangeType);
                        stockQuantChangeHistoryDao.insertSelective(toStockQuantChangeHistory);
                    }
                } catch (Exception e) {
                    LOGGER.error("保存库存变更记录失败!", e);
                }
            }

            try {
                if (old != null && Consts.LAIPAOBA_BRAND_ID.equals(old.getBrandId())) {
                    StockInAndOut stockInAndOut = new StockInAndOut(old, warehouseDao);
                    String task = "{id:" + stockSpillLossOrder.getStockVarianceId() + ", type:" + (stockSpillLossOrder.getStockVarianceType() == 1 ? 7 : 8);
                    ThreadPool.getFixedInstance().execute(new ThreadPoolTask(stockInAndOut, task));
                }
            } catch (Exception e) {
                LOGGER.error("执行同步来跑吧库存任务失败!", e);
            }
        } catch (Exception e) {
            LOGGER.error("更新库存失败!", e);
        }

    }

    /**
     * 获取库存出库列表
     *
     * @param disassemblyOrder 拆装单对象
     * @return List
     */
    private List<StockQuant> getSrcList(DisassemblyOrder disassemblyOrder) throws Exception {
        //出库list对象
        StockQuantQueryRequest srcStockQuant = new StockQuantQueryRequest();
        srcStockQuant.setBrandId(disassemblyOrder.getBrandId());
        if (StringUtils.isNotEmpty(disassemblyOrder.getStoreId())) {
            srcStockQuant.setStoreId(disassemblyOrder.getStoreId());
        }
        srcStockQuant.setWarehouseId(disassemblyOrder.getSrcWarehouseId());
        return selectWithParam(srcStockQuant).getList();
    }

    /**
     * 获取库存入库列表
     *
     * @param disassemblyOrder 拆装单对象
     * @return list
     */
    private List<StockQuant> getToList(DisassemblyOrder disassemblyOrder) throws Exception {
        //入库list对象
        StockQuantQueryRequest toStockQuant = new StockQuantQueryRequest();
        toStockQuant.setBrandId(disassemblyOrder.getBrandId());
        if (StringUtils.isNotEmpty(disassemblyOrder.getStoreId())) {
            toStockQuant.setStoreId(disassemblyOrder.getStoreId());
        }
        toStockQuant.setWarehouseId(disassemblyOrder.getToWarehouseId());
        return selectWithParam(toStockQuant).getList();
    }

    /**
     * 拆装单审核更新库存
     *
     * @param newDetail        母商品对象
     * @param newSonList       子商品
     * @param disassemblyOrder 拆装单对象
     */
    @Override
    public void disassemblyModifyStockQuant(DisassemblyOrderDetail newDetail, List<DisassemblyOrderDetail> newSonList,
                                            DisassemblyOrder disassemblyOrder) throws Exception {
        int orderType = disassemblyOrder.getDisassemblyOrderType();
        if (orderType == Consts.DISASSEMBLY_ASSEMBLE_TYPE) {        //组装
            List<StockQuant> oldTosrcList = getToList(disassemblyOrder);       //入库list
            //母商品入库
            boolean insertFlag = true;
            String disassGoodsId = newDetail.getGoodsId();
            for (StockQuant oldStockQuant : oldTosrcList) {
                String oldStockQuantGoodsId = oldStockQuant.getGoodsId();
                String oldStockQuantId = oldStockQuant.getStockQuantId();
                StockQuant stockQuantDB = selectById(oldStockQuantId);
                if (stockQuantDB == null) {
                    throw new StockQuantException("拆装->(母商品)库存ID:[" + oldStockQuantId + "]数据不存在！");
                }
                if (!disassGoodsId.equalsIgnoreCase(oldStockQuantGoodsId)) {
                    continue;
                }
                //更新
                long totalPrice = (oldStockQuant.getUnitPrice() * oldStockQuant.getStockNum()) + (newDetail.getUnitPrice() * newDetail.getStockNum());
                long price = totalPrice / (oldStockQuant.getStockNum() + newDetail.getStockNum());
                oldStockQuant.setUnitPrice(price);
                oldStockQuant.setStockNum(oldStockQuant.getStockNum() + newDetail.getStockNum());
                int modifyRow = updateEsByStockQuantId(oldStockQuant);
                try {
                    StockQuantChangeHistory stockQuantChangeHistory = new StockQuantChangeHistory(stockQuantDB);
                    stockQuantChangeHistory.setStockChangeType(Consts.STOCK_IN_OUT_ASSEMBLY_ORDER);
                    stockQuantChangeHistory.setStockChangeNum(newDetail.getStockNum());
                    stockQuantChangeHistoryDao.insertSelective(stockQuantChangeHistory);
                } catch (Exception e) {
                    LOGGER.error("保存库存变更记录失败!", e);
                }
                if (modifyRow <= 0) {
                    throw new StockQuantException("拆装单(母商品)==>更新库存失败");
                }
                insertFlag = false;
                break;
            }
            if (insertFlag) {                                      //插入
                int insertRow = insertStockQuant(newDetail, Consts.STOCK_IN_OUT_ASSEMBLY_ORDER);
                if (insertRow <= 0) {
                    throw new StockQuantException("拆装单(母商品)==>插入库存失败");
                }
            }

            List<StockQuant> oldSrcList = getSrcList(disassemblyOrder);     //出库list

            //子商品出库
            for (DisassemblyOrderDetail orderDetail : newSonList) {
                String newDisassGoodsId = orderDetail.getGoodsId();
                for (StockQuant oldStockQuant : oldSrcList) {
                    String quantGoodsId = oldStockQuant.getGoodsId();
                    String stockQuantId = oldStockQuant.getStockQuantId();
                    StockQuant stockQuantDB = selectById(stockQuantId);
                    if (stockQuantDB == null) {
                        throw new StockQuantException("拆装->(子商品)库存ID:[" + stockQuantId + "]数据不存在！");
                    }
                    if (!newDisassGoodsId.equalsIgnoreCase(quantGoodsId)) {
                        continue;
                    }
                    oldStockQuant.setStockNum(oldStockQuant.getStockNum() - orderDetail.getStockNum() * newDetail.getStockNum());
                    int modifyRow = updateEsByStockQuantId(oldStockQuant);
                    if (modifyRow <= 0) {
                        throw new StockQuantException("拆装单(子商品)==>更新库存失败");
                    }
                    try {
                        StockQuantChangeHistory stockQuantChangeHistory = new StockQuantChangeHistory(oldStockQuant);
                        stockQuantChangeHistory.setStockNum(oldStockQuant.getStockNum());
                        stockQuantChangeHistory.setStockChangeNum(newDetail.getStockNum());
                        stockQuantChangeHistory.setStockChangeType(Consts.STOCK_IN_OUT_ASSEMBLY_ORDER);
                        stockQuantChangeHistoryDao.insertSelective(stockQuantChangeHistory);
                    } catch (Exception e) {
                        LOGGER.error("保存库存变更记录失败!", e);
                    }
                }
            }
        } else if (orderType == Consts.DISASSEMBLY_SPLIT_TYPE) {       //拆分

            List<StockQuant> oldSrcList = getSrcList(disassemblyOrder);     //出库list

            //母商品出库
            String newGoodsIdCompare = newDetail.getGoodsId();
            for (StockQuant oldStockQuant : oldSrcList) {
                String oldGoodsId = oldStockQuant.getGoodsId();
                String oldStockQuantId = oldStockQuant.getStockQuantId();
                StockQuant stockQuantDB = selectById(oldStockQuantId);
                if (stockQuantDB == null) {
                    throw new StockQuantException("拆装->(母商品)库存ID:[" + oldStockQuantId + "]数据不存在！");
                }
                if (!newGoodsIdCompare.equalsIgnoreCase(oldGoodsId)) {
                    continue;
                }
                oldStockQuant.setStockNum(oldStockQuant.getStockNum() - newDetail.getStockNum());
                int updateRow = updateEsByStockQuantId(oldStockQuant);
                try {
                    StockQuantChangeHistory stockQuantChangeHistory = new StockQuantChangeHistory(oldStockQuant);
                    stockQuantChangeHistory.setStockNum(oldStockQuant.getStockNum());
                    stockQuantChangeHistory.setStockChangeNum(newDetail.getStockNum());
                    stockQuantChangeHistory.setStockChangeType(Consts.STOCK_IN_OUT_DISASSEMBLY_ORDER);
                    stockQuantChangeHistoryDao.insertSelective(stockQuantChangeHistory);
                } catch (Exception e) {
                    LOGGER.error("保存出入库记录失败!", e);
                }
                if (updateRow <= 0) {
                    throw new StockQuantException("拆装单(母商品)==>更新库存失败");
                }
            }

            List<StockQuant> oldTosrcList = getToList(disassemblyOrder);       //入库list
            //子商品入库
            for (DisassemblyOrderDetail newOrderDetail : newSonList) {
                boolean insertFlag = true;
                String newGoodsId = newOrderDetail.getGoodsId();
                for (StockQuant stockQuant : oldTosrcList) {
                    String oldGoodsId = stockQuant.getGoodsId();
                    String oldStockQuantId = stockQuant.getStockQuantId();
                    StockQuant stockQuantDB = selectById(oldStockQuantId);
                    if (stockQuantDB == null) {
                        throw new StockQuantException("拆装->(子商品)库存ID:[" + oldStockQuantId + "]数据不存在！");
                    }
                    if (!newGoodsId.equalsIgnoreCase(oldGoodsId)) {
                        continue;
                    }
                    //更新
                    Long stockNum = newOrderDetail.getStockNum() * newDetail.getStockNum();
                    long totalPrice = (stockQuant.getUnitPrice() * stockQuant.getStockNum()) + (stockNum * newOrderDetail.getUnitPrice());
                    long price = totalPrice / (stockQuant.getStockNum() + stockNum);
                    stockQuant.setUnitPrice(price);
                    stockQuant.setStockNum(stockQuant.getStockNum() + stockNum);
                    int updateRow = updateEsByStockQuantId(stockQuant);
                    try {
                        StockQuantChangeHistory stockQuantChangeHistory = new StockQuantChangeHistory(stockQuant);
                        stockQuantChangeHistory.setStockChangeNum(newOrderDetail.getStockNum());
                        stockQuantChangeHistory.setStockChangeType(Consts.STOCK_IN_OUT_DISASSEMBLY_ORDER);
                        stockQuantChangeHistoryDao.insertSelective(stockQuantChangeHistory);
                    } catch (Exception e) {
                        LOGGER.error("保存出入库记录失败!", e);
                    }
                    if (updateRow <= 0) {
                        throw new StockQuantException("拆装单(子商品)==>更新库存失败");
                    }
                    insertFlag = false;
                    break;
                }
                if (insertFlag) {
                    Long stockNum = newDetail.getStockNum() * newOrderDetail.getStockNum();
                    newOrderDetail.setStockNum(stockNum);
                    int insertRow = insertStockQuant(newOrderDetail, Consts.STOCK_IN_OUT_DISASSEMBLY_ORDER);
                    if (insertRow <= 0) {
                        throw new StockQuantException("拆装单(子商品)==>插入库存失败");
                    }
                }
            }

        }

        try {
            if (Consts.LAIPAOBA_BRAND_ID.equals(disassemblyOrder.getBrandId())) {
                StockInAndOut stockInAndOut = new StockInAndOut(newDetail, newSonList, disassemblyOrder, warehouseDao);
                String task = "{id:" + disassemblyOrder.getDisassemblyOrderId() + ", type:" + (disassemblyOrder.getDisassemblyOrderType() == Consts.DISASSEMBLY_ASSEMBLE_TYPE ? Consts.STOCK_IN_OUT_ASSEMBLY_ORDER : Consts.STOCK_IN_OUT_DISASSEMBLY_ORDER);
                ThreadPool.getFixedInstance().execute(new ThreadPoolTask(stockInAndOut, task));
            }
        } catch (Exception e) {
            LOGGER.error("执行同步来跑吧库存任务失败!", e);
        }
    }

    /**
     * 拆装单-->插入库存商品
     *
     * @return Integer
     */
    private Integer insertStockQuant(DisassemblyOrderDetail detail, int type) throws StockQuantException {
        DisassemblyOrder order = disassemblyOrderDao.selectByDisassemblyOrderId(detail.getDisassemblyOrderId());
        if (order == null) {
            throw new StockQuantException("拆装单Id[" + detail.getDisassemblyOrderId() + "]数据不存在!");
        }
        StockQuant stockQuant = new StockQuant();
        stockQuant.setBrandId(detail.getBrandId());
        if (StringUtils.isNotEmpty(detail.getStoreId())) {
            stockQuant.setStoreId(detail.getStoreId());
        }
        stockQuant.setWarehouseId(order.getToWarehouseId());
        if (StringUtils.isNotEmpty(order.getToWarehouseName())) {
            stockQuant.setWarehouseName(order.getToWarehouseName());
        }
        stockQuant.setStockQuantId(IdUtil.uuid());
        stockQuant.setGoodsId(detail.getGoodsId());
        stockQuant.setGoodsName(detail.getGoodsName());
        stockQuant.setGoodsBarCode(detail.getGoodsBarCode());
        stockQuant.setUnitName(detail.getUnitName());
        stockQuant.setUnitPrice(detail.getUnitPrice());
        stockQuant.setStockNum(detail.getStockNum());
        stockQuant.setClassifyId(detail.getClassifyId());
        stockQuant.setClassifyName(detail.getClassifyName());
        stockQuant.setGoodsTypeName(detail.getGoodsTypeName());
        stockQuant.setFeatures(detail.getFeatures());
        stockQuant.setGoodsCode(detail.getGoodsCode());
        stockQuant.setGoodsBrandName(detail.getGoodsBrandName());
        try {
            StockQuantChangeHistory stockQuantChangeHistory = new StockQuantChangeHistory(stockQuant);
            stockQuantChangeHistory.setStockNum(0L);
            stockQuantChangeHistory.setStockChangeNum(detail.getStockNum());
            stockQuantChangeHistory.setStockChangeType(type);
            stockQuantChangeHistoryDao.insertSelective(stockQuantChangeHistory);
        } catch (Exception e) {
            LOGGER.error("保存库存变更记录失败!", e);
        }
        return insertEsStockQuant(stockQuant);
    }

    @Override
    public List<List<Object>> exportQueryDetailed(StockQuantQueryRequest stockQuant) throws StockQuantException {
        List<List<Object>> resultList = new ArrayList<>();//返回的数据List
        //查询的List
        List<StockQuantResult> list;
        try {
            if (StringUtils.isNotEmpty(stockQuant.getQueryGroup())) {
                list = selectGroupWithParam(stockQuant).getList();
            } else {
                list = selectWithParam(stockQuant).getList();
            }
            Integer step = 0;
            for (StockQuantResult stockResult : list) {
                ++step;
                List<Object> beanList = new ArrayList<>();
                beanList.add(step);
                double balance = 0.00;
                if (StringUtils.isNotEmpty(stockQuant.getQueryGroup())) {
                    beanList.add(stockResult.getGoodsName());
                    beanList.add(stockResult.getClassifyName());
                    beanList.add(stockResult.getGoodsCode());
                    beanList.add(stockResult.getGoodsBarCode());
                    beanList.add(stockResult.getUnitName());
                    beanList.add(stockResult.getFeatures());
                    beanList.add(stockResult.getTotalNum());
                    if (stockResult.getTotalUnitPrice() == null || stockResult.getTotalUnitPrice() == 0) {
                        beanList.add("0.00");
                    } else {
                        beanList.add(Double.parseDouble(stockResult.getTotalUnitPrice() + "") / 100);
                    }
                    balance = Double.parseDouble(stockResult.getTotalCash() + "") / 100;
                    beanList.add(balance == 0 ? "0.00" : balance);
                } else {
                    beanList.add(stockResult.getWarehouseName());
                    beanList.add(stockResult.getGoodsName());
                    beanList.add(stockResult.getClassifyName());
                    beanList.add(stockResult.getGoodsCode());
                    beanList.add(stockResult.getGoodsBarCode());
                    beanList.add(stockResult.getUnitName());
                    beanList.add(stockResult.getFeatures());
                    beanList.add(stockResult.getStockNum()==null?0:stockResult.getStockNum());
                    if (stockResult.getUnitPrice() == 0) {
                        beanList.add("0.00");
                    } else {
                        beanList.add(Double.parseDouble(stockResult.getUnitPrice() + "") / 100);
                    }
                    balance = stockResult.getStockNum() * Double.parseDouble(stockResult.getUnitPrice() + "") / 100;
                    beanList.add(balance == 0 ? "0.00" : balance);
                }
                beanList.add(stockResult.getInitinalTotalNum()==null?0:stockResult.getInitinalTotalNum());
                beanList.add(stockResult.getInitinalTotalPrice()==null?0:Double.parseDouble(stockResult.getInitinalTotalPrice() + "") / 100);
                beanList.add(stockResult.getInitialTotalCash()==null?0:Double.parseDouble(stockResult.getInitialTotalCash() + "") / 100);
                resultList.add(beanList);
            }
        } catch (Exception e) {
            LOGGER.error("导出库存失败!!", e);
            throw new StockQuantException("导出失败!", e);
        }
        return resultList;
    }

    @Override
    public TotalStockResponse totalStock(BrandStoreRequest brandStoreRequest) {
        try {
            return stockQuantDao.totalStock(brandStoreRequest);
        } catch (Exception e) {
            LOGGER.error("查询总库存失败!", e);
            throw new RuntimeException("查询总库存失败!", e);
        }
    }

    @Override
    public void updateQuantByInspectionPickingIn(InspectionPickingIn inspectionPickingIn) {
        try {
            List<InspectionPickingInDetail> inspectionPickingInDetailList;
            WarehouseQueryRequest warehouseQueryRequest = new WarehouseQueryRequest();

            if (StringUtils.isEmpty(inspectionPickingIn.getInspectionId())) {
                inspectionPickingInDetailList = inspectionPickingIn.getDetailList();
            } else {
                InspectionPickingInDetail inspectionPickingInDetail = new InspectionPickingInDetail();
                inspectionPickingInDetail.setInspectionId(inspectionPickingIn.getInspectionId());
                inspectionPickingInDetailList = inspectionPickingInDetailDao.selectByInspectionId(inspectionPickingInDetail.getInspectionId());
            }
            warehouseQueryRequest.setBrandId(inspectionPickingIn.getBrandId());
            warehouseQueryRequest.setStoreId(inspectionPickingIn.getToStoreId());
            warehouseQueryRequest.setWarehouseStatus(0);
            List<Warehouse> warehouseList = warehouseDao.select(warehouseQueryRequest);
            String warehouseId = warehouseList.get(0).getWarehouseId();
            StockQuantQueryRequest toStockQuant;
            //入库:更新目标仓库库存
            toStockQuant = new StockQuantQueryRequest();
            if (StringUtils.isEmpty(warehouseId)) {
                throw new StockQuantException("仓库不存在!");
            }
            toStockQuant.setWarehouseId(warehouseId);
            //移库:更新来源仓库和目标仓库库存
            for (InspectionPickingInDetail inspectionPickingInDetail : inspectionPickingInDetailList) {
                GoodsOdoo goodsOdoo = goodsOdooService.selectById(inspectionPickingInDetail.getGoodsId());
                //更新目标仓库库存
                if (toStockQuant != null) {
                    toStockQuant.setGoodsId(inspectionPickingInDetail.getGoodsId());
                    List<StockQuant> stockQuantList = stockQuantDao.selectWarehouseGoods(toStockQuant);
                    //库存存在则做更新
                    if (stockQuantList != null && stockQuantList.size() > 0) {
                        StockQuant stockQuant = stockQuantList.get(0);
                        Long centerNum = stockQuant.getStockNum();
                        Long totalAmount = inspectionPickingInDetail.getReceiptNum() + centerNum;
                        stockQuant.setStockNum(totalAmount);
                        //入库库存平均价
                        Long price = (inspectionPickingInDetail.getReceiptNum() * inspectionPickingInDetail.getUnitPrice() + centerNum * stockQuant.getUnitPrice()) / totalAmount;
                        LOGGER.info("入库商品金额:{}======库存商品金额:{} ======总数量:{} ========= 库存余额:{}",inspectionPickingInDetail.getReceiptNum() * inspectionPickingInDetail.getUnitPrice(),stockQuant.getStockNum() * stockQuant.getUnitPrice(),
                                totalAmount,price);
                        stockQuant.setUnitPrice(price);
                        if (StringUtils.isNotEmpty(inspectionPickingInDetail.getGoodsBrandName())) {
                            stockQuant.setGoodsBrandName(inspectionPickingInDetail.getGoodsBrandName());
                        }
                        updateEsByStockQuantId(stockQuant);
                        try {
                            StockQuantChangeHistory stockQuantChangeHistory = new StockQuantChangeHistory(stockQuant);
                            stockQuantChangeHistory.setStockChangeNum(inspectionPickingInDetail.getReceiptNum());
                            stockQuantChangeHistory.setStockChangeType(Consts.STOCK_IN_OUT_DISTRIBUTION_STOCK_IN);
                            stockQuantChangeHistoryDao.insertSelective(stockQuantChangeHistory);
                        } catch (Exception e) {
                            LOGGER.error("保存库存变更记录失败!", e);
                        }
                        continue;
                    }

                    toStockQuant.setBrandId(warehouseList.get(0).getBrandId());
                    toStockQuant.setStoreId(warehouseList.get(0).getStoreId());
                    //创建库存
                    String stockQuantId = IdUtil.uuid();
                    toStockQuant.setStockQuantId(stockQuantId);
                    toStockQuant.setGoodsName(goodsOdoo.getGoodsName());
                    toStockQuant.setUnitName(goodsOdoo.getUnitName());
                    toStockQuant.setClassifyId(goodsOdoo.getClassifyId());
                    toStockQuant.setClassifyName(goodsOdoo.getClassifyName());
                    toStockQuant.setGoodsTypeName(goodsOdoo.getGoodsTypeName());
                    toStockQuant.setGoodsBarCode(goodsOdoo.getGoodsBarCode());
                    toStockQuant.setGoodsCode(goodsOdoo.getGoodsCode());
                    toStockQuant.setFeatures(goodsOdoo.getFeatures());
                    toStockQuant.setGoodsBrandName(goodsOdoo.getGoodsBrandName());
                    toStockQuant.setStockNum(inspectionPickingInDetail.getReceiptNum());
                    //库存价格设置
                    //入库
                    if (inspectionPickingInDetail.getUnitPrice() != null) {
                        toStockQuant.setUnitPrice(inspectionPickingInDetail.getUnitPrice());
                    } else {
                        toStockQuant.setUnitPrice(0L);
                    }

//                    inspectionPickingInDetail.setVarianceStock(toStockQuant.getStockNum());
                    insertEsStockQuant(JsonUtil.fromJson(JsonUtil.toJson(toStockQuant), StockQuant.class));
                    try {
                        StockQuantChangeHistory stockQuantChangeHistory = new StockQuantChangeHistory(toStockQuant);
                        stockQuantChangeHistory.setStockNum(0L);
                        stockQuantChangeHistory.setStockChangeNum(inspectionPickingInDetail.getReceiptNum());
                        stockQuantChangeHistory.setStockChangeType(Consts.STOCK_IN_OUT_DISTRIBUTION_STOCK_IN);
                        stockQuantChangeHistoryDao.insertSelective(stockQuantChangeHistory);
                    } catch (Exception e) {
                        LOGGER.error("保存库存变更记录失败!", e);
                    }
                }

            }
        } catch (Exception e) {
            LOGGER.error("更新库存失败!", e);
        }
    }

    public HttpResponse queryStoreGoods(StoreGoodsStock storeGoodsStock) {
        try {
            List<StockQuant> stockQuantList = stockQuantDao.selectStoreGoods(storeGoodsStock);
            return HttpResponse.success(stockQuantList);
        } catch (Exception e) {
            LOGGER.error("查询库存商品失败!", e);
            return HttpResponse.failure(ResultCode.STOCK_QUANT_QUERY_STORE_GOODS_ERROR);
        }
    }

    @Override
    public StockQuant selectById(String id) {
        try {
            QueryBuilder queryBuilder = QueryBuilders.idsQuery().addIds(id);
            BaseResponse response = searchFunction(queryBuilder, 1, 0, false);
            if (response == null) {
                return stockQuantDao.selectByStockQuantId(id);
            }
            List<StockQuant> stockQuantList = JsonUtil.fromJson(JsonUtil.toJson(response.getList()), new TypeReference<List<StockQuant>>() {
            });
            if (stockQuantList != null && stockQuantList.size() > 0) {
                return stockQuantList.get(0);
            }
            return null;
        } catch (Exception e) {
            LOGGER.error("查询库存信息失败!", e);
            return null;
        }
    }

    @Override
    public BaseResponse selectWithParam(StockQuantQueryRequest query) throws Exception {
        BaseResponse response;
        //过滤忽略的商品
        if (StringUtils.isNotEmpty(query.getSkipIds())) {
            List<String> skipIdList = JsonUtil.fromJson(query.getSkipIds(), new TypeReference<List<String>>() {
            });
            if (skipIdList != null && skipIdList.size() > 0) {
                query.setSkipIdList(skipIdList);
            }
        }
        //没有供应商条件和分类条件，直接查询
        if (StringUtils.isEmpty(query.getSupplierId()) && StringUtils.isEmpty(query.getClassifyId())) {
            return queryWithoutSupplierAndCategory(query);
        }
        /**以下代码没有用到**/
        GoodsQueryRequest goodsQueryRequest = new GoodsQueryRequest();
        goodsQueryRequest.setBrandId(query.getBrandId());
        /**以上代码没有用到**/
        query = constructQueryStockParam(query);
        QueryBuilder queryBuilder = constructQueryBuild(query, false);
        constructQueryPage(query);
        response = searchFunction(queryBuilder, query.getPageSize(), query.getRecordNo(), false);
        if (response == null) {
            response = selectList(query);
        }
        return response;
    }

    @Override
    public HttpResponse initEsStock() {
        try {
            String index = PropertiesUtil.getProperties("erp-api.properties").getProperty("es.index");
            String singleStockType = PropertiesUtil.getProperties("erp-api.properties").getProperty("es.type.stock.single");
            String groupStockType = PropertiesUtil.getProperties("erp-api.properties").getProperty("es.type.stock.group");
            if (!initElasticSearchStruct(index, singleStockType, false) || !initElasticSearchStruct(index, groupStockType, true)) {
                LOGGER.error("同步es库存结构失败!");
            }
            StockQuantQueryRequest query = new StockQuantQueryRequest();
            query.setCount(99999999);
            List<StockQuant> list = stockQuantDao.select(query);
            int errorCount = 0;
            Map<String, StockQuantResult> stockQuantMap = new HashMap<>();
            for (StockQuant old : list) {
                StockQuantResult stockQuant = JsonUtil.fromJson(JsonUtil.toJson(old), StockQuantResult.class);
                try {
                    String stockBrandIdGoodsId = stockQuant.getBrandId() + "_" + stockQuant.getGoodsId();
                    StockQuantResult groupStockQuant = stockQuantMap.get(stockBrandIdGoodsId);
                    initStockQuant(stockQuant);
                    if (groupStockQuant == null) {
                        groupStockQuant = stockQuant;
                        groupStockQuant.setTotalPrice(stockQuant.getStockNum() * stockQuant.getUnitPrice());
                        stockQuantMap.put(stockBrandIdGoodsId, groupStockQuant);
                    } else {
                        groupStockQuant.setStockNum(groupStockQuant.getStockNum() + stockQuant.getStockNum());
                        groupStockQuant.setTotalPrice(groupStockQuant.getTotalPrice() + stockQuant.getStockNum() * stockQuant.getUnitPrice());
                        groupStockQuant.setInitialStockNum(groupStockQuant.getInitialStockNum() + stockQuant.getInitialStockNum());
                        groupStockQuant.setTotalInitialPrice(groupStockQuant.getTotalInitialPrice() + stockQuant.getInitialStockNum() * stockQuant.getInitialUnitPrice());
                    }
                    XContentBuilder contentBuilder = XContentFactory.jsonBuilder().startObject();
                    contentBuilder.field("stock_quant_id", stockQuant.getStockQuantId())
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
                            .field("totalPrice", stockQuant.getStockNum() * stockQuant.getUnitPrice())
                            .field("initial_unit_price", stockQuant.getInitialUnitPrice())
                            .field("initial_stock_num", stockQuant.getInitialStockNum())
                            .field("total_initial_price", stockQuant.getInitialStockNum() * stockQuant.getInitialUnitPrice())
                            .field("stock_upper", stockQuant.getStockUpper())
                            .field("stock_lower", stockQuant.getStockLower())
                            .field("stock_safe", stockQuant.getStockSafe())
                            .field("stock_num_usable", stockQuant.getStockNumUsable())
                            .field("stock_num_using", stockQuant.getStockNumUsing())
                            .field("stock_num_on_order", stockQuant.getStockNumOnOrder())
                            .field("create_time", stockQuant.getCreateTime())
                            .field("update_time", stockQuant.getUpdateTime());
                    if (stockQuant.getUnitPrice() != null && stockQuant.getStockNum() != null) {
                        contentBuilder.field("total_price", stockQuant.getUnitPrice() * stockQuant.getStockNum());
                    }
                    if (stockQuant.getInitialUnitPrice() != null && stockQuant.getInitialStockNum() != null) {
                        contentBuilder.field("total_initial_price", stockQuant.getInitialUnitPrice() * stockQuant.getInitialStockNum());
                    }
                    contentBuilder.endObject();
                    SearchClient.getClient().prepareIndex(index, singleStockType).setSource(contentBuilder)
                            .setId(stockQuant.getStockQuantId()).execute().actionGet();
                } catch (Exception e) {
                    LOGGER.error("同步single库存失败!", e);
                    errorCount++;
                }
            }
            for (String stockBrandGoodsId : stockQuantMap.keySet()) {
                StockQuantResult stockQuant = stockQuantMap.get(stockBrandGoodsId);
                if (stockQuant == null) {
                    continue;
                }
                try {
                    if (stockQuant.getTotalPrice() == null) {
                        stockQuant.setTotalPrice(0L);
                    }
                    if (stockQuant.getStockNum() == null) {
                        stockQuant.setStockNum(0L);
                    }
                    if (stockQuant.getUnitPrice() == null) {
                        stockQuant.setUnitPrice(0L);
                    }
                    if (stockQuant.getTotalInitialPrice() == null) {
                        stockQuant.setTotalInitialPrice(0L);
                    }
                    if (stockQuant.getInitialStockNum() == null) {
                        stockQuant.setInitialStockNum(0L);
                    }
                    if (stockQuant.getInitialUnitPrice() == null) {
                        stockQuant.setInitialUnitPrice(0L);
                    }
                    XContentBuilder contentBuilder = XContentFactory.jsonBuilder().startObject();
                    contentBuilder.field("stock_quant_id", stockBrandGoodsId)
                            .field("brand_id", stockQuant.getBrandId())
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
                            .field("unit_price", stockQuant.getStockNum() == 0 ? 0 : stockQuant.getTotalPrice() / stockQuant.getStockNum())
                            .field("stock_num", stockQuant.getStockNum())
                            .field("initial_unit_price", stockQuant.getInitialStockNum() == 0 ? 0 : stockQuant.getTotalInitialPrice() / stockQuant.getInitialStockNum())
                            .field("initial_stock_num", stockQuant.getInitialStockNum())
                            .field("stock_upper", stockQuant.getStockUpper())
                            .field("stock_lower", stockQuant.getStockLower())
                            .field("stock_safe", stockQuant.getStockSafe())
                            .field("stock_num_usable", stockQuant.getStockNumUsable())
                            .field("stock_num_using", stockQuant.getStockNumUsing())
                            .field("stock_num_on_order", stockQuant.getStockNumOnOrder())
                            .field("create_time", stockQuant.getCreateTime())
                            .field("update_time", stockQuant.getUpdateTime());
                    if (stockQuant.getUnitPrice() != null && stockQuant.getStockNum() != null) {
                        contentBuilder.field("total_price", stockQuant.getUnitPrice() * stockQuant.getStockNum());
                    }
                    if (stockQuant.getInitialUnitPrice() != null && stockQuant.getInitialStockNum() != null) {
                        contentBuilder.field("total_initial_price", stockQuant.getInitialUnitPrice() * stockQuant.getInitialStockNum());
                    }
                    contentBuilder.endObject();
                    SearchClient.getClient().prepareIndex(index, groupStockType).setSource(contentBuilder)
                            .setId(stockBrandGoodsId).execute().actionGet();
                } catch (Exception e) {
                    LOGGER.error("同步group库存失败!", e);
                    errorCount++;
                }
            }
            LOGGER.info("数据总条数: " + list.size() + "其中插入失败数据条数: " + errorCount);
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("初始化es库存失败!", e);
            return HttpResponse.failure(ResultCode.ES_INIT_STOCK_ERROR);
        }
    }

    private int insertEsStockQuant(StockQuant stockQuant) {
        try {
            if (StringUtils.isNotEmpty(stockQuant.getWarehouseId())) {
                Warehouse warehouse = warehouseDao.selectByWarehouseId(stockQuant.getWarehouseId());
                stockQuant.setWarehouseName(warehouse.getWarehouseName());
            }
            int rows = stockQuantDao.insertSelective(stockQuant);
            if (rows == 0) {
                return 0;
            }
            insertOrUpdateStockQuant(stockQuant, null);
            return rows;
        } catch (Exception e) {
            LOGGER.error("插入es库存失败!, 仓库id为: " + stockQuant.getWarehouseId() + "; 商品id为: " + stockQuant.getGoodsId(), e);
            return 0;
        } finally {
        }
    }

    @Override
    public int updateEsByStockQuantId(StockQuant stockQuant) {
        try {
            StockQuant old = null;
            if (StringUtils.isNotEmpty(stockQuant.getStockQuantId())) {
                old = selectById(stockQuant.getStockQuantId());
            } else {
                List stockQuantList = selectWithParam(JsonUtil.fromJson(JsonUtil.toJson(stockQuant), StockQuantQueryRequest.class)).getList();
                if (stockQuantList != null && stockQuantList.size() >= 0) {
                    old = (StockQuant) stockQuantList.get(0);
                }
            }
            if (StringUtils.isNotEmpty(stockQuant.getWarehouseId())) {
                Warehouse warehouse = warehouseDao.selectByWarehouseId(stockQuant.getWarehouseId());
                stockQuant.setWarehouseName(warehouse.getWarehouseName());
            }
            int rows = stockQuantDao.updateByStockQuantId(stockQuant);
            if (rows == 0) {
                return 0;
            }
            insertOrUpdateStockQuant(stockQuant, old);
            return rows;
        } catch (Exception e) {
            LOGGER.error("插入es库存失败!, 仓库id为: " + stockQuant.getWarehouseId() + "; 商品id为: " + stockQuant.getGoodsId(), e);
            return 0;
        }
    }

    private void insertOrUpdateStockQuant(StockQuant stockQuant, StockQuant old) throws Exception {
        if (old != null) {
            initStockQuant(JsonUtil.fromJson(JsonUtil.toJson(old), StockQuantResult.class));
        }
        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
        xContentBuilder.startObject().field("stock_quant_id", stockQuant.getStockQuantId());
        WarehouseQueryRequest query = new WarehouseQueryRequest();
        if (StringUtils.isNotEmpty(stockQuant.getBrandId())) {
            query.setBrandId(stockQuant.getBrandId());
        }
        List<Warehouse> warehouseList = warehouseDao.select(query);
        Map<String, Warehouse> warehouseMap = new HashMap<>();
        for (Warehouse warehouse : warehouseList) {
            warehouseMap.put(warehouse.getWarehouseId(), warehouse);
        }
        if (StringUtils.isNotEmpty(stockQuant.getBrandId())) {
            xContentBuilder.field("brand_id", stockQuant.getBrandId());
        } else if (old != null) {
            xContentBuilder.field("brand_id", old.getBrandId());
        }
        if (StringUtils.isNotEmpty(stockQuant.getStoreId())) {
            xContentBuilder.field("store_id", stockQuant.getStoreId());
        } else if (old != null) {
            xContentBuilder.field("store_id", old.getStoreId());
        }
        if (StringUtils.isNotEmpty(stockQuant.getWarehouseId())) {
            xContentBuilder.field("warehouse_id", stockQuant.getWarehouseId());
            Warehouse warehouse = warehouseMap.get(stockQuant.getWarehouseId());
            xContentBuilder.field("warehouse_name", warehouse == null ? "" : warehouse.getWarehouseName());
        } else if (old != null) {
            xContentBuilder.field("warehouse_id", old.getWarehouseId());
            Warehouse warehouse = warehouseMap.get(old.getWarehouseId());
            xContentBuilder.field("warehouse_name", warehouse == null ? "" : warehouse.getWarehouseName());
        }
        if (StringUtils.isNotEmpty(stockQuant.getGoodsId())) {
            xContentBuilder.field("goods_id", stockQuant.getGoodsId());
        } else if (old != null) {
            xContentBuilder.field("goods_id", old.getGoodsId());
        }
        if (StringUtils.isNotEmpty(stockQuant.getGoodsName())) {
            xContentBuilder.field("goods_name", stockQuant.getGoodsName());
        } else if (old != null) {
            xContentBuilder.field("goods_name", old.getGoodsName());
        }
        if (StringUtils.isNotEmpty(stockQuant.getGoodsPic())) {
            xContentBuilder.field("goods_pic", stockQuant.getGoodsPic());
        } else if (old != null) {
            xContentBuilder.field("goods_pic", old.getGoodsPic());
        }
        if (StringUtils.isNotEmpty(stockQuant.getClassifyId())) {
            xContentBuilder.field("classify_id", stockQuant.getClassifyId());
        } else if (old != null) {
            xContentBuilder.field("classify_id", old.getClassifyId());
        }
        if (StringUtils.isNotEmpty(stockQuant.getClassifyName())) {
            xContentBuilder.field("classify_name", stockQuant.getClassifyName());
        } else if (old != null) {
            xContentBuilder.field("classify_name", old.getClassifyName());
        }
        if (StringUtils.isNotEmpty(stockQuant.getGoodsBarCode())) {
            xContentBuilder.field("goods_bar_code", stockQuant.getGoodsBarCode());
        } else if (old != null) {
            xContentBuilder.field("goods_bar_code", old.getGoodsBarCode());
        }
        if (StringUtils.isNotEmpty(stockQuant.getGoodsCode())) {
            xContentBuilder.field("goods_sku_code", stockQuant.getGoodsCode());
        } else if (old != null) {
            xContentBuilder.field("goods_sku_code", old.getGoodsCode());
        }
        if (StringUtils.isNotEmpty(stockQuant.getFeatures())) {
            xContentBuilder.field("features", stockQuant.getFeatures());
        } else if (old != null) {
            xContentBuilder.field("features", old.getFeatures());
        }
        if (StringUtils.isNotEmpty(stockQuant.getGoodsBrandName())) {
            xContentBuilder.field("goods_brand_name", stockQuant.getGoodsBrandName());
        } else if (old != null) {
            xContentBuilder.field("goods_brand_name", old.getGoodsBrandName());
        }
        if (StringUtils.isNotEmpty(stockQuant.getUnitName())) {
            xContentBuilder.field("unit_name", stockQuant.getUnitName());
        } else if (old != null) {
            xContentBuilder.field("unit_name", old.getUnitName());
        }
        if (stockQuant.getUnitPrice() != null) {
            xContentBuilder.field("unit_price", stockQuant.getUnitPrice());
        } else if (old != null && old.getUnitPrice() != null) {
            xContentBuilder.field("unit_price", old.getUnitPrice());
        }
        if (stockQuant.getStockNum() != null) {
            xContentBuilder.field("stock_num", stockQuant.getStockNum());
        } else if (old != null) {
            xContentBuilder.field("stock_num", old.getStockNum());
        }
        if (stockQuant.getUnitPrice() != null && stockQuant.getStockNum() != null) {
            xContentBuilder.field("total_price", stockQuant.getUnitPrice() * stockQuant.getStockNum());
        } else if (old != null && old.getUnitPrice() != null && old.getStockNum() != null) {
            xContentBuilder.field("total_price", old.getUnitPrice() * old.getStockNum());
        }
        if (stockQuant.getInitialUnitPrice() != null) {
            xContentBuilder.field("initial_unit_price", stockQuant.getInitialUnitPrice());
        } else if (old != null && old.getInitialUnitPrice() != null) {
            xContentBuilder.field("initial_unit_price", stockQuant.getInitialUnitPrice());
        }
        if (stockQuant.getInitialStockNum() != null) {
            xContentBuilder.field("initial_stock_num", stockQuant.getInitialStockNum());
        } else if (old != null) {
            xContentBuilder.field("initial_stock_num", old.getInitialStockNum());
        }
        if (stockQuant.getInitialUnitPrice() != null && stockQuant.getInitialStockNum() != null) {
            xContentBuilder.field("total_initial_price", stockQuant.getInitialUnitPrice() * stockQuant.getInitialStockNum());
        } else if (old != null && old.getInitialUnitPrice() != null && old.getInitialStockNum() != null) {
            xContentBuilder.field("total_initial_price", old.getInitialUnitPrice() * old.getInitialStockNum());
        }
        if (stockQuant.getStockUpper() != null) {
            xContentBuilder.field("stock_upper", stockQuant.getStockUpper());
        } else if (old != null) {
            xContentBuilder.field("stock_upper", old.getStockUpper());
        }
        if (stockQuant.getStockLower() != null) {
            xContentBuilder.field("stock_lower", stockQuant.getStockLower());
        } else if (old != null) {
            xContentBuilder.field("stock_lower", old.getStockLower());
        }
        if (stockQuant.getStockSafe() != null) {
            xContentBuilder.field("stock_safe", stockQuant.getStockSafe());
        } else if (old != null) {
            xContentBuilder.field("stock_safe", old.getStockSafe());
        }
        if (stockQuant.getStockNumUsable() != null) {
            xContentBuilder.field("stock_num_usable", stockQuant.getStockNumUsable());
        } else if (old != null) {
            xContentBuilder.field("stock_num_usable", old.getStockNumUsable());
        }
        if (stockQuant.getStockNumUsing() != null) {
            xContentBuilder.field("stock_num_using", stockQuant.getStockNumUsing());
        } else if (old != null) {
            xContentBuilder.field("stock_num_using", old.getStockNumUsing());
        }
        if (stockQuant.getStockNumOnOrder() != null) {
            xContentBuilder.field("stock_num_on_order", stockQuant.getStockNumOnOrder());
        } else if (old != null) {
            xContentBuilder.field("stock_num_on_order", old.getStockNumOnOrder());
        }
        if (old == null) {
            xContentBuilder.field("create_time", new Date());
        }
        xContentBuilder.field("update_time", new Date());
        xContentBuilder.endObject();
        String index = PropertiesUtil.getProperties("erp-api.properties").getProperty("es.index");
        String singleStockType = PropertiesUtil.getProperties("erp-api.properties").getProperty("es.type.stock.single");
        String groupStockType = PropertiesUtil.getProperties("erp-api.properties").getProperty("es.type.stock.group");
        SearchClient.getClient().prepareIndex(index, singleStockType).setSource(xContentBuilder).setId(stockQuant.getStockQuantId()).execute().actionGet();
        String brandGoodsId;
        StockQuantResult groupStockQuant;
        if (old != null) {
            groupStockQuant = JsonUtil.fromJson(JsonUtil.toJson(old), StockQuantResult.class);
            brandGoodsId = old.getBrandId() + "_" + old.getGoodsId();
        } else {
            groupStockQuant = JsonUtil.fromJson(JsonUtil.toJson(stockQuant), StockQuantResult.class);
            brandGoodsId = stockQuant.getBrandId() + "_" + stockQuant.getGoodsId();

        }
        groupStockQuant.setStockNum(0L);
        groupStockQuant.setTotalPrice(0L);
        groupStockQuant.setInitialStockNum(0L);
        groupStockQuant.setTotalInitialPrice(0L);
        StockQuantQueryRequest stockQuantQueryRequest = new StockQuantQueryRequest();
        stockQuantQueryRequest.setBrandId(groupStockQuant.getBrandId());
        stockQuantQueryRequest.setGoodsId(groupStockQuant.getGoodsId());
        List<StockQuant> stockQuantResult = stockQuantDao.select(stockQuantQueryRequest);
        for (StockQuant child : stockQuantResult) {
            groupStockQuant.setStockNum(groupStockQuant.getStockNum() + child.getStockNum());
            groupStockQuant.setTotalPrice(groupStockQuant.getTotalPrice() + child.getStockNum() * child.getUnitPrice());
            groupStockQuant.setInitialStockNum(groupStockQuant.getInitialStockNum() + child.getInitialStockNum());
            groupStockQuant.setTotalInitialPrice(groupStockQuant.getTotalInitialPrice() + child.getInitialStockNum() * child.getInitialUnitPrice());
        }
        XContentBuilder contentBuilder = XContentFactory.jsonBuilder().startObject();
        contentBuilder.field("stock_quant_id", brandGoodsId)
                .field("brand_id", groupStockQuant.getBrandId())
                .field("goods_id", groupStockQuant.getGoodsId())
                .field("goods_name", StringUtils.isBlank(stockQuant.getGoodsName())?groupStockQuant.getGoodsName():stockQuant.getGoodsName())
                .field("goods_pic", groupStockQuant.getGoodsPic())
                .field("classify_id", StringUtils.isBlank(stockQuant.getClassifyId())?groupStockQuant.getClassifyId():stockQuant.getClassifyId())
                .field("classify_name", StringUtils.isBlank(stockQuant.getClassifyName())?groupStockQuant.getClassifyName():stockQuant.getClassifyName())
                .field("goods_bar_code", StringUtils.isEmpty(stockQuant.getGoodsBarCode())?groupStockQuant.getGoodsBarCode():stockQuant.getGoodsBarCode())
                .field("goods_sku_code", groupStockQuant.getGoodsCode())
                .field("features", groupStockQuant.getFeatures())
                .field("goods_brand_name", StringUtils.isEmpty(stockQuant.getGoodsBrandName())?groupStockQuant.getGoodsBrandName():stockQuant.getGoodsBarCode())
                .field("unit_name", StringUtils.isBlank(stockQuant.getUnitName())?groupStockQuant.getUnitName():stockQuant.getUnitName())
                .field("unit_price", groupStockQuant.getStockNum() == 0 ? 0 : groupStockQuant.getTotalPrice() / groupStockQuant.getStockNum())
                .field("stock_num", groupStockQuant.getStockNum())
                .field("initial_unit_price", groupStockQuant.getInitialStockNum() == 0 ? 0 : groupStockQuant.getTotalInitialPrice() / groupStockQuant.getInitialStockNum())
                .field("initial_stock_num", groupStockQuant.getInitialStockNum())
                .field("stock_upper", groupStockQuant.getStockUpper())
                .field("stock_lower", groupStockQuant.getStockLower())
                .field("stock_safe", groupStockQuant.getStockSafe())
                .field("stock_num_usable", groupStockQuant.getStockNumUsable())
                .field("stock_num_using", groupStockQuant.getStockNumUsing())
                .field("stock_num_on_order", groupStockQuant.getStockNumOnOrder())
                .field("create_time", groupStockQuant.getCreateTime())
                .field("update_time", groupStockQuant.getUpdateTime());
        if (groupStockQuant.getUnitPrice() != null && groupStockQuant.getStockNum() != null) {
            contentBuilder.field("total_price", groupStockQuant.getUnitPrice() * groupStockQuant.getStockNum());
        }
        if (groupStockQuant.getInitialUnitPrice() != null && groupStockQuant.getInitialStockNum() != null) {
            contentBuilder.field("total_initial_price", groupStockQuant.getInitialUnitPrice() * groupStockQuant.getInitialStockNum());
        }
        contentBuilder.endObject();
        SearchClient.getClient().prepareIndex(index, groupStockType).setSource(contentBuilder)
                .setId(brandGoodsId).execute().actionGet();
    }

    /**
     * 查询结果
     *
     * @param queryBuilder 条件
     */
    private BaseResponse searchFunction(QueryBuilder queryBuilder, int pageSize, int recordNo, boolean groupFlag) {
        try {
            String index = PropertiesUtil.getProperties("erp-api.properties").getProperty("es.index");
            if (groupFlag) {
                String groupStockType = PropertiesUtil.getProperties("erp-api.properties").getProperty("es.type.stock.group");
                SearchResponse response = SearchClient.getClient().prepareSearch(index)
                        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                        .setTypes(groupStockType).setQuery(queryBuilder)
                        .setFrom(recordNo).setSize(pageSize).addSort("goods_id", SortOrder.DESC).setExplain(true).execute().actionGet();
                return getStockQuantList(response, groupFlag);
            }
            String singleStockType = PropertiesUtil.getProperties("erp-api.properties").getProperty("es.type.stock.single");
            SearchResponse response = SearchClient.getClient().prepareSearch(index)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setTypes(singleStockType).setQuery(queryBuilder)
                    .setFrom(recordNo).setSize(pageSize).addSort("goods_id", SortOrder.DESC).setExplain(true).execute().actionGet();
            return getStockQuantList(response, groupFlag);
        } catch (Exception e) {
            LOGGER.error("查询es库存失败!", e);
            return null;
        }
    }

    /**
     * 对response结果的分析
     *
     * @param response 查询结果
     */
    private BaseResponse getStockQuantList(SearchResponse response, boolean groupFlag) {
        String brandId = null;
        // 命中的记录数
        long totalHits = response.getHits().totalHits();
        if (totalHits == 0) {
            return new BaseResponse(0, new ArrayList());
        }
        List<Warehouse> warehouseList = warehouseDao.select(new WarehouseQueryRequest());
        Map<String, Warehouse> warehouseMap = new HashMap<>();
        for (Warehouse warehouse : warehouseList) {
            warehouseMap.put(warehouse.getWarehouseId(), warehouse);
        }
        if (groupFlag) {
            List<StockQuantResult> stockQuantList = new ArrayList<>();
            for (SearchHit searchHit : response.getHits()) {
                StockQuantResult stockQuantResult = new StockQuantResult();
                // 打分
                float score = searchHit.getScore();
                Map<String, Object> source = searchHit.getSource();
                stockQuantResult.setStockQuantId(searchHit.getId());
                brandId = searchHit.getId().split("_")[0];
                Object goodsId = source.get("goods_id");
                if (goodsId != null) {
                    stockQuantResult.setGoodsId(goodsId.toString());
                }
                Object goodsName = source.get("goods_name");
                if (goodsName != null) {
                    stockQuantResult.setGoodsName(goodsName.toString());
                }
                Object goodsPic = source.get("goods_pic");
                if (goodsPic != null) {
                    stockQuantResult.setGoodsPic(goodsPic.toString());
                } else {
                    stockQuantResult.setGoodsPic("");
                }
                Object classifyId = source.get("classify_id");
                if (classifyId != null) {
                    stockQuantResult.setClassifyId(classifyId.toString());
                }
                Object classifyName = source.get("classify_name");
                if (classifyName != null) {
                    stockQuantResult.setClassifyName(classifyName.toString());
                }
                Object goodsBarCode = source.get("goods_bar_code");
                if (goodsBarCode != null) {
                    stockQuantResult.setGoodsBarCode(goodsBarCode.toString());
                }
                Object goodsSkuCode = source.get("goods_sku_code");
                if (goodsSkuCode != null) {
                    stockQuantResult.setGoodsCode(goodsSkuCode.toString());
                }
                Object features = source.get("features");
                if (features != null) {
                    stockQuantResult.setFeatures(features.toString());
                }
                Object goodsBrandName = source.get("goods_brand_name");
                if (goodsBrandName != null) {
                    stockQuantResult.setGoodsBrandName(goodsBrandName.toString());
                }
                Object unitName = source.get("unit_name");
                if (unitName != null) {
                    stockQuantResult.setUnitName(unitName.toString());
                }
                Object unitPrice = source.get("unit_price");
                if (unitPrice != null && StringUtils.isNotEmpty(unitPrice.toString())) {
                    stockQuantResult.setTotalUnitPrice(Long.parseLong(unitPrice.toString()));
                }
                Object stockNum = source.get("stock_num");
                if (stockNum != null && StringUtils.isNotEmpty(stockNum.toString())) {
                    stockQuantResult.setTotalNum(Long.parseLong(stockNum.toString()));
                }
                if (unitPrice != null && StringUtils.isNotEmpty(unitPrice.toString()) && stockNum != null && StringUtils.isNotEmpty(stockNum.toString())) {
                    stockQuantResult.setTotalCash(Long.parseLong(unitPrice.toString()) * Long.parseLong(stockNum.toString()));
                }
                Object initialUnitPrice = source.get("initial_unit_price");
                if (initialUnitPrice != null && StringUtils.isNotEmpty(initialUnitPrice.toString())) {
                    stockQuantResult.setInitinalTotalPrice(Long.parseLong(initialUnitPrice.toString()));
                }
                Object initialStockNum = source.get("initial_stock_num");
                if (initialStockNum != null && StringUtils.isNotEmpty(initialStockNum.toString())) {
                    stockQuantResult.setInitinalTotalNum(Long.parseLong(initialStockNum.toString()));
                }
                if (initialUnitPrice != null && StringUtils.isNotEmpty(initialUnitPrice.toString()) && initialStockNum != null && StringUtils.isNotEmpty(initialStockNum.toString())) {
                    stockQuantResult.setInitialTotalCash(Long.parseLong(initialUnitPrice.toString()) * Long.parseLong(initialStockNum.toString()));
                }
                stockQuantList.add(stockQuantResult);
            }
            return new BaseResponse((int) totalHits, stockQuantList);
        } else {
            List<StockQuantResult> stockQuantList = new ArrayList<>();
            for (SearchHit searchHit : response.getHits()) {
                StockQuantResult stockQuant = new StockQuantResult();
                // 打分
                float score = searchHit.getScore();
                Map<String, Object> source = searchHit.getSource();
                stockQuant.setStockQuantId(searchHit.getId());
                Object brandIdObj = source.get("brand_id");
                if (brandIdObj != null) {
                    brandId = brandIdObj.toString();
                    stockQuant.setBrandId(brandId);
                }
                Object storeId = source.get("store_id");
                if (storeId != null) {
                    stockQuant.setStoreId(storeId.toString());
                }
                Object warehouseId = source.get("warehouse_id");
                if (warehouseId != null) {
                    stockQuant.setWarehouseId(warehouseId.toString());
                    Warehouse warehouse = warehouseMap.get(warehouseId.toString());
                    stockQuant.setWarehouseName(warehouse == null ? "" : warehouse.getWarehouseName());
                }
                Object goodsId = source.get("goods_id");
                if (goodsId != null) {
                    stockQuant.setGoodsId(goodsId.toString());
                }
                Object goodsName = source.get("goods_name");
                if (goodsName != null) {
                    stockQuant.setGoodsName(goodsName.toString());
                }
                Object goodsPic = source.get("goods_pic");
                if (goodsPic != null) {
                    stockQuant.setGoodsPic(goodsPic.toString());
                }
                Object classifyId = source.get("classify_id");
                if (classifyId != null) {
                    stockQuant.setClassifyId(classifyId.toString());
                }
                Object classifyName = source.get("classify_name");
                if (classifyName != null) {
                    stockQuant.setClassifyName(classifyName.toString());
                }
                Object goodsBarCode = source.get("goods_bar_code");
                if (goodsBarCode != null) {
                    stockQuant.setGoodsBarCode(goodsBarCode.toString());
                }
                Object goodsSkuCode = source.get("goods_sku_code");
                if (goodsSkuCode != null) {
                    stockQuant.setGoodsCode(goodsSkuCode.toString());
                }
                Object features = source.get("features");
                if (features != null) {
                    stockQuant.setFeatures(features.toString());
                }
                Object goodsBrandName = source.get("goods_brand_name");
                if (goodsBrandName != null) {
                    stockQuant.setGoodsBrandName(goodsBrandName.toString());
                }
                Object unitName = source.get("unit_name");
                if (unitName != null) {
                    stockQuant.setUnitName(unitName.toString());
                }
                Object unitPrice = source.get("unit_price");
                if (unitPrice != null && StringUtils.isNotEmpty(unitPrice.toString())) {
                    stockQuant.setUnitPrice(Long.parseLong(unitPrice.toString()));
                }
                Object stockNum = source.get("stock_num");
                if (stockNum != null && StringUtils.isNotEmpty(stockNum.toString())) {
                    stockQuant.setStockNum(Long.parseLong(stockNum.toString()));
                    stockQuant.setTotalNum(Long.parseLong(stockNum.toString()));
                }
                Object initialUnitPrice = source.get("initial_unit_price");
                if (initialUnitPrice != null && StringUtils.isNotEmpty(initialUnitPrice.toString())) {
                    stockQuant.setInitialUnitPrice(Long.parseLong(initialUnitPrice.toString()));
                    stockQuant.setInitinalTotalPrice(Long.parseLong(initialUnitPrice.toString()));
                }
                Object initialStockNum = source.get("initial_stock_num");
                if (initialStockNum != null && StringUtils.isNotEmpty(initialStockNum.toString())) {
                    stockQuant.setInitialStockNum(Long.parseLong(initialStockNum.toString()));
                    stockQuant.setInitinalTotalNum(Long.parseLong(initialStockNum.toString()));
                }
                if (stockQuant.getInitialUnitPrice() != null && stockQuant.getInitialStockNum() != null) {
                    stockQuant.setTotalInitialPrice(stockQuant.getInitialUnitPrice() * stockQuant.getInitialStockNum());
                    stockQuant.setInitialTotalCash(stockQuant.getInitialUnitPrice() * stockQuant.getInitialStockNum());
                }
                Object stockUpper = source.get("stock_upper");
                if (stockUpper != null && StringUtils.isNotEmpty(stockUpper.toString())) {
                    stockQuant.setStockUpper(Long.parseLong(stockUpper.toString()));
                }
                Object stockLower = source.get("stock_lower");
                if (stockLower != null && StringUtils.isNotEmpty(stockLower.toString())) {
                    stockQuant.setStockLower(Long.parseLong(stockLower.toString()));
                }
                Object stockSafe = source.get("stock_safe");
                if (stockSafe != null && StringUtils.isNotEmpty(stockSafe.toString())) {
                    stockQuant.setStockSafe(Long.parseLong(stockSafe.toString()));
                }
                Object stockNumUsable = source.get("stock_num_usable");
                if (stockNumUsable != null && StringUtils.isNotEmpty(stockNumUsable.toString())) {
                    stockQuant.setStockNumUsable(Long.parseLong(stockNumUsable.toString()));
                }
                Object stockNumUsing = source.get("stock_num_using");
                if (stockNumUsing != null && StringUtils.isNotEmpty(stockNumUsing.toString())) {
                    stockQuant.setStockNumUsing(Long.parseLong(stockNumUsing.toString()));
                }
                Object stockNumOnOrder = source.get("stock_num_on_order");
                if (stockNumOnOrder != null && StringUtils.isNotEmpty(stockNumOnOrder.toString())) {
                    stockQuant.setStockNumOnOrder(Long.parseLong(stockNumOnOrder.toString()));
                }
                Object createTime = source.get("create_time");
                if (createTime != null && StringUtils.isNotEmpty(createTime.toString())) {
                    stockQuant.setCreateTime(DateFormatUtil.parseDateISO(createTime.toString()));
                }
                Object updateTime = source.get("update_time");
                if (updateTime != null && StringUtils.isNotEmpty(updateTime.toString())) {
                    stockQuant.setUpdateTime(DateFormatUtil.parseDateISO(updateTime.toString()));
                }
                stockQuantList.add(stockQuant);
            }
            return new BaseResponse((int) totalHits, stockQuantList);
        }
    }

    private boolean initElasticSearchStruct(String index, String type, boolean groupStatus) {
        try {
            boolean indexResult = SearchClient.createIndex(SearchClient.getClient(), index);
            if (!indexResult) {
                LOGGER.error("创建es index失败!");
            }
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

                    .field("brand_id").startObject().field("type", "keyword").endObject()
                    .field("goods_id").startObject().field("type", "keyword").endObject()
                    .field("classify_id").startObject().field("type", "keyword").endObject()
                    .field("goods_sku_code").startObject().field("type", "keyword").endObject()
                    .field("goods_bar_code").startObject().field("type", "text").endObject()
                    .field("unit_price").startObject().field("type", "long").endObject()
                    .field("stock_num").startObject().field("type", "long").endObject()
                    .field("total_price").startObject().field("type", "long").endObject()
                    .field("initial_stock_num").startObject().field("type", "long").endObject()
                    .field("initial_unit_price").startObject().field("type", "long").endObject()
                    .field("total_initial_price").startObject().field("type", "long").endObject()
                    .field("stock_upper").startObject().field("type", "long").endObject()
                    .field("stock_lower").startObject().field("type", "long").endObject()
                    .field("stock_safe").startObject().field("type", "long").endObject()
                    .field("stock_num_usable").startObject().field("type", "long").endObject()
                    .field("stock_num_using").startObject().field("type", "long").endObject()
                    .field("stock_num_on_order").startObject().field("type", "long").endObject()
                    .field("create_time").startObject().field("type", "date").endObject()
                    .field("update_time").startObject().field("type", "date").endObject();

            if (!groupStatus) {
                builder.field("store_id").startObject().field("type", "keyword").endObject()
                        .field("warehouse_id").startObject().field("type", "keyword").endObject()
                        .field("warehouse_name")
                        .startObject()
                        .field("type", "text")
                        .field("analyzer", "ik_smart")
                        .endObject();
            }

            builder.endObject().endObject();
            PutMappingRequest request = Requests.putMappingRequest(index).source(builder).type(type);
            PutMappingResponse response = SearchClient.getClient().admin().indices().putMapping(request).actionGet();
            return response.isAcknowledged();
        } catch (Exception e) {
            LOGGER.error("初始化es库存结构失败!", e);
            return false;
        }
    }

    @Override
    public BaseResponse selectGroupWithParam(StockQuantQueryRequest query) throws Exception {
        //过滤忽略的商品
        if (StringUtils.isNotEmpty(query.getSkipIds())) {
            List<String> skipIdList = JsonUtil.fromJson(query.getSkipIds(), new TypeReference<List<String>>() {
            });
            if (skipIdList != null && skipIdList.size() > 0) {
                query.setSkipIdList(skipIdList);
            }
        }
        //没有供应商条件和分类条件，直接查询
        if (StringUtils.isEmpty(query.getSupplierId()) && StringUtils.isEmpty(query.getClassifyId())) {
            return queryWithoutSupplierAndCategory(query);
        }
        query = constructQueryStockParam(query);

        boolean groupFlag = true;
        if (StringUtils.isNotEmpty(query.getStoreId())) {
            groupFlag = false;
        }
        BoolQueryBuilder queryBuilder = constructQueryBuild(query, groupFlag);
        constructQueryPage(query);
        BaseResponse response = searchFunction(queryBuilder, query.getPageSize(), query.getRecordNo(), groupFlag);
        if (response == null) {
            int count = stockQuantDao.queryDetailGroupCount(query).size();
            query.setCount(count);
            List<StockQuantResult> stockQuantResultList = stockQuantDao.queryDetailGroupList(query);
            response = new BaseResponse(count, stockQuantResultList);
        } else {
            List<StockQuantResult> list = response.getList();
            list.forEach(s -> {
                boolean pricebl = s.getUnitPrice() != null;
                boolean cashbl = s.getStockNum() != null;
                if(s.getTotalUnitPrice() == null){
                    s.setTotalUnitPrice(pricebl ? s.getUnitPrice() : 0);
                }
                if(s.getTotalCash() == null){
                    s.setTotalCash(pricebl && cashbl ? s.getStockNum() * s.getUnitPrice() : 0);
                }

            });
            response.setList(list);
        }
        return response;
    }

    @Override
    public HttpResponse initDeleteStock() {
        try {
            Properties properties = PropertiesUtil.getProperties("erp-api.properties");
            String queryDeletedAndNotRequireInventoryUrl = properties.getProperty("queryDeletedAndNotRequireInventoryUrl");
            if (StringUtils.isBlank(queryDeletedAndNotRequireInventoryUrl)) {
                return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
            }
            String result = HttpClient.get(queryDeletedAndNotRequireInventoryUrl).action().result();
            HttpResponse response = JsonUtil.fromJson(result, HttpResponse.class);
            if (response == null || StringUtils.isBlank(response.getCode()) || !StringUtils.equals(response.getCode(), "0") || response.getData() == null) {
                return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
            }
            List<String> goodsIdList = JsonUtil.fromJson(JsonUtil.toJson(response.getData()), new TypeReference<List<String>>(){});
            StockQuantQueryRequest stockQuantQueryRequest = new StockQuantQueryRequest();
            for (int i=0;i<goodsIdList.size()/1024;i++) {
                stockQuantQueryRequest.setIdList(goodsIdList.subList(i*1024, (i + 1)*1024));
                List<StockQuantResult> stockQuantList = selectWithParam(stockQuantQueryRequest).getList();
                for (StockQuantResult stockQuant : stockQuantList) {
                    deleteEsData(stockQuant.getBrandId(), stockQuant.getGoodsId(), null);
                    deleteEsData(null, null, stockQuant.getStockQuantId());
                    stockQuantDao.deleteByStockQuantId(stockQuant.getStockQuantId());
                }
            }
            if (goodsIdList.size()%1024 > 0) {
                stockQuantQueryRequest.setIdList(goodsIdList.subList(goodsIdList.size() - goodsIdList.size()%1024, goodsIdList.size()));
                List<StockQuantResult> stockQuantList = selectWithParam(stockQuantQueryRequest).getList();
                for (StockQuantResult stockQuant : stockQuantList) {
                    deleteEsData(stockQuant.getBrandId(), stockQuant.getGoodsId(), null);
                    deleteEsData(null, null, stockQuant.getStockQuantId());
                    stockQuantDao.deleteByStockQuantId(stockQuant.getStockQuantId());
                }
            }
            return HttpResponse.success();
        } catch (Exception e) {
            LOGGER.error("清理库存数据失败！", e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    private BoolQueryBuilder constructQueryBuild(StockQuantQueryRequest query, boolean groupFlag) throws Exception {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (StringUtils.isNotEmpty(query.getStockQuantId())) {
            queryBuilder.must(QueryBuilders.termQuery("stock_quant_id", query.getStockQuantId()));
        }
        if (StringUtils.isNotEmpty(query.getBrandId())) {
            queryBuilder.must(QueryBuilders.termQuery("brand_id", query.getBrandId()));
        }
        if (StringUtils.isNotEmpty(query.getWarehouseId()) && !groupFlag) {
            queryBuilder.must(QueryBuilders.termQuery("warehouse_id", query.getWarehouseId()));
        }
        if (StringUtils.isNotEmpty(query.getStoreId()) && !groupFlag) {
            queryBuilder.must(QueryBuilders.termQuery("store_id", query.getStoreId()));
            if (StringUtils.isEmpty(query.getWarehouseId())) {
                WarehouseQueryRequest warehouseQueryRequest = new WarehouseQueryRequest();
                warehouseQueryRequest.setStoreId(query.getStoreId());
                Warehouse warehouse = warehouseDao.select(warehouseQueryRequest).get(0);
                queryBuilder.must(QueryBuilders.termQuery("warehouse_id", warehouse.getWarehouseId()));
            }
        }
        if (StringUtils.isNotEmpty(query.getGoodsId())) {
            queryBuilder.must(QueryBuilders.termQuery("goods_id", query.getGoodsId()));
        }
        if (query.getIdList() != null && query.getIdList().size() > 0) {
            queryBuilder.must(QueryBuilders.termsQuery("goods_id", query.getIdList()));
        }
        if (query.getSkipIdList() != null && query.getSkipIdList().size() > 0) {
            queryBuilder.mustNot(QueryBuilders.termsQuery("goods_id", query.getSkipIdList()));
        }
        if (StringUtils.isNotEmpty(query.getGoodsName())) {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.should(QueryBuilders.matchQuery("goods_name", query.getGoodsName()));
            queryBuilder.must(boolQueryBuilder);
        }
        if (query.getClassfiyIdList() != null && query.getClassfiyIdList().size() > 0) {
            queryBuilder.must(QueryBuilders.termQuery("classify_id", query.getClassfiyIdList()));
        }
        if (StringUtils.isNotEmpty(query.getGoodsBarCode())) {
            queryBuilder.must(QueryBuilders.wildcardQuery("goods_bar_code", query.getGoodsBarCode() + "*"));
        }
        if (StringUtils.isNotEmpty(query.getGoodsCode())) {
            queryBuilder.must(QueryBuilders.termQuery("goods_sku_code", query.getGoodsCode()));
        }
        if (StringUtils.isNotEmpty(query.getFeatures())) {
            queryBuilder.must(QueryBuilders.matchQuery("features", query.getFeatures()));
        }
        if (StringUtils.isNotEmpty(query.getCondition())) {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.should(QueryBuilders.matchQuery("goods_name", query.getCondition()));
            boolQueryBuilder.should(QueryBuilders.wildcardQuery("goods_bar_code", query.getCondition() + "*"));
            queryBuilder.must(boolQueryBuilder);
        }
        if (query.getSkipIdList() != null && query.getSkipIdList().size() > 0) {
            queryBuilder.mustNot(QueryBuilders.termsQuery("goods_id", query.getSkipIdList()));
        }
        if (query.getMinAmount() != null) {
            queryBuilder.must(QueryBuilders.rangeQuery("stock_num").from(query.getMinAmount()).includeLower(true));
        }
        if (query.getMaxAmount() != null) {
            queryBuilder.must(QueryBuilders.rangeQuery("stock_num").to(query.getMaxAmount()).includeUpper(true));
        }
        return queryBuilder;
    }

    private StockQuantQueryRequest constructQueryPage(StockQuantQueryRequest query) {
        if (query.getPageSize() == null) {
            query.setPageSize(99999999);
            query.setRecordNo(0);
        } else {
            if (query.getPageNum() == null || query.getPageNum() == 0) {
                query.setPageNum(1);
            }
            query.setRecordNo(query.getPageSize() * (query.getPageNum() - 1));
        }
        return query;
    }

    private StockQuant selectGroupById(String brandGoodsId) {
        try {
            QueryBuilder queryBuilder = QueryBuilders.idsQuery().addIds(brandGoodsId);
            BaseResponse response = searchFunction(queryBuilder, 1, 0, true);
            if (response == null) {
                return stockQuantDao.selectByStockQuantId(brandGoodsId);
            }
            List<StockQuant> stockQuantList = JsonUtil.fromJson(JsonUtil.toJson(response.getList()), new TypeReference<List<StockQuant>>() {
            });
            if (stockQuantList != null && stockQuantList.size() > 0) {
                return stockQuantList.get(0);
            }
            return null;
        } catch (Exception e) {
            LOGGER.error("查询库存信息失败!", e);
            return null;
        }
    }

    private void initStockQuant(StockQuantResult stockQuant) {
        if (stockQuant.getUnitPrice() == null) {
            stockQuant.setUnitPrice(0L);
        }
        if (stockQuant.getStockNum() == null) {
            stockQuant.setStockNum(0L);
        }
        if (stockQuant.getTotalPrice() == null) {
            stockQuant.setTotalPrice(0L);
        }
        if (stockQuant.getInitialUnitPrice() == null) {
            stockQuant.setInitialUnitPrice(0L);
        }
        if (stockQuant.getInitialStockNum() == null) {
            stockQuant.setInitialStockNum(0L);
        }
        if (stockQuant.getTotalInitialPrice() == null) {
            stockQuant.setTotalInitialPrice(0L);
        }
    }

    private String addGoodsPic(Map<String, GoodsOdoo> map, String goodsId) {
        if (map != null && map.get(goodsId) != null && !StringUtils.isEmpty(map.get(goodsId).getGoodsPic())) {
            return map.get(goodsId).getGoodsPic();
        }
        return "";
    }

    private void removeInitialValue(List<StockQuantResult> stockQuantResultList) {
        for (StockQuantResult stockQuantResult : stockQuantResultList) {
            stockQuantResult.setInitialTotalCash(null);
            stockQuantResult.setInitinalTotalPrice(null);
            stockQuantResult.setInitinalTotalNum(null);
            stockQuantResult.setTotalInitialPrice(null);
            stockQuantResult.setTotalNum(null);
            stockQuantResult.setTotalPrice(null);
            stockQuantResult.setWarehouseName(null);
        }
    }

    /**
     * 删除es数据
     * @param brandId   品牌id
     * @param goodsId   商品id
     * @param stockQuantId  库存id
     * @throws Exception    异常
     */
    private void deleteEsData(String brandId, String goodsId, String stockQuantId) {
        try {
            String index = PropertiesUtil.getProperties("erp-api.properties").getProperty("es.index");
            String singleStockType = PropertiesUtil.getProperties("erp-api.properties").getProperty("es.type.stock.single");
            String groupStockType = PropertiesUtil.getProperties("erp-api.properties").getProperty("es.type.stock.group");
            if (StringUtils.isNotBlank(stockQuantId)) {
                SearchClient.getClient().prepareDelete(index, singleStockType, stockQuantId).get();
            }
            if (StringUtils.isNotBlank(brandId) && StringUtils.isNotBlank(goodsId)) {
                SearchClient.getClient().prepareDelete(index, groupStockType, brandId + "_" + goodsId).get();
            }
        } catch (Exception e) {
            LOGGER.error("删除库存失败！库存信息为：brandId=" + brandId + ", goodsId=" + goodsId + ", stockQuantId=" + stockQuantId, e);
        }
    }

    private BaseResponse queryWithoutSupplierAndCategory(StockQuantQueryRequest query) throws Exception {
        QueryBuilder queryBuilder = constructQueryBuild(query, false);
        constructQueryPage(query);
        BaseResponse response = searchFunction(queryBuilder, query.getPageSize(), query.getRecordNo(), false);
        if (response == null) {
            response = selectList(query);
        }
        List<StockQuantResult> stockQuantList = response.getList();
        removeInitialValue(stockQuantList);
        return response;
    }

    private StockQuantQueryRequest constructQueryStockParam(StockQuantQueryRequest query) throws Exception {
        GoodsQueryRequest goodsQueryRequest = new GoodsQueryRequest();
        goodsQueryRequest.setBrandId(query.getBrandId());
        //带供应商条件查询，过滤供应商商品
        if (StringUtils.isNotEmpty(query.getSupplierId())) {
            List<String> supplierGoodsIds = supplierService.getSupplierGoodsIds(query.getSupplierId());
            if (supplierGoodsIds.size() == 0) {
                return null;
            }
            query.setIdList(supplierGoodsIds);
        }
        //带分类条件查询，过滤分类（当前分类及子分类）
        if (StringUtils.isNotEmpty(query.getClassifyId())) {
            GoodsClassifyBrand classifyQuery = new GoodsClassifyBrand();
            classifyQuery.setBrandId(query.getBrandId());
            classifyQuery.setClassifyId(query.getClassifyId());
            List<GoodsClassifyBrand> classifyList = GoodsUtil.getClassify(classifyQuery);
            List<String> classifyIdList = new ArrayList<>();
            for (GoodsClassifyBrand goodsClassify : classifyList) {
                classifyIdList.add(goodsClassify.getClassifyId());
            }
            query.setClassfiyIdList(classifyIdList);
            query.setClassifyId(null);
        }
        return query;
    }

}

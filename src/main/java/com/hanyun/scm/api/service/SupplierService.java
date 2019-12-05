package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.SupplierGoods;
import com.hanyun.scm.api.domain.dto.SupplierDTO;
import com.hanyun.scm.api.domain.request.supplier.*;
import com.hanyun.scm.api.exception.SupplierException;

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
 * <p>
 * SupplierService
 * Date: 2016/10/18
 * Time: 下午9:16
 *
 * @author tianye@hanyun.com
 */
public interface SupplierService {

    /**
     * 创建供应商
     *
     * @param supplierCreate
     * @return
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse create(SupplierCreateRequest supplierCreate);

    public Map<String, SupplierGoods> getSupplierGoodsMap(String supplierId);

    /**
     * 查询供应商详情
     *
     * @param supplierId
     * @return
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse detail(String supplierId);

    /**
     * 查询供应商列表
     *
     * @param supplierQuery
     * @return
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse select(SupplierQueryRequest supplierQuery);

    /**
     * 导出供应商列表
     *
     * @param supplierQuery
     * @return
     * @author tangqiming_v@hanyun.com
     */
    List<List<Object>> exportExcelBySuppleir(SupplierQueryRequest supplierQuery) throws SupplierException;

    /**
     * 更新供应商信息
     *
     * @param supplierModify
     * @return
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse modifySupplier(SupplierModifyRequest supplierModify);

    /**
     * 删除供应商->改状态
     *
     * @param supplierModify 对象
     * @return HttpResponse
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse deleteSupplierByStatus(SupplierDeleteStatusRequest supplierModify);

    /**
     * 删除供应商
     *
     * @param supplierId
     * @return
     * @author tangqiming_v@hanyun.com
     */
    HttpResponse deleteSupplier(String supplierId);

    /**
     * 修改供应商可用状态
     *
     * @param supplier
     * @return
     */
    HttpResponse modifyAvailableStatus(SupplierModifyStatusRequest supplier);

    /**
     *  获取供应商商品id list
     * @param supplierId 供应商id
     * @return List<String>
     */
    List<String> getSupplierGoodsIds(String supplierId);

    /**
     * 商品查看供应商列表
     * @param supplier 查询bean
     * @return HttpResponse
     */
    HttpResponse querySupplierGoodsList(SupplierDTO supplier);
}

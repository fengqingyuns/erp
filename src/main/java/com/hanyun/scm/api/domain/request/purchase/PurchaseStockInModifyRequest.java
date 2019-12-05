package com.hanyun.scm.api.domain.request.purchase;

import com.hanyun.scm.api.domain.StockPicking;
import com.hanyun.scm.api.domain.dto.ModifyPurchaseStockInDTO;
import org.hibernate.validator.constraints.NotEmpty;

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
 * PurchaseStockInModifyRequest
 * Date: 2016/12/9
 * Time: 上午10:31
 *
 * @author tianye@hanyun.com
 */
public class PurchaseStockInModifyRequest extends PurchaseStockInCreateRequest {

    @NotEmpty
    private String stockPickingId;      //库存移动单id
    public PurchaseStockInModifyRequest() {}

    public PurchaseStockInModifyRequest(ModifyPurchaseStockInDTO modifyPurchaseStockInDTO) {
        this.setBrandId(modifyPurchaseStockInDTO.getBrandId());
        this.setStockPickingId(modifyPurchaseStockInDTO.getStockPickingId());
        this.setStoreId(modifyPurchaseStockInDTO.getStoreId());
        this.setStoreName(modifyPurchaseStockInDTO.getStoreName());
        this.setSupplierId(modifyPurchaseStockInDTO.getSupplierId());
        this.setSupplierName(modifyPurchaseStockInDTO.getSupplierName());
        this.setToWarehouseId(modifyPurchaseStockInDTO.getToWarehouseId());
        this.setToWarehouseName(modifyPurchaseStockInDTO.getToWarehouseName());
        this.setOperatorId(modifyPurchaseStockInDTO.getOperatorId());
        this.setOperatorName(modifyPurchaseStockInDTO.getOperatorName());
        this.setStockPickingType(modifyPurchaseStockInDTO.getStockPickingType());
        this.setStockPickingPrice(modifyPurchaseStockInDTO.getStockPickingPrice());
        this.setPickingAmount(modifyPurchaseStockInDTO.getPickingAmount());
        this.setPurchaseType(modifyPurchaseStockInDTO.getPurchaseType());
        this.setBusinessManId(modifyPurchaseStockInDTO.getBusinessManId());
        this.setBusinessManName(modifyPurchaseStockInDTO.getBusinessManName());
        this.setRemark(modifyPurchaseStockInDTO.getRemark());
        this.setDistinctPrice(modifyPurchaseStockInDTO.getDistinctPrice());
        this.setSrcOrderId(modifyPurchaseStockInDTO.getSrcOrderId());
        this.setSrcOrderDocumentId(modifyPurchaseStockInDTO.getSrcOrderDocumentId());
        this.setStockPickingGoodsList(modifyPurchaseStockInDTO.getStockPickingGoodsList());
    }
    @Override
    public StockPicking getStockPicking() {
        StockPicking stockPicking = super.getStockPicking();
        stockPicking.setStockPickingId(this.stockPickingId);
        return stockPicking;
    }

    public String getStockPickingId() {
        return stockPickingId;
    }

    public void setStockPickingId(String stockPickingId) {
        this.stockPickingId = stockPickingId;
    }
}

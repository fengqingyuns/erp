package com.hanyun.scm.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanyun.scm.api.domain.request.BaseRequest;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;
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
public class StockQuant extends BaseRequest {

    @JsonIgnore
    private Long id;

    private String stockQuantId;
    @NotEmpty
    private String brandId;

    private String storeId;

    private String warehouseId;

    private String warehouseName;
    
    private String goodsId;

    private String goodsName;

    private String goodsPic;

    private String classifyId;

    private String classifyName;

    private Integer goodsType;

    private String goodsTypeName;
    
    private String unitId;

    private String unitName;

    private Long unitPrice;

    private Long stockNum;

    private Long stockUpper;            //库存上限

    private Long stockLower;            //库存下限

    private Long stockSafe;             //安全库存

    private Long stockNumUsable;		//可用库存
    
    private Long stockNumUsing;			//占用库存
    
    private Long stockNumOnOrder;		//在途库存
    
    private Long totalNum;				//非持久化數據,用來存放總數量
    
    private Long resutNum;				//非持久化數據,用來存放總數量

    private String ids;                 //过滤的商品id集合json

    private List<String> idList;        //过滤的商品id集合

    private String skipIds;             //忽略的商品id集合json

    private List<String> skipIdList;    //忽略的商品id集合

    private Date createTime;

    private Date updateTime;
    
    private String goodsBarCode;
    
    private String planBeginTime;

    private String planEndTime;
    
    private List<StockQuant> stockQuantSingleList; //存放单仓库修改的list

    private List<StockQuant> stockQuantDoubleList; //存放多仓库修改的list
    
    private Integer stockStatus;			//查询的状态下标
    
    private String choose;
    
    private Long   totalPrice;

	private Long allTotalNum;
	
	private Float tableNumber = 0F;		   //存放 上限 下限 安全库存
    
	private String proportionStr;   	   //存放 上限 下限 安全库存 比例
     
	private Float yjNum = 0F;			   //存放短缺数、预警数、挤压数
    
	private Long nowStockNum;				//当前库存

    private String goodsCode;               //商品编码

    private String features;                //规格

    private String goodsBrandName;          //商品品牌名称
    
    private String condition;				//查询条件

    private List<String> classfiyIdList;

    private Long initialUnitPrice;          //期初单价

    private Long initialStockNum;           //期初库存

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public Long getInitialUnitPrice() {
        return initialUnitPrice;
    }

    public void setInitialUnitPrice(Long initialUnitPrice) {
        this.initialUnitPrice = initialUnitPrice;
    }

    public Long getInitialStockNum() {
        return initialStockNum;
    }

    public void setInitialStockNum(Long initialStockNum) {
        this.initialStockNum = initialStockNum;
    }

    public String getGoodsBrandName() {
        return goodsBrandName;
    }

    public void setGoodsBrandName(String goodsBrandName) {
        this.goodsBrandName = goodsBrandName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public Long getStockUpper() {
        return stockUpper;
    }

    public void setStockUpper(Long stockUpper) {
        this.stockUpper = stockUpper;
    }

    public Long getStockLower() {
        return stockLower;
    }

    public void setStockLower(Long stockLower) {
        this.stockLower = stockLower;
    }

    public Long getStockSafe() {
        return stockSafe;
    }

    public void setStockSafe(Long stockSafe) {
        this.stockSafe = stockSafe;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public List<String> getSkipIdList() {
        return skipIdList;
    }

    public void setSkipIdList(List<String> skipIdList) {
        this.skipIdList = skipIdList;
    }

    public String getSkipIds() {
        return skipIds;
    }

    public void setSkipIds(String skipIds) {
        this.skipIds = skipIds;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockQuantId() {
        return stockQuantId;
    }

    public void setStockQuantId(String stockQuantId) {
        this.stockQuantId = stockQuantId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getStockNum() {
        return stockNum;
    }

    public void setStockNum(Long stockNum) {
        this.stockNum = stockNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getGoodsBarCode() {
		return goodsBarCode;
	}

	public void setGoodsBarCode(String goodsBarCode) {
		this.goodsBarCode = goodsBarCode;
	}

	public String getPlanBeginTime() {
		return planBeginTime;
	}

	public void setPlanBeginTime(String planBeginTime) {
		this.planBeginTime = planBeginTime;
	}

	public String getPlanEndTime() {
		return planEndTime;
	}

	public void setPlanEndTime(String planEndTime) {
		this.planEndTime = planEndTime;
	}

	public Long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}

	public Long getResutNum() {
		return resutNum;
	}

	public void setResutNum(Long resutNum) {
		this.resutNum = resutNum;
	}

	public List<StockQuant> getStockQuantSingleList() {
		return stockQuantSingleList;
	}

	public void setStockQuantSingleList(List<StockQuant> stockQuantSingleList) {
		this.stockQuantSingleList = stockQuantSingleList;
	}

	public List<StockQuant> getStockQuantDoubleList() {
		return stockQuantDoubleList;
	}

	public void setStockQuantDoubleList(List<StockQuant> stockQuantDoubleList) {
		this.stockQuantDoubleList = stockQuantDoubleList;
	}

	public Integer getStockStatus() {
		return stockStatus;
	}

	public void setStockStatus(Integer stockStatus) {
		this.stockStatus = stockStatus;
	}

	public String getChoose() {
		return choose;
	}

	public void setChoose(String choose) {
		this.choose = choose;
	}

	public String getGoodsTypeName() {
		return goodsTypeName;
	}

	public void setGoodsTypeName(String goodsTypeName) {
		this.goodsTypeName = goodsTypeName;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getAllTotalNum() {
		return allTotalNum;
	}

	public void setAllTotalNum(Long allTotalNum) {
		this.allTotalNum = allTotalNum;
	}

	public Long getStockNumUsable() {
		return stockNumUsable;
	}

	public void setStockNumUsable(Long stockNumUsable) {
		this.stockNumUsable = stockNumUsable;
	}

	public Long getStockNumUsing() {
		return stockNumUsing;
	}

	public void setStockNumUsing(Long stockNumUsing) {
		this.stockNumUsing = stockNumUsing;
	}

	public Long getStockNumOnOrder() {
		return stockNumOnOrder;
	}

	public void setStockNumOnOrder(Long stockNumOnOrder) {
		this.stockNumOnOrder = stockNumOnOrder;
	}

	public Float getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(Float tableNumber) {
		this.tableNumber = tableNumber;
	}

	public String getProportionStr() {
		return proportionStr;
	}

	public void setProportionStr(String proportionStr) {
		this.proportionStr = proportionStr;
	}

	public Float getYjNum() {
		return yjNum;
	}

	public void setYjNum(Float yjNum) {
		this.yjNum = yjNum;
	}

	public Long getNowStockNum() {
		return nowStockNum;
	}

	public void setNowStockNum(Long nowStockNum) {
		this.nowStockNum = nowStockNum;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

    public List<String> getClassfiyIdList() {
        return classfiyIdList;
    }

    public void setClassfiyIdList(List<String> classfiyIdList) {
        this.classfiyIdList = classfiyIdList;
    }
}
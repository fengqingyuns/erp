package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.request.stock.StockInListRequestDTO;
import com.hanyun.scm.api.service.StockInDetailByAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;

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
 * stockInDetailByApp
 * Date: 2017/8/13 0013
 * Time: 13:29
 *
 * @author tangqiming@hanyun.com
 */
@Controller
@RequestMapping(value = "/stock-in")
public class StockInDetailByAppController {

    @Resource
    private StockInDetailByAppService stockInDetailByAppService;

    private static final Logger LOGGER = LoggerFactory.getLogger(StockInDetailByAppController.class);

    /**
     * app查询入库列表(采购订单,订货单)
     * @param dto 参数
     * @param result 验证
     * @return HttpResponse
     */
    @GetMapping(value = "/list")
    @ResponseBody
    public HttpResponse select(@Valid StockInListRequestDTO dto, BindingResult result){
        if(result.hasErrors()){
            LOGGER.error("查询入库列表失败");
            return HttpResponse.failure(ResultCode.STOCK_IN_LIST_PARAMS_ERROR);
        }
        return stockInDetailByAppService.select(dto);
    }


}

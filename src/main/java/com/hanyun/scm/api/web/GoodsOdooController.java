package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.dto.QueryBillGoodsDTO;
import com.hanyun.scm.api.domain.request.goods.GoodsQueryRequest;
import com.hanyun.scm.api.service.GoodsOdooService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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
 * GoodsOdooController
 * Date: 2016/10/19
 * Time: 下午3:23
 *
 * @author tianye@hanyun.com
 */
@Controller
@RequestMapping(value="/goods")
public class GoodsOdooController {

    private static Logger LOGGER = LoggerFactory.getLogger(GoodsOdooController.class);

    @Resource
    private GoodsOdooService goodsOdooService;

    /**
     * 查询商品列表
     * @param goodsQueryRequest     查询商品参数
     * @return  HttpResponse
     * @author tianye@hanyun.com
     */
    @PostMapping(value="/query")
    @ResponseBody
    public HttpResponse select(@Valid @RequestBody GoodsQueryRequest goodsQueryRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOGGER.error("查询供应链商品参数错误!");
            return HttpResponse.failure(ResultCode.GOODS_ODOO_QUERY_PARAM_ERROR);
        }
        return goodsOdooService.select(goodsQueryRequest);
    }

    /**
     * 查询进销存单据商品信息get
     * @param dto 查询对象
     * @param bindingResult 验证参数
     * @return HttpResponse
     */
    @GetMapping(value = "/bill-goods")
    @ResponseBody
    public HttpResponse queryBillGoods(@Valid QueryBillGoodsDTO dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            LOGGER.error("查询单据商品参数错误");
            return HttpResponse.failure(ResultCode.BILL_GOODS_PARAMS_ERROR);
        }
        return goodsOdooService.selectBillGoods(dto);
    }

    /**
     * 查询进销存单据商品信息Post
     * @param dto 查询对象
     * @param bindingResult 验证参数
     * @return HttpResponse
     */
    @PostMapping(value = "/bill-goods")
    @ResponseBody
    public HttpResponse queryBillGoodsForPost(@RequestBody @Valid QueryBillGoodsDTO dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            LOGGER.error("查询单据商品参数错误");
            return HttpResponse.failure(ResultCode.BILL_GOODS_PARAMS_ERROR);
        }
        return goodsOdooService.selectBillGoods(dto);
    }

}

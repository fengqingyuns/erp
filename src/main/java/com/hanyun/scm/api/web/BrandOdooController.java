package com.hanyun.scm.api.web;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.consts.ResultCode;
import com.hanyun.scm.api.domain.request.brand.BrandCreateRequest;
import com.hanyun.scm.api.domain.request.brand.BrandModifyRequest;
import com.hanyun.scm.api.domain.request.brand.BrandQueryRequest;
import com.hanyun.scm.api.service.BrandOdooService;
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
 * BrandOdooController
 * Date: 2016/10/21
 * Time: 下午6:45
 *
 * @author tianye@hanyun.com
 */
@Controller
@RequestMapping(value="/brand")
public class BrandOdooController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrandOdooController.class);

    @Resource
    private BrandOdooService brandOdooService;

    /**
     * 创建品牌
     * @param brandCreateRequest    创建品牌参数
     * @return  HttpResponse
     * @author tianye@hanyun.com
     */
    @PostMapping(value="/create")
    @ResponseBody
    public HttpResponse create(@Valid @RequestBody BrandCreateRequest brandCreateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOGGER.error("创建品牌供应链信息参数错误!");
            return HttpResponse.failure(ResultCode.BRAND_ODOO_CREATE_PARAM_ERROR);
        }
        return brandOdooService.create(brandCreateRequest);
    }

    /**
     * 查询品牌列表
     * @param brandQueryRequest     查询品牌参数
     * @return  HttpResponse
     * @author tianye@hanyun.com
     */
    @GetMapping(value="/query")
    @ResponseBody
    public HttpResponse select(@Valid BrandQueryRequest brandQueryRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOGGER.error("查询品牌供应链信息参数错误!");
            return HttpResponse.failure(ResultCode.BRAND_ODOO_QUERY_PARAM_ERROR);
        }
        return brandOdooService.select(brandQueryRequest);
    }

    /**
     * 修改品牌
     * @param brandModifyRequest    修改品牌参数
     * @return  HttpResponse
     * @author tianye@hanyun.com
     */
    @PostMapping(value="/modify")
    @ResponseBody
    public HttpResponse modify(@Valid @RequestBody BrandModifyRequest brandModifyRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOGGER.error("修改品牌供应链信息参数错误!");
            return HttpResponse.failure(ResultCode.BRAND_ODOO_MODIFY_PARAM_ERROR);
        }
        return brandOdooService.modify(brandModifyRequest);
    }

    /**
     * 删除品牌
     * @param brandId   品牌id
     * @return  HttpResponse
     * @author tianye@hanyun.com
     */
    @GetMapping(value="/delete/{brandId}")
    @ResponseBody
    public HttpResponse delete(@PathVariable String brandId) {
        return brandOdooService.delete(brandId);
    }

    /**
     * 查询品牌详情
     * @param brandId   品牌id
     * @return  HttpResponse
     * @author tianye@hanyun.com
     */
    @GetMapping(value = "/detail/{brandId}")
    @ResponseBody
    public HttpResponse detail(@PathVariable String brandId) {
        return brandOdooService.detail(brandId);
    }

}

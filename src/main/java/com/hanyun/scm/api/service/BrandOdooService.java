package com.hanyun.scm.api.service;

import com.hanyun.ground.util.protocol.http.HttpResponse;
import com.hanyun.scm.api.domain.request.brand.BrandCreateRequest;
import com.hanyun.scm.api.domain.request.brand.BrandModifyRequest;
import com.hanyun.scm.api.domain.request.brand.BrandQueryRequest;

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
 * BrandOdooService
 * Date: 2016/10/21
 * Time: 下午6:47
 *
 * @author tianye@hanyun.com
 */
public interface BrandOdooService {

    /**
     * 创建品牌
     *
     * @param brandCreateRequest    创建品牌参数
     * @return  HttpResponse
     * @author tianye@hanyun.com
     */
	HttpResponse create(BrandCreateRequest brandCreateRequest);

    /**
     * 查询品牌
     *
     * @param brandQueryRequest     查询品牌参数
     * @return  HttpResponse
     * @author tianye@hanyun.com
     */
	HttpResponse select(BrandQueryRequest brandQueryRequest);

    /**
     * 查询品牌详情
     *
     * @param brandId   品牌id
     * @return  HttpResponse
     * @author tianye@hanyun.com
     */
	HttpResponse detail(String brandId);

    /**
     * 修改品牌信息
     *
     * @param brandModifyRequest    修改品牌参数
     * @return  HttpResponse
     * @author tianye@hanyun.com
     */
	HttpResponse modify(BrandModifyRequest brandModifyRequest);

    /**
     * 删除品牌信息
     *
     * @param brandId   品牌id
     * @return  HttpResponse
     * @author tianye@hanyun.com
     */
	HttpResponse delete(String brandId);

}

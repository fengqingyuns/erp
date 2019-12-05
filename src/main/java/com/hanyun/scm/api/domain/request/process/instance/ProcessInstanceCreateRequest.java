package com.hanyun.scm.api.domain.request.process.instance;

import com.hanyun.scm.api.domain.ProcessInstance;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

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
 * ProcessInstanceCreateRequest
 * Date: 2017/4/5
 * Time: 下午2:39
 *
 * @author tianye@hanyun.com
 */
public class ProcessInstanceCreateRequest extends ProcessInstance {

    @NotEmpty
    private String brandId;

    @NotNull
    private Integer processType;

    private Long price;

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public String getBrandId() {
        return brandId;
    }

    @Override
    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    @Override
    public Integer getProcessType() {
        return processType;
    }

    @Override
    public void setProcessType(Integer processType) {
        this.processType = processType;
    }
}

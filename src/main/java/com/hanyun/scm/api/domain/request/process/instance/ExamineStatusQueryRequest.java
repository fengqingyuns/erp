package com.hanyun.scm.api.domain.request.process.instance;

import com.hanyun.scm.api.domain.dto.OrderDataDTO;
import org.hibernate.validator.constraints.NotBlank;

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
 * ExamineStatusQueryRequest
 * Date: 2017/10/26 0026
 * Time: 21:02
 *
 * @author tangqiming@hanyun.com
 */
public class ExamineStatusQueryRequest {

    @NotBlank(message = "用户id不能为空")
    private String userId;              //登录人id

    private List<OrderDataDTO> orderData;     //订单的数据<单据id,单据的创建人id>

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<OrderDataDTO> getOrderData() {
        return orderData;
    }

    public void setOrderData(List<OrderDataDTO> orderData) {
        this.orderData = orderData;
    }
}

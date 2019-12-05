package com.hanyun.scm.api.common;

import java.math.BigDecimal;

public class UtilCommon {

    /**
     * <p>Title: getMultiplyValue</p>
     * <p>Description:两数相乘(保留小数点后2位小数)</p>
     *
     * @param a
     * @param b
     * @return
     * @date 2012-3-2
     */
    public static double getMultiplyValue(double a, double b) {
        BigDecimal A = BigDecimal.valueOf(a);
        BigDecimal B = BigDecimal.valueOf(b);
        double total = (A.multiply(B)).doubleValue();
        BigDecimal value = BigDecimal.valueOf(total + 0.00000001);
        value = value.setScale(2, BigDecimal.ROUND_HALF_UP);
        total = value.doubleValue();
        return total;
    }
}

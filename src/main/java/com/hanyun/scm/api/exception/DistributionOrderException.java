package com.hanyun.scm.api.exception;

/**
 * Description: 配送单异常类
 * <p>
 * Date 2017-03-07
 * Time 14:39
 *
 * @author 1007661792@qq.com
 **/
public class DistributionOrderException extends Exception {

    public DistributionOrderException(){super();}

    public DistributionOrderException(String message){super(message);}

    public DistributionOrderException(String message, Throwable cause){super(message,cause);}

    public DistributionOrderException(Throwable cause){super(cause);}
}

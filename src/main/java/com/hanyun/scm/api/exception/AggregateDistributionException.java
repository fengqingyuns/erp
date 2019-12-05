package com.hanyun.scm.api.exception;

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
 * AggregateDistributionException
 * Date: 2017/3/15
 * Time: 上午10:13
 *
 * @author tianye@hanyun.com
 */
public class AggregateDistributionException extends Exception{
    public AggregateDistributionException() {
        super();
    }

    public AggregateDistributionException(String message) {
        super(message);
    }

    public AggregateDistributionException(String message, Throwable cause) {
        super(message, cause);
    }

    public AggregateDistributionException(Throwable cause) {
        super(cause);
    }
}

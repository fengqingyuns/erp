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
 * DisassemblyException
 * Date: 2017/3/2
 * Time: 下午4:15
 *
 * @author tianye@hanyun.com
 */
public class DisassemblyException extends Exception{
    public DisassemblyException() {
        super();
    }

    public DisassemblyException(String message) {
        super(message);
    }

    public DisassemblyException(String message, Throwable cause) {
        super(message, cause);
    }

    public DisassemblyException(Throwable cause) {
        super(cause);
    }

}

package com.hanyun.scm.api.dao;

import com.hanyun.scm.api.domain.IdGenerateSeq;
import com.hanyun.scm.api.domain.result.DateResult;
import org.springframework.stereotype.Repository;

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
 * IdGenerateSeqDao
 * Date: 2016/12/15
 * Time: 上午10:24
 *
 * @author tianye@hanyun.com
 */
@SuppressWarnings("UnnecessaryInterfaceModifier")
@Repository
public interface IdGenerateSeqDao {

    public DateResult getTime(String tableName);

    public void truncate(String tableName);

    public void generateSeq(IdGenerateSeq idGenerateSeq);
}

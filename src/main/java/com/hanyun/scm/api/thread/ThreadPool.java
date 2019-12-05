package com.hanyun.scm.api.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
 * ThreadPool
 * Date: 2017/6/18
 * Time: 下午3:36
 *
 * @author tianye@hanyun.com
 */
public class ThreadPool {
    static private ThreadPool threadFixedPool = new ThreadPool(17);
	private ExecutorService executor;
	static public ThreadPool getFixedInstance() { 
	return threadFixedPool; 
	}
	private ThreadPool(int num) { 
	           executor = Executors.newFixedThreadPool(num);
	}
	public void execute(Runnable r) { 
	           executor.execute(r); 

	}

}
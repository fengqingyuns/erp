package com.hanyun.scm.api.domain.request;

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
 * BaseRequest
 * Date: 2016/10/25
 * Time: 下午8:15
 *
 * @author tianye@hanyun.com
 */
public abstract class BaseRequest {
    private Integer pageSize;   //每页条数
    private Integer pageNum;    //页码
    private Integer recordNo;   //起始记录数
    private Integer withPage;   //是否分页0、是 1、否
    private Integer count;      //数据总条数

    public Integer getCount() {
        return count;
    }

    /**
     * 设置数据总条数,如果分页则必须调用此方法
     * @param count 数据总条数
     */
    public void setCount(Integer count) {
        this.count = count;
        dealWithRecordAndPage(count);
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(Integer recordNo) {
        this.recordNo = recordNo;
    }

    public Integer getWithPage() {
        return withPage;
    }

    public void setWithPage(Integer withPage) {
        this.withPage = withPage;
    }

    public void dealWithPage(int count) {
        if (withPage == null || withPage == 0) {
            setPageSize(pageSize == null ? 10 : pageSize);
            if (count > 0 && pageNum != null) {
                int maxPage = count%pageSize==0 ? (count/pageSize) : (count/pageSize+1);
                if (maxPage<pageNum) {
                    setPageNum(maxPage);
                }
            }
            setRecordNo((pageNum == null || pageNum == 0) ? 0 : (pageNum - 1)*pageSize);
        }
    }

    /**
     * 处理分页,如果页码超过实际最大页码数,则查询最后一页数据
     *
     * @param count 数据总条数
     */
    private void dealWithRecordAndPage(int count) {
        if (pageNum==null || pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize != null && pageSize > 0) {
            int maxPage = count%pageSize==0 ? (count/pageSize) : (count/pageSize+1);
            if (maxPage<pageNum) {
                setPageNum(maxPage);
            }
            setRecordNo((pageNum == null || pageNum <= 0) ? 0 : (pageNum - 1)*pageSize);
        }
    }
}

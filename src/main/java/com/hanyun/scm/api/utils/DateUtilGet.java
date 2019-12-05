package com.hanyun.scm.api.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtilGet {

    private static final String DEFAULT_DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String TIME_DATA_FORMAT = "yyyy-MM-dd";

    private static final String YEAR_FORMAT = "yyyyMMdd";

    /**
     * 获取时间 yyyy-mm-dd
     * @return date
     */
    public static String getTime(int amount) {
        return applicationTime(amount, TIME_DATA_FORMAT);
    }

    /**
     * 获取当前时间yyyy-MM-dd HH:mm:ss
     * @return date
     */
    public static String getNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATA_FORMAT);
        return sdf.format(new Date());
    }

    /**
     * 获取时间 yyyyMMdd
     * @return date
     */
    public static String getYear(int amount) {
        return applicationTime(amount, YEAR_FORMAT);
    }

    /**
     * 公用方法
     * @param amout 天数
     * @param format 格式化
     * @return String
     */
    private static String applicationTime(int amout, String format){
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        calendar.setTime(date);
        calendar.add(calendar.DAY_OF_MONTH, amout);
        return sdf.format(calendar.getTime());
    }

    /**
     * 格式化时间 yyyy-MM-dd HH:mm:ss
     * @param date 日期
     * @return String
     */
    public static String formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATA_FORMAT);
        return sdf.format(date);
    }

    /**
     * 获取Calendar通用方法
     * @param year 年
     * @param month 月
     * @return Calendar
     */
    private static Calendar applicationCalendar(Integer year, Integer month){
        if(month > 12){
            month = 12;
        } else if(month < 1){
            month = 1;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        return cal;
    }

    /**
     * 获取某年某月的第一天
     * @param year 年
     * @param month 月
     * @return string
     */
    public static String getFirstDayForMonth(Integer year, Integer month){
        Calendar cal = applicationCalendar(year, month);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        return new SimpleDateFormat(TIME_DATA_FORMAT).format(cal.getTime()) + " 00:00:00";
    }

    /**
     * 获取某年某月的最后一天
     * @param year 年
     * @param month 月
     * @return string
     */
    public static String getLastDayOfMonth(Integer year, Integer month){
        Calendar cal = applicationCalendar(year, month);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return new SimpleDateFormat(TIME_DATA_FORMAT).format(cal.getTime()) + " 23:59:59";
    }

    public static String getYearAndMonth(){
        return new SimpleDateFormat("yyyy/MM").format(new Date());
    }
}


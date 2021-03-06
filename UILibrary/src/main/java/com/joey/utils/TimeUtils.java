package com.joey.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimeUtils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-8-24
 */
public class TimeUtils {

    /**
     * 日期formater
     **/
    public static final String FORMATTER_DATE_AND_TIME0 = "MM/dd/yyyy HH:mm:ss";
    public static final String FORMATTER_DATE_AND_TIME1 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMATTER_DATE_AND_TIME2 = "dd/MM/yyyy HH:mm:ss";
    public static final String FORMATTER_DATE_AND_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMATTER_DATE = "yyyy-MM-dd";
    public static final String FORMATTER_TIME_ = "HH-mm-ss";
    public static final String FORMATTER_TIME = "HH:mm:ss";
    public static final String FORMATTER_DATE_AND_TIME_CH = "yyyy年MM月dd日 HH:mm:ss EEEE";
    public static final String FORMATTER_YEAR_AND_MONTH_CH = "yyyy年MM月";
    public static final String FORMATTER_YEAR_AND_MONTH_DAY = "yyyy年MM月dd日";

    private TimeUtils() {
        throw new AssertionError();
    }


    /**
     * long time to string, format
     *
     * @param timeInMillis
     * @param format
     * @return
     */
    public static String getTime(long timeInMillis, String format) {
        SimpleDateFormat formatDate = new SimpleDateFormat(format);
        return formatDate.format(new Date(timeInMillis));
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format
     *
     * @param
     * @return
     */
    public static String getCurrentTimeInString(String format) {
        return getTime(getCurrentTimeInLong(), format);
    }

    public static long convertDateStrToMillis(String dateStr, String format) {
        SimpleDateFormat formatDate = new SimpleDateFormat(format);
        try {
            Date date = formatDate.parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String convertMillisToHHmmss(long time, String timerFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(timerFormat);
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String convertDateToStr(Date date, String format) {
        SimpleDateFormat formatDate = new SimpleDateFormat(format);
        return formatDate.format(date);
    }

    public static Date convertStrToDate(String dateStr, String format) {
        SimpleDateFormat formatDate = new SimpleDateFormat(format);
        try {
            Date date = formatDate.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}

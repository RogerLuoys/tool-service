package com.luoys.upgrade.toolservice.service.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {


    private static Date getToday(int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTime();
    }

    public static Date getToday() {
        return getToday(0, 0, 30);
    }

    /**
     * 根据日期取得星期几
     *
     * @param date 日期
     * @return 1-周一，2-周二，3-周三，4-周四，5-周五，6-周六，7-周日
     */
    public static Integer getWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekNumber == 0) {
            weekNumber = 7;
        }
        return weekNumber;
    }

    /**
     * 获取指定日期所在周，周一到周日的日期
     *
     * @param date 目标日期，为空则取当前日期
     * @param targetWeek 目标周，1-周一，2-周二，3-周三，4-周四，5-周五，6-周六，7-周日
     * @return 周一对应的日期的0点
     */
    public static Date getWeekDate(Date date, int targetWeek) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekNumber == 0) {
            weekNumber = 7;
        }
        calendar.add(Calendar.DATE, targetWeek - weekNumber);
        return calendar.getTime();
    }

    /**
     * 获取指定日期所在周，周一的开始时间
     *
     * @param date 目标日期，为空则取当前日期
     * @return 周一对应的日期的0点
     */
    public static Date getMonday(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        calendar.add(Calendar.DATE, 1 - day_of_week);
        return calendar.getTime();
    }

    /**
     * 获取指定日期所在周，周日的结束时间
     *
     * @param date 目标日期，为空则取当前日期
     * @return 周日对应日期的23点
     */
    public static Date getSunday(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        calendar.add(Calendar.DATE, 7 - day_of_week);
        return calendar.getTime();
    }

    /**
     * 获取一天的开始
     *
     * @param date 日期
     * @return 传入日期当天的0点
     */
    public static Date getDayStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取一天的结尾
     *
     * @param date 日期
     * @return 传入日期当天的23点
     */
    public static Date getDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取一年的开始
     *
     * @return 传入一年开始的0点
     */
    public static Date getYearStart() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        calendar.set(Calendar.YEAR, currentYear);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Date 转 yyyy-MM-dd HH:mm:ss类型的字符串
     *
     * @param date 需转换的日期
     * @return 转换后的字符串
     */
    public static String dateFormat(Date date) {
        return date == null ? "空日期" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

//    /**
//     * 设置时间
//     *
//     * @param year   年
//     * @param month  月（1-12）
//     * @param day    日（1-31）
//     * @param hour   时（0-23）
//     * @param minute 分（0-59）
//     * @param second 秒（0-59）
//     * @return -
//     */
//    public Date setDate(int year, int month, int day, int hour, int minute, int second) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, year);
//        //calendar月份从0开始算！
//        calendar.set(Calendar.MONTH, month - 1);
//        calendar.set(Calendar.DAY_OF_MONTH, day);
//        calendar.set(Calendar.HOUR_OF_DAY, hour);
//        calendar.set(Calendar.MINUTE, minute);
//        calendar.set(Calendar.SECOND, second);
//        return calendar.getTime();
//    }
//
//    /**
//     * 设置时间，时分秒都默认为1
//     *
//     * @param year  年
//     * @param month 月（1-12）
//     * @param day   日（1-31）
//     * @return -
//     */
//    public Date setDate(int year, int month, int day) {
//        return setDate(year, month, day, 1, 1, 1);
//    }
//
//    /**
//     * 设置时间，时分秒都默认为0\0\30
//     *
//     * @param year  年
//     * @param month 月（1-12）
//     * @param day   日（1-31）
//     * @return -
//     */
//    public Date setDateStart(int year, int month, int day) {
//        return setDate(year, month, day, 0, 0, 30);
//    }
//
//    /**
//     * 设置时间，时分秒都默认为23/59/30
//     *
//     * @param year  年
//     * @param month 月（1-12）
//     * @param day   日（1-31）
//     * @return -
//     */
//    public Date setDateEnd(int year, int month, int day) {
//        return setDate(year, month, day, 23, 59, 30);
//    }
}

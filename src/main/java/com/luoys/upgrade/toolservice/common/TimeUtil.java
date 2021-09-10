package com.luoys.upgrade.toolservice.common;

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
     * @return 0-周日，1-周一，2-周二，3-周三，4-周四，5-周五，6-周六
     */
    public static Integer getWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 获取指定日期所在周，周一的日期
     * @param date 目标日期，为空则取当前日期
     * @return 周一对应的日期
     */
    public static Date getMonday(Date date) {
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
        }
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, 1 - day_of_week);
        return c.getTime();
    }

    /**
     * 获取指定日期所在周，周日的日期
     * @param date 目标日期，为空则取当前日期
     * @return 周日对应的日期
     */
    public static Date getSunday(Date date) {
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
        }
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, 7 - day_of_week);
        return c.getTime();
    }

    /**
     * 默认当天为周日，只能用于按周循环的模板
     *
     * @param cycle task表里的周cycle
     * @return 以周为周期的每日任务开始时间
     */
    public static Date getWeekCycleStartTime(long cycle) {
        if (cycle < 1 || cycle > 7) {
            return null;
        }
        // 获取当天零点（有精度丢失）
        Date todayStart = getToday(0, 0, 30);
        return new Date(todayStart.getTime() + 24 * 60 * 60 * 1000 * cycle);
    }

    /**
     * 默认当天为周日，只能用于按周循环的模板
     *
     * @param cycle task表里的周cycle
     * @return 以周为周期的每日任务开始时间
     */
    public static Date getWeekCycleStartTime(String cycle) {
        return getWeekCycleStartTime(Long.parseLong(cycle));
    }

    /**
     * 默认当天为周日，只能用于按周循环的模板
     *
     * @param cycle task表里的周cycle
     * @return 以周为周期的每日任务结束时间
     */
    public static Date getWeekCycleEndTime(long cycle) {
        if (cycle < 1 || cycle > 7) {
            return null;
        }
        // 获取当天半夜（有精度丢失）
        Date todayEnd = getToday(23, 59, 30);
        return new Date(todayEnd.getTime() + 24 * 60 * 60 * 1000 * cycle);
    }

    /**
     * 默认当天为周日，只能用于按周循环的模板
     *
     * @param cycle task表里的周cycle
     * @return 以周为周期的每日任务结束时间
     */
    public static Date getWeekCycleEndTime(String cycle) {
        return getWeekCycleEndTime(Long.parseLong(cycle));
    }


    /**
     * 设置时间
     *
     * @param year   年
     * @param month  月（1-12）
     * @param day    日（1-31）
     * @param hour   时（0-23）
     * @param minute 分（0-59）
     * @param second 秒（0-59）
     * @return -
     */
    public Date setDate(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        //calendar月份从0开始算！
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * 设置时间，时分秒都默认为1
     *
     * @param year  年
     * @param month 月（1-12）
     * @param day   日（1-31）
     * @return -
     */
    public Date setDate(int year, int month, int day) {
        return setDate(year, month, day, 1, 1, 1);
    }

    /**
     * 设置时间，时分秒都默认为0\0\30
     *
     * @param year  年
     * @param month 月（1-12）
     * @param day   日（1-31）
     * @return -
     */
    public Date setDateStart(int year, int month, int day) {
        return setDate(year, month, day, 0, 0, 30);
    }

    /**
     * 设置时间，时分秒都默认为23/59/30
     *
     * @param year  年
     * @param month 月（1-12）
     * @param day   日（1-31）
     * @return -
     */
    public Date setDateEnd(int year, int month, int day) {
        return setDate(year, month, day, 23, 59, 30);
    }
}

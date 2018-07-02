package com.jay.util;

import jodd.time.JulianDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间工具类
 */
public class DateUtils {

    private static long offset = 0;

    public static void main(String args[]) {
//        String format = "yyyy-MM-dd HH:mm:ss";
//        String warning = "[2016-06-09 23:21:43] 大数据打分模型接口报错，申请ID：1062950 - LogKey: 20160609232143678";
//        System.out.println(StringUtils.substring(warning, 1, 1 + format.length()));
        System.out.println(DateUtils.toCompactDateString(DateUtils.addDays(DateUtils.getNowDate(), -1)));
        System.out.println(DateUtils.toCompactDateString());

    }


    private static ThreadLocal<Map<String, SimpleDateFormat>> dateFormatMap = new ThreadLocal<Map<String, SimpleDateFormat>>() {
        @Override
        public Map<String, SimpleDateFormat> initialValue() {
            return new HashMap<String, SimpleDateFormat>();
        }
    };


    private static SimpleDateFormat getSimpleDateFormat(String format) {
        Map<String, SimpleDateFormat> map = dateFormatMap.get();
        SimpleDateFormat simpleDateFormat = map.get(format);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(format);
            map.put(format, simpleDateFormat);
        }
        return simpleDateFormat;
    }

    /**
     * yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String toDateString(Date date) {
        if (date == null) {
            return "";
        }
        return format(date, "yyyy-MM-dd");
    }

    public static String toDateString() {
        return toDateString(getNowDate());
    }

    /**
     * yyyyMMdd
     *
     * @param date
     * @return
     */
    public static String toCompactDateString(Date date) {
        return format(date, "yyyyMMdd");
    }

    public static String toyyyyMMDateString() {
        return format(getNowDate(), "yyyy-MM");
    }

    public static String toyyyyMMDateString(Date date) {
        return format(date, "yyyy-MM");
    }

    public static String toCompactDateString() {
        return toCompactDateString(getNowDate());
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String toTimeString(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String toTimeString() {
        return toTimeString(getNowDate());
    }

    /**
     * yyyyMMddHHmmss
     *
     * @param date
     * @return
     */
    public static String toCompactTimeString(Date date) {
        return format(date, "yyyyMMddHHmmss");
    }

    public static String toCompactTimeString() {
        return toCompactTimeString(getNowDate());
    }

    public static Date parseFromDateString(String date) {
        return parse(date, "yyyy-MM-dd");
    }

    public static Date parseFromCompactDateString(String date) {
        return parse(date, "yyyyMMdd");
    }

    public static Date parseFromTimeString(String date) {
        return parse(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date parseFromCompactTimeString(String date) {
        return parse(date, "yyyyMMddHHmmss");
    }

    public static String format(Date date, String format) {
        if (date == null)
            return "";
        return getSimpleDateFormat(format).format(date);
    }

    public static Date parse(String date, String format) {
        try {
            return getSimpleDateFormat(format).parse(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int daysBetweenTwoDate(Date d1, Date d2) {
        return (int) Math.ceil(Math.abs(d1.getTime() - d2.getTime()) / (1000d * 60 * 60 * 24));
    }

    /**
     * 计算日期差值, if d1.before(d2) then 返回负值
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int daysSpanTwoDate(Date d1, Date d2) {

        long start = d1.getTime();
        long end = d2.getTime();

        return new Long((start - end) / (1000 * 60 * 60 * 24)).intValue();
    }

    public static int hoursBetweenTwoDate(Date d1, Date d2) {
        return (int) Math.ceil(Math.abs(d1.getTime() - d2.getTime()) / (1000d * 60 * 60 * 24));
    }

    public static Date trim(Date date) {
        String format = "yyyy-MM-dd";
        return parse(format(date, format), format);
    }

    public static boolean isSameDay(Date date1, Date date2) {
        return toDateString(date1).equals(toDateString(date2));
    }

    public static void setOffset(long offset) {
        DateUtils.offset = offset;
    }

    public static long getOffset() {
        return offset;
    }

    public static Date getNowDate() {
        if (offset != 0 && !WebConfig.isProd()) {
            return new Date(System.currentTimeMillis() + offset);
        }
        return new Date();
    }

    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    public static Date setDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, day);
        return calendar.getTime();
    }

    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    public static Date getDate(long time) {
        return new Date(time);
    }

    public static Date addDays(Date date, int days) {
        return add(date, Calendar.DATE, days);
    }

    public static Date addMonth(Date date, int month) {
        return add(date, Calendar.MONTH, month);
    }

    public static Date addMinute(Date date, int minute) {
        return add(date, Calendar.MINUTE, minute);
    }

    public static Date addMinute(int minute) {
        return addMinute(getNowDate(), minute);
    }

    public static Date addSecond(Date date, int second) {
        return add(date, Calendar.SECOND, second);
    }

    public static Date addHour(Date date, int minute) {
        return add(date, Calendar.HOUR_OF_DAY, minute);
    }

    public static Date addYear(Date date, int year) {
        return add(date, Calendar.YEAR, year);
    }

    public static Date add(Date date, int field, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, value);
        return calendar.getTime();
    }

    public static Date trimHour(Date date) {
        return parse(format(date, "yyyyMMddHH"), "yyyyMMddHH");
    }

    public static int yearsBetweenDates(Date newDate, Date oldDate) {

        Calendar c = Calendar.getInstance();
        c.setTime(newDate);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        c.setTime(oldDate);
        int yearOld = c.get(Calendar.YEAR);
        int monthOld = c.get(Calendar.MONTH);

        if (year == yearOld) {
            return 0;
        }

        int months = 12 * (year - yearOld) + month - monthOld;
        if (months < 12) {
            return 0;
        }

        return year - yearOld;

    }

    public static int monthBetweenDates(Date newDate, Date oldDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(newDate);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        c.setTime(oldDate);
        int yearOld = c.get(Calendar.YEAR);
        int monthOld = c.get(Calendar.MONTH);

        int result;
        if (year == yearOld) {
            result = month - monthOld;//两个日期相差几个月，即月份差
        } else {
            result = 12 * (year - yearOld) + month - monthOld;//两个日期相差几个月，即月份差
        }
        return result;
    }

    public static boolean isDateType(String date){
        String format = "yyyyMMddHHmmss";
        try {
            getSimpleDateFormat(format).parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public static boolean isDateType(String date, String format){
        try {
            getSimpleDateFormat(format).parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public static Date nextDueDay(Date date) {
        if (date == null) {
            return DateUtils.getNowDate();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }
}

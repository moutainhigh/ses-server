package com.redescooter.ses.tool.utils;

import com.redescooter.ses.api.common.constant.DateConstant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * description: DateUtil 时间工具类
 * author: jerry.li
 * create: 2019-05-17 15:48
 */

public class DateUtil {

    /**
     * 开始时间
     */
    public static final Long Start = System.currentTimeMillis();
    /**
     * 结束时间
     */
    public static final Long End = System.currentTimeMillis();
    /**
     * 缺省的日期显示格式： yyyy-MM-dd
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 缺省的日期显示格式： yyyy/MM/dd
     */
    public static final String DEFAULT_DATE_FORMAT1 = "yyyy/MM/dd";
    /**
     * 缺省的日期显示格式： yyyy-MM
     */
    public static final String DEFAULT_YYMM_FORMAT = "yyyy-MM";
    /**
     * 缺省的日期时间显示格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 缺省的日期显示格式： HH:mm:ss
     */
    public static final String DEFAULT_TIME_FORMAT="HH:mm:ss";
    /**
     * 缺省的日期时间显示格式：yyyy-MM-dd HH:mm:ss:sss
     */
    public static final String YMDHMSS = "yyyy-MM-dd HH:mm:ss:sss";
    /**
     * 字符串时间
     */
    public static final String YMDHMS = "yyyyMMddHHmmss";

    /**
     * 默认标准时区
     */
    public static final String UTC = "UTC";
    /**
     * 当前时间
     */
    public static Date DATE_NOW = new Date();

    private static final String DEFAULT_CONVERT_PATTERN = "yyyyMMddHHmmssSSS";

    /**
     * 私有构造方法，禁止对该类进行实例化
     */
    private DateUtil() {
    }

    /**
     * 获取当前时间字符串(默认格式:yyyyMMddHHmmssSSS)
     *
     * @return
     */
    public static String getCurrentTimeStrDefault() {
        return getCurrentTimeStr(DEFAULT_CONVERT_PATTERN);
    }

    /**
     * 获取指定时间字符串(默认格式:yyyyMMddHHmmssSSS)
     *
     * @param date
     * @return
     */
    public static String getTimeStrDefault(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_CONVERT_PATTERN);
        return dateFormat.format(date);
    }

    /**
     * 获取当前时间字符串
     *
     * @param pattern 转换格式
     * @return
     */
    public static String getCurrentTimeStr(String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(new Date());
    }

    /**
     * 获取指定时间字符串
     *
     * @param date
     * @return
     */
    public static String getTimeStr(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * 判断时间字符串是否为默认格式
     *
     * @param dateTimeStr
     * @return
     */
    public static boolean isValidDefaultFormat(String dateTimeStr, String pattern) {
        if (pattern == null) {
            pattern = DEFAULT_CONVERT_PATTERN;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            dateFormat.parse(dateTimeStr);
            return true;
        } catch (Exception e) {
            // 如果抛出异常，说明格式不正确
            return false;
        }
    }


    /**
     * 获取开始时间
     *
     * @return 当前日期时间
     */
    public static Long Start() {
        return Long.valueOf(Start);
    }

    /**
     * 获取结束时间
     *
     * @return 当前日期时间
     */
    public static Long End() {
        return Long.valueOf(End);
    }

    /**
     * 开始时间与结束时间差
     *
     * @return 时间差
     */
    public static Long dif(Long End, Long Start) {
        return (End - Start);
    }

    /**
     * 得到系统当前日期时间
     *
     * @return 当前日期时间
     */
    public static Date getNow() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 得到用缺省方式格式化的当前日期
     *
     * @return 当前日期
     */
    public static String getDate() {
        return getDateTime(DEFAULT_DATE_FORMAT);
    }

    /**
     * 得到用缺省方式格式化的当前日期及时间
     *
     * @return 当前日期及时间
     */
    public static String getDateTime() {
        return getDateTime(DEFAULT_DATETIME_FORMAT);
    }


    /**
     * 得到系统当前日期及时间，并用指定的方式格式化
     *
     * @param pattern 显示格式
     * @return 当前日期及时间
     */
    public static String getDateTime(String pattern) {
        Date datetime = Calendar.getInstance().getTime();
        return getDateTime(datetime, pattern);
    }

    /**
     * 得到用指定方式格式化的日期
     *
     * @param date    需要进行格式化的日期
     * @param pattern 显示格式
     * @return 日期时间字符串
     */
    public static String getDateTime(Date date, String pattern) {
        if (null == pattern || "".equals(pattern)) {
            pattern = DEFAULT_DATETIME_FORMAT;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * 得到当前年份
     *
     * @return 当前年份
     */
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 得到当前月份
     *
     * @return 当前月份
     */
    public static int getCurrentMonth() {
        // 用get得到的月份数比实际的小1，需要加上
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 得到当前日
     *
     * @return 当前日
     */
    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DATE);
    }

    public static Date getCurrentToDay() {
        return parse(getDate(), DEFAULT_DATE_FORMAT);
    }

    /**
     * 取得当前日期以后若干天的日期。如果要得到以前的日期，参数用负数。 例如要得到上星期同一天的日期，参数则为-7
     *
     * @param days 增加的日期数
     * @return 增加以后的日期
     */
    public static Date addDays(int days) {
        return add(getNow(), days, Calendar.DATE);
    }

    /**
     * 取得指定日期以后若干天的日期。如果要得到以前的日期，参数用负数。
     *
     * @param date 基准日期
     * @param days 增加的日期数
     * @return 增加以后的日期
     */
    public static Date addDays(Date date, int days) {
        return add(date, days, Calendar.DATE);
    }

    /**
     * 取得当前日期以后某月的日期。如果要得到以前月份的日期，参数用负数。
     *
     * @param months 增加的月份数
     * @return 增加以后的日期
     */
    public static Date addMonths(int months) {
        return add(getNow(), months, Calendar.MONTH);
    }

    /**
     * 取得指定日期以后某月的日期。如果要得到以前月份的日期，参数用负数。 注意，可能不是同一日子，例如2003-1-31加上一个月是2003-2-28
     *
     * @param date   基准日期
     * @param months 增加的月份数
     * @return 增加以后的日期
     */
    public static Date addMonths(Date date, int months) {
        return add(date, months, Calendar.MONTH);
    }

    /**
     * 内部方法。为指定日期增加相应的天数或月数
     *
     * @param date   基准日期
     * @param amount 增加的数量
     * @param field  增加的单位，年，月或者日
     * @return 增加以后的日期
     */
    private static Date add(Date date, int amount, int field) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.add(field, amount);

        return calendar.getTime();
    }

    /**
     * 计算两个日期相差天数。 用第一个日期减去第二个。如果前一个日期小于后一个日期，则返回负数
     *
     * @param one 第一个日期数，作为基准
     * @param two 第二个日期数，作为比较
     * @return 两个日期相差天数
     */
    public static long diffDays(Date one, Date two) {
        return (one.getTime() - two.getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     * 计算两个日期相差天数。 用第一个日期减去第二个。如果前一个日期小于后一个日期，则返回负数
     *
     * @param one 第一个日期数，作为基准
     * @param two 第二个日期数，作为比较
     * @return 两个日期相差天数
     */
    public static long diffDay(Date one, Date two) {
        Calendar calendar = Calendar.getInstance();

        // 得到第一个日期的年分和月份数
        calendar.setTime(one);
        int oneday = calendar.get(Calendar.DAY_OF_YEAR);

        // 得到第二个日期的年份和月份
        calendar.setTime(two);
        int twoday = calendar.get(Calendar.DAY_OF_YEAR);

        return twoday - oneday;
    }

    /**
     * 计算两个日期相差月份数 如果前一个日期小于后一个日期，则返回负数
     *
     * @param one 第一个日期数，作为基准期数，作为比较
     * @param two 第二个日
     * @return 两个日期相差月份数
     */
    public static int diffMonths(Date one, Date two) {

        Calendar calendar = Calendar.getInstance();

        // 得到第一个日期的年分和月份数
        calendar.setTime(one);
        int yearOne = calendar.get(Calendar.YEAR);
        int monthOne = calendar.get(Calendar.MONDAY);

        // 得到第二个日期的年份和月份
        calendar.setTime(two);
        int yearTwo = calendar.get(Calendar.YEAR);
        int monthTwo = calendar.get(Calendar.MONDAY);

        return (yearOne - yearTwo) * 12 + (monthOne - monthTwo);
    }

    /**
     * 将一个字符串用给定的格式转换为日期类型。 <br>
     * 注意：如果返回null，则表示解析失败
     *
     * @param datestr 需要解析的日期字符串
     * @param pattern 日期字符串的格式，默认为“yyyy-MM-dd”的形式
     * @return 解析后的日期
     */
    public static Date parse(String datestr, String pattern) {
        Date date = null;

        if (null == pattern || "".equals(pattern)) {
            pattern = DEFAULT_DATE_FORMAT;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            date = dateFormat.parse(datestr);
        } catch (ParseException e) {
            //
        }

        return date;
    }

    /**
     * 日期转成指定类型字符串
     *
     * @param date
     * @param pattern
     * @return
     * @author 000386
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            if (null == pattern || "".equals(pattern)) {
                pattern = DEFAULT_DATETIME_FORMAT;
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.format(date);

        }
        return null;
    }

    /**
     * 返回本月的最后一天
     *
     * @return 本月最后一天的日期
     */
    public static Date getMonthLastDay() {
        return getMonthLastDay(getNow());
    }

    /**
     * 返回给定日期中的月份中的最后一天
     *
     * @param date 基准日期
     * @return 该月最后一天的日期
     */
    public static Date getMonthLastDay(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 将日期设置为下一月第一天
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 1);

        // 减去1天，得到的即本月的最后一天
        calendar.add(Calendar.DATE, -1);

        return calendar.getTime();
    }

    /**
     * 判断时间大小
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDate(Date date1, Date date2) {

        if (null == date1 && date2 == null) {
            return 0;
        }

        try {
            Date dt1 = DateUtil.parse(DateUtil.getDateTime(date1, DateConstant.DEFAULT_DATETIME_FORMAT),
                    DateConstant.DEFAULT_DATETIME_FORMAT);
            Date dt2 = DateUtil.parse(DateUtil.getDateTime(date2, DateConstant.DEFAULT_DATETIME_FORMAT),
                    DateConstant.DEFAULT_DATETIME_FORMAT);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {

        }
        return 0;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);

            return Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 计算date增加days天后的日期
     *
     * @param days
     * @return
     */
    @SuppressWarnings("static-access")
    public static Date dateAddDays(Date date, int days) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 计算时间差返回秒
     *
     * @param bTime
     * @param sTime
     * @return
     */
    public static Long diffTime(Date bTime, Date sTime) {
        return (bTime.getTime() - sTime.getTime()) / 1000;
    }


    /**
     * 得到完整的时间戳，格式:yyyy-MM-dd HH:mm:ss.SSS(年-月-日 时:分:秒.毫秒)
     *
     * @return 完整的时间戳
     */
    public static String getFullTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

    /**
     * 得到简单的时间戳，格式:yyyyMMdd(年月日)
     *
     * @return 简单的时间戳
     */
    public static String getSimpleTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    /**
     * 根据指定的格式得到时间戳
     *
     * @param pattern 指定的格式
     * @return 指定格式的时间戳
     */
    public static String getTimeStampByPattern(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }

    /**
     * 得到当前日期格式化后的字符串，格式：yyyy-MM-dd(年-月-日)
     *
     * @return 当前日期格式化后的字符串
     */
    public static String getTodayStr() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    /**
     * 时间戳，格式:yyyy-MM-dd HH:mm:ss(年-月-日 时：分：秒)
     *
     * @return 简单的时间戳
     */
    public static String getDateTimeStamp(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }


    /**
     * @param
     * @return java.lang.String
     * @Description 年月日时分秒毫秒
     * @author jerry
     * @date 2018/8/23 14:45
     */
    public static String YMDHMS() {
        return getDateTime(YMDHMSS);
    }


    /**
     * 时间戳，格式:yyyy-MM-dd HH:mm(年-月-日 时：分)
     *
     * @return 简单的时间戳
     */
    public static String getYmdhm(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }

    /**
     * 加指定分钟数
     */
    public static String payDesignationTime(int minutes) {
        long etime1 = System.currentTimeMillis() + minutes * 60 * 1000;//延时函数，单位毫秒，这里是延时了30分钟
        SimpleDateFormat time2 = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
        String etime = time2.format(new Date(etime1));
        return etime;
    }

    /**
     * 时分
     *
     * @param str
     * @return
     */
    public static Date changeYMDHM(String str) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }



    /*————————————————————————————————————时间转——————————————*/

    /**
     * @param time
     * @return java.utils.Date
     * @Title stringToDate
     * @Description String转date
     * @author Jerry
     * @date 2018/12/28 12:29
     */
    public static Date stringToDate(String time) {

        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取当前系统时区
     *
     * @return
     */
    public static String getTimeZone() {
        Calendar cal = Calendar.getInstance();
        int offset = cal.get(Calendar.ZONE_OFFSET);
        cal.add(Calendar.MILLISECOND, -offset);
        Long timeStampUTC = cal.getTimeInMillis();
        Long timeStamp = System.currentTimeMillis();
        Long timeZone = (timeStamp - timeStampUTC) / (1000 * 3600);
        return String.valueOf(timeZone);

    }

    /**
     * 比较两个string 类型的时间格式（时：分：秒）大小 精确到秒
     *
     * @param timeA
     * @param timeB
     * @return
     */
    public static Boolean timeComparison(String timeA, String timeB) {
        try {
            String[] array1 = timeA.split(":");
            int total1 = Integer.valueOf(array1[0]) * 3600 + Integer.valueOf(array1[1]) * 60 + Integer.valueOf(array1[2]);
            String[] array2 = timeB.split(":");
            int total2 = Integer.valueOf(array2[0]) * 3600 + Integer.valueOf(array2[1]) * 60 + Integer.valueOf(array2[2]);
            return total1 - total2 > 0 ? Boolean.TRUE : Boolean.FALSE;
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    /**
     * .Description:根据字符日期返回星期几
     * .Author:jerry
     * .@Date: 2019/07/31
     */
    public static String getWeek(String dateTime) {
        String week = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dateTime);
            SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
            week = dateFm.format(date);
            week = week.replaceAll("星期", "周");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return week;
    }

    /**
     * 获取过去N天内的日期数组
     *
     * @param intervals intervals天内
     * @return 日期数组
     */
    public static ArrayList<String> getDayList(int intervals, String dateFormat) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        for (int i = intervals - 1; i >= 0; i--) {
            pastDaysList.add(getPastDate(i, dateFormat));
        }
        return pastDaysList;
    }

    /**
     * 获取过去N天内的日期数组
     *
     * @param intervals intervals天内
     * @return 日期数组
     */
    public static ArrayList<String> getDayList(Date date, int intervals, String dateFormat) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        for (int i = intervals - 1; i >= 0; i--) {
            pastDaysList.add(getTimeStr(getFrontDay(date, i), dateFormat == null ? DEFAULT_DATE_FORMAT : dateFormat));
        }

        return pastDaysList;
    }

    /**
     * 返回某个日期前几天的日期
     *
     * @param date
     * @param i
     * @return
     */
    public static Date getFrontDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - i);
        return cal.getTime();
    }

    /**
     * 返回某个日期下几天的日期
     *
     * @param date
     * @param i
     * @return
     */
    public static Date getNextDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + i);
        return cal.getTime();
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past, String dateFormat) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(dateFormat == null ? "yyyy-MM-dd" : dateFormat);
        String result = format.format(today);
        return result;
    }

    /**
     * 获取给定时间内的24小时集合
     *
     * @param datetime
     * @return
     */
    public static ArrayList<String> get24HourList(String datetime) {
        ArrayList<String> strList = new ArrayList<>();
        String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        datetime = datetime == null ? now : datetime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(datetime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 24; i++) {
            calendar.add(Calendar.HOUR, 1);
            Date time = calendar.getTime();
            String format = new SimpleDateFormat("yyyy-MM-dd HH").format(time);
            strList.add(format);
        }
        return strList;
    }

    /**
     * 返回两个时间的差值
     * 单位 秒
     *
     * @param dateA
     * @param dateB
     * @return
     */
    public static Long timeComolete(Date dateA, Date dateB) {
        Long temp = dateB.getTime() - dateA.getTime();
        return temp / 1000;
    }

    /**
     * 时间转换为年月日
     *
     * @param time
     * @return
     */
    public static Date timaConversion(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 计算2个日期之间相差的  以年、月、日为单位，各自计算结果是多少
     * 比如：2011-02-02 到  2017-03-02
     * 以年为单位相差为：6年
     * 以月为单位相差为：73个月
     * 以日为单位相差为：2220天
     *
     * @param fromDate
     * @param toDate
     * @param Date     表示想要的 差值  1 年 2 月 3 天
     * @return
     */
    public static Integer dateCompare(Date fromDate, Date toDate, int Date) {
        Calendar from = Calendar.getInstance();
        from.setTime(fromDate);
        Calendar to = Calendar.getInstance();
        to.setTime(toDate);
        //只要年月
        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);
        int year = toYear - fromYear;
        int month = toYear * 12 + toMonth - (fromYear * 12 + fromMonth);
        int day = (int) ((to.getTimeInMillis() - from.getTimeInMillis()) / (24 * 3600 * 1000));
        switch (Date) {
            case 1:
                return year;
            case 2:
                return month;
            case 3:
                return day;
        }
        return 0;
    }

    /**
     * 加30分钟
     */
    public static String pay30() {
        long etime1 = System.currentTimeMillis() + 30 * 60 * 1000;//延时函数，单位毫秒，这里是延时了30分钟
        SimpleDateFormat time2 = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
        String etime = time2.format(new Date(etime1));
        return etime;
    }

    /**
     * 时间戳转Date
     *
     * @param timeStamp
     * @return
     */
    public static Date timeStampToDate(Long timeStamp,String timeZone) {
        TimeZone tz = TimeZone.getTimeZone(timeZone);
        TimeZone.setDefault(tz);
        return new Date(timeStamp);
    }

    // 数字月份转为英文的月份
    public static String numMonToEngMon(String month) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        Date date = sdf.parse(month);
        sdf = new SimpleDateFormat("MMMMM", Locale.US);
        month = sdf.format(date);
        return month;
    }


    /**
     * @Author Aleks
     * @Description  时间加上小时
     * @Date  2020/8/21 10:05
     * @Param [date, hour]
     * @return 时间
     **/
    public static  Date dateAddHour(Date date, int hour){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date == null) {
            return new Date();
        }
        System.out.println("front:" + format.format(date)); //显示输入的日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);// 24小时制
        date = cal.getTime();
        System.out.println("after:" + format.format(date));  //显示更新后的日期
        cal = null;
        return date;

    }

}

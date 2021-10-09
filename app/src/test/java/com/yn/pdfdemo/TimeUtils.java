package com.yn.pdfdemo;

import com.itextpdf.text.pdf.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.*;

/**
 * @Description
 * @Author yelong
 * @CreateDate 2020-07-12 22:22
 */
public class TimeUtils {

    private static Map<String, DateTimeFormatter> formatterCache = new HashMap<>();

    public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";
    public static final String DEFAULT_MONT_TIME_PATTERN = "HH:mm";
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    public static final String DEFAULT_OPC_DATE_PATTERN = "yyyy年M月";
    public static final String DEFAULT_DATE_MONTH_PATTERN = "yyyy-MM";
    public static final String DEFAULT_DATE_CUSTOM_DAY_PATTERN = "2021-1-1 HH:mm:ss";
    public static final String DEFAULT_DATETIME_PATTERN = DEFAULT_DATE_PATTERN + " " + DEFAULT_TIME_PATTERN;
    public static final String DEFAULT_DATETIME_PATTERN1 = DEFAULT_DATE_PATTERN + "\n" + DEFAULT_TIME_PATTERN;

    private static List<String> patterns = Arrays.asList(
            DEFAULT_DATETIME_PATTERN
            , DEFAULT_DATE_PATTERN
            , DEFAULT_TIME_PATTERN);

    static {
        patterns.forEach(s -> formatterCache.put(s, of(s)));
    }

    private static DateTimeFormatter of(String pattern) {
        return DateTimeFormatter.ofPattern(pattern);
    }

    // 获取时间戳
    public static String timestamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    // 是否为合法的
    public static Optional<LocalDate> toDate(String content) {
        if (StringUtil.isEmpty(content)) {
            return Optional.empty();
        }
        try {
            return Optional.of(LocalDate.parse(content, formatterCache.get(DEFAULT_DATE_PATTERN)));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static String parse(Temporal temporal) {
        if (temporal instanceof LocalDateTime) {
            LocalDateTime dateTime = (LocalDateTime) temporal;
            return formatterCache.get(DEFAULT_DATETIME_PATTERN).format(dateTime);
        }

        if (temporal instanceof LocalDate) {
            LocalDate date = (LocalDate) temporal;
            return formatterCache.get(DEFAULT_DATE_PATTERN).format(date);
        }

        if (temporal instanceof LocalTime) {
            LocalTime time = (LocalTime) temporal;
            return formatterCache.get(DEFAULT_TIME_PATTERN).format(time);
        }

        return null;
    }

    // 将时间对象按照指定格式转换为string
    public static String parse(Temporal temporal, String pattern) {
        if (temporal instanceof LocalDateTime) {
            LocalDateTime dateTime = (LocalDateTime) temporal;
            return DateTimeFormatter.ofPattern(pattern).format(dateTime);
        }

        if (temporal instanceof LocalDate) {
            LocalDate date = (LocalDate) temporal;
            return DateTimeFormatter.ofPattern(pattern).format(date);
        }

        if (temporal instanceof LocalTime) {
            LocalTime time = (LocalTime) temporal;
            return DateTimeFormatter.ofPattern(pattern).format(time);
        }

        return null;
    }

    /**
     * 获取当前时间之前的某一天
     * @param day 往前多少天
     * @return
     */
    public static Date getDownDayDate(int day) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -day);
        date = calendar.getTime();
        return date;
    }


    /**
     * 获取当时间之后的某一天
     * @param day 往后多少天
     * @return
     */
    public static Date getUpDayDate(int day) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +day);
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取几个月前的当天时间
     * @param month 往前几个月
     * @return
     */
    public static Date getDownMonthDate(int month) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -month);
        date = calendar.getTime();
        return date;
    }
    /**
     * 获取几个月前的当天时间
     * @param month 往前几个月
     * @return
     */
    public static Date getDownMonthDate(int month,Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -month);
        date = calendar.getTime();
        return date;
    }
    /**
     * 获取几分钟前的当天时间
     * @param minute 往前几分钟
     * @return
     */
    public static Date getDownMinuteDate(int minute,Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, -minute);
        date = calendar.getTime();
        return date;
    }
    /**
     * 获取几秒钟前的当天时间
     * @param second 往前几秒钟
     * @return
     */
    public static Date getDownSecondDate(int second,Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, -second);
        date = calendar.getTime();
        return date;
    }
    /**
     * 获取几天前的时间
     * @param day 往前几天
     * @return
     */
    public static Date getDownDayDate(int day,Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -day);
        date = calendar.getTime();
        return date;
    }
    /**
     * 取得当月天数
     * */
    public static int getCurrentMonthLastDay()
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }


    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }

    /**
     * 根据日期 找到对应日期的 星期
     */
    public static String getDayOfWeekByDate(String date) {
        String dayOfweek = "-1";
        try {
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date myDate = myFormatter.parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat("E");
            String str = formatter.format(myDate);
            dayOfweek = str;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return dayOfweek;
    }


    public static void main(String[] args) throws ParseException {

//        SimpleDateFormat sdf = new SimpleDateFormat(TimeUtils.DEFAULT_DATE_PATTERN);
//        //当天
//        String veryDay = sdf.format(new Date());
//        String substring = veryDay.substring(8, 10);
//        System.out.println(substring);
        Optional<LocalDate> localDate = toDate("2020-11-23");
        Date monthDate = getDownMonthDate(6);
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        String dayOfWeekByDate = getDayOfWeekByDate("2021-05-07");

        System.out.println(dayOfWeekByDate);
    }
}

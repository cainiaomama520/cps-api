package com.mex.pdd.base.common.utils;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 日期处理
 *
 * @author theodo
 * @email 36780272@qq.com
 * @date 2017年10月21日 下午12:53:33
 */
public class DateUtils {
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    //两天之间的天数
    public static List<Date> daysPeriodFromNow(java.util.Date begin) {
        LocalDate end = new LocalDate();
        int i = Days.daysBetween(new LocalDate(begin.getTime()), end).getDays();
        return IntStream.rangeClosed(0, i).mapToObj(offset -> end.minusDays(offset).toDate()).collect(Collectors.toList());
    }

    public static int daysBetweenUsingJoda(java.util.Date begin) {
        return Days.daysBetween(new LocalDate(begin.getTime()), new LocalDate()).getDays();
    }

    public static Date parse(String date) {
        DateTime dateTime = DateTimeFormat.forPattern(DateUtils.DATE_TIME_PATTERN).parseDateTime(date);
        return dateTime.toDate();
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    public static String getDate() {
        return new DateTime().toString(DATE_PATTERN);
    }

    public static Date toUtcDate(String time) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            return df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}

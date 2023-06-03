package cicc.quickfix.client.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;


public class TimeTransfer {
    /**
     * String转LocalDateTime
     * @param str
     * @param format
     * @return
     */
    public LocalDateTime parseString2LocalDateTime(String str, String format){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(str,df);
    }

    /**
     * Date转换为String
     * @param date
     * @param format
     * @return
     */
    public String format(Date date, String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }
    /**
     * 获取两个日期相差的天数
     * @param beginDateTime
     * @param endDateTime
     * @return
     */
    public static Integer getDaySub(LocalDateTime beginDateTime,LocalDateTime endDateTime){
        return endDateTime.getDayOfYear()-beginDateTime.getDayOfYear();
    }

    /**
     * 获取一天最早的时间
     * @param dateTime
     * @return
     */
    public static LocalDateTime getFirstDateTimeOfDay(LocalDateTime dateTime){
        return LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MIN);
    }

    /**
     * 获取一天最晚的时间
     * @param dateTime
     * @return
     */
    public static LocalDateTime getLastDateTimeOfDay(LocalDateTime dateTime){
        return LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MAX);
    }

    /**
     * 获取一个月内的最早一天
     * @param dateTime
     * @return
     */
    public static LocalDateTime getFirstDateTimeOfMonth(LocalDateTime dateTime){
        return LocalDateTime.of(LocalDate.from(dateTime.toLocalDate().with(TemporalAdjusters.firstDayOfMonth())), LocalTime.MIN);
    }

    /**
     * 获取一个月最后一天
     * @param dateTime
     * @return
     */
    public static LocalDateTime getLastDateTimeOfMonth(LocalDateTime dateTime){
        return LocalDateTime.of(LocalDate.from(dateTime.toLocalDate().with(TemporalAdjusters.lastDayOfMonth())), LocalTime.MAX);
    }


}

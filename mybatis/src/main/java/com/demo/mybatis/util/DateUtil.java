package com.demo.mybatis.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: lijiawei04
 * @date: 2021/4/1 4:09 下午
 */
public class DateUtil {

    public static LocalDateTime string2LocalDateTime(String date, DatePatternTypeEnum patternType) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(patternType.pattern));
    }

    public static LocalDateTime string2LocalDateTimeByDefault(String date) {
        return string2LocalDateTime(date, DatePatternTypeEnum.TYPE_1);
    }

    public static String localDateTime2String(LocalDateTime localDateTime, String format) {
        return DateTimeFormatter.ofPattern(format).format(localDateTime);
    }

    public static String localDateTime2StringByDefault(LocalDateTime localDateTime) {
        return localDateTime2String(localDateTime, DatePatternTypeEnum.TYPE_1.getPattern());
    }

    public static long localDateTime2Ms(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDateTime ms2LocalDateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    @Getter
    @AllArgsConstructor
    public enum DatePatternTypeEnum {
        TYPE_1("yyyy-MM-dd HH:mm:ss"),
        TYPE_2("yyyy/MM/dd HH:mm:ss")
        ;
        private final String pattern;
    }

}

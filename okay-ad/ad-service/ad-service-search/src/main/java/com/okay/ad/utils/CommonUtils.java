package com.okay.ad.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author OKali
 */
@Slf4j
public class CommonUtils {

    /**
     * 传递进来一个map，如果这个map的key不存在的情况下，
     * 我们使用传递进来的factory去返回一个新的对象
     * @param key
     * @param map
     * @param factory 这个接口是一个提供者的意思，
     *                只有一个get的抽象类，
     *                没有默认的方法以及静态的方法，
     *                传入一个泛型T的，get方法，返回一个泛型T
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> V getorCreate(K key, Map<K, V> map,
                                       Supplier<V> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }

    public static String stringConcat(String... args) {

        StringBuilder result = new StringBuilder();
        for (String arg : args) {
            result.append(arg);
            result.append("-");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    // Tue Jan 01 08:00:00 CST 2019
    public static Date parseStringDate(String dateString) {

        try {

            DateFormat dateFormat = new SimpleDateFormat(
                    "EEE MMM dd HH:mm:ss zzz yyyy",
                    Locale.US
            );
            return DateUtils.addHours(
                    dateFormat.parse(dateString),
                    -8
            );

        } catch (ParseException ex) {
            log.error("parseStringDate error: {}", dateString);
            return null;
        }
    }
}

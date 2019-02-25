package com.okay.ad.utils;

import com.okay.ad.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

/**
 * Created by OKali on 2019/1/19.
 */
public class CommonUtils {

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy/MM/dd","yyyy.MM.dd"
    };

    public static String md5(String value) {
        return DigestUtils.md5Hex(value).toUpperCase();
    }

    public static Date parseStrDate(String dateStr) throws AdException {

        try {
            return DateUtils.parseDate(
                    dateStr, parsePatterns);
        } catch (Exception ex) {
            throw new AdException(ex.getMessage());
        }
    }
}

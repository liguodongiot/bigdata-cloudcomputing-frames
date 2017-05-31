package com.lgd.es;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Describe: 日期工具类
 *
 * @author: guodong.li
 * @datetime: 2017/5/23 11:10
 */
public class DateUtils {

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public static String date2Str(Date date, String format) {
        if (null != date && !"".equals(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        }
        return null;
    }

    public static String timestamp2Str(Timestamp time) {
        if(null != time && !"".equals(time)){
            Date date = new Date(time.getTime());
            return date2Str(date, DEFAULT_FORMAT);
        }
        return null;
    }

}

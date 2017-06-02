package com.lgd.es;

import org.joda.time.DateTime;

/**
 * Describe:
 *
 * @author: guodong.li
 * @datetime: 2017/6/1 13:11
 */
public class Demo {

    public static void main(String[] args) {
        DateTime dateTime = new DateTime(System.currentTimeMillis());
        System.out.println(dateTime);
        System.out.println(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
    }

}

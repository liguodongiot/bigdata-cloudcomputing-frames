package com.lgd.base;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.*;

/**
 * Created by liguodong on 2017/4/23.
 */
public class Demo {

//    public static void main(String[] args) {
//
//        String str = "ds,12,543,54";
//        System.out.println(str.length());
//
//        String[] arr = str.split(",");
//        System.out.println(arr.length);
//
//        List list = new ArrayList<String>();
//        System.out.println(list.size());
//
//        String answer = "  ";
//        System.out.println(StringUtils.isNotEmpty(answer)); //true
//        System.out.println(StringUtils.isNotBlank(answer)); //false
//        System.out.println(StringUtils.isBlank(answer));
//        String stri = null;
//        System.out.println(StringUtils.isBlank(""));
//        System.out.println(StringUtils.isBlank(stri));
//
//    }


//    public static void main(String[] args){
//        Integer a=1;
//        Integer b=2;
//        Integer c=3;
//        Integer d=3;
//        Integer e=321;
//        Integer f=321;
//        Long g=3L;
//
//        System.out.println(c==d);
//        System.out.println(e==f);
//        System.out.println(e.equals(f));
//        System.out.println("-------------");
//        System.out.println(c==(a+b));
//        System.out.println(c.equals(a+b));
//        System.out.println(g==(a+b));
//        System.out.println(g.equals(a+b));
//
//
//        Random random = new Random();
//        for (int i = 0; i < 10; i++) {
//            System.out.println(random.nextInt(10));
//        }
//
//    }

//    public static void main(String[] args) {
//
//        Map<String,Object> map = new HashMap<>();
//        map.put("a",1);
//        map.put("d",22);
//        Map<String,Object> mapAll = new HashMap<>();
//        mapAll.putAll(map);
//        mapAll.put("a",(Integer.parseInt(mapAll.get("a").toString())+1));
//
//        System.out.println(mapAll.toString());
//        System.out.println(map.toString());
//    }


//    public static void main(String[] args) {
//
//        DateTime dateTime = new DateTime(System.currentTimeMillis());
//        DateTime dateTime2 = new DateTime(dateTime.getYear(),
//                dateTime.getMonthOfYear(),
//                dateTime.getDayOfMonth()+1,0,0,0,0);
//
//        System.out.println(dateTime.toString("yyyyMMdd HH:mm:ss"));
//        System.out.println(dateTime2.toString("yyyyMMdd HH:mm:ss"));
//        System.out.println("------------------");
//        System.out.println(dateTime.toString());
//        //System.out.println(dateTime2.tos);
//        //DateTime dateTime3 = new DateTime(dateTime2.ge)
//
//        System.out.println(dateTime2.getMillis());
//        System.out.println(dateTime2.toString());
//
//        System.out.println(new Date().getTime());
//
//        System.out.println(dateTime2.toDate().getTime());
//        System.out.println(System.currentTimeMillis());
//        System.out.println(new DateTime(System.currentTimeMillis()).toDate().getTime());
//    }

    public static void main(String[] args) {
        String str = "狮子座在2016年11";
        System.out.println(str.length());


        System.out.println(str.substring(0,10));



    }


}

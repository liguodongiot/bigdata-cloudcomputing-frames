package com.lgd.base;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
    public static void main(String[] args){
        Integer a=1;
        Integer b=2;
        Integer c=3;
        Integer d=3;
        Integer e=321;
        Integer f=321;
        Long g=3L;

        System.out.println(c==d);
        System.out.println(e==f);
        System.out.println(e.equals(f));
        System.out.println("-------------");
        System.out.println(c==(a+b));
        System.out.println(c.equals(a+b));
        System.out.println(g==(a+b));
        System.out.println(g.equals(a+b));
    }

}

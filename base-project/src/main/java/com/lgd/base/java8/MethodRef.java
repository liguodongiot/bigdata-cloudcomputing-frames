package com.lgd.base.java8;

import java.util.ArrayList;
import java.util.List;

/**
 * 方法引用
 */

public class MethodRef {

    public static void main(String[] args) {
        List names = new ArrayList();
        names.add("Mahesh");
        names.add("Suresh");
        names.add("Ramesh");
        names.add("Naresh");
        names.add("Kalpesh");

        names.forEach(System.out::println);


        names.forEach(x->System.out.print(x+":"));
    }

}

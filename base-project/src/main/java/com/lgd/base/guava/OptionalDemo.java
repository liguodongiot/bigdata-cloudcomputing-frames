package com.lgd.base.guava;

import com.google.common.base.Optional;

/**
 * <p>Project: bigdata-frames</p>
 * <p>Package: com.lgd.base.guava</p>
 * <p>Title: Optional</p>
 * <p>Description:
 *      Optional用于包含非空对象的不可变对象。
 *      Optional对象，用于不存在值表示null。这个类有各种实用的方法，
 *      以方便代码来处理为可用或不可用，而不是检查null值。
 * </p>
 *
 * @author guodong.li
 * @version 1.0.0
 * @date 2018/6/19
 */

public class OptionalDemo {


    public static void main(String args[]){

        Integer value1 =  null;
        Integer value2 =  new Integer(10);

        //Optional.fromNullable - allows passed parameter to be null.
        Optional<Integer> a = Optional.fromNullable(value1);

        //Optional.of - throws NullPointerException if passed parameter is null
        Optional<Integer> b = Optional.of(value2);

        System.out.println(sum(a,b));
    }

    public static Integer sum(Optional<Integer> a, Optional<Integer> b){

        //Optional.isPresent - checks the value is present or not
        //如果包含一个(非空)的实例，返回true。
        System.out.println("First parameter is present: " + a.isPresent());
        System.out.println("Second parameter is present: " + b.isPresent());

        System.out.println("First parameter is orNull: " + a.orNull());

        //Optional.or - returns the value if present otherwise returns
        //the default value passed.
        Integer value1 = a.or(new Integer(0));

        //Optional.get - gets the value, value should be present
        Integer value2 = b.get();

        return value1 + value2;
    }

}

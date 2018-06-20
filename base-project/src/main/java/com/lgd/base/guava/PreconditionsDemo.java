package com.lgd.base.guava;

import com.google.common.base.Preconditions;

/**
 * <p>Project: bigdata-frames</p>
 * <p>Package: com.lgd.base.guava</p>
 * <p>Title: </p>
 * <p>Description:
 *      Preconditions提供静态方法来检查方法或构造函数，
 *      被调用是否给定适当的参数。它检查的先决条件。其方法失败抛出IllegalArgumentException。
 * </p>
 *
 * @author guodong.li
 * @version 1.0.0
 * @date 2018/6/19
 */

public class PreconditionsDemo {

    public static void main(String args[]){

        try {
            System.out.println(sqrt(-3.0));
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(sum(null,3));
        }catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(getValue(6));
        }catch(IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }

        try {
            checkPositionIndex(2);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        try {
            checkPositionIndexes(2,3);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }


        try {
            checkState(-1);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    //确保涉及的一个或多个参数来调用方法表达式的真相。
    public static double sqrt(double input) {
        Preconditions.checkArgument(input > 0.0,
                "Illegal Argument passed: Negative value %s.", input);
        return Math.sqrt(input);
    }

    //确保对象引用作为参数传递给调用方法不为空。
    public static int sum(Integer a, Integer b){
        a = Preconditions.checkNotNull(a,
                "Illegal Argument passed: First parameter is Null.");
        b = Preconditions.checkNotNull(b,
                "Illegal Argument passed: Second parameter is Null.");
        return a+b;
    }

    /**
     * 校验元素索引是否有效
     * index大于等于0小于size
     * @param input
     * @return
     */
    // 确保索引指定一个数组，列表或尺寸大小的字符串有效的元素。
    public static int getValue(int input){
        int[] data = {1,2,3,4,5};
        Preconditions.checkElementIndex(input, data.length,
                "Illegal Argument passed: Invalid index.");
        return 0;
    }


    /**
     * index大于等于0小于等于size
     * 校验元素索引是否有效,使用checkPositionIndex校验，在临界值不产生异常
     * @param input
     */
    public static void checkPositionIndex(int input){


        int[] data = {1,2,3,4,5};
        Preconditions.checkPositionIndex(input, data.length,
                "Illegal Argument passed: Invalid index.");
    }

    /**
     * 校验是否是有效的索引区间
     * @param start
     * @param end
     */
    public static void checkPositionIndexes(int start, int end){

        int[] data = {1,2,3,4,5};
        Preconditions.checkPositionIndexes(start,end, data.length);
    }

    // 校验表达式是否正确，并使用占位符输出错误信息，使用方法作为表达式
    public static void checkState(int a){
        Preconditions.checkState(a>0, "%s is wrong", "testState()");
    }



}

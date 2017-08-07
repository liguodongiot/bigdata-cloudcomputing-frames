package com.lgd.base.definesort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Describe:
 * author: guodong.li
 * datetime: 2017/7/20 10:43
 */
public class UserUtils {

    /**
     *  数组的排序（降序）+Comparator接口
     * @param arr
     */
    public static void bubbleSort(Object[] arr,Comparator com)
    {
        boolean sorted = true;
        int len = arr.length;
        for(int j=0;j<len-1;j++)
        {
            sorted = true;
            for(int i=0;i<len-1-j;i++)
            {
                if(com.compare(arr[i], arr[i+1])<0)
                {
                    Object  temp = arr[i];
                    arr[i] = arr[i+1];
                    arr[i+1] = temp;
                    sorted = false;
                }
            }
            if(sorted){
                break;
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    /**
     * list的排序+比较器
     * @param list
     * @param com
     */
    public static <T> void bubbleSort(List<T> list, Comparator<T> com)
    {
        //第一步：转成数组
        Object [] arr = list.toArray();
        bubbleSort(arr,com);
        //第二步：改变容器中对应的值
        for(int i=0; i<arr.length; i++)
        {
            list.set(i, (T)(arr[i]));
        }
    }
}

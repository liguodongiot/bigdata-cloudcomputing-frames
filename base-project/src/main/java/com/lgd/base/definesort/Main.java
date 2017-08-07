package com.lgd.base.definesort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Describe:
 * author: guodong.li
 * datetime: 2017/7/20 10:44
 */
public class Main {

    public static void main(String[] args) {
        UserInfo userInfo1 = new UserInfo(1L,"d32",32);
        UserInfo userInfo2 = new UserInfo(2L,"d3dd2",44);
        UserInfo userInfo3 = new UserInfo(3L,"d3ds2",37);
        List<UserInfo> list = new ArrayList<>();
        list.add(userInfo1);
        list.add(userInfo2);
        list.add(userInfo3);

//        UserUtils.bubbleSort(list,new UserInfoComp());
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i).toString());
//        }


        list.sort(new UserInfoComp());
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString());
        }
    }

}

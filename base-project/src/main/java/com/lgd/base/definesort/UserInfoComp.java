package com.lgd.base.definesort;

/**
 * Describe:
 * author: guodong.li
 * datetime: 2017/7/20 10:41
 */
public class UserInfoComp implements java.util.Comparator<UserInfo>{


    @Override
    public int compare(UserInfo o1, UserInfo o2) {
        if(o1.getAge()-o2.getAge()<=0){
            return 1;
        } else {
            return -1;
        }
        //return o2.getAge()-o1.getAge();
    }
}

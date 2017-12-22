package com.lgd.base;


import com.lgd.base.definesort.UserInfo;
import net.sf.json.JSONObject;

public class SerDemo {

    //
    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo(1L,"liguodong",33);
        //将java对象转换为json对象
        JSONObject json = JSONObject.fromObject(userInfo);
        //将json对象转换为字符串
        String str = json.toString();
        System.out.println(str);

        JSONObject jsonObject = JSONObject.fromObject(str);
        UserInfo jb = (UserInfo) JSONObject.toBean(jsonObject,UserInfo.class);

        System.out.println(jb.toString());


    }

}

package com.lgd.base.collection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: MapMain</p>
 * <p>Description: </p>
 *
 * @author liguodong
 * @version 1.0.0
 * @date 2017/4/14 9:56
 */
public class MapMain {


    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("name","dsaf");
        map.put("desc", "da");

        Map<String, String> mapNew = new HashMap<>();
        mapNew.putAll(map);
        map.put("name","dddddd");

        System.out.println(mapNew.get("name"));
        System.out.println(map.get("name"));

        List<Map<String, String>> mapList = new ArrayList<>();
        mapList.add(map);

        System.out.println(mapList.get(0).toString());
        //map.clear();
        //System.out.println(mapList.get(0).toString());
        map = new HashMap<>();
        System.out.println(mapList.get(0).toString());


        Map<String,Object> tempMap = new HashMap<>();
        String[] keyWords = {"name","desc"};

        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(mapNew));//new JSONObject();
        extractInfo(tempMap, keyWords, jsonObject);
        System.out.println(tempMap.toString());

    }

    public static void extractInfo(Map<String,Object> tempMap, String[] keyWords, JSONObject parse){
        for (String keyWord : keyWords) {
            tempMap.put(keyWord, parse.get(keyWord)==null?"":parse.get(keyWord));
        }
    }



}

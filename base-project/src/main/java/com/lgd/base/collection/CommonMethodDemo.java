package com.lgd.base.collection;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.StringUtils;


import java.util.*;

/**
 * Describe:
 *
 * @author: guodong.li
 * @datetime: 2017/5/18 17:18
 */
public class CommonMethodDemo {

    public static void main(String[] args) {
        List<String> list = null;
        System.out.println(ListUtils.emptyIfNull(list));
        List<String> list2 = null;
        List<String> stringList = new ArrayList<>();
        stringList.add("liguodong");
        stringList.add("chinese");
        System.out.println(ListUtils.defaultIfNull(list2,stringList));

        System.out.println("-------------------------");
        Map<String,Object> stringMap = new HashMap<>();
        System.out.println(stringMap);
        System.out.println(MapUtils.isEmpty(stringMap));
        stringMap.put("liguodong","1993");
        stringMap.put("kaka","32");
        System.out.println(MapUtils.isEmpty(stringMap));

        System.out.println("~~~~~~~~~~~~~~~~~");

        Map<String,Object> map = null;
        System.out.println(MapUtils.isEmpty(map));
        map = MapUtils.emptyIfNull(map);
        System.out.println(map);//EmptyMap
        System.out.println(map.size());

        System.out.println(MapUtils.isEmpty(map));

        System.out.println("-------------------------");

        String str = null;
        System.out.println(StringUtils.isNotEmpty(str));
        System.out.println(StringUtils.isNotBlank(null));
        System.out.println(StringUtils.isNotBlank("   "));
        System.out.println(StringUtils.isBlank("   "));

        System.out.println("-------------------------");

        Set set = null;
        System.out.println(SetUtils.emptyIfNull(set));
        System.out.println(SetUtils.emptySet());
    }

}

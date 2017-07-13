package com.lgd.ai;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Describe:
 *
 * @author: liguodong
 * @datetime: 2017/7/2 17:47
 */

public class Vsm {

    public static double calCosSim(Map<Character, Integer> v1, Map<Character, Integer> v2) {

        double sclar = 0.0,
                norm1=0.0,
                norm2=0.0,
                similarity=0.0;

        Set<Character> v1Keys = v1.keySet();
        Set<Character> v2Keys = v2.keySet();

        Set<Character> both= new HashSet<>();
        both.addAll(v1Keys);
        both.retainAll(v2Keys);
        System.out.println(both);

        for (Character str1 : both) {
            sclar += v1.get(str1) * v2.get(str1);
        }

        for (Character str1:v1.keySet()){
            norm1+=Math.pow(v1.get(str1),2);
        }
        for (Character str2:v2.keySet()){
            norm2+=Math.pow(v2.get(str2),2);
        }

        similarity=sclar/Math.sqrt(norm1*norm2);
        System.out.println("sclar:"+sclar);
        System.out.println("norm1:"+norm1);
        System.out.println("norm2:"+norm2);
        System.out.println("similarity:"+similarity);
        return similarity;
    }

    public static void main(String[] args) {

//        Map<String, Double> m1 = new HashMap<>();
//        m1.put("Hello", 1.0);
//        m1.put("css", 1.0);
//        m1.put("Lucene", 1.0);
//
//        Map<String, Double> m2 = new HashMap<>();
//        m2.put("Hello", 1.0);
//        m2.put("Word", 1.0);
//        m2.put("Hadoop", 1.0);
//        m2.put("java", 1.0);
//        m2.put("html", 1.0);
//        m2.put("css", 1.0);
        String string1 = "怎么回事";
        String string2 = "在吗。账户被锁定怎么回事";

        Map<Character, Integer> aMap = new HashMap<>();

        //统计每个汉字出现的次数
        for (Character character1 : string1.toCharArray()) {
            if (aMap.containsKey(character1)) {
                aMap.put(character1,aMap.get(character1)+1);
            } else {
                aMap.put(character1, 1);
            }
        }

        Map<Character, Integer> bMap = new HashMap<>();
        for (Character character2 : string2.toCharArray()) {
            if (bMap.containsKey(character2)) {
                bMap.put(character2,bMap.get(character2)+1);
            } else {
                bMap.put(character2, 1);
            }
        }
        calCosSim(aMap, bMap);

    }
}
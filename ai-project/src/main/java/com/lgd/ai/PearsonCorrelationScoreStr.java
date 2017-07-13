package com.lgd.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Describe:
 * author: guodong.li
 * datetime: 2017/7/11 18:32
 */
public class PearsonCorrelationScoreStr {

    private Map<String, Map<Character, Integer>> dataset = null;

    public PearsonCorrelationScoreStr(String str1,String str2) {
        initDataSet(str1, str2);
    }

    /**
     * 初始化数据集
     */
    private void initDataSet(String string1,String string2) {
        dataset = new HashMap<String, Map<Character, Integer>>();

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

        dataset.put("A", aMap);

        dataset.put("B", bMap);

    }

    public Map<String, Map<Character, Integer>> getDataSet() {
        return dataset;
    }

    /**
     * @param person1
     * @param person2
     *
     * @return 皮尔逊相关度值
     */
    public double sim_pearson(String person1, String person2) {
        // 找出双方都评论过的电影,（皮尔逊算法要求）
        List<Character> list = new ArrayList<Character>();
        for (Map.Entry<Character, Integer> p1 : dataset.get(person1).entrySet()) {
            if (dataset.get(person2).containsKey(p1.getKey())) {
                list.add(p1.getKey());
            }
        }

        double sumX = 0.0;
        double sumY = 0.0;
        double sumX_Sq = 0.0;
        double sumY_Sq = 0.0;
        double sumXY = 0.0;
        int N = list.size();

        for (Character name : list) {
            Map<Character, Integer> p1Map = dataset.get(person1);
            Map<Character, Integer> p2Map = dataset.get(person2);

            sumX += p1Map.get(name);
            sumY += p2Map.get(name);
            sumX_Sq += Math.pow(p1Map.get(name), 2);
            sumY_Sq += Math.pow(p2Map.get(name), 2);
            sumXY += p1Map.get(name) * p2Map.get(name);
        }

        double numerator = sumXY - sumX * sumY / N;
        double denominator = Math.sqrt((sumX_Sq - sumX * sumX / N) * (sumY_Sq - sumY * sumY / N));

        // 分母不能为0
        if (denominator == 0) {
            return 0;
        }

        return numerator / denominator;
    }

    public static void main(String[] args) {
        PearsonCorrelationScoreStr pearsonCorrelationScore = new PearsonCorrelationScoreStr("没有","你没有贷款呀");
        System.out.println(pearsonCorrelationScore.sim_pearson("A",
                "B"));
    }


}

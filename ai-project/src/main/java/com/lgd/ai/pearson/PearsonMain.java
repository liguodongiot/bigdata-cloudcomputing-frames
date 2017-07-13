package com.lgd.ai.pearson;

/**
 * Describe:
 * author: guodong.li
 * datetime: 2017/7/11 19:04
 */
public class PearsonMain {

    public static void main(String[] args) {
        DataNode x = new DataNode(new double[] {1, 2, 3, 5, 8});
        DataNode y = new DataNode(new double[] {0.11, 0.12, 0.13, 0.15, 0.18,0.20});

        PearsonCorrelationScore score = new PearsonCorrelationScore(x, y);
        System.out.println(score.getPearsonCorrelationScore());
    }

}

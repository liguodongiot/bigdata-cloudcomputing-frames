package com.lgd.ai.pearson;

/**
 * Describe:
 * author: guodong.li
 * datetime: 2017/7/11 19:03
 */
public class PearsonCorrelationScore {
    private double[] xData;
    private double[] yData;
    private double xMeans;
    private double yMeans;
    /**
     * 求解皮尔逊的分子
     */
    private double numerator;
    /**
     * 求解皮尔逊系数的分母
     */
    private double denominator;
    /**
     * 计算最后的皮尔逊系数
     */
    private double pearsonCorrelationScore;

    public PearsonCorrelationScore(DataNode x, DataNode y) {
        //先获取DataNode的数据
        this.xData = x.getDatas();
        this.yData = y.getDatas();
        //拿到两个数据的平均值
        this.xMeans = this.getMeans(xData);
        this.yMeans = this.getMeans(yData);
        //计算皮尔逊系数的分子
        this.numerator = this.generateNumerator();
        //计算皮尔逊系数的分母
        this.denominator = this.generateDenomiator();
        //计算皮尔逊系数
        this.pearsonCorrelationScore = this.numerator / this.denominator;
    }

    /**
     * 生成分子
     * @return 分子
     */
    private double generateNumerator() {
        double sum = 0.0;
        for (int i = 0; i < xData.length; i++) {
            sum += (xData[i] - xMeans) * (yData[i] - yMeans);
        }
        return sum;
    }
    /**
     * 生成分母
     * @return 分母
     */
    private double generateDenomiator() {
        double xSum = 0.0;
        for (int i = 0; i < xData.length; i++) {
            xSum += (xData[i] - xMeans) * (xData[i] - xMeans);
        }

        double ySum = 0.0;
        for (int i = 0; i < yData.length; i++) {
            ySum += (yData[i] - yMeans) * (yData[i] - yMeans);
        }

        return Math.sqrt(xSum) * Math.sqrt(ySum);
    }

    /**
     * 根据给定的数据集进行平均值计算
     * @param datas 数据集
     * @return 给定数据集的平均值
     */
    private double getMeans(double[] datas) {
        double sum = 0.0;
        for (int i = 0; i < datas.length; i++) {
            sum += datas[i];
        }
        return sum / datas.length;
    }

    public double getPearsonCorrelationScore() {
        return this.pearsonCorrelationScore;
    }
}
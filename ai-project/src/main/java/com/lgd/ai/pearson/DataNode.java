package com.lgd.ai.pearson;

import java.util.Arrays;

/**
 * Describe:
 * author: guodong.li
 * datetime: 2017/7/11 19:01
 */

public class DataNode {
    double[] datas;

    public double[] getDatas() {
        return datas;
    }

    public void setDatas(double[] datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "DataNode [datas=" + Arrays.toString(datas) + "]";
    }

    public DataNode() {

    }
    public DataNode(double[] datas) {
        super();
        this.datas = datas;
    }
}

package com.lgd.ai.pearson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Describe:
 *
 * 2,4,7
 * 1,3,6
 * s
 *
 * author: guodong.li
 * datetime: 2017/7/11 20:22
 */
public class CallClass {


    public static void main(String[] args) throws IOException {
        double CORR = 0.0;
        List<String> xList = new ArrayList<String>();;
        List<String> yList = new ArrayList<String>();

        System.out.println("Please input your X's varieties and Y's varieties\r"+
                "differnt line,then you should key into \"s\" to end the inputing operator!");

        //initial varieties for xList,yList;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str =null;
        boolean flag = false;
        while(!(str=br.readLine()).equals("s")){
            String[] vStr = str.split(",");
            int size = vStr.length;
            if(flag == false){
                for(int i=0;i<size;i++){
                    xList.add(i, vStr[i]);
                }
                flag = true;
            }else if(flag == true){
                for(int i=0;i<size;i++){
                    yList.add(i, vStr[i]);
                }
                flag = false;
            }

        }

        NumeratorCalculate nc = new NumeratorCalculate(xList,yList);
        double numerator = nc.calcuteNumerator();
        DenominatorCalculate dc = new DenominatorCalculate();
        double denominator = dc.calculateDenominator(xList, yList);
        CORR = numerator/denominator;
        System.out.println("We got the result by Calculating:");
        System.out.printf("CORR = "+CORR);
    }
}

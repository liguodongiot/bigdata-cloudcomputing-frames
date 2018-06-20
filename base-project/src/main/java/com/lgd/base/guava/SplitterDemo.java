package com.lgd.base.guava;

import com.google.common.base.Splitter;

public class SplitterDemo {
    public static void main(String args[]){
        SplitterDemo tester = new SplitterDemo();
        tester.testSplitter();
    }

    //omitEmptyStrings  省略空字符串
    private void testSplitter(){
        System.out.println(Splitter.on(',')
                .trimResults()
                .omitEmptyStrings()
                .split("the ,quick, , brown         , fox,              jumps, over, the, lazy, little dog."));
    }
}
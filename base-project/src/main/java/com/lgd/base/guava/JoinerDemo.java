package com.lgd.base.guava;

import java.util.Arrays;
import com.google.common.base.Joiner;

/**
 * <p>Project: bigdata-frames</p>
 * <p>Package: com.lgd.base.guava</p>
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author guodong.li
 * @version 1.0.0
 * @date 2018/6/20
 */

public class JoinerDemo {
    public static void main(String args[]){
        JoinerDemo tester = new JoinerDemo();
        tester.testJoiner();
    }

    private void testJoiner(){
        System.out.println(Joiner.on(",")
                .skipNulls()
                .join(Arrays.asList(1,2,3,4,5,null,6)));
        System.out.println(Joiner.on(",")
                .useForNull("-1")
                .join(Arrays.asList(1,2,3,4,5,null,6)));
    }
}
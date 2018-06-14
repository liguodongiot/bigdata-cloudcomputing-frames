package com.lgd.base;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * <p>Project: bigdata-frames</p>
 * <p>Package: com.lgd.base</p>
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author guodong.li
 * @version 1.0.0
 * @date 2018/6/11
 */

public class DateTest {

    @Test
    public void test(){
        DateTime oneTime = new DateTime("2017-01-01");

        DateTime twoTime = new DateTime("2017-01-01");

        DateTime threeTime = new DateTime("2018-01-02");

        System.out.println(oneTime.isBefore(twoTime));

        System.out.println(oneTime.isBefore(threeTime));

        int timePartNum = 0;
        for (DateTime currTime=oneTime; currTime.isBefore(threeTime); currTime=getNextTime("月",3, currTime)) {
            timePartNum++;
        }

        System.out.println(timePartNum);
    }

    public static DateTime getNextTime(String freqUnit, Integer freqNum, DateTime currentTime){
        DateTime nextTime;
        if("日".equals(freqUnit)){
            nextTime = currentTime.plusDays(freqNum);
        } else if("月".equals(freqUnit)){
            nextTime = currentTime.plusMonths(freqNum);
        } else {
            nextTime = currentTime.plusYears(freqNum);
        }
        return nextTime;
    }

}

package com.lgd.es.wordsplit;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;

import java.io.File;
import java.util.List;

/**
 * Describe:
 * author: guodong.li
 * datetime: 2017/7/20 21:51
 */
public class Demo {

    public static void main(String[] args) {
        //word();
        file();
    }

    //对文本进行分词
    public static void word(){
        //移除停用词
        List<Word> words = WordSegmenter.seg("杨尚川是APDPlat应用级产品开发平台的作者");
        System.out.println(words);
        //保留停用词
        List<Word> wordsStop = WordSegmenter.segWithStopWords("杨尚川是APDPlat应用级产品开发平台的作者");
        System.out.println(wordsStop);
    }

    //对文件进行分词
    public static void file(){
        String input = "d:/text.txt";
        String output1 = "d:/word1.txt";
        String output2 = "d:/word2.txt";
        try {
            //移除停用词
            WordSegmenter.seg(new File(input), new File(output1));
            //保留停用词
            WordSegmenter.segWithStopWords(new File(input), new File(output2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("over");

    }


}

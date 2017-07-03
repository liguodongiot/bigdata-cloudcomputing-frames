package com.lgd.ai;


import java.io.IOException;
import java.io.StringReader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * Describe:
 * https://github.com/yangyining/IKAnalyzer
 * Dictionary
 * @author: liguodong
 * @datetime: 2017/7/3 8:02
 */

public class IkSegmentation {

    public static String Seg(String sentence) throws IOException {
        String text="";
        //创建分词对象
        Analyzer anal=new IKAnalyzer(true);
        StringReader reader=new StringReader(sentence);
        //分词
        TokenStream ts=anal.tokenStream("", reader);
        CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);
        ts.reset();
        //遍历分词数据
        while(ts.incrementToken()){
            text+=term.toString()+"|";
        }
        reader.close();
        return text.trim()+"\n";
    }

    public static void main(String[] args) throws IOException {
        IkSegmentation ik = new IkSegmentation();
        //System.out.println(ik.Seg("使用IK Analyzer实现中文分词之Java实现"));
        System.out.println(ik.Seg("我伤心了"));
    }
}

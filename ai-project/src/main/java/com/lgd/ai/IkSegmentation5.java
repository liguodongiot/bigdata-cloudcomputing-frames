//package com.lgd.ai;
//
//
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.TokenStream;
//import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.env.Environment;
//import org.wltea.analyzer.cfg.Configuration;
//import org.wltea.analyzer.lucene.IKAnalyzer;
//
//import java.io.IOException;
//import java.io.StringReader;
//
///**
// * Describe: IK分词
// * https://github.com/yangyining/IKAnalyzer
// * Dictionary
// * @author: liguodong
// * @datetime: 2017/7/3 8:02
// */
//
//public class IkSegmentation5 {
//
//    public static String Seg(String sentence) throws IOException {
//        String text="";
//
//        Settings settings = Settings.builder().put("path.home","D:\\install\\elasticsearch-5.2.0")
//                .build();
//        Configuration configuration=new Configuration(new Environment(settings), Settings.EMPTY).setUseSmart(true);
//
//        //创建分词对象
//        Analyzer anal=new IKAnalyzer(configuration);
//        StringReader reader=new StringReader(sentence);
//        //分词
//        TokenStream ts=anal.tokenStream("", reader);
//        CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);
//        ts.reset();
//        //遍历分词数据
//        while(ts.incrementToken()){
//            text+=term.toString()+"|";
//        }
//        reader.close();
//        return text.trim()+"\n";
//    }
//
//    public static void main(String[] args) throws IOException {
//        IkSegmentation5 ik = new IkSegmentation5();
//        //System.out.println(ik.Seg("使用IK Analyzer实现中文分词之Java实现"));
//        System.out.println(ik.Seg("我伤心了"));
//    }
//}

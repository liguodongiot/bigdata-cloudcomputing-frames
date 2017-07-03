package com.lgd.ai;

/**
 * Describe:
 *
 * @author: liguodong
 * @datetime: 2017/7/2 17:34
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class DocumentSimilarity {
    private static int len = 0;	//总文档数
    private static ArrayList<String[]> doc = new ArrayList<String[]>();	//文档内容
    private static HashMap<String, Float> []tdidf;	//TDIDF值
    private static int leng[];	//文档词数
    private static float film[]; //词向量的模

    //读取文件
    public static void readFile(String path) throws IOException{
        long a = System.currentTimeMillis();
        FileReader reader = new FileReader(path);
        BufferedReader br = new BufferedReader(reader);
        String str = null,strdoc = "";

        while((str = br.readLine()) != null) {
            if(str.length() == 0){
                if(strdoc.equals("")) continue;
                doc.add(strdoc.split("  "));
                strdoc = "";
                ++len;
            }
            else
                strdoc += str.substring(23);
        }
        br.close();
        reader.close();
        long b = System.currentTimeMillis();
        System.out.println("读取文件时间："+(b-a)+" ms");
    }

    //计算TDIDF值
    public static void calTdidf(){
        long a = System.currentTimeMillis();

        HashMap<String, Float> []tmp_tdidf = new HashMap[len];

        int size;
        int n = 0;
        String str;
        HashMap<String,Integer> idf = new HashMap<String, Integer>();
        for(String d[]:doc){
            size = d.length;
            leng[n] = size;	//文档词数
            HashMap<String, Float> td = new HashMap<String, Float>();
            for(int i = 0; i < size; ++i){
                str = d[i];
                if(!td.containsKey(str)){
                    td.put(str, (float) 1.0);
                    if(idf.containsKey(str))
                        idf.put(str, idf.get(str)+1);
                    else
                        idf.put(str, (int) 1);
                }
                else
                    td.put(str, td.get(str)+1);
            }
            tmp_tdidf[n++] = td;
        }

        String del = "";
        float ft,sum_tdidf[] = new float[len];
        for(int i = 0; i < len; ++i){
            for(String st:tmp_tdidf[i].keySet()){
                ft = (float) (Math.log(tmp_tdidf[i].get(st)/leng[i] + 1)*Math.log(len/idf.get(st)));
                sum_tdidf[i] += ft;
                tmp_tdidf[i].put(st, ft);
            }
        }

        //过滤
        float ftmp;
        for(int i = 0; i < len; ++i){
            HashMap<String, Float> td = new HashMap<String, Float>();

            ValueComparator bvc =  new ValueComparator(tmp_tdidf[i]);
            TreeMap<String,Float> sorted_map = new TreeMap<String,Float>(bvc);
            sorted_map.putAll(tmp_tdidf[i]);
            ft = 0;

            for(String key:sorted_map.keySet()){
                ftmp = tmp_tdidf[i].get(key);
                ft += ftmp;
                if(ft/sum_tdidf[i] > 0.8)//保留前80%的Tdidf值
                    break;
                td.put(key, ftmp);
            }
            tdidf[i] = td;
        }

        long b = System.currentTimeMillis();
        System.out.println("计算td-idf+去除噪音时间："+(b-a)+" ms");
    }

    //预处理向量的模
    public static void calFilm(){
        long a = System.currentTimeMillis();
        for(int i = 0; i < len; ++i){
            film[i] = (float) 0.0;
            for(String str:tdidf[i].keySet()){
                film[i] += tdidf[i].get(str)*tdidf[i].get(str);
            }
            film[i] = (float) Math.sqrt(film[i]);
        }
        long b = System.currentTimeMillis();
        System.out.println("处理向量模时间："+(b-a)+" ms");
    }

    //计算余弦距离
    public static float cosine(int i,int j){
        int x = i, y = j;
        float ans = 0;
        if(tdidf[i].size() > tdidf[j].size()){
            x = j;
            y = i;
        }

        for(String str:tdidf[x].keySet()){
            if(tdidf[y].containsKey(str))
                ans += tdidf[x].get(str)*tdidf[y].get(str);
        }
        return ans/(film[x]*film[y]);
    }

    public static void calSimilarity() throws IOException{
        long a = System.currentTimeMillis();
        FileWriter fw = new FileWriter("DocSimilarityMatrix.csv");
        for(int i = 0; i < len; ++i){
            for(int j = 0; j < i; ++j ){
                fw.write(cosine(i, j)+",");
            }
            fw.write("1.0,");
            fw.write("\n");
        }
        fw.close();
        long b = System.currentTimeMillis();
        System.out.println("计算相似度并写入时间："+(b-a)+" ms");
    }

    public static void main(String[] args) throws IOException {
        long start_time = System.currentTimeMillis();
        String path = "199801_clear.txt";
        readFile(path);
        tdidf =  new HashMap[len];
        leng = new int[len];
        film = new float[len];
        calTdidf();
        calFilm();
        calSimilarity();
        System.out.println("总运行时间："+(System.currentTimeMillis() - start_time)+" ms");
    }
}

class ValueComparator implements Comparator<String> {

    Map<String, Float> base;
    public ValueComparator(Map<String, Float> base) {
        this.base = base;
    }

    //按values降序
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        }
    }
}

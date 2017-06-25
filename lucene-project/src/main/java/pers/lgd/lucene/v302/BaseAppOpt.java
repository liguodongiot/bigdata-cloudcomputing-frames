//package pers.lgd.lucene.v302;
//
//
//import org.apache.lucene.document.Document;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.queryParser.QueryParser;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.Query;
//import org.apache.lucene.search.ScoreDoc;
//import org.apache.lucene.search.TopDocs;
//import pers.lgd.lucene.entity.Article;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Describe:
// *
// * @author: liguodong
// * @datetime: 2017/6/10 18:35
// */
//public class BaseAppOpt {
//
//    public static void main(String[] args) throws Exception {
//        createIndexDB();
//        //准备工作
//        String keywords = "淘宝";
//        findIndexDB(keywords);
//    }
//
//    /**
//     * 创建索引库
//     * 将Aritcle对象放入索引库中的原始记录表中，从而形成词汇表
//     */
//    public static void createIndexDB() throws Exception{
//        //创建Article对象
//        Article article = new Article(1,"淘宝","淘宝是一家IT互联网公司。");
//
//        //创建Document对象
//        Document document = LuceneUtil.javabean2document(article);
//        IndexWriter indexWriter = new IndexWriter(LuceneUtil.getDirectory(),
//                LuceneUtil.getAnalyzer(),
//                LuceneUtil.getMaxFieldLength());
//        //将document对象写入lucene索引库
//        indexWriter.addDocument(document);
//        //关闭IndexWriter字符流对象
//        indexWriter.close();
//    }
//
//
//    /**
//     * 根据关键字从索引库中搜索符合条件的内容
//     */
//    public static void findIndexDB(String keywords) throws Exception{
//        //准备工作
//        //String keywords = "淘宝";
//
//        List<Article> articleList = new ArrayList<Article>();
//
//		/**
//		 * 创建查询解析器对象
//		 * 参数一：使用分词器的版本，提倡使用该jar包中的最高版本
//		 * 参数二：争对document对象中的哪个属性进行搜索
//		 */
//        QueryParser queryParser = new QueryParser(LuceneUtil.getVersion(),
//                "content",
//                LuceneUtil.getAnalyzer());
//
//        //创建对象对象封装查询关键字
//        Query query = queryParser.parse(keywords);
//
//        //创建IndexSearcher字符流对象
//        IndexSearcher indexSearcher = new IndexSearcher(LuceneUtil.getDirectory());
//
//        /**
//         * 根据关键字，去索引库中的词汇表搜索
//         *
//		 * 参数一：表示封装关键字查询对象，其它QueryParser表示查询解析器
//		 * 参数二：MAX_RECORD表示如果根据关键字搜索出来的内容较多，只取前MAX_RECORD个内容
//		 *        不足MAX_RECORD个数的话，以实际为准
//		 */
//        int MAX_RECORD = 100;
//        TopDocs topDocs = indexSearcher.search(query,MAX_RECORD);
//
//        //迭代词汇表中符合条件的编号
//        for(int i=0;i<topDocs.scoreDocs.length;i++){
//            //取出封装编号和分数的ScoreDoc对象
//            ScoreDoc scoreDoc = topDocs.scoreDocs[i];
//            //取出每一个编号，例如:0,1,2
//            int no = scoreDoc.doc;
//            //根据编号去索引库中的原始记录表中查询对应的document对象
//            Document document = indexSearcher.doc(no);
//            //封装到artilce对象中
//            Article article = (Article)LuceneUtil.document2javabean(document,Article.class);
//            //将article对象加入到list集合中
//            articleList.add(article);
//        }
//        //迭代结果集
//        for(Article a:articleList){
//            System.out.println(a);
//        }
//    }
//
//}

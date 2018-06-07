//package pers.lgd.lucene.v302;
//
//import org.apache.lucene.document.Document;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.Term;
//import pers.lgd.lucene.entity.Article;
//
///**
// * Describe:
// *      增删改查索引库
// * @author: liguodong
// * @datetime: 2017/6/11 10:47
// */
//public class CurdArticleDao {
//
//    public void add() throws Exception {
//        Article article = new Article(1,"唯品会","唯品会是一家特卖商城。");
//
//        Document document = LuceneUtil.javabean2document(article);
//        IndexWriter indexWriter = new IndexWriter(LuceneUtil.getDirectory(),
//                LuceneUtil.getAnalyzer(),
//                LuceneUtil.getMaxFieldLength());
//        indexWriter.addDocument(document); //核心
//        indexWriter.close();
//
//    }
//
//    public void addAll() throws Exception {
//        IndexWriter indexWriter = new IndexWriter(LuceneUtil.getDirectory(),
//                LuceneUtil.getAnalyzer(),
//                LuceneUtil.getMaxFieldLength());
//        for (int i = 0; i < 10; i++) {
//            Article article = new Article(i+1,"唯品会"+(i+1),"唯品会是一家特卖商城。");
//            Document document = LuceneUtil.javabean2document(article);
//            indexWriter.addDocument(document);
//        }
//        indexWriter.close();
//    }
//
//    public void update() throws Exception {
//        Article article = new Article(777,"唯品会"+777,"唯品会是一家特卖商城。");
//        Document document = LuceneUtil.javabean2document(article);
//        IndexWriter indexWriter = new IndexWriter(LuceneUtil.getDirectory(),
//                LuceneUtil.getAnalyzer(),
//                LuceneUtil.getMaxFieldLength());
//		/**
//         * 更新id=7的document对象
//         *
//		 * 参数一：term表示需要更新的document对象，id表示document对象中的id属性,7表示该id属性的值
//		 * 参数二：新的document对象
//		 */
//        indexWriter.updateDocument(new Term("id","7"),document);//核心
//        indexWriter.close();
//    }
//
//    public void delete() throws Exception {
//        IndexWriter indexWriter = new IndexWriter(LuceneUtil.getDirectory(),
//                LuceneUtil.getAnalyzer(),
//                LuceneUtil.getMaxFieldLength());
//        indexWriter.deleteDocuments(new Term("id","2"));//核心
//        indexWriter.close();
//    }
//
//    public void deleteAll() throws Exception {
//        IndexWriter indexWriter = new IndexWriter(LuceneUtil.getDirectory(),
//                LuceneUtil.getAnalyzer(),
//                LuceneUtil.getMaxFieldLength());
//        indexWriter.deleteAll();//核心
//        indexWriter.close();
//    }
//
//    public void findAllByKeywords() throws Exception {
//        //准备工作
//        String keywords = "唯品会";
//        BaseAppOpt.findIndexDB(keywords);
//    }
//
//
//    public static void main(String[] args) throws Exception {
//        CurdArticleDao curdArticleDao = new CurdArticleDao();
//        //单条
//        //curdArticleDao.add();
//        //多条
//        //curdArticleDao.addAll();
//        //更新
//        //curdArticleDao.update();
//        //删除
//        //curdArticleDao.delete();
//        //删除所有
//        curdArticleDao.deleteAll();
//        //查找符合条件的文档
//        curdArticleDao.findAllByKeywords();
//
//    }
//
//
//}

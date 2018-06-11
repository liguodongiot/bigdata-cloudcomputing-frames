package com.lgd.lucene;

import com.lgd.lucene.entity.HtmlBean;
import com.lgd.lucene.entity.HtmlBeanUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>Project: bigdata-frames</p>
 * <p>Package: com.lgd.lucene</p>
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author guodong.li
 * @version 1.0.0
 * @date 2018/6/8
 */
@Service
public class LuceneService {

    public static final String indexDir = "D:\\highcharts_index";
    public static final String dataDir = "D:\\highcharts";

    public void createIndex() throws IOException {
        File indexFile = new File(indexDir);

//        if(indexFile.exists()){
//            boolean delete = indexFile.delete();
//            boolean mkdirs = indexFile.mkdirs();
//        }

        Directory dir = FSDirectory.open(indexFile.toPath());
        Analyzer analyzer = new StandardAnalyzer();

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

        IndexWriter writer = new IndexWriter(dir, config);
        File file = new File(dataDir);
        // 遍历文件
        Collection<File> files = FileUtils.listFiles(file, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);

        RAMDirectory rdir = new RAMDirectory();
        IndexWriterConfig ramConfig = new IndexWriterConfig(analyzer);
        ramConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter ramWriter = new IndexWriter(rdir, ramConfig);

        int i = 0;
        List<Document>  list = new ArrayList<>();
        for (File f : files) {

            HtmlBean htmlBean = HtmlBeanUtil.parseHtml(f);
            Document doc = new Document();
            doc.add(new Field("title", htmlBean.getTitle()==null?"":htmlBean.getTitle(), TextField.TYPE_STORED));
            doc.add(new Field("content", htmlBean.getContent(), TextField.TYPE_STORED));
            doc.add(new Field("url", htmlBean.getUrl(), StringField.TYPE_STORED));
            //list.add(doc);
            ramWriter.addDocument(doc);
            i++;
            if(i==2){
                //writer.addDocuments(list);
                //list.clear();
                ramWriter.close();
                String[] listAll = rdir.listAll();
                writer.addIndexes(rdir);
                rdir=new RAMDirectory();
                IndexWriterConfig tempConf = new IndexWriterConfig(analyzer);
                tempConf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
                ramWriter = new IndexWriter(rdir,tempConf);
                i=0;
            }
        }
//        if(!list.isEmpty()){
//            writer.addDocuments(list);
//        }
        String[] listAll = rdir.listAll();
        if(listAll !=null && listAll.length!=0){
            ramWriter.close();
            writer.addIndexes(rdir);
        }
        writer.close();
        dir.close();


    }


    public void searchIndex(String ask){
        try {
            Path path = Paths.get(indexDir);
            Directory dir = FSDirectory.open(path);

            IndexReader reader = DirectoryReader.open(dir);

            IndexSearcher searcher = new IndexSearcher(reader);

            StandardAnalyzer standardAnalyzer = new StandardAnalyzer();

            QueryParser qp = new QueryParser("title",standardAnalyzer);
            Query query = qp.parse(ask);

            TopDocs search = searcher.search(query, 10);

            ScoreDoc[] scoreDocs = search.scoreDocs;
            System.out.println(search.totalHits);
            List<HtmlBean> htmlBeanList = new ArrayList<>();
            for(ScoreDoc sc:scoreDocs){
                int docId = sc.doc;
                Document document = reader.document(docId);
                System.out.println(document.get("url"));
                SimpleHTMLFormatter sf = new SimpleHTMLFormatter("<font color=\"red\">", "</font>");
                QueryScorer qs = new QueryScorer(query, "title");
                Highlighter highlighter = new Highlighter(sf, qs);
                String title = highlighter.getBestFragment(new StandardAnalyzer(),"title", document.get("title"));
                String content = highlighter.getBestFragment(new StandardAnalyzer(),"content", document.get("content"));
                String url = document.get("url");
                HtmlBean hb = new HtmlBean();
                hb.setContent(content);
                hb.setTitle(title);
                hb.setUrl(url);
                htmlBeanList.add(hb);
            }
            System.out.println(htmlBeanList.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

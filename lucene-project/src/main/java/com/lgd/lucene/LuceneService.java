package com.lgd.lucene;

import com.lgd.lucene.entity.HtmlBean;
import com.lgd.lucene.entity.HtmlBeanUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

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

        Directory dir = FSDirectory.open(indexFile.toPath());
        Analyzer analyzer = new StandardAnalyzer();

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

        IndexWriter writer = new IndexWriter(dir, config);
        File file = new File(dataDir);
        // 遍历文件
        Collection<File> files = FileUtils.listFiles(file, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        for (File f : files) {

            HtmlBean htmlBean = HtmlBeanUtil.parseHtml(f);
            Document doc = new Document();
            doc.add(new Field("title", htmlBean.getTitle()==null?"":htmlBean.getTitle(), TextField.TYPE_STORED));
            doc.add(new Field("content", htmlBean.getContent(), TextField.TYPE_STORED));
            doc.add(new Field("url", htmlBean.getUrl(), StringField.TYPE_STORED));
            writer.addDocument(doc);
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
            for(ScoreDoc sc:scoreDocs){
                int docId = sc.doc;
                Document document = reader.document(docId);
                System.out.println(document.get("url"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

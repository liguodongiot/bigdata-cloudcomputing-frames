package com.lgd.lucene.index;

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
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

/**
 * Describe:
 * author: guodong.li
 * datetime: 2017/6/21 16:36
 */

public class BaseTest {

    private static final String indexDir="D:\\lucene\\index";
    private static final String dataDir="D:\\lucene\\data";

    /**
     * 索引
     */
    @Test
    public void createIndex(){
        try {
            File indexFile = new File(indexDir);
            Directory dir = FSDirectory.open(indexFile.toPath());

            Analyzer analyzer = new StandardAnalyzer();

            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

            IndexWriter writer = new IndexWriter(dir,config);
            File file = new File(dataDir);
            // 遍历文件
            Collection<File> files = FileUtils.listFiles(file, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
            for(File f : files){
                Document doc = new Document();
                doc.add(new Field("title",  f.getName(), TextField.TYPE_STORED));
                doc.add(new Field("content", FileUtils.readFileToString(f, StandardCharsets.UTF_8),TextField.TYPE_STORED));
                doc.add(new LongPoint("totalSpace", f.getTotalSpace()));
                writer.addDocument(doc);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 搜索
     */
    @Test
    public void search(){
        try {
            Path path = Paths.get(indexDir);
            Directory dir = FSDirectory.open(path);

            IndexReader reader = DirectoryReader.open(dir);

            IndexSearcher searcher = new IndexSearcher(reader);

            StandardAnalyzer standardAnalyzer = new StandardAnalyzer();

            QueryParser qp = new QueryParser("content",standardAnalyzer);
            Query query = qp.parse("python"); //java

            TopDocs search = searcher.search(query, 10);

            ScoreDoc[] scoreDocs = search.scoreDocs;
            for(ScoreDoc sc:scoreDocs){
                int docId = sc.doc;
                Document document = reader.document(docId);
                System.out.println(document.get("title"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}

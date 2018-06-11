package com.lgd.lucene;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.Random;

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
@RestController
public class LuceneController {

    @Autowired
    private LuceneService service;

    @RequestMapping("create")
    public String createIndex() throws IOException {
        File file =new File(LuceneService.indexDir);
        if(file.exists()){
            //file.delete();
            deleteDir(file);
            file.mkdirs();
        }
        service.createIndex();
        return "create"+ new Random().nextInt(100);
    }

    // http://localhost:8080/search?ask=Highcharts
    @RequestMapping("search")
    public String searchIndex(String ask) {
        service.searchIndex(ask);
        return "search"+ new Random().nextInt(100);
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

}

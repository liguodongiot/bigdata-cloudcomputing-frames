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
            file.delete();
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


}

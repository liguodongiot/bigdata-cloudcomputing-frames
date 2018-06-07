package com.lgd.lucene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Describe:
 *
 * @author: liguodong
 * @datetime: 2017/6/11 19:20
 */
@RestController
public class DemoController {

    private final static Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(){
        LOGGER.info("测试日志。。。。");
        return "hello springboot!";
    }

}

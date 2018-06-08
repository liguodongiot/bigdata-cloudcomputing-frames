package com.lgd.lucene.entity;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.File;
import java.io.IOException;

/**
 * <p>Project: bigdata-frames</p>
 * <p>Package: com.lgd.lucene.entity</p>
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author guodong.li
 * @version 1.0.0
 * @date 2018/6/8
 */

public class HtmlBeanUtil {

    public static HtmlBean parseHtml(File file){
        HtmlBean hb = new HtmlBean();
        try {
            Source sc = new Source(file);
            Element title = sc.getFirstElement(HTMLElementName.TITLE);

            String content = sc.getTextExtractor().toString();

            hb.setContent(content);
            hb.setTitle(title.getTextExtractor().toString());
            String path = file.getAbsolutePath();
            path = path.substring(3).replaceAll("\\\\", "/");
            hb.setUrl("http://"+path);
            return hb;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hb;
    }
}

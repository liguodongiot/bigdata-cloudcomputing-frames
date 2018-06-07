package com.lgd.lucene.simple;

import java.io.File;
import java.io.FileFilter;

/**
 * Describe:
 *      用于为.txt文件过滤器
 * @author: liguodong
 * @datetime: 2017/6/10 15:59
 */
public class TextFileFilter implements FileFilter {

    public boolean accept(File pathname) {
        return pathname.getName().toLowerCase().endsWith(".txt");
    }

}

package com.lgd.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.SecurityUtil;
import org.apache.hadoop.security.UserGroupInformation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Project: bigdata-frames</p>
 * <p>Package: com.lgd.hadoop.hdfs</p>
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author guodong.li
 * @version 1.0.0
 * @date 2018/6/1
 */




public class HdfsKerberos {
    public static final String KEYTAB_FILE_KEY = "hdfs.keytab.file";
    public static final String USER_NAME_KEY = "hdfs.kerberos.principal";
    private static Configuration conf = new Configuration();


    public static void main(String[] args) throws IOException {
        System.setProperty("hadoop.home.dir", "D:\\hadoop-2.6.5");
        //设置系统的Kerberos配置信息
        System.setProperty("java.security.krb5.conf","D:/impala/krb5.conf");

        //keytab文件的路径
        conf.set(KEYTAB_FILE_KEY, "D:/impala/finance.keytab");
        //principal
        conf.set(USER_NAME_KEY, "finance");

        login(conf);


        System.out.println(loadHdfsFile("/user/hive/warehouse/ai_data.db/test_hive/part-m-00000"));
    }

    public static List<String> loadHdfsFile(String filePath){
        List<String> resultList = new ArrayList<>();
        FileSystem fileSystem = null;
        try {
            fileSystem = FileSystem.get(conf);
            FSDataInputStream fs = fileSystem.open(new Path(filePath));
            BufferedReader bis = new BufferedReader(new InputStreamReader(fs,"UTF-8"));
            String line;
            while ((line = bis.readLine()) != null) {
                resultList.add(line);
            }
            fileSystem.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static void login(Configuration hdfsConfig) throws IOException {
        boolean securityEnabled = UserGroupInformation.isSecurityEnabled();
        if (securityEnabled) {
            String keytab = conf.get(KEYTAB_FILE_KEY);
            if (keytab != null) {
                hdfsConfig.set(KEYTAB_FILE_KEY, keytab);
            }
            String userName = conf.get(USER_NAME_KEY);
            if (userName != null) {
                hdfsConfig.set(USER_NAME_KEY, userName);
            }
            SecurityUtil.login(hdfsConfig, KEYTAB_FILE_KEY, USER_NAME_KEY);
        }
    }
}
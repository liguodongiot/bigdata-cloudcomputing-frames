package com.lgd.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by liguodong on 2017/3/23.
 */
public class HdfsApp {

    private final static String PATH = "hdfs://ubuntu:8020";
    private final static  String DIR = "/";

    public static void main(String[] args) throws Exception{
        final FileSystem fileSystem = getFileSystem();

        //浏览文件夹
        browseFile(fileSystem);
    }
    public static FileSystem getFileSystem() throws IOException, URISyntaxException {
        return FileSystem.get(new URI(PATH), new Configuration());
    }

    public static void browseFile(FileSystem fileSystem) throws IOException
    {
        //final FileStatus[] listStatus = fileSystem.listStatus(new Path("/"));
        final FileStatus[] listStatus = fileSystem.listStatus(new Path(DIR));

        for (FileStatus fileStatus : listStatus) {
            String isDir = fileStatus.isDir()?"文件夹":"文件";
            final String permission = fileStatus.getPermission().toString();//权限
            final short replication = fileStatus.getReplication();//副本
            final long len = fileStatus.getLen();
            final String path = fileStatus.getPath().toString();//路径
            System.out.println(isDir+"\t"+permission+"\t"+replication+"\t"+len+"\t"+path);

        }
    }

}

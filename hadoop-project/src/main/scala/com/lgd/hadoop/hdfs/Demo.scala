package com.lgd.hadoop.hdfs

import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileStatus, FileSystem, Path}

/**
  * Created by liguodong on 2017/3/23.
  */
object Demo {

  private val PATH = "hdfs://ubuntu:8020"
  private val DIR = "/"

  def main(args: Array[String]): Unit = {

    val fileSystem:FileSystem =  HdfsApp.getFileSystem
    HdfsApp.browseFile(fileSystem)

    browseFile(getFileSystem)
  }

  def getFileSystem: FileSystem = {
    FileSystem.get(new URI(PATH), new Configuration())
  }

  def browseFile(fileSystem: FileSystem): Unit ={
    val listStatus: Array[FileStatus] = fileSystem.listStatus(new Path(DIR))

    listStatus.map(
      fileStatus=>{
        val isDir = if (fileStatus.isDir) "文件夹" else "文件"
        //权限
        val permission: String  = fileStatus.getPermission.toString
        val replication : Short = fileStatus.getReplication//副本
        val len : Long  = fileStatus.getLen
        val path : String  = fileStatus.getPath.toString//路径

        isDir+"\t"+permission+"\t"+replication+"\t"+len+"\t"+path
      }
    ).foreach(println)
  }


}

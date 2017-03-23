package com.lgd.hadoop.mr

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat

/**
  * Created by liguodong on 2017/3/23.
  */

class WordCount{

  def submitJob(conf:Configuration): Unit ={
    val job = Job.getInstance(conf, "word count")
    job.setJarByClass(classOf[WordCount])
    job.setMapperClass(classOf[WordCountMapper])
    job.setCombinerClass(classOf[WordCountReducer])
    job.setReducerClass(classOf[WordCountReducer])
    job.setOutputKeyClass(classOf[Text])
    job.setOutputValueClass(classOf[IntWritable])

    FileInputFormat.addInputPath(job, new Path(WordCount.INPUT))
    FileOutputFormat.setOutputPath(job, new Path(WordCount.OUTPUT))
    System.exit(if (job.waitForCompletion(true)) 0 else 1)
  }

}

object WordCount {

  private val INPUT = "hdfs://ubuntu:8020/input/"
  private val OUTPUT = "hdfs://ubuntu:8020/output"

  def main(args: Array[String]): Unit = {
    val conf = new Configuration

    WordCountApp.deleteDir(conf, OUTPUT)

    val wordCount = new WordCount
    wordCount.submitJob(conf)
  }
}







package com.lgd.hadoop.mr

import java.io.IOException
import java.util.StringTokenizer

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Mapper


/**
  * Created by liguodong on 2017/3/23.
  */
class WordCountMapper extends Mapper[Any, Text, Text, IntWritable] {
  private val word = new Text
  private val one = new IntWritable(1)

  @throws[IOException]
  @throws[InterruptedException]
  override def map(key: Any, value: Text, context: Mapper[Any,Text,Text,IntWritable]#Context)  {
    val itr = new StringTokenizer(value.toString)
    while (itr.hasMoreTokens) {
      word.set(itr.nextToken)
      context.write(word, one)
    }
  }
}

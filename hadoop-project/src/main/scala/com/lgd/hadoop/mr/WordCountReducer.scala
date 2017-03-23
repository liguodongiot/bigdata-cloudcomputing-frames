package com.lgd.hadoop.mr

import java.lang.Iterable

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Reducer


/**
  * Created by liguodong on 2017/3/23.
  */

class WordCountReducer extends Reducer[Text, IntWritable, Text, IntWritable] {
  private val result = new IntWritable

  override def reduce(key: Text, values: Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {
    var sum = 0

    val iteratorValues = values.iterator()
    while(iteratorValues.hasNext){
      sum += iteratorValues.next().get()
    }

    result.set(sum)
    context.write(key, result)
  }
}

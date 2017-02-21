package com.lgd.spark.quota

import java.text.SimpleDateFormat
import java.util.Calendar

/**
  * Created by liguodong on 2017/2/21.
  */
object TimeUtils {

  val simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  val calendar = Calendar.getInstance()

  def apply(time: String) = {
    calendar.setTime(simpleDateFormat.parse(time))
    calendar.getTimeInMillis
  }

  def getCertainDayTime(amount: Int): Long ={
    calendar.add(Calendar.DATE, amount)
    val time = calendar.getTimeInMillis
    calendar.add(Calendar.DATE, -amount)
    time
  }
}

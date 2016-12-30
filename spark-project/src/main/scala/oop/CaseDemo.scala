package oop

/**
  * Created by liguodong on 2016/12/30.
  */
object CaseDemo {

  def main(args: Array[String]): Unit = {

    val userInfoTask1 = UserInfoTask(1,"li")
    val userInfoTask2 = UserInfoTask(2,"kiki")

    println(userInfoTask1+"[:::::]"+userInfoTask2)

    val timeOutTask1 = TimeOutTask
    val timeOutTask2 = TimeOutTask

    println(timeOutTask1+"[:::::]"+timeOutTask2)


  }

}

case class UserInfoTask(id:Int,name:String)

case object TimeOutTask




package actor

import scala.actors.Actor

/**
  * 2.10版本及之前的版本
  * Created by liguodong on 2016/12/30.
  */
object MyActorTest extends App{
  //启动Actor
  MyActor1.start()
  MyActor2.start()

}

object MyActor1 extends Actor{
  //重新act方法
  def act(){
    for(i <- 1 to 10){
      println("actor-1 " + i)
      Thread.sleep(2000)
    }
  }
}

object MyActor2 extends Actor{
  //重新act方法
  def act(){
    for(i <- 1 to 10){
      println("actor-2 " + i)
      Thread.sleep(2000)
    }
  }
}
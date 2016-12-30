package actor

import scala.actors.{Actor, Future}
import scala.collection.mutable.{HashSet, ListBuffer}
import scala.io.Source

/**
  * Actor方式统计单词
  * 读取目录文件统计单词数据
  * Created by liguodong on 2016/12/30.
  */
object ActorWordCount {

  def main(args: Array[String]): Unit = {
    val replySet = new HashSet[Future[Any]]()
    val resultList = new ListBuffer[ResultTask]()
    val files = Array[String]("d://word.txt","d://wo.txt")

    for(f <- files){
      val actor = new Task
      //!!表示异步发送
      val reply = actor.start() !! SubmitTask(f)
      replySet += reply
      //actor.start() !! StopTask
    }

    while(replySet.size>0){
      //任务完成的future
      val toCompute = replySet.filter(_.isSet)

      for(f <- toCompute){
        val  result = f.apply().asInstanceOf[ResultTask]
        resultList += result
        //移除
        replySet -= f
      }
      Thread.sleep(1000)
    }

    //汇总
    //List((hello,3),(hi,4), (hello,2),(jetty,5))
    val fr = resultList.flatMap(_.result).groupBy(_._1).mapValues(_.foldLeft(0)(_+_._2))
    println(fr)

  }


}

case class SubmitTask(fileName:String)
case class ResultTask(result : Map[String,Int])
case object StopTask

//每个文件单词出现的次数
class Task extends Actor{

  override def act(): Unit = {
    loop{
      react{
        case SubmitTask(fileName) => {
          val result = Source.fromFile(fileName).getLines().flatMap(_.split(" ")).map((_,1)).toList.groupBy(_._1).mapValues(_.size)
          sender ! ResultTask(result)
        }
        case StopTask =>{
          exit()
        }
      }
    }
  }


}

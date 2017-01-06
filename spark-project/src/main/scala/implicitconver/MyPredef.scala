package implicitconver

import java.io.File

/**
  * Created by liguodong on 2017/1/5.
  */
object MyPredef {

  implicit def fileToRichFile(f: File) = new RichFile(f)

//  implicit def girl2Ordered(g:Girl) = new Ordered[Girl]{
//    override def compare(that: Girl): Int = g.faceValue-that.faceValue
//  }

//  implicit val girl2Ordered = (g:Girl) => new Ordered[Girl]{
//    override def compare(that: Girl): Int = g.faceValue-that.faceValue
//  }

//  implicit val girl2Ordered =  new Ordering[Girl]{
//    override def compare(x: Girl, y: Girl): Int = x.faceValue-y.faceValue
//  }

  trait GirlOrdering extends Ordering[Girl]{
    override def compare(x: Girl, y: Girl): Int = x.faceValue-y.faceValue
  }
  implicit object Girl extends GirlOrdering

}

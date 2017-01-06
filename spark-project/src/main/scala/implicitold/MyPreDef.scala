package implicitold

/**
  * Created by liguodong on 2017/1/6.
  */
object MyPreDef {

  implicit def girlToOrdered(girl: Girl) = new Ordered[Girl]{
    override def compare(that: Girl): Int = {
      if(girl.faceValue == that.faceValue) {
        girl.size - that.size
      } else {
        girl.faceValue - that.faceValue
      }
    }
  }

//implicit val girlToOrdered = (girl: Girl) => new Ordered[Girl]{
//  override def compare(that: Girl): Int = {
//    if(girl.faceValue == that.faceValue) {
//      girl.size - that.size
//    } else {
//      girl.faceValue - that.faceValue
//    }
//  }
//}


  implicit object girlOrdering extends Ordering[Girl] {
    override def compare(x: Girl, y: Girl): Int = {
      if(x.faceValue == y.faceValue) {
        x.size - y.size
      } else {
        x.faceValue - y.faceValue
      }
    }
  }


//  implicit val girlOrdering = new Ordering[Girl] {
//    override def compare(x: Girl, y: Girl): Int = {
//      if(x.faceValue == y.faceValue) {
//        x.size - y.size
//      } else {
//        x.faceValue - y.faceValue
//      }
//    }
//  }

}

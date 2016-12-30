package fp

/**
  * Created by liguodong on 2016/12/28.
  */
object practiceMain extends App{

  //创建一个List
  val lst0 = List(1,7,9,8,0,3,5,4,6,2)

  //将lst0中每个元素乘以10后生成一个新的集合
  val lst2 = lst0.map(_*10)

  //将lst0中的偶数取出来生成一个新的集合
  val lst3 = lst0.filter(_%2==0)

  //将lst0排序后生成一个新的集合
  val lst4 = lst0.sortWith(_<_)
  val lst5 = lst0.sorted
  //奇数在前，偶数在后
  val lst6 = lst0.sortBy(_%2==0)
  println(lst6)

  //反转顺序
  val lst7 = lst0.reverse

  //将lst0中的元素4个一组,类型为Iterator[List[Int]]
  val lst8 = lst0.grouped(4)
  println(":::"+lst8)
  //将Iterator转换成List
  val lst9 = lst8.toList
  println(lst9)

  //将多个list压扁成一个List
  val lst10 = lst9.flatten
  println(lst10)

  val lines = List("hello tom hello jerry", "hello jerry", "hello kitty")
  //先按空格切分，在压平
  val lines2 = lines.flatMap(_.split(" "))
  val lines3 = lines.map(_.split(" ")).flatten
  println(lines2)
  println(lines3)


  //单词统计
  val words = lines.flatMap(_.split(" "))

  //val wordsAndOne = words.map(_=>(_,1))
  val wordsAndOne = words.map((_,1))
  println(wordsAndOne)
  val grouped = wordsAndOne.groupBy(_._1)
  println(grouped)

  val result = grouped.map(t => (t._1,t._2.size))
  println(result)

  val resultSort = result.toList.sortBy(_._2).reverse
  println(resultSort)
  //降序
  println(result.toList.sortWith(_._2>_._2))

  val wordConnt = lines.flatMap(_.split(" ")).map((_,1)).groupBy(_._1).map(t => (t._1,t._2.size)).toList.sortWith(_._2>_._2)
  println("Word count:"+wordConnt)

  //mapValues 对value进行处理，key不变
  val wordConnt2 = lines.flatMap(_.split(" ")).map((_,1)).groupBy(_._1).mapValues(_.size).toList.sortWith(_._2>_._2)
  println("Word count:"+wordConnt2)

  val a = Array(1,2,3,4,5,6)
  println(a.sum)
  //reduce 默认调用reduceLeft
  println(a.reduce(_+_))
  println(a.reduce(_-_))

  //5-6
  println(a.reduceRight(_-_))

  println(a.par.reduce(_+_))

  println(a.fold(10)(_+_))

  println(a.par.fold(0)(_+_))
  //CPU每一个核都有一个初始值
  println(a.par.fold(10)(_+_))

  //从左往右
  println(a.foldLeft(10)(_+_))

  println("grouged:"+grouped)
  //第一个下划线表示的是List  第二个下划线表示是初始值或累加值，第三个下划线表示元组的第二个元素
  println(grouped.mapValues(_.foldLeft(0)(_+_._2)))




  //化简：reduce
  //将非特定顺序的二元操作应用到所有元素
  val ls0 = List(1,2,3,4,5)
  ls0.par.sum

  ls0.par.reduce(_+_)

  //按照特定的顺序
  ls0.reduceLeft(_+_)


  //折叠：有初始值（无特定顺序）
  lst0.par.fold(0)(_+_)

  //折叠：有初始值（有特定顺序）
  lst0.foldLeft(100)((x,y)=>x+y)
  lst0.foldLeft(0)(_+_)

  //聚合
  val arr = List(List(1, 2, 3), List(3, 4, 5), List(2), List(0))

  //第一个是先将里面元素求和，第二个函数再将外面求和
  //val result2 = arr.aggregate(0)(_+_.sum,_+_)

  val result2 = arr.aggregate(0)(_+_.reduce(_+_),_+_)
  println(result2)

  val result3 = arr.aggregate(10)(_+_.reduce(_+_),_+_)
  println(result3)
  val result4 = arr.par.aggregate(10)(_+_.reduce(_+_),_+_)
  println(result4)

  val l1 = List(5,6,4,7)
  val l2 = List(1,2,3,4)

  //求并集
  val r1 = l1.union(l2)

  //求交集
  val r2 = l1.intersect(l2)

  //求差集
  val r3 = l1.diff(l2)
  println(r3)



  // val lines = List("hello tom hello jerry", "hello jerry", "hello kitty")
  //lines.flatMap(_.split(" ")).map((_, 1)).groupBy(_._1).mapValues(_.foldLeft(0)(_+_._2))
  //lines.flatMap(_.split(" ")).map((_, 1)).groupBy(_._1).map(t=>(t._1, t._2.size)).toList.sortBy(_._2).reverse

}


ImplicitValue

RichFile

泛型

T : M  上下文界定  M是一个泛型类型.如：class Pair[T : Ordering]

[T <: UpperBound]       上界

[T >: LowerBound]       下界

[T <% ViewBound]        视图界定

[T : ContextBound]      上下文界定

[+T] 协变

[-T] 逆变


上界

Pair

---

排序

Java的方式

Boy

scala的方式

Chooser

---

```
trait IntOrdering extends Ordering[Int] {
    def compare(x: Int, y: Int) =
      if (x < y) -1
      else if (x == y) 0
      else 1
}
implicit object Int extends IntOrdering

```
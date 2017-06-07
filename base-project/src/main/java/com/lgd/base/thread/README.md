
### Executors 线程池创建的几种方式
**ExecutorsMain**

### volatile
```
用volatile修饰的变量，线程在每次使用变量的时候，都会读取变量修改后的最新的值。
volatile很容易被误用，用来进行原子性操作。

与锁相比，Volatile 变量是一种非常简单但同时又非常脆弱的同步机制，
它在某些情况下将提供优于锁的性能和伸缩性。
如果严格遵循 volatile 的使用条件（即变量真正独立于其他变量和自己以前的值）
—— 在某些情况下可以使用 volatile 代替 synchronized 来简化代码。
然而，使用 volatile 的代码往往比使用锁的代码更加容易出错。
```
**VolatileMain**

### 阻塞队列
```
1)ArrayBlockingQueue:
规定大小的BlockingQueue,其构造函数必须带一个int参数来指明其大小.
其所含的对象是以FIFO(先入先出)顺序排序的.

2)LinkedBlockingQueue:
大小不定的BlockingQueue,若其构造函数带一个规定大小的参数,
生成的BlockingQueue有大小限制,若不带大小参数,所生成的BlockingQueue的大小由Integer.MAX_VALUE来决定.
其所含的对象是以FIFO(先入先出)顺序排序的

3)PriorityBlockingQueue:
类似于LinkedBlockQueue,
但其所含对象的排序不是FIFO,而是依据对象的自然排序顺序或者是构造函数的Comparator决定的顺序.

4)SynchronousQueue:
特殊的BlockingQueue,对其的操作必须是放和取交替完成的.

其中LinkedBlockingQueue和ArrayBlockingQueue比较起来,它们背后所用的数据结构不一样,
导致LinkedBlockingQueue的数据吞吐量要大于ArrayBlockingQueue,
但在线程数量很大时其性能的可预见性低于ArrayBlockingQueue.
```
**BlockingQueueMain**


### ForkJoin批处理分页数据
**BatchPageForkJoinMain**



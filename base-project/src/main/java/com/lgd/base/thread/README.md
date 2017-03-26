
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
**BlockingQueueMain**


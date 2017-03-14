


```
线程资源必须通过线程池提供，不允许在应用中自行显式创建线程。
线程池不允许使用 Executors 去创建，而是通过 ThreadPoolExecutor 的方式，这样
的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。

说明： Executors 返回的线程池对象的弊端如下：
1） FixedThreadPool 和 SingleThreadPool:
允许的请求队列长度为 Integer.MAX_VALUE，可能会堆积大量的请求，从而导致 OOM。
2） CachedThreadPool 和 ScheduledThreadPool:
允许的创建线程数量为 Integer.MAX_VALUE， 可能会创建大量的线程，从而导致 OOM。




ThreadPoolExecutor配置

ThreadPoolExecutor(int corePoolSize, int maximumPoolSize,long keepAliveTime, TimeUnit unit,
BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler)

corePoolSize：  线程池中所保存的核心线程数。线程池启动后默认是空的，只有任务来临时才会创建线程以处理请求。
prestartAllCoreThreads方法可以在线程池启动后即启动所有核心线程以等待任务。

maximumPoolSize：线程池允许创建的最大线程数。
当workQueue使用无界队列时（如：LinkedBlockingQueue），则此参数无效。

keepAliveTime： 当前线程池线程总数大于核心线程数时，终止多余的空闲线程的时间。

unit： 终止多余的空闲线程的时间单位

workQueue： 工作队列，如果当前线程池达到核心线程数时（corePoolSize），
且当前所有线程都处于活动状态，则将新加入的任务放到此队列中。

下面仅列几个常用的：
ArrayBlockingQueue：　　基于数组结构的有界队列，此队列按FIFO原则对任务进行排序。
如果队列满了还有任务进来，则调用拒绝策略。

LinkedBlockingQueue：　基于链表结构的无界队列，此队列按FIFO原则对任务进行排序。
因为它是无界的，根本不会满，所以采用此队列后线程池将忽略拒绝策略（handler）参数；
同时还将忽略最大线程数（maximumPoolSize）等参数。

SynchronousQueue：　直接将任务提交给线程而不是将它加入到队列，实际上此队列是空的。
每个插入的操作必须等到另一个调用移除的操作；如果新任务来了线程池没有任何可用线程处理的话，则调用拒绝策略。
其实要是把maximumPoolSize设置成无界（Integer.MAX_VALUE）的，加上SynchronousQueue队列，
就等同于Executors.newCachedThreadPool()。

PriorityBlockingQueue：　具有优先级的队列的有界队列，可以自定义优先级；
默认是按自然排序，可能很多场合并不合适。

-handler：　拒绝策略，当线程池与workQueue队列都满了的情况下，对新加任务采取的策略。
当提交任务数超过maxmumPoolSize + workQueue之和时，任务会交给RejectedExecutionHandler来处理

AbortPolicy：　　  拒绝任务，抛出RejectedExecutionException异常。默认值。
CallerRunsPolicy
DiscardOldestPolicy：  如果执行程序尚未关闭，则位于工作队列头部的任务将被删除，
然后重试执行程序（如果再次失败，则重复此过程）。
这样的结果是最后加入的任务反而有可能被执行到，先前加入的都被抛弃了。
DiscardPolicy：　加不进的任务都被抛弃了，同时没有异常抛出。

threadFactory：	新建线程工厂

corePoolSize，maximumPoolSize，workQueue之间关系。

1.当线程池小于corePoolSize时，新提交任务将创建一个新线程执行任务，即使此时线程池中存在空闲线程。
2.当线程池达到corePoolSize时，新提交任务将被放入workQueue中，等待线程池中任务调度执行
3.当workQueue已满，且maximumPoolSize>corePoolSize时，新提交任务会创建新线程执行任务
4.当提交任务数超过maximumPoolSize时，新提交任务由RejectedExecutionHandler处理
5.当线程池中超过corePoolSize线程，空闲时间达到keepAliveTime时，关闭空闲线程
6.当设置allowCoreThreadTimeOut(true)时，线程池中corePoolSize线程空闲时间达到keepAliveTime也将关闭



一、ThreadPoolExcutor为一些Executor提供了基本的实现，这些Executor是由Executors中的工厂
newCahceThreadPool、newFixedThreadPool和newScheduledThreadExecutor返回的。
ThreadPoolExecutor是一个灵活的健壮的池实现，允许各种各样的用户定制。

二、线程的创建与销毁
1、核心池大小、最大池大小和存活时间共同管理着线程的创建与销毁。

2、核心池的大小是目标的大小；
线程池的实现试图维护池的大小；即使没有任务执行，池的大小也等于核心池的大小，并直到工作队列充满前，
池都不会创建更多的线程。如果当前池的大小超过了核心池的大小，线程池就会终止它。

3、最大池的大小是可同时活动的线程数的上限。

4、如果一个线程已经闲置的时间超过了存活时间，它将成为一个被回收的候选者。

5、newFixedThreadPool工厂为请求的池设置了核心池的大小和最大池的大小，而且池永远不会超时.

6、newCacheThreadPool工厂将最大池的大小设置为Integer.MAX_VALUE，核心池的大小设置为0，超时设置为一分钟。
这样创建了无限扩大的线程池，会在需求量减少的情况下减少线程数量。

三、管理
1、 ThreadPoolExecutor允许你提供一个BlockingQueue来持有等待执行的任务。
任务排队有3种基本方法：无限队列、有限队列和同步移交。

2、 newFixedThreadPool和newSingleThreadExectuor默认使用的是一个无限的 LinkedBlockingQueue。
如果所有的工作者线程都处于忙碌状态，任务会在队列中等候。
如果任务持续快速到达，超过了它们被执行的速度，队列也会无限制地增加。
稳妥的策略是使用有限队列，比如ArrayBlockingQueue或有限的LinkedBlockingQueue以及 PriorityBlockingQueue。

3、对于庞大或无限的池，可以使用SynchronousQueue，完全绕开队列，直接将任务由生产者交给工作者线程.
newCacheThreadPool默认使用的是SynchronousQueue.

4、可以使用PriorityBlockingQueue通过优先级安排任务.


四、总结：
1、用ThreadPoolExecutor自定义线程池，看线程是的用途，如果任务量不大，可以用无界队列，
如果任务量非常大，要用有界队列，防止OOM。
2、如果任务量很大，还要求每个任务都处理成功，要对提交的任务进行阻塞提交，重写拒绝机制，
改为阻塞提交。保证不抛弃一个任务。
3、最大线程数一般设为2N+1最好，N是CPU核数。
4、核心线程数，看应用，如果是任务，一天跑一次，设置为0，合适，因为跑完就停掉了，如果是常用线程池，
看任务量，是保留一个核心还是几个核心线程数。
5、如果要获取任务执行结果，用CompletionService，但是注意，获取任务的结果的要重新开一个线程获取，
如果在主线程获取，就要等任务都提交后才获取，
就会阻塞大量任务结果，队列过大OOM，所以最好异步开个线程获取结果。

```




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

corePoolSize： 线程池维护线程的最少数量
maximumPoolSize：线程池维护线程的最大数量
keepAliveTime： 线程池维护线程所允许的空闲时间
unit： 线程池维护线程所允许的空闲时间的单位
workQueue： 线程池所使用的缓冲队列
handler： 线程池对拒绝任务的处理策略

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

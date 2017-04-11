

### jconsole 
jconsole是一种集成了上面所有命令功能的可视化工具，可以分析jvm的内存使用情况和线程等信息。

**启动jconsole**

通过$JDK_HOME/bin目录下的“jconsole.exe”启动Jconsole后，将自动搜索出本机运行的所有JVM进程，
不需要用户使用jps来查询了，双击其中一个进程即可开始监控。
也可以“远程连接服务器，进行远程虚拟机的监控。”


### jvisualvm
提供了和jconsole的功能类似，提供了一大堆的插件。
插件中，Visual GC（可视化GC）还是比较好用的，可视化GC可以看到内存的具体使用情况。

```
1、线程私有的数据区域有：
 Java虚拟机栈（Java Stack）
本地方法栈（Native Stack）
2、线程共有的数据区域有：
堆（Java Heap）
方法区
```

### JVM参数列表
```
java -Xmx3550m -Xms3550m -Xmn2g -Xss128k -XX:NewRatio=4 -XX:SurvivorRatio=4 -XX:MaxPermSize=16m  -XX:MaxTenuringThreshold=0

-Xmx3550m：最大堆内存为3550M。
-Xms3550m：初始堆内存为3550m。
此值可以设置与-Xmx相同，以避免每次垃圾回收完成后JVM重新分配内存。

-Xmn2g：设置年轻代大小为2G。
整个堆大小=年轻代大小 + 年老代大小 + 持久代大小。
持久代一般固定大小为64m，所以增大年轻代后，将会减小年老代大小。
此值对系统性能影响较大，Sun官方推荐配置为整个堆的3/8。

-Xss128k：设置每个线程的堆栈大小。
JDK5.0以后每个线程堆栈大小为1M，在相同物理内存下，减小这个值能生成更多的线程。
但是操作系统对一个进程内的线程数还是有限制的，不能无限生成，经验值在 3000~5000左右。 

-XX:NewRatio=4:设置年轻代（包括Eden和两个Survivor区）与年老代的比值（除去持久代）。
设置为4，则年轻代与年老代所占比值为1：4，年轻代占整个堆栈的1/5

-XX:SurvivorRatio=8：设置年轻代中Eden区与Survivor区的大小比值。
设置为8，则两个Survivor区与一个Eden区的比值为2:8，一个Survivor区占整个年轻代的1/10

-XX:MaxPermSize=16m:设置持久代大小为16m。

-XX:MaxTenuringThreshold=0：设置垃圾最大年龄。
如果设置为0的话，则年轻代对象不经过Survivor区，直接进入年老代。
对于年老代比较多的应用，可以提高效率。如果将此值设置为一个较大值，则年轻代对象会在Survivor区进行多次复制，
这样可以增加对象 再年轻代的存活时间，增加在年轻代即被回收的概论。

收集器设置
-XX:+UseSerialGC:设置串行收集器
-XX:+UseParallelGC:设置并行收集器
-XX:+UseParalledlOldGC:设置并行年老代收集器
-XX:+UseConcMarkSweepGC:设置并发收集器

垃圾回收统计信息
-XX:+PrintGC
-XX:+PrintGCDetails
-XX:+PrintGCTimeStamps
-Xloggc:filename

并行收集器设置
-XX:ParallelGCThreads=n:设置并行收集器收集时使用的CPU数。并行收集线程数。
-XX:MaxGCPauseMillis=n:设置并行收集最大暂停时间
-XX:GCTimeRatio=n:设置垃圾回收时间占程序运行时间的百分比。公式为1/(1+n)

并发收集器设置
-XX:+CMSIncrementalMode:设置为增量模式。适用于单CPU情况。
-XX:ParallelGCThreads=n:设置并发收集器年轻代收集方式为并行收集时，使用的CPU数。并行收集线程数。
```
### 内存 
**OutOfMemoryMain**

### 线程
**ThreadSimulationMain**
**LockThreadMain**



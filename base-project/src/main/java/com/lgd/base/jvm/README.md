

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

### OutOfMemoryError异常总结 
```
JVM内存区域中，除了程序计数器外，
其他几个运行时区域都有可能发生OutOfMemoryError（OOM）异常。
本文对OOM异常进行总结，通过代码验证JVM规范中描述的运行时区域存储的内容；
了解可能导致这些区域OOM异常的代码，能够在工作中根据异常代码对内存哪一出区域进行定位。

基于Sun的HotSpot虚拟机，在Eclipse中Run/Arguments中可以进行虚拟机启动参数的设置，
这些参数对实验结果有直接影响，不能忽略。
```
#### Java堆溢出
**HeapOOMDemo**

```
Java堆用于存储对象实例，
只要不断地创建对象，并且保证GC Roots到对象之间有可达路径来避免垃圾回收机制清除这些对象，
对象数量达到最大堆容量限制，则发生溢出。

限制Java堆的大小为20MB，最小值和最大值相同（不可扩展）。
当虚拟机出现内存溢出异常时Dump出当前的内存堆转储快照以便事后分析。

java.lang.OutOfMemoryError: Java heap space
Dumping heap to java_pid7768.hprof ...
Heap dump file created [27987840 bytes in 0.142 secs]

这种异常比较常见，一般先通过内存映像分析工具（如Eclipse Memory Analyzer）
对Dump出来的堆转储快照进行分析，确实是内存泄露还是内存溢出。

内存泄露
查看泄露对象到GC Roots的引用链，定位泄露代码位置。

内存溢出
如果不存在泄露，即内存中的对象确实都还必须活着，检查JVM堆参数（-Xmx与-Xms），
调大参数，检查代码是否存在某些对象生命周期过长，持有状态过长的情况，减少程序运行期的内存消耗。

```
#### 虚拟机栈和本地方法栈溢出
**JavaVMStackSOFDemo**
**JavaVMStackOOMDemo**
```
HotSpot不区分虚拟机栈和本地方法栈，栈容量只能由-Xss参数设定。
StackOverFlow：线程申请的栈深度超过允许的最大深度
OutOfMemoryError： 虚拟机扩展时无法申请到足够的内存空间


StackOverFlow的情况：递归调用方法，定义大量的本地变量，增大此方法帧中本地变量表的长度。
stack length: 999
Exception in thread "main" java.lang.StackOverflowError
    at com.jvm.OutOfMemoryError.JavaVMStackSOF.stackLeak(JavaVMStackSOF.java:26)
    at com.jvm.OutOfMemoryError.JavaVMStackSOF.stackLeak(JavaVMStackSOF.java:26)
    at com.jvm.OutOfMemoryError.JavaVMStackSOF.stackLeak(JavaVMStackSOF.java:26)
    ......


OutOfMemoryError：多线程下的内存溢出，与栈空间是否足够大并不存在任何联系。
为每个线程的栈分配的内存越大（参数-Xss），那么可以建立的线程数量就越少，
建立线程时就越容易把剩下的内存耗尽，越容易内存溢出。
在这种情况下，如果不能减少线程数目或者更换64位虚拟机时，减少最大堆和减少栈容量能够换区更多的线程。

注意：Windows平台虚拟机中，Java的线程映射到操作系统的内核线程上执行，下面代码执行可能造成系统假死！

```

#### 方法区和运行常量池溢出 
```
运行时常量池
JDK1.7开始逐步“去永久代”，下面的讨论可以测试下实际影响。
String.intern()是一个Native方法，它的作用是：如果运行时常量池中已经包含一个等于此String对象内容的字符串，
则返回常量池中该字符串的引用；如果没有，则在常量池中创建与此String内容相同的字符串，
并返回常量池中创建的字符串的引用。JDK7的intern()方法的实现有所不同，
当常量池中没有该字符串时，不再是在常量池中创建与此String内容相同的字符串，
而改为在常量池中记录堆中首次出现的该字符串的引用，并返回该引用。

在JDK1.6之前，常量池分配在永久代，以下代码在JDK1.6下运行才回发生内存溢出，
如果在JDK1.7及其之后的版本运行，则是死循环：

Exception in thread "main" java.lang.OutOfMemoryError: PermGen space
    at java.lang.String.intern(Native Method)
    ......

方法区
方法区用于存放Class的相关信息，如果运行时产生大量的类去填满方法区，就可能发生方法区的内存溢出。 
例如主流框架spring、hibernate对大量的类进行增强时，利用CGLib字节码生成动态类；
大量JSP或动态JSP(JSP第一次运行时需要编译为Java类）。

下面为CGLib动态生成类导致的方法区溢出：    
Caused by: java.lang.OutOfMemoryError: PermGen space
    at java.lang.ClassLoader.defineClass1(Native Method)
    ......    
```

#### 本地直接内存溢出
**DirectMemoryOOMDemo**

```
Java虚拟机可以通过参数-XX:MaxDirectMemorySize设定本机直接内存可用大小，
如果不指定，则默认与java堆内存大小相同。
JDK中可以通过反射获取Unsafe类(Unsafe的getUnsafe()方法只有启动类加载器Bootstrap才能返回实例)
直接操作本机直接内存。
通过使用-XX:MaxDirectMemorySize=10M，限制最大可使用的本机直接内存大小为10MB。

Exception in thread "main" java.lang.OutOfMemoryError
at sun.misc.Unsafe.allocateMemory(Native Method)
at com.lgd.base.jvm.DirectMemoryOOMDemo.main(DirectMemoryOOMDemo.java:34)

这种异常的明显特征是在Heap Dump文件中看不见明显的异常。如果OOM之后Dump的文件比较小，
程序中直接或间接用到了IO/NIO，可以考虑是否是这方面的原因。
```


### 流式统计单词
**WordCount**

```
#模拟产生数据
nc -lk 8888
```

### 本地运行spark设置日志级别的2种方式

#### 1、通过配置文件
```
log4j.rootLogger=warn,console
# console
log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.Threshold=info
log4j.appender.console.ImmediateFlush=true
log4j.appender.console.Target=System.out
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=[%-5p] %d %m %x %n
```

#### 2、代码中设置
```
val sc: SparkContext = new SparkContext(sparkConf)
sc.setLogLevel("WARN")
//sc.setLogLevel("DEBUG")
//sc.setLogLevel("ERROR")
//sc.setLogLevel("INFO")
```

### 数据累加

**StatefulWordCount**


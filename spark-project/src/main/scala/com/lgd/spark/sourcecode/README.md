### spark启动流程
```
sbin/start-all.sh   ->  start-master.sh   -> start-slaves.sh

sbin/start-master.sh  ->  先读取变量  sbin/spark-daemon.sh start org.apache.spark.deploy.master.Master 1 --ip $SPARK_MASTER_IP --port $SPARK_MASTER_PORT --webui-port $SPARK_MASTER_WEBUI_PORT

sbin/spark-daemon.sh  ->  /bin/spark-class $command "$@"

/bin/spark-class   ->     exec "$RUNNER" -cp "$CLASSPATH" $JAVA_OPTS "$@"
```

`org.apache.spark.deploy.master.Master`
`org.apache.spark.deploy.worker.Worker`

---

### spark提交任务的过程
```
bin/spark-submit --class cn.itcast.spark.WordCount  --master spark://node-1.itcast.cn:7077 --executor-memory 2g --total-executor-cores 4

exec "$SPARK_HOME"/bin/spark-class org.apache.spark.deploy.SparkSubmit  -> exec "$RUNNER" -cp "$CLASSPATH" $JAVA_OPTS "$@"

spark-class org.apache.spark.deploy.SparkSubmit -> submit -> doRunMain （args class com.lgd.spark.WordCount ...）
--> Class.forName通过反射调用自定义类的main方法（只有一个进程）
```

`org.apache.spark.deploy.SparkSubmit`


---

SparkContext --> 执行主构造器 --> 1.创建了SparkEnv(创建了ActorSystem) --> 2. 创建TaskScheduler --> 3. 创建DAGScheduler
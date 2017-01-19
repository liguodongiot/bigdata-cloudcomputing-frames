

Spark SQL 2.1.0 文档
http://spark.apache.org/docs/latest/sql-programming-guide.html


### SqlContext 和 加载Json数据

SqlContextDemo

### SparkSession

SparkSessionDemo

SparkSessionRddToDf

### StructType 和 写入json格式数据到HDFS

不指定数据格式默认保存为Parquet格式（Parquet是面向分析型业务的列式存储格式）




---
### Hive On Spark的使用

```
如果lib中没有mysql驱动需要指定mysql驱动

/home/liguodong/install/spark/bin/spark-shell \
--master spark://ubuntu:7077 \
--executor-memory 1500m \
--total-executor-cores 1 \
--driver-class-path /home/liguodong/install/hive/lib/mysql-connector-java-5.1.35-bin.jar

#这种方式类似于 HIVE-CLI
/home/liguodong/install/spark/bin/spark-sql \
--master spark://ubuntu:7077\
--executor-memory 1g \
--total-executor-cores 1 \
--driver-class-path /home/liguodong/install/hive/lib/mysql-connector-java-5.1.35-bin.jar
```


HiveOnSparkDemo

```
/home/liguodong/install/spark/bin/spark-submit \
--class com.lgd.spark.sql.HiveOnSparkDemo \
--master spark://ubuntu:7077 \
--executor-memory 1024m \
--total-executor-cores 1 \
/home/liguodong/code/original-spark-1.0.jar
```


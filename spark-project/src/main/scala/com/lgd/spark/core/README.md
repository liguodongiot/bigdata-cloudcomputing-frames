

### 单词统计

WordCount

### 本地测试

ForeachDemo


### 计算用户在基站停留时间最长的两个小区
```
手机号，日期，基站，连接状态（1表示建立连接，0表示断开连接）
18688888888,20160327082400,16030401EAFB68F1E3CDF819735E1C66,1
18611132889,20160327082500,16030401EAFB68F1E3CDF819735E1C66,1
18688888888,20160327170000,16030401EAFB68F1E3CDF819735E1C66,0
18611132889,20160327180000,16030401EAFB68F1E3CDF819735E1C66,0
```

```
基站ID，经度，纬度，信号类型
9F36407EAD0629FC166F14DDE7970F68,116.304864,40.050645,6
CC0710CC94ECC657A8561DE549D940E0,116.303955,40.041935,6
16030401EAFB68F1E3CDF819735E1C66,116.296302,40.032296,6
```

MobileUserLocation

AdvMobileUserLocation


### 网站访问次数

UrlCount

AdvUrlCount

### 自定义分区

UrlCountPartition


### 自定义排序
```
方式一、自定义类继承Ordering 或者 Ordered
方式二、通过隐式转换
```
GoddessSort


### IP查找
```
1.0.1.0|1.0.3.255|16777472|16778239|亚洲|中国|福建|福州||电信|350100|China|CN|119.306239|26.075302
1.0.8.0|1.0.15.255|16779264|16781311|亚洲|中国|广东|广州||电信|440100|China|CN|113.280637|23.125178
1.0.32.0|1.0.63.255|16785408|16793599|亚洲|中国|广东|广州||电信|440100|China|CN|113.280637|23.125178
```
IpDemo

IpLocation

### JdbcRDD

JdbcRddDemo


### Datesets
```
Spark引入DataFrame，它可以提供high-level functions让Spark更好的处理结构数据的计算。
这让Catalyst optimizer 和Tungsten（钨丝） execution engine自动加速大数据分析。
发布DataFrame之后开发者收到了很多反馈，其中一个主要的是大家反映缺乏编译时类型安全。
为了解决这个问题，Spark采用新的Dataset API (DataFrame API的类型扩展)。
Dataset API扩展DataFrame API支持静态类型和运行已经存在的Scala或Java语言的用户自定义函数。
对比传统的RDD API，Dataset API提供更好的内存管理，特别是在长任务中有更好的性能提升。
```

**DatasetsMain**

**DatasetsSpark2Main**
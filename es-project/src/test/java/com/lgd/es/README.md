

### 反射方式初始化
**ReflexTest**

#### 设置分片数和副本数
**testShardAndReplicas()**

```
setting：通过setting可以更改es配置可以用来修改副本数和分片数。

# 1、查看
# 通过curl或浏览器查看副本分片信息
curl -XGET http://10.250.140.215:9200/liguodong/_settings?pretty
{
  "liguodong" : {
    "settings" : {
      "index" : {
        "creation_date" : "1495679177083",
        "number_of_shards" : "4",
        "number_of_replicas" : "0",
        "uuid" : "VTsw4_8TR_O_7JplzQG-Og",
        "version" : {
          "created" : "5020099"
        },
        "provided_name" : "liguodong"
      }
    }
  }
}


# 2、修改
不存在索引liguodong时可以指定副本和分片，如果shb03已经存在则只能修改副本
curl -XPUT http://10.250.140.215:9200/liguodong -d '{"settings":{"number_of_shards":4,"number_of_replicas":2}}'


liguodong已经存在不能修改分片
curl -XPUT http://10.250.140.215:9200/liguodong/_settings -d '{"index":{"number_of_replicas":2}}'


curl -XGET 'http://10.250.140.215:9200/liguodong/_search?pretty'
{
  "took" : 2,
  "timed_out" : false,
  "_shards" : {
    "total" : 4,
    "successful" : 4,
    "failed" : 0
  },
  "hits" : {
    "total" : 0,
    "max_score" : null,
    "hits" : [ ]
  }
}
```

```
mapping：我们在es中添加索引数据时不需要指定数据类型，
es中有自动影射机制，字符串映射为string，数字映射为long。
通过mappings可以指定数据类型是否存储等属性。


1：查看mapping信息
curl -XGET http://10.250.140.215:9200/liguodong/_mappings?pretty

2：修改，通过mappings还可以指定分词器
"analyzer": "ik_max_word",
            "search_analyzer": "ik_max_word"

操作不存在的索引
curl -XPUT http://172.22.1.133:9200/koko

curl -XPOST http://172.22.1.133:9200/koko/emp/_mapping -d '{"properties":{"name":{"type":"text","analyzer":"ik_max_word","searchAnalyzer": "ik_max_word"}}}'

curl -XPUT http://172.22.1.133:9200/koko -d'{"mappings":{"emp":{"properties":{"name":{"type":"string","analyzer":"ik_max_word","searchAnalyzer": "ik_max_word"}}}}}'

操作已存在的索引

curl -XPOST http://172.22.1.133:9200/koko/emp/_mapping -d '{"properties":{"name":{"type":"text","analyzer":"ik_max_word"}}}'
curl -XPOST http://172.22.1.133:9200/koko/stu/_mapping -d '{"properties":{"name":{"type":"text","analyzer":"ik_max_word","search_analyzer": "ik_max_word"}}}'


curl -XGET http://172.22.1.133:9200/koko/_mappings?pretty



```


```
elasticsearch官方只提供smartcn这个中文分词插件，效果不是很好

curl 'http://10.250.140.215:9200/liguodong/_analyze?pretty=true' -d '{"text":"我是中国人"}'
{
  "tokens" : [
    {
      "token" : "我",
      "start_offset" : 0,
      "end_offset" : 1,
      "type" : "<IDEOGRAPHIC>",
      "position" : 0
    },
    {
      "token" : "是",
      "start_offset" : 1,
      "end_offset" : 2,
      "type" : "<IDEOGRAPHIC>",
      "position" : 1
    },
    {
      "token" : "中",
      "start_offset" : 2,
      "end_offset" : 3,
      "type" : "<IDEOGRAPHIC>",
      "position" : 2
    },
    {
      "token" : "国",
      "start_offset" : 3,
      "end_offset" : 4,
      "type" : "<IDEOGRAPHIC>",
      "position" : 3
    },
    {
      "token" : "人",
      "start_offset" : 4,
      "end_offset" : 5,
      "type" : "<IDEOGRAPHIC>",
      "position" : 4
    }
  ]
}
```





```
Elasticsearch 支持的核心类型：

JSON 基础类型
字符串: text, keyword
数字: byte, short, integer, long, float, double，half_float
时间: date
布尔值: true, false
数组: array
对象: object

ES 独有类型

多重: multi
经纬度: geo_point
网络地址: ip
堆叠对象: nested object
二进制: binary
附件: attachment
```


### 创建一个新的索引
```
curl -XPUT "http://localhost:9200/blog"
```

### 添加一条文档
```
curl -XPUT "http://localhost:9200/blog/article/1" -d '{            
    "title":"test",
    "content":"test content"
}'
```






### IK中文分词插件

ik分词器，安装之前，先说明一些变化：

1、之前可以在node节点上配置index默认的分词器，如果是多节点，那么在每个节点上都配置就行了。
这个有点不灵活，所以。5.0之后，
ES已经不再支持在elasticsearch.yml中配置分词器，改而在新建索引时，使用settings去配置，这个会在后面的编程中说到。
2.之前使用delete-by-query插件来实现type的整个删除。这个插件也是从5.0开始没有了，被整个到ES的Core中。

3.从5.0开始ik的tokenizer发生了变化，提供了两种，一种为ik_smart，一种为ik_max_word。
直接一点，ik_max_word会尽量从输入中拆分出更多token，而ik_smart则相反，
个人感觉，ik_max_word就是原来的ik，ik_smart是新加的。


```
官网
https://github.com/medcl/elasticsearch-analysis-ik

下载：https://github.com/medcl/elasticsearch-analysis-ik.git
切换分支：git checkout tags/{version}
打包：mvn package
复制和解压缩target/releases/elasticsearch-analysis-ik-{version}.zip 到 your-es-root/plugins/ik
重启elasticsearch
```


```
elasticsearch官方只提供smartcn这个中文分词插件，效果不是很好
curl 'http://172.22.1.133:9200/liguodong/_analyze?pretty=true' -d '{"text":"我是中国人"}'

使用IK分词插件
curl 'http://172.22.1.133:9200/megacorp/_analyze?tokenizer=ik_max_word&pretty=true' -d '{"text":"我是中国人"}'


对比
http://172.22.1.133:9200/megacorp/_analyze?text=我是中国人
http://172.22.1.133:9200/megacorp/_analyze?text=中华人民共和国MN&tokenizer=ik_max_word

curl 'http://172.22.1.133:9200/megacorp/_analyze?pretty=true' -d '{"text":"我是中国人"}'
curl 'http://172.22.1.133:9200/megacorp/_analyze?tokenizer=ik_max_word&pretty=true' -d '{"text":"我是中国人"}'
curl 'http://172.22.1.133:9200/megacorp/_analyze?analyzer=ik_max_word&pretty=true' -d '{"text":"我是中国人"}'
curl 'http://172.22.1.133:9200/megacorp/_analyze?tokenizer=ik_smart&pretty=true' -d '{"text":"我是中国人"}'
curl 'http://172.22.1.133:9200/megacorp/_analyze?analyzer=ik_smart&pretty=true' -d '{"text":"我是中国人"}'




Analyzer(分析器): ik_smart,ik_max_word , Tokenizer(分词器):ik_smart,ik_max_word
http://172.22.1.133:9200/xma_faq_dev/_analyze?text=中华人民共和国MN&tokenizer=ik_smart
http://172.22.1.133:9200/xma_faq_dev/_analyze?text=中华人民共和国MN&tokenizer=ik_max_word


# 1.create a index
curl -XPUT http://172.22.1.133:9200/index

# 2.create a mapping
curl -XPOST http://172.22.1.133:9200/index/fulltext/_mapping -d '
{
    "fulltext": {
             "_all": {
            "analyzer": "ik_max_word",
            "search_analyzer": "ik_max_word",
            "term_vector": "no",
            "store": "false"
        },
        "properties": {
            "content": {
                "type": "text",
                "analyzer": "ik_max_word",
                "search_analyzer": "ik_max_word",
                "include_in_all": "true",
                "boost": 8
            }
        }
    }
}'


curl -XGET http://172.22.1.133:9200/index/_mappings?pretty

curl -XGET http://172.22.1.133:9200/index/_settings?pretty

curl -XGET 'http://172.22.1.133:9200/index/fulltext/_search?pretty'

# 3.index some docs

curl -XPOST http://172.22.1.133:9200/index/fulltext/1 -d '
{"content":"美国留给伊拉克的是个烂摊子吗"}
'

curl -XPOST http://172.22.1.133:9200/index/fulltext/2 -d '
{"content":"公安部：各地校车将享最高路权"}
'

curl -XPOST http://172.22.1.133:9200/index/fulltext/3 -d '
{"content":"中韩渔警冲突调查：韩警平均每天扣1艘中国渔船"}
'

curl -XPOST http://172.22.1.133:9200/index/fulltext/4 -d '
{"content":"中国驻洛杉矶领事馆遭亚裔男子枪击 嫌犯已自首"}
'

4.query with highlighting 查询并且高亮关键词

curl -XPOST http://172.22.1.133:9200/index/fulltext/_search?pretty  -d'
{
    "query" : { "match" : { "content" : "中国" }},
    "highlight" : {
        "pre_tags" : ["<tag1>", "<tag2>"],
        "post_tags" : ["</tag1>", "</tag2>"],
        "fields" : {
            "content" : {}
        }
    }
}'

结果：
{
  "took" : 11,
  "timed_out" : false,
  "_shards" : {
    "total" : 5,
    "successful" : 5,
    "failed" : 0
  },
  "hits" : {
    "total" : 2,
    "max_score" : 4.278213,
    "hits" : [
      {
        "_index" : "index",
        "_type" : "fulltext",
        "_id" : "4",
        "_score" : 4.278213,
        "_source" : {
          "content" : "中国驻洛杉矶领事馆遭亚裔男子枪击 嫌犯已自首"
        },
        "highlight" : {
          "content" : [
            "<tag1>中国</tag1>驻洛杉矶领事馆遭亚裔男子枪击 嫌犯已自首"
          ]
        }
      },
      {
        "_index" : "index",
        "_type" : "fulltext",
        "_id" : "3",
        "_score" : 2.2110996,
        "_source" : {
          "content" : "中韩渔警冲突调查：韩警平均每天扣1艘中国渔船"
        },
        "highlight" : {
          "content" : [
            "中韩渔警冲突调查：韩警平均每天扣1艘<tag1>中国</tag1>渔船"
          ]
        }
      }
    ]
  }
}

```

### 小知识
```


ElasticSearch默认分词器的配置。已知ES默认的分词器是标准分词器Standard。如果需要修改默认分词器可以做如下设置：
在配置文件config/elasticsearch.yml中添加  index.anlysis.analyzer.default.type:ik。

当然在ik5.0.0以后中还是有些许的变化

IK5.0.0：移除名为ik的analyzer和tokenizer，修改为 ik_smart 和 ik_max_word
Analyzer: ik_smart , ik_max_word , Tokenizer: ik_smart , ik_max_word
所以在配置是ik是无效的，
需要配置为 ik_smart ,或者是 ik_max_word。
其中 ik_smart 为最少切分，ik_max_word为最细粒度划分


ik_max_word: 会将文本做最细粒度的拆分，比如会将“中华人民共和国国歌”
拆分为“中华人民共和国,中华人民,中华,华人,人民共和国,人民,人,民,共和国,共和,和,国国,国歌”，
会穷尽各种可能的组合；

ik_smart: 会做最粗粒度的拆分，
比如会将“中华人民共和国国歌”拆分为“中华人民共和国,国歌”。


关于两种不同分词的用处以及区别：
1.使用方面的不同：

其中我们在做索引的时候，希望能将所有的句子切分的更详细，
以便更好的搜索，所以ik_max_word更多的用在做索引的时候，但是在搜索的时候，
对于用户所输入的query(查询)词，我们可能更希望得比较准确的结果，
例如，我们搜索“无花果”的时候，更希望是作为一个词进行查询，
而不是切分为"无"，“花”，“果”三个词进行结果的召回，因此ik_smart更加常用语对于输入词的分析。

2.效率方面的不同：

ik_max_word分词相对来说效率更加迅速，而ik_smart的效率比不上ik_max_word(个人做索引的时候将两
种分词器进行尝试得出的结果，有误的话，望指正)

```

### ES Client
```
对于ES Client，有两种形式，一个是TransportClient，一个是NodeClient。两个的区别为：

TransportClient作为一个外部访问者，通过HTTP去请求ES的集群，对于集群而言，它是一个外部因素。

NodeClient顾名思义，是作为ES集群的一个节点，它是ES中的一环，其他的节点对它是感知的，
不像TransportClient那样，ES集群对它一无所知。NodeClient通信的性能会更好，但是因为是ES的一环，
所以它出问题，也会给ES集群带来问题。NodeClient可以设置不作为数据节点，在elasticsearch.yml中设置，
这样就不会在此节点上分配数据。

如果用ES的节点，大家仁者见仁智者见智，各按所需。
```

### 分片
```
简单说下分片(shard)。ES本身是分布式的，所以分片自然而然，它的分片并非将所有的数据都放在一起，
以默认的5个主分片为例，ES会将数据均衡的存储到5个分片上，也就是这5个分片的数据并集才是整个数据集，
这5个分片会按照一定规则分配到不同的ES Node上。这样的5个分片叫主分片。然后就是从分片，
默认设置是一个主分片会有一个从分片，那么就有5个从分片，
那么默认配置会产生10个分片（5主5从）就散布在所有的Node上。主分片的个数是索引新建的时候设置的，
一经设置，不可改变，因为ES判断一条文档存放到哪个分片就是通过这个主分片数量来控制的。
简单来讲，插入的文档号与5取余（实际不是这样实现的，但是也很简单）。检索结果的时候，
也是通过这个来确认结果分布的，所以不能改。从分片的数量可以随便改，因为塔是跟主分片关联的。
另外，Node节点也可以随时加，而且ES还会在新节点加入之后，重新调整数据分片的分布。
```

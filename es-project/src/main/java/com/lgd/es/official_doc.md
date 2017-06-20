

```
# 查看所有索引
curl -XGET  http://10.250.140.215:9200/_cat/indices
curl -XGET  http://172.22.1.28:9200/_cat/indices


curl -XGET  http://172.22.1.133:9200/_all?pretty
curl -XGET  http://172.22.1.133:9200/*?pretty

# 查看某个索引
curl -XGET http://172.22.1.133:9200/twitter/_mappings?pretty
curl -XGET http://172.22.1.133:9200/twitter2/_settings?pretty

curl -XGET http://172.22.1.133:9200/google?pretty

## _settings, _mappings 和 _aliases
curl -XGET http://172.22.1.133:9200/google/_settings,_mappings?pretty

# 检查索引中是否有数据
curl '172.22.1.133:9200/_cat/indices/haha?v'
health status index uuid                   pri rep docs.count docs.deleted store.size pri.store.size
yellow open   haha  W9Lxe1pDT-q1kkaaYZ4KzQ   5   1          0            0       650b           650b

curl '172.22.1.133:9200/_cat/indices/sichuan?v'
health status index   uuid                   pri rep docs.count docs.deleted store.size pri.store.size
yellow open   sichuan dk4MtDVmS7iep0jfT3huKQ   5   1          3            0     11.8kb         11.8kb
```


### 创建索引
```
curl -XPUT "http://172.22.1.133:9200/google"
curl -XPUT "http://172.22.1.133:9200/google?pretty"

# settings
curl -XPUT "http://172.22.1.133:9200/twitter" -d '
{
    "settings" : {
        "index" : {
            "number_of_shards" : 3,
            "number_of_replicas" : 2
        }
    }
}'

curl -XPUT "http://172.22.1.133:9200/twitter2" -d '
{
    "settings" : {
        "number_of_shards" : 3,
        "number_of_replicas" : 2
    }
}'


# settings和mappings
curl -XPUT "http://172.22.1.133:9200/twitter3?pretty" -d '
{
    "settings" : {
        "number_of_shards" : 1
    },
    "mappings" : {
        "type1" : {
            "properties" : {
                "field1" : { "type" : "text" }
            }
        }
    }
}'

curl -XPUT "http://172.22.1.133:9200/twitter4?pretty" -d '
{
    "settings" : {
        "number_of_shards" : 2
    },
    "mappings" : {
        "type1" : {
            "properties" : {
                "field1" : { "type" : "text" },
                "field2" : { "type" : "short" },
                "field3" : { "type" : "text" }
            }
        },
        "type2" : {
              "properties" : {
                  "field4" : { "type" : "keyword" },
                  "field5" : { "type" : "long" },
                  "field6" : { "type" : "text" }
              }
          }
    }
}'


```

### 删除索引
```
# 某个
curl -XDELETE "http://172.22.1.133:9200/twitter?pretty"

# 支持通配符
curl -XDELETE "http://172.22.1.133:9200/twitter*?pretty"

# 通过使用_all，*删除所有索引（慎用）

```


### 添加类型和字段
```
# 创建一个索引并指定一个类型和字段
curl -XPUT "http://172.22.1.133:9200/twitter?pretty" -d '
{
  "mappings": {
    "tweet": {
      "properties": {
        "message": {
          "type": "text"
        }
      }
    }
  }
}'

curl -XGET http://172.22.1.133:9200/twitter/_mappings?pretty

# 添加一个类型
curl -XPUT "http://172.22.1.133:9200/twitter/_mapping/user?pretty" -d '
{
  "properties": {
    "name": {
      "type": "text"
    }
  }
}'



# 再已存在的类型中添加字段
curl -XPUT "http://172.22.1.133:9200/twitter/_mapping/tweet?pretty" -d '
{
  "properties": {
    "user_name": {
      "type": "text"
    }
  }
}'

```

### 更新字段
通常mapping中的字段不能被更新。以下几种方式可以。
例外：

new `properties` can be added to **Object datatype** fields.

new `multi-fields` can be added to existing fields.

the `ignore_above` parameter can be updated. 

```
curl -XPUT "http://172.22.1.133:9200/my_index?pretty" -d '
{
  "mappings": {
    "user": {
      "properties": {
        "name": {
          "properties": {
            "first": {
              "type": "text"
            }
          }
        },
        "user_id": {
          "type": "keyword"
        }
      }
    }
  }
}'

curl -XGET http://172.22.1.133:9200/my_index/_mappings?pretty


curl -XPUT "http://172.22.1.133:9200/my_index/_mapping/user?pretty" -d '
{
  "properties": {
    "name": {
      "properties": {
        "last": { 
          "type": "long"
        }
      }
    },
    "user_id": {
      "type": "keyword",
      "ignore_above": 100 
    }
  }
}'

curl -XGET http://172.22.1.133:9200/my_index/_mappings?pretty

{
  "my_index" : {
    "mappings" : {
      "user" : {
        "properties" : {
          "name" : {
            "properties" : {
              "first" : {
                "type" : "text"
              },
              "last" : {
                "type" : "long"
              }
            }
          },
          "user_id" : {
            "type" : "keyword",
            "ignore_above" : 100
          }
        }
      }
    }
  }
}


# 插入数据
curl -XPUT 'http://172.22.1.133:9200/my_index/user/2?pretty' -d '{
   "user_id" : "433ggff333",
   "name" : [
      {
         "first" : "dave", "last" : 18
      },

      {
         "first" : "kevin", "last" : 22
      }
   ]
}'

# 获取数据
curl -XGET 'http://172.22.1.133:9200/my_index/user/2?pretty'

{
  "_index" : "my_index",
  "_type" : "user",
  "_id" : "2",
  "_version" : 1,
  "found" : true,
  "_source" : {
    "user_id" : "433ggff333",
    "name" : [
      {
        "first" : "dave",
        "last" : 18
      },
      {
        "first" : "kevin",
        "last" : 22
      }
    ]
  }
}

#################

curl -XPUT 'http://172.22.1.133:9200/my_index2?pretty' -d '
{
  "mappings": {
    "type_one": {
      "properties": {
        "text": { 
          "type": "text",
          "analyzer": "standard"
        }
      }
    },
    "type_two": {
      "properties": {
        "text2": { 
          "type": "text",
          "analyzer": "standard"
        }
      }
    }
  }
}'

curl -XPUT 'http://172.22.1.133:9200/my_index2/_mapping/type_one?pretty' -d '
{
  "properties": {
    "text": {
      "type": "text",
      "analyzer": "standard",
      "search_analyzer": "whitespace"
    }
  }
}'


curl -XGET 'http://172.22.1.133:9200/my_index2/_mapping?pretty'


#################

一个索引中不同类型中相同名字的字段必须是相同的映射，因为他们内部又同一字段支持。


curl -XPUT 'http://172.22.1.133:9200/my_index3?pretty' -d '
{
  "mappings": {
    "type_one": {
      "properties": {
        "text": { 
          "type": "text",
          "analyzer": "standard"
        }
      }
    },
    "type_two": {
      "properties": {
        "text": { 
          "type": "text",
          "analyzer": "standard"
        }
      }
    }
  }
}'
##########################
报错
curl -XPUT 'http://172.22.1.133:9200/my_index3/_mapping/type_one?pretty' -d '
{
"properties": {
  "text": {
    "type": "text",
    "analyzer": "standard",
    "search_analyzer": "whitespace"
  }
}
}'


##########################
正确
update_all_types（更新所有类型）

curl -XPUT 'http://172.22.1.133:9200/my_index3/_mapping/type_one?update_all_types&pretty' -d '
{
"properties": {
  "text": {
    "type": "text",
    "analyzer": "standard",
    "search_analyzer": "whitespace"
  }
}
}'

curl -XGET 'http://172.22.1.133:9200/my_index3/_mapping/type_one?pretty'

```


###
```


```

```


```

```


```
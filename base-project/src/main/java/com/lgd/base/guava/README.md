
https://www.yiibai.com/guava/guava_preconditions_class.html


Optional用于包含非空对象的不可变对象。 Optional对象，用于不存在值表示null。
这个类有各种实用的方法，
以方便代码来处理为可用或不可用，而不是检查null值。
OptionalDemo


Preconditions提供静态方法来检查方法或构造函数，被调用是否给定适当的参数。
它检查的先决条件。其方法失败抛出IllegalArgumentException。
PreconditionsDemo




Guava通过接口LoadingCache提供了一个非常强大的基于内存的LoadingCache<K，V>。在缓存中自动加载值，它提供了许多实用的方法，在有缓存需求时非常有用。
LoadingCacheDemo



Ordering(排序)可以被看作是一个丰富的比较具有增强功能的链接，多个实用方法，多类型排序功能等。
OrderingDemo



Objects类提供适用于所有对象，如equals, hashCode等辅助函数
ObjectsDemo



Range 表示一个间隔或一个序列。它被用于获取一组数字/串在一个特定范围之内。
RangeDemo


Throwable类提供了相关的Throwable接口的实用方法。
ThrowableDemo



----------

字符串工具

Joiner 提供了各种方法来处理字符串加入操作，对象等。
JoinerDemo

CaseFormat 是一种实用工具类，以提供不同的ASCII字符格式之间的转换。
CaseFormatDemo


Splitter 提供了各种方法来处理分割操作字符串，对象等。
SplitterDemo

CharMatcher 提供了各种方法来处理各种JAVA char类型值。
CharMatcherDemo



------------------

集合工具


Multiset接口扩展设置有重复的元素，并提供了各种实用的方法来处理这样的元素在集合中出现。
MultisetDemo


Multimap多重映射接口扩展映射，使得其键一次可被映射到多个值。
MultimapDemo


BiMap是一种特殊的映射其保持映射，同时确保没有重复的值是存在于该映射和一个值可以安全地用于获取键背面的倒数映射。
BiMapDemo


Table代表一个特殊的映射，其中两个键可以在组合的方式被指定为单个值。它类似于创建映射的映射。
TableDemo


------------------

数学工具

IntMath提供整型的实用方法。
IntMathDemo


------------------
原语工具

Doubles是double基本类型的实用工具类。
# 1 Java8 tutorial
示例代码：com.java8
入门教程链接： http://winterbe.com/posts/2014/03/16/java-8-tutorial/

# 2 Machine learning

https://developers.google.com/machine-learning/crash-course/

# 3 日志级别

日志记录器（Logger）的行为是分等级的。如下表所示：

分为OFF、FATAL、ERROR、WARN、INFO、DEBUG、ALL或者您定义的级别。
Log4j建议只使用四个级别，优先级从高到低分别是 ERROR、WARN、INFO、DEBUG。
通过在这里定义的级别，您可以控制到应用程序中相应级别的日志信息的开关。
比如在这里定义了INFO级别，则应用程序中所有DEBUG级别的日志信息将不被打印出来，也是说大于等于的级别的日志才输出。


# 4 HashMap的putIfAbsent()方法
示例代码：com.java8.map.PutIfAbsentTest
## 来源
在此方法出现在HashMap里面之前，JDK给出的解决方案是ConcurrentMap的putIfAbsent()方法。
出现在HashMap里面的这个putIfAbsent()方法与之前的解决方法具有相同的功能，

## 特点
1.当value为null的时候，putIfAbsent()方法会覆盖null值，直到value不为null为止
2.当value初始值不为null的时候，putIfAbsent()保证返回值始终是唯一的，并且是多线程安全的
3.putIfAbsent()是有返回值的，应该对他的返回值进行非空判断
4.2和3主要应用在单例模式中

# 5 ConcurrentHashMap中的putIfAbsent方法

putIfAbsent方法主要是在向ConcurrentHashMap中添加键—值对的时候，它会先判断该键值对是否已经存在。

1.如果不存在（新的entry），那么会向map中添加该键值对，并返回null。

2.如果已经存在，那么不会覆盖已有的值，直接返回已经存在的值。
相当于：
```
 V v = map.get(key);
 if (v == null)
     v = map.put(key, value);
 return v;
```

返回值：

（1）如果是新的记录，那么会向map中添加该键值对，并返回null。

（2）如果已经存在，那么不会覆盖已有的值，直接返回已经存在的值。

# 6 自定义注解
示例代码： com.annatation

# 7 Date API

Java 8 contains a brand new date and time API under the package java.time.
The new Date API is comparable with the Joda-Time library, however it's not the same.
The following examples cover the most important parts of this new API.

Unlike java.text.NumberFormat the new DateTimeFormatter is immutable and thread-safe.

# 8 Annotations

Annotations in Java 8 are repeatable. Let's dive directly into an example to figure that out.

First, we define a wrapper annotation which holds an array of the actual annotations:

# 9 Java读取/写入Yaml配置文件

yaml配置文件格式规范：- 表示sequence(list列表结构)，: 表示map键值对

# 10 Mockito教程

https://www.cnblogs.com/Ming8006/p/6297333.html

# 11 maven profile动态选择配置文件

 profile可以让我们定义一系列的配置信息，然后指定其激活条件。这样我们就可以定义多个profile，然后每个profile对应不同的激活条件和配置信息，从而达到不同环境使用不同配置信息的效果。

　profile定义的位置：

（1）针对于特定项目的profile配置我们可以定义在该项目的pom.xml中。

（2）针对于特定用户的profile配置，我们可以在用户的settings.xml文件中定义profile。该文件在用户家目录下的“.m2”目录下。

（3）全局的profile配置。全局的profile是定义在Maven安装目录下的“conf/settings.xml”文件中的。

## 动态配置注意点：
    注入顺序如下，如果没找到相应的值，则再找下一个配置文件中的相应配置
    1.全局配置文件conf/setting.xml
    2.用户目录下的setting.xml
    3.当前工程的pom.xml
    4.resources目录下的配置文件(本例为${profiles.active}/test.yml)

参见：
    1. https://www.cnblogs.com/0201zcr/p/6262762.html
    2. http://blog.csdn.net/lihe2008125/article/details/50443491
    3. https://www.cnblogs.com/harvey2017/p/7762286.html
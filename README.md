# 1.Java8 tutorial
示例代码：com.java8
入门教程链接： http://winterbe.com/posts/2014/03/16/java-8-tutorial/

# 2.Machine learning

https://developers.google.com/machine-learning/crash-course/

# 3.日志级别

日志记录器（Logger）的行为是分等级的。如下表所示：

分为OFF、FATAL、ERROR、WARN、INFO、DEBUG、ALL或者您定义的级别。
Log4j建议只使用四个级别，优先级从高到低分别是 ERROR、WARN、INFO、DEBUG。
通过在这里定义的级别，您可以控制到应用程序中相应级别的日志信息的开关。
比如在这里定义了INFO级别，则应用程序中所有DEBUG级别的日志信息将不被打印出来，也是说大于等于的级别的日志才输出。


#4 HashMap的putIfAbsent()方法
示例代码：com.java8.map.PutIfAbsentTest
## 来源
在此方法出现在HashMap里面之前，JDK给出的解决方案是ConcurrentMap的putIfAbsent()方法。
出现在HashMap里面的这个putIfAbsent()方法与之前的解决方法具有相同的功能，

## 特点
1.当value为null的时候，putIfAbsent()方法会覆盖null值，直到value不为null为止
2.当value初始值不为null的时候，putIfAbsent()保证返回值始终是唯一的，并且是多线程安全的
3.putIfAbsent()是有返回值的，应该对他的返回值进行非空判断
4.2和3主要应用在单例模式中

#5 ConcurrentHashMap中的putIfAbsent方法

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

#6 自定义注解
示例代码： com.annatation

#7 Date API

Java 8 contains a brand new date and time API under the package java.time.
The new Date API is comparable with the Joda-Time library, however it's not the same.
The following examples cover the most important parts of this new API.

Unlike java.text.NumberFormat the new DateTimeFormatter is immutable and thread-safe.

#8 Annotations

Annotations in Java 8 are repeatable. Let's dive directly into an example to figure that out.

First, we define a wrapper annotation which holds an array of the actual annotations:
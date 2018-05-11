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
## 4.1 来源
在此方法出现在HashMap里面之前，JDK给出的解决方案是ConcurrentMap的putIfAbsent()方法。
出现在HashMap里面的这个putIfAbsent()方法与之前的解决方法具有相同的功能，

## 4.2 特点
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
 
## 11.1 profile定义的位置
 1. 针对于特定项目的profile配置我们可以定义在该项目的pom.xml中。
 2. 针对于特定用户的profile配置，我们可以在用户的settings.xml文件中定义profile。该文件在用户家目录下的“.m2”目录下。
 3. 全局的profile配置。全局的profile是定义在Maven安装目录下的“conf/settings.xml”文件中的。

## 11.2 动态配置注意点
 注入顺序如下，如果没找到相应的值，则再找下一个配置文件中的相应配置
 1. 全局配置文件conf/setting.xml
 2. 用户目录下的setting.xml
 3. 当前工程的pom.xml
 4. resources目录下的配置文件(本例为${profiles.active}/test.yml)

## 11.3 参考链接
 1. https://www.cnblogs.com/0201zcr/p/6262762.html
 2. http://blog.csdn.net/lihe2008125/article/details/50443491
 3. https://www.cnblogs.com/harvey2017/p/7762286.html


# 12 SLF4J门面日志框架
## 12.1 门面模式
slf4j是门面模式的典型应用，门面模式，其核心为外部与一个子系统的通信必须通过一个统一的外观对象进行，使得子系统更易于使用
门面模式的核心为Facade即门面对象，门面对象核心为几个点：
1.知道所有子角色的功能和责任
2.将客户端发来的请求委派到子系统中，没有实际业务逻辑
3.不参与子系统内业务逻辑的实现

## 12.2 为什么使用slf4j
我们为什么要使用slf4j，举个例子：
```
我们自己的系统中使用了logback这个日志系统
我们的系统使用了A.jar，A.jar中使用的日志系统为log4j
我们的系统又使用了B.jar，B.jar中使用的日志系统为slf4j-simple
这样，我们的系统就不得不同时支持并维护logback、log4j、slf4j-simple三种日志框架，非常不便。
```

解决这个问题的方式就是引入一个适配层，由适配层决定使用哪一种日志系统，而调用端只需要做的事情就是打印日志而不需要关心如何打印日志，slf4j或者commons-logging就是这种适配层，slf4j是本文研究的对象。
从上面的描述，我们必须清楚地知道一点：slf4j只是一个日志标准，并不是日志系统的具体实现。理解这句话非常重要，slf4j只做两件事情：
1. 提供日志接口
2. 提供获取具体日志对象的方法

slf4j-simple、logback都是slf4j的具体实现，log4j并不直接实现slf4j，但是有专门的一层桥接slf4j-log4j12来实现slf4j。

## 12.3 SLF4j 和 common-logging
https://blog.csdn.net/xydds/article/details/51606010

## 12.4 配置Slf4j依赖，桥接各种多个日志组件（排除commons-logging依赖的影响)
https://www.cnblogs.com/gsyun/p/6814696.html

## 12.5 日志组件slf4j介绍及配置详解
https://blog.csdn.net/foreverling/article/details/51385128

# 13 日期间隔计算

在java 编程中，不可避免用到计算时间差。前面我写过几篇文章，关于java 时间计算的，还有timezone 转换的文章，但没有这么具体到相差到天数，小时，分钟，秒数都列出来的情况，所以这里再总结下。
1. 用JDK 自带API 实现。
2. 利用 joda time library 来实现.

#14 从jar包中读取资源Class对象

com.utils.PackageUtil

#15 JAVA 泛型通配符 ? EXTENDS SUPER 的用法

## 15.1 <? extends Hero>

ArrayList heroList<? extends Hero> 表示这是一个Hero泛型或者其子类泛型
heroList 的泛型可能是Hero
heroList 的泛型可能是APHero
heroList 的泛型可能是ADHero
所以 可以确凿的是，从heroList取出来的对象，一定是可以转型成Hero的


但是，不能往里面放东西，因为
放APHero就不满足<ADHero>
放ADHero又不满足<APHero>

## 15.2 <? super Hero>

ArrayList heroList<? super Hero> 表示这是一个Hero泛型或者其父类泛型
heroList的泛型可能是Hero
heroList的泛型可能是Object

可以往里面插入Hero以及Hero的子类
但是取出来有风险，因为不确定取出来是Hero还是Object

## 15.3 泛型通配符?

泛型通配符? 代表任意泛型
既然?代表任意泛型，那么换句话说，这个容器什么泛型都有可能

所以只能以Object的形式取出来
并且不能往里面放对象，因为不知道到底是一个什么泛型的容器
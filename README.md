# No Framework
<a href='https://gitee.com/ainilili/No-Framework/members'><img src='https://img.shields.io/maven-central/v/com.gitee.ainilili/noframework.svg?style=flat-square' alt='maven'></img></a>
<img alt="code style" src="https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square">

## 一、序言
NoFramework是笔者在闲暇之余写的一个网络爬虫项目```Seeker```一路延续发展到后来的```No```系列的框架的诞生。一路走来，不知不觉已经快一年了，这期间有些项目有的被重写过，有的架构也发生了大的改变，还有的一直在调优过程中转变着解决方案，在付出时间的同时，获得的回报是独立思考过程带给自己的对底层细节的了解。

很多人很疑惑于它的作用，在这里诠释一下:
```
它可以代替tomcat、spring、mybatis来帮助开发者进行web服务搭建！
```

您使用这套框架的好处是：
> - 可直接与作者沟通交流
> - 可参与框架的开发
> - 能够感受到使用国产框架的魅力

Java界的很多框架原理都很简单，我所熟知的框架中不出意外的会利用到：
> - ThreadLocal
> - Dynamic Proxy
> - Reflect
> - Genericity
> - AQS&CAS

所以实现一套框架看起来很简单，因为需要的技术也并不怎么高大上，并且还有一堆现成的开源可以直接拿来用的。但是在实现的过程中确实有很多意想不到的难点，如果您有兴趣可以亲自简单实现个Spring并在测试环境下小用一下，保证能达到需求的情况下，这个过程应该会使您对我刚才的话感悟颇深。

回归主题，在将近一年的时间里，我从最基本的```Json、XML解析```到```服务容器```再到```服务框架```最终满足了一个小型网站的基本开发。当然有些模块还有很多不足，也有很多待优化的功能，这些笔者都会坚持下来接着去完成。也许最终这些代码变得毫无意义，但是我仍然会坚持的去完成它，并将它打造的越来越好！

## 二、项目介绍
No Framework，简称**NF**，为Java Web开发提供整套服务框架解决方案。

### 2.1、NF模块构建
- [x] Maven (groupId：com.gitee.ainilili)
- [x] Git (https://gitee.com/ainilili)

### 2.2、NF模块介绍
#### 2.2.1、父模块
项目名 | 版本号 | 功能
---|---|---
noframework | 1.1.3 | 子模块的parent依赖

#### 2.2.2、子模块

项目名 | 版本号 | 功能
---|---|---
noson | 1.1.3 | Json序列化&反序列化
nocat | 1.1.3 | 基于Java的Web容器
noaoc | 1.1.3 | IOC&AOP
nomvc | 1.1.3 | MVC
nolog | 1.1.3 | 日志及工具包
seeker | 1.1.3 | Xml解析
nodb | 1.1.3 | ORM
noasm | 1.1.3 | ASM工具
nomvc-support-for-nocat | 1.1.3 | 提供对Nocat的MVC支持
nomvc-support-for-tomcat | 1.1.3 | 提供对Tomcat的MVC支持
noaoc-listener-for-nocat | 1.1.3 | Noaoc加载监听器
nf | 1.1.3 | 提供其他模块的依赖

### 2.3、NF模块详细介绍
在学习使用之前，如果您有兴趣，可以先看看它们是具体作用~

NF每个模块都占据着很重要的地位，它们相互协调，一起完成最终的工作，下面就简单介绍一下它们都担负着什么样的重要使命。
#### 2.3.1、Noson - Json序列化及反序列化工具

Noson负责着前后台交互过程中的Json数据解析工作，是MVC容器默认的Json序列化及反序列化工具。在经过不断的优化后，Noson的解析性能也是相当可观，虽然不及前辈们写的FastJson、Jackson、Gson等，但是如果不是对性能太过要求，Noson是您最好的选择。

Noson有着极高的容错率，例如一下的Json字符串，Noson也能容错解析
```
{value:[{\"key1\":a,'key2':b,key3:1c,key4:'\"d',key5:\"'e\",key6:':,[]{}',key7:,key8:'%$&%$&%^><:'}]}
```
以下是解析后并序列化为标准Json字符串
```
{"value":[{"key1":"a","key2":"b","key3":"1c","key4":"\"d","key5":"\'e","key6":":,[]{}","key7":"key8:\'%$&%$&%^><:\'"}]}
```
Noson也可以处理非常复杂的类型
```
json = "{list:[{map:{map:{list:[{map:{value:{\"name\":nico,age:21,skill:[java,c,c#,python,php,javascript],deposit:0.0,info:{address:china,job:IT}}}},{map:{value:{\"name\":nico,age:21,skill:[java,c,c#,python,php,javascript],deposit:0.0,info:{address:china,job:IT}}}}]}}},{map:{map:{list:[{map:{value:{\"name\":nico,age:21,skill:[java,c,c#,python,php,javascript],deposit:0.0,info:{address:china,job:IT}}}},{map:{value:{\"name\":nico,age:21,skill:[java,c,c#,python,php,javascript],deposit:0.0,info:{address:china,job:IT}}}}]}}}]}";
Map<String, List<Map<String, Map<String, Map<String, List<Map<String, Map<String, Nico>>>>>>>> target = Noson.convert(json, new NoType<Map<String, List<Map<String, Map<String, Map<String, List<Map<String, Map<String, Nico>>>>>>>>>(){});
```
Noson可以解决循环嵌套
```
Cycle c = new Cycle();
System.out.println(Noson.reversal(c));

Map<String, Object> map = new HashMap<String, Object>();
map.put("map", map);
System.out.println(Noson.reversal(map));

List<Object> list = new ArrayList<Object>();
list.add(list);
System.out.println(Noson.reversal(list));

Set<Object> sets = new HashSet<Object>();
sets.add(sets);
System.out.println(Noson.reversal(sets));

map.put("list", list);
System.out.println(Noson.reversal(map));
```
输出
```
{"cycle":{}}
{"map":{}}
[[]]
[[]]
{"list":[[]],"map":{"list":[[]]}}
```
Noson还有更多神奇的地方，如果您有兴趣，可以在下一节学习的过程中深入了解。
#### 2.3.2、Nocat - 轻量级的Web服务容器
Nocat是基于Java socket编制的轻量级web服务器，提供简单的资源访问功能，向外提供三大模块：
- Filter
- Listener
- Api

功能可以看做Tomcat的过滤器、监听器、Servlet，区别于Tomcat的地方在于Nocat可以更简单的在Main函数中启动一个服务：
```
public static void main(String[] args) {
		ServerBootStrap bootStrap = new ServerBootStrap();
		bootStrap.start(8080);
	}
```
上述代码会在本地启动一个```8080```为端口的服务。
#### 2.3.3、Noaoc - IOC及AOP功能
Noaoc使用及其少的代码量实现了Spring核心的两大模块```IOC```和```AOP```，并且同时支持xml及注解的配置。

Noaoc负责整个大家族中的类依赖管理，并提供及其灵活的对外扩展能力，这就大大方便了```Nomvc```的接入。

Noaoc提供一个对Nocat的Listener监听器用于容器初始化
> noaoc-listener-for-nocat

在Nocat的xml配置文件中加上监听器并设置参数：
```
<listener>
    <handler>org.nico.aoc.listener.NoaocListener</handler>
	<payload>
		{
		    //要扫描的xml配置
			"xmls":["cat-mysql.xml","cat-redis.xml"],
			
			//要扫描的注解包配置
			"packs":["org.nico.ct"],
			
			//自扩展组件
			"compents":["org.nico.cat.mvc.compent.MVCCompent"]
		}
	</payload>
</listener>
```

#### 2.3.4、Nomvc - 同时为Tomcat和Nocat提供MVC的技术支持

Nomvc是基于路由层的一个MVC控制器，出参入参使用Noson辅助解析，自带```Verify```模块负责方法入参的验证。

Nomvc对外提供两个扩展模块：
> nomvc-support-for-nocat

> nomvc-support-for-tomcat

接入Nocat只需要在xml配置文件中加上监听器和Api拦截路径：
```
<listener>
    <handler>org.nico.cat.mvc.listener.ListenerForNocat</handler>
	<payload>
	    //mvc注解包
		{"scanpack":"org.nico.ct.controller"}
	</payload>
</listener>
```
```
<api>
	<uri>/v1/**</uri>
	<handler>org.nico.cat.mvc.router.RouterForNocat</handler>
</api>
```
#### 2.3.5、Nolog - 轻量级的日志及工具包

Nolog使用简单几个文件，实现了轻量级的日志工具，另外附带着一些常用的工具包。

Nolog本身并没有任何依赖，因为要支持切换```Log4J```，所以添加了对Log4J级SLF的依赖。

Nolog入口文件名为```logno.properties```，之后将会简单介绍Nolog的配置参数及使用。

#### 2.3.6、Seeker - 超文本解析器及爬虫工具

Seeker最初的定位是爬虫工具，将爬取的HTML文本解析并提供可搜索的接口去获取目标数据，后来发现Seeker也可以用来做XML的解析。

Seeker在NF大家族中负责超文本解析工作，例如Nocat及Noaoc的配置。

#### 2.3.7、Nodb - ORM框架

类似Hibernate，但是比Hibernate轻得多的orm框架，目前只支持Mysql，性能直逼JDBC，提供方便的条件查询及分页接口。

可使用Noaoc直接整合：
```
<book id="DataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
	<param key="jdbcUrl"
		value="jdbc:mysql://localhost:3306/xx?useUnicode=true&characterEncoding=utf8&useSSL=false" />
	<param key="user" value="xx" />
	<param key="password" value="xx" />
	<param key="driverClass" value="com.mysql.jdbc.Driver" />
	<param key="maxPoolSize" value="100" />
	<param key="minPoolSize" value="10" />
</book>

<book id="MysqlSession" class="org.nico.db.session.branch.MysqlSession">
	<param key="autoCommit" value="false" />
	<label name="dataSource" ref="DataSource" />
</book>

<book id="MysqlDBHelper" class="org.nico.db.helper.impl.MysqlDBHelper">
	<param key="printLog" value="false" />
	<label name="session" ref="MysqlSession" />
</book>

<book id="TransationManager" class="org.nico.db.datasource.TransationManager">
	<label name="dataSource" ref="DataSource" />
</book>
```

#### 2.3.8、Noasm - 简单的ASM框架
依赖asm源码，目前提供的方法有：
- 对象方法参数名的获取
- 增强式反射

#### 2.3.9、Nf - 懒人的利器
如果您不想在pom.xml中写那么多的NF模块依赖，就直接引入NF模块即可。

## 三、使用教程

### 3.1、每个项目更详细的使用文档

- [Noson详细文档](https://gitee.com/ainilili/No-Framework/blob/master/nico-noson/README.md)
- [Nocat详细文档](https://gitee.com/ainilili/No-Framework/blob/master/nico-nocat/README.md)
- [Noaoc详细文档](https://gitee.com/ainilili/No-Framework/blob/master/nico-noaoc/README.md)
- [Nomvc详细文档](https://gitee.com/ainilili/No-Framework/blob/master/nico-nomvc/README.md)
- [Noson详细文档](https://gitee.com/ainilili/No-Framework/blob/master/nico-noson/README.md)
- [Nolog详细文档](https://gitee.com/ainilili/No-Framework/blob/master/nico-nolog/README.md)
- [Seeker详细文档](https://gitee.com/ainilili/No-Framework/blob/master/nico-seeker/README.md)
- [Nodb详细文档](https://gitee.com/ainilili/No-Framework/blob/master/nico-nodb/README.md)
- [Noasm详细文档](https://gitee.com/ainilili/No-Framework/blob/master/nico-noasm/README.md)

### 3.2、环境搭建

#### 3.2.1、Maven搭建
也许您需要引入父模块：
```
<dependency>
  <groupId>com.gitee.ainilili</groupId>
  <artifactId>noframework</artifactId>
  <version>${version}</version>
  <type>pom</type>
</dependency>
```

如果想引入所有模块，您只需要引入```nf```依赖即可：
```
<dependency>
  <groupId>com.gitee.ainilili</groupId>
  <artifactId>nf</artifactId>
  <version>${version}</version>
</dependency>
```

如果您想单独引用：
```
<dependency>
  <groupId>com.gitee.ainilili</groupId>
  <artifactId>${module-name}</artifactId>
  <version>${version}</version>
</dependency>
```
这里的```${module-name}```是模块名称，例如引入```noson v1.0.1```模块：
```
<dependency>
  <groupId>com.gitee.ainilili</groupId>
  <artifactId>noson</artifactId>
  <version>1.0.5</version>
</dependency>
```
另外，有些模块是被其他模块依赖的，具体依赖可以到[http://mvnrepository.com/](进行查询)

#### 3.2.2、普通项目搭建
使用Git工具将项目```Clone```到本地，之后使用您的IDE为您的项目引入模块到环境中，或者您可以手动打包成jar然后引入!

#### 3.2.3、DEMO
NF提供一个简单的项目[CoffeeTime](https://gitee.com/ainilili/CoffeeTime)用来演示NF实战的过程，将项目通过Git工具Clone到本地，然后以```Maven
Project```的方式导入到您的IDE中即可。

CoffeeTime 启动入口：```org.nico.ct.CtApplication```

TP：为了方便大家运行，数据库链接均使用笔者云服务器外网IP，希望大家不要做坏事，虽然服务器没什么价值，但是仍会给笔者带来一些困扰~
### 3.3、使用演示
下面就以[CoffeeTime](https://gitee.com/ainilili/CoffeeTime)来演示一下NF框架的使用。
 > 项目结构如下：
![输入图片说明](https://gitee.com/uploads/images/2018/0622/091221_6be055b3_1607679.png "20180622010510611.png")

#### 3.3.1、创建项目
新建一个Maven项目，并在pom中添加NF依赖及其他常用的 jar
``` 
<dependencies> 
	<dependency>
	  <groupId>com.gitee.ainilili</groupId>
	  <artifactId>nf</artifactId>
	  <version>1.0.5</version>
	</dependency>
	<dependency>
	    <groupId>redis.clients</groupId>
	    <artifactId>jedis</artifactId>
	    <version>2.8.1</version>
	</dependency>
	<dependency>
	    <groupId>mysql</groupId>
	    <artifactId>mysql-connector-java</artifactId>
	    <version>5.1.38</version>
	</dependency>
	<dependency>
	    <groupId>c3p0</groupId>
	    <artifactId>c3p0</artifactId>
	    <version>0.9.1.2</version>
	</dependency>
</dependencies>
```
#### 3.3.2、编写配置文件
首先是**Nocat**的配置文件cat.xml：
```
<?xml version="1.0" encoding="UTF-8"?>
<app>
	<cat:welcomes>
		<welcome>index.html</welcome>
		<welcome>index.jsp</welcome>
		<welcome>index.asp</welcome>
		<welcome>index.php</welcome> 
	</cat:welcomes>

	<cat:configs>
		<!-- 定义访问资源的路径, / 代表项目工作路径 -->
	   	<property field="server_resource_path" 			value="/web" 				/>
		<property field="server_port" 					value="8080" 				/>
		<property field="server_charset" 				value="utf-8" 				/>
		<property field="server_revice_buffer_size" 	value="104857600" 			/>
		<property field="server_so_timeout" 			value="0" 					/>
	</cat:configs>
	
	<cat:listeners>
		<listener>
		    <handler>org.nico.aoc.listener.NoaocListener</handler>
			<payload>
				{
					"xmls":[
						"cat-mysql.xml",
						"cat-redis.xml"
					],
					"packs":[
						"org.nico.ct"
					],
					"compents":[
						"org.nico.cat.mvc.compent.MVCCompent"
					]
				}
			</payload>
		</listener>
		<listener>
		    <handler>org.nico.cat.mvc.listener.ListenerForNocat</handler>
			<payload>
				{"scanpack":"org.nico.ct.controller"}
			</payload>
		</listener>
	</cat:listeners>
	
	<cat:filters>
		<filter>
			<uri>/v1/authc/**</uri>
			<handler>org.nico.ct.section.AuthFilter</handler>
		</filter>
	</cat:filters>
	
	<cat:apis>
		<api>
			<uri>/v1/**</uri>
			<handler>org.nico.cat.mvc.router.RouterForNocat</handler>
		</api>
	</cat:apis>
	
</app>
```
配置**Noaoc**对**Mysql**及**Redis**的集成文件
> cat-mysql.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<books>
	<book id="DataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<param key="jdbcUrl"
			value="jdbc:mysql://localhost:3306/ct?useUnicode=true&characterEncoding=utf8&useSSL=false" />
		<param key="user" value="root" />
		<param key="password" value="root" />
		<param key="driverClass" value="com.mysql.jdbc.Driver" />
		<param key="maxPoolSize" value="100" />
		<param key="minPoolSize" value="10" />
<!-- 		<param key="maxIdleTime" value="0" /> -->
	</book>

	<book id="MysqlSession" class="org.nico.db.session.branch.MysqlSession">
		<param key="autoCommit" value="false" />
		<label name="dataSource" ref="DataSource" />
	</book>

	<book id="MysqlDBHelper" class="org.nico.db.helper.impl.MysqlDBHelper">
		<param key="printLog" value="false" />
		<label name="session" ref="MysqlSession" />
	</book>

	<book id="TransationManager" class="org.nico.db.datasource.TransationManager">
		<label name="dataSource" ref="DataSource" />
	</book>

	<aspect id="TransationSection" class="org.nico.ct.section.TransationSection">
		<aop execution="expression(org.nico.ct.service.impl.*.*(..))">
			<before proxy-method="before" />
			<around proxy-method="around" />
			<after proxy-method="after" />
			<wrong proxy-method="wrong" />
		</aop>
	</aspect>

</books>
```
> cat-redis.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<books>
	<book id="JedisPoolConfig" class="redis.clients.jedis.JedisPoolConfig">
		 <param key="maxTotal" value="200" />
		 <param key="maxIdle" value="50" />
		 <param key="testOnBorrow" value="true" />
		 <param key="testOnReturn" value="true" />
	</book>
	
	<book id="JedisPool" class="redis.clients.jedis.JedisPool">
		 <label name="poolConfig" ref="JedisPoolConfig" />
		 <param key="host" value="localhost" />
		 <param key="port" value="6379" />
		 <param key="timeout" value="30000" />
		 <param key="password" value="iamct" />
		 <param key="database" value="0" />
	</book>
	
	<book id="JedisUtils" class="org.nico.ct.util.JedisUtils">
		 <label name="jedisPool" ref="JedisPool" />
	</book>	 

</books>
```
> Nolog的配置 logno.properties：
```
nico.log.path	= noblog.log
nico.log.level	= DEBUG
nico.log.format	= ${time} [\t${threadId}\t] ${logType} ${className}\uFF1A${line}  -  ${message}
nico.log.print.stack.trace = on
#nico.log.type = LOG4J
```
#### 3.3.3、包结构
![输入图片说明](https://gitee.com/uploads/images/2018/0622/091404_06d5eeca_1607679.png "20180622011634646.png")
#### 3.3.4、Dao层配置
> BaseDao.java

![输入图片说明](https://gitee.com/uploads/images/2018/0622/091448_68429722_1607679.png "201806220117535.png")
> DaoImpl.java，@Dao用于声明一个Bean

![输入图片说明](https://gitee.com/uploads/images/2018/0622/091542_ef5f288b_1607679.png "20180622011859484.png")
#### 3.3.5、Service配置
> ServerImpl配置，@Label用于注入Bean，@Service用于声明一个Service

![输入图片说明](https://gitee.com/uploads/images/2018/0622/091620_7a20b863_1607679.png "20180622012018464.png")
#### 3.3.6、Controller配置及使用
> Controller基础配置

> @RestCinema用于声明一个Controller，直接返回Json

> @Lobby用于定制路由
![输入图片说明](https://gitee.com/uploads/images/2018/0622/091641_b7ea5d74_1607679.png "20180622012131470.png")
> path入参

> @PathParam声明该参数来源于path
![输入图片说明](https://gitee.com/uploads/images/2018/0622/091706_e14359cb_1607679.png "20180622012519870.png")
> body及url传参

> @Body用于声明参数来源于Body

> @QueryParam用于声明参数来源于url
![输入图片说明](https://gitee.com/uploads/images/2018/0622/091722_8ece7b40_1607679.png "20180622012607804.png")
> 参数验证

> @Verify声明一个需要进一步验证的实体类

> @NotNull不允许为空

> @Length限制长度

> @Match正则验证

> @Range范围验证

![输入图片说明](https://gitee.com/uploads/images/2018/0622/091740_b2ec5bfc_1607679.png "20180622012700367.png")
#### 3.3.7、Aop切面配置
> 数据库事务配置
```
package org.nico.ct.section;

import java.sql.SQLException;

import org.nico.aoc.aspect.AspectProxy;
import org.nico.aoc.aspect.point.AspectPoint;
import org.nico.aoc.aspect.point.ProcessingAspectPoint;
import org.nico.aoc.scan.annotations.After;
import org.nico.aoc.scan.annotations.Around;
import org.nico.aoc.scan.annotations.Before;
import org.nico.aoc.scan.annotations.Label;
import org.nico.aoc.scan.annotations.Wrong;
import org.nico.db.datasource.TransationManager;

//@Aspect	//这里不使用注解，使用XML配置增加清晰明了
public class TransationSection implements AspectProxy{

	@Label
	private TransationManager tm;
	
	@Override
	@Before(value = "expression(org.nico.ct.service.impl.*.*(..))")
	public void before(AspectPoint point) {
		try {
			tm.beginTransaction();  
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	@Around(value = "expression(org.nico.ct.service.impl.*.*(..))")
	public Object around(ProcessingAspectPoint point) throws Throwable {
		Object result = point.process();
		return result;
	}

	@Override
	@After(value = "expression(org.nico.ct.service.impl.*.*(..))")
	public void after(AspectPoint point) {
		try {
			tm.commitTransaction();
			tm.relaseConnection();
		} catch (SQLException e) {
		} 
	}

	@Override
	@Wrong(value = "expression(org.nico.ct.service.impl.*.*(..))")
	public void wrong(ProcessingAspectPoint point, Throwable e) throws Throwable{
		tm.rollbackTransaction();
		throw e;
	}

}
```
> 登录验证
![输入图片说明](https://gitee.com/uploads/images/2018/0622/091838_337cf736_1607679.png "2018062201331579.png")
#### 3.3.8、启动服务
![输入图片说明](https://gitee.com/uploads/images/2018/0622/091906_46ef2765_1607679.png "20180622013503203.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0622/091929_7c51ccc2_1607679.png "20180622013550452.png")
#### 3.3.9、前端页面存放
![输入图片说明](https://gitee.com/uploads/images/2018/0622/091958_530682e7_1607679.png "20180622013824512.png")
#### 3.3.10、访问
![输入图片说明](https://gitee.com/uploads/images/2018/0622/092012_79e4c86a_1607679.png "20180622014041959.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0622/092024_b4f8b4fd_1607679.png "20180622014118338.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0622/092037_d1f30c59_1607679.png "20180622014143509.png")
![输入图片说明](https://gitee.com/uploads/images/2018/0622/092048_5a4507b9_1607679.png "20180622014216392.png")
视频直播流媒体支持
![视频流](https://gitee.com/uploads/images/2018/0622/092104_511b2231_1607679.png "20180622014606746.png")

## 四、继续努力

### 4.1、发现Bug
如果您发现项目中存在BUG或者设计不合理的地方，非常希望能够提``Issues```给我，让NF框架越来越完美
### 4.2、下一步计划
 - 使用NF写完CoffeeTime
 - 使用NF写完CoffeeBlog
 - 完善Nocat上传模块
 - 增加RPC模块
 - Noaoc增加定时
 - 优化Nodb
 - 以后再定

### 4.3、联系我
Email - ```ainililia@163.com```

QQ  - ```473048656```

交流群 - ```177563526```
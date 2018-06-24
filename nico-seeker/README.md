# 介绍

#### 为什么取名Seeker

我创作Seeker的初衷是为了开发一个可视化爬虫系统，而Seeker作为其中的组件之一英译的含义是`n. 探求者；搜查人`，我认为这个名字很适合它，所以决定定名为Seeker~

#### Seeker的作用

Seeker作为组件之一，它所处的角色就是解析+搜索，它可以将一个超文本解析成一个可操作的容器，我们使用这个容器来获取我们想要的信息。

#### Seeker的结构

Seeker目前由一下几个组块总成：

> *   Document：文本存储结构及字典
>     
>     
> *   Regex ：负责提供正则支持
>     
>     
> *   Scanner ：负责扫描
>     
>     
> *   Searcher ：负责搜索
>     
>     
> *   Http ：负责Http请求
>     
>     
> *   Stream ：负责数据流处理
>     
>     
> *   Plan：定制计划
>     
>     
> *   Starter：负责计划的启动
>     
>     
> *   StarterHelper：快速启动

其中**Scanner**的作用在其中处于核心的地位，超文本解析处理全靠它，不过很遗憾的是目前解析算法的时间复杂度和空间复杂度都是`O(n^2)`，这个待优化中。

# 详解

在使用Seeker之前，我将详细介绍一下前面所讲的几个组块没块的协作流程。

### Document

#### DomBean

DomBean（文本bean）一块是负责储存Scanner扫描后的数据，它的数据结构是一个普通树（tree），比如以下文本

```
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
    </head>
    <body>

    </body>
</html>

```

存储后的结构如下

![image.png](http://nginx.ikuvn.com:1024/images/261504761175853.jpg)

每一个标签都相当于一个DomBean，它的子标签会存储在它自身的一个List中，他的标签属性会存储在自身的map当中，附带着存储的还有他的标签名和其他一些有用的属性。

#### DomHelper

它可以将DomBean解析成一个html文档（字符串），可以更好的查看当前DomBean的结构

#### DomMember

负责标签的注册，其中分为三个注册商

> *   MEMBERS_NORMAL
>     
>     
> *   MEMBERS_SPECIAL
>     
>     
> *   MEMBERS_OTHER

**MEMBERS_NORMAL** 面对的群体是普通的标签，类似于`<div></div>`带标签体的标签和`<input/>`自封闭的标签

**MEMBERS_SPECIAL** 面的的是`<img>`可以不封闭的标签

**MEMBERS_OTHER** 面对的是`<script></script>`和`<style></style>`这种不属于Html范畴的标签

在Scanner中这三个注册商非常重要，它们在其中负责对标签类型的验证以达到用不同的方式去解析文档~

### Regex

这一块主要负责一些正则表达式的定义和提供一个对外可以直接使用的正则方法，忽略不讲了

### Scanner

Scanner对于整个Seeker服务来讲是非常重要的，对外它解析外来文本，对内提供解析结果存储在DomBean结构中来对其他服务提供数据，它的解析流程图如下

![image.png](http://nginx.ikuvn.com:1024/images/261504764970368.jpg)

通过上述算法及细节处理可以适应多种情景文档的读取

### Seacher

负责提供搜索服务，搜索对象就是Scanner层解析后的DomBean对象，下面是实现的其中一个搜索器之一`NicoSeacher`

```
        /**
	 * 通过前后缀及属性名搜索
	 * @param prefix
	 * @param paramName
	 * @param paramValue
	 * @param domBeans
	 * @param bank
	 * @param searchingUnique
	 */
private void searchingAssemble(String prefix, String paramName, String paramValue, List<DomBean> domBeans, List<DomBean> bank, boolean searchingUnique, DomBean[] tmpDomBeans){
		if(domBeans == null) return;
		if(tmpDomBeans != null) domBeans = Arrays.asList(tmpDomBeans);
		Iterator<DomBean> items = domBeans.iterator();
		while(items.hasNext()){
			DomBean domBean = items.next();
			if(((domBean.get(paramName) != null 
					&& domBean.get(paramName).equals(paramValue)) 
					|| StringUtils.isBlank(paramName) )
					&& ( domBean.getPrefix().equals(prefix) || StringUtils.isBlank(prefix) )){
				bank.add(domBean);
				if(searchingUnique){
					return;
				}
			}
			if(this.globalCheck){
				searchingAssemble(prefix, paramName, paramValue, domBean.getDomProcessers(), bank, searchingUnique, null);
			}
		}
	}
```

这一部分可以通过实现`com.nico.seeker.searcher.SeekerSearcher`类来换成自己的搜索器

### HTTP

对外提供**HTTP**的`Get`和`Post`请求来获取对应资源的页面源码，提供给Scanner解析

### Stream

流的处理，可以将文件内容读取，提供给Scanner解析

### Plan

自定义计划，可以让Seeker根据Plan的内容进行计划爬取

#### SeekerTrack

自定义爬虫轨迹，其数据结构如下

```
private String uri;

    /*
     * 请求类型
     */
    private HttpMethod httpMethod;

    /*
     * 请求参数
     */
    private Map<String, Object> params;

    /*
     * 具体轨迹信息
     */
    private List<TrackBean> trackBeans;

    /*
     * 搜索器 
     */
    private String searcher;

    /*
     * 收获
     */
    private List<HarvestBean> harvestCollect = new ArrayList<HarvestBean>();

```

其中有两个对象`TrackBean`和`HarvestBean`

**TrackBean**负责定制每一条路线具体的**行程**，数据结构如下

```
/*
     * 前缀
     */
    private String prefix;

    /*
     * 属性内容
     */
    private String paramValue;

    /*
     * 属性内容 
     */
    private String paramName;

    /*
     * 是否收获
     */
    private Boolean recycle;

    /*
     * 是否初始化
     */
    private Boolean reset;

    /*
     * 子流程
     */
    private List<TrackBean> trackBeans;

    /*
     * 记录
     */
    private String record;

```

**HarvestBean**负责存储需要**收获**的轨迹所收获的数据

这两者在**SeekerTrack**之中共存，相互合作实现`定制计划`=>`分析`=>`轨迹搜索`=>`存储`的流程

### Start

负责以上整个流程的启动

### StartHelper

可以将流程定制为Json格式的数据快速启动，Json格式如下

```
{    
'header':{
            'method':'get',
            'seacher':'com.nico.seeker.searcher.impl.NicoSearcher',
            'uri':'http://tieba.baidu.com/f',
            'params':[{"key":"kw","value":"java"},{"key":"ie","value":"utf-8"},{"key":"pn","value":"50"}]
        },
'tracks':[
            {'prefix':'div','paramName':'class','paramValue':'threadlist_title pull_left j_th_tit '},
            {'prefix':'a','recycle':'true','record':'帖子链接'}
        ]
}

```

StartHelper会解析Json数据自动装配SeekerTrack来供Start启动

# 使用
## 1、超文本解析
Seeker对文本的解析分两步：扫描 & 搜索

在解析一个超文本内容之前我们先拟定获取的文本内容为dom，接下来我们首先初始化扫描器：
```
SeekerScanner scanner = new NicoScanner(dom);
```
接下来获取搜索器
```
SeekerSearcher searcher = new NicoSearcher(scanner);
```
SeekerSearcher对外提供一下方法：

```
public interface SeekerSearcher {
	
	/**
	 * 查找DomBean集合
	 * @param prefix		标签前缀（div、span、ul） 
	 * @param paramName		属性名
	 * @param paramValue	属性内容
	 * @return
	 */
	public SeekerSearcher searching(String prefix, String paramName, String paramValue);
	
	/**
	 * 查找DomBean集合
	 * @param prefix		标签前缀（div、span、ul） 
	 * @param paramName		属性名
	 * @param paramValue	属性内容
	 * @param tmpDomBeans		要搜索的集合
	 * @return
	 */
	public SeekerSearcher searching(String prefix, String paramName, String paramValue, DomBean[] tmpDomBeans);
	
	/**
	 * 查找DomBean集合
	 * @param prefix		标签前缀（div） 
	 * @return
	 */
	public SeekerSearcher searching(String prefix);
	
	/**
	 * 查找DomBean集合
	 * @param prefix		标签前缀（div） 
	 * @param tmpDomBeans		要搜索的集合
	 * @return
	 */
	public SeekerSearcher searching(String prefix, DomBean[] tmpDomBeans);
	
	/**
	 * 查找DomBean集合
	 * @param prefix		标签前缀（div） 
	 * @param ret			true：不跟进
	 * @return
	 */
	public SeekerSearcher searching(String prefix, boolean ret);
	
	/**
	 * 查找DomBean集合
	 * @param prefix		标签前缀（div） 
	 * @param ret			true：不跟进
	 * @param tmpDomBeans		要搜索的集合
	 * @return
	 */
	public SeekerSearcher searching(String prefix, boolean ret, DomBean[] tmpDomBeans);
	
	/**
	 * 查找DomBean集合
	 * @param prefix		标签前缀（div、span、ul） 
	 * @param paramName		属性名
	 * @param paramValue	属性内容
	 * @param ret			true：不跟进
	 * @return
	 */
	public SeekerSearcher searching(String prefix, String paramName, String paramValue, boolean ret);
	
	/**
	 * 查找DomBean集合
	 * @param prefix		标签前缀（div、span、ul） 
	 * @param paramName		属性名
	 * @param paramValue	属性内容
	 * @param ret			true：不跟进
	 * @param tmpDomBeans		要搜索的集合
	 * @return
	 */
	public SeekerSearcher searching(String prefix, String paramName, String paramValue, boolean ret, DomBean[] tmpDomBeans);
	
	/**
	 * 获取DomBean集合
	 * @return
	 */
	public List<DomBean> getResults();
	
	/**
	 * 获取单个DomBean
	 * @return
	 */
	public DomBean getSingleResult();
	
	/**
	 * 设置DomBeans搜索对象
	 * @param domBeans
	 */
	public SeekerSearcher setDomBeans(DomBean[]  domBeans);
	
	/**
	 * 设置DomBeans搜索对象
	 * @param domBeans
	 */
	public SeekerSearcher setDomBeans(List<DomBean> domBeans);
	
	/**
	 * 是否开启全局搜索（默认开启）
	 * @param globalCheck
	 */
	public SeekerSearcher setGlobalCheck(boolean globalCheck);
	
	/**
	 * 回到根节点
	 */
	public SeekerSearcher reset();
}
```
### 使用示例：
以一下文本为例：
```
<?xml version="1.0" encoding="UTF-8"?>
<books>
    <book id="dataSource" class="com.nico.db.datasource.DataSource">
        <param key="uri" value="jdbc:mysql://localhost:3306/nodb?useUnicode=true&amp;characterEncoding=utf8" />
        <param key="username" value="root" />
        <param key="password" value="root" />
        <param key="driver" value="com.mysql.jdbc.Driver" />
    </book>
    
    <book id="session" class="com.nico.db.session.branch.MysqlSession">
        <label name="dataSource" ref="dataSource"/>
    </book>
    
    <book id="helper" class="com.nico.db.helper.impl.MysqlDBHelper">
        <label name="session" ref="session"/>
    </book>
    
    <book id="CustomerDaoImpl" class="com.nico.example.dao.impl.CustomerDaoImpl">
    	 <param key="name" value="nico" />
    	 <label name="dataSource" ref="dataSource"/>
    </book>
</books>
```
#### 获取所有的book标签
```
seacher.searching("book").getResults()
```
获取结果
```
[DomBean [param={id=dataSource, class=com.nico.db.datasource.DataSource}, prefix=book, selfSealing=false, paramStr=id="dataSource" class="com.nico.db.datasource.DataSource", body=
        <param key="uri" value="jdbc:mysql://localhost:3306/nodb?useUnicode=true&amp;characterEncoding=utf8" />
        <param key="username" value="root" />
        <param key="password" value="root" />
        <param key="driver" value="com.mysql.jdbc.Driver" />
    ], DomBean [param={id=session, class=com.nico.db.session.branch.MysqlSession}, prefix=book, selfSealing=false, paramStr=id="session" class="com.nico.db.session.branch.MysqlSession", body=
        <label name="dataSource" ref="dataSource"/>
    ], DomBean [param={id=helper, class=com.nico.db.helper.impl.MysqlDBHelper}, prefix=book, selfSealing=false, paramStr=id="helper" class="com.nico.db.helper.impl.MysqlDBHelper", body=
        <label name="session" ref="session"/>
    ], DomBean [param={id=CustomerDaoImpl, class=com.nico.example.dao.impl.CustomerDaoImpl}, prefix=book, selfSealing=false, paramStr=id="CustomerDaoImpl" class="com.nico.example.dao.impl.CustomerDaoImpl", body=
    	 <param key="name" value="nico" />
    	 <label name="dataSource" ref="dataSource"/>
    ]]
```
#### 获取book标签下的param标签
```
seacher.searching("book").searching("param").getResults()
```
获取结果
```
[DomBean [param={value=jdbc:mysql://localhost:3306/nodb?useUnicode=true&amp;characterEncoding=utf8, key=uri}, prefix=param, selfSealing=true, paramStr=key="uri" value="jdbc:mysql://localhost:3306/nodb?useUnicode=true&amp;characterEncoding=utf8" , body=null], DomBean [param={value=root, key=username}, prefix=param, selfSealing=true, paramStr=key="username" value="root" , body=null], DomBean [param={value=root, key=password}, prefix=param, selfSealing=true, paramStr=key="password" value="root" , body=null], DomBean [param={value=com.mysql.jdbc.Driver, key=driver}, prefix=param, selfSealing=true, paramStr=key="driver" value="com.mysql.jdbc.Driver" , body=null], DomBean [param={value=nico, key=name}, prefix=param, selfSealing=true, paramStr=key="name" value="nico" , body=null]]
```
#### 获取第一个book标签下的param标签
```
DomBean domBean = seacher.searching("book").getSingleResult();
		List<DomBean> results = seacher.searching("param", new DomBean[]{domBean}).getResults();
		System.out.println(results);
```
获取结果
```
[DomBean [param={value=jdbc:mysql://localhost:3306/nodb?useUnicode=true&amp;characterEncoding=utf8, key=uri}, prefix=param, selfSealing=true, paramStr=key="uri" value="jdbc:mysql://localhost:3306/nodb?useUnicode=true&amp;characterEncoding=utf8" , body=null], DomBean [param={value=root, key=username}, prefix=param, selfSealing=true, paramStr=key="username" value="root" , body=null], DomBean [param={value=root, key=password}, prefix=param, selfSealing=true, paramStr=key="password" value="root" , body=null], DomBean [param={value=com.mysql.jdbc.Driver, key=driver}, prefix=param, selfSealing=true, paramStr=key="driver" value="com.mysql.jdbc.Driver" , body=null]]
```
#### 获取key=password的标签
```
DomBean domBean = seacher.searching("param", "key", "password").getSingleResult();
		System.out.println(domBean);
```
获取结果
```
DomBean [param={value=root, key=password}, prefix=param, selfSealing=true, paramStr=key="password" value="root" , body=null]
```
#### reset()的使用
```
DomBean domBean = seacher.searching("param", "key", "password").getSingleResult();
		System.out.println(domBean);
		domBean = seacher.searching("param", "key", "username").getSingleResult();
		System.out.println(domBean);
		domBean = seacher.reset().searching("param", "key", "username").getSingleResult();
		System.out.println(domBean);
```
获取结果，第二次获取不到对应的标签，reset一下就可以获取到
```
DomBean [param={value=root, key=password}, prefix=param, selfSealing=true, paramStr=key="password" value="root" , body=null]
null
DomBean [param={value=root, key=username}, prefix=param, selfSealing=true, paramStr=key="username" value="root" , body=null]
```
#### 带回归的搜索
```
DomBean domBean = seacher.searching("param", "key", "password", true).getSingleResult();
		System.out.println(domBean);
		domBean = seacher.searching("param", "key", "username").getSingleResult();
		System.out.println(domBean);
```
获取结果，第二次获取可以获取到值
```
DomBean [param={value=root, key=password}, prefix=param, selfSealing=true, paramStr=key="password" value="root" , body=null]
DomBean [param={value=root, key=username}, prefix=param, selfSealing=true, paramStr=key="username" value="root" , body=null]
```
#### 设置搜索模板，获取第一个book标签的第一个param标签
```
List<DomBean> books = seacher.searching("param").getResults();
		DomBean domBean = seacher.searching("param", new DomBean[]{books.get(0)}).getSingleResult();
		System.out.println(domBean);
```
获取结果
```
DomBean [param={value=jdbc:mysql://localhost:3306/nodb?useUnicode=true&amp;characterEncoding=utf8, key=uri}, prefix=param, selfSealing=true, paramStr=key="uri" value="jdbc:mysql://localhost:3306/nodb?useUnicode=true&amp;characterEncoding=utf8" , body=null]
```

当然以上示例中的参数混搭效果更佳，使用前最好看一下接口提供的方法介绍及参数介绍。

## 2、网络爬虫

### Seacher级使用

我们拿CSDN专家组用户页做测试

首先我们需要获取一个Scanner对象

```
Map<String, Object> params = new HashMap<String, Object>();
        params.put("channelid", 0);
        params.put("page", 1);
        SeekerScanner scan = new NicoScanner("http://blog.csdn.net/peoplelist.html", HttpMethod.GET, params);

```

然后我们获取Seacher对象

```
SeekerSearcher ns = new NicoSearcher(scan);

```

接下来使用Seacher来进行搜索，比如获取页码

```
List<DomBean> domBeans = ns.searching("div", "class", "page_nav").searching("span").getResults();
        DomBean domBean = domBeans.get(0);
        System.out.println(domBean);

```

运行之后控制台打印结果

```
DomBean [param={}, prefix=span, selfSealing=false, paramStr=, body= 1515条  共127页]

```

我们再看一下对应的页面源码

```
<div class="page_nav">
    <span> 1515条  共127页</span>
    <strong>1</strong> 
    <a href="/peoplelist.html?&page=2">2</a>
    <a href="/peoplelist.html?&page=3">3</a> 
    <a href="/peoplelist.html?&page=4">4</a>
    <a href="/peoplelist.html?&page=5">5</a> 
    <a href="/peoplelist.html?&page=6">...</a> 
    <a href="/peoplelist.html?&page=2">下一页</a>
    <a href="/peoplelist.html?&page=127">尾页</a> 
</div>

```

在这里要说一下`searching`这个方法的规则

#### 搜索规则一

```
Scanner扫描后的数据集是包含整个文本所有标签的，没searching一次，则当前数据集就会变成searching之后的数据集，也就是说，没搜索一次，我们的数据集都在更新，下次搜索之后再前一次搜索的基础上进行检索！！

```

那么我们如果想恢复到最初的数据集呢？当然有办法~

`Seacher`提供给一个方法`public void reset()`可以恢复

#### 搜索规则二

```
Seacher默认搜索是对全局文档 进行搜索的，但是我们的文档可是分层的，如果我们只想搜索当前层的文档怎么办呢？

```

`Seacher`提供`public void setGlobalCheck(boolean globalCheck)`来改变当前搜索范围

`boolean globalCheck`设定

| 值 | 影响 |
| --- | --- |
| true | 全局搜索 |
| false | 只针对本层搜索 |

### Start级使用

使用Start级我们的流程制定比起Seacher级就规范了很多，同样上述操作获取页码，我们的代码就可以变成如下

```
//设置请求参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("channelid", 0);
        params.put("page", 1);
        //设置轨迹
        List<TrackBean> ts = new ArrayList<TrackBean>();
        //轨迹一
        ts.add(new TrackBean("div", "class", "page_nav"));
        //轨迹二（带回收和记录）
        TrackBean t = new TrackBean("span",true);
        t.setRecord("页码爬取");
        ts.add(t);
        //装入SeekerTrack
        SeekerTrack st = new SeekerTrack();
        st.setUri("http://blog.csdn.net/peoplelist.html");
        st.setParams(params);
        st.setSearcher("com.nico.seeker.searcher.impl.NicoSearcher");
        st.setHttpMethod(HttpMethod.GET);
        st.setTrackBeans(ts);
        //装入SeekerStart
        SeekerStart ss = new SeekerStart(st);
        //启动SeekerStart
        ss.run();
        //查看结果集
        for(HarvestBean hb: ss.getHarvestCollect()){
            System.out.println(hb);
        }

```

控制台打印结果

```
HarvestBean [time=2017-09-07 03:03:46, domBeans=[DomBean [param={}, prefix=span, selfSealing=false, paramStr=, body= 1515条 共127页]], record=页码爬取]

```

但是这样是不是也很繁琐呢？能不能更简单呢？请往下看~

### StartHelper启动方式

首先准备我们的Json文件

```
{
{    
'header':{
            'method':'get',
            'seacher':'com.nico.seeker.searcher.impl.NicoSearcher',
            'uri':'http://blog.csdn.net/peoplelist.html',
            'params':[{'key':'channelid','value':'0'},{'key':'page','value':'1'}]
        },
'tracks':[
            {'prefix':'div','paramName':'class','paramValue':'page_nav'},
            {'prefix':'span','recycle':'true','record':'页码爬取'}
        ]
}

```

接下来是Java代码

```
String tracks = "";
        try {
            tracks = NioUtils.readFileToString(new File("E://test/csdn.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        SeekerStarterHelper ssh = new SeekerStarterHelper(new SeekerStart());
        ssh.runHelper(tracks);
        List<HarvestBean> hbs = ssh.getHarvestCollect();
        for(HarvestBean h: hbs){
            System.out.println(h);
        }

```

控制台打印结果

```
HarvestBean [time=2017-09-07 03:33:11, domBeans=[DomBean [param={}, prefix=span, selfSealing=false, paramStr=, body= 1515条 共127页]], record=页码爬取]

```

很少的代码就可以爬取内容

# 后记

`Seeker`已托管于`GitHub [https://github.com/ainilili/seeker](https://github.com/ainilili/seeker)`
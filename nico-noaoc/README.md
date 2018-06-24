## 一、noaoc为什么叫noaoc
noaoc的命名分两部分，因为笔者English Name是nico，所以我已以no开头，至于aoc就是aop + ioc的缩写~
## 二、noaoc能做什么事情
上文说的已经很明显了，aop(切面编程)和ioc(控制反转)是noaoc最擅长的。不过noaoc作为轻量级的设计层面框架，目前只实现了部分功能->**依赖注入**，支持xml的形式和注解的形式。
aop的实现需要等待noasm的完善。
## 三、noaoc的使用
### 1、IOC
#### xml方式配置
noaoc支持xml方式的配置，例如如下xml配置片段
```
<book id="dataSource" class="com.nico.db.datasource.DataSource">
        <param key="uri" value="jdbc:mysql://localhost:3306/nodb?useUnicode=true&amp;characterEncoding=utf8" />
        <param key="username" value="root" />
        <param key="password" value="root" />
        <param key="driver" value="com.mysql.jdbc.Driver" />
</book>
```
每一个**book**标签代表一个对象的实例，标签body中的每一个**param**标签代表示例中的对应**key**值的属性值，也就是说：
```
<param key="username" value="root" />
```
其中的username对应```class="com.nico.db.datasource.DataSource"```中属性名为username的字段，并为它赋予初值root。

在noaoc中，需要被构造注入和set方法注入的非对象属性均以**param**标签表示，如果需要注入的属性为对象，则需要以下表示：
```
<book id="session" class="com.nico.db.session.branch.MysqlSession">
        <label name="dataSource" ref="dataSource"/>
</book>
```
**label**标签表示注入的属性是一个对象，同样的适用于构造注入和set注入。
#### annotation方式配置
相对于xml配置，annotation配置相对更加简单方便，请看下面的例子：
```
@Book
public class CustomerDaoImpl implements CustomerDao{

	@Label
	private NoDBHelper helper;
	
	private DataSource dataSource;
	
	private String name;
	
	@ConstructionLabel(
			labels = {
					@Label(type = LabelType.BYNAME, name = "dataSource")
					})
	public CustomerDaoImpl(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Customer> get(Map<String, Object> query, DBPage page) {
		return helper.select(query, page, Customer.class);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
```
noaoc提供三种注解：
- ```@Book``` 
- ```@Label```
- ```@ConstructionLabel``` 

##### @Book
```
/** 
 * Representing this class is a Book.
 * 
 * @author nico
 * @version createTime：2018年1月21日 下午2:08:06
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Book {

	String name() default "";
}
```

@Book注解只能存在于Class上方，代表当前类需要被noaoc实例化成对象并存储起来。当然如果当前类以存在于xml文档中并已被加载，则noaoc不会再去加载第二遍，而是从book仓库中获取到已被加载的对象然后对其进行接下来的操作。

@Book提供一个缺省属性值**name**：
```
@Book(name = "CustomerDao")
public class CustomerDaoImpl implements CustomerDao{}
```
如果设置name，noaoc将会以name值作为当前对象实例存储时的名字，否则将会默认以Class对象的SimpleName作为存储名。

##### @Label
```
/** 
 * Representing this property is an injection property.
 * 
 * @author nico
 * @version createTime：2018年1月21日 下午2:10:49
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Label {

	LabelType type() default LabelType.BYALL;
	
	String name() default "";
}
```

@Label注解存在于@Book标明的类中的属性值上方，通过Setter注入属性，@Label提供两个属性：
- ```type```
- ```name```

**type**代表当前字段注入时的方式，它是一个枚举类型的属性，其枚举属性有：
- ```BYTYPE```  通过类型查找
- ```BYNAME```  通过名字查找
- ```BYALL```   通过所有方式

特殊说明：BYALL属性会优先以BYNAME的形式寻找符合注入的注入，如果通过name查找为空，就会以BYTYPE的方式查找。

##### @ConstructionLabel
```
/** 
 * Representing this property is an injection property.
 * 
 * @author nico
 * @version createTime：2018年1月21日 下午2:10:49
 */

@Target({ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ConstructionLabel {

	Label[] labels();
}
```
@ConstructionLabel放于构造方法上，用来代表当前类实例化时需要注入的属性，其内部需要一个Label数组，数组中元素的顺序表示该构造方法形参的顺序：
```
@ConstructionLabel(
			labels = {
					@Label(type = LabelType.BYNAME, name = "dataSource")
					})
	public CustomerDaoImpl(DataSource dataSource){
		this.dataSource = dataSource;
	}
```

#### 实例对象的获取
从上文我们已经知道如何通过xml和annotation的方式创建对应的实例对象，下面就说明一下如何将noaoc用起来，如何获取实例对象。

noaoc提供两个扫描器：
- ```AnnotationScanner``` 注解扫描器
- ```ConfigScanner``` XML扫描器

它们都实现了同一个接口**BookScanner**：
```
/**
 * Base Book Scanner
 * 
 * @author nico
 * @version createTime：2018年2月4日 下午7:58:30
 */
public interface BookScanner {
	
	public BookShop scan(String uris) throws Exception ;
	
}
```
实现该接口只需要实现一个scan方法，其中的参数在annotation扫描方式下代表**包名**，在xml扫描方式下代表**xml文件名**，默认会到classpath下寻找对应的文件，且多个用```;```隔开，我们在项目加载的时候开启扫描：
```
BookScanner xmlScanner = new ConfigScanner();
BookScanner annotationScanner = new AnnotationScanner();
xmlScanner.scan("application-book.xml;application-config.xml");
annotationScanner.scan("com.nico.noaoc.example");
```
之后我们可以通过```BookShop```的单例获取我们想要的实例：
```
CustomerDaoImpl customerDaoImpl = (CustomerDaoImpl) BookShop.getInstance().get("customerDaoImpl").getObject();
```
以上就是目前noaoc版本的IOC模块的功能介绍，当然很多功能还有待完善，noaoc将会越来越成熟。







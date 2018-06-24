## 一、Noson
### 1、简介
Noson是一个轻量级的Json解析工具，也是我的开源项目中很重要的一份子。

### 2、提供功能

1. 序列化（Java Object转Json）
2. 反序列化（Json转Java Object）
3. 字段校验
4. 循环引用处理

### 3、部署
Noson没有任何依赖，以jar的形式导入或者源码的形式加载均可以快捷使用，即插即用。

## 二、开始使用
### 1、NosonConfig配置
Noson提供多种静态方法为Json操作提供技术支持，在使用之前，我们有必要了解一下Noson的配置参数。我们需要使用```org.nico.noson.NosonConfig```类来修改默认配置，配置参数均为静态参数：

参数名 | 类型 | 作用
---|---|---
ALLOW_EMPTY | Boolean | 默认值为True，设置为False时将会在序列化时忽略为NULL的字段。
ALLOW_EMPTY_TO_NULL | Boolean | 默认值为True，设置为False时将会为NULL值赋予初值。
ALLOW_CYCLE_MAX_COUNT | Int | 默认值为0，循环引用最大序列化次数。
DEFAULT_SCANNER | NoScanner | 默认Json扫描器
ALLOW_MODIFY | Set<String> | 允许Java Object反序列化的字段类型
DEFAULT_DATE_FORMAT | SimpleDateFormat | 默认日期转换格式

### 2、Json序列化与反序列化
首先我们先定义两个Json字符串：
```
//Single Json Object Str
String json = "{\"name\":nico,age:21,skill:[java,c,c#,python,php,javascript],deposit:0.0,info:{address:china,job:IT}}";

//Json Object Array
String jsonArray = "[" + json + "," + json + "]";
```
#### Json解析
```
//Noson parse
Noson noson = Noson.parseNoson(json);
List<Object> nosonArray = Noson.parseArray(jsonArray);

//Noson to Json
System.out.println("==========>> Noson to Json：");
System.out.println(json = noson.toString());
System.out.println(jsonArray = nosonArray.toString());
```
输出
```
==========>> Noson to Json：
{"name":"nico","age":21,"skill":["java","c","c#","python","php","javascript"],"deposit":0.0,"info":{"address":"china","job":"IT"}}
[{"name":"nico","age":21,"skill":["java","c","c#","python","php","javascript"],"deposit":0.0,"info":{"address":"china","job":"IT"}}, {"name":"nico","age":21,"skill":["java","c","c#","python","php","javascript"],"deposit":0.0,"info":{"address":"china","job":"IT"}}]
```
#### 获取参数
当我们获取到一个Noson对象的时候，我们可以当成Map来使用它。
```
//Get parameter from noson
System.out.println("==========>> Get parameter from noson：");
System.out.println("name\t" + noson.get("name"));
System.out.println("age\t" + noson.get("age"));
System.out.println("skill\t" + noson.get("skill"));
System.out.println("deposit\t" + noson.get("deposit"));
System.out.println("info\t" + noson.get("info"));
```
输出
```
==========>> Get parameter from noson：
name	nico
age	21
skill	[java, c, c#, python, php, javascript]
deposit	0.0
info	{"address":"china","job":"IT"}
```
#### Json反序列化为Map
```
//Convert to Map
Map<String, Object> testMap = Noson.convert(json, Map.class);
System.out.println("==========>> Convert to Map：");
System.out.println(testMap);
System.out.println();
```
输出
```
==========>> Convert to Map：
{name=nico, age=21, skill=[java, c, c#, python, php, javascript], deposit=0.0, info={address=china, job=IT}}
```
#### Json反序列化成List
```
//Convert to List
List<Object> testList = Noson.convert(jsonArray, List.class);
System.out.println("==========>> Convert to List：");
System.out.println(testList);
```
输出
```
==========>> Convert to List：
[{name=nico, age=21, skill=[java, c, c#, python, php, javascript], deposit=0.0, info={address=china, job=IT}}, {name=nico, age=21, skill=[java, c, c#, python, php, javascript], deposit=0.0, info={address=china, job=IT}}]
```
#### Json反序列化成Set
```
//Convert to Set
Set<Object> testSet = Noson.convert(jsonArray, Set.class);
System.out.println("==========>> Convert to Set：");
System.out.println(testSet);
System.out.println();
```
输出
```
==========>> Convert to Set：
[{name=nico, age=21, skill=[java, c, c#, python, php, javascript], deposit=0.0, info={address=china, job=IT}}]
```
#### Json反序列化成Java Object
```
//Convert to Java Object
System.out.println("==========>> Convert to Java Object：");
Nico nico = Noson.convert(json, Nico.class);
System.out.println(nico);
System.out.println();
```
输出
```
==========>> Convert to Java Object：
Nico [name=nico, age=21, skill=[c#, python, java, c, php, javascript], deposit=0.0, info=info [address=china, job=IT]]
```
#### Json反序列化成复杂类型
Noson支持复杂类型转换，使用```NoType<T>```作为要转换类型的入口来完成复杂类型序列化的工作。为了更好的完成这个示例，我们引入一个Java Object：
```
public class Nico {

	private String name;
	
	private int age;
	
	private Set<String> skill;
	
	private double deposit;
	
	private Info info;
	
	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Set<String> getSkill() {
		return skill;
	}

	public void setSkill(Set<String> skill) {
		this.skill = skill;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}
	
	@Override
	public String toString() {
		return "Nico [name=" + name + ", age=" + age + ", skill=" + skill + ", deposit=" + deposit + ", info=" + info
				+ "]";
	}

	public static class Info{
		
		private String address;
		
		private Job job;

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public Job getJob() {
			return job;
		}

		public void setJob(Job job) {
			this.job = job;
		}

		@Override
		public String toString() {
			return "info [address=" + address + ", job=" + job + "]";
		}
		
	}
	
	public static enum Job{
		
		IT,
		
		OTHER
		
	}
}
```
开始转换
```
//Convert to Complex Types
System.out.println("==========>> Convert to Complex Types：");
List<Nico> nicos = Noson.convert(jsonArray, new NoType<List<Nico>>(){});
System.out.println(nicos);
System.out.println();
```
输出
```
==========>> Convert to Complex Types：
[Nico [name=nico, age=21, skill=[c#, python, java, c, php, javascript], deposit=0.0, info=info [address=china, job=IT]], Nico [name=nico, age=21, skill=[c#, python, java, c, php, javascript], deposit=0.0, info=info [address=china, job=IT]]]
```
#### 对象序列化为Json
```
//Reversal Object to Json
System.out.println("==========>> Reversal Object to Json：");
System.out.println(Noson.reversal(testMap));
System.out.println(Noson.reversal(testList));
System.out.println(Noson.reversal(testSet));
System.out.println(Noson.reversal(nico));
System.out.println(Noson.reversal(nicos));
System.out.println();
```
输出
```
==========>> Reversal Object to Json：
{"name":"nico","age":21,"skill":["java","c","c#","python","php","javascript"],"deposit":0.0,"info":{"address":"china","job":"IT"}}
[{"name":"nico","age":21,"skill":["java","c","c#","python","php","javascript"],"deposit":0.0,"info":{"address":"china","job":"IT"}},{"name":"nico","age":21,"skill":["java","c","c#","python","php","javascript"],"deposit":0.0,"info":{"address":"china","job":"IT"}}]
[{"name":"nico","age":21,"skill":["java","c","c#","python","php","javascript"],"deposit":0.0,"info":{"address":"china","job":"IT"}}]
{"name":"nico","age":21,"skill":["c#","python","java","c","php","javascript"],"deposit":0.0,"info":{"address":"china","job":"IT"}}
[{"name":"nico","age":21,"skill":["c#","python","java","c","php","javascript"],"deposit":0.0,"info":{"address":"china","job":"IT"}},{"name":"nico","age":21,"skill":["c#","python","java","c","php","javascript"],"deposit":0.0,"info":{"address":"china","job":"IT"}}]
```
### 循环引用处理
Noson可以解决反序列化期间的对象循环引用带来的死循环问题。
#### Java Object循环引用解决
我们引入测试类```Cycle```
```
public class Cycle {

	private String id = null;
	
	private Cycle cycle = this;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Cycle getCycle() {
		return cycle;
	}

	public void setCycle(Cycle cycle) {
		this.cycle = cycle;
	}

	@Override
	public String toString() {
		return "CycleEntity [id=" + id + ", cycle=" + cycle + "]";
	}
}
```
测试代码
```
Cycle c = new Cycle();
System.out.println(Noson.reversal(c));
```
输出
```
{"id":null,"cycle":null}
```
#### 其他类型循环引用处理
对于其他类型的循环引用难题，Noson有通用的解决方案：
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
{"id":null,"cycle":null}
{"map":null}
[null]
[null]
{"list":[null],"map":null}
```
如果我们将```NosonConfig.ALLOW_CYCLE_MAX_COUNT```设置为**1**，控制台输出将会改变为：
```
{"id":null,"cycle":{"id":null,"cycle":null}}
{"map":{"map":null}}
[[null]]
[[null]]
{"list":[[null]],"map":{"list":[[null]],"map":null}}
```
如果我们想给Java基本类型字段为**null**的值赋予初值，我们可以设置```NosonConfig.ALLOW_EMPTY_TO_NULL```
```
NosonConfig.ALLOW_EMPTY_TO_NULL = false;
```
输出
```
{"id":"","cycle":{"id":"","cycle":null}}
{"map":{"map":null}}
[[null]]
[[null]]
{"list":[[null]],"map":{"list":[[null]],"map":null}}
```
如果我们不需要显示null对象，我们可以设置```NosonConfig.ALLOW_EMPTY```
```
NosonConfig.ALLOW_EMPTY = false;
```
输出
```
{"cycle":{}}
{"map":{}}
[[]]
[[]]
{"list":[[]],"map":{"list":[[]]}}
```
### 容错性
我对Noson容错性做了一个测试
```
//这里使用FastScanner解析器
NosonConfig.DEFAULT_SCANNER = new FastScanner();
		
String json = "[{},{list:[]},{'list':[{},{\"list\":[]}]},[{key:123}],[],{}]";
System.out.println(Noson.parseArray(json));
System.out.println();

json = "{value:[{\"key1\":a,'key2':b,key3:1c,key4:'\"d',key5:\"'e\",key6:':,[]{}',key7:,key8:'%$&%$&%^><:'}]}";
System.out.println(Noson.parseNoson(json).toString());
System.out.println(Noson.parseNoson(Noson.parseNoson(json).toString()));
```
输出
```
[{}, {"list":[]}, {"list":[{},{"list":[]}]}, [{"key":123}], [], {}]

{"value":[{"key1":"a","key2":"b","key3":"1c","key4":"\"d","key5":"\'e","key6":":,[]{}","key7":"","key8":"%$&%$&%^><:"}]}
{"value":[{"key1":"a","key2":"b","key3":"1c","key4":"\"d","key5":"\'e","key6":":,[]{}","key7":"","key8":"%$&%$&%^><:"}]}
```
### Noson注解使用
为了便于演示，结合开发中的业务，我们新建```Order```类：
```
public class Order {

	private String name;
	
	private double price;
	
	private int count;
	
	private String address;
	
	private List<Nico> books;
	
	public List<Nico> getBooks() {
		return books;
	}

	public void setBooks(List<Nico> books) {
		this.books = books;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Order [name=" + name + ", price=" + price + ", count=" + count + ", address=" + address + ", books="
				+ books + "]";
	}
	
}
```
测试
```
String json = "{name:订单1,price:2.95,count:3,address:China,books:[{}]}";
Order order = Noson.convert(json, Order.class);
System.out.println(order);
```
输出
```
Order [name=订单1, price=2.95, count=10, address=China, books=[{}]]
```
OK，一切正常，我们开始注解测试~
#### @NoIgnore 忽略字段
我们在```Order```里中随机挑选几个字段加上**@NoIgnore**注解
```
@NoIgnore
private String name;

@NoIgnore
private double price;

private int count;

private String address;

@NoIgnore
private List<Nico> books;
```
输出
```
Order [name=null, price=0.0, count=10, address=China, books=null]
```
#### @ParamVerify 字段校验
**@ParamVerify**提供反序列化时的字段校验功能，其中提供以下几种校验方式：
1. public int maxLength() default Integer.MAX_VALUE;
    字符串最大长度
2. public int minLength() default 0;
    字符串最小长度
3. public boolean accessNull() default true;
    是否允许为null
4. public double[] range() default {Double.MIN_VALUE, Double.MAX_VALUE};
    数字类型范围
5. public String textFormat() default "*";
    格式匹配（'abc*efg' -> 'abcdefg'）

当验证不通过时，Noson将会抛出```org.nico.noson.exception.VerifyException```异常，在正式业务处理中，我们可以通过捕获这个异常来处理序列化校验失败的场景。

我们在下```Order```中的加入注解
```
@ParamVerify(minLength = 2, maxLength = 6)
private String name;

@ParamVerify(range = {10, 88.8})
private double price;

@ParamVerify(range = {0, 5})
private int count;

@ParamVerify(textFormat = "China*")
private String address;

@NoIgnore
private List<Nico> books;
```
将以上注解场景翻译
1. 2 <= name.length <= 6
2. 10 <= price <= 88.8
3. 0 <= count <= 5
4. address.matches("China(.*)")

输出
```
Exception in thread "main" java.lang.RuntimeException: org.nico.noson.exception.VerifyException: Validation fails at：org.nico.noson.test.entity.Order[price] value not access < 10.0
	at org.nico.noson.scanner.depot.NoDepot.convert(NoDepot.java:99)
	at org.nico.noson.Noson.convert(Noson.java:80)
	at org.nico.noson.test.AnnotationTest.main(AnnotationTest.java:16)
Caused by: org.nico.noson.exception.VerifyException: Validation fails at：org.nico.noson.test.entity.Order[price] value not access < 10.0
	at org.nico.noson.util.reflect.FieldUtils.set(FieldUtils.java:46)
	at org.nico.noson.handler.convert.ConvertHandler.handleObject(ConvertHandler.java:71)
	at org.nico.noson.handler.convert.impl.ConvertObjectHandler.handle(ConvertObjectHandler.java:28)
	at org.nico.noson.handler.convert.impl.ConvertListHandler.handle(ConvertListHandler.java:34)
	at org.nico.noson.handler.convert.impl.ConvertMapHandler.handle(ConvertMapHandler.java:24)
	at org.nico.noson.handler.convert.impl.ConvertNoTypeHandler.handle(ConvertNoTypeHandler.java:27)
	at org.nico.noson.handler.convert.impl.ConvertVerityHandler.handle(ConvertVerityHandler.java:19)
	at org.nico.noson.handler.convert.ConvertHandlerQueue.handle(ConvertHandlerQueue.java:30)
	at org.nico.noson.scanner.depot.NoDepot.convert(NoDepot.java:97)
	... 2 more
```
报错信息
```
Validation fails at：org.nico.noson.test.entity.Order[price] value not access < 10.0
```
报错原因：price字段不满足```@ParamVerify(range = {10, 88.8})```规则，price必须满足```10 <= price <= 88.8
```
String json = "{name:订单1,price:23.5,count:10,address:China,books:[{}]}";
Order order = Noson.convert(json, Order.class);
System.out.println(order);
```
输出
```
Order [name=订单1, price=23.5, count=3, address=China, books=null]
```
如果我们将name值修改一下
```
String json = "{name:订,price:23.5,count:3,address:China,books:[{}]}";
Order order = Noson.convert(json, Order.class);
System.out.println(order);
```
输出
```
Exception in thread "main" java.lang.RuntimeException: org.nico.noson.exception.VerifyException: Validation fails at：org.nico.noson.test.entity.Order[name] length not access < 2
	at org.nico.noson.scanner.depot.NoDepot.convert(NoDepot.java:99)
	at org.nico.noson.Noson.convert(Noson.java:80)
	at org.nico.noson.test.AnnotationTest.main(AnnotationTest.java:16)
Caused by: org.nico.noson.exception.VerifyException: Validation fails at：org.nico.noson.test.entity.Order[name] length not access < 2
	at org.nico.noson.util.reflect.FieldUtils.set(FieldUtils.java:46)
	at org.nico.noson.handler.convert.ConvertHandler.handleObject(ConvertHandler.java:71)
	at org.nico.noson.handler.convert.impl.ConvertObjectHandler.handle(ConvertObjectHandler.java:28)
	at org.nico.noson.handler.convert.impl.ConvertListHandler.handle(ConvertListHandler.java:34)
	at org.nico.noson.handler.convert.impl.ConvertMapHandler.handle(ConvertMapHandler.java:24)
	at org.nico.noson.handler.convert.impl.ConvertNoTypeHandler.handle(ConvertNoTypeHandler.java:27)
	at org.nico.noson.handler.convert.impl.ConvertVerityHandler.handle(ConvertVerityHandler.java:19)
	at org.nico.noson.handler.convert.ConvertHandlerQueue.handle(ConvertHandlerQueue.java:30)
	at org.nico.noson.scanner.depot.NoDepot.convert(NoDepot.java:97)
	... 2 more
```
报错信息
```
Validation fails at：org.nico.noson.test.entity.Order[name] length not access < 2
```
报错原因：name必须满足（name：2 <= name.length <= 6）

## 三、总结
Noson还不算成熟，可能存在着很多意想不到的问题，成熟的项目都是在反复测试和实践使用以及反复迭代中磨炼出来的，如果大家在使用过程中遇到问题，请及时与我沟通。

email：ainililia@163.com



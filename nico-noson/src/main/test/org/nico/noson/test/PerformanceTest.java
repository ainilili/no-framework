package org.nico.noson.test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.nico.noson.Noson;
import org.nico.noson.test.entity.ComplexEntity;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PerformanceTest {

	private static Gson gson = new Gson();
	
	public static void main(String[] args) {
		
		int num = 10 * 10000;
//		int num = 100;
//		String simpleJson = ",{id:2018,str:字符串,num:28.18,date:'2018-4-7 16:42:48',type:TYPE2,list:[字符串1,字符串2,字符串3],map:{key1:value1,key2:value2,key3:value3},set:[1,2,3],floats:[10.2,10.3,10.4],entity:{id:2018,str:字符串,num:28.18,date:'2018-4-7 16:42:48',type:TYPE2,list:[字符串1,字符串2,字符串3],map:{key1:value1,key2:value2,key3:value3},set:[1,2,3]}}";
//		StringBuilder testJson = new StringBuilder("[{bigDecimal:15646.5445645645,id:2018,str:字符串,num:28.18,date:'2018-4-7 16:42:48',type:TYPE2,list:[字符串1,字符串2,字符串3],map:{key1:value1,key2:value2,key3:value3},set:[1,2,3],floats:[10.2,10.3,10.4],entity:{id:2018,str:字符串,num:28.18,date:'2018-4-7 16:42:48',type:TYPE2,list:[],map:{},set:[]}}");
//		for(int index = 0; index < num; index ++){
//			testJson.append(",{id:" + index + ",str:字符串,num:28.18,date:'2018-4-7 16:42:48',type:TYPE2,list:[字符串1,字符串2,字符串3],map:{key1:value1,key2:value2,key3:value3},set:[1,2,3],floats:[10.2,10.3,10.4],entity:{id:2018,str:字符串,num:28.18,date:'2018-4-7 16:42:48',type:TYPE2,list:[字符串1,字符串2,字符串3],map:{key1:value1,key2:value2,key3:value3},set:[1,2,3]}}");
//		}
		String simpleJson = ",{'id':'2018','str':'字符串'}";
		StringBuilder testJson = new StringBuilder("[{'id':'2018','str':'字符串'}");
		for(int index = 0; index < num; index ++){
			testJson.append(",{'id':'" + index + "','str':'字符串'}");
		}
		testJson.append("]");
		long start = System.nanoTime();
		List<ComplexEntity> list = nosonTest(testJson.toString(), new ArrayList<ComplexEntity>(){}.getClass());
//		List<ComplexEntity> list = gsonTest(testJson.toString(), new TypeToken<List<ComplexEntity>>(){}.getType());
//		List<ComplexEntity> list = JSON.parseObject(testJson.toString(), new TypeToken<List<ComplexEntity>>(){}.getType());
		System.out.println("序列化" + num + "耗时：" + (System.nanoTime() - start)/1000000 + "(ms)");
		System.out.println();
		System.out.println("转换结果验证：");
		ComplexEntity entity = list.get(0);
//		System.out.println(entity.getList().getClass());
//		System.out.println(entity.getSet().getClass());
//		System.out.println(entity.getMap().getClass());
//		System.out.println(entity.getFloats()[0]);
		System.out.println(entity);
		
		start = System.nanoTime();
		Noson.reversal(list);
//		gson.toJson(list);
//		JSON.toJSON(list);
		System.out.println("反序列化" + num + "耗时：" + (System.nanoTime() - start)/1000000 + "(ms)");
		
		
//		System.out.println(Noson.convert(Noson.reversal(entity), ComplexEntity.class));
	
	}
	
	public static <T> T nosonTest(String json, Class<T> clazz){
		return Noson.convert(json, clazz);
	}
	
	public static <T> T gsonTest(String json, Type clazz){
		return gson.fromJson(json, clazz);
	}
}

package org.nico.noson.test;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PerformanceTest1 {

	private static Gson gson = new Gson();
	
	public static void main(String[] args) {
		
		int num = 10 * 10000;
		String simpleJson = ",{id:2018,str:字符串,num:28.18,date:'2018-4-7 16:42:48',type:TYPE2,list:[字符串1,字符串2,字符串3],map:{key1:value1,key2:value2,key3:value3},set:[1,2,3],floats:[10.2,10.3,10.4],entity:{id:2018,str:字符串,num:28.18,date:'2018-4-7 16:42:48',type:TYPE2,list:[字符串1,字符串2,字符串3],map:{key1:value1,key2:value2,key3:value3},set:[1,2,3]}}";
		StringBuilder testJson = new StringBuilder("[{bigDecimal:15646.5445645645,id:2018,str:字符串,num:28.18,date:'2018-4-7 16:42:48',type:TYPE2,list:[字符串1,字符串2,字符串3],map:{key1:value1,key2:value2,key3:value3},set:[1,2,3],floats:[10.2,10.3,10.4],entity:{id:2018,str:字符串,num:28.18,date:'2018-4-7 16:42:48',type:TYPE2,list:[],map:{},set:[]}}");
		for(int index = 0; index < num; index ++){
			testJson.append(simpleJson);
		}
		testJson.append("]");
		long start = System.currentTimeMillis();
//		List<Map<String, Object>> list = Noson.convert(testJson.toString(), new NoType<List<Map<String, Object>>>(){});
		List<Map<String, Object>> list = gson.fromJson(testJson.toString(), new TypeToken<List<Map<String, Object>>>(){}.getType());
		System.out.println(System.currentTimeMillis() - start);
		System.out.println();
		System.out.println("转换结果验证：");
		System.out.println(list.get(0));
		
		start = System.currentTimeMillis();
//		Noson.reversal(list);
		gson.toJson(list);
//		JSON.toJSON(list);
		System.out.println("反序列化" + num + "耗时：" + (System.currentTimeMillis() - start) + "(ms)");
	
	}
	
	public static <T> T nosonTest(String json, Class<T> clazz){
		return Noson.convert(json, clazz);
	}
	
	public static <T> T gsonTest(String json, Type clazz){
		return gson.fromJson(json, clazz);
	}
}

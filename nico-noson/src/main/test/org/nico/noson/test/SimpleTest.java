package org.nico.noson.test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.noson.test.entity.Nico;

public class SimpleTest {

	public static void main(String[] args) {
		
		//Single Json Object Str
		String json = "{\"name\":nico,age:21,skill:[java,c,c#,python,php,javascript],deposit:0.0,info:{address:china,job:IT}}";
		
		//Json Object Array
		String jsonArray = "[" + json + "," + json + "]";

		//Noson parse
		Noson noson = Noson.parseNoson(json);
		List<Object> nosonArray = Noson.parseArray(jsonArray);

		//Noson to Json
		System.out.println("==========>> Noson to Json：");
		System.out.println(noson.toString());
		System.out.println(nosonArray.toString());
		System.out.println();

		//Get parameter from noson
		System.out.println("==========>> Get parameter from noson：");
		System.out.println("name\t" + noson.get("name"));
		System.out.println("age\t" + noson.get("age"));
		System.out.println("skill\t" + noson.get("skill"));
		System.out.println("deposit\t" + noson.get("deposit"));
		System.out.println("info\t" + noson.get("info"));
		System.out.println();

		//Convert to Map
		Map<String, Object> testMap = Noson.convert(json, Map.class);
		System.out.println("==========>> Convert to Map：");
		System.out.println(testMap);
		System.out.println(testMap.getClass());
		System.out.println();

		//Convert to List
		List<Object> testList = Noson.convert(jsonArray, List.class);
		System.out.println("==========>> Convert to List：");
		System.out.println(testList);
		System.out.println(testList.getClass());
		System.out.println();

		//Convert to Set
		Set<Object> testSet = Noson.convert(jsonArray, Set.class);
		System.out.println("==========>> Convert to Set：");
		System.out.println(testSet);
		System.out.println(testSet.getClass());
		System.out.println();

		//Convert to Java Object
		System.out.println("==========>> Convert to Java Object：");
		Nico nico = Noson.convert(json, Nico.class);
		System.out.println(nico);
		System.out.println();
		
		//Convert to Complex Types
		System.out.println("==========>> Convert to Complex Types：");
		json = "{list:[{map:{map:{list:[{map:{value:{\"name\":nico,age:21,skill:[java,c,c#,python,php,javascript],deposit:0.0,info:{address:china,job:IT}}}},{map:{value:{\"name\":nico,age:21,skill:[java,c,c#,python,php,javascript],deposit:0.0,info:{address:china,job:IT}}}}]}}},{map:{map:{list:[{map:{value:{\"name\":nico,age:21,skill:[java,c,c#,python,php,javascript],deposit:0.0,info:{address:china,job:IT}}}},{map:{value:{\"name\":nico,age:21,skill:[java,c,c#,python,php,javascript],deposit:0.0,info:{address:china,job:IT}}}}]}}}]}";
		Map<String, List<Map<String, Map<String, Map<String, List<Map<String, Map<String, Nico>>>>>>>> target = Noson.convert(json, new NoType<Map<String, List<Map<String, Map<String, Map<String, List<Map<String, Map<String, Nico>>>>>>>>>(){});
		System.out.println(target);
		System.out.println(target.get("list"));
		System.out.println(target.get("list").get(0));
		System.out.println(target.get("list").get(0).get("map"));
		System.out.println(target.get("list").get(0).get("map").get("map"));
		System.out.println(target.get("list").get(0).get("map").get("map").get("list"));
		System.out.println(target.get("list").get(0).get("map").get("map").get("list").get(0));
		System.out.println(target.get("list").get(0).get("map").get("map").get("list").get(0).get("map"));
		System.out.println(target.get("list").get(0).get("map").get("map").get("list").get(0).get("map").get("value"));
		System.out.println();
		
		//Reversal Object to Json
		System.out.println("==========>> Reversal Object to Json：");
		System.out.println(Noson.reversal(testMap));
		System.out.println(Noson.reversal(testList));
		System.out.println(Noson.reversal(testSet));
		System.out.println(Noson.reversal(nico));
		System.out.println(Noson.reversal(target));
		System.out.println();

	}
}

package org.nico.noson.test;

import org.nico.noson.Noson;
import org.nico.noson.NosonConfig;
import org.nico.noson.scanner.impl.SimpleScanner;


public class FaultToleranceTest {

	public static char c;
	
	public static void main(String[] args) {
		
		//这里使用FastScanner解析器
		NosonConfig.DEFAULT_SCANNER = new SimpleScanner();
		
		String json = "[{},{list:[]},{'list':[{},{\"list\":[[],[]]}]},[{key:123}],[],{}]";
		System.out.println(Noson.parseArray(json));
		System.out.println();
		
		json = "{value:[{\"key1\":a,'key2':b,key3:1c,key4:'\"d',key5:\"'e\",key6:':,[]{}',key7:,key8:'%$&%$&%^><:'}]}";
		System.out.println(Noson.parseNoson(json).toString());
		json = Noson.reversal(Noson.parseNoson(json));
		System.out.println(json);
		
		System.out.println(Noson.parseNoson(json));
		
		
	}
}

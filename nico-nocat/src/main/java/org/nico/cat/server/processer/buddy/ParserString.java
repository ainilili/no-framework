package org.nico.cat.server.processer.buddy;

import java.util.Map.Entry;
import java.util.prefs.NodeChangeEvent;

/**
 * Parse String
 * 
 * @author nico
 * @date 2018年4月11日
 */
public class ParserString {
	
	private String[] strs;
	
	private int pos = 0;
	
	public ParserString(String str) {
		setString(str);
	}
	
	public String readLine(){
		if(strs == null || pos == strs.length){
			return null;
		}else{
			return strs[pos++];
		}
	}
	
	public StrNode<String, String> readLineNode(){
		return readLineNode("[:]");
	}
	
	public StrNode<String, String> readLineNode(String split){
		String str = readLine();
		if(str == null){
			return null;
		}else{
			String[] nodes = str.split(split, 2);
			if(nodes.length == 2){
				return new StrNode<String, String>(nodes[0], nodes[1].trim());
			}else{
				return null;
			}
		}
	}
	
	public void reset(){
		pos = 0;
	}
	
	public void setString(String str){
		if(str != null){
			strs = str.split("\r\n|\n");
		}
	}
	
	/**
	 * Stroge current line String info with Key-Value
	 * 
	 * @author nico
	 * @date 2018年4月11日
	 */
	public static class StrNode<K, V> implements Entry<K, V>{

		private K key;
		
		private V value;
		
		public StrNode(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			this.value = value;
			return value;
		}
		
		
	}
	
}

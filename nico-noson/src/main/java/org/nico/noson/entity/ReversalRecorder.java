package org.nico.noson.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/** 
 * Used to store parsed objects in the parsing process.
 * 
 * @author nico
 */
public class ReversalRecorder {

	//Store the objects that have been loaded.
	private Collection<Object> loaders;
	
	//The number of times the object is loaded.
	//Used to determine whether or not to continue loading.
	private Map<Object, Count> loaderRecord; 
	
	public ReversalRecorder(){
		loaders = new ArrayList<Object>();
		loaderRecord = new HashMap<Object, Count>();
	}

	public boolean contains(Object obj){
		return loaders.contains(obj);
	}
	
	public int getCount(Object obj){
		return loaderRecord.get(obj.getClass()) == null ? 0 : loaderRecord.get(obj.getClass()).getValue();
	}
	
	public Object add(Object obj){
		loaders.add(obj);
		Count count = null;
		if((count = loaderRecord.get(obj.getClass())) != null){
			count.add();
		}else{
			loaderRecord.put(obj.getClass(), new Count());
		}
		return obj;
	}
	
	public Object remove(Object obj){
		loaders.remove(obj);
		Count count = null;
		if((count = loaderRecord.get(obj.getClass())) != null){
			count.red();
			if(count.getValue() == 0){
				loaderRecord.remove(obj.getClass());
			}
		}
		return obj;
	}
	
	public int size(){
		return loaderRecord.size();
	}
	
	public static class Count{
		private int value = 1;
		
		public void add(){
			this.value ++;
		}
		
		public void red(){
			this.value --;
		}

		public int getValue() {
			return value;
		}

	}
}

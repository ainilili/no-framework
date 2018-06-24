package org.nico.cat.server.response.buddy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** 
 * 对于Http Headers中的属性可为重复，例如“Set-Cookie”，对于这种情况，传统的Map不能满足数据结构存储要求（put会覆盖掉同名属性），
 * 所以{@link ResponseHeader}类就诞生了，对于需要替换掉的属性提供put方法，对于可以重复的属性提供add方法
 * @author nico
 * @version createTime：2018年2月4日 下午1:36:14
 */

public class ResponseHeader<K, V>{

	private List<Entry<K, V>> entryList;

	public ResponseHeader() {
		this.entryList = new ArrayList<Entry<K, V>>();
	}

	public int size() {
		return entryList.size();
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public boolean containsKey(Object key) {
		return getValues(key).size() > 0;
	}

	public List<V> getValues(Object key) {
		List<V> list = new ArrayList<V>();
		if(! isEmpty()){
			for(Entry<K, V> entry: entryList){
				if((key == null && entry.getKey() == null) || (entry.getKey() != null && entry.getKey().equals(key))){
					list.add(entry.getValue());
				}
			}
		}
		return list;
	}

	public V getValue(Object key) {
		V v = null;
		if(! isEmpty()){
			for(Entry<K, V> entry: entryList){
				if((key == null && entry.getKey() == null) || (entry.getKey() != null && entry.getKey().equals(key))){
					v = entry.getValue();
					break;
				}
			}
		}
		return v;
	}

	public V putFirst(K key, V value) {
		return put(key, value, false, false);
	}

	public V putLast(K key, V value) {
		return put(key, value, false, true);
	}

	public V putAll(K key, V value) {
		return put(key, value, true, true);
	}

	private V put(K key, V value, boolean isAll, boolean isLast) {
		if(! isEmpty()){
			if(! isLast){
				for(int index = 0; index < entryList.size(); index ++ ){
					Entry<K, V> entry = entryList.get(index);
					if((key == null && entry.getKey() == null) || (entry.getKey() != null && entry.getKey().equals(key))){
						entry.setValue(value);
						if(! isAll){
							break;
						}
					}
				}
			}else{
				boolean isTarget = false;
				for(int index = entryList.size() - 1; index >= 0; index -- ){
					Entry<K, V> entry = entryList.get(index);
					if((key == null && entry.getKey() == null) || (entry.getKey() != null && entry.getKey().equals(key))){
						entry.setValue(value);
						isTarget = true;
						if(! isAll){
							break;
						}
					}
				}
				if(! isTarget){
					add(key, value);
				}
			}
		}else{
			entryList.add(new Entry<K, V>(key, value));
		}
		return value;
	}

	public V add(K key, V value) {
		entryList.add(new Entry<K, V>(key, value));
		return value;
	}

	public Object removeAll(Object key) {
		return remove(key, true);
	}

	public Object removeLast(Object key) {
		return remove(key, false);
	}

	private Object remove(Object key, boolean isAll) {
		V v = null;
		if(! isEmpty()){
			for(int index = entryList.size() - 1; index >= 0; index --){
				Entry<K, V> entry = entryList.get(index);
				if((key == null && entry.getKey() == null) || (entry.getKey() != null && entry.getKey().equals(key))){
					v = entry.getValue();
					entryList.remove(index);
					if(! isAll){
						break;
					}
				}
			}
		}
		return v;
	}

	public void clear() {
		entryList = new ArrayList<Entry<K, V>>();
	}

	public Set<K> keySet() {
		Set<K> ks = new HashSet<K>();
		if(! isEmpty()){
			for(Entry<K, V> entry: entryList){
				ks.add(entry.getKey());
			}
		}
		return ks;
	}

	public Collection<V> values() {
		Collection<V> cvs = new ArrayList<V>();
		if(! isEmpty()){
			for(Entry<K, V> entry: entryList){
				cvs.add(entry.getValue());
			}
		}
		return cvs;
	}

	public List<Entry<K, V>> entryList() {
		return this.entryList;
	}

	public static class Entry<K, V>{

		private K key;

		private V value;

		public Entry(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public void setKey(K key) {
			this.key = key;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

	}

}

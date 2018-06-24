package org.nico.util.stack.impl;

import org.nico.util.stack.Stack;

@SuppressWarnings("unchecked")
public class ArrayStack<V> extends Stack<V> {
	
	private static final int DEFAULT_SIZE = 19;
	
	private static final float DEFAULT_CAPACITY = 0.7f;
	
	private static final int DEFAULT_MULTIPLE = 2;
	
	private V[] elements = (V[]) new Object[DEFAULT_SIZE];
	
	private int size = 0;
	
	private int index = 0;
	
	@Override
	public V get() {
		return elements[size - 1];
	}
	
	@Override
	public V push(V v) {
		elements[size++] = v;
		dilatation();
		return v;
	}

	@Override
	public V pop() {
		if(size > 0){
			V v = elements[--size];
			elements[size] = null;
			return v;
		}
		return null;
	}
	
	@Override
	public V popQueue() {
		if(index < size){
			V v = elements[index];
			elements[index++] = null;
			return v;
		}
		return null;
	}

	@Override
	public int size() {
		return size;
	}
	
	@Override
	public void clear() {
		elements = (V[]) new Object[DEFAULT_SIZE];
		size = 0;
	}
	
	/**
	 * 扩容
	 * @return
	 */
	public V[] dilatation(){
		int capacity = elements.length;
		if(size > capacity * DEFAULT_CAPACITY){
			V[] newElements = (V[]) new Object[capacity * DEFAULT_MULTIPLE];
			for(int index = 0; index < size; index ++ ){
				newElements[index] = elements[index];
			}
			elements = newElements;
			return newElements;
		}
		return elements;
	}

	

	

	

}

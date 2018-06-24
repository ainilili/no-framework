package org.nico.util.stack;

public abstract class Stack<V> {
	
	public abstract V push(V v);
	
	public abstract V pop();
	
	public abstract V popQueue();
	
	public abstract int size();
	
	public abstract void clear();
	
	public abstract V get();
}

package org.nico.aoc.book;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 存放book标签属性
 * @author nico
 *
 */
public class Book {
	
	private String id;
	
	private Class<?> clazz;
	
	private Object object;
	
	private BookTag bookTag;
	
	private Set<Relier> reliers;
	
	public Book(String id, Class<?> clazz) {
		this.id = id;
		this.clazz = clazz;
		this.reliers = new HashSet<Relier>();
	}
	
	public void newInstance() throws InstantiationException, IllegalAccessException{
		if(this.clazz != null){
			this.object = this.clazz.newInstance();
		}
	}

	public Set<Relier> getReliers() {
		return reliers;
	}

	public BookTag getBookTag() {
		return bookTag;
	}

	public void setBookTag(BookTag bookTag) {
		this.bookTag = bookTag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
	public static class Relier{
		
		private Book book;
		
		private String fieldName;

		public Relier(Book book, String fieldName) {
			super();
			this.book = book;
			this.fieldName = fieldName;
		}

		public Book getBook() {
			return book;
		}

		public void setBook(Book book) {
			this.book = book;
		}

		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
	}

}

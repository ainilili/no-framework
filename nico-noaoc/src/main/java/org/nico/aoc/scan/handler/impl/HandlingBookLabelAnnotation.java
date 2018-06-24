package org.nico.aoc.scan.handler.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nico.aoc.book.Book;
import org.nico.aoc.book.Book.Relier;
import org.nico.aoc.book.shop.BookShop;
import org.nico.aoc.scan.annotations.ConstructionLabel;
import org.nico.aoc.scan.annotations.Label;
import org.nico.aoc.scan.buddy.AnnotationBuddy;
import org.nico.aoc.scan.entity.LabelType;
import org.nico.aoc.scan.handler.LoaderHandler;
import org.nico.util.collection.ArrayUtils;
import org.nico.util.collection.CollectionUtils;

/** 
 * 
 * @author nico
 * @version createTime：2018年1月21日 下午2:43:25
 */

public class HandlingBookLabelAnnotation extends LoaderHandler{

	@Override
	public void handle(List<Book> newBooks) throws Throwable {
		for(Book book: newBooks){
			if(book != null){
				BookStorage bookStorage = instancingBook(bookShop, book);
				if(bookStorage.getObj() != null){
					book.setObject(bookStorage.getObj());
					inject.parameterSetInject(book.getObject(), bookStorage.getParameters());
				}else{
					logging.warning("The book " + book.getId() + " builder fail !!");
				}
			}
		}
		next(newBooks);
	}
	
	/**
	 * Append label object into sourceMap
	 * 
	 * @param bookShop books factory | books shop
	 * @param sourceMap the page parameters
	 * @param searcher searcher
	 * @throws Throwable 
	 */
	private void appendLabelParameters(BookShop bookShop, Map<Object, Object> sourceMap, Book book) throws Throwable{
		List<Field> fieldList = AnnotationBuddy.getLabelFields(book.getClazz());
		if(CollectionUtils.isNotBlank(fieldList)){
			for(Field field: fieldList){
				Label label = null;
				if((label = field.getDeclaredAnnotation(Label.class)) != null){
					assemblyLabelTarget(bookShop, field.getType(), label, field.getName(), sourceMap, book);
				}
			}
		}
	}
	
	/**
	 * Load the Label corresponding attribute.
	 * 
	 * @param bookShop {@link BookShop}
	 * @param clazz The target class
	 * @param label The target label
	 * @param name The target name
	 * @param sourceMap The target map
	 * @param currentBook Current operation book
	 * @throws Throwable 
	 */
	private void assemblyLabelTarget(BookShop bookShop, Class<?> clazz, Label label, String name, Map<Object, Object> sourceMap, Book currentBook) throws Throwable{
		Book targetBook = null;
		if(label.type() == LabelType.BYNAME){
			if((targetBook = bookShop.get(label.name())) != null){
				Object target = targetBook.getObject() == null ? instancingBook(bookShop, targetBook).getObj() : targetBook.getObject();
				if(target != null){
					sourceMap.put(name, target);
				}
			}
		}else if(label.type() == LabelType.BYTYPE){
			if(CollectionUtils.isNotBlank(bookShop.getBooks())){
				for(Book book: bookShop.getBooks()){
					if(clazz.isAssignableFrom(book.getClazz())){
						sourceMap.put(name, book.getObject());
						targetBook = book;
						break;
					}
				}
			}
		}else{
			if((targetBook = bookShop.get(label.name())) != null){
				Object target = targetBook.getObject() == null ? instancingBook(bookShop, targetBook).getObj() : targetBook.getObject();
				if(target != null){
					sourceMap.put(name, target);
				}
			}else{
				for(Book tb: bookShop.getBooks()){
					if(clazz.isAssignableFrom(tb.getClazz())){
						sourceMap.put(name, tb.getObject());
						targetBook = tb;
						break;
					}
				}
			}
		}
		if(targetBook != null){
			targetBook.getReliers().add(new Relier(currentBook, name));
		}
	}
	

	/**
	 * Instancing book's object
	 * 
	 * @param bookShop
	 * @param book
	 * @param searcher
	 * @return
	 * @throws Throwable 
	 */
	private BookStorage instancingBook(BookShop bookShop, Book book) throws Throwable{
//		logging.debug("Instancing the book with (id-class) " + book.getId() + "-" + book.getClazz().getName());
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		Constructor<?> constructor = AnnotationBuddy.getLabelConstructionParams(book.getClazz());
		if(constructor != null){
			ConstructionLabel constructionLabel = constructor.getDeclaredAnnotation(ConstructionLabel.class);
			Label[] labels = constructionLabel.labels();
			Parameter[] params = constructor.getParameters();
			if(ArrayUtils.isNotBlank(params)){
				if(params.length == labels.length){
					for(int index = 0; index < params.length; index ++){
						assemblyLabelTarget(bookShop, params[index].getType(), labels[index], labels[index].name(), parameters, book);
					}
				}else{
					logging.warning("The constructor's parameters size != ConstructionLabel's labels");
				}
			}
		}
		appendLabelParameters(bookShop, parameters, book);
		return new BookStorage(book.getObject() == null ? inject.parameterConstructorInject(book.getClazz(), parameters) : book.getObject(), parameters);
	}
	
	public static class BookStorage{

		private Object obj;

		private Map<Object, Object> parameters;

		public BookStorage(Object obj, Map<Object, Object> parameters) {
			super();
			this.obj = obj;
			this.parameters = parameters;
		}

		public Object getObj() {
			return obj;
		}

		public void setObj(Object obj) {
			this.obj = obj;
		}

		public Map<Object, Object> getParameters() {
			return parameters;
		}

		public void setParameters(Map<Object, Object> parameters) {
			this.parameters = parameters;
		}

	}

}

package org.nico.aoc.scan.handler.impl;

import java.lang.reflect.Method;
import java.util.List;

import org.nico.aoc.ConfigKey;
import org.nico.aoc.aspect.buddy.AspectBuddy;
import org.nico.aoc.book.Book;
import org.nico.aoc.scan.annotations.After;
import org.nico.aoc.scan.annotations.Around;
import org.nico.aoc.scan.annotations.Aspect;
import org.nico.aoc.scan.annotations.Before;
import org.nico.aoc.scan.annotations.Wrong;
import org.nico.aoc.scan.entity.AspectDic;
import org.nico.aoc.scan.handler.LoaderHandler;
import org.nico.aoc.throwable.DefinitionException;

/** 
 * Handling {@link Aspect} for <b>AOP</b>
 * 
 * @author nico
 * @version createTime：2018年3月7日 下午2:36:51
 */

public class HandlingAspectAnnotation extends LoaderHandler{

	@Override
	public void handle(List<Book> newBooks) throws Throwable {
		for(Book book: newBooks){
			Class<?> clazz = book.getClazz();
			if(clazz.isAnnotationPresent(Aspect.class)){
				Method[] methods = clazz.getDeclaredMethods();
				if(methods != null && methods.length > 0){
					for(AspectDic aspectDic: ConfigKey.ASPECT_DICTIONARIES_ORDER){
						for(Method subMethod: methods){
							if(subMethod.isAnnotationPresent(aspectDic.getAnnotationClass())){
								if(aspectDic.eq(Before.class)){
									handleBefore(bookShop.get(assemble.generateId(clazz)), subMethod);
								}else if(aspectDic.eq(Around.class)){
									handleAround(bookShop.get(assemble.generateId(clazz)), subMethod);
								}else if(aspectDic.eq(After.class)){
									handleAfter(bookShop.get(assemble.generateId(clazz)), subMethod);
								}else if(aspectDic.eq(Wrong.class)){
									handleWrong(bookShop.get(assemble.generateId(clazz)), subMethod);
								}
							}
						}
					}
				}
			}
		}
		next(newBooks);
	}
	
	/**
	 * Handle the method under the {@link Around} annotation.
	 * 
	 * @param book	The book of the current method.
	 * @param subMethod Current Method
	 * @throws DefinitionException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	private void handleAround(Book book, Method aspectMethod) throws NoSuchMethodException, SecurityException, DefinitionException{
		String value = aspectMethod.getDeclaredAnnotation(Around.class).value();
		AspectBuddy.handleAspect(book, aspectMethod, book.getClazz().getDeclaredAnnotation(Aspect.class).type().toString(), value, AspectDic.AROUND);
	}
	
	/**
	 * Handle the method under the {@link Before} annotation.
	 * 
	 * @param book	The book of the current method.
	 * @param subMethod Current Method
	 * @throws DefinitionException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	private void handleBefore(Book book, Method aspectMethod) throws NoSuchMethodException, SecurityException, DefinitionException{
		String value = aspectMethod.getDeclaredAnnotation(Before.class).value();
		AspectBuddy.handleAspect(book, aspectMethod, book.getClazz().getDeclaredAnnotation(Aspect.class).type().toString(), value, AspectDic.BEFORE);
	}
	
	/**
	 * Handle the method under the {@link After} annotation.
	 * 
	 * @param book	The book of the current method.
	 * @param subMethod Current Method
	 * @throws DefinitionException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	private void handleAfter(Book book, Method aspectMethod) throws NoSuchMethodException, SecurityException, DefinitionException{
		String value = aspectMethod.getDeclaredAnnotation(After.class).value();
		AspectBuddy.handleAspect(book, aspectMethod, book.getClazz().getDeclaredAnnotation(Aspect.class).type().toString(), value, AspectDic.AFTER);
	}
	
	/**
	 * Handle the method under the {@link Wrong} annotation.
	 * 
	 * @param book	The book of the current method.
	 * @param subMethod Current Method
	 * @throws DefinitionException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	private void handleWrong(Book book, Method aspectMethod) throws NoSuchMethodException, SecurityException, DefinitionException{
		String value = aspectMethod.getDeclaredAnnotation(Wrong.class).value();
		AspectBuddy.handleAspect(book, aspectMethod, book.getClazz().getDeclaredAnnotation(Aspect.class).type().toString(), value, AspectDic.WRONG);
	}
	

}

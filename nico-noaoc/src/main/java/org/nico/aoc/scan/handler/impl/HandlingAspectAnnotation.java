package org.nico.aoc.scan.handler.impl;

import java.lang.reflect.Method;
import java.util.List;

import org.nico.aoc.ConfigKey;
import org.nico.aoc.aspect.buddy.AspectBuddy;
import org.nico.aoc.book.Book;
import org.nico.aoc.scan.annotations.aspect.After;
import org.nico.aoc.scan.annotations.aspect.Around;
import org.nico.aoc.scan.annotations.aspect.Aspect;
import org.nico.aoc.scan.annotations.aspect.Before;
import org.nico.aoc.scan.annotations.aspect.Section;
import org.nico.aoc.scan.annotations.aspect.Wrong;
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
			Method[] methods = clazz.getDeclaredMethods();
			Book proxyBook = book;
			if(methods != null && methods.length > 0){
				if(clazz.isAnnotationPresent(Aspect.class)){
					
					for(AspectDic aspectDic: ConfigKey.ASPECT_DICTIONARIES_ORDER){
						for(Method method: methods){
							/**
							 * Analyze the proxy class with the {@link Aspect} annotation
							 */
							aspecting(proxyBook, method, aspectDic);
						}
					}
				}
			}
			
			clazz = book.getClazz();
			methods = clazz.getDeclaredMethods();
			
			for(Method method: methods){
				/**
				 * Handle methods with {@link Section} annotations and weave {@link AspectProxy}
				 */
				if(method.isAnnotationPresent(Section.class)){
					Section section = method.getDeclaredAnnotation(Section.class);
					AspectBuddy.aspectBySection(section.proxyBy(), book, method, section.type().toString());
				}
			}
			
		}
		next(newBooks);
	}
	
	/**
	 * Begin to weave
	 * 
	 * @param proxyBook
	 * 			Proxy book
	 * @param proxyMethod
	 * 			Proxy method
	 * @param aspectDic
	 * 			Aspect dic
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws DefinitionException
	 */
	public void aspecting(Book proxyBook, Method proxyMethod, AspectDic aspectDic) throws NoSuchMethodException, SecurityException, DefinitionException{
		if(proxyMethod.isAnnotationPresent(aspectDic.getAnnotationClass())){
			if(aspectDic.eq(Before.class)){
				handleBefore(proxyBook, proxyMethod);
			}else if(aspectDic.eq(Around.class)){
				handleAround(proxyBook, proxyMethod);
			}else if(aspectDic.eq(After.class)){
				handleAfter(proxyBook, proxyMethod);
			}else if(aspectDic.eq(Wrong.class)){
				handleWrong(proxyBook, proxyMethod);
			}
		}
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
	private void handleAround(Book proxyBook, Method aspectMethod) throws NoSuchMethodException, SecurityException, DefinitionException{
		String[] values = aspectMethod.getDeclaredAnnotation(Around.class).value();
		AspectBuddy.aspectByExecution(proxyBook, aspectMethod, proxyBook.getClazz().getDeclaredAnnotation(Aspect.class).type().toString(), values, AspectDic.AROUND);
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
	private void handleBefore(Book proxyBook, Method aspectMethod) throws NoSuchMethodException, SecurityException, DefinitionException{
		String[] values = aspectMethod.getDeclaredAnnotation(Before.class).value();
		AspectBuddy.aspectByExecution(proxyBook, aspectMethod, proxyBook.getClazz().getDeclaredAnnotation(Aspect.class).type().toString(), values, AspectDic.BEFORE);
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
	private void handleAfter(Book proxyBook, Method aspectMethod) throws NoSuchMethodException, SecurityException, DefinitionException{
		String[] values = aspectMethod.getDeclaredAnnotation(After.class).value();
		AspectBuddy.aspectByExecution(proxyBook, aspectMethod, proxyBook.getClazz().getDeclaredAnnotation(Aspect.class).type().toString(), values, AspectDic.AFTER);
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
	private void handleWrong(Book proxyBook, Method aspectMethod) throws NoSuchMethodException, SecurityException, DefinitionException{
		String[] values = aspectMethod.getDeclaredAnnotation(Wrong.class).value();
		AspectBuddy.aspectByExecution(proxyBook, aspectMethod, proxyBook.getClazz().getDeclaredAnnotation(Aspect.class).type().toString(), values, AspectDic.WRONG);
	}
	

}

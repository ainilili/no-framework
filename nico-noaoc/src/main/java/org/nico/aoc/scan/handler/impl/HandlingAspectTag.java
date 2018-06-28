package org.nico.aoc.scan.handler.impl;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.nico.aoc.ConfigKey;
import org.nico.aoc.aspect.buddy.AspectBuddy;
import org.nico.aoc.aspect.point.AspectPoint;
import org.nico.aoc.aspect.point.ProcessingAspectPoint;
import org.nico.aoc.book.Book;
import org.nico.aoc.book.BookTag;
import org.nico.aoc.scan.entity.AspectDic;
import org.nico.aoc.scan.entity.AspectType;
import org.nico.aoc.scan.handler.LoaderHandler;
import org.nico.aoc.throwable.DefinitionException;
import org.nico.aoc.throwable.LackException;
import org.nico.util.collection.CollectionUtils;
import org.nico.util.string.StringUtils;

/** 
 * 
 * @author nico
 * @version createTime：2018年3月10日 下午7:40:04
 */

public class HandlingAspectTag extends LoaderHandler{

	@Override
	protected void handle(List<Book> newBooks) throws Throwable {
		if(bookShop != null){
			if(CollectionUtils.isNotBlank(newBooks)){
				for(Book book: newBooks){
					BookTag bookTag = book.getBookTag();
					if(bookTag != null){
						if(bookTag.getName().equals(ConfigKey.TAG_ASPECT)){
							if(CollectionUtils.isNotBlank(bookTag.getSubTags())){
								for(BookTag aopTag: bookTag.getSubTags()){
									if(aopTag.getName().equals(ConfigKey.TAG_AOP)){
										loadAopTag(aopTag, book);
									}
								}
							}
						}
					}
				}
			}
		}
		next(newBooks);
	}

	private void loadAopTag(BookTag bookTag, Book book) throws LackException, NoSuchMethodException, SecurityException, DefinitionException{
		if(CollectionUtils.isBlank(bookTag.getSubTags())){
			throw new LackException("Tag [aop] subTags is requirex, but it's null");
		}
		if(bookTag.getProperties() == null){
			throw new LackException("Tag [aop] properties is required, but it's null");
		}
		String aopExecution = bookTag.getProperties().get(ConfigKey.ASPECT_EXECUTION);
		String proxyType = bookTag.getProperties().get(ConfigKey.ASPECT_PROXY_TYPE);
		if(proxyType == null){
			proxyType = AspectType.JDK_PROXY.toString().toLowerCase();
		}
		List<BookTag> subTags = bookTag.getSubTags();
		loadAopSubTags(subTags, aopExecution, proxyType, book);
	}

	private void loadAopSubTags(List<BookTag> subTags, String aopExecution, String proxyType, Book book) throws LackException, SecurityException, DefinitionException{
		for(AspectDic aspectDic: ConfigKey.ASPECT_DICTIONARIES_ORDER){
			for(BookTag subTag: subTags){
				if(subTag.getProperties() == null){
					throw new LackException("Tag [" + subTag.getName() + "] properties is requirex, but it's null");
				}
				if(subTag.getName().equalsIgnoreCase(aspectDic.toString())){
					Map<String, String> properties = subTag.getProperties();
					String value = aopExecution;
					String methodName = properties.get(ConfigKey.ASPECT_PROXY_METHOD);
					if(StringUtils.isBlank(methodName)){
						throw new LackException("Tag [" + subTag.getName() + "] properties must contains key [" + ConfigKey.ASPECT_PROXY_METHOD + "]");
					}
					if(properties.containsKey(ConfigKey.ASPECT_EXECUTION)){
						value = properties.get(ConfigKey.ASPECT_EXECUTION);
					}else{
						if(StringUtils.isBlank(value)){
							throw new LackException("Tag [" + subTag.getName() + "] properties must contains key [" + ConfigKey.ASPECT_EXECUTION + "]");
						}
					}
					Method aspectMethod = null;
					try {
						switch(aspectDic){
						case BEFORE: 
						case AFTER: 
							aspectMethod = book.getClazz().getDeclaredMethod(methodName, AspectPoint.class);
							break;
						case AROUND:
							aspectMethod = book.getClazz().getDeclaredMethod(methodName, ProcessingAspectPoint.class);
							break;
						case WRONG:
							aspectMethod = book.getClazz().getDeclaredMethod(methodName, ProcessingAspectPoint.class, Throwable.class);
							break;
						default:
							aspectMethod = book.getClazz().getDeclaredMethod(methodName, AspectPoint.class);
						}
						AspectBuddy.aspectByExecution(book, aspectMethod, proxyType, new String[] {value}, aspectDic);
					}catch(NoSuchMethodException e) {
						
					}
				}
			}
		}

	}
}

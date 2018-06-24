package org.nico.aoc.scan.handler.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nico.aoc.ConfigKey;
import org.nico.aoc.book.Book;
import org.nico.aoc.book.Book.Relier;
import org.nico.aoc.book.BookTag;
import org.nico.aoc.scan.handler.LoaderHandler;
import org.nico.aoc.throwable.BookNotFoundException;
import org.nico.util.collection.CollectionUtils;

/** 
 * 
 * @author nico
 * @version createTime：2017年12月16日 下午7:44:21
 */

public class HandlingBookLabelTag extends LoaderHandler{

	@Override
	public void handle(List<Book> newBooks) throws Throwable {
		if(CollectionUtils.isNotBlank(newBooks)){
			for(Book book: newBooks){
				BookTag bookTag = book.getBookTag();
				Map<Object, Object> parameters = new HashMap<Object, Object>();
				if(bookTag != null && CollectionUtils.isNotBlank(bookTag.getSubTags())){
					for(BookTag subTag: bookTag.getSubTags()){
						if(subTag.getName().equals(ConfigKey.TAG_LABEL)){
							String fieldName = subTag.getProperties().get(ConfigKey.BOOK_LABEL_NAME);
							String refName = subTag.getProperties().get(ConfigKey.BOOK_LABEL_REF);
							Book currentBook = bookShop.get(refName);
							if(currentBook == null){
								throw new BookNotFoundException("Not found Book [" + fieldName + "] to inject the " + book.getClazz());
							}else{
								Object target = currentBook.getObject();
								parameters.put(fieldName, target);
								/**
								 * Add Relier
								 */
								currentBook.getReliers().add(new Relier(book, fieldName));
							}
						}
					}
				}
				if(null != book.getObject()){
					inject.parameterSetInject(book.getObject(), parameters);
				}
			}
		}
		next(newBooks);
	}

}

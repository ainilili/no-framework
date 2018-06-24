package org.nico.aoc.scan.handler.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nico.aoc.ConfigKey;
import org.nico.aoc.book.Book;
import org.nico.aoc.book.Book.Relier;
import org.nico.aoc.book.BookTag;
import org.nico.aoc.book.shop.BookShop;
import org.nico.aoc.scan.handler.LoaderHandler;
import org.nico.aoc.throwable.BookNotFoundException;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;
import org.nico.seeker.dom.DomBean;
import org.nico.seeker.searcher.SeekerSearcher;
import org.nico.util.collection.CollectionUtils;

/** 
 * 
 * @author nico
 * @version createTime：2017年12月16日 下午3:49:39
 */

public class HandlingBookPageTag extends LoaderHandler{

	@Override
	public void handle(List<Book> newBooks) throws Throwable {
		if(CollectionUtils.isNotBlank(newBooks)){
			for(Book book: newBooks){
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
		BookTag bookTag = book.getBookTag();
		if(bookTag != null && bookTag.getSubTags() != null){
			for(BookTag subTag: bookTag.getSubTags()){
				if(subTag.getName().equals(ConfigKey.TAG_LABEL)){
					String ref = subTag.getProperties().get(ConfigKey.BOOK_LABEL_REF);
					String fieldName = subTag.getProperties().get(ConfigKey.BOOK_LABEL_NAME);
					Book currentBook = null;
					if(null != (currentBook = bookShop.get(ref))){
						Object target = currentBook.getObject() == null ? instancingBook(bookShop, bookShop.get(ref)).getObj() : bookShop.get(ref).getObject();
						if(target != null){
							sourceMap.put(fieldName, target);
							/**
							 * Add Relier
							 */
							currentBook.getReliers().add(new Relier(book, fieldName));
						}
					}else{
						throw new BookNotFoundException("Not found Book [" + fieldName + "] to inject the " + book.getClazz());
					}
				}
			}
		}
	}

	/**
	 * instancing book's object
	 * @param bookShop
	 * @param book
	 * @param searcher
	 * @return
	 * @throws Throwable 
	 */
	private BookStorage instancingBook(BookShop bookShop, Book book) throws Throwable{
		logging.debug(">>>> Instancing the book with (id-class) " + book.getId() + "-" + book.getClazz().getName());
		BookTag bookTag = book.getBookTag();
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		if(bookTag != null && bookTag.getSubTags() != null){
			for(BookTag subTag: bookTag.getSubTags()){
				if(subTag.getName().equals(ConfigKey.TAG_PAGE)){
					parameters.put(subTag.getProperties().get(ConfigKey.BOOK_PAGE_KEY), subTag.getProperties().get(ConfigKey.BOOK_PAGE_VALUE));
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

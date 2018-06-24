package org.nico.aoc.scan.buddy;

import java.util.ArrayList;
import java.util.List;

import org.nico.aoc.book.Book;
import org.nico.aoc.book.BookTag;
import org.nico.seeker.dom.DomBean;

/** 
 * Assemeble some entity uniformly
 * @author nico
 * @version createTime：2017年12月16日 下午3:24:41
 */
public class AssembleBuddy {

	/**
	 * Assemble book with id & clazz
	 * 
	 * @param id book's id
	 * @param clazz book's class
	 * @return book
	 * @throws ClassNotFoundException 
	 */
	public Book assembleBook(String id, String clazz, DomBean domBean) throws ClassNotFoundException{
		return assembleBook(id, Class.forName(clazz), domBean);
	}
	
	public Book assembleBook(String id, String clazz) throws ClassNotFoundException{
		return assembleBook(id, Class.forName(clazz), null);
	}
	
	private BookTag assembleBookTag(DomBean domBean){
		if(domBean == null)
			return null;
		BookTag bookTag = new BookTag();
		bookTag.setName(domBean.getPrefix());
		bookTag.setProperties(domBean.getParam());
		bookTag.setSubTags(assembleBookTags(domBean.getDomProcessers()));
		return bookTag;
	}
	
	private List<BookTag> assembleBookTags(List<DomBean> domBeans){
		List<BookTag> bookTags = new ArrayList<BookTag>();
		if(domBeans != null && domBeans.size() > 0){
			for(DomBean domBean: domBeans){
				bookTags.add(assembleBookTag(domBean));
			}
		}
		return bookTags;
	}
	
	public Book assembleBook(String id, Class<?> clazz){
		Book book = new Book(id, clazz);
		return book;
	}
	
	public Book assembleBook(String id, Class<?> clazz, DomBean domBean){
		Book book = new Book(id, clazz);
		book.setBookTag(assembleBookTag(domBean));
		return book;
	}
	
	/**
	 * Generate id of the class
	 * 
	 * @param clazz
	 * @return
	 */
	public String generateId(Class<?> clazz){
		return clazz.getSimpleName();
	}
}

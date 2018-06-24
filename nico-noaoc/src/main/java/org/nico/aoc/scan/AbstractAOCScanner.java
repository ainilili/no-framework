package org.nico.aoc.scan;


import java.util.List;

import org.nico.aoc.book.Book;
import org.nico.aoc.scan.buddy.AssembleBuddy;

/**
 * Base Book Scanner
 * 
 * @author nico
 * @version createTime：2018年2月4日 下午7:58:30
 */
public abstract class AbstractAOCScanner {
	
	/**
	 * assemble params into book
	 */
	protected AssembleBuddy assemble = new AssembleBuddy();
	
	public abstract List<Book> scan(List<String> uris) throws Exception;
	
}

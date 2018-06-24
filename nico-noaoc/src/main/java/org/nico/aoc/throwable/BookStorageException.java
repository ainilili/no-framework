package org.nico.aoc.throwable;

/** 
 * 
 * @author nico
 * @version createTime：2018年3月7日 下午2:55:23
 */

public class BookStorageException extends Exception{

	private static final long serialVersionUID = 1L;

	public BookStorageException(String bookId) {
		super(bookId + " already exist");
	}


}

package org.nico.aoc.scan.handler;

import java.util.List;

import org.nico.aoc.book.Book;
import org.nico.aoc.book.shop.BookShop;
import org.nico.aoc.inject.BookInject;
import org.nico.aoc.inject.impl.SimpleInject;
import org.nico.aoc.scan.buddy.AssembleBuddy;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;
import org.nico.seeker.searcher.SeekerSearcher;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年12月14日 下午10:47:20
 */
public abstract class LoaderHandler {
	
	protected Logging logging = LoggingHelper.getLogging(LoaderHandler.class);
	
	/**
	 * the nextHandler
	 */
	protected LoaderHandler nextHandler;
	
	/**
	 * Assemble params into book
	 */
	protected AssembleBuddy assemble = new AssembleBuddy();
	
	/**
	 * Inject params into book
	 */
	protected BookInject inject = new SimpleInject();
	
	/**
	 * Global book shop
	 */
	protected volatile BookShop bookShop = BookShop.getInstance();
	
	/**
	 * Excute next handler
	 * 
	 * @param bookShop BookShop
	 * @param searcher Searcher
	 * @throws Throwable 
	 */
	public void next(List<Book> newBooks) throws Throwable{
		if(nextHandler != null){
			nextHandler.handle(newBooks);
		}
	}
	
	/**
	 * Handle Book
	 * @param book be handled book
	 * @param searcher config searcher
	 */
	protected abstract void handle(List<Book> newBooks) throws Throwable;

}

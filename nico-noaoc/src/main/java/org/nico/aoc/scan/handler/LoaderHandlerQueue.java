package org.nico.aoc.scan.handler;

import java.util.LinkedList;
import java.util.List;

import org.nico.aoc.book.Book;
import org.nico.aoc.book.shop.BookShop;
import org.nico.aoc.scan.handler.impl.HandlingAspectAnnotation;
import org.nico.aoc.scan.handler.impl.HandlingAspectTag;
import org.nico.aoc.scan.handler.impl.HandlingBookLabelAnnotation;
import org.nico.aoc.scan.handler.impl.HandlingBookLabelTag;
import org.nico.aoc.scan.handler.impl.HandlingBookPageTag;

/** 
 * 
 * @author nico
 * @version createTime：2018年4月15日 下午2:03:16
 */

public class LoaderHandlerQueue {
	
	private static LinkedList<LoaderHandler> link;

	static{
		link = new LinkedList<LoaderHandler>();
		add(new HandlingBookPageTag());
		add(new HandlingBookLabelTag());
		add(new HandlingBookLabelAnnotation());
		add(new HandlingAspectTag());
		add(new HandlingAspectAnnotation());
	}

	public static void add(LoaderHandler handler){
		if(link.size() > 0){
			link.getLast().nextHandler = handler;
		}
		link.add(handler);
	}

	public static void handle(List<Book> newBooks) throws Throwable{
		if(link.size() > 0){
			link.getFirst().handle(newBooks);
		}
	}

}

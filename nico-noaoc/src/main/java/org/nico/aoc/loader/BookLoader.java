package org.nico.aoc.loader;

import java.util.List;

import org.nico.aoc.book.Book;
import org.nico.aoc.book.shop.BookShop;
import org.nico.aoc.scan.AocAbstractScanner;
import org.nico.aoc.scan.AocAnnotationScanner;
import org.nico.aoc.scan.AocConfigScanner;
import org.nico.aoc.scan.handler.LoaderHandlerQueue;

/** 
 * 
 * @author nico
 * @version createTime：2018年4月15日 下午1:59:13
 */

public class BookLoader {

	static AocAbstractScanner configScanner = new AocConfigScanner();
	static AocAbstractScanner annotationScanner = new AocAnnotationScanner();
	
	public static void loader(List<String> packs, List<String> uris) throws Throwable{
		List<Book> newBooks = configScanner.scan(uris);
		newBooks.addAll(annotationScanner.scan(packs));
		LoaderHandlerQueue.handle(newBooks);
	}
	
	public static void loaderByXML(List<String> uris) throws Throwable{
		List<Book> newBooks = configScanner.scan(uris);
		LoaderHandlerQueue.handle(newBooks);
	}
	
	public static void loaderByAnnotation(List<String> packs) throws Throwable{
		List<Book> newBooks = annotationScanner.scan(packs);
		LoaderHandlerQueue.handle(newBooks);
		BookShop bookShop = BookShop.getInstance();
	}
	
	public static void loaderCompent(List<String> compents) throws ClassNotFoundException {
		if(compents != null && ! compents.isEmpty()) {
			for(String compent: compents) {
				Class.forName(compent);
			}
		}
	}
}

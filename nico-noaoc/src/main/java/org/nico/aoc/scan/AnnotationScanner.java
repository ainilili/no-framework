package org.nico.aoc.scan;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.nico.aoc.ConfigKey;
import org.nico.aoc.book.Book;
import org.nico.aoc.book.shop.BookShop;
import org.nico.aoc.throwable.BookHandlingException;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;
import org.nico.util.collection.CollectionUtils;
import org.nico.util.reflect.ClassUtils;

/** 
 * 
 * @author nico
 * @version createTime：2018年1月21日 下午1:56:52
 */

public class AnnotationScanner extends AbstractAOCScanner{

	private Logging logging = LoggingHelper.getLogging(AnnotationScanner.class);

	@Override
	public List<Book> scan(List<String> packs) throws BookHandlingException {
		List<Book> newBooks = new ArrayList<Book>();
		if(packs == null || packs.isEmpty()){
			return newBooks;
		}
		BookShop bookShop = BookShop.getInstance();
		try {
			List<Class<?>> classes = new ArrayList<Class<?>>();
			for(String pack: packs){
				try{
					logging.info("Scanning the class package's name：" + pack);
					classes.addAll(ClassUtils.getClasses(pack));
				}catch(Exception e){
					logging.error("Scanner package's fail at：" + e.getMessage());
					logging.error(e);
				}
			}
			if(CollectionUtils.isNotBlank(classes)){
				for(int index = classes.size() - 1; index >= 0; index --){
					Class<?> clazz = classes.get(index);
					Annotation[] annotations = clazz.getDeclaredAnnotations();
					boolean access = false;
					if(annotations != null && annotations.length > 0){
						for(Annotation an: annotations){
							if(ConfigKey.ACCESS_BOOK_ABOUT_ANNOTATIONS.contains(an.annotationType())){
								String id = assemble.generateId(clazz);
								if(! bookShop.containsBook(id)){
									Book book = assemble.assembleBook(id, clazz);
									bookShop.put(book);
									newBooks.add(book);
								}
								break;
							}
						}
					}
				}
			}
		} catch (Throwable e) {
			throw new BookHandlingException(e.getMessage(), e);
		}
		return newBooks;
	}


}

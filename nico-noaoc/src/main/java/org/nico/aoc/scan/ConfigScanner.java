package org.nico.aoc.scan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.nico.aoc.ConfigKey;
import org.nico.aoc.book.Book;
import org.nico.aoc.book.shop.BookShop;
import org.nico.aoc.throwable.BookHandlingException;
import org.nico.aoc.throwable.BookLoaderException;
import org.nico.aoc.throwable.LackException;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;
import org.nico.seeker.dom.DomBean;
import org.nico.seeker.scan.SeekerScanner;
import org.nico.seeker.scan.impl.NicoScanner;
import org.nico.seeker.searcher.SeekerSearcher;
import org.nico.seeker.searcher.impl.NicoSearcher;
import org.nico.util.collection.CollectionUtils;
import org.nico.util.stream.StreamUtils;
import org.nico.util.string.StringUtils;


public class ConfigScanner extends AbstractAOCScanner{

	private Logging logging = LoggingHelper.getLogging(ConfigScanner.class);

	@Override
	public List<Book> scan(List<String> uris) throws Exception {
		List<Book> newBooks = new ArrayList<Book>();
		if(uris == null || uris.isEmpty()){
			return newBooks;
		}
		BookShop bookShop = BookShop.getInstance();
		try {
			SeekerScanner scanner = new NicoScanner();
			SeekerSearcher searcher = new NicoSearcher();
			List<DomBean> domBeans = new ArrayList<DomBean>();
			for(String uri: uris){
				try{
					logging.info("Begin scanning the document with uri：" + uri);
					String documents = StreamUtils.readStream2Str(ConfigKey.CLASSPATH + uri);
					if(StringUtils.isNotBlank(documents)){
						domBeans.addAll(scanner.domScan(documents));
					}
				}catch(Exception e){
					logging.error("Scanner xml fail at：" + e.getMessage());
					logging.error(e);
				}
			}
			searcher.setDomBeans(domBeans);
			List<DomBean> rootBeans = searcher.searching(ConfigKey.TAG_ROOT, true).getResults();
			if(CollectionUtils.isBlank(rootBeans)){
				throw new LackException("Lack root tag [books] in the xml");
			}
			domBeans.clear();
			for(DomBean rootBean: rootBeans) {
				domBeans.addAll(rootBean.getDomProcessers());
			}
			if(CollectionUtils.isNotBlank(domBeans)){
				for(DomBean domBean: domBeans){
					if(ConfigKey.ACCESS_BOOK_ABOUT_TAGS.contains(domBean.getPrefix())){
						String bookId = (String)domBean.get(ConfigKey.BOOK_ID);
						String bookClass = (String)domBean.get(ConfigKey.BOOK_CLASS);
						Book book = null;
						if(! bookShop.containsBook(bookId)){
							book = assemble.assembleBook(bookId, bookClass, domBean);
						}else{
							throw new BookLoaderException("Book id [" + bookId + "] already be loaded for class " + bookShop.get(bookId).getClazz());
						}
						if(null != book){
							bookShop.put(book);
							newBooks.add(book);
							logging.debug("Get book by (id-class) " + book.getId() + "-" + book.getClazz().getName());
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

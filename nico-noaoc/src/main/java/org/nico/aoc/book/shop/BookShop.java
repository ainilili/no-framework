package org.nico.aoc.book.shop;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.nico.aoc.book.Book;
import org.nico.aoc.throwable.BookStorageException;

public class BookShop {
	
	private static BookShop instance;
	
	private Map<String, Book> booksMap;

	private BookShop(){
		booksMap = new HashMap<String, Book>();
	}
	
	public static BookShop getInstance(){
		try {    
            if(instance != null){
            }else{  
                Thread.sleep(200);  
                synchronized (BookShop.class) {  
                    if(instance == null){
                        instance = new BookShop();  
                    }  
                }  
            }   
        } catch (InterruptedException e) {   
            e.printStackTrace();  
        }  
        return instance; 
	}
	
	public void put(Book book) throws BookStorageException{
		String id = book.getId().toLowerCase();
		if(booksMap.containsKey(id)){
			throw new BookStorageException(id);
		}
		booksMap.put(id, book);
	}
	
	public Book get(String id){
		return booksMap.get(id.toLowerCase());
	}
	
	public boolean containsBook(String id){
		return booksMap.containsKey(id.toLowerCase());
	}
	
	public Set<Book> getBooks(){
		Set<Book> books = new HashSet<Book>();
		books.addAll(booksMap.values());
		return books;
	}

	@Override
	public String toString() {
		return "BookShop [booksMap=" + booksMap + "]";
	}
	
}

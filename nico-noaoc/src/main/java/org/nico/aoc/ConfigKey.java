package org.nico.aoc;

import java.util.ArrayList;
import java.util.List;

import org.nico.aoc.scan.annotations.Aspect;
import org.nico.aoc.scan.annotations.Book;
import org.nico.aoc.scan.annotations.Dao;
import org.nico.aoc.scan.annotations.Service;
import org.nico.aoc.scan.entity.AspectDic;

public class ConfigKey {

	public static final String CLASSPATH;
	
	public static final String TAG_ROOT;
	
	public static final String TAG_BOOK;
	
	public static final String TAG_PAGE;
	
	public static final String TAG_LABEL;
	
	public static final String TAG_ASPECT;
	
	public static final String TAG_AOP;
	
	public static final String TAG_BEFORE;
	
	public static final String TAG_AFTER;
	
	public static final String TAG_AROUND;
	
	public static final String BOOK_ID;
	
	public static final String BOOK_CLASS;
	
	public static final String BOOK_PAGE_KEY;
	
	public static final String BOOK_PAGE_VALUE;
	
	public static final String BOOK_LABEL_REF;
	
	public static final String BOOK_LABEL_NAME;
	
	public static final String ASPECT_ID;
	
	public static final String ASPECT_CLASS;
	
	public static final String ASPECT_EXECUTION;
	
	public static final String ASPECT_PROXY_METHOD;
	
	public static final String ASPECT_PROXY_TYPE;
	
	public static final String URI_SEOARATOR = "[;]";
	
	public static final List<Class<?>> ACCESS_BOOK_ABOUT_ANNOTATIONS;
	
	public static final List<String> ACCESS_BOOK_ABOUT_TAGS;
	
	public static final AspectDic[] ASPECT_DICTIONARIES_ORDER = new AspectDic[]{AspectDic.WRONG, AspectDic.AROUND, AspectDic.BEFORE, AspectDic.AFTER};
	
	public static final String KEY_OF_ASPECT_EXPRESSION = "expression";
	
	public static final String REGIX_OF_PARSE_EXPRESSION = "(?<=\\()(.*)(?=\\))";
	
	static{
		{
			{
				CLASSPATH = ConfigKey.class.getResource("/").getPath();
			}
		}
		{
			{
				TAG_ROOT = "books";
				TAG_BOOK = "book";
				TAG_PAGE = "param";
				TAG_LABEL = "label";
				TAG_ASPECT = "aspect";
				TAG_AOP = "aop";
				TAG_BEFORE = "before";
				TAG_AFTER = "after";
				TAG_AROUND = "around";
			}
		}
		{
			{
				BOOK_ID = "id";
				BOOK_CLASS = "class";
				BOOK_PAGE_KEY = "key";
				BOOK_PAGE_VALUE = "value";
				BOOK_LABEL_REF = "ref";
				BOOK_LABEL_NAME = "name";
				ASPECT_ID = BOOK_ID;
				ASPECT_CLASS = BOOK_CLASS;
				ASPECT_EXECUTION = "execution";
				ASPECT_PROXY_METHOD = "proxy-method";
				ASPECT_PROXY_TYPE = "proxy-type";
			}
		}
		{
			{
				ACCESS_BOOK_ABOUT_ANNOTATIONS = new ArrayList<Class<?>>(){
					private static final long serialVersionUID = -643707827223305738L;
					{
						add(Book.class);
						add(Aspect.class);
						add(Dao.class);
						add(Service.class);
					}
				};
				ACCESS_BOOK_ABOUT_TAGS = new ArrayList<String>(){
					private static final long serialVersionUID = -643707827223305738L;
					{
						add(TAG_BOOK);
						add(TAG_ASPECT);
					}
				};
			}
		}
	}
	
}

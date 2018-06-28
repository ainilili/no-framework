package org.nico.aoc.aspect.buddy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nico.aoc.ConfigKey;
import org.nico.aoc.aspect.buddy.AspectBuddy.ExecutionEntity.MethodWrapper;
import org.nico.aoc.aspect.handler.AspectProxyHandlerSupport;
import org.nico.aoc.aspect.handler.AspectProxyHandlerForJDKImpl;
import org.nico.aoc.book.Book;
import org.nico.aoc.book.Book.Relier;
import org.nico.aoc.book.shop.BookShop;
import org.nico.aoc.scan.entity.AspectDic;
import org.nico.aoc.scan.entity.AspectType;
import org.nico.aoc.throwable.DefinitionException;
import org.nico.aoc.util.reflect.FieldUtils;
import org.nico.asm.proxy.cglib.CglibProxy;
import org.nico.util.string.StringUtils;

import net.sf.cglib.proxy.Enhancer;

/** 
 * Execution expressions handle small tools.
 * 
 * @author nico
 * @version createTime：2018年3月7日 下午3:22:51
 */

public class AspectBuddy {

	public static final String UNIFIED = "(.*)";

	public static final String REGEX_FOR_UNIFIED = "[*]+";
	
	public static final String CGLIB_PROXY_OBJECT_MARK = "$$EnhancerByCGLIB$$";
	
	public static final String CGLIB_BE_PROXY_OBJECT_MARK = "val$handler";

	/**
	 * Parser Expression from the aspect point
	 * 
	 * @param value aspect point
	 * @return value
	 * @throws DefinitionException expression format error
	 */
	public static String parseExecution(String value) throws DefinitionException{
		Matcher matcher = Pattern.compile(ConfigKey.REGIX_OF_PARSE_EXPRESSION).matcher(value);
		matcher.find();
		String result = matcher.group();
		if(StringUtils.isNotBlank(result)){
			return result;	
		}else{
			throw new DefinitionException("expression format error");
		}
	}

	/**
	 * Match Expressions
	 * 
	 * @param execution 
	 * 			Execution
	 * @param book 
	 * 			The proxy hosts the {@link Book} of the class
	 * @throws DefinitionException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static List<ExecutionEntity> matchExecution(String[] executions, Book book) throws DefinitionException, NoSuchMethodException, SecurityException{
		if(executions == null || executions.length == 0){
			throw new NullPointerException("execution is null");
		}
		
		List<ExecutionEntity> executionEntities = new ArrayList<ExecutionEntity>();
		
		for(String execution: executions) {
			if(! execution.startsWith(ConfigKey.KEY_OF_ASPECT_EXPRESSION)){
				throw new DefinitionException("around must start with 'execution'");
			}
			execution = parseExecution(execution).replaceAll(REGEX_FOR_UNIFIED, UNIFIED);
			List<ExecutionEntity> es = handlerExpression(BookShop.getInstance().getBooks(), UNIFIED + execution + UNIFIED);
			if(es != null) {
				executionEntities.addAll(es);
			}
		}
		
		return executionEntities;
	}

	/**
	 * Match the method in the execution in the books.
	 * 
	 * @param books Be matched books
	 * @param expression execution
	 * @return List<ExecutionEntity>
	 */
	public static List<ExecutionEntity> handlerExpression(Set<Book> books, String expression){
		if(books != null && books.size() > 0){
			List<ExecutionEntity> entities = new ArrayList<ExecutionEntity>();
			Class<?> clazz = null;
			Method[] methods = null;
			Method currentMethod = null;
			for(Book book: books){
				clazz = book.getClazz();
				methods = clazz.getDeclaredMethods();
				if(methods != null){
					List<MethodWrapper> accessMethods = new ArrayList<MethodWrapper>();
					for(int index = 0; index < methods.length; index ++){
						currentMethod = methods[index];
						if(currentMethod.toString().matches(expression)){
							accessMethods.add(new MethodWrapper(currentMethod));
						}
					}
					if(accessMethods.size() > 0){
						entities.add(new ExecutionEntity(book, accessMethods));
					}
				}
			}
			return entities;
		}else{
			return null;
		}
	}

	/**
	 * Handle base Aspect with excution
	 * 
	 * @param book 
	 * 		Proxy book
	 * @param aspectMethod 
	 * 		Proxy method
	 * @param value 
	 * 		Execution
	 * @param aspectDic 
	 * 		Aspect type 
	 * @throws NoSuchMethodException 
	 * 		Can't found be proxyed method
	 * @throws SecurityException
	 * @throws DefinitionException 
	 * 		No matches about the point definition
	 */
	public static void aspectByExecution(Book proxyBook, Method aspectMethod, String proxyType, String[] values, AspectDic aspectDic) throws NoSuchMethodException, SecurityException, DefinitionException{
		List<ExecutionEntity> executionEntities = AspectBuddy.matchExecution(values, proxyBook);
		if(executionEntities == null || executionEntities.size() == 0){
			throw new DefinitionException("No matches about the point definition：" + values);
		}
		for(ExecutionEntity entity: executionEntities){
			if(entity.getBook() != null){
				aspect(proxyBook.getObject(), aspectMethod, entity.getBook(), entity.getMethodWrappers(), proxyType, aspectDic);
			}
		}
	}
	
	/**
	 * Proxy through the {@link Section} annotation
	 * 
	 * @param proxyClass
	 * 			Proxy class
	 * @param beProxyBook
	 *   		Be proxied book
	 * @param beProxyMethod
	 * 			Be proxied method
	 * @param proxyType
	 * 			Proxy type {@link AspectType}
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static void aspectBySection(Class<?> proxyClass, Book beProxyBook, Method beProxyMethod, String proxyType) throws InstantiationException, IllegalAccessException{
		List<MethodWrapper> mws = new ArrayList<MethodWrapper>();
		mws.add(new MethodWrapper(beProxyMethod));
		aspect(proxyClass.newInstance(), null, beProxyBook, mws, proxyType, AspectDic.NULL);
	}
	
	/**
	 * Aspect
	 * 
	 * @param proxyObject
	 * 			Proxy object
	 * @param aspectMethod
	 * 			Proxy method
	 * @param beProxyBook
	 * 			Be proxied book
	 * @param beProxyMethodWrapper
	 * 			Be proxied method wrapper
	 * @param proxyType
	 * 			Proxy type {@link AspectType}
	 * @param aspectDic
	 * 			Aspect dic, if this param is null, will proxy all method from {@link AspectProxy}
	 */
	public static void aspect(Object proxyObject, Method aspectMethod, Book beProxyBook, List<MethodWrapper> beProxyMethodWrapper, String proxyType, AspectDic aspectDic){
		Class<?> target = beProxyBook.getClazz();
		Object obj = null;
		
		InvocationHandler invocationHandler = new AspectProxyHandlerForJDKImpl(proxyObject, aspectMethod, beProxyMethodWrapper, beProxyBook.getObject(), aspectDic);
		
		if(proxyType.equalsIgnoreCase(AspectType.JDK_PROXY.toString())){
			obj = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target.getInterfaces(), invocationHandler);
		
		}else if(proxyType.equalsIgnoreCase(AspectType.CGLIB_PROXY.toString())){
			obj = CglibProxy.newProxyInstance(target, invocationHandler);
		
		}else{
			throw new RuntimeException("No proxy type is " + proxyType);
		}
		
		beProxyBook.setObject(obj);
		flushAspectObject2Reliers(beProxyBook.getReliers(), obj);
	}

	/**
	 * Reinjecting the newly - represented object into the dependent.
	 * 
	 * @param reliers
	 * @param newObject
	 */
	public static void flushAspectObject2Reliers(Set<Relier> reliers, Object newObject){
		if(reliers != null && reliers.size()> 0){
			Iterator<Relier> relierItems = reliers.iterator();
			while(relierItems.hasNext()){
				Relier relier = relierItems.next();
				Object target = relier.getBook().getObject();
				if(target != null){
					Class<?> clazz = target.getClass();
					try {
						Field field = FieldUtils.getField(relier.getFieldName(), clazz);
						FieldUtils.set(field, target, clazz, newObject);
					} catch (Exception e) {
					}
				}
			}
		}
	}

	/**
	 * Get target Object from proxy Object.
	 * 
	 * @param proxyObject
	 * 				Proxy Object
	 * @return Target Object
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static Object getTargetObject(Object proxyObject){
		Object target = proxyObject;
		/**
		 * JDK Proxy
		 */
		if(proxyObject instanceof Proxy){
			InvocationHandler handler = Proxy.getInvocationHandler(proxyObject);
			if(handler instanceof AspectProxyHandlerSupport){
				target = ((AspectProxyHandlerSupport) handler).getBeProxyObject();
			}
		}else if(proxyObject.getClass().getName().indexOf(CGLIB_PROXY_OBJECT_MARK) != -1){
			try{
				Field h = proxyObject.getClass().getDeclaredField("CGLIB$CALLBACK_0");  
		        h.setAccessible(true);  
		        Object dynamicAdvisedInterceptor = h.get(proxyObject);  
		          
		        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField(CGLIB_BE_PROXY_OBJECT_MARK);  
		        advised.setAccessible(true);  
		        
		        InvocationHandler handler = (InvocationHandler) advised.get(dynamicAdvisedInterceptor);
		        if(handler instanceof AspectProxyHandlerSupport){
		        	target = ((AspectProxyHandlerSupport) handler).getBeProxyObject();
		        }
			}catch(Throwable e){
				throw new RuntimeException(e);
			}
		}
		if(target instanceof Proxy || proxyObject.getClass().getName().indexOf(CGLIB_PROXY_OBJECT_MARK) != -1){
			return getTargetObject(target);
		}
		return target;
	}

	/**
	 * The method used to store the retrieved Book and the corresponding proxy.
	 * On the other hand, the clazz field is used to store the classes that 
	 * need to be represented if the class that represents static classes or is 
	 * not managed by the noaoc container.
	 */
	public static class ExecutionEntity{

		private Book book;

		private Class<?> clazz;

		private List<MethodWrapper> MethodWrappers;

		public ExecutionEntity(Class<?> clazz,
				List<MethodWrapper> methodWrappers) {
			super();
			this.clazz = clazz;
			MethodWrappers = methodWrappers;
		}

		public ExecutionEntity(Book book, List<MethodWrapper> methodWrappers) {
			super();
			this.book = book;
			MethodWrappers = methodWrappers;
		}

		public Book getBook() {
			return book;
		}

		public void setBook(Book book) {
			this.book = book;
		}

		public Class<?> getClazz() {
			return clazz;
		}

		public void setClazz(Class<?> clazz) {
			this.clazz = clazz;
		}

		public List<MethodWrapper> getMethodWrappers() {
			return MethodWrappers;
		}

		public void setMethodWrappers(List<MethodWrapper> methodWrappers) {
			MethodWrappers = methodWrappers;
		}

		public static class MethodWrapper{

			private Method method;

			public MethodWrapper(Method method) {
				this.method = method;
			}

			@Override
			public boolean equals(Object arg0) {
				try{
					MethodWrapper wrapper = (MethodWrapper) arg0;
					if(wrapper.method.getName().equals(method.getName())){
						Type[] types = wrapper.method.getGenericParameterTypes();
						Type[] types1 = method.getGenericParameterTypes();
						if(types == null && types1 == null){
							return true;
						}else if(types.length == types1.length){
							boolean isSame = true;
							for(int index = 0; index < types.length; index ++){
								if(!types[index].toString().equals(types1[index].toString())){
									isSame = false;
								}
							}
							if(isSame){
								return true;
							}
						}
					}
				}catch(Exception e){
				}
				return false;
			}

			@Override
			public String toString() {
				return method.toString();
			}

		}

	}
}

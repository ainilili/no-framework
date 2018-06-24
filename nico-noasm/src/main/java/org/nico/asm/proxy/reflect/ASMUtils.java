package org.nico.asm.proxy.reflect;

/** 
 * Some util for ASM 
 * 
 * @author nico
 * @version createTime：2018年4月21日 下午8:22:36
 */

public class ASMUtils {

	/**
	 * Replace Class name by '.' to '/';
	 * 
	 * @param clazz 
	 * 			Class
	 * @return 
	 * 			Class type
	 */
	public static String classTypeAdapter(Class<?> clazz){
		if(null == clazz){
			throw new NullPointerException();
		}
		return clazz.getName().replaceAll("[.]", "/");
	}
	
	/**
	 * Get Field name from method name
	 * 
	 * @param methodName
	 * 			Method name
	 * @return
	 * 			Field name
	 */
	public static String getFieldNameByMethod(String methodName){
		if(methodName.startsWith("is")){
			methodName = methodName.substring(2);
		}else{
			methodName = methodName.substring(3);
		}
		return methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
	}
	
}

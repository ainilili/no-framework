package org.nico.util.collection;


/** 
 * 
 * @author nico
 * @version 创建时间：2017年12月13日 下午8:25:50
 */

public class ArrayUtils {
	
	public static boolean isNotBlank(Object[] array){
		if(array == null) return false;
		if(array.length == 0) return false;
		return true;
	}
	
	public static boolean isNotBlank(byte[] array){
		if(array == null) return false;
		if(array.length == 0) return false;
		return true;
	}
	
	public static boolean isBlank(Object[] array){
		return !isNotBlank(array);
	}
}

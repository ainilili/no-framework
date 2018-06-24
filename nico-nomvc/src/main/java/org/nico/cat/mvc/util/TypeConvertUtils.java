package org.nico.cat.mvc.util;

/** 
 * 
 * @author nico
 * @version createTime：2018年5月17日 下午10:56:18
 */

public class TypeConvertUtils {

	public static <T> T convert(Class<T> clz, Object target){
		return (T)target;
	}
}

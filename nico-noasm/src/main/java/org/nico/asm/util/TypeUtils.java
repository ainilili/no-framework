package org.nico.asm.util;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.Type;

/** 
 * 
 * @author nico
 * @version createTime：2018年5月3日 下午11:31:06
 */

public class TypeUtils {

	
	public static Type[] convertASMTypes(Class<?>[] types){
		Type[] asmTypes = new Type[types == null ? 0 : types.length];
		if(types != null){
			int index = 0;
			for(Class<?> clazz: types){
				asmTypes[index ++] = Type.getType(clazz);
			}
		}
		return asmTypes;
	}
	
	
}

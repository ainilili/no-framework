package org.nico.cat.mvc.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;

import org.nico.asm.buddy.ASMMethodBuddy;
import org.nico.asm.contains.entity.ASMMethodEntity;
import org.nico.asm.contains.entity.ASMParameterEntity;

/**
 * Some util about ASM
 * 
 * @author nico
 * @date 2018年3月6日
 */
public class AsmUtils {

	/**
	 * Get method's parameter display name of the class and method
	 * 
	 * @param clazz class
	 * @param method method
	 * @return Map<Integer, ParameterEntity>
	 * @throws IOException 
	 */
	public static List<ASMParameterEntity> getMethodParameters(Method method) throws IOException{
		ASMMethodEntity methodEntity = ASMMethodBuddy.getMethodEntity(method);
		return methodEntity.getNormalParameters();
	}
}

package org.nico.cat.mvc.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.List;

import org.nico.asm.buddy.ASMMethodBuddy;
import org.nico.asm.contains.entity.ASMMethodEntity;
import org.nico.asm.contains.entity.ASMParameterEntity;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

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
	public static String[] getMethodParameters(Method method) throws IOException{
		ASMMethodEntity me = ASMMethodBuddy.getMethodEntity(method);
		return me != null ? me.getParameters() : null;
	}
	
}

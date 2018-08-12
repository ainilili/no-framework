package org.nico.asm.buddy;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.nico.asm.contains.entity.ASMClassEntity;
import org.nico.asm.contains.entity.ASMMethodEntity;

/** 
 * ASM buddies for class
 * 
 * @author nico
 * @version createTime：2018年4月30日 下午9:12:51
 */

public class ASMClassBuddy {

	public static ASMClassEntity getClassEntity(Class<?> clazz) throws IOException{
		ASMClassEntity classEntity = new ASMClassEntity();

		final List<ASMMethodEntity> normalMethods = new ArrayList<ASMMethodEntity>();
		final List<ASMMethodEntity> constructionMethods = new ArrayList<ASMMethodEntity>();
		
		classEntity.setClassName(clazz.getName());
		classEntity.setNormalMethods(normalMethods);
		classEntity.setConstructionMethods(constructionMethods);
		
		final Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		final Method[] methods = clazz.getDeclaredMethods();
		
		if(constructors != null && constructors.length > 0) {
			for(Constructor<?> con: constructors) {
				constructionMethods.add(ASMMethodBuddy.getConstructorEntity(con));
			}
		}
		
		if(methods != null && methods.length > 0) {
			for(Method method: methods) {
				normalMethods.add(ASMMethodBuddy.getMethodEntity(method));
			}
		}
		
		return classEntity;
	}

	public static void main(String[] args) throws IOException {
		ASMClassEntity entity = ASMClassBuddy.getClassEntity(ASMClassEntity.class);
		System.out.println(entity);
	}
}

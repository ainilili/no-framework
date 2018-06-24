package org.nico.asm.buddy;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.nico.asm.contains.entity.ASMClassEntity;
import org.nico.asm.contains.entity.ASMConstructionEntity;
import org.nico.asm.contains.entity.ASMMethodEntity;
import org.nico.asm.contains.entity.ASMParameterEntity;
import org.nico.asm.util.TypeUtils;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

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
		final List<ASMConstructionEntity> constructionMethods = new ArrayList<ASMConstructionEntity>();
		
		classEntity.setClassName(clazz.getName());
		classEntity.setNormalMethods(normalMethods);
		classEntity.setConstructionMethods(constructionMethods);
		
		final Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		final Method[] methods = clazz.getDeclaredMethods();

		ClassReader classReader = new ClassReader(clazz.getName());
		ClassVisitor visitor = new ClassVisitor(Opcodes.ASM5) {

			@Override
			public MethodVisitor visitMethod(int access, String name, String desc, String signature,
					String[] exceptions) {
				
				
				ASMConstructionEntity methodEntity = new ASMConstructionEntity();

				final List<ASMParameterEntity> normalParameters = new ArrayList<ASMParameterEntity>();

				methodEntity.setNormalParameters(normalParameters);
				methodEntity.setMethodName(name);

				if(name.equals("<init>")){
					constructionMethods.add(methodEntity);
					try {
						if(constructors != null && constructors.length > 0){
							for(Constructor<?> constructor: constructors){
								Type[] ts = TypeUtils.convertASMTypes(constructor.getParameterTypes());
								if(Arrays.equals(ts, Type.getArgumentTypes(desc))){
									methodEntity.setConstructor(constructor);
									break;
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(!name.equals("<clinit>")){
					normalMethods.add(methodEntity);
					try {
						if(methods != null && methods.length > 0){
							for(Method method: methods){
								Type[] ts = TypeUtils.convertASMTypes(method.getParameterTypes());
								if(Arrays.equals(ts, Type.getArgumentTypes(desc))){
									methodEntity.setMethod(method);
									break;
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				return new MethodVisitor(Opcodes.ASM5) {

					@Override
					public void visitLocalVariable(String name, String desc, String signature, Label start, Label end,
							int index) {
						if(! name.equals("this")){
							normalParameters.add(new ASMParameterEntity(name, signature));
						}
						super.visitLocalVariable(name, desc, signature, start, end, index);
					}

				};
			}

		};
		classReader.accept(visitor, ClassReader.EXPAND_FRAMES);
		return classEntity;
	}

	public static void main(String[] args) throws IOException {
		ASMClassEntity entity = ASMClassBuddy.getClassEntity(ASMClassEntity.class);
		System.out.println(entity);
	}
}

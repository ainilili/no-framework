package org.nico.asm.proxy.reflect;

/** 
 * 
 * @author nico
 * @version createTime：2018年4月21日 下午2:51:01
 */

public class GeneratorClassLoader extends ClassLoader {

	public Class<?> defineClassFromClassFile(String className,   
			byte[] classFile) throws ClassFormatError {   
		return defineClass(className, classFile, 0,   
				classFile.length);  
	}   
}

package org.nico.asm.proxy.reflect;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

/** 
 * This class reads a class's information through asm: Field, Method, 
 * and provides a common Method for external calls.
 * 
 * @author nico
 * @version createTime：2018年4月21日 下午7:51:53
 */
public class ASMClassReader extends ASMConfiguration{

	private HashSet<ASMField> asmFields;
	
	private Map<String, ASMMethod> asmGetMethods;
	
	private Map<String, ASMMethod> asmSetMethods;
	
	private Class<?> targetClass;
	
	public ASMClassReader(Class<?> targetClass) throws IOException {
		super();
		this.targetClass = targetClass;
		this.asmFields = new LinkedHashSet<ASMField>();
		this.asmGetMethods = new LinkedHashMap<String, ASMMethod>();
		this.asmSetMethods = new LinkedHashMap<String, ASMMethod>();
		init();
	}
	
	private void init() throws IOException{
		ClassReader classReader = new ClassReader(targetClass.getName());
		ClassVisitor classVisitor = new ClassVisitor(OPCODE) {

			@Override
			public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
				//Save field info
				asmFields.add(new ASMField(name, access, desc, signature));
				return super.visitField(access, name, desc, signature, value);
			}

			@Override
			public MethodVisitor visitMethod(int access, String name, String desc, String signature,
					String[] exceptions) {
				if(! "<init>".equals(name)){
					if(name.startsWith("set")){
						asmSetMethods.put(ASMUtils.getFieldNameByMethod(name), new ASMMethod(access, name, desc, signature, exceptions));
					}else if(name.startsWith("get") || name.startsWith("is")){
						asmGetMethods.put(ASMUtils.getFieldNameByMethod(name), new ASMMethod(access, name, desc, signature, exceptions));
					}
				}
				return super.visitMethod(access, name, desc, signature, exceptions);
			}
			
		};
		classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);
	}
	
	public HashSet<ASMField> getAsmFields() {
		return asmFields;
	}

	public void setAsmFields(HashSet<ASMField> asmFields) {
		this.asmFields = asmFields;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}

	public Map<String, ASMMethod> getAsmGetMethods() {
		return asmGetMethods;
	}

	public void setAsmGetMethods(Map<String, ASMMethod> asmGetMethods) {
		this.asmGetMethods = asmGetMethods;
	}

	public Map<String, ASMMethod> getAsmSetMethods() {
		return asmSetMethods;
	}

	public void setAsmSetMethods(Map<String, ASMMethod> asmSetMethods) {
		this.asmSetMethods = asmSetMethods;
	}





	public class ASMField{
		
		private String name;
		
		private int access;
		
		private String desc;
		
		private String signature;
		
		public ASMField(String name, int access, String desc, String signature) {
			super();
			this.name = name;
			this.access = access;
			this.desc = desc;
			this.signature = signature;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAccess() {
			return access;
		}

		public void setAccess(int access) {
			this.access = access;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getSignature() {
			return signature;
		}

		public void setSignature(String signature) {
			this.signature = signature;
		}

		@Override
		public String toString() {
			return "ASMField [name=" + name + ", access=" + access + ", desc=" + desc + ", signature=" + signature
					+ "]";
		}
		
	}
	
	public class ASMMethod{
		
		private int access;
		
		private String name;
		
		private String desc; 
		
		private String signature;
		
		private String[] exceptions;

		public ASMMethod(int access, String name, String desc, String signature, String[] exceptions) {
			super();
			this.access = access;
			this.name = name;
			this.desc = desc;
			this.signature = signature;
			this.exceptions = exceptions;
		}

		public int getAccess() {
			return access;
		}

		public void setAccess(int access) {
			this.access = access;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getSignature() {
			return signature;
		}

		public void setSignature(String signature) {
			this.signature = signature;
		}

		public String[] getExceptions() {
			return exceptions;
		}

		public void setExceptions(String[] exceptions) {
			this.exceptions = exceptions;
		}

		@Override
		public String toString() {
			return "ASMMethod [access=" + access + ", name=" + name + ", desc=" + desc + ", signature=" + signature
					+ ", exceptions=" + Arrays.toString(exceptions) + "]";
		}
		
		
	}
	
}

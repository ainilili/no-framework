package org.nico.asm.proxy.reflect;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.nico.asm.proxy.reflect.ASMClassReader.ASMField;
import org.nico.asm.proxy.reflect.ASMClassReader.ASMMethod;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/** 
 * 
 * @author nico
 * @version createTime：2018年4月21日 下午1:58:26
 */

public class ASMClassProxyBuilder extends ASMConfiguration{

	/**
	 * Gets the Class proxy object by ASM
	 * 
	 * @param obj
	 * @throws NosonException 
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public AbstractASMClassProxy getASMClassProxy(Class<?> clazz) throws Exception {

		AbstractASMClassProxy targetProxy = null;

		try{

			ClassReader classReader = new ClassReader(ASMClassProxy.class.getName());
			ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

			ASMClassProxyVisitor asmClassProxyVisitor = new ASMClassProxyVisitor(classWriter, clazz);

			classReader.accept(asmClassProxyVisitor, ClassReader.SKIP_DEBUG);

			byte[] classFile = classWriter.toByteArray(); 

			//	调试专用
//			FileOutputStream fos = new FileOutputStream("D:\\2018workpace\\noson\\bin\\org\\nico\\noson\\util\\asm\\" + ASMClassProxy.class.getSimpleName() + ".class");  
//			fos.write(classFile);  
//			fos.close();  
//
//			Verbose verbose = new Verbose(ASMClassProxy.class);
//			System.out.println(verbose.getBytecode());

			Class<ASMClassProxy> newClass = (Class<ASMClassProxy>) new GeneratorClassLoader().defineClassFromClassFile(ASMClassProxy.class.getName(), classFile);

			targetProxy = newClass.newInstance();

		}catch(IOException e){
			throw new Exception(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		return targetProxy;  

	}

	public class ASMClassProxyVisitor extends ClassVisitor{

		private Class<?> clazz;

		public ASMClassProxyVisitor(ClassVisitor cv, Class<?> clazz) {  
			super(OPCODE, cv);
			this.clazz = clazz;
		} 

		@Override
		public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
			if("set".equals(name)){
				MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
				MethodVisitor setMethodVisitor = new ProxySetMethodVisit(mv, clazz);
				return setMethodVisitor;
			}else if("get".equals(name)){
				MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
				MethodVisitor getMethodVisitor = new ProxyGetMethodVisit(mv, clazz);
				return getMethodVisitor;
			}
			return super.visitMethod(access, name, desc, signature, exceptions);
		}

		class ProxySetMethodVisit extends MethodVisitor {  

			private Class<?> clazz;

			public ProxySetMethodVisit(MethodVisitor mv, Class<?> clazz) {  
				super(Opcodes.ASM5, mv);      
				this.clazz = clazz;
			}

			@Override  
			public void visitCode() {
				try {
					ASMClassReader asmClassReader = new ASMClassReader(clazz);
					Set<ASMField> asmFields = asmClassReader.getAsmFields();
					Map<String, ASMMethod> asmMethods = asmClassReader.getAsmSetMethods();

					int size = 0;

					if((size = asmFields.size()) > 0){
						//游标位置
						int index = -1;
						//存放Label
						LinkedList<Label> labelList = new LinkedList<Label>();

						String targetClassName = ASMUtils.classTypeAdapter(clazz);
						Iterator<ASMField> asmFieldItems = asmFields.iterator();

						//将方法第1个形参（操作对象）推到栈顶
						mv.visitVarInsn(Opcodes.ALOAD, 1);

						//栈顶元素怒类型转换check
						mv.visitTypeInsn(Opcodes.CHECKCAST, targetClassName);

						//将新的对象引用存入本地变量
						mv.visitVarInsn(Opcodes.ASTORE, 4);

						size = 0;

						for(ASMField f: asmFields){
							if(asmMethods.containsKey(f.getName())){
								size++;
							}
						}

						//初始化Label
						for(int count = 0; count < size; count ++){
							labelList.add(new Label());	
						}


						while(asmFieldItems.hasNext()){
							ASMField asmField = asmFieldItems.next();

							ASMMethod asmMethod = asmMethods.get(asmField.getName());
							if(asmMethod == null){
								continue;
							}

							index ++;

							if(index < size - 1){
								//将（属性名）推倒栈顶
								mv.visitVarInsn(Opcodes.ALOAD, 2);
								//临时常量
								mv.visitLdcInsn(asmField.getName());
								//判断临时常量和属性名是否一致
								mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
							}

							if(index < size - 1){
								//if else 阶段如果正确的话执行label范围的代码
								mv.visitJumpInsn(Opcodes.IFEQ, labelList.get(index));
							}

							//操作对象推倒栈顶
							mv.visitVarInsn(Opcodes.ALOAD, 4);
							//要set的对象推倒栈顶
							mv.visitVarInsn(Opcodes.ALOAD, 3);

							String castName = asmField.getDesc();

							DescInfo descInfo = null;
							if((descInfo = DESCINFO.get(asmField.getDesc())) != null){
								castName = descInfo.getOwner();
							}

							//直接以L开头的,要去掉L
							if(castName.startsWith("L")){
								castName = castName.substring(1);
							}

							//数组类型对象要将';'去掉
							if(!castName.contains("[") && castName.endsWith(";")){
								castName = castName.replace(";", "");
							}

							//类型转换检测
							mv.visitTypeInsn(Opcodes.CHECKCAST, castName);

							/*
							 * 如果要特殊处理的类型，要获取一下特殊处理后的值
							 * 例如：Integer->Integer.intValue
							 */
							if(null != descInfo){
								mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, castName, descInfo.getMethod(), descInfo.getDesc(), false);	
							}

							//setMethod赋值
							mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, targetClassName, asmMethod.getName() , asmMethod.getDesc(), false);

							//if else阶段GOTO尾部
							if(index < size - 1){
								mv.visitJumpInsn(Opcodes.GOTO, labelList.getLast());
							}

							//Label打下记号
							mv.visitLabel(labelList.get(index));
						}
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}finally{
					mv.visitCode();
				}
			}  
		}  

		class ProxyGetMethodVisit extends MethodVisitor {  

			private Class<?> clazz;

			public ProxyGetMethodVisit(MethodVisitor mv, Class<?> clazz) {  
				super(Opcodes.ASM5, mv);      
				this.clazz = clazz;
			}

			@Override  
			public void visitCode() {
				try {
					ASMClassReader asmClassReader = new ASMClassReader(clazz);
					Set<ASMField> asmFields = asmClassReader.getAsmFields();
					Map<String, ASMMethod> asmMethods = asmClassReader.getAsmGetMethods();

					int size = 0;

					if((size = asmFields.size()) > 0){
						//游标位置
						int index = -1;
						//存放Label
						LinkedList<Label> labelList = new LinkedList<Label>();

						String targetClassName = ASMUtils.classTypeAdapter(clazz);
						Iterator<ASMField> asmFieldItems = asmFields.iterator();

						//将方法第1个形参（操作对象）推到栈顶
						mv.visitVarInsn(Opcodes.ALOAD, 1);

						//栈顶元素怒类型转换check
						mv.visitTypeInsn(Opcodes.CHECKCAST, targetClassName);

						//将新的对象引用存入本地变量
						mv.visitVarInsn(Opcodes.ASTORE, 3);

						size = 0;

						for(ASMField f: asmFields){
							if(asmMethods.containsKey(f.getName())){
								size++;
							}
						}

						//初始化Label
						for(int count = 0; count < size; count ++){
							labelList.add(new Label());	
						}


						while(asmFieldItems.hasNext()){
							ASMField asmField = asmFieldItems.next();

							ASMMethod asmMethod = asmMethods.get(asmField.getName());
							if(asmMethod == null){
								continue;
							}

							index ++;

//							if(index < size - 1){
								//将（属性名）推倒栈顶
								mv.visitVarInsn(Opcodes.ALOAD, 2);
								//临时常量
								mv.visitLdcInsn(asmField.getName());
								//判断临时常量和属性名是否一致
								mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
//							}

//							if(index < size - 1){
								//if else 阶段如果正确的话执行label范围的代码
								mv.visitJumpInsn(Opcodes.IFEQ, labelList.get(index));
//							}

							//操作对象推倒栈顶
							mv.visitVarInsn(Opcodes.ALOAD, 3);

							String castName = asmField.getDesc();

							DescInfo descInfo = null;
							if((descInfo = DESCINFO.get(asmField.getDesc())) != null){
								castName = descInfo.getOwner();
							}

							//直接以L开头的,要去掉L
							if(castName.startsWith("L")){
								castName = castName.substring(1);
							}

							//数组类型对象要将';'去掉
							if(!castName.contains("[") && castName.endsWith(";")){
								castName = castName.replace(";", "");
							}

							//get取值
							mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, targetClassName, asmMethod.getName() , asmMethod.getDesc(), false);

							/*
							 * 如果要特殊处理的类型，要获取一下特殊处理后的值
							 * 例如：Integer->Integer.intValue
							 */
							if(null != descInfo){
								mv.visitMethodInsn(Opcodes.INVOKESTATIC, descInfo.getOwner(), "valueOf", descInfo.getConvertDesc(), false);	
							}

							//if else阶段GOTO尾部
//							if(index < size - 1){
//								mv.visitJumpInsn(Opcodes.GOTO, labelList.getLast());
								mv.visitInsn(Opcodes.ARETURN);
//							}

							//Label打下记号
							mv.visitLabel(labelList.get(index));
						}
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}finally{
					mv.visitCode();
				}
			}  

		}  

	}


}

package org.nico.noson.scanner.impl;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.nico.noson.entity.TypeBean;
import org.nico.noson.exception.NosonException;
import org.nico.noson.scanner.buddy.JsonFieldParser.ParserResult;
import org.nico.noson.scanner.impl.SimpleScanner.SimpleStruct.StructType;
import org.nico.noson.util.reflect.ClassUtils;
import org.nico.noson.util.type.TypeUtils;

/** 
 * On the basis of the original FastScanner, the updated version 
 * is directly converted to the required type when parsing Json. 
 * It does not need to be converted into Noson object and then 
 * transferred again.
 * 
 * @author nico
 */

public class SimpleScanner extends EmptyScanner{

	@Override
	public <T> T scan(String json, Class<T> clazz) throws NosonException {

		//	对需要转换的类型先解析其泛型链
		List<Class<?>> classItes = TypeUtils.getGenericityTypeIterate(clazz);
		List<Class<?>> subClassItes = null;
		
		// 	泛型链长度
		int classItesSize = classItes.size();

		//	结构栈，用来保存非基本类型数据结构
		Stack<SimpleStruct> structStack = new Stack<SimpleStruct>();
		
		Stack<List<Class<?>>> subClassItesStack = new Stack<List<Class<?>>>();
		int subClassItesSumNumber = 0;

		//	KEY/VALUE暂存容器
		//	StringBuffer keyBuffer = new StringBuffer();
		//	StringBuffer valueBuffer = new StringBuffer();
		String keyCache = null;
		String valueCache = null;


		//	初始化状态
		ScanState state = ScanState.NORMAL;

		//	解析结果
		Object result = null;

		//	保存当前父级对象是否是用户自定义对象标识符
		boolean preObjectIsUserDefined = false;

		//	保存当前父级对象类型
		Class<?> preObjectType = null;

		char[] chars = json.toCharArray();
		Object newStruct = null;
		Class<?> newClass = null;
		char c = 0;
		int stackSize = 0;
		
		for(int index = 0; index < chars.length; index ++){
			c = chars[index];
			if((c == '[' || c == '{')){
				stackSize = structStack.size();
				subClassItes = subClassItesStack.isEmpty() ? null : subClassItesStack.peek();
				
				if(stackSize < classItesSize && ! classItes.get(stackSize).getName().equalsIgnoreCase(Object.class.getName())){
					newClass = classItes.get(stackSize);
				}else if(
						stackSize > classItesSize
						&& subClassItes != null 
						&& subClassItes.size() > 0 
						&& stackSize - (subClassItesSumNumber - subClassItes.size()) - classItesSize < subClassItes.size() 
						) {
					int subIndex = stackSize - (subClassItesSumNumber - subClassItes.size()) - classItesSize;
					newClass = subClassItes.get(subIndex);
				}else if(preObjectIsUserDefined){
					TypeBean<?> typeBean = TypeUtils.getFieldType(preObjectType, TypeUtils.typeAllotKey(keyCache));
					newClass = typeBean.getMainClass();
					if(typeBean.getGenericityBeans() != null && typeBean.getGenericityBeans().length > 0){
						List<Class<?>> classItems = TypeUtils.getGenericityTypeIterate(typeBean);
						subClassItesStack.push(classItems);
						subClassItesSumNumber += classItems == null ? 0 : classItems.size();
					}else {
						subClassItesStack.push(null);
					}
				}else if(c == '['){
					newClass = Collection.class;
				}else if(c == '{'){
					newClass = Map.class;
				}
				newStruct = TypeUtils.getStructInstance(newClass);
				SimpleStruct currentSimpleStruct = new SimpleStruct(TypeUtils.typeAllotKey(keyCache), newStruct, newClass);

				if(currentSimpleStruct.getStructType() == StructType.PARAM){
					SimpleStruct preSimpleStruct = structStack.peek();
					TypeUtils.setParamIntoObject(preSimpleStruct.getValue(), currentSimpleStruct);
				}else{
					structStack.push(currentSimpleStruct);
				}
				preObjectIsUserDefined = currentSimpleStruct.getStructType() == StructType.OBJECT;
				preObjectType = currentSimpleStruct.getClassType();
				state = currentSimpleStruct.getState();
				if(result == null){
					result = newStruct;
				}
				continue;
			}else if(c == ']' || c == '}'){
				SimpleStruct currentSimpleStruct = null;
				if(valueCache != null){
					currentSimpleStruct = structStack.peek();

					String key = TypeUtils.typeAllotKey(keyCache);
					Object value = TypeUtils.typeAllotValue(valueCache);

					TypeUtils.setParamIntoObject(currentSimpleStruct.getValue(), new SimpleStruct(key, value));

					valueCache = null;
				}
				currentSimpleStruct = structStack.pop();
				if(! structStack.isEmpty()){
					SimpleStruct preSimpleStruct = structStack.peek();
					TypeUtils.setParamIntoObject(preSimpleStruct.getValue(), currentSimpleStruct);
					currentSimpleStruct = preSimpleStruct;
				}
				preObjectIsUserDefined = currentSimpleStruct.getStructType() == StructType.OBJECT;
				preObjectType = currentSimpleStruct.getClassType();
				state = currentSimpleStruct.getState();
				if(preObjectIsUserDefined) {
					if(! subClassItesStack.isEmpty()) {
						List<Class<?>> classItems = subClassItesStack.pop();
						subClassItesSumNumber -= classItems == null ? 0 : classItems.size();
					}
				}
				continue;
			}
			switch(state){
			case KEY:
				if(c != ','){
					ParserResult parserResult = jsonKeyParser.parser(json, index);
					keyCache = parserResult.getValue();
					index += parserResult.getLen();
					state = ScanState.VALUE;
				}
				break;
			case VALUE:
				if(c == ','){
					if(valueCache != null){
						SimpleStruct currentSimpleStruct = structStack.peek();

						String key = TypeUtils.typeAllotKey(keyCache);
						Object value = TypeUtils.typeAllotValue(valueCache);
						
						TypeUtils.setParamIntoObject(currentSimpleStruct.getValue(), new SimpleStruct(key, value));

						valueCache = null;

						state = currentSimpleStruct.state;
					}
				}else{
					ParserResult parserResult = jsonValueParser.parser(json, index);
					valueCache = parserResult.getValue();
					index += parserResult.getLen() - 1;
				}
				break;
			default:
				break;
			}
		}
//		System.out.println("总总长度" + json.length());
//		System.out.println("循环次数" + count);
		return (T)result;
	}


	/**
	 * Scan state
	 * 
	 * @author nico
	 * @version createTime：2018年4月10日 下午8:42:44
	 */
	static enum ScanState{

		KEY,

		VALUE,

		NORMAL,
		;

	}

	/**
	 * 用来将当前处理对象包装，以便最后反向注入
	 * 
	 * @author nico
	 * @version createTime：2018年4月10日 下午8:28:04
	 */
	public static class SimpleStruct{

		private String name;

		private Object value;

		private Class<?> classType;

		private ScanState state;

		private StructType structType;

		public SimpleStruct(String name, Object value) {
			this(name, value, value == null ? null : value.getClass());
		}

		public SimpleStruct(String name, Object value, Class<?> classType) {
			this.name = name;
			if(value == null){
				structType = StructType.PARAM;
				state = ScanState.KEY;
			}else{
				this.value = value;
				this.classType = classType;
				if(value instanceof Collection){
					structType = StructType.COLLECTION;
					state = ScanState.VALUE;
				}else if(value.getClass().isArray()){
					structType = StructType.ARRAY;
					state = ScanState.VALUE;
				}else if(value instanceof Map){
					structType = StructType.MAP;
					state = ScanState.KEY;
				}else if(ClassUtils.isJavaClass(classType)){
					structType = StructType.PARAM;
					state = ScanState.KEY;
				}else{
					structType = StructType.OBJECT;
					state = ScanState.KEY;
				}
			}
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public ScanState getState() {
			return state;
		}

		public void setState(ScanState state) {
			this.state = state;
		}


		public Class<?> getClassType() {
			return classType;
		}

		public void setClassType(Class<?> classType) {
			this.classType = classType;
		}

		public StructType getStructType() {
			return structType;
		}

		public void setStructType(StructType structType) {
			this.structType = structType;
		}



		public static enum StructType{
			ARRAY,

			COLLECTION,

			MAP,

			PARAM,

			OBJECT,

			;

		}

	}
	
}

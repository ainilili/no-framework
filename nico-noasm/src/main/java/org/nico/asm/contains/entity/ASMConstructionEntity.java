package org.nico.asm.contains.entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/** 
 * 
 * @author nico
 * @version createTime：2018年4月30日 下午9:16:40
 */

public class ASMConstructionEntity extends ASMMethodEntity{

	private Constructor<?> constructor;
	
	public Constructor<?> getConstructor() {
		return constructor;
	}

	public void setConstructor(Constructor<?> constructor) {
		this.constructor = constructor;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List<ASMParameterEntity> getNormalParameters() {
		return normalParameters;
	}

	public void setNormalParameters(List<ASMParameterEntity> normalParameters) {
		this.normalParameters = normalParameters;
	}

	public ASMParameterEntity getReturnParameter() {
		return returnParameter;
	}

	public void setReturnParameter(ASMParameterEntity returnParameter) {
		this.returnParameter = returnParameter;
	}

	@Override
	public String toString() {
		return "ASMConstructionEntity [constructor=" + constructor + "]";
	}


}

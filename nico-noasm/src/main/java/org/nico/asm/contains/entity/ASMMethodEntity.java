package org.nico.asm.contains.entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.List;

/** 
 * 
 * @author nico
 * @version createTime：2018年4月30日 下午9:16:40
 */

public class ASMMethodEntity {

	protected String[] parameters;
	
	protected Executable method;
	
	public ASMMethodEntity() {
		super();
	}

	public ASMMethodEntity(String[] parameters, Executable method) {
		super();
		this.parameters = parameters;
		this.method = method;
	}

	public String[] getParameters() {
		return parameters;
	}

	public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}

	public Executable getMethod() {
		return method;
	}

	public void setMethod(Executable method) {
		this.method = method;
	}


}

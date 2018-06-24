package org.nico.asm.contains.entity;

import java.lang.reflect.Type;

/** 
 * 
 * @author nico
 * @version createTime：2018年4月30日 下午9:17:21
 */

public class ASMParameterEntity {
	
	private String name;

	private String typeName;
	
	public ASMParameterEntity(String name, String typeName) {
		super();
		this.name = name;
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ASMParameterEntity [name=" + name + ", typeName=" + typeName + "]";
	}
	
	
}

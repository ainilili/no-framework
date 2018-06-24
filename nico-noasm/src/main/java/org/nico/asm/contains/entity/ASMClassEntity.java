package org.nico.asm.contains.entity;

import java.util.List;

/** 
 * 
 * @author nico
 * @version createTime：2018年4月30日 下午9:16:29
 */

public class ASMClassEntity {

	private String className;
	
	private List<ASMConstructionEntity> constructionMethods;
	
	private List<ASMMethodEntity> normalMethods;
	
	private List<ASMParameterEntity> normalFields;
	
	public List<ASMParameterEntity> getNormalFields() {
		return normalFields;
	}

	public void setNormalFields(List<ASMParameterEntity> normalFields) {
		this.normalFields = normalFields;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<ASMConstructionEntity> getConstructionMethods() {
		return constructionMethods;
	}

	public void setConstructionMethods(List<ASMConstructionEntity> constructionMethods) {
		this.constructionMethods = constructionMethods;
	}

	public List<ASMMethodEntity> getNormalMethods() {
		return normalMethods;
	}

	public void setNormalMethods(List<ASMMethodEntity> normalMethods) {
		this.normalMethods = normalMethods;
	}

	@Override
	public String toString() {
		return "ASMClassEntity [className=" + className + ", constructionMethods=" + constructionMethods
				+ ", normalMethods=" + normalMethods + ", normalFields=" + normalFields + "]";
	}

	
}

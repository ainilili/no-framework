package org.nico.asm.contains.entity;

import java.util.List;

/** 
 * 
 * @author nico
 * @version createTime：2018年4月30日 下午9:16:29
 */

public class ASMClassEntity {

	private String className;
	
	private List<ASMMethodEntity> constructionMethods;
	
	private List<ASMMethodEntity> normalMethods;

	public List<ASMMethodEntity> getConstructionMethods() {
		return constructionMethods;
	}

	public void setConstructionMethods(List<ASMMethodEntity> constructionMethods) {
		this.constructionMethods = constructionMethods;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}


	public List<ASMMethodEntity> getNormalMethods() {
		return normalMethods;
	}

	public void setNormalMethods(List<ASMMethodEntity> normalMethods) {
		this.normalMethods = normalMethods;
	}

	
}

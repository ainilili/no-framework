package org.nico.seeker.scan;

import java.util.List;

import org.nico.seeker.dom.DomBean;

public abstract class SeekerScanner {
	
	protected List<DomBean> domBeans;
	
	protected String document;
	
	/**
	 * 解析超文本
	 * @param dom 超文本内容
	 * @return	{@link List}
	 */
	public abstract List<DomBean> domScan(String dom);

	public List<DomBean> getDomBeans() {
		return domBeans;
	}

	public void setDomBeans(List<DomBean> domBeans) {
		this.domBeans = domBeans;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}
	
	public boolean isBlank(char c){
		if(c == '\n' || c == '\r' || c == ' ' || c == '\t') return true;
		return false;
	}

}

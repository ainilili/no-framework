package org.nico.seeker.searcher.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.nico.noson.util.string.StringUtils;
import org.nico.seeker.dom.DomBean;
import org.nico.seeker.scan.SeekerScanner;
import org.nico.seeker.searcher.SeekerSearcher;

public class NicoSearcher implements SeekerSearcher{

	{
		globalCheck = true;	
	}

	private SeekerScanner domScanner;
	
	/** 源集合 **/
	private List<DomBean> sourceBeans;

	/** 被当前搜索的集合  **/
	private List<DomBean> domBeans;
	
	/** 搜索返回的结果集 **/
	private List<DomBean> results;

	private boolean globalCheck;


	public NicoSearcher(SeekerScanner domScanner){
		this.domScanner  = domScanner;
		this.sourceBeans = domScanner.getDomBeans();
		this.results = this.sourceBeans;
		this.domBeans = this.sourceBeans;
	}
	
	public NicoSearcher(){}

	public SeekerScanner getDomScanner() {
		return domScanner;
	}

	public void setDomScanner(SeekerScanner domScanner) {
		this.domScanner = domScanner;
	}

	public boolean isGlobalCheck() {
		return globalCheck;
	}

	/**
	 * 通过前后缀及属性名搜索
	 * @param prefix
	 * @param paramName
	 * @param paramValue
	 * @param domBeans
	 * @param bank
	 * @param searchingUnique
	 */
	private void searchingAssemble(String prefix, String paramName, String paramValue, List<DomBean> domBeans, List<DomBean> bank, boolean searchingUnique, DomBean[] tmpDomBeans){
		if(domBeans == null) return;
		if(tmpDomBeans != null) domBeans = Arrays.asList(tmpDomBeans);
		Iterator<DomBean> items = domBeans.iterator();
		while(items.hasNext()){
			DomBean domBean = items.next();
			if(((domBean.get(paramName) != null 
					&& domBean.get(paramName).equals(paramValue)) 
					|| StringUtils.isBlank(paramName) )
					&& ( domBean.getPrefix().equals(prefix) || StringUtils.isBlank(prefix) )){
				bank.add(domBean);
				if(searchingUnique){
					return;
				}
			}
			if(this.globalCheck){
				searchingAssemble(prefix, paramName, paramValue, domBean.getDomProcessers(), bank, searchingUnique, null);
			}
		}
	}

	@Override
	public List<DomBean> getResults() {
		return this.results;
	}
	
	@Override
	public DomBean getSingleResult() {
		return this.results != null && this.results.size() > 0 ? this.results.get(0) : null;
	}

	@Override
	public SeekerSearcher setGlobalCheck(boolean globalCheck) {
		this.globalCheck = globalCheck;
		return this;
	}

	@Override
	public SeekerSearcher searching(String prefix, String paramName, String paramValue){
		return searching(prefix, paramName, paramValue, false, null);
	}

	@Override
	public SeekerSearcher searching(String prefix) {
		return this.searching(prefix, null, null, null);
	}

	@Override
	public SeekerSearcher searching(String prefix, boolean ret) {
		return this.searching(prefix, null, null, ret, null);
	}

	@Override
	public SeekerSearcher searching(String prefix, String paramName, String paramValue, boolean ret) {
		return searching(prefix, paramName, paramValue, ret, null);
	}
	
	@Override
	public SeekerSearcher searching(String prefix, String paramName, String paramValue, DomBean[] tmpDomBeans) {
		return searching(prefix, paramName, paramValue, false, tmpDomBeans);
	}

	@Override
	public SeekerSearcher searching(String prefix, DomBean[] tmpDomBeans) {
		return this.searching(prefix, null, null, tmpDomBeans);
	}

	@Override
	public SeekerSearcher searching(String prefix, boolean ret, DomBean[] tmpDomBeans) {
		return this.searching(prefix, null, null, ret, tmpDomBeans);
	}

	@Override
	public SeekerSearcher searching(String prefix, String paramName, String paramValue, boolean ret,
			DomBean[] tmpDomBeans) {
		List<DomBean> bank = new ArrayList<DomBean>();
		searchingAssemble(prefix, paramName, paramValue, domBeans, bank, false, tmpDomBeans);
		this.results = bank;
		if(!ret){
			this.domBeans = this.results;
		}
		return this;
	}

	@Override
	public SeekerSearcher setDomBeans(DomBean[] domBeans) {
		this.sourceBeans = Arrays.asList(domBeans);
		this.domBeans = sourceBeans;
		return this;
	}
	
	@Override
	public SeekerSearcher setDomBeans(List<DomBean> domBeans){
		this.sourceBeans = domBeans;
		this.domBeans = sourceBeans;
		return this;
	}

	@Override
	public SeekerSearcher reset() {
		this.globalCheck = true;
		this.domBeans = sourceBeans;
		return this;
	}


}

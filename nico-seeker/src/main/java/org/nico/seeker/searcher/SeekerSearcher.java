package org.nico.seeker.searcher;

import java.util.List;

import org.nico.seeker.dom.DomBean;

public interface SeekerSearcher {
	
	/**
	 * 查找DomBean集合
	 * @param prefix		标签前缀（div、span、ul） 
	 * @param paramName		属性名
	 * @param paramValue	属性内容
	 * @return
	 */
	public SeekerSearcher searching(String prefix, String paramName, String paramValue);
	
	/**
	 * 查找DomBean集合
	 * @param prefix		标签前缀（div、span、ul） 
	 * @param paramName		属性名
	 * @param paramValue	属性内容
	 * @param tmpDomBeans		要搜索的集合
	 * @return
	 */
	public SeekerSearcher searching(String prefix, String paramName, String paramValue, DomBean[] tmpDomBeans);
	
	/**
	 * 查找DomBean集合
	 * @param prefix		标签前缀（div） 
	 * @return
	 */
	public SeekerSearcher searching(String prefix);
	
	/**
	 * 查找DomBean集合
	 * @param prefix		标签前缀（div） 
	 * @param tmpDomBeans		要搜索的集合
	 * @return
	 */
	public SeekerSearcher searching(String prefix, DomBean[] tmpDomBeans);
	
	/**
	 * 查找DomBean集合
	 * @param prefix		标签前缀（div） 
	 * @param ret			true：不跟进
	 * @return
	 */
	public SeekerSearcher searching(String prefix, boolean ret);
	
	/**
	 * 查找DomBean集合
	 * @param prefix		标签前缀（div） 
	 * @param ret			true：不跟进
	 * @param tmpDomBeans		要搜索的集合
	 * @return
	 */
	public SeekerSearcher searching(String prefix, boolean ret, DomBean[] tmpDomBeans);
	
	/**
	 * 查找DomBean集合
	 * @param prefix		标签前缀（div、span、ul） 
	 * @param paramName		属性名
	 * @param paramValue	属性内容
	 * @param ret			true：不跟进
	 * @return
	 */
	public SeekerSearcher searching(String prefix, String paramName, String paramValue, boolean ret);
	
	/**
	 * 查找DomBean集合
	 * @param prefix		标签前缀（div、span、ul） 
	 * @param paramName		属性名
	 * @param paramValue	属性内容
	 * @param ret			true：不跟进
	 * @param tmpDomBeans		要搜索的集合
	 * @return
	 */
	public SeekerSearcher searching(String prefix, String paramName, String paramValue, boolean ret, DomBean[] tmpDomBeans);
	
	/**
	 * 获取DomBean集合
	 * @return
	 */
	public List<DomBean> getResults();
	
	/**
	 * 获取单个DomBean
	 * @return
	 */
	public DomBean getSingleResult();
	
	/**
	 * 设置DomBeans搜索对象
	 * @param domBeans
	 */
	public SeekerSearcher setDomBeans(DomBean[]  domBeans);
	
	/**
	 * 设置DomBeans搜索对象
	 * @param domBeans
	 */
	public SeekerSearcher setDomBeans(List<DomBean> domBeans);
	
	/**
	 * 是否开启全局搜索（默认开启）
	 * @param globalCheck
	 */
	public SeekerSearcher setGlobalCheck(boolean globalCheck);
	
	/**
	 * 回到根节点
	 */
	public SeekerSearcher reset();
}

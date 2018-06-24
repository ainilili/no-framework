package org.nico.seeker.scan.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nico.seeker.dom.DomBean;
import org.nico.seeker.dom.DomMember;
import org.nico.seeker.http.HttpMethod;
import org.nico.seeker.http.HttpUtils;
import org.nico.seeker.regex.RegexUtils;
import org.nico.seeker.scan.SeekerScanner;

/** 
 * 
 * @author nico
 * @version createTime：2018年1月21日 上午11:26:32
 */

public class NicoScanner extends SeekerScanner{

	public NicoScanner(String dom){
		this.document      = dom;
		this.domBeans = domScan(dom);
	}

	public NicoScanner(){}

	public NicoScanner(String url, HttpMethod method, Map<String, Object> param){
		String dom = "";
		if(method == HttpMethod.GET){
			dom = HttpUtils.sendGet(url, param);
		}else{
			dom = HttpUtils.sendPost(url, param);
		}
		this.document      = dom;
		this.domBeans = domScan(dom);
	}
	
	@Override
	public List<DomBean> domScan(String dom){
		return domScan(dom, null);
	}
	
	/**
	 * Document扫描
	 * 		>>不确定估算
	 * 		时间复杂度O(lgn)  
	 * 		空间复杂度O(n^2)	 
	 * @param dom 需要扫描的文本
	 * @return	List<DomProcesser>
	 */
	
	public List<DomBean> domScan(String dom, DomBean baseBean){
		List<DomBean> domProcessers = new ArrayList<DomBean>();
		char[] domChars = dom.toCharArray();			
		StringBuffer cache = new StringBuffer();		//存储临时数据
		boolean searcher = false;						//前缀记录标记
		boolean member   = false;						//成员认证标记
		boolean readBody = false;						//内容记录标记
		boolean isOther  = false;						//是否为特殊
		boolean isAnnotation = false;					//是否为注释
		int splies = 0;									//单引号闭合标记
		int dplies = 0;									//双引号闭合标记
		int isOff  = 0;									//标签闭合标记
		DomBean domProcesser = new DomBean(baseBean);			//存储Domcument
		for(int index = 0; index < domChars.length; index++){

			//System.out.println(index);
			char c = domChars[index];
			
			if(isAnnotation){
				if(c == '<' && domChars[index + 1] == '!'){
					boolean off = true;
					String refer = DomMember.MEMBERS_ANNOTATION.get(domProcesser.getPrefix());
					for(int i = 0 ;i < refer.length(); i ++){
						if(((i + index + 1) > domChars.length - 1 ) || domChars[i + index + 1] != refer.charAt(i)) off = false;
					}
					if(off){
						isOff++;
					}
				}
				if(c == '-' && domChars[index + 1] == '-' && domChars[index + 2] == '>'){
					if(isOff == 0){
						isAnnotation = false;
					}else{
						isOff --;						
					}
				}
				continue;
			}
			
			/*
			 * 如果当前member处于true的状态，则开始当前对象深层解析
			 */
			if(member){
				/*
				 * 这一部分判断单双引号是否闭合
				 */
				if(c == '\'' && splies == 0){
					splies = 1;
				}else if(c == '\'' && splies == 1){
					splies = 0;
				}
				if(c == '\"' && dplies == 0){
					dplies = 1;
				}else if(c == '\"' && dplies == 1){
					dplies = 0;
				}
				/*
				 * 解析标签体内容走这里
				 */
				if((splies == 0 && dplies == 0) || (! isOther && readBody )){
					if(readBody){
						/*
						 * 期间遇到其他"障碍物",则判断当前障碍物是否和主体前缀一样
						 * 一样则isOffer ++
						 */
						if(c == '<' && domChars[index + 1] != '/'){
							boolean off = true;
							String refer = domProcesser.getPrefix();
							for(int i = 0 ;i < refer.length(); i ++){
								if(((i + index + 1) > domChars.length - 1 ) || domChars[i + index + 1] != refer.charAt(i)) off = false;
							}
							if(off){
								isOff++;
							}
						}
						/*
						 * 期间遇到其他"障碍物"的闭合标签,则判断当前障碍物的闭合标签是否和主体前缀一样
						 * 一样则判断isOffer是否为0
						 * 		为0 ： 扫描完毕 	member   => false
						 * 						readBody => false
						 * 						index 		跳跃
						 * 						domProcesser赋值
						 * 	     不为0 ：isOff --
						 */						
						if(c == '<' && domChars[index + 1] == '/'){
							boolean off = true;
							String refer = domProcesser.getPrefix();
							for(int i = 0 ;i < refer.length(); i ++){
								if(((i + index + 2) > domChars.length - 1) || domChars[i + index + 2] != refer.charAt(i)) off = false;
							}
							if(off){
								if(isOff == 0){
									member 		= false;
									readBody 	= false;
									index      += domProcesser.getPrefix().length() + 2 ;
									domProcesser.setBody(cache.toString());
									if(! isOther){
										domProcesser.setDomProcessers(domScan(cache.toString()));
									}
									domProcessers.add(domProcesser);
									domProcesser = new DomBean(baseBean);
								}else{
									cache.append(c);
									isOff -- ;
								}
							}else{
								cache.append(c);
							}
						}else{
							cache.append(c);
						}
						if(isOff != 0 && index == domChars.length - 1){
							isOff = 0;
							domProcesser.setBody(cache.toString());
							if(! isOther){
								domProcesser.setDomProcessers(domScan(cache.toString()));
							}
							domProcessers.add(domProcesser);
						}
					}else{
						/*
						 * 如果全都闭合了
						 * 就可以安心的去找属性了
						 */
						if(! isBlank(c)){
							if(c != '=' && c != '>' && c != '/') cache.append(c);
							if(c == '=') cache.append("=");
							/*
							 * 如果为闭合标签
							 */
							else if(c == '/' && domChars[index + 1] == '>'){
								member = false;
								domProcesser.setParamStr(cache.toString());
								domProcesser.setSelfSealing(true);
								domProcessers.add(domProcesser);
								domProcesser = new DomBean(baseBean);
							}
							/*
							 * 如果为开标签
							 */
							else if(c == '>'){
								/*
								 * 判断是否是特殊标签（example ：img不需要闭合）
								 * 		true : 扫描完毕    member => false
								 * 		false: 开启标签体扫描   readBody = true
								 */
								if(DomMember.MEMBERS_SPECIAL_FILTER.contains(domProcesser.getPrefix())){
									member = false;
									domProcesser.setParamStr(cache.toString());
									domProcesser.setSelfSealing(true);
									domProcessers.add(domProcesser);
									domProcesser = new DomBean(baseBean);
								}else{
									domProcesser.setParamStr(cache.toString());
									domProcesser.setSelfSealing(false);
									cache.setLength(0);
									readBody = true;
									continue;
								}
							}
						}else{
							cache.append(c);
						}
					}
				}else{
					cache.append(c);
				}
			}else{
				/*
				 * 首先判断开头是否为'<'并且searcher为false
				 * 是的话开启搜索（前缀）
				 */
				if(c == '<' && !searcher && index < domChars.length - 1 && domChars[index+1] != '/' && domChars[index+1] != '?' && (domChars[index+1] != '!' || domChars[index+2] == '-')){
					cache.setLength(0);
					searcher = true;
					isOther  = false;
					splies 	 = 0;
					dplies   = 0;
					continue;
				}
				/*
				 * 如果当前字符为空，不为闭合'/'符号并且searcher为true状态
				 * 验证该前缀是否已注册
				 * 		已注册：member => true
				 * 		无注册: searcher => false
				 */
				if(cache.toString().equals("!--") || (isBlank(c) || c == '/' || c == '>') &&  searcher && !cache.toString().equals("")){
					cache = RegexUtils.filterBlank(cache.toString());
					cache = RegexUtils.filterAnnotation(cache.toString());
					member = DomMember.MEMBERS_ALL_FILTER.contains(cache.toString());
					member = true;
					if(member){ 
						if(c == '/' || c == '>'){
							--index;
						}
						domProcesser.setPrefix(cache.toString());
						if(DomMember.MEMBERS_ANNOTATION.containsKey(domProcesser.getPrefix())){
							isAnnotation = true;
							member = false;
						}
						isOther = DomMember.MEMBERS_OTHER_FILTER.contains(domProcesser.getPrefix());
					}
					cache.setLength(0);
					searcher = false;
				} 
				else if(searcher) cache.append(c);
			}
		}
		return domProcessers;
	}

}

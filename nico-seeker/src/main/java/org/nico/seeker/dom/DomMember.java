package org.nico.seeker.dom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomMember {
	/*
	 * 普通标签
	 */
	public static final String[] MEMBERS_NORMAL = {
		"div","span","ul","li","table","td","tr","img","select","input","option","script","link","br",
		"p","a","style","head","body","em","meta","title","i","small","h4","html","ol","b","noscript",
		"textarea","dl","dd","dt","ins","iframe","b","strong","code","form","label","h1","hr","thead",
		"tbody","button","h3","sub","sup","font"
	}; 
	
	/*
	 * 特殊标签（闭合不需要加'/')
	 */
	public static final String[] MEMBERS_SPECIAL = {
		"img","input","link","br","meta","hr"
	};
	
	/*
	 * 其他标签
	 */
	public static final String[] MEMBERS_OTHER = {
		"script","style"
	};
	
	public static final Map<String, String> MEMBERS_ANNOTATION = new HashMap<String, String>(){
		{
			put("!--", "-->");
		}
	};
	
	/*
	 * 普通标签注册
	 */
	public static final List<String> MEMBERS_NORMAL_FILTER = new ArrayList<String>(){
		private static final long serialVersionUID = 1L;
		String prefix = "";
		String suffix = "";
		{
			for(String member: MEMBERS_NORMAL){
				add(prefix + member + suffix);
			}
		}
	};
	
	/*
	 * 其他标签注册
	 */
	public static final List<String> MEMBERS_OTHER_FILTER = new ArrayList<String>(){
		private static final long serialVersionUID = 1L;
		String prefix = "";
		String suffix = "";
		{
			for(String member: MEMBERS_OTHER){
				add(prefix + member + suffix);
			}
		}
	};
	
	/*
	 * 特殊标签注册
	 */
	public static final List<String> MEMBERS_SPECIAL_FILTER = new ArrayList<String>(){
		private static final long serialVersionUID = 1L;
		String prefix = "";
		String suffix = "";
		{
			for(String member: MEMBERS_SPECIAL){
				add(prefix + member + suffix);
			}
		}
	};
	
	
	/*
	 * 全部标签注册
	 */
	public static final List<String> MEMBERS_ALL_FILTER = new ArrayList<String>(){
		private static final long serialVersionUID = 1L;
		String prefix = "";
		String suffix = "";
		{
			for(String member: MEMBERS_NORMAL){
				add(prefix + member + suffix);
			}
			for(String member: MEMBERS_SPECIAL){
				add(prefix + member + suffix);
			}
			for(String member: MEMBERS_OTHER){
				add(prefix + member + suffix);
			}
		}
	};
}

package org.nico.seeker.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	/**
	 * 获取匹配单一值 (批处理)
	 * @param regex 正则表达式
	 * @param matchStr 被匹配字符串
	 * @return 匹配后的对象
	 */
	public static String getMatcherValue(String matchStr , String... regexs){
		Pattern p = null;
		Matcher m = null;
		for(String regex:regexs){
			p = Pattern.compile(regex);
			m = p.matcher(matchStr);
			if(m.find()){
				matchStr =  m.group(0);
			}
		}
		return matchStr;
	}
	
	/**
	 * 过滤字符串中的空格
	 * @param str
	 * @return
	 */
	public static StringBuffer filterBlank(String str){
		StringBuffer sb = new StringBuffer();
		char[] chars = str.toCharArray();
		for(char c: chars){
			if(c == '\n' || c == '\r' || c == ' ' || c == '\t'){
				continue;
			}else{
				sb.append(c);
			}
		}
		return sb;
	}
	
	public static StringBuffer filterAnnotation(String str){
		if(str.matches("!--+")){
			return new StringBuffer("!--");
		}
		return new StringBuffer(str);
	}
	
}

package org.nico.seeker.regex;

public class RegexLibraries {
	
	/**
	 * 匹配内容
	 */
	public static final String VALUE_REGEX1 = "([\\S\\s]+)"; 
	public static final String VALUE_REGEX2 = "(\\S+)";
	
	
	/**
	 * 匹配双引号中的内容 - 正则表达式
	 */
	public static final String DOUBLE_QUOTE_REGEX = "(?<=\")"+VALUE_REGEX1+"(?=\")";
	
	/**
	 * 匹配单引号中的内容 - 正则表达式
	 */
	public static final String SINGLE_QUOTE_REGEX = "(?<=')"+VALUE_REGEX1+"(?=')";
}

package org.nico.util.matching;

/**
 * 字符串匹配工具类
 * @author nico
 * @date 2018年1月11日
 */
public class MatchingUtils {

	/**
	 * 判断refer是否匹配berefer
	 * Example:
	 * 		<p>isMatch("/rest/node/**","/rest/node/get") = true
	 *		<br>isMatch("/rest/node/*","/rest/node/get") = false
	 *		<br>isMatch("/rest/node/get","/rest/node/get") = true
	 *		<br>isMatch("/rest/node/get","/rest/node/save") = false
	 * 
	 * @param refer 
	 * 				参照文本
	 * @param berefer 
	 * 				被参照文本
	 * @return 
	 * 				boolean
	 */
	public static boolean isMatch(String refer, String berefer){
		if(refer != null && berefer != null){
			return berefer.matches(refer.replaceAll("\\*+", "(.*)"));
		}
		return false;
	}
	
}

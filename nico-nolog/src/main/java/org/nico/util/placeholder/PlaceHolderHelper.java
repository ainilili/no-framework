package org.nico.util.placeholder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nico.util.string.StringUtils;
/**
 * 占位符预编译
 * @author nico
 * @version 创建时间：2017年7月18日 下午11:17:14
 */
public class PlaceHolderHelper{
	private String placeholderPrefix;
	private String placeholderSuffix;
	private String point = "[.]";

	public PlaceHolderHelper(String placeholderPrefix, String placeholderSuffix){
		this.placeholderPrefix = placeholderPrefix;
		this.placeholderSuffix = placeholderSuffix;
	}
	/**
	 * 替换占位符
	 * @param str
	 * @param map
	 * @return
	 */
	public String replacePlaceholders(String str, Map<String, Object> map){
		return replacePlaceholders(str,  (Object)map);
	}
	/**
	 * 替换占位符
	 * @param str
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String replacePlaceholders(String str, Object obj){
		StringBuilder sBuilder = new StringBuilder(str);
		if(StringUtils.isNotBlank(placeholderPrefix) && StringUtils.isNotBlank(placeholderSuffix)){
			placeholdersScanner(sBuilder, 0, obj);	
		}else{
			placeholdersBlankScanner(sBuilder, obj);
		}
		return sBuilder.toString();
	}

	/**
	 * 占位符扫描
	 * @param sBuilder
	 * @param startIndex
	 * @param obj
	 */
	public void placeholdersScanner(StringBuilder sBuilder, int startIndex, Object obj){
		if (sBuilder.length() <= startIndex)  return;
		int index = sBuilder.indexOf(this.placeholderPrefix, startIndex - 1);
		int preStart = index + this.placeholderPrefix.length() - 1;
		if (index != -1){
			index = preStart;
			StringBuilder cache = new StringBuilder();
			while ((index++ < sBuilder.length()) && (!sBuilder.substring(index, index + this.placeholderSuffix.length()).equals(this.placeholderSuffix))) {
				cache.append(sBuilder.charAt(index));
			}
			String replace = searchFromSerialize(obj, cache.toString());
			if (replace != null){
				int dif = replace.length() - cache.length();
				sBuilder.replace(preStart + 1 - this.placeholderPrefix.length(), index + this.placeholderSuffix.length(), replace);
				placeholdersScanner(sBuilder, index + dif, obj);
			}else{
				placeholdersScanner(sBuilder, index, obj);
			}
		}
	}

	/**
	 * 无前后缀的字符串扫描
	 * @param sBuilder
	 * @param params
	 */
	public void placeholdersBlankScanner(StringBuilder sBuilder, Object params){
		String regx = "[[!=]|[==]|[<<]|[>>]|[<]|[>]|[+]|[-]|[*]|[\\^]|[\\|\\|]|[\\|]|[\\&\\&]|[&]|[/]|[(]|[)]|[{]|[}]|[\\[]|[\\]]]";
		String regxC = "[0-9a-zA-Z[.][']#_\\$]";
		String[] datas = sBuilder.toString().split(regx);
		StringBuilder symBuilder = new StringBuilder(sBuilder.toString().replaceAll(regxC, ""));
		int defaultLen = datas.length;
		for(int pre = 0; pre < defaultLen; pre ++){
			String express = datas[pre];
			if(StringUtils.isBlank(express) || StringUtils.isNum(express)) continue;
			String value = null;
			if((value = searchFromSerialize(params, express.trim())) != null){
				datas[pre] = value;
			}
		}
		char[] chars = symBuilder.toString().toCharArray();
		int cursor = 0;
		sBuilder.delete(0, sBuilder.length());
		for(int index = 0; index < datas.length; index ++){
			if(cursor < chars.length){
				char c = chars[cursor++];
				if(StringUtils.isBlank(c)){
					index --;
					continue;
				}
				if(StringUtils.isBlank(datas[index])){
					sBuilder.append(c);
				}else{
					sBuilder.append(datas[index] + c);
				}
			}else{
				if(StringUtils.isNotBlank(datas[index])){
					sBuilder.append(datas[index]);
				}
			}
		}
		while(cursor < chars.length){
			sBuilder.append(chars[cursor++]);
		}
	}


	/**
	 * 通过占位符内容获取对应属性值
	 * @param obj
	 * @param cache
	 * @return
	 */
	public String searchFromSerialize(Object obj, String cache){
		if (obj == null) return null;
		String[] queue = cache.split(this.point);
		Object result = obj;
		if (queue.length == 1){
			if ((result instanceof Map)) {
				result = ((Map<?,?>)result).get(queue[0]);
			} else if ((result instanceof List)) {
				result = ((List<?>)result).get(Integer.parseInt(queue[0]));
			} else {
				result = SerializeUtils.getFieldValue(queue[0], result);
				if(result == null){
					return String.valueOf(obj);
				}
			}
			if (result == null) return null;
		}else{
			for (int index = 0; index < queue.length; index++){
				if ((result instanceof Map)) {
					result = ((Map<?,?>)result).get(queue[index]);
				} else if ((result instanceof List)) {
					result = ((List<?>)result).get(Integer.parseInt(queue[index]));
				} else {
					result = SerializeUtils.getFieldValue(queue[index], result);
				}
				if (result == null) return null;
			}
		}
		return String.valueOf(result);
	}

	public static void main(String[] args){
		
		PlaceHolderHelper p = new PlaceHolderHelper(null, null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("#port", 8);
		System.out.println(p.replacePlaceholders("#port==1", map));
	}
}

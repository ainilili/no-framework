package org.nico.util.placeparser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nico.util.string.StringUtils;

/** 
 * 通过模板中设定的站位符从对比样本中获取对应的属性
 * </br>
 * <b>Example:</b>
 * </br>
 * <b>prefix:</b>${
 * </br>
 * <b>suffix:</b>}
 * </br>
 * <b>template:</b>这是商品的价格为${price}元
 * </br>
 * <b>reference:</b>这是商品的价格为16元
 * </br>
 * 调用parser方法将会获取到一个map：[price=16]
 * @author nico
 * @version createTime：2018年1月6日 下午4:33:24
 */

public class PlaceParserHelper {

	/** 前缀 **/
	private String prefix;
	
	/** 后缀 **/
	private String suffix;

	public PlaceParserHelper(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}
	
	/**
	 * 从参考样本或获取模板中对应的属性值
	 * @param template 模板
	 * @param reference 样本
	 * @param resultMap 已存在的resultMap
	 * @return 解析之后的属性值
	 */
	public Map<String, Object> parser(Map<String, Object> resultMap, String template, String reference){
		if(StringUtils.isBlank(template) || StringUtils.isBlank(reference)) return null;
		char[] templateChars = template.toCharArray();
		char[] referenceChars = reference.toCharArray();
		
		/** 模板游标**/ 
		int cursor = 0;
		
		/**
		 * record status of scanning process
		 * status == 0 -> 普通比对扫描
		 * status == 1 -> 命中扫描
		 * status == 2 -> 命中扫描value
		 */
		int status = 0;
		
		/** 暂时保存扫描的属性名 **/
		StringBuilder key = new StringBuilder();
		
		/** 暂时保存扫描的属性值 **/
		StringBuilder value = new StringBuilder();
		
		for(int index = 0; index < templateChars.length; index ++){
			char currentChar = templateChars[index];
			switch(status){
			case 0:
				if(index + cursor >= referenceChars.length || currentChar == referenceChars[index + cursor]){
					continue;
				}else{
					/** 匹配前缀 **/
					if(matching(index, templateChars, prefix)){
						/** 开始扫描 **/
						status = 1;   
						/** 推index **/
						index += prefix.length() - 1;
						cursor -= prefix.length();
					}else{
						if(index == 0){
							cursor ++;
						}else{
							cursor += index + 1;
						}
						index = -1;
					}
				}
				break;
			case 1:
				/** 匹配后缀 **/
				int tsite = 0;
				for(tsite = index; tsite < templateChars.length; tsite ++){
					if(matching(tsite, templateChars, suffix)){
						break;
					}else{
						key.append(templateChars[tsite]);
					}
				}
				int rsite = 0;
				boolean turnPut = false;
				for(rsite = index + cursor; rsite < referenceChars.length; rsite ++){
					if(tsite == templateChars.length - 1){
						value.append(referenceChars[rsite]);
						if(rsite == referenceChars.length - 1){
							turnPut = true;
						}
					}else{
						if(tsite + 1 < templateChars.length && templateChars[tsite + 1] == referenceChars[rsite]){
							turnPut = true;
						}else{
							value.append(referenceChars[rsite]);
						}
					}
					if(turnPut){
						/** 停止扫描 **/
						status = 0;
						String keyValue = key.toString();
						if(resultMap.containsKey(keyValue)){
							Object obj = resultMap.get(keyValue);
							if(obj instanceof List){
								((List<String>)obj).add(value.toString());
							}else if(obj instanceof String){
								resultMap.put(keyValue, Arrays.asList(obj, value.toString()));
							}
							resultMap.put(keyValue, value.toString());
						}else{
							resultMap.put(keyValue, value.toString());
						}
						/** builder置位 **/
						key.setLength(0);
						value.setLength(0);
						/** 游标置位 **/
						index = tsite;
						cursor = rsite - tsite - suffix.length();
						break;
					}
				}
				break;
			}
			
		}
		return resultMap;
	}
	
	/**
	 * 从参考样本或获取模板中对应的属性值
	 * @param template 模板
	 * @param reference 样本
	 * @return 解析之后的属性值
	 */
	public Map<String, Object> parser(String template, String reference){
		return parser(new HashMap<String, Object>(), template, reference);
	}
	
	/**
	 *   判断char数组从index位置开始是否能匹配到指定的字符串
	 * @param index 开始匹配位置
	 * @param chars char数组
	 * @param str 指定的字符串
	 * @return boolean
	 */
	private boolean matching(int index, char[] chars, String str){
		boolean hit = true;
		for(int site = index; site < index + str.length(); site ++){
			if(chars[site] != str.charAt(site - index)){
				hit = false;
				break;
			}
		}
		return hit;
	}
	
	public static void main(String[] args) {
		PlaceParserHelper helper = new PlaceParserHelper("${", "}");
		String template = "这是商品的价格为${price}元,又${hello}或者是什么${dog}？又或者什么呢${123}？你好呀${321}";
		String reference = "123这是商品的价格为16元,又或者是什么狗屁？又或者什么呢12？你好呀32a";
		System.out.println(helper.parser(template, reference));
	}
}

package org.nico.noson.util.string;


public class StringUtils {
	
	public static boolean isBlank(String str){
		if(str == null) return true;
		if(str.length() == 0) return true;
		char[] chars = str.toCharArray();
		for(char c: chars){
			if( c == ' ' || c == '\n' || c == '\r' || c == '\t'){
				continue;
			}else{
				return false;
			}
		}
		return true;
	}
	
	public static boolean isNotBlank(String str){
		return ! isBlank(str);
	}
	
	public static boolean isBlank(Object obj){
		if(obj == null) return true;
		return isBlank(String.valueOf(obj));
	}
	
	public static boolean isNotBlank(Object obj){
		return ! isBlank(obj);
	}
	
	public static boolean isNum(String str){
		try{
			Double.parseDouble(str);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}
	
	public static boolean isNotNum(String str){
		return ! isNum(str);
	}
	
}

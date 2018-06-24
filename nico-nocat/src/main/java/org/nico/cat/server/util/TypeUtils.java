package org.nico.cat.server.util;

import org.nico.util.string.StringUtils;

/** 
 * Type utils
 * @author nico
 * @version createTime：2018年1月15日 下午9:25:18
 */
public class TypeUtils {

	/**
	 * Get boolean from string
	 * @param value value
	 * @return boolean
	 */
	public static boolean getBoolean(String value){
		boolean bool = true;
		if(StringUtils.isNotBlank(value)){
			try{
				bool = Boolean.valueOf(value);
			}catch(Exception e){
				bool = true;
			}
		}
		return bool;
	}

}

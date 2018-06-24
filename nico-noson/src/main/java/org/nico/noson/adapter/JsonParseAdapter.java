package org.nico.noson.adapter;

import org.nico.noson.exception.NosonFormatException;
import org.nico.noson.util.string.FormatUtils;
import org.nico.noson.verify.SymbolVerify;

/** 
 * Noson parses the distribution center
 * 
 * @author nico
 * @version 2017-11-24 11:15:22
 */

public class JsonParseAdapter {
	
	/**
	 * Verify that Json is valid and assign the scanner
	 * 
	 * @param json Json to verifyv
	 * @return Return scan object
	 * @throws NosonFormatException Json format error
	 */
	public static String adapter(String json) throws NosonFormatException{
		SymbolVerify verify = new SymbolVerify();
		if(json != null){
			json = FormatUtils.formatJson(json);
			if(verify.check(json)){
				if((json.startsWith("{") && json.endsWith("}")) || (json.startsWith("[") && json.endsWith("]"))){
					return json;
				}else{
					throw new NosonFormatException("json str format error !!");
				}
			}else{
				throw new NosonFormatException("json str is not closed !!");
			}
		}else{
			return null;
		}
	}
}

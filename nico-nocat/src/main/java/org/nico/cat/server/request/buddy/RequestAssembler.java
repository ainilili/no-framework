package org.nico.cat.server.request.buddy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Map;
import java.util.Map.Entry;

import org.nico.log.Logging;
import org.nico.log.LoggingHelper;
import org.nico.util.reflect.FieldUtils;
import org.nico.util.reflect.MethodUtils;

/** 
 * 
 * @author nico
 * @version createTime：2018年1月7日 下午7:11:41
 */

public class RequestAssembler extends RequestContent{

	private Logging logging = LoggingHelper.getLogging(RequestAssembler.class);
	
	public void assmebly(Map<String, Object> params){
		if(params != null && params.size() > 0){
			for(Entry<String, Object> parameter: params.entrySet()){
				try {
					FieldUtils.set(FieldUtils.getField(parameter.getKey(), this.getClass()), this, this.getClass(), parameter.getValue());
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ParseException e) {
					logging.debug(parameter.getKey() + " not found");
				}
			}
		}
	}
}

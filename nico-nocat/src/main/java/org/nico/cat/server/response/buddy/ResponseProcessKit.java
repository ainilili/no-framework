package org.nico.cat.server.response.buddy;

import org.nico.cat.config.ConfigTemplate;
import org.nico.cat.server.response.parameter.HttpCode;

public class ResponseProcessKit {

	public static String processHttpCode(HttpCode httpCode){
		return ConfigTemplate.PLACE_HOLDER.replacePlaceholders(ConfigTemplate.TEMPLATE_RESPONSE_SYSTEM_BODY, httpCode.getMessage());
	}
	
}

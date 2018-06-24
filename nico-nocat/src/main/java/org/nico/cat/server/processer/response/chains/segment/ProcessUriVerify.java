package org.nico.cat.server.processer.response.chains.segment;

import org.nico.cat.server.processer.response.chains.AbstractResponseProcess;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.response.Response;
import org.nico.cat.server.response.buddy.ResponseProcessKit;
import org.nico.cat.server.response.parameter.ContentType;
import org.nico.cat.server.response.parameter.HttpCode;
import org.nico.util.string.StringUtils;

/** 
 * 
 * @author nico
 * @version createTime：2018年1月7日 下午10:42:18
 */

public class ProcessUriVerify extends AbstractResponseProcess{

	@Override
	public Response process(Request request, Response response) throws Exception {
		String uri = request.getUri();
		if(StringUtils.isBlank(uri)){
			response.setHttpcode(HttpCode.HS404);
			return response;
		}else{
			return next(request, response);
		}
	}

}

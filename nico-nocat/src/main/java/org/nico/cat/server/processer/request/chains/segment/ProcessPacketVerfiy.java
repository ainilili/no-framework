package org.nico.cat.server.processer.request.chains.segment;

import java.util.Map;
import java.util.Set;

import javax.lang.model.element.TypeElement;

import org.nico.cat.server.processer.request.chains.AbstractRequestProcess;
import org.nico.cat.server.request.Request;
import org.nico.util.string.StringUtils;

/** 
 * Verify packet before process
 * @author nico
 * @version createTime：2018年1月7日 下午1:16:45
 */
public class ProcessPacketVerfiy extends AbstractRequestProcess{

	@Override
	public Request process(String packet, Request request) throws Exception {
		/** 如果请求信息为空 **/
		if(StringUtils.isBlank(packet)){
			throw new NullPointerException("Http header is null");
		}
		return next(packet, request);
	}

	

}

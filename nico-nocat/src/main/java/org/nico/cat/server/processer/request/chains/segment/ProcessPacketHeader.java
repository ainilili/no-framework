package org.nico.cat.server.processer.request.chains.segment;

import java.util.HashMap;
import java.util.Map;

import org.nico.cat.config.ConfigTemplate;
import org.nico.cat.server.processer.buddy.ParserString;
import org.nico.cat.server.processer.buddy.ParserString.StrNode;
import org.nico.cat.server.processer.request.chains.AbstractRequestProcess;
import org.nico.cat.server.request.Request;

/**
 * Process packet header
 * @author nico
 * @date 2018年1月5日
 */
public class ProcessPacketHeader extends AbstractRequestProcess{

	@Override
	public Request process(String packet, Request request) throws Exception {
		
		ParserString parserString = new ParserString(packet);
		
		String httpLine = parserString.readLine();
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//Parse httpHeader uri
		ConfigTemplate.PLACE_PARSER.parser(resultMap, ConfigTemplate.TEMPLATE_HTTP_HEADER, httpLine);
		request.assmebly(resultMap);
		
		//Clear
		resultMap.clear();
		
		//Parse httpHeader properties
		StrNode<String, String> lineNode = null;
		while((lineNode = parserString.readLineNode()) != null){
			resultMap.put(lineNode.getKey(), lineNode.getValue());
		}
		request.setHeaders(resultMap);
		
		return next(packet, request);
	}

}

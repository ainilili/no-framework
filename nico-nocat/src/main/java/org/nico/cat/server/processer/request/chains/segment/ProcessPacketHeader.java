package org.nico.cat.server.processer.request.chains.segment;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

import org.nico.cat.config.ConfigTemplate;
import org.nico.cat.server.exception.error.PacketException;
import org.nico.cat.server.processer.buddy.ParserString;
import org.nico.cat.server.processer.buddy.ParserString.StrNode;
import org.nico.cat.server.processer.request.chains.AbstractRequestProcess;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.request.parameter.RequestMethod;
import org.nico.cat.server.util.UriUtils;

/**
 * Process packet header
 * @author nico
 * @date 2018年1月5日
 */
public class ProcessPacketHeader extends AbstractRequestProcess{

	@Override
	public Request process(String packet, Request request) throws Exception {
		
		ParserString parserString = new ParserString(packet);
		
		String httpVersion = parserString.readLine();
		
		String[] versionInfos = null;
		try{
			versionInfos = httpVersion.split(" ", 3);
		}catch(PatternSyntaxException e){
			throw new PacketException("Packet [version] format has error.");
		}
		
		request.setMethod(RequestMethod.valueOf(versionInfos[0]));
		
		String uri = versionInfos[1];
		request.setUri(uri.substring(0, uri.indexOf("?") == -1 ? uri.length() : uri.indexOf("?")));
		request.setVersion(versionInfos[2]);
		request.setProperties(UriUtils.getProperties(uri));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//Parse httpHeader properties
		StrNode<String, String> lineNode = null;
		while((lineNode = parserString.readLineNode()) != null){
			resultMap.put(lineNode.getKey(), lineNode.getValue());
		}
		request.setHeaders(resultMap);
		
		return next(packet, request);
	}

}

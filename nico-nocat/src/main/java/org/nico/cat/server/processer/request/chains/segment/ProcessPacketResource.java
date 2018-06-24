package org.nico.cat.server.processer.request.chains.segment;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.nico.cat.config.ConfigTemplate;
import org.nico.cat.server.exception.error.UnsupportException;
import org.nico.cat.server.processer.request.chains.AbstractRequestProcess;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.request.extra.Resource;
import org.nico.cat.server.stream.ChunkedByteArrayInputStream;
import org.nico.util.bytes.ByteUtils;
import org.nico.util.string.StringUtils;

/**
 * Process packet resource
 * @author nico
 * @date 2018年1月5日
 */
public class ProcessPacketResource extends AbstractRequestProcess{

	private static final String TEMPLATE_CONTENT_DISPOSITION = "Content-Disposition: form-data; name=\"${logicName}\"; filename=\"${localName}\"";

	private static final int BLOCK_LENGTH = 99999999;
	
	@Override
	public Request process(String packet, Request request) throws Exception {
		if(request.getHeaders().containsKey("method") && request.getHeaders().get("method").equals("POST")){
			if(request.getHeaders().containsKey("contentType")){
				String contentType = request.getHeader("contentType");
				int index = -1;
				if(contentType.contains("multipart/form-data") && (index = contentType.indexOf("boundary")) != -1){
					char[] chars = contentType.toCharArray();
					StringBuilder builder = new StringBuilder();
					for(int s = index + "boundary".length() + 1; s < chars.length; s ++){
						char currentChar = chars[s];
						if(currentChar != ';'){
							builder.append(currentChar);
						}else{
							break;
						}
					}
					String splitMark = builder.toString();
					if(StringUtils.isNotBlank(request.getBody())){
						request.setResourceMap(parseResourceMapFromBody(request.getBody(), splitMark));
					}
				}
			}
		}
		return next(packet, request);
	}

	/**
	 * Get resource map object from the request body
	 * 
	 * @param body Request byte[] body
	 * @param splitMark Split mark
	 * @return Resource Map
	 * @throws UnsupportedEncodingException 
	 */
	private Map<String, Resource> parseResourceMapFromBody(String body, String splitMark) throws Exception{
		Map<String, Resource> resourceMap = null;
		if(StringUtils.isNotBlank(body)){
			resourceMap = new HashMap<String, Resource>();
			String[] parts = body.split("--" + splitMark);
			for(int index = 1; index < parts.length - 1; index ++){
				String part = parts[index];
				Resource resource = null;
				resource = parseResourceFromPartBody(part);
				if(resource != null){
					resourceMap.put(resource.getLogicName(), resource);
				}
			}
		}
		return resourceMap;
	}

	/**
	 * Get resource from part of the request body
	 * 
	 * @param part Part of the request body
	 * @return {@link Resource}
	 * @throws UnsupportException 
	 * @throws UnsupportedEncodingException 
	 */
	private Resource parseResourceFromPartBody(String part) throws UnsupportException {
		Resource resource = new Resource();
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(part)){
			ConfigTemplate.PLACE_PARSER.parser(map, TEMPLATE_CONTENT_DISPOSITION, part);
			if(map.containsKey("logicName")){
				resource.setLogicName(map.get("logicName").toString());
			}
			if(map.containsKey("localName")){
				resource.setLocalName(map.get("localName").toString());
			}
			String[] resources = part.split(ConfigTemplate.LINE_FEED + ConfigTemplate.LINE_FEED, 2);
			if(resources.length == 2){
				String res = resources[1];
				try {
					resource.setDatas(ByteUtils.block(res, BLOCK_LENGTH, "iso-8859-1"));
				} catch (UnsupportedEncodingException e) {
					throw new UnsupportException("Unsupport the charset：" + e.getMessage());
				}
				resource.setInputStream(new ChunkedByteArrayInputStream(resource.getDatas()));
				return resource;
			}
		}
		return null;
	}

}

package org.nico.cat.server.processer.request.chains.segment;


import org.nico.cat.server.exception.error.PacketException;
import org.nico.cat.server.processer.request.chains.AbstractRequestProcess;
import org.nico.cat.server.request.Request;

/** 
 * Verify packet before process
 * @author nico
 * @version createTime：2018年1月7日 下午1:16:45
 */
public class ProcessPacketVerfiy extends AbstractRequestProcess{

	@Override
	public Request process(String packet, Request request) throws Exception {

		if(packet == null){
			throw new PacketException("HTTP header is null");
		}
		return next(packet, request);
	}

	

}

package org.nico.cat.server.processer.request.chains;

import java.util.LinkedList;

import org.nico.cat.server.processer.request.chains.segment.ProcessPacketBody;
import org.nico.cat.server.processer.request.chains.segment.ProcessPacketCookie;
import org.nico.cat.server.processer.request.chains.segment.ProcessPacketHeader;
import org.nico.cat.server.processer.request.chains.segment.ProcessPacketResource;
import org.nico.cat.server.processer.request.chains.segment.ProcessPacketVerfiy;
import org.nico.cat.server.request.Request;

/**
 * Request process chains
 * <p>
 * to process the packet in the chains
 * @author nico
 * @date 2018年1月5日
 */
public class RequestProcessChains{

	private static final LinkedList<AbstractRequestProcess> chains;

	/**
	 * Append the process in to the chains
	 */
	static{
		chains = new LinkedList<AbstractRequestProcess>();
		RequestProcessChains.append(new ProcessPacketVerfiy());
		RequestProcessChains.append(new ProcessPacketHeader());
		RequestProcessChains.append(new ProcessPacketCookie());
		RequestProcessChains.append(new ProcessPacketBody());
		RequestProcessChains.append(new ProcessPacketResource());
	}

	/**
	 * Assembly the process into the chains
	 * @param process one the the chains
	 */
	private static void append(AbstractRequestProcess process){
		if(chains.size() > 0){
			/** add the new process into the last of the chains where is the next chain **/
			chains.getLast().setNext(process);
		}
		chains.add(process);
	}

	/**
	 * The entrance to process the packet
	 * 
	 * @param request request
	 * @return {@link Request}
	 * @throws Exception 
	 */
	public static Request process(String packet, Request request) throws Exception{
		if(chains.size() > 0){
			return chains.getFirst().process(packet, request);
		}
		return request;
	}


}

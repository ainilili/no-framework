package org.nico.cat.server.processer.response.chains;

import java.util.LinkedList;

import org.nico.cat.server.processer.response.chains.segment.ProcessCookie;
import org.nico.cat.server.processer.response.chains.segment.ProcessSession;
import org.nico.cat.server.processer.response.chains.segment.ProcessUriApi;
import org.nico.cat.server.processer.response.chains.segment.ProcessUriFilter;
import org.nico.cat.server.processer.response.chains.segment.ProcessUriResource;
import org.nico.cat.server.processer.response.chains.segment.ProcessUriVerify;
import org.nico.cat.server.processer.response.chains.segment.ProcessVerify;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.response.Response;

/**
 * Request process chains
 * <p>
 * to process the packet in the chains
 * @author nico
 * @date 2018年1月5日
 */
public class ResponseProcessChains{

	private static final LinkedList<AbstractResponseProcess> chains;
	
	/**
	 * Append the process in to the chains
	 */
	static{
		chains = new LinkedList<AbstractResponseProcess>();
		ResponseProcessChains.append(new ProcessVerify());
		ResponseProcessChains.append(new ProcessUriVerify());
		ResponseProcessChains.append(new ProcessUriFilter());
		ResponseProcessChains.append(new ProcessUriApi());
		ResponseProcessChains.append(new ProcessUriResource());
		ResponseProcessChains.append(new ProcessSession());
		ResponseProcessChains.append(new ProcessCookie());
	}
	
	/**
	 * Assembly the process into the chains
	 * @param process one the the chains
	 */
	private static void append(AbstractResponseProcess process){
		if(chains.size() > 0){
			/** add the new process into the last of the chains where is the next chain **/
			chains.getLast().setNext(process);
		}
		chains.add(process);
	}
	
	/**
	 * the entrance to process the packet
	 * @param request request
	 * @return {@link Request}
	 * @throws Exception 
	 */
	public static Response process(Request request, Response response) throws Exception {
		if(chains.size() > 0){
			return chains.getFirst().process(request, response);
		}
		return response;
	}
}

package org.nico.cat.server.processer.request.chains;

import org.nico.cat.server.request.Request;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;

/**
 * Abstract request process
 * @author nico
 * @date 2018年1月5日
 */
public abstract class AbstractRequestProcess {

	private AbstractRequestProcess next;
	
	protected Logging logging = LoggingHelper.getLogging(AbstractRequestProcess.class);
	
	protected AbstractRequestProcess next() {
		return next;
	}

	protected void setNext(AbstractRequestProcess next) {
		this.next = next;
	}
	
	protected Request next(String packet, Request request) throws Exception{
		if(next != null){
			return next.process(packet, request);
		}
		return request;
	}
	
	/**
	 * Process packets
	 * @param packet packet
	 * @param parser parser
	 * @param request request
	 * @return {@link Request}
	 */
	public abstract Request process(String packet, Request request) throws Exception;

}

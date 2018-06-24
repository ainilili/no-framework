package org.nico.cat.server.processer.response.chains;


import org.nico.cat.server.container.module.ApiModule;
import org.nico.cat.server.container.module.FilterModule;
import org.nico.cat.server.container.moudle.realize.executor.ModuleExecutor;
import org.nico.cat.server.container.moudle.realize.executor.impl.ApiExecutor;
import org.nico.cat.server.container.moudle.realize.executor.impl.FilterExecutor;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.response.Response;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;

/**
 * Abstract request process
 * @author nico
 * @date 2018年1月5日
 */
public abstract class AbstractResponseProcess {

	private AbstractResponseProcess next;
	
	protected Logging logging = LoggingHelper.getLogging(AbstractResponseProcess.class);
	
	protected AbstractResponseProcess next() {
		return next;
	}

	protected void setNext(AbstractResponseProcess next) {
		this.next = next;
	}
	
	protected Response next(Request request, Response response) throws Exception{
		if(next != null){
			return next.process(request, response);
		}
		return response;
	}
	
	public abstract Response process(Request request, Response response) throws Exception;
	
}

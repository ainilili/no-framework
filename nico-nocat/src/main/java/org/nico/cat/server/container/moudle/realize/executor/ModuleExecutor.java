package org.nico.cat.server.container.moudle.realize.executor;

import org.nico.cat.server.request.Request;
import org.nico.cat.server.response.Response;

/**
 * Module executor
 * @author nico
 * @date 2018年1月11日
 */
public interface ModuleExecutor<T> {

	public void execute(Request request, Response response, T module) throws Exception;
	
	public boolean matching(String uri, T module);
}

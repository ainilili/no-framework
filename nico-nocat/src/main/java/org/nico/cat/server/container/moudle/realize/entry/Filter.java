package org.nico.cat.server.container.moudle.realize.entry;

import org.nico.cat.server.container.moudle.realize.AbstractRealize;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.response.Response;

/**
 * Filter the {@link Response} and {@link Request}
 * @author nico
 * @date 2018年1月11日
 */
public abstract class Filter extends AbstractRealize{
	
	/**
	 * Filter entrance
	 * @param response response
	 * @param request request
	 */
	public abstract void filter(Request request, Response response);
}

package org.nico.cat.mvc.listener;

import java.io.IOException;
import java.util.Map;

import org.nico.cat.mvc.exception.NomvcException;
import org.nico.cat.server.container.module.ListenerModule;
import org.nico.cat.server.container.moudle.realize.entry.Listener;

/**
 * The listener will load the scanner.
 * 
 * @author nico
 * @version createTime：2018年1月16日 下午9:21:34
 */
public class ListenerForNocat extends SimpleListener implements Listener{

	@Override
	public void init(Map<String, Object> properties, ListenerModule listenerModule) throws IOException, NomvcException {
		/**
		 * This is the art of my code, LILI i love you.
		 */
		analyze.analyze(
				mvcscan.scan(
						verifyConfig(
								loaderConfig(listenerModule.getPayload())
								)
						)
				);
	}

}

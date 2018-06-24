package org.nico.cat.mvc.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.nico.cat.mvc.config.NomvcConfig;
import org.nico.cat.mvc.exception.LackRequiredConfigException;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;
import org.nico.noson.Noson;

public class ListenerForTomcat extends TomcatListener implements ServletContextListener{

	private Logging logging = LoggingHelper.getLogging(ListenerForTomcat.class);

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		//Don't do anything for the time being?
	}

	private String convert2Payload(ServletContext context) throws LackRequiredConfigException{
		Noson noson = new Noson();
		Object pointer = null;
		NomvcConfig.CONFIG_CONTEXT_PATH = context.getContextPath();
		if((pointer = context.getInitParameter(NomvcConfig.CONFIG_FIELD_SCANPACK)) != null){
			noson.put(NomvcConfig.CONFIG_FIELD_SCANPACK, pointer);
		}else{
			throw new LackRequiredConfigException(NomvcConfig.CONFIG_FIELD_SCANPACK);
		}
		return noson.toString();
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext context = servletContextEvent.getServletContext();
		/**
		 * This is the art of my code, LILI i love you.
		 */
		try {
			analyze.analyze(
					mvcscan.scan(
							verifyConfig(
									loaderConfig(convert2Payload(context))
									)
							)
					);
		} catch (Exception e) {
			logging.error(e);
		}
	}

}

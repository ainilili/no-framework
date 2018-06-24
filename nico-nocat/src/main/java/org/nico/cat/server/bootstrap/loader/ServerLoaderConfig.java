package org.nico.cat.server.bootstrap.loader;

import org.nico.log.Logging;
import org.nico.log.LoggingHelper;

/** 
 * Server tag config
 * @author nico
 * @version createTime：2018年1月8日 下午9:18:09
 */
public class ServerLoaderConfig {
	
	protected Logging logging = LoggingHelper.getLogging(ServerLoaderProcess.class);
	
	/** Welcome page tag **/
	protected static final String CAT_WELCOMES = "cat:welcomes";
	
	/**  Welcome page tag's sub-tag **/
	protected static final String CAT_WELCOMES_WELCOME = "welcome";
	
	/**  Config tag **/
	protected static final String CAT_CONFIGS = "cat:configs";
	
	/**  Config tag's property **/
	protected static final String CAT_CONFIGS_PROPERTY = "property";
	
	/**  Config tag's property's field **/
	protected static final String CAT_CONFIGS_PROPERTY_FIELD = "field";
	
	/** Parameter tag **/
	protected static final String CAT_PARAMETERS = "cat:parameters";
	
	/** Parameter tag's sub-tag **/
	protected static final String CAT_PARAMETERS_PARAMETER = "parameter";
	
	/** Parameter tag's sub-tag's key **/
	protected static final String CAT_PARAMETERS_PARAMETER_KEY = "key";
	
	/** Listener tag **/
	protected static final String CAT_LISTENERS = "cat:listeners";
	
	/** Listener tag's sub-tag **/
	protected static final String CAT_LISTENERS_LISTENER = "listener";
	
	/** Listener tag's handle class **/
	protected static final String CAT_LISTENERS_LISTENER_HANDLER = "handler";
	
	/** Listener tag's payload **/
	protected static final String CAT_LISTENERS_LISTENER_PAYLOAD = "payload";
	
	/**  Filter tag **/
	protected static final String CAT_FILTERS = "cat:filters";
	
	/**  Filter tag's sub-tag **/
	protected static final String CAT_FILTERS_FILTER = "filter";
	
	/** Filter tag's uri **/
	protected static final String CAT_FILTERS_FILTER_URI = "uri";
	
	/** Filter tag's singleton **/
	protected static final String CAT_FILTERS_FILTER_SINGLETON = "singleton";
	
	/**  Filter tag's handle class **/
	protected static final String CAT_FILTERS_FILTER_HANDLER = "handler";
	
	/**  Filter tag's payload, and it is not required **/
	protected static final String CAT_FILTERS_FILTER_PAYLOAD = "payload";
	
	/** Api tag **/
	protected static final String CAT_APIS = "cat:apis";
	
	/** Api tag's sub-tag **/
	protected static final String CAT_APIS_API = "api";
	
	/** Api tag's field singleton **/
	protected static final String CAT_APIS_API_SINGLETON = "singleton";
	
	/** Api tag's uri **/
	protected static final String CAT_APIS_API_URI = "uri";
	
	/** Api tag's handle class **/
	protected static final String CAT_APIS_API_HANDLER = "handler";
	
	/** Api tag's payload, and it is not required **/
	protected static final String CAT_APIS_API_PAYLOAD = "payload";
	
}

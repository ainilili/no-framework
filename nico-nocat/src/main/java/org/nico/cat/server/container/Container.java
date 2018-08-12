package org.nico.cat.server.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nico.cat.server.container.module.ApiModule;
import org.nico.cat.server.container.module.FilterModule;
import org.nico.cat.server.container.module.ListenerModule;
import org.nico.cat.server.container.moudle.realize.entry.Api;
import org.nico.cat.server.container.moudle.realize.entry.Filter;
import org.nico.cat.server.container.moudle.realize.entry.Listener;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.request.extra.Session;
import org.nico.cat.server.response.Response;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;

/** 
 * Save the container for the service configuration information.
 * 
 * @author nico
 * @version createTime：2018年1月8日 下午9:55:26
 */
public abstract class Container {

	private static Container instance;
	
	private Container(){
		welcomes = new ArrayList<String>();
		parameters = new HashMap<String, Object>();
		apis = new ArrayList<ApiModule>();
		filters = new ArrayList<FilterModule>();
		listeners = new ArrayList<ListenerModule>();
		apiMap = new HashMap<String, Api>();
		filterMap = new LinkedHashMap<String, Filter>();
		sessionMap = new HashMap<String, Session>();
		requestContainer = new ThreadLocal<Request>();
		responseContainer = new ThreadLocal<Response>();
	}
	
	public static Container getInstance(){
		try {    
            if(instance != null){
            }else{  
                Thread.sleep(300);  
                synchronized (Container.class) {  
                    if(instance == null){
                        instance = new CatContainer();  
                    }  
                }  
            }   
        } catch (InterruptedException e) {   
            e.printStackTrace();  
        }  
        return instance; 
	}
	
	protected List<String> welcomes;
	
	protected Map<String, Object> parameters;
	
	protected List<ApiModule> apis;
	
	protected List<FilterModule> filters;
	
	protected List<ListenerModule> listeners;
	
	protected Map<String, Api> apiMap;
	
	protected Map<String, Filter> filterMap;
	
	protected Map<String, Session> sessionMap;
	
	protected ThreadLocal<Request> requestContainer;
	
	protected ThreadLocal<Response> responseContainer;
	
	public List<String> getWelcomes() {
		return welcomes;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public List<ApiModule> getApis() {
		return apis;
	}

	public List<FilterModule> getFilters() {
		return filters;
	}

	public List<ListenerModule> getListeners() {
		return listeners;
	}
	
	public Api getApi(String uri){
		return apiMap.get(uri);
	}
	
	public Filter getFilter(String uri){
		return filterMap.get(uri);
	}
	
	public Session getSession(String sessionId){
		return sessionMap.get(sessionId);
	}
	
	public boolean containsApi(String uri){
		return apiMap.containsKey(uri);
	}
	
	public boolean containsFilter(String uri){
		return filterMap.containsKey(uri);
	}
	
	public boolean containsSession(String sessionId){
		return sessionMap.containsKey(sessionId);
	}
	
	public abstract void appendWelcome(String welcome);
	
	public abstract void appendApiModule(ApiModule api);
	
	public abstract void appendFilterModule(FilterModule filter);
	
	public abstract void appendListenerModule(ListenerModule listener);
	
	public abstract void putParameter(String key, Object value);
	
	public abstract void putApi(String uri, Api api);
	
	public abstract void putFilter(String uri, Filter filter);
	
	public abstract void putSession(String sessionId, Session session);
	
	public abstract void setActivityRequest(Request request);
	
	public abstract Request getActivityRequest();
	
	public abstract void setActivityResponse(Response response);
	
	public abstract Response getActivityResponse();
	
	public abstract void init();
	
	/**
	 * Simple implements Container
	 * 
	 * @author nico
	 * @version createTime：2018年1月9日 下午8:20:27
	 */
	private static class CatContainer extends Container{
		
		private Logging logging = LoggingHelper.getLogging(CatContainer.class);
		
		@Override
		public void appendWelcome(String welcome){
			 synchronized (this) {
				 if(welcomes == null) welcomes = new ArrayList<String>();
			 }
			 welcomes.add(welcome);
		}

		@Override
		public void appendApiModule(ApiModule api) {
			synchronized (this) {
				 if(apis == null) apis = new ArrayList<ApiModule>();
			 }
			apis.add(api);
		}

		@Override
		public void appendFilterModule(FilterModule filter) {
			synchronized (this) {
				 if(filters == null) filters = new ArrayList<FilterModule>();
			 }
			filters.add(filter);
		}

		@Override
		public void appendListenerModule(ListenerModule listener) {
			synchronized (this) {
				 if(listeners == null) listeners = new ArrayList<ListenerModule>();
			 }
			listeners.add(listener);
		}

		@Override
		public void init() {
			if(listeners != null){
				for(ListenerModule module: listeners){
					try {
						logging.info("Listener " + module.getHandler() + " init");
						Listener listener = module.getHandler().newInstance();
						listener.init(this.parameters, module);
					} catch (Exception e) {
						logging.error(e);
					}
				}
			}
		}
		
		@Override
		public void putParameter(String key, Object value) {
			 synchronized (this) {
				 if(parameters == null) parameters = new HashMap<String, Object>();
			 }
			 parameters.put(key, value);
		}
		
		@Override
		public void putApi(String uri, Api api){
			synchronized (this) {
				 if(apiMap == null) apiMap = new HashMap<String, Api>();
			 }
			apiMap.put(uri, api);
		}
		
		@Override
		public void putFilter(String uri, Filter filter){
			synchronized (this) {
				 if(filterMap == null) filterMap = new HashMap<String, Filter>();
			 }
			filterMap.put(uri, filter);
		}

		@Override
		public void putSession(String sessionId, Session session) {
			synchronized (this) {
				 if(sessionMap == null) sessionMap = new HashMap<String, Session>();
			 }
			sessionMap.put(sessionId, session);
		}

		@Override
		public void setActivityRequest(Request request) {
			requestContainer.set(request);
		}

		@Override
		public Request getActivityRequest() {
			return requestContainer.get();
		}

		@Override
		public void setActivityResponse(Response response) {
			responseContainer.set(response);
		}

		@Override
		public Response getActivityResponse() {
			return responseContainer.get();
		}
		
	}
}

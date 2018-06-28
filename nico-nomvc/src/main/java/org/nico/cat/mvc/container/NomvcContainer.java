package org.nico.cat.mvc.container;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.nico.cat.mvc.container.entity.CinemaEntity;
import org.nico.cat.mvc.container.entity.LobbyEntity;
import org.nico.cat.mvc.parameter.RequestMethod;
import org.nico.cat.mvc.util.PathValidMatchUtils;


public class NomvcContainer {
	
private static NomvcContainer instance;
	
	private NomvcContainer(){
		cinemaMap = new ConcurrentHashMap<Class<?>, CinemaEntity>();
		lobbyMap = new ConcurrentHashMap<RequestMethod, Map<String, LobbyEntity>>();
	}
	
	public static NomvcContainer getInstance(){
		try {    
            if(instance != null){
            }else{  
                Thread.sleep(300);  
                synchronized (NomvcContainer.class) {  
                    if(instance == null){
                        instance = new NomvcContainer();  
                    }  
                }  
            }   
        } catch (InterruptedException e) {   
            e.printStackTrace();  
        }  
        return instance; 
	}
	
	private Map<Class<?>, CinemaEntity> cinemaMap;
	
	private Map<RequestMethod, Map<String, LobbyEntity>> lobbyMap;
	
	public void putCinema(Class<?> clazz, CinemaEntity cinemaEntity){
		cinemaMap.put(clazz, cinemaEntity);
	}
	
	public void putLobby(final String mapping, final LobbyEntity lobbyEntity){
		RequestMethod requestMethod = lobbyEntity.getLobby().requestMethod();
		if(lobbyMap.containsKey(requestMethod)) {
			lobbyMap.get(requestMethod).put(mapping, lobbyEntity);
		}else {
			lobbyMap.put(requestMethod, new ConcurrentHashMap<String, LobbyEntity>(){
				private static final long serialVersionUID = 1L;
				{
					put(mapping, lobbyEntity);
				}
			});
		}
	}
	
	public boolean containsLobbyEntity(String mapping, RequestMethod method){
		return getLobbyEntity(mapping, method) != null;
	}
	
	public boolean containsCinemaEntity(Class<?> clazz){
		return cinemaMap.containsKey(clazz);
	}
	
	public LobbyEntity getLobbyEntity(String mapping, RequestMethod method){
		Map<String, LobbyEntity> routers = null;
		String routerKey = "~#$%";
		for(int i = 0; i< 2; i++) {
			if(i == 0) {
				routers = lobbyMap.get(method);
			}else {
				if(method.equals(RequestMethod.DEFAULT)) {
					break;
				}
				routers = lobbyMap.get(RequestMethod.DEFAULT);
			}
			
			if(routers != null && ! routers.isEmpty()) {
				for(String key: routers.keySet()){
					if(PathValidMatchUtils.pathValidMatcher(key, mapping)){
						routerKey = key.indexOf("{") >= routerKey.indexOf("{") ? key : routerKey;
					}
				}
			}
			if(routers != null && routers.containsKey(routerKey)) {
				return routers.get(routerKey);
			}
		}
		return null;
	}
	
	public CinemaEntity getCinemaEntity(Class<?> clazz){
		return cinemaMap.get(clazz);
	}
	
	
}

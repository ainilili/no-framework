package org.nico.cat.mvc.scan;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.nico.cat.mvc.container.entity.CinemaEntity;
import org.nico.cat.mvc.scan.annotations.Cinema;
import org.nico.cat.mvc.scan.annotations.Lobby;
import org.nico.cat.mvc.scan.annotations.RestCinema;

public abstract class AbstractMVCScanner {
	
	protected boolean isCinema(Class<?> clazz){
		return clazz == null ? false : clazz.isAnnotationPresent(Cinema.class);
	}

	protected boolean isRestCinema(Class<?> clazz){
		return clazz == null ? false : clazz.isAnnotationPresent(RestCinema.class);
	}

	protected boolean isMapping(Class<?> clazz){
		return clazz == null ? false : clazz.isAnnotationPresent(Lobby.class);
	}

	protected boolean isMapping(Method method){
		return method == null ? false : method.isAnnotationPresent(Lobby.class);
	}

	protected Cinema getCinema(Class<?> clazz){
		return clazz.getDeclaredAnnotation(Cinema.class);
	}

	protected RestCinema getRestCinema(Class<?> clazz){
		return clazz.getDeclaredAnnotation(RestCinema.class);
	}

	protected Lobby getLobby(Class<?> clazz){
		return clazz.getDeclaredAnnotation(Lobby.class);
	}

	protected Lobby getLobby(Method method){
		return method.getDeclaredAnnotation(Lobby.class);
	}
	
	public abstract List<CinemaEntity> scan(Map<String, Object> config) throws IOException;
}

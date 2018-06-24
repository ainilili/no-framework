package org.nico.cat.mvc.container.entity;

import java.lang.reflect.Method;

import org.nico.cat.mvc.scan.annotations.Lobby;

public class LobbyEntity {

	private Method method;
	
	private Class<?> clazz;
	
	private Lobby superLobby;
	
	private Lobby lobby;
	
	private String unbrokenMapping = null;

	public LobbyEntity(Method method, Lobby lobby) {
		super();
		this.method = method;
		this.lobby = lobby;
	}
	
	public LobbyEntity(Class<?> clazz, Lobby lobby) {
		super();
		this.clazz = clazz;
		this.lobby = lobby;
	}



	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Lobby getSuperLobby() {
		return superLobby;
	}

	public void setSuperLobby(Lobby superLobby) {
		this.superLobby = superLobby;
	}

	public Lobby getLobby() {
		return lobby;
	}

	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public String getUnbrokenMapping() {
		return unbrokenMapping;
	}

	public void setUnbrokenMapping(String unbrokenMapping) {
		this.unbrokenMapping = unbrokenMapping;
	}
	
}

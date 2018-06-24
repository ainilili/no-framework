package org.nico.cat.mvc.container.entity;

import java.util.ArrayList;
import java.util.List;

import org.nico.cat.mvc.scan.annotations.Cinema;

public class CinemaEntity {

	private Class<?> clazz;
	
	private Cinema cinema;
	
	private LobbyEntity lobbyEntity;
	
	private List<LobbyEntity> lobbies;
	
	public CinemaEntity(Class<?> clazz, Cinema cinema) {
		super();
		this.clazz = clazz;
		this.cinema = cinema;
		this.lobbies = new ArrayList<LobbyEntity>(20);
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	public List<LobbyEntity> getLobbies() {
		return lobbies;
	}

	public void setLobbies(List<LobbyEntity> lobbies) {
		this.lobbies = lobbies;
	}

	public LobbyEntity getLobbyEntity() {
		return lobbyEntity;
	}

	public void setLobbyEntity(LobbyEntity lobbyEntity) {
		this.lobbyEntity = lobbyEntity;
	}

}

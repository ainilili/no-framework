package org.nico.noson.test.entity;

import java.util.List;
import java.util.Map;

public class GenericityEntity {

	private int id;
	
	private List<Map<String, GenericityEntity>> entity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Map<String, GenericityEntity>> getEntity() {
		return entity;
	}

	public void setEntity(List<Map<String, GenericityEntity>> entity) {
		this.entity = entity;
	}
	
}

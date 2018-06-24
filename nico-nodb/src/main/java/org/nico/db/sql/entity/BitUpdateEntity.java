package org.nico.db.sql.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BitUpdateEntity {
	
	private String tableName;
	
	private String sets;
	
	private String primaries;
	
	private List<Object> objs;
	
	public BitUpdateEntity(String tableName, String sets, String primaries,
			List<Object> objs) {
		this.tableName = tableName;
		this.sets = sets;
		this.primaries = primaries;
		this.objs = objs;
	}
	
	public Map<String, Object> convertMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", tableName);
		map.put("sets", sets);
		map.put("primaries", primaries);
		return map;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getSets() {
		return sets;
	}

	public void setSets(String sets) {
		this.sets = sets;
	}

	public String getPrimaries() {
		return primaries;
	}

	public void setPrimaries(String primaries) {
		this.primaries = primaries;
	}

	public List<Object> getObjs() {
		return objs;
	}

	public void setObjs(List<Object> objs) {
		this.objs = objs;
	}
	
}

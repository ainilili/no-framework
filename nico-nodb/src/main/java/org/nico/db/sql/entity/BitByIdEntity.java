package org.nico.db.sql.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BitByIdEntity {
	
	private String columns;
	
	private String tableName;
	
	private String primaries;

	private List<Object> objs;
	
	public BitByIdEntity(String columns, String tableName, String primaries,
			List<Object> objs) {
		super();
		this.columns = columns;
		this.tableName = tableName;
		this.primaries = primaries;
		this.objs = objs;
	}
	
	public Map<String, Object> convertMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", tableName);
		map.put("primaries", primaries);
		map.put("columns", columns);
		return map;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
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

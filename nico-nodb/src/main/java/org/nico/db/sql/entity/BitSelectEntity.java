package org.nico.db.sql.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BitSelectEntity {
	
	private String columns;
	
	private String conditions;
	
	private String sorts;
	
	private String fromto;
	
	private List<Object> objs;
	
	private String tableName;
	
	public BitSelectEntity(String columns, String conditions, String sorts,
			String fromto, List<Object> objs, String tableName) {
		this.columns = columns;
		this.conditions = conditions;
		this.sorts = sorts;
		this.fromto = fromto;
		this.objs = objs;
		this.tableName = tableName;
	}

	public Map<String, Object> convertMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("columns", columns);
		map.put("conditions", conditions);
		map.put("sorts", sorts);
		map.put("fromto", fromto);
		map.put("tableName", tableName);
		return map;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getSorts() {
		return sorts;
	}

	public void setSorts(String sorts) {
		this.sorts = sorts;
	}

	public String getFromto() {
		return fromto;
	}

	public void setFromto(String fromto) {
		this.fromto = fromto;
	}

	public List<Object> getObjs() {
		return objs;
	}

	public void setObjs(List<Object> objs) {
		this.objs = objs;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}

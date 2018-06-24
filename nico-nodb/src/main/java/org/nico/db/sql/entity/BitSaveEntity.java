package org.nico.db.sql.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年12月5日 下午10:06:38
 */

public class BitSaveEntity {
	
	private String tableName;
	
	private String columns;
	
	private String params;
	
	private List<Object> objs;

	public BitSaveEntity(String tableName, String columns, String params, List<Object> objs) {
		this.tableName = tableName;
		this.columns = columns;
		this.params = params;
		this.objs = objs;
	}
	
	public Map<String, Object> convertMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", tableName);
		map.put("columns", columns);
		map.put("params", params);
		return map;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public List<Object> getObjs() {
		return objs;
	}

	public void setObjs(List<Object> objs) {
		this.objs = objs;
	}

}

package org.nico.db.sql.entity;

import java.util.List;

public class SqlEntity {
	
	private String sql;
	
	private List<Object> params;

	public SqlEntity(String sql, List<Object> params) {
		super();
		this.sql = sql;
		this.params = params;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<Object> getParams() {
		return params;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}
	
}

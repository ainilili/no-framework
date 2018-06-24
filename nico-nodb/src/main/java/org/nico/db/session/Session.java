package org.nico.db.session;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

/**
 * A session with a database connection.
 * 
 * @author nico
 * @date 2018年4月16日
 */
public abstract class Session {
	
	protected DataSource dataSource;

	public Session() {}

	public Session(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public abstract void close(); 
	
	public abstract void commit();
	
	public abstract void open();
	
	public abstract long update(String sql, List<?> params)  throws Exception;
	
	public abstract List<Map<String, Object>> select(String sql, List<?> params) throws Exception;
	
}

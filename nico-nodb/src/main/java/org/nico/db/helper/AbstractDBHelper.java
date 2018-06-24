package org.nico.db.helper;

import java.util.List;
import java.util.Map;

import org.nico.db.page.DBPage;
import org.nico.db.session.Session;

public abstract class AbstractDBHelper {
	
	protected Session session;
	
	protected boolean printLog = false;

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	public boolean isPrintLog() {
		return printLog;
	}

	public void setPrintLog(boolean printLog) {
		this.printLog = printLog;
	}

	public abstract <T> T save(T entity);
	
	public abstract <T> long update(T entity);
	
	public abstract <T> long update(T entity, boolean part);
	
	public abstract <T> long update(Map<String, Object> params, Class<T> clazz, Object id);
	
	public abstract long update(String tableName, String sets, String conditions, List<Object> objs);

	public abstract <T> T delete(T entity);
	
	public abstract <T> boolean delete(Class<?> clazz, Object... ids);
	
	public abstract <T> T get(Class<T> clazz, Object... ids);

	public abstract <T> List<T> select(Map<String, Object> criterias, DBPage page, Class<T> clazz);

	public abstract <T> T selectSingle(Map<String, Object> criterias, Class<T> clazz);
	
	public abstract <T> long count(Map<String, Object> criterias, Class<T> clazz);
	
	public abstract <T> List<Map<String, Object>> excuteQuery(String sql, List<Object> params);
	
	public abstract long excuteUpdate(String sql, List<Object> params);
	
}

package org.nico.db.helper.impl;

import java.util.List;
import java.util.Map;

import org.nico.db.exception.SqlQueryException;
import org.nico.db.helper.AbstractDBHelper;
import org.nico.db.page.DBPage;
import org.nico.db.session.Session;
import org.nico.db.session.serialize.DBObjectSerialize;
import org.nico.db.sql.SqlProcess;
import org.nico.db.sql.entity.SqlEntity;
import org.nico.db.sql.impl.MysqlProcess;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;
import org.nico.noson.Noson;

public class MysqlDBHelper extends AbstractDBHelper{

	private Logging logging = LoggingHelper.getLogging("NODB");

	private SqlProcess process = new MysqlProcess();

	public MysqlDBHelper() {}

	public MysqlDBHelper(Session session) {
		this.session = session;
	}

	@Override
	public <T> T save(T entity) {
		try { 
			update(process.bySave(entity)); 
		} catch (Exception e) {
			logging.error(e);
			throw new SqlQueryException(e.getMessage());
		}
		return entity;
	}

	@Override
	public <T> long update(T entity) {
		return update(entity, true);
	}

	@Override
	public <T> long update(T entity, boolean part) {
		long update = 0;
		try {
			update = update(process.byUpdate(entity, part));
		} catch (Exception e) {
			logging.error(e);
			throw new SqlQueryException(e.getMessage());
		}
		return update;
	}

	@Override
	public <T> long update(Map<String, Object> params, Class<T> clazz, Object id) {
		long update = 0;
		T entity = Noson.convert(params, clazz);
		try {
			update = update(process.byUpdate(entity, true));
		} catch (Exception e) {
			logging.error(e);
			throw new SqlQueryException(e.getMessage());
		}
		return update;
	}

	@Override
	public long update(String tableName, String sets, String conditions,
			List<Object> objs) {
		long result = -1;
		try {
			result = update(process.byUpdate(tableName, sets, conditions, objs));
		} catch (Exception e) {
			logging.error(e);
			throw new SqlQueryException(e.getMessage());
		}
		return result;
	}

	@Override
	public <T> T delete(T entity) {
		try {
			update(process.byDelete(entity));
		} catch (Exception e) {
			logging.error(e);
			throw new SqlQueryException(e.getMessage());
		}
		return entity;
	}

	@Override
	public <T> boolean delete(Class<?> clazz, Object... ids) {
		try {
			update(process.byDelete(clazz, ids));
		} catch (Exception e) {
			logging.error(e);
			throw new SqlQueryException(e.getMessage());
		}
		return true;
	}

	@Override
	public <T> List<T> select(Map<String, Object> criterias, DBPage page, Class<T> clazz) {
		List<T> entities = null;
		try {
			List<Map<String, Object>> results = select(process.bySelect(clazz.newInstance(), criterias, page));
			entities = DBObjectSerialize.MapListSerializeObjects(results, clazz);
		} catch (Exception e) {
			logging.error(e);
			throw new SqlQueryException(e.getMessage());
		}
		return entities;
	}

	@Override
	public <T> T selectSingle(Map<String, Object> criterias, Class<T> clazz) {
		T entity = null;
		try {
			List<Map<String, Object>> results = select(process.bySelectSingle(clazz.newInstance(), criterias));
			List<T> entities = DBObjectSerialize.MapListSerializeObjects(results, clazz);
			if(entities != null && entities.size() > 0) entity = entities.get(0);
		} catch (Exception e) {
			logging.error(e);
			throw new SqlQueryException(e.getMessage());
		}
		return entity;
	}

	@Override
	public <T> long count(Map<String, Object> criterias, Class<T> clazz) {
		long count = 0;
		try {
			List<Map<String, Object>> results = select(process.byCount(clazz.newInstance(), criterias));
			if(results != null && results.size() > 0) count = (Long) results.get(0).get("count");
		} catch (Exception e) {
			logging.error(e);
			throw new SqlQueryException(e.getMessage());
		}
		return count;
	}

	@Override
	public <T> T get(Class<T> clazz, Object... ids) {
		T entity = null;
		try {
			List<Map<String, Object>> results = select(process.byId(clazz, ids));
			List<T> entities = DBObjectSerialize.MapListSerializeObjects(results, clazz);
			if(entities != null && entities.size() > 0) entity = entities.get(0);
		} catch (Exception e) {
			logging.error(e);
			throw new SqlQueryException(e.getMessage());
		}
		return entity;
	}

	@Override
	public <T> List<Map<String, Object>> excuteQuery(String sql, List<Object> params) {
		List<Map<String, Object>> results = null;
		try {
			results = select(new SqlEntity(sql, params));
		} catch (Exception e) {
			logging.error(e);
			throw new SqlQueryException(e.getMessage());
		}
		return results;
	}

	@Override
	public long excuteUpdate(String sql, List<Object> params) {
		long result = -1;
		try {
			result = update(new SqlEntity(sql, params));
		} catch (Exception e) {
			logging.error(e);
			throw new SqlQueryException(e.getMessage());
		}
		return result;
	}

	/**
	 * Update the table infos by sqlEntity
	 * @param sqlEntity contains sql & params
	 * @return be updated count
	 * @throws Exception 
	 */
	public long update(SqlEntity sqlEntity) throws Exception{
		long result = -1;
		session.open();
		try {
			if(printLog) {
				logging.debug("===============================================================================================================");
				logging.debug("beginning to excute the sql：" + sqlEntity.getSql());
				logging.debug("beginning to inject the pam：" + sqlEntity.getParams());
				result = session.update(sqlEntity.getSql(), sqlEntity.getParams());
				logging.debug("excute successful and change " + result);
				logging.debug("alread close the session ~");
				logging.debug("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
			}else {
				result = session.update(sqlEntity.getSql(), sqlEntity.getParams());
			}
		}finally {
			session.close();
		}
		return result;
	}

	public List<Map<String, Object>> select(SqlEntity sqlEntity) throws Exception{
		List<Map<String, Object>> results = null;
		try {
			session.open();
			if(printLog) {
				logging.debug("===============================================================================================================");
				logging.debug("beginning to excute the sql：" + sqlEntity.getSql());
				logging.debug("beginning to inject the pam：" + sqlEntity.getParams());
				results = session.select(sqlEntity.getSql(), sqlEntity.getParams());
				logging.debug("excute successful and get " + results);
				logging.debug("already close the session ~");
				logging.debug("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
			}else {
				results = session.select(sqlEntity.getSql(), sqlEntity.getParams());
			}
		}finally{
			session.close();
		}
		return results;
	}

}

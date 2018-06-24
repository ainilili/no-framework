package org.nico.db.sql;

import java.util.List;
import java.util.Map;

import org.nico.db.page.DBPage;
import org.nico.db.sql.entity.SqlEntity;

public interface SqlProcess {
	
	/**
	 * save entity
	 * @param object you operation entity
	 * @return	SqlEntity
	 * @throws Exception
	 */
	public SqlEntity bySave(Object object) throws Exception;
	
	/**
	 * update entity
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public SqlEntity byUpdate(Object object, boolean part) throws Exception;
	
	/**
	 * user-defined update entity 
	 * @param tableName user u
	 * @param sets u.age = 16, u.name = 'example'
	 * @param conditions u.id = '1'
	 * @return
	 * @throws Exception
	 */
	public SqlEntity byUpdate(String tableName, String sets, String conditions, List<Object> objs) throws Exception;
	
	/**
	 * delete entity
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public SqlEntity byDelete(Object object) throws Exception;
	
	/**
	 * Delete entity by ids
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public SqlEntity byDelete(Class<?> clazz, Object... ids) throws Exception;
	
	/**
	 * select entity
	 * @param queryOptions search criterias 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public SqlEntity bySelect(Object object, Map<String, Object> criterias, DBPage page) throws Exception;
	
	/**
	 * select single entity
	 * @param objectg
	 * @param criterias
	 * @return
	 * @throws Exception
	 */
	public SqlEntity bySelectSingle(Object object, Map<String, Object> criterias) throws Exception;
	
	/**
	 * select count
	 * @param objectg
	 * @param criterias
	 * @return
	 * @throws Exception
	 */
	public SqlEntity byCount(Object object, Map<String, Object> criterias) throws Exception;
	
	/**
	 * select by id
	 * @param object
	 * @return
	 */
	public SqlEntity byId(Class<?> clazz, Object... ids) throws Exception;
}

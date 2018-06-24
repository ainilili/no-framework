package org.nico.db.sql.impl;

import java.util.List;
import java.util.Map;

import org.nico.db.exception.NoPrimaryKeyException;
import org.nico.db.page.DBPage;
import org.nico.db.sql.SqlProcess;
import org.nico.db.sql.entity.BitByIdEntity;
import org.nico.db.sql.entity.BitDeleteEntity;
import org.nico.db.sql.entity.BitSaveEntity;
import org.nico.db.sql.entity.BitSelectEntity;
import org.nico.db.sql.entity.BitUpdateEntity;
import org.nico.db.sql.entity.SqlEntity;
import org.nico.db.sql.factory.BitSqlFactory;
import org.nico.util.placeholder.PlaceHolderHelper;

public class MysqlProcess implements SqlProcess{

	private PlaceHolderHelper holderHelper = new PlaceHolderHelper("#{", "}");
	
	private BitSqlFactory sqlFactory = new BitSqlFactory();

	private final String TEMPLATE_SAVE = "INSERT INTO #{tableName}(#{columns}) VALUES(#{params})";

	private final String TEMPLATE_UPDATE = "UPDATE #{tableName} SET #{sets} WHERE #{primaries}";

	private final String TEMPLATE_DELETE = "DELETE FROM #{tableName} WHERE #{primaries}";

	private final String TEMPLATE_SELECT = "SELECT #{columns} FROM #{tableName} WHERE #{conditions} #{sorts} #{fromto}";
	
	private final String TEMPLATE_SELECT_BYID = "SELECT #{columns} FROM #{tableName} WHERE #{primaries} LIMIT 1";
	
	private final String TEMPLATE_COUNT = "SELECT COUNT(0) as count FROM #{tableName} WHERE #{conditions}";
	
	@Override
	public SqlEntity bySave(Object object) {
		Class<?> clazz = object.getClass();
		BitSaveEntity saveEntity = sqlFactory.getSave(clazz, object);
		String sql = holderHelper.replacePlaceholders(TEMPLATE_SAVE, saveEntity.convertMap());
		return new SqlEntity(sql, saveEntity.getObjs());
	}

	@Override
	public SqlEntity byUpdate(Object object, boolean part) throws NoPrimaryKeyException {
		Class<?> clazz = object.getClass();
		BitUpdateEntity updateEntity = sqlFactory.getUpdate(clazz, object, part);
		String sql = holderHelper.replacePlaceholders(TEMPLATE_UPDATE, updateEntity.convertMap());
		return new SqlEntity(sql, updateEntity.getObjs());
	}
	
	@Override
	public SqlEntity byUpdate(String tableName, String sets, String conditions, List<Object> objs)
			throws Exception {
		BitUpdateEntity updateEntity = new BitUpdateEntity(tableName, sets, conditions, objs);
		String sql = holderHelper.replacePlaceholders(TEMPLATE_UPDATE, updateEntity.convertMap());
		return new SqlEntity(sql, updateEntity.getObjs());
	}

	@Override
	public SqlEntity byDelete(Object object) throws NoPrimaryKeyException {
		Class<?> clazz = object.getClass();
		BitDeleteEntity deleteEntity = sqlFactory.getDelete(clazz, object);
		String sql = holderHelper.replacePlaceholders(TEMPLATE_DELETE, deleteEntity.convertMap());
		return new SqlEntity(sql, deleteEntity.getObjs());
	}
	
	@Override
	public SqlEntity byDelete(Class clazz, Object... ids) throws NoPrimaryKeyException {
		BitDeleteEntity deleteEntity = sqlFactory.getDelete(clazz, ids);
		String sql = holderHelper.replacePlaceholders(TEMPLATE_DELETE, deleteEntity.convertMap());
		return new SqlEntity(sql, deleteEntity.getObjs());
	}

	@Override
	public SqlEntity bySelect(Object object, Map<String, Object> criterias, DBPage page)
			throws Exception {
		BitSelectEntity selectEntity = sqlFactory.getSelect(object, criterias, page);
		String sql = holderHelper.replacePlaceholders(TEMPLATE_SELECT, selectEntity.convertMap());
		return new SqlEntity(sql, selectEntity.getObjs());
	}

	@Override
	public SqlEntity bySelectSingle(Object object, Map<String, Object> criterias) throws Exception {
		SqlEntity sqlEntity = bySelect(object, criterias, new DBPage(0, 1, null));
		return sqlEntity;
	}

	@Override
	public SqlEntity byCount(Object object, Map<String, Object> criterias) throws Exception {
		BitSelectEntity selectEntity = sqlFactory.getSelect(object, criterias, null);
		String sql = holderHelper.replacePlaceholders(TEMPLATE_COUNT, selectEntity.convertMap());
		return new SqlEntity(sql, selectEntity.getObjs());
	}

	@Override
	public SqlEntity byId(Class<?> clazz, Object... ids) throws NoPrimaryKeyException {
		BitByIdEntity byIdEntity = sqlFactory.getById(clazz, ids);
		String sql = holderHelper.replacePlaceholders(TEMPLATE_SELECT_BYID, byIdEntity.convertMap());
		return new SqlEntity(sql, byIdEntity.getObjs());
	}


}

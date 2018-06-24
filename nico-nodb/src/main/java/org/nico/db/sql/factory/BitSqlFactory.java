package org.nico.db.sql.factory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.nico.db.annotation.Ignore;
import org.nico.db.annotation.Primary;
import org.nico.db.annotation.buddy.AnnotationGetter;
import org.nico.db.annotation.param.PrimaryType;
import org.nico.db.exception.NoGetMethodException;
import org.nico.db.helper.impl.MysqlDBHelper;
import org.nico.db.page.DBPage;
import org.nico.db.reflect.util.FieldUtils;
import org.nico.db.reflect.util.FieldUtils.FieldEntity;
import org.nico.db.reflect.verify.FieldVerify;
import org.nico.db.sql.entity.BitByIdEntity;
import org.nico.db.sql.entity.BitDeleteEntity;
import org.nico.db.sql.entity.BitSaveEntity;
import org.nico.db.sql.entity.BitSelectEntity;
import org.nico.db.sql.entity.BitUpdateEntity;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;
import org.nico.util.string.StringUtils;

public class BitSqlFactory {

	private Logging logging = LoggingHelper.getLogging(MysqlDBHelper.class);

	public BitSaveEntity getSave(Class<?> clazz, Object object){
		Field[] fields = clazz.getDeclaredFields();
		String tableName = AnnotationGetter.getTableName(clazz);
		StringBuilder columns = new StringBuilder();
		StringBuilder params = new StringBuilder();
		List<Object> objs = new ArrayList<Object>();
		for(Field field: fields){
			if(FieldVerify.isAccessField(field)){
				try {
					FieldEntity entity = FieldUtils.get(field, object, object.getClass());
					if(entity != null){
						if(FieldVerify.isPrimaryKey(field)){
							if(field.getDeclaredAnnotation(Primary.class).type() == PrimaryType.AUTO_INCREMENT){
								continue;
							}
						}
						columns.append(entity.getFieldName() + ",");
						params.append("?,");
						objs.add(entity.getObj());
					}
				} catch (IllegalArgumentException | NoGetMethodException  e) {
					logging.error(e.getMessage());
				}
			}
		}
		columns.deleteCharAt(columns.length() - 1);
		params.deleteCharAt(params.length() - 1);

		return new BitSaveEntity(tableName, columns.toString(), params.toString(), objs);
	}

	public BitUpdateEntity getUpdate(Class<?> clazz, Object object, boolean part){
		Field[] fields = clazz.getDeclaredFields();
		String tableName = AnnotationGetter.getTableName(clazz);
		StringBuilder sets = new StringBuilder();
		StringBuilder primaries = new StringBuilder(" 1=1");
		List<Object> objs = new ArrayList<Object>();
		Object primaryObj = null;
		for(Field field: fields){
			if(FieldVerify.isAccessField(field)){
				try {
					FieldEntity entity = FieldUtils.get(field, object, object.getClass());
					if(entity != null){
						if(FieldVerify.isPrimaryKey(field)){
							primaries.append(" and " + entity.getFieldName() + "=?");
							primaryObj = entity.getObj();
							continue;
						}else{
							if(!part || (part && entity.getObj() != null)){
								sets.append(entity.getFieldName() + "=?,");
								objs.add(entity.getObj());
							}
						}
					}
				} catch (IllegalArgumentException | NoGetMethodException  e) {
					logging.error(e.getMessage());
				}
			}
		}
		objs.add(primaryObj);
		sets.deleteCharAt(sets.length() - 1);
		return new BitUpdateEntity(tableName, sets.toString(), primaries.toString(), objs);
	}

	public BitDeleteEntity getDelete(Class<?> clazz, Object object){
		Field[] fields = clazz.getDeclaredFields();
		String tableName = AnnotationGetter.getTableName(clazz);
		StringBuilder primaries = new StringBuilder(" 1=1");
		List<Object> objs = new ArrayList<Object>();
		for(Field field: fields){
			if(FieldVerify.isAccessField(field)){
				try {
					FieldEntity entity = FieldUtils.get(field, object, object.getClass());
					if(entity != null){
						if(FieldVerify.isAccessField(field)){
							primaries.append(" and " + entity.getFieldName() + "=?");
							objs.add(entity.getObj());
							continue;
						}
					}
				} catch (IllegalArgumentException | NoGetMethodException  e) {
					logging.error(e.getMessage());
				}
			}
		}
		return new BitDeleteEntity(tableName, primaries.toString(), objs);
	}

	public BitDeleteEntity getDelete(Class<?> clazz, Object... ids){
		Field[] fields = clazz.getDeclaredFields();
		String tableName = AnnotationGetter.getTableName(clazz);
		StringBuilder primaries = new StringBuilder(" 1=1");
		List<Object> objs = new ArrayList<Object>();
		int index = 0;
		for(Field field: fields){
			if(FieldVerify.isAccessField(field)){
				try {
					if(FieldVerify.isAccessField(field) && field.isAnnotationPresent(Primary.class)){
						primaries.append(" and " + FieldUtils.getFieldName(field) + "=?");
						objs.add(ids[index ++]);
						continue;
					}
				} catch (IllegalArgumentException  e) {
					logging.error(e.getMessage());
				}
			}
		}
		return new BitDeleteEntity(tableName, primaries.toString(), objs);
	}


	public BitSelectEntity getSelect(Object object, Map<String, Object> criterias, DBPage page){
		Class<?> clazz = object.getClass();
		char initially = clazz.getName().charAt(0);
		List<Object> objs = new ArrayList<Object>();
		String tableName = AnnotationGetter.getTableName(clazz) + " " + initially;
		StringBuilder columns = new StringBuilder();
		StringBuilder conditions = new StringBuilder("1 = 1");
		StringBuilder sorts = new StringBuilder();
		StringBuilder fromto = new StringBuilder();
		//Columns
		for(Field field: clazz.getDeclaredFields()){
			if(FieldVerify.isAccessField(field)){
				try {
					columns.append(initially + "." + FieldUtils.getFieldName(field) + ",");
				} catch (IllegalArgumentException  e) {
					logging.error(e.getMessage());
				}
			}
		}
		columns.deleteCharAt(columns.length() - 1);
		//Conditions
		if(criterias != null && criterias.size() > 0){
			for(Entry<String, Object> entry: criterias.entrySet()){
				Object value = entry.getValue();
				//String
				if(value instanceof String){
					conditions.append(" and " + initially + "." + FieldUtils.getFieldName(clazz, entry.getKey()) + " = ?");
					objs.add(((String) value));
				}else if(value instanceof List){
					List<?> values = (List<?>) value;
					if(values.size() > 0){
						StringBuilder cache = new StringBuilder();
						String how = " in (";
						String end = ")";
						for(Object obj: values){
							if(obj instanceof Long){
								if(values.size() == 2){
									cache.setLength(0);
									how = " between";
									end = " ";
									objs.add(new Date((Long)values.get(0)));
									objs.add(new Date((Long)values.get(1)));
									cache.append(" ? and ? ");
									break;
								}else{
									cache.append("?");
								}
							}else{
								cache.append("?");
							}
							objs.add(obj);
						}
						cache.replace(cache.length() - 1, cache.length(), "");
						conditions.append(" and " + initially + "." + entry.getKey() + how + cache + end);
					}
				}else{
					conditions.append(" and " + initially + "." + entry.getKey() + " = " + value);
					objs.add(value);
				}
			}
			if(page != null){
				if(page.getLength() > 0){
					if(page.getLength() == 1){
						fromto.append("LIMIT " + page.getLength());
					}else{
						fromto.append("LIMIT " + page.getOffset() + "," + page.getLength());
					}
				}
				if(StringUtils.isNotBlank(page.getSort())){
					sorts.append("ORDER BY ");
					String[] sortArray = page.getSort().split("[,]");
					for(String sort: sortArray){
						String rule = sort.endsWith("+") ? " asc," : " desc,";
						String param = sort.replaceAll("[+|-]", "");
						sorts.append(initially + "." + param + rule);
					}
					sorts.deleteCharAt(sorts.length() - 1);
				}
			}
		}
		return new BitSelectEntity(columns.toString(), conditions.toString(), sorts.toString(), fromto.toString(), objs, tableName);
	}

	public BitByIdEntity getById(Class<?> clazz, Object... ids){
		Field[] fields = clazz.getDeclaredFields();
		char initially = clazz.getName().charAt(0);
		String tableName = AnnotationGetter.getTableName(clazz) + " " + initially;
		StringBuilder columns = new StringBuilder();
		StringBuilder primaries = new StringBuilder(" 1=1");
		List<Object> objs = Arrays.asList(ids);
		for(Field field: fields){
			if(FieldVerify.isAccessField(field)){
				try {
					try {
						columns.append(initially + "." + FieldUtils.getFieldName(field) + ",");
						if(field.getDeclaredAnnotation(Primary.class) != null){
							primaries.append(" and " + initially + "." + FieldUtils.getFieldName(field) + "=?");
						}
					} catch (IllegalArgumentException  e) {
						logging.error(e.getMessage());
					}
				} catch (IllegalArgumentException e) {
					logging.error(e.getMessage());
				}
			}
		}
		columns.deleteCharAt(columns.length() - 1);
		return new BitByIdEntity(columns.toString(), tableName, primaries.toString(), objs);
	}

}

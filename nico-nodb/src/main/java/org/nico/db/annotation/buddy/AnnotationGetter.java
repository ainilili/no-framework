package org.nico.db.annotation.buddy;

import org.nico.db.annotation.Table;
import org.nico.db.exception.TableNameNullException;
import org.nico.db.exception.phrase.SqlExceptionPhrase;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;
import org.nico.util.string.StringUtils;

public class AnnotationGetter {
	
	private static Logging logging = LoggingHelper.getLogging(AnnotationGetter.class);
	
	public static String getTableName(Class<?> clazz) {
		Table name = clazz.getAnnotation(Table.class);
		if(name == null){
			return clazz.getSimpleName().toLowerCase();
		}else{
			if(StringUtils.isNotBlank(name.value())){
				return name.value();
			}else{
				try {
					throw new TableNameNullException(SqlExceptionPhrase.TABLE_NAME_NULL_EXCEPTION);
				} catch (TableNameNullException e) {
					logging.error(e.getMessage());
				}
			}
		}
		return null;
	}
}

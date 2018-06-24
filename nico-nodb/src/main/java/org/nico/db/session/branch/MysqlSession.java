package org.nico.db.session.branch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nico.db.DBConfiguare;
import org.nico.db.datasource.TransationManager;
import org.nico.db.session.Session;

public class MysqlSession extends Session{

	private boolean autoCommit = true;

	public boolean isAutoCommit() {
		return autoCommit;
	}

	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}
	
	@Override
	public void commit() {
	} 

	@Override
	public synchronized void open() {  
	}  

	@Override
	public void close(){
	}


	@Override
	public long update(String sql, List<?> params) throws Exception {
		Connection connection = TransationManager.connectionHolder.get();
		PreparedStatement pstmt = null; 
		long result = -1;
		try{
			pstmt = connection.prepareStatement(sql);  
			int index = 1;  
			if (params != null && !params.isEmpty()) {  
				for (int i = 0; i < params.size(); i++) { 
					Object obj = params.get(i);
					if(obj instanceof Date){
						pstmt.setObject(index++, DBConfiguare.dateFormat.format(obj));  
					}else{
						pstmt.setObject(index++, obj);  
					}
				}
			}
			result = pstmt.executeUpdate();
		}catch(Exception e){
			throw e;
		}finally{
			if (pstmt != null) pstmt.close();
//			if (connection != null)	connection.close();
		}
		return result;  
	}

	@Override
	public List<Map<String, Object>> select(String sql, List<?> params)
			throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
		Connection connection = TransationManager.connectionHolder.get();
		PreparedStatement pstmt = null;  
		ResultSet resultSet = null;
		try{
			int index = 1;  
			pstmt = connection.prepareStatement(sql); 
			if (params != null && !params.isEmpty()) {  
				for (int i = 0; i < params.size(); i++) {  
					Object obj = params.get(i);
					if(obj instanceof Date){
						pstmt.setObject(index++, DBConfiguare.dateFormat.format(obj));  
					}else{
						pstmt.setObject(index++, obj);  
					} 
				}  
			}  
			resultSet = pstmt.executeQuery();  
			ResultSetMetaData metaData = resultSet.getMetaData();  
			int cols_len = metaData.getColumnCount();  
			while (resultSet.next()) {  
				Map<String, Object> map = new HashMap<String, Object>();  
				for (int i = 0; i < cols_len; i++) {  
					String cols_name = metaData.getColumnName(i + 1);  
					Object cols_value = resultSet.getObject(cols_name);  
					if (cols_value == null) {  
						cols_value = "";  
					}  
					map.put(cols_name, cols_value);  
				}  
				list.add(map);  
			}  
		}catch(Exception e){
			throw e;
		}finally{
			if (resultSet != null) resultSet.close(); 
			if (pstmt != null) pstmt.close();
//			if (connection != null)	connection.close();
		}
		return list; 
	}

}

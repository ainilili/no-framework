package org.nico.db.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;


public class TransationManager{

	public static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>();  

	private DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public synchronized Connection getConnection() throws SQLException{  
		Connection conn = connectionHolder.get();  
		if (conn == null || conn.isClosed()){  
			conn = dataSource.getConnection(); 
			connectionHolder.set(conn); 
		} 
		return conn;  
	}  

	public void relaseConnection() throws SQLException{  
		Connection conn = connectionHolder.get();  
		if (conn != null){  
			connectionHolder.remove();  
			conn.close();
		}  
	}  

	public void beginTransaction() throws SQLException{ 
		Connection conn = getConnection();  
		if (conn != null){  
			if (conn.getAutoCommit()){  
				conn.setAutoCommit(false);  
			}  
		}  
	}  

	public void commitTransaction() throws SQLException{ 
		Connection conn = connectionHolder.get();  
		if (conn != null){  
			if (! conn.getAutoCommit()){  
				conn.commit();  
			}  
		}  
	}  

	public void rollbackTransaction(){  
		Connection conn = connectionHolder.get();  
		try {  
			if (conn != null){  
				if(!conn.getAutoCommit()){  
					conn.rollback();  
				}  
			}     
		}catch(SQLException e){  
			e.printStackTrace();  
		}     
	}

}

package org.nico.db.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/** 
 * Connection pool
 * 
 * @author nico
 * @version createTime：2018年4月1日 下午1:08:03
 */
@Deprecated
public class ConnectionPool {
	
	/**
	 * Single Connection service Num
	 */
	public static int singleMaxService = 10;
	
	/**
	 * Max Connection
	 */
	public static int maxConnection = 20;
	
	/**
	 * Min Connection
	 */
	public static int minConnection = 2;

	/**
	 * DataSource
	 */
	private SimpleDataSource dataSource;

	private boolean autoCommit;

	private Map<Connection, ConnectionEntity> connections;
	
	public ConnectionPool() throws SQLException{
		connections = new ConcurrentHashMap<Connection, ConnectionEntity>();
	}
	
	public synchronized Connection getConnection() throws SQLException{
		if(connections.size() > 0){
			for(Entry<Connection, ConnectionEntity> entry: connections.entrySet()){
				ConnectionEntity ce = entry.getValue();
				synchronized (ce.lockObj) {
					if(ce.count < singleMaxService &&  ! ce.isFree() || connections.size() >= maxConnection){
						ce.p();
						return entry.getKey();
					}
				}
			}
		}
		ConnectionEntity ce = new ConnectionEntity(connecting());
		connections.put(ce.connection, ce);
		return ce.connection;
	}

	private Connection connecting() throws SQLException{
		Connection connection = DriverManager.getConnection(dataSource.getUri(), dataSource.getUsername(), dataSource.getPassword()); 
		connection.setAutoCommit(autoCommit);
		return connection;
	}

	public void relaseConnection(Connection connection) throws SQLException{
		if(connections.containsKey(connection)){
			ConnectionEntity ce = connections.get(connection);
			synchronized (ce.lockObj) {
				if(ce.count > 0){
					ce.v();
				}
				if(ce.isFree() && connections.size() > minConnection){
					connections.remove(connection);
					connection.close();
				}
			}
		}
	}

	public SimpleDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(SimpleDataSource dataSource) throws SQLException {
		this.dataSource = dataSource;
		/**
		 * 初始化启动链接
		 */
		for(int index= 0; index < minConnection; index ++){
			ConnectionEntity ce = new ConnectionEntity(connecting());
			connections.put(ce.connection, ce);
		}
	}

	public boolean isAutoCommit() {
		return autoCommit;
	}

	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}

	static class ConnectionEntity{

		private Connection connection;

		private int count = 1;
		
		private Object lockObj = new Object();
		
		public ConnectionEntity(Connection connection) {
			this.connection = connection;
		}

		public synchronized void p(){
			++ count;
		}

		public synchronized void v(){
			-- count;
		}
		
		public boolean isFree(){
			return count <= 0;
		}

	}
}

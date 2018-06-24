package org.nico.db.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.nico.db.datasource.connection.SimpleConnection;
import org.nico.db.datasource.connection.SimpleConnection.ConnectionStatus;

/**
 * Simple Data Source
 * <p>
 * The inside contains a connectionHolder for ThreadLocal transaction management, 
 * and each phase will modify the clientinfo-> Status attribute in Connection,
 *  whose type is an enumeration {@link ConnectionStatus}.If and only if the Status 
 *  of Status is CONNECTED or CLOSED, the connection will be CLOSED. If the state 
 *  is UNOCCUPIED, the connection is free, and then put back in the pool for other requests!
 * 
 * 
 * @author nico
 * @date 2018/4/16
 */
public class SimpleDataSource implements DataSource{

	public static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>();  

	protected String uri;

	protected String username;

	protected String password;

	protected String driver;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public SimpleDataSource() {
		super();
	}


	public SimpleDataSource(String uri, String username, String password,
			String driver) {
		super();
		this.uri = uri;
		this.username = username;
		this.password = password;
		this.driver = driver;
	}

	private Connection connecting() throws SQLException{
		return connecting(username, password);
	}

	private Connection connecting(String username, String password) throws SQLException{
		return new SimpleConnection(DriverManager.getConnection(uri, username, password));
	}

	@Override
	public synchronized Connection getConnection() throws SQLException{  
		Connection conn = connectionHolder.get();  
		if (conn == null){  
			conn = connecting(); 
			connectionHolder.set(conn); 
//			conn.getClientInfo().put("status", ConnectionStatus.CONNECTED);
		} 
		return conn;  
	}  

	@Override
	public synchronized Connection getConnection(String username, String password)
			throws SQLException {
		Connection conn = connectionHolder.get();  
		if (conn == null){  
			conn = connecting(username, password);
			connectionHolder.set(conn);  
//			conn.getClientInfo().put("status", ConnectionStatus.CONNECTED);
		}     
		return conn;  
	}

	public void relaseConnection() throws SQLException{  
		Connection conn = connectionHolder.get();  
		if (conn != null){  
//			conn.getClientInfo().put("status", ConnectionStatus.CLOSED);
//			conn.close();  
			connectionHolder.remove();  
		}  
	}  

	public void beginTransaction() throws SQLException{ 
		Connection conn = connectionHolder.get();  
		if (conn != null){  
			if (conn.getAutoCommit()){  
				conn.setAutoCommit(false);  
			}  
			conn.getClientInfo().put("status", ConnectionStatus.CONNECTED_TRANSACTION_BEGINED);
		}  
	}  

	public void commitTransaction() throws SQLException{ 
		Connection conn = connectionHolder.get();  
		if (conn != null){  
			if (! conn.getAutoCommit()){  
				conn.commit();  
				conn.getClientInfo().put("status", ConnectionStatus.CONNECTED_TRANSACTION_COMMITED);
			}  
		}  
	}  

	/**
	 * Only modify connection status
	 * 
	 * @param conn 
	 * 			Connection
	 * @throws SQLException 
	 */
	public void closedTransaction() throws SQLException{  
		Connection conn = connectionHolder.get();  
		if (conn != null){  
			conn.getClientInfo().put("status", ConnectionStatus.CONNECTED_TRANSACTION_CLOSEED);
		}  
	} 

	public void rollbackTransaction(){  
		Connection conn = connectionHolder.get();  
		try {  
			if (conn != null){  
				if(!conn.getAutoCommit()){  
					conn.rollback();  
					conn.getClientInfo().put("status", ConnectionStatus.CONNECTED_TRANSACTION_ROLLBACK);
				}  
			}     
		}catch(SQLException e){  
			e.printStackTrace();  
		}     
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {

	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {

	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

}

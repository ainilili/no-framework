package org.nico.seeker.dom;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.nico.noson.util.string.StringUtils;

public class DomBean {

	private List<DomBean> domProcessers;

	private ConcurrentMap<String, String> param = new ConcurrentHashMap<String, String>();

	private String prefix;

	private boolean selfSealing;

	private String paramStr;

	private String body;
	
	private DomBean baseBean;

	public DomBean(DomBean baseBean){
		this.baseBean = baseBean;
	}

	public List<DomBean> getDomProcessers() {
		return domProcessers;
	}

	public void setDomProcessers(List<DomBean> domProcessers) {
		this.domProcessers = domProcessers;
	}

	public String get(String key){
		if(null == key) return key;
		return (String)this.param.get(key);
	}
	
	public boolean containsKey(String key){
		return this.param.containsKey(key);
	}
	
	public void setParam(String key, String value) {
		this.param.put(key, value);
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public boolean isSelfSealing() {
		return selfSealing;
	}

	public void setSelfSealing(boolean selfSealing) {
		this.selfSealing = selfSealing;
	}

	public String getParamStr() {
		return paramStr;
	}
	
	public DomBean getBaseBean() {
		return baseBean;
	}

	public void setBaseBean(DomBean baseBean) {
		this.baseBean = baseBean;
	}

	public ConcurrentMap<String, String> getParam() {
		return param;
	}

	public void setParamStr(String paramStr) {
		if(StringUtils.isNotBlank(paramStr));
		char[] domChars = paramStr.toCharArray();
		String[] kv = new String[2];
		StringBuffer cache = new StringBuffer();
		int singleCount = 0;
		int doubleCount = 0;
		boolean scanValue = false;
		for(int index = 0; index < domChars.length; index++){
			char c = domChars[index];
			if(index == domChars.length - 1){
				if(scanValue){
					kv[1] = cache.toString();
					this.param.put(kv[0], kv[1]);
				}
			}else{
				if(doubleCount == 0 && c == '\''){
					singleCount = singleCount == 1 ? 0 : 1;
					continue;
				}
				if(singleCount == 0 && c == '"'){
					doubleCount = doubleCount == 1 ? 0 : 1;
					continue;
				}
				if(c == '\r' || c == '\n') continue;
				if(scanValue){
					if(singleCount == 1 || doubleCount == 1){
						cache.append(c);
					}
					if(cache.length() != 0 && singleCount == 0 && doubleCount == 0){
						kv[1] = cache.toString();
						cache.setLength(0);
						this.param.put(kv[0], kv[1]);
						scanValue = false;
					}
				}else{
					if(c == '='){
						kv[0] = cache.toString().trim();
						cache.setLength(0);
						scanValue = true;
						continue;
					}else{
						cache.append(c);	
					}
				}
			}
		}
		this.paramStr = paramStr;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public DomBean getChild(int index){
		return this.domProcessers.get(index);
	}

	@Override
	public String toString() {
		return "DomBean [param=" + param + ", prefix=" + prefix + ", selfSealing="
				+ selfSealing + ", paramStr=" + paramStr + ", body=" + body + "]";
	}
	
}

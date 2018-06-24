package org.nico.seeker.plan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nico.seeker.dom.DomBean;
import org.nico.seeker.http.HttpMethod;

/** 
 * 定制轨迹
 * @author nico
 * @version 创建时间：2017年9月2日 下午8:05:12
 */
public class SeekerTrack {
	
	/*
	 * 路径
	 */
	private String uri;
	
	/*
	 * 请求类型
	 */
	private HttpMethod httpMethod;
	
	/*
	 * 请求参数
	 */
	private Map<String, Object> params;
	
	/*
	 * 具体轨迹信息
	 */
	private List<TrackBean> trackBeans;

	/*
	 * 搜索器 
	 */
	private String searcher;
	
	/*
	 * 收获
	 */
	private List<HarvestBean> harvestCollect = new ArrayList<HarvestBean>();
	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public List<TrackBean> getTrackBeans() {
		return trackBeans;
	}

	public void setTrackBeans(List<TrackBean> trackBeans) {
		this.trackBeans = trackBeans;
	}

	public String getSearcher() {
		return searcher;
	}

	public void setSearcher(String searcher) {
		this.searcher = searcher;
	}

	public List<HarvestBean> getHarvestCollect() {
		return harvestCollect;
	}

	public void setHarvestCollect(List<HarvestBean> harvestCollect) {
		this.harvestCollect = harvestCollect;
	}

	
}

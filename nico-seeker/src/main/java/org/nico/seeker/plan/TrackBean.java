package org.nico.seeker.plan;

import java.util.List;
import java.util.Map;

import org.nico.seeker.dom.DomBean;

/** 
 * 计划轨迹Bean
 * @author nico
 * @version 创建时间：2017年9月2日 下午8:08:43
 */
public class TrackBean {
	
	{
		recycle = false;
		reset = false;
	}
	
	/*
	 * 前缀
	 */
	private String prefix;
	
	/*
	 * 属性内容
	 */
	private String paramValue;
	
	/*
	 * 属性内容 
	 */
	private String paramName;
	
	/*
	 * 是否收获
	 */
	private Boolean recycle;
	
	/*
	 * 是否初始化
	 */
	private Boolean reset;
	
	/*
	 * 子流程
	 */
	private List<TrackBean> trackBeans;
	
	/*
	 * 记录
	 */
	private String record;
	
	public TrackBean() {}

	public TrackBean(String prefix, String paramName, String paramValue) {
		this.prefix = prefix;
		this.paramValue = paramValue;
		this.paramName = paramName;
	}
	
	public TrackBean(String prefix, String paramName, String paramValue, boolean recycle) {
		this.prefix = prefix;
		this.paramValue = paramValue;
		this.paramName = paramName;
		this.recycle = recycle;
	}
	
	public TrackBean(String prefix, String paramName, String paramValue, boolean recycle, boolean reset) {
		this.prefix = prefix;
		this.paramValue = paramValue;
		this.paramName = paramName;
		this.recycle = recycle;
		this.reset = reset;
	}
	
	public TrackBean(String prefix) {
		this.prefix = prefix;
	}
	
	public TrackBean(String prefix, boolean recycle) {
		this.prefix = prefix;
		this.recycle = recycle;
	}
	
	public TrackBean(String prefix, boolean recycle, boolean reset) {
		this.prefix = prefix;
		this.recycle = recycle;
		this.reset = reset;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public Boolean getRecycle() {
		return recycle;
	}

	public void setRecycle(Boolean recycle) {
		this.recycle = recycle;
	}

	public Boolean getReset() {
		return reset;
	}

	public void setReset(Boolean reset) {
		this.reset = reset;
	}

	public List<TrackBean> getTrackBeans() {
		return trackBeans;
	}

	public void setTrackBeans(List<TrackBean> trackBeans) {
		this.trackBeans = trackBeans;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}
	
}

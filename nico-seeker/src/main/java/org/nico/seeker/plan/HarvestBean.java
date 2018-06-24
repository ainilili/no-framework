package org.nico.seeker.plan;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.nico.seeker.dom.DomBean;
import org.nico.seeker.dom.DomHelper;

public class HarvestBean {
	
	private Date time;
	
	private List<DomBean> domBeans;
	
	private String record;
	
	private String printTime;
	
	private String formating;
	
	public HarvestBean(List<DomBean> domBeans, String record) {
		this.domBeans = domBeans;
		this.record = record;
		this.time = new Date();
		this.printTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(this.time);
		this.setFormating(domBeans);
	}

	public List<DomBean> getDomBeans() {
		return domBeans;
	}

	public void setDomBeans(List<DomBean> domBeans) {
		this.domBeans = domBeans;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public String getPrintTime() {
		return printTime;
	}

	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}
	
	public String getFormating() {
		return formating;
	}

	public void setFormating(List<DomBean> domBeans) {
		StringBuffer sb = new StringBuffer();
		DomHelper.viewHelper(sb, domBeans, 0);
		this.formating = sb.toString();
	}

	@Override
	public String toString() {
		return "HarvestBean [time=" + printTime + ", domBeans=" + domBeans
				+ ", record=" + record + "]";
	}
	
}

package org.nico.log.define;

public enum LoggingSwitch {
	
	//开
	ON(true),
	
	//关
	OFF(false);
	
	private boolean on;
	
	public boolean on(){
		return on;
	}
	
	LoggingSwitch(boolean on){
		this.on = on;
	}
	
}

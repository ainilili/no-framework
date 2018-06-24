package com.nico.example.entity;


public enum CustomerType implements CodeBaseEnum{
	
	PERSONAGE(0),
	
	ENTERPRISE(1);

	private int count;
	
	private CustomerType(int count){
		this.count = count;
	}
	
	@Override
	public int code() {
		return this.count;
	}
}	

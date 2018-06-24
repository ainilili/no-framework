package com.nico.example.entity;

public enum DeleteFlag implements CodeBaseEnum{
	
	/*
	 * 未删除
	 */
	NODEL(0),
	
	/*
	 * 逻辑删除
	 */
	DEL(1);
	
	private int count;
	
	private DeleteFlag(int count){
		this.count = count;
	}

	@Override
	public int code() {
		return this.count;
	}
}

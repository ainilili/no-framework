package com.nico.example.entity;

/**
 * 用户身份
 * @author liuqianyuan
 *
 */
public enum UserIdentity implements CodeBaseEnum{
	
	/*
	 * 总部
	 */
	HEADQUARTERS(0),
	
	/*
	 * 区域
	 */
	REGION(1),
	
	/*
	 * 管理员用户
	 */
	ADMIN(2),
	
	/*
	 * 普通用户
	 */
	NORMAL(3);
	
	private int count;
	
	private UserIdentity(int count){
		this.count = count;
	}
	
	@Override
	public int code() {
		return this.count;
	}

}

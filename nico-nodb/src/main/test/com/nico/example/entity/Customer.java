package com.nico.example.entity;

import java.io.Serializable;
import java.util.Date;

import org.nico.db.annotation.Primary;
import org.nico.db.annotation.Table;

@Table("customer")
public class Customer implements Serializable{

	@Primary
	private String id;
	
	private UserIdentity identity;
	
	private String name;
	
	private String email;
	
	private CustomerType customerType;
	
	private Date createTime;
	
	private Integer registeredCapital;
	
	private String businessScope;
	
	private Integer flag;
	
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserIdentity getIdentity() {
		return identity;
	}

	public void setIdentity(UserIdentity identity) {
		this.identity = identity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}


	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(Integer registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", identity=" + identity + ", name="
				+ name + ", email=" + email + ", customerType=" + customerType
				+ ", createTime=" + createTime + ", registeredCapital="
				+ registeredCapital + ", businessScope=" + businessScope + "]";
	}
	
}

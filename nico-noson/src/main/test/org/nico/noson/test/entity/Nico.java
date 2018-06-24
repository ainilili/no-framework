package org.nico.noson.test.entity;

import java.util.Set;

public class Nico {

	private String name;
	
	private int age;
	
	private Set<String> skill;
	
	private double deposit;
	
	private Info info;
	
	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Set<String> getSkill() {
		return skill;
	}

	public void setSkill(Set<String> skill) {
		this.skill = skill;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}
	
	@Override
	public String toString() {
		return "Nico [name=" + name + ", age=" + age + ", skill=" + skill + ", deposit=" + deposit + ", info=" + info
				+ "]";
	}

	public static class Info{
		
		private String address;
		
		private Job job;

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public Job getJob() {
			return job;
		}

		public void setJob(Job job) {
			this.job = job;
		}

		@Override
		public String toString() {
			return "info [address=" + address + ", job=" + job + "]";
		}
		
	}
	
	public static enum Job{
		
		IT,
		
		OTHER
		
	}
}

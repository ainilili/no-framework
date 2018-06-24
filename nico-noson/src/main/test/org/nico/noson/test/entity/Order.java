package org.nico.noson.test.entity;

import java.util.List;

import org.nico.noson.annotations.JsonIgnore;

public class Order {

	private String name;
	
	private double price;
	
	private int count;
	
	private String address;
	
	@JsonIgnore
	private List<Nico> books;
	
	public List<Nico> getBooks() {
		return books;
	}

	public void setBooks(List<Nico> books) {
		this.books = books;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Order [name=" + name + ", price=" + price + ", count=" + count + ", address=" + address + ", books="
				+ books + "]";
	}
	
}

package org.nico.noson.test;

import org.nico.noson.Noson;
import org.nico.noson.test.entity.Order;


public class AnnotationTest {

	public static void main(String[] args) {
		String json = "{name:订单,price:23.5,count:3,address:China,books:[{}]}";
		Order order = Noson.convert(json, Order.class);
		System.out.println(order);
	}
	
}

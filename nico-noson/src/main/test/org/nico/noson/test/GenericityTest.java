package org.nico.noson.test;

import org.nico.noson.Noson;
import org.nico.noson.test.entity.GenericityEntity;

public class GenericityTest {

	
	public static void main(String[] args) {
		
		String json = "{id:1,entity:[{name:{id:2}}]}";
		
		GenericityEntity bv =  Noson.convert(json, GenericityEntity.class);
		
		System.out.println(bv.getEntity().get(0).get("name").getId());
	}
}

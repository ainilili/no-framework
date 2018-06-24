package org.nico.util.collection;

import java.util.Collection;

public class CollectionUtils {

	public static <T extends Collection<?>> boolean isNotBlank(T list){
		if(list == null) return false;
		if(list.size() == 0) return false;
		return true;
	}
	
	public static <T extends Collection<?>> boolean isBlank(T list){
		return !isNotBlank(list);
	}
}

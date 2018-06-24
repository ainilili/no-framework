package org.nico.noson.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nico.noson.Noson;
import org.nico.noson.NosonConfig;
import org.nico.noson.test.entity.Cycle;

public class CycleTest {

	public static void main(String[] args) {
		
		//设置死循环-最大循环上限
		NosonConfig.ALLOW_CYCLE_MAX_COUNT = 5;
		NosonConfig.ALLOW_EMPTY = false;
		NosonConfig.ALLOW_EMPTY_TO_NULL = false;
		
		Cycle c = new Cycle();
		System.out.println(Noson.reversal(c));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("map", map);
		System.out.println(Noson.reversal(map));
		
		List<Object> list = new ArrayList<Object>();
		list.add(list);
		System.out.println(Noson.reversal(list));
		
		Set<Object> sets = new HashSet<Object>();
		sets.add(sets);
		System.out.println(Noson.reversal(sets));
		
		map.put("list", list);
		System.out.println(Noson.reversal(map));
		
	}
}

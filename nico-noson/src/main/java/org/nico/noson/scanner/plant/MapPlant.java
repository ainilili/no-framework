package org.nico.noson.scanner.plant;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapPlant extends AbstractPlant{

	@Override
	public Map get() {
		return new LinkedHashMap<String, Object>();
	}

}

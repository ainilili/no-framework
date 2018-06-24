package org.nico.noson.scanner.plant;

import java.util.LinkedHashSet;
import java.util.Set;

public class SetPlant extends AbstractPlant{

	@Override
	public Set get() {
		return new LinkedHashSet<Object>();
	}

}

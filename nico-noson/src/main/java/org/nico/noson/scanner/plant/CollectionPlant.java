package org.nico.noson.scanner.plant;

import java.util.Collection;
import java.util.LinkedList;

public class CollectionPlant extends AbstractPlant{

	@Override
	public Collection get() {
		return new LinkedList<Object>();
	}

}

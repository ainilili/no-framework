package org.nico.noson.scanner.plant;

import java.util.LinkedList;
import java.util.List;

public class ListPlant extends AbstractPlant{

	@Override
	public List get() {
		return new LinkedList<Object>();
	}

}

package org.nico.cat.mvc.compent;

import org.nico.aoc.ConfigKey;
import org.nico.cat.mvc.scan.annotations.Cinema;
import org.nico.cat.mvc.scan.annotations.RestCinema;

public class MVCCompent {

	static{
		/**
		 * 这里扩展aoc对mvc注解支持
		 */
		ConfigKey.ACCESS_BOOK_ABOUT_ANNOTATIONS.add(Cinema.class);
		ConfigKey.ACCESS_BOOK_ABOUT_ANNOTATIONS.add(RestCinema.class);
	}
}

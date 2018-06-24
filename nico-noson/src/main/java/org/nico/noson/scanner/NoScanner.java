package org.nico.noson.scanner;

import java.util.Collection;

import org.nico.noson.Noson;
import org.nico.noson.exception.NosonException;

public interface NoScanner {

	/**
	 * Scanning the json as single Object & convert to Noson
	 * 
	 * @param json 
	 * 			The json being scanned
	 * @return noson 
	 * 			object
	 */
	public Noson scanSingle(String json);
	
	/**
	 * Scanning the json as array Object & convert to List<Object>
	 * 
	 * @param json 
	 * 			Json being scanned
	 * @return array 
	 * 			Object
	 */
	public Collection<Object> scanArray(String json);
	
	
	public <T> T scan(String json, Class<T> clazz) throws NosonException;
}

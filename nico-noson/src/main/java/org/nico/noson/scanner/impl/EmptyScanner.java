package org.nico.noson.scanner.impl;

import java.util.Collection;

import org.nico.noson.Noson;
import org.nico.noson.exception.NosonException;
import org.nico.noson.scanner.NoScanner;
import org.nico.noson.scanner.buddy.JsonFieldParser;
import org.nico.noson.scanner.buddy.JsonKeyParser;
import org.nico.noson.scanner.buddy.JsonValueParser;

/** 
 * Used for interface method transitions
 * 
 * @author nico
 */

public class EmptyScanner implements NoScanner{

	protected JsonFieldParser jsonKeyParser;
	
	protected JsonFieldParser jsonValueParser;
	
	public EmptyScanner(){
		jsonKeyParser = new JsonKeyParser();
		jsonValueParser = new JsonValueParser();
	}
	
	@Override
	public Noson scanSingle(String json) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<Object> scanArray(String json) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T scan(String json, Class<T> clazz) throws NosonException {
		throw new UnsupportedOperationException();
	}

}

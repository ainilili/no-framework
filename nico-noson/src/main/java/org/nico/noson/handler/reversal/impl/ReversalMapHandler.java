package org.nico.noson.handler.reversal.impl;

import java.util.Map;

import org.nico.noson.entity.ReversalRecorder;
import org.nico.noson.handler.reversal.ReversalHandler;

public class ReversalMapHandler extends ReversalHandler{

	@Override
	public String handle(Object obj) {
		if(obj instanceof Map){
			return handleMap((Map<Object, Object>)obj, new ReversalRecorder());
		}
		return nextHandler.handle(obj);
	}

}

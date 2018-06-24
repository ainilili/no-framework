package org.nico.noson.handler.reversal.impl;

import java.util.Collection;

import org.nico.noson.entity.ReversalRecorder;
import org.nico.noson.handler.reversal.ReversalHandler;

public class ReversalArrayHandler extends ReversalHandler{

	@Override
	public String handle(Object obj) {
		if(obj instanceof Collection){
			return handleArray(obj, new ReversalRecorder());
		}
		return nextHandler.handle(obj);
	}

}

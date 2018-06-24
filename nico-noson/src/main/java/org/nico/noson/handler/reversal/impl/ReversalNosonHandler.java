package org.nico.noson.handler.reversal.impl;


import org.nico.noson.Noson;
import org.nico.noson.entity.ReversalRecorder;
import org.nico.noson.handler.reversal.ReversalHandler;

public class ReversalNosonHandler extends ReversalHandler{

	@Override
	public String handle(Object obj) {
		if(obj instanceof Noson){
			return handleNoson((Noson)obj, new ReversalRecorder());
		}
		return nextHandler.handle(obj);
	}

}

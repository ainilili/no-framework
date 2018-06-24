package org.nico.noson.handler.reversal.impl;

import org.nico.noson.handler.reversal.ReversalHandler;

public class ReversalVerityHandler extends ReversalHandler{

	@Override
	public String handle(Object obj) {
		if(obj == null){
			return null;
		}
		return this.nextHandler.handle(obj);
	}

}

package org.nico.noson.handler.reversal.impl;

import org.nico.noson.entity.ReversalRecorder;
import org.nico.noson.handler.reversal.ReversalHandler;

public class ReversalObjectHandler extends ReversalHandler{

	@Override
	public String handle(Object obj) {
		return handleObject(obj, new ReversalRecorder());
	}

}

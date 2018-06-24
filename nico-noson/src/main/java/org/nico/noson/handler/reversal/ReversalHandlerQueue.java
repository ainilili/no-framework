package org.nico.noson.handler.reversal;

import java.util.LinkedList;

public class ReversalHandlerQueue {

	private LinkedList<ReversalHandler> handlers;

	public ReversalHandlerQueue(){
		handlers = new LinkedList<ReversalHandler>();
	}

	public void add(ReversalHandler handler){
		if(handlers.size() > 0){
			handlers.getLast().nextHandler = handler;
		}
		handlers.add(handler);
	}

	public String handle(Object object){
		if(handlers.size() > 0){
			return handlers.getFirst().handle(object);
		}
		return null;
	}
}

package org.nico.util.expression;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.nico.util.placeholder.PlaceHolderHelper;

public class ExpressionHelper {
	
	private Object params;
	
	private String expression;
	
	private ExpressionHandler handler;
	
	public ExpressionHelper(Object params, String expression) {
		this.params = params;
		this.expression = expression;
		handler = new ExpressionHandler();
	}
	
	public Object calculate(){
		PlaceHolderHelper placeHolder = new PlaceHolderHelper(null, null);
		expression = placeHolder.replacePlaceholders(expression.replaceAll("'", ""), params);
		System.out.println("表达式: " + expression);
		return handler.calculate(expression);
	}
	
	public static void main(String[] args) {
		String str = "'type' == 'Fault' || (('type' == 'Monitor') && (now_status != 0))";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", 1);
		map.put("Fault", 2);
		map.put("Monitor", 3);
		map.put("now_status", 3);
		ExpressionHelper eh = new ExpressionHelper(map, str);
		eh.calculate();
		
	}
	
}

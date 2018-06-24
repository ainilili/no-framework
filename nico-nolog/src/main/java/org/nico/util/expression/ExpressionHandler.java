package org.nico.util.expression;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.nico.util.math.MathUtils;
import org.nico.util.stack.Stack;
import org.nico.util.stack.impl.ArrayStack;
import org.nico.util.string.StringUtils;

public class ExpressionHandler {

	private Map<String, Integer> table = new HashMap<String, Integer>(){
		private static final long serialVersionUID = 1L;
		{
			put("|", -30);
			put("^", -20);
			put("&", -10);
			put(">>",-5);
			put("<<",-5);
			put("+", 1);
			put("-", 1);
			put("*", 2);
			put("/", 2);
			put("(", 3);
			put(")", 3);
			put("[", 4);
			put("]", 4);
			put("{", 5);
			put("}", 5);

		}
	};

	private Map<String, String> symbolicAssemblyable = new HashMap<String, String>(){
		private static final long serialVersionUID = 1L;
		{
			put("(", ")");
			put("[", "]");
			put("{", "}");
		}
	};

	private Set<String> ruleSymbolSet = new HashSet<String>(){
		private static final long serialVersionUID = 1L;
		{
			add("(");
			add("[");
			add("{");
			add(")");
			add("]");
			add("}");
		}
	};

	private Set<String> symbolSet = new HashSet<String>(){
		private static final long serialVersionUID = 1L;
		{
			add("+");
			add("-");
			add("*");
			add("/");
			add("(");
			add("[");
			add("{");
			add(")");
			add("]");
			add("}");
			add("^");
			add("|");
			add("&");
			add(">");
			add("<");

		}
	};

	private Set<String> specialSymbolSet = new HashSet<String>(){
		private static final long serialVersionUID = 1L;
		{
			add(")");
			add("]");
			add("}");
		}
	};

	private final String BLANK = "$";

	public Number calculate(String expression) {
		Stack<Number> numStack = new ArrayStack<Number>();
		Stack<String> symStack = new ArrayStack<String>();
		String[] datas = preprocessing(expression);
		for(int i = 0; i < datas.length; i ++){
			String current = datas[i];
			if(StringUtils.isBlank(current)) continue;
			String next = i < datas.length - 1 ? datas[i + 1] : null;
			if(StringUtils.isNum(current)){
				numStack.push(Double.parseDouble(current));
				if(StringUtils.isNotBlank(next)){
					if(StringUtils.isNotNum(next)){
						if(symStack.size() > 0){
							String preSym = symStack.get();
							if(compare(preSym, next) != -1){
								Number currentNum = numStack.pop();
								Number preNum = numStack.pop();
								Number newNum = formulaTable(preNum, currentNum, preSym);
								numStack.push(newNum);
								symStack.pop();
								continue;
							}
						}
					}
				}else{
					String sym = null;
					while(StringUtils.isNotBlank((sym = symStack.pop()))){
						if(ruleSymbolSet.contains(sym)) continue;
						Number currentNum = numStack.pop();
						Number preNum = numStack.pop();
						Number newNum = formulaTable(preNum, currentNum, sym);
						numStack.push(newNum);
					}
				}
			}else{
				if(ruleSymbolSet.contains(current)){
					StringBuffer temp = new StringBuffer();
					int j = i + 1;
					int count = 0;
					for( ; j < datas.length; j ++ ){
						if(datas[j].equals(current)) count ++;
						if(datas[j].equals(symbolicAssemblyable.get(current)) && count == 0) break;
						if(datas[j].equals(symbolicAssemblyable.get(current)) && count != 0) count --;
						temp.append(datas[j]);
					}
					datas[j] = String.valueOf(calculate(temp.toString()));
					i = j - 1;
					continue;
				};
				symStack.push(current);
			}
		}
		return numStack.pop();
	}

	private String[] preprocessing(String expression){
		char[] chars = expression.toCharArray();
		StringBuffer expressionPlus = new StringBuffer();
		for(int index = 0; index < chars.length; index ++){
			char c = chars[index];
			if(symbolSet.contains(String.valueOf(c)) && index > 0 && !symbolSet.contains(String.valueOf(expressionPlus.charAt(expressionPlus.length() - 1)))){
				String target = String.valueOf(c);
				if(c == '-' && index > 0 && specialSymbolSet.contains(String.valueOf(chars[index - 1]))){
					expressionPlus.append(target);
				}else{
					switch(c){
					case '>': 
					case '<':
						if(index + 1 < chars.length && chars[index + 1] == c){
							target = String.valueOf(c) + String.valueOf(chars[index + 1]);
							index ++;
						}else{
							try {
								throw new Exception("Target [" + c + "] is illegal !!");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						break;
					}
					expressionPlus.append(BLANK + target + BLANK);
				}

			}else{
				if(StringUtils.isNotBlank(c)){
					expressionPlus.append(c);
					if(symbolSet.contains(String.valueOf(c))){
						if(index == 0 && c == '-') continue;
						expressionPlus.append(BLANK);
					}
				}
			}
		}
		String[] targets = expressionPlus.toString().replace(BLANK + BLANK, BLANK).split("[$]");
		if(targets != null){
			for(int index = 0; index < targets.length; index ++){
				String target = targets[index];
				int mod = 0;
				if((mod = target.indexOf("x")) != -1 || (mod = target.indexOf("X")) != -1){
					targets[index] = String.valueOf(MathUtils.HexToInt(target));
				}
			}
		}
		return targets;
	}

	private Number formulaTable(Number currentNum, Number preNum, String sym){
		Number target = null;
		if(sym.equals("+")){
			target = currentNum.doubleValue() + preNum.doubleValue();
		}else if(sym.equals("-")){
			target = currentNum.doubleValue() - preNum.doubleValue();
		}else if(sym.equals("*")){
			target = currentNum.doubleValue() * preNum.doubleValue();
		}else if(sym.equals("/")){
			target = currentNum.doubleValue() / preNum.doubleValue();
		}else if(sym.equals("^")){
			target = currentNum.intValue() ^ preNum.intValue();
		}else if(sym.equals("&")){
			target = currentNum.intValue() & preNum.intValue();
		}else if(sym.equals("|")){
			target = currentNum.intValue() | preNum.intValue();
		}else if(sym.equals(">>")){
			target = currentNum.intValue() >> preNum.intValue();
		}else if(sym.equals("<<")){
			target = currentNum.intValue() << preNum.intValue();
		}
		return target;
	}

	private int compare(String e1, String e2){
		int p1 = table.get(e1);
		int p2 = table.get(e2);
		if(p1 == p2){
			return 0;
		}else if(p1 < p2){
			return -1;
		}else{
			return 1;
		}
	}



}

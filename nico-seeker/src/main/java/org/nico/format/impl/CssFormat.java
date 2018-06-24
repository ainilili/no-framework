package org.nico.format.impl;

import org.nico.format.GrammarFormat;
import org.nico.format.symbol.SymbolSets;
import org.nico.noson.util.string.StringUtils;

public class CssFormat  extends SymbolSets implements GrammarFormat{

	@Override
	public String format(String grammar) {
		StringBuilder css = new StringBuilder();
		if(StringUtils.isBlank(grammar)) return null;
		grammar = grammar.replaceAll(" |\t", "");
		char[] chars = grammar.toCharArray();
		int count = 0;
		for(int index = 0; index < chars.length; index ++ ){
			char target = chars[index];
			if(target == bracket){
				++count;
				css.append(bracket + "\r\n" + nTableFeed(count));
			}else if(target == parentheses){
				--count;
				css.append("\r\n" + nTableFeed(count) + parentheses + "\r\n");
			}else if(target == semicolon){
				css.append("\r\n" + nTableFeed(count));
			}else{
				css.append(target);
			}
		}
		return css.toString();
	}

}

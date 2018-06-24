package org.nico.format.symbol;

public class SymbolSets {
	
	protected char semicolon = ';';

	protected char bracket = '{';
	
	protected char parentheses = '}';
	
	public String nTableFeed(int n){
		StringBuilder lines = new StringBuilder();
		while(n-- >= 0){
			lines.append("\t");
		}
		return lines.toString();
	}
}

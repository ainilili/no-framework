package org.nico.noson.verify;

/** 
 * Noson notation verification
 * 
 * @author nico
 */

public class SymbolVerify {

	//Braces are currently closed
	private int brace = 0;

	//The brackets are currently closed
	private int bracket = 0;

	//Single quotes are currently closed
	private int singleQuote = 0;

	//Double quotes are currently closed
	private int doubleQuote = 0;
	
	//The precondition for reading the value tag is that there is a colon in front of it
	private int colon = 0;

	//Temporary parameters
	private int temp = 0;
	
	//Last character
	private char preChar = 0;
	
	/**
	 * Verify that all symbols of the current scan node are closed
	 * 
	 * @return The verification results
	 */
	public boolean safetyAll(){
		return brace == 0 && bracket == 0 && singleQuote == 0 && doubleQuote == 0 && temp == 0;
	}
	
	/**
	 * Verify that the current scan node quotes (single/double) are closed
	 * 
	 * @return The verification results
	 */
	public boolean safetyQuote(){
		return singleQuote == 0 && doubleQuote == 0;
	}
	
	/**
	 * Verify that the current scan node braces are closed
	 * 
	 * @return The verification results
	 */
	public boolean safetyBrace(){
		return brace == 0;
	}
	
	/**
	 * Verify that the parentheses in the current scan node are closed
	 * 
	 * @return The verification results
	 */
	public boolean safetyBracket(){
		return bracket == 0;
	}
	
	/**
	 * Verify that the current scan node single quotation marks are closed
	 * 
	 * @return The verification results
	 */
	public boolean safetySingleQuote(){
		return singleQuote == 0;
	}
	
	/**
	 * Verify that the current scan node double quotation marks are closed
	 * 
	 * @return The verification results
	 */
	public boolean safetyDoubleQuote(){
		return doubleQuote == 0;
	}
	
	/**
	 * Verify that the current scan node is reading value </br>
	 * <b>introduce：</b>
	 * When the scan arrives at:, the value is ready to be read
	 * 
	 * @return boolean
	 */
	public boolean turnValue(){
		return colon == 1;
	}
	
	
	/**
	 * The json sensitive symbol is scanned and the hit returns true
	 * 
	 * @param target char
	 * @return boolean
	 */
	public boolean verify(char target){
		int sum = brace + bracket + singleQuote + doubleQuote;
		switch(target){
		case '{': brace++; break;
		case '}': brace--; break;
		case '[': bracket++; break;
		case ']': bracket--; break;
		case '\'': if(preChar != '\\' && doubleQuote == 0) temp = (singleQuote == 1) ? --singleQuote : ++singleQuote; break;
		case '\"': if(preChar != '\\' && singleQuote == 0) temp = (doubleQuote == 1) ? --doubleQuote : ++doubleQuote; break;
		case ':': if( singleQuote + doubleQuote == 0) colon = 1; break;
		}
		preChar = target;
		return sum != (brace + bracket + singleQuote + doubleQuote);
	}
	
	/**
	 * Check that the json string is valid
	 * 
	 * @param json json
	 * @return boolean
	 */
	public boolean check(String json){
		char[] chars = json.toCharArray();
		for(char ch: chars){
			verify(ch);
		}
		return safetyAll();
	}

	/**
	 * To determine whether a symbol is a special symbol
	 * <b>example: </b> when the quotation marks are closed, the scan to ':' returns true
	 * 
	 * @param c char
	 * @return boolean
	 */
	public boolean isSpecial(char c){
		if(singleQuote + doubleQuote != 0) return false;
		return c == ':';
	}
	
	public int getBrace() {
		return brace;
	}

	public void setBrace(int brace) {
		this.brace = brace;
	}

	public int getBracket() {
		return bracket;
	}

	public void setBracket(int bracket) {
		this.bracket = bracket;
	}

	public int getSingleQuote() {
		return singleQuote;
	}

	public void setSingleQuote(int singleQuote) {
		this.singleQuote = singleQuote;
	}

	public int getDoubleQuote() {
		return doubleQuote;
	}

	public void setDoubleQuote(int doubleQuote) {
		this.doubleQuote = doubleQuote;
	}

	public int getColon() {
		return colon;
	}

	public void setColon(int colon) {
		this.colon = colon;
	}
	
}

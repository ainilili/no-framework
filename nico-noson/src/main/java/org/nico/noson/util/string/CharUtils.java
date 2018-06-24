package org.nico.noson.util.string;

public class CharUtils {
	
	public static boolean checkBlankChar(char c){
		if(c == '﻿' || c == ' ' || c == '\t' || c == '\n' || c == '\r'){
			return true;
		}
		return false;
	}
}

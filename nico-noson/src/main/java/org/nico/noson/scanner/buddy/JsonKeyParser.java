package org.nico.noson.scanner.buddy;

public class JsonKeyParser implements JsonFieldParser{

	@Override
	public ParserResult parser(String json, int offset) {
		char startChar = json.charAt(offset);
		int subTail = -1;
		int increment = 0;
		int temp = 0;
		if(startChar == '\'' || startChar == '"'){
			increment = 2;
			temp = offset;
			while(temp == offset || json.charAt(temp - 1) == '\\'){
				temp = json.indexOf(startChar, ++ temp);
			}
			++ offset;
			subTail = temp;
		}else{
			subTail = json.indexOf(':', offset);
		}
		return new ParserResult(json.substring(offset, subTail), subTail - offset + increment);
	}

}

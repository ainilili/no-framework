package org.nico.noson.scanner.buddy;


public class JsonValueParser implements JsonFieldParser{

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
			int tempTail = -1;
			temp = json.indexOf(',', offset);
			subTail = temp;
			subTail = ((tempTail = json.indexOf(']', offset)) < subTail && tempTail != -1) || subTail == -1 ? tempTail : subTail;
			subTail = ((tempTail = json.indexOf('}', offset)) < subTail && tempTail != -1) || subTail == -1 ? tempTail : subTail;
			if(temp != subTail){
				increment = 0;
			}
		}
		return new ParserResult(json.substring(offset, subTail), subTail - offset + increment);
	}
	
}

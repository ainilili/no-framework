package org.nico.noson.scanner.buddy;

/**
 * Provides an interface to implement Field resolution in different scenarios.
 * 
 * @author nico
 */
public interface JsonFieldParser {

	/**
	 * Parser
	 * 
	 * @param json Json str
	 * @param offset Offset
	 * @return {@link ParserResult}
	 */
	public ParserResult parser(String json, int offset);
	
	/**
	 * These result values are necessary for the outside world 
	 * to store the resolved results.
	 * 
	 * @author nico
	 */
	public static class ParserResult{
		
		private String value;
		
		private int len;

		public ParserResult(String value, int len) {
			super();
			this.value = value;
			this.len = len;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public int getLen() {
			return len;
		}

		public void setLen(int len) {
			this.len = len;
		}

		@Override
		public String toString() {
			return "ParserResult [value=" + value + ", len=" + len + "]";
		}
	}
}

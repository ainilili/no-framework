package org.nico.cat.server.response.parameter;

/** 
 * Http response code
 * @author nico
 * @version createTime：2018年1月10日 下午10:22:44
 */

public enum HttpCode {

	HS100(100, "Continue"),
	HS101(101, "Switching Protocols"),
	HS102(102, "Processing"),
	HS200(200, "OK"),
	HS201(201, "Created"),
	HS202(202, "Accepted"),
	HS203(203, "Non-Authoritative Information"),
	HS204(204, "No Content"),
	HS205(205, "Reset Content"),
	HS206(206, "Partial Content"),
	HS207(207, "Multi-Status"),
	HS300(300, "Multiple Choices"),
	HS301(301, "Moved Permanently"),
	HS302(302, "Move temporarily"),
	HS303(303, "See Other"),
	HS304(304, "Not Modified"),
	HS305(305, "Use Proxy"),
	HS306(306, "Switch Proxy"),
	HS307(307, "Temporary Redirect"),
	HS400(400, "Bad Request"),
	HS401(401, "Unauthorized"),
	HS402(402, "Payment Required"),
	HS403(403, "Forbidden"),
	HS404(404, "Not Found"),
	HS405(405, "Method Not Allowed"),
	HS406(406, "Not Acceptable"),
	HS407(407, "Proxy Authentication Required"),
	HS408(408, "Request Timeout"),
	HS409(409, "Conflict"),
	HS410(410, "Gone"),
	HS411(411, "Length Required"),
	HS412(412, "Precondition Failed"),
	HS413(413, "Request Entity Too Large"),
	HS414(414, "Request-URI Too Long"),
	HS415(415, "Unsupported Media Type"),
	HS416(416, "Requested Range Not Satisfiable"),
	HS417(417, "Expectation Failed"),
	HS421(421, "too many connections"),
	HS422(422, "Unprocessable Entity"),
	HS423(423, "Locked"),
	HS424(424, "Failed Dependency"),
	HS425(425, "Unordered Collection"),
	HS426(426, "Upgrade Required"),
	HS449(449, "Retry With"),
	HS451(451, "Unavailable For Legal Reasons"),
	HS500(500, "Internal Server Error"),
	HS501(501, "Not Implemented"),
	HS502(502, "Bad Gateway"),
	HS503(503, "Service Unavailable"),
	HS504(504, "Gateway Timeout"),
	HS505(505, "HTTP Version Not Supported"),
	HS506(506, "Variant Also Negotiates"),
	HS507(507, "Insufficient Storage"),
	HS509(509, "Bandwidth Limit Exceeded"),
	HS510(510, "Not Extended"),
	HS600(600, "Unparseable Response Headers");
	
	private int code;
	
	private String message;

	private HttpCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}

package org.nico.util.string.line;

/**
 * 获取当前执行代码行数
 * @author nico
 * @date 2018年1月22日
 */
public class LineUtils {

	public static int getLineInfo(){
		StackTraceElement[] elements = new Throwable().getStackTrace();
		StackTraceElement ste = elements[3];
		return ste.getLineNumber();
	}
}

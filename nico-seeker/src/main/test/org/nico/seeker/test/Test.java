package org.nico.seeker.test;

import org.nico.seeker.scan.SeekerScanner;
import org.nico.seeker.scan.impl.NicoScanner;

/** 
 * 
 * @author nico
 * @version createTime：2018年3月10日 下午10:26:05
 */

public class Test {

	public static void main(String[] args) {
		String dom = "<!--<a txt='1'/> --><b txt='1' />";
		SeekerScanner scan = new NicoScanner(dom);
		System.out.println(scan.getDomBeans());
	}
}

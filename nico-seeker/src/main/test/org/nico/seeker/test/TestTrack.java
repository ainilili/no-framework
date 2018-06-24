package org.nico.seeker.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.nico.seeker.dom.DomBean;
import org.nico.seeker.dom.DomHelper;
import org.nico.seeker.plan.HarvestBean;
import org.nico.seeker.starter.SeekerStart;
import org.nico.seeker.starter.helper.SeekerStarterHelper;
import org.nico.seeker.stream.NioUtils;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年9月2日 下午9:48:29
 */

public class TestTrack {
	
	public static void main(String[] args) {
		String tracks = "";
		try {
			tracks = NioUtils.readFileToString(new File("E://test/csdn.json"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		SeekerStarterHelper ssh = new SeekerStarterHelper(new SeekerStart());
		ssh.runHelper(tracks);
		List<HarvestBean> hbs = ssh.getHarvestCollect();
		for(HarvestBean h: hbs){
			System.out.println(h);
		}
		
	/*	List<DomBean> dbs = seeker.getSearcher().getDomBeans();
		StringBuffer html = new StringBuffer();
		DomHelper.viewHelper(html, dbs, 0);
		System.out.println("开始写入");
		File f = new File("E://test/out1.html");
		
		try {
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(html.toString().getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
	}
}

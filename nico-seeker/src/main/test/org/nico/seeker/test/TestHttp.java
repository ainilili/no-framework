package org.nico.seeker.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nico.seeker.dom.DomBean;
import org.nico.seeker.http.HttpMethod;
import org.nico.seeker.scan.SeekerScanner;
import org.nico.seeker.scan.impl.NicoScanner;
import org.nico.seeker.searcher.SeekerSearcher;
import org.nico.seeker.searcher.impl.NicoSearcher;

public class TestHttp {
	
	public static void main(String[] args) throws IOException {
		
//		SeekerScanner scan = new NicoScanner("https://www.konsus.com/product/professional-powerpoint-formatting", HttpMethod.GET, null);
//		//SeekerScanner scan = new SeekerScanner(String);
//		System.out.println(scan.getDocument());
//		System.out.println("开始写入");
//		StringBuffer sb = new StringBuffer();
//		DomHelper.viewHelper(sb, scan.getDomBeans(), 0);
//		File f = new File("E://test/out2.html");
//		
//		try {
//			FileOutputStream fos = new FileOutputStream(f);
//			fos.write(sb.toString().getBytes());
//			fos.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		/*SeekerScanner scan = new SeekerScanner("https://www.tmall.com/", HttpMethod.GET, null);
		System.out.println(scan.getDomProcessers().get(0).getBody());*/
		
		
		
//		
//		SeekerSearcher ns = new NicoSearcher(scan);
//		List<DomBean> domBeans = ns.searching("div", "class", "page_nav").searching("span").getResults();
//		DomBean domBean = domBeans.get(0);
//		System.out.println(domBean);
//		
//		//设置请求参数
//		params = new HashMap<String, Object>();
//		params.put("channelid", 0);
//		params.put("page", 1);
//		//设置轨迹
//		List<TrackBean> ts = new ArrayList<TrackBean>();
//		//轨迹一
//		ts.add(new TrackBean("div", "class", "page_nav"));
//		//轨迹二（带回收和记录）
//		TrackBean t = new TrackBean("span",true);
//		t.setRecord("页码爬取");
//		ts.add(t);
//		//装入SeekerTrack
//		SeekerTrack st = new SeekerTrack();
//		st.setUri("https://blog.csdn.net/peoplelist.html");
//		st.setParams(params);
//		st.setSearcher("org.nico.seeker.searcher.impl.NicoSearcher");
//		st.setHttpMethod(HttpMethod.GET);
//		st.setTrackBeans(ts);
//		//装入SeekerStart
//		SeekerStart ss = new SeekerStart(st);
//		//启动SeekerStart
//		ss.run();
//		//查看结果集
//		for(HarvestBean hb: ss.getHarvestCollect()){
//			System.out.println(hb);
//		}
		
		/*------------------------csdn专家组爬取测试---------------------------------*/
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("channelid", 0);
		params.put("page", 1);
		SeekerScanner scan = new NicoScanner("https://blog.csdn.net/peoplelist.html", HttpMethod.GET, params);
		System.out.println(scan.getDomBeans().size());
		
		SeekerSearcher ns = new NicoSearcher(scan);
		String pageStr = ns.searching("div", "class", "page_nav").searching("span").getResults().get(0).getBody();
		int page = Integer.parseInt(pageStr.split("共")[1].replaceAll("[^0-9]", ""));
		System.out.println("页码: " + page);
		ns.reset();
		List<DomBean> domBeans = ns.searching("dl").getResults();
		for(DomBean d: domBeans){
			System.out.println("------------------");
			ns.setDomBeans(new DomBean[]{d});
			String userHref = ns.searching("dd").searching("a").getResults().get(0).get("href").toString();
			userHref = "https" + userHref.substring(userHref.indexOf(':'));
			System.out.println("此用户链接为: " + userHref);
			SeekerScanner userScan = new NicoScanner(userHref, HttpMethod.GET, null);
			SeekerSearcher userNs = new NicoSearcher(userScan);
			List<DomBean> userDomBeans = userNs.searching("div", "id", "article_list").searching("div", "class", "list_item clearfix article_item").getResults();
			int index = 1;
			for(DomBean userDomBean: userDomBeans){
				ns.setDomBeans(new DomBean[]{userDomBean});
				String blogUrl = ns.searching("a").getResults().get(0).get("href").toString();
				System.out.println("第一页第" + (index++) + "篇博客的url为： " + blogUrl);
//				SeekerScanner blogScan = new NicoScanner(blogUrl, HttpMethod.GET, null);
//				SeekerSearcher blogNs = new NicoSearcher(blogScan);
//				DomBean blogDomBean = blogNs.searching("div", "id", "article_content").getResults().get(0);
//				System.out.print("内容是：" );
//				System.out.println(blogDomBean.getBody());
			}
		}
	} 
}

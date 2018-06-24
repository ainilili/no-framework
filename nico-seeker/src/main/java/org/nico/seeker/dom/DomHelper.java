package org.nico.seeker.dom;

import java.util.List;

import org.nico.format.GrammarFormat;
import org.nico.format.impl.CssFormat;
import org.nico.format.impl.JavaScriptFormat;

public class DomHelper {
	
	
	public static void viewHelper(StringBuffer dom, List<DomBean> domBeans, int index){
		GrammarFormat jsFormat = new JavaScriptFormat();
		GrammarFormat cssFormat = new CssFormat();
		StringBuffer sb = new StringBuffer("");
		for(int i = 0; i < index; i++){
			sb.append("\t");
		}
		if(domBeans == null) return;
		for(DomBean d: domBeans){
			String paramStr = "";
			if(d.getParamStr() != null){
				paramStr = d.getParamStr().replaceAll("&", " ");
			}
			if(d.isSelfSealing()){
				dom.append(sb.toString() + "<" + d.getPrefix() + " " + paramStr + " />" + "\r\n");
			}else{
				dom.append(sb.toString() + "<" + d.getPrefix() + " " + paramStr + " >" + "\r\n");
				if(d.getDomProcessers() != null && d.getDomProcessers().size() > 0){
					viewHelper(dom, d.getDomProcessers(), index + 1);
				}else{
					if(d.getPrefix().equals("script")){
						dom.append(jsFormat.format(d.getBody()) + "\r\n");
					}else if(d.getPrefix().equals("style")){
						dom.append(cssFormat.format(d.getBody()) + "\r\n");
					}else{
						dom.append(sb.toString() + d.getBody() + "\r\n");
					}
				}
				dom.append(sb.toString() + "</"+ d.getPrefix() + ">"+ "\r\n");
			}
		}
	}
}

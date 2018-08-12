package org.nico.fig.center;

import org.nico.fig.center.bean.NocatBean;

public class ConfigCenter {

	private static ConfigCenter instance;
	
	public static ConfigCenter getInstance(){
		try {    
            if(instance != null){
            }else{  
                Thread.sleep(300);  
                synchronized (ConfigCenter.class) {  
                    if(instance == null){
                        instance = new ConfigCenter();  
                    }  
                }  
            }   
        } catch (InterruptedException e) {   
            e.printStackTrace();  
        }  
        return instance; 
	}
	
	public static void copy(ConfigCenter center) {
		center.setNocat(center.getNocat());
	}
	
	private NocatBean nocat;

	public NocatBean getNocat() {
		return nocat;
	}

	public void setNocat(NocatBean nocat) {
		this.nocat = nocat;
	}
	

}

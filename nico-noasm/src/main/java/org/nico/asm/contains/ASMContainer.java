package org.nico.asm.contains;

/** 
 * 
 * @author nico
 * @version createTime：2018年4月30日 下午9:02:50
 */

public class ASMContainer {

	private static ASMContainer container;

	private ASMContainer(){};

	public static ASMContainer getInstance(){
		try {    
			if(container != null){

			}else{
				Thread.sleep(100);  
				synchronized (ASMContainer.class) {
					if(container == null){

						container = new ASMContainer();

					}
				}
			}
		} catch (InterruptedException e) {   
			e.printStackTrace();  
		} 
		return container;
	}
	
}

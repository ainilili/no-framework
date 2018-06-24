package org.nico.aoc.aspect;

import org.nico.aoc.aspect.point.AspectPoint;
import org.nico.aoc.aspect.point.ProcessingAspectPoint;

/** 
 * 
 * @author nico
 * @version createTime：2018年3月7日 下午10:26:20
 */
public interface AspectProxy {
	
	public void before(AspectPoint point) throws Throwable;
	
	public Object around(ProcessingAspectPoint point) throws Throwable;
	
	public void after(AspectPoint point) throws Throwable;
	
	public void wrong(ProcessingAspectPoint point, Throwable throwable) throws Throwable;
}

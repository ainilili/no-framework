package org.nico.cat.mvc.container.analyze;

import java.util.List;

import org.nico.cat.mvc.container.entity.CinemaEntity;

/** 
 * Analyze the cinema entity
 * @author nico
 * @version createTime：2018年1月16日 下午9:40:50
 */
public interface Analyze {
	
	/**
	 * analyze
	 */
	public void analyze(List<CinemaEntity> cinemaEntities);
}

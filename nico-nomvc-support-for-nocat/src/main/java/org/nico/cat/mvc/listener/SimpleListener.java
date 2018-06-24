package org.nico.cat.mvc.listener;

import java.util.Map;

import org.nico.aoc.ConfigKey;
import org.nico.cat.mvc.config.NomvcConfig;
import org.nico.cat.mvc.container.analyze.Analyze;
import org.nico.cat.mvc.container.analyze.AnalyzeContainer;
import org.nico.cat.mvc.exception.LackRequiredConfigException;
import org.nico.cat.mvc.exception.NomvcException;
import org.nico.cat.mvc.exception.NullConfigException;
import org.nico.cat.mvc.scan.AbstractMVCScanner;
import org.nico.cat.mvc.scan.SimpleCinemaScanner;
import org.nico.noson.Noson;
import org.nico.noson.util.string.StringUtils;

public class SimpleListener {

	/**
	 * Scanner
	 */
	protected AbstractMVCScanner mvcscan;
	
	/**
	 * Analyze cinema entity
	 */
	protected Analyze analyze;
	
	public SimpleListener() {
		this.mvcscan = new SimpleCinemaScanner();
		this.analyze = new AnalyzeContainer();
	}

	/**
	 * Loader Configuration
	 * @throws LackRequiredConfigException 
	 */
	protected Map<String, Object> loaderConfig(String payload) throws NomvcException{
		if(StringUtils.isNotBlank(payload)){
			try{
				return Noson.parseNoson(payload);					
			}catch(Exception e){
				throw new LackRequiredConfigException("all");
			}
		}else{
			throw new NullConfigException();
		}
	}

	/**
	 * VerifyConfig
	 * @param config
	 * @return
	 * @throws LackRequiredConfigException
	 */
	protected Map<String, Object> verifyConfig(Map<String, Object> config) throws NomvcException{
		if(config == null){
			throw new LackRequiredConfigException("all");
		}
		if(!config.containsKey(NomvcConfig.CONFIG_FIELD_SCANPACK)){
			throw new LackRequiredConfigException(NomvcConfig.CONFIG_FIELD_SCANPACK);
		}
		return config;
	}
}

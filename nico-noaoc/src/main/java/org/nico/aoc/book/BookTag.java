package org.nico.aoc.book;

import java.util.List;
import java.util.Map;

/** 
 * In the XML scanning process, the tag of the book source is saved in BookTag.
 * 
 * @author nico
 * @version createTime：2018年3月10日 下午6:20:29
 */

public class BookTag {
	
	/**
	 * Tag name(prfix)
	 */
	private String name;
	
	/**
	 * Tag properties
	 */
	private Map<String, String> properties;
	
	/**
	 * Sub tags
	 */
	private List<BookTag> subTags;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public List<BookTag> getSubTags() {
		return subTags;
	}

	public void setSubTags(List<BookTag> subTags) {
		this.subTags = subTags;
	}
	
}

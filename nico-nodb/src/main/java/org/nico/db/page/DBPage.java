package org.nico.db.page;

public class DBPage {
	
	private Integer offset;
	
	private Integer length;
	
	private String sort;

	public DBPage(Integer offset, Integer length, String sort) {
		this.offset = offset;
		this.length = length;
		this.sort = sort;
	}
	
	public DBPage() {}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

}

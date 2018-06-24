package org.nico.noson.test.entity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** 
 * Complex type for performance test tuning
 * 
 * @author nico
 */
public class ComplexEntity{

	private int id;
	
	private char c;
	
	private Integer inte;
	
	private String str;
	
	private double num;
	
	private Date date;
	
	private Type type;
	
	private List<String> list;
	
	private String[] strs;
	
	private Map<String, Object> map;
	
	private Set<Integer> set;
	
	private float[] floats;
	
	private BigDecimal bigDecimal;
	
	private ComplexEntity entity;
	
	public String[] getStrs() {
		return strs;
	}

	public void setStrs(Object obj) {
		String[] sss = (String[])obj;
		this.strs = sss;
	}

	public float[] getFloats() {
		return floats;
	}

	public Integer getInte() {
		return inte;
	}

	public char getC() {
		return c;
	}

	public void setC(char c) {
		this.c = c;
	}

	public void setInte(Integer inte) {
		this.inte = inte;
	}

	public void setStrs(String[] strs) {
		this.strs = strs;
	}

	public void setFloats(float[] floats) {
		this.floats = floats;
	}

	public BigDecimal getBigDecimal() {
		return bigDecimal;
	}

	public void setBigDecimal(BigDecimal bigDecimal) {
		this.bigDecimal = bigDecimal;
	}

	public ComplexEntity getEntity() {
		return entity;
	}

	public void setEntity(ComplexEntity entity) {
		this.entity = entity;
	}

	@Override
	public String toString() {
		return "ComplexEntity [id=" + id + ", c=" + c + ", inte=" + inte + ", str=" + str + ", num=" + num + ", date="
				+ date + ", type=" + type + ", list=" + list + ", strs=" + Arrays.toString(strs) + ", map=" + map
				+ ", set=" + set + ", floats=" + Arrays.toString(floats) + ", bigDecimal=" + bigDecimal + ", entity="
				+ entity + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public double getNum() {
		return num;
	}

	public void setNum(double num) {
		this.num = num;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public Set<Integer> getSet() {
		return set;
	}

	public void setSet(Set<Integer> set) {
		this.set = set;
	}

	public static enum Type{
		
		Type1,
		
		TYPE2,
		
		TYPE3
		
	}
	
	
	
}

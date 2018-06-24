package org.nico.asm.proxy.reflect;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.Opcodes;

/** 
 * 
 * @author nico
 * @version createTime：2018年4月21日 下午8:02:38
 */

public class ASMConfiguration {

	protected final int OPCODE = Opcodes.ASM5;
	
	protected final Map<String, DescInfo> DESCINFO = new HashMap<String, ASMConfiguration.DescInfo>();
	
	{
		DESCINFO.put("I", new DescInfo("java/lang/Integer", "intValue", "()I", "(I)Ljava/lang/Integer;"));
		DESCINFO.put("F", new DescInfo("java/lang/Float", "floatValue", "()F", "(F)Ljava/lang/Float;"));
		DESCINFO.put("J", new DescInfo("java/lang/Long", "longValue", "()J", "(J)Ljava/lang/Long;"));
		DESCINFO.put("C", new DescInfo("java/lang/Character", "charValue", "()C", "(C)Ljava/lang/Character;"));
		DESCINFO.put("D", new DescInfo("java/lang/Double", "doubleValue", "()D", "(D)Ljava/lang/Double;"));
		DESCINFO.put("B", new DescInfo("java/lang/Byte", "byteValue", "()B", "(B)Ljava/lang/Byte;"));
		DESCINFO.put("S", new DescInfo("java/lang/Short", "shortValue", "()S", "(S)Ljava/lang/Short;"));
		DESCINFO.put("Z", new DescInfo("java/lang/Boolean", "equals", "(Ljava/lang/Object;)Z", "(Z)Ljava/lang/Boolean;"));
	}
	
	protected class DescInfo{
		
		private String owner;
		
		private String method;
		
		private String desc;
		
		private String convertDesc;

		public DescInfo(String owner, String method, String desc, String convertDesc) {
			super();
			this.owner = owner;
			this.method = method;
			this.desc = desc;
			this.convertDesc = convertDesc;
		}

		public String getOwner() {
			return owner;
		}

		public void setOwner(String owner) {
			this.owner = owner;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getConvertDesc() {
			return convertDesc;
		}

		public void setConvertDesc(String convertDesc) {
			this.convertDesc = convertDesc;
		}

	}
}
